import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employee } from '../model/employee';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8089/employees';

  constructor(private http: HttpClient, private authService: AuthenticationService) {}

   getAllEmployees(): Observable<Employee[]> {

     const url = `${this.baseUrl}/getAll`;
    return this.http.get<Employee[]>(url);
   }


}
