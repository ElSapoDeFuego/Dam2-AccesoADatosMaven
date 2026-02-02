package tarea16.modelos;

import java.util.List;
import java.util.Objects;

public class Grupo {
	private int id_grupo;
	private String nombre;
	private String ciclo;
	private String curso;
	private List<Alumno> alumnos;

	public Grupo() {
		super();
	}

	public Grupo(String nombre, String ciclo, String curso) {
		super();
		this.nombre = nombre;
		this.ciclo = ciclo;
		this.curso = curso;
	}

	public Grupo(int id_grupo, String nombre, String ciclo, String curso) {
		super();
		this.id_grupo = id_grupo;
		this.nombre = nombre;
		this.ciclo = ciclo;
		this.curso = curso;

	}

	// con array de alumnos
	public Grupo(int id_grupo, String nombre, String ciclo, String curso, List<Alumno> alumnos) {
		super();
		this.id_grupo = id_grupo;
		this.nombre = nombre;
		this.ciclo = ciclo;
		this.curso = curso;
		this.alumnos = alumnos;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ciclo, curso, id_grupo, nombre);
	}

	@Override
	public String toString() {
		return "Grupo [id_grupo=" + id_grupo + ", nombre=" + nombre + ", ciclo=" + ciclo + ", curso=" + curso + "]";
	}

	public int getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

}
