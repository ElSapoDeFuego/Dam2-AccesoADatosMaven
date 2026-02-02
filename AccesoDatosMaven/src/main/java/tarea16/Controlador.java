package tarea16;

import java.util.ArrayList;
import java.util.List;

import tarea16.bbdd.AlumnoDaoImplementacion;
import tarea16.bbdd.AlumnosDao;
import tarea16.interfazUsuario.InterfazVista;
import tarea16.interfazUsuario.Vista;
import tarea16.modelos.Alumno;
import tarea16.modelos.Grupo;

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
			case 14:
				// BUsCAR ALUMNO POR GRUPO
				int idGrupo = vista.pedirId();
				List<Alumno> alumnosDelGrupo = modelo.obtenerAlumnosPorGrupo(idGrupo);
				if (alumnosDelGrupo == null || alumnosDelGrupo.isEmpty()) {
					vista.mostrarMensaje("No hay nadie en este grupo o no existe");
				} else {
					vista.listarAlumnos(alumnosDelGrupo);
				}
				break;
			case 15:
				//MOSTRAR ALUMNO POR PK
				List<Alumno> listaCompleta = modelo.obtenerTodos();
				vista.listarAlumnosResumen(listaCompleta);

				int idBuscado = vista.pedirId();

				Alumno elegido = modelo.obtenerAlumnoPorPk(idBuscado);

				if (elegido != null) {
					List<Alumno> listaElegido = new ArrayList<Alumno>();
					listaElegido.add(elegido);
					vista.mostrarMensaje("\n--- DATOS COMPLETOS DEL ALUMNO ---");
					vista.listarAlumnos(listaElegido);
				} else {
					vista.mostrarMensaje("No existe ningún alumno con esa PK");
				}
				break;
			case 16:
				//CAMBIAR GRUPO DE ALUMNO
				List<Alumno> listaCompleta2 = modelo.obtenerTodos();
				vista.listarAlumnosResumen(listaCompleta2);
				vista.mostrarMensaje("Selecciona el alumno que cambia:");
				int idBuscado2 = vista.pedirId();
				List<Grupo> listaGrupos = modelo.obtenerTodosLosGrupos();
				vista.listarGruposResumen(listaGrupos);
				vista.mostrarMensaje("Selecciona el nuevo grupo:");
				int idNuevoGrupo = vista.pedirId();
				if (modelo.actualizarGrupoAlumno(idBuscado2, idNuevoGrupo) > 0) {
					vista.mostrarMensaje("El alumno cambio de grupo con éxito.");
				} else {
					vista.mostrarMensaje("Error al cambiar el grupo. O el alumno no existe o el grupo es invalido.");
				}
				break;
			case 17:
				//EXPORTAR UN GRUPO JSON
				List<Grupo> todosGrupos = modelo.obtenerTodosLosGrupos();
				vista.listarGruposResumen(todosGrupos);
				vista.mostrarMensaje("¿Que grupo quieres exportar?");
				int idGrupoElegido = vista.pedirId();

				if (modelo.exportarUnicoGrupoJSON(idGrupoElegido)) {
					vista.mostrarMensaje("JSON generado correctamente");

				} else {
					vista.mostrarMensaje("No se pudo generar el JSON");
				}
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
