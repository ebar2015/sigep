package co.gov.dafp.sigep2.sistema;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.deledago.CarguesMasivosDelegate;
import co.gov.dafp.sigep2.entities.CmConfiguracion;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entities.ProcesosCargaMasiva;
import co.gov.dafp.sigep2.entities.SequenciasSigep;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.enums.TipoAccionEnum;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class CargueMasivoThread extends Thread {

	private File archivoCargaMasiva = null;
	private File archivoCargaMasivaAnexo = null;
	private Long tipoProceso;
	private UsuarioDTO usuarioSesion;
	private EntidadDTO entidad;
	private String rutaLogs;
	private String token;
	private Persona infoUsuario;
	private String pathPlantillaLogs;
	private XSSFWorkbook excelWB;

	public static final String VALOR_PARAMETRO_NO_ENCONTRADO_POR_DEFECTO = "0";
	public static final Short ESTADO_ACTIVO = 1;
	public static final String NOMBRE_SEQUENCIA_TABLA = "PROCESO_CARGA_MASIVA";
	protected static Logger logger = Logger.getInstance(CargueMasivoThread.class);
	protected static int tamanoBytes = 1024 * 10;
	protected static String  NUMERO_DOCUMENTO = "000746453";

	/**
	 * Constructor del Hilo para la carga masiva
	 * 
	 * @param archivo
	 * @param archivoZip
	 * @param tipoProceso
	 * @param usuario
	 * @param entidad
	 * @param pathPlantillaLogs
	 */
	public CargueMasivoThread(File archivo, File archivoZip, Long tipoProceso, UsuarioDTO usuario, EntidadDTO entidad,
			Persona infoUsuario, String pathPlantillaLogs, String rutaLogs, String token) {
		this.archivoCargaMasiva = archivo;
		this.archivoCargaMasivaAnexo = archivoZip;
		this.tipoProceso = tipoProceso;
		this.usuarioSesion = usuario;
		this.entidad = entidad;
		this.infoUsuario = infoUsuario;
		this.pathPlantillaLogs = pathPlantillaLogs;
		this.rutaLogs = rutaLogs;
		this.token = token;
		this.excelWB = null;
	}
	
	public CargueMasivoThread(File archivo, File archivoZip, Long tipoProceso, UsuarioDTO usuario, EntidadDTO entidad,
			Persona infoUsuario, String pathPlantillaLogs, String rutaLogs, String token, XSSFWorkbook excelWB) {
		this.archivoCargaMasiva = archivo;
		this.archivoCargaMasivaAnexo = archivoZip;
		this.tipoProceso = tipoProceso;
		this.usuarioSesion = usuario;
		this.entidad = entidad;
		this.infoUsuario = infoUsuario;
		this.pathPlantillaLogs = pathPlantillaLogs;
		this.rutaLogs = rutaLogs;
		this.token = token;
		this.excelWB = excelWB;
	}

	/**
	 * Ejecución del hilo para la carga masiva
	 */
	@Override
	public void run() {
		FileInputStream fis = null;
		//XSSFWorkbook excelWB = null;
		Parametrica paramProceso = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(tipoProceso));

		try {
			if (archivoCargaMasiva != null && paramProceso != null) {
				
				if (excelWB == null) {
					fis = new FileInputStream(archivoCargaMasiva);
					excelWB = new XSSFWorkbook(fis);
				}
				procesarArchivo(excelWB, paramProceso);
				

			} else {
				List<String> email = new ArrayList<String>();
				email.add(infoUsuario.getCorreoElectronico());
				CarguesMasivosDelegate.emailResultadoCargueMasivo(email, null,
						"Ocurrió un error al intentar procesar el archivo. Por favor intentar nuevamente.",
						paramProceso.getValorParametro());
			}
		} catch (Exception ioe) {
			try {
				List<String> email = new ArrayList<String>();
				email.add(infoUsuario.getCorreoElectronico());
				CarguesMasivosDelegate.emailResultadoCargueMasivo(email, null,
						"El sistema no pudo iniciar el proceso de cargue, ocurrió un error al intentar procesar el archivo. Por favor intentar la carga nuevamente.",
						paramProceso.getValorParametro());
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}
		} finally {
			if (archivoCargaMasiva != null) {
				if (!archivoCargaMasiva.delete()) {
					logger.error("Error borrando el archivo", "Error borrando el archivo");
				}
			}
		}
	}

	/**
	 * Método para procesar el archivo de excel, de acuerdo a la paramétrica
	 * 
	 * @param archivo - Excel con la información
	 * @param proceso - Proceso de carga masiva seleccionado
	 */
	private void procesarArchivo(XSSFWorkbook archivo, Parametrica proceso) {
		// Se realiza la consulta de todos los tabs del proceso CM_HOJA_DE_VIDA
		ProcesosCargaMasiva encabezado 			= null;
		SequenciasSigep contadorProcesoCarga 	= null;
		boolean encabezadoGuardado 				= false;
		String StringCellValue 					= "";

		XSSFCell cellValue, nroIdent;

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		List<Parametrica> tabsArchivo = ComunicacionServiciosSis
				.getParametricaPorIdPadre(proceso.getCodTablaParametrica().longValue());

		// Si no trae ningún resultado es porque el proceso no tiene varias tabs.

		if (tabsArchivo.isEmpty()) {
			tabsArchivo = new ArrayList<Parametrica>();
			tabsArchivo.add(proceso);
		}

		for (Parametrica tabArchivo : tabsArchivo) {
			// Trae la hoja de Excel de acuerdo al parámetro
			XSSFSheet hojaPlantilla = archivo.getSheet(tabArchivo.getValorParametro());// Selecciona la hoja del archivo
																						// de excel
			int semaforoFila = 0;
			int contadorFilas = 0;

			List<CmConfiguracion> columnasArchivo = obtenerConfiguracionProcesoMasivo(tabArchivo);
			List<String> datos = new ArrayList<>();

			if (hojaPlantilla != null) {

				for (int j = 0; j <= hojaPlantilla.getLastRowNum(); j++) {
					XSSFRow fila = hojaPlantilla.getRow(j);

					if (fila != null) {

						cellValue = fila.getCell(0);

						nroIdent = fila.getCell(1);

						try {
							StringCellValue = String.valueOf(cellValue);// convierto cellValue a String
							if ("CM_CREAR_ENTIDAD".equals(tabArchivo.getValorParametro())) {
								if (StringCellValue.equals("* Denominación (Nombre de Entidad)")) {
									semaforoFila = 1;
								}
							}else if("CM_CREAR_NOMENCLATURA_SALARIAL".equals(tabArchivo.getValorParametro())) {
								if (StringCellValue.equals("* Tipo de Norma")) {
									semaforoFila = 1;
								}
							}else if("CM_CREAR_ESTRUCTURA".equals(tabArchivo.getValorParametro())) {
								if (StringCellValue.equals("* Jerarquía")) {
									semaforoFila = 1;
								}
							}else if("CM_CREAR_GRUPO".equals(tabArchivo.getValorParametro())) {
								if (StringCellValue.equals("Cod_dependencia")) {
									semaforoFila = 1;
								}
							}else if("CM_CREAR_PLANTA".equals(tabArchivo.getValorParametro())) {
								if (StringCellValue.equals("* Clase planta")) {
									semaforoFila = 1;
								}
							}
							else {
								if (StringCellValue.equals("* Tipo de identificación")) {
									semaforoFila = 1;
								}
							}
						} catch (Exception e) {
							
						}

						if (semaforoFila >= 1)
							contadorFilas++;

						/**
						 *	Para todas las cargas masivas preguntamos si * Tipo de identificación o * Número de identificación son
						 *	igual a null.
						 * 
						 *  Consideraciones especiales:
						 *  
						 *   - Para el proceso de Crear entidad se validan si los campos: * Entidad ó * Sigla
						 *   	son igual a null.
						 * 	 - Para el proceso de Nomenclatura y Escala Salarial se validan si los campos: * Tipo de Norma ó * Fecha de la Norma
						 * 		son igual a null. 
						 */
						
						if (contadorFilas >= 2 && ((StringCellValue.equals("null") || "".equals(StringCellValue))
								)){
							break;
						}

						if (fila != null && contadorFilas >= 2) {
							// Se almacena cada fila en formato JSON
							String jsonFila = procesarColumnasPorFila(fila, columnasArchivo);
							datos.add(jsonFila);
						}

					}

				}

			}

			if (!datos.isEmpty()) {
				// Bandera para controlar que el encabezado solo se ingrese una vez, para todas
				// los tabs del archivo
				if (!encabezadoGuardado) {
					contadorProcesoCarga = ComunicacionServiciosSis.getSequenciasSigep(NOMBRE_SEQUENCIA_TABLA);
					if (contadorProcesoCarga != null) {
						encabezado = guardarEncabezadoCargaMasiva(contadorProcesoCarga);
						if (encabezado.isError()) {
							try {
								List<String> email = new ArrayList<String>();
								email.add(infoUsuario.getCorreoElectronico());
								CarguesMasivosDelegate.emailResultadoCargueMasivo(email, null,
										"Ocurrió un problema al consultar la secuencia del encabezado. Favor verificar con su administrador.",
										proceso.getValorParametro());
							} catch (SIGEP2SistemaException e) {
								e.printStackTrace();
							}
							return;
						}
						copiarArchivoCarga(archivo, proceso, pathPlantillaLogs, contadorProcesoCarga);// Copia la
																										// plantilla de
																										// cargue subida
																										// por el
																										// cliente
						encabezadoGuardado = true;
					} else {
						try {
							List<String> email = new ArrayList<String>();
							email.add(infoUsuario.getCorreoElectronico());
							CarguesMasivosDelegate.emailResultadoCargueMasivo(email, null,
									"Ocurrió un problema al consultar la secuencia del encabezado. Favor verificar con su administrador.",
									proceso.getValorParametro());
						} catch (SIGEP2SistemaException e) {
							e.printStackTrace();
						}

						return;
					}
				}

				for (String registro : datos) {
					JsonParser parser = new JsonParser();
					JsonObject registroJson = parser.parse(registro).getAsJsonObject();

					// Se agregan los datos de auditoria

					registroJson.addProperty("codProcesoCargaMasiva", contadorProcesoCarga.getSecuencia());
					registroJson.addProperty("audFechaActualizacion", format.format(new Date()));
					registroJson.addProperty("audAccion", TipoAccionEnum.INSERT.getIdAccion());
					registroJson.addProperty("audCodRol", usuarioSesion.getCodRol());
					registroJson.addProperty("audCodUsuario", usuarioSesion.getId());
					
					try {
						// Se invoca el servicio dinámicamente de acuerdo al Valor de la paramétrica.
						// (El nombre del método será: "set" + tabArchivo.getValorParametro())
						invocarServicio(ComunicacionServiciosSis.class.getName(),
								"set" + tabArchivo.getValorParametro(), registroJson.toString());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| SecurityException | NoSuchMethodException | IllegalArgumentException
							| InvocationTargetException e) {
						logger.error("void procesarArchivoHV(HSSFWorkbook archivo, Parametrica proceso)", e);
					}
				}
			}

		}
	}

	/**
	 * Método encargado de copiar el archivo de carga del usuario en la carpeta de
	 * logs masivo.
	 * 
	 */

	public void copiarArchivoCarga(XSSFWorkbook archivo, Parametrica proceso, String pathPlantillaLogs,
			SequenciasSigep contadorProcesoCarga) {

		String rutaGuardar = rutaLogs + "historico/" + contadorProcesoCarga.getSecuencia() + FileUtil.XLSX;
		String rutaArchivoLogs = pathPlantillaLogs + "historico/" + contadorProcesoCarga.getSecuencia() + FileUtil.XLSX;

		try {
			FileOutputStream outFile = new FileOutputStream(new File(rutaArchivoLogs));
			archivo.write(outFile);
			outFile.close();

			String rutaAmazon = enviarArchivoAmazon(rutaArchivoLogs, rutaGuardar, token, WebUtils.CNS_RUTA_LOGS_CM, true ,
									WebUtils.TP_LOGS_CM, "");
			
			ProcesosCargaMasiva procesoCm = ComunicacionServiciosSis.getProcesosCargaMasiva(contadorProcesoCarga.getSecuencia());
			procesoCm.setUrlArchivoLogs(rutaAmazon);
			procesoCm.setAudAccion(63);
			ComunicacionServiciosSis.setProcesosCargaMasiva(procesoCm);
		} catch (FileNotFoundException e) {
			logger.error("Error copiarArchivoCarga [Error]", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Error copiarArchivoCarga [Error]", e.getMessage());
			e.printStackTrace();
		}catch(OutOfMemoryError e) {
      		logger.log().error("Error copiarArchivoCarga [Error] Memory", e.getMessage());
      		e.printStackTrace();
      	 }
	}

	/**
	 * Retorna la lista de la configuración de las columnas de acuerdo al proceso de
	 * carga masiva seleccionado
	 * 
	 * @param proceso
	 * @return Listado de columnas con sus respectivas propiedades para el proceso
	 *         de carga masiva
	 */
	private List<CmConfiguracion> obtenerConfiguracionProcesoMasivo(Parametrica proceso) {
		List<CmConfiguracion> configuracion = null;

		if (proceso != null) {
			CmConfiguracion cmConfiguracion = new CmConfiguracion();
			cmConfiguracion.setCodProcesoCargue((short) proceso.getCodTablaParametrica().longValue());
			cmConfiguracion.setFlgActivo(ESTADO_ACTIVO);
			configuracion = ComunicacionServiciosSis.getCmConfiguracion(cmConfiguracion);
		}
		return configuracion;
	}

	/**
	 * Se encarga de almacenar el encabezado para el proceso de carga masiva
	 * seleccionado
	 * 
	 * @param contadorProcesoCarga
	 */
	private ProcesosCargaMasiva guardarEncabezadoCargaMasiva(SequenciasSigep contadorProcesoCarga) {
		ProcesosCargaMasiva encabezado = new ProcesosCargaMasiva();
		encabezado.setCodProcesoCargaMasiva(contadorProcesoCarga.getSecuencia());
		encabezado.setCodEntidad((short) entidad.getId());
		encabezado.setFechaInicio(new Date());
		encabezado.setTipoCargue((short) tipoProceso.longValue());
		encabezado.setAudFechaActualizacion(new Date());
		encabezado.setAudAccion(TipoAccionEnum.INSERT.getIdAccion());
		encabezado.setAudCodRol((int)usuarioSesion.getCodRol());
		encabezado.setAudCodUsuario(usuarioSesion.getId());
		return ComunicacionServiciosSis.setProcesosCargaMasiva(encabezado);

	}
	/*
	 * @Author: Nestor Riasco
	 * 
	 * Metodo para validar que la ruta del archivo anexo que viene en la plantilla,
	 * se encuentre en el archivo zip de los anexos
	 * 
	 * @param contadorProcesoCarga,
	 * 
	 * @return boolean
	 */

	private boolean validarRutaArchivoZip(String rutaArchivoZip, File archivoZip) {

		try {
			ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));

			ZipEntry entrada = zis.getNextEntry();
			while (entrada != null) {
				String nombreArchivo = entrada.getName().toLowerCase();
				if (!entrada.isDirectory()) {
					if (nombreArchivo.indexOf(rutaArchivoZip.toLowerCase()) != -1) {
						return true;
					}
				}

				entrada = zis.getNextEntry();
			}
			zis.closeEntry();
			zis.close();
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return false;
		}

	}

	/**
	 * Se encarga de procesar una fila del archivo, de acuerdo a la configuración de
	 * las columnas del proceso masivo seleccionado.
	 * 
	 * @param fila            - Fila del archivo
	 * @param columnasArchivo - Configuración de las columnas parametrizadas en BD
	 * @return - Una cadena en formato JSON con la informacion de la fila.
	 */
	private String procesarColumnasPorFila(XSSFRow fila, List<CmConfiguracion> columnasArchivo) {
		JsonObject json = new JsonObject();
		String strMensajeTrunk = "";
		boolean lbExcede;
		String valorCelda;
		for (CmConfiguracion columna : columnasArchivo) {
			XSSFCell celda = fila.getCell(columna.getPosicionCampo().intValue());
			if (celda != null) {
				
				try {
					
					if (DateUtil.isCellDateFormatted(celda)) {
						//String dateFmt = celda.getCellStyle().getDataFormatString();//Detectamos el formato
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");//inicializamos el formato
						valorCelda = format.format(celda.getDateCellValue());//formateamos el campo
						valorCelda = valorCelda.replace(";", "");//12/00/2018;@
						valorCelda = valorCelda.replace("@", "");//12/00/2018;@
						valorCelda = valorCelda.replace("/", "");//12/00/2018;@
					}
					else {
						celda.setCellType(Cell.CELL_TYPE_STRING);
						valorCelda = celda.getStringCellValue().trim();						
					}
					
				} catch (Exception e) {
					celda.setCellType(Cell.CELL_TYPE_STRING);
					valorCelda = celda.getStringCellValue().trim();
				}
				lbExcede = false;
				try {
					lbExcede = validaTammano(valorCelda.toString(), columna.getLongitud().intValue());
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}

				if (lbExcede) {
					strMensajeTrunk = strMensajeTrunk + " Campo: " + columna.getDescripcionCampo()
							+ " excede el tamaño predeterminado, verifique la información, valor ingresado: "
							+ valorCelda + ", ";
					// valorCelda = valorCelda.substring(0, columna.getLongitud()-1);
					valorCelda = "0";
				}
				
				
				// SE VALIDA QUE EL ARCHIVO ZIP NO SE NULO
				if (archivoCargaMasivaAnexo != null) {
					// SE VALIDA QUE EL CAMPO LEIDO SEA ANEXO
					if (columna.getObligatorio() == 1) {
						if (!this.validarRutaArchivoZip(valorCelda, archivoCargaMasivaAnexo)) {
							strMensajeTrunk = strMensajeTrunk + " Campo: " + columna.getDescripcionCampo()
									+ " La ruta y archivo ingresado no se encuentra en el archivo .zip de anexos, verifique la información, valor ingresado: "
									+ valorCelda + ", ";
							valorCelda = "";

						} else {
							valorCelda = procesarArchivoZip(archivoCargaMasivaAnexo, valorCelda);
							
						}
					}
				}
				/**
				 * tipoCampo: Numerico = 0 ó String = 1
				 */
				try {
					if (columna.getTipoCampo() == 0) {
						String valueStr;
						try {
							if (!"".equals(valorCelda)) {
								Double valor = Double.parseDouble(valorCelda);
								valueStr = valorCelda;
							} else {
								valueStr = null;
							}
						} catch (Exception e) {
							strMensajeTrunk = strMensajeTrunk + " Campo: " + columna.getDescripcionCampo()
									+ " el valor ingresado no es de tipo numérico: " + valorCelda + ", ";
							valueStr = "0";
						}
						json.addProperty(columna.getNombreCampo(), valueStr);
					} else if (columna.getTipoCampo() == 1) {
						json.addProperty(columna.getNombreCampo(), valorCelda);
					}

					

				} catch (NullPointerException e) {
					logger.error("String procesarColumnasPorFila(HSSFRow fila, List<CmConfiguracion> columnasArchivo)",
							e.getMessage());
					if (columna.getTipoCampo() == 0) {
						json.addProperty(columna.getNombreCampo(), 0);
					} else if (columna.getTipoCampo() == 1) {
						json.addProperty(columna.getNombreCampo(), "");
					}
				} catch (NumberFormatException e) {
					logger.error("String procesarColumnasPorFila(HSSFRow fila, List<CmConfiguracion> columnasArchivo)",
							e.getMessage());
					json.addProperty(columna.getNombreCampo(), 0);
				}
			}
		}
		if (!"".equals(strMensajeTrunk)) {
			json.addProperty("resultado", strMensajeTrunk);
		}
		return json.toString();
	}

	/**
	 * Método para procesar el archivo de excel, de acuerdo a la paramétrica
	 * 
	 * @param archivo - Excel con la información
	 * @param proceso - Proceso de carga masiva seleccionado
	 */
	private String procesarArchivoZip(File archivoZip, String rutaArchivo) {
		String rutaAmazon = "";
		String ruta = rutaArchivo.substring(0, rutaArchivo.indexOf("/") + 1);
		String archivo = rutaArchivo.substring(ruta.length(), rutaArchivo.length());
		String rutaGuardar = null;
		String filePath = null;
		FileOutputStream fout = null;
		BufferedOutputStream bufout = null;
		byte[] buffer = new byte[tamanoBytes];
		int read = 0;

		try {

			ZipInputStream zis = new ZipInputStream(new FileInputStream(archivoZip));
			ZipEntry entrada = zis.getNextEntry();
			while (entrada != null) {
				String nombreArchivo = entrada.getName();
				if (!entrada.isDirectory()) {
					String rutaZip = nombreArchivo.substring(0, nombreArchivo.indexOf("/") + 1);

					if (ruta.toLowerCase().contentEquals(rutaZip.toLowerCase())) {

						String archivoPDF = rutaArchivo.substring(ruta.length(), rutaArchivo.length());

						Boolean ifPDF = (archivoPDF.toLowerCase().endsWith(".pdf")
								&& !rutaZip.toLowerCase().contentEquals("fotousuario/"));

						Boolean ifJPG = (archivoPDF.toLowerCase().endsWith(".jpg")
								&& rutaZip.toLowerCase().contentEquals("fotousuario/"));

						if (ifPDF || ifJPG) {

							if (archivo.contentEquals(archivoPDF)) {

								switch (rutaZip.toLowerCase()) {
								case "fotousuario/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_FOTO_USUARIO) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_FOTO_USUARIO, false, 
											WebUtils.TP_FOTO_USUARIO, NUMERO_DOCUMENTO);
									break;
								case "documento/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_DOCUMENTO) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_DOCUMENTO, false,
											WebUtils.TP_DOCUMENTO_IDENTIFICACION, NUMERO_DOCUMENTO);
									break;
								case "libretamilitar/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_LIBRETA_MILITAR) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_LIBRETA_MILITAR, false,
											WebUtils.TP_LIBRETA_MILITAR, NUMERO_DOCUMENTO);
									break;
								case "idiomasoporte/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_IDIOMA_SOPORTE) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_IDIOMA_SOPORTE, false,
											WebUtils.TP_IDIOMA_SOPORTE, NUMERO_DOCUMENTO);
									break;
								case "experienciadocente/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_DOC_EXP_DOCENTE) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_DOC_EXP_DOCENTE, false,
											WebUtils.TP_DOC_EXP_DOCENTE, NUMERO_DOCUMENTO);
									break;
								case "experiencialaboral/":
									rutaGuardar = ConfigurationBundleConstants
											.getString(ConfigurationBundleConstants.CNS_RUTA_DOC_EXP_LABORAL) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_DOC_EXP_LABORAL, false,
											WebUtils.TP_DOC_EXP_LABORAL, NUMERO_DOCUMENTO);
									break;
								case "tarjetaprofesional/":
									rutaGuardar = ConfigurationBundleConstants.getString(
											ConfigurationBundleConstants.CNS_RUTA_TARJETA_PROFESIONAL) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_TARJETA_PROFESIONAL, false,
											WebUtils.TP_TARJETA_PROFESIONAL, NUMERO_DOCUMENTO);
									break;
								default:
									rutaGuardar = ConfigurationBundleConstants.getString(
											ConfigurationBundleConstants.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE) + archivo;
									filePath = ConfigurationBundleConstants.getRutaArchivo(rutaGuardar);
									fout = new FileOutputStream(new File(filePath));
									bufout = new BufferedOutputStream(fout);
									read = 0;
									while ((read = zis.read(buffer)) != -1) {
										bufout.write(buffer, 0, read);
									}
									bufout.close();
									fout.close();
									rutaAmazon = enviarArchivoAmazon(filePath, rutaGuardar, token, WebUtils.CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE, false,
											WebUtils.TP_CONOCIMIENTO_SOPORTE, NUMERO_DOCUMENTO);
									break;
								}

							}

						} else {
							logger.error(
									"String procesarColumnasPorFila(HSSFRow fila, List<CmConfiguracion> columnasArchivo)",
									"ERROR CARGANDO ANEXOS");
						}

					}
				}

				entrada = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

		} catch (IOException ex) {

			logger.error("String procesarColumnasPorFila(HSSFRow fila, List<CmConfiguracion> columnasArchivo)",
					"ERROR CARGANDO ANEXOS");
			ex.printStackTrace();
		}
		
		return rutaAmazon;

	}

	
	/**
	 * Método creado para la inserción de los documentos al AmazonS3
	 * @param filePath
	 * @param token
	 * @param ruta
	 * @param rutaGuardar
	 * @return
	 */
	private String enviarArchivoAmazon(String filePath, String rutaGuardar, String token, String ruta, boolean guardarConNombreArchivo, 
										String tipologia, String numerodocumento) {
		String rutaAmazon = "";
		
		try {
			String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
	                token, ruta, tipologia, numerodocumento);
			Gson gson = new Gson();
			ErrorMensajes resp = gson.fromJson(response, ErrorMensajes.class);
			if(!resp.isError()) {
				if(guardarConNombreArchivo) {	
					rutaAmazon = "/" + ruta + "/" + resp.getNombreArchivo();
				}else {
					rutaAmazon = resp.getUrlArchivo();
				}
					
			}else{
				rutaAmazon = rutaGuardar;
			}
		
		} catch (Exception e) {
			logger.error("Error enviarArchivoAmazon [Error]", e.getMessage());
			return rutaAmazon;
		}
		
			return rutaAmazon;
	}

	/**
	 * Método que se encarga de mapear el valor de la celda, con las paramétricas
	 * del sistema
	 * 
	 * @param idParametrica - Id de la paramétrica
	 * @param valorCelda    valor a mapear
	 * @return Retorna el ID asociado a la paramétrica
	 */
	private String retornarValorParametrica(Long idParametrica, String valorCelda) {
		Map<String, Parametrica> maestro = obtenerMapaRegistrosParametrica(idParametrica);
		Parametrica valorMaestro = maestro.get(valorCelda);
		if (valorMaestro != null) {
			valorCelda = valorMaestro.getCodTablaParametrica().toString();
		} else {
			valorCelda = VALOR_PARAMETRO_NO_ENCONTRADO_POR_DEFECTO;
		}
		return valorCelda;
	}

	/**
	 * Se realiza una consulta de los datos de las paramétricas tipo tabla y se
	 * cargan en memoria para que no sea necesario generar una consulta en Base de
	 * Datos por cada registro del archivo
	 * 
	 * @param codPadreParametrica
	 * @return
	 */
	private Map<String, Parametrica> obtenerMapaRegistrosParametrica(Long codPadreParametrica) {
		Map<String, Parametrica> mapaParametricas = new HashMap<String, Parametrica>();
		List<Parametrica> registros = ComunicacionServiciosSis.getParametricaPorIdPadre(codPadreParametrica);
		for (Parametrica param : registros) {
			mapaParametricas.put(param.getNombreParametro(), param);
		}
		return mapaParametricas;
	}

	/***
	 * Permite cargar un método en una clase indicada de forma dinámica
	 *
	 * @param a
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static void invocarServicio(String clase, String metodoLlamado, String a)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SecurityException,
			NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Object tempClass = Class.forName(clase).newInstance();
		Class claseCargada = tempClass.getClass();
		// Firma del metodo.
		Class[] argumentos = new Class[1];
		argumentos[0] = String.class;
		// Busqueda del metodo a ejecutar
		Method metodo = claseCargada.getDeclaredMethod(metodoLlamado, argumentos);
		// Ejecucion del método pasandole la clase de este y los parametros.
		metodo.invoke(tempClass, a);

	}

	public Persona getInfoUsuario() {
		return infoUsuario;
	}

	public void setInfoUsuario(Persona infoUsuario) {
		this.infoUsuario = infoUsuario;
	}

	private boolean validaTammano(String dato, int tamanno) throws UnsupportedEncodingException {
		int total = 0;
		for (int i = 0; i < dato.length(); i++) {
			char a = dato.charAt(i);
			String s = new String(new char[] { a });
			byte[] bytes = s.getBytes("UTF-8");
			int bit = bytes.length;
			if (bit > 1) {
				total += bit + 1;
			} else {
				total += bit;
			}
		}
		
		logger.info("Dato: " + dato + " ---- Total: " + total);
		
		if (total > tamanno)
			return true;
		return false;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRutaLogs() {
		return rutaLogs;
	}

	public void setRutaLogs(String rutaLogs) {
		this.rutaLogs = rutaLogs;
	}

}
