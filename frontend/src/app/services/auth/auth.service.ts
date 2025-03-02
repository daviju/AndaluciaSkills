import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, Subject, tap, throwError, BehaviorSubject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

/**
 * Interfaz que define la estructura de respuesta de autenticación desde el servidor
 * Contiene la información del usuario autenticado y su token JWT
 */
interface AuthResponse {
  token: string;          // Token JWT para autenticación
  username: string;       // Nombre de usuario
  role: string;           // Rol del usuario (ROLE_ADMIN, ROLE_EXPERTO)
  especialidadId: number; // ID de la especialidad asociada al usuario
  nombre: string;         // Nombre del usuario
  apellidos: string;      // Apellidos del usuario
  idUser: number;         // ID único del usuario
}

/**
 * Servicio de autenticación responsable de gestionar:
 * - Login y logout de usuarios
 * - Almacenamiento y recuperación del token JWT
 * - Información del usuario autenticado
 * - Estado de autenticación (logueado/no logueado)
 */
@Injectable({
  providedIn: 'root' // Hace que el servicio esté disponible en toda la aplicación
})
export class AuthService {
  private apiUrl = '/api/auth';                           // URL base para endpoints de autenticación
  private token: string | null = null;                    // Token JWT almacenado en memoria
  private rol: string | null = null;                      // Rol del usuario actual
  private _estaLogueado = false;                         // Estado de autenticación
  private usuario: any = null;                           // Datos del usuario autenticado
  private authStateChanged = new BehaviorSubject<boolean>(false); // Observable para cambios en el estado de autenticación
  private especialidadId: number | null = null;          // ID de la especialidad asociada

  /**
   * Constructor del servicio
   * Inicializa recuperando los datos de sesión desde localStorage si existen
   * @param http Cliente HTTP para realizar peticiones al backend
   */
  constructor(private http: HttpClient) {
    this.recuperarDatosGuardados();
  }

  /**
   * Getter para verificar si el usuario está autenticado
   * @returns boolean - true si hay un usuario logueado, false en caso contrario
   */
  get estaLogueado(): boolean {
    return this._estaLogueado;
  }

  /**
   * Obtiene el nombre completo del usuario actual
   * @returns string - Nombre y apellidos concatenados o cadena vacía si no hay usuario
   */
  getNombreCompleto(): string {
    if (this.usuario) {
      return `${this.usuario.nombre} ${this.usuario.apellidos}`.trim();
    }
    return '';
  }

  /**
   * Obtiene el rol del usuario actual
   * @returns string - Rol del usuario o cadena vacía si no hay rol
   */
  getRol(): string {
    return this.rol || '';
  }

