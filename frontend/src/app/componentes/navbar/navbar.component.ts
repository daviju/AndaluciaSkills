// Importaciones necesarias para el componente
import { Component } from '@angular/core';              // Importamos el decorador Component de Angular
import { Router, RouterLink } from '@angular/router';   // Importamos Router para la navegación y RouterLink para enlaces
import { CommonModule } from '@angular/common';         // Importamos CommonModule para directivas comunes de Angular
import { AuthService } from '../../services/auth/auth.service'; // Importamos el servicio de autenticación

/**
 * Componente de la barra de navegación
 * Este componente gestiona la barra de navegación superior de la aplicación
 * y maneja la información del usuario autenticado
 */
@Component({
  selector: 'app-navbar',            // Selector CSS para usar el componente en el HTML
  standalone: true,                  // Indica que es un componente independiente
  imports: [CommonModule, RouterLink], // Módulos necesarios para el funcionamiento
  templateUrl: './navbar.component.html', // Plantilla HTML asociada
  styleUrls: ['./navbar.component.scss']   // Estilos CSS asociados
})
export class NavbarComponent {
  /**
   * Constructor del componente
   * @param authService Servicio de autenticación inyectado para manejar el estado de la sesión
   * @param router Servicio de enrutamiento para la navegación entre páginas
   */
  constructor(public authService: AuthService, private router: Router) { }

  /**
   * Obtiene el nombre completo del usuario desde el almacenamiento local
   * @returns Devuelve el nombre completo del usuario o 'Usuario' si no hay datos
   */
  getNombreCompleto(): string {
    // Obtiene los datos de autenticación del almacenamiento local
    const datos = localStorage.getItem('DATOS_AUTH');
    if (datos) {
      // Convierte los datos JSON a objeto
      const datosJson = JSON.parse(datos);
      if (datosJson.usuario) {
        // Combina nombre y apellidos del usuario
        return `${datosJson.usuario.nombre} ${datosJson.usuario.apellidos}`;
      }
    }
    // Valor por defecto si no hay datos
    return 'Usuario';
  }

  /**
   * Obtiene el rol del usuario desde el almacenamiento local
   * @returns Devuelve 'Administrador', 'Experto' o cadena vacía según el rol
   */
  getRol(): string {
    // Obtiene los datos de autenticación del almacenamiento local
    const datos = localStorage.getItem('DATOS_AUTH');
    if (datos) {
      // Convierte los datos JSON a objeto
      const datosJson = JSON.parse(datos);
      if (datosJson.usuario && datosJson.usuario.role) {
        // Convierte el rol técnico a un nombre más amigable
        return datosJson.usuario.role === 'ROLE_ADMIN' ? 'Administrador' : 'Experto';
      }
    }
    // Retorna cadena vacía si no hay rol
    return '';
  }

  /**
   * Método para cerrar la sesión del usuario
   * Llama al servicio de autenticación y redirecciona al login
   */
  cerrarSesion() {
    // Llama al método del servicio de autenticación para cerrar sesión
    this.authService.cerrarSesion();
    // Redirecciona al usuario a la página de login
    this.router.navigate(['/login']);
  }
}