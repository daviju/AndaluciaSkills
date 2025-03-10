import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ToastrModule } from 'ngx-toastr';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

interface Especialidad {
  nombre: string;
  codigo: string;
}

@Component({
  selector: 'app-crear-especialidad',
  templateUrl: './crear-especialidades.component.html',
  styleUrls: ['./crear-especialidades.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  providers: [FormBuilder]
})
export class CrearEspecialidadComponent implements OnInit {
  especialidadForm!: FormGroup;
  isSubmitting: boolean = false;
  isEditing: boolean = false;

  constructor(
    private fb: FormBuilder,
    private especialidadService: EspecialidadService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) {
    this.initForm();
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditing = true;
      this.cargarEspecialidad(id);
    }
  }

  private cargarEspecialidad(id: number): void {
    this.especialidadService.getEspecialidad(id).subscribe(
      (data) => {
        this.especialidadForm.patchValue({
          nombre: data.nombre,
          codigo: data.codigo
        });
      },
      (error) => {
        console.error('Error al cargar la especialidad:', error);
        this.toastr.error('Error al cargar la especialidad');
        this.router.navigate(['/admin/especialidades']);
      }
    );
  }

  private initForm(): void {
    this.especialidadForm = this.fb.group({
      nombre: ['', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(50)
      ]],
      codigo: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(10),
        Validators.pattern('^[A-Z0-9]+$') // Solo letras mayúsculas y números
      ]]
    });
  }

  // Getters para facilitar el acceso a los controles del formulario
  get nombreControl() {
    return this.especialidadForm.get('nombre');
  }

  get codigoControl() {
    return this.especialidadForm.get('codigo');
  }

  // Validadores personalizados para los mensajes de error
  getErrorMessage(controlName: string): string {
    const control = this.especialidadForm.get(controlName);
    if (control?.errors) {
      if (control.errors['required']) {
        return `El ${controlName} es requerido`;
      }
      if (control.errors['minlength']) {
        return `El ${controlName} debe tener al menos ${control.errors['minlength'].requiredLength} caracteres`;
      }
      if (control.errors['maxlength']) {
        return `El ${controlName} no puede tener más de ${control.errors['maxlength'].requiredLength} caracteres`;
      }
      if (control.errors['pattern']) {
        return 'El código solo puede contener letras mayúsculas y números';
      }
    }
    return '';
  }

  onSubmit(): void {
    if (this.especialidadForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      const especialidad: Especialidad = this.especialidadForm.value;
      
      const operation = this.isEditing 
        ? this.especialidadService.editarEspecialidad(+this.route.snapshot.params['id'], especialidad)
        : this.especialidadService.crearEspecialidad(especialidad);

      operation.pipe(
        tap(() => {
          const message = this.isEditing 
            ? 'Especialidad actualizada con éxito'
            : 'Especialidad creada con éxito';
          this.toastr.success(message, 'Éxito');
          this.router.navigate(['/admin/especialidades']);
        }),
        catchError(error => {
          console.error('Error:', error);
          
          let errorMessage = this.isEditing 
            ? 'Error al actualizar la especialidad'
            : 'Error al crear la especialidad';

          if (error.error?.message) {
            errorMessage = error.error.message;
          } else if (error.status === 403) {
            errorMessage = 'No tienes permisos';
            if (error.error?.message?.includes('token')) {
              this.router.navigate(['/login']);
            }
          } else if (error.status === 409) {
            errorMessage = 'Ya existe una especialidad con ese código';
          }
          
          this.toastr.error(errorMessage, 'Error');
          this.isSubmitting = false;
          return of(null);
        })
      ).subscribe();
    } else {
      this.markFormGroupTouched(this.especialidadForm);
    }
  }

  // Función para marcar todos los campos como tocados
  private markFormGroupTouched(formGroup: FormGroup): void {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  // Método para limpiar el formulario
  resetForm(): void {
    this.especialidadForm.reset();
    Object.keys(this.especialidadForm.controls).forEach(key => {
      const control = this.especialidadForm.get(key);
      control?.setErrors(null);
    });
  }

  // Método para cancelar la creación
  cancelar(): void {
    if (this.especialidadForm.dirty) {
      if (confirm('¿Estás seguro de que quieres cancelar? Los cambios no guardados se perderán.')) {
        this.router.navigate(['/admin/especialidades']);
      }
    } else {
      this.router.navigate(['/admin/especialidades']);
    }
  }
}