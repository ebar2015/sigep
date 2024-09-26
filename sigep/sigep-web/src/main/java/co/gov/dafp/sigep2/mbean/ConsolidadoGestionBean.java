package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.util.LinkedList;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.datamodel.CifrasLazyDataModel;
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
@ManagedBean
public class ConsolidadoGestionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 8705307162309233771L;

	private CifrasEmpleoPublico consolidadoSeleccionado;
	private CifrasEmpleoPublico filtro;

	private boolean habilitarConsulta = false;

	private CifrasLazyDataModel listaCifras;

	public CifrasEmpleoPublico getConsolidadoSeleccionado() {
		return consolidadoSeleccionado;
	}

	public void setConsolidadoSeleccionado(CifrasEmpleoPublico consolidadoSeleccionado) {
		this.consolidadoSeleccionado = consolidadoSeleccionado;
	}

	public CifrasEmpleoPublico getFiltro() {
		return filtro;
	}

	public void setFiltro(CifrasEmpleoPublico filtro) {
		this.filtro = filtro;
	}

	public boolean isHabilitarConsulta() {
		return habilitarConsulta;
	}

	public void setHabilitarConsulta(boolean habilitarConsulta) {
		CifrasEmpleoPublicoBean cifrasEmpleoPublicoBean = BaseBean.findBean("cifrasEmpleoPublicoBean");
		cifrasEmpleoPublicoBean.setHabilitarConsulta(habilitarConsulta);
		this.habilitarConsulta = habilitarConsulta;
	}

	public CifrasLazyDataModel getListaCifras() {
		return listaCifras;
	}

	public void setListaCifras(CifrasLazyDataModel listaCifras) {
		this.listaCifras = listaCifras;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {

	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
		filtro = new CifrasEmpleoPublico();
		setHabilitarConsulta(false);

		try {
			if (usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				setHabilitarConsulta(true);
			} else {
				return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init()", e);
		}

		CifrasEmpleoPublico filtroInit = new CifrasEmpleoPublico(this.filtro);
		listaCifras = new CifrasLazyDataModel(filtroInit);
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);		
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		filtro = new CifrasEmpleoPublico();

		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		try {
			this.init();
		} catch (SIGEP2SistemaException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Inicializa el buscador de cifras de empleo teniendo en cuenta las
	 * restricciones de seguridad por rol para la funcionalidad
	 */
	public void montarDatos() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}

		if (filtro.getConsolidado() != null) {
			if (TitlesBundleConstants.TTL_CONSOLIDADO_GESTION_ENT_REP_ASESOR.equals(filtro.getConsolidado())) {
				filtro.setAvanceAsesor(true);
				filtro.setConsolidadoGestion(false);
			} else {
				filtro.setAvanceAsesor(false);
				filtro.setConsolidadoGestion(true);
			}
		}

		// Se valida que al menos se haya seleccionado alguna de las tematicas
		if (!filtro.isAvanceAsesor() && !filtro.isConsolidadoGestion()) {
			setHabilitarConsulta(false);
			this.listaCifras.setListaCifras(new LinkedList<>());
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_REQUERIDO, getLocale()) + ". "
							+ TitlesBundleConstants
									.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_RESUMEN_CARGOS_PUBLICOS));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.renderResponse();
		} else {
			setHabilitarConsulta(true);
			listaCifras = new CifrasLazyDataModel(filtro);
		}
	}
}
