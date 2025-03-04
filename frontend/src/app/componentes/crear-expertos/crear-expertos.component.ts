import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpertoService } from '../../services/experto/experto.service';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crear-experto',
  templateUrl: '../../componentes/crear-expertos/crear-expertos.component.html',
  styleUrls: ['./crear-expertos.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule]
})
export class CrearExpertoComponent implements OnInit {
  experto: any = {
    username: '',
    password: '',
    nombre: '',
    apellidos: '',
    dni: '',
    role: 'ROLE_EXPERTO',
    especialidad_id_especialidad: null
  };
  especialidades: any[] = [];
  isEditing = false;
  dniError: string = '';
  isSubmitting = false;

  constructor(
    private expertoService: ExpertoService,
    private especialidadService: EspecialidadService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.cargarEspecialidades();
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditing = true;
      this.expertoService.getExperto(id).subscribe({
        next: (data) => {
          this.experto = {
            ...data,
            especialidad_id_especialidad: data.especialidad?.idEspecialidad
          };
        },
        error: (error) => {
          console.error('Error al cargar experto:', error);
          alert('Error al cargar los datos del experto');
        }
      });
    }
  }

  cargarEspecialidades() {
    this.especialidadService.getEspecialidades().subscribe({
      next: (data) => {
        this.especialidades = data;
      },
      error: (error) => {
        console.error('Error al cargar especialidades:', error);
        alert('Error al cargar las especialidades');
      }
    });
  }

  onSubmit() {
    if (!this.validarFormulario()) {
      console.error('Formulario inválido');
      return;
    }

    if (this.isSubmitting) {
      return;
    }

    this.isSubmitting = true;

    const expertoData = {
      idUser: this.experto.idUser,
      username: this.experto.username.trim(),
      nombre: this.experto.nombre.trim(),
      apellidos: this.experto.apellidos.trim(),
      dni: this.experto.dni.trim(),
      role: 'ROLE_EXPERTO',
      especialidad: {
        idEspecialidad: Number(this.experto.especialidad_id_especialidad)
      }
    };

    if (this.experto.password && this.experto.password.trim() !== '') {
      expertoData['password'] = this.experto.password.trim();
    }

    console.log('Datos del experto antes de enviar:', expertoData);

    const operation = this.isEditing
      ? this.expertoService.editarExperto(this.experto.idUser, expertoData)
      : this.expertoService.crearExperto(expertoData);

    operation.subscribe({
      next: (response) => {
        console.log(`Experto ${this.isEditing ? 'editado' : 'creado'} correctamente:`, response);
        this.router.navigate(['/admin/expertos']);
      },
      error: (error) => {
        console.error('Error completo:', error);
        let errorMessage = `Error al ${this.isEditing ? 'editar' : 'crear'} el experto`;
        
        if (error.status === 403) {
          const authData = JSON.parse(localStorage.getItem('DATOS_AUTH') || '{}');
          console.log('Datos de autenticación:', authData);
          errorMessage = 'No tienes permisos para realizar esta acción';
        } else if (error.error?.message) {
          errorMessage = error.error.message;
        }
        
        alert(errorMessage);
        this.isSubmitting = false;
      },
      complete: () => {
        this.isSubmitting = false;
      }
    });
  }

  validarDNI(dni: string): { isValid: boolean, message: string } {
    const letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
    
    if (!dni) {
      return { isValid: false, message: 'El DNI es obligatorio' };
    }
    
    dni = dni.trim().toUpperCase();
    
    if (dni.length !== 9) {
      return { isValid: false, message: 'El DNI debe tener 9 caracteres (8 números y 1 letra)' };
    }

    const match = dni.match(/^(\d{8})([A-Z])$/);
    if (!match) {
      if (!/^\d{8}$/.test(dni.slice(0, 8))) {
        return { isValid: false, message: 'Los primeros 8 caracteres deben ser números' };
      }
      if (!/[A-Z]/.test(dni[8])) {
        return { isValid: false, message: 'El último carácter debe ser una letra' };
      }
      return { isValid: false, message: 'DNI no válido' };
    }

    const numero = parseInt(match[1]);
    const letraProporcionada = match[2];
    const letraCalculada = letrasValidas.charAt(numero % 23);
    
    if (letraCalculada !== letraProporcionada) {
      return { isValid: false, message: 'DNI no válido' };
    }

    return { isValid: true, message: '' };
  }

  onDniChange() {
    if (this.experto.dni) {
      const validacion = this.validarDNI(this.experto.dni);
      this.dniError = validacion.message;
    } else {
      this.dniError = 'El DNI es obligatorio';
    }
  }

  private validarFormulario(): boolean {
    if (!this.experto.username || 
        (!this.isEditing && !this.experto.password) || 
        !this.experto.nombre || 
        !this.experto.apellidos || 
        !this.experto.dni || 
        !this.experto.especialidad_id_especialidad) {
      console.error('Todos los campos son obligatorios');
      alert('Todos los campos son obligatorios');
      return false;
    }

    const validacionDNI = this.validarDNI(this.experto.dni);
    if (!validacionDNI.isValid) {
      this.dniError = validacionDNI.message;
      alert(validacionDNI.message);
      return false;
    }

    return true;
  }

  cancelar() {
    this.router.navigate(['/admin/expertos']);
  }
}