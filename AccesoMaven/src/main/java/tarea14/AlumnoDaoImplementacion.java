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
	public int aniadir(Alumno alu) throws SQLException {
		String sql = """
					INSERT INTO alumno (nombre, apellidos, fecha_nacimiento, email, grupo)
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

	}

	@Override
	public Alumno obtenerPorElId(int id) throws SQLException {
		Alumno result = null;
		String sql = """
				SELECT id_alumno, nombre, apellidos, fechaNacimiento, email. grupo
				FROM alumnos
				WHERE id_alumno = ?
				""";
		try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

			pstm.setInt(1, id);

			try (ResultSet rs = pstm.executeQuery()) {
				while (rs.next()) {
					result = new Alumno();
					result = new Alumno();
					result.setId_alumno(rs.getInt("id_alumno"));
					result.setNombre(rs.getString("nombre"));
					result.setApellidos(rs.getString("apellidos"));
					result.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
					result.setEmail(rs.getString("email"));
					result.setGrupo(rs.getInt("grupo"));
				}
			}

		}
		return result;
	}

	@Override
	public List<Alumno> obtenerTodos() throws SQLException {
		String sql = """
				SELECT id_alumno, nombre, apellidos, fechaNacimiento, email. grupo
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
			
		}
		return result;
	}

	@Override
	public int modificar(Alumno alu) throws SQLException {
		String sql = """
				UPDATE alumno SET
					nombre = ?, apellidos = ?, fechaNacimiento = ?, email = ?, grupo = ? WHERE id_empleado = ?
				""";
		int result;
		
		try (Connection conn = MyDataSource.getConnection();
				PreparedStatement pstm = conn.prepareStatement(sql);){
			
			
		} 
		return 0;
	}

	@Override
	public void borrar(int id) throws SQLException {
		// TODO Auto-generated method stub

	}

}
