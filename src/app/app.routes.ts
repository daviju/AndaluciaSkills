import { Routes } from '@angular/router';
import { ListaCompetidoresComponent } from './lista-competidores/lista-competidores.component';

export const routes: Routes = [
    { path: '', component: ListaCompetidoresComponent, pathMatch: 'full' },
    { path: 'experto', component: ListaCompetidoresComponent, pathMatch: 'full' },
];
