package ConexionServCli.Conexion.Persistencia;
import java.sql.*;

public class PartidaDAO {

    public long guardar(byte[] data) throws SQLException {

        String sql = "INSERT INTO PARTIDAS (INFOPARTIDA) VALUES (?)";

        try (Connection conn = BaseDeDatosConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setBytes(1, data);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    throw new RuntimeException("No se pudo obtener ID generado");
                }
            }
        }
    }

    public byte[] cargar(long idPartida) throws SQLException {

        String sql = "SELECT INFOPARTIDA FROM PARTIDAS WHERE IDPARTIDA = ?";

        try (Connection conn = BaseDeDatosConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idPartida);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("INFOPARTIDA");
                } else {
                    throw new RuntimeException("Partida no encontrada");
                }
            }
        }
    }
}