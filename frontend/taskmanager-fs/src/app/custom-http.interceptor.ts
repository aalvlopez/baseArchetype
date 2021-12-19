import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class CustomHttpInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
		let autHeader:string|null = localStorage.getItem('autHeader');
		if(autHeader!=null){
			request = request.clone({
		      setHeaders: {
		        'Authorization': `${autHeader}`,
		      },
	    });
		}
    return next.handle(request);
  }
}
