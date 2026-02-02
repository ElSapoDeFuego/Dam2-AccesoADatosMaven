package tarea16.interfazUsuario;

import java.util.List;

import tarea16.modelos.Alumno;
import tarea16.modelos.Grupo;

public interface InterfazVista {
	int mostrarMenu();

	Alumno pedirDatosAlumno();

	int pedirId();

	String pedirNombre();

	String pedirCurso(List<String> cursos);

	void mostrarMensaje(String mensaje);

	void listarAlumnos(List<Alumno> alumnos);

	String pedirPatronApellido();

	Grupo pedirDatosGrupo();

	void listarAlumnosResumen(List<Alumno> alumnos);

	void listarGruposResumen(List<Grupo> grupos);
}
