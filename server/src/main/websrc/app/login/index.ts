import {Component} from 'angular2/core';
import {FORM_DIRECTIVES} from 'angular2/common';
import {Http, Headers, HTTP_PROVIDERS} from 'angular2/http';

@Component({
  selector: 'login',
  providers: [
    HTTP_PROVIDERS
  ],
  directives: [
    ...FORM_DIRECTIVES
  ],
  pipes: [],
  styles: [],
  template: `
  <div>
    <form (ngSubmit)="performLogin()">
      <fieldset>
        <label for="username">Username:</label>
        <input type="text" id="username" [(ngModel)]="username">
      </fieldset>
      <fieldset>
        <label for="password">Password:</label>
        <input type="password" id="password" [(ngModel)]="password">
      </fieldset>
      <button type="submit" id="login">Login</button>
    </form>
  </div>`
})
export default class Login {
  http: Http;
  username: String;
  password: String;

  constructor(http: Http) {
    this.http = http;
  }

  performLogin() {

    // Lol, manually building urlencoded post body because $http only takes strings
    let loginRequest = 'username=' + this.username
      + '&password=' + this.password;
    this.http.post('/api/login', loginRequest, {headers: new Headers({
        'Content-Type': 'application/x-www-form-urlencoded'
      })})
      .subscribe(
        () => {
          alert('Login succeeded')
        },
        err => {
          console.log(err);
          alert('Login failed!');
        });
  }
}
