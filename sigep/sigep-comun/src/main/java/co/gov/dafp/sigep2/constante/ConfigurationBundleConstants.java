package co.gov.dafp.sigep2.constante;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;

public final class ConfigurationBundleConstants {

	private static Logger logger = Logger.getInstance(ConfigurationBundleConstants.class);

	private static final String CONFIGURACION_BUNDLE = "co.gov.dafp.sigep2.configuration";
	private static final String CONFIG_PATH = "CONFIG_PATH";

	private ConfigurationBundleConstants() throws FileNotFoundException {
		properties = new Properties();
		String configPath = System.getProperty(CONFIG_PATH);
		if (configPath == null || configPath.isEmpty()) {
			configPath = "C:" + System.getProperty("file.separator");
		}

		classPath = configPath + CONFIGURACION_BUNDLE.replace(".", System.getProperty("file.separator"))
				+ ".properties";

		try {
			InputStream stream = new FileInputStream(classPath);
			properties.load(stream);
		} catch (IOException e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
		}
	}

	private Properties properties;
	private String classPath;

	private static final String key 								= "CNS_KEY"; // llave
	private static final String iv 									= "CNS_IV"; // vector de inicializaci�n
	private static final String rutaSyBase 							= "CNS_RUTA_SYBASE";
	private static final String aliasSistema 						= "CNS_ALIAS_SISTEMA";
	private static final String aliasSitio 							= "CNS_ALIAS_SITIO";
	private static final String CNS_X_FRAME_OPTIONS_DENY 			= "CNS_X_FRAME_OPTIONS_DENY";
	private static final String CNS_X_FRAME_OPTIONS_SOME_ORIGIN 	= "CNS_X_FRAME_OPTIONS_SOME_ORIGIN";
	private static final String CNS_X_XSS_PROTECTION 				= "CNS_X_XSS_PROTECTION";
	private static final String CNS_X_CONTENT_TYPE_OPTIONS 			= "CNS_X_CONTENT_TYPE_OPTIONS";
	private static final String CNS_VIEWPORT 						= "CNS_VIEWPORT";
	private static final String CNS_PRAGMA 							= "CNS_PRAGMA";
	private static final String CNS_CACHE_CONTROL 					= "CNS_CACHE_CONTROL";
	private static final String CNS_X_UA_COMPATIBLE 				= "CNS_X_UA_COMPATIBLE";
	private static final String timeOut 							= "CNS_TIMEOUT";
	private static final String charset 							= "CNS_CHARSET";
	private static final String encoding 							= "CNS_ENCODING";
	private static final String adminNombre 						= "CNS_ADMIN_NOMBRE";
	private static final String adminCargo 							= "CNS_ADMIN_CARGO";
	private static final String adminCuentaCorreo 					= "CNS_ADMIN_CUENTA_CORREO";
	private static final String adminTelefono 						= "CNS_ADMIN_TELEFONO";
	private static final String fileSeparator 						= "CNS_FILE_SEPARATOR";
	private static final String extensionZip 						= "CNS_EXTENSION_ZIP";
	private static final String extension7z 						= "CNS_EXTENSION_7Z";
	private static final String recaptcha 							= "CNS_CAPTCHA";
	private static final String timeOutConversation 				= "CNS_TIME_OUT_CONVERSATION";
	private static final String timeOutIdle 						= "CNS_TIME_OUT_IDLE";
	private static final String renderMenu 							= "CNS_RENDER_MENU";
	private static final String paginatorsSize 						= "CNS_PAGINATORS_PAGE";
	private static final String paginatorSize 						= "CNS_PAGINATOR_PAGE";
	public static final String CNS_URL_PORTAL 						= "CNS_URL_PORTAL";
	public static final String CNS_SESION_REDIRECCIONAR_URL_PORTAL 	= "CNS_SESION_REDIRECCIONAR_URL_PORTAL";

