/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.converter;

import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Sergio
 */
@FacesConverter(value = "entidadBdConverter")
public class EntidadBDConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        try {
            Long codEnt = Long.parseLong(string);
            return ComunicacionServiciosEnt.getEntidadPorId(codEnt);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null || !(o instanceof Entidad) || ((Entidad) o).getCodEntidad() == null ) {
            return null;
        }
        return "" + ((Entidad) o).getCodEntidad().intValue();
    }

}
