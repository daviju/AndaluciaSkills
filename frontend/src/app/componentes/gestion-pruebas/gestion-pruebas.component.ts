import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { PruebasService } from '../../services/pruebas/pruebas.service';
import { AuthService } from '../../services/auth/auth.service';
import { Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-gestionar-pruebas',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './gestion-pruebas.component.html',
  styleUrls: ['./gestion-pruebas.component.css']
})
export class GestionarPruebasComponent implements OnInit {
  pruebaForm: FormGroup;
  loading = false;
  error = '';
  pruebaCreada = false;
  pruebaActual: any = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private pruebasService: PruebasService,
    private router: Router
  ) {
    this.pruebaForm = this.fb.group({
      enunciado: ['', [Validators.required, Validators.minLength(10)]],
      puntuacionMaxima: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      items: this.fb.array([])
    });
  }

  ngOnInit() {
    if (this.items.length === 0) {
      this.agregarItem();
    }
  }

  get items() {
    return this.pruebaForm.get('items') as FormArray;
  }

  agregarItem() {
    const itemForm = this.fb.group({
      descripcion: ['', [Validators.required, Validators.minLength(5)]],
      peso: ['', [Validators.required, Validators.min(1), Validators.max(100)]],
      grados_consecucion: ['', [Validators.required, Validators.min(0), Validators.max(100)]] // Cambiar este nombre
    });
    this.items.push(itemForm);
  }

  eliminarItem(index: number) {
    if (this.items.length > 1) {
      this.items.removeAt(index);
    } else {
      this.error = 'Debe haber al menos un item';
    }
  }

  onSubmit() {
    if (this.formInvalid) {
        this.markFormGroupTouched(this.pruebaForm);
        return;
    }

    this.loading = true;
    this.error = '';

    const pruebaData = {
        enunciado: this.pruebaForm.get('enunciado')?.value,
        puntuacionMaxima: this.pruebaForm.get('puntuacionMaxima')?.value,
        especialidad_idEspecialidad: this.authService.getEspecialidadFromToken()
    };

    this.pruebasService.createPrueba(pruebaData).pipe(
        switchMap(pruebaCreada => {
            this.pruebaActual = pruebaCreada; // Guardamos la prueba creada
            
            // Mapear los items asegurando que los tipos sean correctos
            const items = this.items.value.map((item: any) => ({
                descripcion: item.descripcion,
                peso: Number(item.peso),
                grados_consecucion: Number(item.grados_consecucion),
                prueba_idPrueba: pruebaCreada.idPrueba
            }));

            return this.pruebasService.createItemsForPrueba(pruebaCreada.idPrueba, items);
        })
    ).subscribe({
        next: (response) => {
            this.loading = false;
            this.pruebaCreada = true;
        },
        error: (error) => {
            console.error('Error detallado:', error);
            this.loading = false;
            this.error = 'Error al crear la prueba o sus items';
        }
    });
}

  nuevaPrueba() {
    this.pruebaForm.reset();
    this.pruebaCreada = false;
    this.pruebaActual = null;
    this.items.clear();
    this.agregarItem();
  }

  get formInvalid(): boolean {
    if (this.pruebaForm.invalid) return true;
    if (this.items.length === 0) return true;
    
    const totalPeso = this.items.controls.reduce((sum, control) => 
      sum + Number(control.get('peso')?.value || 0), 0);
    
    return totalPeso !== 100;
  }

  isItemInvalid(index: number, field: string): boolean {
    const control = this.items.at(index).get(field);
    return control ? (control.invalid && control.touched) : false;
  }

  private markFormGroupTouched(formGroup: FormGroup | FormArray) {
    Object.values(formGroup.controls).forEach(control => {
      if (control instanceof FormGroup || control instanceof FormArray) {
        this.markFormGroupTouched(control);
      } else {
        control.markAsTouched();
      }
    });
  }

  descargarPlantilla() {
    if (!this.pruebaActual?.idPrueba) {
        this.error = 'Primero debe crear la prueba';
        return;
    }

    this.pruebasService.descargarPlantillaEvaluacion(this.pruebaActual.idPrueba)
        .subscribe({
            next: (blob: Blob) => {
                const url = window.URL.createObjectURL(blob);
                const link = document.createElement('a');
                link.href = url;
                link.download = 'plantilla-evaluacion.pdf';
                link.click();
                window.URL.revokeObjectURL(url);
            },
            error: (error) => {
                console.error('Error al descargar la plantilla:', error);
                this.error = 'Error al descargar la plantilla de evaluación';
            }
        });
  }
}