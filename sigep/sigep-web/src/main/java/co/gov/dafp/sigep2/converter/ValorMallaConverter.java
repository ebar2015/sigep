package co.gov.dafp.sigep2.converter;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import co.gov.dafp.sigep2.mbean.PlantillaBean;
import co.gov.dafp.sigep2.util.xml.reporte.config.ValorMalla;

@Named("valorMallaConverter")
@ManagedBean
public class ValorMallaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		List<ValorMalla> valoresMallaPlantilla = new LinkedList<>();
		try {
			ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
			PlantillaBean plantillaBean = (PlantillaBean) eLContext.getELResolver().getValue(eLContext, null,
					"plantillaBean");
			valoresMallaPlantilla.addAll(plantillaBean.getLstOrden());
		} catch (Exception e) {
			return null;
		}
		ValorMalla valorMalla = new ValorMalla();
		if (!valoresMallaPlantilla.isEmpty()) {
			valorMalla.setId(BigInteger.valueOf((Long.valueOf(value))));
			try {
				for (ValorMalla malla : valoresMallaPlantilla) {
					if (BigInteger.valueOf((Long.valueOf(value))).equals(malla.getId())) {
						return malla;
					}
				}
			} catch (IndexOutOfBoundsException e) {
				return valorMalla;
			}
		}
		return valorMalla;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof BigInteger) {
			return String.valueOf(value);
		}

		Object id = String.valueOf(((ValorMalla) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
