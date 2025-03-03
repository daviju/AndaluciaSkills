import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearExpertosComponent } from './crear-expertos.component';

describe('CrearExpertosComponent', () => {
  let component: CrearExpertosComponent;
  let fixture: ComponentFixture<CrearExpertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearExpertosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearExpertosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
