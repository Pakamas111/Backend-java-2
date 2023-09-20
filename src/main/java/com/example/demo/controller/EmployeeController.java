package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Employee;
import com.example.demo.model.Role;
import com.example.demo.model.Skill;


import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.SkillRepository;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SkillRepository skillRepository;

	@GetMapping("/employee")
	public ResponseEntity<Object> getemployee() {
		try {
			List<Employee> employees = employeeRepository.findAll();
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.OK);
			// TODO: handle exception
		}
	}

	@PostMapping("/employee")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee body) {
		try {
			Employee employee = employeeRepository.save(body);
			Optional<Role> role = roleRepository.findById(4);
			body.setRole(role.get());
			
			
			
			for(Skill skill:body.getSkills()) {
				skill.setEmployee(employee);
			
				skillRepository.save(skill);
			}
				
			
			return new ResponseEntity<>(employee, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Integer server  error", HttpStatus.OK);
		}
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<Object> getEmployeeDetail(@PathVariable Integer employeeId) {
		try {
			
			Optional<Employee> employee = employeeRepository.findById(employeeId);
			
			
			if (employee.isPresent()) {
				return new ResponseEntity<>(employee, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employeee not found", HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Integer server  error", HttpStatus.OK);
		}
	}

	@PutMapping("/employee/{employeeId}")
	public ResponseEntity<Object> updatEmployee(@PathVariable Integer employeeId, @RequestBody Employee body) {
		try {
			Optional<Employee> employee = employeeRepository.findById(employeeId);

			if (employee.isPresent()) {

				Employee employeeEdit = employee.get();

				employeeEdit.setFirstName(body.getFirstName());
				employeeEdit.setLastName(body.getLastName());
				employeeEdit.setSalary(body.getSalary());
				employeeEdit.setEmployeeId(body.getEmployeeId());

				employeeRepository.save(employeeEdit);

				return new ResponseEntity<>("DELETE SUCSESS",HttpStatus.OK);

			} else {

				return new ResponseEntity<>("Not found",HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
	
		}

	}

	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<Object> deletEmployee(@PathVariable Integer employeeId) {
		
		try {
			Optional<Employee> employee = employeeRepository.findById(employeeId);
			
			if (employee.isPresent()) {
				employeeRepository.delete(employee.get());

				return new ResponseEntity<>("DELETE SUCSESS",HttpStatus.OK);
		}  else {
			return new ResponseEntity<>("Not found",HttpStatus.BAD_REQUEST);
		}
			
		} catch (Exception e) {
			
			return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}