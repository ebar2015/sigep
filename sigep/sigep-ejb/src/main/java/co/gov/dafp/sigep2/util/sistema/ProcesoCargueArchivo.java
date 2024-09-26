package co.gov.dafp.sigep2.util.sistema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.NotSupportedException;
import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.ExcelToCSVConverter;
import co.gov.dafp.sigep2.entity.jpa.comun.ArchivoCargue;
import co.gov.dafp.sigep2.entity.jpa.comun.ArchivoCargueLog;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.factoria.ArchivoCargueFactoria;
import co.gov.dafp.sigep2.factoria.ArchivoCargueLogFactoria;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.factoria.ProcesoArchivoFactoria;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.StringUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;
import co.gov.dafp.sigep2.util.xml.Xml;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;
import co.gov.dafp.sigep2.util.xml.elemento.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.elemento.Columna;
import co.gov.dafp.sigep2.util.xml.elemento.CuentaCorreo;
import co.gov.dafp.sigep2.util.xml.elemento.Extension;
import co.gov.dafp.sigep2.util.xml.elemento.Registro;
import co.gov.dafp.sigep2.util.xml.elemento.SeparadorCsv;
import co.gov.dafp.sigep2.util.xml.elemento.TipoArchivo;
import co.gov.dafp.sigep2.util.xml.elemento.TipoCruceFecha;
import co.gov.dafp.sigep2.util.xml.elemento.TipoDato;
import co.gov.dafp.sigep2.util.xml.elemento.TipoRegistro;
import co.gov.dafp.sigep2.util.xml.elemento.TipoValidacion;
import co.gov.dafp.sigep2.util.xml.elemento.Validacion;

public abstract class ProcesoCargueArchivo implements Serializable {
	private static final long serialVersionUID = -1107034213145351573L;

	protected Logger logger = Logger.getInstance(ProcesoCargueArchivo.class);

	protected static final String no_cargado = "no cargado" + FileUtil.getFileSeparator();
	protected static final String sql = "sql" + FileUtil.getFileSeparator();

	private IEnvioCorreoLocal envioCorreo;
	protected ProcesoArchivoFactoria procesoArchivoFactoria;
	protected ArchivoCargueFactoria archivoCargueFactoria;
	protected ArchivoCargueLogFactoria archivoCargueLogFactoria;
	protected ParametricaFactoria parametricaFactoria;
	private Usuario usuarioArchivoCargue;
	private RolDTO rolCargueId;
	private boolean extensionOriginalExcel = false;
	private static final SeparadorCsvCaracter caracterExcelToCSV = SeparadorCsvCaracter.PUNTO_COMA;

	transient List<File> archivosCague;
	private String rutaArchivoEnCargue;
	private String rutaArchivoCargado;
	protected File archivoCargue;
	protected Date fechaCargue;
	protected Date fechaArchivo = null;

	protected String validacion;
	protected Long totalRegistros;

	protected StringBuilder sentencias;
	protected ProcesoArchivoDTO procesoArchivo;
	protected ArchivoCargue archivoCargado;
	transient Archivo plantillaArchivo;

	private List<ArchivoCargue> archivosCargueEjecutados;
	private List<ArchivoCargueLog> cargueLog;
	private List<ArchivoCargueLog> cargueLogValidacionInformacion;
	boolean impedirGuardadoBaseDatos = false;

	private long entidadId;

	transient List<String> adjuntos;

	protected Xml xml;

	private boolean tieneArchivosSalida = false;

	private static boolean alive = false;
	private static ProcesoArchivoDTO procesoArchivoE;

	private int longitudMaximaColumnas = ConfigurationBundleConstants
			.getInt(ConfigurationBundleConstants.CNS_TAMANIO_MAXIMO_COLUMNAS_VARCHAR_DDBB);

	private XSSFWorkbook workbook;

	protected ProcesoCargueArchivo() {
		super();
	}

	public void setProcesoArchivoFactoria(ProcesoArchivoFactoria procesoArchivoDao) {
		this.procesoArchivoFactoria = procesoArchivoDao;
	}

	public void setArchivoCargueDao(ArchivoCargueFactoria archivoCargueDao) {
		this.archivoCargueFactoria = archivoCargueDao;
	}

	public void setArchivoCargueLogDao(ArchivoCargueLogFactoria archivoCargueLogDao) {
		this.archivoCargueLogFactoria = archivoCargueLogDao;
	}

	public void setParametricaFactoria(ParametricaFactoria constanteDao) {
		this.parametricaFactoria = constanteDao;
	}

	public void setMailService(IEnvioCorreoLocal envioCorreo) {
		this.envioCorreo = envioCorreo;
	}

	public void setFechaCargue(Date fechaCargue) {
		this.fechaCargue = fechaCargue;
	}

	public List<File> getArchivosCague() {
		return archivosCague;
	}

	public void setArchivosCague(List<File> archivosCague) {
		this.archivosCague = archivosCague;
	}

	public void setUsuarioArchivoCargue(Usuario usuarioArchivoCargue) {
		this.usuarioArchivoCargue = usuarioArchivoCargue;
	}

	public void setRolCargueId(RolDTO rolCargueId) {
		this.rolCargueId = rolCargueId;
	}

	public void setProcesoArchivo(ProcesoArchivoDTO procesoArchivo) {
		this.procesoArchivo = procesoArchivo;
	}

	public Archivo getPlantillaArchivo() {
		return plantillaArchivo;
	}

	public void setPlantillaArchivo(Archivo plantillaArchivo) {
		this.plantillaArchivo = plantillaArchivo;
	}

	public ArchivoCargue getArchivoCargado() {
		return archivoCargado;
	}

	public Usuario getUsuarioArchivoCargue() {
		return usuarioArchivoCargue;
	}

	public static boolean isAlive() {
		return alive;
	}

	public static void setAlive(boolean alive) {
		if (!alive) {
			ProcesoCargueArchivo.setProcesoArchivoE(null);
		}
		Logger logger = Logger.getInstance(ProcesoCargueArchivo.class);
		logger.info("static void setAlive(boolean alive)", "Cambiando estado de bloqueo de proceso: " + alive);
		ProcesoCargueArchivo.alive = alive;
	}

	public static ProcesoArchivoDTO getProcesoArchivoE() {
		return procesoArchivoE;
	}

	public static void setProcesoArchivoE(ProcesoArchivoDTO procesoArchivoE) {
		ProcesoCargueArchivo.procesoArchivoE = procesoArchivoE;
	}

	/**
	 * Cargar la configuración para el archivo seleccionado
	 * 
	 * @throws SIGEP2NegocioException
	 *
	 */
	public void cargarConfiguracionXML() throws Exception {
		xml = Xml.getEstructura(Xml.getXml(this.procesoArchivo.getNombrePlantilla()));
		if (xml.getArchivo().size() == 1) {
			this.plantillaArchivo = xml.getArchivo().get(0);
		}
	}

