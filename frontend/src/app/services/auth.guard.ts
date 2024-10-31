import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import {
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const isTokenExpired = this.tokenService.isTokenExpired();
    const isUserIdValid = this.tokenService.getUserId() > 0;
    debugger;
    if (!isTokenExpired && isUserIdValid) {
      return true;
    } else {
      this.router.navigate(['login']);
      return false;
    }
  }
}