	private static final String ejbInitialContextFactory = "CNS_INITIAL_CONTEXT_FACTORY";
	private static final String ejbProviderURL 		= "CNS_PROVIDER_URL";
	private static final String ejbURLPkgPrefixes 	= "CNS_URL_PKG_PREFIXES";
	private static final String ejbAppName 			= "CNS_APP_NAME";
	private static final String ejbModuleName 		= "CNS_MODULE_NAME";

	private static final String IP_SERVIDOR 		= "IP_SERVIDOR";
	private static final String PUERTO_SERVIDOR 	= "PUERTO_SERVIDOR";
	private static final String PROTOCOLO 			= "PROTOCOLO";

	public static final String CNS_LOGO 			= "CNS_LOGO";
	public static final String CNS_LOGO_CORREO 		= "CNS_LOGO_CORREO";
	public static final String CNS_LEMA_GOBIERNO 	= "CNS_LEMA_GOBIERNO";
	public static final String CNS_CSS 				= "CNS_CSS";

	public static final String SEPARADOR_ENCRIPTADO = "SEPARADOR_ENCRIPTADO";

	public static final String CNS_FECHA_HORA_FORMATO 		= "CNS_FECHA_HORA_FORMATO";
	public static final String CNS_FECHA_FORMATO 			= "CNS_FECHA_FORMATO";
	public static final String CNS_FECHA_HORA_FORMATO_VO 	= "CNS_FECHA_HORA_FORMATO_VO";
	public static final String CNS_FECHA_FORMATO_VO 		= "CNS_FECHA_FORMATO_VO";

	// Parametros job cargue
	public static final String CNS_JOBS_LIST_CLASS 								= "CNS_JOBS_LIST_CLASS";
	public static final String CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO 				= "CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO";
	public static final String CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_DORMIR 		= "CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_DORMIR";
	public static final String CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_TIEMPO_DORMIR 	= "CNS_JOB_CARGUE_ARCHIVO_AUTOMATICO_TIEMPO_DORMIR";
	public static final String CNS_JOB_CARGUE_ARCHIVO_BORRAR_SCRIPTS_SQL 		= "CNS_JOB_CARGUE_ARCHIVO_BORRAR_SCRIPTS_SQL";
	public static final String CNS_RUTA_BIENES_RENTAS 							= "CNS_RUTA_BIENES_RENTAS";

	// Parametros mails sistema
	private static final String CUENTA_MAIL_SISTEMA 				= "CUENTA_MAIL_SISTEMA";
	public static final String MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO 	= "MAIL_SISTEMA_BASE_CUERPO_ENCABEZADO";
	public static final String MAIL_SISTEMA_BASE_CUERPO_PIE 		= "MAIL_SISTEMA_BASE_CUERPO_PIE";
	public static final String MAIL_SISTEMA_NOTA_PRIVACIDAD 		= "MAIL_SISTEMA_NOTA_PRIVACIDAD";
	public static final String MAIL_SISTEMA_FIRMA 					= "MAIL_SISTEMA_FIRMA";

	public static final String HABEAS_DATA = "HABEAS_DATA";

