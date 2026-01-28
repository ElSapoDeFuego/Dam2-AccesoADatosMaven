
package tarea15;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Menu {
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		iniciarMenu();
	}

	static public void iniciarMenu() {
		int opcion = 0;
		do {
			mostrarMenuTexto();
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				insertarAlumno();
				break;
			case 2:
				mostrarTodosAlumnos();
				break;
			case 3:
				exportarFichero();
				break;
			case 4:
				importarFichero();
				break;
			case 5:
				modificarNombre();
				break;
			case 6:
				eliminarAlumno();
				break;
			case 7:
				eliminarPorApellido();
				break;
			case 8:
				exportarJSON();
				break;
			case 9:
				importarJson();
				break;
			}
		} while (opcion != 0);

	}

	 public void mostrarMenuTexto() {
		System.out.println("Eige una opcion:" + "\n1. Insertar alumno" + "\n2. Listar alumnos (Consola)"
				+ "\n3. Exportar a fichero (Texto/Bin)" + "\n4. Importar fichero a BD"
				+ "\n5. Modificar nombre (por ID)" + "\n6. Eliminar alumno (por ID)" + "\n7. Eliminar por apellido"
				+ "\n8. Exportar a XML/JSON" + "\n9. Importar XML/JSON a BD" + "\n0. Salir");
		System.out.print("Pon un número: ");
	}

	public  void insertarAlumno() {
		System.out.println("Dame su nia (int):");

		int nia = sc.nextInt();
		System.out.println("Nombre:");
		sc.nextLine();
		String nombre = sc.nextLine();

		System.out.println("Apellidos:");
		String apellidos = sc.nextLine();
		System.out.println("Genero (h/m):");
		char genero = sc.next().toLowerCase().charAt(0);
		sc.nextLine();
		System.out.print("Fecha Nacimiento (YYYY-MM-DD): ");
		String fechaStr = sc.nextLine();
		Date fecha_nacimiento = Date.valueOf(fechaStr);

		System.out.print("Ciclo: ");
		String ciclo = sc.nextLine();

		System.out.print("Curso: ");
		String curso = sc.nextLine();

		System.out.print("Grupo: ");
		String grupo = sc.nextLine();
		String sql = "INSERT INTO alumno (nia, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, grupo) "
				+ "VALUES (" + nia + " , '" + nombre + "', '" + apellidos + "', '" + genero + "', '" + fecha_nacimiento
				+ "', '" + ciclo + "', '" + curso + "', '" + grupo + "')";

		try {
			Connection conn = Conexion.conectar();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Insertado");
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  void mostrarTodosAlumnos() {
		String sql = "SELECT * FROM alumno";
		try {
			Connection conn = Conexion.conectar();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			boolean hayAlguien = false;

			while (rs.next()) {
				hayAlguien = true;
				int nia = rs.getInt("nia");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String genero = rs.getString("genero");
				Date fecha_nacimiento = rs.getDate("fecha_nacimiento");
				String ciclo = rs.getString("ciclo");
				String curso = rs.getString("curso");
				String grupo = rs.getString("grupo");

				System.out.println("nia: " + nia + ", nombre: " + nombre + ", apellidos " + apellidos + ", genero: "
						+ genero + ", fecha nacimiento: " + fecha_nacimiento + ", ciclo: " + ciclo + ", curso: " + curso
						+ ", grupo: " + grupo + "");

			}
			if (!hayAlguien) {
				System.out.println("La tabla esta vacía");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	 public void exportarFichero() {
		String nombrearchivo = "alumnos.txt";
		String sql = "Select * from alumno";
		try (Connection conn = Conexion.conectar(); PrintWriter pw = new PrintWriter(new FileWriter(nombrearchivo));) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int contador = 0;
			while (rs.next()) {
				int nia = rs.getInt("nia");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String genero = rs.getString("genero");
				Date fecha = rs.getDate("fecha_nacimiento");
				String ciclo = rs.getString("ciclo");
				String curso = rs.getString("curso");
				String grupo = rs.getString("grupo");
				String linea = nia + ";" + nombre + ";" + apellidos + ";" + genero + ";" + fecha + ";" + ciclo + ";"
						+ curso + ";" + grupo;
				pw.println(linea);
				contador++;
			}
			System.out.println("Se guardaron " + contador + " alumnos en " + nombrearchivo + ", que bien");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 public void importarFichero() {
		String archivo = "alumnos.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(archivo));
				Connection conn = Conexion.conectar();
				Statement stmt = conn.createStatement();) {
			String linea;
			int cont = 0;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(";");
				if (datos.length < 8) {
					continue;
				}
				int nia = Integer.parseInt(datos[0]);
				String nombre = datos[1];
				String apellidos = datos[2];
				String genero = datos[3];
				String fecha_nacimiento = datos[4];
				String ciclo = datos[5];
				String curso = datos[6];
				String grupo = datos[7];

				String sql = "INSERT INTO alumno (nia, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, grupo) "
						+ "VALUES (" + nia + " , '" + nombre + "', '" + apellidos + "', '" + genero + "', '"
						+ fecha_nacimiento + "', '" + ciclo + "', '" + curso + "', '" + grupo + "')";
				stmt.executeUpdate(sql);
				cont++;
				System.out.println("Insertado " + nombre);
			}

			System.out.println("se agregaron " + cont);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void modificarNombre() {
		System.out.println("Dame su nia");
		int nia = sc.nextInt();
		sc.nextLine();
		System.out.println("Dame el nombre que quieres que tenga");
		String nuevonombre = sc.nextLine();
		String sql = "Update alumno Set nombre = '" + nuevonombre + "' where nia = " + nia;
		try (Connection conn = Conexion.conectar(); Statement stmt = conn.createStatement()) {
			int filasQueAfecta = stmt.executeUpdate(sql);
			if (filasQueAfecta > 0) {
				System.out.println("Cambio hecho");
			} else {
				System.out.println("no exite alumno con el nia" + nia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void eliminarAlumno() {
		sc.nextLine();
		System.out.println("Dime el nia del alumno para borrarlo");
		int nia = sc.nextInt();
		sc.nextLine();
		String sql = "Delete from alumno where nia = " + nia;
		try (Connection conn = Conexion.conectar(); Statement stmt = conn.createStatement();) {
			int filasborradas = stmt.executeUpdate(sql);
			if (filasborradas > 0) {
				System.out.println("Alumno con nia " + nia + " borrado");
			} else {
				System.out.println("No existe alumno con ese nia");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  void eliminarPorApellido() {
		System.out.println("Escribe lo que tenga que contener el apellido para borralo");
		sc.nextLine();
		String trozo = sc.nextLine();
		String sql = "Delete from alumno where apellidos like '%" + trozo + "%'";
		try (Connection conn = Conexion.conectar(); Statement stmt = conn.createStatement();) {
			int muertos = stmt.executeUpdate(sql);
			if (muertos > 0) {
				System.out.println("Se eliminaron " + muertos + " alumnos");
			} else {
				System.out.println("Nadie tiene ese texto dentro de su apellido");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void exportarJSON() {
		String sql = "Select * from alumno";
		String archivo = "alumnos.json";
		List<Alumno> alumnillos = new ArrayList<>();
		try (Connection conn = Conexion.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				int nia = rs.getInt("nia");
				String nombre = rs.getString("nombre");
				String apellidos = rs.getString("apellidos");
				String generoStr = rs.getString("genero");
				char genero = generoStr.charAt(0);
				String fecha = rs.getDate("fecha_nacimiento").toString();
				String ciclo = rs.getString("ciclo");
				String curso = rs.getString("curso");
				String grupo = rs.getString("grupo");
				Alumno a = new Alumno(nia, nombre, apellidos, genero, fecha, ciclo, curso, grupo);
				alumnillos.add(a);
			}
			Gson gson = new Gson();
			try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
				gson.toJson(alumnillos, pw);
				System.out.println("Json creado");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public  void importarJson() {
		String archivo = "alumnos.json";
		try (BufferedReader br = new BufferedReader(new FileReader(archivo));
				Connection conn = Conexion.conectar();
				Statement stmt = conn.createStatement();) {
			Gson gson = new Gson();
			Alumno[] alumnillos = gson.fromJson(br, Alumno[].class);
			int cont = 0;

			if (alumnillos != null) {
				for (Alumno a : alumnillos) {
					String sql = "INSERT INTO alumno (nia, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, grupo) "
							+ "VALUES (" + a.getNia() + " , '" + a.getNombre() + "', '" + a.getApellidos() + "', '"
							+ a.getGenero() + "', '" + a.getFecha() + "', '" + a.getCiclo() + "', '" + a.getCurso()
							+ "', '" + a.getGrupo() + "')";
					stmt.executeUpdate(sql);
					cont++;
					System.out.println("Insertado " + a.getNombre());
				}
			}
			System.out.println("se importaton " + cont + " alumnos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
