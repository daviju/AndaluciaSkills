import { Routes } from '@angular/router';
import { ListaCompetidoresComponent } from './componentes/lista-competidores/lista-competidores.component';
import { LoginComponent } from './componentes/login/login.component';

export const routes: Routes = [
    { path: '', component: ListaCompetidoresComponent, pathMatch: 'full' },
    { path: 'login', component: LoginComponent, pathMatch: 'full' },
    { path: 'experto', component: ListaCompetidoresComponent, pathMatch: 'full' },
];
