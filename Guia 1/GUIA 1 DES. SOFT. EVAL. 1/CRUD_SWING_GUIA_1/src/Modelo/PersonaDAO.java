package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Landero L. Lucio
 */

public class PersonaDAO {
    conexion conectar = new conexion(); 
    Connection con;
    PreparedStatement ps; 
    ResultSet rs; 

    // LISTAR/READ
    public List<Persona> listar() {
        List<Persona> datos = new ArrayList<>();
        String sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados"; // Consulta correcta
        
        try {
            con = conectar.getConnection(); 
            ps = con.prepareStatement(sql); 
            rs = ps.executeQuery(); 
            
       
            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("Id_Empleado")); 
                p.setNombre(rs.getString("NombresEmpleado")); 
                p.setCorreo(rs.getString("Correo")); 
                p.setTelefono(rs.getString("Telefono")); 
                datos.add(p); 
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al listar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return datos;
    }

    // AGREGAR/CREATE
    public int Agregar(Persona p) {
    String sql = "INSERT INTO Persona.Empleados (DUI_Empleado, ISSS_Empleado, NombresEmpleado, ApellidosEmpleado, FechaNacEmpleado, Telefono, Correo, Id_Cargo, Id_Direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql); 
        
        ps.setString(1, "00000000-0"); //DUIGenerico
        ps.setInt(2, 123456789); //ISSS EMPLEADO Generico
        ps.setString(3, p.getNombre()); 
        ps.setString(4, "Apellido Genérico"); //APELLDO EMPLEADO Generico
        ps.setDate(5, java.sql.Date.valueOf("1990-01-01")); //FECHA NAC EMPLEADO Generico
        ps.setString(6, p.getTelefono()); 
        ps.setString(7, p.getCorreo()); 
        ps.setInt(8, 1); //ID_CARGO Generico
        ps.setInt(9, 1); //ID_Direccion Generico
        //PORQUE generico? Porque en la BASE DE DATOS los campos ESTAN SET A NOT NULL y como no tenemos botones y textfields en la vista con los demas campos los valores no pueden quedar nulos hence hay que hacerlo generico o hacer un alter a la base de ddatos para que acepte valores nulos.
        ps.executeUpdate(); 
    } catch (SQLException e) {
        System.out.println("Error SQL al agregar: " + e.getMessage());
        return 0;
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    return 1; 
}

    // EDITAR/UPDATE
    public int Editar(Persona p) {
    String sql = "UPDATE Persona.Empleados SET NombresEmpleado=?, Correo=?, Telefono=? WHERE Id_Empleado=?";
    
        try {
            con = conectar.getConnection(); 
            ps = con.prepareStatement(sql); 
            ps.setString(1, p.getNombre());   
            ps.setString(2, p.getCorreo());   
            ps.setString(3, p.getTelefono()); 
            ps.setInt(4, p.getId());          
            ps.executeUpdate(); 
        } catch (SQLException e) {
            System.out.println("Error SQL al editar: " + e.getMessage());
        return 0; 
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return 1; 
    }
    
    // ELIMINAR/DELETE
    public void Eliminar(int id) {
    String sql = "DELETE FROM Persona.Empleados WHERE Id_Empleado=?";
    
        try {
            con = conectar.getConnection(); 
            ps = con.prepareStatement(sql); 
        
        
            ps.setInt(1, id); 
        
            ps.executeUpdate(); 
        } catch (SQLException e) {
            System.out.println("Error SQL al eliminar: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
}
    //FILTRAR por ID, NOMBRE, CORREO, y TELEFONO
    public List<Persona> filtrar(String id, String nombre, String correo, String telefono) {
    List<Persona> datos = new ArrayList<>();
    String sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados WHERE 1=1";

        if (id != null && !id.isEmpty()) {
            sql += " AND Id_Empleado = ?"; 
        }
        if (nombre != null && !nombre.isEmpty()) {
            sql += " AND NombresEmpleado LIKE ?";
        }
        if (correo != null && !correo.isEmpty()) {
            sql += " AND Correo LIKE ?";
        }
        if (telefono != null && !telefono.isEmpty()) {
            sql += " AND Telefono LIKE ?";
        }

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql); 

        int paramIndex = 1;
            if (id != null && !id.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(id));
            }
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(paramIndex++, "%" + nombre + "%");
            }
            if (correo != null && !correo.isEmpty()) {
                ps.setString(paramIndex++, "%" + correo + "%");
            }
            if (telefono != null && !telefono.isEmpty()) {
                ps.setString(paramIndex++, "%" + telefono + "%");
            }

        rs = ps.executeQuery(); 

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("Id_Empleado"));
                p.setNombre(rs.getString("NombresEmpleado"));
                p.setCorreo(rs.getString("Correo"));
                p.setTelefono(rs.getString("Telefono"));
                datos.add(p); 
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al filtrar: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return datos;
}

    public Persona obtenerPorId(int id) {
        Persona p = new Persona();
        String sql = "SELECT Id_Empleado, NombresEmpleado, Correo, Telefono FROM Persona.Empleados WHERE Id_Empleado=?";
    
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
        
            if (rs.next()) {
                p.setId(rs.getInt("Id_Empleado"));
                p.setNombre(rs.getString("NombresEmpleado"));
                p.setCorreo(rs.getString("Correo"));
                p.setTelefono(rs.getString("Telefono"));
            }
        } catch (SQLException e) {
            System.out.println("Error SQL al obtener por ID: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return p;
    }
}
