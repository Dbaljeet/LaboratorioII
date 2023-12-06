package cl.ucn.felix.biblioteca.api;

public class ExcepcionLibroInvalido extends Exception {

	private static final long serialVersionUID = 8321472268063571076L;

	public ExcepcionLibroInvalido(String mensaje) {
		super("Libro invalido: " + mensaje);
	}
}
