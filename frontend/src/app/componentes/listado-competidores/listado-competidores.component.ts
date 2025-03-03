import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompetidorService } from '../../services/competidor/competidor.service';

@Component({
  selector: 'app-lista-competidores',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listado-competidores.component.html',
  styleUrls: ['./listado-competidores.component.css']
})
export class ListaCompetidoresComponent implements OnInit {
  competidores: any[] = [];

  constructor(private competidorService: CompetidorService) { }

  ngOnInit(): void {
    this.obtenerCompetidores();
  }

  obtenerCompetidores(): void {
    this.competidorService.getCompetidores().subscribe(
      data => this.competidores = data,
      error => console.error('Error al obtener competidores:', error)
    );
  }
}