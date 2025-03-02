import { ComponentFixture, TestBed } from '@angular/core/testing'; // Importaciones necesarias para las pruebas

import { LoginComponent } from './login.component'; // Importa el componente a probar

describe('LoginComponent', () => { // Describe el conjunto de pruebas para `LoginComponent`
  let component: LoginComponent; // Variable para el componente
  let fixture: ComponentFixture<LoginComponent>; // Variable para el fixture del componente

  beforeEach(async () => { // Antes de cada prueba, configura el módulo de pruebas
    await TestBed.configureTestingModule({
      imports: [LoginComponent] // Importa el componente que se está probando
    })
    .compileComponents(); // Compila los componentes

    fixture = TestBed.createComponent(LoginComponent); // Crea una instancia del componente
    component = fixture.componentInstance; // Asigna la instancia del componente a la variable `component`
    fixture.detectChanges(); // Detecta los cambios en el componente
  });

  it('should create', () => { // Prueba para verificar que el componente se crea correctamente
    expect(component).toBeTruthy(); // Verifica que el componente exista
  });
});