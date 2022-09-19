package ru.job4j.auth.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private int inn;
    private Timestamp dateHiring;
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "EMPLOYEE_ID_FK"))
    private Set<Person> accounts;

    public static Employee of(int id, String name, String surname, int inn, Set<Person> accounts) {
        Employee employee = new Employee();
        employee.id = id;
        employee.name = name;
        employee.surname = surname;
        employee.inn = inn;
        employee.dateHiring = new Timestamp(System.currentTimeMillis());
        employee.accounts = accounts;
        return employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public Timestamp getDateHiring() {
        return dateHiring;
    }

    public void setDateHiring(Timestamp dateHiring) {
        this.dateHiring = dateHiring;
    }

    public Set<Person> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Person> accounts) {
        this.accounts = accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return id == employee.id
                && inn == employee.inn
                && Objects.equals(name, employee.name)
                && Objects.equals(surname, employee.surname)
                && Objects.equals(dateHiring, employee.dateHiring)
                && Objects.equals(accounts, employee.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, inn, dateHiring, accounts);
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", firstName='" + name + '\''
                + ", lastName='" + surname + '\''
                + ", inn=" + inn
                + ", dateHiring=" + dateHiring
                + ", accounts=" + accounts
                + '}';
    }
}
