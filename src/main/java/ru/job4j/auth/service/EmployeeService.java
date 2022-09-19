package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Set<Employee> findAll() {
        return repository.findAll();
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Optional<Employee> findById(int id) {
        return repository.findById(id);
    }

    public void delete(Employee employee) {
        repository.delete(employee);
    }
}
