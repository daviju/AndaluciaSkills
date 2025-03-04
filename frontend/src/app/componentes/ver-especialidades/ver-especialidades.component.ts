import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';

@Component({
  selector: 'app-ver-especialidad',
  templateUrl: './ver-especialidades.component.html',
  styleUrls: ['./ver-especialidades.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class VerEspecialidadComponent implements OnInit {
  especialidad: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private especialidadService: EspecialidadService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.especialidadService.getEspecialidad(id).subscribe(
      data => {
        this.especialidad = data;
        console.log('Datos de la especialidad cargados:', data); // Log para debug
      },
      error => {
        console.error('Error al cargar la especialidad:', error);
        this.volver();
      }
    );
  }

  volver() {
    this.router.navigate(['admin/especialidades']);
  }
}