import { Routes } from '@angular/router';
import { LoginComponent } from './componentes/login/login.component';
import { ListaCompetidoresComponent } from './componentes/listado-competidores/listado-competidores.component';

// EXPERTOS
import { GestionExpertosComponent } from './componentes/gestion-expertos/gestion-expertos.component';
import { CrearExpertosComponent } from './componentes/crear-expertos/crear-expertos.component';
import { VerExpertosComponent } from './componentes/ver-expertos/ver-expertos.component';

// ESPECIALIDAD
import { GestionEspecialidadesComponent } from './componentes/gestion-especialidades/gestion-especialidades.component';
import { CrearEspecialidadesComponent } from './componentes/crear-especialidades/crear-especialidades.component';
import { VerEspecialidadComponent } from './componentes/ver-especialidades/ver-especialidades.component';

// GANADORES
import { VerGanadoresComponent } from './componentes/ver-ganadores/ver-ganadores.component';

// PARTICIPANTES
import { GestionParticipantesComponent } from './componentes/gestion-participantes/gestion-participantes.component';
import { CrearParticipantesComponent } from './componentes/crear-participantes/crear-participantes.component';
import { VerParticipantesComponent } from './componentes/ver-participantes/ver-participantes.component';

// PUNTUACIONES
import { GestionPuntuacionesComponent } from './componentes/gestion-puntuaciones/gestion-puntuaciones.component';

// PRUEBAS
import { GestionPruebasComponent } from './componentes/gestion-pruebas/gestion-pruebas.component';
import { ListadoPruebasPorPuntuacionComponent } from './componentes/listado-pruebas-por-puntuacion/listado-pruebas-por-puntuacion.component';

// PUNTUAR ITEMS
import { PuntuacionItemsComponent } from './componentes/puntuacion-items/puntuacion-items.component';

export const routes: Routes = [
  // Rutas públicas
  { path: '', component: ListaCompetidoresComponent },
  { path: 'login', component: LoginComponent },

  // Rutas de administrador
  { path: 'admin', children: [
    { path: 'especialidades', component: GestionEspecialidadesComponent },
    { path: 'crear-especialidad', component: CrearEspecialidadesComponent },
    { path: 'editar-especialidad/:id', component: CrearEspecialidadesComponent },
    { path: 'expertos', component: GestionExpertosComponent },
    { path: 'crear-experto', component: CrearExpertosComponent },
    { path: 'ver-experto/:id', component: VerExpertosComponent },
    { path: 'editar-experto/:id', component: CrearExpertosComponent },
    { path: 'ganadores', component: VerGanadoresComponent },
    { path: 'ver-especialidad/:id', component: VerEspecialidadComponent },
    { path: 'participantes', component: GestionParticipantesComponent },
    { path: 'crear-participante', component: CrearParticipantesComponent },
    { path: 'ver-participante/:id', component: VerParticipantesComponent },
    { path: 'editar-participante/:id', component: CrearParticipantesComponent }
  ]},

  // Rutas de experto
  { path: 'experto', children: [
    { path: 'puntuaciones', component: GestionPuntuacionesComponent },
    { path: 'participantes', component: GestionParticipantesComponent },
    { path: 'pruebas', component: GestionPruebasComponent },
    { path: 'listar-prueba-puntuacion/:participanteId', component: ListadoPruebasPorPuntuacionComponent},
    { path: 'puntuar-items/:participanteId/:pruebaId', component: PuntuacionItemsComponent }
  ]},

  { path: '**', redirectTo: '' }
];