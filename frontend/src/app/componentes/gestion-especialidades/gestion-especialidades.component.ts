import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GestionarEspecialidadesComponent  {
  private apiUrl = '/api/especialidades';

  constructor(private http: HttpClient) {}

  // BUSCAR TODAS LAS ESPECIALIDADES
  getEspecialidades(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl, { headers: this.getAuthHeaders() });
  }

  // BUSCAR ESPECIALIDAD
  getEspecialidad(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/buscarespecialidad/${id}`, { headers: this.getAuthHeaders() });
  }

  // CREAR ESPECIALIDAD
  crearEspecialidad(especialidad: any): Observable<any> {
    // Asegurarnos de que los datos estén en el formato correcto
    const payload = {
      nombre: especialidad.nombre,
      codigo: especialidad.codigo
    };
    
    return this.http.post<any>(`${this.apiUrl}/crearespecialidad`, payload, { 
      headers: this.getAuthHeaders()
    });
  }

  // EDITAR ESPECIALIDAD
  editarEspecialidad(id: number, especialidad: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/modificarespecialidad/${id}`, especialidad, { 
      headers: this.getAuthHeaders() 
    });
  }

  // BORRAR ESPECIALIDAD
  borrarEspecialidad(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/eliminarespecialidad/${id}`, { 
      headers: this.getAuthHeaders() 
    });
  }

  // Helper method to get headers with token
  private getAuthHeaders(): HttpHeaders {
    const authData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
    const token = authData.token;
    
    if (!token) {
      console.error('No se encontró el token de autenticación');
    }

    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }
}