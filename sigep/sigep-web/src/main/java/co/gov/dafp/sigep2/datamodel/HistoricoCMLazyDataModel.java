package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.entities.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.mbean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class HistoricoCMLazyDataModel extends LazyDataModel<ProcesosCargaMasivaExt> {
	private static final long serialVersionUID = -3189386520796667904L;
	private ProcesosCargaMasivaExt historicoCM;
	private List<ProcesosCargaMasivaExt> listaHistoricoCM;

	public ProcesosCargaMasiva getHistoricoCM() {
		return historicoCM;
	}

	public void setHistoricoCM(ProcesosCargaMasivaExt historicoCM) {
		this.historicoCM = historicoCM;
	}

	public HistoricoCMLazyDataModel(ProcesosCargaMasivaExt historicoCM) {
		this.setHistoricoCM(historicoCM);
	}

	@Override
	public List<ProcesosCargaMasivaExt> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			historicoCM.setFieldName(sortField);
			historicoCM.setAscDesc(sortOrder.toString().equals("ASCENDING")?"ASC":"DESC" );
			List<ProcesosCargaMasivaExt> listaHistoricoCM = ComunicacionServiciosSis.getProcesosCargaMasivaCompleto(historicoCM, first, pageSize);
			if (listaHistoricoCM != null && listaHistoricoCM.size() > 0) {
				ProcesosCargaMasivaExt r = (ProcesosCargaMasivaExt) listaHistoricoCM.get(0);
				this.setRowCount(r.getTotal().intValue());
			} else
				this.setRowCount(0);
			return listaHistoricoCM;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<ProcesosCargaMasivaExt> getListaHistoricoCM() {
		return this.listaHistoricoCM = listaHistoricoCM;
	}
}