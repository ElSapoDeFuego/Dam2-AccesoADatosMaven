package tarea15.bbdd;

import java.util.List;

import tarea15.modelos.Alumno;
import tarea15.modelos.Grupo;

public interface AlumnosDao {

	int insertarAlumno(Alumno alu);

	int insertarGrupo(Grupo grupo);

	List<Alumno> obtenerTodos();

	List<String> obtenerCursos();

	int modificarNombre(int id, String nombre);

	int eliminarAlumno(int id);

	int eliminarPorApellido(String patron);

	int eliminarPorCurso(String curso);

	void exportarFichero();

	void importarFichero();

	void exportarAlumnosJSON();

	void importarAlumnosJSON();
	
	void exportarGruposJSON();


	void importarGruposJSON();
}
