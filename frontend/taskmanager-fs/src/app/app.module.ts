import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { ApiModule } from './core/api/v1/api.module';

import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TaskListComponent } from './task-list/task-list.component';

import { CustomHttpInterceptor } from './custom-http.interceptor';
import { TopBarComponent } from './top-bar/top-bar.component'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TaskListComponent,
    TopBarComponent
  ],
  imports: [
		ApiModule,
		HttpClientModule,
    BrowserModule,
    AppRoutingModule,
		FormsModule
  ],
  providers: [{
      provide : HTTP_INTERCEPTORS,
      useClass: CustomHttpInterceptor,
      multi   : true,
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
