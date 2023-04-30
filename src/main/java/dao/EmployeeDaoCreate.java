package dao;

import confug.ConnectionDB;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoCreate extends EmployeeDao{
    @Override
    public void save(Employee employee) {
        try(Connection connection = ConnectionDB.getConnection()) {
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
}
