import { Routes } from '@angular/router';
import { LoginComponent } from './componentes/login/login.component';
import { ListaCompetidoresComponent } from './componentes/listado-competidores/listado-competidores.component';

// EXPERTOS
import { GestionarExpertosComponent } from './componentes/gestion-expertos/gestion-expertos.component';
import { CrearExpertoComponent } from './componentes/crear-expertos/crear-expertos.component';
import { VerExpertoComponent } from './componentes/ver-expertos/ver-expertos.component';

// ESPECIALIDAD
import { GestionarEspecialidadesComponent } from './componentes/gestion-especialidades/gestion-especialidades.component';
import { CrearEspecialidadComponent } from './componentes/crear-especialidades/crear-especialidades.component';
import { VerEspecialidadComponent } from './componentes/ver-especialidades/ver-especialidades.component';

// GANADORES
import { VisualizarGanadoresComponent } from './componentes/ver-ganadores/ver-ganadores.component';

// PARTICIPANTES
import { GestionarParticipantesComponent } from './componentes/gestion-participantes/gestion-participantes.component';
import { CrearParticipantesComponent } from './componentes/crear-participantes/crear-participantes.component';
import { VerParticipantesComponent } from './componentes/ver-participantes/ver-participantes.component';

// PUNTUACIONES
import { GestionPuntuacionesComponent } from './componentes/gestion-puntuaciones/gestion-puntuaciones.component';

// PRUEBAS
import { GestionarPruebasComponent } from './componentes/gestion-pruebas/gestion-pruebas.component';
import { ListadoPruebasPorPuntuacionComponent } from './componentes/listado-pruebas-por-puntuacion/listado-pruebas-por-puntuacion.component';

// PUNTUAR ITEMS
import { PuntuacionItemsComponent } from './componentes/puntuacion-items/puntuacion-items.component';

export const routes: Routes = [
  // Rutas públicas
  { path: '', component: ListaCompetidoresComponent },
  { path: 'login', component: LoginComponent },

  // Rutas de administrador
  { path: 'admin', children: [
    { path: 'especialidades', component: GestionarEspecialidadesComponent },
    { path: 'crear-especialidad', component: CrearEspecialidadComponent },
    { path: 'editar-especialidad/:id', component: CrearEspecialidadComponent },
    { path: 'expertos', component: GestionarExpertosComponent },
    { path: 'crear-experto', component: CrearExpertoComponent },
    { path: 'ver-experto/:id', component: VerExpertoComponent },
    { path: 'editar-experto/:id', component: CrearExpertoComponent },
    { path: 'ganadores', component: VisualizarGanadoresComponent },
    { path: 'ver-especialidad/:id', component: VerEspecialidadComponent },
    { path: 'participantes', component: GestionarParticipantesComponent },
    { path: 'crear-participante', component: CrearParticipantesComponent },
    { path: 'ver-participante/:id', component: VerParticipantesComponent },
    { path: 'editar-participante/:id', component: CrearParticipantesComponent }
  ]},

  // Rutas de experto
  { path: 'experto', children: [
    { path: 'puntuaciones', component: GestionPuntuacionesComponent },
    { path: 'participantes', component: GestionarParticipantesComponent },
    { path: 'pruebas', component: GestionarPruebasComponent },
    { path: 'listar-prueba-puntuacion/:participanteId', component: ListadoPruebasPorPuntuacionComponent},
    { path: 'puntuar-items/:participanteId/:pruebaId', component: PuntuacionItemsComponent }
  ]},

  { path: '**', redirectTo: '' }
];