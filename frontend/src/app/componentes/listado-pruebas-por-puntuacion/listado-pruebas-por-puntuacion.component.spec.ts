import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListadoPruebasPorPuntuacionComponent } from './listado-pruebas-por-puntuacion.component';

describe('ListadoPruebasPorPuntuacionComponent', () => {
  let component: ListadoPruebasPorPuntuacionComponent;
  let fixture: ComponentFixture<ListadoPruebasPorPuntuacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListadoPruebasPorPuntuacionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListadoPruebasPorPuntuacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
