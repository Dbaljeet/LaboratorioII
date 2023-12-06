package cl.ucn.felix.biblioteca.servicio.api;

import java.util.Set;

import cl.ucn.felix.biblioteca.api.ExcepcionLibroExistente;
import cl.ucn.felix.biblioteca.api.ExcepcionLibroInvalido;
import cl.ucn.felix.biblioteca.api.ExcepcionLibroNoEncontrado;
import cl.ucn.felix.biblioteca.api.Libro;

public interface ServicioInventarioLibro extends Autenticacion{

	Set<String> obtenerGrupos(String sesion);
	void adicionarLibro(String sesion, String isbn, String titulo, String autor, String categoria) throws ExcepcionLibroExistente, ExcepcionLibroInvalido;
	void modificarCategoriaLibro(String sesion, String isbn, String categoria) throws ExcepcionLibroNoEncontrado, ExcepcionLibroInvalido;
	void removerLibro(String sesion, String isbn) throws ExcepcionLibroNoEncontrado;
	Libro obtenerLibro(String sesion, String isbn) throws ExcepcionLibroNoEncontrado, ExcepcionSesionNoValidaTiempoEjecucion;
	Set<String> buscarLibrosPorCategoria(String sesion, String categoriaLike);
	Set<String> buscarLibrosPorAutor(String session, String autorLike);
	Set<String> buscarLibrosPorTitulo(String sesion, String tituloLike);
	
}