	public static final String PASS_VALIDATE_LOGIN 							= "PASS_VALIDATE_LOGIN";
	public static final String MAXIMO_INTENTOS_FALLIDOS_LOGIN 				= "MAXIMO_INTENTOS_FALLIDOS_LOGIN";
	public static final String PAGINA_RESTABLECER_PASSWORD 					= "PAGINA_RESTABLECER_PASSWORD";
	public static final String TIEMPO_VIGENCIA_TICKET_DIAS 					= "TIEMPO_VIGENCIA_TICKET_DIAS";
	public static final String TIEMPO_VIGENCIA_TICKET_DIAS_CODIGO_PARAM 	= "TIEMPO_VIGENCIA_TICKET_DIAS_CODIGO_PARAM";
	public static final String TIEMPO_VIGENCIA_TICKET_HORAS 				= "TIEMPO_VIGENCIA_TICKET_HORAS";
	public static final String TIEMPO_VIGENCIA_TICKET_HORAS_CODIGO_PARAM 	= "TIEMPO_VIGENCIA_TICKET_HORAS_CODIGO_PARAM";
	public static final String PASS_VALIDATE_EXPRESION_REGULAR 				= "PASS_VALIDATE_EXPRESION_REGULAR";
	public static final String PASS_VALIDATE_MINIMO 						= "PASS_VALIDATE_MINIMO";
	public static final String PASS_VALIDATE_MAXIMO 						= "PASS_VALIDATE_MAXIMO";
	public static final String PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS 	= "PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS";
	public static final String PASS_VALIDATE_MINIMO_MAYUSCULAS 				= "PASS_VALIDATE_MINIMO_MAYUSCULAS";
	public static final String PASS_VALIDATE_MINIMO_NUMEROS 				= "PASS_VALIDATE_MINIMO_NUMEROS";
	public static final String PASS_VALIDATE_MINIMO_SIMBOLOS 				= "PASS_VALIDATE_MINIMO_SIMBOLOS";

	// edad minima
	public static final String PASS_EDAD_MINIMA = "PASS_EDAD_MINIMA";
	public static final String PASS_EDAD_MAXIMA = "PASS_EDAD_MAXIMA";

	// MAIL REESTABLECER CONTRASEÑA
	public static final String MAIL_RESTABLECER_PASSWORD_ASUNTO = "MAIL_RESTABLECER_PASSWORD_ASUNTO";
	public static final String MAIL_RESTABLECER_PASSWORD_CUERPO = "MAIL_RESTABLECER_PASSWORD_CUERPO";
	
	// MAIL CONTRASEÑA REESTABLECIDA
	public static final String MAIL_PASSWORD_RESTABLECIDO_ASUNTO = "MAIL_PASSWORD_RESTABLECIDO_ASUNTO";
	public static final String MAIL_PASSWORD_RESTABLECIDO_CUERPO = "MAIL_PASSWORD_RESTABLECIDO_CUERPO";
	
	// TIPOS DE CARGA MASIVA
	public static final String CNS_CM_RUTA_DESCARGA_LOG 		= "CM_RUTA_DESCARGA_LOG";
	public static final String CNS_CM_PATH_PLANTILLAS 			= "CM_PATH_PLANTILLAS";
	public static final String CNS_CM_ACTIVAR_USUARIOS 			= "CM_ACTIVAR_USUARIOS";
	public static final String CNS_CM_CREAR_USUARIOS			= "CM_CREAR_USUARIOS";
	public static final String CNS_CM_HOJA_DE_VIDA 				= "CM_HOJA_DE_VIDA";
	public static final String CNS_FILA_INICIO 					= "CNS_FILA_INICIO_";

	public static final String CNS_RAIZ_SISTEMA 						= "CNS_RAIZ_SISTEMA";
	private static final String CNS_FILE_SEPARATOR 						= "CNS_FILE_SEPARATOR";
	public static final String CNS_PLANTILLAS_XML 						= "CNS_PLANTILLAS_XML";
	public static final String CNS_FORMATOS_CARGUE 						= "CNS_FORMATOS_CARGUE";
	public static final String CNS_ARCHIVO_SALIDA 						= "CNS_ARCHIVO_SALIDA";
	public static final String CNS_ARCHIVO_REPORTE 						= "CNS_ARCHIVO_REPORTE";
	public static final String CNS_VISTA_REPORTE 						= "CNS_VISTA_REPORTE";
	public static final String CNS_URL_XSD_XML 							= "CNS_URL_XSD_XML";
	public static final String CNS_URL_XSD_XML_REPORTES 				= "CNS_URL_XSD_XML_REPORTES";
	public static final String CNS_URL_XML_CONFIGURACION_BASE_REPORTES 	= "CNS_URL_XML_CONFIGURACION_BASE_REPORTES";
	public static final String CNS_URL_XML_REPORTES_CATALOGO 			= "CNS_URL_XML_REPORTES_CATALOGO";
	public static final String CNS_URL_XML_REPORTES_PORTAL 				= "CNS_URL_XML_REPORTES_PORTAL";
	public static final String CNS_URL_XML_REPORTES_CORREO 				= "CNS_URL_XML_REPORTES_CORREO";
	public static final String CNS_COMPRIMIR_ARCHIVO_CARGUE 			= "CNS_COMPRIMIR_ARCHIVO_CARGUE";
	public static final String CNS_COMPRIMIR_ARCHIVO_CARGUE_SQL 		= "CNS_COMPRIMIR_ARCHIVO_CARGUE_SQL";
	public static final String CNS_INDEX_HTML = "CNS_INDEX_HTML";

