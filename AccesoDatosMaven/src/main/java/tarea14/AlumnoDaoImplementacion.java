package tarea14;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDaoImplementacion implements AlumnoDao {

	private static AlumnoDaoImplementacion instancia;

	static {
		instancia = new AlumnoDaoImplementacion();
	}

	private AlumnoDaoImplementacion() {
	}

	public static AlumnoDaoImplementacion obtenerInstancia() {
		return instancia;
	}

	@Override
	public int aniadir(Alumno alu){
		String sql = """
					INSERT INTO alumnos (nombre, apellidos, fecha_nacimiento, email, grupo)
					VALUES (?, ?, ?, ?, ?);
				""";

		int result;
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setString(1, alu.getNombre());
			pstm.setString(2, alu.getApellidos());
			pstm.setDate(3, Date.valueOf(alu.getFechaNacimiento()));
			pstm.setString(4, alu.getEmail());
			pstm.setInt(5, alu.getGrupo());

			result = pstm.executeUpdate();

			return result;
		}
		catch (SQLException e) {
	        System.out.println("Error al añadir: " + e.getMessage());
	        return -1;
	    }

	}

	@Override
	public Alumno obtenerPorElId(int id) {
		Alumno result = null;
		String sql = """
				SELECT id_alumno, nombre, apellidos, fechaNacimiento, email, grupo
				FROM alumnos
				WHERE id_alumno = ?
				""";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			pstm.setInt(1, id);

			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					result = new Alumno();
					
					result.setId_alumno(rs.getInt("id_alumno"));
					result.setNombre(rs.getString("nombre"));
					result.setApellidos(rs.getString("apellidos"));
					result.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
					result.setEmail(rs.getString("email"));
					result.setGrupo(rs.getInt("grupo"));
				}
				return result;
			}
			catch (SQLException e) {
		        System.out.println("Error al obtener: " + e.getMessage());
		        return null;
		    }

		} catch (SQLException e) {
	        System.out.println("Error al obtener: " + e.getMessage());
	        return null;
	    }
	
	}

	@Override
	public List<Alumno> obtenerTodos() {
		String sql = """
				SELECT id_alumno, nombre, apellidos, fechaNacimiento, email, grupo
				FROM alumnos
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
				result.add(alu);
			}
			return result;
			
		}catch (SQLException e) {
	        System.out.println("Error al obtener: " + e.getMessage());
	        return null;
	    }
		
	}

	@Override
	public int modificar(Alumno alu)  {
		String sql = """
				UPDATE alumno SET
					nombre = ?, apellidos = ?, fechaNacimiento = ?, email = ?, grupo = ? WHERE id_alumno = ?
				""";
		int result;
		
		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);){
			pstm.setString(1, alu.getNombre());
			pstm.setString(2, alu.getApellidos());
			pstm.setDate(3, Date.valueOf(alu.getFechaNacimiento()));
			pstm.setString(4, alu.getEmail());
			pstm.setInt(5, alu.getGrupo());
			
			pstm.setInt(6, alu.getId_alumno());
			result = pstm.executeUpdate();
			return result;
		} 
		catch (SQLException e) {
	        System.out.println("Error al modificar: " + e.getMessage());
	        return -1;
	    }
		
	}

	@Override
	public void borrar(int id) {
		String sql = """
				DELETE FROM alumno WHERE id_alumno = ?
				""";
		try(Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql)) {
			pstm.setInt(1, id);
			pstm.executeUpdate();
			
		} 
		catch (SQLException e) {
	        System.out.println("Error al obtener: " + e.getMessage());
	    }

	}

}
