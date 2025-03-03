import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearEspecialidadesComponent } from '../crear-especialidades/crear-especialidades.component';

describe('CrearEspecialidadComponent', () => {
  let component: CrearEspecialidadesComponent;
  let fixture: ComponentFixture<CrearEspecialidadesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearEspecialidadesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearEspecialidadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
