import { Component, OnInit } from '@angular/core';
import { EmployeeService } from '../service/employee.service';
import { Employee } from '../model/employee';
import { GridOptions } from 'ag-grid-community';
import { Router } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';
@Component({
  selector: 'app-employee-grid',
  templateUrl: './employee-grid.component.html',
  styleUrls: ['./employee-grid.component.css']
})
export class EmployeeGridComponent implements OnInit {
logout() {
  this.authService.logout();
}
  employees!: Employee[];
  columnDefs!: any[];
  defaultColDef: any;
  gridOptions!: GridOptions;
  constructor(private employeeService: EmployeeService, private router: Router, private authService: AuthenticationService) { }

  ngOnInit() {
    this.getAllEmployees();
    this.columnDefs = [
      { field: 'id', headerName: 'ID', sortable: true, filter: true, checkboxSelection: true },
      { field: 'name', headerName: 'Name',sortable: true, filter: true },
      { field: 'emailId', headerName: 'Email',sortable: true, filter: true },
      { field: 'designation',headerName: 'Designation', sortable: true, filter: true },
      { headerName: 'Designation Group', minWidth: 250, field: 'designation', cellRendererParams: { suppressCount: true } }

     ];
     this.defaultColDef = {
      sortable: true,
      filter: true,
      flex: 1
    };

    // this.gridOptions = {
    //   suppressMakeColumnVisibleAfterUnGroup: true,
    //   suppressRowClickSelection: true,
    //   autoGroupColumnDef: {
    //     headerName: 'Designation Group',
    //     minWidth: 150,
    //     cellRendererParams: {
    //       suppressCount: true
    //     }
    //   }
    // };
  }

  getAllEmployees() {
    this.employeeService.getAllEmployees().subscribe(
      (data: Employee[]) => {
        this.employees = data;
      },
      (error: any) => {
        console.error('Failed to fetch employees', error);
      }
    );
  }
}
