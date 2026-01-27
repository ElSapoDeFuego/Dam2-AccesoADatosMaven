package tarea15;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	private static final String URL = "jdbc:mysql://localhost:3306/Alumnos06";
    private static final String USER = "root";
    private static final String PASS = "alumno";
    
    public static Connection conectar() {
    	Connection conn = null;
    	try {
			conn = DriverManager.getConnection(URL,USER,PASS);
		} catch (Exception e) {
			System.out.println("Error de conexión: "+ e.getMessage());
		}
    	return conn;
    }
}
