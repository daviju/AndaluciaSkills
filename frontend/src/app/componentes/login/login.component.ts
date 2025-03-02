import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth/auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  isLoading: boolean = false;
  hidePassword: boolean = true;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private toastr: ToastrService
  ) {
    // Inicializar el formulario con validaciones
    this.loginForm = this.fb.group({
      username: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.pattern(/^[a-zA-Z0-9._-]+$/)
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(6),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$/)
      ]],
      rememberMe: [false]
    });
  }

  ngOnInit(): void {
    // Comprobar si hay credenciales guardadas
    const savedUsername = localStorage.getItem('rememberedUser');
    if (savedUsername) {
      this.loginForm.patchValue({
        username: savedUsername,
        rememberMe: true
      });
    }
  }

  // Getters para acceder fácilmente a los controles del formulario
  get usernameControl() {
    return this.loginForm.get('username');
  }

  get passwordControl() {
    return this.loginForm.get('password');
  }

  // Método para mostrar/ocultar la contraseña
  togglePasswordVisibility(): void {
    this.hidePassword = !this.hidePassword;
  }

  // Método para manejar el envío del formulario
  async onSubmit(): Promise<void> {
    if (this.loginForm.valid) {
      try {
        this.isLoading = true;
        const { username, password, rememberMe } = this.loginForm.value;

        // Guardar username si rememberMe está activado
        if (rememberMe) {
          localStorage.setItem('rememberedUser', username);
        } else {
          localStorage.removeItem('rememberedUser');
        }

        // Llamada al servicio de autenticación
        const response = await this.authService.login(username, password).toPromise();
        
        if (response && response.token) {
          // Guardar el token
          this.authService.setToken(response.token);
          
          // Mostrar mensaje de éxito
          this.toastr.success('¡Bienvenido!', 'Login exitoso');
          
          // Redirigir al dashboard o página principal
          this.router.navigate(['/dashboard']);
        }
      } catch (error: any) {
        // Manejar errores específicos
        if (error.status === 401) {
          this.toastr.error('Usuario o contraseña incorrectos', 'Error de autenticación');
        } else if (error.status === 403) {
          this.toastr.error('No tienes permiso para acceder', 'Error de autorización');
        } else {
          this.toastr.error('Ha ocurrido un error inesperado', 'Error');
        }
        console.error('Error en el login:', error);
      } finally {
        this.isLoading = false;
      }
    } else {
      // Marcar todos los campos como tocados para mostrar errores
      Object.keys(this.loginForm.controls).forEach(key => {
        const control = this.loginForm.get(key);
        if (control) {
          control.markAsTouched();
        }
      });
    }
  }

  // Método para recuperar contraseña
  onForgotPassword(): void {
    this.router.navigate(['/recuperar-password']);
  }

  // Método para ir al registro
  onRegister(): void {
    this.router.navigate(['/registro']);
  }

  // Método para obtener mensajes de error específicos
  getErrorMessage(controlName: string): string {
    const control = this.loginForm.get(controlName);
    if (control?.hasError('required')) {
      return `El ${controlName === 'username' ? 'usuario' : 'contraseña'} es requerido`;
    }
    if (control?.hasError('minlength')) {
      return `El ${controlName === 'username' ? 'usuario' : 'contraseña'} debe tener al menos ${
        controlName === 'username' ? '3' : '6'
      } caracteres`;
    }
    if (control?.hasError('pattern')) {
      return controlName === 'username' 
        ? 'El usuario solo puede contener letras, números, puntos, guiones y guiones bajos'
        : 'La contraseña debe contener al menos una mayúscula, una minúscula y un número';
    }
    return '';
  }
}