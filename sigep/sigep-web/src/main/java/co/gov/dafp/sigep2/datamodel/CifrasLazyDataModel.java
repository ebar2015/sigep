package co.gov.dafp.sigep2.datamodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ibm.icu.impl.LocaleDisplayNamesImpl.DataTable;

import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.mbean.CifrasEmpleoPublicoBean;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosGI;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.StringUtil;
import co.gov.dafp.sigep2.util.logger.Logger;

public class CifrasLazyDataModel extends LazyDataModel<CifrasEmpleoPublico> {
	private static final long serialVersionUID = -1754112803800935205L;

	transient Logger logger = Logger.getInstance(getClass());

	private CifrasEmpleoPublico cifra;

	private CifrasEmpleoPublico total;

	private boolean resetearBusqueda = false;

	private List<CifrasEmpleoPublico> listaCifras = null;

	public CifrasEmpleoPublico getCifra() {
		return cifra;
	}

	public void setCifra(CifrasEmpleoPublico cifra) {
		this.cifra = cifra;
	}

	public CifrasLazyDataModel(CifrasEmpleoPublico cifra) {
		this.cifra = cifra;
		this.resetearBusqueda = true;
	}

	/**
	 * Carga lo valores <code>lazy</code> de acuerdo a los parametros contenidos
	 * en {@link CifrasLazyDataModel#cifra}
	 * 
	 * @param first
	 *            Indica el numero de la pagina desde la cual se mostraran los
	 *            registros
	 * @param pageSize
	 *            Indica el tamanio de la pagina
	 * @param sortField
	 *            No utilizado
	 * @param sortOrder
	 *            No utilizado
	 * 
	 * @return {@link LinkedList} de {@link CifrasEmpleoPublico} con los
	 *         registros que coinciden segun {@link CifrasLazyDataModel#cifra}
	 */
	@Override
	public List<CifrasEmpleoPublico> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String title = "List<CifrasEmpleoPublicoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)";
		listaCifras = new LinkedList<>();
		total = new CifrasEmpleoPublico();

		CifrasEmpleoPublicoBean cifrasEmpleoPublicoBean = BaseBean.findBean("cifrasEmpleoPublicoBean");
		if (cifrasEmpleoPublicoBean == null) {
			return new LinkedList<>();
		}

		if (resetearBusqueda) {
			cifrasEmpleoPublicoBean.setHabilitarConsulta(true);
		}

		if (!cifrasEmpleoPublicoBean.isHabilitarConsulta()) {
			return new LinkedList<>();
		}

		cifrasEmpleoPublicoBean.setHabilitarConsulta(false);
		try {
			int firstTemp = first;
			if (resetearBusqueda) {
				firstTemp = 0;
				resetearBusqueda = false;
			}
			if (cifra != null) {
				if (!cifra.isAvanceAsesor() && !cifra.isConsolidadoGestion()) {
					if (cifra.getEntidad() == null || cifra.getEntidad().isEmpty()) {
						//cifra.setEntidad("*");
						cifra.setEntidad(null);
					} else if (!cifra.getEntidad().equals("*") && !cifra.getEntidad().contains("|")) {
						cifra.setEntidad(StringUtil.textoInflexivo(cifra.getEntidad().trim()));
					}

					if (cifra.getCodEntidad() == null || cifra.getCodEntidad().isEmpty()) {
						//cifra.setCodEntidad("*");
						cifra.setCodEntidad(null);
					} else if (!cifra.getCodEntidad().equals("*") && !cifra.getCodEntidad().contains("|")) {
						cifra.setCodEntidad("^[0]*(" + cifra.getCodEntidad() + ")$");
					}
				}
				cifra.setLimitIni(firstTemp);
				cifra.setLimitFin(pageSize);
			} else {
				cifra = new CifrasEmpleoPublico();
			}

			listaCifras = ComunicacionServiciosGI.getCifrasEmpleoPublico(cifra);

			if (listaCifras != null && !listaCifras.isEmpty()) {
				cifrasEmpleoPublicoBean.setHabilitarConsulta(true);
				if (!cifra.isConsolidadoGestion()) {
					total = ComunicacionServiciosGI.getTotalCifrasEmpleoPublico(cifra);
					this.setRowCount(total.getTotal().intValue());
				} else {
					this.setRowCount(1);
				}
			} else
				this.setRowCount(0);

			return listaCifras;
		} catch (Exception e) {
			logger.error(title, e);
		}

		return listaCifras;
	}

	/**
	 * Obtiene el registro de {@link CifrasLazyDataModel#listaCifras} de acuerdo
	 * a <code>rowKey</code> quien contiene el identificador del registro desde
	 * el {@link DataTable}
	 */
	@Override
	public CifrasEmpleoPublico getRowData(String rowKey) {
		try {
			CifrasEmpleoPublico cifraSel = new CifrasEmpleoPublico();
			cifraSel.setCodCifrasEmpleoPublico(Long.valueOf(rowKey));
			if (this.listaCifras.contains(cifraSel)) {
				return this.listaCifras.get(this.listaCifras.indexOf(cifraSel));
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}

	public List<CifrasEmpleoPublico> getListaCifras() {
		return listaCifras;
	}

	public void setListaCifras(List<CifrasEmpleoPublico> listaCifras) {
		this.listaCifras = listaCifras;
	}

	/**
	 * Devuelve el registro de totales de la tabla
	 */
	public CifrasEmpleoPublico getTotal() {
		return total;
	}

	public void setTotal(CifrasEmpleoPublico total) {
		this.total = total;
	}
}