	public static final String CNS_FORMATOS_FECHAS 		= "CNS_FORMATOS_FECHAS";
	public static final String CNS_METODO_TO_DATE_DDBB 	= "CNS_METODO_TO_DATE_DDBB";

	public static final String CNS_PALABRAS_RESERVADAS_DDBB = "CNS_PALABRAS_RESERVADAS_DDBB";

	public static final String CNS_CLAVE_USUARIO 							= "CNS_CLAVE_USUARIO";
	public static final String CNS_DIAS_VENCE_CLAVE_USUARIO 				= "CNS_DIAS_VENCE_CLAVE_USUARIO";
	public static final String CNS_DIAS_VENCE_CLAVE_USUARIO_CODIGO_PARAM 	= "CNS_DIAS_VENCE_CLAVE_USUARIO_CODIGO_PARAM";

	public static final String CNS_FECHA_PRIMER_SEMESTRE 		= "CNS_FECHA_PRIMER_SEMESTRE";
	public static final String CNS_FECHA_SEGUNDO_SEMESTRE 		= "CNS_FECHA_SEGUNDO_SEMESTRE";

	public static final String CNS_ANIOS_ATRAS_MOSTRAR_SISTEMA 		= "CNS_ANIOS_ATRAS_MOSTRAR_SISTEMA";
	public static final String CNS_ANIOS_ADELANTE_MOSTRAR_SISTEMA 	= "CNS_ANIOS_ADELANTE_MOSTRAR_SISTEMA";

	public static final String CNS_ARCHIVO_PLANTILLAS_ADMINISTRACION_USUARIOS = "CNS_ARCHIVO_PLANTILLAS_ADMINISTRACION_USUARIOS";

	public static final String CNS_ARCHIVO_PLANTILLAS_BG = "CNS_ARCHIVO_PLANTILLAS_BG";

	public static final String CNS_ARCHIVO_PLANTILLAS_REPORTE_RECAUDO 		= "CNS_ARCHIVO_PLANTILLAS_REPORTE_RECAUDO";
	public static final String CNS_ARCHIVO_PLANTILLAS_REPORTE_PRESUPUESTO 	= "CNS_ARCHIVO_PLANTILLAS_REPORTE_PRESUPUESTO";
	public static final String CNS_ARCHIVO_PLANTILLAS_REPORTE_PRIORIZACION 	= "CNS_ARCHIVO_PLANTILLAS_REPORTE_PRIORIZACION";

	public static final String CNS_MAESTRO_CUENTA_RECAUDO_SUBSISTENCIA 	= "CNS_MAESTRO_CUENTA_RECAUDO_SUBSISTENCIA";
	public static final String CNS_MAESTRO_CUENTA_RECAUDO_SOLIDARIDAD 	= "CNS_MAESTRO_CUENTA_RECAUDO_SOLIDARIDAD";

	// Internacionalizacion
	public static final String CNS_LENGUAJE_ESPANIOL 	= "CNS_LENGUAJE_ESPANIOL";
	public static final String CNS_LENGUAJE_INGLES 		= "CNS_LENGUAJE_INGLES";
	public static final String CNS_ACENTOS 				= "CNS_ACENTOS";

