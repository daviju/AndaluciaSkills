import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PruebasService } from '../../services/pruebas/pruebas.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listar-pruebas-puntuacion',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './listado-pruebas-por-puntuacion.component.html',
  styleUrls: ['./listado-pruebas-por-puntuacion.component.scss']
})
export class ListarPruebaPuntuacionComponent implements OnInit {
  pruebas: any[] = [];
  participanteId: number | null = null;
  loading = false;
  error: string | null = null;
  especialidadId: number | null = null;

  constructor(
    private pruebasService: PruebasService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    // Obtener el ID del participante de la URL
    this.route.params.subscribe(params => {
      this.participanteId = +params['participanteId']; // El + convierte el string a número
      this.cargarPruebasNoEvaluadas();
    });
  }

  cargarPruebasNoEvaluadas() {
    this.loading = true;
    this.error = null;

    if (!this.participanteId) {
      this.error = 'No se ha proporcionado un ID de participante válido';
      this.loading = false;
      return;
    }

    // Llamamos al método que trae solo las pruebas no evaluadas
    this.pruebasService.getPruebasNoEvaluadasByParticipante(this.participanteId).subscribe({
      next: (data) => {
        console.log('Pruebas no evaluadas cargadas:', data);
        this.pruebas = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar las pruebas no evaluadas:', error);
        this.error = 'Error al cargar las pruebas no evaluadas para este participante';
        this.loading = false;
      }
    });
  }

  seleccionarPrueba(pruebaId: number) {
    this.router.navigate(['/experto/puntuar-items', this.participanteId, pruebaId]);
  }
}