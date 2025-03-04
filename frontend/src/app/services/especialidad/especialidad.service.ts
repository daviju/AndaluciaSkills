import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class EspecialidadService {
  private apiUrl = '/api/especialidades';

  constructor(private http: HttpClient) {}


  // BUSCAR TODAS LAS ESPECIALIDADES
  getEspecialidades(): Observable<any[]> {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.get<any[]>(`${this.apiUrl}`, { headers });
  }


  // BUSCAR ESPECIALIDAD
  getEspecialidad(id: number): Observable<any> {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.get<any>(`${this.apiUrl}/buscarespecialidad/${id}`, { headers });
  }


  // CREAR ESPECIALIDAD
  crearEspecialidad(especialidad: any): Observable<any> {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.post<any>(`${this.apiUrl}/crearespecialidad`, especialidad, { headers });
  }


  // EDITAR ESPECIALIDAD
  editarEspecialidad(id: number, especialidad: any): Observable<any> {
    const especialidadToSend = {
      idEspecialidad: id,
      nombre: especialidad.nombre,
      codigo: especialidad.codigo
    };

    const headers = this.getAuthHeaders();
    console.log('Token:', headers.get('Authorization'));
    console.log('Datos a enviar:', especialidadToSend);

    return this.http.put<any>(
      `${this.apiUrl}/modificarespecialidad/${id}`, 
      especialidadToSend, 
      { headers }
    );
  }


  // BORRAR ESPECIALIDAD
  borrarEspecialidad(id: number): Observable<any> {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.delete<any>(`${this.apiUrl}/eliminarespecialidad/${id}`, { headers });
  }


  // Helper method to get headers with token
  private getAuthHeaders(): HttpHeaders {
    const authData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
    const token = authData.token;
    
    if (!token) {
      console.error('No se encontró el token');
      return new HttpHeaders();
    }

    // Decodificar el token para debug
    try {
      const tokenPayload = JSON.parse(atob(token.split('.')[1]));
      console.log('Token payload:', tokenPayload);
    } catch (e) {
      console.error('Error decodificando el token:', e);
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}