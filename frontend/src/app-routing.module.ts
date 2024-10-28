import { provideRouter, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/components/home/home.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { DetailProductComponent } from './app/components/detail-product/detail-product.component';
import { OrderComponent } from './app/components/order/order.component';
import { OrderDetailComponent } from './app/components/order-detail/order.detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products/:id', component: DetailProductComponent },
  { path: 'orders', component: OrderComponent },
  { path: 'orders/:id', component: OrderDetailComponent },
];
