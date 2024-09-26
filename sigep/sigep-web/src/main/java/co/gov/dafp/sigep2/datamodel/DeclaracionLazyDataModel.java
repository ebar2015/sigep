/**
 * 
 */
package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.entities.Declaracion;
import co.gov.dafp.sigep2.mbean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosBR;

/**
 * @author joseviscaya
 *
 */
public class DeclaracionLazyDataModel extends LazyDataModel<DeclaracionExt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7620198749326488970L;
	
	DeclaracionExt dec;
	
	
	public DeclaracionLazyDataModel(DeclaracionExt dec) {
		this.dec = dec;
	}
	
	@Override
	public List<DeclaracionExt> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		dec.setConfirmacion((short) 1);
		try {
			dec.setFieldName(sortField);
			dec.setAscDesc(sortOrder.toString().equals("ASCENDING")?"ASC":"DESC" );
			List<DeclaracionExt> listDec = ComunicacionServiciosBR.getDeclaracionFiltro(dec, first, pageSize);
			if (listDec != null && listDec.size() > 0) {
				DeclaracionExt r = listDec.get(0);
				this.setRowCount((int) r.getTotal());
			} else
				this.setRowCount(0);
			return listDec;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * @return the dec
	 */
	public Declaracion getDec() {
		return dec;
	}

	/**
	 * @param dec the dec to set
	 */
	public void setDec(DeclaracionExt dec) {
		this.dec = dec;
	}
}