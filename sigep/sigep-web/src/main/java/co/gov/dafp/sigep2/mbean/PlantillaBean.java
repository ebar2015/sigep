package co.gov.dafp.sigep2.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.component.selectmanycheckbox.SelectManyCheckbox;
import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.PlantillaLazyDataModel;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.entities.ColumnaTablaGestionInformacion;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.TablaGestionInformacion;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosGI;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.produces.XmlReporteProduces;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.StringUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.reporte.Reporte;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;
import co.gov.dafp.sigep2.util.xml.reporte.config.CuentaCorreo;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaConfiguracion;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Notificacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Parametro;
import co.gov.dafp.sigep2.util.xml.reporte.config.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.SQL;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoBandeja;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoValidacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.ValorMalla;

@Named
@ConversationScoped
public class PlantillaBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 6472614713316492339L;

	@Inject
	@ManagedProperty(value = "xmlReporteProduces")
	private XmlReporteProduces xmlProduces;

	private XmlReporte xml;

	private XmlReporte xmlConfiguracion;

	private Reporte procesoSeleccionado;
	private MallaConfiguracion reporteSeleccionado;
	private Registro registroSeleccionado;
	private Columna columnaSeleccionada;
	private SQL sqlSeleccionada;
	private Parametro parametroSeleccionado;
	private Notificacion notificacionSeleccionada;
	private CuentaCorreo cuentaCorreoSeleccionada;
	private MallaReporte mallaSeleccionada;
	private ValorMalla valorMallaSeleccionado;

	private static final String GESTIONAR_REPORTE 					= "gestionarReporte";
	private static final String GESTIONAR_REPORTE_SUB_MENU 			= "GestionarReportesTab";
	private static final String GESTIONAR_REPORTE_CREAR 			= "GestionarReporteCrearTag";
	private static final String GESTIONAR_REPORTE_MODIFICAR 		= "GestionarReporteModificarTag";
	private static final String GESTIONAR_REPORTE_MODIFICAR_2 		= "GestionarReporteModificarTag2";
	private static final String GESTIONAR_REPORTE_ELIMINAR 			= "GestionarReporteEliminarTag";
	private static final String GESTIONAR_REPORTE_CONSULTAR 		= "GestionarReporteConsultarTag";
	private static final String GESTIONAR_REPORTE_AGREGAR 			= "GestionarReporteAgregarCatalogoTag";
	private static final String GESTIONAR_REPORTE_QUITAR 			= "GestionarReporteQuitarCatalogoTag";
	private static final String GESTIONAR_REPORTE_AGREGAR_MALLA 	= "agregarMallaReportesTag";
	private static final String GESTIONAR_REPORTE_MODIFICAR_MALLA 	= "modificarMallaReportesTag";
	private static final String GESTIONAR_REPORTE_AGREGAR_COLUMNA 	= "agregarColumnaReportesTag";
	private static final String GESTIONAR_REPORTE_MODIFICAR_COLUMNA = "modificarColumnaReportesTag";
	static final String GESTIONAR_REPORTE_CATALOGO 					= "catalogoReportesTag";
	private static final String ENCABEZADO_MENSAGE 					= "<h1>GESTION DE LA INFORMACION</H1><br/><br/>"
			+ "<p>Solicitud de publicación del reporte <br/>- #nombreReporte</p><hr><b>SIGEPII</B>";
	private static final String ASUNTO 			= "SigepII (reporte)";

	private static final String COLUMNA 		= "columna";
	private static final String FACES_REDIRECT 	= "faces-redirect=true;dialog=";
	private static final String RECURSO_ID 		= "recursoId=";

	private FormaConsulta formaConsultaAnterior;

	private List<String> listaRol;

	private List<String> listaMalla;

	private List<MallaReporte> listaMallaReporte;

	private String nombrePlantilla;

	private PlantillaLazyDataModel listaPlantillas = null;

	private boolean renderIngresoInformacion = true;

	private BigInteger idColumna;

	private TablaGestionInformacion tablaGestionInformacion;
	private ColumnaTablaGestionInformacion columnaTablaGestionInformacion;

	private List<TablaGestionInformacion> listaTablaGestionInformacion;
	private List<ColumnaTablaGestionInformacion> listaColumnaTablaGestionInformacion;

	private List<ValorMalla> lstOrden;
	
	static final String PARAMETRO_PERSONA_ID = "cod_persona";
	static final String PARAMETRO_ENTIDAD_ID = "cod_entidad";

	public BigInteger getIdColumna() {
		return idColumna;
	}

	public void setIdColumna(BigInteger idColumna) {
		this.idColumna = idColumna;
	}

	public boolean isRenderIngresoInformacion() {
		return renderIngresoInformacion;
	}

	public void setRenderIngresoInformacion(boolean renderIngresoInformacion) {
		this.renderIngresoInformacion = renderIngresoInformacion;
	}

	public TablaGestionInformacion getTablaGestionInformacion() {
		return tablaGestionInformacion;
	}

	public void setTablaGestionInformacion(TablaGestionInformacion tablaGestionInformacion) {
		this.tablaGestionInformacion = tablaGestionInformacion;
	}

	public ColumnaTablaGestionInformacion getColumnaTablaGestionInformacion() {
		return columnaTablaGestionInformacion;
	}

	public void setColumnaTablaGestionInformacion(ColumnaTablaGestionInformacion columnaTablaGestionInformacion) {
		this.columnaTablaGestionInformacion = columnaTablaGestionInformacion;
	}

	public List<TablaGestionInformacion> getListaTablaGestionInformacion() {
		return listaTablaGestionInformacion;
	}

	public void setListaTablaGestionInformacion(List<TablaGestionInformacion> listaTablaGestionInformacion) {
		this.listaTablaGestionInformacion = listaTablaGestionInformacion;
	}

	public List<ColumnaTablaGestionInformacion> getListaColumnaTablaGestionInformacion() {
		return listaColumnaTablaGestionInformacion;
	}

	public void setListaColumnaTablaGestionInformacion(
			List<ColumnaTablaGestionInformacion> listaColumnaTablaGestionInformacion) {
		this.listaColumnaTablaGestionInformacion = listaColumnaTablaGestionInformacion;
	}

	public List<ValorMalla> getLstOrden() {
		return lstOrden;
	}

	public void setLstOrden(List<ValorMalla> lstOrden) {
		this.lstOrden = lstOrden;
	}

	public XmlReporte getXml() {
		return xml;
	}

	public void setXml(XmlReporte xml) {
		this.xml = xml;
	}

	public XmlReporte getXmlConfiguracion() {
		return xmlConfiguracion;
	}

	public void setXmlConfiguracion(XmlReporte xmlConfiguracion) {
		this.xmlConfiguracion = xmlConfiguracion;
	}

	public MallaConfiguracion getReporteSeleccionado() {
		return reporteSeleccionado;
	}

	public void setReporteSeleccionado(MallaConfiguracion reporteSeleccionado) {
		this.reporteSeleccionado = reporteSeleccionado;
	}

	public Registro getRegistroSeleccionado() {
		if (registroSeleccionado == null) {
			registroSeleccionado = new Registro();
			registroSeleccionado.setSQL(new SQL());
		}
		if (registroSeleccionado.getSQL() == null && registroSeleccionado.isContieneSentenciaSQL()) {
			registroSeleccionado.setSQL(new SQL());
		}
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(Registro registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public SQL getSqlSeleccionada() {
		return sqlSeleccionada;
	}

	public void setSqlSeleccionada(SQL sqlSeleccionada) {
		this.sqlSeleccionada = sqlSeleccionada;
	}

	public Columna getColumnaSeleccionada() {
		return columnaSeleccionada;
	}

	public void setColumnaSeleccionada(Columna columnaSeleccionada) {
		this.columnaSeleccionada = columnaSeleccionada;
	}

	public Notificacion getNotificacionSeleccionada() {
		return notificacionSeleccionada;
	}

	public void setNotificacionSeleccionada(Notificacion notificacionSeleccionada) {
		this.notificacionSeleccionada = notificacionSeleccionada;
	}

	public Parametro getParametroSeleccionado() {
		return parametroSeleccionado;
	}

	public void setParametroSeleccionado(Parametro parametroSeleccionado) {
		this.parametroSeleccionado = parametroSeleccionado;
	}

	public CuentaCorreo getCuentaCorreoSeleccionada() {
		return cuentaCorreoSeleccionada;
	}

	public void setCuentaCorreoSeleccionada(CuentaCorreo cuentaCorreoSeleccionada) {
		this.cuentaCorreoSeleccionada = cuentaCorreoSeleccionada;
	}

	public MallaReporte getMallaSeleccionada() {
		return mallaSeleccionada;
	}

	public void setMallaSeleccionada(MallaReporte mallaSeleccionada) {
		this.mallaSeleccionada = mallaSeleccionada;
	}

	public ValorMalla getValorMallaSeleccionado() {
		return valorMallaSeleccionado;
	}

	public void setValorMallaSeleccionado(ValorMalla valorMallaSeleccionado) {
		this.valorMallaSeleccionado = valorMallaSeleccionado;
	}

	public List<String> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<String> listaRol) {
		this.listaRol = listaRol;
	}

	public List<String> getListaMalla() {
		return listaMalla;
	}

	public void setListaMalla(List<String> listaMalla) {
		this.listaMalla = listaMalla;
	}

	public List<MallaReporte> getListaMallaReporte() {
		return listaMallaReporte;
	}

	public void setListaMallaReporte(List<MallaReporte> listaMallaReporte) {
		this.listaMallaReporte = listaMallaReporte;
	}

	public PlantillaLazyDataModel getListaPlantillas() {
		return listaPlantillas;
	}

	public void setListaPlantillas(PlantillaLazyDataModel listaPlantillas) {
		this.listaPlantillas = listaPlantillas;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		
		final FacesContext fc = FacesContext.getCurrentInstance();
		
	    if (fc.isValidationFailed()) {	    	
    		final boolean existGlobalMessages = !fc.getMessageList(null).isEmpty();
	        if (!existGlobalMessages) {
		    	mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_MSG_EDITAR_REPORTE_ERROR);
		    	return;
	        }
	    }
	    
		if (TipoPlantilla.CONFIGURACION.equals(this.xml.getTipoPlantilla())) {
			return;
		}
		
		if (recursoId != null && (GESTIONAR_REPORTE_CREAR.equals(recursoId) || GESTIONAR_REPORTE_MODIFICAR.equals(recursoId))) 
		{
			if (this.listaMallaReporte == null) {
				this.listaMallaReporte = new LinkedList<>();
			}
			if (this.mallaSeleccionada == null) {
				this.mallaSeleccionada = new MallaReporte();
			}
			if (this.valorMallaSeleccionado == null) {
				this.valorMallaSeleccionado = new ValorMalla();
			}
			return;
		}
	}

	/**
	 * Inicializa los atributos del controlador de vista del CRUD de reportes
	 */
	@PostConstruct
	public void init() {
		if (this.procesoSeleccionado == null) {
			this.procesoSeleccionado = new Reporte();
		}
		if (this.reporteSeleccionado == null) {
			this.reporteSeleccionado = new MallaConfiguracion();
		}
		this.registroSeleccionado = new Registro();
		this.sqlSeleccionada = new SQL();
		this.columnaSeleccionada = new Columna();
		this.parametroSeleccionado = new Parametro();
		this.notificacionSeleccionada = new Notificacion();
		this.cuentaCorreoSeleccionada = new CuentaCorreo();
		this.lstOrden = new LinkedList<>();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,
				ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);

	}

	/**
	 * Carga la primera paginacion de la grilla de plantillas de reportes, tambien
	 * es invocado para la busqueda con filtros
	 */
	public void printTable() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO + "->" + e.getMessage());
			return;
		}
		this.listaPlantillas = new PlantillaLazyDataModel(xml);

	}

	/**
	 * Crea una nueva plantilla de reporte
	 */
	@Override
	public String persist() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return null;
			}

			StringBuilder nombre = new StringBuilder();
			String[] elementosNombre = this.xml.getNombre().trim().split(" ");
			String nombreReporte;

			for (String elementoNombre : elementosNombre) {
				if (!elementoNombre.trim().isEmpty()) {
					if (!this.xml.getNombre().trim().startsWith(elementoNombre)) {
						nombre.append(StringUtil
								.UppercaseFirstLetters(StringUtil.reemplazarAcentos(elementoNombre).toLowerCase()));
					} else {
						nombre.append(StringUtil.reemplazarAcentos(elementoNombre).toLowerCase());
					}
				}
			}

			nombreReporte = nombre.toString().replace(" ", "") + FileUtil.XML;

			if (this.validarReporteExistente(nombreReporte)) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_CREAR_PLANTILLA_XML_EXISTE);
				return null;
			}
			
			/*Validar relaciones por cod_persona o por cod_entidad*/
			int relacionminima = this.listaMallaReporte.size() - 1 , cod_persona = 0, cod_entidad = 0, total_relaciones = 0;
			for (MallaReporte malla : this.listaMallaReporte) {
				for (MallaConfiguracion mallaConfig : this.xmlConfiguracion.getMallaConfiguracion()) {
					if (mallaConfig.getId().equals(malla.getId())) {
						for (Columna columna : mallaConfig.getColumna()) {
							if (PARAMETRO_PERSONA_ID.equalsIgnoreCase(columna.getNombreColumna())) {
								cod_persona++;
								break;
							}
						}
						for (Columna columna : mallaConfig.getColumna()) {
							if (PARAMETRO_ENTIDAD_ID.equalsIgnoreCase(columna.getNombreColumna())) {
								cod_entidad++;
								break;
							}
						}					
					}
				}
			}
			total_relaciones = (cod_persona- 1 ) ;
			if(total_relaciones<0) total_relaciones = 0;
			
			total_relaciones = total_relaciones + (cod_entidad - 1);
			
			if( this.listaMallaReporte.size() > 1 && (total_relaciones < relacionminima) ){
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_CREAR_PLANTILLA_REPORTE_NO_RELACION_MALLAS);
				return null;
			}			

			procesarConfiguracion();
			
			if (this.xml.getId() != null) {
				this.init();
				abrirAccion(0, GESTIONAR_REPORTE, FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_CREAR_PLANTILLA_XML
						+ ";" + RECURSO_ID + GESTIONAR_REPORTE_SUB_MENU);
				this.conversation.end();
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_CREAR_PLANTILLA_XML);
				return null;
			}
			Persona persona = ComunicacionServiciosHV.getPersonaPorId(getUsuarioSesion().getCodPersona());
			if (persona != null) {
				if (persona.getCorreoElectronico() != null && !persona.getCorreoElectronico().isEmpty()) {
					String mensage = ENCABEZADO_MENSAGE.replace("#nombreReporte", nombreReporte);
					ConnectionHttp.sendEmailHTML(persona.getCorreoElectronico(), mensage, ASUNTO);
				}
			}

		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
			return null;
		}
		return "";
	}


	/*
	 * @Author: Nestor Riasco
	 * 
	 * Metodo para validar si el reporte ya exite
	 * 
	 * @param reporte,
	 * 
	 * @return boolean
	 */

	private boolean validarReporteExistente(String reporte) throws IOException {
		String rutaXml = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML);
		File carpetaXml = new File(rutaXml);

		if (carpetaXml.exists()) {
			if (carpetaXml.isDirectory()) {
				File[] reportes = carpetaXml.listFiles();
				for (int i = 0; i < reportes.length; i++) {
					if (reporte.indexOf(reportes[i].getName()) != -1) {
						return true;
					}

				}
			}
		}
		return false;

	}

	/**
	 * Alista el CRUD de reportes de auerdo a los parametros recibidos
	 */
	@Override
	public void retrieve() throws NotSupportedException {
		if (!PlantillaBean.GESTIONAR_REPORTE_CATALOGO.equals(recursoId)) {
			if (FacesContext.getCurrentInstance().isPostback()) {
				return;
			}
			try {
				if (this.conversation.isTransient()) {
					this.conversation.begin();
					this.conversation.setTimeout(timeOut);
				}
			} catch (NonexistentConversationException e) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_URL_INVALID);
			}
		}

		try {
			this.xml = XmlReporte.getInstance();

			if (this.recursoId != null && !TipoPlantilla.CONFIGURACION.equals(xml.getTipoPlantilla())) {
				List<XmlReporte> plantillas = xmlProduces.getPlantillasReportes();
				this.listaMalla = new LinkedList<>();
				for (XmlReporte plantilla : plantillas) {
					if (plantilla.getPlantillaXML().endsWith(ConfigurationBundleConstants
							.getString(ConfigurationBundleConstants.CNS_URL_XML_CONFIGURACION_BASE_REPORTES))) {
						this.xmlConfiguracion = plantilla;
						break;
					}
				}
			}

			if (id != null && id > 0) {
				List<XmlReporte> plantillas = xmlProduces.getPlantillasReportes();
				this.xml.setId(BigInteger.valueOf(id));
				this.xml = plantillas.get(plantillas.indexOf(this.xml));
				this.nombrePlantilla = this.xml.getPlantillaXML().substring(
						this.xml.getPlantillaXML().lastIndexOf(FileUtil.BACKSLASH) + 1,
						this.xml.getPlantillaXML().length());
				this.nombrePlantilla = this.nombrePlantilla.replaceAll("//", "/");
				this.nombrePlantilla = getName(this.nombrePlantilla);
				BigInteger mallaId = this.reporteSeleccionado.getId();
				BigInteger valorMallaId = this.columnaSeleccionada.getId();

				if (TipoPlantilla.REPORTE.equals(this.xml.getTipoPlantilla())) {
					this.listaMalla = new LinkedList<>();
					this.listaMallaReporte = new LinkedList<>();
					this.listaMallaReporte.addAll(this.xml.getMallaReporte());

					for (XmlReporte plantilla : plantillas) {
						if (plantilla.getPlantillaXML().endsWith(FileUtil.BACKSLASH + ConfigurationBundleConstants
								.getString(ConfigurationBundleConstants.CNS_URL_XML_CONFIGURACION_BASE_REPORTES))) {
							this.xmlConfiguracion = plantilla;
							break;
						}
					}
				}

				cargarReporteEditar();
				int i = 0;
				if (recursoId != null) {
					switch (recursoId) {
					case GESTIONAR_REPORTE_AGREGAR_MALLA:
						this.reporteSeleccionado = new MallaConfiguracion();
						this.reporteSeleccionado.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
						cargarColumnasTabla();
						break;
					case GESTIONAR_REPORTE_MODIFICAR_MALLA:
						i = 0;
						if (mallaId != null) {
							for (MallaConfiguracion malla : xml.getMallaConfiguracion()) {
								if (malla.getId().equals(mallaId)) {
									this.reporteSeleccionado = xml.getMallaConfiguracion().get(i);
									break;
								}
								i++;
							}
						}
						cargarColumnasTabla();
						this.tablaGestionInformacion.setNombre(this.reporteSeleccionado.getNombreTabla());
						break;
					case GESTIONAR_REPORTE_AGREGAR_COLUMNA:
						this.columnaSeleccionada = new Columna();
					case GESTIONAR_REPORTE_MODIFICAR_COLUMNA:
						i = 0;
						if (mallaId != null) {
							for (MallaConfiguracion malla : xml.getMallaConfiguracion()) {
								if (malla.getId().equals(mallaId)) {
									this.reporteSeleccionado = xml.getMallaConfiguracion().get(i);
									break;
								}
								i++;
							}
						}
						if (valorMallaId != null) {
							i = 0;
							for (Columna valorMalla : this.reporteSeleccionado.getColumna()) {
								if (valorMalla.getId().equals(valorMallaId)) {
									this.columnaSeleccionada = this.reporteSeleccionado.getColumna().get(i);
									break;
								}
								i++;
							}
						}
						if (reporteSeleccionado != null && reporteSeleccionado.getNombreTabla() != null
								&& !reporteSeleccionado.getNombreTabla().isEmpty()) {
							this.tablaGestionInformacion = new TablaGestionInformacion();
							this.tablaGestionInformacion.setNombre(reporteSeleccionado.getNombreTabla());
							this.cargarColumnasTabla();
						}
						renderIngresoInformacion = true;
						break;
					case GESTIONAR_REPORTE_CREAR:
					case GESTIONAR_REPORTE_MODIFICAR:
						if (this.listaMallaReporte != null) {
							// Se cargan las mallas seleccionadas previamente
							for (MallaReporte seleccionada : this.listaMallaReporte) {
								for (MallaConfiguracion configuracion : this.xmlConfiguracion.getMallaConfiguracion()) {
									if (configuracion.getId().equals(seleccionada.getId())) {
										this.listaMalla.add(seleccionada.getId().toString());
										break;
									}
								}
							}
						}
						renderIngresoInformacion = true;
						break;
					case GESTIONAR_REPORTE_CONSULTAR:
					case GESTIONAR_REPORTE_ELIMINAR:
					case GESTIONAR_REPORTE_AGREGAR:
					case GESTIONAR_REPORTE_QUITAR:
					default:
						renderIngresoInformacion = false;
						break;
					}
				}

				BigInteger index = BigInteger.ZERO;

				for (MallaReporte mallaReporte : xml.getMallaReporte()) {
					for (ValorMalla valorMalla : mallaReporte.getValorMalla()) {
						if (valorMalla.getOrden() == null) {
							index = index.add(BigInteger.ONE);
							valorMalla.setOrden(index);
						}
						lstOrden.add(valorMalla);
					}
				}

				Collections.sort(this.lstOrden, new Comparator<ValorMalla>() {
					@Override
					public int compare(ValorMalla c1, ValorMalla c2) {
						return c1.getOrden().compareTo(c2.getOrden());
					}
				});
				this.listaRol = xml.getRol();
			} else {
				if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
					return;
				}
				if (GESTIONAR_REPORTE_SUB_MENU.equals(recursoId)) {
					listaPlantillas = new PlantillaLazyDataModel(xml);
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
	}

	/**
	 * Actualiza la plantilla seleccionada desde la grilla de busqueda
	 */
	@Override
	public String update() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return null;
			}
			procesarConfiguracion();
			this.init();

			if (GESTIONAR_REPORTE_AGREGAR_COLUMNA.equals(recursoId)
					|| GESTIONAR_REPORTE_MODIFICAR_COLUMNA.equals(recursoId)) {
				abrirAccion(id, COLUMNA,
						FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_EDITAR_PLANTILLA_XML + ";" + RECURSO_ID
								+ GESTIONAR_REPORTE_AGREGAR_COLUMNA + ";mallaReporte="
								+ this.reporteSeleccionado.getId());
			}
			if (GESTIONAR_REPORTE_AGREGAR_MALLA.equals(recursoId)
					|| GESTIONAR_REPORTE_MODIFICAR_MALLA.equals(recursoId)) {
				abrirAccion(id, "malla", FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_EDITAR_PLANTILLA_XML + ";"
						+ RECURSO_ID + GESTIONAR_REPORTE_AGREGAR_COLUMNA);
				this.conversation.end();
			} else {
				if (this.procesoSeleccionado.getId() == null) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_EDITAR_PLANTILLA_XML);
				} else {
					abrirAccion(0, GESTIONAR_REPORTE,
							FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_EDITAR_PLANTILLA_XML + ";" + RECURSO_ID
									+ GESTIONAR_REPORTE_SUB_MENU);
					this.conversation.end();
				}
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
			return null;
		}
		return "";
	}

	/**
	 * Genera una replica del estado actual de la plantilla del reporte antes de ser
	 * modificada. Esto, para efectos de trazabilidad
	 */
	private void copiarPlantilla() {
		String msg = "void copiarPlantilla()";
		InputStream in = null;
		OutputStream out = null;
		try {
			String carpetaDestinoAuditoria = ConfigurationBundleConstants
					.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML) + "backUp"
					+ FileUtil.getFileSeparator() + xml.getNombre() + FileUtil.getFileSeparator();
			File carpetaDestino = new File(carpetaDestinoAuditoria);
			if (!carpetaDestino.exists()) {
				carpetaDestino.mkdirs();
			}
			File origen = new File(
					ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
							+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			File destino = new File(carpetaDestinoAuditoria + FileUtil.getFileSeparator()
					+ DateUtils.formatearACadena(DateUtils.getFechaSistema(), "yyyyMMdd_HHmmss") + "_"
					+ this.getUsuarioSesion().getNombreUsuario().replace(".", "").toLowerCase() + "_"
					+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			in = new FileInputStream(origen);
			out = new FileOutputStream(destino);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			logger.error(msg, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.log().error("void copiarPlantilla()", e.getMessage());
			}
		}
	}

	/**
	 * Ubica la plantilla a publicar en la respectiva carpeta
	 */
	private boolean copiarPlantillaPublicar() {
		String msg = "void copiarPlantillaPublicar()";
		InputStream in = null;
		OutputStream out = null;
		String destinoPublicacion = null;

		if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL.equals(this.procesoSeleccionado.getFormaConsulta())) {
			destinoPublicacion = ConfigurationBundleConstants
					.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO);
		} else if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
				.equals(this.procesoSeleccionado.getFormaConsulta())) {
			destinoPublicacion = ConfigurationBundleConstants
					.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL);
		} else if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_CORREO
				.equals(this.procesoSeleccionado.getFormaConsulta())) {
			destinoPublicacion = ConfigurationBundleConstants
					.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CORREO);
		}

		if (destinoPublicacion == null) {
			return false;
		}
		String carpetaDestinoAuditoria = ConfigurationBundleConstants.getRutaArchivo(
				ConfigurationBundleConstants.CNS_PLANTILLAS_XML) + destinoPublicacion + FileUtil.getFileSeparator();
		File carpetaDestino = new File(carpetaDestinoAuditoria);
		if (!carpetaDestino.exists()) {
			carpetaDestino.mkdirs();
		}

		try {
			File origen = new File(
					ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
							+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			File destino = new File(carpetaDestinoAuditoria + FileUtil.getFileSeparator()
					+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			in = new FileInputStream(origen);
			out = new FileOutputStream(destino);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			return true;
		} catch (IOException e) {
			logger.error(msg, e);
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.log().error("void copiarPlantilla()", e.getMessage());
			}
		}
	}

	/**
	 * Ubica la plantilla a eliminar en la respectiva carpeta
	 */
	private boolean copiarPlantillaEliminar() {
		String msg = "void copiarPlantillaEliminar()";
		InputStream in = null;
		OutputStream out = null;
		String destinoPublicacion = "eliminado";

		String carpetaDestinoAuditoria = ConfigurationBundleConstants.getRutaArchivo(
				ConfigurationBundleConstants.CNS_PLANTILLAS_XML) + destinoPublicacion + FileUtil.getFileSeparator();
		File carpetaDestino = new File(carpetaDestinoAuditoria);
		if (!carpetaDestino.exists()) {
			carpetaDestino.mkdirs();
		}
		File origen = null;
		File destino = null;
		try {
			origen = new File(
					ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
							+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			destino = new File(carpetaDestinoAuditoria + FileUtil.getFileSeparator()
					+ DateUtils.formatearACadena(DateUtils.getFechaSistema(), "yyyyMMdd_HHmmss") + "_"
					+ this.getUsuarioSesion().getNombreUsuario().replace(".", "").toLowerCase() + "_"
					+ this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
			in = new FileInputStream(origen);
			out = new FileOutputStream(destino);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (IOException e) {
			logger.error(msg, e);
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				FileUtil.moverArchivo(origen.getPath(), destino.getPath());
			} catch (IOException e) {
				logger.log().error("void copiarPlantilla()", e.getMessage());
			}
		}
		return !origen.exists();
	}

	/**
	 * Ubica la plantilla a quitarla de la respectiva carpeta
	 */
	private boolean copiarPlantillaQuitar() {
		String msg = "void copiarPlantillaQuitar()";
		try {
			String destinoQuitar = null;

			if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL.equals(this.procesoSeleccionado.getFormaConsulta())) {
				destinoQuitar = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO);
			} else if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
					.equals(this.procesoSeleccionado.getFormaConsulta())) {
				destinoQuitar = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL);
			} else if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_CORREO
					.equals(this.procesoSeleccionado.getFormaConsulta())) {
				destinoQuitar = ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CORREO);
			}

			if (destinoQuitar == null) {
				return false;
			}

			String rutaQuitarPublicacion = ConfigurationBundleConstants
					.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML) + destinoQuitar;
			File carpetaPublicacionCorreo = new File(rutaQuitarPublicacion);
			if (carpetaPublicacionCorreo.exists()) {

				for (File reporte : carpetaPublicacionCorreo.listFiles()) {
					String nameFile = reporte.getName();
					String plantilla = this.nombrePlantilla.replace("/", "");
					if (nameFile.equals(plantilla)) {
						if (reporte.delete()) {
							this.procesoSeleccionado.setPublicado(false);
						} else {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.getStringMessagesBundle(
											MessagesBundleConstants.DLG_ELIMINACION_FALLIDA, getLocale()));
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error(msg, e);
			return false;
		}
		return true;
	}

	/**
	 * Valida que el nombre de la plantilla sea valido
	 */
	private void validarNombrePlantilla() throws SIGEP2SistemaException {
		this.nombrePlantilla = this.nombrePlantilla.replaceAll("//", "/");
		this.nombrePlantilla = getName(this.nombrePlantilla);
		Pattern pat = Pattern.compile(ExpresionesRegularesConstants.REGEX_NOMBRE_PLANTILLA_ARCHIVO_CARGUE);
		Matcher mat = pat.matcher(this.nombrePlantilla.replace(FileUtil.BACKSLASH, "").replace(FileUtil.SLASH, ""));
		if (!mat.matches()) {
			logger.log().error("void validarNombrePlantilla() throws SIGEP2SistemaException", MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_NOMBRE_INVALIDO, getLocale()));
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_NOMBRE_INVALIDO, getLocale()));
		}
	}

	/**
	 * Permite eliminar el reporte de la lista de reportes que el sistema genera
	 * periodicamente
	 */
	@Override
	public void delete() throws NotSupportedException {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('borrarReporte').hide();");
			procesarConfiguracion();
			if (this.procesoSeleccionado.isEliminado()) {
				this.init();
				abrirAccion(0, GESTIONAR_REPORTE, FACES_REDIRECT + MessagesBundleConstants.DLG_ELIMINACION_EXITOSA + ";"
						+ RECURSO_ID + GESTIONAR_REPORTE_SUB_MENU);
				this.conversation.end();
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
	}

	/**
	 * Adiciona una columna al bloque de maya de configuracion en la plantilla base
	 * <code></code>
	 */
	public void seleccionarColumnaDialogo() {
		if (this.columnaSeleccionada.getId() == null) {
			this.columnaSeleccionada.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
		}
		if (this.reporteSeleccionado.getColumna().contains(this.columnaSeleccionada)) {
			this.reporteSeleccionado.getColumna().remove(this.columnaSeleccionada);
		}
		this.reporteSeleccionado.getColumna().add(this.columnaSeleccionada);

		Collections.sort(this.reporteSeleccionado.getColumna(), new Comparator<Columna>() {
			@Override
			public int compare(Columna c1, Columna c2) {
				return c1.getId().compareTo(c2.getId());
			}
		});

		for (Columna columna : this.reporteSeleccionado.getColumna()) {
			if (columna.getId() == null) {
				columna.setId(new BigInteger(String.valueOf(Calendar.getInstance().getTimeInMillis())));
			}
		}
		this.columnaSeleccionada = new Columna();
	}

	/**
	 * Remueve el bloque de configuracion de la plantilla base del CRUD de reportes
	 */
	public void quitarReporte() {
		if (this.xml.getMallaConfiguracion().contains(reporteSeleccionado)) {
			this.xml.getMallaConfiguracion().remove(reporteSeleccionado);
		}
	}

	/**
	 * Remueve el valor malla de la malla seleccionada
	 */
	public void quitarValorMallaReporte() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('quitarValorMalla').hide();");

		if (this.listaMallaReporte.contains(mallaSeleccionada)
				&& mallaSeleccionada.getValorMalla().contains(valorMallaSeleccionado)) {
			mallaSeleccionada.getValorMalla().remove(valorMallaSeleccionado);
			lstOrden.remove(valorMallaSeleccionado);
		}
	}

	/**
	 * Remueve la columna seleccionada de la plantilla de configuracion
	 */
	public void quitarColumna() {
		int i = 0;
		for (Columna columna : reporteSeleccionado.getColumna()) {
			if (columna.getId().equals(this.columnaSeleccionada.getId())) {
				reporteSeleccionado.getColumna().remove(i);
				return;
			}
			i++;
		}
	}

	/**
	 * Completa la carga de la plantilla seleccionada, validando si cumple o no con
	 * la definicion en el <code>reporte_1_0.xsd</code>
	 */
	public void cargarReporteEditar() {
		try {
			this.init();
			if (xml == null || xml.getNombre().isEmpty()) {
				throw new SIGEP2SistemaException(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_EDITAR_PLANTILLA_XML, getLocale()));
			}
			xmlProduces.setTipoPlantilla(xml.getTipoPlantilla());
			formaConsultaAnterior = xml.getFormaConsulta();
		} catch (Exception e) {
			this.init();
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
	}

	/**
	 * Permite visualizar el reporte como publicado en el catalogo de reportes
	 */
	public void publicar() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			procesarConfiguracion();
			if (this.procesoSeleccionado.isPublicado()) {
				this.init();
				abrirAccion(0, GESTIONAR_REPORTE,
						FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_PUBLICAR_PLANTILLA_XML + ";" + RECURSO_ID
								+ GESTIONAR_REPORTE_SUB_MENU);
				this.conversation.end();
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_PUBLICAR_PLANTILLA_XML);
			}
		} catch (Exception e) {
			logger.error("void publicar()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, e.getMessage());
		}
	}

	/**
	 * Permite eliminar el reporte publicado del catalogo de reportes
	 */
	public void quitar() {

		try {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('quitarRep').hide();");

			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			procesarConfiguracion();
			if (!this.procesoSeleccionado.isPublicado()) {
				this.init();
				abrirAccion(0, GESTIONAR_REPORTE, FACES_REDIRECT + MessagesBundleConstants.MSG_INFO_QUITAR_PLANTILLA_XML
						+ ";" + RECURSO_ID + GESTIONAR_REPORTE_SUB_MENU);
				this.conversation.end();
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_QUITAR_PLANTILLA_XML);
			}
		} catch (Exception e) {
			logger.error("void quitar()", e);
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_QUITAR_PLANTILLA_XML);
		}
	}

	/**
	 * Valida la configuracion del reporte y guarda los cambios
	 */
	void procesarConfiguracion() throws Exception {
		if (xml.getXmlFile().exists() && id == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					xml.getXmlFile().getAbsolutePath());
			throw new SIGEP2SistemaException(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE, getLocale()));
		}

		this.procesoSeleccionado = new Reporte();

		if (this.xml.getId() == null) {
			synchronized (this) {
				this.xml.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
			}
		}

		if (formaConsultaAnterior != null && !formaConsultaAnterior.equals(this.xml.getFormaConsulta())) {
			this.procesoSeleccionado.setFormaConsulta(formaConsultaAnterior);
			copiarPlantillaQuitar();
		}

		this.procesoSeleccionado.setId(this.xml.getId());
		this.procesoSeleccionado.setVersionEsquema(this.xml.getVersionEsquema());
		this.procesoSeleccionado.setNombre(this.xml.getNombre());
		this.procesoSeleccionado.setDescripcion(this.xml.getDescripcion());
		this.procesoSeleccionado.setFormaConsulta(this.xml.getFormaConsulta());
		this.procesoSeleccionado.setFormatoReporte(this.xml.getFormatoReporte());
		this.procesoSeleccionado.setPeriodoGeneracion(this.xml.getPeriodoGeneracion());
		this.procesoSeleccionado.setClaseJava(this.xml.getClaseJava());
		this.procesoSeleccionado.setTipoGrafico(this.xml.getTipoGrafico());

		this.procesoSeleccionado.getRol().addAll(this.listaRol);
		if (this.listaMallaReporte != null) {
			this.procesoSeleccionado.getMallaReporte().addAll(this.listaMallaReporte);
		}
		this.procesoSeleccionado.getMallaConfiguracion().addAll(this.xml.getMallaConfiguracion());
		this.procesoSeleccionado.getNotificacion().addAll(this.xml.getNotificacion());
		this.procesoSeleccionado.setTipoPlantilla(this.xml.getTipoPlantilla());

		if (this.xml.getUsuarioSolicita() == null || this.xml.getUsuarioSolicita().getNombreCuenta() == null
				|| this.xml.getUsuarioSolicita().getNombreCuenta().isEmpty()) {
			CuentaCorreo usuarioSolicita = new CuentaCorreo();
			usuarioSolicita.setBandeja(TipoBandeja.A);
			String correoElectronico = getUsuarioSesion().getCorreoElectronico() != null
					? getUsuarioSesion().getCorreoElectronico()
					: ConfigurationBundleConstants.mailSystem();
			usuarioSolicita.setNombreCuenta(correoElectronico);

			this.procesoSeleccionado.setUsuarioSolicita(usuarioSolicita);
		} else {
			this.procesoSeleccionado.setUsuarioSolicita(this.xml.getUsuarioSolicita());
		}

		// Acciones propias del CRUD sobre la plantilla
		if (!TipoPlantilla.CONFIGURACION.equals(this.procesoSeleccionado.getTipoPlantilla())) {
			generarSentenciaSQL();
			if (this.procesoSeleccionado.getSQL().isEmpty()) {
				this.xml.setId(null);
				return;
			}
		}

		switch (recursoId) {
		case GESTIONAR_REPORTE_AGREGAR_MALLA:
			this.procesoSeleccionado.getMallaConfiguracion().add(reporteSeleccionado);
		case GESTIONAR_REPORTE_MODIFICAR_MALLA:
			break;
		case GESTIONAR_REPORTE_AGREGAR_COLUMNA:
		case GESTIONAR_REPORTE_MODIFICAR_COLUMNA:
			seleccionarColumnaDialogo();
			this.procesoSeleccionado.getMallaConfiguracion()
					.remove(this.procesoSeleccionado.getMallaConfiguracion().indexOf(this.reporteSeleccionado));
			this.procesoSeleccionado.getMallaConfiguracion().add(this.reporteSeleccionado);
			break;
		case GESTIONAR_REPORTE_CREAR:
			this.procesoSeleccionado.setTipoPlantilla(TipoPlantilla.REPORTE);
			StringBuilder nombre = new StringBuilder();
			StringBuilder nombreReporte = new StringBuilder();
			String[] elementosNombre = this.xml.getNombre().trim().split(" ");

			for (String elementoNombre : elementosNombre) {
				if (!elementoNombre.trim().isEmpty()) {
					if (!this.xml.getNombre().trim().startsWith(elementoNombre)) {
						nombre.append(StringUtil
								.UppercaseFirstLetters(StringUtil.reemplazarAcentos(elementoNombre).toLowerCase()));
					} else {
						nombre.append(StringUtil.reemplazarAcentos(elementoNombre).toLowerCase());
					}
					nombreReporte.append(StringUtil.UppercaseFirstLetters(elementoNombre.toLowerCase()) + " ");
				}
			}
			this.procesoSeleccionado.setNombre(nombreReporte.toString().trim());
			this.nombrePlantilla = nombre.toString().replace(" ", "") + FileUtil.XML;

			this.xml.setVersionEsquema(BigDecimal.valueOf(1.0).toString());
			break;
		case GESTIONAR_REPORTE_MODIFICAR:
			break;
		case GESTIONAR_REPORTE_CONSULTAR:
			break;
		case GESTIONAR_REPORTE_ELIMINAR:
			String rutaCarpetaPublicacionCorreo = ConfigurationBundleConstants
					.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
					+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CORREO);
			rutaCarpetaPublicacionCorreo = rutaCarpetaPublicacionCorreo.replace("//", "/");
			File carpetaPublicacionCorreo = new File(rutaCarpetaPublicacionCorreo);
			if (carpetaPublicacionCorreo.exists()) {
				boolean plantillaExiste = false;
				for (File reporteCorreo : carpetaPublicacionCorreo.listFiles()) {
					if (reporteCorreo.getPath().endsWith(FileUtil.BACKSLASH + this.nombrePlantilla)) {
						plantillaExiste = true;
						if (reporteCorreo.delete()) {
							this.procesoSeleccionado.setEliminado(true);
						} else {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.getStringMessagesBundle(
											MessagesBundleConstants.DLG_ELIMINACION_FALLIDA, getLocale()));
						}
						break;
					}
				}
				if (!plantillaExiste) {
					this.procesoSeleccionado.setEliminado(true);
				}

				this.procesoSeleccionado.setEliminado(copiarPlantillaEliminar());
			}
			break;
		case GESTIONAR_REPORTE_AGREGAR:
			if (copiarPlantillaPublicar()) {
				this.procesoSeleccionado.setPublicado(true);
				for (Notificacion notificacionPublicacion : xmlConfiguracion.getNotificacion()) {
					if (TipoValidacion.PUBLICACION.equals(notificacionPublicacion.getTipoValidacion())) {
						String asunto = notificacionPublicacion.getAsunto().replace("NOMBRE_REPORTE", xml.getNombre());
						String cuerpoP = notificacionPublicacion.getCuerpo().replace("NOMBRE_REPORTE", xml.getNombre())
								.replace("RUTA_REPORTE", ConfigurationBundleConstants.getPrefijoApp());

						GestionInformacionDelegate.enviarMail(asunto, cuerpoP,
								ConfigurationBundleConstants.adminCuentaCorreo(),
								Arrays.asList(xml.getUsuarioSolicita().getNombreCuenta()), null);
						break;
					}
				}
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_PLANTILLA_XML,
								getLocale()));
				return;
			}
			break;
		case GESTIONAR_REPORTE_QUITAR:
			if (copiarPlantillaQuitar()) {
				this.procesoSeleccionado.setPublicado(false);
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_FATAL_PLANTILLA_XML,
								getLocale()));
				return;
			}
			break;
		default:
			break;
		}

		// Se actualiza la version del reporte
		Double versionEsquema = null;
		switch (recursoId) {
		case GESTIONAR_REPORTE_AGREGAR_COLUMNA:
		case GESTIONAR_REPORTE_MODIFICAR_COLUMNA:
		case GESTIONAR_REPORTE_MODIFICAR:
		case GESTIONAR_REPORTE_ELIMINAR:
			if (procesoSeleccionado.isEliminado()) {
				versionEsquema = Double.valueOf(this.xml.getVersionEsquema()) + 0.1;
				this.procesoSeleccionado.setVersionEsquema(versionEsquema.toString());
			}
			break;
		default:
			this.procesoSeleccionado.setVersionEsquema(this.xml.getVersionEsquema());
		}

		if (!procesoSeleccionado.isEliminado()) {
			this.validarNombrePlantilla();
			if (!PlantillaBean.GESTIONAR_REPORTE_CATALOGO.equals(recursoId)) {
				this.copiarPlantilla();

				// Se crea la plantilla xml
				XmlReporte.getPlantillaXml(this.procesoSeleccionado, this.nombrePlantilla, "");
				// Se valida que la plantilla corresponda con la estructura del
				// xsd
				// obteniendo un objecto desde el xml
				xml = XmlReporte.getEstructura(XmlReporte.getXml(this.nombrePlantilla, ""));

			} else {
				xml.unsetRegistro();
				xml.getRegistro().addAll(procesoSeleccionado.getRegistro());
				xml.unsetSQL();
				xml.getSQL().addAll(procesoSeleccionado.getSQL());
			}
		}
	}

	/**
	 * Determina a que funcionalidades del CRUD de reportes tiene permiso el usuario
	 * en sesion desde la configuracion del reporte
	 * 
	 * @param crud        Configuracion permiso para el item de recurso
	 * @param dataReporte Configuracion del reporte
	 * 
	 * @return {@link Boolean} indica si el usuario tiene o no permiso para el
	 *         recurso
	 */
	public boolean tienePermiso(PermisoUsuarioRolDTO crud, XmlReporte dataReporte) {
		if (crud==null  || crud.getAccion() == null) {
			return false;
		}

		if (TipoPlantilla.CONFIGURACION.equals(dataReporte.getTipoPlantilla())
				&& "TTL_REPORTES_MODIFICAR".equals(crud.getAccion())) {
			return true;
		} else if (TipoPlantilla.REPORTE.equals(dataReporte.getTipoPlantilla())) {
			if (!"TTL_REPORTES_CREAR".equals(crud.getAccion())) {
				switch (crud.getAccion()) {
				case "TTL_REPORTES_A_CATALOGO":
					if (!dataReporte.isPublicado() && !dataReporte.isEliminado()) {
						return true;
					}
					break;
				case "TTL_REPORTES_R_CATALOGO":
					if (dataReporte.isPublicado() && !dataReporte.isEliminado()) {
						return true;
					}
					break;
				case "TTL_REPORTES_ELIMINAR":
					if (dataReporte.getPeriodoGeneracion() != null && !dataReporte.isEliminado()) {
						return true;
					}
					break;
				default:
					if (!dataReporte.isEliminado()) {
						return true;
					}
				}
			}
		}
		return false;

	}

	/**
	 * Carga las lista que corresponden a la configuracion en DDBB
	 */
	public void cargarColumnasTabla() {
		if (tablaGestionInformacion != null) {
			this.listaColumnaTablaGestionInformacion = ComunicacionServiciosGI
					.columnasTablasGestionInformacion(tablaGestionInformacion);
		} else {
			this.listaTablaGestionInformacion = ComunicacionServiciosGI.tablasGestionInformacion();
			this.tablaGestionInformacion = new TablaGestionInformacion();
		}
	}

	/**
	 * Carga las mallas desde la configuracion base a la configuracion del reporte
	 * 
	 * @param event Captura el evento de seleccion desde la vista
	 */
	public void cargarMallas(AjaxBehaviorEvent event) {
		SelectManyCheckbox selectManyCheckbox = (SelectManyCheckbox) event.getSource();
		Object[] mallasSeleccionadas = (Object[]) selectManyCheckbox.getSubmittedValue();

		if (this.listaMallaReporte == null) {
			this.listaMallaReporte = new LinkedList<>();
		}

		try {
			if (mallasSeleccionadas != null) {
				if (mallasSeleccionadas.length == 0) {
					this.listaMalla = new LinkedList<>();
					this.listaMallaReporte = new LinkedList<>();
					this.xml.unsetMallaReporte();
					return;
				}

				List<String> mallasEliminadas = new LinkedList<>();
				for (String malla : this.listaMalla) {
					boolean eliminada = true;
					for (int i = 0; i < mallasSeleccionadas.length; i++) {
						if (malla.equals(mallasSeleccionadas[i])) {
							eliminada = false;
							break;
						}
					}
					if (eliminada) {
						mallasEliminadas.add(malla);
					}
				}
				for (String mallaEliminada : mallasEliminadas) {
					Iterator<MallaReporte> temp = this.listaMallaReporte.iterator();
					while (temp.hasNext()) {
						MallaReporte m = temp.next();
						if (m.getId().equals(BigInteger.valueOf(Long.valueOf(mallaEliminada)))) {
							this.listaMalla.remove(mallaEliminada);
							
							for (ValorMalla vm: m.getValorMalla()){
								lstOrden.remove(vm);
							}
							this.listaMallaReporte.remove(m);
							break;
						}
					}
				}

				for (int i = 0; i < mallasSeleccionadas.length; i++) {
					for (MallaConfiguracion mallaSeleccionadaConfiguracion : this.xmlConfiguracion
							.getMallaConfiguracion()) {
						String idMalla = (String) mallasSeleccionadas[i];
						if (mallaSeleccionadaConfiguracion.getId().equals(BigInteger.valueOf(Long.valueOf(idMalla)))) {
							MallaReporte malla = new MallaReporte();
							malla.setId(mallaSeleccionadaConfiguracion.getId());
							malla.setNombre(mallaSeleccionadaConfiguracion.getNombre());

							if (malla.getId() != null && !this.listaMallaReporte.contains(malla)) {
								boolean existe = false;
								for (MallaReporte m : listaMallaReporte) {
									if (m.getId().equals(malla.getId())) {
										existe = true;
										break;
									}
								}
								if (!existe) {
									ValorMalla nuevoValorMalla = new ValorMalla();
									nuevoValorMalla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
									malla.getValorMalla().add(nuevoValorMalla);
									this.listaMallaReporte.add(malla);
								} else {
									break;
								}
							} else {
								mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
										MessagesBundleConstants.DLG_VALOR_REQUERIDO);
							}
						}
					}
				}
			}

			Collections.sort(this.listaMallaReporte, new Comparator<MallaReporte>() {
				@Override
				public int compare(MallaReporte r1, MallaReporte r2) {
					return r1.getNombre().compareTo(r2.getNombre());
				}
			});
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * Agrega un nuevo valor malla a la malla seleccionada
	 * 
	 * @param idMalla identificador de la malla seleccionada
	 */
	public void agregarValorMalla(BigInteger idMalla) {
		if (listaMallaReporte != null) {
			for (MallaReporte mr : this.listaMallaReporte) {
				if (mr.getId().equals(idMalla)) {
					this.mallaSeleccionada = mr;
					break;
				}
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
			return;
		}
		if (mallaSeleccionada == null) {
			return;
		}
		if (this.listaMallaReporte.contains(mallaSeleccionada)) {
			for (ValorMalla v : mallaSeleccionada.getValorMalla()) {
				if (v.getNombre() == null || v.getNombre().isEmpty()) {
					mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_VALOR_REQUERIDO);
					return;
				}
			}
			ValorMalla valorMalla = new ValorMalla();
			valorMalla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
			mallaSeleccionada.getValorMalla().add(valorMalla);
		}
	}

	/**
	 * Carga la configuracion por defecto desde la columna de la configuracion de la
	 * malla
	 * 
	 * @param idMalla    identificador de la malla seleccionada
	 * @param valorMalla valor malla a predeterminar
	 */
	public void cargarValorMalla(BigInteger idMalla, ValorMalla valorMalla) {
		if (idMalla != null && valorMalla != null) {
			for (MallaConfiguracion malla : this.xmlConfiguracion.getMallaConfiguracion()) {
				if (idMalla.equals(malla.getId())) {
					List<Columna> columnasMalla = malla.getColumna();
					for (Columna columna : columnasMalla) {
						if (columna.getId().equals(valorMalla.getId())) {
							valorMalla.setNombre(columna.getEtiquetaColumna());
							valorMalla.setNombreColumna(columna.getNombreColumna());
							if (columna.isAgrupamiento()) {
								valorMalla.setPermiteOperacionesAgrupamiento(true);
								valorMalla.setAgrupamiento(columna.isAgrupamiento());
								valorMalla.setTotalizado(columna.isTotalizado());
								valorMalla.setConteo(columna.isConteo());
							} else {
								valorMalla.setPermiteOperacionesAgrupamiento(true);
								valorMalla.setAgrupamiento(false);
								valorMalla.setTotalizado(false);
								valorMalla.setConteo(false);
							}
							String prefijoPK = "cod_";
							valorMalla.setMostrarEnReporte(
									!columna.getNombreColumna().toLowerCase().startsWith(prefijoPK));
							valorMalla.setEsParametro(columna.getNombreColumna().toLowerCase().startsWith(prefijoPK));
							lstOrden.add(valorMalla);
						}
					}
				}
			}
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_VALOR_REQUERIDO);
		}
	}

	/**
	 * Carga los columnas de la respectiva configuracion de la malla seleccionada
	 * 
	 * @param idMalla identificador de la malla seleccionada
	 * @return {@link List} de {@link Columna} Columnas de la configuracion de la
	 *         malla
	 */
	public List<Columna> getColumnasMalla(BigInteger idMalla) {
		List<Columna> columnasMalla = new LinkedList<>();
		if (idMalla != null) {
			for (MallaConfiguracion malla : this.xmlConfiguracion.getMallaConfiguracion()) {
				if (idMalla.equals(malla.getId())) {
					return malla.getColumna();
				}
			}
		}
		return columnasMalla;
	}

	/**
	 * Tomando la configuracion seleccionada para la plantilla de reporte se generan
	 * la sentencia SQL para el reporte
	 */
	void generarSentenciaSQL() {
		if (this.listaMallaReporte == null || this.listaMallaReporte.isEmpty()) {
			return;
		}
		MallaReporte mallaReporte = null;
		if (this.listaMallaReporte.size() == 1)
			mallaReporte = listaMallaReporte.get(0);

		List<Parametro> parametros = new LinkedList<>();

		StringBuilder select = new StringBuilder("select * from (select distinct ");
		StringBuilder selectTotal = new StringBuilder("select * from (select ");
		StringBuilder from = new StringBuilder(" from ");
		StringBuilder groupBy = new StringBuilder(" group by ");
		StringBuilder groupByGrafico = new StringBuilder(" group by ");

		Registro cabecera = new Registro();
		cabecera.setDescripcion(this.procesoSeleccionado.getNombre());
		cabecera.setCsv(false);
		cabecera.setTipoRegistro(TipoRegistro.CABECERA);
		cabecera.setContieneSentenciaSQL(false);

		Registro detalle = new Registro();
		detalle.setDescripcion(this.procesoSeleccionado.getNombre());
		detalle.setCsv(false);
		detalle.setTipoRegistro(TipoRegistro.DETALLE);
		detalle.setContieneSentenciaSQL(true);

		Registro total = new Registro();
		total.setDescripcion(this.procesoSeleccionado.getNombre());
		total.setCsv(false);
		total.setTipoRegistro(TipoRegistro.PIE);
		total.setContieneSentenciaSQL(true);

		boolean esPrimeraTabla = true;
		boolean agregarTablaJoin = true;
		boolean tieneTotal = false;
		String prefijoAnterior = "";
		String prefijoAnteriorPersona = "";
		boolean tieneAgrupamiento = false;
		int itemCol = 0;
		boolean todasTienenCodPersona = true;

		List<String> tablasCodPersona = new LinkedList<>();

		for (MallaReporte malla : this.listaMallaReporte) {
			for (MallaConfiguracion mallaConfig : this.xmlConfiguracion.getMallaConfiguracion()) {
				if (mallaConfig.getId().equals(malla.getId())) {
					List<Columna> columnasFiltrar = new LinkedList<>();
					columnasFiltrar.addAll(mallaConfig.getColumna());
					boolean flag = false;
					for (Columna columna : columnasFiltrar) {
						if ("cod_persona".equalsIgnoreCase(columna.getNombreColumna())) {
							flag = true;
							tablasCodPersona.add(mallaConfig.getPrefijoTabla());
							break;
						}
					}
					if (!flag) {
						todasTienenCodPersona = false;
					}
				}
			}
		}
		
		if (tablasCodPersona.size() >= 2) {
			todasTienenCodPersona = true;
		} else {
			todasTienenCodPersona = false;
		}

		BigInteger idMalla = null;
		List<MallaReporte> listaMallaReporteTemp = new LinkedList<>();
		List<MallaConfiguracion> listaMallaConfiValidadoTemp = new LinkedList<>();

		listaMallaReporteTemp.addAll(this.listaMallaReporte);

		boolean fromTable = false;

		for (MallaConfiguracion mallaConfig : this.xmlConfiguracion.getMallaConfiguracion()) {
			if (mallaReporte != null && mallaReporte.getId().equals(mallaConfig.getId())) {
				if (!fromTable) {
					fromTable = true;
					from.append(mallaConfig.getNombreTabla().toLowerCase());
					from.append(" ");
					from.append(mallaConfig.getPrefijoTabla().toLowerCase());
				}
			}
			if (idMalla != null && this.listaMallaReporte.size() == 1) {
				continue;
			} else if (idMalla == null && this.listaMallaReporte.size() == 1) {
				idMalla = BigInteger.ZERO;
				agregarTablaJoin = false;
			} else if (this.listaMallaReporte.size() > 1) {
				agregarTablaJoin = true;
				for (MallaReporte malla : listaMallaReporteTemp) {
					if (!listaMallaConfiValidadoTemp.contains(mallaConfig)) {
						if (malla.getId().equals(mallaConfig.getId())) {
							idMalla = malla.getId();
							listaMallaConfiValidadoTemp.add(mallaConfig);
							if (listaMallaConfiValidadoTemp.size() > 1) {
								esPrimeraTabla = false;
							}
							agregarTablaJoin = true;
							break;
						}
						agregarTablaJoin = false;
					} else {
						agregarTablaJoin = false;
						break;
					}
				}
			}

			if (agregarTablaJoin && !esPrimeraTabla && this.listaMallaReporte.size() > 1) {
				from.append(" inner join ");
			}
			if (agregarTablaJoin) {
				from.append(mallaConfig.getNombreTabla().toLowerCase());
				from.append(" ");
				from.append(mallaConfig.getPrefijoTabla().toLowerCase());
			}

			if (agregarTablaJoin && !esPrimeraTabla && this.listaMallaReporte.size() > 1) {
				from.append(" on ");
				from.append(prefijoAnterior);
				from.append(".cod_entidad = ");
				from.append(mallaConfig.getPrefijoTabla().toLowerCase());
				from.append(".cod_entidad ");
				if (todasTienenCodPersona) {
					if (tablasCodPersona.contains(mallaConfig.getPrefijoTabla().toLowerCase())) {
						if (tablasCodPersona.contains(prefijoAnterior)) {
							from.append(" and ");
							from.append(prefijoAnterior);
							from.append(".cod_persona = ");
							from.append(mallaConfig.getPrefijoTabla().toLowerCase());
							from.append(".cod_persona ");
						} else if (tablasCodPersona.contains(prefijoAnteriorPersona)) {
							from.append(" and ");
							from.append(prefijoAnteriorPersona);
							from.append(".cod_persona = ");
							from.append(mallaConfig.getPrefijoTabla().toLowerCase());
							from.append(".cod_persona ");
						}

						prefijoAnteriorPersona = mallaConfig.getPrefijoTabla().toLowerCase();
					}
				}
			}
			if (agregarTablaJoin) {
				prefijoAnterior = mallaConfig.getPrefijoTabla().toLowerCase();
			}
		}

		for (ValorMalla valorMalla : lstOrden) {
			for (MallaConfiguracion mallaConfig : this.xmlConfiguracion.getMallaConfiguracion()) {
				for (Columna columna : mallaConfig.getColumna()) {
					if (columna.getId().equals(valorMalla.getId())) {
						if (valorMalla.isMostrarEnReporte()) {
							itemCol++;

							columna.setEtiquetaColumna(valorMalla.getNombre());
							if (xml.getTipoGrafico() != null) {
								columna.setGraficar(valorMalla.isGraficar());
							} else {
								valorMalla.setGraficar(false);
							}

							cabecera.getColumna().add(columna);
							detalle.getColumna().add(columna);
							total.getColumna().add(columna);

							if (valorMalla.isConteo() || valorMalla.isTotalizado() || valorMalla.isGraficar()) {
								tieneTotal = true;
								if (valorMalla.isGraficar()) {
									if (valorMalla.isTotalizado()) {
										select.append("sum(");
										select.append(mallaConfig.getPrefijoTabla().toLowerCase());
										select.append(".");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append(") ");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append("_");
										select.append(itemCol);
										select.append(", ");

										selectTotal.append("sum(");
										selectTotal.append(mallaConfig.getPrefijoTabla().toLowerCase());
										selectTotal.append(".");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append(") ");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append("_");
										selectTotal.append(itemCol);
										selectTotal.append(", ");

										columna.setTotalizado(true);
									} else if (valorMalla.isConteo()) {
										if (valorMalla.isAgrupamiento()) {
											select.append(mallaConfig.getPrefijoTabla().toLowerCase());
											select.append(".");
											select.append(columna.getNombreColumna().toLowerCase());
											select.append(" ");
											select.append(columna.getNombreColumna().toLowerCase());
											select.append("_");
											select.append(itemCol++);
											select.append(", ");
										}
										select.append("count(");
										select.append(mallaConfig.getPrefijoTabla().toLowerCase());
										select.append(".");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append(") ");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append("_");
										select.append(itemCol);
										select.append(", ");

										selectTotal.append("count(");
										selectTotal.append(mallaConfig.getPrefijoTabla().toLowerCase());
										selectTotal.append(".");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append(") ");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append("_");
										selectTotal.append(itemCol);
										selectTotal.append(", ");

										columna.setTotalizado(true);
									} else {
										select.append(mallaConfig.getPrefijoTabla().toLowerCase());
										select.append(".");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append(" ");
										select.append(columna.getNombreColumna().toLowerCase());
										select.append("_");
										select.append(itemCol);
										select.append(", ");

										selectTotal.append(mallaConfig.getPrefijoTabla().toLowerCase());
										selectTotal.append(".");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append(" ");
										selectTotal.append(columna.getNombreColumna().toLowerCase());
										selectTotal.append("_");
										selectTotal.append(itemCol);
										selectTotal.append(", ");

										valorMalla.setAgrupamiento(true);
										columna.setTotalizado(false);
									}
								} else if (valorMalla.isConteo()) {
									select.append("count(");
									select.append(mallaConfig.getPrefijoTabla().toLowerCase());
									select.append(".");
									select.append(columna.getNombreColumna().toLowerCase());
									select.append(") ");
									select.append(columna.getNombreColumna().toLowerCase());
									select.append("_");
									select.append(itemCol);
									select.append(", ");

									selectTotal.append("count(");
									selectTotal.append(mallaConfig.getPrefijoTabla().toLowerCase());
									selectTotal.append(".");
									selectTotal.append(columna.getNombreColumna().toLowerCase());
									selectTotal.append(") ");
									selectTotal.append(columna.getNombreColumna().toLowerCase());
									selectTotal.append("_");
									selectTotal.append(itemCol);
									selectTotal.append(", ");
								} else if (valorMalla.isTotalizado()) {
									select.append("sum(");
									select.append(mallaConfig.getPrefijoTabla().toLowerCase());
									select.append(".");
									select.append(columna.getNombreColumna().toLowerCase());
									select.append(") ");
									select.append(columna.getNombreColumna().toLowerCase());
									select.append("_");
									select.append(itemCol);
									select.append(", ");

									selectTotal.append("sum(");
									selectTotal.append(mallaConfig.getPrefijoTabla().toLowerCase());
									selectTotal.append(".");
									selectTotal.append(columna.getNombreColumna().toLowerCase());
									selectTotal.append(") ");
									selectTotal.append(columna.getNombreColumna().toLowerCase());
									selectTotal.append("_");
									selectTotal.append(itemCol);
									selectTotal.append(", ");
								}
							} else {
								select.append(mallaConfig.getPrefijoTabla().toLowerCase());
								select.append(".");
								select.append(columna.getNombreColumna().toLowerCase());
								select.append(" ");
								select.append(columna.getNombreColumna().toLowerCase());
								select.append("_");
								select.append(itemCol);
								select.append(", ");

								selectTotal.append("null");
								selectTotal.append(" ");
								selectTotal.append(columna.getNombreColumna().toLowerCase());
								selectTotal.append("_");
								selectTotal.append(itemCol);
								selectTotal.append(", ");
							}
						}
						if (valorMalla.isEsParametro()) {
							Parametro parametro = new Parametro();
							parametro.setId(valorMalla.getId());
							parametro.setEtiquetaColumna(valorMalla.getNombre());
							parametro.setRequerido(false);
							parametro.setTipoDato(columna.getTipoDato());
							parametros.add(parametro);

							String comparador = "";

							StringBuilder valorParametro = new StringBuilder("");
							if (valorMalla.getCalificadorParametro() != null
									&& valorMalla.getValorComparacionParametro() != null) {
								valorParametro.append(valorMalla.getValorComparacionParametro());

								if (CalificadorComparacion.TTL_REPORTES_IGNORAR
										.equals(valorMalla.getCalificadorParametro())
										|| CalificadorComparacion.TTL_REPORTES_IGUAL
												.equals(valorMalla.getCalificadorParametro())) {
									comparador = "=";
								} else if (CalificadorComparacion.TTL_REPORTES_DIFERENTE_A
										.equals(valorMalla.getCalificadorParametro())) {
									comparador = "!=";
								} else if (CalificadorComparacion.TTL_REPORTES_MAYOR_IGUAL_QUE
										.equals(valorMalla.getCalificadorParametro())) {
									comparador = ">=";
								} else if (CalificadorComparacion.TTL_REPORTES_MAYOR_QUE
										.equals(valorMalla.getCalificadorParametro())) {
									comparador = ">";
								} else if (CalificadorComparacion.TTL_REPORTES_MENOR_IGUAL_QUE
										.equals(valorMalla.getCalificadorParametro())) {
									comparador = "<=";
								} else if (CalificadorComparacion.TTL_REPORTES_MENOR_QUE
										.equals(valorMalla.getCalificadorParametro())) {
									comparador = "<";
								}

								valorParametro = new StringBuilder();
								valorParametro.append(":" + columna.getNombreColumna().toLowerCase());
							} else {
								valorParametro.append(":" + columna.getNombreColumna().toLowerCase());
							}
							parametro.setNombre(valorParametro.toString());
						}

						if (valorMalla.isAgrupamiento()) {
							tieneAgrupamiento = true;
						}

						if (valorMalla.isGraficar() && valorMalla.isAgrupamiento()) {
							groupByGrafico.append(mallaConfig.getPrefijoTabla().toLowerCase());
							groupByGrafico.append(".");
							groupByGrafico.append(columna.getNombreColumna().toLowerCase());
							groupByGrafico.append(", ");
						}

						groupBy.append(mallaConfig.getPrefijoTabla().toLowerCase());
						groupBy.append(".");
						groupBy.append(columna.getNombreColumna().toLowerCase());
						groupBy.append(", ");
						break;
					}
				}
			}
		}

		StringBuilder sentenciaCompleta = new StringBuilder();
		if (select.toString().lastIndexOf(COMA) > 1) {
			sentenciaCompleta.append(select.toString().substring(0, select.toString().lastIndexOf(COMA)));
			sentenciaCompleta.append(from.toString());
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
			return;
		}

		if (tieneAgrupamiento && groupByGrafico.toString().lastIndexOf(COMA) > 1) {
			sentenciaCompleta
					.append(groupByGrafico.toString().substring(0, groupByGrafico.toString().lastIndexOf(COMA)));
		} else if (tieneAgrupamiento && groupBy.toString().lastIndexOf(COMA) > 1) {
			sentenciaCompleta.append(groupBy.toString().substring(0, groupBy.toString().lastIndexOf(COMA)));
		}
		sentenciaCompleta.append(") reporte");

		// Sentencia totalizadora
		StringBuilder sentenciaTotalCompleta = new StringBuilder();
		if (tieneTotal) {
			if (selectTotal.toString().lastIndexOf(COMA) > 1) {
				sentenciaTotalCompleta
						.append(selectTotal.toString().substring(0, selectTotal.toString().lastIndexOf(COMA)));
				sentenciaTotalCompleta.append(from.toString());
			} else {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
				return;
			}

			if (tieneAgrupamiento && groupByGrafico.toString().lastIndexOf(COMA) > 1) {
				sentenciaTotalCompleta
						.append(groupByGrafico.toString().substring(0, groupByGrafico.toString().lastIndexOf(COMA)));
			} else if (tieneAgrupamiento && groupBy.toString().lastIndexOf(COMA) > 1) {
				sentenciaTotalCompleta.append(groupBy.toString().substring(0, groupBy.toString().lastIndexOf(COMA)));
			}
			sentenciaTotalCompleta.append(") reporte");
		}

		SQL sentencia = new SQL();
		sentencia.setSentencia(sentenciaCompleta.toString());
		sentencia.getParametro().addAll(parametros);

		procesoSeleccionado.unsetSQL();
		procesoSeleccionado.getSQL().add(sentencia);
		detalle.setSQL(sentencia);

		procesoSeleccionado.getRegistro().add(cabecera);
		procesoSeleccionado.getRegistro().add(detalle);

		if (tieneTotal) {
			SQL sentenciaTotal = new SQL();
			sentenciaTotal.setSentencia(sentenciaTotalCompleta.toString());
			sentenciaTotal.getParametro().addAll(parametros);

			procesoSeleccionado.getSQL().add(sentenciaTotal);
			total.setSQL(sentenciaTotal);
			procesoSeleccionado.getRegistro().add(total);
		}
	}

	/**
	 * Carga la configuracion básica de la tabla seleccionada
	 */
	public void cargarDatosTabla() {
		try {
			for (TablaGestionInformacion tabla : this.listaTablaGestionInformacion) {
				if (this.tablaGestionInformacion.getNombre().equals(tabla.getNombre())) {
					String[] prefijosList = tabla.getNombre().split("_");
					StringBuilder prefijo = new StringBuilder();
					StringBuilder nombre = new StringBuilder();
					for (String letra : prefijosList) {
						if (!"V".equals(letra) && !letra.isEmpty() && letra.length() >= 3) {
							if (!tabla.getNombre().endsWith(letra)) {
								prefijo.append(letra.substring(0, 1));
							} else {
								prefijo.append(letra.substring(0, 3));
							}
							if (letra.length() >= 3) {
								nombre.append(StringUtil.UppercaseFirstLetters(letra.toLowerCase()));
								nombre.append(" ");
							}
						}
					}

					this.reporteSeleccionado.setPrefijoTabla(prefijo.toString().toLowerCase());
					this.reporteSeleccionado.setNombreTabla(tabla.getNombre());
					this.reporteSeleccionado.setNombre(nombre.toString().trim());

					this.listaColumnaTablaGestionInformacion = ComunicacionServiciosGI
							.columnasTablasGestionInformacion(this.tablaGestionInformacion);

					for (ColumnaTablaGestionInformacion columnaTabla : this.listaColumnaTablaGestionInformacion) {
						if (columnaTabla.getTipoDato() != null) {
							Columna columna = new Columna();
							columna.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							Thread.sleep(1l);
							columna.setNombreColumna(columnaTabla.getNombre());

							prefijosList = columnaTabla.getNombre().split("_");
							nombre = new StringBuilder();
							for (String letra : prefijosList) {
								nombre.append(StringUtil.UppercaseFirstLetters(letra.toLowerCase()));
								nombre.append(" ");
							}
							columna.setObservaciones(nombre.toString().trim());
							columna.setDescripcion(nombre.toString().trim());
							columna.setEtiquetaColumna(nombre.toString().trim());
							columna.setRequerido(true);
							columna.setCalificadorValidacionContraFechaSistema(
									CalificadorComparacion.TTL_REPORTES_MENOR_IGUAL_QUE);

							if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value().equals(columnaTabla.getTipoDato())) {
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE);
								columna.setMascara(DateUtils.FECHA_FORMATO);
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_QUERY.value()
									.equals(columnaTabla.getTipoDato())) {
								String prefijoPK = "cod_";
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_QUERY);
								columna.setSentencia("select " + columna.getNombreColumna().toLowerCase()
										+ " id, columna_nombre_descripcion descripcion from "
										+ columna.getNombreColumna().toLowerCase().replace(prefijoPK, ""));
								columna.setMascara("^[a-zA-Z]*$");
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_BOOLEAN.value()
									.equals(columnaTabla.getTipoDato())) {
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_BOOLEAN);
								columna.setMascara("");
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.value()
									.equals(columnaTabla.getTipoDato())) {
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL);
								columna.setMascara(NumeroUtil.FORMATO_DECIMAL);
							} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value()
									.equals(columnaTabla.getTipoDato())) {
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER);
								columna.setMascara(NumeroUtil.FORMATO_ENTERO);
							} else {
								columna.setTipoDato(TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING);
								columna.setMascara(".*");
							}

							this.reporteSeleccionado.getColumna().add(columna);
						} else {
							this.reporteSeleccionado = new MallaConfiguracion();
							mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									"");
							return;
						}
					}
					break;
				}
			}
		} catch (

		Exception e) {
			logger.error("void cargarDatosTabla()", e);
		}
	}

	/**
	 * Valida la configuracion y concatena el where de la sentencia
	 * 
	 * @param where          {@link StringBuilder} que lleva el valor del where en
	 *                       proceso
	 * @param tipoDato       {@link TipoDato} tipo de dato de la columna
	 * @param prefijoTabla   {@link String} alias de la tabla para relacionar sus
	 *                       columnas
	 * @param nombreColumna  {@link String} nombre de la columa a concatenar
	 * @param valorParametro {@link String} valor que llevaria la columna en el
	 *                       where de la columna
	 * 
	 * @return {@link StringBuilder} valores concatenados al where
	 */
	public StringBuilder concatenarWhere(StringBuilder where, TipoDato tipoDato, String prefijoTabla,
			String nombreColumna, String valorParametro, CalificadorComparacion calificadorComparacion) {
		if (where == null || prefijoTabla == null || nombreColumna == null || valorParametro == null) {
			throw new IllegalArgumentException();
		}

		final String comparador = " COMPARADOR ";

		if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.equals(tipoDato)) {
			if (CalificadorComparacion.TTL_REPORTES_IGNORAR.equals(calificadorComparacion)) {
				where.append("and regexp_like (upper(nvl(");
				where.append(prefijoTabla.toLowerCase());
				where.append(".");
				where.append(nombreColumna.toLowerCase());
				where.append(", '*')), upper(");
				where.append("nvl(" + valorParametro + ", '*')");
				where.append(")) ");
			} else {
				where.append("and ");
				where.append(prefijoTabla.toLowerCase());
				where.append(".");
				where.append(nombreColumna.toLowerCase());
				where.append(comparador);
				where.append("nvl(" + valorParametro + ", " + prefijoTabla.toLowerCase() + "."
						+ nombreColumna.toLowerCase() + ")");
				where.append(" ");
			}
		} else {
			where.append("and ");
			where.append(prefijoTabla.toLowerCase());
			where.append(".");
			where.append(nombreColumna.toLowerCase());
			where.append(comparador);
			where.append("nvl(" + valorParametro + ", " + prefijoTabla.toLowerCase() + "." + nombreColumna.toLowerCase()
					+ ")");
			where.append(" ");
		}
		return where;
	}

	/**
	 * Metodo para confirmar la eliminacion de reportes del catalogo
	 */
	public void confirmarQuitar() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('quitarRep').show();");
	}

	/**
	 * Metodo para confirmar la eliminacion de reportes del catalogo
	 */
	public void confirmarBorrar() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('borrarReporte').show();");
	}

	/**
	 * Metodo para confirmar la eliminacion de valorMalla
	 */
	public void confirmarQuitarValorMalla() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('quitarValorMalla').show();");
	}

	/**
	 * Metodo para calcelar las confirmaciones de los modales de eliminacion
	 */
	public void cancelarQuitar() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('quitarRep').hide();");
		context.execute("PF('quitarValorMalla').hide();");
		context.execute("PF('borrarReporte').hide();");
	}

	/**
	 * Metodo para hacer back hacia la grilla de consulta de reportes
	 */
	@Override
	public void cancelar() {
		abrirAccion(0, "gestionarReporte.xhtml", RECURSO_ID + GESTIONAR_REPORTE_SUB_MENU);
	}

	public void cancelarAnterior() {
		abrirAccion(id, "gestionar.xhtml", RECURSO_ID + GESTIONAR_REPORTE_MODIFICAR);
	}

	public String getName(String data) {
		String d[] = data.split("/");
		if (d.length > 0) {
			for (String string : d) {
				data = string;
			}
		}
		return data;
	}

	/**
	 * Metodo utilizado para la lista de autocompletar en los filtros de busqueda
	 * 
	 * @param query Texto a buscar de forma inflexiva dentro de atributo nombre de
	 *              la plantilla xml
	 * @return {@link List} de {@link String} con las coincidencias
	 */
	public List<String> completeText(String query) {
		List<String> results = new LinkedList<>();

		if (query == null || query.isEmpty()) {
			return results;
		}

		int pageSize = ConfigurationBundleConstants.getpaginatorSize();

		PlantillaLazyDataModel dataModel = new PlantillaLazyDataModel(XmlReporte.getInstance());
		dataModel.load(0, ConfigurationBundleConstants.getpaginatorSize(), "", SortOrder.ASCENDING,
				new HashMap<String, Object>());

		xml.setNombre(query);

		dataModel = new PlantillaLazyDataModel(xml);
		dataModel.load(0, pageSize, "", SortOrder.ASCENDING, new HashMap<String, Object>());

		for (XmlReporte xmlSearch : dataModel.getListaPlantillas()) {
			if (!results.contains(xmlSearch.getNombre())) {
				results.add(xmlSearch.getNombre());
			}
		}

		Collections.sort(results, new Comparator<String>() {
			@Override
			public int compare(String c1, String c2) {
				return c1.compareTo(c2);
			}
		});

		return results;
	}

	/**
	 * Determina la posicion disponible para un valor malla del reporte
	 */
	public void calcularOrdenValores() {
		BigInteger index = BigInteger.ZERO;
		for (ValorMalla malla : lstOrden) {
			index = index.add(BigInteger.ONE);
			malla.setOrden(index);
		}

		Collections.sort(this.lstOrden, new Comparator<ValorMalla>() {
			@Override
			public int compare(ValorMalla c1, ValorMalla c2) {
				return c1.getOrden().compareTo(c2.getOrden());
			}
		});

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ordenarValoresMallaDialog').hide();");
	}

	public void seleccionarColumna() {
		columnaSeleccionada = new Columna();

		for (Columna seleccionar : reporteSeleccionado.getColumna()) {
			if (seleccionar.getId().equals(idColumna)) {
				columnaSeleccionada = seleccionar;
				break;
			}
		}
	}
	
	public void abrirAccionValidar(long idRegistro, String paginaP, String paramentrosAdicionales) {
		String accion="";
		String[] parametros = paramentrosAdicionales.split("=");
		if(parametros!=null && !"".equals(parametros) && parametros.length>1){
			accion = parametros[1];
		}
		
		
		if ( accion!=null && !"".equals(accion) && (GESTIONAR_REPORTE_MODIFICAR_2.equals(accion)  ||GESTIONAR_REPORTE_ELIMINAR.equals(accion)) ){
			if(reporteExisteCatalogo(idRegistro)){
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_GI_REPORTE_EXISTE_CATALOGO);
			}else{
				abrirAccion( idRegistro,  paginaP,  paramentrosAdicionales) ;
			}
		}else{
			abrirAccion( idRegistro,  paginaP,  paramentrosAdicionales) ;
		}
	}
	
	private boolean reporteExisteCatalogo(Long idReporte){
		boolean plantillaExisteCatalogo = false;
		String rutaCarpetaPublicacion = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO);
		rutaCarpetaPublicacion = rutaCarpetaPublicacion.replace("//", "/");
		File carpetaPublicacionCorreo = new File(rutaCarpetaPublicacion);
		if (carpetaPublicacionCorreo.exists()) {
			try {
				String nombrePlantillaBuscar="";
				List<XmlReporte> plantillasReportes = xmlProduces.getPlantillasReportes();
				XmlReporte xmlModificar = XmlReporte.getInstance();
				xmlModificar.setId(BigInteger.valueOf(idReporte));
				xmlModificar = plantillasReportes.get(plantillasReportes.indexOf(xmlModificar));
				nombrePlantillaBuscar = xmlModificar.getPlantillaXML().substring(
						xmlModificar.getPlantillaXML().lastIndexOf(FileUtil.BACKSLASH) + 1,
						xmlModificar.getPlantillaXML().length());
				nombrePlantillaBuscar = nombrePlantillaBuscar.replaceAll("//", "/");
				nombrePlantillaBuscar = getName(nombrePlantillaBuscar);		
				for (File reporte : carpetaPublicacionCorreo.listFiles()) {
					if (reporte.getPath().endsWith(FileUtil.BACKSLASH + nombrePlantillaBuscar) 
							|| reporte.getPath().endsWith(FileUtil.SLASH + nombrePlantillaBuscar) ) {
						plantillaExisteCatalogo = true;
						break;
					}
				}				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		}
		return plantillaExisteCatalogo;
	}
	

}
