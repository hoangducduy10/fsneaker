import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { TokenService } from '../../services/token.service';
import { UserResponse } from '../../responses/user/user.response';
import { OrderAdminComponent } from './orders/order.admin/order.admin.component';
import { CommonModule } from '@angular/common';
import { CategoryAdminComponent } from './category/category.admin/category.admin.component';
import { ProductAdminComponent } from './product/product.admin.component';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    OrderAdminComponent,
    CommonModule,
    CategoryAdminComponent,
    ProductAdminComponent,
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss',
})
export class AdminComponent implements OnInit {
  adminComponent: string = 'orders';
  userResponse?: UserResponse | null;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.userResponse = this.userService.getUserFromLocalStorage();
  }

  logout() {
    this.userService.removeUserFromLocalStorage();
    this.tokenService.removeToken();
    this.userResponse = this.userService.getUserFromLocalStorage();
  }

  showAdminComponent(componentName: string): void {
    this.adminComponent = componentName;
  }
}
