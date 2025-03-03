import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerGanadoresComponent } from './ver-ganadores.component';

describe('VerGanadoresComponent', () => {
  let component: VerGanadoresComponent;
  let fixture: ComponentFixture<VerGanadoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VerGanadoresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VerGanadoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
