import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

export const roleGuard = (route: any) => {
  const router = inject(Router);
  const authService = inject(AuthService);

  // Obtiene los roles permitidos de la ruta
  const roles = route.data?.['roles'] as Array<string>;

  if (!roles || roles.length === 0) {
    return true;
  }

  // Verifica si el usuario tiene el rol necesario
  const userRole = authService.getRol();
  if (roles.includes(userRole)) {
    return true;
  }

  // Si no tiene el rol adecuado, redirige al dashboard
  router.navigate(['/dashboard']);
  return false;
};