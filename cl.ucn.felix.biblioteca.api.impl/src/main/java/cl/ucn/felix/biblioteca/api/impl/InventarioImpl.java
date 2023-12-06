package cl.ucn.felix.biblioteca.api.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cl.ucn.felix.biblioteca.api.ExcepcionLibroInvalido;
import cl.ucn.felix.biblioteca.api.ExcepcionLibroNoEncontrado;
import cl.ucn.felix.biblioteca.api.Inventario;
import cl.ucn.felix.biblioteca.api.Libro;
import cl.ucn.felix.biblioteca.api.LibroMutable;

public class InventarioImpl implements Inventario {

	public static final String CATEGORIA_DEFECTO = "default";

	private Map<String, LibroMutable> libroByIsbn = new HashMap<String, LibroMutable>();
	private Map<String, Integer> categorias =  new HashMap<String, Integer>();


	public LibroMutable crearLibro(String isbn) {

		return new LibroMutableImpl(isbn);

	}
	
	public LibroMutable cargarLibroParaEdicion(String isbn) throws ExcepcionLibroNoEncontrado {

		LibroMutable libro= this.libroByIsbn.get(isbn);
		if (libro == null) {
			throw new ExcepcionLibroNoEncontrado(isbn);
		}
		return libro;
	}


	public Set<String> getCategorias(){

		return this.categorias.keySet();

	}

	public String guardarLibro(LibroMutable libro) throws ExcepcionLibroInvalido {


		String isbn = libro.getIsbn();
		if (isbn == null) {

			throw new ExcepcionLibroInvalido("ISBN no esta seteado");

		}

		this.libroByIsbn.put(isbn, libro);
		String categoria = libro.getCategoria();
		if (categoria == null) {
			categoria = CATEGORIA_DEFECTO;
		}

		if (this.categorias.containsKey(categoria)) {

			int contador = this.categorias.get(categoria);
			this.categorias.put(categoria, contador + 1);
		}
		else {
			this.categorias.put(categoria, 1);
		}

		return isbn;
	}

	public void removerLibro(String isbn) throws ExcepcionLibroNoEncontrado {

		Libro libro = this.libroByIsbn.remove(isbn);
		if (libro == null) {

			throw new ExcepcionLibroNoEncontrado(isbn);
		}

		String categoria = libro.getCategoria();
		int contador = this.categorias.get(categoria);
		if (contador == 1) {
			this.categorias.remove(categoria);
		}
		else
		{
			this.categorias.put(categoria, contador -1);
		}

	}

	public Libro cargarLibro(String isbn) throws ExcepcionLibroNoEncontrado {

		return cargarLibroEditar(isbn);

	}

	public LibroMutable cargarLibroEditar(String isbn) throws ExcepcionLibroNoEncontrado
	{
		LibroMutable libro = this.libroByIsbn.get(isbn);
		if (libro == null) {
			throw new ExcepcionLibroNoEncontrado(isbn);
		}
		return libro;
	}

	public Set<String> buscarLibros(Map<CriterioBusqueda,String> criterio) {

		List<Libro> libros = new LinkedList<Libro>();

		libros.addAll(this.libroByIsbn.values());

		for (Map.Entry<CriterioBusqueda, String> criterios : criterio.entrySet()) {

			Iterator<Libro> it = libros.iterator();
			while (it.hasNext()) {
				Libro libro = it.next();
				switch (criterios.getKey()) {
				case AUTOR_LIKE: 
					if (!checkStringMatch(libro.getAutor(), criterios.getValue()))
					{
						it.remove();
						continue;
					}

					break;

				case ISBN_LIKE:
					if (!checkStringMatch(libro.getIsbn(), criterios.getValue()))
					{
						it.remove();
						continue;
					}

					break;
				case CATEGORIA_LIKE:
					if (!checkStringMatch(libro.getCategoria(), criterios.getValue()))
					{
						it.remove();
						continue;
					}

					break;
				case TITULO_LIKE:
					if (!checkStringMatch(libro.getTitulo(), criterios.getValue()))
					{
						it.remove();
						continue;
					}

					break;
				default:
					break;
				}
			}
		}

		HashSet<String> isbns = new HashSet<String>();
		for (Libro libro : libros) {
			isbns.add(libro.getIsbn());
		}

		return isbns;

	}

	private boolean checkStringMatch(String attr, String critVal)
	{
		if (attr == null) {
			return false;
		}
		attr = attr.toLowerCase();
		critVal = critVal.toLowerCase();
		boolean startsWith = critVal.startsWith("%");
		boolean endsWith = critVal.endsWith("%");
		if (startsWith && endsWith) {
			if (critVal.length()==1) {
				return true;
			}
			else {
				return attr.contains(
						critVal.substring(1, critVal.length() - 1));
			}
		}
		else if (startsWith) { 
			return attr.endsWith(critVal.substring(1));
		}
		else if (endsWith) {
			return attr.startsWith(
					critVal.substring(0, critVal.length() - 1));
		}
		else {
			return attr.equals(critVal);
		}
	}

}
