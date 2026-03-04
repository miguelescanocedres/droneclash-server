package ConexionServCli.Conexion.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CrearEstructuraBDMain {

    private static final String HOST = "localhost";
    private static final int PORT = 3306;

    private static final String USER = "root";
    private static final String PASSWORD = "Proyecto";

    private static final String URL_SIN_DB =
            "jdbc:mysql://" + HOST + ":" + PORT +
                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public static void main(String[] args) {

        String crearDatabase = "CREATE DATABASE IF NOT EXISTS droneclash";
        String usarDatabase = "USE droneclash";

        String crearTabla =
                "CREATE TABLE IF NOT EXISTS PARTIDAS (" +
                        "IDPARTIDA BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                        "INFOPARTIDA LONGBLOB NOT NULL" +
                        ")";

        try (Connection conn = DriverManager.getConnection(URL_SIN_DB, USER, PASSWORD);
             Statement st = conn.createStatement()) {


            st.executeUpdate(crearDatabase);
            System.out.println("Base droneclash creada/verificada.");


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