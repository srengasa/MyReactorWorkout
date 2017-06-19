package com.example.demo.service;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/Department")
public class DepartmentRestController {

	@PostMapping("/getEmployee")
	public Mono<Employee> getEmployee(@RequestBody Department dept) {
		System.out.println("Received request for employee " + dept);
		Employee emp = new Employee(dept.getDeptName(), dept.getDeptId());
		return Mono.just(emp);
	}
}
