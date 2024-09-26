package co.gov.dafp.sigep2.sistema.locator;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.interfaces.IServiceRemote;

public class ServiceLocator<T extends IServiceRemote> {
	private static ServiceLocator<IServiceRemote> instance = null;
	private Context context = null;
	private HashMap<String, Object> serviceCache = new HashMap<>();

	@Deprecated
	private ServiceLocator() throws NamingException {
		Properties jndiProps = new Properties();
		jndiProps.put(Context.PROVIDER_URL, ConfigurationBundleConstants.getEJBProviderURL());
		jndiProps.put(Context.URL_PKG_PREFIXES, ConfigurationBundleConstants.getEJBURLPkgPrefixes());
		jndiProps.put("jboss.naming.client.ejb.context", true);
		context = new InitialContext(jndiProps);
		serviceCache = new HashMap<>(5);
	}
	
	private ServiceLocator(boolean remoto)throws NamingException {
		System.out.println("Inicializando ServiceLocator(remoto=true)");
		
		final Hashtable<String, String> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        
		context = new InitialContext(jndiProperties);
		if(context !=null)
			System.out.println("Iniciado ServiceLocator success");
	}

	public synchronized static ServiceLocator<IServiceRemote> getInstance() throws NamingException {
		if (instance == null) {
			instance = new ServiceLocator<>(true);
		}
		return instance;
	}

	public Object getService(String jndiName) throws NamingException {
		if (!serviceCache.containsKey(jndiName)) {
			serviceCache.put(jndiName, context.lookup(jndiName));
		}
		return serviceCache.get(jndiName);
	}
	
}
