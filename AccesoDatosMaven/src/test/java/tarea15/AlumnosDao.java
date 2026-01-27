package tarea15;

import java.util.List;

public interface AlumnosDao {
	
	void insertarAlumno(Alumno alu);

	void insertarGrupo(Grupo grupo);

	List<Alumno> obtenerTodos(); 

	void modificarNombre(int id, String nombre);

	void eliminarAlumno(int id);

	void eliminarPorCurso(String curso);

	void exportarFichero();

	void importarFichero();

	void exportarJSON();

	void importarJSON();
}
