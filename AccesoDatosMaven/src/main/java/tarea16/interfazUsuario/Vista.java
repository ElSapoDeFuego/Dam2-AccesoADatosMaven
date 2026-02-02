package tarea16.interfazUsuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import tarea16.modelos.Alumno;
import tarea16.modelos.Grupo;

public class Vista implements InterfazVista {
	private Scanner sc = new Scanner(System.in);

	@Override
	public int mostrarMenu() {
		System.out.println("\n--- MENU 16---" + "\n1. Insertar alumno" + "\n2. Insertar grupo" + "\n3. Listar alumnos"
				+ "\n4. Exportar a TXT" + "\n5. Importar TXT a BD" + "\n6. Modificar nombre (por ID)"
				+ "\n7. Eliminar alumno (por ID)" + "\n8. Eliminar por curso" + "\n9. Eliminar por apellido"
				+ "\n10. Exportar alumnos JSON" + "\n11. Importar alumnos JSON" + "\n12. Exportar grupos JSON"
				+ "\n13. Importar grupos JSON" + "\n14. Mostrar alumnos segun el grupo (por ID)"
				+ "\n15. Mostrar alumno por PK" + "\n16. Cambiar de grupo a un alumno"
				+ "\n17. Exportar grupo único JSON" + "\n0. Salir");
		System.out.print("Pon un número: ");
		int opcion = sc.nextInt();
		sc.nextLine();
		return opcion;
	}

	@Override
	public Alumno pedirDatosAlumno() {
		System.out.println("\n--- REGISTRO DE NUEVO ALUMNO ---");

		System.out.println("Nombre:");
		String nombre = sc.nextLine();

		System.out.println("Apellidos:");
		String apellidos = sc.nextLine();

		System.out.println("Genero (h/m):");
		char genero = sc.next().toLowerCase().charAt(0);
		sc.nextLine();

		LocalDate fecha = null;
		boolean fechaCorrecta = false;
		while (!fechaCorrecta) {
			System.out.print("Fecha Nacimiento (YYYY-MM-DD): ");
			String fechaStr = sc.nextLine();
			try {
				fecha = LocalDate.parse(fechaStr);
				fechaCorrecta = true;
			} catch (Exception e) {
				System.out.println("Error. Formato inválido. usa YYYY-MM-DD.");
			}
		}

		System.out.print("Email: ");
		String email = sc.nextLine();

		System.out.print("ID del Grupo (numero): ");
		int idGrupo = sc.nextInt();
		sc.nextLine();

		return new Alumno(nombre, apellidos, fecha, email, idGrupo, genero);
	}

	@Override
	public int pedirId() {
		System.out.println("Dime el id:");
		int id = sc.nextInt();
		sc.nextLine();
		return id;
	}

	@Override
	public String pedirNombre() {
		System.out.println("Dame el nombre que quieres que tenga:");
		return sc.nextLine();
	}

	@Override
	public String pedirCurso(List<String> cursos) {
		System.out.println("Cursos disponibles en la BD: " + cursos);
		System.out.print("Escoge el curso: ");
		return sc.nextLine();
	}

	@Override
	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);

	}

	@Override
	public void listarAlumnos(List<Alumno> alumnos) {
		if (alumnos == null || alumnos.isEmpty()) {
			System.out.println("La tabla esta vacía");
		} else {
			for (Alumno a : alumnos) {
				System.out.println("NIA: " + a.getId_alumno() + " | Nombre: " + a.getNombre() + " " + a.getApellidos()
						+ " | Género: " + a.getGenero() + " | Fecha Nac: " + a.getFechaNacimiento() + " | Email: "
						+ a.getEmail() + " | Grupo: " + a.getNombreGrupo() + " (ID: " + a.getGrupo() + ")");
			}
		}
	}

	@Override
	public void listarAlumnosResumen(List<Alumno> alumnos) {
		System.out.println("\n--- LISTA ALUMNOS ---");
		for (Alumno a : alumnos) {
			System.out.println("ID: " + a.getId_alumno() + " | Nombre: " + a.getNombre());
		}
	}

	@Override
	public void listarGruposResumen(List<Grupo> grupos) {
		System.out.println("\n--- LISTA GRUPOS ---");
		if (grupos == null || grupos.isEmpty()) {
			System.out.println("No hay grupos en la BD");
		} else {
			for (Grupo g : grupos) {
				System.out.println("ID: " + g.getId_grupo() + " | Nombre: " + g.getNombre());
			}
		}
	}

	@Override
	public String pedirPatronApellido() {
		System.out.print("Introduce el apellido (o parte) a buscar: ");
		String apellido = sc.nextLine();
		return apellido;
	}

	@Override
	public Grupo pedirDatosGrupo() {
		System.out.println("\n--- DATOS DEL NUEVO GRUPO ---");

		System.out.print("Nombre del grupo (ej: DAM1): ");
		String nombre = sc.nextLine();

		System.out.print("Ciclo (ej: Grado Superior): ");
		String ciclo = sc.nextLine();

		System.out.print("Curso (ej: 2025/26): ");
		String curso = sc.nextLine();

		return new Grupo(nombre, ciclo, curso);
	}
}
