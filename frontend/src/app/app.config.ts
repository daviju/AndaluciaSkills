import { ApplicationConfig, provideZoneChangeDetection, inject } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { HttpRequest, provideHttpClient, withInterceptors } from '@angular/common/http';
import { AuthService } from './services/auth/auth.service';
import { provideAnimations } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideAnimations(), 
    AuthService, // Proporcionamos AuthService a nivel de aplicación
    provideHttpClient(
      withInterceptors([
        (request: HttpRequest<unknown>, next) => {
          const authService = inject(AuthService); // Inyectamos la instancia existente
          const token = authService.getToken();
          
          if (token) {
            const authReq = request.clone({
              headers: request.headers.set('Authorization', `Bearer ${token}`)
            });
            return next(authReq);
          }
          return next(request);
        }
      ])
    )
  ]
};