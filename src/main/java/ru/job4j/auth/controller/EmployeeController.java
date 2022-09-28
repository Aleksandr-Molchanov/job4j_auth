package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.EmployeeService;
import ru.job4j.auth.service.PersonService;

import java.sql.Timestamp;
import java.util.Set;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RestTemplate rest;

    private final EmployeeService employeeService;

    private final PersonService personService;

    private BCryptPasswordEncoder encoder;

    private static final String API = "http://localhost:8080/users/";

    private static final String API_ID = "http://localhost:8080/users/{id}";

    public EmployeeController(final EmployeeService employeeService, BCryptPasswordEncoder encoder, PersonService personService) {
        this.employeeService = employeeService;
        this.encoder = encoder;
        this.personService = personService;
    }

    @GetMapping("/")
    public Set<Employee> findAll() {
        return this.employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable int id) {
        var employee = this.employeeService.findById(id);
        return new ResponseEntity<Employee>(
                employee.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Employee is not found. Please, check ID."
                )),
                HttpStatus.OK
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<Employee> create(@RequestBody Employee employee,
                                           @PathVariable int id) {
        checkingInputData(employee);
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
        checkingInputData(employee);
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

    @PatchMapping("/example2")
    public Person example2(@RequestBody Person person) {
        Person current = personService.findById(person.getId()).get();
        if (person.getUsername() == null || person.getPassword() == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if (person.getPassword().length() < 6) {
            throw new IllegalArgumentException("Invalid password. Password length must be more than 5 characters.");
        }
        current.setUsername(person.getUsername());
        current.setPassword(encoder.encode(person.getPassword()));
        return this.personService.save(current);
    }

    private void checkingInputData(@RequestBody Employee employee) {
        if (employee.getName() == null || employee.getSurname() == null) {
            throw new NullPointerException("Name and Surname mustn't be empty");
        }
        if (Integer.toString(employee.getInn()).length() != 8) {
            throw new IllegalArgumentException("Invalid INN. INN length must be equal to 9 characters.");
        }
    }
}