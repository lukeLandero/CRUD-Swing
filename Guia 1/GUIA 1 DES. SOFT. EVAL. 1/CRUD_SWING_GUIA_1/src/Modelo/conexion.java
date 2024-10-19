package Modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Landero L. Lucio
 */
public class conexion {
    private String databaseName = "TiendaByteCode";
    private String url = "jdbc:sqlserver://DESKTOP-46QQROO;databaseName=" + databaseName + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    
    public Connection getConnection() {
        Connection conexion = null; 
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            conexion = DriverManager.getConnection(url);
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos " + databaseName + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error de Clase no encontrada: " + e.getMessage());
        }
        return conexion; 
    }
}
