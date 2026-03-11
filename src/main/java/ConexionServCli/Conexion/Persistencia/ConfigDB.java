package ConexionServCli.Conexion.Persistencia;

import java.io.InputStream;
import java.util.Properties;

public class ConfigDB {

    private static final Properties props = new Properties();

    static {
        try (InputStream input =
                     ConfigDB.class.getClassLoader()
                             .getResourceAsStream("db.config.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró db.config.properties");
            }

            props.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración DB", e);
        }
    }

    public static String getHost() {
        return props.getProperty("db.host");
    }

    public static int getPort() {
        return Integer.parseInt(props.getProperty("db.port"));
    }

    public static String getUser() {
        return props.getProperty("db.user");
    }

    public static String getPassword() {
        return props.getProperty("db.password");
    }

    public static String getDatabase() {
        return props.getProperty("db.database");
    }

    public static String getURL() {
        return props.getProperty("db.url");
    }
}