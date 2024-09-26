package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.EvaluacionDesempeno;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entities.Publicacion;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ViewScoped
@ManagedBean
public class GerenciaPublicaBeanOld extends BaseBean implements Serializable {

	private static final long serialVersionUID = 3695472896249884567L;

	private DatoAdicionalExt datoAdicional;
	private LogroRecurso logroRecurso;
	private PersonaExt persona;
	private Publicacion publicacion;
	private EvaluacionDesempenoExt evaluacion;
	private List<EvaluacionDesempeno> mostrarEvaluacion;
	private ReconocimientoExt reconocimiento;
	private ParticipacionProyectoExt participacionProyecto;
	private ParticipacionInstitucion participacionInstitucion;
	private HistoricoModificacionHojaVida modificacionHojaVida;
	private ExperienciaProfesionalExt expProfesional;

	private Long codEntidad = null;
	private Long codPersona = getUsuarioSesion().getCodPersona();
	private Integer codFormulario = null;

	private Boolean flgOtraOrientacion;
	private Boolean flgFormulario;
	private Boolean flgLogros = false;
	private Boolean flgPublicacion = false;
	private Boolean flgEvaluacion = false;
	private Boolean flgPacionProyecto = false;
	private Boolean flgInstitucion = false;
	private Boolean flgReconocimiento = false;
	private Boolean flgActivarPorPais;
	private Boolean flgActivarPorPaisProject;
	private Boolean flgActCargoPub;
	private Boolean flgActCargoPriv;
	private Boolean flgValidRolPermission = false;

