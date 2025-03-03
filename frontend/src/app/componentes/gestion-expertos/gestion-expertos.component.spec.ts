import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionExpertosComponent } from '../gestion-expertos/gestion-expertos.component';

describe('GestionarExpertosComponent', () => {
  let component: GestionExpertosComponent;
  let fixture: ComponentFixture<GestionExpertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionExpertosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionExpertosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
