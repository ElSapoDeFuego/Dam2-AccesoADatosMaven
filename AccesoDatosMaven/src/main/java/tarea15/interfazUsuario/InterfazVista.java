package tarea15.interfazUsuario;

import java.util.List;

import tarea15.modelos.Alumno;
import tarea15.modelos.Grupo;

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
}
