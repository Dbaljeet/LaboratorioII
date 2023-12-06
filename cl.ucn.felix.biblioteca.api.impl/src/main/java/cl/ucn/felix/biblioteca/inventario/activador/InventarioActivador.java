package cl.ucn.felix.biblioteca.inventario.activador;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


import cl.ucn.felix.biblioteca.api.Inventario;
import cl.ucn.felix.biblioteca.api.impl.InventarioImpl;

public class InventarioActivador implements BundleActivator {

	
	private ServiceRegistration<Inventario> reg = null;
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println(" \nComenzando implementacion de Inventario de Libros ");
		Inventario inventario = new InventarioImpl();
		this.reg = context.registerService(Inventario.class,  inventario , null);
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("\nParando implementacion de Inventario de Libros ");
		if (this.reg!=null) {
			context.ungetService(reg.getReference());
			this.reg = null;
		}		
	}
}
