package co.gov.dafp.sigep2.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.jpa.seguridad.NacionalidadPerfil;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Persona;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoDiscapacidadDTO;
import co.gov.dafp.sigep2.factoria.DatoAdicionalFactoria;
import co.gov.dafp.sigep2.factoria.DatoContactoFactoria;
import co.gov.dafp.sigep2.factoria.DepartamentoFactoria;
import co.gov.dafp.sigep2.factoria.EstadoCivilFactoria;
import co.gov.dafp.sigep2.factoria.MunicipioFactoria;
import co.gov.dafp.sigep2.factoria.NacionalidadPerfilFactoria;
import co.gov.dafp.sigep2.factoria.PaisFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.PoblacionEtnicaFactoria;
import co.gov.dafp.sigep2.factoria.TipoDiscapacidadFactoria;
import co.gov.dafp.sigep2.factoria.TipoZonaFactoria;
import co.gov.dafp.sigep2.interfaces.IDatosPersonalesRemote;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

@Stateless
public class DatosPersonalesEJB implements IDatosPersonalesRemote {

    private static final long serialVersionUID = 5662350904891047133L;
    private static final Logger logger = Logger.getInstance(DatosPersonalesEJB.class);

    @EJB
    PersonaFactoria personaFactoria;

    @EJB
    DatoContactoFactoria datoContactoFactoria;

    @EJB
    DatoAdicionalFactoria datoAdicionalFactoria;

    @EJB
    TipoDiscapacidadFactoria tipoDiscapacidadFactoria;

    @EJB
    PaisFactoria paisFactoria;

    @EJB
    TipoZonaFactoria tipoZonaFactoria;

    @EJB
    DepartamentoFactoria departamentoFactoria;

    @EJB
    MunicipioFactoria municipioFactoria;

    @EJB
    EstadoCivilFactoria estadoCivilFactoria;

    @EJB
    PoblacionEtnicaFactoria poblacionEtnicaFactoria;

    @EJB
    NacionalidadPerfilFactoria nacionalidadPerfilFactoria;

    @EJB
    private IEnvioCorreoLocal mailService;

    @Override
    public PersonaDTO findPersonaId(long id) {
	return (PersonaDTO) personaFactoria.find(id).getDTO();
    }

    @Override
    public void actualizarDatosDemograficos(long codPersona, List<PaisDTO> nacionalidad, UsuarioDTO usuarioRegistro,
	    long rolUsuario) throws SIGEP2SistemaException {
	Persona persona = personaFactoria.find(codPersona);
	this.agregarNacionalidadPersona(nacionalidad, persona, usuarioRegistro, rolUsuario);
    }

    @Override
    public List<TipoDiscapacidadDTO> findTipoDiscapacidad() {
	return tipoDiscapacidadFactoria.findTipoDiscapacidad();
    }

    @Override
    public List<PersonaDTO> obtenerUsuariosFiltros(long tipoDoc, String doc, String primerNombre, String segundoNombre,
	    String primerApellido, String segundoAPellido, Boolean busquedaDocumento) throws SIGEP2SistemaException {
	try {
	    return personaFactoria.obtenerUsuariosFiltros(tipoDoc, doc, primerNombre, segundoNombre, primerApellido,
		    segundoAPellido, busquedaDocumento);
	} catch (Exception ex) {
	    logger.log().error(
		    "EJB - public void actualizarDatosPersonales(PersonaDTO personaDTO, UsuarioDTO usuarioRegistro): ",
		    ex);
	    return null;
	}
    }

    @Override
    public List<PaisDTO> findNacionalidadByCodPersonaId(long codPersona) throws SIGEP2SistemaException {

	List<PaisDTO> listNacionalidad = new ArrayList<PaisDTO>();
	List<NacionalidadPerfil> nacionalidadPerfil = nacionalidadPerfilFactoria
		.findNacionalidadByCodPersonaId(codPersona);

	for (NacionalidadPerfil nacionalidad : nacionalidadPerfil) {
	    listNacionalidad.add((PaisDTO) nacionalidad.getCodPais().getDTO());
	}

	return listNacionalidad;
    }

