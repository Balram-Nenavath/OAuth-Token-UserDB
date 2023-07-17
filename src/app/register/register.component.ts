import { Component, OnInit } from '@angular/core';
import { User } from '../model/user';
import { AuthenticationService } from '../service/authentication.service';
import { Router } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{

  user: User = {
    id: 0,
    username: '',
    password: '',
    roles: []
  };
  msg: string='';
  userForm!: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private authService : AuthenticationService) {}

  ngOnInit() {
    this.userForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      roles: this.formBuilder.array([])
    });
  }

  get roles() {
    return this.userForm.get('roles') as FormArray;
  }

  addRole() {
    this.roles.push(this.formBuilder.control('', Validators.required));
  }

  removeRole(index: number) {
    this.roles.removeAt(index);
  }

  registerUser() {
    if (this.userForm.valid) {
      const user: User = {
        id: 0,
        username: this.userForm.value.username,
        password: this.userForm.value.password,
        roles: this.userForm.value.roles
      };

      this.authService.registerCheck(user).subscribe(
        (        response: any) => {
          console.log('User saved successfully:', response);
          this.router.navigate(['/login'])
        },
        (        error: any) => {
          console.error('Error saving user:', error);
        }
      );
    }
  }
    gotoLogin(){
    this.router.navigate(['/login'])
  }

}
