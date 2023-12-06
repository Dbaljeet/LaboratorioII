package cl.ucn.felix.biblioteca.api;

public interface LibroMutable extends Libro {
	
	void setIsbn(String isbn);
	void setTitulo(String titulo);
	void setAutor(String autor);
	void setCategoria(String categoria);

}
