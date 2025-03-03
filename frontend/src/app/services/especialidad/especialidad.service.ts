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
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.http.put<any>(`${this.apiUrl}/modificarespecialidad/${id}`, especialidad, { headers });
  }


  // BORRAR ESPECIALIDAD
  borrarEspecialidad(id: number): Observable<any> {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.delete<any>(`${this.apiUrl}/eliminarespecialidad/${id}`, { headers });
  }


  // Helper method to get headers with token
  private getAuthHeaders(): HttpHeaders {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }
}