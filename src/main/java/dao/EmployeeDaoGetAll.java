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

public class EmployeeDaoGetAll extends EmployeeDao{

    @Override
    public List<Employee> getAll() {
        ResultSet set;
        try(Connection connection = ConnectionDB.getConnection()) {

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
}
