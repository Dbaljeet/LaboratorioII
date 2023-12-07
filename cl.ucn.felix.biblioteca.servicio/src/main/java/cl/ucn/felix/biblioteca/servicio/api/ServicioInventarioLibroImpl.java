package cl.ucn.felix.biblioteca.servicio.api;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import cl.ucn.felix.biblioteca.api.ExcepcionLibroNoEncontrado;
import cl.ucn.felix.biblioteca.api.Inventario;
import cl.ucn.felix.biblioteca.api.Libro;

public class ServicioInventarioLibroImpl implements ServicioInventarioLibro{

	private String sesion;
	private BundleContext contexto;
	
	
	public ServicioInventarioLibroImpl(BundleContext contexto) {
		
		this.contexto = contexto;
	}

		
	@Override
	public String login(String username, String password) throws ExcepcionCredencialInvalida {
		// TODO Auto-generated method stub
		if ("admin".equals(username) && "admin".equals(password)) {
			
			this.sesion = Long.toString(System.currentTimeMillis());
			return this.sesion;
		}
		throw new ExcepcionCredencialInvalida(username);
	}

	@Override
	public void logout(String sesion) throws ExcepcionSesionNoValidaTiempoEjecucion {
		// TODO Auto-generated method stub
		chequearSesion(sesion);
		this.sesion = null;
	}

	@Override
	public boolean sesionEsValida(String sesion) {
		// TODO Auto-generated method stub
		return this.sesion != null && this.sesion.equals(sesion);
	}
	
	protected void chequearSesion(String sesion) throws ExcepcionSesionNoValidaTiempoEjecucion {
		
		if (!sesionEsValida(sesion)) {
			throw new ExcepcionSesionNoValidaTiempoEjecucion(sesion);
		}
	}

	@Override
	public Set<String> obtenerGrupos(String sesion)  {
		// TODO Auto-generated method stub
		Inventario inventario = buscarLibroEnInventario();
		return inventario.getCategorias();
		
	}

	@Override
	public void adicionarLibro(String sesion, String isbn, String titulo, String autor, String categoria) {
		// TODO Auto-generated method stub
		this.chequearSesion(sesion);
		Inventario inventario;
		try {
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
		}
		LibroMutable newLibro = crearLibro(isbn);
		newLibro.setTitulo(titulo);
		newLibro.setAutor(autor);
		newLibro.setCategoria(categoria);

		String isbn = inventario.guardarLibro(newLibro);
		
	}

	@Override
	public void modificarCategoriaLibro(String sesion, String isbn, String categoria) {
		// TODO Auto-generated method stub
		this.chequearSesion(sesion);
		Inventario inventario;
		try {
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
		}

		LibroMutable libro = inventario.cargarLibro(isbn);

		libro.setCategoria(categoria);
		
	}

	@Override
	public void removerLibro(String sesion, String isbn) {
		// TODO Auto-generated method stub
		this.chequearSesion(sesion);
		Inventario inventario;
		try {
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
		}
		inventario.removerLibro(isbn);
	}

	@Override
	public Libro obtenerLibro(String sesion, String isbn) throws ExcepcionLibroNoEncontrado , ExcepcionSesionNoValidaTiempoEjecucion{
		// TODO Auto-generated method stub
		this.chequearSesion(sesion);
		Inventario inventario = buscarLibroEnInventario();
		return inventario.cargarLibro(isbn);
	}

	@Override
	public Set<String> buscarLibrosPorCategoria(String sesion, String categoriaLike) {
		// TODO Auto-generated method stub
		this.chequearSesion(sesion);
		Inventario inventario;
		try {
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
			return null;
		}

		Map<Inventario.CriterioBusqueda, String> criterio = Map.of(
				Inventario.CriterioBusqueda.CATEGORIA_LIKE, categoriaLike
		);

		return inventario.buscarLibros(criterio);
	}

	@Override
	public Set<String> buscarLibrosPorAutor(String session, String autorLike) {
		// TODO Auto-generated method stub
		Inventario inventario;
		try {
			chequearSesion(sesion);
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
			return null;
		}

		Map<Inventario.CriterioBusqueda, String> criterio = Map.of(
				Inventario.CriterioBusqueda.AUTOR_LIKE, autorLike
		);
		return inventario.buscarLibros(criterio);
	}

	@Override
	public Set<String> buscarLibrosPorTitulo(String sesion, String tituloLike) {
		// TODO Auto-generated method stub
		Inventario inventario;
		try {
			inventario = buscarLibroEnInventario();
		} catch (ExcepcionSesionNoValidaTiempoEjecucion e) {
			e.printStackTrace();
			return null;
		}

		Map<Inventario.CriterioBusqueda, String> criterio = Map.of(
				Inventario.CriterioBusqueda.TITULO_LIKE, tituloLike
		);

		return inventario.buscarLibros(criterio);
	}

	private Inventario buscarLibroEnInventario() throws ExcepcionSesionNoValidaTiempoEjecucion {
		
		String nombre = Inventario.class.getName();
		ServiceReference<?> ref = this.contexto.getServiceReference(nombre);
		if (ref == null) {
			throw new ExcepcionSesionNoValidaTiempoEjecucion(nombre);
		}
		return (Inventario) this.contexto.getService(ref);
 		
	}
	
}
