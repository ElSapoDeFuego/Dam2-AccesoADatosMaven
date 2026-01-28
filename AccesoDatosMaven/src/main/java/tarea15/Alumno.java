package tarea15;

import java.time.LocalDate;
import java.util.Objects;

public class Alumno {
	private int id_alumno;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String email;
	private int grupo;
	private char genero;

	public Alumno() {

	}

	public Alumno(String nombre, String apellidos, LocalDate fechaNacimiento, String email, int grupo, char genero) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.grupo = grupo;
		this.genero = genero;
	}

	public Alumno(int id_alumno, String nombre, String apellidos, LocalDate fechaNacimiento, String email, int grupo,
			char genero) {
		super();
		this.id_alumno = id_alumno;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.email = email;
		this.grupo = grupo;
		this.genero = genero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, email, fechaNacimiento, genero, grupo, id_alumno, nombre);
	}

	@Override
	public String toString() {
		return "Alumno [id_alumno=" + id_alumno + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", fechaNacimiento=" + fechaNacimiento + ", email=" + email + ", grupo=" + grupo + ", genero="
				+ genero + "]";
	}

	public int getId_alumno() {
		return id_alumno;
	}

	public void setId_alumno(int id_alumno) {
		this.id_alumno = id_alumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

}
