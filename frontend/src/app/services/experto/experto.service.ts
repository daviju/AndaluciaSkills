import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ExpertoService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getExpertos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/buscarexpertos`, {
      headers: this.getAuthHeaders()
    });
  }

  getExperto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/buscarexperto/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  crearExperto(experto: any): Observable<any> {
    const expertoToSend = {
      username: experto.username.trim(),
      password: experto.password.trim(),
      nombre: experto.nombre.trim(),
      apellidos: experto.apellidos.trim(),
      dni: experto.dni.trim(),
      role: 'ROLE_EXPERTO',
      especialidad: {
        idEspecialidad: Number(experto.especialidad_id_especialidad)
      }
    };
    
    const headers = this.getAuthHeaders();
    console.log('Datos a enviar al backend:', expertoToSend);
    
    return this.http.post<any>(`${this.apiUrl}/crearexperto`, expertoToSend, { headers }).pipe(
      tap({
        next: (response) => console.log('Respuesta exitosa:', response),
        error: (error) => console.error('Error en la petición:', error)
      })
    );
  }

  editarExperto(id: number, experto: any): Observable<any> {
    // Crear objeto con la estructura correcta que espera el backend
    const expertoToSend = {
      idUser: id,
      username: experto.username.trim(),
      nombre: experto.nombre.trim(),
      apellidos: experto.apellidos.trim(),
      dni: experto.dni.trim(),
      role: 'ROLE_EXPERTO',
      especialidad: {
        idEspecialidad: Number(experto.especialidad_id_especialidad)
      }
    };

    // Mantener la contraseña solo si se proporcionó una nueva
    if (experto.password && experto.password.trim() !== '') {
      expertoToSend['password'] = experto.password.trim();
    }

    const headers = this.getAuthHeaders();
    
    // Debug
    console.log('Token:', headers.get('Authorization'));
    console.log('Datos a enviar:', expertoToSend);

    return this.http.put<any>(
      `${this.apiUrl}/modificarexperto/${id}`,
      expertoToSend,
      { headers }
    ).pipe(
      tap({
        next: (response) => console.log('Respuesta exitosa:', response),
        error: (error) => console.error('Error en la petición:', error)
      })
    );
  }

  borrarExperto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/borrarexperto/${id}`, {
      headers: this.getAuthHeaders()
    }).pipe(
      tap({
        next: (response) => console.log('Experto eliminado correctamente'),
        error: (error) => console.error('Error al eliminar experto:', error)
      })
    );
  }

  private getAuthHeaders(): HttpHeaders {
    const authData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
    
    if (!authData.token) {
      console.error('No se encontró el token de autenticación');
      return new HttpHeaders();
    }

    try {
      const tokenParts = authData.token.split('.');
      if (tokenParts.length === 3) {
        const payload = JSON.parse(atob(tokenParts[1]));
        console.log('Token payload:', payload);
      }
    } catch (e) {
      console.error('Error al decodificar el token:', e);
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authData.token}`
    });
  }
}