	public final void lanzarCargue() throws Exception {
		String tituloLog = "final void lanzarCargue(String rutaArchivoEnCargue)";
		// Control de cargue en paralelo
		boolean habilitadoParaDormir = ConfigurationBundleConstants
				.getBoolean(ConfigurationBundleConstants.CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_DORMIR);
		if (habilitadoParaDormir) {
			long dormir = ConfigurationBundleConstants
					.getLong(ConfigurationBundleConstants.CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_TIEMPO_DORMIR);
			while (ProcesoCargueArchivo.isAlive()) {
				if (ProcesoCargueArchivo.getProcesoArchivoE() == null) {
					ProcesoCargueArchivo.setAlive(false);
					continue;
				}
				logger.info(tituloLog,
						"El proceso '" + procesoArchivo.getDescripcion() + "' ha entrado en espera por "
								+ (dormir / 1000) + " segundos. Ejecutando '"
								+ ProcesoCargueArchivo.getProcesoArchivoE().getDescripcion() + "'");
				Thread.sleep(dormir);
				habilitadoParaDormir = ConfigurationBundleConstants
						.getBoolean(ConfigurationBundleConstants.CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_DORMIR);
				if (!habilitadoParaDormir) {
					ProcesoCargueArchivo.setAlive(false);
				}
			}
		}
		ProcesoCargueArchivo.setAlive(true);
		ProcesoCargueArchivo.setProcesoArchivoE(procesoArchivo);
		// Fin control de cargue en paralelo

		try {
			archivosCargueEjecutados = new LinkedList<>();
			if (this.plantillaArchivo != null) {
				this.rutaArchivoEnCargue = ConfigurationBundleConstants
						.getRutaArchivo(this.plantillaArchivo.getRutaEntrada());
				this.rutaArchivoCargado = ConfigurationBundleConstants
						.getRutaArchivo(this.plantillaArchivo.getRutaSalida());
			}
			logger.log().info(tituloLog, "Inicio proceso de cargue de archivo");

			List<Archivo> archivos = getListaArchivosConfiguracion();
			impedirGuardadoBaseDatos = false;

			cargueLog = new LinkedList<>();
			for (File archivoCargueFile : archivosCague) {
				try {
					if (archivoCargueFactoria.findByNombreArchivoCargue(archivoCargueFile.getName()) != null) {
						logger.warn(tituloLog, "Archivo " + archivoCargueFile.getName()
								+ " ha sido cargado con anterioridad. Cargue ignorado.");
						break;
					}
					this.archivoCargue = archivoCargueFile;
					this.plantillaArchivo = null;
					if (!archivos.isEmpty()) {
						for (Archivo archivoPlantilla : archivos) {
							this.rutaArchivoEnCargue = ConfigurationBundleConstants
									.getRutaArchivo(archivoPlantilla.getRutaEntrada());
							this.rutaArchivoCargado = ConfigurationBundleConstants
									.getRutaArchivo(archivoPlantilla.getRutaSalida());
							logger.info(tituloLog, archivoCargueFile.getName());
							logger.info(tituloLog, archivoPlantilla.getMascara());
							if (this.nombreArchivoCorrecto(archivoPlantilla)) {
								this.plantillaArchivo = archivoPlantilla;
								break;
							}
						}
					}
					if (!archivos.isEmpty() && this.plantillaArchivo == null) {
						this.plantillaArchivo = archivos.get(0);
					}
					if (this.plantillaArchivo != null
							&& this.plantillaArchivo.getTipoArchivo().equals(TipoArchivo.ENTRADA)
							&& archivoCargueFile.exists()) {
						this.rutaArchivoEnCargue = ConfigurationBundleConstants
								.getRutaArchivo(this.plantillaArchivo.getRutaEntrada());
						this.rutaArchivoCargado = ConfigurationBundleConstants
								.getRutaArchivo(this.plantillaArchivo.getRutaSalida());
						String nombreArchivoCargue = this.archivoCargue.getName().replace(ConfigurationBundleConstants
								.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML), "");
						archivoCargado = new ArchivoCargue();
						archivoCargado.setFlgEstado(false);
						archivoCargado.setFechaCargue(DateUtils.getFechaSistema());
						archivoCargado.setNombreArchivoCargue(nombreArchivoCargue.toUpperCase());
						archivoCargado.setFechaArchivo(null);
						archivoCargado.setTotalRegistros(0l);
						archivoCargado.setCodProcesoCargue(this.procesoArchivo.getId());
						archivoCargado.setCodUsuarioCargue(usuarioArchivoCargue.getId());
						archivoCargado.setAudFechaActualizacion(DateUtils.getFechaSistema());
						archivoCargado.setAudCodUsuario(usuarioArchivoCargue.getId());
						archivoCargado.setAudCodRol(1l);
						archivoCargado.setAudAccion(1l);//CR TODO PQR
						this.archivoCargueFactoria.persist(archivoCargado, usuarioArchivoCargue);
						this.archivosCargueEjecutados.add(archivoCargado);

						totalRegistros = BigDecimal.ZERO.longValue();
						Validacion validacionInformacion = null;
						Validacion notificarExitoso = null;
						if (Extension.XLS.equals(this.plantillaArchivo.getExtension())) {
							this.archivoCargue = ExcelToCSVConverter.convertFromXls(archivoCargueFile,
									caracterExcelToCSV);
							extensionOriginalExcel = true;
						} else if (Extension.XLSX.equals(this.plantillaArchivo.getExtension())) {
							this.archivoCargue = ExcelToCSVConverter.convertFromXlsx(archivoCargueFile,
									caracterExcelToCSV);
							extensionOriginalExcel = true;
						} else {
							this.archivoCargue = archivoCargueFile;
						}
						boolean volcarArchivoDDBB = true;
						boolean generarSalidas = false;
						boolean resultadoValidacionOK = false;
						for (Validacion validacion : this.plantillaArchivo.getValidacion()) {
							if (validacion.isValidar()) {
								resultadoValidacionOK = false;
								if (TipoValidacion.NOMBRE.equals(validacion.getTipoValidacion())) {
									resultadoValidacionOK = this.nombreArchivoCorrecto(this.plantillaArchivo);
								} else if (TipoValidacion.ESTRUCTURA.equals(validacion.getTipoValidacion())) {
									resultadoValidacionOK = this.estructuraArchivoCorrecta(this.plantillaArchivo,
											validacion);
									generarSalidas = resultadoValidacionOK;
									if (!resultadoValidacionOK) {
										this.moverArchivoNoCargado(archivoCargueFile);
									}
								} else if (TipoValidacion.INFORMACION.equals(validacion.getTipoValidacion())) {
									if (validacion.isPersistido()) {
										validacionInformacion = validacion;
										resultadoValidacionOK = true;
									} else if (!this.impedirGuardadoBaseDatos) {
										resultadoValidacionOK = this.informacionArchivoCorrecta(this.plantillaArchivo);
										if (!resultadoValidacionOK && validacion.isTransaccional()) {
											impedirGuardadoBaseDatos = this.plantillaArchivo.isBloquearCargueLote();
											if (impedirGuardadoBaseDatos) {
												ArchivoCargue archivoCargado = archivoCargueFactoria
														.find(this.archivoCargado.getId());
												archivoCargado.setFlgEstado(Boolean.FALSE);
												this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
											}
										}
									}
								} else if (TipoValidacion.NOTIFICACION.equals(validacion.getTipoValidacion())) {
									this.enviarNotificacion(validacion, this.plantillaArchivo, archivoCargueFile, true);
									return;
								} else if (TipoValidacion.EXITOSO.equals(validacion.getTipoValidacion())) {
									notificarExitoso = validacion;
									resultadoValidacionOK = true;
								}
								if (!TipoValidacion.EXITOSO.equals(validacion.getTipoValidacion())) {
									if (!resultadoValidacionOK) {
										if (validacion.isTransaccional()
												|| TipoValidacion.ESTRUCTURA.equals(validacion.getTipoValidacion())) {
											volcarArchivoDDBB = false;
											impedirGuardadoBaseDatos = this.plantillaArchivo.isBloquearCargueLote();
										}
										if (validacion.getNotificacion().isNotificar()) {
											this.enviarNotificacion(validacion, this.plantillaArchivo,
													archivoCargueFile,
													!TipoValidacion.ESTRUCTURA.equals(validacion.getTipoValidacion()));
										}
									} else {
										logger.log().info(tituloLog, validacion.getTipoValidacion()
												+ " de archivo correcto(a) : " + this.archivoCargue.getName());
									}
								}
								if (impedirGuardadoBaseDatos) {
									this.registrarLog("Proceso de cargue interrumpido por inconcistencias en "
											+ archivoCargueFile.getName(), null, null, Boolean.TRUE);
								}
							}
						}
						// Iniciar preparacion para el volcado a DDBB
						if (volcarArchivoDDBB && !impedirGuardadoBaseDatos) {
							if (!this.volcadoArchivoDDBBCorrecto(this.archivoCargado.getId(), nombreArchivoCargue)) {
								impedirGuardadoBaseDatos = this.plantillaArchivo.isBloquearCargueLote();
								resultadoValidacionOK = true;
								ArchivoCargue archivoCargado = archivoCargueFactoria.find(this.archivoCargado.getId());
								archivoCargado.setTotalRegistros(totalRegistros);
								this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
								logger.log().error(tituloLog, "El volcado de información terminó con errores");
								continue;
							}

							ArchivoCargue archivoCargado = archivoCargueFactoria.find(this.archivoCargado.getId());
							archivoCargado.setFechaArchivo(fechaArchivo);
							archivoCargado.setTotalRegistros(totalRegistros);
							archivoCargado.setAudAccion(1l);//UP TODO PQR
							this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
							logger.log().info(tituloLog, "Volcado de información de archivo a base de datos correcta : "
									+ this.archivoCargue.getName());
						} else {
							ArchivoCargue archivoCargado = archivoCargueFactoria.find(this.archivoCargado.getId());
							archivoCargado.setFlgEstado(Boolean.FALSE);
							this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
						}
						if (volcarArchivoDDBB && !impedirGuardadoBaseDatos) {
							if (validacionInformacion != null
									&& TipoValidacion.INFORMACION.equals(validacionInformacion.getTipoValidacion())
									&& validacionInformacion.isPersistido() && validacionInformacion.isValidar()) {
								resultadoValidacionOK = this.informacionArchivoCorrecta(this.plantillaArchivo);
								if (!resultadoValidacionOK && validacionInformacion.isTransaccional()) {
									impedirGuardadoBaseDatos = this.plantillaArchivo.isBloquearCargueLote();
									if (impedirGuardadoBaseDatos) {
										ArchivoCargue archivoCargado = archivoCargueFactoria
												.find(this.archivoCargado.getId());
										archivoCargado.setFlgEstado(Boolean.FALSE);
										this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
									}
								}
								if (!resultadoValidacionOK) {
									if (tieneArchivosSalida) {
										for (Archivo archivoSalida : xml.getArchivo()) {
											if (archivoSalida.getTipoArchivo().equals(TipoArchivo.SALIDA)) {
												plantillaArchivo = archivoSalida;
												this.generarSalida();
											}
										}
									}
									if (validacionInformacion.getNotificacion().isNotificar()) {
										this.enviarNotificacion(validacionInformacion, this.plantillaArchivo,
												archivoCargueFile, true);
									}
								} else if (generarSalidas && tieneArchivosSalida
										&& validacionInformacion.getNotificacion().isNotificar()) {
									for (Archivo archivoSalida : archivos) {
										if (TipoArchivo.SALIDA.equals(archivoSalida.getTipoArchivo())) {
											plantillaArchivo = archivoSalida;
											this.generarSalida();
										}
									}
									this.enviarNotificacion(validacionInformacion, this.plantillaArchivo,
											archivoCargueFile, true);
								}
							}
							if (notificarExitoso != null && resultadoValidacionOK) {
								this.enviarNotificacion(notificarExitoso, this.plantillaArchivo, archivoCargueFile,
										true);
							}
						}
						FileUtil.eliminarArchivo(this.archivoCargue);
					} else {
						logger.log().error(tituloLog,
								"No se encuentra una plantilla compatible con : " + this.archivoCargue.getName());
						this.moverArchivoNoCargado(archivoCargueFile);
					}
				} catch (Exception e) {
					logger.error(tituloLog, e);
					if (this.archivoCargado != null) {
						this.registrarLog(e.getMessage(), null, null, Boolean.FALSE);
					}
					throw new SIGEP2SistemaException();
				} finally {
					registrarEnArchivoCargue();
				}
			}
			this.eliminarArchivosCargados();
		} catch (Exception e) {
			logger.error(tituloLog, e);
			this.eliminarArchivosCargados();
			throw new SIGEP2SistemaException();
		} finally {
			ProcesoCargueArchivo.setAlive(false);
		}
	}

	public boolean nombreArchivoCorrecto(Archivo archivo) {
		String tituloLog = "boolean nombreArchivoCorrecto(Archivo archivo)";
		StringBuffer validacionBuffer = new StringBuffer();
		if (archivo.getNombre() == null && archivo.getMascara() == null) {
			validacion = "No se ha definido el mecanismo de comparación por nombre. Revise la configuración en la plantilla "
					+ this.procesoArchivo.getNombrePlantilla();
			logger.log().error(tituloLog, validacion);
			validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);

			this.validacion = HTMLUtil.abreListaNoOrdenada + validacionBuffer.toString()
					+ HTMLUtil.cierraListaNoOrdenada;
			return false;
		}
		String nombreArchivoPlantilla = archivo.getNombre();
		String extensionArchivoPlantilla = archivo.getExtension().value();

		String nombreArchivo = this.archivoCargue.getName().replace(this.rutaArchivoEnCargue, "");

		if (nombreArchivo.toLowerCase().indexOf(extensionArchivoPlantilla.toLowerCase()) < 0
				&& !extensionOriginalExcel) {
			String validacion = "La extensión del archivo no es válida.";
			logger.log().error(tituloLog, validacion);
			validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
		}
		nombreArchivo = nombreArchivo.toLowerCase().replace(extensionArchivoPlantilla.toLowerCase(), "").toUpperCase();

		if (extensionOriginalExcel) {
			nombreArchivo = nombreArchivo.toLowerCase().replace(Extension.CSV.value().toLowerCase(), "").toUpperCase();
		}

		String validacion = "La longitud nombre del archivo no corresponde con la longitud parametrizada.";

		// Validación del formato de nombre de archivo
		if (archivo.getMascara() == null) {
			if (archivo.getCalificadorLongitudNombre() != null) {
				final CalificadorComparacion calificadorLongitudNombre = archivo.getCalificadorLongitudNombre();

				if (!validarPorCriterioComparacion(nombreArchivo.length(), archivo.getLongitudNombre().intValue(),
						calificadorLongitudNombre)) {
					logger.log().error(tituloLog, validacion);
					validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
				}
			}

			if (!nombreArchivoPlantilla.equals(nombreArchivo)) {
				validacion = "El nombre del archivo no cumple con la estructura parametrizada.";
				logger.log().error(tituloLog, validacion);
				validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
			}
		} else {
			Pattern pat = Pattern.compile(archivo.getMascara());
			Matcher mat = pat.matcher(nombreArchivo);
			if (!mat.matches()) {
				validacion = "El nombre del archivo '" + nombreArchivo + "' no cumple con el patrón establecido";
				logger.log().error(tituloLog, validacion);
				validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
			}
		}

		// Validación de la fecha en el nombre del archivo
		if (!TipoCruceFecha.IGNORAR.equals(archivo.getCruceFechaArchivo())
				&& !TipoCruceFecha.IGNORAR.equals(archivo.getFormatoFechaArchivo())) {
			Date fechaCruce = null;
			try {
				if (Xml.CAPTURA.equals(archivo.getCruceFechaArchivo().value())) {
					fechaCruce = this.fechaCargue;
				} else if (Xml.SISTEMA.equals(archivo.getCruceFechaArchivo().value())) {
					fechaCruce = DateUtils.getFechaSistema();
				}
				fechaArchivo = DateUtils.formatearAFecha(archivo.getFormatoFechaArchivo(), nombreArchivo);
				fechaCruce = DateUtils.formatearAFecha(archivo.getFormatoFechaArchivo(),
						DateUtils.formatearACadena(fechaCruce, archivo.getFormatoFechaArchivo()));
			} catch (ParseException e) {
				validacion = "No se pudo validar la fecha de cargue Vs. la fecha del archivo por errores de conversión.";
				validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
				return false;
			}
			final CalificadorComparacion calificadorFechaArchivo = archivo.getCalificadorFechaArchivo();

			validacion = "La fecha del archivo no corresponde a un período válido de recaudo, debe ser '"
					+ StringUtil.UppercaseFirstLetters(
							archivo.getCalificadorFechaArchivo().value().toLowerCase().replace("_", " "))
					+ "' a " + DateUtils.formatearACadena(fechaCruce, DateUtils.FECHA_FORMATO_VO) + ".";
			if (!validarPorCriterioComparacion(fechaArchivo.getTime(), fechaCruce.getTime(), calificadorFechaArchivo)) {
				logger.log().error(tituloLog, validacion);
				validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
			}
		}

		this.validacion = ((validacionBuffer.toString().isEmpty()
				? MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_EXITOSO)
				: HTMLUtil.abreListaNoOrdenada + validacionBuffer.toString() + HTMLUtil.cierraListaNoOrdenada));
		return validacionBuffer.toString().isEmpty();
	}

	public boolean estructuraArchivoCorrecta(Archivo archivo, Validacion validacionEstructura) throws Exception {
		String tagPosicion = "Posición (";
		String tituloLog = "boolean estructuraArchivoCorrecta(Archivo archivo)";
		sentencias = new StringBuilder();
		FileReader f = null;
		BufferedReader buffer = null;
		try {
			StringBuffer validacionBuffer = new StringBuffer();
			f = new FileReader(this.archivoCargue.getPath());
			buffer = new BufferedReader(f);
			String lineaArchivo;
			logger.log().info(tituloLog, "Inicio lectura de archivo");
			int posicionLinea = 0;
			validacion = null;
			List<String> tiposRegistrosPlantilla = this.getTiposRegistro(archivo.getRegistro());
			List<String> tiposRegistrosPlantillaControlLote = null;
			List<String> tiposRegistrosObligatorios = new LinkedList<>();
			List<String> tiposRegistrosObligatoriosLote = new LinkedList<>();
			String sentencia = null;
			boolean tieneControlLote = false;
			for (Registro registro : archivo.getRegistro()) {
				if (TipoRegistro.CONTROL_DE_LOTE.equals(registro.getTipoRegistro())) {
					tieneControlLote = true;
					tiposRegistrosPlantillaControlLote = this.getTiposRegistroControlLote(archivo.getRegistro());
					break;
				}
			}

			TipoRegistro ultimoTipoRegistroValido = null;
			boolean tieneTipoRegistroPosicionMayorAUno = false;
			boolean omitirPrimerRegistro = archivo.isOmitirPrimerRegistro();
			while ((lineaArchivo = buffer.readLine()) != null) {
				if (omitirPrimerRegistro) {
					omitirPrimerRegistro = false;
					continue;
				}
				boolean lineaConError = false;
				posicionLinea++;
				StringBuffer error = new StringBuffer();
				error.append("linea " + posicionLinea + ": '" + lineaArchivo + "'");
				sentencia = null;
				StringBuilder validacion = new StringBuilder();
				for (Registro registro : archivo.getRegistro()) {
					boolean encontroTipoRegistro = false;
					Columna tipoRegistro = new Columna();
					String tipoRegistroLineaArchivo = "";
					Matcher matTipoRegistro = null;
					if (registro.isValidarTipoRegistro()) {
						tipoRegistro = registro.getColumna().get(registro.getPosicionTipoRegistro().intValue() - 1);
						tipoRegistroLineaArchivo = this.subCadena(archivo.getExtension(), registro, tipoRegistro,
								lineaArchivo);
						if (!tieneTipoRegistroPosicionMayorAUno && registro.getPosicionTipoRegistro().intValue() > 1) {
							tieneTipoRegistroPosicionMayorAUno = true;
						}

						Pattern patTipoRegistro = Pattern.compile(tipoRegistro.getMascara());
						matTipoRegistro = patTipoRegistro.matcher(tipoRegistroLineaArchivo);

						// Valida la obligatoriedad de los registros en el
						// archivo
						// cargado de acuerdo a lo definido en la plantilla
						if (!tiposRegistrosObligatorios.contains(tipoRegistroLineaArchivo)
								&& tiposRegistrosPlantilla.contains(tipoRegistroLineaArchivo)
								&& registro.isRequerido()) {
							tiposRegistrosObligatorios.add(tipoRegistroLineaArchivo);
						}

						if (tiposRegistrosPlantilla.contains(tipoRegistroLineaArchivo)) {
							encontroTipoRegistro = true;
						}

						// Valida que el registro encontrado sea válido
						String tipoRegistroS = " El tipo de registro '";
						String noEsValido = "' no es válido.";
						boolean tipoRegistroValido = true;
						// Valida que el registro encontrado sea válido
						if (!tiposRegistrosPlantilla.contains(tipoRegistroLineaArchivo)
								&& archivo.getRegistro().indexOf(registro) >= archivo.getRegistro().size()
								&& !encontroTipoRegistro) {
							tipoRegistroValido = false;
						}
						if (!tiposRegistrosPlantilla.contains(tipoRegistroLineaArchivo)
								&& !tieneTipoRegistroPosicionMayorAUno && !encontroTipoRegistro) {
							tipoRegistroValido = false;
						}

						if (!tipoRegistroValido) {
							lineaConError = true;
							error.append(tipoRegistroS + tipoRegistroLineaArchivo + noEsValido);
							break;
						}
					}

					// se valida la estructura del tipo de registro
					if ((matTipoRegistro != null && matTipoRegistro.matches()) || !registro.isValidarTipoRegistro()) {
						// Si tiene control de lote, valida que el lote
						// tenga
						// los tipos de registros requeridos
						if (tieneControlLote) {
							// Valida la obligatoriedad de los registros en
							// el
							// archivo
							// cargado de acuerdo a lo definido en la
							// plantilla
							boolean tipoRegistroEnLote = !TipoRegistro.CABECERA.equals(registro.getTipoRegistro())
									&& !TipoRegistro.PIE.equals(registro.getTipoRegistro());
							if (tipoRegistroEnLote && !tiposRegistrosObligatoriosLote.contains(tipoRegistroLineaArchivo)
									&& tiposRegistrosPlantilla.contains(tipoRegistroLineaArchivo)
									&& registro.isRequerido()) {
								tiposRegistrosObligatoriosLote.add(tipoRegistroLineaArchivo);
							}
							if (TipoRegistro.CONTROL_DE_LOTE.equals(registro.getTipoRegistro())
									&& !tiposRegistrosObligatoriosLote.isEmpty()) {
								for (String registroPlantilla : tiposRegistrosPlantillaControlLote) {
									if (!tiposRegistrosObligatoriosLote.contains(registroPlantilla)) {
										lineaConError = true;
										error.append("El tipo de registro de valor '" + registroPlantilla
												+ "' es requerido.");
									}
								}
								tiposRegistrosObligatoriosLote = new LinkedList<>();
							}
							boolean entraPorTipoRegistroLote = TipoRegistro.CONTROL_DE_LOTE
									.equals(ultimoTipoRegistroValido)
									&& !TipoRegistro.CONTROL_DE_LOTE.equals(registro.getTipoRegistro());
							if (ultimoTipoRegistroValido != null && entraPorTipoRegistroLote
									&& !TipoRegistro.ENCABEZADO_DE_LOTE.equals(registro.getTipoRegistro())
									&& !TipoRegistro.PIE.equals(registro.getTipoRegistro())) {
								lineaConError = true;
								error.append("El tipo de registro '" + tipoRegistroLineaArchivo
										+ "' no es permitido en la posición actual.");
							}

							if (!TipoRegistro.PIE.equals(registro.getTipoRegistro())) {
								ultimoTipoRegistroValido = registro.getTipoRegistro();
							}
						}
						int longitudEsperada = 0;
						boolean longitudCorrecta = true;
						if (!registro.isCsv()) {
							for (Columna columna : registro.getColumna()) {
								if (columna.isRenderizado() && columna.getFin() != null
										&& columna.getFin().intValue() > 0) {
									BigInteger inicio = columna.getInicio() != null ? columna.getInicio().negate()
											: BigInteger.ONE.negate();
									longitudEsperada = longitudEsperada + (columna.getFin() != null
											&& columna.getFin().compareTo(BigInteger.ZERO) == 1
													? columna.getFin().add(inicio).add(BigInteger.ONE).intValue() : 1);
								}
							}
						}
						if (longitudEsperada > 0 && longitudEsperada < lineaArchivo.length()) {
							lineaConError = true;
							String validacionLongitud = HTMLUtil.retornoCarro + "Longitud del registro esperada "
									+ longitudEsperada + ", longitud encontrada " + lineaArchivo.length() + ".";
							error.append(validacionLongitud);
							longitudCorrecta = false;
						}
						if (!longitudCorrecta) {
							break;
						}
						String valor = null;
						for (Columna columna : registro.getColumna()) {
							try {
								// Se validan los campos que no sean tipo de
								// registro
								if ((tipoRegistroLineaArchivo.equals(tipoRegistro.getValorPorDefecto())
										&& columna.isRenderizado()) || !registro.isValidarTipoRegistro()) {
									try {
										valor = this.subCadena(archivo.getExtension(), registro, columna, lineaArchivo);
										Pattern pat = Pattern.compile(columna.getMascara());
										Matcher mat = pat.matcher(valor);
										if (!mat.matches() && !valor.trim().isEmpty()) {
											logger.log().warn(tituloLog,
													"Columna: " + columna.getDescripcion()
															+ " / Validando por: Mascara: " + columna.getMascara()
															+ " Valor: '" + valor + "'");
											lineaConError = true;
											error.append(HTMLUtil.retornoCarro + tagPosicion + columna.getInicio()
													+ (columna.getFin() != null ? "," + columna.getFin() : "")
													+ "): El valor '<span style='color:red'>" + valor
													+ "</span>' para el campo '" + columna.getDescripcion()
													+ "' contiene valores no permitidos."
													+ (columna.getObservaciones() != null
															&& !columna.getObservaciones().isEmpty()
																	? " " + columna.getObservaciones() : ""));
											validacion.append(
													columna.getObservaciones() + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
										}
										if (valor.trim().isEmpty() && columna.isRequerido()) {
											lineaConError = true;
											String msg = "Valor vacío para el campo '" + columna.getDescripcion()
													+ "' y este valor es requerido.";
											error.append(HTMLUtil.retornoCarro + tagPosicion + columna.getInicio()
													+ (columna.getFin() != null ? "," + columna.getFin() : "") + "): "
													+ msg);
											validacion.append(msg + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
										}

										if (columna.isRequerido() && !columna.getValorPorDefecto().isEmpty()
												&& !valor.equals(columna.getValorPorDefecto())) {
											lineaConError = true;
											String msg = "El valor '" + valor + "' para el campo '"
													+ columna.getDescripcion()
													+ "' no corresponde con el valor fijo configurado en la plantilla: '"
													+ columna.getValorPorDefecto() + "'";
											error.append(HTMLUtil.retornoCarro + tagPosicion + columna.getInicio()
													+ (columna.getFin() != null ? "," + columna.getFin() : "") + "): "
													+ msg);
											validacion.append(msg + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
										}
										if (registro.isContieneSentenciaSQL() && registro.getSQL() != null) {
											if (sentencia == null) {
												sentencia = registro.getSQL().getSentencia()
														.replace(FileUtil.PUNTO_COMA, FileUtil.RETORNO_CARRO);
											}
											if (columna.getParametro() != null && !columna.getParametro().isEmpty()
													&& !lineaConError) {
												String valorParametro = valor.trim();
												if (TipoDato.BOOLEAN.value().equals(columna.getTipoDato().value())) {
													valorParametro = "true".equals(valorParametro) ? "1" : "0";
												} else if (TipoDato.DATE.value()
														.equals(columna.getTipoDato().value())) {
													if (!valorParametro.isEmpty()) {
														Date fecha = null;
														try {
															fecha = DateUtils.formatearAFecha(
																	columna.getObservaciones(), valorParametro);
														} catch (ParseException e) {
															lineaConError = true;
															error.append(HTMLUtil.retornoCarro + tagPosicion
																	+ columna.getInicio()
																	+ (columna.getFin() != null ? "," + columna.getFin()
																			: "")
																	+ "): El valor '<span style='color:red'>" + valor
																	+ "</span>' para el campo '"
																	+ columna.getDescripcion()
																	+ "' no tiene un formato válido "
																	+ (" " + columna.getObservaciones() != null
																			&& !columna.getObservaciones().isEmpty()
																					? columna.getObservaciones() : ""));
															String msg = "El valor '" + valor + "' para el campo '"
																	+ columna.getDescripcion()
																	+ "' no tiene un formato válido "
																	+ (" " + columna.getObservaciones() != null
																			&& !columna.getObservaciones().isEmpty()
																					? columna.getObservaciones() : "");
															validacion.append(msg + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
														}
														if (!lineaConError && columna.isValidarContraFechaArchivo()) {
															final CalificadorComparacion calificadorContraFechaArchivo = columna
																	.getCalificadorValidacionContraFechaArchivo();
															long fechaArchivoLong = fechaArchivo.getTime();
															long fechaCargueLong = fecha.getTime();

															if (!validarPorCriterioComparacion(fechaArchivoLong,
																	fechaCargueLong, calificadorContraFechaArchivo)) {
																lineaConError = true;
																String validacionFechaCampo = StringUtil
																		.UppercaseFirstLetters(
																				calificadorContraFechaArchivo.value()
																						.toLowerCase());

																logger.log().warn(tituloLog,
																		"Columna: " + columna.getDescripcion()
																				+ " / Validando por: Mascara: "
																				+ columna.getMascara() + " Valor: '"
																				+ valor + "'");
																error.append(HTMLUtil.retornoCarro + tagPosicion
																		+ columna.getInicio()
																		+ (columna.getFin() != null
																				? "," + columna.getFin() : "")
																		+ "): El valor '<span style='color:red'>"
																		+ valor + "</span>' para el campo '"
																		+ columna.getDescripcion()
																		+ "' en referencia a la fecha del archivo, esta debe ser '"
																		+ validacionFechaCampo + "' a "
																		+ DateUtils.formatearACadena(fechaArchivo)
																		+ (columna.getObservaciones() != null
																				&& !columna.getObservaciones().isEmpty()
																						? " en formato " + columna
																								.getObservaciones()
																						: ""));
																String msg = "El valor '" + valor + "' para el campo '"
																		+ columna.getDescripcion()
																		+ "' en referencia a la fecha del archivo, esta debe ser '"
																		+ validacionFechaCampo + "' a "
																		+ DateUtils.formatearACadena(fechaArchivo)
																		+ (columna.getObservaciones() != null
																				&& !columna.getObservaciones().isEmpty()
																						? " en formato " + columna
																								.getObservaciones()
																						: "");
																validacion
																		.append(msg + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
															}
														}
														// Si no hay errores en
														// la
														// fecha se procede a
														// montar
														// el valor para el
														// parametro
														if (!lineaConError) {
															valorParametro = ConfigurationBundleConstants
																	.getString(
																			ConfigurationBundleConstants.CNS_METODO_TO_DATE_DDBB)
																	.replace("FECHA_CONVERTIR",
																			DateUtils.formatearACadena(fecha,
																					columna.getObservaciones()))
																	.replace("FORMATO",
																			columna.getObservaciones());
														}
													} else if (!columna.isRequerido()) {
														valorParametro = "null";
													}
												} else if (TipoDato.DECIMAL.value()
														.equals(columna.getTipoDato().value())) {
													if (!valorParametro.isEmpty()) {
														if (valorParametro.indexOf('.') > -1) {
															if (!valorParametro.contains("-")) {
																valorParametro = String.valueOf(
																		new java.math.BigDecimal(valorParametro));
															} else {
																valorParametro = String
																		.valueOf(new java.math.BigDecimal(
																				valorParametro.replace("-", ""))
																						.negate());
															}
														} else {
															try {
																valorParametro = valorParametro.substring(0,
																		valorParametro.length() - 2)
																		+ "."
																		+ valorParametro.substring(
																				valorParametro.length() - 2,
																				valorParametro.length());
															} catch (IndexOutOfBoundsException e) {
																logger.warn(tituloLog,
																		"Dato numérico sin componente decimal", e);
															}
														}
													}
												} else if (TipoDato.PERCENT.value()
														.equals(columna.getTipoDato().value())) {
													if (valorParametro.isEmpty()) {
														valorParametro = String.valueOf(valorParametro);
													}
												} else if (TipoDato.DOUBLE.value()
														.equals(columna.getTipoDato().value())) {
													if (!valorParametro.isEmpty()) {
														valorParametro = valorParametro.substring(0,
																valorParametro.length() - 2) + "."
																+ valorParametro.substring(valorParametro.length() - 2,
																		valorParametro.length());
													}
												} else if (TipoDato.INTEGER.value()
														.equals(columna.getTipoDato().value())) {
													if (!valorParametro.isEmpty()) {
														try {
															if (!valorParametro.contains("-")) {
																valorParametro = String.valueOf(
																		new java.math.BigInteger(valorParametro));
															} else {
																valorParametro = String
																		.valueOf(new java.math.BigInteger(
																				valorParametro.replace("-", ""))
																						.negate());
															}
														} catch (NumberFormatException e) {
															int index = valorParametro.indexOf(',');
															if (index < 0) {
																index = valorParametro.length();
															}
															if (!valorParametro.contains("-")) {
																valorParametro = String.valueOf(new BigInteger(
																		valorParametro.substring(0, index)));
															} else {
																valorParametro = String.valueOf(new BigInteger(
																		valorParametro.substring(0, index)).negate());
															}
														}
													} else {
														valorParametro = "null";
													}
												} else if (TipoDato.STRING.value()
														.equals(columna.getTipoDato().value())) {
													valorParametro = valorParametro.replace("'", "''").replace("&",
															"'||'&'||'");
													valorParametro = "'" + valorParametro + "'";
												}
												sentencia = sentencia.replace(":" + columna.getParametro(),
														valorParametro);
											}
										}
									} catch (StringIndexOutOfBoundsException e) {
										logger.info(tituloLog, e);
										if (columna.isRequerido()) {
											lineaConError = true;
											String msg = "El valor para el campo '" + columna.getDescripcion()
													+ "' no pudo ser leído y es requerido. Longitud esperada "
													+ longitudEsperada + ", longitud encontrada "
													+ lineaArchivo.length() + ".";
											error.append(HTMLUtil.retornoCarro + tagPosicion + columna.getInicio() + ","
													+ columna.getFin() + "): " + msg);
											validacion.append(msg + HTMLUtil.IDENTIFICADOR_FIN_ITEM);
										} else if (columna.getParametro() != null
												&& !columna.getParametro().isEmpty()) {
											String valorParametro = null;
											if (TipoDato.BOOLEAN.value().equals(columna.getTipoDato().value())) {
												valorParametro = "0";
											} else {
												valorParametro = "NULL";
											}
											sentencia = sentencia.replace(":" + columna.getParametro(), valorParametro);
										}
									}
								}
							} catch (NumberFormatException e) {
								logger.error(tituloLog, e);
								String mensaje = tagPosicion + columna.getInicio()
										+ (columna.getFin() != null ? "," + columna.getFin() : "")
										+ "): El valor para el campo '" + columna.getDescripcion()
										+ "' no pudo ser validado. Error presentado 'NumberFormatException' sobre el valor '"
										+ valor + "'. Se intentó obtener un valor de tipo '"
										+ columna.getTipoDato().value() + "'";
								logger.error(tituloLog, mensaje);
								registrarLog(mensaje, lineaArchivo, "Se esperaba un valor numérico", Boolean.FALSE);
							} catch (Exception e) {
								logger.error(tituloLog, e);
								String mensaje = tagPosicion + columna.getInicio()
										+ (columna.getFin() != null ? "," + columna.getFin() : "")
										+ "): El valor para el campo '" + columna.getDescripcion()
										+ "' no pudo ser validado. Error presentado '" + e.getMessage()
										+ "' sobre el valor '" + valor + "'.";
								logger.error(tituloLog, mensaje);
								registrarLog(mensaje, lineaArchivo, e.getMessage(), Boolean.FALSE);
							}
						}
					}

				}

				if (!lineaConError) {
					if (sentencias != null && sentencia != null) {
						sentencias
								.append(sentencia.replace("?archivo_cargue_id", String.valueOf(archivoCargado.getId()))
										.replace("?entidad_id", String.valueOf(entidadId))
										.replace("?aud_usuario_id", String.valueOf(usuarioArchivoCargue.getId()))
										.replace("?aud_rol_id", String.valueOf(rolCargueId.getId())));
						totalRegistros++;
					}
				} else if (validacionEstructura.isTransaccional()) {
					sentencias = null;
				}
				if (lineaConError) {
					logger.log().error(tituloLog, error);
					try {
						this.registrarLog(HTMLUtil.abreItem + error + HTMLUtil.cierraItem, lineaArchivo,
								validacion.toString(), Boolean.FALSE);
						validacionBuffer.append(HTMLUtil.abreItem + error + HTMLUtil.cierraItem);
					} catch (OutOfMemoryError e) {
						logger.warn(tituloLog, "Falla de memoria", e);
						try {
							String[] validaciones = validacionBuffer.toString().split("</li><li>");
							for (String val : validaciones) {
								try {
									this.registrarLog(val, lineaArchivo, validacion.toString(), Boolean.FALSE);
								} catch (Exception e2) {
									logger.log().error(tituloLog, e2);
								}
							}

							if (error != null) {
								registrarLog(error.toString(), lineaArchivo, validacion.toString(), Boolean.FALSE);
							}
							logger.log().info(tituloLog, "Log cargue actualizado");
						} catch (Exception e2) {
							logger.log().error(tituloLog, e2);
						} finally {
							validacionBuffer = new StringBuffer();
						}
					}
				}
			}

			if (!tiposRegistrosObligatorios.isEmpty()) {
				for (String registroPlantilla : tiposRegistrosPlantilla) {
					if (!tiposRegistrosObligatorios.contains(registroPlantilla)) {
						validacion = "El tipo de registro de valor '" + registroPlantilla + "' es requerido.";
						validacionBuffer.append(HTMLUtil.abreItem + validacion + HTMLUtil.cierraItem);
					}
				}
			}
			logger.log().info(tituloLog, "Fin lectura de archivo");
			this.validacion = ((validacionBuffer.toString().isEmpty()
					? (MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_EXITOSO))
					: HTMLUtil.abreListaNoOrdenada + validacionBuffer.toString() + HTMLUtil.cierraListaNoOrdenada));
			if (sentencias != null
					&& (!validacionEstructura.isTransaccional() || validacionBuffer.toString().isEmpty())) {
				return true;
			}
		} catch (Exception e) {
			logger.error(tituloLog, e);
			registrarLog(e.getMessage(), null, null, Boolean.FALSE);
		} finally {
			if (buffer != null) {
				buffer.close();
			}
			if (f != null) {
				f.close();
			}
		}
		return false;
	}

	public abstract boolean informacionArchivoCorrecta(Archivo archivo);

	public final boolean volcadoArchivoDDBBCorrecto(Long idArchivoCargue, String nombreArchivo) {
		String tituloLog = "final boolean volcadoArchivoDDBBCorrecto(Long idArchivoCargue, String nombreArchivo)";

		String rutaArchivoSQL = rutaArchivoCargado + sql;
		String nombreArchivoSQL = DateUtils.formatearACadena(this.fechaCargue, "yyyyMMdd") + "_"
				+ xml.getNombre().replace(" ", "_") + "_AC" + idArchivoCargue + ".sql";

		File archivoConScripts = new File(rutaArchivoSQL + nombreArchivoSQL);
		try {
			File rutaArchivoCargadoCarpeta = new File(rutaArchivoCargado);
			if (!rutaArchivoCargadoCarpeta.exists() && !rutaArchivoCargadoCarpeta.mkdirs()) {
				throw new IOException("No se pudo crear la carpeta " + rutaArchivoCargado);
			}

			FileUtil.copiarArchivo(rutaArchivoSQL, nombreArchivoSQL, sentencias, ConfigurationBundleConstants
					.getBoolean(ConfigurationBundleConstants.CNS_COMPRIMIR_ARCHIVO_CARGUE_SQL));

			// Llamado stored procedure para la insercion de los registros en
			// DDBB
			InputStream inputStreamArchivoFaltante = new FileInputStream(archivoConScripts);
			this.archivoCargado.setScriptsSQL(IOUtils.toString(inputStreamArchivoFaltante));
			archivoCargueFactoria.merge(archivoCargado, null);
			this.archivoCargueFactoria.ejecutarCargue(idArchivoCargue, this.usuarioArchivoCargue.getId(),
					this.rolCargueId.getId());
			this.archivoCargado = archivoCargueFactoria.find(this.archivoCargado.getId());

			boolean borrarArchivosScripts = ConfigurationBundleConstants
					.getBoolean(ConfigurationBundleConstants.CNS_JOB_CARGUE_ARCHIVO_BORRAR_SCRIPTS_SQL);
			String nombreArchivoProcesado = null;
			if (this.archivoCargado.isFlgEstado()) {
				if (borrarArchivosScripts) {
					FileUtil.eliminarArchivo(archivoConScripts + ConfigurationBundleConstants.getExtensionZip());
				}
				nombreArchivoProcesado = this.rutaArchivoCargado + nombreArchivo;
				nombreArchivoProcesado = nombreArchivoProcesado.replace("/", FileUtil.getFileSeparator());

				FileUtil.moverArchivo(this.archivoCargue.getPath(), nombreArchivoProcesado);
				FileUtil.Zip(nombreArchivoProcesado, nombreArchivoProcesado);
				FileUtil.eliminarArchivo(nombreArchivoProcesado);
			} else {
				this.moverArchivoNoCargado(this.archivoCargue);
			}
			if (borrarArchivosScripts) {
				FileUtil.eliminarArchivo(archivoConScripts);
			}

			sentencias = null;
		} catch (Exception e) {
			logger.log().error(tituloLog, e);
			try {
				registrarLog(e.getMessage(), null, e.getMessage(), Boolean.FALSE);
				File archivoCargueFile = new File(this.rutaArchivoEnCargue + nombreArchivo);
				this.moverArchivoNoCargado(archivoCargueFile);
			} catch (Exception e1) {
				logger.log().error(tituloLog, e1);
			}
		}
		return this.archivoCargado.isFlgEstado();
	}

	public final List<String> getTiposRegistro(List<Registro> registros) throws SIGEP2NegocioException {
		List<String> tiposRegistro = new LinkedList<>();
		for (Registro registro : registros) {
			Columna estructura = registro.getColumna().get(registro.getPosicionTipoRegistro().intValue() - 1);
			tiposRegistro.add(estructura.getValorPorDefecto());
			logger.log().debug("final List<String> getTiposRegistro(List<Registro> registros)",
					"Tipo de archivo: " + estructura.getValorPorDefecto());
		}
		return tiposRegistro;
	}

	public final List<String> getTiposRegistroControlLote(List<Registro> registros) throws SIGEP2NegocioException {
		List<String> tiposRegistro = new LinkedList<>();
		for (Registro registro : registros) {
			if (!TipoRegistro.CABECERA.equals(registro.getTipoRegistro())
					&& !TipoRegistro.PIE.equals(registro.getTipoRegistro())) {
				Columna estructura = registro.getColumna().get(registro.getPosicionTipoRegistro().intValue() - 1);
				tiposRegistro.add(estructura.getValorPorDefecto());
				logger.log().debug("final List<String> getTiposRegistroControlLote(List<Registro> registros)",
						"Tipo de archivo: " + estructura.getValorPorDefecto());
			}
		}
		return tiposRegistro;
	}

	protected final List<CuentaCorreo> getA(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> a = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(Xml.A)) {
				a.add(cuenta);
			}
		}
		return a;
	}

	protected final List<CuentaCorreo> getCC(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> cc = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(Xml.CC)) {
				cc.add(cuenta);
			}
		}
		return cc;
	}

	protected final List<CuentaCorreo> getCCO(List<CuentaCorreo> cuentasCorreo) {
		List<CuentaCorreo> cco = new ArrayList<>();
		for (CuentaCorreo cuenta : cuentasCorreo) {
			if (cuenta.getBandeja().value().equals(Xml.CCO)) {
				cco.add(cuenta);
			}
		}
		return cco;
	}

	protected final String subCadena(Extension extensionArchivo, Registro registro, Columna columna, String cadenaP) {
		String msg = "final String subCadena(Extension extensionArchivo, Registro registro, Columna columna, String cadenaP)";
		String cadena = cadenaP;
		if (!registro.isCsv() && Extension.TXT.value().equals(extensionArchivo.value())) {
			try {
				cadena = cadena.substring(columna.getInicio().intValue() - 1, columna.getFin().intValue());
			} catch (StringIndexOutOfBoundsException e) {
				if ((columna.getInicio().intValue() - 1) <= cadenaP.length()) {
					cadena = cadena.substring(columna.getInicio().intValue() - 1, cadenaP.length());
					if (cadena.trim().isEmpty()) {
						logger.warn(msg, e);
						throw new StringIndexOutOfBoundsException();
					} else {
						logger.warn(msg,
								"Valor obtenido hasta longitud máxima encontrada en el registro '" + cadena
										+ "' posiciones (" + (columna.getInicio().intValue() - 1) + ", "
										+ cadenaP.length() + ")");
					}
				} else {
					logger.warn(msg, e);
					throw new StringIndexOutOfBoundsException();
				}
			}
		} else if (registro.isCsv() && (Extension.CSV.value().equals(extensionArchivo.value())
				|| Extension.TXT.value().equals(extensionArchivo.value()))) {
			String caracterSeparador = null;
			if (extensionOriginalExcel) {
				caracterSeparador = caracterExcelToCSV.value();
			} else {
				if (SeparadorCsv.COMA.equals(registro.getCaracterCsv())) {
					caracterSeparador = SeparadorCsvCaracter.COMA.value();
				} else if (SeparadorCsv.PUNTO_COMA.equals(registro.getCaracterCsv())) {
					caracterSeparador = SeparadorCsvCaracter.PUNTO_COMA.value();
				} else if (SeparadorCsv.TABULADOR.equals(registro.getCaracterCsv())) {
					caracterSeparador = SeparadorCsvCaracter.TABULADOR.value();
				} else if (SeparadorCsv.RETORNO_CARRO.equals(registro.getCaracterCsv())) {
					caracterSeparador = SeparadorCsvCaracter.RETORNO_CARRO.value();
				}
			}
			cadena = cadena.split(caracterSeparador)[columna.getInicio().intValue() - 1];
		} else if (Extension.XLS.value().equals(extensionArchivo.value())
				|| Extension.XLSX.value().equals(extensionArchivo.value())) {
			String caracterSeparador = caracterExcelToCSV.value();
			try {
				logger.info(msg, cadena);
				cadena = cadena.split(caracterSeparador)[columna.getInicio().intValue() - 1];
				logger.info(msg, cadena);
			} catch (IndexOutOfBoundsException e) {
				logger.error(msg, e);
				cadena = "";
			}
		}
		return cadena;
	}

	protected void enviarNotificacion(Validacion validacion, Archivo archivo, File archivoCargue, boolean registrarLog)
			throws Exception {
		String tituloLog = "void enviarNotificacion(Validacion validacion, Archivo archivo, File archivoCargue)";
		if (!TipoValidacion.EXITOSO.equals(validacion.getTipoValidacion())
				&& !TipoValidacion.NOMBRE.equals(validacion.getTipoValidacion()) && registrarLog) {
			String[] validaciones = this.validacion.split("</li><li>");
			try {
				for (String val : validaciones) {
					registrarLog(val, null, null, MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_EXITOSO).contains(val));
				}
				logger.log().info(tituloLog, "Log cargue actualizado");
			} catch (Exception e) {
				logger.log().error(tituloLog, e);
			}
		} else if (TipoValidacion.NOMBRE.equals(validacion.getTipoValidacion()) && registrarLog) {
			registrarLog("Nombre de archivo" + this.archivoCargue.getName() + " incorrecto",
					"El nombre del archivo no es correcto", this.archivoCargue.getName(), Boolean.FALSE);
		}

		logger.log().info(tituloLog, "Envío de notifiación por archivo " + this.archivoCargue.getName());
		if (validacion.getNotificacion().isNotificar()) {
			if (TipoValidacion.INFORMACION.equals(validacion.getTipoValidacion())) {
				this.traerValidacionesInformacion();
			}
			this.validacion = HTMLUtil.listaNoOrdenada(this.validacion);

			String NOMBRE_ARCHIVO = archivoCargue.getCanonicalPath()
					.replace(this.rutaArchivoEnCargue.replace("/", FileUtil.getFileSeparator()), "")
					.replace(archivo.getExtension().value(), "");
			envioCorreo.enviarMail(validacion.getNotificacion().getAsunto().replace("NOMBRE_ARCHIVO", NOMBRE_ARCHIVO),
					HTMLUtil.abreParrafo
							+ validacion.getNotificacion().getCuerpo().replace("NOMBRE_ARCHIVO", NOMBRE_ARCHIVO)
							+ HTMLUtil.espacioEnBlanco + HTMLUtil.cierraParrafo
							+ (this.validacion != null ? this.validacion : ""),
					validacion.getNotificacion().getDesde(), this.getA(validacion.getNotificacion().getCuentaCorreo()),
					this.adjuntos, this.getCC(validacion.getNotificacion().getCuentaCorreo()));
		}
	}

	public void enviarNotificacion(IEnvioCorreoLocal envioCorreo, Validacion validacion,
			Map<String, Object> parametrosAsunto, Map<String, Object> parametrosCuerpo) throws Exception {

		if (validacion.getNotificacion().isNotificar()) {
			String asunto = validacion.getNotificacion().getAsunto();
			String cuerpo = validacion.getNotificacion().getCuerpo();
			for (String key : parametrosCuerpo.keySet()) {
				cuerpo = cuerpo.replace(key, parametrosCuerpo.get(key).toString());
			}
			for (String key : parametrosAsunto.keySet()) {
				asunto = asunto.replace(key, parametrosAsunto.get(key).toString());
			}
			logger.log().error(
					"void enviarNotificacion(IEnvioCorreoLocal envioCorreo, Validacion validacion, Map<String, Object> parametrosAsunto, Map<String, Object> parametrosCuerpo)",
					"Envío de notifiación por archivo : " + asunto);
			envioCorreo.enviarMail(asunto,
					HTMLUtil.abreParrafo + cuerpo + HTMLUtil.espacioEnBlanco + HTMLUtil.cierraParrafo
							+ (this.validacion != null ? this.validacion : ""),
					validacion.getNotificacion().getDesde(), this.getA(validacion.getNotificacion().getCuentaCorreo()),
					this.getCC(validacion.getNotificacion().getCuentaCorreo()));
		}
	}

	protected void generarSalida() throws NotSupportedException {
		if (adjuntos == null)
			adjuntos = new LinkedList<>();
		this.generarExcel();
	}

	private void generarExcel() throws NotSupportedException {
		String tituloLog = "void generarExcel()";
		String pathname = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_SALIDA)
				+ FileUtil.BACKSLASH + entidadId + FileUtil.BACKSLASH;
		File directorioSalida = new File(pathname);
		if (directorioSalida.mkdirs()) {
			logger.log().info(tituloLog, "'" + pathname + "' creado");
		}
		pathname = pathname
				+ plantillaArchivo.getNombre()
						.replace(plantillaArchivo.getFormatoFechaArchivo(),
								DateUtils.formatearACadena(archivoCargado.getFechaArchivo(),
										plantillaArchivo.getFormatoFechaArchivo()))
						.trim()
				+ plantillaArchivo.getExtension().value().toLowerCase();
		try {
			workbook = new XSSFWorkbook();
			// Create a blank sheet
			XSSFSheet sheet = workbook
					.createSheet(plantillaArchivo.getNombre()
							.replace(plantillaArchivo.getFormatoFechaArchivo(), DateUtils.formatearACadena(
									archivoCargado.getFechaArchivo(), plantillaArchivo.getFormatoFechaArchivo()))
							.trim());

			Map<Object, Object[]> data = new TreeMap<>();
			Map<Object, Object> parameters = new HashMap<>();
			int index = 2;
			boolean tieneRegistros = false;
			for (Registro registro : this.plantillaArchivo.getRegistro()) {
				if (registro.isContieneSentenciaSQL()) {
					List<Object[]> records = null;
					parameters.put("archivo_cargue_id", this.archivoCargado.getId());
					if (TipoRegistro.CABECERA.equals(registro.getTipoRegistro())
							|| TipoRegistro.PIE.equals(registro.getTipoRegistro())) {
						records = this.procesoArchivoFactoria.executeNativeQuery(registro.getSQL().getSentencia(),
								parameters, 1);
					} else {
						records = this.procesoArchivoFactoria.executeNativeQuery(registro.getSQL().getSentencia(),
								parameters, 0);
					}
					if (!records.isEmpty()) {
						tieneRegistros = true;
					}
					String[] cabecera = new String[registro.getColumna().size()];
					Object[] detalle = null;
					int contRecords = 1;
					for (Object record : records) {
						contRecords++;
						detalle = new Object[registro.getColumna().size()];
						logger.log().debug(tituloLog, record);
						for (Columna columna : registro.getColumna()) {
							int i = columna.getOrden().intValue() - 1;
							cabecera[i] = columna.getEtiquetaColumna();
							Object valor = null;
							if (TipoDato.DECIMAL.equals(columna.getTipoDato())) {
								try {
									valor = (BigDecimal) record;
								} catch (ClassCastException e) {
									logger.error(tituloLog, e);
									valor = (BigDecimal) ((Object[]) record)[i];
								}
								if (columna.getMascara() != null && !columna.getMascara().isEmpty()) {
									valor = NumeroUtil.formatoMoneda((BigDecimal) valor, columna.getMascara())
											.replace(NumeroUtil.SEPARADOR_MILES, ",")
											.replace(NumeroUtil.SEPARADOR_DECIMAL, ".");
								}
							} else if (TipoDato.INTEGER.equals(columna.getTipoDato())) {
								BigDecimal valorBigDecimal = null;
								try {
									valorBigDecimal = (BigDecimal) record;
								} catch (ClassCastException e) {
									logger.error(tituloLog, e);
									try {
										valorBigDecimal = (BigDecimal) ((Object[]) record)[i];
										valor = valorBigDecimal != null ? valorBigDecimal.intValue() : null;
									} catch (ClassCastException e1) {
										valor = (Integer) ((Object[]) record)[i];
									}
								}
							} else if (TipoDato.STRING.equals(columna.getTipoDato())) {
								try {
									valor = String.valueOf(((Object[]) record)[i]);
								} catch (Exception e) {
									logger.error(tituloLog, e);
									valor = record.toString();
								}
							} else if (TipoDato.DATE.equals(columna.getTipoDato())) {
								try {
									valor = DateUtils.formatearACadena((Date) record, columna.getMascara());
								} catch (ClassCastException e) {
									logger.error(tituloLog, e);
									valor = DateUtils.formatearACadena((Date) ((Object[]) record)[i],
											columna.getMascara());
								}
							} else {
								try {
									valor = record;
								} catch (ClassCastException e) {
									logger.error(tituloLog, e);
									valor = ((Object[]) record)[i];
								}
							}
							detalle[i] = valor;
						}
						data.put(Integer.toString(index), detalle);
						index++;
					}
					data.put(Integer.toString(index - contRecords), cabecera);
				}
			}

			// Iterate over data and write to sheet
			Set<Object> keyset = data.keySet();

			int rownum = 0;
			for (Object key : keyset) {
				// create a row of excelsheet
				Row row = sheet.createRow(rownum++);

				// get object array of prerticuler key
				Object[] objArr = data.get(key);

				int cellnum = 0;

				for (Object obj : objArr) {
					Cell cell = row.createCell(cellnum++);
					if (obj instanceof String) {
						cell.setCellValue((String) obj);
					} else if (obj instanceof Integer) {
						cell.setCellValue((Integer) obj);
					}
				}
			}
			if (tieneRegistros) {
				// Write the workbook in file system
				File salida = new File(pathname);
				FileOutputStream out = new FileOutputStream(salida);
				workbook.write(out);
				out.close();
				logger.log().info(tituloLog, "'" + pathname + "' generado");
				// FileUtil.Zip(pathname, pathname);
				// String salidaZip = pathname +
				// ConfigurationBundleConstants.getExtensionZip();
				adjuntos.add(pathname);
				// FileUtil.eliminarArchivo(salida);
			}
		} catch (Exception e) {
			logger.log().error(tituloLog, e);
		}
	}

	public abstract void prepararCargue() throws Exception;

	public boolean validarPorCriterioComparacion(long valorReferencia, long valorAEvaluar,
			CalificadorComparacion operador) {
		final CalificadorComparacion calificadorContraFechaArchivo = operador;
		long referenciaLong = valorReferencia;
		long aEvaluarLong = valorAEvaluar;

		if (CalificadorComparacion.IGUAL.equals(calificadorContraFechaArchivo) && referenciaLong != aEvaluarLong) {
			return false;
		}
		if (CalificadorComparacion.MENOR_QUE.equals(calificadorContraFechaArchivo) && referenciaLong >= aEvaluarLong) {
			return false;
		}
		if (CalificadorComparacion.MENOR_IGUAL_QUE.equals(calificadorContraFechaArchivo)
				&& referenciaLong > aEvaluarLong) {
			return false;
		}
		if (CalificadorComparacion.MAYOR_QUE.equals(calificadorContraFechaArchivo) && referenciaLong <= aEvaluarLong) {
			return false;
		}
		if (CalificadorComparacion.MAYOR_IGUAL_QUE.equals(calificadorContraFechaArchivo)
				&& referenciaLong < aEvaluarLong) {
			return false;
		}
		if (CalificadorComparacion.DIFERENTE_A.equals(calificadorContraFechaArchivo)
				&& referenciaLong == aEvaluarLong) {
			return false;
		}
		return true;
	}

	@Transactional
	private void registrarLog(String error, String valorRegistro, String validacion, Boolean estadoLog) {
		String msg = "void registrarLog(String val, Boolean estadoLog)";
		try {
			if (error != null) {
				ArchivoCargueLog log = new ArchivoCargueLog();
				log.setAudAccion(1l);
				log.setAudCodRol(1l);
				log.setAudCodUsuario(this.usuarioArchivoCargue.getId());
				log.setAudFechaActualizacion(DateUtils.getFechaSistema());
				log.setArchivoCargue(archivoCargado);
				String errorFormat = null;

				errorFormat = error.replace(HTMLUtil.abreListaNoOrdenada, "")
						.replace(HTMLUtil.cierraListaNoOrdenada, "").replace(HTMLUtil.abreListaOrdenada, "")
						.replace(HTMLUtil.cierraListaOrdenada, "").replace(HTMLUtil.abreItem, "")
						.replace(HTMLUtil.cierraItem, "");

				int lenghtFin = errorFormat.length();
				if (lenghtFin > longitudMaximaColumnas) {
					errorFormat = errorFormat.substring(0, longitudMaximaColumnas);
				}
				log.setError(errorFormat);
				log.setValidacion(validacion);
				String valorRegistroF = valorRegistro;
				if (valorRegistro != null) {
					valorRegistroF = valorRegistro.replace(";-;", "; ;");
				}
				log.setRegistro(valorRegistroF);
				log.setFlgLogExitoso(estadoLog);

				cargueLog.add(log);
				logger.info(msg, "Movimiento registrado en el log de cargue");
			}
		} catch (Exception e) {
			String log = "Hubo un problema al registrar el movimiento en el log de cargue para el archivo "
					+ archivoCargado.getNombreArchivoCargue() + ". Esta es una captura controlada del error.";
			logger.error(msg, log);
			logger.error(msg, e);
			registrarLog(log, valorRegistro, null, Boolean.FALSE);
		}
	}

	protected List<Archivo> getListaArchivosConfiguracion() {
		String msg = "List<Archivo> getListaArchivosConfiguracion()";
		List<Archivo> archivos = new LinkedList<>();

		tieneArchivosSalida = false;
		if (plantillaArchivo == null) {
			archivos.addAll(xml.getArchivo());
			logger.info(msg, xml.getArchivo().get(0).getMascara());
		} else {
			archivos.add(plantillaArchivo);
			for (Archivo archivo : xml.getArchivo()) {
				logger.info(msg, archivo.getMascara());
				if (archivo.getTipoArchivo().equals(TipoArchivo.SALIDA)) {
					archivos.add(archivo);
					if (!tieneArchivosSalida) {
						tieneArchivosSalida = true;
						this.adjuntos = new LinkedList<>();
					}
				}
			}
		}

		return archivos;
	}

	/**
	 * Mueve el archivo en proceso de cargue a la carpeta de no cargados
	 * 
	 * @param archivoCargueFile
	 */
	private void moverArchivoNoCargado(File archivoCargueFile) throws Exception {
		if (archivoCargueFile != null) {
			String nombreArchivoProcesado = rutaArchivoEnCargue + no_cargado + archivoCargueFile.getName();
			FileUtil.moverArchivo(archivoCargueFile.getAbsolutePath(), nombreArchivoProcesado);
			FileUtil.Zip(nombreArchivoProcesado, nombreArchivoProcesado);
			FileUtil.eliminarArchivo(nombreArchivoProcesado);
			return;
		}

		throw new InvalidParameterException();
	}

	/**
	 * En caso de suspención del cargue se eliminan los archivos ya cargados
	 * 
	 * @throws SIGEP2NegocioException
	 */
	private void eliminarArchivosCargados() throws Exception {
		if (!impedirGuardadoBaseDatos) {
			return;
		}
		for (ArchivoCargue archivoCargado : this.archivosCargueEjecutados) {
			archivoCargado.setFlgEstado(Boolean.FALSE);
			if (archivoCargado.isFlgEstado()) {
				logger.info("void eliminarArchivosCargados()",
						"Cargue imcompleto por errores en uno o varios archivos este lote");
			}
			this.registrarLog("Cargue imcompleto por errores en uno o varios archivos este lote", null, null,
					Boolean.FALSE);
			this.archivoCargueFactoria.merge(archivoCargado, usuarioArchivoCargue);
		}
	}

	/**
	 * Actualiza el registro de ArchivoCargue en base de datos incluyendo el log
	 * 
	 * @param archivoCargado
	 * @throws SIGEP2NegocioException
	 */
	private void registrarEnArchivoCargue() throws SIGEP2NegocioException {
		String msg = "void registrarEnArchivoCargue(ArchivoCargue archivoCargado)";
		try {
			if (cargueLog != null && !cargueLog.isEmpty()) {
				archivoCargado.setLogCargue(cargueLog);
				this.archivoCargueFactoria.merge(archivoCargado, this.usuarioArchivoCargue);
				cargueLog = new LinkedList<>();
			}
		} catch (Exception e) {
			logger.error(msg, e);
			throw new SIGEP2NegocioException(e);
		}
	}

	private void traerValidacionesInformacion() {
		if (cargueLogValidacionInformacion == null) {
			cargueLogValidacionInformacion = new LinkedList<>();
		}

		cargueLogValidacionInformacion = archivoCargueLogFactoria.obtenerArchivoLog(this.archivoCargado.getId());
		if (!cargueLogValidacionInformacion.isEmpty()) {
			this.validacion = "";

			for (ArchivoCargueLog log : this.cargueLogValidacionInformacion) {
				this.validacion = this.validacion + log.getError() + HTMLUtil.IDENTIFICADOR_FIN_ITEM;
			}
		}
	}

	public long getEntidadId() {
		return entidadId;
	}

	public void setEntidadId(long entidadId) {
		this.entidadId = entidadId;
	}
}
