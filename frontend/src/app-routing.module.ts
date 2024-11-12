import { provideRouter, RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './app/components/home/home.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { DetailProductComponent } from './app/components/detail-product/detail-product.component';
import { OrderComponent } from './app/components/order/order.component';
import { OrderDetailComponent } from './app/components/order-detail/order.detail.component';
import { AdminComponent } from './app/components/admin/admin.component';
import { AuthGuardFn } from './app/guards/auth.guard';
import { AdminGuardFn } from './app/guards/admin.guard';
import { OrderAdminComponent } from './app/components/admin/orders/order.admin/order.admin.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'products/:id', component: DetailProductComponent },
  { path: 'orders', component: OrderComponent, canActivate: [AuthGuardFn] },
  { path: 'orders/:id', component: OrderDetailComponent },
  // Admin
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuardFn] },
  {
    path: 'admin/orders',
    component: OrderAdminComponent,
    canActivate: [AdminGuardFn],
  },
];
