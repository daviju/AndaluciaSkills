import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ParticipantesService } from '../../services/participantes/participantes.service';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';
import { forkJoin } from 'rxjs';

@Component({
    selector: 'app-crear-participantes',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './crear-participantes.component.html',
    styleUrls: ['./crear-participantes.component.scss']
})
export class CrearParticipantesComponent implements OnInit {
    participante: any = {
        nombre: '',
        apellidos: '',
        centro: '',
        especialidad_id_especialidad: null
    };
    especialidades: any[] = [];
    isEditing = false;
    private initialParticipante: any;

    constructor(
        private participantesService: ParticipantesService,
        private especialidadService: EspecialidadService,
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        const id = this.route.snapshot.params['id'];
        
        if (id) {
            this.isEditing = true;
            // Usar forkJoin para cargar especialidades y participante simultáneamente
            forkJoin({
                especialidades: this.especialidadService.getEspecialidades(),
                participante: this.participantesService.getParticipante(id)
            }).subscribe({
                next: (result) => {
                    console.log('Datos cargados:', result);
                    
                    // Primero guardamos las especialidades
                    this.especialidades = result.especialidades;
                    
                    // Luego configuramos el participante con su especialidad
                    this.participante = {
                        ...result.participante,
                        especialidad_id_especialidad: result.participante.especialidad?.idEspecialidad
                    };
                    
                    // Verificar que la especialidad existe en la lista
                    const especialidadExiste = this.especialidades.some(
                        esp => esp.idEspecialidad === this.participante.especialidad_id_especialidad
                    );
                    
                    if (!especialidadExiste) {
                        console.error('La especialidad del participante no se encuentra en la lista');
                    }
                    
                    // Guardar estado inicial
                    this.initialParticipante = {...this.participante};
                    
                    console.log('Participante configurado:', this.participante);
                    console.log('Especialidad seleccionada:', this.participante.especialidad_id_especialidad);
                },
                error: (error) => {
                    console.error('Error al cargar datos:', error);
                    this.router.navigate(['/experto/participantes']);
                }
            });
        } else {
            // Si es creación nueva, solo cargamos las especialidades
            this.cargarEspecialidades();
            this.initialParticipante = {...this.participante};
        }
    }

    cargarEspecialidades() {
        this.especialidadService.getEspecialidades().subscribe({
            next: (data) => {
                this.especialidades = data;
                console.log('Especialidades cargadas:', this.especialidades);
            },
            error: (error) => console.error('Error al cargar especialidades:', error)
        });
    }

    // El resto de métodos se mantienen igual...

    cancelar() {
        if (this.hasUnsavedChanges()) {
            if (confirm('Hay cambios sin guardar. ¿Estás seguro de que quieres salir?')) {
                this.router.navigate(['/experto/participantes']).then(() => {
                    console.log('Navegación completada');
                }).catch(error => {
                    console.error('Error en la navegación:', error);
                });
            }
        } else {
            this.router.navigate(['/experto/participantes']).then(() => {
                console.log('Navegación completada');
            }).catch(error => {
                console.error('Error en la navegación:', error);
            });
        }
    }

    hasUnsavedChanges(): boolean {
        return JSON.stringify(this.participante) !== JSON.stringify(this.initialParticipante);
    }

    onSubmit() {
        if (!this.validarFormulario()) {
            console.error('Formulario inválido');
            return;
        }

        if (this.isEditing) {
            const id = this.route.snapshot.params['id'];
            console.log('ID de la ruta:', id);
            console.log('Datos a actualizar:', this.participante);

            this.participantesService.editarParticipante(id, this.participante).subscribe({
                next: (response) => {
                    console.log('Participante actualizado:', response);
                    this.router.navigate(['experto/participantes']);
                },
                error: (error) => {
                    console.error('Error al actualizar:', error);
                }
            });
        } else {
            this.participantesService.crearParticipante(this.participante).subscribe({
                next: (response) => {
                    console.log('Participante creado:', response);
                    this.router.navigate(['experto/participantes']);
                },
                error: (error) => {
                    console.error('Error al crear:', error);
                }
            });
        }
    }

    private validarFormulario(): boolean {
        if (!this.participante.nombre || 
            !this.participante.apellidos || 
            !this.participante.centro || 
            !this.participante.especialidad_id_especialidad) {
            console.error('Campos requeridos no completados');
            return false;
        }
        return true;
    }
}