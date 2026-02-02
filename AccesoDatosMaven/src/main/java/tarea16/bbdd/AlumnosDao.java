package tarea16.bbdd;

import java.util.List;

import tarea16.modelos.Alumno;
import tarea16.modelos.Grupo;

public interface AlumnosDao {

	int insertarAlumno(Alumno alu);

	int insertarGrupo(Grupo grupo);

	List<Alumno> obtenerTodos();

	List<String> obtenerCursos();
	
	List<Alumno> obtenerAlumnosPorGrupo(int idGrupo);

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

	Alumno obtenerAlumnoPorPk(int id);

	List<Grupo> obtenerTodosLosGrupos();

	int actualizarGrupoAlumno(int idAlu, int idGrupo);

	boolean exportarUnicoGrupoJSON(int idGrupo);
}
