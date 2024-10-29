import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Product } from '../../models/product';
import { CartService } from '../../services/cart.service';
import { ProductService } from '../../services/product.service';
import { environment } from '../../environments/environment';
import { OrderService } from '../../services/order.service';
import { OrderDTO } from '../../dtos/user/order/order.dto';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
})
export class OrderComponent implements OnInit {
  orderForm: FormGroup;
  cartItems: { product: Product; quantity: number }[] = [];

  couponCode: string = '';
  totalAmount: number = 0;
  orderData: OrderDTO = {
    user_id: 2,
    fullname: '',
    email: '',
    phone_number: '',
    address: '',
    note: '',
    total_money: 10,
    payment_method: 'cod',
    shipping_method: 'express',
    coupon_code: '',
    cart_items: [],
  };

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private fb: FormBuilder
  ) {
    this.orderForm = this.fb.group({
      fullname: ['Hoang Duc Duy', Validators.required],
      email: ['duy1024@gmail.com', [Validators.email]],
      phone_number: [
        '0862004184',
        [Validators.required, Validators.pattern(/^0\d{9}$/)],
      ],
      address: ['Ha Tay', [Validators.required, Validators.minLength(5)]],
      note: ['Hàng lóng'],
      shipping_method: ['express'],
      payment_method: ['other'],
    });
  }

  ngOnInit(): void {
    debugger;
    const cart = this.cartService.getCart();
    const productIds = Array.from(cart.keys()); // chuyen list id tu Map sang Cart

    debugger;
    this.productService.getProductsByIds(productIds).subscribe({
      next: (products) => {
        debugger;
        this.cartItems = productIds.map((productId) => {
          debugger;
          const product = products.find((p) => p.id === productId);
          if (product) {
            product.thumbnail = `${environment.apiBaseUrl}/products/images/${product.thumbnail}`;
          }
          return {
            product: product!,
            quantity: cart.get(productId)!,
          };
        });
      },
      complete: () => {
        debugger;
        this.calculateTotal();
      },
      error: (error: any) => {
        debugger;
        console.log('Error fetching detail: ', error);
      },
    });
  }

  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  applyCoupon(): void {}

  placeOrder() {
    debugger;
    if (this.orderForm.valid) {
      // this.orderData.fullname = this.orderForm.get('fullname')!.value;
      // Dung toan tu spread (...) de copy value tu form vao orderData
      this.orderData = {
        ...this.orderData,
        ...this.orderForm.value,
      };
      this.orderData.cart_items = this.cartItems.map((cartItem) => ({
        product_id: cartItem.product.id,
        quantity: cartItem.quantity,
      }));

      this.orderService.placeOrder(this.orderData).subscribe({
        next: (response) => {
          debugger;
          alert('Đặt hàng thành công!');
        },
        complete: () => {
          debugger;
          this.calculateTotal();
        },
        error: (error: any) => {
          debugger;
          alert('Lỗi khi đặt hàng!');
          console.log('Lỗi khi đặt hàng!', error);
        },
      });
    } else {
      alert('Dữ liệu không hợp lệ. Vui lòng kiểm tra lại đơn hàng!');
    }
  }
}
