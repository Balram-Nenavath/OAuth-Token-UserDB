import { Component } from '@angular/core';
import { User } from '../model/user';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData = {
    username: '',
    password: ''
  };
  msg: string='';
 // authToken: any;
  constructor(private router: Router, private authService : AuthenticationService){}

  gotoRegister(){
    this.router.navigate(['/register'])
  }

  loginUser(){
    const body = new URLSearchParams();
    body.set('username', this.loginData.username);
    body.set('password', this.loginData.password);
    body.set('grant_type', 'password');



    this.authService.loginCheck(body.toString()).subscribe(
      (data: any) => {
        console.log("response received",data)
        alert("Success! Please login to continue");
        this.router.navigate(['/employee']);
      },
      (error: any) => {
        console.log("exception occured",error);
        this.msg = "Bad credentials, please enter valid email and password";
      }
    );
  }
}
