import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PuntuacionItemsComponent } from './puntuacion-items.component';

describe('PuntuacionItemsComponent', () => {
  let component: PuntuacionItemsComponent;
  let fixture: ComponentFixture<PuntuacionItemsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PuntuacionItemsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PuntuacionItemsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
