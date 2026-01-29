package tarea15.interfazUsuario;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import tarea15.modelos.Alumno;
import tarea15.modelos.Grupo;

public class Vista implements InterfazVista {
	private Scanner sc = new Scanner(System.in);

	@Override
	public int mostrarMenu() {
		System.out.println("Eige una opcion:" + "\n1. Insertar alumno" + "\n2. Listar alumnos (Consola)"
				+ "\n3. Exportar a fichero (Texto/Bin)" + "\n4. Importar fichero a BD"
				+ "\n5. Modificar nombre (por ID)" + "\n6. Eliminar alumno (por ID)" + "\n7. Eliminar por apellido"
				+ "\n8. Exportar a XML/JSON" + "\n9. Importar XML/JSON a BD" + "\n0. Salir");
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
		System.out.println("Dime el id del alumno:");
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
		System.out.print("Escribe el curso para borrar a sus alumnos: ");
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
						+ " | Grupo: " + a.getNombreGrupo() + " (ID: " + a.getGrupo() + ")");
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
