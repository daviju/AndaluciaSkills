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
    console.log('Datos a enviar al backend (Crear):', expertoToSend);
    
    return this.http.post<any>(`${this.apiUrl}/CrearExperto`, expertoToSend, { headers });
  }

  editarExperto(id: number, experto: any): Observable<any> {
    // Creamos un objeto básico sin password
    const expertoToSend: any = {
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

    // Añadir contraseña solo si se ha proporcionado una nueva
    if (experto.password && experto.password.trim() !== '') {
      expertoToSend.password = experto.password.trim();
    }

    const headers = this.getAuthHeaders();
    console.log('Datos a enviar al backend (Modificar):', expertoToSend);
    console.log('URL:', `${this.apiUrl}/ModificarExperto/${id}`);
    
    return this.http.put<any>(
      `${this.apiUrl}/ModificarExperto/${id}`, 
      expertoToSend, 
      { headers }
    ).pipe(
      tap({
        next: (response) => console.log('Respuesta exitosa:', response),
        error: (error) => {
          console.error('Error en la petición:', error);
          console.error('Status:', error.status);
          console.error('Mensaje:', error.message);
          if (error.error) {
            console.error('Error del servidor:', error.error);
          }
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

    const token = authData.token;
    console.log('Token siendo usado:', token);

    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}