import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaCompetidoresComponent } from '../listado-competidores/listado-competidores.component';

describe('ListadoCompetidoresComponent', () => {
  let component: ListaCompetidoresComponent;
  let fixture: ComponentFixture<ListaCompetidoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaCompetidoresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaCompetidoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
