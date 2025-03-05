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

          console.log('URL de la petición:', request.url);
          console.log('Token encontrado:', token);
          
          if (token) {
            const authReq = request.clone({
              headers: request.headers.set('Authorization', `Bearer ${token}`)
            });

            console.log('Headers de la petición:', authReq.headers.keys());
            return next(authReq);
          }
          console.warn('No se encontró token válido o el token no es un string');
          return next(request);
        }
      ])
    )
  ]
};