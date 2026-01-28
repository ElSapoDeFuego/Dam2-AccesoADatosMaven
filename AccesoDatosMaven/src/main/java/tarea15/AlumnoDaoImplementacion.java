package tarea15;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AlumnoDaoImplementacion implements AlumnosDao {
	private static AlumnoDaoImplementacion instancia;
	private static final Logger logger = LogManager.getLogger(AlumnoDaoImplementacion.class);

	static {
		instancia = new AlumnoDaoImplementacion();
	}

	private AlumnoDaoImplementacion() {
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
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, grupo.getNombre());
			pstm.setString(2, grupo.getCiclo());
			pstm.setString(3, grupo.getCurso());

			int result = pstm.executeUpdate();
			logger.info("Grupo {} insertado,", grupo.getNombre());
			return result;
		} catch (SQLException e) {
			logger.error("Error al insertar grupo: {}", e.getMessage());
			return -1;
		}
	}

	@Override
	public List<Alumno> obtenerTodos() {
		String sql = """
				SELECT id_alumno, nombre, apellidos, fechaNacimiento, email, grupo, genero
				FROM alumno
				""";
		List<Alumno> result = new ArrayList<Alumno>();
		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);
				ResultSet rs = pstm.executeQuery();) {
			Alumno alu;
			while (rs.next()) {
				alu = new Alumno();
				alu.setId_alumno(rs.getInt("id_alumno"));
				alu.setNombre(rs.getString("nombre"));
				alu.setApellidos(rs.getString("apellidos"));
				alu.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
				alu.setEmail(rs.getString("email"));
				alu.setGrupo(rs.getInt("grupo"));
				alu.setGenero(rs.getString("genero").charAt(0));
				result.add(alu);
			}
			logger.info("Mostrando alumnos, se encontraron {} alumnos :", result.size());
			return result;
		} catch (SQLException e) {
			logger.error("Error al obtener alumnos {}", e.getMessage());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void importarFichero() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exportarJSON() {
		// TODO Auto-generated method stub

	}

	@Override
	public void importarJSON() {
		// TODO Auto-generated method stub

	}
}
