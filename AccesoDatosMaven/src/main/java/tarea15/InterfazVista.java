package tarea15;

import java.util.List;

public interface InterfazVista {
	int mostrarMenu();

	Alumno pedirDatosAlumno();

	int pedirId();

	String pedirNombre();

	String pedirCurso(List<String> cursos);

	void mostrarMensaje(String mensaje);

	void listarAlumnos(List<Alumno> alumnos);
}
