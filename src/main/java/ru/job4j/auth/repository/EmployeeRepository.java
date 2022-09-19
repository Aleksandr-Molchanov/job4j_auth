package ru.job4j.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.auth.model.Employee;

import java.util.Set;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    @Override
    Set<Employee> findAll();
}
