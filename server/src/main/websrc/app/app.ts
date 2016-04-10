import {Component} from 'angular2/core';
import {RouteConfig, Router, ROUTER_DIRECTIVES} from 'angular2/router';
import {FORM_PROVIDERS} from 'angular2/common';
// For some reason dir is not getting imported automagically for sidenav...
var dir_111 = require('@angular2-material/core/rtl/dir');
import {MD_SIDENAV_DIRECTIVES} from '@angular2-material/sidenav/sidenav';
import {MdToolbar} from '@angular2-material/toolbar/toolbar';
import {MD_LIST_DIRECTIVES} from '@angular2-material/list/list';

import {RouterActive} from './directives/router-active';
import Home from './home';
import Login from './login';
import About from './about';

/*
 * App Component
 * Top Level Component
 */
@Component({
    selector: 'app',
    providers: [...FORM_PROVIDERS],
    directives: [
        ...ROUTER_DIRECTIVES,
        RouterActive,
        Login,
        MD_SIDENAV_DIRECTIVES,
        MdToolbar,
        MD_LIST_DIRECTIVES
    ],
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
    template: require('./app.tpl')()
})
@RouteConfig([
    { path: '/', component: Home, name: 'Home' },
    { path: '/login', component: Login, name: 'Login' },
    { path: '/about', component: About, name: 'About' },
    { path: '/**', redirectTo: ['Home'] }
])
export class App {
    name = 'DND-Tool Thing';
    url = 'https://github.com/Zizekftw/DND-Tool';

    constructor() {

    }
}
