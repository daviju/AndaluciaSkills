import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadService } from '../../services/especialidad/especialidad.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-especialidades',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-especialidades.component.html',
  styleUrls: ['./ver-especialidades.component.scss']
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
        console.log('Datos de la especialidad:', data);
      },
      error => {
        console.error('Error:', error);
        this.volver();
      }
    );
  }

  volver() {
    this.router.navigate(['admin/especialidades']);
  }
}