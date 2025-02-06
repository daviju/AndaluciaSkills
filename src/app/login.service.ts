import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  token: string;
  perfil: string; // admin, experto
  logged: boolean;
  user: any; // objeto con los datos del usuario


  constructor() {
    this.token = "";
    this.perfil = "";
    this.logged = false;
    this.user = {};
  }

  almacenar() {
    var objeto: any;
    objeto = {
      token: this.token,
      perfil: this.perfil,
      logged: this.logged,
      user: this.user
    };
    localStorage.setItem("LOGIN", JSON.stringify(objeto));
  }

  reuperar() {

    var cadena: any;

    cadena = localStorage.getItem("LOGIN") || "";

    if (cadena != "") {

      var objeto = JSON.parse(cadena);

      this.token = objeto.token;
      this.perfil = objeto.perfil;
      this.logged = objeto.logged;
      this.user = objeto.user;

    } else {

      this.token = "";
      this.perfil = "";
      this.logged = false;
      this.user = {};

    }

  }

}
