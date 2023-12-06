package cl.ucn.felix.biblioteca.api;

import java.util.Map;
import java.util.Set;

public interface Inventario {

	enum CriterioBusqueda
	{
		ISBN_LIKE,
		TITULO_LIKE,
		AUTOR_LIKE,
		CATEGORIA_LIKE
	}
	
	Set<String> getCategorias();
	
	LibroMutable crearLibro(String isbn) throws ExcepcionLibroExistente;
	
	LibroMutable cargarLibroParaEdicion(String isbn) throws ExcepcionLibroNoEncontrado;
	
	String guardarLibro(LibroMutable isbn) throws ExcepcionLibroInvalido;
	
	Libro cargarLibro(String isbn) throws ExcepcionLibroNoEncontrado;
	
	void removerLibro(String isbn) throws ExcepcionLibroNoEncontrado;
	
	Set<String> buscarLibros( Map<CriterioBusqueda, String> criterio);
}
