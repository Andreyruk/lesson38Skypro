package dao;

import confug.ConnectionDB;
import model.City;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDaoGet extends EmployeeDao{
    @Override
    public Employee get(long id) {
        ResultSet set;
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select id, first_name, last_name, gender, age, city_id from public.employee where id=?");
            preparedStatement.setLong(1,id);
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
}
