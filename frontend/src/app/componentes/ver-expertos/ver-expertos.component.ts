import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ExpertoService } from '../../services/experto/experto.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ver-experto',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-expertos.component.html',
  styleUrls: ['./ver-expertos.component.scss']
})
export class VerExpertoComponent implements OnInit {
  experto: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private expertoService: ExpertoService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    this.expertoService.getExperto(id).subscribe(
      data => {
        this.experto = data;
        console.log('Datos del experto:', data); // Para depuración
      },
      error => {
        console.error('Error:', error);
        this.volver();
      }
    );
  }

  volver() {
    this.router.navigate(['admin/expertos']);
  }
}