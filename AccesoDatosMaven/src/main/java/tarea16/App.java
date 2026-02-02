package tarea16;

import tarea16.bbdd.AlumnoDaoImplementacion;
import tarea16.interfazUsuario.InterfazVista;
import tarea16.interfazUsuario.Vista;
import tarea16.bbdd.AlumnosDao;

public class App {
	public static void main(String[] args) {
		AlumnosDao modelo = AlumnoDaoImplementacion.obtenerInstancia();
		InterfazVista vista = new Vista();
		Controlador controlador = new Controlador();

		controlador.ejecutar(modelo, vista);
	}

}
