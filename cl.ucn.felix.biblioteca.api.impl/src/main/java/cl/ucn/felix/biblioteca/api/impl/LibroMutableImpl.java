package cl.ucn.felix.biblioteca.api.impl;

import cl.ucn.felix.biblioteca.api.LibroMutable;


public class LibroMutableImpl implements LibroMutable{


	private String isbn;
	private String autor;
	private String titulo;
	private String categoria;


	public LibroMutableImpl(String isbn) {
		this.setIsbn(isbn);
	}
	
	@Override
	public String getIsbn() {
		// TODO Auto-generated method stub
		return this.isbn;
	}

	@Override
	public String getTitulo() {
		// TODO Auto-generated method stub
		return this.titulo;
	}

	@Override
	public String getAutor() {
		// TODO Auto-generated method stub
		return this.autor;
	}

	@Override
	public String getCategoria() {
		// TODO Auto-generated method stub
		return this.categoria;
	}

	@Override
	public void setIsbn(String isbn) {
		// TODO Auto-generated method stub
		this.isbn = isbn;
	}

	@Override
	public void setTitulo(String titulo) {
		// TODO Auto-generated method stub
		this.titulo = titulo;
	}

	@Override
	public void setAutor(String autor) {
		// TODO Auto-generated method stub
		this.autor = autor;
	}

	@Override
	public void setCategoria(String categoria) {
		// TODO Auto-generated method stub
		this.categoria = categoria;
	}

	public String toString() {

		StringBuffer buf = new StringBuffer();
		buf.append(this.getCategoria()).append(": ");
		buf.append(this.getTitulo()).append(" de ").append(this.getAutor());
		return buf.toString();
	}


}
