package com.example.demo.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Employee")
public class EmployeeRestController {

	@PostMapping("/getDepartment")
	public Mono<Department> helloEmployee(@RequestBody Employee emp) {
		System.out.println("Employee : " + emp);
		return Mono.just(new Department(emp.getEmpName(), emp.getEmpId()));
	}
}