	// DDBB
	public static final String CNS_TAMANIO_MAXIMO_COLUMNAS_VARCHAR_DDBB = "CNS_TAMANIO_MAXIMO_COLUMNAS_VARCHAR_DDBB";

	// RUTAS CARGAR ARCHIVOS USUARIO
	public static final String CNS_RUTA_DOCUMENTO 					= "CNS_RUTA_DOCUMENTO";
	public static final String CNS_RUTA_LIBRETA_MILITAR 			= "CNS_RUTA_LIBRETA_MILITAR";
	public static final String CNS_RUTA_DOCUMENTO_DISCAPACIDAD		= "CNS_RUTA_DOCUMENTO_DISCAPACIDAD";
	public static final String CNS_RUTA_FOTO_USUARIO 				= "CNS_RUTA_FOTO_USUARIO";
	public static final String PATH_SERVER_WEB_SERVICES 			= "PATH_SERVER_WEB_SERVICES";
	public static final String CNS_RUTA_TARJETA_PROFESIONAL 		= "CNS_RUTA_TARJETA_PROFESIONAL";
	public static final String CNS_RUTA_EDUCACION_FORMAL 			= "CNS_RUTA_EDUCACION_FORMAL";
	public static final String CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE 	= "CNS_RUTA_OTRO_CONOCIMIENTO_SOPORTE";
	public static final String CNS_RUTA_IDIOMA_SOPORTE 				= "CNS_RUTA_IDIOMA_SOPORTE";
	public static final String CNS_RUTA_DOC_EXP_DOCENTE 			= "CNS_RUTA_DOC_EXP_DOCENTE";
	public static final String CNS_RUTA_DOC_EXP_LABORAL 			= "CNS_RUTA_DOC_EXP_LABORAL";
	public static final String CNS_RUTA_FILE_FOTO_HOJA_VIDA 		= "CNS_RUTA_FILE_FOTO_HOJA_VIDA";
	public static final String CNS_RUTA_FILE_TEMP 					= "CNS_RUTA_FILE_TEMP";
	public static final String CNS_RUTA_DOCUMENTOS_ADICIONALES 		= "CNS_RUTA_DOCUMENTOS_ADICIONALES";
	
	/*Video Tutoriales*/
	public static final String OPT_VIDEO_TUTORIAL_SESSION 			="OPT_VIDEO_TUTORIAL_SESSION";
	public static final String OPT_VIDEO_GENERAL 					="OPT_VIDEO_GENERAL";
	public static final String OPT_VIDEO_INGRESOSISTEMA 			="OPT_VIDEO_INGRESOSISTEMA";
	public static final String OPT_VIDEO_HOJAVIDA 					="OPT_VIDEO_HOJAVIDA";
	public static final String OPT_VIDEO_ADMINISTRACION 			="OPT_VIDEO_ADMINISTRACION";
	public static final String OPT_VIDEO_BYR 						="OPT_VIDEO_BYR";
	public static final String OPT_VIDEO_CONTRATOS 					="OPT_VIDEO_CONTRATOS";
	public static final String OPT_VIDEO_SITUACIONESADMINISTRATIVAS ="OPT_VIDEO_SITUACIONESADMINISTRATIVAS";
	public static final String OPT_VIDEO_VINCULACIONES 				="OPT_VIDEO_VINCULACIONES";
	public static final String OPT_VIDEO_CARGUESMASIVOS 			="OPT_VIDEO_CARGUESMASIVOS";
	public static final String OPT_VIDEO_GESTIONINFORMACION 		="OPT_VIDEO_GESTIONINFORMACION";
	public static final String OPT_VIDEO_ENTIDADES 					="OPT_VIDEO_ENTIDADES";
	public static final String HABILITAR_PICO_CEDULA 				="HABILITAR_PICO_CEDULA";
	public static final String CNS_RUTA_CEDULAS_ADMIN 				="CNS_RUTA_CEDULAS_ADMIN";

