import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login', // Define el nombre del selector HTML para este componente
  standalone: true, // Indica que este es un componente independiente (standalone)
  imports: [CommonModule, FormsModule], // Módulos importados necesarios para este componente
  templateUrl: './login.component.html', // Ruta al archivo HTML que actúa como plantilla para este componente
  styleUrls: ['./login.component.css'] // Ruta al archivo CSS que contiene los estilos para este componente
})
export class LoginComponent {
  // Definición de variables de clase
  username: string = ''; // Variable para almacenar el nombre de usuario
  password: string = ''; // Variable para almacenar la contraseña
  error: string = ''; // Variable para almacenar mensajes de error
  loading: boolean = false; // Variable para indicar si la solicitud de login está en curso

  constructor(
    private authService: AuthService, // Inyección del servicio de autenticación
    private router: Router // Inyección del servicio de enrutamiento para la navegación
  ) { }

  // Método que se llama al enviar el formulario de login
  onSubmit() {
    // Validaciones básicas para asegurarse de que los campos no estén vacíos
    if (!this.username || !this.password) {
        this.error = 'Por favor, complete todos los campos'; // Mensaje de error si algún campo está vacío
        return; // Salir del método si la validación falla
    }

    // Limpiar los datos antes de enviarlos
    const username = this.username.trim(); // Eliminar espacios en blanco del nombre de usuario
    const password = this.password.trim(); // Eliminar espacios en blanco de la contraseña

    console.log('Intentando login con:', {
        username: username,
        password: password
    });

    this.loading = true; // Indicar que la solicitud de login está en curso
    this.error = ''; // Limpiar cualquier mensaje de error previo

    // Llamar al método de inicio de sesión del servicio de autenticación
    this.authService.iniciarSesion(username, password).subscribe({
        next: (response) => {
            console.log('Login exitoso:', response);
            this.loading = false; // Indicar que la solicitud de login ha finalizado
            this.router.navigate(['/']); // Navegar a la página de inicio en caso de éxito
        },
        error: (error) => {
            console.error('Error en login:', error);
            this.loading = false; // Indicar que la solicitud de login ha finalizado
            // Manejo de errores de la solicitud de login
            if (error.status === 401) {
                this.error = 'Usuario o contraseña incorrectos'; // Mensaje de error para credenciales incorrectas
            } else {
                this.error = 'Error al conectar con el servidor'; // Mensaje de error para otros problemas de conexión
            }
        }
    });
  }
}