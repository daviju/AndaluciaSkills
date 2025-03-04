import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarPruebaPuntuacionComponent } from './listado-pruebas-por-puntuacion.component';

describe('ListarPruebaPuntuacionComponent', () => {
  let component: ListarPruebaPuntuacionComponent;
  let fixture: ComponentFixture<ListarPruebaPuntuacionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarPruebaPuntuacionComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ListarPruebaPuntuacionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
