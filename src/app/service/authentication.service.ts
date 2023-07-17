import { Injectable } from '@angular/core';
import { User } from '../model/user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';
@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  baseUrl = 'http://localhost:8089/users/user';

  constructor(
    private http: HttpClient,
    private localStoraqeService: LocalStorageService,
    private router: Router
  ) {}
  registerCheck(user: User) {
    return this.http.post(this.baseUrl, user);
  }

  isAuthenticated(): boolean {
    return this.localStoraqeService.retrieve('authenticationToken') != null;
  }

  loginCheck(body: any) {
    const headers = new HttpHeaders({
      'Access-Control-Allow-Origin': '*',
      'Content-Type': 'application/x-www-form-urlencoded',
      Authorization: 'Basic ' + btoa('ram2910:ramSecret'),
    });
    return this.http.post<any>(
      'http://localhost:8089/oauth/token',
      body.toString(),
      { headers }
    ).pipe(map((data: any) => {
      console.log(" data here in logincheck"+ data.access_token)
      this.localStoraqeService.store('authenticationToken', data.access_token);
      return true;
    }));
  }

  logout() {
    this.localStoraqeService.clear('authenticationToken');
    this.router.navigate(['/login']);
  }

}
