// Importar los módulos y clases necesarios para las pruebas
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { NavbarComponent } from './navbar.component';

// Describir el conjunto de pruebas para NavbarComponent
describe('NavbarComponent', () => {

  // Definir variables para el componente y el fixture
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;

  // Antes de cada prueba, configurar el módulo de pruebas y crear el componente
  beforeEach(async () => {

    // Configurar el módulo de pruebas con el NavbarComponent
    await TestBed.configureTestingModule({

      // Importar NavbarComponent
      imports: [NavbarComponent]

    })

    // Compilar los componentes
    .compileComponents();

    // Crear el fixture para NavbarComponent
    fixture = TestBed.createComponent(NavbarComponent);

    // Obtener la instancia de NavbarComponent del fixture
    component = fixture.componentInstance;

    // Detectar cambios para el componente
    fixture.detectChanges();
  });

  // Definir un caso de prueba para verificar si el componente se crea correctamente
  it('should create', () => {

    // Afirmar que la instancia del componente es verdadera, lo que significa que existe
    expect(component).toBeTruthy();
  });
});