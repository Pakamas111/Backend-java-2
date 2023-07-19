package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@RestController
public class EmployeeController {

	private List<Employee> data = new ArrayList<Employee>();

	@GetMapping("/employee")
	public List<Employee> getEmployee() {
		return data;
	}

	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody Employee body) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getEmployeeId() == body.getEmployeeId()) {
				return null;
			}
		}

		data.add(body);
		return body;

	}

	@GetMapping("/employee/{employeeId}")
	public Employee getEmployeeDetail(@PathVariable Integer employeeId) {

		for (int i = 0; i < data.size(); i++) {
			if (employeeId == data.get(i).getEmployeeId()) {
				return data.get(i);
			}
		}
		return null;
	}

	@PutMapping("/employee/{employeeId}")
	public Employee updaEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {

		for (int i = 0; i < data.size(); i++) {
			if (employeeId == data.get(i).getEmployeeId()) {

				data.get(i).setFirstName(body.getFirstName());
				data.get(i).setLastName(body.getLastName());
				data.get(i).setSalary(body.getSalary());

				return data.get(i);

			}
		}
		return null;
	}

	@DeleteMapping("/employee/{employeeId}")
	public String deleEmployee(@PathVariable Integer employeeId) {
		for (int i = 0; i < data.size(); i++) {
			if (employeeId == data.get(i).getEmployeeId()) {
				data.remove(i);
				
				return "delete sucess";
			}
		}

		return "emplyee not found";
	}

}
