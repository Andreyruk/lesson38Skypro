import confug.ConnectionDB;
import dao.*;
import model.City;
import model.Employee;

import java.sql.*;
import java.util.List;

public class Application extends ConnectionDB {
    private static Dao<City> cityDao;
    private static Dao<Employee> employeeDaoCreate;
    private static Dao<Employee> employeeDaoDelete;
    private static Dao<Employee> employeeDaoGet;
    private static Dao<Employee> employeeDaoGetAll;
    private static Dao<Employee> employeeDaoUpdate;

    public static void main(String[] args) {
        cityDao = new CityDao();
        employeeDaoCreate = new EmployeeDaoCreate();
        employeeDaoDelete = new EmployeeDaoDelete();
        employeeDaoGet = new EmployeeDaoGet();
        employeeDaoGetAll = new EmployeeDaoGetAll();
        employeeDaoUpdate = new EmployeeDaoUpdate();

        ResultSet set;
       try(Connection connection = ConnectionDB.getConnection()) {
//           Statement statement = connection.createStatement();
           PreparedStatement preparedStatement = connection.prepareStatement("select e.first_name, e.last_name, e.gender, e.age, c.city_name from public.employee e left join public.city c on c.city_id=e.city_id where e.id=?");
           preparedStatement.setLong(1,4);
           set = preparedStatement.executeQuery();
           //set = statement.executeQuery("select e.first_name, e.last_name, e.gender, e.age, c.city_name from public.employee e left join public.city c on c.city_id=e.city_id where e.id=3");
           while (set.next()) {
               System.out.println(set.getString(1));
               System.out.println(set.getString(2));
               System.out.println(set.getString(3));
               System.out.println(set.getString(4));
               System.out.println(set.getString(5));
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

       City city = new City();
       city.setCityName("Москва");
       cityDao.save(city);
       city = cityDao.getAll().stream().filter(i -> i.getCityName().equals("Москва")).findFirst().orElseThrow(() -> new RuntimeException("Город не найден"));

       Employee employee = new Employee();
       employee.setFirstName("Вовка");
       employee.setLastName("Вовкин");
       employee.setGender("male");
       employee.setAge(70);
       employee.setCity(city);

        employeeDaoCreate.save(employee);
        List<Employee> employees = employeeDaoGetAll.getAll();
        System.out.println(employees);
        Employee createdEmployee = employees.stream().filter(i -> i.getFirstName().equals("Вовка") && i.getLastName().equals("Вовкин")).findFirst().orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        System.out.println(employeeDaoGet.get(createdEmployee.getId()).getFirstName());
        employeeDaoDelete.delete(createdEmployee.getId());
        System.out.println(employeeDaoGetAll.getAll());
        cityDao.delete(city.getCityId());
    }
}
