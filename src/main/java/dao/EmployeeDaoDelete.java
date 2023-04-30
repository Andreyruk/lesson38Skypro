package dao;

import confug.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoDelete extends EmployeeDao{
    @Override
    public void delete(long id) {
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from public.employee where id=?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
