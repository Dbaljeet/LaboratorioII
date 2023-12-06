package cl.ucn.felix.biblioteca.servicios.tui;

import java.util.HashSet;
import java.util.Set;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import cl.ucn.felix.biblioteca.api.ExcepcionLibroExistente;
import cl.ucn.felix.biblioteca.api.ExcepcionLibroInvalido;
import cl.ucn.felix.biblioteca.api.ExcepcionLibroNoEncontrado;
import cl.ucn.felix.biblioteca.api.Libro;
import cl.ucn.felix.biblioteca.servicio.api.ExcepcionCredencialInvalida;
import cl.ucn.felix.biblioteca.servicio.api.ExcepcionSesionNoValidaTiempoEjecucion;
import cl.ucn.felix.biblioteca.servicio.api.ServicioInventarioLibro;

public class ServicioInventarioProxy {

	public static final String AMBITO = "libro";

	public static final String[] FUNCIONES = new String[] {"buscar", "adicionar"};

	private BundleContext contexto;

	public ServicioInventarioProxy(BundleContext contexto)
	{
		this.contexto = contexto;
	}

	@Descriptor("Buscar Libros por Autor, Titulo, or Categoria")
	public Set<Libro> buscar(
			@Descriptor("username") String username,
			@Descriptor("password") String password,
			@Descriptor("busqueda en atributos: autor, titulo, o categoria") String atributo,
			@Descriptor("match like (usar % al principio o final de <like>"+ " para uso de wild-card)") String filter) 
					throws ExcepcionCredencialInvalida
	{

		ServicioInventarioLibro servicio = buscarLibroEnInventario();

		String sesion = servicio.login(username, password);

		Set<String> resultados;
		if ("titulo".equals(atributo))
		{
			resultados = servicio.buscarLibrosPorTitulo(sesion, filter);
		}
		else if ("autor".equals(atributo))
		{
			resultados = servicio.buscarLibrosPorAutor(sesion, filter);

		}
		else if ("categoria".equals(atributo))
		{
			resultados =servicio.buscarLibrosPorCategoria(sesion, filter);
		}
		else
		{
			throw new RuntimeException("Atributo invalido, esperando uno de { 'titulo', "+
					"'autor', 'categria' } se obtuvo '"+atributo+"'");
		}

		return obtenerLibros(sesion, servicio, resultados);
	}

	protected ServicioInventarioLibro buscarLibroEnInventario() {

		ServiceReference<?> referencia = contexto.getServiceReference(ServicioInventarioLibro.class.getName());

		if (referencia == null)
		{
			throw new RuntimeException("ServicioInventarioLibro no esta registrado, no puedo invocar operacion");
		}
		ServicioInventarioLibro servicio = (ServicioInventarioLibro) this.contexto.getService(referencia);
		if (servicio == null)
		{
			throw new RuntimeException("ServicioInventarioLibro no esta registrado, no puedo invocar operacion");
		}
		return servicio;

	}

	private Set<Libro> obtenerLibros(String sesion, ServicioInventarioLibro servicio, Set<String> resultados)
	{
		Set<Libro> libros = new HashSet<Libro>();
		for (String isbn : resultados)
		{
			Libro book;
			try
			{
				book = servicio.obtenerLibro(sesion, isbn);
				libros.add(book);
			}
			catch (ExcepcionLibroNoEncontrado | ExcepcionSesionNoValidaTiempoEjecucion e)
			{
				System.err.println("ISBN " + isbn + " referenciado pero no encontrado");
			}
		}
		return libros;
	}

	@Descriptor("Adicionar libros")
	public String adicionar(@Descriptor("username") String username,
			@Descriptor("password") String password,
			@Descriptor("ISBN") String isbn,
			@Descriptor("Titulo") String titulo,
			@Descriptor("Autor") String autor,
			@Descriptor("Categoria") String categoria)
					throws ExcepcionCredencialInvalida,
					ExcepcionLibroExistente, ExcepcionLibroInvalido
	{
		ServicioInventarioLibro servicio = buscarLibroEnInventario();
		String sesion = servicio.login(username, password);
		servicio.adicionarLibro(sesion, isbn, titulo, autor, categoria);
		return isbn;
	}
	
}
