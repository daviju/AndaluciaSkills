import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { CompetidorService } from '../../services/competidor/competidor.service';

@Component({
  selector: 'app-lista-competidores',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule
  ],
  templateUrl: './listado-competidores.component.html',
  styleUrls: ['./listado-competidores.component.css']
})
export class ListaCompetidoresComponent implements OnInit {
  competidores: any[] = [];
  error: string = '';

  constructor(private competidorService: CompetidorService) { }

  ngOnInit(): void {
    this.obtenerCompetidores();
  }

  obtenerCompetidores(): void {
    this.competidorService.getCompetidores().subscribe({
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