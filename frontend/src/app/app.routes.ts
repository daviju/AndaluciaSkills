import { Routes } from '@angular/router';
import { ListaCompetidoresComponent } from './componentes/lista-competidores/lista-competidores.component';
import { LoginComponent } from './componentes/login/login.component';
import { authGuard } from './guards/auth.guard';
import { roleGuard } from './guards/role.guard';

export const routes: Routes = [
  // Ruta por defecto - redirige al dashboard si está autenticado, al login si no
  { 
    path: '', 
    redirectTo: '/dashboard', 
    pathMatch: 'full' 
  },

  // Ruta de login
  { 
    path: 'login', 
    component: LoginComponent
  },

  // Ruta del dashboard - requiere autenticación
  { 
    path: 'dashboard', 
    component: ListaCompetidoresComponent,
    canActivate: [authGuard]
  },

  // Ruta de experto - requiere autenticación y rol de experto
  { 
    path: 'experto', 
    component: ListaCompetidoresComponent,
    canActivate: [authGuard, roleGuard],
    data: { roles: ['ROLE_EXPERTO'] }
  },

  // Ruta para manejar URLs no válidas
  { 
    path: '**', 
    redirectTo: '/dashboard' 
  }
];