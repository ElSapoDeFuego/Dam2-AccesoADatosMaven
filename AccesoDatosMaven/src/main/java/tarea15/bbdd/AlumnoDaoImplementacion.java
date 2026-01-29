package tarea15.bbdd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tarea15.modelos.Alumno;
import tarea15.modelos.Grupo;

import org.apache.logging.log4j.LogManager;

public class AlumnoDaoImplementacion implements AlumnosDao {
	private static AlumnoDaoImplementacion instancia;
	private static final Logger logger = LogManager.getLogger(AlumnoDaoImplementacion.class);

	static {
		instancia = new AlumnoDaoImplementacion();
	}

	public AlumnoDaoImplementacion() {
	}

	public static AlumnoDaoImplementacion obtenerInstancia() {
		return instancia;
	}

	@Override
	public int insertarAlumno(Alumno alu) {
		String sql = """
				INSERT INTO alumno (nombre, apellidos, fechaNacimiento, email, grupo, genero)
				VALUES (?, ?, ?, ?, ?, ?);
				""";
		int result;
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, alu.getNombre());
			pstm.setString(2, alu.getApellidos());
			pstm.setDate(3, Date.valueOf(alu.getFechaNacimiento()));
			pstm.setString(4, alu.getEmail());
			pstm.setInt(5, alu.getGrupo());
			pstm.setString(6, String.valueOf(alu.getGenero()));

