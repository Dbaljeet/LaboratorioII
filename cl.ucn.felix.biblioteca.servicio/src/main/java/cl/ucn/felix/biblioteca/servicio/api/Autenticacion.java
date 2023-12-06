package cl.ucn.felix.biblioteca.servicio.api;

public interface Autenticacion {

	String login(String username, String password) throws ExcepcionCredencialInvalida;
	void logout(String sesion) throws ExcepcionSesionNoValidaTiempoEjecucion;
	boolean sesionEsValida(String sesion);
}
