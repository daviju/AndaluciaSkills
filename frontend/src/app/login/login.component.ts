import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  imports: [ CommonModule, FormsModule ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  usuario: string;
  pass: string;

  constructor(private servicioLogin: LoginService) {
    this.usuario = "";
    this.pass = "";
  }

  login():void {
    this.servicioLogin.login(this.usuario, this.pass);
  }

}
