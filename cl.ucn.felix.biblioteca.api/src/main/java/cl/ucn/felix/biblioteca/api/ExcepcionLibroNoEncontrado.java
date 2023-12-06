package cl.ucn.felix.biblioteca.api;

public class ExcepcionLibroNoEncontrado extends Exception {

	private static final long serialVersionUID = -5155088717583622072L;

	public ExcepcionLibroNoEncontrado(String isbn) {
        super("Libro no encontrado: " + isbn);
	}
}
