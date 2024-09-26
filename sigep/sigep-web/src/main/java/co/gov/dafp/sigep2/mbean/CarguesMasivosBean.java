package co.gov.dafp.sigep2.mbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.Estructura;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.DenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.MunicipioExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.mbean.ext.NomenclaturaExt;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.CargueMasivoThread;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.xml.Columna;

@Named
@ConversationScoped
public class CarguesMasivosBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -131671368173191781L;
	private Long tipoProceso;
	private StreamedContent formato;
	private UploadedFile archivoCargaMasiva = null;
	private UploadedFile archivoCargaMasivaAnexo = null;
	private String nombreArchivoCargaActivacionUsuarios;
	private String nombreArchivoCargaAnexos;
	private Boolean habilitadoArchivoAnexo = false;
	File cargaMasivaXls = null;
	File cargaMasivaAnexo = null;
	private String strMsgMensajes, strMsgTamanoArchivo;
	private Long llTamArchivos;
	private Long lngValorEntidad;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
	}

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");
		this.validaPermisosAcciones(recursoId);

		try {
			Parametrica parametrica = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAMANO_ARCHIVOS_CM.getValue()));
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro())) {
				llTamArchivos = Long.valueOf(parametrica.getValorParametro());
			} else
				llTamArchivos = 1000000L;

		} catch (Exception z) {
			llTamArchivos = 1000000L;
		}

		lngValorEntidad = getEntidadUsuario().getId();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,
				ConfigurationBundleConstants.OPT_VIDEO_CARGUESMASIVOS);
		buscarEntidad();
	}

	public void buscarEntidad() {
		Entidad entidad = ComunicacionServiciosEnt.getEntidadPorId(lngValorEntidad);
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

	/**
	 * MÃ©todo utilizado para descargar la plantilla de cargue masivo de acuerdo al
	 * tipo. TIPOS DE PROCESO: - Activación Masiva de Usuarios - Creación masiva de
	 * usuarios - Hoja de Vida - Evaluación de desempeño
	 */
	public void descargarPlantilla() {
		buscarEntidad();
		if (this.tipoProceso == null) {
			return;
		}
		logger.info(MessagesBundleConstants.CM_DOWN_PLANTILLA_tipoProceso + tipoProceso);
		System.out.println(MessagesBundleConstants.CM_DOWN_PLANTILLA_tipoProceso + tipoProceso);
		Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoProceso));

		if (paramProceso == null || paramProceso.getValorParametro().isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_PARAM_PROCESO_CARGUE_MASIVO_NO_EXISTE);
			return;
		}
		try {
			switch (paramProceso.getValorParametro()) {
			case MessagesBundleConstants.CM_ACTIVAR_USUARIOS:
				List<String> paths = getPaths(paramProceso);
				String pathPlantillas = paths.get(0);
				String pathPlantillasUser = paths.get(1);
				String pathPlantillaDownload = DownloadXlxsActivarUsers(pathPlantillas, pathPlantillasUser,
						paramProceso);
				this.generateFile(pathPlantillaDownload, paths.get(1));
				break;

			case MessagesBundleConstants.CM_ROLES:				
				  List<String> pathsRoles = getPaths(paramProceso);
				    String pathPlantillasRoles = pathsRoles.get(0);
				    String pathPlantillasUserRoles = pathsRoles.get(1);
				    String pathPlantillaDownloadRoles = DownloadXlxsRoles(pathPlantillasRoles, pathPlantillasUserRoles, paramProceso);
				    this.generateFile(pathPlantillaDownloadRoles, pathsRoles.get(1));
				break;
				
			case MessagesBundleConstants.CM_CREAR_NOMENCLATURA_SALARIAL:				
				List<String> pathsNomenclatura = getPaths(paramProceso);
			    String pathPlantillasNomenclatura = pathsNomenclatura.get(0);
			    String pathPlantillasUserNomenclatura = pathsNomenclatura.get(1);
			    String pathPlantillaDownloadNomenclatura = DownloadXlxsNomenclaturaS(pathPlantillasNomenclatura, pathPlantillasUserNomenclatura, paramProceso);
			    this.generateFile(pathPlantillaDownloadNomenclatura, pathsNomenclatura.get(1));
				break;

				
			case MessagesBundleConstants.CM_CREAR_USUARIOS:
				List<String> pathsUsuario = getPaths(paramProceso);
				String pathCrearUsuarioPlantillas = pathsUsuario.get(0);
				String pathPlantillaUserCrearUsuario = pathsUsuario.get(1);
				String pathPlantilaCrearUsuarioDownload = DownloadXlxsCrearUsuarios(pathCrearUsuarioPlantillas,
						pathPlantillaUserCrearUsuario, paramProceso);
				this.generateFile(pathPlantilaCrearUsuarioDownload, pathPlantillaUserCrearUsuario);
				break;

			case MessagesBundleConstants.CM_CONTRATOS:
				List<String> pathsContratos = getPaths(paramProceso);
				String pathContratosPlantillas = pathsContratos.get(0);
				String pathContratosPlantillasUser = pathsContratos.get(1);
				String pathPlantillasContratosDownload = DownloadXlxsContratos(pathContratosPlantillas,
						pathContratosPlantillasUser, paramProceso);
				this.generateFile(pathPlantillasContratosDownload, pathContratosPlantillasUser);
				break;

			case MessagesBundleConstants.CM_CREAR_PLANTA:
				List<String> pathPlantas = getPaths(paramProceso);
				String pathPlantaPlantillas = pathPlantas.get(0);
				String pathPlantaPlantillasUser = pathPlantas.get(1);
				String pathPlantillasPlantasDownload = DownloadXlxsCrearPlanta(pathPlantaPlantillas,
						pathPlantaPlantillasUser, paramProceso);
				this.generateFile(pathPlantillasPlantasDownload, pathPlantaPlantillasUser);

				break;

			case MessagesBundleConstants.CM_VINCULACIONES:
				List<String> pathsVinculacion = getPaths(paramProceso);
				String pathPlantillasV = pathsVinculacion.get(0);
				String pathPlantillasUserV = pathsVinculacion.get(1);
				String pathPlantillaDownloadV = DownloadXlxsVinculacion(pathPlantillasV, pathPlantillasUserV,
						paramProceso);
				this.generateFile(pathPlantillaDownloadV, pathsVinculacion.get(1));
				break;
				
			default:

				List<String> pathsDefault = getPaths(paramProceso);
				String pathPlantillasD = pathsDefault.get(0);
				String pathPlantillasUserD = pathsDefault.get(1);
				String pathPlantillaDownloadD = archivoDownloadXlxs(pathPlantillasD, pathPlantillasUserD, paramProceso);
				this.generateFile(pathPlantillaDownloadD, pathsDefault.get(1));
			}
		} catch (Exception e) {
			logger.error("void downloadFormato()", e);
		} finally {

		}
	}
	
	public String DownloadXlxsVinculacion(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
	            + ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
	            + FileUtil.XLSX;
	    List<Parametrica> tabsArchivo = ComunicacionServiciosSis
	            .getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
	    if (tabsArchivo.isEmpty()) {
	        tabsArchivo = new ArrayList<Parametrica>();
	        tabsArchivo.add(proceso);
	    }
	    try {
	        FileInputStream file 		= new FileInputStream(pathPlantillas);
	        XSSFWorkbook workbook 		= new XSSFWorkbook(file);
	        ExternalContext contexto 	= FacesContext.getCurrentInstance().getExternalContext();
	        EntidadDTO entidadUsuario 	= (EntidadDTO) contexto.getSessionMap()
	                .get(MessagesBundleConstants.entidadUsuario);

	        // Creación de objetos para manipular entidades
	        EntidadPlantaExt entidadExt = new EntidadPlantaExt();
	        entidadExt.setCodEntidad(entidadUsuario.getId());
			entidadExt.setFlgActivo((short) 1);
			
			EntidadPlantaDetalleExt entidadPlanta = new EntidadPlantaDetalleExt();
			entidadPlanta.setCodEntidad(entidadUsuario.getId());
			entidadPlanta.setFlgActivo((short) 1);
			
			DependenciaEntidadExt depEntidad = new DependenciaEntidadExt();
			depEntidad.setFlgActivo((short) 1);
			depEntidad.setCodEntidad(entidadUsuario.getId());
	        
	        for (Parametrica tabArchivo : tabsArchivo) {
	            crearEncabezado(workbook, tabArchivo, entidadUsuario);
	            
	            List<Parametrica> parametricaTDoctos = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_DE_IDENTIFICACION.getValue());
	            
	            List<EntidadPlantaExt> listClasificacion = ComunicacionServiciosVin
						.getClasificacionPlantaByEntidad(entidadExt);
	            
	            List<EntidadPlantaExt> clasePlantaByEntidad = ComunicacionServiciosVin
						.getClasePlantaByEntidad(entidadExt);
	            
	            List<EntidadPlantaDetalleExt> listCargos = ComunicacionServiciosVin.getCargosPlanta(entidadPlanta);
	            
	            List<EntidadPlantaDetalleExt> listCodigosCargos = ComunicacionServiciosVin.getCodigosCargos(entidadPlanta);
	            
				List<Parametrica> parametricaTipoNombramiento = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPO_NOMBRAMIENTO.getValue());
				
				List<EntidadPlantaDetalleExt> listNaturalezaEmpleo = ComunicacionServiciosVin
						.getNaturalezaEmpleo(entidadPlanta);
				
				List<DependenciaEntidadExt> listDependencia = ComunicacionServiciosVin
						.getDependenciaEntidadFilter(depEntidad);
				

	            XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);

	            int maxSize = Math.max(parametricaTDoctos.size(), listClasificacion.size());
	            maxSize = Math.max(maxSize, clasePlantaByEntidad.size());
	            maxSize = Math.max(maxSize, listCargos.size());
	            maxSize = Math.max(maxSize, listCodigosCargos.size());
	            maxSize = Math.max(maxSize, parametricaTipoNombramiento.size());
	            maxSize = Math.max(maxSize, listNaturalezaEmpleo.size());
	            maxSize = Math.max(maxSize, listDependencia.size());

	            for (int i = 0; i < maxSize; i++) {
	                XSSFRow fila = hoja.createRow(i + 1);

	                // Columna 1: Tipos de identificacion
	                if (i < parametricaTDoctos.size()) {
	                    Parametrica parametrica = parametricaTDoctos.get(i);
	                    Cell celda = fila.createCell(1);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 2: Clase planta
	                if (i < clasePlantaByEntidad.size()) {
	                	EntidadPlantaExt clase = clasePlantaByEntidad.get(i);
	                    Cell celda = fila.createCell(3);
	                    celda.setCellValue(clase.getNombreClasePlanta());
	                }

	                // Columna 3: clasificacion planta
	                if (i < listClasificacion.size()) {
	                	EntidadPlantaExt clasificacion = listClasificacion.get(i);
	                    Cell celda = fila.createCell(5);
	                    celda.setCellValue(clasificacion.getNombreClasificacionPlanta());
	                }

	                // Columna 4: Tipo nombramiento
	                if (i < parametricaTipoNombramiento.size()) {
	                	Parametrica parametrica = parametricaTipoNombramiento.get(i);
	                    Cell celda = fila.createCell(7);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 5: Naturaleza Empleo
	                if (i < listNaturalezaEmpleo.size()) {
	                	EntidadPlantaDetalleExt naturalezaEmpleo = listNaturalezaEmpleo.get(i);
	                    Cell celda = fila.createCell(9);
	                    celda.setCellValue(naturalezaEmpleo.getNombreNaturalezaEmpleo());
	                }

	                // Columna 6: Denominaciones
	                if (i < listCargos.size()) {
	                	EntidadPlantaDetalleExt entidadPlantaDetalle = listCargos.get(i);
	                    Cell celda = fila.createCell(13);
	                    celda.setCellValue(entidadPlantaDetalle.getNombreCargo());
	                    celda = fila.createCell(11);
						celda.setCellValue(entidadPlantaDetalle.getNombreGrado());
						Cell celda1 = fila.createCell(25);
	                    celda1.setCellValue(entidadPlantaDetalle.getNombreCodigo());
	                    Cell celda2 = fila.createCell(26);
	                    celda2.setCellValue(entidadPlantaDetalle.getNombreGrado());
	                }
	                
	                if (i < listCodigosCargos.size()) {
	                	EntidadPlantaDetalleExt denominacionExt = listCodigosCargos.get(i);
	                    Cell celda = fila.createCell(15);
	                    celda.setCellValue(denominacionExt.getNombreCodigo());
	                    Cell celda1 = fila.createCell(22);
	                    celda1.setCellValue(denominacionExt.getNombreDenominacion());
	                    Cell celda2 = fila.createCell(23);
	                    celda2.setCellValue(denominacionExt.getNombreCodigo());
	                }
	               
	                if (i < listDependencia.size()) {
	                	DependenciaEntidadExt dependencia = listDependencia.get(i);
	                    Cell celda = fila.createCell(17);
	                    celda.setCellValue(dependencia.getNombreDependencia());
	                }
	            }
	            file.close();
	        }	        
	        FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
	        workbook.write(outFile);
	        outFile.close();
	    } catch (Exception e) {
	        logger.error("Error al descargar archivo Excel", e);
	    }
	    return pathServeletDescarga; 
	
	}

	
	public String DownloadXlxsNomenclaturaS(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;

		try {
			FileInputStream file = new FileInputStream(pathPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);

			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");

			crearEncabezado(workbook, proceso, entidadUsuario);

			List<Parametrica> parametricaTNorma = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.TIPO_NORMA.getValue());
			List<Parametrica> parametricaCOrganica = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.TIPO_CLASIFICACION_ORGANICA.getValue());
			List<Parametrica> parametricaSCarrera = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.TIPO_SISTEMA_CARRERA.getValue());
			List<Parametrica> parametricaNivel = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.NIVEL_JERARQUICO.getValue());
			List<Denominacion> listaDenominaciones = ComunicacionServiciosEnt
					.getDenomincacionesFiltro(new Denominacion());
			List<Parametrica> parametricaCodigo = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.CODIGO_DENOMINACION.getValue());
			List<Parametrica> parametricaGrado = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.ENTIDAD_NOMENCLATURA_GRADOS.getValue());
			List<Parametrica> parametricaTmoneda = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.TIPO_MONEDA.getValue());

			int maxSize = Arrays
					.asList(parametricaTNorma.size(), parametricaCOrganica.size(), parametricaSCarrera.size(),
							parametricaNivel.size(), listaDenominaciones.size(), parametricaCodigo.size(),
							parametricaGrado.size(), parametricaTmoneda.size())
					.stream().mapToInt(Integer::intValue).max().orElse(0);

			for (int index = 0; index < maxSize; index++) {
				XSSFRow fila = hoja.getRow(index + 1);
				if (fila == null) {
					fila = hoja.createRow(index + 1);
				}

				if (index < parametricaTNorma.size()) {
					Parametrica parametrica = parametricaTNorma.get(index);
					Cell celda = fila.createCell(1);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaCOrganica.size()) {
					Parametrica parametrica = parametricaCOrganica.get(index);
					Cell celda = fila.createCell(3);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaSCarrera.size()) {
					Parametrica parametrica = parametricaSCarrera.get(index);
					Cell celda = fila.createCell(5);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaNivel.size()) {
					Parametrica parametrica = parametricaNivel.get(index);
					Cell celda = fila.createCell(7);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < listaDenominaciones.size()) {
					Denominacion parametrica = listaDenominaciones.get(index);
					Cell celda = fila.createCell(9);
					celda.setCellValue(parametrica.getNombreDenominacion());
				}

				if (index < parametricaCodigo.size()) {
					Parametrica parametrica = parametricaCodigo.get(index);
					Cell celda = fila.createCell(11);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaGrado.size()) {
					Parametrica parametrica = parametricaGrado.get(index);
					Cell celda = fila.createCell(13);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaTmoneda.size()) {
					Parametrica parametrica = parametricaTmoneda.get(index);
					Cell celda = fila.createCell(15);
					celda.setCellValue(parametrica.getValorParametro());
				}

			}

			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
			workbook.write(outFile);
			outFile.close();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pathServeletDescarga;
	}

	public String DownloadXlxsRoles(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());

		RolExt objRolExt = new RolExt();
		objRolExt.setFlgActivo((short) 1);

		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}
		try {
			FileInputStream file = new FileInputStream(pathPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");

			crearEncabezado(workbook, proceso, entidadUsuario);

			List<Parametrica> parametricaTDoctos = ComunicacionServiciosSis
					.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_DE_IDENTIFICACION.getValue());
			List<RolDTO> parametricaRoles = ComunicacionServiciosSis.getRolesSistemaXObjeto(objRolExt);

			IntStream.range(0, Math.max(parametricaTDoctos.size(), parametricaRoles.size())).forEach(index -> {

				XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);
				if (hoja == null) {
					hoja = workbook.createSheet(MessagesBundleConstants.PARAMETRICAS);
				}
				XSSFRow filaTitulo = hoja.getRow(0);
				if (filaTitulo == null) {
					filaTitulo = hoja.createRow(0);
				}

				if (index < parametricaTDoctos.size()) {
					Parametrica parametrica = parametricaTDoctos.get(index);
					XSSFRow fila = hoja.getRow(index + 1);
					if (fila == null) {
						fila = hoja.createRow(index + 1);
					}
					Cell celda = fila.createCell(1);
					celda.setCellValue(parametrica.getValorParametro());
				}

				if (index < parametricaRoles.size()) {
					RolDTO rol = parametricaRoles.get(index);
					XSSFRow fila2 = hoja.getRow(index + 1);
					if (fila2 == null) {
						fila2 = hoja.createRow(index + 1);
					}
					Cell celda2 = fila2.createCell(3);
					celda2.setCellValue(rol.getNombre());
				}

			});

			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathServeletDescarga;
	}

	
	public String DownloadXlxsCrearUsuarios(String pathCrearUsuarioPlantillas, String pathPlantillaUserCrearUsuario,
			Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}
		try {
			FileInputStream file = new FileInputStream(pathCrearUsuarioPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap()
					.get(MessagesBundleConstants.entidadUsuario);

			for (Parametrica tabArchivo : tabsArchivo) {
				crearEncabezado(workbook, tabArchivo, entidadUsuario);
				
				List<Parametrica> parametricaTDoctos = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_DE_IDENTIFICACION.getValue());
				List<Parametrica> parametricaTAsociacion = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_ASOCIACION.getValue());
				List<Parametrica> parametricaTGenero = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.GENERO.getValue());
				XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);
				IntStream.range(0, Math.max(Math.max(parametricaTDoctos.size(), parametricaTAsociacion.size()),
						parametricaTGenero.size())).forEach(index -> {
							XSSFRow fila = hoja.getRow(index + 1);
							if (fila == null) {
								fila = hoja.createRow(index + 1);
							}

							if (index < parametricaTDoctos.size()) {
								Parametrica parametrica = parametricaTDoctos.get(index);
								Cell celda = fila.createCell(1);
								celda.setCellValue(parametrica.getValorParametro());
							}

							if (index < parametricaTAsociacion.size()) {
								Parametrica parametrica = parametricaTAsociacion.get(index);
								Cell celda = fila.createCell(3);
								celda.setCellValue(parametrica.getValorParametro());
							}

							if (index < parametricaTGenero.size()) {
								Parametrica parametrica = parametricaTGenero.get(index);
								Cell celda = fila.createCell(5);
								celda.setCellValue(parametrica.getValorParametro());
							}
						});

				file.close();
			}
			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUserCrearUsuario));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			logger.error("Error al descargar archivo Excel", e);
		}
		return pathServeletDescarga; // Se devuelve un valor predeterminado, puedes modificarlo según sea necesario.
	}

	public String DownloadXlxsCrearPlanta(String pathContratosPlantillas, String pathContratosPlantillasUser,
	        Parametrica proceso) {
	    String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
	            + ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
	            + FileUtil.XLSX;
	    List<Parametrica> tabsArchivo = ComunicacionServiciosSis
	            .getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
	    if (tabsArchivo.isEmpty()) {
	        tabsArchivo = new ArrayList<Parametrica>();
	        tabsArchivo.add(proceso);
	    }
	    try {
	        FileInputStream file = new FileInputStream(pathContratosPlantillas);
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
	        EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap()
	                .get(MessagesBundleConstants.entidadUsuario);

	        // Creación de objetos para manipular entidades
	        DependenciaEntidadExt depEntidad = new DependenciaEntidadExt();
	        depEntidad.setFlgActivo((short) 1);
	        depEntidad.setCodEntidad(entidadUsuario.getId());

	        NomenclaturaExt nomenclatura = new NomenclaturaExt();
	        nomenclatura.setCodEntidad(new BigDecimal(entidadUsuario.getId()));
	        nomenclatura.setFlgActivo((short) 1);
	        nomenclatura.setFlgDefinitivo((short) 1);

	        NomenclaturaDenominacionExt objEntidad = new NomenclaturaDenominacionExt();
	        objEntidad.setCodEntidad(entidadUsuario.getId());

	        DenominacionExt objDenominacion = new DenominacionExt();
	        objDenominacion.setCodEntidad(entidadUsuario.getId());

	        DenominacionExt buscador = new DenominacionExt(); 
            buscador.setCodEntidad(entidadUsuario.getId());
	        
	        for (Parametrica tabArchivo : tabsArchivo) {
	            crearEncabezado(workbook, tabArchivo, entidadUsuario);

	            List<Parametrica> parametricaClasePlanta = ComunicacionServiciosSis
	                    .getParametricaActivaPorIdPadre(TipoParametro.CLASE_PLANTA.getValue());

	            List<Parametrica> parametricaClasificacionPlanta = ComunicacionServiciosSis
	                    .getParametricaActivaPorIdPadre(TipoParametro.CLASIFICACION_PLANTA.getValue());

	            List<Parametrica> parametricaTipoNorma = ComunicacionServiciosSis
	                    .getParametricaActivaPorIdPadre(TipoParametro.TIPO_NORMA.getValue());
	            
	            List<NomenclaturaExt> parametricaCodigoNomenclatura = ComunicacionServiciosEnt
	            		.getNomenclaturaFiltro(nomenclatura);

	            List<NomenclaturaExt> parametricaSistemaCarrera = ComunicacionServiciosEnt
	                    .getNomenclaturaFiltro(nomenclatura);
	            
	            List<NomenclaturaDenominacionExt> parametricaNivel = ComunicacionServiciosEnt
	                    .getNivelJerarquicoXEntidad(objEntidad);
	            
	            List<DenominacionExt> parametricaDenominacion = ComunicacionServiciosEnt
	                    .getDenominacionXEntidad(buscador);
	            
	            List<NomenclaturaDenominacionExt> parametricaCodigoDenominacion = ComunicacionServiciosEnt
	                    .getCodigoDenominacionXEntidad(objEntidad);
	            
	            List<NomenclaturaDenominacionExt> parametricaGrado = ComunicacionServiciosEnt
	                    .getGradoDenominacionXEntidad(objEntidad);
	            
	            List<Parametrica> ParametricaTipoPlanta = ComunicacionServiciosSis
	                    .getParametricaActivaPorIdPadre(TipoParametro.TIPO_PLANTA.getValue());
	            
	            List<Parametrica> parametricaNaturalezaEmpleo = ComunicacionServiciosSis
	                    .getParametricaActivaPorIdPadre(TipoParametro.NATURALEZA_EMPLEO.getValue());
	            
	            List<DependenciaEntidadExt> ParametricaDependencia = ComunicacionServiciosVin
	            		.getDependenciaEntidadFilter(depEntidad);
	           


	            XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);

	            int maxSize = Math.max(parametricaClasePlanta.size(), parametricaClasificacionPlanta.size());
	            maxSize = Math.max(maxSize, parametricaTipoNorma.size());
	            maxSize = Math.max(maxSize, parametricaCodigoNomenclatura.size());
	            maxSize = Math.max(maxSize, parametricaSistemaCarrera.size());
	            maxSize = Math.max(maxSize, parametricaNivel.size());
	            maxSize = Math.max(maxSize, parametricaDenominacion.size());
	            maxSize = Math.max(maxSize, parametricaCodigoDenominacion.size());
	            maxSize = Math.max(maxSize, parametricaGrado.size());
	            maxSize = Math.max(maxSize, ParametricaTipoPlanta.size());
	            maxSize = Math.max(maxSize, parametricaNaturalezaEmpleo.size());
	            maxSize = Math.max(maxSize, ParametricaDependencia.size());


	            for (int i = 0; i < maxSize; i++) {
	                XSSFRow fila = hoja.createRow(i + 1);

	                // Columna 1: Clase planta
	                if (i < parametricaClasePlanta.size()) {
	                    Parametrica parametrica = parametricaClasePlanta.get(i);
	                    Cell celda = fila.createCell(1);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 2: Clasificación de la Planta
	                if (i < parametricaClasificacionPlanta.size()) {
	                    Parametrica parametrica = parametricaClasificacionPlanta.get(i);
	                    Cell celda = fila.createCell(3);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 3: Tipo Norma
	                if (i < parametricaTipoNorma.size()) {
	                    Parametrica parametrica = parametricaTipoNorma.get(i);
	                    Cell celda = fila.createCell(5);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 4: Codigo Nomenclatura
	                if (i < parametricaCodigoNomenclatura.size()) {
	                    NomenclaturaExt nomenclaturaExt = parametricaCodigoNomenclatura.get(i);
	                    Cell celda = fila.createCell(7);
	                    celda.setCellValue(nomenclaturaExt.getCodNomenclatura());

	                }

	                // Columna 5: Sistema de Carrera
	                if (i < parametricaSistemaCarrera.size()) {
	                    NomenclaturaExt nomenclaturaExt = parametricaSistemaCarrera.get(i);
	                    Cell celda = fila.createCell(9);
	                    celda.setCellValue(nomenclaturaExt.getNombreSistemaCarrera());
	                }

	                // Columna 6: NIVEL JERARQUICO
	                if (i < parametricaNivel.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaNivel.get(i);
	                    
	                    Cell celda = fila.createCell(11);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getNivelCargo());
	                }

	             
	                // Columna 7: Denominaciones
	                if (i < parametricaDenominacion.size()) {
	                    DenominacionExt denominacionExt = parametricaDenominacion.get(i);
	                    Cell celda = fila.createCell(13);
	                    celda.setCellValue(denominacionExt.getNombreDenominacion());
	                }

	                // Columna 8: CODIGO DENOMINACION
	                if (i < parametricaCodigoDenominacion.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaCodigoDenominacion.get(i);
	                    Cell celda = fila.createCell(15);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getCodigoCargo());
	                }

	                // Columna 9: GRADO CARGO
	                if (i < parametricaGrado.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaGrado.get(i);
	                    Cell celda = fila.createCell(17);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getGradoCargo());
	                }

	                // Columna 10: Tipo Planta
	                if (i < ParametricaTipoPlanta.size()) {
	                    Parametrica parametrica = ParametricaTipoPlanta.get(i);
	                    Cell celda = fila.createCell(19);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 11: Naturaleza del Empleo
	                if (i < parametricaNaturalezaEmpleo.size()) {
	                    Parametrica parametrica = parametricaNaturalezaEmpleo.get(i);
	                    Cell celda = fila.createCell(21);
	                    celda.setCellValue(parametrica.getValorParametro());
	                }

	                // Columna 12: Dependencias
	                if (i < ParametricaDependencia.size()) {
	                    DependenciaEntidadExt dependencia = ParametricaDependencia.get(i);
	                    Cell celda = fila.createCell(23);
	                    celda.setCellValue(dependencia.getNombreDependencia());
	                }
	                
	                //Columna 13: Codigo Nomenclatura y Sistema Carrera
	                if (i < parametricaCodigoNomenclatura.size()) {
	                    NomenclaturaExt nomenclaturaExt = parametricaCodigoNomenclatura.get(i);
	                    Cell celda = fila.createCell(28);
	                    celda.setCellValue(nomenclaturaExt.getCodNomenclatura());

	                }

	                if (i < parametricaSistemaCarrera.size()) {
	                    NomenclaturaExt nomenclaturaExt = parametricaSistemaCarrera.get(i);
	                    Cell celda = fila.createCell(29);
	                    celda.setCellValue(nomenclaturaExt.getNombreSistemaCarrera());
	                }
	                //Columna 14: Nivel y Denominación
	                if (i < parametricaDenominacion.size()) {
	                    DenominacionExt dependencia = parametricaDenominacion.get(i);
	                    Cell celda = fila.createCell(31);
	                    celda.setCellValue(dependencia.getNivelCargo());
	                }
	                if (i < parametricaDenominacion.size()) {
	                    DenominacionExt dependencia = parametricaDenominacion.get(i);
	                    Cell celda = fila.createCell(32);
	                    celda.setCellValue(dependencia.getNombreDenominacion());
	                }
	                
	                //Columna 15:Denominación y Codigo Denominación
	                if (i < parametricaCodigoDenominacion.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaCodigoDenominacion.get(i);
	                    Cell celda = fila.createCell(34);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getNombreCargo());
	                }
	                if (i < parametricaCodigoDenominacion.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaCodigoDenominacion.get(i);
	                    Cell celda = fila.createCell(35);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getCodigoCargo());
	                }
	                
	                //Columna 16: Codigo Cargo y Grado Cargo
	                if (i < parametricaGrado.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaGrado.get(i);
	                    Cell celda = fila.createCell(37);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getCodigoCargo());
	                }
	                if (i < parametricaGrado.size()) {
	                    NomenclaturaDenominacionExt nomenclaturaDenominacionExt = parametricaGrado.get(i);
	                    Cell celda = fila.createCell(38);
	                    celda.setCellValue(nomenclaturaDenominacionExt.getGradoCargo());
	                }
	                
	                
	                
	            }

	        }

	        

	        file.close();
	        
	        FileOutputStream outFile = new FileOutputStream(new File(pathContratosPlantillasUser));
	        workbook.write(outFile);
	        outFile.close();
	    } catch (Exception e) {
	        logger.error("Error al descargar archivo Excel", e);
	    }
	    return pathServeletDescarga; 
	}


	public String DownloadXlxsContratos(String pathContratosPlantillas, String pathContratosPlantillasUser,
			Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}
		try {
			FileInputStream file = new FileInputStream(pathContratosPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap()
					.get(MessagesBundleConstants.entidadUsuario);

			DependenciaEntidadExt depEntidad = new DependenciaEntidadExt();
			depEntidad.setFlgActivo((short) 1);
			depEntidad.setCodEntidad(entidadUsuario.getId());

			Municipio municipio = new Municipio();
			municipio.setFlgActivo((short) 1);

			for (Parametrica tabArchivo : tabsArchivo) {
				crearEncabezado(workbook, tabArchivo, entidadUsuario);

				List<Parametrica> parametricaTDoctos = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_DE_IDENTIFICACION.getValue());
				//
				// List<SelectItem> parametricaTDoctos =
				// WebUtils.getIParametricasActivas(TipoParametro.TIPOS_DE_IDENTIFICACION);

				List<DependenciaEntidadExt> lstDependenciasEntidad = ComunicacionServiciosVin
						.getDependenciaEntidadFilter(depEntidad);
				// id
				List<Departamento> listDepartamentos = ComunicacionServiciosSis.getDepartamentos(169);

				List<MunicipioExt> listMunicipios = ComunicacionServiciosSis.getMunByDep(municipio);

				List<Parametrica> parametricaTMonedaContrato = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPO_MONEDA.getValue());

				List<Parametrica> parametricaTMonedaHonorarios = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPO_MONEDA.getValue());

				List<Parametrica> parametricaTFuenteFinanciacion = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPO_FUENTE_FINANCIACION.getValue());

				List<Parametrica> parametricaTAdminRecursos = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPO_ADMON_RECUROS_CONTRATO.getValue());

				XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);

				int maxSize1 = Math.max(parametricaTDoctos.size(), lstDependenciasEntidad.size());
				int maxSize2 = Math.max(listDepartamentos.size(), parametricaTMonedaContrato.size());
				int maxSize3 = Math.max(parametricaTMonedaHonorarios.size(), parametricaTFuenteFinanciacion.size());
				int maxSize4 = Math.max(listMunicipios.size(), parametricaTAdminRecursos.size());

				int maxSize = Math.max(Math.max(maxSize1, maxSize2), Math.max(maxSize3, maxSize4));

				IntStream.range(0, maxSize).forEach(index -> {
					XSSFRow fila = hoja.getRow(index + 1);
					if (fila == null) {
						fila = hoja.createRow(index + 1);
					}

					if (index < parametricaTDoctos.size()) {
						Parametrica parametrica = parametricaTDoctos.get(index);
						Cell celda = fila.createCell(1);
						celda.setCellValue(parametrica.getValorParametro());
					}

					if (index < listDepartamentos.size()) {
						Departamento departamento = listDepartamentos.get(index);
						Cell celda = fila.createCell(3);
						celda.setCellValue(departamento.getNombreDepartamento());
					}

					if (index < listMunicipios.size()) {
						MunicipioExt muni = listMunicipios.get(index);
						Cell celda = fila.createCell(5);
						celda.setCellValue(muni.getNombreMunicipio());
					}

					if (index < parametricaTMonedaContrato.size()) {
						Parametrica parametrica = parametricaTMonedaContrato.get(index);
						Cell celda = fila.createCell(7);
						celda.setCellValue(parametrica.getValorParametro());
					}

					if (index < parametricaTMonedaHonorarios.size()) {
						Parametrica parametrica = parametricaTMonedaHonorarios.get(index);
						Cell celda = fila.createCell(9);
						celda.setCellValue(parametrica.getValorParametro());
					}

					if (index < parametricaTFuenteFinanciacion.size()) {
						Parametrica parametrica = parametricaTFuenteFinanciacion.get(index);
						Cell celda = fila.createCell(11);
						celda.setCellValue(parametrica.getValorParametro());
					}

					if (index < parametricaTAdminRecursos.size()) {
						Parametrica parametrica = parametricaTAdminRecursos.get(index);
						Cell celda = fila.createCell(13);
						celda.setCellValue(parametrica.getValorParametro());
					}

					if (index < lstDependenciasEntidad.size()) {
						DependenciaEntidadExt dependencia = lstDependenciasEntidad.get(index);
						Cell celda = fila.createCell(15);
						celda.setCellValue(dependencia.getNombreDependencia());
					}

					if (index < listMunicipios.size()) {
						MunicipioExt muni = listMunicipios.get(index);
						Cell celda = fila.createCell(19);
						celda.setCellValue(muni.getNombreMunicipio());
					}

					if (index < listMunicipios.size()) {
						MunicipioExt muni = listMunicipios.get(index);
						Cell celda = fila.createCell(20);
						celda.setCellValue(muni.getNombreDepartamento());
					}

				});

				file.close();
			}
			FileOutputStream outFile = new FileOutputStream(new File(pathContratosPlantillasUser));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			logger.error("Error al descargar archivo Excel", e);
		}
		return pathServeletDescarga; // Se devuelve un valor predeterminado, puedes modificarlo según sea necesario.
	}

	/*
	 * Metodo Generico Para descargar el resto de plantillas que no persisten en BD,
	 * para llenar las listas.
	 */
	public String archivoDownloadXlxs(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {

		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}
		try {
			FileInputStream file = new FileInputStream(pathPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap()
					.get(MessagesBundleConstants.entidadUsuario);
			for (Parametrica tabArchivo : tabsArchivo) {
				crearEncabezado(workbook, tabArchivo, entidadUsuario);
				file.close();
			}
			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathServeletDescarga;
	}

	/*
	 * Metodo para Retornar Ruta Descarga Plantilla CM_ACTIVAR_USUARIOS con la
	 * Persistencia a datos obteniendo la lista de Tipos Documento y Tipos de
	 * Asociacion.
	 */
	public String DownloadXlxsActivarUsers(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {
		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}
		try {
			FileInputStream file = new FileInputStream(pathPlantillas);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");

			for (Parametrica tabArchivo : tabsArchivo) {
				crearEncabezado(workbook, tabArchivo, entidadUsuario);
				List<Parametrica> parametricaTDoctos = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_DE_IDENTIFICACION.getValue());
				List<Parametrica> parametricaTAsociacion = ComunicacionServiciosSis
						.getParametricaActivaPorIdPadre(TipoParametro.TIPOS_ASOCIACION.getValue());
				final XSSFSheet hoja = workbook.getSheet(MessagesBundleConstants.PARAMETRICAS);
				XSSFRow filaTitulo = hoja.getRow(0);
				if (filaTitulo == null) {
					filaTitulo = hoja.createRow(0);
				}

				IntStream.range(0, Math.max(parametricaTDoctos.size(), parametricaTAsociacion.size()))
						.forEach(index -> {
							if (index < parametricaTDoctos.size()) {
								Parametrica parametrica = parametricaTDoctos.get(index);
								XSSFRow fila = hoja.getRow(index + 1);
								if (fila == null) {
									fila = hoja.createRow(index + 1);
								}
								Cell celda = fila.createCell(1);
								celda.setCellValue(parametrica.getValorParametro());
							}

							if (index < parametricaTAsociacion.size()) {
								Parametrica parametrica = parametricaTAsociacion.get(index);
								XSSFRow fila2 = hoja.getRow(index + 1);
								if (fila2 == null) {
									fila2 = hoja.createRow(index + 1);
								}
								Cell celda2 = fila2.createCell(3);
								celda2.setCellValue(parametrica.getValorParametro());
							}
						});
				file.close();
			}
			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pathServeletDescarga;
	}


	/*
	 * Metodo para Obtener la informacion de los encabezados que se muestran en
	 * comun en todas las plantillas de Cargue Masivo
	 */
	public void crearEncabezado(XSSFWorkbook workbook, Parametrica tabArchivo, EntidadDTO entidadUsuario) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		int rowFecha = 1, rowCodDentidad = 2, rowNomDentidad = 3;
		int colFecha = 1, colCodDentidad = 1, colNomDentidad = 1;
		XSSFSheet sheet = workbook.getSheet(tabArchivo.getValorParametro());
		Cell cell = null;

		// Setea Fecha en Plantilla Excel
		XSSFRow sheetrowFecha = sheet.getRow(rowFecha);
		if (sheetrowFecha == null) {
			sheetrowFecha = sheet.createRow(rowFecha);
		}
		cell = sheetrowFecha.getCell(colFecha);
		if (cell == null) {
			cell = sheetrowFecha.createCell(colFecha);
		}
		cell.setCellValue(dateFormat.format(date));

		XSSFRow sheetrowCodEn = sheet.getRow(rowCodDentidad);
		if (sheetrowCodEn == null) {
			sheetrowCodEn = sheet.createRow(rowCodDentidad);
		}
		cell = sheetrowCodEn.getCell(colCodDentidad);
		if (cell == null) {
			cell = sheetrowCodEn.createCell(colCodDentidad);
		}
		cell.setCellValue(entidadUsuario.getCodigoSigep());

		XSSFRow sheetrowNomEn = sheet.getRow(rowNomDentidad);
		if (sheetrowNomEn == null) {
			sheetrowNomEn = sheet.createRow(rowNomDentidad);
		}
		cell = sheetrowNomEn.getCell(colNomDentidad);
		if (cell == null) {
			cell = sheetrowNomEn.createCell(colNomDentidad);
		}
		cell.setCellValue(entidadUsuario.getNombreEntidad());

	}

	/*
	 * Metodo para Obtener los Paths de la descarga de las plantillas.
	 */
	public List<String> getPaths(Parametrica paramProceso) {
		String pathCargasMasivas = System.getProperty("CONFIG_PATH") + ConfigurationBundleConstants.getPathRutaLog();
		String pathPlantillas = pathCargasMasivas + "plantillas/" + paramProceso.getValorParametro() + FileUtil.XLSX;
		logger.info("CM DOWN PLANTILLA " + pathPlantillas);
		String pathPlantillaUser = pathCargasMasivas + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLSX;
		List<String> paths = new ArrayList<>();
		paths.add(pathPlantillas);
		paths.add(pathPlantillaUser);
		return paths;
	}

	/*
	 * Metodo Util para Abrir y Cerrar el Canal de las rutas de Descarga de las
	 * plantillas.
	 */
	public void generateFile(String pathPlantillaDownload, String pathPlantillaUser) {
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("window.open('" + pathPlantillaDownload + "', '_blank');");
			File fichero = new File(pathPlantillaUser);
			fichero.setExecutable(true, false);
			fichero.setReadable(true, false);
			fichero.setWritable(true, false);
			logger.info("Success void downloadFormato()", context);
		} catch (Exception e) {
			logger.error("void downloadFormato()", e);
		}

	}

	/**
	 * Método encargado de recibir el evento de la carga de archivos anexos del
	 * proceso masivo.
	 * 
	 * @param e
	 *            - Evento que contiene el archivo cargado.
	 * @throws IOException
	 */
	public void cargaMasivaAnexoUpload(FileUploadEvent e) throws IOException {
		buscarEntidad();
		byte[] bytes = null;
		if (tipoProceso == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_TIPO_PROCESO_CARGUE_MASIVO_REQUERIDO);
			return;
		}
		archivoCargaMasivaAnexo = e.getFile();
		bytes = archivoCargaMasivaAnexo.getContents();
		cargaMasivaAnexo = new File(archivoCargaMasivaAnexo.getFileName());
		FileUtils.writeByteArrayToFile(cargaMasivaAnexo, bytes);

		// verificamos si en el nombre del archivo no existen caracteres especiales
		String nombreArchivo = new String(archivoCargaMasivaAnexo.getFileName().getBytes("ISO-8859-1"), "UTF-8");
		this.nombreArchivoCargaAnexos = nombreArchivo;

	}

	/**
	 * Método encargado de recibir el evento de la carga de archivo del proceso
	 * masivo.
	 * 
	 * @param e
	 *            - Evento que contiene el archivo cargado.
	 * @throws IOException
	 */
	public void cargaMasivaUpload(FileUploadEvent e) throws IOException {
		buscarEntidad();
		Parametrica padreProcesosCarguesMasivos = ComunicacionServiciosSis
				.getParametricaIntetos("PROCESOS DE CARGUES MASIVOS");

		byte[] bytes = null;
		if (tipoProceso == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_TIPO_PROCESO_CARGUE_MASIVO_REQUERIDO);
			return;
		}
		archivoCargaMasiva = e.getFile();
		bytes = archivoCargaMasiva.getContents();
		cargaMasivaXls = new File(archivoCargaMasiva.getFileName());
		cargaMasivaXls.setExecutable(true, false);
		cargaMasivaXls.setReadable(true, false);
		cargaMasivaXls.setWritable(true, false);
		FileUtils.writeByteArrayToFile(cargaMasivaXls, bytes);

		// verificamos si en el nombre del archivo no existen caracteres especiales
		String nombreArchivo = new String(archivoCargaMasiva.getFileName().getBytes("ISO-8859-1"), "UTF-8");
		this.nombreArchivoCargaActivacionUsuarios = nombreArchivo;

	}

	public void procesarArchivo() throws IOException {
		buscarEntidad();
		// verificamos si en el nombre del archivo no existen caracteres especiales
		String nombreArchivo = new String(archivoCargaMasiva.getFileName().getBytes("ISO-8859-1"), "UTF-8");
		String replacedString = nombreArchivo.replace(".", "");
		if (checkString(replacedString) == false) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_CARGA_MASIVA_CARACTER_ESPECIAL);
			return;
		}

		if (tipoProceso == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_TIPO_PROCESO_CARGUE_MASIVO_REQUERIDO);
			return;
		}
		if (archivoCargaMasiva == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_CARGA_MASIVA_PLANTILLA_OBLIGATORIA);
			return;
		}

		if (archivoCargaMasivaAnexo == null && tipoProceso == TipoParametro.CM_HOJA_DE_VIDA.getValue()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ARCHIVO_ZIP);
			return;
		}

		if (archivoCargaMasivaAnexo != null && tipoProceso == TipoParametro.CM_HOJA_DE_VIDA.getValue()) {

			String nombreArchivoAnexo = new String(archivoCargaMasivaAnexo.getFileName().getBytes("ISO-8859-1"),
					"UTF-8");
			String replacedStringAnexo = nombreArchivoAnexo.replace(".", "");

			if (checkString(replacedStringAnexo) == false) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_CARGA_MASIVA_CARACTER_ESPECIAL);
				return;
			}

			if (!validarExistePDFJPG(cargaMasivaAnexo)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ARCHIVO_ZIP);
				return;
			}

			if (!this.validarArchivoZip(cargaMasivaAnexo)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_ERROR_ARCHIVO_ZIP);
				return;
			}
		}
		Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoProceso));
		XSSFWorkbook excelWB = null;
		if (archivoCargaMasiva != null && paramProceso != null) {
			FileInputStream fis = new FileInputStream(cargaMasivaXls);
			excelWB = new XSSFWorkbook(fis);
			String mssgErrorEncabezado = validarProceso(excelWB, paramProceso);
			if (mssgErrorEncabezado != null) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						mssgErrorEncabezado);
				return;
			}

		}

		String relativePath = ConfigurationBundleConstants.getPathRutaLog();
		String pathPlantillaLogs = System.getProperty("CONFIG_PATH") + relativePath;
		Persona infoUsuario = ComunicacionServiciosHV.getPersonaPorId(getUsuarioSesion().getCodPersona());
		String token = ComunicacionServiciosHV.getTokenService();
		CargueMasivoThread hiloCargueMasivo = new CargueMasivoThread(cargaMasivaXls, cargaMasivaAnexo, tipoProceso,
				getUsuarioSesion(), getEntidadUsuario(), infoUsuario, pathPlantillaLogs, relativePath, token, excelWB);
		hiloCargueMasivo.start();
		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.DLG_CARGUE_MASIVO_INICIADO);

		limpiarFormulario();

	}

	private void limpiarFormulario() {
		this.tipoProceso = null;
		this.archivoCargaMasiva = null;
		this.archivoCargaMasivaAnexo = null;
		this.nombreArchivoCargaAnexos = "";
		this.nombreArchivoCargaActivacionUsuarios = "";
	}

	/**
	 * Retorna el mensaje de error con respecto a la validaciÃ³n del archivo de
	 * excel para la carga masiva seleccionado
	 * 
	 * @param proceso
	 * @return
	 * @return
	 * @return Mensaje de error mensajesErrores
	 */
	public String validarProceso(XSSFWorkbook archivo, Parametrica proceso) {

		String mensajesErrores = null;
		// HSSFCell cellValueNomProceso, cellValueProceso;
		String nomProcesoForm = proceso.getValorParametro().toString();

		String codigoSigep = getEntidadUsuario().getCodigoSigep();// variable de sesión del codigo de la entidad
																	// seleccionada

		String nombreSigep = getEntidadUsuario().getNombreEntidad();// variable de sesión del codigo de la entidad
		// seleccionada

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String fechaSystema = dateFormat.format(date);

		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());

		// Si no trae ningún resultado es porque el proceso no tiene varias tabs.
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}

		for (Parametrica tabArchivo : tabsArchivo) {
			// Trae la hoja de Excel de acuerdo al parÃ¡metro
			String prueba = tabArchivo.getValorParametro();
			XSSFSheet hojaPlantilla = archivo.getSheet(tabArchivo.getValorParametro());// Selecciona la hoja del archivo
			// de excel

			if (hojaPlantilla != null) {

				XSSFRow fila = hojaPlantilla.getRow(0);// leemos la primera de fila de cada una de las hojas

				String cellValueProceso = String.valueOf(fila.getCell(1));// convierto cellValue a String

				XSSFRow fila2 = hojaPlantilla.getRow(1);// leemos la primera de fila de cada una de las hojas

				XSSFCell celda = fila2.getCell(1); // leemos la fecha del proces del archivo de excel
				celda.setCellType(HSSFCell.CELL_TYPE_STRING); // la convertimos a string
				String cellValueFecha = celda.getStringCellValue();

				XSSFRow fila3 = hojaPlantilla.getRow(2);// leemos la primera de fila de cada una de las hojas

				String cellValueCodEntidad = String.valueOf(fila3.getCell(1));// convierto cellValue a String

				XSSFRow fila4 = hojaPlantilla.getRow(3);// leemos la primera de fila de cada una de las hojas

				String cellValueNomEntidad = String.valueOf(fila4.getCell(1));// convierto cellValue a String

				if (!cellValueProceso.equals(tabArchivo.getValorParametro())) {
					mensajesErrores = MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_NOM_PROCESO_HOJA_EXCEL, getLocale())
							+ " " + cellValueProceso + " "
							+ MessagesBundleConstants
									.getStringMessagesBundle(MessagesBundleConstants.MSG_DEBERA_SER_IGUAL, getLocale())
							+ " " + tabArchivo.getValorParametro();
					break;
				}

				if (!cellValueFecha.equals(fechaSystema)) {
					mensajesErrores = MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_FECHA_INGRESADA_EXCEL, getLocale()) + " " + cellValueProceso
							+ " es: " + cellValueFecha + " " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_DEBERA_SER_IGUAL, getLocale())
							+ " " + fechaSystema;
					break;
				}

				if (!cellValueCodEntidad.equals(codigoSigep)) {
					mensajesErrores = MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CODIGO_ENTIDAD, getLocale()) + " "
							+ cellValueCodEntidad + " "
							+ MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_INGRESADO_HOJA_EXCEL, getLocale())
							+ " " + cellValueProceso + " " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_DEBERA_SER_IGUAL, getLocale())
							+ " " + codigoSigep;
					break;

				}

				if (!cellValueNomEntidad.equals(nombreSigep)) {

					mensajesErrores = MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_NOMBRE_ENTIDAD, getLocale()) + " "
							+ cellValueNomEntidad + " "
							+ MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_INGRESADO_HOJA_EXCEL, getLocale())
							+ " " + cellValueProceso + " " + MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_DEBERA_SER_IGUAL, getLocale())
							+ " " + nombreSigep;
					break;

				}

				/*
				 * if (!cellValueProceso.equals(nomProcesoForm) ||
				 * !cellValueCodEntidad.equals(codigoSigep)) { mensajesErrores =
				 * "Se encontraron errores en el encabezado de la hoja " +
				 * tabArchivo.getValorParametro(); break; }
				 */
			} else {
				mensajesErrores = MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CARGA_MASIVA_NOMBRE_PROCESO_ERRONEO,
								getLocale())
						.replace("%PROCESO", tabArchivo.getValorParametro());
				break;
			}
		}

		return mensajesErrores;
	}

	/**
	 * Método para validar el archivo zip que contenga solo archivos PDF o JPG
	 * 
	 * @param File
	 *            - archivoZip con la información
	 * @return boolean
	 * 
	 */
	private boolean validarArchivoZip(File archivoZip) {
		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));
			ZipEntry entrada = zis.getNextEntry();
			while (entrada != null) {
				String nombreArchivo = entrada.getName();
				if (!entrada.isDirectory()) {
					String rutaZip = nombreArchivo.substring(0, nombreArchivo.indexOf("/") + 1);
					String archivoAdjunto = nombreArchivo.substring(rutaZip.length(), nombreArchivo.length());
					if ((archivoAdjunto.toLowerCase().endsWith(".pdf")
							&& nombreArchivo.toLowerCase().indexOf("fotousuario/") != -1)) {
						return false;
					}
					if ((archivoAdjunto.toLowerCase().endsWith(".jpg")
							&& !(nombreArchivo.toLowerCase().indexOf("fotousuario/") != -1))) {
						return false;
					}
					if (!archivoAdjunto.toLowerCase().endsWith(".pdf")
							&& !archivoAdjunto.toLowerCase().endsWith(".jpg"))
						return false;
				}
				entrada = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * Método para validar el archivo zip contenga al menos un archivo pdf o jpg en
	 * su interior
	 * 
	 * @param File
	 *            - archivoZip con la información
	 * @return boolean
	 * 
	 */
	private boolean validarExistePDFJPG(File archivoZip) {

		try {
			int contadorArchivos = 0;
			ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));

			ZipEntry entrada = zis.getNextEntry();
			while (entrada != null) {
				String nombreArchivo = entrada.getName();
				if (!entrada.isDirectory()) {
					String rutaZip = nombreArchivo.substring(0, nombreArchivo.indexOf("/") + 1);
					String archivoPDF = nombreArchivo.substring(rutaZip.length(), nombreArchivo.length());

					if (archivoPDF.toLowerCase().endsWith(".pdf") || archivoPDF.toLowerCase().endsWith(".jpg")) {
						contadorArchivos++;
					}
				}

				entrada = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			if (contadorArchivos > 0) {
				return true;
			} else {
				return false;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * Retorna la ruta desde donde se debe descargar la plantilla para la carga
	 * masiva seleccionado
	 * 
	 * @param proceso
	 * @return
	 * @return
	 * @return Mensaje de error mensajesErrores
	 */

	/**
	 * Retorna la ruta desde donde se debe descargar la plantilla para la carga
	 * masiva seleccionado
	 * 
	 * @param proceso
	 * @return
	 * @return
	 * @return Mensaje de error mensajesErrores
	 */
	// public String archivoDownload(HSSFWorkbook archivo, Parametrica proceso,
	// String Ubicacion, String rutaArchivoTemp) {
	public String archivoDownload(String pathPlantillas, String pathPlantillaUser, Parametrica proceso) {

		// Path de donde descargaremos el archivo de usuario
		/*
		 * String pathDescarga = ConfigurationBundleConstants
		 * .getRutaArchivoApp(ConfigurationBundleConstants.CNS_FORMATOS_CARGUE) +
		 * getUsuarioSesion().getNombreUsuario() + FileUtil.XLS;
		 */

		String pathServeletDescarga = ConfigurationBundleConstants.getUrlApp() + "DescargarExcel?path="
				+ ConfigurationBundleConstants.getPathRutaLog() + "temporal/" + getUsuarioSesion().getNombreUsuario()
				+ FileUtil.XLS;

		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());

		// Si no trae ningÃºn resultado es porque el proceso no tiene varias tabs.
		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		int rowFecha = 1, rowCodDentidad = 2, rowNomDentidad = 3;
		int colFecha = 1, colCodDentidad = 1, colNomDentidad = 1;

		try {

			FileInputStream file = new FileInputStream(pathPlantillas);

			HSSFWorkbook workbook = new HSSFWorkbook(file);

			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");

			for (Parametrica tabArchivo : tabsArchivo) {
				// Trae la hoja de Excel de acuerdo al parÃ¡metro

				// Abrimos las hojas de la plantilla segÃºn la parametrica
				HSSFSheet sheet = workbook.getSheet(tabArchivo.getValorParametro());
				Cell cell = null;

				// Retrieve the row and check for null
				HSSFRow sheetrowFecha = sheet.getRow(rowFecha);
				if (sheetrowFecha == null) {
					sheetrowFecha = sheet.createRow(rowFecha);
				}
				// Update the value of cell
				cell = sheetrowFecha.getCell(colFecha);
				if (cell == null) {
					cell = sheetrowFecha.createCell(colFecha);
				}
				cell.setCellValue(dateFormat.format(date));

				// Retrieve the row and check for null
				HSSFRow sheetrowCodEn = sheet.getRow(rowCodDentidad);
				if (sheetrowCodEn == null) {
					sheetrowCodEn = sheet.createRow(rowCodDentidad);
				}
				// Update the value of cell
				cell = sheetrowCodEn.getCell(colCodDentidad);
				if (cell == null) {
					cell = sheetrowCodEn.createCell(colCodDentidad);
				}
				cell.setCellValue(entidadUsuario.getCodigoSigep());

				// Retrieve the row and check for null
				HSSFRow sheetrowNomEn = sheet.getRow(rowNomDentidad);
				if (sheetrowNomEn == null) {
					sheetrowNomEn = sheet.createRow(rowNomDentidad);
				}
				// Update the value of cell
				cell = sheetrowNomEn.getCell(colNomDentidad);
				if (cell == null) {
					cell = sheetrowNomEn.createCell(colNomDentidad);
				}
				cell.setCellValue(entidadUsuario.getNombreEntidad());
				file.close();
			}
			FileOutputStream outFile = new FileOutputStream(new File(pathPlantillaUser));
			workbook.write(outFile);
			outFile.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pathServeletDescarga;
	}

	/**
	 * Verifica si la cadena contiene caracteres especiales o espacios si cumple
	 * devuelve true de lo contrario falÃ±se
	 * 
	 * @return respuesta
	 */

	public static boolean checkString(String str) {
		boolean respuesta = false;
		if ((str).matches("([a-z]|[A-Z]|[0-9]|\\s)+")) {
			respuesta = true;
		}
		return respuesta;
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
	}

	public Long getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(Long tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public StreamedContent getFormato() {
		return formato;
	}

	public void setFormato(StreamedContent formato) {
		this.formato = formato;
	}

	public UploadedFile getArchivoCargaMasiva() {
		return archivoCargaMasiva;
	}

	public void setArchivoCargaMasiva(UploadedFile archivoCargaMasiva) {
		this.archivoCargaMasiva = archivoCargaMasiva;
	}

	public String getNombreArchivoCargaActivacionUsuarios() {
		return nombreArchivoCargaActivacionUsuarios;
	}

	public void setNombreArchivoCargaActivacionUsuarios(String nombreArchivoCargaActivacionUsuarios) {
		this.nombreArchivoCargaActivacionUsuarios = nombreArchivoCargaActivacionUsuarios;
	}

	/**
	 * @return el habilitadoArchivoAnexo
	 */
	public Boolean getHabilitadoArchivoAnexo() {
		return habilitadoArchivoAnexo;
	}

	/**
	 * @param habilitadoArchivoAnexo
	 *            el habilitadoArchivoAnexo a establecer
	 */
	public void setHabilitadoArchivoAnexo(Boolean habilitadoArchivoAnexo) {
		this.habilitadoArchivoAnexo = habilitadoArchivoAnexo;
	}

	/**
	 * @return el archivoCargaMasivaAnexo
	 */
	public UploadedFile getArchivoCargaMasivaAnexo() {
		return archivoCargaMasivaAnexo;
	}

	/**
	 * @param archivoCargaMasivaAnexo
	 *            el archivoCargaMasivaAnexo a establecer
	 */
	public void setArchivoCargaMasivaAnexo(UploadedFile archivoCargaMasivaAnexo) {
		this.archivoCargaMasivaAnexo = archivoCargaMasivaAnexo;
	}

	/**
	 * @return el cargaMasivaXls
	 */
	public File getCargaMasivaXls() {
		return cargaMasivaXls;
	}

	/**
	 * @param cargaMasivaXls
	 *            el cargaMasivaXls a establecer
	 */
	public void setCargaMasivaXls(File cargaMasivaXls) {
		this.cargaMasivaXls = cargaMasivaXls;
	}

	/**
	 * @return el cargaMasivaAnexo
	 */
	public File getCargaMasivaAnexo() {
		return cargaMasivaAnexo;
	}

	/**
	 * @param cargaMasivaAnexo
	 *            el cargaMasivaAnexo a establecer
	 */
	public void setCargaMasivaAnexo(File cargaMasivaAnexo) {
		this.cargaMasivaAnexo = cargaMasivaAnexo;
	}

	/**
	 * @return el nombreArchivoCargaAnexos
	 */
	public String getNombreArchivoCargaAnexos() {
		return nombreArchivoCargaAnexos;
	}

	/**
	 * @param nombreArchivoCargaAnexos
	 *            el nombreArchivoCargaAnexos a establecer
	 */
	public void setNombreArchivoCargaAnexos(String nombreArchivoCargaAnexos) {
		this.nombreArchivoCargaAnexos = nombreArchivoCargaAnexos;
	}

	public void habilitarAnexos() {
		this.nombreArchivoCargaActivacionUsuarios = "";
		this.nombreArchivoCargaAnexos = "";

		if (this.tipoProceso == 1625) {
			this.habilitadoArchivoAnexo = true;
		} else {
			this.habilitadoArchivoAnexo = false;
		}
	}

	public String getStrMsgMensajes() {
		return strMsgMensajes;
	}

	public void setStrMsgMensajes(String strMsgMensajes) {
		this.strMsgMensajes = strMsgMensajes;
	}

	public Long getLlTamArchivos() {
		return llTamArchivos;
	}

	public void setLlTamArchivos(Long llTamArchivos) {
		this.llTamArchivos = llTamArchivos;
	}

	public String getStrMsgTamanoArchivo() {
		return strMsgTamanoArchivo;
	}

	public void setStrMsgTamanoArchivo(String strMsgTamanoArchivo) {
		this.strMsgTamanoArchivo = strMsgTamanoArchivo;
	}

	public void procesarArchivoConfirmar() {
		if (tipoProceso == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_TIPO_PROCESO_CARGUE_MASIVO_REQUERIDO);
			return;
		}
		if (archivoCargaMasiva == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_CARGA_MASIVA_PLANTILLA_OBLIGATORIA);
			return;
		}
		if (archivoCargaMasivaAnexo == null && tipoProceso == TipoParametro.CM_HOJA_DE_VIDA.getValue()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_ARCHIVO_ZIP);
			return;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
		Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoProceso));
		strMsgMensajes = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_CARGA_MASIVA_PROCESO_MSG, getLocale())
				.replace("%PROCESO%", paramProceso.getDescripcion())
				.replace("%ENTIDAD%", entidadUsuario.getNombreEntidad());

		RequestContext.getCurrentInstance().execute("PF('dlgConfirmarProcesar').show();");

	}

	public void procesarArchivoCancelar() {
		limpiarFormulario();
	}

}