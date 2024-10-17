import { bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/components/home/home.component';
import { DetailProductComponent } from './app/components/detail-product/detail-product.component';
import { OrderConfirmComponent } from './app/components/order-confirm/order-confirm.component';
import { OrderComponent } from './app/components/order/order.component';
import { LoginComponent } from './app/components/login/login.component';
import { RegisterComponent } from './app/components/register/register.component';
import { HTTP_INTERCEPTORS, provideHttpClient } from '@angular/common/http';
import { TokenInterceptor } from './app/interceptors/token.interceptor';
import { importProvidersFrom } from '@angular/core';
import { TokenService } from './app/services/token.service';

bootstrapApplication(OrderComponent, {
  providers: [
    provideHttpClient(),
    TokenService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
  ],
}).catch((err) => console.error(err));
