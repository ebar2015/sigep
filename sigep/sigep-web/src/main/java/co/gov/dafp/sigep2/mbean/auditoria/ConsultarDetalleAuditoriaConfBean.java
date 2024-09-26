package co.gov.dafp.sigep2.mbean.auditoria;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.primefaces.model.LazyDataModel;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.AuditoriaLazyDataModel;
import co.gov.dafp.sigep2.entities.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.AccionAuditoriaDTO;
import co.gov.dafp.sigep2.mbean.ext.AuditoriaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
@ManagedBean
public class ConsultarDetalleAuditoriaConfBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 7511489077369551258L;
	private AccionAuditoriaDTO accionAuditoria;
	private AccionAuditoriaDTO tipoAccion;
	private Date fechaInicial;
	private Date fechaFinal;

	private AuditoriaConfiguracion auditoriasporClave = new AuditoriaConfiguracion();
	private String dato;
	private boolean habilitargrillaVerDetalle = false;
	private AuditoriaExt objetoAuditoria = new AuditoriaExt();
	private Boolean flgValidRolPermission = false;
	private LazyDataModel<AuditoriaExt> auditorias;
	private boolean flgEsAuditoriaConsulta=false;

	public LazyDataModel<AuditoriaExt> getAuditorias() {
		return auditorias;
	}

	public void setAuditorias(LazyDataModel<AuditoriaExt> auditorias) {
		this.auditorias = auditorias;
	}

	public AuditoriaConfiguracion getAuditoriasporClave() {
		return auditoriasporClave;
	}

	public void setAuditoriasporClave(AuditoriaConfiguracion auditoriasporClave) {
		this.auditoriasporClave = auditoriasporClave;
	}

	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub

	}

	public boolean isHabilitargrillaVerDetalle() {
		return habilitargrillaVerDetalle;
	}

	public void setHabilitargrillaVerDetalle(boolean habilitargrillaVerDetalle) {
		this.habilitargrillaVerDetalle = habilitargrillaVerDetalle;
	}

	/*
	 * metodo que retorna a la vista la informacion de la tabla seleccionada para su
	 * respectivo Detalle de Configuracion.
	 */
	@PostConstruct
	public void init() {
		try {
			tipoAccion = new AccionAuditoriaDTO();
			initialization();
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();
			String idtabla = request.getParameter("id");
			auditoriasporClave = ComunicacionServiciosSis.getAuditoriaConfiguracion(Integer.parseInt(idtabla));
			
			String tabla="TABLA ";
			dato = tabla + auditoriasporClave.getNombreTabla();
			auditorias=new AuditoriaLazyDataModel();
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}

	}

	/*
	 * Metodo que valida el Rol Usuario del sistema y los permisos de acceso a datos
	 * a la aplicacion.
	 */
	public void initialization() {
		// String rolesValid[] = { "SUPERADM", "ADMINFUNC", "ADMINTEC" };
		if (getUsuarioSesion() != null) {
			try {
				flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR,
						RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_TECNICO);
				if (flgValidRolPermission == false) {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				}
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			} catch (IOException e) {
				logger.error("void init() finalizarConversacion", e);
			}

		}

	}

	/*
	 * Metodo que retorna a la vista una lista que contiene la fecha en que se
	 * realiza una accion CRUD en una tabla determinada.
	 */
	public void mostrarTablaDetalle() {
		initialization();
		try {
			AuditoriaExt audita = new AuditoriaExt();
			audita.setCodTablaParametrica(tipoAccion !=null ? tipoAccion.getId() : null);
			audita.setNombreTabla(dato);
			audita.setFechaIni(objetoAuditoria.getFechaIni());
			audita.setFechaFin(objetoAuditoria.getFechaFin());
			if(audita.getCodTablaParametrica().equals(Long.valueOf(TipoParametro.AUDITORIA_CONSULTA.getValue()))) {
				flgEsAuditoriaConsulta = true;
			}else{
				flgEsAuditoriaConsulta = false;
			}
				
			auditorias = new AuditoriaLazyDataModel(audita,true);
			habilitargrillaVerDetalle = true;
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/*
	 * metodo que permite habilitar un panel Detalle despues de realizar un proceso
	 * ó accion.
	 */
	public void habilitarFormulariobusqueda() {

		habilitargrillaVerDetalle = true;
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub

	}

	private void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public AccionAuditoriaDTO getAccionAuditoria() {
		return accionAuditoria;
	}

	public void setAccionAuditoria(AccionAuditoriaDTO accionAuditoria) {
		this.accionAuditoria = accionAuditoria;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public AccionAuditoriaDTO getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(AccionAuditoriaDTO tipoAccion) {
		this.tipoAccion = tipoAccion;
	}

	public AuditoriaExt getObjetoAuditoria() {
		return objetoAuditoria;
	}

	public void setObjetoAuditoria(AuditoriaExt objetoAuditoria) {
		this.objetoAuditoria = objetoAuditoria;
	}

	public boolean isFlgEsAuditoriaConsulta() {
		return flgEsAuditoriaConsulta;
	}

	public void setFlgEsAuditoriaConsulta(boolean flgEsAuditoriaConsulta) {
		this.flgEsAuditoriaConsulta = flgEsAuditoriaConsulta;
	}
}