import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { AuthService } from "../auth/auth.service";
import { Injectable } from "@angular/core";
import { catchError, switchMap, tap } from 'rxjs/operators';

export interface DtoEvaluacionItem {
  evaluacion_id_evaluacion: number;
  item_id_item: number;
  valoracion: number;
  prueba_id_prueba: number;
}

export interface DtoEvaluacion {
  idEvaluacion?: number;
  prueba_idPrueba: number;
  participante_idParticipante: number;
  user_idUser: number;
  notaFinal: number;
}

export interface Evaluacion {
  prueba_id_prueba: number;
  participante_id_participante: number;
  user_id_user: number;
  evaluacionItems: EvaluacionItem[];
}

export interface EvaluacionItem {
  item_id_item: number;
  valoracion: number;
  prueba_id_prueba: number;
}

@Injectable({
  providedIn: 'root'
})
export class PuntuacionesService {
  private apiUrl = '/api';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  getParticipantesByEspecialidad(): Observable<any[]> {
    const especialidadId = this.authService.getEspecialidadFromToken();

    if (!especialidadId) {
      return throwError(() => new Error('No se encontró ID de especialidad'));
    }

    // Asegúrate de que el token se obtiene correctamente
    const token = this.authService.getToken();
    if (!token) {
      return throwError(() => new Error('No se encontró el token'));
    }

    const headers = new HttpHeaders()
      .set('Authorization', `Bearer ${token}`)
      .set('Content-Type', 'application/json');

    console.log('Headers:', headers);
    console.log('Especialidad ID:', especialidadId);

    return this.http.get<any[]>(
      `${this.apiUrl}/participantes/buscarparticipantesespecialidad/${especialidadId}`,
      { headers }
    ).pipe(
      tap(response => console.log('Respuesta:', response)),
      catchError(error => {
        console.error('Error detallado:', error);
        if (error.status === 403) {
          // Si es un error de autorización, cerrar sesión
          this.authService.cerrarSesion();
        }
        return throwError(() => error);
      })
    );
  }

  getItemsByPrueba(pruebaId: number): Observable<any[]> {
    console.log('Solicitando items para prueba:', pruebaId);

    return this.http.get<any[]>(
      `${this.apiUrl}/items/buscarItemPorPrueba/${pruebaId}`
    ).pipe(
      tap(items => {
        console.log('Items recibidos:', items);
      }),
      catchError(error => {
        console.error('Error al obtener items:', error);
        if (error.status === 403) {
          console.error('Error de autorización. Redirigiendo al login...');
        }
        return throwError(() => error);
      })
    );
  }

  verificarEvaluacionExistente(pruebaId: number, participanteId: number): Observable<boolean> {
    console.log('Verificando evaluación existente:', { pruebaId, participanteId });

    return this.http.get<boolean>(
      `${this.apiUrl}/pruebas/evaluacion/existe/${pruebaId}/${participanteId}`
    ).pipe(
      tap(existe => {
        console.log('Resultado verificación:', existe);
      }),
      catchError(error => {
        console.error('Error al verificar evaluación:', error);
        return throwError(() => error);
      })
    );
  }

  guardarEvaluacion(evaluacion: Evaluacion): Observable<any> {
    console.log('Guardando evaluación:', evaluacion);

    const evaluacionData = {
      idPrueba: evaluacion.prueba_id_prueba,
      idParticipante: evaluacion.participante_id_participante,
      idUser: evaluacion.user_id_user
    };

    return this.http.post<DtoEvaluacion>(
      `${this.apiUrl}/pruebas/evaluacion`,
      evaluacionData
    ).pipe(
      tap(response => {
        console.log('Evaluación creada:', response);
      }),
      switchMap(evaluacionCreada => {
        const items = evaluacion.evaluacionItems.map(item => ({
          evaluacion_id_evaluacion: evaluacionCreada.idEvaluacion!,
          item_id_item: item.item_id_item,
          valoracion: Number(item.valoracion),
          prueba_id_prueba: evaluacion.prueba_id_prueba
        }));

        console.log('Guardando items de evaluación:', items);

        return this.http.post<DtoEvaluacionItem[]>(
          `${this.apiUrl}/pruebas/evaluaciones/${evaluacionCreada.idEvaluacion}/valoraciones`,
          items
        ).pipe(
          tap(response => {
            console.log('Items guardados y nota final actualizada:', response);
          })
        );
      }),
      catchError(error => {
        if (error.status === 409) {
          console.error('Conflicto: Participante ya evaluado');
          return throwError(() => new Error('Este participante ya ha sido evaluado en esta prueba'));
        }
        console.error('Error detallado:', error);
        return throwError(() => new Error(`Error al guardar la evaluación: ${error.message || 'Error desconocido'}`));
      })
    );
  }
}