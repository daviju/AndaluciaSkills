import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionPruebasComponent } from './gestion-pruebas.component';

describe('GestionPruebasComponent', () => {
  let component: GestionPruebasComponent;
  let fixture: ComponentFixture<GestionPruebasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionPruebasComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionPruebasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
