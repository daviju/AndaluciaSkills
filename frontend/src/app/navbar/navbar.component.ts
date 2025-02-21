import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-navbar',
  imports: [ CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  perfil : string;
  nombre : string;

  constructor(private route:Router, private servicio:LoginService) {
    this.perfil = "";
    this.nombre = "";
  }

  logout() {
    this.servicio.logout();
    this.nombre = "";
  }

  login() {
    this.route.navigate(['/login']);
  }

  isLogged() {
    return this.servicio.isLogged();
  }

  getNombre() {
    return this.servicio.getNombre();
  }
}
