import { Product } from './product';

export interface OrderDetail {
  id: number;
  product: Product;
  price: number;
  number_of_products: number;
  total_money: number;
  color?: string;
}
