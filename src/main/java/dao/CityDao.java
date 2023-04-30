package dao;

import confug.ConnectionDB;
import model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDao implements Dao<City>{
    @Override
    public City get(long id) {
        ResultSet set;
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select * from public.city where city_id=?");
            preparedStatement.setLong(1,id);
            set = preparedStatement.executeQuery();
            while (set.next()) {
                City city = new City();
                city.setCityId(set.getLong(1));
                city.setCityName(set.getString(2));
                return city;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<City> getAll() {
        ResultSet set;
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("select * from public.city");
            set = preparedStatement.executeQuery();
            List<City> cities = new ArrayList<>();
            while (set.next()) {
                City city = new City();
                city.setCityId(set.getLong(1));
                city.setCityName(set.getString(2));
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(City city) {
        try(Connection connection = ConnectionDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into public.city(city_name) values (?)");
            preparedStatement.setString(1, city.getCityName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(long id, City model) {
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("update public.city set city_name=? where id=?");
            preparedStatement.setString(1, model.getCityName());
            preparedStatement.setLong(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try(Connection connection = ConnectionDB.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from public.city where city_id=?");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
