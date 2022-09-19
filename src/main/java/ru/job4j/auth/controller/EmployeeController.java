package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.EmployeeService;

import java.sql.Timestamp;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RestTemplate rest;

    private final EmployeeService employeeService;

    private static final String API = "http://localhost:8080/person/";

    private static final String API_ID = "http://localhost:8080/person/{id}";

    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public Set<Employee> findAll() {
        return this.employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = this.employeeService.findById(id);
        return new ResponseEntity<Employee>(
                employee.orElse(new Employee()),
                employee.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<Employee> create(@RequestBody Employee employee,
                                           @PathVariable int id) {
        Person person = rest.getForObject(API_ID, Person.class, id);
        employee.setAccounts(Set.of(person));
        employee.setDateHiring(new Timestamp(System.currentTimeMillis()));
        return new ResponseEntity<Employee>(
                this.employeeService.save(employee),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<Void> update(@RequestBody Employee employee,
                                       @PathVariable int id) {
        Person person = rest.getForObject(API_ID, Person.class, id);
        employee.setAccounts(Set.of(person));
        employee.setDateHiring(new Timestamp(System.currentTimeMillis()));
        this.employeeService.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Employee employee = new Employee();
        employee.setId(id);
        this.employeeService.delete(employee);
        return ResponseEntity.ok().build();
    }
}