package co.gov.dafp.sigep2.mbean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.HistoricoCMLazyDataModel;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.CmActivarUsuarios;
import co.gov.dafp.sigep2.entities.CmConfiguracion;
import co.gov.dafp.sigep2.entities.CmContratos;
import co.gov.dafp.sigep2.entities.CmCrearEntidad;
import co.gov.dafp.sigep2.entities.CmCrearEstructura;
import co.gov.dafp.sigep2.entities.CmCrearGrupo;
import co.gov.dafp.sigep2.entities.CmCrearNomenclaturaSalarial;
import co.gov.dafp.sigep2.entities.CmCrearPlanta;
import co.gov.dafp.sigep2.entities.CmCrearUsuarios;
import co.gov.dafp.sigep2.entities.CmHvEducacionDesaHumano;
import co.gov.dafp.sigep2.entities.CmHvEducacionFormal;
import co.gov.dafp.sigep2.entities.CmHvEducacionIdiomas;
import co.gov.dafp.sigep2.entities.CmHvEducacionOtros;
import co.gov.dafp.sigep2.entities.CmHvEvaluacionDesempeno;
import co.gov.dafp.sigep2.entities.CmHvExperienciaLaboral;
import co.gov.dafp.sigep2.entities.CmHvExperienciaLaboralDocen;
import co.gov.dafp.sigep2.entities.CmHvInformacionBasica;
import co.gov.dafp.sigep2.entities.CmRoles;
import co.gov.dafp.sigep2.entities.CmSituacionesAdministrativas;
import co.gov.dafp.sigep2.entities.CmVinculaciones;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.ProcesosCargaMasivaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.UtilidadesFaces;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.enums.TipoDocumentoIdentidadEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
@ManagedBean
public class HistoricoCarguesMasivosBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -131671368173191781L;
	public static final Short ESTADO_ACTIVO = 1;
	private String TIPO_DOC_PASAPORTE = TipoDocumentoIdentidadEnum.PA.getDescripcion();
	private ProcesosCargaMasivaExt historicoCM;
	private LazyDataModel<ProcesosCargaMasivaExt> listaHistoricoCM;
	private Date fechaInicio;
	private Date fechaFin;
	private Short tipoCargue;
	private PersonaDTO persona;
	private Short estado;
	private EntidadDTO entidad;
	private List<SelectItem> lstEntidadesCombo = new ArrayList<SelectItem>();
	private Long entidadSelect;
	private Long diasHistoricoCM;
	private StreamedContent archivoLog;
	private Boolean bmultiEntidad= false;

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@PostConstruct
	public void init() {
		// Inicio - Validacion de seguridad de roles por funcionalidad
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, String> paramMap = contexto.getRequestParameterMap();
        String recursoId = paramMap.get("recursoId");
		validaPermisosAcciones(recursoId);
		UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		diasHistoricoCM = 8L;
		if (usuarioSesion != null) {
			try {
				List<EntidadDTO> listaEntidades = IngresoSistemaDelegate.obtenerEntidadesUsuario(usuarioSesion);
				 if (listaEntidades != null &&  listaEntidades.size() >1 ) {
					 bmultiEntidad = true;
					 for(EntidadDTO entidad:listaEntidades){
						 lstEntidadesCombo.add(new SelectItem(entidad.getId(),entidad.getNombreEntidad()));
					 }
					} else  {
						entidad =  (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
						bmultiEntidad = false;
					} 
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}
		}
		persona = new PersonaDTO();
		historicoCM = new ProcesosCargaMasivaExt();
		printTable();
		
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_CARGUESMASIVOS);		
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
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		if (id != null) {
			this.init();
		}
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	}
	
	public LazyDataModel<ProcesosCargaMasivaExt> getListaHistoricoCM() {
		return listaHistoricoCM;
	}

	public void setListaHistoricoCM(LazyDataModel<ProcesosCargaMasivaExt> listaHistoricoCM) {
		this.listaHistoricoCM = listaHistoricoCM;
	}

	public void printTable() {
		try {
			if (!usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO,
					RolDTO.JEFE_CONTRATOS, RolDTO.OPERADOR_CONTRATOS, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
	}

	/**
	 * Se realiza la búsqueda de los procesos de acuerdo a los filtros seleccionados
	 * 
	 */
	public void consultarProcesos() {
		if (!validarForm())
			return;
		historicoCM = new ProcesosCargaMasivaExt();
		historicoCM.setFechaInicio(fechaInicio);
		historicoCM.setFechaFin(fechaFin);
		historicoCM.setTipoCargue(tipoCargue);
		if (persona.getTipoIdentificacionId() != null) {
			historicoCM.setCodTipoIdentificacion(persona.getTipoIdentificacionId().getId());
		}
		historicoCM.setNumeroIdentificacion(
				persona.getNumeroIdentificacion().equals("") ? null : persona.getNumeroIdentificacion());
		historicoCM.setPrimerNombre(persona.getPrimerNombre().equals("") ? null : persona.getPrimerNombre());
		historicoCM.setPrimerApellido(persona.getPrimerApellido().equals("") ? null : persona.getPrimerApellido());
		historicoCM.setEstado(estado);
		if (lstEntidadesCombo!=null && lstEntidadesCombo.size() > 1 && entidadSelect!=null) {
				historicoCM.setCodEntidad(entidadSelect.shortValue());
		}
		listaHistoricoCM = new HistoricoCMLazyDataModel(historicoCM);
	}

	private boolean validarForm() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dFechaIni = null,dFechaSistema=null;
		try {
			if(fechaInicio!=null){
				dFechaIni= sdf.parse(sdf.format(fechaInicio));
				dFechaSistema= sdf.parse(sdf.format(new Date()));				
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int contadorFiltros = 0;

		if (fechaInicio != null) {
			contadorFiltros++;
		}

		if (fechaFin != null) {
			contadorFiltros++;
		}

		if (tipoCargue != null) {
			contadorFiltros++;
		}

		if (persona.getTipoIdentificacionId() != null) {
			contadorFiltros++;
		}

		if (!"".equals(persona.getNumeroIdentificacion())) {
			contadorFiltros++;
		}

		if (!"".equals(persona.getPrimerNombre())) {
			contadorFiltros++;
		}

		if (!"".equals(persona.getPrimerApellido())) {
			contadorFiltros++;
		}

		if (estado != null) {
			contadorFiltros++;
		}

		if (entidadSelect!=null) {
				contadorFiltros++;
		}
		
		if(dFechaIni!=null && dFechaIni.after(dFechaSistema)){
			return false;
		}
		if (contadorFiltros == 0) {
			listaHistoricoCM = null;
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_FILTRO_CONSULTA_HISTORICO_CM);
			return false;
		}
		return true;
	}

	/**
	 * Se limpian todos los filtros de búsqueda
	 * 
	 */
	public void limpiar() {
		this.fechaInicio = null;
		this.fechaFin = null;
		this.tipoCargue = null;
		this.persona.setTipoIdentificacionId(null);
		this.persona.setNumeroIdentificacion("");
		this.persona.setPrimerNombre("");
		this.persona.setPrimerApellido("");
		this.estado = null;
		this.entidadSelect = null;
		listaHistoricoCM = null;
	}

	/**
	 * Método para la descarga de los logs.
	 * 
	 */
	public void descargarLogProceso(ProcesosCargaMasivaExt proceso) {
		proceso = ComunicacionServiciosSis.getProcesosCargaMasiva(proceso.getCodProcesoCargaMasiva());
		Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(proceso.getTipoCargue()));
		try {
			String relativePath = ConfigurationBundleConstants.getPathRutaLog();
			String pathArchivoLog = System.getProperty("CONFIG_PATH") + relativePath;
			pathArchivoLog = pathArchivoLog.replace("\\", "/");
			String urlArchivo = WebUtils.WS_MULTIMEDIA_DOWNLOAD + ComunicacionServiciosHV.getTokenService() + proceso.getUrlArchivoLogs();
			try {
				BufferedInputStream in = new BufferedInputStream(new URL(urlArchivo).openStream());
				
				File file = new File(pathArchivoLog + "historico/"+  proceso.getCodProcesoCargaMasiva() + FileUtil.XLSX);
				file.setExecutable(true, false);
			    file.setReadable(true, false);
			    file.setWritable(true, false);
				FileOutputStream fout = new FileOutputStream(file);
			    byte dataBuffer[] = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			    	fout.write(dataBuffer, 0, bytesRead);
			    }
			    fout.flush();
			    fout.close();
			} catch (IOException e) {
			    //e.printStackTrace();
			}
			
			//Validar si el archivo existe
			File archivoLogs = new File(pathArchivoLog + "historico/"+  proceso.getCodProcesoCargaMasiva() + FileUtil.XLSX);
			if(archivoLogs.exists()) {
			
				// Consultar nombre del archivo
				String nombreArchivoCargado = proceso.getCodProcesoCargaMasiva().toString();
	
				String pathServeletDescarga = generarRegistrosLogs(paramProceso, pathArchivoLog, nombreArchivoCargado,
						proceso.getCodProcesoCargaMasiva());
				
				String archivoDescarga = pathServeletDescarga + "&path1=" + pathArchivoLog + "historico/" + 
				nombreArchivoCargado + FileUtil.XLSX + "&proceso=" + proceso.getTipoCargue();
				
				//Abrimos el servelet que descarga el log
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("var a = document.createElement('a'); a.target = '_blank'; a.href = '" + archivoDescarga +"'; a.click();");
			}else {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ARCHIVO_ARCHIVO_NO_EXISTE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna la ruta desde donde se debe descargar la plantilla con el historial
	 * de los logs
	 * 
	 * @param nombreArchivoCargado
	 * @param codProceso
	 * 
	 * @param proceso
	 * @return
	 * @return
	 * @return Mensaje de error mensajesErrores
	 */
	public String generarRegistrosLogs(Parametrica paramProceso, String Ubicacion, String nombreArchivoCargado,
			Long codProceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path=" 
				+ ConfigurationBundleConstants.getPathRutaLog() + "historico/" + codProceso + FileUtil.XLSX;
		String pathArchivoLogs = Ubicacion + "historico/" + nombreArchivoCargado  + FileUtil.XLSX;
		InputStream excelStream = null;
		boolean blnExisteTab = false;

		Calendar calendario = Calendar.getInstance();
		int year, mes, dia, hora, minutos, segundos;

		year = calendario.get(Calendar.YEAR);
		mes = calendario.get(Calendar.MONTH);
		dia = calendario.get(Calendar.DAY_OF_MONTH);
		hora = calendario.get(Calendar.HOUR_OF_DAY);
		minutos = calendario.get(Calendar.MINUTE);
		segundos = calendario.get(Calendar.SECOND);

		String fechaConHora = year + "-" + mes + "-" + dia + ":" + hora + ":" + minutos + ":" + segundos;

		try {
			excelStream = new FileInputStream(pathArchivoLogs);

			XSSFWorkbook hssfWorkbook = new XSSFWorkbook(excelStream);
			
			List<Parametrica> tabsArchivo = ComunicacionServiciosSis.getParametricaPorIdPadre(paramProceso.getCodTablaParametrica().longValue());
			// Si no trae ningún resultado es porque el proceso no tiene varias tabs.
			if (tabsArchivo.isEmpty()) {
				tabsArchivo = new ArrayList<Parametrica>();
				tabsArchivo.add(paramProceso);
			}
			for (Parametrica tabArchivo : tabsArchivo) {
				List<CmConfiguracion> configuracion = null;
				CmConfiguracion cmConfiguracion = new CmConfiguracion();
				cmConfiguracion.setCodProcesoCargue((short) tabArchivo.getCodTablaParametrica().longValue());
				cmConfiguracion.setFlgActivo(ESTADO_ACTIVO);
				configuracion = ComunicacionServiciosSis.getCmConfiguracion(cmConfiguracion);
				// Trae la hoja de Excel de acuerdo al parametro
				String hojaExcel = tabArchivo.getValorParametro();
				// Elegimos la hoja que se pasa por parámetro.
				XSSFSheet hssfSheet = hssfWorkbook.getSheet(hojaExcel);
				String logs = "LOGS_" + hojaExcel;
				if(logs.length() > 31) {
					logs = logs.substring(0, 31);
				}
				
				XSSFSheet sheetLog = hssfWorkbook.getSheet(logs);
				if(sheetLog !=null) {
				   for(int i=0;i<hssfWorkbook.getNumberOfSheets();i++){
		                if(hssfWorkbook.getSheetAt(i).getSheetName().equals(logs)){
		                	//hssfWorkbook.removeSheetAt(i);
		                	blnExisteTab = true;
		                	break;
		                }
		            } 
				}
				if (!blnExisteTab){
					// Creamos la hoja nueva que vamos a utilizar.
					XSSFSheet hssfSheetNew = hssfWorkbook.createSheet(logs);
					// Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el
					// contenido de las celdas.
					XSSFRow hssfRow;
					XSSFRow hssfRowNew = null;
	
					// Inicializo el objeto que leerá el valor de la celda
					XSSFCell cellNew;
	
					String cellValue;
	
					/*Encabezado del documento*/
					/*Estilos*/
					/*encabezado*/
					XSSFCellStyle  style = hssfWorkbook.createCellStyle();
					XSSFFont font = hssfWorkbook.createFont();
					font.setFontName("Arial");
					font.setFontHeightInPoints((short)10);
					font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
					style.setFont(font);
					style.setFillForegroundColor((short) 529);
					style.setFillPattern(CellStyle.SOLID_FOREGROUND);
					style.setFillBackgroundColor((short) 57);
					
					/*detalles*/
					XSSFCellStyle  styleDetalle = hssfWorkbook.createCellStyle();
					XSSFFont fontDetalle = hssfWorkbook.createFont();
					fontDetalle.setFontName("Arial");
					fontDetalle.setFontHeightInPoints((short)10);
					
					for (int r = 0; r < 6; r++) {
						hssfRow = hssfSheet.getRow(r);
						if (hssfRow == null) {
							break;
						} else {
							hssfRowNew = hssfSheetNew.createRow(r);
	
							for (int c = 0; c < configuracion.size(); c++) {
	
								cellValue = hssfRow.getCell(c) == null ? ""
										: (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_STRING)
												? hssfRow.getCell(c).getStringCellValue()
												: (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_NUMERIC)
														? "" + hssfRow.getCell(c).getNumericCellValue()
														: (hssfRow.getCell(c).getCellType() == Cell.CELL_TYPE_BOOLEAN)
																? "" + hssfRow.getCell(c).getBooleanCellValue()
																: "";
								cellNew = hssfRowNew.createCell(c);
								cellNew.setCellType(Cell.CELL_TYPE_STRING);
								if (r == 1 && c == 1)
									cellNew.setCellValue(fechaConHora);
								else
									cellNew.setCellValue(cellValue);
								
								/*damos formaro solo al encabezado*/
								if(c<=1 || r ==5)
									cellNew.setCellStyle(style);							
							}
						}
					}
	
					cellNew = hssfRowNew.createCell(configuracion.size());
					cellNew.setCellType(Cell.CELL_TYPE_STRING);
					cellNew.setCellValue("Estado");
					cellNew.setCellStyle(style);
					
					cellNew = hssfRowNew.createCell(configuracion.size() + 1);
					cellNew.setCellType(Cell.CELL_TYPE_STRING);
					cellNew.setCellValue("Resultado");
					cellNew.setCellStyle(style);
					
					hssfSheetNew.autoSizeColumn(configuracion.size());
					hssfSheetNew.autoSizeColumn(configuracion.size() + 1);				
					/*Fin del encabezdo*/
	
					List<?> infoLogs = ComunicacionServiciosSis.getCmCrearUsuarios(codProceso);
					List<CmCrearUsuarios> 				infoLogs1 = null;
					List<CmActivarUsuarios> 			infoLogs2 = null;
					List<CmHvEvaluacionDesempeno> 		infoLogs3 = null;
					List<CmHvInformacionBasica> 		infoLogs4 = null;
					List<CmHvEducacionFormal> 			infoLogs5 = null;
					List<CmHvEducacionDesaHumano> 		infoLogs6 = null;
					List<CmHvEducacionOtros> 			infoLogs7 = null;
					List<CmHvEducacionIdiomas> 			infoLogs8 = null;
					List<CmHvExperienciaLaboral> 		infoLogs9 = null;
					List<CmHvExperienciaLaboralDocen> 	infoLogs10 = null;
					List<CmSituacionesAdministrativas> 	infoLogs11 = null;
					List<CmContratos> 					infoLogs12 = null;
					List<CmVinculaciones> 				infoLogs13 = null;
					List<CmRoles> 						infoLogs14 = null;
					List<CmCrearEntidad> 				infoLogs15 = null;
					List<CmCrearNomenclaturaSalarial> 	infoLogs16 = null;
					List<CmCrearPlanta> 				infoLogs17 = null;
					List<CmCrearEstructura> 			infoLogs18 = null;
					List<CmCrearGrupo> 					infoLogs19 = null;
					
					int opLog = 0;
	
					if (paramProceso.getCodTablaParametrica().toString().equals("1622")) {
						infoLogs1 = ComunicacionServiciosSis.getCmCrearUsuarios(codProceso);
						infoLogs 	= infoLogs1;
						opLog		= 1;
					} else if (paramProceso.getCodTablaParametrica().toString().equals("1623")) {
						infoLogs2 = ComunicacionServiciosSis.getCmActivarUsuarios(codProceso);
						infoLogs = infoLogs2;
						opLog		= 2;
					} else if (paramProceso.getCodTablaParametrica().toString().equals("1624")) {
						infoLogs3 = ComunicacionServiciosSis.getCmEvaluacionDesempeno(codProceso);
						infoLogs = infoLogs3;
						opLog		= 3;
					} else if (paramProceso.getCodTablaParametrica().toString().equals("1625")) {
						
						if (tabArchivo.getCodTablaParametrica().toString().equals("1686")) {
							infoLogs4 = ComunicacionServiciosSis.getCmHvInformacionBasica(codProceso);
							infoLogs = infoLogs4;
							opLog		= 4;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1687")) {
							infoLogs5 = ComunicacionServiciosSis.getCmHvEducacionFormal(codProceso);
							infoLogs = infoLogs5;
							opLog		= 5;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1688")) {
							infoLogs6 = ComunicacionServiciosSis.getCmHvEducacionDesaHumano(codProceso);
							infoLogs = infoLogs6;
							opLog		= 6;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1689")) {
							infoLogs7 = ComunicacionServiciosSis.getCmHvEducacionOtros(codProceso);
							infoLogs = infoLogs7;
							opLog		= 7;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1690")) {
							infoLogs8 = ComunicacionServiciosSis.getCmHvEducacionIdiomas(codProceso);
							infoLogs = infoLogs8;
							opLog		= 8;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1691")) {
							infoLogs9 = ComunicacionServiciosSis.getCmHvExperienciaLaboral(codProceso);
							infoLogs = infoLogs9;
							opLog		= 9;
						} else if (tabArchivo.getCodTablaParametrica().toString().equals("1692")) {
							infoLogs10 = ComunicacionServiciosSis.getCmHvExperienciaLaboralDocen(codProceso);
							infoLogs = infoLogs10;
							opLog		= 10;
						}
						
					} else if(paramProceso.getCodTablaParametrica().toString().equals("1626")) {
						infoLogs11 = ComunicacionServiciosSis.getCmSituacionesAdministrativas(codProceso);
						infoLogs = infoLogs11;
						opLog		= 11;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("1627")) {
						infoLogs12 = ComunicacionServiciosSis.getCmContratos(codProceso);
						infoLogs = infoLogs12;
						opLog		= 12;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("2068")) {
						infoLogs13 = ComunicacionServiciosSis.getCmVinculaciones(codProceso);
						infoLogs = infoLogs13;
						opLog		= 13;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("2074")) {
						infoLogs14 = ComunicacionServiciosSis.getCmRoles(codProceso);
						infoLogs = infoLogs14;
						opLog		= 14;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("1628")) {
						infoLogs15 = ComunicacionServiciosSis.getCmCrearEntidad(codProceso);
						infoLogs = infoLogs15;
						opLog		= 15;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("2164")) {
						infoLogs16 = ComunicacionServiciosSis.getCmCrearNomenclaturaSalarial(codProceso);
						infoLogs = infoLogs16;
						opLog		= 16;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("2248")) {
						infoLogs17 = ComunicacionServiciosSis.getCmCrearPlanta(codProceso);
						infoLogs = infoLogs17;
						opLog		= 17;
					} else if(paramProceso.getCodTablaParametrica().toString().equals("2245")) {
						if (tabArchivo.getCodTablaParametrica().toString().equals("2246")) {
							infoLogs18 = ComunicacionServiciosSis.getCmCrearEstructura(codProceso);
							infoLogs = infoLogs18;
							opLog		= 18;
						}else if (tabArchivo.getCodTablaParametrica().toString().equals("2247")) {
							infoLogs19 = ComunicacionServiciosSis.getCmCrearGrupo(codProceso);
							infoLogs = infoLogs19;
							opLog		= 19;
						}
					} 
					
					for (int r = 0; r < infoLogs.size(); r++) {
	
						if (infoLogs.get(r) == null) {
							break;
						} else {
							hssfRowNew = hssfSheetNew.createRow(r + 6);
							for (int x = 0; x < configuracion.size(); x++) {
								try {
									Class valCelda = infoLogs.get(r).getClass();
									String nombreMetodo = configuracion.get(x).getNombreCampo();
									nombreMetodo = "get" + nombreMetodo.substring(0, 1).toUpperCase()+ nombreMetodo.substring(1);
									Method metodo = valCelda.getDeclaredMethod(nombreMetodo);
									Object valorCelda = metodo.invoke(infoLogs.get(r));
									if(valorCelda == null)
										valorCelda = "";
									cellNew = hssfRowNew.createCell(x);
									cellNew.setCellType(Cell.CELL_TYPE_STRING);
									cellNew.setCellValue(valorCelda.toString());
									cellNew.setCellStyle(styleDetalle);
									hssfSheetNew.autoSizeColumn(x);	
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
								} catch (SecurityException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
	
							String resultado = null;
							switch (opLog) {
							  case 1:
								  if (infoLogs1 != null && infoLogs1.get(r).getResultado() != null) {
										if (infoLogs1.get(r).getResultado().equals("Usuario creado correctamente")
												||infoLogs1.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								  break;
							  case 2:
								  if (infoLogs2 != null && infoLogs2.get(r).getResultado() != null) {
										if (infoLogs2.get(r).getResultado().equals("Usuario activado correctamente")
												||infoLogs2.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
							    break;
							  case 3:
								  if (infoLogs3 != null && infoLogs3.get(r).getResultado() != null) {
										if (infoLogs3.get(r).getResultado().equals("Evaluación de Desempeño guardada correctamente")
												||infoLogs3.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
							    break;
							  case 4:
								  if (infoLogs4 != null && infoLogs4.get(r).getResultado() != null) {
										if (infoLogs4.get(r).getResultado().equals("Informacion Basica creada correctamente")
												||infoLogs4.get(r).getResultado().equals("Registro insertado exitosamente")
												||infoLogs4.get(r).getResultado().equals("Usuario existe y se ha actualizado exitosamente")
												
												)
											resultado = "OK";
										else
											resultado = "Fallido";
									}
							    break;
							  case 5:
								  if (infoLogs5 != null && infoLogs5.get(r).getResultado() != null) {
										if (infoLogs5.get(r).getResultado().equals("Educacion Formal creada correctamente")
												||infoLogs5.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
							    break;
							  case 6:
								  if (infoLogs6 != null && infoLogs6.get(r).getResultado() != null) {
										if (infoLogs6.get(r).getResultado().equals("Educacion Desarrollo Humano creada correctamente")
												||  infoLogs6.get(r).getResultado().equals("Registro insertado exitosamente") )
											resultado = "OK";
										else
											resultado = "Fallido";
									}
							    break;
							  case 7:
								  if (infoLogs7 != null && infoLogs7.get(r).getResultado() != null) {
										if (infoLogs7.get(r).getResultado().equals("Educacion Otros creada correctamente")
												||infoLogs7.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
								  }
									
							    break;
							  case 8:
								  if (infoLogs8 != null && infoLogs8.get(r).getResultado() != null) {
										if (infoLogs8.get(r).getResultado().equals("Educacion Idiomas creada correctamente")
												||infoLogs8.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 9:
								  if (infoLogs9 != null && infoLogs9.get(r).getResultado() != null) {
										if (infoLogs9.get(r).getResultado().equals("Experiencia Laboral creada correctamente")
												||infoLogs9.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 10:
								  if (infoLogs10 != null && infoLogs10.get(r).getResultado() != null) {
										if (infoLogs10.get(r).getResultado().equals("Experiencia Laboral Docente creada correctamente")
												||infoLogs10.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 11:
								  if (infoLogs11 != null && infoLogs11.get(r).getResultado() != null) {
										if (infoLogs11.get(r).getResultado().equals("Situación administrativa creada correctamente")
												||infoLogs11.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 12:
								  if (infoLogs12 != null && infoLogs12.get(r).getResultado() != null) {
										if (infoLogs12.get(r).getResultado().equals("Contrato creado correctamente")
												||infoLogs12.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 13:
								  if (infoLogs13 != null && infoLogs13.get(r).getResultado() != null) {
										if (infoLogs13.get(r).getResultado().equals("Viculación creada correctamente")
												||infoLogs13.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 14:
								  if (infoLogs14 != null && infoLogs14.get(r).getResultado() != null) {
										if (infoLogs14.get(r).getResultado().equals("Proceso finalizado correctamente")
												||infoLogs14.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 15:
								  if (infoLogs15 != null && infoLogs15.get(r).getResultado() != null) {
										if (infoLogs15.get(r).getResultado().equals("Entidad creada correctamente")
												||infoLogs15.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 16:
								  if (infoLogs16 != null && infoLogs16.get(r).getResultado() != null) {
										if (infoLogs16.get(r).getResultado().equals("Nomenclatura y Escala Salarial creada correctamente")
												||infoLogs16.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 17:
								  if (infoLogs17 != null && infoLogs17.get(r).getResultado() != null) {
										if (infoLogs17.get(r).getResultado().equals("Planta creada correctamente")
												||infoLogs17.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 18:
								  if (infoLogs18 != null && infoLogs18.get(r).getResultado() != null) {
										if (infoLogs18.get(r).getResultado().equals("Estructura creada correctamente")
												||infoLogs18.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							  case 19:
								  if (infoLogs19 != null && infoLogs19.get(r).getResultado() != null) {
										if (infoLogs19.get(r).getResultado().equals("Grupo creado correctamente")
												||infoLogs19.get(r).getResultado().equals("Registro insertado exitosamente"))
											resultado = "OK";
										else
											resultado = "Fallido";
									}
								    break;
							}

							cellNew = hssfRowNew.createCell(configuracion.size());
							cellNew.setCellType(Cell.CELL_TYPE_STRING);
							cellNew.setCellValue(resultado);
							cellNew.setCellStyle(styleDetalle);
	
							cellNew = hssfRowNew.createCell(configuracion.size() + 1);
							cellNew.setCellType(Cell.CELL_TYPE_STRING);
							
							switch (opLog) {
							  case 1:
								  cellNew.setCellValue(((CmCrearUsuarios) infoLogs1.get(r)).getResultado());
							    break;
							  case 2:
								  cellNew.setCellValue(((CmActivarUsuarios) infoLogs2.get(r)).getResultado());
							    break;
							  case 3:
								  cellNew.setCellValue(((CmHvEvaluacionDesempeno) infoLogs3.get(r)).getResultado());
							    break;
							  case 4:
								  cellNew.setCellValue(((CmHvInformacionBasica) infoLogs4.get(r)).getResultado());
							    break;
							  case 5:
								  cellNew.setCellValue(((CmHvEducacionFormal) infoLogs5.get(r)).getResultado());
							    break;
							  case 6:
								  cellNew.setCellValue(((CmHvEducacionDesaHumano) infoLogs6.get(r)).getResultado());
							    break;
							  case 7:
								  cellNew.setCellValue(((CmHvEducacionOtros) infoLogs7.get(r)).getResultado());
							    break;
							  case 8:
								  cellNew.setCellValue(((CmHvEducacionIdiomas) infoLogs8.get(r)).getResultado());
							    break;
							  case 9:
								  cellNew.setCellValue(((CmHvExperienciaLaboral) infoLogs9.get(r)).getResultado());
								    break;
							  case 10:
								  cellNew.setCellValue(((CmHvExperienciaLaboralDocen) infoLogs10.get(r)).getResultado());
								    break;
							  case 11:
								  cellNew.setCellValue(((CmSituacionesAdministrativas) infoLogs11.get(r)).getResultado());
								    break;
							  case 12:
								  cellNew.setCellValue(((CmContratos) infoLogs12.get(r)).getResultado());
								    break;
							  case 13:
								  cellNew.setCellValue(((CmVinculaciones) infoLogs13.get(r)).getResultado());
								    break;
							  case 14:
								  cellNew.setCellValue(((CmRoles) infoLogs14.get(r)).getResultado());
								    break;
							  case 15:
								  cellNew.setCellValue(((CmCrearEntidad) infoLogs15.get(r)).getResultado());
								    break;
							  case 16:
								  cellNew.setCellValue(((CmCrearNomenclaturaSalarial) infoLogs16.get(r)).getResultado());
								    break;
							  case 17:
								  cellNew.setCellValue(((CmCrearPlanta) infoLogs17.get(r)).getResultado());
								    break;
							  case 18:
								  cellNew.setCellValue(((CmCrearEstructura) infoLogs18.get(r)).getResultado());
								    break;
							  case 19:
								  cellNew.setCellValue(((CmCrearGrupo) infoLogs19.get(r)).getResultado());
								    break;
							}
						}
					}
					hssfSheetNew.autoSizeColumn(configuracion.size());
					hssfSheetNew.autoSizeColumn(configuracion.size() + 1);	
				}
			}
			File file = new File(pathArchivoLogs);
			file.setExecutable(true, false);
		    file.setReadable(true, false);
		    file.setWritable(true, false);
			FileOutputStream outFile = new FileOutputStream(file);
			hssfWorkbook.write(outFile);
			outFile.flush();
			outFile.close();
		} catch (FileNotFoundException fileNotFoundException) {
			System.out.println("No se encontró el fichero: " + fileNotFoundException);
		} catch (IOException ex) {
			System.out.println("Error al procesar el fichero: " + ex);
		} finally {
			try {
				excelStream.close();
			} catch (IOException ex) {
				System.out.println("Error al procesar el fichero después de cerrarlo: " + ex);
			}
		}
		return pathServeletDescarga;
	}
	

	public void abrirUrl(String dir) throws IOException {
		UtilidadesFaces.redirect(dir);
	}

	public ProcesosCargaMasiva getHistoricoCM() {
		return historicoCM;
	}

	public void setHistoricoCM(ProcesosCargaMasivaExt historicoCM) {
		this.historicoCM = historicoCM;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public PersonaDTO getPersona() {
		return persona;
	}

	public void setPersona(PersonaDTO persona) {
		this.persona = persona;
	}

	public String getTIPO_DOC_PASAPORTE() {
		return TIPO_DOC_PASAPORTE;
	}

	public void setTIPO_DOC_PASAPORTE(String tIPO_DOC_PASAPORTE) {
		TIPO_DOC_PASAPORTE = tIPO_DOC_PASAPORTE;
	}

	public Short getEstado() {
		return estado;
	}

	public void setEstado(Short estado) {
		this.estado = estado;
	}

	public Short getTipoCargue() {
		return tipoCargue;
	}

	public void setTipoCargue(Short tipoCargue) {
		this.tipoCargue = tipoCargue;
	}

	public EntidadDTO getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadDTO entidad) {
		this.entidad = entidad;
	}

	public Long getDiasHistoricoCM() {
		return diasHistoricoCM;
	}

	public void setDiasHistoricoCM(Long diasHistoricoCM) {
		this.diasHistoricoCM = diasHistoricoCM;
	}



	public StreamedContent getArchivoLog() {
		return archivoLog;
	}

	public void setArchivoLog(StreamedContent archivoLog) {
		this.archivoLog = archivoLog;
	}
	
	public Boolean getBmultiEntidad() {
		return bmultiEntidad;
	}

	public void setBmultiEntidad(Boolean bmultiEntidad) {
		this.bmultiEntidad = bmultiEntidad;
	}

	public List<SelectItem> getLstEntidadesCombo() {
		return lstEntidadesCombo;
	}

	public void setLstEntidadesCombo(List<SelectItem> lstEntidadesCombo) {
		this.lstEntidadesCombo = lstEntidadesCombo;
	}

	public Long getEntidadSelect() {
		return entidadSelect;
	}

	public void setEntidadSelect(Long entidadSelect) {
		this.entidadSelect = entidadSelect;
	}
	
	
}