	public static Object getObject(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return p.properties.getProperty(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public static int getInt(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return Integer.parseInt(p.properties.getProperty(key));
		} catch (Exception e) {
			logger.error("", e);
		}
		return 0;
	}

	public static String getString(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return p.properties.getProperty(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return "";
	}

	public static List<String> getListString(String key) {
		if (key != null && key.contains(SeparadorCsvCaracter.COMA.value())) {
			return Arrays.asList(ConfigurationBundleConstants.getString(key).split(SeparadorCsvCaracter.COMA.value()));
		} else {
			return Arrays
					.asList(ConfigurationBundleConstants.getString(key).split(SeparadorCsvCaracter.PUNTO_COMA.value()));
		}
	}

	public static BigDecimal getBigDecimal(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return BigDecimal.valueOf(Double.valueOf(p.properties.getProperty(key)));
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public static Long getLong(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return Long.valueOf(p.properties.getProperty(key));
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public static Boolean getBoolean(String key) {
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return Boolean.valueOf(p.properties.getProperty(key));
		} catch (Exception e) {
			logger.error("", e);
		}
		return Boolean.FALSE;
	}

	public static Double getPercent(String key) {
		Double value = 0.00;
		try {
			ConfigurationBundleConstants p = new ConfigurationBundleConstants();
			return (Math.rint(Double.valueOf(p.properties.getProperty(key)) * 100) / 100) / 100;
		} catch (Exception e) {
			logger.error("", e);
		}
		return value;
	}

	public static String getRutaSyBase() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.rutaSyBase);
	}

	public static String key() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.key);
	}

	public static String iv() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.iv);
	}

	public static String charset() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.charset);
	}

	public static String encoding() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.encoding);
	}

	public static String adminCuentaCorreo() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.adminCuentaCorreo);
	}

	public static String adminNombre() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.adminNombre);
	}

	public static String adminCargo() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.adminCargo);
	}

	public static String adminTelefono() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.adminTelefono);
	}

	public static int timeOut() {
		return ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.timeOut) * 60;
	}

	public static String ipServer() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.IP_SERVIDOR);
	}

	public static String aliasSistema() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.aliasSistema);
	}

	public static String aliasSitio() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.aliasSitio);
	}

	public static String xFrameOptionsDeny() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_X_FRAME_OPTIONS_DENY);
	}

	public static String xFrameOptionsSomeOrigin() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_X_FRAME_OPTIONS_SOME_ORIGIN);
	}

	public static String pragma() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_PRAGMA);
	}

	public static String cacheControl() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_CACHE_CONTROL);
	}

	public static String xUACompatible() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_X_UA_COMPATIBLE);
	}

	public static String portServer() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.PUERTO_SERVIDOR);
	}

	public static String mailSystem() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CUENTA_MAIL_SISTEMA);
	}

	public static String getMonedaLocal() {
		return NumeroUtil.SIMBOLO_MONEDA;
	}

	public static String getFileseparator() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.fileSeparator);
	}

	public static String getExtensionZip() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.extensionZip);
	}

	public static String getExtension7z() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.extension7z);
	}

	public static boolean getRecaptcha() {
		return ConfigurationBundleConstants.getBoolean(ConfigurationBundleConstants.recaptcha);
	}

	public static boolean renderMenu() {
		return ConfigurationBundleConstants.getBoolean(ConfigurationBundleConstants.renderMenu);
	}

	public static long getTimeOutConversation() {
		return ConfigurationBundleConstants.getLong(ConfigurationBundleConstants.timeOutConversation);
	}

	public static long getTimeOutIdle() {
		return ConfigurationBundleConstants.getLong(ConfigurationBundleConstants.timeOutIdle);
	}

	public static String getpaginatorsSize() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.paginatorsSize);
	}

	public static int getpaginatorSize() {
		return ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.paginatorSize);
	}

	public static String getEJBInitialcontextFactory() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.ejbInitialContextFactory);
	}

	public static String getEJBProviderURL() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.ejbProviderURL);
	}

	public static String getEJBURLPkgPrefixes() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.ejbURLPkgPrefixes);
	}

	public static String getEJBAppName() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.ejbAppName);
	}

	public static String getEJBModuleName() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.ejbModuleName);
	}

	public static String getPrefijoApp() {
		String puerto = ConfigurationBundleConstants.getString(PUERTO_SERVIDOR) != null
				&& !"80".equals(ConfigurationBundleConstants.getString(PUERTO_SERVIDOR))
						? ":" + ConfigurationBundleConstants.getString(PUERTO_SERVIDOR) : "";
		return ConfigurationBundleConstants.getString(PROTOCOLO) + "://"
				+ ConfigurationBundleConstants.getString(IP_SERVIDOR) + puerto + "/"
				+ ConfigurationBundleConstants.aliasSistema() + "/" + ConfigurationBundleConstants.aliasSitio() + "/";
	}

	public static String getRutaArchivo(String key) {
		String configPath = System.getProperty(CONFIG_PATH);
		String systemFileSeparator = System.getProperty("file.separator");
		String applicationFileSeparator = getString(CNS_FILE_SEPARATOR);
		if (configPath == null || configPath.isEmpty()) {
			configPath = getString(CNS_RAIZ_SISTEMA) + ":" + systemFileSeparator;
		}
		String ruta = getString(key);
		if (ruta == null) {
			ruta = configPath + key.replace(applicationFileSeparator, systemFileSeparator);
		} else {
			ruta = configPath + ruta.replace(applicationFileSeparator, systemFileSeparator);
		}
		return ruta;
	}

	public static String getRutaArchivoApp(String key) {
		String configPath = getPrefijoApp();
		String systemFileSeparator = FileUtil.getFileSeparator();

		String ruta = getString(key);
		if (ruta == null) {
			ruta = configPath + key.replace(systemFileSeparator, FileUtil.SLASH);
		} else {
			ruta = configPath + ruta.replace(systemFileSeparator, FileUtil.SLASH);
		}
		ruta = ruta.replace(systemFileSeparator, FileUtil.SLASH);
		return ruta;
	}

	/**
	 * Jose Viscaya 07 de Febrero de 2018
	 * 
	 * @return
	 */
	public static String getPathServerRest() {
		String path = ConfigurationBundleConstants.getString(PATH_SERVER_WEB_SERVICES);
		return path;
	}

	/**
	 * Hugo Quintero 22 de Julio de 2018
	 * 
	 * @return
	 */
	public static String getPathRutaLog() {
		String path = ConfigurationBundleConstants.getString(CNS_CM_RUTA_DESCARGA_LOG);
		return path;
	}
	
	public static String xXSSProtection() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_X_XSS_PROTECTION);
	}

	public static String xContentTypeOptions() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_X_CONTENT_TYPE_OPTIONS);
	}

	public static String viewport() {
		return ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_VIEWPORT);
	}

	/**
	 * Hugo Quintero 31 de Agosto de 2018
	 * 
	 * @return
	 */
	public static String getUrlApp() {
		String puerto = ConfigurationBundleConstants.getString(PUERTO_SERVIDOR) != null
				&& !"80".equals(ConfigurationBundleConstants.getString(PUERTO_SERVIDOR))
						? ":" + ConfigurationBundleConstants.getString(PUERTO_SERVIDOR) : "";
		return ConfigurationBundleConstants.getString(PROTOCOLO) + "://"
				+ ConfigurationBundleConstants.getString(IP_SERVIDOR) + puerto + "/"
				+ ConfigurationBundleConstants.aliasSistema() + "/";
	}
}