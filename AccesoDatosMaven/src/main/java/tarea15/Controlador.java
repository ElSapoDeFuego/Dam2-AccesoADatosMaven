package tarea15;

import java.util.List;

import tarea15.bbdd.AlumnoDaoImplementacion;
import tarea15.bbdd.AlumnosDao;
import tarea15.interfazUsuario.InterfazVista;
import tarea15.interfazUsuario.Vista;
import tarea15.modelos.Alumno;
import tarea15.modelos.Grupo;

public class Controlador {

	public void ejecutar(AlumnosDao modelo, InterfazVista vista) {
		int opcion;
		do {
			opcion = vista.mostrarMenu();
			switch (opcion) {
			case 1: // INSERTAR ALUMNO
				Alumno alu = vista.pedirDatosAlumno();
				int resultado = modelo.insertarAlumno(alu);

				if (resultado > 0) {
					vista.mostrarMensaje("Alumno insertado con éxito!");
				} else {
					vista.mostrarMensaje("Error al insertar. Intentelo de nuevo");
				}
				break;
			case 2:// INSERTAR GRUPO
				Grupo grupo = vista.pedirDatosGrupo();
				if (modelo.insertarGrupo(grupo) > 0)
					vista.mostrarMensaje("Grupo creado con éxito");
				else
					vista.mostrarMensaje("No se pudo crear el grupo.");
				break;

			case 3: // LISTAR ALUMNOS
				List<Alumno> lista = modelo.obtenerTodos();
				if (lista != null) {
					vista.listarAlumnos(lista);
				} else {
					vista.mostrarMensaje("No se han podido cargar los alumnos.");
				}

				break;
			case 4:
//				 EXPORTAR TXT
				modelo.exportarFichero();
				vista.mostrarMensaje("Exportado a alumnos.txt");
				break;
			case 5:
//				// IMPORTAR TXT A BD
				modelo.importarFichero();
				vista.mostrarMensaje("Datos del TXT volcados en la BD.");
				break;
			case 6:
				// MODIFICAR NOMBRE POR PK
				int idMod = vista.pedirId();
				String nom = vista.pedirNombre();
				if (modelo.modificarNombre(idMod, nom) > 0)
					vista.mostrarMensaje("Nombre cambiado!");
				break;
			case 7:
				// ELIMINAR POR PK
				if (modelo.eliminarAlumno(vista.pedirId()) > 0)
					vista.mostrarMensaje("Alumno borrado.");
				break;
			case 8:
				// ELIMINAR POR CURSO
				List<String> cursos = modelo.obtenerCursos();
				String curso = vista.pedirCurso(cursos);
				int n = modelo.eliminarPorCurso(curso);
				vista.mostrarMensaje("Se han eliminado a " + n + " alumnos.");
				break;
			case 9:
				// ELIMINAR POR APELLIDO
				String patron = vista.pedirPatronApellido();
				int borrados = modelo.eliminarPorApellido(patron);
				if (borrados > 0)
					vista.mostrarMensaje("Se han borrado " + borrados + " alumnos por apellido.");
				else
					vista.mostrarMensaje("Nadie tiene ese apellido, o ese trozo de apellido");
				break;
			case 10:
				// EXPORTAR ALUMNOS JSON
				modelo.exportarAlumnosJSON();
				vista.mostrarMensaje("Alumnos exportados a JSON");
				break;
			case 11:
				// IMPORTAR ALUMNOS JSON
				modelo.importarAlumnosJSON();
				vista.mostrarMensaje("Alumnos JSON importados a la BD.");
				break;
			case 12:
				// EXPORTAR GRUPOS JSON
				modelo.exportarGruposJSON();
				vista.mostrarMensaje("Grupos (con sus alumnos) exportados a JSON.");
				break;
			case 13:
				// IMPORTAR GRUPOS JSON
				modelo.importarGruposJSON();
				
				vista.mostrarMensaje("Grupos json importados a la BD.");
				break;

			case 0:
				vista.mostrarMensaje("Saliendo...");
				break;
			default:
				vista.mostrarMensaje("Elige una opcion válida");
				break;
			}
		} while (opcion != 0);

	}
}
