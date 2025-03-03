import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionParticipantesComponent } from './gestion-participantes.component';

describe('GestionParticipantesComponent', () => {
  let component: GestionParticipantesComponent;
  let fixture: ComponentFixture<GestionParticipantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionParticipantesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionParticipantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
