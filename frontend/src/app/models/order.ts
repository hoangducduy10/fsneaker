export interface Order {
  user_id: number;
  fullname: string;
  email: string;
  phone_number: string;
  address: string;
  note: string;
  total_money: number;
  shipping_method: string;
  payment_method: string;
  cart_items: {
    product_id: number;
    quantity: number;
  }[];
}
