import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';
import { Product } from '../models/product';
import { OrderDTO } from '../dtos/user/order/order.dto';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  private apiGetOrders = `${environment.apiBaseUrl}/orders`;

  constructor(private http: HttpClient) {}

  placeOrder(orderData: OrderDTO): Observable<any> {
    return this.http.post(`${this.apiGetOrders}`, orderData);
  }
}