  /**
   * Método privado que recupera los datos de autenticación guardados en localStorage
   * Se ejecuta durante la inicialización del servicio
   */
  private recuperarDatosGuardados() {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      this.token = datos.token;
      this.rol = datos.rol;
      this._estaLogueado = datos.estaLogueado;
      this.usuario = datos.usuario;
      this.especialidadId = datos.especialidadId;
      this.authStateChanged.next(true); // Notifica a los suscriptores que hay un usuario autenticado
      console.log('Datos recuperados:', {
        token: !!this.token,
        rol: this.rol,
        estaLogueado: this._estaLogueado,
        usuario: this.usuario
      });
    }
  }

  /**
   * Guarda la sesión del usuario en localStorage y actualiza el estado del servicio
   * @param response - Respuesta de autenticación recibida del servidor
   */
  public guardarSesion(response: AuthResponse) {
    console.log('Respuesta del servidor:', response);
    const datos = {
      token: response.token,
      rol: response.role,
      estaLogueado: true,
      usuario: {
        username: response.username,
        role: response.role,
        nombre: response.nombre,
        apellidos: response.apellidos,
        idUser: response.idUser  
      },
      especialidadId: response.especialidadId
    };
    
    // Actualiza el estado interno del servicio
    this.token = response.token;
    this.rol = response.role;
    this._estaLogueado = true;
    this.usuario = datos.usuario;
    this.especialidadId = response.especialidadId;
    
    console.log('Guardando datos en localStorage:', datos);
    localStorage.setItem('DATOS_AUTH', JSON.stringify(datos));
    this.authStateChanged.next(true); // Notifica el cambio de estado de autenticación
  }

  /**
   * Realiza el proceso de inicio de sesión enviando credenciales al servidor
   * @param nombreUsuario - Nombre de usuario
   * @param contraseña - Contraseña del usuario
   * @returns Observable<any> - Respuesta del servidor
   */
  iniciarSesion(nombreUsuario: string, contraseña: string): Observable<any> {
    const credentials = {
      username: nombreUsuario.trim(),
      password: contraseña.trim()
    };

    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          if (response && response.token) {
            // Si la respuesta contiene un token, guarda los datos de sesión
            this.token = response.token;
            this.rol = response.role;
            this._estaLogueado = true;
            this.usuario = {
              username: response.username,
              role: response.role,
              nombre: response.nombre,       
              apellidos: response.apellidos  
            };
            this.guardarSesion(response);
            this.authStateChanged.next(true);
          }
        }),
        catchError(error => {
          console.error('Error en inicio de sesión:', error);
          return throwError(() => new Error('Error en el inicio de sesión'));
        })
      );
  }

  /**
   * Cierra la sesión del usuario actual
   * Limpia todos los datos de autenticación en memoria y localStorage
   */
  cerrarSesion() {
    this.token = "";
    this.rol = "";
    this._estaLogueado = false;
    this.usuario = {};
    this.especialidadId = null;
    
    localStorage.removeItem("DATOS_AUTH");
    this.authStateChanged.next(false); // Notifica que ya no hay usuario autenticado
  }

  /**
   * Crea las cabeceras HTTP con el token de autenticación
   * @returns HttpHeaders - Cabeceras para solicitudes autenticadas
   */
  getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
  }

  /**
   * Verifica si el usuario tiene rol de administrador
   * @returns boolean - true si el usuario es administrador, false en caso contrario
   */
  esAdmin(): boolean {
    return this.rol === 'ROLE_ADMIN';
  }

  /**
   * Verifica si el usuario tiene rol de experto
   * @returns boolean - true si el usuario es experto, false en caso contrario
   */
  esExperto(): boolean {
    return this.rol === 'ROLE_EXPERTO';
  }

  /**
   * Obtiene el token JWT desde localStorage
   * @returns string - Token JWT o cadena vacía si no existe
   */
  getToken(): string {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      return datos.token || '';
    }
    return '';
  }

  /**
   * Obtiene el ID de especialidad del usuario desde localStorage
   * @returns number | null - ID de especialidad o null si no existe
   */
  getEspecialidadFromToken(): number | null {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      if (datos.especialidadId) {
        console.log('Especialidad ID encontrada:', datos.especialidadId);
        return datos.especialidadId;
      }
    }
    console.log('No se encontró especialidad ID en localStorage');
    return null;
  }

  /**
   * Obtiene el ID del usuario desde localStorage
   * @returns number | null - ID del usuario o null si no existe
   */
  getUserId(): number | null {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      return datos.usuario?.idUser || null;
    }
    return null;
  }

  /**
   * Obtiene el nombre del usuario desde localStorage
   * @returns string | null - Nombre del usuario o null si no existe
   */
  getNombreFromToken(): string | null {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      return datos.usuario?.nombre || null;
    }
    return null;
  }

  /**
   * Obtiene los apellidos del usuario desde localStorage
   * @returns string | null - Apellidos del usuario o null si no existe
   */
  getApellidosFromToken(): string | null {
    const datosAuth = localStorage.getItem('DATOS_AUTH');
    if (datosAuth) {
      const datos = JSON.parse(datosAuth);
      return datos.usuario?.apellidos || null;
    }
    return null;
  }

  /**
   * Obtiene el nombre completo del usuario desde localStorage
   * @returns {nombre: string, apellidos: string} | null - Objeto con nombre y apellidos o null si no existe
   */
  getUserFullName(): {nombre: string, apellidos: string} | null {
    const userData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
    if (userData && userData.nombre && userData.apellidos) {
      return {
        nombre: userData.nombre,
        apellidos: userData.apellidos
      };
    }
    return null;
  }
}