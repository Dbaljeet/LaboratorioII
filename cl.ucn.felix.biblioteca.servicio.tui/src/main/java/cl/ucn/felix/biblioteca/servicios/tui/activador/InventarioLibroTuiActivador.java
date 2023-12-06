package cl.ucn.felix.biblioteca.servicios.tui.activador;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cl.ucn.felix.biblioteca.servicios.tui.ServicioInventarioProxy;

public class InventarioLibroTuiActivador implements BundleActivator {

	@Override
	public void start(BundleContext contexto) throws Exception {
		// TODO Auto-generated method stub
		Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put("osgi.command.scope", cl.ucn.felix.biblioteca.servicios.tui.ServicioInventarioProxy.AMBITO);
		props.put("osgi.command.function",cl.ucn.felix.biblioteca.servicios.tui.ServicioInventarioProxy.FUNCIONES);
		ServicioInventarioProxy servicioInventatrioProxy = new ServicioInventarioProxy(contexto);
		contexto.registerService(ServicioInventarioProxy.class.getName(),servicioInventatrioProxy, props);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
	
}
