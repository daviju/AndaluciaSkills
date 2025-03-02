import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginForm: FormGroup;
  error: string = '';
  isLoading: boolean = false;  // Cambiado de loading a isLoading

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      this.error = 'Por favor, complete todos los campos';
      return;
    }

    const { username, password } = this.loginForm.value;

    console.log('Intentando login con:', {
      username: username,
      password: password
    });

    this.isLoading = true;  // Cambiado de loading a isLoading
    this.error = '';

    this.authService.iniciarSesion(username, password).subscribe({
      next: (response) => {
        console.log('Login exitoso:', response);
        this.isLoading = false;  // Cambiado de loading a isLoading
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        console.error('Error en login:', error);
        this.isLoading = false;  // Cambiado de loading a isLoading
        if (error.status === 401) {
          this.error = 'Usuario o contraseña incorrectos';
        } else {
          this.error = 'Error al conectar con el servidor';
        }
      }
    });
  }
}