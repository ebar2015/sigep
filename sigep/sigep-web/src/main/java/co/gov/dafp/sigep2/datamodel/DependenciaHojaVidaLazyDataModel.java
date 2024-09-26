package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.entities.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class DependenciaHojaVidaLazyDataModel extends LazyDataModel<DependenciaHojaVidaExt> {
	
	private static final long serialVersionUID = -2840006338284664700L;
	
	private DependenciaHojaVidaExt dependenciaHV;
	private List<DependenciaHojaVidaExt> listaDependenciaHV;

	

	/**
	 * @return the dependenciaHV
	 */
	public DependenciaHojaVidaExt getDependenciaHV() {
		return dependenciaHV;
	}

	/**
	 * @param dependenciaHV the dependenciaHV to set
	 */
	public void setDependenciaHV(DependenciaHojaVidaExt dependenciaHV) {
		this.dependenciaHV = dependenciaHV;
	}

	/**
	 * @return the listaDependenciaHV
	 */
	public List<DependenciaHojaVidaExt> getListaDependenciaHV() {
		return listaDependenciaHV;
	}

	/**
	 * @param listaDependenciaHV the listaDependenciaHV to set
	 */
	public void setListaDependenciaHV(List<DependenciaHojaVidaExt> listaDependenciaHV) {
		this.listaDependenciaHV = listaDependenciaHV;
	}


	public DependenciaHojaVidaLazyDataModel(DependenciaHojaVidaExt dependenciaHV) {
		this.setDependenciaHV(dependenciaHV);
	}


	@Override
	public List<DependenciaHojaVidaExt> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			dependenciaHV.setFieldName(sortField);
			dependenciaHV.setAscDesc(sortOrder.toString().equals("ASCENDING")?"ASC":"DESC" );
			dependenciaHV.setLimitInit(first);
			dependenciaHV.setLimitEnd(pageSize);
			List<DependenciaHojaVidaExt> listaDependenciaHv = ComunicacionServiciosSis.getDependenciaHVFiltro(dependenciaHV);
			if (listaDependenciaHv != null && listaDependenciaHv.size() > 0) {
				DependenciaHojaVidaExt r = (DependenciaHojaVidaExt) listaDependenciaHv.get(0);
				this.setRowCount(r.getTotal().intValue());
			} else
				this.setRowCount(0);
			return listaDependenciaHv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}