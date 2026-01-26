package tarea14;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlumnoDaoImplementacionTest {
	private AlumnoDao dao;

	@BeforeEach
	void setUp() {
		dao = AlumnoDaoImplementacion.obtenerInstancia();
	}

	@Test
	void testAniadir() {
		Alumno alu = new Alumno(0, "Alejandro", "Roca", java.time.LocalDate.now(), "alejandro@gmail.com", 1);
		int res;

		res = dao.aniadir(alu);
		assertEquals(1, res, "Error al insertar: no se guardo el alumno");

	}

	@Test
	void testObtenerPorElId() {

		int idExistente = 1;
		Alumno resultado = dao.obtenerPorElId(idExistente);
		assertNotNull(resultado, "El alumno con ID " + idExistente + " no existe");
		assertEquals(idExistente, resultado.getId_alumno(), "El ID no coincide");
		assertNotNull(resultado.getNombre(), "El nombre del alumno es null");

	}

	@Test
	void testObtenerTodos() {
		List<Alumno> lista;

		lista = dao.obtenerTodos();
		assertNotNull(lista, "La lista no puede ser null");
		assertFalse(lista.isEmpty(), "La lista de alumnos está vacia");

	}

	@Test
	void testModificar() {
		Alumno alu;

		alu = dao.obtenerPorElId(1);
		assertNotNull(alu, "No se puede modificar: el alumno 1 no existe");
		alu.setNombre("Alejandro editado");
		int res = dao.modificar(alu);
		assertEquals(1, res, "No se actualizo en la base de datos");

	}

	@Test
	void testBorrar() {

		dao.borrar(2);
		Alumno alu;
		alu = dao.obtenerPorElId(2);
		assertNull(alu, "Error: El alumno sigue existiendo tras el borrado");

	}

}
