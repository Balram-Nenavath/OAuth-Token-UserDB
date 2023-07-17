package com.example.oauthtask.controller;

import com.example.oauthtask.dto.EmployeeDTO;
import com.example.oauthtask.service.EmployeeService;
import com.example.oauthtask.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeService empService;
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private Authentication authentication;
    @Test
    void saveEmployeeTest() {
        EmployeeDTO employee = new EmployeeDTO(3L, "rajesh", "raj@gmail.com", "Developer");
        when(employeeService.addEmployee(employee)).thenReturn(employee);
        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employee);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    void getEmployeeByIdTest() {
        EmployeeDTO employee = new EmployeeDTO();
        employee.setId(1L);
        employee.setName("bhaskar");
        employee.setDesignation("Tester");
        employee.setEmailId("java@gmail.com");
        doReturn(employee).when(employeeService).getEmployeeById(1L);
        ResponseEntity<EmployeeDTO> result = employeeController.getEmployeeById(1L);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(employee, result.getBody());
    }

    @Test
    void getEmployeeByIdNotFound() {
        doReturn(null).when(employeeService).getEmployeeById(1L);
        ResponseEntity<EmployeeDTO> result = employeeController.getEmployeeById(1L);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    @WithMockUser(authorities = { "ADMIN", "USER" })
    void getAllEmployees_success() {
        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(new EmployeeDTO(2L, "bhaskar", "IT@gmail.com", "Java Developer"));
        employees.add(new EmployeeDTO(3L, "bhuvan", "HR@gmail.com", "HR"));

       // when(authentication.isAuthenticated()).thenReturn(true);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
    }



    @Test
    @WithMockUser(roles = { "SOME_ROLE" })
    void getAllEmployees_Forbidden() {
        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllEmployees_Unauthorized() {
        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }



}