    public void agregarNacionalidadPersona(List<PaisDTO> nacionalidad, Persona persona, UsuarioDTO usuarioRegistro,
	    long rolUsuario) throws SIGEP2SistemaException {

	List<NacionalidadPerfil> listNacionalidadPerfilDTO = persona.getNacionalidadPerfil();

	if (listNacionalidadPerfilDTO.size() != 0) {
	    for (NacionalidadPerfil nacionalidadPerfil : listNacionalidadPerfilDTO) {
		boolean thatIs = false;
		for (PaisDTO paisDTO : nacionalidad) {
		    Pais pais = paisFactoria.convertirPaisDTO(paisDTO);
		    if (pais.getId().equals(nacionalidadPerfil.getCodPais().getId())
			    && nacionalidadPerfil.isFlgActivo() == true) {
			thatIs = true;
			break;
		    }
		}

		if (thatIs == false) {
		    nacionalidadPerfil.setAudCodUsuario(usuarioRegistro.getId());
		    nacionalidadPerfil.setAudCodRol(rolUsuario);
		    nacionalidadPerfil.setAudAccion(Long.valueOf(TipoAccionEnum.UPDATE.getIdAccion()));
		    nacionalidadPerfil.setAudFechaActualizacion(DateUtils.getFechaSistema());
		    nacionalidadPerfil.setFlgActivo(false);
		    nacionalidadPerfilFactoria.merge(nacionalidadPerfil, null);
		}
	    }

	    for (PaisDTO paisDTO : nacionalidad) {
		boolean thatIs = false;
		Pais pais = paisFactoria.convertirPaisDTO(paisDTO);
		for (NacionalidadPerfil nacionalidadPerfil : listNacionalidadPerfilDTO) {
		    if (nacionalidadPerfil.getCodPais().getId().equals(pais.getId())
			    && nacionalidadPerfil.isFlgActivo() == true) {
			thatIs = true;
			break;
		    }
		}

		if (thatIs == false) {
		    NacionalidadPerfil nacionalidadEncontrada = nacionalidadPerfilFactoria
			    .buscarNacionalidadPorPaisYPersona(persona.getId(), pais.getId());

		    if (nacionalidadEncontrada == null) {
			NacionalidadPerfil nacionalidadPerfil = new NacionalidadPerfil();
			nacionalidadPerfil.setCodPais(pais);
			nacionalidadPerfil.setAudCodUsuario(usuarioRegistro.getId());
			nacionalidadPerfil.setAudCodRol(rolUsuario);
			nacionalidadPerfil.setAudAccion(Long.valueOf(TipoAccionEnum.UPDATE.getIdAccion()));
			nacionalidadPerfil.setAudFechaActualizacion(DateUtils.getFechaSistema());
			persona.addNacionalidadPerfil(nacionalidadPerfil);
		    } else {
			nacionalidadEncontrada.setFlgActivo(true);
			nacionalidadPerfilFactoria.merge(nacionalidadEncontrada, null);
		    }
		}
	    }

	} else {
	    for (PaisDTO paisDTO : nacionalidad) {
		NacionalidadPerfil nacionalidadPerfil = new NacionalidadPerfil();
		Pais pais = paisFactoria.convertirPaisDTO(paisDTO);

		nacionalidadPerfil.setCodPais(pais);
		nacionalidadPerfil.setAudCodUsuario(usuarioRegistro.getId());
		nacionalidadPerfil.setAudCodRol(rolUsuario);
		nacionalidadPerfil.setAudAccion(Long.valueOf(TipoAccionEnum.INSERT.getIdAccion()));
		nacionalidadPerfil.setAudFechaActualizacion(DateUtils.getFechaSistema());

		persona.addNacionalidadPerfil(nacionalidadPerfil);
	    }
	}

	try {
	    personaFactoria.merge(persona, null);
	} catch (SIGEP2SistemaException e) {
	    logger.log().error(
		    "EJB - public void agregarNacionalidadPersona(List<PaisDTO> nacionalidad, Persona persona, UsuarioDTO usuarioRegistro )");
	    throw new SIGEP2SistemaException(e.getMessage());
	}
    }

    @Override
    public void emailActualizacionDatosPersonaDC(String usuarioModificado, Date fechaModificacion,
	    String usuarioModificador, List<String> email, List<String> ccEmail, List<String> camposEditados)
	    throws SIGEP2SistemaException {
	try {
	    String asunto = "SIGEPII Información";
	    String body = HTMLUtil.abreParrafo + usuarioModificado
		    + " Su información en la plataforma ha sido actualizada";
	    if (camposEditados.size() > 0) {
		body += " con la siguiente información:";
		body += HTMLUtil.retornoCarro;
		body += HTMLUtil.retornoCarro;
		for (String campos : camposEditados) {
		    body += HTMLUtil.tab + campos;
		    body += HTMLUtil.retornoCarro;
		}
	    } else {
		body += ".";
	    }
	    body += HTMLUtil.espacioEnBlanco;
	    body += HTMLUtil.cierraParrafo;
	    mailService.enviarMail(asunto, body, null, email, ccEmail);
	} catch (Exception e) {
	    e.getStackTrace();
	    logger.log().error("EJB - desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
	    throw new SIGEP2SistemaException(e.getMessage());
	}
    }

    @Override
    public void emailActualizacionDatosPersona(String usuarioModificado, String email) throws SIGEP2SistemaException {
	try {
	    List<String> toList = new ArrayList<>();
	    String asunto = "SIGEPII Información";
	    String body = HTMLUtil.abreParrafo + usuarioModificado
		    + " Su información en la plataforma ha sido actualizada" + HTMLUtil.espacioEnBlanco
		    + HTMLUtil.cierraParrafo;
	    toList.add(email);
	    mailService.enviarMail(asunto, body, null, toList, null);
	} catch (Exception e) {
	    e.getStackTrace();
	    logger.log().error("EJB - desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
	    throw new SIGEP2SistemaException(e.getMessage());
	}
    }

	@Override
	public void enviarEmailPersonasDesvincular(String asunto, String mensaje, String correo) throws SIGEP2SistemaException {
		try {
		    List<String> toList = new ArrayList<>();
		    String body = HTMLUtil.abreParrafo + mensaje + HTMLUtil.espacioEnBlanco  + HTMLUtil.cierraParrafo;
		    toList.add(correo);
		    mailService.enviarMail(asunto, body, null, toList, null);
		} catch (Exception e) {
		    e.getStackTrace();
		    logger.log().error("EJB - desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
		    throw new SIGEP2SistemaException(e.getMessage());
		}
	}
	
	 public void emailActualizacionDatosPersona(String nombre, String cuerpo, String email) throws SIGEP2SistemaException{
	    	try {
			    List<String> toList = new ArrayList<>();
			    String asunto = "SIGEPII Información, Eliminación de planta";
			    String body = HTMLUtil.abreParrafo + nombre + " Se se ha eliminado una planta por favor ingrese y genere las respectivas vinculaciones y desvinculaciones" + HTMLUtil.espacioEnBlanco + HTMLUtil.cierraParrafo;
			    toList.add(email);
			    mailService.enviarMail(asunto, body, null, toList, null);
			} catch (Exception e) {
			    logger.log().error("EJB - desasociarUsuarioEntidad(Long codPersona, Long codEntidad)", e);
			    throw new SIGEP2SistemaException(e.getMessage());
			}
	    }
	    
	
}