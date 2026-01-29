package tarea15;

import java.util.List;

public class Controlador {
	public static void main(String[] args) {
		AlumnosDao modelo = new AlumnoDaoImplementacion();
		InterfazVista vista = new Vista();
		new Controlador().ejecutar(modelo, vista);
	}

	public void ejecutar(AlumnosDao modelo, InterfazVista vista) {
		int opcion;
		do {
			opcion = vista.mostrarMenu();
			switch (opcion) {
			case 1:
				// insertarAlumno();
				Alumno alu = vista.pedirDatosAlumno();
				int resultado = modelo.insertarAlumno(alu);

				// Decidimos qué decir al usuaario
				if (resultado > 0) {
					vista.mostrarMensaje("Alumno insertado con éxito!");
				} else {
					vista.mostrarMensaje("Error al insertar. Intentelo de nuevo");
				}
				break;
			case 2:
//				mostrarTodosAlumnos();
				List<Alumno> lista = modelo.obtenerTodos();
				if (lista != null) {
					vista.listarAlumnos(lista);
				} else {
					vista.mostrarMensaje("No se han podido cargar los alumnos.");
				}
				
				break;
			case 3:
//				exportarFichero();
				break;
			case 4:
//				importarFichero();
				break;
			case 5:
//				modificarNombre();
				break;
			case 6:
//				eliminarAlumno();
				break;
			case 7:
//				eliminarPorApellido();
				break;
			case 8:
//				exportarJSON();
				break;
			case 9:
//				importarJson();
				break;
			}
		} while (opcion != 0);

	}
}
