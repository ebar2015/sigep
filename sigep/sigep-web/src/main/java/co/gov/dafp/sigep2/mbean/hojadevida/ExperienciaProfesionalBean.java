package co.gov.dafp.sigep2.mbean.hojadevida;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoPeriod;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entities.CargoHojaVida;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.DependenciaHojaVida;
import co.gov.dafp.sigep2.entities.DependenciaHojaVidaExt;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.ExperienciaProfesional;
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.DatosPersonalesBean;
import co.gov.dafp.sigep2.mbean.DireccionBean;
import co.gov.dafp.sigep2.mbean.ext.CargoHojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaVidaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.utils.AuditoriaSeguridad;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * @author Milciades Vargas
 * @version 3.0
 * @Class contiene toda la logica para poder crear,editar,elimnar una
 *        experiencia profesional
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module Hoja de vida
 * @Fecha: 07/06/2018
 */
@Named
@ViewScoped
@ManagedBean
public class ExperienciaProfesionalBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = -7920885168079358180L;

	private Entidad entidad; // variable para el ojb entidad
	private PersonaExt persona; // variable para el ojb persona
	private ExperienciaProfesional expPro; // variable para el ojb experiencia profesional
	private ExperienciaProfesionalExt detalleExperiencia; // variable para el ojb experiencia profesional
	private HistoricoModificacionHojaVida modificacionHojaVida; // variable para el ojb modificacion hoja de vida
	private UploadedFile cargarDodumento = null; // variable para el cargue de un archivo
	private EditarDireccionDTO editarDireccion; // variable para agregar la generacion de la direccion

	//private List<Entidad> listEntPriv; // variable para la lista de entidades privadas
	private List<ExperienciaProfesionalExt> listExperienciaProfesionalExt; // variable para la lista de experiencias
																			// profesionales
	private List<ExperienciaProfesionalExt> listDateExperienciaProfesionalExt; // variable para la lista de experiencias utilizada para el calculo del tiempo
																			// profesionales sin duplicar cuando son iguales
	
	private boolean habilitarFormulario = false; // variable para habilitar el formulario
	private boolean mostrarComboEntidadPrivada; // variable para habilitar datos de entidad privada
	private boolean mostrarComboEntidadPublica; // variable para habilitar datos de entidad publica
	private boolean mostrarDatosPorPaisEntidad; // variable para habilitar datos por pais seleccionado
	private boolean deshabilitarRetiro = false; // variable para habilitar fecha de retiro
	private boolean deshabilitarHorasMes = true; // variable para habilitar datos de hora y mes
	private boolean habilitarControles; // variable para habilitar controles
	private boolean deshabilitarbotTab; // variable para habilitar los tab
	private boolean activoentidad; // variable para verificar entidad activa
	private boolean mostratEditarDireccion; // variable para habilitar el formulario de direccion
	private Boolean flgValidRolPermission = false; // variable para verificar permisos
	private Boolean flgValidadoDocumento = Boolean.FALSE; // variable para habilitar el cargue de documento
	private Boolean flgDireccionRequerida = Boolean.FALSE;

	static final long DIAS_ANIO = 365; // variable para obtener los días de un año
	static final long DIAS_MES = 30; // variable para obtener los días de un mes
	static final long MESES_ANIO = 12; // variable para obtener los MESES DEL ANIO
	private long anio = 0; // variable para el valor año
	private long mes = 0;// variable para el valor mes
	private long dia = 0; // variable para el valor dia
	private long auxMes = 0; // variable para mes
	private Integer tiempoLaborado = 0; // variable para el tiempo laborado
	private String banderaPais = "/resources/images/banderas/co.png"; // variable para la ruta por de fecto de la imagen
	private String nombreArchivo; // variable para el nombre del archivo
	static final long COD_JEFE_CONTROL_INTERNO = 15; // variable para envio de correos desde modificar HV
	
	long codPersona = getUsuarioSesion().getCodPersona(); // variable para el codigo de persona

	private String rutaArchivo = null;
	private List<DependenciaHojaVidaExt> 	lstDependenciaHV;
	private DependenciaHojaVida 		dependenciaHV;
	private List<CargoHojaVidaExt> 		lstCargoHV;
	private CargoHojaVida 				cargoHV;
	
	private AuditoriaSeguridad  auditoriaSeguridad; 

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

	/**
	 * @return the detalleExperiencia
	 */
	public ExperienciaProfesionalExt getDetalleExperiencia() {
		return detalleExperiencia;
	}

	/**
	 * @return the flgValidadoDocumento
	 */
	public Boolean getFlgValidadoDocumento() {
		return flgValidadoDocumento;
	}

	/**
	 * @param flgValidadoDocumento
	 *            the flgValidadoDocumento to set
	 */
	public void setFlgValidadoDocumento(Boolean flgValidadoDocumento) {
		this.flgValidadoDocumento = flgValidadoDocumento;
	}

	/**
	 * @param detalleExperiencia
	 *            the detalleExperiencia to set
	 */
	public void setDetalleExperiencia(ExperienciaProfesionalExt detalleExperiencia) {
		this.detalleExperiencia = detalleExperiencia;
	}

	/**
	 * @return the listExperienciaProfesionalExt
	 */
	public List<ExperienciaProfesionalExt> getListExperienciaProfesionalExt() {
		return listExperienciaProfesionalExt;
	}
	
	

	public List<ExperienciaProfesionalExt> getListDateExperienciaProfesionalExt() {
		return listDateExperienciaProfesionalExt;
	}

	public void setListDateExperienciaProfesionalExt(List<ExperienciaProfesionalExt> listDateExperienciaProfesionalExt) {
		this.listDateExperienciaProfesionalExt = listDateExperienciaProfesionalExt;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * @param flgValidRolPermission
	 *            the flgValidRolPermission to set
	 */
	public void setListExperienciaProfesionalExt(List<ExperienciaProfesionalExt> listExperienciaProfesionalExt) {
		this.listExperienciaProfesionalExt = listExperienciaProfesionalExt;
	}

	/**
	 * @return the persona
	 */
	public PersonaExt getPersona() {
		return persona;
	}

	/**
	 * @param persona
	 *            the persona to set
	 */
	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	/**
	 * @return the codPersona
	 */
	public long getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona
	 *            the codPersona to set
	 */
	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return the expPro
	 */
	public ExperienciaProfesional getExpPro() {
		return expPro;
	}

	/**
	 * @param expPro
	 *            the expPro to set
	 */
	public void setExpPro(ExperienciaProfesional expPro) {
		this.expPro = expPro;
	}

	/**
	 * @return the entidad
	 */
	public Entidad getEntidad() {
		return entidad;
	}

	/**
	 * @param entidad
	 *            the entidad to set
	 */
	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	/**
	 * @return the habilitarFormulario
	 */
	public boolean isHabilitarFormulario() {
		return habilitarFormulario;
	}

	/**
	 * @param habilitarFormulario
	 *            the habilitarFormulario to set
	 */
	public void setHabilitarFormulario(boolean habilitarFormulario) {
		this.habilitarFormulario = habilitarFormulario;
	}

	/**
	 * @return the mostrarComboEntidadPrivada
	 */
	public boolean isMostrarComboEntidadPrivada() {
		return mostrarComboEntidadPrivada;
	}

	/**
	 * @param mostrarComboEntidadPrivada
	 *            the mostrarComboEntidadPrivada to set
	 */
	public void setMostrarComboEntidadPrivada(boolean mostrarComboEntidadPrivada) {
		this.mostrarComboEntidadPrivada = mostrarComboEntidadPrivada;
	}

	/**
	 * @return the mostrarComboEntidadPublica
	 */
	public boolean isMostrarComboEntidadPublica() {
		return mostrarComboEntidadPublica;
	}

	/**
	 * @param mostrarComboEntidadPublica
	 *            the mostrarComboEntidadPublica to set
	 */
	public void setMostrarComboEntidadPublica(boolean mostrarComboEntidadPublica) {
		this.mostrarComboEntidadPublica = mostrarComboEntidadPublica;
	}

	/**
	 * @return the mostrarDatosPorPaisEntidad
	 */
	public boolean isMostrarDatosPorPaisEntidad() {
		return mostrarDatosPorPaisEntidad;
	}

	/**
	 * @param mostrarDatosPorPaisEntidad
	 *            the mostrarDatosPorPaisEntidad to set
	 */
	public void setMostrarDatosPorPaisEntidad(boolean mostrarDatosPorPaisEntidad) {
		this.mostrarDatosPorPaisEntidad = mostrarDatosPorPaisEntidad;
	}

	/**
	 * @return the deshabilitarRetiro
	 */
	public boolean isDeshabilitarRetiro() {
		return deshabilitarRetiro;
	}

	/**
	 * @param deshabilitarRetiro
	 *            the deshabilitarRetiro to set
	 */
	public void setDeshabilitarRetiro(boolean deshabilitarRetiro) {
		this.deshabilitarRetiro = deshabilitarRetiro;
	}

	/**
	 * @return the deshabilitarHorasMes
	 */
	public boolean isDeshabilitarHorasMes() {
		return deshabilitarHorasMes;
	}

	/**
	 * @param deshabilitarHorasMes
	 *            the deshabilitarHorasMes to set
	 */
	public void setDeshabilitarHorasMes(boolean deshabilitarHorasMes) {
		this.deshabilitarHorasMes = deshabilitarHorasMes;
	}

	/**
	 * @return the habilitarControles
	 */
	public boolean isHabilitarControles() {
		return habilitarControles;
	}

	/**
	 * @param habilitarControles
	 *            the habilitarControles to set
	 */
	public void setHabilitarControles(boolean habilitarControles) {
		this.habilitarControles = habilitarControles;
	}

	/**
	 * @return the deshabilitarbotTab
	 */
	public boolean isDeshabilitarbotTab() {
		return deshabilitarbotTab;
	}

	/**
	 * @param deshabilitarbotTab
	 *            the deshabilitarbotTab to set
	 */
	public void setDeshabilitarbotTab(boolean deshabilitarbotTab) {
		this.deshabilitarbotTab = deshabilitarbotTab;
	}

	/**
	 * @return the activoentidad
	 */
	public boolean isActivoentidad() {
		return activoentidad;
	}

	/**
	 * @param activoentidad
	 *            the activoentidad to set
	 */
	public void setActivoentidad(boolean activoentidad) {
		this.activoentidad = activoentidad;
	}

	/**
	 * @return the cargarDodumento
	 */
	public UploadedFile getCargarDodumento() {
		return cargarDodumento;
	}

	/**
	 * @param cargarDodumento
	 *            the cargarDodumento to set
	 */
	public void setCargarDodumento(UploadedFile cargarDodumento) {
		this.cargarDodumento = cargarDodumento;
	}

	/**
	 * @return the tiempoLaborado
	 */
	public int getTiempoLaborado() {
		return tiempoLaborado;
	}

	/**
	 * @param tiempoLaborado
	 *            the tiempoLaborado to set
	 */
	public void setTiempoLaborado(int tiempoLaborado) {
		this.tiempoLaborado = tiempoLaborado;
	}

	/**
	 * @return the anio
	 */
	public long getAnio() {
		return anio;
	}

	/**
	 * @param anio
	 *            the anio to set
	 */
	public void setAnio(long anio) {
		this.anio = anio;
	}

	/**
	 * @return the mes
	 */
	public long getMes() {
		return mes;
	}

	/**
	 * @param mes
	 *            the mes to set
	 */
	public void setMes(long mes) {
		this.mes = mes;
	}

	/**
	 * @return the dia
	 */
	public long getDia() {
		return dia;
	}

	/**
	 * @param dia
	 *            the dia to set
	 */
	public void setDia(long dia) {
		this.dia = dia;
	}

	/**
	 * @return the auxMes
	 */
	public long getAuxMes() {
		return auxMes;
	}

	/**
	 * @param auxMes
	 *            the auxMes to set
	 */
	public void setAuxMes(long auxMes) {
		this.auxMes = auxMes;
	}

	/**
	 * @return the banderaPais
	 */
	public String getBanderaPais() {
		return banderaPais;
	}

	/**
	 * @param banderaPais
	 *            the banderaPais to set
	 */
	public void setBanderaPais(String banderaPais) {
		this.banderaPais = banderaPais;
	}

	/**
	 * @return the modificacionHojaVida
	 */
	public HistoricoModificacionHojaVida getModificacionHojaVida() {
		return modificacionHojaVida;
	}

	/**
	 * @param modificacionHojaVida
	 *            the modificacionHojaVida to set
	 */
	public void setModificacionHojaVida(HistoricoModificacionHojaVida modificacionHojaVida) {
		this.modificacionHojaVida = modificacionHojaVida;
	}

	/**
	 * @return the flgValidRolPermission
	 */
	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	/**
	 * @param flgValidRolPermission
	 *            the flgValidRolPermission to set
	 */
	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	/**
	 * @return the mostratEditarDireccion
	 */
	public boolean isMostratEditarDireccion() {
		return mostratEditarDireccion;
	}

	/**
	 * @param isMostratEditarDireccion
	 *            the isMostratEditarDireccion to set
	 */
	public void setMostratEditarDireccion(boolean mostratEditarDireccion) {
		this.mostratEditarDireccion = mostratEditarDireccion;
	}

	/**
	 * @return the editarDireccion
	 */
	public EditarDireccionDTO getEditarDireccion() {
		return editarDireccion;
	}

	/**
	 * @param editarDireccion
	 *            the editarDireccion to set
	 */
	public void setEditarDireccion(EditarDireccionDTO editarDireccion) {
		this.editarDireccion = editarDireccion;
	}

	/**
	 * @return the lstDependenciaHV
	 */
	public List<DependenciaHojaVidaExt> getLstDependenciaHV() {
		return lstDependenciaHV;
	}

	/**
	 * @param lstDependenciaHV the lstDependenciaHV to set
	 */
	public void setLstDependenciaHV(List<DependenciaHojaVidaExt> lstDependenciaHV) {
		this.lstDependenciaHV = lstDependenciaHV;
	}

	/**
	 * @return the dependenciaHV
	 */
	public DependenciaHojaVida getDependenciaHV() {
		return dependenciaHV;
	}

	/**
	 * @param dependenciaHV the dependenciaHV to set
	 */
	public void setDependenciaHV(DependenciaHojaVida dependenciaHV) {
		this.dependenciaHV = dependenciaHV;
	}
	/**
	 * @return the lstCargoHV
	 */
	public List<CargoHojaVidaExt> getLstCargoHV() {
		return lstCargoHV;
	}

	/**
	 * @param lstCargoHV the lstCargoHV to set
	 */
	public void setLstCargoHV(List<CargoHojaVidaExt> lstCargoHV) {
		this.lstCargoHV = lstCargoHV;
	}

	/**
	 * @return the cargoHV
	 */
	public CargoHojaVida getCargoHV() {
		return cargoHV;
	}

	/**
	 * @param cargoHV the cargoHV to set
	 */
	public void setCargoHV(CargoHojaVida cargoHV) {
		this.cargoHV = cargoHV;
	}


	@Inject
    protected DireccionBean direccionBean;	

	/**
	 * Metodo para iniciar parametros en la vista
	 */
	@PostConstruct
	public void init() {
		this.initialization();
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		detalleExperiencia 		= new ExperienciaProfesionalExt();
		modificacionHojaVida 	= new HistoricoModificacionHojaVida();
		editarDireccion 		= new EditarDireccionDTO();
		dependenciaHV 			= new DependenciaHojaVida();
		cargoHV					= new CargoHojaVida();
		habilitarControles 		= true;
		habilitarFormulario 	= false;
		
		auditoriaSeguridad = new AuditoriaSeguridad();
		auditoriaSeguridad.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		auditoriaSeguridad.setCodUsuarioRol(new BigDecimal(getUsuarioSesion().getCodRol()));
		ComunicacionServiciosHV.setAuditoriaSeguridad(auditoriaSeguridad);

		this.mostrarPanelDatosPorPaisEntidad();
		this.mostrarTablaExperienciaProfesional();
		this.calcularTiempoLaborado();
	}

	/**
	 * Metodo para inicializar los parametros para la vista
	 */
	public void initialization() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String idPersona = request.getParameter("id");
		String msnIOException = "void init() finalizarConversacion";

		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR,
							RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS,
							RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
					if (!flgValidRolPermission) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error(msnIOException, e);
				}
			}
		} else {
			if (getUsuarioSesion() != null) {
				codPersona = getUsuarioSesion().getCodPersona();
				try {
					Boolean flgValidPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA,
							RolDTO.SERVIDOR_PUBLICO);
					if (!flgValidPermission) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error(msnIOException, e);
				}
			} else {
				try {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				} catch (IOException e) {
					logger.error(msnIOException, e);
				}
			}
		}
	}

	/**
	 * Metodo para mostrar registros en la tabla
	 */
	public void mostrarTablaExperienciaProfesional() {
		listExperienciaProfesionalExt = ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true);
		if(!listExperienciaProfesionalExt.isEmpty()) {
			for (int i = 0; i < listExperienciaProfesionalExt.size(); i++) {
				if(persona == null) {
					persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
				}
				listExperienciaProfesionalExt.get(i).setUrlDocumentoSoporte(WebUtils.validarUrl(listExperienciaProfesionalExt.get(i).getUrlDocumentoSoporte(), persona.getNumeroIdentificacion(), listExperienciaProfesionalExt.get(i).getCodExperienciaProfesional()+"", WebUtils.TP_DOC_EXP_LABORAL));
			}
		}
	}

	/**
	 * Metodo para mostrar la experiencia profesional activa
	 */
	public boolean experienciaActiva() {
		boolean activoEntidad = false;
		List<ExperienciaProfesional> listaExperienciasActivas = ComunicacionServiciosHV.getexpeproporcodpersona(codPersona, 1, 1);
		for (ExperienciaProfesional expProActiva : listaExperienciasActivas) {
			if (expProActiva.getFlgActivoEntidad() && expProActiva.getFlgActivo() && !expProActiva
					.getCodExperienciaProfesional().equals(detalleExperiencia.getCodExperienciaProfesional())) {
				activoEntidad = true;
				break;
			}
		}

		return activoEntidad;
	}
	
	/**
	 * Metodo que se encarga de obtener la lista de experiencias, para realizar el calculo de el tiempo total laborado
	 */
	private void obtenerExperiencasCalculo() {
		listDateExperienciaProfesionalExt = ComunicacionServiciosHV.getDateExperiencias(codPersona);
	}

	/**
	 * Metodo para calcular el tiempo laborado
	 */
	public void calcularTiempoLaborado() {
		obtenerExperiencasCalculo();
		
		anio 					= 0;
		mes 					= 0;
		dia 					= 0;
		
		tiempoLaborado = DatosPersonalesBean.calculartiempoExperienciaProfesionalTotal(listDateExperienciaProfesionalExt);
    	/*Long[] tiempos = DatosPersonalesBean.calculartiempoExperienciaprofesional(listDateExperienciaProfesionalExt);	
    	anio = tiempos[6];
    	mes  = tiempos[7];
    	dia  = tiempos[8];*/
		
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

	/**
	 * Metodo para mostrar formulario de editar o ver el registro de una experiencia
	 * profesional
	 */
	public void mostrarFormulario(boolean valid) {
		nombreArchivo = "";
		detalleExperiencia 	= new ExperienciaProfesionalExt();
		editarDireccion 	= new EditarDireccionDTO();
		dependenciaHV 		= new DependenciaHojaVida();
		cargoHV				= new CargoHojaVida();
		this.mostrarPanelDatosPorPaisEntidad();
		this.activoEntidad();
		this.mostrarCamposTipoEntidad();
		habilitarFormulario = valid;
		habilitarControles = false;
		deshabilitarHorasMes = true;
	}
	
	
	
	/**
	 * Metodo para limpiar formulario de cancelar experiencia profesional
	 */
	public void limpiarFormulario() {
		nombreArchivo = "";
		detalleExperiencia 	= new ExperienciaProfesionalExt();
		editarDireccion 	= new EditarDireccionDTO();
		dependenciaHV 		= new DependenciaHojaVida();
		cargoHV				= new CargoHojaVida();
		this.mostrarPanelDatosPorPaisEntidad();
		this.activoEntidad();
		this.mostrarCamposTipoEntidad();
		habilitarFormulario 	= false;
		habilitarControles 		= false;
		deshabilitarHorasMes 	= true;
		mostrarTablaExperienciaProfesional();
		}

	/**
	 * Metodo para mostrar paneles dependiendo del tipo de entidad
	 */
	public void mostrarCamposTipoEntidad() {
		if (detalleExperiencia.getCodTipoEntidad() == null) {
			mostrarComboEntidadPublica = true;
			mostrarComboEntidadPrivada = false;
			detalleExperiencia.setCodEntidad(null);
			detalleExperiencia.setNombreEntidad(null);
			detalleExperiencia.setCodCargo(null);
			detalleExperiencia.setCargo(null);
			detalleExperiencia.setCodNivelCargo(null);
			detalleExperiencia.setCodDenominacion(null);
			detalleExperiencia.setCodGrado(null);
		} else {
			if (detalleExperiencia.getCodTipoEntidad().equals(142)) {/*publica*/
				mostrarComboEntidadPublica = true;
				mostrarComboEntidadPrivada = false;
			} else {/*privada*/
				mostrarComboEntidadPublica = false;
				mostrarComboEntidadPrivada = true;
			}
		}
	}

	/**
	 * @param entidadPrivQuery
	 * Metodo para listar las entidades privadas
	 */
	public List<String> listarEntidadPrivada(String entidadPrivQuery) {
		ExperienciaProfesionalExt buscador = new ExperienciaProfesionalExt();
		buscador.setLimitInit(0);
		buscador.setLimitEnd(1000);
		buscador.setNombreEntidad(entidadPrivQuery);
		buscador.setCodTipoEntidad(detalleExperiencia.getCodTipoEntidad());
		List<ExperienciaProfesionalExt> listaEntidadesPrivadas;
		List<String> filtroEntPrivs = new ArrayList<>();
		listaEntidadesPrivadas = ComunicacionServiciosHV.getEntidadesPriv(buscador);
		if(!listaEntidadesPrivadas.isEmpty()) {
			for (ExperienciaProfesionalExt entPriv : listaEntidadesPrivadas) {
				filtroEntPrivs.add(entPriv.getNombreEntidad());	
			}
		}		
		return filtroEntPrivs;
	}


	/**
	 * Metodo para mostrar panel de departamento y municipio cuando se selecciona el
	 * pais
	 */
	public void mostrarPanelDatosPorPaisEntidad() {
		if (detalleExperiencia.getCodPais() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(detalleExperiencia.getCodPais());
			if (pais.getNombrePais().equalsIgnoreCase("COLOMBIA")) {
				mostrarDatosPorPaisEntidad = true;
			} else {
				mostrarDatosPorPaisEntidad = false;
				detalleExperiencia.setCodDepartamento(null);
				detalleExperiencia.setCodMunicipio(null);
			}
			if (pais.getIndicativoPais() != null) {
				detalleExperiencia.setIndicativoTelefono("+" + pais.getIndicativoPais());
			}

			if (pais.getBanderaUrl() != null) {
				this.setBanderaPais(pais.getBanderaUrl().toLowerCase());
			} else {
				this.setBanderaPais("/resources/images/banderas/default.png");
			}
		} else {
			detalleExperiencia.setCodPais(169);
			mostrarDatosPorPaisEntidad = true;

			Pais paisdefecto = ComunicacionServiciosSis.getpisporid(169);

			if (paisdefecto.getIndicativoPais() != null) {
				detalleExperiencia.setIndicativoTelefono("+" + paisdefecto.getIndicativoPais());
			}

			if (paisdefecto.getBanderaUrl() != null) {
				this.setBanderaPais(paisdefecto.getBanderaUrl().toLowerCase());
			} else {
				this.setBanderaPais("/resources/images/banderas/default.png");
			}
		}
	}

	/**
	 * Metodo para agregar direccion entidad publica
	 */
	public void asignarDireccionEntidad() {
		if (detalleExperiencia.getCodEntidad() != null) {
			entidad = ComunicacionServiciosEnt.getEntidadPorId(detalleExperiencia.getCodEntidad());
			Direccion dir = new Direccion();
			try {
				dir.llenarDatosDesdeJson(entidad.getDireccion());
				direccionBean.setDireccionSeleccionada(dir);
	            direccionBean.mostrarDireccion();
	            detalleExperiencia.setDireccionEntidad(direccionBean.getDireccionGenerada());				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metodo para buscar y agregar los indicativos por pais
	 */
	public void agregarIndicativoDepartamento() {
		if (detalleExperiencia.getCodDepartamento() != null) {
			Pais pais = ComunicacionServiciosSis.getpisporid(detalleExperiencia.getCodPais());
			Departamento departamento = ComunicacionServiciosSis.getdeptoporid(detalleExperiencia.getCodDepartamento());
			String indicativo = "+" + pais.getIndicativoPais() + departamento.getIndicativo();
			detalleExperiencia.setIndicativoTelefono(indicativo);
		}
	}

	/**
	 * Metodo para activar panel de entidades
	 */
	public void activoEntidad() {
		if (detalleExperiencia.getFlgActivoEntidad()) {
			this.deshabilitarRetiro = true;
			detalleExperiencia.setFechaRetiro(null);
			detalleExperiencia.setCodMotivoRetiro(null);
		} else {
			this.deshabilitarRetiro = false;
		}
		this.calcularTiempoExperiencia();
	}

	/**
	 * Metodo para habilitar campos de horas por mes
	 */
	public void habilitarHorasPromedioMes() {
		if (detalleExperiencia.getCodJornadaLaboral() != null) {
			if (detalleExperiencia.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())) {
				this.deshabilitarHorasMes = false;
			} else {
				this.deshabilitarHorasMes = true;
				detalleExperiencia.setHorasPromedioMes(null);
			}
			if(detalleExperiencia.getCodJornadaLaboral() != null && detalleExperiencia.getFechaIngreso()!= null) {
				this.calcularTiempoExperiencia();
			}
			
		} else {
			this.deshabilitarHorasMes = true;
			detalleExperiencia.setHorasPromedioMes(null);
		}
	}

	ELContext elContext = FacesContext.getCurrentInstance().getELContext();
	DatosPersonalesBean mBDatosPersonalesBean = (DatosPersonalesBean) elContext.getELResolver().getValue(elContext,
			null, "datosPersonalesBean");
	public String guardarExperiencialaboral(){
		if(mBDatosPersonalesBean.isLbISGestionarHV()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('dialogExpProfesional').show()");
		}else{
			guardarExperiencialaboralSinConfirmar();
		}
		return null;
	}		
	
	public boolean guardarExperiencialaboralSinConfirmar() {

		if (detalleExperiencia.getDireccionEntidad() == null
				|| detalleExperiencia.getDireccionEntidad().trim().length() < 1) {
			this.flgDireccionRequerida = true;
			return false;
		}

		detalleExperiencia.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		detalleExperiencia.setAudCodRol((int) this.getRolAuditoria().getId());
		detalleExperiencia.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		detalleExperiencia.setAudFechaActualizacion(DateUtils.getFechaSistema());

		if (detalleExperiencia.getCodExperienciaProfesional() == null) {
			detalleExperiencia.setCodPersona(BigDecimal.valueOf(codPersona));
			detalleExperiencia.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}

		if (detalleExperiencia.getCodTipoEntidad() == null || detalleExperiencia.getCodTipoEntidad().equals(0)) {
			detalleExperiencia.setFlgEntidadPublica(false);
			detalleExperiencia.setCodEntidad(null);
		} else {
			if (detalleExperiencia.getCodTipoEntidad().equals(142)) {
				detalleExperiencia.setFlgEntidadPublica(true);
				detalleExperiencia.setNombreEntidad(null);
			} else {
				detalleExperiencia.setFlgEntidadPublica(false);
				detalleExperiencia.setCodEntidad(null);
			}
		}

		if (detalleExperiencia.getFlgActivoEntidad() && this.experienciaActiva() && this.verificarUsuarioSinPermisoActivas()) {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.MSG_EXPERIENCIA_ACTIVA, "");
			return false;
		}

		detalleExperiencia.setCodDependenciaHV(dependenciaHV.getCodDependenciaHojaVida() != null ? dependenciaHV.getCodDependenciaHojaVida().longValue() : null );
		detalleExperiencia.setCodCargoHV(cargoHV.getCodCargoHojaVida()!= null ? cargoHV.getCodCargoHojaVida().longValue() : null );
		detalleExperiencia.setFlgActivo(true);
		boolean valid = ComunicacionServiciosHV.setexpProfesional(detalleExperiencia);

		if (valid) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_DATOS_GUARDADOS_EXITOSA_EXP_LABORAL);
			detalleExperiencia = new ExperienciaProfesionalExt();
			habilitarFormulario = true;
			mostrarTablaExperienciaProfesional();
			mBDatosPersonalesBean.setEditado(false);
			this.calcularTiempoLaborado();
			mBDatosPersonalesBean.getUiDatosModificados().setValue("0");
			RequestContext.getCurrentInstance().update("frmPrincipal:modificadoHV");	
			mBDatosPersonalesBean.refrescarProgresoHojaVida();
			mBDatosPersonalesBean.actualizaModificacionHv();
			dependenciaHV 	= new DependenciaHojaVida();
			cargoHV 		= new CargoHojaVida();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			return false;
		}

		habilitarFormulario = false;
		return true;
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

	/**
	 * @param experienciaProfesionalExt
	 * Metodo para activar el formulario y visualizar la informacion de experiencia profesional
	 */
	public void mostrarInformacionExperienciaProfesional(ExperienciaProfesionalExt experienciaProfesionalExt) {
		if(experienciaProfesionalExt!=null) {
		detalleExperiencia = experienciaProfesionalExt;
		cargoHV = new  CargoHojaVida();
		dependenciaHV = new  DependenciaHojaVida();
		if(detalleExperiencia != null && detalleExperiencia.getCodDependenciaHV() != null) {
			dependenciaHVFiltroPorID(new BigDecimal(detalleExperiencia.getCodDependenciaHV()));
		}
		if(detalleExperiencia != null && detalleExperiencia.getCodCargoHV() != null) {
			cargoHVFiltroPorID(new BigDecimal(detalleExperiencia.getCodCargoHV()));
		}
		habilitarFormulario = true;
		mostrarCamposTipoEntidad();
		mostrarPanelDatosPorPaisEntidad();
		habilitarControles = true;
		deshabilitarRetiro = true;
		deshabilitarHorasMes = true;
		calcularTiempoExperiencia();
		if (experienciaProfesionalExt != null && experienciaProfesionalExt.getUrlDocumentoSoporte() != null
				&& !"".equals(experienciaProfesionalExt.getUrlDocumentoSoporte() != null))
			this.setNombreArchivo("CertificadoExperienciaLaboral.pdf");
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_ERROR_ACTUALIZACION, "");
		}
	}

	/**
	 * @param experienciaProfesionalExt
	 *            Metodo para activar el formulario y visualizar la informacion de
	 *            experiencia profesional
	 */
	public void actualizarExperienciaProfesional(ExperienciaProfesionalExt experienciaProfesionalExt) {
		if(experienciaProfesionalExt!=null) {
			this.flgValidadoDocumento = false;
			this.mostrarFormulario(false);
			if (nombreArchivo!=null)
				this.setNombreArchivo(nombreArchivo);
			cargoHV 				= new  CargoHojaVida();
			dependenciaHV 			= new  DependenciaHojaVida();
			detalleExperiencia 		= experienciaProfesionalExt;
			if(detalleExperiencia != null && detalleExperiencia.getCodDependenciaHV() != null) {
				dependenciaHVFiltroPorID(new BigDecimal(detalleExperiencia.getCodDependenciaHV()));
			}
			if(detalleExperiencia != null && detalleExperiencia.getCodCargoHV() != null) {
				cargoHVFiltroPorID(new BigDecimal(detalleExperiencia.getCodCargoHV()));
			}
			habilitarFormulario = true;
			habilitarControles = false;
			activoEntidad();
			mostrarPanelDatosPorPaisEntidad();
			mostrarCamposTipoEntidad();
			calcularTiempoExperiencia();
			
			if (detalleExperiencia.getFlgValidaJefe() && this.flgValidRolPermission) {
				this.flgValidadoDocumento = true;
			}
			if (detalleExperiencia.getCodJornadaLaboral() != null && detalleExperiencia.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue()) ) {
				this.deshabilitarHorasMes = false;
			}else {
				this.deshabilitarHorasMes = true;
			}
			
			if (experienciaProfesionalExt != null && experienciaProfesionalExt.getUrlDocumentoSoporte() != null
					&& !"".equals(experienciaProfesionalExt.getUrlDocumentoSoporte() != null))
				this.setNombreArchivo("CertificadoExperienciaLaboral.pdf");
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.MSG_ERROR_ACTUALIZACION, "");
		}
	}

	/**
	 * @param experienciaProfesionalExt
	 *            Metodo para eliminar una experiencia profesional
	 */
	public void desactivarExperienciaProfesional(ExperienciaProfesionalExt experienciaProfesionalExt) {
		try {
			
			experienciaProfesionalExt.setFlgActivo(false);
			experienciaProfesionalExt.setAudFechaActualizacion(DateUtils.getFechaSistema());
			experienciaProfesionalExt.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
			experienciaProfesionalExt.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
			experienciaProfesionalExt.setAudCodRol((int) this.getRolAuditoria().getId());
			Boolean valid = ComunicacionServiciosHV.setexpProfesional(experienciaProfesionalExt);

			if (valid) {
				mBDatosPersonalesBean.actualizaModificacionHv();
				this.mostrarTablaExperienciaProfesional();
				this.calcularTiempoLaborado();
				habilitarFormulario = false;
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_ELIMINACION_EXITOSA, "");
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_ELIMINACION_FALLIDA, "");
			}
		} catch (Exception e) {
			e.getStackTrace();
		}

	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// Do nothing because, it isn't necessary.
	}

	/**
	 * @param e
	 * Metodo para cargar un archivo pdf con documento de soporte
	 */
	public void documentoUpload(FileUploadEvent e) throws IOException {
		cargarDodumento = e.getFile();

		byte[] bytes = null;
		try {
			if (null != cargarDodumento) {
				Date fechaActual = new Date();
				bytes = cargarDodumento.getContents();
				String ext = FilenameUtils.getExtension(cargarDodumento.getFileName());
				String filename = fechaActual.getTime() + "." + ext.toLowerCase();
				String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_DOC_EXP_LABORAL)
						+ filename;
				String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
				try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)))) {
					stream.write(bytes);
					stream.flush();
				}catch (IOException err) {
					logger.log().error("Error al trata de escribir el archivo en la ruta:" + ruta, err.getMessage());
				}
				ErrorMensajes resp=null;
				
				if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("0")){
					String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
		                    				ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOC_EXP_LABORAL,
		                    				WebUtils.TP_DOC_EXP_LABORAL, mBDatosPersonalesBean.getPersona().getNumeroIdentificacion());
					Gson gson = new Gson();
					resp = gson.fromJson(response, ErrorMensajes.class);
				}else if (DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("1")||DatosPersonalesBean.HADOOP_OPCION_CARGA.equals("2")){/*Operacion cliente windows*/
					resp=DatosPersonalesBean.uploadHaddopApi(WebUtils.CNS_RUTA_DOC_EXP_LABORAL, WebUtils.TP_DOC_EXP_LABORAL, filePath, persona.getNumeroIdentificacion());			
				}				
				
				if(!resp.isError()) {
					this.setNombreArchivo(resp.getNombreArchivo());
					detalleExperiencia.setUrlDocumentoSoporte(resp.getUrlArchivo());
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
									.replace("%nombrearchivo%", resp.getNombreArchivo()));					
				}else {
					this.setNombreArchivo(null);
					detalleExperiencia.setUrlDocumentoSoporte(null);
					mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));					
				}

			}

		} catch (Exception e2) {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ERROR_GUARDAR, getLocale()));
		}
		
	}

	/**
	 * Metodo para calcular el tiempo de la experiencia profesional
	 */
	public void calcularTiempoExperiencia() {
		String dateC = "";
		if(detalleExperiencia.getCodJornadaLaboral() != null) {
			if(detalleExperiencia.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())) {
				deshabilitarHorasMes = false;
			}else {
				deshabilitarHorasMes = true;
			}
		}
		List<ExperienciaProfesionalExt>  lstExperiencias = new ArrayList<>();
		if(detalleExperiencia!=null)
			lstExperiencias.add(detalleExperiencia);
    	Long[] tiempos = DatosPersonalesBean.calculartiempoExperienciaprofesional(lstExperiencias);
    	long annos = 0; // variable para el valor año
    	long meses = 0;// variable para el valor mes
    	long dias = 0; // variable para el valor dia
    	if (detalleExperiencia!=null && detalleExperiencia.getCodTipoEntidad()!=null && detalleExperiencia.getCodTipoEntidad().equals(142)){
    		annos = tiempos[0];
    		meses = tiempos[1];
    		dias  = tiempos[2];
    	}else{
    		annos = tiempos[3];
    		meses = tiempos[4];
    		dias  = tiempos[5];    		
    	}
    	dateC = annos + " Años " + meses + " Meses " + dias + " Días";
    	detalleExperiencia.setTiempoexperiencia(dateC);
	}
	public void calcularTiempoExperiencia1() {
		
		Date fechaInicial = detalleExperiencia.getFechaIngreso();
		Date fechaFin = detalleExperiencia.getFechaRetiro() != null ? detalleExperiencia.getFechaRetiro() : new Date();
		
		if (fechaInicial != null) {
			Calendar calendarFin = Calendar.getInstance();
			calendarFin.setTime(fechaFin);
			calendarFin.add(Calendar.DAY_OF_YEAR, 1);  
			long diferencia = (calendarFin.getTime().getTime() - fechaInicial.getTime());
			long horasExp = ((diferencia) / 1000 / 60 / 60);

			if (detalleExperiencia.getCodJornadaLaboral() != null
					&& detalleExperiencia.getCodJornadaLaboral().equals(259)) {
				horasExp /= 2;
			}

			if (detalleExperiencia.getCodJornadaLaboral() != null
					&& detalleExperiencia.getCodJornadaLaboral().equals(TipoParametro.TIEMPO_PARCIAL.getValue())
					&& detalleExperiencia.getHorasPromedioMes() != null
					&& detalleExperiencia.getHorasPromedioMes() > 0) {
				horasExp = ((horasExp * detalleExperiencia.getHorasPromedioMes()) / 192);
			}

			long tiempoExp = horasExp / 24;
			long diaExp = 0;
			long mesExp = 0;
			long anioExp = 0;
			long auxMes = 0;

			if (tiempoExp >= DIAS_ANIO) {
				anioExp = tiempoExp / DIAS_ANIO;
				auxMes = tiempoExp % DIAS_ANIO;
				if (auxMes >= DIAS_MES) {
					mesExp = auxMes / DIAS_MES;
					diaExp = auxMes % DIAS_MES;
				} else {
					diaExp = auxMes;
				}
			} else {
				mesExp = tiempoExp / DIAS_MES;
				diaExp = tiempoExp % DIAS_MES;
			}

			if (tiempoExp < DIAS_MES) {
				diaExp = tiempoExp;
			}

			detalleExperiencia.setTiempoexperiencia(anioExp + " Años " + mesExp + " Meses " + diaExp + " Días");
		}
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		// Do nothing because it isn't necessary.
	}

	/**
	 * Metodo para mostrar dialogo e insertar la confirmacion de un cambio de datos
	 */
	public void confirmarDatosGuardar() {
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		guardarConfirmacionDatos();
	}

	/**
	 * Metodo para guardar y confirmar los cambios de un registro
	 */
	public void guardarConfirmacionDatos() {

		HojaVidaExt hojaVidaFilter = new HojaVidaExt();
		hojaVidaFilter.setCodPersona(persona.getCodPersona());
		hojaVidaFilter.setCodEntidad(this.getEntidadUsuario().getId());
		hojaVidaFilter.setFlgActivo(true);
		hojaVidaFilter.setLimitEnd(1);

		List<String> camposEditados = new ArrayList<>();
		List<HojaVidaExt> listHojaVida = ComunicacionServiciosHV.getHojaVidafiltro(hojaVidaFilter);

		if (detalleExperiencia.getCodExperienciaProfesional() != null) {

			ExperienciaProfesionalExt detalleExperienciaAnterior = ComunicacionServiciosHV
					.expProfesionalPorId(detalleExperiencia.getCodExperienciaProfesional());

			if (detalleExperienciaAnterior.getCodTipoEntidad() == null) {
				detalleExperienciaAnterior.setCodTipoEntidad(null);
			}

			if (detalleExperiencia.getCodTipoEntidad() != detalleExperienciaAnterior.getCodTipoEntidad()) {
				if (detalleExperiencia.getCodTipoEntidad() == 0) {
					detalleExperiencia.setCodTipoEntidad(null);
					camposEditados.add("Tipo de Entidad: ");
				} else {
					Parametrica parametrica = ComunicacionServiciosSis
							.getParametricaporId(BigDecimal.valueOf(detalleExperiencia.getCodTipoEntidad()));

					camposEditados.add("Tipo de Entidad: " + parametrica.getNombreParametro().toUpperCase());
				}
			}

			if (detalleExperienciaAnterior.getNombreEntidadExt() == null) {
				detalleExperienciaAnterior.setNombreEntidadExt("");
			}

			if (detalleExperiencia.getNombreEntidadExt() == null) {
				detalleExperiencia.setNombreEntidadExt("");
			}

			if (!detalleExperienciaAnterior.getNombreEntidadExt().trim()
					.equals(detalleExperiencia.getNombreEntidadExt().trim())) {
				camposEditados.add("Nombre Entidad: " + detalleExperiencia.getNombreEntidadExt());
			}

			if (detalleExperiencia.getCodPais() != null
					&& !detalleExperiencia.getCodPais().equals(detalleExperienciaAnterior.getCodPais())) {
				Pais paisExperiencia = ComunicacionServiciosSis.getpisporid(detalleExperiencia.getCodPais());
				camposEditados.add("Pais Experiencia Laboral: " + paisExperiencia.getNombrePais());
			}

			if (detalleExperiencia.getCodDepartamento() != detalleExperienciaAnterior.getCodDepartamento()) {

				if (detalleExperiencia.getCodDepartamento() == null) {
					detalleExperiencia.setCodDepartamento(null);
					camposEditados.add("Departamento Experiencia Laboral: ");
				} else {

					Departamento departamentoExperiencia = ComunicacionServiciosSis
							.getdeptoporid(detalleExperiencia.getCodDepartamento());
					camposEditados.add(
							"Departamento Experiencia Laboral: " + departamentoExperiencia.getNombreDepartamento());
				}
			}

			if (detalleExperiencia.getCodMunicipio() != detalleExperienciaAnterior.getCodMunicipio()) {
				if (detalleExperiencia.getCodMunicipio() == null) {
					detalleExperiencia.setCodMunicipio(null);
					camposEditados.add("Ciudad Experiencia Laboral: ");
				} else {
					Municipio municipioExperiencia = ComunicacionServiciosSis
							.getMunicipiosid(detalleExperiencia.getCodMunicipio());

					camposEditados.add("Ciudad Experiencia Laboral: " + municipioExperiencia.getNombreMunicipio());
				}
			}

			if (detalleExperienciaAnterior.getDireccionEntidad() == null) {
				detalleExperienciaAnterior.setDireccionEntidad("");
			}

			if (detalleExperiencia.getDireccionEntidad() == null) {
				detalleExperiencia.setDireccionEntidad("");
			}

			if (!detalleExperienciaAnterior.getDireccionEntidad().trim()
					.equals(detalleExperiencia.getDireccionEntidad().trim())) {
				camposEditados.add("Dirección Entidad: " + detalleExperiencia.getDireccionEntidad());
			}

			if (detalleExperienciaAnterior.getTelefono() == null) {
				detalleExperienciaAnterior.setTelefono("");
			}

			if (detalleExperiencia.getTelefono() == null) {
				detalleExperiencia.setTelefono("");
			}

			if (!detalleExperienciaAnterior.getTelefono().trim().equals(detalleExperiencia.getTelefono().trim())) {
				camposEditados.add("Teléfono Entidad: " + detalleExperiencia.getTelefono());
			}

			if (detalleExperienciaAnterior.getDependencia() == null) {
				detalleExperienciaAnterior.setDependencia("");
			}

			if (detalleExperiencia.getDependencia() == null) {
				detalleExperiencia.setDependencia("");
			}

			if (!detalleExperiencia.getDependencia().trim()
					.equals(detalleExperienciaAnterior.getDependencia().trim())) {
				if (detalleExperiencia.getDependencia() == null) {
					detalleExperiencia.setDependencia("");
					camposEditados.add("Dependencia o Área: ");
				}
				camposEditados.add("Dependencia o Área: " + detalleExperiencia.getDependencia());
			}

			if (detalleExperienciaAnterior.getCargo() == null) {
				detalleExperienciaAnterior.setCargo("");
			}

			if (detalleExperiencia.getCargo() == null) {
				detalleExperiencia.setCargo("");
			}

			if (!detalleExperienciaAnterior.getCargo().trim().equals(detalleExperiencia.getCargo().trim())) {
				if (detalleExperiencia.getCargo() == null) {
					detalleExperiencia.setCargo("");
					camposEditados.add("Cargo: ");
				}
				camposEditados.add("Cargo: " + detalleExperiencia.getCargo());
			}

			if (detalleExperienciaAnterior.getFlgActivoEntidad() != detalleExperiencia.getFlgActivoEntidad()) {
				String flgActivoEntidad = "";
				if (detalleExperiencia.getFlgActivoEntidad()) {
					flgActivoEntidad = "Si";
				} else {
					flgActivoEntidad = "No";
				}
				camposEditados.add("Está Activo en Entidad: " + flgActivoEntidad);
			}

			if (!detalleExperienciaAnterior.getFechaInicio().trim()
					.equals(detalleExperiencia.getFechaInicio().trim())) {
				camposEditados.add("Fecha Ingreso: " + detalleExperiencia.getFechaInicio());
			}

			if (detalleExperienciaAnterior.getCodJornadaLaboral() == null) {
				detalleExperienciaAnterior.setCodJornadaLaboral(null);
			}

			if (detalleExperiencia.getCodJornadaLaboral() == null) {
				detalleExperiencia.setCodJornadaLaboral(null);
			}

			if (detalleExperienciaAnterior.getCodJornadaLaboral() != detalleExperiencia.getCodJornadaLaboral()) {
				if (detalleExperiencia.getCodJornadaLaboral() == null) {
					camposEditados.add("Jornada Laboral: ");
				} else {
					Parametrica parametrica = ComunicacionServiciosSis
							.getParametricaporId(BigDecimal.valueOf(detalleExperiencia.getCodJornadaLaboral()));
					camposEditados.add("Jornada Laboral: " + parametrica.getNombreParametro().toUpperCase());
				}
			}

			if (!detalleExperiencia.getFlgActivoEntidad()) {

				if (detalleExperienciaAnterior.getFechaFin() == null) {
					detalleExperienciaAnterior.setFechaFin("");
				}

				if (detalleExperiencia.getFechaFin() == null) {
					detalleExperiencia.setFechaFin("");
				}

				if (!detalleExperienciaAnterior.getFechaFin().trim().equals(detalleExperiencia.getFechaFin().trim())) {
					camposEditados.add("Fecha Retiro: " + detalleExperiencia.getFechaFin());
					detalleExperiencia.setFechaFin(null);
				}

				if (detalleExperienciaAnterior.getCodMotivoRetiro() == null) {
					detalleExperienciaAnterior.setCodMotivoRetiro(null);
				}

				if (detalleExperiencia.getCodMotivoRetiro() == null) {
					detalleExperiencia.setCodMotivoRetiro(null);
				}

				if (detalleExperienciaAnterior.getCodMotivoRetiro() != detalleExperiencia.getCodMotivoRetiro()) {

					if (detalleExperiencia.getCodMotivoRetiro() == null) {
						camposEditados.add("Motivo Retiro: ");
						detalleExperiencia.setCodMotivoRetiro(null);
					} else {
						Parametrica parametrica = ComunicacionServiciosSis
								.getParametricaporId(BigDecimal.valueOf(detalleExperiencia.getCodMotivoRetiro()));
						camposEditados.add("Motivo Retiro: " + parametrica.getNombreParametro().toUpperCase());
					}
				}
			}

			if (detalleExperienciaAnterior.getHorasPromedioMes() == null) {
				detalleExperienciaAnterior.setHorasPromedioMes(null);
			}

			if (detalleExperiencia.getHorasPromedioMes() == null) {
				detalleExperiencia.setHorasPromedioMes(null);
			}

			if (detalleExperienciaAnterior.getHorasPromedioMes() != detalleExperiencia.getHorasPromedioMes()) {
				camposEditados.add("Horas Promedio al Mes: " + detalleExperiencia.getHorasPromedioMes());
			}

			camposEditados.add("Modificado Por : " + this.getUsuarioSesion().getNumeroIdentificacion() + " - "
					+ this.getUsuarioSesion().getNombrePersona());

			camposEditados.add("Entidad que Modifica : " + this.getEntidadUsuario().getNombreEntidad());
		}

		modificacionHojaVida.setCodHojaVida(BigDecimal.valueOf(listHojaVida.get(0).getCodHojaVida()));
		modificacionHojaVida.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		modificacionHojaVida.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		modificacionHojaVida.setAudCodRol((int) this.getRolAuditoria().getId());
		modificacionHojaVida.setAudFechaModificacion(DateUtils.getFechaSistema());

		guardarExperiencialaboralSinConfirmar();

		ComunicacionServiciosHV.setmodificacionhohadevida(modificacionHojaVida);
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		RequestContext context = RequestContext.getCurrentInstance();

		context.execute("PF('dialogExpProfesional').hide();");

		PersonaExt personaControlInterno = new PersonaExt();
		personaControlInterno.setCodRol(new BigDecimal(COD_JEFE_CONTROL_INTERNO));
		personaControlInterno.setCodEntidad(new BigDecimal(getEntidadUsuario().getId()));
		List<PersonaExt> listaPersonalControlInterno = ComunicacionServiciosHV.getPersonaControlInterno(personaControlInterno);
		List<String> email = new ArrayList<>();
		List<String> ccEmail = new ArrayList<>();
		mBDatosPersonalesBean.setEditado(false);
		try {
			for (PersonaExt personaExt : listaPersonalControlInterno) {
				ccEmail.add(personaExt.getCorreoElectronico());
			}
			email.add(persona.getCorreoElectronico());
			email.add(persona.getCorreoAlternativo());
			HojaDeVidaDelegate.emailActualizacionDatosPersonaDC(persona.getNombreUsuario(),
					modificacionHojaVida.getAudFechaModificacion(), personaControlInterno.getNombreUsuario(), email,
					ccEmail, camposEditados);
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	/**
	 * Metodo para cancelar y ocultar el formulario de confirmar cambios de un
	 * registro
	 */
	public void cancelarConfirmacionDatos() {
		this.mostrarFormulario(false);
		modificacionHojaVida = new HistoricoModificacionHojaVida();
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('dialogExpProfesional').hide();");
	}

	/**
	 * Metrodo para mostrar formulario de generar direccion
	 */
	public void mostrarPanelEditarDireccion(boolean value) {
		this.flgDireccionRequerida = false;
		cargarDireccionPanelEditarDireccion();
		mostratEditarDireccion = value;
	}

	
	public void cargarDireccionPanelEditarDireccion() {
		if (this.detalleExperiencia.getDireccionEntidad()!= null && !this.detalleExperiencia.getDireccionEntidad().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.detalleExperiencia.getDireccionEntidad(), EditarDireccionDTO.class);
				if (direccion != null) {
					this.editarDireccion = direccion;
				}
			} catch (JsonSyntaxException e) {

				
			}
		}
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
		
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		String jsonDireccion = gson.toJson(editarDireccion);
		
		detalleExperiencia.setDireccionEntidad(jsonDireccion);
		mostratEditarDireccion = false;
	}

	/**
	 * Pablo Quintana - 26/09/2018 Método que obtiene la url del anexo
	 * 
	 * @param tipoDocVisualiza
	 *            1. Hoja de vida - Experiencia Laboral
	 */
	public void visualizarArchivoExperienciaProf(String tipoDocVisualiza) {
		String ruta = "";
		if ("1".equals(tipoDocVisualiza)) {
			if (detalleExperiencia != null && detalleExperiencia.getUrlDocumentoSoporte() != null)
				ruta = detalleExperiencia.getUrlDocumentoSoporte();
		}
		if(ruta==null || "".equals(ruta)) {
			rutaArchivo=null;
		}else {
			if(persona == null) {
				persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
			}
//			rutaArchivo = WebUtils.getUrlFileHadoop(ruta,persona.getNumeroIdentificacion(),detalleExperiencia.getCodExperienciaProfesional()+"",WebUtils.TP_DOC_EXP_LABORAL);
			rutaArchivo = WebUtils.getUrlFile(ruta);
		}
	}
	
	/**
	 * Metodo para convertir la direccion y mostrarla*/
	public String mostrarDireccionGenerada() {
		String direccion = "";
		try {	
			if (detalleExperiencia.getDireccionEntidad() != null && !detalleExperiencia.getDireccionEntidad().isEmpty()) {
				Direccion dir = new Direccion();
				dir.llenarDatosDesdeJson(detalleExperiencia.getDireccionEntidad());
				direccionBean.setDireccionSeleccionada(dir);
	            direccionBean.mostrarDireccion();
	            direccion = direccionBean.getDireccionGenerada();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return direccion;
	}
	
	
	

	public String getRutaArchivo() {
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String calcular( Date fechaini, Date fechaFin, int index ) {
		Date fechaFinFinal;
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(fechaFin); 
	    calendar.add(Calendar.DAY_OF_YEAR, 1);  
	    fechaFinFinal = calendar.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		ChronoLocalDate from = ChronoLocalDate.from(formatter.parse(sdf.format(fechaini)));
        ChronoLocalDate to = ChronoLocalDate.from(formatter.parse(sdf.format(fechaFinFinal)));
        ChronoPeriod period = ChronoPeriod.between(from, to);	
        int dias, meses, annos;
        dias = (int) period.get(DAYS);
        meses = (int) period.get(MONTHS);
        annos =  (int) period.get(YEARS);
        if(index == 1) {
        	return annos + " Años " + meses + " Meses " + dias + " Días";
        }else if(index == 2){
        	return annos/2 + " Años " + meses/2 + " Meses " + dias/2 + " Días";
        }else if(index == 3){
        	return annos+ "-" + meses+ "-" + dias;
        }else if(index == 4){
        	return annos/2+ "-" + meses/2+ "-" + dias/2;
        }else {
        	return "Años 0 Meses 0 Días 0";
        }   
	}
	/**
	 * Metodo que filtra la dependencia segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroDependenciaHV
	 */
	public List<DependenciaHojaVidaExt> listarDependenciaHVFiltro(String entidadPrivQuery){
		DependenciaHojaVidaExt filtroDependenciaHVS = new DependenciaHojaVidaExt();
		filtroDependenciaHVS.setNombreDependencia(entidadPrivQuery);
		filtroDependenciaHVS.setFlgActivo((short)1);
		filtroDependenciaHVS.setLimitInit(0);
		filtroDependenciaHVS.setLimitEnd(1000);
		lstDependenciaHV = ComunicacionServiciosSis.getDependenciaHVFiltro(filtroDependenciaHVS);
		if(lstDependenciaHV.isEmpty()) {
	    	 return new ArrayList<>();
	     }
		return lstDependenciaHV;
	}
	
	/**
	 * Metodo que filtra la dependencia segun lo enviado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroDependenciaHV
	 */
	public void dependenciaHVFiltroPorID(BigDecimal codDependenciaHojaVida){
		DependenciaHojaVida dependenciaObject = new  DependenciaHojaVida();
		dependenciaHV = new  DependenciaHojaVida();
		dependenciaObject.setCodDependenciaHojaVida(codDependenciaHojaVida);
		dependenciaHV = ComunicacionServiciosSis.getDependenciaHojaVidaById(dependenciaObject);
	}
	
	
	/**
	 * Metodo que filtra el cargo segun lo ingresado por el usuario
	 * @param entidadPrivQuery
	 * @return filtroCargoHV
	 */
	public List<CargoHojaVidaExt> listarCargoHVFiltro(String entidadPrivQuery){
		CargoHojaVidaExt filtroCargoHV = new CargoHojaVidaExt();
		filtroCargoHV.setNombreCargo(entidadPrivQuery);
		filtroCargoHV.setFlgActivo((short)1);
		filtroCargoHV.setLimitInit(0);
		filtroCargoHV.setLimitEnd(1000);
		if(detalleExperiencia!= null && detalleExperiencia.getCodTipoEntidad()!= null
				&& detalleExperiencia.getCodTipoEntidad() > 0 && detalleExperiencia.getCodTipoEntidad() == TipoParametro.ENTIDAD_PUBLICA.getValue()) {
			filtroCargoHV.setFlgPublico((short) 1);
		}else if(detalleExperiencia!= null && detalleExperiencia.getCodTipoEntidad()!= null && 
				detalleExperiencia.getCodTipoEntidad() > 0 ) {
			filtroCargoHV.setFlgPublico((short) 0);
		}
		lstCargoHV = ComunicacionServiciosSis.getCargoHVFiltro(filtroCargoHV);
		if(lstCargoHV.isEmpty()) {
	    	 return new ArrayList<>();
	     }
		return lstCargoHV;
	}
	
	/**
	 * Metodo que filtra la dependencia segun lo enviado por el usuario
	 * @param entidadPrivQuery
	 * @return cargoHVFiltroPorID
	 */
	public void cargoHVFiltroPorID(BigDecimal codCargoHojaVida){
		CargoHojaVida cargoObject = new  CargoHojaVida();
		cargoHV = new  CargoHojaVida();
		cargoObject.setCodCargoHojaVida(codCargoHojaVida);
		cargoHV = ComunicacionServiciosSis.getCargoHojaVidaById(cargoObject);
	}
	
	/**
	 * Metodo que verifica si la persona a la que se le esta adicionando la experiencia
	 * es contratista, medico o docente. Si es asi, permite tener mas de una experiencia laboral actual.
	 * */
	public boolean verificarUsuarioSinPermisoActivas() {
		UsuarioExt usuario = ComunicacionServiciosHV.getusuarioext(codPersona,getEntidadUsuario().getId());
		if(usuario != null && usuario.getCodTipoVinculacion() != null 
				&& usuario.getCodTipoVinculacion().intValue() !=TipoParametro.TIPO_VINCULACION_CONTRATISTA.getValue()) {	
			VinculacionExt objFilter = new VinculacionExt();
			objFilter.setCodPersona(persona.getCodPersona());
			objFilter.setFlgActivo((short)1);
			objFilter.setCodEntidad(getEntidadUsuario().getId());
			 List<VinculacionExt> vinculacionActiva = ComunicacionServiciosVin.getvinculacionactual(objFilter);
			if(!vinculacionActiva.isEmpty() && vinculacionActiva.get(0).getFlgMedicoDocente() != null 
				&&  vinculacionActiva.get(0).getFlgMedicoDocente() != 1) {
				return true;
			}
			return true;
		}
		return false;
	}
	
}