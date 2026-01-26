package tarea14;

import java.sql.SQLException;
import java.util.List;

public interface AlumnoDao {
	
	int aniadir(Alumno alu)  ;
	
	Alumno obtenerPorElId(int id)  ;
	
	List<Alumno> obtenerTodos()  ;
	
	int modificar(Alumno alu)  ;
	
	void borrar(int id)  ;
}
