package co.gov.dafp.sigep2.mbean.hojadevida;

import java.util.List;
import java.util.Map;

import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;

public class LazyModelHV extends LazyDataModel<PersonaDTO> {
	private static final long serialVersionUID = -3189386520796667904L;
	private PersonaDTO persona, personaR;
	private UsuarioDTO usuario;
	private long idEntidadSesion;
	private String codTipoAsociacion;

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public String getCodTipoAsociacion() {
		return codTipoAsociacion;
	}

	public void setCodTipoAsociacion(String codTipoAsociacion) {
		this.codTipoAsociacion = codTipoAsociacion;
	}

	public PersonaDTO getPersonaR() {
		return personaR;
	}

	public void setPersonaR(PersonaDTO personaR) {
		this.personaR = personaR;
	}

	public long getIdEntidadSesion() {
		return idEntidadSesion;
	}

	public void setIdEntidadSesion(long idEntidadSesion) {
		this.idEntidadSesion = idEntidadSesion;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {

		this.persona = persona;
	}

	public LazyModelHV(PersonaDTO persona, UsuarioDTO usuario, long idEntidadSesion, String codTipoAsociacion) {
		this.codTipoAsociacion = codTipoAsociacion;
		this.idEntidadSesion = idEntidadSesion;
		this.persona = persona;
		this.usuario = usuario;
	}

	@Override
	public List<PersonaDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		personaR = new PersonaDTO();
		try {
			List<PersonaDTO> listaHV = HojaDeVidaDelegate.listarPersonasConHojaDeVida(first, pageSize, persona, usuario,idEntidadSesion, codTipoAsociacion,sortField,sortOrder.toString()); 
			this.setRowCount(HojaDeVidaDelegate.filasPersonasConHV(persona, usuario, idEntidadSesion, codTipoAsociacion));
			for (PersonaDTO personaConEF : listaHV) {
				personaR = personaConEF;
				try {
					TipoDocumentoDTO tipoDocumentoHV = AdministracionDelegate.findTipoDocumentoId(personaR.getCodTipoIdentificacion());
					personaR.setTipoIdentificacionId(tipoDocumentoHV);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return listaHV;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}