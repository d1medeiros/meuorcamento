package org.meuorcamento.ws;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.meuorcamento.ws.resource.ContaResource;

@ApplicationPath("api")
public class ContaApplication extends Application{
	
	public ContaApplication() {

	}

	 @Override
	    public Set<Class<?>> getClasses()
	    {
	        final Set<Class<?>> classes = new HashSet<>();
	        classes.add(ContaResource.class);
	        
	        
	        return classes;
	    }
}
