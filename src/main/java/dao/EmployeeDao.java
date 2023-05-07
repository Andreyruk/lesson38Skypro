package dao;

import confug.ConnectionDB;
import model.City;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao implements Dao<Employee> {
    @Override
    public Employee get(long id) {

        try (Connection connection = ConnectionDB.getConnection()) {
            ResultSet set;
            PreparedStatement preparedStatement = connection.prepareStatement("select id, first_name, last_name, gender, age, city_id from public.employee where id=?");
            preparedStatement.setLong(1, id);
            set = preparedStatement.executeQuery();
            while (set.next()) {
                Employee employee = new Employee();
                employee.setId(set.getLong(1));
                employee.setFirstName(set.getString(2));
                employee.setLastName(set.getString(3));
                employee.setGender(set.getString(4));
                employee.setAge(set.getInt(5));
                Dao<City> cityDao = new CityDao();
                City city = cityDao.get(set.getLong(6));
                if (city != null)
                    employee.setCity(city);
                return employee;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Employee> getAll() {

        try (Connection connection = ConnectionDB.getConnection()) {
            ResultSet set;
            PreparedStatement preparedStatement = connection.prepareStatement("select id, first_name, last_name, gender, age, city_id from public.employee");
            set = preparedStatement.executeQuery();
            List<Employee> employees = new ArrayList<>();
            while (set.next()) {
                Employee employee = new Employee();
                employee.setId(set.getLong(1));
                employee.setFirstName(set.getString(2));
                employee.setLastName(set.getString(3));
                employee.setGender(set.getString(4));
                employee.setAge(set.getInt(5));
                Dao<City> cityDao = new CityDao();
                City city = cityDao.get(set.getLong(6));
                if (city != null)
                    employee.setCity(city);

                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Employee employee) {
        try (Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into public.employee(first_name, last_name, gender, age, city_id) values (?,?,?,?,?)");
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getGender());
            preparedStatement.setInt(4, employee.getAge());
            if (employee.getCity() != null)
                preparedStatement.setLong(5, employee.getCity().getCityId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(long id, Employee model) {
        try (Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("update public.employee set first_name=?,last_name=?, gender=?,age=?,city_id=? where id=?");
            preparedStatement.setString(1, model.getFirstName());
            preparedStatement.setString(2, model.getLastName());
            preparedStatement.setString(3, model.getGender());
            preparedStatement.setInt(4, model.getAge());
            if (model.getCity() != null)
                preparedStatement.setLong(5, model.getCity().getCityId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from public.employee where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
