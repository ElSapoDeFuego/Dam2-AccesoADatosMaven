package tarea14;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDao {
	
	int aniadir(Alumno alu) throws SQLException;
	
	Alumno obtenerPorElId(int id) throws SQLException;
	
	List<Alumno> obtenerTodos() throws SQLException;
	
	int modificar(Alumno alu) throws SQLException;
	
	void borrar(int id) throws SQLException;
}
