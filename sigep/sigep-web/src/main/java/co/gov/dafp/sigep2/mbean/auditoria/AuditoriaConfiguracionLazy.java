package co.gov.dafp.sigep2.mbean.auditoria;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

public class AuditoriaConfiguracionLazy extends LazyDataModel<AuditoriaConfiguracionDTO>{

	private static final long serialVersionUID = -1766767738275149628L;

	public AuditoriaConfiguracionLazy() {
	    try {
			this.setRowCount(AdministracionDelegate.findAuditoriaConfiguracionTotalRows());
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			this.setRowCount(0);
		}
	}
	
	@Override
	public List<AuditoriaConfiguracionDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		try {
			return AdministracionDelegate.findAuditoriaConfiguracion(first, pageSize);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return null;
	}
}
