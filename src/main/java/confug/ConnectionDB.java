package confug;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skypro","postgres","7andreyru@Skypro_");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;

    }

}
