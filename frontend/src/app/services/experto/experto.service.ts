import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExpertoService {
  private apiUrl = '/api/users';

  constructor(private http: HttpClient) {}

  getExpertos(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/BuscarExpertos`, {
      headers: this.getAuthHeaders()
    });
  }

  getExperto(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/BuscarExperto/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  crearExperto(experto: any): Observable<any> {
    // Crear una copia limpia del objeto experto con solo los campos necesarios
    const expertoToSend = {
        username: experto.username.trim(),
        password: experto.password.trim(), // Aseguramos que la contraseña está en texto plano
        nombre: experto.nombre.trim(),
        apellidos: experto.apellidos.trim(),
        dni: experto.dni.trim(),
        role: 'ROLE_EXPERTO',
        especialidad_id_especialidad: Number(experto.especialidad_id_especialidad)
    };
    
    const headers = this.getAuthHeaders();
    console.log('Datos a enviar al backend:', expertoToSend);
    
    return this.http.post<any>(`${this.apiUrl}/CrearExperto`, expertoToSend, { headers });
  }

  editarExperto(id: number, experto: any): Observable<any> {
    // Asegurarse de que el ID de especialidad sea un número
    const expertoToSend = {
      idUser: id,
      username: experto.username.trim(),
      nombre: experto.nombre.trim(),
      apellidos: experto.apellidos.trim(),
      dni: experto.dni.trim(),
      role: 'ROLE_EXPERTO',
      especialidad_id_especialidad: Number(experto.especialidad_id_especialidad)
    };

    // IMPORTANTE: No incluimos el campo password al editar

    const headers = this.getAuthHeaders();
    console.log('Datos a enviar para edición:', expertoToSend);
    
    return this.http.put<any>(`${this.apiUrl}/ModificarExperto/${id}`, expertoToSend, { headers })
      .pipe(
        tap({
          next: (response) => console.log('Respuesta exitosa:', response),
          error: (error) => {
            console.error('Error en la petición:', error);
            console.error('Status:', error.status);
            console.error('Mensaje de error:', error.message);
          }
        })
      );
  }

  borrarExperto(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/BorrarExperto/${id}`, {
      headers: this.getAuthHeaders()
    });
  }

  private getAuthHeaders(): HttpHeaders {
    const authData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
    
    if (!authData.token) {
      console.error('No se encontró el token de autenticación');
      return new HttpHeaders();
    }

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${authData.token}`
    });
  }
}