	private List<SelectItem> listaNombreEntidad;
	private List<SelectItem> listaCargoPri;
	private List<SelectItem> listaCargoPub;
	private List<ExperienciaProfesionalExt> listaEntidadExpProfesional;

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext,
			null, "datosPersonalesBean");

	public List<ExperienciaProfesionalExt> getListaEntidadExpProfesional() {
		return listaEntidadExpProfesional;
	}

	public void setListaEntidadExpProfesional(List<ExperienciaProfesionalExt> listaEntidadExpProfesional) {
		this.listaEntidadExpProfesional = listaEntidadExpProfesional;
	}

	public DatoAdicionalExt getDatoAdicional() {
		return datoAdicional;
	}

	public void setDatoAdicional(DatoAdicionalExt datoAdicional) {
		this.datoAdicional = datoAdicional;
	}

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	public LogroRecurso getLogroRecurso() {
		return logroRecurso;
	}

	public void setLogroRecurso(LogroRecurso logroRecurso) {
		this.logroRecurso = logroRecurso;
	}

	public Boolean getFlgOtraOrientacion() {
		return flgOtraOrientacion;
	}

	public void setFlgOtraOrientacion(Boolean flgOtraOrientacion) {
		this.flgOtraOrientacion = flgOtraOrientacion;
	}

	public Boolean getFlgFormulario() {
		return flgFormulario;
	}

	public void setFlgFormulario(Boolean flgFormulario) {
		this.flgFormulario = flgFormulario;
	}

	public Boolean getFlgLogros() {
		return flgLogros;
	}

	public void setFlgLogros(Boolean flgLogros) {
		this.flgLogros = flgLogros;
	}

	public List<SelectItem> getListaNombreEntidad() {
		return listaNombreEntidad;
	}

	public void setListaNombreEntidad(List<SelectItem> listaNombreEntidad) {
		this.listaNombreEntidad = listaNombreEntidad;
	}

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

	public Boolean getFlgPublicacion() {
		return flgPublicacion;
	}

	public void setFlgPublicacion(Boolean flgPublicacion) {
		this.flgPublicacion = flgPublicacion;
	}

	public EvaluacionDesempenoExt getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionDesempenoExt evaluacion) {
		this.evaluacion = evaluacion;
	}

	public List<EvaluacionDesempeno> getMostrarEvaluacion() {
		return mostrarEvaluacion;
	}

	public void setMostrarEvaluacion(List<EvaluacionDesempeno> mostrarEvaluacion) {
		this.mostrarEvaluacion = mostrarEvaluacion;
	}

	public Boolean getFlgEvaluacion() {
		return flgEvaluacion;
	}

	public void setFlgEvaluacion(Boolean flgEvaluacion) {
		this.flgEvaluacion = flgEvaluacion;
	}

	public List<SelectItem> getListaCargoPri() {
		return listaCargoPri;
	}

	public void setListaCargoPri(List<SelectItem> listaCargoPri) {
		this.listaCargoPri = listaCargoPri;
	}

	public List<SelectItem> getListaCargoPub() {
		return listaCargoPub;
	}

	public void setListaCargoPub(List<SelectItem> listaCargoPub) {
		this.listaCargoPub = listaCargoPub;
	}

	public ReconocimientoExt getReconocimiento() {
		return reconocimiento;
	}

	public void setReconocimiento(ReconocimientoExt reconocimiento) {
		this.reconocimiento = reconocimiento;
	}

	public Boolean getFlgActivarPorPais() {
		return flgActivarPorPais;
	}

	public void setFlgActivarPorPais(Boolean flgActivarPorPais) {
		this.flgActivarPorPais = flgActivarPorPais;
	}

	public ParticipacionProyectoExt getParticipacionProyecto() {
		return participacionProyecto;
	}

	public void setParticipacionProyecto(ParticipacionProyectoExt participacionProyecto) {
		this.participacionProyecto = participacionProyecto;
	}

	public Boolean getFlgActivarPorPaisProject() {
		return flgActivarPorPaisProject;
	}

	public void setFlgActivarPorPaisProject(Boolean flgActivarPorPaisProject) {
		this.flgActivarPorPaisProject = flgActivarPorPaisProject;
	}

	public Boolean getFlgActCargoPub() {
		return flgActCargoPub;
	}

	public void setFlgActCargoPub(Boolean flgActCargoPub) {
		this.flgActCargoPub = flgActCargoPub;
	}

	public Boolean getFlgActCargoPriv() {
		return flgActCargoPriv;
	}

	public void setFlgActCargoPriv(Boolean flgActCargoPriv) {
		this.flgActCargoPriv = flgActCargoPriv;
	}

	public Boolean getFlgPacionProyecto() {
		return flgPacionProyecto;
	}

	public void setFlgPacionProyecto(Boolean flgPacionProyecto) {
		this.flgPacionProyecto = flgPacionProyecto;
	}

	public Boolean getFlgInstitucion() {
		return flgInstitucion;
	}

	public void setFlgInstitucion(Boolean flgInstitucion) {
		this.flgInstitucion = flgInstitucion;
	}

	public Boolean getFlgReconocimiento() {
		return flgReconocimiento;
	}

	public void setFlgReconocimiento(Boolean flgReconocimiento) {
		this.flgReconocimiento = flgReconocimiento;
	}

	public ParticipacionInstitucion getParticipacionInstitucion() {
		return participacionInstitucion;
	}

	public void setParticipacionInstitucion(ParticipacionInstitucion participacionInstitucion) {
		this.participacionInstitucion = participacionInstitucion;
	}

	public Integer getCodFormulario() {
		return codFormulario;
	}

	public void setCodFormulario(Integer codFormulario) {
		this.codFormulario = codFormulario;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

	public ExperienciaProfesionalExt getExpProfesional() {
		return expProfesional;
	}

	public void setExpProfesional(ExperienciaProfesionalExt expProfesional) {
		this.expProfesional = expProfesional;
	}

	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {

		this.initialization();
		ExternalContext contexto;
		contexto = FacesContext.getCurrentInstance().getExternalContext();
		Long codPer = (Long) contexto.getSessionMap().get("id");

		if (codPer != null)
			codPersona = codPer;

		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		datoAdicional = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		logroRecurso = ComunicacionServiciosHV.getLogroRecursoPorPersona(codPersona);
		publicacion = ComunicacionServiciosHV.getpublicacionoporpersona(codPersona);
		reconocimiento = ComunicacionServiciosHV.getreconocimientoporpersona(codPersona);
		participacionProyecto = ComunicacionServiciosHV.getparticipacionProyeporpersona(codPersona);
		participacionInstitucion = ComunicacionServiciosHV.getparticipacionInstitucionporpersona(codPersona);
		evaluacion = ComunicacionServiciosHV.getEvaluacionDesempenoPorPersona(codPersona, null);
		modificacionHojaVida = new HistoricoModificacionHojaVida();

		expProfesional = new ExperienciaProfesionalExt();
		expProfesional.setCodPersona(BigDecimal.valueOf(codPersona));
		expProfesional.setFlgActivo(true);
		expProfesional.setLimitInit(0);
		expProfesional.setLimitEnd(10);

		listaNombreEntidad = new ArrayList<SelectItem>();
		listaCargoPri = new ArrayList<SelectItem>();
		listaCargoPub = new ArrayList<SelectItem>();

		listaEntidadExpProfesional = ComunicacionServiciosHV.getexperienciaprofesionalporpersona(expProfesional);
		List<ExperienciaProfesionalExt> listaCargoExpProfesional = ComunicacionServiciosHV.getCargoExp(expProfesional);

		if (logroRecurso.getCodLogroRecurso() != null) {
			this.flgLogros = true;
		} else {
			this.flgLogros = false;
		}

		if (publicacion.getCodPublicacion() != null) {
			this.flgPublicacion = true;
		} else {
			this.flgPublicacion = false;
		}

		if (evaluacion.getCodEvaluacionDesempeno() != null) {
			this.flgEvaluacion = true;
		} else {
			this.flgEvaluacion = false;
		}

		if (reconocimiento.getCodReconocimiento() != null) {
			this.flgReconocimiento = true;
		} else {
			this.flgReconocimiento = false;
		}

		if (participacionProyecto.getCodParticipacionProyecto() != null) {
			this.flgPacionProyecto = true;
		} else {
			this.flgPacionProyecto = false;
		}

		if (participacionInstitucion.getCodParticipacionInstitucion() != null) {
			this.flgInstitucion = true;
		} else {
			this.flgInstitucion = false;
		}

		if (!listaEntidadExpProfesional.isEmpty()) {
			for (ExperienciaProfesionalExt aux : listaEntidadExpProfesional) {
				if (aux.getNombreEntidad() != null && !aux.getNombreEntidad().isEmpty()) {
					listaNombreEntidad.add(new SelectItem(aux.getNombreEntidad(), aux.getNombreEntidad()));
				} else {
					if (aux.getNombreEntidad() == null) {
						listaNombreEntidad.add(new SelectItem(aux.getNombreEntidadExt(), aux.getNombreEntidadExt()));
					}
				}
			}
		}

		if (!listaCargoExpProfesional.isEmpty()) {
			for (ExperienciaProfesionalExt aux : listaCargoExpProfesional) {
				if (aux.getFlgEntidadPublica()) {
					listaCargoPub.add(new SelectItem(aux.getCargo(), aux.getCargo()));
				} else {
					listaCargoPri.add(new SelectItem(aux.getCargo(), aux.getCargo()));
				}
			}
		}

		this.mostrarOtraOrientacion();
		this.habilitarFomulario();
		this.mostratPanelPorPais();
		this.mostratPanelPorPaisProject();
		this.deshabilitarCargo();
	}

	public void initialization() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String idPersona = request.getParameter("id");

		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					flgValidRolPermission = this.usuarioTieneRolAsignado(RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR,
							RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS,
							RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
					if (flgValidRolPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		} else {
			if (getUsuarioSesion() != null) {
				codPersona = getUsuarioSesion().getCodPersona();
				try {
					Boolean flgValidRolPermission = this.usuarioTieneRolAsignado(RolDTO.CONTRATISTA,
							RolDTO.SERVIDOR_PUBLICO);
					if (flgValidRolPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			} else {
				try {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		}
	}

	public void guardarDatosAdicionales() {

		datoAdicional.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		datoAdicional.setAudCodRol((int) this.getRolAuditoria().getId());
		datoAdicional.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (datoAdicional.getCodDatoAdicional() == null) {
			datoAdicional.setCodPersona(BigDecimal.valueOf(codPersona));
			datoAdicional.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			datoAdicional.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setDatoContactoAdi(datoAdicional);

		if (valid) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			datoAdicional = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
			this.mostrarOtraOrientacion();
			mBDatosPersonalesBean.setEditado(false);
			this.habilitarFomulario();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			flgFormulario = false;
		}
	}

	public void guardarLogroRecurso() {

		logroRecurso.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		logroRecurso.setAudCodRol((int) this.getRolAuditoria().getId());
		logroRecurso.setAudFechaActualizacion(DateUtils.getFechaSistema());
		if(flgLogros)
			logroRecurso.setFlgAdministraRecursos((short) 1);
		else
			logroRecurso.setFlgAdministraRecursos((short) 0);

		if (logroRecurso.getCodLogroRecurso() == null) {
			logroRecurso.setCodPersona(BigDecimal.valueOf(codPersona));
			logroRecurso.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			logroRecurso.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setLogroRecursoPorPersona(logroRecurso);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			logroRecurso = ComunicacionServiciosHV.getLogroRecursoPorPersona(codPersona);
			this.flgLogros = true;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void guardarPublicacion() {

		publicacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		publicacion.setAudCodRol((int) this.getRolAuditoria().getId());
		publicacion.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (publicacion.getCodPublicacion() == null) {
			publicacion.setCodPersona(BigDecimal.valueOf(codPersona));
			publicacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			publicacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}
		System.out.println(codPersona + " GUARSDAR COD PERSONA PUBLICACION " + publicacion.getCodPersona());
		boolean valid = ComunicacionServiciosHV.setpublicacionoporpersona(publicacion);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			publicacion = ComunicacionServiciosHV.getpublicacionoporpersona(codPersona);
			this.flgPublicacion = true;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void guardarEvaluacionDesempeno() {
		evaluacion.setCodTipoEvaluacion(566);
		evaluacion.setCodNivelCumplimiento(568);
		evaluacion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		evaluacion.setAudCodRol((int) this.getRolAuditoria().getId());
		evaluacion.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (evaluacion.getCodEvaluacionDesempeno() == null) {
			evaluacion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
			evaluacion.setCodPersona(BigDecimal.valueOf(codPersona));
			evaluacion.setCodEntidad(getEntidadUsuario().getId());
		} else {
			evaluacion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setEvaluacionDesempeno(evaluacion);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			this.flgEvaluacion = true;
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void guardarReconocimiento() {

		reconocimiento.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		if(reconocimiento.getFlgEsPremio()!=null && reconocimiento.getFlgEsPremio().equals(0))
			reconocimiento.setFlgEsReconocimiento((short) 1);
		else
			reconocimiento.setFlgEsReconocimiento((short) 0);
		reconocimiento.setAudCodRol((int) this.getRolAuditoria().getId());
		reconocimiento.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (reconocimiento.getCodReconocimiento() == null) {
			reconocimiento.setCodPersona(BigDecimal.valueOf(codPersona));
			reconocimiento.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			reconocimiento.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setreconocimientoporpersona(reconocimiento);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			reconocimiento = ComunicacionServiciosHV.getreconocimientoporpersona(codPersona);
			this.mostratPanelPorPais();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void guardarParticipacionProyecto() {

		participacionProyecto.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		participacionProyecto.setAudCodRol((int) this.getRolAuditoria().getId());
		participacionProyecto.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (participacionProyecto.getCodParticipacionProyecto() == null) {
			participacionProyecto.setCodPersona(BigDecimal.valueOf(codPersona));
			participacionProyecto.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			participacionProyecto.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setparticipacionProyeporpersona(participacionProyecto);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			participacionProyecto = ComunicacionServiciosHV.getparticipacionProyeporpersona(codPersona);
			this.mostratPanelPorPaisProject();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void guardarParticipacionInstitucion() {

		participacionInstitucion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		participacionInstitucion.setAudCodRol((int) this.getRolAuditoria().getId());
		participacionInstitucion.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (participacionInstitucion.getCodParticipacionInstitucion() == null) {
			participacionInstitucion.setCodPersona(BigDecimal.valueOf(codPersona));
			participacionInstitucion.setAudCodAccion(TipoAccionEnum.INSERT.getIdAccion());
		} else {
			participacionInstitucion.setAudCodAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setparticipacionInstitucionporpersona(participacionInstitucion);

		if (valid) {
			mBDatosPersonalesBean.setEditado(false);
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA);
			participacionInstitucion = ComunicacionServiciosHV.getparticipacionInstitucionporpersona(codPersona);
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
		}
	}

	public void mostrarOtraOrientacion() {
		if (datoAdicional.getCodOrientacionSexual() != null && datoAdicional.getCodOrientacionSexual().equals(381)) {
			flgOtraOrientacion = true;
		} else {
			flgOtraOrientacion = false;
			datoAdicional.setOtraOrientacionSexual(null);
		}
	}

	public void habilitarFomulario() {
		if (datoAdicional.getCodDatoAdicional() != null) {
			flgFormulario = true;
		} else {
			flgFormulario = false;
		}
	}

	public void habilitarLogroRecurso() {
		if (!flgLogros) {
			logroRecurso = new LogroRecurso();
		} else {
			logroRecurso = ComunicacionServiciosHV.getLogroRecursoPorPersona(codPersona);
		}
	}

	public void habilitarPublicacion() {
		if (!flgPublicacion) {
			publicacion = new Publicacion();
		} else {
			publicacion = ComunicacionServiciosHV.getpublicacionoporpersona(codPersona);
		}
	}

	public void habilitarEvaluacion() {
		if (!flgEvaluacion) {
			evaluacion = new EvaluacionDesempenoExt();
		} else {
			evaluacion = ComunicacionServiciosHV.getEvaluacionDesempenoPorPersona(codPersona, null);
		}
	}

	public void habilitarPremios() {
		if (!flgReconocimiento) {
			reconocimiento = new ReconocimientoExt();
		} else {
			reconocimiento = ComunicacionServiciosHV.getreconocimientoporpersona(codPersona);
		}
	}

	public void habilitarParProyecto() {
		if (!flgPacionProyecto) {
			participacionProyecto = new ParticipacionProyectoExt();
		} else {
			participacionProyecto = ComunicacionServiciosHV.getparticipacionProyeporpersona(codPersona);
		}
	}

	public void habilitarParInstitucion() {
		if (!flgInstitucion) {
			participacionInstitucion = new ParticipacionInstitucion();
		} else {
			participacionInstitucion = ComunicacionServiciosHV.getparticipacionInstitucionporpersona(codPersona);
		}
	}

	public void deshabilitarCargo() {
		flgActCargoPub = false;
		flgActCargoPriv = false;

		for (ExperienciaProfesionalExt aux : listaEntidadExpProfesional) {
			if (aux.getNombreEntidad().equals(this.evaluacion.getNombreEntidadPublica())) {
				if (aux.getFlgEntidadPublica()) {
					flgActCargoPub = true;
					this.evaluacion.setCargoEntidadPrivada("0");
				} else {
					flgActCargoPriv = true;
					this.evaluacion.setCargoGrado("0");
				}
			}
		}
	}

	public void mostratPanelPorPais() {
		flgActivarPorPais = true;
		if (reconocimiento.getCodPais() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(reconocimiento.getCodPais());
			if (pais.getNombrePais().toUpperCase().equals("COLOMBIA")) {
				flgActivarPorPais = false;
			} else {
				flgActivarPorPais = true;
				reconocimiento.setCodDepartamento(null);
				reconocimiento.setCodMunicipio(null);
			}
		}
	}

	public void mostratPanelPorPaisProject() {
		flgActivarPorPaisProject = true;
		if (participacionProyecto.getCodPais() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(participacionProyecto.getCodPais());
			if (pais.getNombrePais().toUpperCase().equals("COLOMBIA")) {
				flgActivarPorPaisProject = false;
			} else {
				flgActivarPorPaisProject = true;
				participacionProyecto.setCodDepartamento(null);
				participacionProyecto.setCodMunicipio(null);
			}
		}
	}

	public void cancelarDatosAdicionales() {
		datoAdicional = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		this.mostrarOtraOrientacion();
	}

	public void cancelarLogroRecurso() {
		logroRecurso = ComunicacionServiciosHV.getLogroRecursoPorPersona(codPersona);
	}

	public void cancelarGuardarPublicacion() {
		publicacion = ComunicacionServiciosHV.getpublicacionoporpersona(codPersona);
		if (publicacion.getCodPublicacion() != null) {
			flgPublicacion = true;
		}
	}

	public void cancelarEvaluacionDesempeno() {
		evaluacion = ComunicacionServiciosHV.getEvaluacionDesempenoPorPersona(codPersona, null);
		this.deshabilitarCargo();
		if (evaluacion.getCodEntidad() != null) {
			flgEvaluacion = true;
		}
	}

	public void cancelarReconocimiento() {
		reconocimiento = ComunicacionServiciosHV.getreconocimientoporpersona(codPersona);
		this.mostratPanelPorPais();
	}

	public void cancelarParticipacionProyecto() {
		participacionProyecto = ComunicacionServiciosHV.getparticipacionProyeporpersona(codPersona);
		this.mostratPanelPorPaisProject();
	}

	public void cancelarParticipacionInstitucion() {
		participacionInstitucion = ComunicacionServiciosHV.getparticipacionInstitucionporpersona(codPersona);
	}

	public void confirmarDatosGuardar(int formulario) {
		codFormulario = formulario;
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		guardarConfirmacionDatos();
	}

	public void guardarConfirmacionDatos() {

		List<String> camposEditados = new ArrayList<>();

		if (codFormulario.intValue() == 1 && datoAdicional.getCodDatoAdicional() != null) {
			DatoAdicionalExt datoAdicionalAnterior = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
			if (datoAdicionalAnterior.getCodCabezaHogar().intValue() != datoAdicional.getCodCabezaHogar().intValue()) {
				camposEditados.add("Cabeza de familia: " + ComunicacionServiciosSis
						.getParametricaporId(new BigDecimal(datoAdicional.getCodCabezaHogar().intValue()))
						.getNombreParametro());
			}
			if (datoAdicionalAnterior.getCodVictimaDesplazamiento().intValue() != datoAdicional
					.getCodVictimaDesplazamiento().intValue()) {
				camposEditados.add("Víctima de desplazamiento: " + ComunicacionServiciosSis
						.getParametricaporId(new BigDecimal(datoAdicional.getCodVictimaDesplazamiento().intValue()))
						.getNombreParametro());
			}
			if (datoAdicionalAnterior.getCodOrientacionSexual().intValue() != datoAdicional.getCodOrientacionSexual()
					.intValue()) {
				camposEditados.add("Identidad sexual: " + ComunicacionServiciosSis
						.getParametricaporId(new BigDecimal(datoAdicional.getCodOrientacionSexual().intValue()))
						.getNombreParametro());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 2 && logroRecurso.getCodLogroRecurso() != null) {
			LogroRecurso logroRecursoAnterior = ComunicacionServiciosHV.getLogroRecursoPorPersona(codPersona);
			if (logroRecursoAnterior.getFlgAdministraRecursos() != logroRecurso.getFlgAdministraRecursos()) {
				if (logroRecurso.getFlgAdministraRecursos().equals(1)) {
					camposEditados.add("Ha administrado recursos: Si");
				} else {
					camposEditados.add("Ha administrado recursos: No");
				}
			}
			if (!(logroRecursoAnterior.getNombreEntidad().trim().equals(logroRecurso.getNombreEntidad().trim()))) {
				camposEditados.add("Nombre entidad: " + logroRecurso.getNombreEntidad());
			}
			if (logroRecursoAnterior.getNumEmpleados().intValue() != logroRecurso.getNumEmpleados().intValue()) {
				camposEditados
						.add("Número de empleados de la organización: " + logroRecurso.getNumEmpleados().intValue());
			}
			if (logroRecursoAnterior.getNumPersonasCargo() != logroRecurso.getNumPersonasCargo()) {
				camposEditados.add("Número de empleados a cargo: " + logroRecurso.getNumPersonasCargo());
			}
			if (logroRecursoAnterior.getValorRecursoEconomico().longValue() != logroRecurso.getValorRecursoEconomico()
					.longValue()) {
				camposEditados
						.add("Recursos económicos asignados: " + logroRecurso.getValorRecursoEconomico().longValue());
			}
			if (!(logroRecursoAnterior.getLogroSobresaliente().trim()
					.equals(logroRecurso.getLogroSobresaliente().trim()))) {
				camposEditados.add("Logro sobresaliente: " + logroRecurso.getLogroSobresaliente());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 3 && publicacion.getCodPublicacion() != null) {
			PublicacionExt publicacionAnterior = ComunicacionServiciosHV.getpublicacionoporpersona(codPersona);
			if (publicacionAnterior.getCodTipoArticulo() == null) {
				publicacionAnterior.setCodTipoArticulo(null);
			}
			if (publicacion.getCodTipoArticulo() == null) {
				publicacion.setCodTipoArticulo(null);
			}
			if (publicacionAnterior.getCodTipoArticulo() != publicacion.getCodTipoArticulo()) {
				if (publicacion.getCodTipoArticulo() == null) {
					camposEditados.add("Tipo de artículo: Sin seleccionar opción");
				} else {
					camposEditados.add("Tipo de artículo: " + ComunicacionServiciosSis
							.getParametricaporId(new BigDecimal(publicacion.getCodTipoArticulo().intValue()))
							.getNombreParametro());
				}
			}
			if (!(publicacionAnterior.getNombreArticulo().trim().equals(publicacion.getNombreArticulo().trim()))) {
				camposEditados.add("Nombre del artículo: " + publicacion.getNombreArticulo());
			}
			if (publicacionAnterior.getCodTipoPublicacion() == null) {
				publicacionAnterior.setCodTipoPublicacion(null);
			}
			if (publicacion.getCodTipoPublicacion() == null) {
				publicacion.setCodTipoPublicacion(null);
			}
			if (publicacionAnterior.getCodTipoPublicacion() != publicacion.getCodTipoPublicacion()) {
				if (publicacion.getCodTipoPublicacion() == null) {
					camposEditados.add("Tipo de publicación: Sin seleccionar opción");
				} else {
					camposEditados.add("Tipo de publicación: " + ComunicacionServiciosSis
							.getParametricaporId(new BigDecimal(publicacion.getCodTipoPublicacion().intValue()))
							.getNombreParametro());
				}
			}
			if (!(publicacionAnterior.getNombreLibro().trim().equals(publicacion.getNombreLibro().trim()))) {
				camposEditados.add("Nombre del libro: " + publicacion.getNombreLibro());
			}
			if (publicacionAnterior.getCodOtroTipoPublicacion() == null) {
				publicacionAnterior.setCodOtroTipoPublicacion(null);
			}
			if (publicacion.getCodOtroTipoPublicacion() == null) {
				publicacion.setCodOtroTipoPublicacion(null);
			}
			if (publicacionAnterior.getCodOtroTipoPublicacion() != publicacion.getCodOtroTipoPublicacion()) {
				if (publicacion.getCodOtroTipoPublicacion() == null) {
					camposEditados.add("Producción bibliográfica: Sin seleccionar opción");
				} else {
					camposEditados
							.add("Producción bibliográfica: " + publicacion.getCodOtroTipoPublicacion().intValue());
				}
			}
			if (!(publicacionAnterior.getNombrePublicacion().trim()
					.equals(publicacion.getNombrePublicacion().trim()))) {
				camposEditados.add("Nombre de la publicación: " + publicacion.getNombrePublicacion());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 4 && evaluacion.getCodEvaluacionDesempeno() != null) {
			EvaluacionDesempenoExt evaluacionAnterior = ComunicacionServiciosHV
					.getEvaluacionDesempenoPorPersona(codPersona, null);
			if (evaluacionAnterior.getCodEntidad().longValue() != evaluacion.getCodEntidad().longValue()) {
				camposEditados.add("Nombre de entidad: "
						+ ComunicacionServiciosHV.getEntidadPorId333(evaluacion.getCodEntidad()).getNombreEntidad());
			}
			if (evaluacionAnterior.getCargoEntidadPrivada() == null) {
				evaluacionAnterior.setCargoEntidadPrivada("");
			}
			if (evaluacion.getCargoEntidadPrivada() == null) {
				evaluacion.setCargoEntidadPrivada("");
			}
			if (!(evaluacionAnterior.getCargoEntidadPrivada().trim()
					.equals(evaluacion.getCargoEntidadPrivada().trim()))) {
				camposEditados.add("Cargo en empresa privada: " + evaluacion.getCargoEntidadPrivada());
			}
			if (evaluacionAnterior.getResultadoEvaluacion().intValue() != evaluacion.getResultadoEvaluacion()
					.intValue()) {
				camposEditados.add("Calificación obtenida: " + evaluacion.getResultadoEvaluacion().intValue());
			}
			if (!(evaluacionAnterior.getEscalaEvaluacion().trim().equals(evaluacion.getEscalaEvaluacion().trim()))) {
				camposEditados.add("Escala de calificación: " + evaluacion.getEscalaEvaluacion());
			}
			if (!(evaluacionAnterior.getFechaInicioString().trim().equals(evaluacion.getFechaInicioString().trim()))) {
				camposEditados.add("Fecha de inicio: " + evaluacion.getFechaInicioString());
			}
			if (!(evaluacionAnterior.getFechaFinalString().trim().equals(evaluacion.getFechaFinalString().trim()))) {
				camposEditados.add("Fecha Fin: " + evaluacion.getFechaFinalString());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 5 && reconocimiento.getCodReconocimiento() != null) {
			ReconocimientoExt reconocimientoAnterior = ComunicacionServiciosHV.getreconocimientoporpersona(codPersona);
			if (reconocimientoAnterior.getFlgEsPremio() != reconocimiento.getFlgEsPremio()) {
				if (reconocimiento.getFlgEsPremio()!=null && reconocimiento.getFlgEsPremio().equals(0)) {
					camposEditados.add("Es un premio");
				} else {
					camposEditados.add("Es un reconocimiento");
				}
			}
			if (!(reconocimientoAnterior.getNombreEntidad().trim().equals(reconocimiento.getNombreEntidad().trim()))) {
				camposEditados.add("Nombre de la entidad: " + reconocimiento.getNombreEntidad());
			}
			if (!(reconocimientoAnterior.getFechaReconocimientoString().trim()
					.equals(reconocimiento.getFechaReconocimientoString().trim()))) {
				camposEditados.add("Fecha de la premiación: " + reconocimiento.getFechaReconocimientoString());
			}
			if (reconocimientoAnterior.getCodPais().intValue() != reconocimiento.getCodPais().intValue()) {
				camposEditados.add("Pais de residencia: " + ComunicacionServiciosSis
						.getpisporid(Long.parseLong("" + reconocimiento.getCodPais())).getNombrePais());
			}
			if (reconocimientoAnterior.getCodDepartamento() != reconocimiento.getCodDepartamento()) {
				if (reconocimiento.getCodDepartamento() == null) {
					reconocimiento.setCodDepartamento(null);
					camposEditados.add("Se escogio pais diferente a colombia");
				} else {
					camposEditados.add("Departamento: " + ComunicacionServiciosSis
							.getdeptoporid(reconocimiento.getCodDepartamento()).getNombreDepartamento());
				}
			}
			if (reconocimientoAnterior.getCodMunicipio() != reconocimiento.getCodMunicipio()) {
				if (reconocimiento.getCodMunicipio() == null) {
					reconocimiento.setCodMunicipio(null);
					camposEditados.add("Se escogio pais diferente a colombia");
				} else {
					camposEditados.add("Municipio: " + ComunicacionServiciosSis
							.getMunicipiosid(reconocimiento.getCodMunicipio()).getNombreMunicipio());
				}
			}

			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 6 && participacionProyecto.getCodParticipacionProyecto() != null) {
			ParticipacionProyectoExt participacionProyectoAnterior = ComunicacionServiciosHV
					.getparticipacionProyeporpersona(codPersona);
			if (!(participacionProyectoAnterior.getNombreProyecto().trim()
					.equals(participacionProyecto.getNombreProyecto().trim()))) {
				camposEditados.add("Nombre del proyecto: " + participacionProyecto.getNombreProyecto());
			}
			if (!(participacionProyectoAnterior.getRolLaborado().trim()
					.equals(participacionProyecto.getRolLaborado().trim()))) {
				camposEditados.add("Rol desempeñado: " + participacionProyecto.getRolLaborado());
			}
			if (!(participacionProyectoAnterior.getNombreEntidad().trim()
					.equals(participacionProyecto.getNombreEntidad().trim()))) {
				camposEditados.add("Nombre de la entidad: " + participacionProyecto.getNombreEntidad());
			}
			if (participacionProyectoAnterior.getCodPais().intValue() != participacionProyecto.getCodPais()
					.intValue()) {
				camposEditados.add("Pais de residencia: " + ComunicacionServiciosSis
						.getpisporid(Long.parseLong("" + participacionProyecto.getCodPais())).getNombrePais());
			}
			if (participacionProyectoAnterior.getCodDepartamento() != participacionProyecto.getCodDepartamento()) {
				if (participacionProyecto.getCodDepartamento() == null) {
					participacionProyecto.setCodDepartamento(null);
					camposEditados.add("Se escogio pais diferente a colombia");
				} else {
					camposEditados.add("Departamento: " + ComunicacionServiciosSis
							.getdeptoporid(participacionProyecto.getCodDepartamento()).getNombreDepartamento());
				}
			}
			if (participacionProyectoAnterior.getCodMunicipio() != participacionProyecto.getCodMunicipio()) {
				if (participacionProyecto.getCodMunicipio() == null) {
					participacionProyecto.setCodMunicipio(null);
					camposEditados.add("Se escogio pais diferente a colombia");
				} else {
					camposEditados.add("Municipio: " + ComunicacionServiciosSis
							.getMunicipiosid(participacionProyecto.getCodMunicipio()).getNombreMunicipio());
				}
			}
			if (!(participacionProyectoAnterior.getFechaInicialString().trim()
					.equals(participacionProyecto.getFechaInicialString().trim()))) {
				camposEditados.add("Fecha de inicio: " + participacionProyecto.getFechaInicialString());
			}
			if (!(participacionProyectoAnterior.getFechaFinalString().trim()
					.equals(participacionProyecto.getFechaFinalString().trim()))) {
				camposEditados.add("Fecha fin: " + participacionProyecto.getFechaFinalString());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else if (codFormulario.intValue() == 7 && participacionInstitucion.getCodParticipacionInstitucion() != null) {
			ParticipacionInstitucion participacionInstitucionAnterior = ComunicacionServiciosHV
					.getparticipacionInstitucionporpersona(codPersona);
			if (!(participacionInstitucionAnterior.getNombreEntidadOrganizacion().trim()
					.equals(participacionInstitucion.getNombreEntidadOrganizacion().trim()))) {
				camposEditados
						.add("Nombre de la corporación: " + participacionInstitucion.getNombreEntidadOrganizacion());
			}
			if (!(participacionInstitucionAnterior.getNombreRazonSocialInstitucion().trim()
					.equals(participacionInstitucion.getNombreRazonSocialInstitucion().trim()))) {
				camposEditados.add("Razón social: " + participacionInstitucion.getNombreRazonSocialInstitucion());
			}
			if (!(participacionInstitucionAnterior.getNombreInstitucion().trim()
					.equals(participacionInstitucion.getNombreInstitucion().trim()))) {
				camposEditados.add("Nombre de la entidad: " + participacionInstitucion.getNombreInstitucion());
			}
			if (camposEditados.size() > 0) {
				System.out.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> "
						+ new Gson().toJson(camposEditados));
			} else {
				System.out
						.println("EducacionFormalBean.guardarConfirmacionDatos() CAMPOS EDITADOS --> SIN MODIFICACION");
			}
		} else {
			camposEditados.add("Se ha agregado un nuevo registro.");
		}

		camposEditados.add("Modificado Por : " + this.getUsuarioSesion().getNumeroIdentificacion() + " - "
				+ this.getUsuarioSesion().getNombrePersona());
		camposEditados.add("Entidad que Modifica : " + this.getEntidadUsuario().getNombreEntidad());

		switch (codFormulario) {
		case 1:
			this.guardarDatosAdicionales();
			break;
		case 2:
			this.guardarLogroRecurso();
			break;
		case 3:
			this.guardarPublicacion();
			break;
		case 4:
			this.guardarEvaluacionDesempeno();
			break;
		case 5:
			this.guardarReconocimiento();
			break;
		case 6:
			this.guardarParticipacionProyecto();
			break;
		case 7:
			this.guardarParticipacionInstitucion();
			break;
		default:
			break;
		}

		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(persona.getCodPersona());
		hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);

		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);

		modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());

		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);

		codFormulario = null;
		
		PersonaExt personaControlInterno = new PersonaExt();
		personaControlInterno.setCodRol(new BigDecimal("10"));
		personaControlInterno.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
		List<PersonaExt> listaPersonalControlInterno = ComunicacionServiciosHV
				.getPersonaControlInterno(personaControlInterno);
		List<String> email = new ArrayList<>();
		List<String> ccEmail = new ArrayList<>();

		try {
			for (PersonaExt personaExt : listaPersonalControlInterno) {
				ccEmail.add(personaExt.getCorreoElectronico());
			}
			email.add(persona.getCorreoElectronico());
			HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(persona.getNombreUsuario(),
					modificacionHojaVida.getAudFechaModificacion(), personaControlInterno.getNombreUsuario(), email,
					ccEmail, camposEditados);
		} catch (Exception e) {
			logger.error("void guardarConfirmacionDatos() - enviar correo", e);
		}
	}

	public void cancelarConfirmacionDatos() {
		switch (codFormulario) {
		case 1:
			this.cancelarDatosAdicionales();
			break;
		case 2:
			this.cancelarLogroRecurso();
			break;
		case 3:
			this.cancelarGuardarPublicacion();
			break;
		case 4:
			this.cancelarEvaluacionDesempeno();
			break;
		case 5:
			this.cancelarReconocimiento();
			break;
		case 6:
			this.cancelarParticipacionProyecto();
			break;
		case 7:
			this.cancelarParticipacionInstitucion();
			break;
		default:
			break;
		}

		codFormulario = null;
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogGerenciaPublica').hide();");
	}

	public void validarIngresoAdministracion() {
		try {
			Boolean flgValidRolPermission = this.usuarioTieneRolAsignado(RolDTO.SERVIDOR_PUBLICO,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_ENTIDADES);
			if (flgValidRolPermission == false) {
				this.finalizarConversacion("/persona/informacionPersonal.xhtml",
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		} catch (IOException e) {
			logger.error("void init() finalizarConversacion", e);
		}
	}

	public void confirmarCancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').show();");
	}

	public void cancelarCancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').hide();");
	}

	@Override
	/**
	 * Metodo para hacer back hacia la página <b>index.xhtml</b>
	 */
	public void cancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').hide();");

		if (flgValidRolPermission) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("gestionarHojaDeVida.xhtml?");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("informacionPersonal.xhtml?");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}