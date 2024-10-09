import { Injectable } from '@angular/core';
import { ProductService } from './product.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  // Dung map de luu tru gio hang, key la id san pham, value la so luong
  private cart: Map<number, number> = new Map();

  constructor(private productService: ProductService) {
    // Lay data cart tu localStorage khi khoi tao service
    const sotredCart = localStorage.getItem('cart');
    if (sotredCart) {
      this.cart = new Map(JSON.parse(sotredCart));
    }
  }

  addToCart(productId: number, quantity: number = 1): void {
    debugger;
    if (this.cart.has(productId)) {
      this.cart.set(productId, this.cart.get(productId)! + quantity);
    } else {
      this.cart.set(productId, quantity);
    }
    this.saveCartToLocalStorage();
  }

  getCart(): Map<number, number> {
    return this.cart;
  }

  private saveCartToLocalStorage(): void {
    debugger;
    localStorage.setItem(
      'cart',
      JSON.stringify(Array.from(this.cart.entries()))
    );
  }

  clearCart(): void {
    this.cart.clear();
    this.saveCartToLocalStorage();
  }
}