			result = pstm.executeUpdate();
			logger.info("ALumno insertado con exito: {} {}", alu.getNombre(), alu.getApellidos());
			return result;
		} catch (SQLException e) {
			logger.error("Error al insertar alumno en la BD: {}", e.getMessage());
			return -1;
		}

	}

	@Override
	public int insertarGrupo(Grupo grupo) {
		String sql = "INSERT INTO grupos (nombre, ciclo, curso) VALUES (?, ?, ?)";

		// RETURN_GENERATED_KEYS para pillar el ID
		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			pstm.setString(1, grupo.getNombre());
			pstm.setString(2, grupo.getCiclo());
			pstm.setString(3, grupo.getCurso());

			pstm.executeUpdate();

			// recupero el ID
			try (ResultSet rs = pstm.getGeneratedKeys()) {
				if (rs.next()) {
					int id = rs.getInt(1);
					logger.info("Grupo {} insertado con ID: {}", grupo.getNombre(), id); //
					return id;
				}
			}
			return -1; // Por si acaso

		} catch (SQLException e) {

			logger.error("Error al insertar grupo: {}", e.getMessage());
			return -1;
		}
	}

	@Override
	public List<Alumno> obtenerTodos() {
	
		String sql = """
				SELECT a.id_alumno, a.nombre, a.apellidos, a.fechaNacimiento, a.email, a.grupo, a.genero,
				       g.nombre AS nom_grupo
				FROM alumno a
				LEFT JOIN grupos g ON a.grupo = g.id_grupo
				""";
		List<Alumno> result = new ArrayList<>();

		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			while (rs.next()) {
				Alumno alu = new Alumno();
				alu.setId_alumno(rs.getInt("id_alumno"));
				alu.setNombre(rs.getString("nombre"));
				alu.setApellidos(rs.getString("apellidos"));
				alu.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
				alu.setEmail(rs.getString("email"));
				alu.setGrupo(rs.getInt("grupo")); 
				alu.setGenero(rs.getString("genero").charAt(0));

				alu.setNombreGrupo(rs.getString("nom_grupo"));

				result.add(alu);
			}
			logger.info("Mostrando alumnos con sus grupos.");
			return result;
		} catch (SQLException e) {
			logger.error("Error al obtener alumnos con JOIN: {}", e.getMessage());
			return null;
		}

	}

	@Override
	public List<String> obtenerCursos() {
		// distinct para que muestre solo 1 de primero o de segundo, etc
		String sql = "SELECT DISTINCT curso FROM grupos";
		List<String> cursos = new ArrayList<>();
		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {
			while (rs.next()) {
				cursos.add(rs.getString("curso"));
			}
			return cursos;
		} catch (SQLException e) {
			logger.error("No se pudieron obtener los cursos: {}", e.getMessage());
			return null;
		}
	}

	@Override
	public int modificarNombre(int id, String nombre) {
		String sql = "UPDATE alumno SET nombre = ? WHERE id_alumno = ?";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, nombre);
			pstm.setInt(2, id);
			int affected = pstm.executeUpdate();
			logger.info("Nombre modificado de alumno con  ID: {}", id);
			return affected;
		} catch (SQLException e) {
			logger.error("Error al modificar: {}", e.getMessage());
			return -1;
		}

	}

	@Override
	public int eliminarAlumno(int id) {
		String sql = "DELETE FROM alumno WHERE id_alumno = ?";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, id);
			logger.info("Alumnno con id {} borrado", id);
			return pstm.executeUpdate();
		} catch (SQLException e) {
			logger.error("No se pudo borrar al Alumno con ID {}: {}", id, e.getMessage());
			return -1;
		}

	}

	@Override
	public int eliminarPorCurso(String curso) {
		String sql = "DELETE FROM alumno WHERE grupo IN (SELECT id_grupo FROM grupos WHERE curso = ?)";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, curso);
			int borrados = pstm.executeUpdate();
			logger.info(" {} alumnos borrados del curso {}", borrados, curso);
			return borrados;
		} catch (SQLException e) {
			logger.error("Hubo un error en el borrado: {}", e.getMessage());
			return -1;
		}

	}

	@Override
	public void exportarFichero() {
		String nombreFichero = "alumnos.txt";
		List<Alumno> alumnos = obtenerTodos();
		try (PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero))) {
			for (Alumno a : alumnos) {
				// csv
				pw.println(a.getId_alumno() + ";" + a.getNombre() + ";" + a.getApellidos() + ";" + a.getGenero() + ";"
						+ a.getFechaNacimiento() + ";" + a.getEmail() + ";" + a.getGrupo());
			}
			logger.info("Exportados {} alumnos a {}", alumnos.size(), nombreFichero);
		} catch (java.io.IOException e) {
			logger.error("Error al exportar TXT: {}", e.getMessage());
		}
	}

	@Override
	public void importarFichero() {
		String nombreFichero = "alumnos.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] lineaCSV = linea.split(";");

				Alumno alumnillo = new Alumno(lineaCSV[1], lineaCSV[2], LocalDate.parse(lineaCSV[4]), lineaCSV[5],
						Integer.parseInt(lineaCSV[6]), lineaCSV[3].charAt(0));
				insertarAlumno(alumnillo);
			}
			logger.info("Importación de TXT completada.");
		} catch (Exception e) {
			logger.error("Error al importar desde TXT: {}", e.getMessage());
		}

	}

	@Override
	public int eliminarPorApellido(String patron) {
		String sql = "DELETE FROM alumno WHERE apellidos LIKE ?";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, "%" + patron + "%"); // no olvidar %
			int borrados = pstm.executeUpdate();
			logger.info("Se han borrado {} alumnos con el patrón: {}", borrados, patron);
			return borrados;
		} catch (SQLException e) {
			logger.error("Error al borrar por apellido: {}", e.getMessage());
			return -1;
		}

	}

	@Override
	public void exportarAlumnosJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try (FileWriter fw = new FileWriter("alumnos.json")) {
			List<Alumno> lista = obtenerTodos();
			gson.toJson(lista, fw);
			logger.info("Exportados {} alumnos a JSON con éxito.", lista.size());
		} catch (IOException e) {
			logger.error("Error al exportar JSON de alumnos: {}", e.getMessage());
		}
	}

	@Override
	public void importarAlumnosJSON() {
		Gson gson = new Gson();

		try (FileReader fr = new FileReader("alumnos.json")) {
			Alumno[] alumnos = gson.fromJson(fr, Alumno[].class);

			if (alumnos != null) {
				for (Alumno a : alumnos) {
					insertarAlumno(a);
				}
				logger.info("Importación terminada: {} alumnos metidos en la BD.", alumnos.length); // [cite: 44, 77]
			}
		} catch (Exception e) {
			logger.error("Error al importar JSON a la BD: {}", e.getMessage());
		}

	}

	// lo pongo aqui porque se supone que hay una unica interfaz segun el enunciado
	@Override
	public void exportarGruposJSON() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<Grupo> listaGrupos = new ArrayList<>();
		String sql = "SELECT id_grupo, nombre, ciclo, curso FROM grupos";

		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery()) {

			while (rs.next()) {
				Grupo g = new Grupo();
				g.setId_grupo(rs.getInt("id_grupo"));
				g.setNombre(rs.getString("nombre"));
				g.setCiclo(rs.getString("ciclo"));
				g.setCurso(rs.getString("curso"));
				// Meto a los alumnos dentro del grupo temporal
				g.setAlumnos(obtenerAlumnosDeUnGrupo(g.getId_grupo()));
				listaGrupos.add(g);
			}

			try (FileWriter fw = new FileWriter("grupos_alumnos.json")) {
				gson.toJson(listaGrupos, fw);
				logger.info("Exportación de grupos con alumnos terminada.");
			}
		} catch (SQLException | IOException e) {
			logger.error("Error en el export: {}", e.getMessage());
		}

	}

	@Override
	public void importarGruposJSON() {
		Gson gson = new Gson();
		try (FileReader fr = new FileReader("grupos_alumnos.json")) {
			Grupo[] grupos = gson.fromJson(fr, Grupo[].class);

			if (grupos != null) {
				for (Grupo g : grupos) {
					// insertargrupo devuelve el id
					int idNuevo = insertarGrupo(g);

					// Si el grupo se creoo bien y tiene alumnos dentro
					if (idNuevo > 0 && g.getAlumnos() != null) {
						for (Alumno a : g.getAlumnos()) {
							a.setGrupo(idNuevo);
							insertarAlumno(a);
						}
					}
				}
				logger.info("Importación de grupos y alumnos desde JSON completada.");
			}
		} catch (Exception e) {
			logger.error("Error al importar grupos JSON: {}", e.getMessage());
		}
	}

	// este metodo es solo para el exportar de grupo, que si no queda muy largo ese
	// metodo (que ya lo es un poco)
	private List<Alumno> obtenerAlumnosDeUnGrupo(int idGrupo) {
		List<Alumno> lista = new ArrayList<>();

		String sql = "SELECT id_alumno, nombre, apellidos, fechaNacimiento, email, grupo, genero FROM alumno WHERE grupo = ?";

		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			pstm.setInt(1, idGrupo);
			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					Alumno a = new Alumno();
					a.setId_alumno(rs.getInt("id_alumno"));
					a.setNombre(rs.getString("nombre"));
					a.setApellidos(rs.getString("apellidos"));
					a.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
					a.setEmail(rs.getString("email"));
					a.setGrupo(rs.getInt("grupo"));
					a.setGenero(rs.getString("genero").charAt(0));
					lista.add(a);
				}
			}
		} catch (SQLException e) {
			logger.error("Error al recuperar alumnos del grupo {}: {}", idGrupo, e.getMessage());
		}
		return lista;
	}
}
