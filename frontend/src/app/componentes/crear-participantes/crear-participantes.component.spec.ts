import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearParticipantesComponent } from './crear-participantes.component';

describe('CrearParticipantesComponent', () => {
  let component: CrearParticipantesComponent;
  let fixture: ComponentFixture<CrearParticipantesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearParticipantesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearParticipantesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
