package dao;

import confug.ConnectionDB;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoUpdate extends EmployeeDao{
    @Override
    public void update(long id, Employee model) {
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("update public.employee set first_name=?,last_name=?, gender=?,age=?,city_id=? where id=?");
            preparedStatement.setString(1,model.getFirstName());
            preparedStatement.setString(2,model.getLastName());
            preparedStatement.setString(3,model.getGender());
            preparedStatement.setInt(4,model.getAge());
            if (model.getCity() != null)
                preparedStatement.setLong(5,model.getCity().getCityId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
