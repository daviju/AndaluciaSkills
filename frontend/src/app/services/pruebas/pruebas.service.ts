import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { from, Observable, throwError } from 'rxjs';
import { catchError, map, switchMap, tap } from 'rxjs/operators';
import { AuthService } from '../../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PruebasService {
  private apiUrl = '/api/pruebas';

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) { }

  private getAuthHeaders(): HttpHeaders {
    const token = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token;
    return new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Accept': 'application/pdf'  // Añadimos esto para asegurarnos que aceptamos PDF
    });
}

  // Obtener una prueba por ID
  getPruebaById(id: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.apiUrl}/${id}`, { headers });
  }

  // Crear una nueva prueba (método antiguo)
  createPruebaSingle(pruebaData: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/crear`, pruebaData, { headers });
  }

  createItems(items: any[]): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/crearItem`, items, { headers });
  }

  // Obtener especialidades (si este método debería estar en otro servicio, muévelo)
  getEspecialidades(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>('http://localhost:9000/api/especialidades', { headers });
  }

  createPruebaWithItems(data: {prueba: any, items: any[]}): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/crearPruebaConItems`, data, { headers })
      .pipe(
        catchError(error => {
          console.error('Error al crear prueba con items:', error);
          return throwError(() => error);
        })
      );
  }

  // 1. Primero crear la prueba
  createPrueba(prueba: any): Observable<any> {
    const headers = new HttpHeaders({
        'Authorization': `Bearer ${JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}').token}`,
        'Content-Type': 'application/json'
    });

    console.log('Headers enviados:', headers); // Debug
    console.log('Token enviado:', headers.get('Authorization')); // Debug
    console.log('Datos de la prueba:', prueba); // Debug

    return this.http.post(`${this.apiUrl}/crearPrueba`, prueba, { headers }).pipe(
        catchError(error => {
            console.error('Error en createPrueba:', error);
            return throwError(() => error);
        })
    );
}

  // 2. Luego crear los items para esa prueba
  createItemsForPrueba(pruebaId: number, items: any[]): Observable<any> {
    const headers = this.getAuthHeaders();
    
    const formattedItems = items.map(item => ({
        descripcion: item.descripcion,
        peso: item.peso,
        grados_consecucion: item.grados_consecucion,
        prueba_id_prueba: pruebaId // Asegúrate de que el nombre coincida exactamente
    }));

    console.log('Items formateados a enviar:', formattedItems);
    return this.http.post(`${this.apiUrl}/crearItems`, formattedItems, { headers });
  }

  getPruebasByEspecialidad(): Observable<any[]> {
    const especialidadId = this.authService.getEspecialidadFromToken();
    if (!especialidadId) {
      console.error('No se encontró ID de especialidad en el token');
      return throwError(() => new Error('No se encontró ID de especialidad'));
    }
    
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/buscarPruebasPorEspecialidad/${especialidadId}`, { headers })
      .pipe(
        tap(pruebas => console.log('Pruebas obtenidas:', pruebas)),
        catchError(error => {
          console.error('Error al obtener pruebas:', error);
          return throwError(() => error);
        })
      );
  }

  descargarPlantillaEvaluacion(pruebaId: number): Observable<Blob> {
    const headers = this.getAuthHeaders();
    console.log('Token siendo enviado:', headers.get('Authorization')); // Para debug
    
    return this.http.get(`${this.apiUrl}/plantillaevaluacion/${pruebaId}`, {
        responseType: 'blob',
        headers: headers,
        observe: 'response'  // Esto nos permitirá ver la respuesta completa
    }).pipe(
        map(response => response.body as Blob),
        catchError(error => {
            console.error('Headers enviados:', headers);
            console.error('Error completo:', error);
            return throwError(() => error);
        })
    );
}

}