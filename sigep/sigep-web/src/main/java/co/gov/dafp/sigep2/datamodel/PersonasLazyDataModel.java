package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;

public class PersonasLazyDataModel extends LazyDataModel<PersonaDTO> {

	PersonaDTO persona;
	long entidadID=-1;
	
	public PersonaDTO getPersona() {
		return persona;
	}
	
	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}
	
	public PersonasLazyDataModel(PersonaDTO persona) {
		this.persona=persona;
	}
	
	public PersonasLazyDataModel(PersonaDTO persona,long entidadID) {
		this.persona=persona;
		this.entidadID=entidadID;
	}
	
	@Override
	public List<PersonaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			List<PersonaDTO> listaPersona =null;
			if(persona.getTipoIdentificacionId()!=null) {
				listaPersona = AdministracionDelegate.buscarPersonas(entidadID,persona.getTipoIdentificacionId().getId(), persona.getNumeroIdentificacion(), 
						persona.getPrimerNombre(), persona.getSegundoNombre(), persona.getPrimerApellido(), persona.getSegundoApellido(), first, pageSize, sortField,sortOrder.toString());
			}else {
				listaPersona = AdministracionDelegate.buscarPersonas(entidadID,-1L, "", 
						persona.getPrimerNombre(), persona.getSegundoNombre(), persona.getPrimerApellido(), persona.getSegundoApellido(), first, pageSize,sortField,sortOrder.toString());
			}
			if (listaPersona != null && listaPersona.size() > 0) {
				PersonaDTO r = (PersonaDTO) listaPersona.get(0);
				this.setRowCount(r.getTotal().intValue());
			} else
				this.setRowCount(0);

			return listaPersona;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
