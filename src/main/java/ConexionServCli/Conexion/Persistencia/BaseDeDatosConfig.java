package ConexionServCli.Conexion.Persistencia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDatosConfig {
    private static final String URL = ConfigDB.getURL();

    private static final String USER = ConfigDB.getUser();
    private static final String PASSWORD = ConfigDB.getPassword();

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
