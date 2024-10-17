import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { ProductService } from '../../services/product.service';
import { CategoryService } from '../../services/category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../../models/product';
import { environment } from '../../environments/environment';
import { ProductImage } from '../../models/product.image';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';

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

  constructor(
    private productService: ProductService, // private router: Router, // private categoryService: CategoryService // private activatedRoute: ActivatedRoute
    private cartService: CartService
  ) {}

  ngOnInit() {
    debugger;
    const idParam = 8;
    if (idParam !== null) {
      this.productId = +idParam;
    }
    if (!isNaN(this.productId)) {
      this.productService.getDetailProduct(this.productId).subscribe({
        next: (response: any) => {
          debugger;
          if (response.product_images && response.product_images.length > 0) {
            response.product_images.forEach((product_image: ProductImage) => {
              product_image.imageUrl = `${environment.apiBaseUrl}/products/images/${product_image.imageUrl}`;
            });
          }

          debugger;
          this.product = response;
          this.showImage(0);
        },
        complete: () => {
          debugger;
        },
        error: (error: any) => {
          debugger;
          console.error('Error fetching detail: ', error);
        },
      });
    } else {
      console.error('Invalid productId: ', idParam);
    }
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
    } else {
      console.error('Cannot add to cart because the product is null!');
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

  buyNow(): void {}
}
