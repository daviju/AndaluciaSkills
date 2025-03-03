import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ParticipantesService {
  private apiUrl = '/api/participantes';

  constructor(private http: HttpClient) { }

  getParticipantes(): Observable<any[]> {
    console.log('Haciendo petición a:', this.apiUrl);
    return this.http.get<any[]>(`${this.apiUrl}`, {
      headers: this.getAuthHeaders()
    }).pipe(
      tap(response => console.log('Respuesta del servidor:', response)),
      catchError(error => {
        console.error('Error completo:', error);
        console.error('Headers de la respuesta:', error.headers);
        console.error('Status:', error.status);
        console.error('Error message:', error.error);
        return throwError(() => error);
      })
    );
  }

  getParticipante(id: number): Observable<any> {
    console.log(`Solicitando participante con ID: ${id}`);
    return this.http.get<any>(`${this.apiUrl}/BuscarParticipante/${id}`, {
      headers: this.getAuthHeaders()
    }).pipe(
      tap(data => console.log('Datos recibidos:', data)),
      catchError(error => {
        console.error('Error al obtener participante:', error);
        return throwError(() => error);
      })
    );
  }

  crearParticipante(participante: any): Observable<any> {
    // Asegurarse de que el ID de especialidad sea un número
    if (participante.especialidad_id_especialidad) {
      participante.especialidad_id_especialidad = Number(participante.especialidad_id_especialidad);
    }

    const headers = this.getAuthHeaders();
    console.log('Datos a enviar:', participante); // Para depuración
    return this.http.post<any>(`${this.apiUrl}/CrearParticipante`, participante, { headers });
  }

  editarParticipante(id: number, participante: any): Observable<any> {
    // Asegurarse de que el objeto a enviar tiene el ID correcto
    const participanteToUpdate = {
      idParticipante: id,  // Añadir explícitamente el ID
      nombre: participante.nombre,
      apellidos: participante.apellidos,
      centro: participante.centro,
      especialidad_id_especialidad: Number(participante.especialidad_id_especialidad)
    };

    console.log('Datos a enviar en edición:', participanteToUpdate);

    return this.http.put<any>(
      `${this.apiUrl}/ModificarParticipante/${id}`,
      participanteToUpdate,
      { headers: this.getAuthHeaders() }
    ).pipe(
      tap(response => console.log('Respuesta del servidor:', response)),
      catchError(error => {
        console.error('Error en la petición:', error);
        return throwError(() => error);
      })
    );
  }

  borrarParticipante(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/BorrarParticipante/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  private getAuthHeaders(): HttpHeaders {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    console.log('Datos auth raw:', datosAuth);  // Ver los datos exactos en localStorage
    const datos = JSON.parse(datosAuth || '{}');
    const token = datos.token;
    console.log('Token exacto:', token);  // Ver el token exacto

    if (!token) {
      console.error('No hay token disponible');
      return new HttpHeaders();
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log('Header Authorization:', headers.get('Authorization'));
    return headers;
  }

}
