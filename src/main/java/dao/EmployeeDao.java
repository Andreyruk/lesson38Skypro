package dao;

import model.Employee;

import java.util.List;

public abstract class EmployeeDao implements Dao<Employee>{
    @Override
    public Employee get(long id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }

    @Override
    public void save(Employee employee) {

    }

    @Override
    public void update(long id, Employee model) {

    }

    @Override
    public void delete(long id) {

    }
}
