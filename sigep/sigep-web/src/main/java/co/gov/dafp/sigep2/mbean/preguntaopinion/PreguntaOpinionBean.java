package co.gov.dafp.sigep2.mbean.preguntaopinion;

import java.io.IOException;
import java.util.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;


import org.primefaces.component.export.ExcelOptions;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.chart.PieChartModel;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.ResultadoPreguntaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;


/**
* @author Maria Alejandra Colorado Rios
* @version 1.0
* @Class PreguntaOpinionBean.java
* @Proyect DAFP
* @Company ADA S.A. 
* @Module Administracion del sistema
* Fecha: 24/03/2018
*/

@Named
@ViewScoped
@ManagedBean
public class PreguntaOpinionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 2460218007393706546L;
	
	//Variables para almacenar los resultados a mostrar en las graficas
	private PieChartModel graficoPuntaje;
	private PieChartModel graficoRol;
	private PieChartModel graficoNivelJerarquico;
	private PieChartModel graficoRetenSocial;
	private PieChartModel graficoRangoEdad;
	
	//Variables que almacenan las preguntas de opinion existentes y sus resultados.
	private PreguntaOpinion preguntaOpinion;
	private PreguntaOpinion preguntaFiltro;
	private ResultadoPregunta resultadoPregunta;
	
	//Variables que almacenan la información de las consultas hechas para cada una de las graficas.
	private List<PreguntaOpinion> listaPreguntaOpinion;
	private List<ResultadoPregunta> listaResultadoPorcentaje;
	private List<ResultadoPreguntaExt> listaResultadoRol;
	private List<ResultadoPreguntaExt> listaResultadoEdad;
	private List<ResultadoPreguntaExt> listaResultadoNivel;
	private List<ResultadoPreguntaExt>  listaResultadoRetenSocial;
	private List<ResultadoPreguntaExt> listaDetalleResultadoPregunta;
	private List<ResultadoPreguntaExt> listaInformacionResultadoPregunta;
	private Map<Integer, Integer> mapaSeleccionGrafica ;
	
	//Variables del formulario
	private boolean habilitarPanelVerResultado = false;
	private boolean habilitarPanelCrearPregunta = false;
	private boolean habilitarPanelConsultaRender = false;
	private boolean habilitarMensajeCrear = false;
	private boolean habilitarMensajeModificar = false;
	private boolean habilitarPanelPuntajeSeleccionado = false;
	private Boolean flgValidaRolPermiso = false;
	private boolean accionModificar= false;
	
	private String preguntaRandom = "";
	private String strMsgInicial;
	private long persona;
	private int puntajeSeleccionado= 0;
	private long codPregunta = 0;
	private ExcelOptions excelOpt;
	
	private LazyDataModel<PreguntaOpinion> listPreguntaOpinion;
	
	@PostConstruct
	public void init() {
		preguntaOpinion = new PreguntaOpinion();
		preguntaFiltro = new PreguntaOpinion();
		resultadoPregunta = new ResultadoPregunta();
		mapaSeleccionGrafica = new HashMap<Integer, Integer>();
		
		habilitarPanelConsultaRender = true;
		habilitarMensajeCrear = false;
		habilitarMensajeModificar = false;
		FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);
		initialization();
		mostrarTablaPregunta();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ADMINISTRACION);		
	
	}

	public void initialization() {
		if (getUsuarioSesion() != null) {
			try {
				flgValidaRolPermiso = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SUPER_ADMINISTRADOR, RolDTO.ADMINISTRADOR_FUNCIONAL);
				if (flgValidaRolPermiso == false) {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				}
			} catch (SIGEP2SistemaException e) {
				logger.error("void init() usuarioTieneRolAsignado", e);
			} catch (IOException e) {
				logger.error("void init() finalizarConversacion", e);
			}
		}
	}
	
	
	/**
	 * Metodo que almacena una pregunta de opinion.
	 * Si el objeto enviado no tiene cod_pregunta_opinion, crea un nuevo registro.
	 * Si el objeto enviado tiene cod_pregunta_opinion, actualiza el registro
	 */
	public void persistNuevaOpinion() {
		preguntaOpinion.setAudFechaActualizacion(DateUtils.getFechaSistema());
		preguntaOpinion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
		preguntaOpinion.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
		if(accionModificar) {
			preguntaOpinion.setAudAccion(TipoAccionEnum.UPDATE.getIdAccion());
		}else {
			preguntaOpinion.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		}
		if(!accionModificar) {
			if(validarPreguntaRepetida()) {
				return;
			}		
		}
		boolean valid = ComunicacionServiciosSis.setpreguntaopinion(preguntaOpinion);
		if (valid) {
			if(accionModificar) {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PREGUNTA_OPINION_MODIFICADO_EXITOSO);
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PREGUNTA_OPINION_GUARDADO_EXITOSO);
			}
			preguntaOpinion = new PreguntaOpinion();
			mostrarTablaPregunta();
			habilitarPanelCrearPregunta = false;
			habilitarMensajeCrear = false;
			habilitarMensajeModificar = false;
			habilitarPanelConsultaRender = true;
			accionModificar=false;
			preguntaOpinion = new PreguntaOpinion();
		} else {
			mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_CREAR_REGISTRO);
			preguntaOpinion = new PreguntaOpinion();
		}
	}
	
	/**
	 * Metodo que valida si una pregunta ya esta creada y posee el mismo nombre.
	 */
	public boolean validarPreguntaRepetida() {
		boolean preguntaExistente = false;
		PreguntaOpinion objePregunta  = new PreguntaOpinion();
		objePregunta.setPregunta(preguntaOpinion.getPregunta());
		List<PreguntaOpinion> preguntaIgual = ComunicacionServiciosAdmin.preguntaOpinionByNombre(objePregunta);
		if(!preguntaIgual.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_PREGUNTA_OPINION_YA_EXISTENTE);
			preguntaExistente = true;
		}
		return preguntaExistente;
	}
	
	
	/**
	 * Metodo que consulta el servicio getPreguntaOpinion y retorna la lista de preguntas
	 * para la visualización de la tabla
	 */
	public void mostrarTablaPregunta() {
		llenarLimitesConsulta();
		listaPreguntaOpinion = ComunicacionServiciosSis.getPreguntaOpinion(preguntaOpinion);
	}
	
	/**
	 * metodo que actualiza la tabla de preguntas de opinion de acuerdo a los parametros enviados
	 */
	public void consultarPregunta() {
		if(preguntaFiltro.getPregunta().equals("") && preguntaFiltro.getFechaInicio() == null && preguntaFiltro.getFechaFin() == null)
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_VALIDAR_DATOS_CONSULTAR);
		llenarLimitesConsulta();
		listaPreguntaOpinion = ComunicacionServiciosSis.getPreguntaOpinion(preguntaFiltro);
	}

	/**
	 * Metodo utilizado para la modificacion de una pregunta de opinion
	 * @param id, es el cod_entidad_pregunta a modificar
	 */
	public void modificarPreguntaOpinion() {
		this.setStrMsgInicial(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_OPINION_MODIFICAR, getLocale()));
		habilitarMensajeModificar= true;
		accionModificar = true;
		habilitarPanelCrearPregunta = true;
		habilitarPanelConsultaRender = false;
		habilitarMensajeCrear = false;
	}

	/**
	 * Metodo utilizado para la eliminación de la pregunta de opinion,
	 * Si la pregunta de opinion no tiene respuestas asociadas, realiza un eliminado físico en BD
	 * Si la pregunta de opinion tiene respuestas asociadas, realiza un eliminado logico de la pregunta en BD
	 * @param id, es el cod_pregunta_opinion a eliminar
	 */
	
	public void eliminarPreguntaOpinion(long id) {
		try {
			habilitarPanelCrearPregunta = false;
			habilitarMensajeCrear = false;
			habilitarPanelConsultaRender = true;
			habilitarMensajeModificar= false;
			
			if (preguntaOpinion != null) {
				if (preguntaOpinion.getTotalRespuesta()) {
					preguntaOpinion.setFlgActivo(false);
					preguntaOpinion.setAudAccion(TipoAccionEnum.DELETE.getIdAccion());
					preguntaOpinion.setAudCodRol(BigDecimal.valueOf(this.getRolAuditoria().getId()));
					preguntaOpinion.setAudCodUsuario(BigDecimal.valueOf(this.getUsuarioSesion().getId()));
					boolean valid = ComunicacionServiciosSis.setpreguntaopinion(preguntaOpinion);
					if (valid) {
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INHABILITAR_PREGUNTA);
						preguntaOpinion = new PreguntaOpinion();
						mostrarTablaPregunta();

					}
				} else {
					String eliminado = ComunicacionServiciosSis.delpreguntaopinion(id);
					if (eliminado.equals("\"true\"")) {
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ELIMINAR_PREGUNTA);
						preguntaOpinion = new PreguntaOpinion();
						mostrarTablaPregunta();
					} else {
						mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.DLG_ELIMINACION_FALLIDA);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Metodo utilizado en la vista para habilitar el panel de crear una nueva pregunta de opinion, 
	 * mostrar el mensaje de creacion y actualizar el panel de consulta donde se muestra la pregunta creada
	 * @param validCrear
	 * @param mensajeCrear
	 * @param validConsulta
	 */
	public void habilitarPanelCrearPregunta(boolean validCrear, boolean mensajeCrear, boolean validConsulta) {
		preguntaOpinion = new PreguntaOpinion();
		habilitarPanelCrearPregunta = validCrear;
		habilitarPanelConsultaRender = validConsulta;
		habilitarMensajeCrear = mensajeCrear;
		habilitarMensajeModificar= false;
		this.setStrMsgInicial(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_PREGUNTA_OPINION_CREAR, getLocale()));
	}

	/**
	 * Metodo que permite mostrar las graficas de la pregunta de opinion seleccionada,
	 * y ademas, muestra el detalle del resultado de la pregunta seleccionada
	 * @param codPregunta
	 */
	public void habilitarResultadosOpinion(long codPregunta) {
		habilitarPanelVerResultado = true;
		habilitarPanelPuntajeSeleccionado = false;
		crearGraficoPuntaje(codPregunta);
		mostrarDetalleResultadoPregunta(codPregunta);
	}

	/**
	 * Metodo que permite mostrar tabla con el detalle de los resultados
	 * de la pregunta de opinion. Carga una lista con las respuestas para esa pregunta
	 * @param codPregunta
	 */
	
	public void mostrarDetalleResultadoPregunta(long codPregunta) {
		habilitarPanelCrearPregunta = false;
		habilitarMensajeCrear = false;
		habilitarPanelConsultaRender = true;
		habilitarMensajeModificar= false;
		preguntaOpinion = new PreguntaOpinion();
		listaDetalleResultadoPregunta = ComunicacionServiciosSis.getRespuestaPreguntaPersona(codPregunta);
		ResultadoPregunta obj = new ResultadoPregunta();
		obj.setCodPreguntaOpinion((int)codPregunta);
		listaInformacionResultadoPregunta = ComunicacionServiciosSis.getInformacionRespuestas(obj);
	}
	
	
	/**
	 * Metodo que permite redirreccionar la aplicacion a la visualizacion de la HV de la persona
	 */
	public void redireccionarHojaVida() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../persona/hojaDeVidaView.xhtml?id=" + persona);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que realiza la consulta en BD de las respuestas hechas para una pregunta especifica.
	 * Los resultados ponderados se realizan en el servicio, este metodo entonces, crea el grafico de
	 * puntaje con el resultado retornado por el servicio.
	 * @param codPregunta
	 */
	public void crearGraficoPuntaje(long codPregunta) {
		this.codPregunta = codPregunta;
		graficoPuntaje = new PieChartModel();
		listaResultadoPorcentaje =  new ArrayList<ResultadoPregunta>();
		mapaSeleccionGrafica = new HashMap<Integer, Integer>();
		listaResultadoPorcentaje = ComunicacionServiciosSis.getRespuestasGrafico(codPregunta);
		if(listaResultadoPorcentaje.size() >0) {
			int index = 0;
			for(ResultadoPregunta insResultado : listaResultadoPorcentaje ) {
				graficoPuntaje.set("Puntaje "+ insResultado.getPuntaje(), insResultado.getPorcentaje());
				mapaSeleccionGrafica.put(insResultado.getPuntaje(), index);
				index++;
			}
			graficoPuntaje.setShowDataLabels(true);
			graficoPuntaje.setShadow(true);
			graficoPuntaje.setTitle("Puntaje");
			graficoPuntaje.setLegendPosition("e");
			//mapaSeleccionGrafica.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
			
		}else {
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_RESPUESTA_PREGUNTA);
			habilitarPanelVerResultado = false;
			habilitarPanelPuntajeSeleccionado = false;
		}
	}
	
	/**
	 * Metodo que obtiene el indice seleccionado para el grafico de puntaje de las preguntas,
	 * cuando el ussuario da clic sobre la grafica.
	 * @param event
	 */
	
	public void obtenerIndexSeleccionadoGraficaPuntaje(ItemSelectEvent event) {
		int index =  event.getItemIndex();
		obtenerPuntajeSeleccionado(index);
	}
	
	/**
	 * Todas las graficas de informacion de perfil que se piden,
	 * dependen del puntaje seleccionado, de acuerdo a eso,
	 * este metodo obtiene el puntaje que selecciona la persona
	 * y llama el metodo crearGraficasInformacionPerfil() quien se encarga de
	 * crear las graficas relacionadas
	 * @param index
	 */
	public void obtenerPuntajeSeleccionado(int index) {
		habilitarPanelPuntajeSeleccionado = true;
		Iterator<Entry<Integer, Integer>> i=mapaSeleccionGrafica.entrySet().iterator();
		Entry<Integer, Integer> entry= null;
		while (i.hasNext()) {
			entry = i.next();
			if (entry.getValue() == index) {
				// entry.getKey(); clave // c = entry.getValue(); //valor
				puntajeSeleccionado = entry.getKey();
				break;
			}
		}
		crearGraficasInformacionPerfil();
	}
	
	/**
	 * Metodo que permite crear el grafico de roles.
	 * Realiza el llamado al servicio getRespuestaPreguntaEstadistica,
	 * que es el que se encarga de obtener el porcentaje de roles que respondieron a dicha pregunta,
	 * para que el metodo lo pueda leer y visualizar. 
	 */
	public void crearGraficoRoles() {
		graficoRol = new PieChartModel();
		if (puntajeSeleccionado != 0) {
			resultadoPregunta = new ResultadoPregunta();
			resultadoPregunta.setCodPreguntaOpinion((int) (long) codPregunta);
			resultadoPregunta.setPuntaje(puntajeSeleccionado);
			listaResultadoRol = ComunicacionServiciosSis.getRespuestaPreguntaEstadistica(resultadoPregunta);
			if (listaResultadoRol.size() > 0) {
				for (ResultadoPreguntaExt insResultadoRol : listaResultadoRol) {
					graficoRol.set(insResultadoRol.getNombreRol(), insResultadoRol.getPorcentaje());
				}
			}else {
				graficoRol.set("Sin Roles", 100);
			} 
		}
		graficoRol.setShowDataLabels(true);
		graficoRol.setShadow(true);
		graficoRol.setTitle("Rol");
		graficoRol.setLegendPosition("e");
	}

	
	/**
	 * Metodo que permite crear crear el grafico del rango de edades de las personas
	 * que respondieron a la pregunta de opinion seleccionado, con el puntaje selecciado.
	 * Este metodo hace un llamado al servicio getRespuestasGraficoEdades, quien se encarga
	 * de realizar el ponderado de las personas que respondieron a estas preguntas.
	 */
	public void crearGraficoRangoEdad() {
		graficoRangoEdad= new PieChartModel();
		if (puntajeSeleccionado != 0) {
			listaResultadoEdad = ComunicacionServiciosSis.getRespuestasGraficoEdades(codPregunta, puntajeSeleccionado);
			if(listaResultadoEdad.size() > 0) {
				for (ResultadoPreguntaExt insResultadoEdad : listaResultadoEdad) {
					graficoRangoEdad.set(insResultadoEdad.getNombreRango(), insResultadoEdad.getPorcentaje());
				}
			}else {
				graficoRangoEdad.set("Sin Edad", 100);
			}		
		}
		graficoRangoEdad.setShowDataLabels(true);
		graficoRangoEdad.setShadow(true);
		graficoRangoEdad.setTitle("Rango Edad");
		graficoRangoEdad.setLegendPosition("e");
	}
	
	/**
	 * Metodo que permite crear el grafico de Nivel (Dierectivo, Asesor, Profesional, Tecnico, Asistencial)
	 * segun las personas que respondieron a la pregunta seleccionada con el puntaje seleccionado
	 */
	
	public void crearGraficoNivel() {
		graficoNivelJerarquico = new PieChartModel();
		if(puntajeSeleccionado != 0) {
			resultadoPregunta.setCodPreguntaOpinion((int) (long) codPregunta);
			resultadoPregunta.setPuntaje(puntajeSeleccionado);
			listaResultadoNivel = ComunicacionServiciosSis.getGraficoNivelJerarquico(resultadoPregunta);
			if (listaResultadoNivel.size() > 0) {
				for (ResultadoPreguntaExt insResultadoNivel : listaResultadoNivel) {
					graficoNivelJerarquico.set(insResultadoNivel.getNombreNivel(), insResultadoNivel.getPorcentaje());
					
				}
			}else {
				graficoNivelJerarquico.set("Sin Nivel", 100);
			}		
		}
		graficoNivelJerarquico.setShowDataLabels(true);
		graficoNivelJerarquico.setShadow(true);
		graficoNivelJerarquico.setTitle("Nivel Jerárquico");
		graficoNivelJerarquico.setLegendPosition("e");
	}
	
	/**
	 * Metodo que permite crear el metodo de reten social (Condicion de discapacidad, cabeza de familia) 
	 */
	public void crearGraficoRetenSocial() {
		graficoRetenSocial = new PieChartModel();
		if(puntajeSeleccionado != 0) {
			resultadoPregunta.setCodPreguntaOpinion((int) (long) codPregunta);
			resultadoPregunta.setPuntaje(puntajeSeleccionado);
			listaResultadoRetenSocial = ComunicacionServiciosSis.getGraficoRetenSocial(resultadoPregunta);
			if (listaResultadoRetenSocial.size() > 0) {
				for (ResultadoPreguntaExt insResultadoRetenSocial : listaResultadoRetenSocial) {
					graficoRetenSocial.set("Cabeza Familia: " + insResultadoRetenSocial.getCabezaFamilia() + 
							"\n"+" - Discapacidad: " + insResultadoRetenSocial.getDiscapacidad(), insResultadoRetenSocial.getPorcentaje());
				}
			}else {
				graficoRetenSocial.set("Sin Razon Social", 100);
			}	
			
			
		}
		graficoRetenSocial.setShowDataLabels(true);
		graficoRetenSocial.setShadow(true);
		graficoRetenSocial.setTitle("Reten Social");
		graficoRetenSocial.setLegendPosition("e");
	}
	
	/**
	 * Metodo que se utiliza cuando la persona selecciona uno de los porcentajes de la grafica de 
	 * puntaje de preguntas de opinion
	 */
	public void crearGraficasInformacionPerfil() {
		crearGraficoRoles();
		crearGraficoNivel();
		crearGraficoRetenSocial();
		crearGraficoRangoEdad();
	}
	
	
	/**
	 * Metodo que llena los limites de la consulta para la tablas de pregunta de Opinion
	 * y Resultados de pregunta
	 */
	public void llenarLimitesConsulta() {
		preguntaOpinion.setLimitInit(0);
		preguntaOpinion.setLimitEnd(10000);
	}
	
	/**
	 * Aplica estilos al exportar de excel
	 * @param document
	 */
	public void postProcessXLS(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 11, "Resultado Preguntas");
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public String generarFecha() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        return format.format(new Date());
    }
	
	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
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
	
	
	/**
	 * @return the graficoPuntaje
	 */
	public PieChartModel getGraficoPuntaje() {
		return graficoPuntaje;
	}

	/**
	 * @param graficoPuntaje the graficoPuntaje to set
	 */
	public void setGraficoPuntaje(PieChartModel graficoPuntaje) {
		this.graficoPuntaje = graficoPuntaje;
	}

	/**
	 * @return the graficoRol
	 */
	public PieChartModel getGraficoRol() {
		return graficoRol;
	}

	/**
	 * @param graficoRol the graficoRol to set
	 */
	public void setGraficoRol(PieChartModel graficoRol) {
		this.graficoRol = graficoRol;
	}

	/**
	 * @return the graficoNivelJerarquico
	 */
	public PieChartModel getGraficoNivelJerarquico() {
		return graficoNivelJerarquico;
	}

	/**
	 * @param graficoNivelJerarquico the graficoNivelJerarquico to set
	 */
	public void setGraficoNivelJerarquico(PieChartModel graficoNivelJerarquico) {
		this.graficoNivelJerarquico = graficoNivelJerarquico;
	}

	/**
	 * @return the graficoRetenSocial
	 */
	public PieChartModel getGraficoRetenSocial() {
		return graficoRetenSocial;
	}

	/**
	 * @param graficoRetenSocial the graficoRetenSocial to set
	 */
	public void setGraficoRetenSocial(PieChartModel graficoRetenSocial) {
		this.graficoRetenSocial = graficoRetenSocial;
	}

	/**
	 * @return the graficoRangoEdad
	 */
	public PieChartModel getGraficoRangoEdad() {
		return graficoRangoEdad;
	}

	/**
	 * @param graficoRangoEdad the graficoRangoEdad to set
	 */
	public void setGraficoRangoEdad(PieChartModel graficoRangoEdad) {
		this.graficoRangoEdad = graficoRangoEdad;
	}

	/**
	 * @return the preguntaOpinion
	 */
	public PreguntaOpinion getPreguntaOpinion() {
		return preguntaOpinion;
	}

	/**
	 * @param preguntaOpinion the preguntaOpinion to set
	 */
	public void setPreguntaOpinion(PreguntaOpinion preguntaOpinion) {
		this.preguntaOpinion = preguntaOpinion;
	}

	/**
	 * @return the resultadoPregunta
	 */
	public ResultadoPregunta getResultadoPregunta() {
		return resultadoPregunta;
	}

	/**
	 * @param resultadoPregunta the resultadoPregunta to set
	 */
	public void setResultadoPregunta(ResultadoPregunta resultadoPregunta) {
		this.resultadoPregunta = resultadoPregunta;
	}

	/**
	 * @return the listaPreguntaOpinion
	 */
	public List<PreguntaOpinion> getListaPreguntaOpinion() {
		return listaPreguntaOpinion;
	}

	/**
	 * @param listaPreguntaOpinion the listaPreguntaOpinion to set
	 */
	public void setListaPreguntaOpinion(List<PreguntaOpinion> listaPreguntaOpinion) {
		this.listaPreguntaOpinion = listaPreguntaOpinion;
	}

	/**
	 * @return the listaResultadoPorcentaje
	 */
	public List<ResultadoPregunta> getListaResultadoPorcentaje() {
		return listaResultadoPorcentaje;
	}

	/**
	 * @param listaResultadoPorcentaje the listaResultadoPorcentaje to set
	 */
	public void setListaResultadoPorcentaje(List<ResultadoPregunta> listaResultadoPorcentaje) {
		this.listaResultadoPorcentaje = listaResultadoPorcentaje;
	}

	/**
	 * @return the listaResultadoRol
	 */
	public List<ResultadoPreguntaExt> getListaResultadoRol() {
		return listaResultadoRol;
	}

	/**
	 * @param listaResultadoRol the listaResultadoRol to set
	 */
	public void setListaResultadoRol(List<ResultadoPreguntaExt> listaResultadoRol) {
		this.listaResultadoRol = listaResultadoRol;
	}

	/**
	 * @return the listaResultadoEdad
	 */
	public List<ResultadoPreguntaExt> getListaResultadoEdad() {
		return listaResultadoEdad;
	}

	/**
	 * @param listaResultadoEdad the listaResultadoEdad to set
	 */
	public void setListaResultadoEdad(List<ResultadoPreguntaExt> listaResultadoEdad) {
		this.listaResultadoEdad = listaResultadoEdad;
	}

	/**
	 * @return the listaDetalleResultadoPregunta
	 */
	public List<ResultadoPreguntaExt> getListaDetalleResultadoPregunta() {
		return listaDetalleResultadoPregunta;
	}

	/**
	 * @param listaDetalleResultadoPregunta the listaDetalleResultadoPregunta to set
	 */
	public void setListaDetalleResultadoPregunta(List<ResultadoPreguntaExt> listaDetalleResultadoPregunta) {
		this.listaDetalleResultadoPregunta = listaDetalleResultadoPregunta;
	}

	/**
	 * @return the mapaSeleccionGrafica
	 */
	public Map<Integer, Integer> getMapaSeleccionGrafica() {
		return mapaSeleccionGrafica;
	}

	/**
	 * @param mapaSeleccionGrafica the mapaSeleccionGrafica to set
	 */
	public void setMapaSeleccionGrafica(Map<Integer, Integer> mapaSeleccionGrafica) {
		this.mapaSeleccionGrafica = mapaSeleccionGrafica;
	}

	/**
	 * @return the habilitarPanelVerResultado
	 */
	public boolean isHabilitarPanelVerResultado() {
		return habilitarPanelVerResultado;
	}

	/**
	 * @param habilitarPanelVerResultado the habilitarPanelVerResultado to set
	 */
	public void setHabilitarPanelVerResultado(boolean habilitarPanelVerResultado) {
		this.habilitarPanelVerResultado = habilitarPanelVerResultado;
	}

	/**
	 * @return the habilitarPanelCrearPregunta
	 */
	public boolean isHabilitarPanelCrearPregunta() {
		return habilitarPanelCrearPregunta;
	}

	/**
	 * @param habilitarPanelCrearPregunta the habilitarPanelCrearPregunta to set
	 */
	public void setHabilitarPanelCrearPregunta(boolean habilitarPanelCrearPregunta) {
		this.habilitarPanelCrearPregunta = habilitarPanelCrearPregunta;
	}

	/**
	 * @return the habilitarPanelConsultaRender
	 */
	public boolean isHabilitarPanelConsultaRender() {
		return habilitarPanelConsultaRender;
	}

	/**
	 * @param habilitarPanelConsultaRender the habilitarPanelConsultaRender to set
	 */
	public void setHabilitarPanelConsultaRender(boolean habilitarPanelConsultaRender) {
		this.habilitarPanelConsultaRender = habilitarPanelConsultaRender;
	}

	/**
	 * @return the habilitarMensajeCrear
	 */
	public boolean isHabilitarMensajeCrear() {
		return habilitarMensajeCrear;
	}

	/**
	 * @param habilitarMensajeCrear the habilitarMensajeCrear to set
	 */
	public void setHabilitarMensajeCrear(boolean habilitarMensajeCrear) {
		this.habilitarMensajeCrear = habilitarMensajeCrear;
	}

	/**
	 * @return the habilitarMensajeModificar
	 */
	public boolean isHabilitarMensajeModificar() {
		return habilitarMensajeModificar;
	}

	/**
	 * @param habilitarMensajeModificar the habilitarMensajeModificar to set
	 */
	public void setHabilitarMensajeModificar(boolean habilitarMensajeModificar) {
		this.habilitarMensajeModificar = habilitarMensajeModificar;
	}

	/**
	 * @return the habilitarPanelPuntajeSeleccionado
	 */
	public boolean isHabilitarPanelPuntajeSeleccionado() {
		return habilitarPanelPuntajeSeleccionado;
	}

	/**
	 * @param habilitarPanelPuntajeSeleccionado the habilitarPanelPuntajeSeleccionado to set
	 */
	public void setHabilitarPanelPuntajeSeleccionado(boolean habilitarPanelPuntajeSeleccionado) {
		this.habilitarPanelPuntajeSeleccionado = habilitarPanelPuntajeSeleccionado;
	}

	/**
	 * @return the flgValidaRolPermiso
	 */
	public Boolean getFlgValidaRolPermiso() {
		return flgValidaRolPermiso;
	}

	/**
	 * @param flgValidaRolPermiso the flgValidaRolPermiso to set
	 */
	public void setFlgValidaRolPermiso(Boolean flgValidaRolPermiso) {
		this.flgValidaRolPermiso = flgValidaRolPermiso;
	}

	/**
	 * @return the accionModificar
	 */
	public boolean isAccionModificar() {
		return accionModificar;
	}

	/**
	 * @param accionModificar the accionModificar to set
	 */
	public void setAccionModificar(boolean accionModificar) {
		this.accionModificar = accionModificar;
	}

	/**
	 * @return the preguntaRandom
	 */
	public String getPreguntaRandom() {
		return preguntaRandom;
	}

	/**
	 * @param preguntaRandom the preguntaRandom to set
	 */
	public void setPreguntaRandom(String preguntaRandom) {
		this.preguntaRandom = preguntaRandom;
	}

	/**
	 * @return the persona
	 */
	public long getPersona() {
		return persona;
	}

	/**
	 * @param persona the persona to set
	 */
	public void setPersona(long persona) {
		this.persona = persona;
	}

	/**
	 * @return the puntajeSeleccionado
	 */
	public int getPuntajeSeleccionado() {
		return puntajeSeleccionado;
	}

	/**
	 * @param puntajeSeleccionado the puntajeSeleccionado to set
	 */
	public void setPuntajeSeleccionado(int puntajeSeleccionado) {
		this.puntajeSeleccionado = puntajeSeleccionado;
	}

	/**
	 * @return the codPregunta
	 */
	public long getCodPregunta() {
		return codPregunta;
	}

	/**
	 * @param codPregunta the codPregunta to set
	 */
	public void setCodPregunta(long codPregunta) {
		this.codPregunta = codPregunta;
	}

	/**
	 * @return the listPreguntaOpinion
	 */
	public LazyDataModel<PreguntaOpinion> getListPreguntaOpinion() {
		return listPreguntaOpinion;
	}

	/**
	 * @param listPreguntaOpinion the listPreguntaOpinion to set
	 */
	public void setListPreguntaOpinion(LazyDataModel<PreguntaOpinion> listPreguntaOpinion) {
		this.listPreguntaOpinion = listPreguntaOpinion;
	}

	/**
	 * @return the strMsgInicial
	 */
	public String getStrMsgInicial() {
		return strMsgInicial;
	}

	/**
	 * @param strMsgInicial the strMsgInicial to set
	 */
	public void setStrMsgInicial(String strMsgInicial) {
		this.strMsgInicial = strMsgInicial;
	}

	/**
	 * @return the preguntaFiltro
	 */
	public PreguntaOpinion getPreguntaFiltro() {
		return preguntaFiltro;
	}

	/**
	 * @param preguntaFiltro the preguntaFiltro to set
	 */
	public void setPreguntaFiltro(PreguntaOpinion preguntaFiltro) {
		this.preguntaFiltro = preguntaFiltro;
	}

	/**
	 * @return the listaResultadoNivel
	 */
	public List<ResultadoPreguntaExt> getListaResultadoNivel() {
		return listaResultadoNivel;
	}

	/**
	 * @param listaResultadoNivel the listaResultadoNivel to set
	 */
	public void setListaResultadoNivel(List<ResultadoPreguntaExt> listaResultadoNivel) {
		this.listaResultadoNivel = listaResultadoNivel;
	}

	/**
	 * @return the excelOpt
	 */
	public ExcelOptions getExcelOpt() {
		return excelOpt;
	}

	/**
	 * @param excelOpt the excelOpt to set
	 */
	public void setExcelOpt(ExcelOptions excelOpt) {
		this.excelOpt = excelOpt;
	}

	/**
	 * @return the listaInformacionResultadoPregunta
	 */
	public List<ResultadoPreguntaExt> getListaInformacionResultadoPregunta() {
		return listaInformacionResultadoPregunta;
	}

	/**
	 * @param listaInformacionResultadoPregunta the listaInformacionResultadoPregunta to set
	 */
	public void setListaInformacionResultadoPregunta(List<ResultadoPreguntaExt> listaInformacionResultadoPregunta) {
		this.listaInformacionResultadoPregunta = listaInformacionResultadoPregunta;
	}

	/**
	 * @return the listaResultadoRetenSocial
	 */
	public List<ResultadoPreguntaExt> getListaResultadoRetenSocial() {
		return listaResultadoRetenSocial;
	}

	/**
	 * @param listaResultadoRetenSocial the listaResultadoRetenSocial to set
	 */
	public void setListaResultadoRetenSocial(List<ResultadoPreguntaExt> listaResultadoRetenSocial) {
		this.listaResultadoRetenSocial = listaResultadoRetenSocial;
	}
}

