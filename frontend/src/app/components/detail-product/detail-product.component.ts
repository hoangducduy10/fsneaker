import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { ProductService } from '../../services/product.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';
import { ProductImage } from '../../models/product.image';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { OrderDTO } from '../../dtos/user/order/order.dto';

@Component({
  selector: 'app-detail-product',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './detail-product.component.html',
  styleUrl: './detail-product.component.scss',
})
export class DetailProductComponent implements OnInit {
  product?: Product;
  productId: number = 0;
  currentImageIndex: number = 0;
  quantity: number = 1;
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
    private productService: ProductService,
    private cartService: CartService,
    private activatedRoute: ActivatedRoute,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit() {
    this.activatedRoute.params.subscribe((params) => {
      const idParam = +params['id'];
      if (!isNaN(idParam)) {
        this.productId = idParam;
        this.getProductDetail(this.productId);
      } else {
        console.error('Invalid productId: ', idParam);
      }
    });
  }

  getProductDetail(productId: number) {
    this.productService.getDetailProduct(productId).subscribe({
      next: (response: any) => {
        if (response.product_images && response.product_images.length > 0) {
          response.product_images.forEach((product_image: ProductImage) => {
            product_image.imageUrl = `${environment.apiBaseUrl}/products/images/${product_image.imageUrl}`;
          });
        }

        this.product = response;
        this.showImage(0);
      },
      error: (error: any) => {
        console.error('Error fetching detail: ', error);
      },
    });
  }

  showImage(index: number): void {
    debugger;
    if (
      this.product &&
      this.product.product_images &&
      this.product.product_images.length > 0
    ) {
      if (index < 0) {
        index = 0;
      } else if (index >= this.product.product_images.length) {
        index = this.product.product_images.length - 1;
      }
      this.currentImageIndex = index;
    }
  }

  thumbnailClick(index: number) {
    this.currentImageIndex = index;
  }

  nextImage(): void {
    debugger;
    this.showImage(this.currentImageIndex + 1);
  }

  previousImage(): void {
    debugger;
    this.showImage(this.currentImageIndex - 1);
  }

  addToCart(): void {
    debugger;
    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity);
      alert('Added to cart successfully!');
    } else {
      alert('Cannot add to cart because the product is null!');
    }
  }

  increaseQuantity(): void {
    this.quantity++;
  }
  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  calculateTotal(): void {
    this.totalAmount = this.cartItems.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  buyNow() {
    if (this.product) {
      this.orderData.cart_items = [
        {
          product_id: this.product.id,
          quantity: this.quantity,
        },
      ];
      this.orderData.total_money = this.product.price * this.quantity;

      this.orderService.placeOrder(this.orderData).subscribe({
        next: (orderResponse: any) => {
          const orderId = orderResponse.id;
          this.router.navigate(['/orders', orderId]);
        },
        error: (error: any) => {
          console.error('Error creating order:', error);
          alert('Failed to create order!');
        },
      });
    } else {
      alert('Product not found!');
    }
  }
}
