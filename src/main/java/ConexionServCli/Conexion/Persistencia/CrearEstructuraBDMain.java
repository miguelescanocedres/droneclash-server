package ConexionServCli.Conexion.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearEstructuraBDMain {

    public static void main(String[] args) {

        String host = ConfigDB.getHost();
        int port = ConfigDB.getPort();
        String user = ConfigDB.getUser();
        String password = ConfigDB.getPassword();
        String database = ConfigDB.getDatabase();

        String urlSinDB =
                "jdbc:mysql://" + host + ":" + port +
                        "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

        String crearDatabase = "CREATE DATABASE IF NOT EXISTS " + database;
        String usarDatabase = "USE " + database;

        String crearTabla =
                "CREATE TABLE IF NOT EXISTS PARTIDAS (" +
                        "IDPARTIDA BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "INFOPARTIDA LONGBLOB NOT NULL" +
                        ")";

        try (Connection conn = DriverManager.getConnection(urlSinDB, user, password);
             Statement st = conn.createStatement()) {

            st.executeUpdate(crearDatabase);
            System.out.println("Base " + database + " creada/verificada.");

            st.executeUpdate(usarDatabase);

            st.executeUpdate(crearTabla);
            System.out.println("Tabla PARTIDAS creada/verificada.");

            System.out.println("Estructura de base de datos lista");

        } catch (SQLException e) {
            System.err.println("Error creando estructura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}