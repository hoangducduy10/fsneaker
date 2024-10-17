export class CartItemDTO {
  product_id: number;
  quantity: number;

  constructor(data: any) {
    this.product_id = data.product_id;
    this.quantity = data.quantity;
  }
}
