package tarea15;

import java.util.List;

public interface AlumnosDao {

	int insertarAlumno(Alumno alu);

	int insertarGrupo(Grupo grupo);

	List<Alumno> obtenerTodos();

	List<String> obtenerCursos();

	int modificarNombre(int id, String nombre);

	int eliminarAlumno(int id);

	int eliminarPorCurso(String curso);

	void exportarFichero();

	void importarFichero();

	void exportarJSON();

	void importarJSON();
}
