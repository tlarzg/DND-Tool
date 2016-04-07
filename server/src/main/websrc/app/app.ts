import {Component} from 'angular2/core';
import {RouteConfig, Router, ROUTER_DIRECTIVES} from 'angular2/router';
import {FORM_PROVIDERS} from 'angular2/common';

import {RouterActive} from './directives/router-active';
import Home from './home';
import Login from './login';


/*
 * App Component
 * Top Level Component
 */
@Component({
  selector: 'app',
  providers: [ ...FORM_PROVIDERS ],
  directives: [ ...ROUTER_DIRECTIVES, RouterActive, Login ],
  pipes: [],
  styles: [`
    nav ul {
      list-style-type: none;
      margin: 1em 0;
      padding: 0;
    }
    nav li {
      display: inline-block;
      padding: 0.25em;
    }
    nav li.active {
      background-color: lightgray;
    }
    footer {
      margin-top: 1em;
      border-top: 1px solid #ccc;
      padding-top: 0.5em;
    }
  `],
  template: `
    <header>
      <nav>
        <h1>Hello {{ name }}</h1>
        <ul>
          <li router-active="active">
            <a [routerLink]=" ['Home'] ">Home</a>
          </li>
          <li router-active="active">
            <a [routerLink]=" ['Login'] ">Login</a>
          </li>
        </ul>
      </nav>
    </header>
    <main>
      <router-outlet></router-outlet>
    </main>
    <footer>
      This is a DND Tool Thing. View me on <a [href]="url">Github</a>
    </footer>
  `
})
@RouteConfig([
  { path: '/', component: Home, name: 'Home' },
  { path: '/login', component: Login, name: 'Login' },
  { path: '/**', redirectTo: ['Home'] }
])
export class App {
  name = 'DND-Tool Thing';
  url = 'https://github.com/Zizekftw/DND-Tool';

  constructor() {

  }
}
