import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParticipantesService } from '../../services/participantes/participantes.service';  // Cambiado aquí

@Component({
  selector: 'app-lista-competidores',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './listado-competidores.component.html',
  styleUrls: ['./listado-competidores.component.scss']
})
export class ListaCompetidoresComponent implements OnInit {
  competidores: any[] = [];
  error: string = '';

  constructor(private participantesService: ParticipantesService) { }  // Cambiado aquí

  ngOnInit(): void {
    this.obtenerCompetidores();
  }

  obtenerCompetidores(): void {
    this.participantesService.getParticipantes().subscribe({  // Cambiado aquí
      next: (data) => {
        this.competidores = data;
        console.log('Competidores cargados:', data);
      },
      error: (err) => {
        this.error = 'Error al cargar los competidores';
        console.error('Error al obtener competidores:', err);
      }
    });
  }
}