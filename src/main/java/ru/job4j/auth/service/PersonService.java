package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Optional<Person> findById(int id) {
        return repository.findById(id);
    }

    public void delete(Person person) {
        repository.delete(person);
    }
}
