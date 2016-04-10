import {Component} from 'angular2/core';
import {FORM_DIRECTIVES} from 'angular2/common';
import {Http, Headers, HTTP_PROVIDERS} from 'angular2/http';
// For some reason field-value is not getting imported without this...
var field_value_111 = require('@angular2-material/core/annotations/field-value');
import {MdButton} from '@angular2-material/button/button';
import {MD_INPUT_DIRECTIVES} from '@angular2-material/input';

@Component({
    selector: 'login',
    providers: [
        HTTP_PROVIDERS
    ],
    directives: [
        ...FORM_DIRECTIVES,
        MdButton,
        MD_INPUT_DIRECTIVES
    ],
    pipes: [],
    styles: [],
    template: require('./login.tpl')()
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
        this.http.post('/api/login', loginRequest, {
            headers: new Headers({
                'Content-Type': 'application/x-www-form-urlencoded'
            })
        })
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
