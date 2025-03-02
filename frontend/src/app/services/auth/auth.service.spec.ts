import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';

/**
 * Suite de pruebas para el servicio AuthService
 * Verifica la funcionalidad básica del servicio de autenticación
 */
describe('AuthService', () => {
  let service: AuthService; // Instancia del servicio que será probada

  /**
   * Configuración que se ejecuta antes de cada prueba
   * Prepara el entorno de pruebas y crea una instancia fresca del servicio
   */
  beforeEach(() => {
    // Configura el módulo de pruebas con las dependencias necesarias
    TestBed.configureTestingModule({
      // Aquí se podrían añadir providers, imports, etc. necesarios para las pruebas
    });
    
    // Obtiene una instancia del servicio desde el inyector de dependencias de Angular
    service = TestBed.inject(AuthService);
  });

  /**
   * Prueba básica que verifica que el servicio se crea correctamente
   * Es el primer paso para asegurar que la inyección de dependencias funciona
   */
  it('should be created', () => {
    // Verifica que la instancia del servicio existe
    expect(service).toBeTruthy();
  });
  
  // Aquí se añadirían más pruebas para verificar la funcionalidad específica del servicio:
  // - Tests para iniciarSesion()
  // - Tests para cerrarSesion()
  // - Tests para verificar la recuperación de datos desde localStorage
  // - Tests para los métodos de verificación de roles (esAdmin, esExperto)
  // - Tests para los getters de información del usuario
});