import { ApplicationConfig, provideZoneChangeDetection, inject } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { HttpRequest, provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthService } from './services/auth/auth.service';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideToastr } from 'ngx-toastr';
import { ReactiveFormsModule } from '@angular/forms';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimations(),
    provideToastr({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
      closeButton: true
    }),
    AuthService,
    provideHttpClient(
      withInterceptors([
        (request: HttpRequest<unknown>, next) => {
          const authService = inject(AuthService);
          const token = authService.getToken();
          
          console.log('URL original:', request.url);
          console.log('Token encontrado:', token ? `${token.substring(0, 20)}...` : 'No token');
          
          if (token) {
            const authReq = request.clone({
              headers: request.headers
                .set('Authorization', `Bearer ${token}`)
                .set('Content-Type', 'application/json')
            });
            
            console.log('Headers completos:', {
              Authorization: authReq.headers.get('Authorization'),
              'Content-Type': authReq.headers.get('Content-Type')
            });

            return next(authReq);
          }
          
          return next(request);
        }
      ])
    )
  ]
};