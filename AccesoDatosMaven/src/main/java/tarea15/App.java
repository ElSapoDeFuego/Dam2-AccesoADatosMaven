package tarea15;

import tarea15.bbdd.AlumnoDaoImplementacion;
import tarea15.interfazUsuario.InterfazVista;
import tarea15.interfazUsuario.Vista;
import tarea15.bbdd.AlumnosDao;

public class App {
	public static void main(String[] args) {
		AlumnosDao modelo = AlumnoDaoImplementacion.obtenerInstancia();
		InterfazVista vista = new Vista();
		Controlador controlador = new Controlador();

		controlador.ejecutar(modelo, vista);
	}

}
