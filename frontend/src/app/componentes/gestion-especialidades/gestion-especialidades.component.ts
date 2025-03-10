import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';
import { ToastrService } from 'ngx-toastr';

interface Especialidad {
  idEspecialidad: number;
  nombre: string;
  codigo: string;
}

@Component({
  selector: 'app-gestion-especialidades',
  templateUrl: './gestion-especialidades.component.html',
  styleUrls: ['./gestion-especialidades.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    RouterModule // Aseguramos que RouterModule está importado
  ]
})
export class GestionarEspecialidadesComponent implements OnInit {
  especialidades: Especialidad[] = [];
  loading: boolean = true;
  error: string | null = null;

  constructor(
    private especialidadService: EspecialidadService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.cargarEspecialidades();
  }

  cargarEspecialidades(): void {
    this.loading = true;
    this.especialidadService.getEspecialidades()
      .subscribe({
        next: (data) => {
          this.especialidades = data;
          this.loading = false;
          console.log('Especialidades cargadas:', this.especialidades); // Log para debug
        },
        error: (error) => {
          console.error('Error al cargar especialidades:', error);
          this.error = 'Error al cargar las especialidades';
          this.loading = false;
          if (error.status === 403) {
            this.toastr.error('No tienes permisos para ver las especialidades');
            this.router.navigate(['/login']);
          } else {
            this.toastr.error(this.error);
          }
        }
      });
  }

  verEspecialidad(id: number): void {
    console.log('Intentando ver especialidad con ID:', id); // Log para debug
    try {
      this.router.navigate(['admin/ver-especialidad', id]).then(
        (success) => console.log('Navegación exitosa:', success),
        (error) => console.error('Error en la navegación:', error)
      );
    } catch (error) {
      console.error('Error al intentar navegar:', error);
    }
  }

  editarEspecialidad(id: number): void {
    this.router.navigate(['admin/editar-especialidad', id]);
  }

  borrarEspecialidad(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar esta especialidad?')) {
      this.especialidadService.borrarEspecialidad(id)
        .subscribe({
          next: () => {
            this.toastr.success('Especialidad eliminada con éxito');
            this.cargarEspecialidades();
          },
          error: (error) => {
            console.error('Error al eliminar especialidad:', error);
            if (error.status === 403) {
              this.toastr.error('No tienes permisos para eliminar especialidades');
              this.router.navigate(['/login']);
            } else if (error.status === 409) {
              this.toastr.error('No se puede eliminar la especialidad porque está siendo utilizada');
            } else {
              this.toastr.error('Error al eliminar la especialidad');
            }
          }
        });
    }
  }
}