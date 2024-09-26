package co.gov.dafp.sigep2.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.mbean.ext.AuditoriaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class AuditoriaLazyDataModel extends LazyDataModel<AuditoriaExt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2639810002406199991L;

	AuditoriaExt auditoria;
	
	boolean doLoad=false;
	
	public AuditoriaExt getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(AuditoriaExt auditoria) {
		this.auditoria = auditoria;
	}

	public AuditoriaLazyDataModel(AuditoriaExt auditoriaExt, boolean doLoad) {
		this.auditoria=auditoriaExt;
		this.doLoad=doLoad;
	}
	
	public AuditoriaLazyDataModel() {
		
	}
	
	@Override
	public List<AuditoriaExt> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		
		if(doLoad) {
				auditoria.setNombreTabla(auditoria.getNombreTabla().replace("TABLA", "").trim());
				auditoria.setLimitInit(first);
				auditoria.setLimitEnd(pageSize);
				List<AuditoriaExt> listAud=ComunicacionServiciosSis.getAuditoriaFechas(auditoria);
				if(listAud != null && listAud.size()>0) {
					AuditoriaExt ad=listAud.get(0);
					this.setRowCount(ad.getTotal());
					return listAud;
				}
				this.setRowCount(0);
				return listAud;
		}
		
		return new ArrayList<AuditoriaExt>();
		
	}
}
