package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.datamodel.PersonasLazyDataModel;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.UsuarioRolDependencia;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioEntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioPasswordDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.produces.EntidadProduces;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
public class ConsultarRolesPorUsuarioBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -1199368366370427715L;

	private String TIPO_DOC_PASAPORTE = TipoDocumentoIdentidadEnum.PA.getDescripcion();
	private PersonaDTO persona;
	private boolean disableButton = true;
	private boolean entidadSeleccionada = false;

	private boolean tieneRolAdmin = false;

	private EntidadDTO entidad;

	private LazyDataModel<PersonaDTO> listaPersonas;

	private List<RolExt> listaRolesByUser;

	private List<EntidadDTO> listaEntidades;

	private RolExt rolAsignado;
	private PersonaDTO usuarioSeleccionado;
	private Date fechaIncio;
	private Date fechaFin;
	private Date today = new Date();
	private String nom;

	@Inject
	private EntidadProduces entidadProduces;

	public boolean isDisableButton() {
		return disableButton;
	}

	public void setDisableButton(boolean disable) {
		this.disableButton = disable;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public boolean isEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(boolean entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public List<EntidadDTO> completeEntidadDTO(String query) {

		List<EntidadDTO> filteredEntidades = new ArrayList<EntidadDTO>();

		for (int i = 0; i < listaEntidades.size(); i++) {
			EntidadDTO ent = listaEntidades.get(i);
			System.out.println("Ent=" + ent.getNombreEntidad());
			System.out.println("query=" + query);
			if (ent.getNombreEntidad().toLowerCase().startsWith(query.toLowerCase())) {
				filteredEntidades.add(ent);
				System.out.println("added");
			}
		}
		System.out.println("filter=" + filteredEntidades.size());
		return filteredEntidades;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		setAdministradorRol();
		try {
			setListaEntidades(entidadProduces.getEntidades());
		} catch (SIGEP2SistemaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		persona = new PersonaDTO();

		if (getEntidadUsuario() != null) {
			entidadSeleccionada = true;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_SIN_ENTIDAD_ASOCIADA);
		}

	}

	private void setAdministradorRol() {
		try {
			tieneRolAdmin = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
		} catch (SIGEP2SistemaException e) {
			tieneRolAdmin = false;
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void listener() {
		buscarPersonas();
	}

	public void buscarPersonas() {
		String mensaje = "";

		if (persona.getTipoIdentificacionId() == null && persona.getNumeroIdentificacion().isEmpty()) {
			if (persona.getPrimerNombre().isEmpty()) {
				mensaje = mensaje + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TRANSVERSAL_PERSONAS_PRIMER_NOMBRE, getLocale()) + ", ";
			}

			if (persona.getPrimerApellido().isEmpty()) {
				mensaje = mensaje + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TRANSVERSAL_PERSONAS_PRIMER_APELLIDO, getLocale()) + ", ";
			}
		}

		if (persona.getPrimerNombre().isEmpty() || persona.getPrimerApellido().isEmpty()) {
			if (persona.getTipoIdentificacionId() == null && !persona.getNumeroIdentificacion().isEmpty()) {
				mensaje = mensaje + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_TIPO_DOCUMENTO, getLocale()) + ", ";
			}

			if (persona.getTipoIdentificacionId() != null && persona.getNumeroIdentificacion().isEmpty()) {
				mensaje = mensaje + TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_NICK_NAME, getLocale()) + ", ";
			}
		}

		if (!mensaje.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CRITERIO_MINIMO, getLocale()) + ": "+ mensaje 
					+ TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CONSULTA_NUEVAMENTE, getLocale()));

		} else {
			if (tieneRolAdmin) {
				if (entidad != null) {
					listaPersonas = new PersonasLazyDataModel(persona, this.entidad.getId());
				} else {
					listaPersonas = new PersonasLazyDataModel(persona, 0l);
				}
			} else {
				listaPersonas = new PersonasLazyDataModel(persona, this.getEntidadUsuario().getId());
			}
			RequestContext.getCurrentInstance().scrollTo("divScrollTableRoles");
		}
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void limpiarCampos() {
		persona = new PersonaDTO();
	}

	public void limpiarDoc() {
		if (persona != null) { // && persona.getTipoIdentificacionId()!=null) {
			persona.setNumeroIdentificacion("");
			// }else {// if(persona!=null &&
			// persona.getTipoIdentificacionId()==null) {
			persona.setPrimerNombre("");
			persona.setPrimerApellido("");
			persona.setSegundoNombre("");
			persona.setSegundoApellido("");
		}
		listaPersonas = null;
	}

	public LazyDataModel<PersonaDTO> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(LazyDataModel<PersonaDTO> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public void onRowToggle(ToggleEvent event) {
		PersonaDTO per = (PersonaDTO) event.getData();
		try {
			RolExt rolExt = new RolExt();
			if (tieneRolAdmin) {
				rolExt.setCodPersona(per.getId());
				rolExt.setCodEntidad(per.getCodEntidad());
			} else {
				rolExt.setCodPersona(per.getId());
			}
			rolExt.setOcultarRolBase("1");
			List<RolExt> listaRolesByUserTemp = ComunicacionServiciosSis.getRolPorPersonaEntidad(rolExt);
			List<RolExt> listaRolesByUser = new LinkedList<>();
			for (RolExt rolUser : listaRolesByUserTemp) {
				if (!listaRolesByUser.contains(rolUser)) {
					listaRolesByUser.add(rolUser);
				}
			}
			setListaRolesByUser(listaRolesByUser);
		} catch (Exception e) {
			listaRolesByUser = new ArrayList<>();
			e.printStackTrace();
		}
	}

	public void eliminarRolAsignadoUsuario(RolExt rolAsignado, PersonaDTO usuarioSeleccionado)
			throws SIGEP2SistemaException, IOException {
		UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioByLogin(
				usuarioSeleccionado.getTipoIdentificacionId().getId(), usuarioSeleccionado.getNumeroIdentificacion());
		if (usuario != null) {
			UsuarioEntidadDTO usuarioEntidad = IngresoSistemaDelegate.consultarUsuarioEntidad(usuario.getId(),
					rolAsignado.getCodEntidad());
			if (usuarioEntidad != null) {
				UsuarioRolEntidadDTO usuarioAsociar = new UsuarioRolEntidadDTO(0L, rolAsignado.getCodRol().intValue(),
						usuarioEntidad.getId(), false, false);
				UsuarioDTO usuarioAuditoria = getUsuarioSesion();
				usuarioAuditoria.setCodRol(getRolAuditoria().getId());
				AdministracionDelegate.asociarRolUsuario(usuarioAsociar, usuarioAuditoria,
						String.valueOf(TipoParametro.AUDITORIA_DELETE.getValue()));
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_ROL_ELIMINADO);
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_USUARIO_ASOCIADO_NO_EXISTE);
			}
		}
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya 10 ene. 2019
	 * 
	 * @param rolAsignado
	 * @param usuarioSeleccionado
	 * @throws SIGEP2SistemaException
	 * @throws IOException
	 */
	public void modificarRolAsignadoUsuario(RolExt rolAsignado) throws SIGEP2SistemaException, IOException {
		if (rolAsignado.getCodUsuarioRolEntidad() != null) {
			UsuarioRolDependencia dep = new UsuarioRolDependencia();
			dep.setFlgActivo((short) 1);
			dep.setCodUsuarioRolEntidad(new BigDecimal(rolAsignado.getCodUsuarioRolEntidad()));
			dep.setAudCodRol((int) getUsuarioSesion().getCodRol());
			dep.setAudFechaActualizacion(new Date());
			dep.setFechaFin(rolAsignado.getFechaFin());
			dep.setCodDependenciaEntidad(rolAsignado.getCodDependenciaEntidad());
			dep.setFechaInicio(rolAsignado.getFechaInicio());
			dep.setAudCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
			if (rolAsignado.getCodUsuarioRolDependencia() != null) {
				dep.setAudAccion(63);
				dep.setCodUsuarioRolDependencia(new BigDecimal(rolAsignado.getCodUsuarioRolDependencia()));
				dep.setCodDependenciaEntidad(rolAsignado.getCodDependenciaEntidad());
			} else {
				dep.setAudAccion(785);
			}
			dep = ComunicacionServiciosEnt.setUsuarioRolDependencia(dep);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ROL_MODIFICADO);
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_USUARIO_ASOCIADO_NO_EXISTE);
		}
	}

	public String getUsuarioEntidad(long tipoIdentificacion, String numeroIdentificacion)
			throws SIGEP2SistemaException {
		UsuarioEntidadDTO usuarioEntidad = null;
		UsuarioPasswordDTO usuario = IngresoSistemaDelegate.getUsuarioByLogin(tipoIdentificacion, numeroIdentificacion);
		if (usuario != null) {
			usuarioEntidad = IngresoSistemaDelegate.consultarUsuarioEntidad(usuario.getId(),
					getEntidadUsuario().getId());
			if (usuarioEntidad != null) {
				Entidad entidad = ComunicacionServiciosEnt.getEntidadPorId(usuarioEntidad.getEntidad());
				return entidad.getNombreEntidad();
			}
		}
		return "";
	}

	public EntidadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadDTO entidad) {
		this.entidad = entidad;
	}

	public List<EntidadDTO> getListaEntidades() {
		return listaEntidades;
	}

	public void setListaEntidades(List<EntidadDTO> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public boolean getTieneRolAdmin() {
		return tieneRolAdmin;
	}

	public void setAdministrador(boolean tieneRolAdmin) {
		this.tieneRolAdmin = tieneRolAdmin;
	}

	public PersonaDTO getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(PersonaDTO usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public String getTIPO_DOC_PASAPORTE() {
		return TIPO_DOC_PASAPORTE;
	}

	public void setTIPO_DOC_PASAPORTE(String tIPO_DOC_PASAPORTE) {
		TIPO_DOC_PASAPORTE = tIPO_DOC_PASAPORTE;
	}

	/**
	 * @return the listaRolesByUser
	 */
	public List<RolExt> getListaRolesByUser() {
		return listaRolesByUser;
	}

	/**
	 * @param listaRolesByUser
	 *            the listaRolesByUser to set
	 */
	public void setListaRolesByUser(List<RolExt> listaRolesByUser) {
		this.listaRolesByUser = listaRolesByUser;
	}

	/**
	 * @return the rolAsignado
	 */
	public RolExt getRolAsignado() {
		return rolAsignado;
	}

	/**
	 * @param rolAsignado
	 *            the rolAsignado to set
	 */
	public void setRolAsignado(RolExt rolAsignado) {
		this.rolAsignado = rolAsignado;
	}

	/**
	 * @return the fechaIncio
	 */
	public Date getFechaIncio() {
		return fechaIncio;
	}

	/**
	 * @param fechaIncio
	 *            the fechaIncio to set
	 */
	public void setFechaIncio(Date fechaIncio) {
		this.fechaIncio = fechaIncio;
	}

	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the today
	 */
	public Date getToday() {
		return today;
	}

	/**
	 * @param today
	 *            the today to set
	 */
	public void setToday(Date today) {
		this.today = today;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void guardar() {
		System.out.println("guardar()");
		System.out.println(nom);
	}

	public void guardar1(String data) {
		System.out.println("guardar1()");
		System.out.println(data);
	}

	/**
	 * Valida que la fecha inicial sea menor o igual a la final, en caso de ser
	 * incorrecta el resultado de la validacion, se deshabilitara el boton
	 * Aceptar para asi bloquear que el usuario confirme el cambio
	 * 
	 * @param event
	 *            Captura del evento de cambio de valor en el componente de
	 *            fecha
	 * 
	 */
	public void onDateSelect(SelectEvent event) {
		if (rolAsignado.getFechaInicio() != null && rolAsignado.getFechaFin() != null
				&& rolAsignado.getFechaInicio().after(rolAsignado.getFechaFin())) {
			disableButton = true;

			String msg = MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.DLG_FECHA_INVALIDA_MENOR_INICIAL, getLocale())
					.replace("START_DATE_NAME", TitlesBundleConstants.getStringMessagesBundle("LBL_FECHA_INICIAL"))
					.replace("END_DATE_NAME", TitlesBundleConstants.getStringMessagesBundle("LBL_FECHA_FIN_2"));

			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, msg);
		} else {
			disableButton = false;
		}
	}

}