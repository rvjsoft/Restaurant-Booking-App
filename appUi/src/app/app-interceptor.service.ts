import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SessionService } from './session.service';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

    constructor(private session: SessionService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // All HTTP requests are going to go through this method
        if (!req.url.endsWith('login')) {
            req.headers.set('Authorization', 'Basic ' + btoa(this.session.userName + ':' + this.session.password))
        }
        return next.handle(req);
    }
}