package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import co.gov.dafp.sigep2.daemon.DaemonDataMig;
import co.gov.dafp.sigep2.scheduler.inicializator.InicializaJobs;
import co.gov.dafp.sigep2.utils.LoggerSigep;

/**
 * @author Jose Viscaya.
 * @version V1.2
 * @Class Server RestFull
 */
public class Server extends Application implements Serializable {

	private static final long serialVersionUID = 3134741714962839122L;
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	/**
	 * 
	 * @throws Exception
	 */
	public Server() throws Exception {
		singletons.add(new ServicesSigepRestLogin());
		singletons.add(new ServicesSigepRestUsuarios());
		singletons.add(new ServicesSigepRestCont());
		singletons.add(new ServicesSigepRestHV());
		singletons.add(new ServicesSigepRestSis());
		singletons.add(new ServicesSigepRestBR());
		singletons.add(new ServicesSigepRestAck());
		singletons.add(new ServicesSigepRestEnt());
		singletons.add(new ServicesSigepRestAdmin());
		singletons.add(new ServicesSigepRestPortal());
		singletons.add(new ServicesSigepRestVin());
		singletons.add(new ServicesSigepRestMovile());
		singletons.add(new ServicesSigepRestGI());
		singletons.add(new ServicesUploadExcel());
		singletons.add(new ServicesSigepRestConciliacion());
		singletons.add(new SendMail());
	
		try{
			singletons.add(new InicializaJobs());
		}catch( Exception z){
			LoggerSigep logger = new LoggerSigep();
			logger.info("Exception add singleton InicializaJobs : "+ new Date(), Server.class);
		}
		singletons.add(new SendSMS());
		DaemonDataMig.starTask();
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
