package cl.ucn.felix.biblioteca.api;

public class ExcepcionLibroExistente extends Exception {

	private static final long serialVersionUID = 5120358203999152018L;

	public ExcepcionLibroExistente(String isbn) {
		super("El libro ya existe en el catalogo: " + isbn);
	}
	
}
