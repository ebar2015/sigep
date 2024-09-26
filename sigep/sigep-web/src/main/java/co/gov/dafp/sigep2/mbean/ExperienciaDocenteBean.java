package co.gov.dafp.sigep2.mbean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;
import com.ibm.icu.text.SimpleDateFormat;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.InstitucionEducativa;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.InstitucionEducativaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean
public class ExperienciaDocenteBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -8294926268304335684L;

	/**
	 * Variables que guardan informacion relacionada con l
	 */
	private InstitucionEducativa ins;
	private UsuarioDTO usuario;
	private PersonaExt persona;
	private EditarDireccionDTO editarDireccion;
	private ExperienciaDocenteDTO experienciaDocente;
	private HistoricoModificacionHojaVida modificacionHojaVida;
	private ExperienciaDocenteExt detalleExperienciaDocente;
	private UploadedFile cargarExpDocente = null;

	private boolean mostrarDatosPorPais;
	private boolean actualizarDatosExperienciaDocente;
	private boolean habilitarControles;
	private boolean mostratEditarDireccion;
	private boolean habilitarFormulario = false;
	private boolean habilitarFechaRetiro = false;
	private boolean deshabilitarHorasMesExpDoc = true; // variable para habilitar datos de hora y mes
	private Boolean flgValidRolPermission = false;
	private Boolean flgValidadoDocumento = Boolean.FALSE;
	private Boolean flgDireccionRequerida = Boolean.FALSE;

	private List<ExperienciaDocenteExt> listExperienciaDocenteExt;
	private List<InstitucionEducativaExt> listInstEducativa;
	private List<ExperienciaDocenteExt> listDateExperienciaDocenteExt; // variable para la lista de experiencias utilizada para el calculo del tiempo
	
	private String nombreInstitucion;
	private String banderaPais = "/resources/images/banderas/co.png";

	private long codPersona = getUsuarioSesion().getCodPersona();

	private long anio = 0;
	private long mes = 0;
	private long dia = 0;
	private int auxMes = 0;
	private int tiempoLaborado = 0;
	static final int DIAS_ANIO = 365;
	static final int DIAS_MES = 30;
	private String nombreArchivo;
	private String rutaArchivo;
	/* HabilitarDeshabilitar direccion segun tipo de zona */
	private Integer codTipoZonaRural, codTipoZonaUrbana;
	private boolean flgDisabledDireccionTexto, flgDisabledDireccionBoton;
	static final long COD_JEFE_CONTROL_INTERNO = 15; // variable para envio de correos desde modificar HV

	/**
	 * @return el flgDireccionRequerida
	 */
	public Boolean getFlgDireccionRequerida() {
		return flgDireccionRequerida;
	}

	/**
	 * @param flgDireccionRequerida
	 *            el flgDireccionRequerida a establecer
	 */
	public void setFlgDireccionRequerida(Boolean flgDireccionRequerida) {
		this.flgDireccionRequerida = flgDireccionRequerida;
	}

	public Boolean getFlgValidadoDocumento() {
		return flgValidadoDocumento;
	}

	public void setFlgValidadoDocumento(Boolean flgValidadoDocumento) {
		this.flgValidadoDocumento = flgValidadoDocumento;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public boolean isHabilitarControles() {
		return habilitarControles;
	}

	public void setHabilitarControles(boolean habilitarControles) {
		this.habilitarControles = habilitarControles;
	}

	public ExperienciaDocenteDTO getExperienciaDocente() {
		return experienciaDocente;
	}

	public void setExperienciaDocente(ExperienciaDocenteDTO experienciaDocente) {
		this.experienciaDocente = experienciaDocente;
	}
	
	public int getTiempoLaborado() {
		return tiempoLaborado;
	}

	public void setTiempoLaborado(int tiempoLaborado) {
		this.tiempoLaborado = tiempoLaborado;
	}


	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public boolean getMostrarDatosPorPais() {
		return mostrarDatosPorPais;
	}

	public void setMostrarDatosPorPais(boolean mostrarDatosPorPais) {
		this.mostrarDatosPorPais = mostrarDatosPorPais;
	}

	public boolean isActualizarDatosExperienciaDocente() {
		return actualizarDatosExperienciaDocente;
	}

	public void setActualizarDatosExperienciaDocente(boolean actualizarDatosExperienciaDocente) {
		this.actualizarDatosExperienciaDocente = actualizarDatosExperienciaDocente;
	}

	public ExperienciaDocenteExt getDetalleExperienciaDocente() {
		return detalleExperienciaDocente;
	}

	public void setDetalleExperienciaDocente(ExperienciaDocenteExt detalleExperienciaDocente) {
		this.detalleExperienciaDocente = detalleExperienciaDocente;
	}


	public long getAnio() {
		return anio;
	}

	public void setAnio(long anio) {
		this.anio = anio;
	}

	public long getMes() {
		return mes;
	}

	public void setMes(long mes) {
		this.mes = mes;
	}

	public long getDia() {
		return dia;
	}

	public void setDia(long dia) {
		this.dia = dia;
	}
	
	public int getAuxMes() {
		return auxMes;
	}

	public void setAuxMes(int auxMes) {
		this.auxMes = auxMes;
	}

	public UploadedFile getCargarExpDocente() {
		return cargarExpDocente;
	}

	public void setCargarExpDocente(UploadedFile cargarExpDocente) {
		this.cargarExpDocente = cargarExpDocente;
	}

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	public boolean isHabilitarFormulario() {
		return habilitarFormulario;
	}

	public void setHabilitarFormulario(boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	public boolean isMostratEditarDireccion() {
		return mostratEditarDireccion;
	}

	public void setMostratEditarDireccion(boolean mostratEditarDireccion) {
		this.mostratEditarDireccion = mostratEditarDireccion;
	}

	public EditarDireccionDTO getEditarDireccion() {
		return editarDireccion;
	}

	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}

	public List<ExperienciaDocenteExt> getListExperienciaDocenteExt() {
		return listExperienciaDocenteExt;
	}

	public void setListExperienciaDocenteExt(List<ExperienciaDocenteExt> listExperienciaDocenteExt) {
		this.listExperienciaDocenteExt = listExperienciaDocenteExt;
	}

	public boolean isHabilitarFechaRetiro() {
		return habilitarFechaRetiro;
	}

	public void setHabilitarFechaRetiro(boolean habilitarFechaRetiro) {
		this.habilitarFechaRetiro = habilitarFechaRetiro;
	}

	public boolean isDeshabilitarHorasMesExpDoc() {
		return deshabilitarHorasMesExpDoc;
	}

	public void setDeshabilitarHorasMesExpDoc(boolean deshabilitarHorasMes) {
		this.deshabilitarHorasMesExpDoc = deshabilitarHorasMes;
	}

	public String getBanderaPais() {
		return banderaPais;
	}

	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public InstitucionEducativa getIns() {
		return ins;
	}

	public void setIns(InstitucionEducativa ins) {
		this.ins = ins;
	}

	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	@PostConstruct
	public void init() {
		rutaArchivo = null;
		this.initialization();

		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
		if (!listExperienciaDocenteExt.isEmpty()) {
			for (int i = 0; i < listExperienciaDocenteExt.size(); i++) {
				listExperienciaDocenteExt.get(i).setUrlDocumentoSoporte(WebUtils.validarUrl(
						listExperienciaDocenteExt.get(i).getUrlDocumentoSoporte(), persona.getNumeroIdentificacion(),
						listExperienciaDocenteExt.get(i).getCodExperienciaDocente() + "", WebUtils.TP_DOC_EXP_DOCENTE));
			}
		}

		experienciaDocente = new ExperienciaDocenteDTO();
		detalleExperienciaDocente = new ExperienciaDocenteExt();
		editarDireccion = new EditarDireccionDTO();
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		listInstEducativa = new ArrayList<>();
		habilitarControles = true;
		habilitarFechaRetiro = true;
		nombreInstitucion = "";

		this.mostrarPanelDatosPorPais();
		this.calcularTiempoLaborado();

		codTipoZonaRural = Integer.valueOf(TipoParametro.COD_TIPO_ZONA_RURAL.getValue());
		codTipoZonaUrbana = Integer.valueOf(TipoParametro.COD_TIPO_ZONA_URBANA.getValue());
	}

	/**
	 * Metodo que valida si el usuario tiene permiso para ver la funcionalidad
	 */

	public void initialization() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String idPersona = request.getParameter("id");

		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO,
							RolDTO.AUDITOR, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
							RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS,
							RolDTO.OPERADOR_TALENTO_HUMANO);
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
					Boolean flgValidPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA,
							RolDTO.SERVIDOR_PUBLICO);
					if (flgValidPermission == false) {
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

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext,
			null, "datosPersonalesBean");

	public String guardarExperienciaDocente() {
		if (mBDatosPersonalesBean.isLbISGestionarHV()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogExpDocente').show()");
		} else {
			persistExperienciaDocenteSinConfirmar();
		}
		return null;
	}

	/**
	 * Metodo que Guarda una experiencia Docente
	 */
	public boolean persistExperienciaDocenteSinConfirmar() {
		if (detalleExperienciaDocente.getDireccion() == null
				|| detalleExperienciaDocente.getDireccion().trim().length() < 1) {
			this.flgDireccionRequerida = true;
			return false;
		}

		if (detalleExperienciaDocente.getHorasSemana() != null && detalleExperienciaDocente.getHorasSemana() == 0) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INV_NUMERO_HORAS_CATEDRA);
			return false;
		}

		detalleExperienciaDocente.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		detalleExperienciaDocente.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		detalleExperienciaDocente.setAudCodRol((int) this.getRolAuditoria().getId());
		detalleExperienciaDocente.setAudFechaActualizacion(DateUtils.getFechaSistema());
		detalleExperienciaDocente.setFlgActivo(true);

		if (!listInstEducativa.isEmpty() && listInstEducativa.size() > 0) {
			for (int i = 0; i < listInstEducativa.size(); i++) {
				if (listInstEducativa.get(i).getNombreInstitucion().equals(this.nombreInstitucion)) {
					detalleExperienciaDocente
							.setCodInstitucion(listInstEducativa.get(i).getCodInstitucionEducativa().intValue());
				}
			}
		}

		if (detalleExperienciaDocente.getCodExperienciaDocente() == null) {
			detalleExperienciaDocente.setCodPersona(codPersona);
			detalleExperienciaDocente.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		boolean valid = ComunicacionServiciosHV.setExperiancaDoc(detalleExperienciaDocente);

		if (valid == true) {
			detalleExperienciaDocente = new ExperienciaDocenteExt();
			habilitarFormulario = true;
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA_EXP_DOCENTE);
			listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
			calcularTiempoLaborado();
			habilitarFormulario = false;
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			return false;
		}
		return true;
	}

	/**
	 * Metodo para ver una experiencia Laboral
	 * 
	 * @param id
	 *            Variable que contiene el codigo de la experiencia a visualizar
	 */

	public void verExperienciaDocente(long id) {
		detalleExperienciaDocente = ComunicacionServiciosHV.getExperianciaDoc001(id);
		if (detalleExperienciaDocente.getCodInstitucion() != null) {
			ins = ComunicacionServiciosEnt.getInstitucionEducaporId(detalleExperienciaDocente.getCodInstitucion());
			nombreInstitucion = ins.getNombreInstitucion();
		} else {
			nombreInstitucion = "";
		}

		habilitarFormulario = true;
		habilitarControles = true;
		cargarExpDocente = null;
		mostrarPanelDatosPorPais();
		changeState();
		habilitaDireccion();
		calcularTiempoExperienciaDocente();
	}

	public void actualizarExperienciaDocente(long id) {

		this.flgValidadoDocumento = false;

		mostrarPanelEditarDireccion(false);
		detalleExperienciaDocente = ComunicacionServiciosHV.getExperianciaDoc001(id);
		if (detalleExperienciaDocente.getCodInstitucion() != null) {
			ins = ComunicacionServiciosEnt.getInstitucionEducaporId(detalleExperienciaDocente.getCodInstitucion());
			nombreInstitucion = ins.getNombreInstitucion();
		} else {
			nombreInstitucion = "";
		}

		cargarExpDocente = null;

		habilitarFormulario = true;
		habilitarControles = false;
		mostrarPanelDatosPorPais();
		changeState();
		calcularTiempoExperienciaDocente();
		if (detalleExperienciaDocente.isFlgVerificado() && this.flgValidRolPermission) {
			this.flgValidadoDocumento = true;
		}

		if (detalleExperienciaDocente != null && detalleExperienciaDocente.getUrlDocumentoSoporte() != null)
			this.setNombreArchivo("CertificadoExperienciaLaboralDocente.pdf");
		habilitaDireccion();
	}

	public void desactivarExperienciaDocente(long id) {
		try {
			ExperienciaDocenteExt expDocente;
			expDocente = ComunicacionServiciosHV.getExperianciaDoc001(id);
			if (expDocente != null) {
				expDocente.setFlgActivo(false);
				expDocente.setAudFechaActualizacion(DateUtils.getFechaSistema());
				expDocente.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
				expDocente.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
				expDocente.setAudCodRol((int) this.getRolAuditoria().getId());
				ComunicacionServiciosHV.setExperiancaDoc(expDocente);
				calcularTiempoLaborado();
				mBDatosPersonalesBean.actualizaModificacionHv();
				listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_ELIMINACION_EXITOSA, "");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_ELIMINACION_FALLIDA, "");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	public List<String> listarInstitucionEducativa(String entidadPrivQuery) {
		List<String> filtroListInsEducativa = new ArrayList<>();
		InstitucionEducativa filtroInstitucionEducativa = new InstitucionEducativa();
		if (!entidadPrivQuery.equals("")) {
			filtroInstitucionEducativa.setNombreInstitucion(entidadPrivQuery);
		}
		filtroInstitucionEducativa.setFlgActivo((short) 1);
		listInstEducativa = ComunicacionServiciosAdmin.obtenerInstitucionEducativaFiltro(filtroInstitucionEducativa);

		for (InstitucionEducativa insEducativa : listInstEducativa) {
			filtroListInsEducativa.add(insEducativa.getNombreInstitucion());
		}
		return filtroListInsEducativa;
	}
	
	/**
	 * Metodo que se encarga de obtener la lista de experiencias, para realizar el calculo de el tiempo total laborado
	 */
	private void obtenerExperiencasCalculo() {
		listDateExperienciaDocenteExt = ComunicacionServiciosHV.getDateExperienciasDocente(codPersona);
	}
	
	public void calcularTiempoLaborado() {
		obtenerExperiencasCalculo();
		anio 	= 0;
		mes 	= 0;
		dia 	= 0;
		auxMes 	= 0;
		tiempoLaborado = 0;

		tiempoLaborado = DatosPersonalesBean.calculartiempoExperienciaDocente(listDateExperienciaDocenteExt);

		if (tiempoLaborado >= DIAS_ANIO) {
			anio = tiempoLaborado / DIAS_ANIO;
			auxMes = tiempoLaborado % DIAS_ANIO;
			if (auxMes >= DIAS_MES) {
				mes = auxMes / DIAS_MES;
				dia = auxMes % DIAS_MES;
			} else {
				dia = auxMes;
			}
		} else {
			anio = 0;
			mes = tiempoLaborado / DIAS_MES;
			dia = tiempoLaborado % DIAS_MES;
		}

		if (tiempoLaborado < DIAS_MES) {
			dia = tiempoLaborado;
		}
	}
	
	public void calcularTiempoExperienciaDocente() {
		String dateC = "";
		if (detalleExperienciaDocente.getCodJornadaLaboral() != null) {
			if (detalleExperienciaDocente.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())) {
				deshabilitarHorasMesExpDoc = false;
			} else {
				deshabilitarHorasMesExpDoc = true;
			}
		}
		ExperienciaProfesionalExt objDatosParaCalcularExpDoc = new ExperienciaProfesionalExt();
		objDatosParaCalcularExpDoc.setFechaIngreso(detalleExperienciaDocente.getFechaIngreso());
		objDatosParaCalcularExpDoc.setFechaRetiro(detalleExperienciaDocente.getFechaFinalizacion());
		objDatosParaCalcularExpDoc.setCodJornadaLaboral(detalleExperienciaDocente.getCodJornadaLaboral());
		objDatosParaCalcularExpDoc.setHorasPromedioMes(detalleExperienciaDocente.getHorasPromedioMes());
		List<ExperienciaProfesionalExt> lstExperiencias = new ArrayList<>();
		if (objDatosParaCalcularExpDoc != null)
			lstExperiencias.add(objDatosParaCalcularExpDoc);
		Long[] tiempos = DatosPersonalesBean.calculartiempoExperienciaprofesional(lstExperiencias);
		long annos = 0; // variable para el valor año
		long meses = 0;// variable para el valor mes
		long dias = 0; // variable para el valor dia
		annos = tiempos[3];
		meses = tiempos[4];
		dias = tiempos[5];
		dateC = annos + " Años " + meses + " Meses " + dias + " Días";
		detalleExperienciaDocente.setTiempoExperienciaExpDoc(dateC);
	}

	public void habilitarHorasPromedioMes() {
		if (detalleExperienciaDocente.getCodJornadaLaboral() != null) {
			if (detalleExperienciaDocente.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())) {
				this.deshabilitarHorasMesExpDoc = false;
			} else {
				this.deshabilitarHorasMesExpDoc = true;
				detalleExperienciaDocente.setHorasPromedioMes(null);
			}
			if (detalleExperienciaDocente.getCodJornadaLaboral() != null
					&& detalleExperienciaDocente.getFechaIngreso() != null) {
				this.calcularTiempoExperienciaDocente();
			}

		} else {
			this.deshabilitarHorasMesExpDoc = true;
			detalleExperienciaDocente.setHorasPromedioMes(null);
		}
	}

	public void mostrarPanelDatosPorPais() {
		if (detalleExperienciaDocente.getCodPais() != null) {
			detalleExperienciaDocente.setIndicativoTelefono(null);
			Pais pais = ComunicacionServiciosSis.getpisporid(detalleExperienciaDocente.getCodPais());
			if (pais.getNombrePais() != null && pais.getNombrePais().toUpperCase().equals("COLOMBIA")) {
				mostrarDatosPorPais = true;
			} else {
				mostrarDatosPorPais = false;
				detalleExperienciaDocente.setCodDepartamento(null);
				detalleExperienciaDocente.setCodCiudad(null);
			}
			if (pais.getIndicativoPais() != null) {
				detalleExperienciaDocente.setIndicativoTelefono("+" + pais.getIndicativoPais());
			}

			if (pais.getBanderaUrl() != null) {
				this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
			} else {
				this.setBanderaPais("/resources/images/banderas/default.png");
			}
		} else {
			mostrarDatosPorPais = true;

			Pais paisdefecto = ComunicacionServiciosSis.getpisporid(169);

			if (paisdefecto.getIndicativoPais() != null) {
				detalleExperienciaDocente.setIndicativoTelefono("+" + paisdefecto.getIndicativoPais());
			}

			if (paisdefecto.getBanderaUrl() != null) {
				this.setBanderaPais(paisdefecto.getBanderaUrl().toLowerCase());
			} else {
				this.setBanderaPais("/resources/images/banderas/default.png");
			}

		}
	}

	public void agregarIndicativoDepartamento() {
		if (detalleExperienciaDocente.getCodDepartamento() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(detalleExperienciaDocente.getCodPais());
			Departamento departamento = ComunicacionServiciosSis
					.getdeptoporid(detalleExperienciaDocente.getCodDepartamento());
			String indicativo = "+" + pais.getIndicativoPais() + departamento.getIndicativo();
			detalleExperienciaDocente.setIndicativoTelefono(indicativo);
		}
	}

	public void uploadDocumentoExpDocente(FileUploadEvent e) throws IOException {
		cargarExpDocente = e.getFile();
		byte[] bytes = null;
		try {
			if (null != cargarExpDocente) {
				Date fechaActual = new Date();
				bytes = cargarExpDocente.getContents();
				String ext = FilenameUtils.getExtension(cargarExpDocente.getFileName());
				String filename = fechaActual.getTime() + "." + ext;
				String ruta = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.CNS_RUTA_DOC_EXP_DOCENTE) + filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try {
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
					stream.write(bytes);
					stream.close();
				} catch (IOException err) {
					logger.log().error("Error al trata de escribir en ExpDocente el archivo en la ruta:" + ruta,
							err.getMessage());
				}
				ErrorMensajes resp = null;
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")) {
					String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
							ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOC_EXP_DOCENTE,
							WebUtils.TP_DOC_EXP_DOCENTE, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				} else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")
						|| DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")) {/* Operacion cliente windows */
					resp = DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOC_EXP_DOCENTE,
							WebUtils.TP_DOC_EXP_DOCENTE, filePath, persona.getNumeroIdentificacion());
				}
				if (!resp.isError()) {
					detalleExperienciaDocente.setUrlDocumentoSoporte(resp.getUrlArchivo());
					this.setNombreArchivo(filename);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA,
											getLocale())
									.replace("%nombrearchivo%", resp.getNombreArchivo()));
				} else {
					detalleExperienciaDocente.setUrlDocumentoSoporte(null);
					this.setNombreArchivo(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR,
									getLocale()));// TODO: handle exception
				}
			}

		} catch (Exception e2) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR,
							getLocale()));// TODO: handle exception
		}

	}

	public void changeState() {
		if (detalleExperienciaDocente.isFlgActualmente()) {
			this.habilitarFechaRetiro = false;
			detalleExperienciaDocente.setFechaFinalizacion(null);
		} else {
			this.habilitarFechaRetiro = true;
		}
	}

	public void habilitarFormularioExpDocente(boolean valid) {
		detalleExperienciaDocente = new ExperienciaDocenteExt();
		nombreArchivo = "";
		cargarExpDocente = null;
		nombreInstitucion = "";
		habilitarFormulario = valid;
		habilitarControles = false;
		detalleExperienciaDocente.setCodPais(169);
		mostrarPanelDatosPorPais();
		calcularTiempoExperienciaDocente();
		if (valid)
			habilitaDireccion();
		deshabilitarHorasMesExpDoc = true;
	}

	@Override
	public String persist() throws NotSupportedException {
		return null;
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
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@Override
	public void delete() throws NotSupportedException {
	}

	/**
	 * Metrodo para mostrar formulario de generar direccion
	 */
	public void mostrarPanelEditarDireccion(boolean value) {
		this.flgDireccionRequerida = false;
		editarDireccion = new EditarDireccionDTO();
		mostratEditarDireccion = value;
	}

	/**
	 * Metrodo para generar la direccion
	 */
	public void generarDireccion() {
		String primerDirecion = "";
		String segundaDirecion = "";

		if (editarDireccion.getTipoVia() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getTipoVia().getSigla();
		}

		if (editarDireccion.getSegundoNumero() != null && !editarDireccion.getSegundoNumero().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundoNumero();
		}

		if (editarDireccion.getNumero() != null && !editarDireccion.getNumero().isEmpty()) {
			primerDirecion = primerDirecion + " " + editarDireccion.getNumero();
		}

		if (editarDireccion.getTercerLetra() != null) {
			segundaDirecion = segundaDirecion + editarDireccion.getTercerLetra().getSigla();
		}

		if (editarDireccion.getPrimerLetra() != null) {
			primerDirecion = primerDirecion + editarDireccion.getPrimerLetra().getSigla();
		}

		if (editarDireccion.isBis()) {
			primerDirecion = primerDirecion + " BIS";
		}

		if (editarDireccion.getSegundaLetra() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getSegundaLetra().getSigla();
		}

		if (editarDireccion.getOrientacion() != null) {
			primerDirecion = primerDirecion + " " + editarDireccion.getOrientacion().getSigla();
		}

		if (editarDireccion.getTercerNumero() != null && !editarDireccion.getTercerNumero().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getTercerNumero();
		}

		if (editarDireccion.getSegundaOrientacion() != null) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getSegundaOrientacion().getSigla();
		}

		if (editarDireccion.getComplemento() != null && !editarDireccion.getComplemento().isEmpty()) {
			segundaDirecion = segundaDirecion + " " + editarDireccion.getComplemento();
		}

		segundaDirecion = segundaDirecion == "" ? "" : " -" + segundaDirecion;
		editarDireccion.setDireccionGenerada(primerDirecion + segundaDirecion);
	}

	/**
	 * metodo para agregar la direccion generada en el objeto a guardar
	 */
	public void agergarDireccion() {
		detalleExperienciaDocente.setDireccion(editarDireccion.getDireccionGenerada());
		mostratEditarDireccion = false;
	}

	public void confirmarDatosGuardar() {
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		guardarConfirmacionDatos();
	}

	public void guardarConfirmacionDatos() {

		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(persona.getCodPersona());
		hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);

		List<String> camposEditados = new ArrayList<>();
		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);

		if (detalleExperienciaDocente.getCodExperienciaDocente() != null) {
			ExperienciaDocenteExt detalleExperienciaDocenteAnterior = ComunicacionServiciosHV
					.getExperianciaDoc001(detalleExperienciaDocente.getCodExperienciaDocente());

			if (detalleExperienciaDocente.getFlgEntidadPublica() != detalleExperienciaDocenteAnterior
					.getFlgEntidadPublica()) {
				String publica = "PRIVADA";
				if (detalleExperienciaDocente.getFlgEntidadPublica()) {
					publica = "PÚBLICA";
				}
				camposEditados.add("Tipo de Institución: " + publica);
			}

			if (detalleExperienciaDocenteAnterior.getNombreInstitucionIe() == null) {
				detalleExperienciaDocenteAnterior.setNombreInstitucionIe("");
			}

			if (detalleExperienciaDocente.getNombreInstitucionIe() == null) {
				detalleExperienciaDocente.setNombreInstitucionIe("");
			}

			if (!detalleExperienciaDocenteAnterior.getNombreInstitucionIe().trim()
					.equals(detalleExperienciaDocente.getNombreInstitucionIe().trim())) {
				camposEditados.add("Nombre Institución: " + detalleExperienciaDocente.getNombreInstitucionIe());
			}

			if (detalleExperienciaDocente.getCodPais() != null
					&& !detalleExperienciaDocente.getCodPais().equals(detalleExperienciaDocenteAnterior.getCodPais())) {
				Pais paisExperiencia = ComunicacionServiciosSis.getpisporid(detalleExperienciaDocente.getCodPais());
				camposEditados.add("Pais Experiencia Docente: " + paisExperiencia.getNombrePais());
			}

			if (detalleExperienciaDocenteAnterior.getCodDepartamento() == null) {
				detalleExperienciaDocenteAnterior.setCodDepartamento(null);
			}

			if (detalleExperienciaDocente.getCodDepartamento() == null) {
				detalleExperienciaDocente.setCodDepartamento(null);
			}

			if (detalleExperienciaDocente.getCodDepartamento() != detalleExperienciaDocenteAnterior
					.getCodDepartamento()) {
				if (detalleExperienciaDocente.getCodDepartamento() == null) {
					detalleExperienciaDocente.setCodDepartamento(null);
					camposEditados.add("Departamento Experiencia Docente: ");
				} else {
					Departamento departamentoExperiencia = ComunicacionServiciosSis
							.getdeptoporid(detalleExperienciaDocente.getCodDepartamento());
					camposEditados.add(
							"Departamento Experiencia Docente: " + departamentoExperiencia.getNombreDepartamento());
				}
			}

			if (detalleExperienciaDocenteAnterior.getCodCiudad() == null) {
				detalleExperienciaDocenteAnterior.setCodCiudad(null);
			}

			if (detalleExperienciaDocente.getCodCiudad() == null) {
				detalleExperienciaDocente.setCodCiudad(null);
			}

			if (detalleExperienciaDocente.getCodCiudad() != detalleExperienciaDocenteAnterior.getCodCiudad()) {
				if (detalleExperienciaDocente.getCodCiudad() == null) {
					detalleExperienciaDocente.setCodCiudad(null);
					camposEditados.add("Ciudad Experiencia Docente: ");
				} else {
					Municipio municipioExperiencia = ComunicacionServiciosSis
							.getMunicipiosid(detalleExperienciaDocente.getCodCiudad());
					camposEditados.add("Ciudad Experiencia Docente: " + municipioExperiencia.getNombreMunicipio());
				}
			}

			if (detalleExperienciaDocenteAnterior.getDireccion() == null) {
				detalleExperienciaDocenteAnterior.setDireccion("");
			}

			if (detalleExperienciaDocente.getDireccion() == null) {
				detalleExperienciaDocente.setDireccion("");
			}

			if (!detalleExperienciaDocenteAnterior.getDireccion().trim()
					.equals(detalleExperienciaDocente.getDireccion().trim())) {
				camposEditados.add("Dirección: " + detalleExperienciaDocente.getDireccion());
			}

			if (detalleExperienciaDocenteAnterior.getTelefono() == null) {
				detalleExperienciaDocenteAnterior.setTelefono("");
			}

			if (detalleExperienciaDocente.getTelefono() == null) {
				detalleExperienciaDocente.setTelefono("");
			}

			if (!detalleExperienciaDocenteAnterior.getTelefono().trim()
					.equals(detalleExperienciaDocente.getTelefono().trim())) {
				camposEditados.add("Teléfono: " + detalleExperienciaDocente.getTelefono());
			}

			if (detalleExperienciaDocenteAnterior.getMateriaImpartida() == null) {
				detalleExperienciaDocenteAnterior.setMateriaImpartida("");
			}

			if (detalleExperienciaDocente.getMateriaImpartida() == null) {
				detalleExperienciaDocente.setMateriaImpartida("");
			}

			if (!detalleExperienciaDocenteAnterior.getMateriaImpartida().trim()
					.equals(detalleExperienciaDocente.getMateriaImpartida().trim())) {
				camposEditados.add("Materia Impartida: " + detalleExperienciaDocente.getMateriaImpartida());
			}

			if (!(detalleExperienciaDocenteAnterior.isFlgActualmente() == detalleExperienciaDocente
					.isFlgActualmente())) {
				String flgActivoEntidad = "";
				if (detalleExperienciaDocente.isFlgActualmente()) {
					flgActivoEntidad = "Si";
				} else {
					flgActivoEntidad = "No";
				}
				camposEditados.add("Laborando Actualmente: " + flgActivoEntidad);
			}

			if (!detalleExperienciaDocenteAnterior.getFechaInicio().trim()
					.equals(detalleExperienciaDocente.getFechaInicio().trim())) {
				camposEditados.add("Fecha Ingreso: " + detalleExperienciaDocente.getFechaInicio());
			}

			if (!detalleExperienciaDocente.isFlgActualmente()) {

				if (detalleExperienciaDocenteAnterior.getFechaFinal() == null) {

					detalleExperienciaDocenteAnterior.setFechaFinal("");
				}

				if (detalleExperienciaDocente.getFechaFinal() == null) {
					detalleExperienciaDocente.setFechaFinal("");
				}

				if (!(detalleExperienciaDocenteAnterior.getFechaFinal().trim()
						.equals(detalleExperienciaDocente.getFechaFinal().trim()))) {
					camposEditados.add("Fecha Retiro	: " + detalleExperienciaDocente.getFechaFinal());
				}
			}

			/*if (detalleExperienciaDocente.getHorasSemana() == 0) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_INV_NUMERO_HORAS_CATEDRA);
				return;
			}*/

//			if (!(detalleExperienciaDocenteAnterior.getHorasSemana().shortValue() == detalleExperienciaDocente
//					.getHorasSemana().shortValue())) {
//				camposEditados.add("Número de horas cátedra semanales: " + detalleExperienciaDocente.getHorasSemana());
//			}

			if (detalleExperienciaDocenteAnterior.getCodNivelEducativo() == null) {
				detalleExperienciaDocenteAnterior.setCodNivelEducativo(0);
			}

			if (detalleExperienciaDocente.getCodNivelEducativo() == null) {
				detalleExperienciaDocente.setCodNivelEducativo(0);
			}

			if (!(detalleExperienciaDocenteAnterior.getCodNivelEducativo().intValue() == detalleExperienciaDocente
					.getCodNivelEducativo().intValue())) {
				if (detalleExperienciaDocente.getCodNivelEducativo().intValue() == 0) {
					detalleExperienciaDocente.setCodNivelEducativo(null);
					camposEditados.add("Nivel Educativo: ");
				} else {
					Parametrica parametrica = ComunicacionServiciosSis
							.getParametricaporId(BigDecimal.valueOf(detalleExperienciaDocente.getCodNivelEducativo()));
					camposEditados.add("Nivel Educativo: " + parametrica.getNombreParametro().toUpperCase());
				}
			}

			if (detalleExperienciaDocenteAnterior.getCodAreaConocimiento() == null) {
				detalleExperienciaDocenteAnterior.setCodAreaConocimiento((long) 0);
			}

			if (detalleExperienciaDocente.getCodAreaConocimiento() == null) {
				detalleExperienciaDocente.setCodAreaConocimiento((long) 0);
			}

			if (!(detalleExperienciaDocenteAnterior.getCodAreaConocimiento().intValue() == detalleExperienciaDocente
					.getCodAreaConocimiento().intValue())) {
				if (detalleExperienciaDocente.getCodAreaConocimiento().intValue() == 0) {
					detalleExperienciaDocente.setCodAreaConocimiento(null);
					camposEditados.add("Área de Conocimiento: ");
				} else {
					Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(
							BigDecimal.valueOf(detalleExperienciaDocente.getCodAreaConocimiento()));
					camposEditados.add("Área de Conocimiento: " + parametrica.getNombreParametro().toUpperCase());
				}
			}

			camposEditados.add("Modificado Por : " + this.getUsuarioSesion().getNumeroIdentificacion() + " - "
					+ this.getUsuarioSesion().getNombrePersona());

			camposEditados.add("Entidad que Modifica : " + this.getEntidadUsuario().getNombreEntidad());
		}

		this.persistExperienciaDocenteSinConfirmar();

		modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());

		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogExpDocente').hide();");

		PersonaExt personaControlInterno = new PersonaExt();
		personaControlInterno.setCodRol(new BigDecimal(COD_JEFE_CONTROL_INTERNO));
		personaControlInterno.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
		List<PersonaExt> listaPersonalControlInterno = ComunicacionServiciosHV
				.getPersonaControlInterno(personaControlInterno);
		List<String> email = new ArrayList<>();
		List<String> ccEmail = new ArrayList<>();
		try {
			mBDatosPersonalesBean.setEditado(false);
			for (PersonaExt personaExt : listaPersonalControlInterno) {
				ccEmail.add(personaExt.getCorreoElectronico());
			}
			email.add(persona.getCorreoElectronico());
			email.add(persona.getCorreoAlternativo());
			HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(persona.getNombreUsuario(),
					modificacionHojaVida.getAudFechaModificacion(), personaControlInterno.getNombreUsuario(), email,
					ccEmail, camposEditados);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelarConfirmacionDatos() {
		this.habilitarFormularioExpDocente(false);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogExpDocente').hide();");
	}

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * Pablo Quintana - 26/09/2018 Método que obtiene la url del anexo
	 * 
	 * @param tipoDocVisualiza
	 *            1. Hoja de vida - Experiencia Laboral Docente
	 */
	public void visualizarArchivoExperienciaDocente(String tipoDocVisualiza) {
		String ruta = "";
		if ("1".equals(tipoDocVisualiza)) {
			if (detalleExperienciaDocente != null && detalleExperienciaDocente.getUrlDocumentoSoporte() != null)
				ruta = detalleExperienciaDocente.getUrlDocumentoSoporte();
		}
		if (ruta == null || "".equals(ruta)) {
			rutaArchivo = null;
		} else {
			if (persona == null) {
				persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			}
			if (detalleExperienciaDocente != null && detalleExperienciaDocente.getUrlDocumentoSoporte() != null)
				rutaArchivo = WebUtils.getUrlFile(detalleExperienciaDocente.getUrlDocumentoSoporte());
		}
	}

	/**
	 * Pablo Quintana 30/01/2019 Método que habilita el campo dirección cuándo el
	 * tipo de zona es rural, y lo eshabilita cuándo el tipo de zona es urbana para
	 * editar con el componente
	 */

	public void habilitaDireccion() {
		if (habilitarControles) {
			flgDisabledDireccionTexto = true;
			flgDisabledDireccionBoton = true;
		} else {
			if (detalleExperienciaDocente != null && detalleExperienciaDocente.getCodTipoZona() != null) {
				if (detalleExperienciaDocente.getCodTipoZona().equals(codTipoZonaRural)) {
					flgDisabledDireccionTexto = false;
					flgDisabledDireccionBoton = true;
				} else if (detalleExperienciaDocente.getCodTipoZona().equals(codTipoZonaUrbana)) {
					flgDisabledDireccionTexto = true;
					flgDisabledDireccionBoton = false;
				} else {
					flgDisabledDireccionTexto = true;
					flgDisabledDireccionBoton = true;
				}
			} else {
				flgDisabledDireccionTexto = true;
				flgDisabledDireccionBoton = true;
			}
		}
	}

	public boolean isFlgDisabledDireccionTexto() {
		return flgDisabledDireccionTexto;
	}

	public void setFlgDisabledDireccionTexto(boolean flgDisabledDireccionTexto) {
		this.flgDisabledDireccionTexto = flgDisabledDireccionTexto;
	}

	public boolean isFlgDisabledDireccionBoton() {
		return flgDisabledDireccionBoton;
	}

	public void setFlgDisabledDireccionBoton(boolean flgDisabledDireccionBoton) {
		this.flgDisabledDireccionBoton = flgDisabledDireccionBoton;
	}

}