import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionPuntuacionesComponent } from './gestion-puntuaciones.component';

describe('GestionPuntuacionesComponent', () => {
  let component: GestionPuntuacionesComponent;
  let fixture: ComponentFixture<GestionPuntuacionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionPuntuacionesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionPuntuacionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
