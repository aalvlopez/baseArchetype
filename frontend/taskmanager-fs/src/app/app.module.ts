import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { ApiModule } from './core/api/v1/api.module';

import { HttpClientModule } from '@angular/common/http';
import { TaskListComponent } from './task-list/task-list.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TaskListComponent
  ],
  imports: [
		ApiModule,
		HttpClientModule,
    BrowserModule,
    AppRoutingModule,
		FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
