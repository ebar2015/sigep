package co.gov.dafp.sigep2.sistema.locator;

import java.util.ArrayList;
import java.util.List;

import co.gov.dafp.sigep2.interfaces.IServiceRemote;
import co.gov.dafp.sigep2.util.logger.Logger;

public class Cache<T extends IServiceRemote> {
	private Logger logger = Logger.getInstance(getClass());
	private List<T> services;

	public Cache() {
		services = new ArrayList<>();
	}

	public T getService(String serviceName) {
		for (T service : services) {
			if (serviceName.equalsIgnoreCase(service.getClass().getName())) {
				logger.info("", "Returning cached  " + serviceName + " object");
				return service;
			}
		}
		return null;
	}

	public void addService(T newService) {
		if (newService instanceof IServiceRemote) {
			boolean exists = false;
			for (T service : services) {
				if (newService.getClass().getName().equalsIgnoreCase(service.getClass().getName())) {
					exists = true;
				}
			}
			if (!exists) {
				services.add(newService);
			}
		}
	}
}
