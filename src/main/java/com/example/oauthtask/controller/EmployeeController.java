package com.example.oauthtask.controller;

import com.example.oauthtask.dto.EmployeeDTO;
import com.example.oauthtask.service.EmployeeService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return new ResponseEntity<>(employeeService.addEmployee(employeeDTO), HttpStatus.CREATED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

		List<EmployeeDTO> employeeDTOs = employeeService.getAllEmployees();
		return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("id") Long id) {
		EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
		if (employeeDTO != null) {
			return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
