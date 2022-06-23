import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { UiComponentsModule } from './ui-components/ui-components.module';
import { RegisterUserModule } from './register-user/register-user.module';
import { CustomerModule } from './customer/customer.module';
import { RestaurantModule } from './restaurant/restaurant.module';
import { GeneralModule } from './general/general.module';
import { LoadBarComponent } from './load-bar/load-bar.component';
import { AuthInterceptorService } from './app-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoadBarComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    UiComponentsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RegisterUserModule,
    CustomerModule,
    RestaurantModule,
    GeneralModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS, 
    useClass: AuthInterceptorService, 
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
