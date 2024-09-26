package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.mbean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class CargoHojaVidaLazyDataModel extends LazyDataModel<CargoHojaVidaExt> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6356449938155248761L;
	private CargoHojaVidaExt cargoHV;
	private List<CargoHojaVidaExt> listaCargosHV;
	/**
	 * @return the cargoHV
	 */
	public CargoHojaVidaExt getCargoHV() {
		return cargoHV;
	}

	/**
	 * @param cargoHV the cargoHV to set
	 */
	public void setCargoHV(CargoHojaVidaExt cargoHV) {
		this.cargoHV = cargoHV;
	}

	/**
	 * @return the listaCargosHV
	 */
	public List<CargoHojaVidaExt> getListaCargosHV() {
		return listaCargosHV;
	}

	/**
	 * @param listaCargosHV the listaCargosHV to set
	 */
	public void setListaCargosHV(List<CargoHojaVidaExt> listaCargosHV) {
		this.listaCargosHV = listaCargosHV;
	}

	public CargoHojaVidaLazyDataModel(CargoHojaVidaExt cargoHV) {
		this.setCargoHV(cargoHV);
	}

	@Override
	public List<CargoHojaVidaExt> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			cargoHV.setFieldName(sortField);
			cargoHV.setAscDesc(sortOrder.toString().equals("ASCENDING")?"ASC":"DESC" );
			cargoHV.setLimitInit(first);
			cargoHV.setLimitEnd(pageSize);
			List<CargoHojaVidaExt> listaCargosHv = ComunicacionServiciosSis.getCargoHVFiltro(cargoHV);
			if (listaCargosHv != null && listaCargosHv.size() > 0) {
				CargoHojaVidaExt r = (CargoHojaVidaExt) listaCargosHv.get(0);
				this.setRowCount(r.getTotal().intValue());
			} else
				this.setRowCount(0);
			return listaCargosHv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}