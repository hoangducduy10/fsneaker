import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.response';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';
import { NgbPopoverConfig, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TokenService } from '../../services/token.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule, NgbModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent implements OnInit {
  userResponse?: UserResponse | null;
  isPopoverOpen = false;

  togglePopover(event: Event): void {
    event.preventDefault();
    this.isPopoverOpen = !this.isPopoverOpen;
  }

  handleItemClick(index: number): void {
    if (index === 2) {
      this.userService.removeUserFromLocalStorage();
      this.tokenService.removeToken();
      this.userResponse = this.userService.getUserFromLocalStorage();
    }
    this.isPopoverOpen = false;
  }

  constructor(
    private userService: UserService,
    private popoverConfig: NgbPopoverConfig,
    private tokenService: TokenService
  ) {}

  ngOnInit() {
    this.userResponse = this.userService.getUserFromLocalStorage();
  }
}
