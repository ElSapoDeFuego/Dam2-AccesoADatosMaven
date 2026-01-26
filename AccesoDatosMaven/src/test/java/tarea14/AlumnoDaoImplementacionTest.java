package tarea14;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
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
		Alumno alu = new Alumno(0, "Alejandro", "Perez", java.time.LocalDate.now(), "alejandro@gmail.com", 1);
		int res;

		res = dao.aniadir(alu);
		assertEquals(1, res, "Se inserta 1 fila");

	}

	@Test
	void testObtenerPorElId() {

		int idExistente = 1;
		Alumno resultado = dao.obtenerPorElId(idExistente);
		assertNotNull(resultado, "El alumno con ID " + idExistente + " debería existir");
		assertEquals(idExistente, resultado.getId_alumno(), "El ID no coincide");
		assertNotNull(resultado.getNombre(), "El nombre no puede ser null");

	}

	@Test
	void testObtenerTodos() {
		List<Alumno> lista;

		lista = dao.obtenerTodos();
		assertNotNull(lista, "La lista no puede ser null");
		assertFalse(lista.isEmpty(), "Debe haber al menos un alumno");

	}

	@Test
	void testModificar() {
		Alumno alu;

		alu = dao.obtenerPorElId(1);
		assertNotNull(alu, "El alumno 1 debería existir para la prueba");
		alu.setNombre("Editado Pro");
		int res = dao.modificar(alu);
		assertEquals(1, res, "El update debe afectar a 1 fila");

	}

	@Test
	void testBorrar() {

		dao.borrar(2);
		Alumno alu;
		alu = dao.obtenerPorElId(2);
		assertNull(alu, "Si esta borrado,, tiene que dar null");

	}

}
