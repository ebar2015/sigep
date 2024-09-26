package co.gov.dafp.sigep2.constante;

import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import co.gov.dafp.sigep2.util.StringUtil;

public final class TitlesBundleConstants {

	private TitlesBundleConstants() {
	}

	public static final String TITLES_BUNDLE_CONSTANTS = "co.gov.dafp.sigep2.titles";

	public static final String TTL_SYSTEM_NAME 						= "TTL_SYSTEM_NAME";
	public static final String TTL_SYSTEM_OWNER 					= "TTL_SYSTEM_OWNER";
	public static final String TTL_SYSTEM_KIEWORDS 					= "TTL_SYSTEM_KIEWORDS";
	public static final String TTL_SYSTEM_DESCRIPTION 				= "TTL_SYSTEM_DESCRIPTION";
	public static final String TTL_COPYRIGHT 						= "TTL_COPYRIGHT";
	public static final String TTL_SYSTEM_SHORT_NAME 				= "TTL_SYSTEM_SHORT_NAME";
	public static final String TTL_USER 							= "TTL_USER";
	public static final String TTL_CREATE 							= "TTL_CREATE";
	public static final String TTL_EDIT 							= "TTL_EDIT";
	public static final String TTL_DELETE 							= "TTL_DELETE";
	public static final String TTL_CONSULT 							= "TTL_CONSULT";
	public static final String TTL_CANCEL 							= "TTL_CANCEL";
	public static final String TTL_CLOSE 							= "TTL_CLOSE";
	public static final String TTL_EXIT 							= "TTL_EXIT";
	public static final String TTL_OPEN 							= "TTL_OPEN";
	public static final String TTL_SEND 							= "TTL_SEND";
	public static final String TTL_ENTER 							= "TTL_ENTER";
	public static final String TTL_LOGON 							= "TTL_LOGON";
	public static final String TTL_GO_TO 							= "TTL_GO_TO";
	public static final String TTL_NICK_NAME 						= "TTL_NICK_NAME";
	public static final String TTL_PASSWORD 						= "TTL_PASSWORD";
	public static final String TTL_FORGET_PASSWORD 					= "TTL_FORGET_PASSWORD";
	public static final String TTL_SUSPEND 							= "TTL_SUSPEND";
	public static final String TTL_ACTIVATE 						= "TTL_ACTIVATE";
	public static final String TTL_ACEPTAR 							= "TTL_OK";
	public static final String LBL_ANIOS 							= "LBL_ANIOS";
	public static final String TTL_VER_DETALLE 						= "TTL_VER_DETALLE";
	public static final String TTL_APROBAR_VERIFICAR 				= "TTL_APROBAR_VERIFICAR";
	public static final String BTN_TABLA_ELIMINAR 					= "BTN_TABLA_ELIMINAR";
	public static final String TTL_DESHABILITADO_SEGURIDAD_ROLES 	= "TTL_DESHABILITADO_SEGURIDAD_ROLES";
	public static final String MSG_PREGUNTA_CANCELAR 				= "MSG_PREGUNTA_CANCELAR";
	public static final String LBL_ENCARGO 							= "LBL_ENCARGO";
	public static final String LBL_PROVISIONALIDAD 					= "LBL_PROVISIONALIDAD";
	public static final String LBL_POR_PROVEER						= "LBL_POR_PROVEER";
	public static final String LBL_VACANCIA_DEFINITIVA 				= "LBL_VACANCIA_DEFINITIVA";
	public static final String LBL_MAXIMO_NIVEL_DECISORIO 			= "LBL_MAXIMO_NIVEL_DECISORIO";
	public static final String LBL_OTRO_NIVEL_DECISORIO 			= "LBL_OTRO_NIVEL_DECISORIO";
	public static final String LBL_TOTAL_EP_REON 					= "LBL_TOTAL_EP_REON";

	public static final String TTL_HOME 		= "TTL_HOME";
	public static final String TTL_WELCOME 		= "TTL_WELCOME";
	public static final String TTL_BEGIN 		= "TTL_BEGIN";
	public static final String TTL_ABOUT 		= "TTL_ABOUT";
	public static final String TTL_LOGIN 		= "TTL_LOGIN";
	public static final String TTL_SUB_LOGIN 	= "TTL_SUB_LOGIN";

	public static final String TTL_SUB_TRANSVERSAL_USUARIO_RESTABLECER = "TTL_SUB_TRANSVERSAL_USUARIO_RESTABLECER";

	public static final String TTL_TRANSVERSAL_ADMINISTRAR_MAESTRO 						= "TTL_TRANSVERSAL_ADMINISTRAR_MAESTRO";
	public static final String TTL_SUB_TRANSVERSAL_ADMINISTRAR_MAESTRO_CREAR 			= "TTL_SUB_TRANSVERSAL_ADMINISTRAR_MAESTRO_CREAR";
	public static final String TTL_SUB_TRANSVERSAL_ADMINISTRAR_MAESTRO_AGREGAR_DETALLE 	= "TTL_SUB_TRANSVERSAL_ADMINISTRAR_MAESTRO_AGREGAR_DETALLE";
	public static final String LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_NOMBRE_TABLA 		= "LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_NOMBRE_TABLA";
	public static final String LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_ESTADO_TABLA 		= "LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_ESTADO_TABLA";
	public static final String LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_NOMBRE_REGISTRO 		= "LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_NOMBRE_REGISTRO";
	public static final String LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_ESTADO_REGISTRO 		= "LBL_TRANSVERSAL_ADMINISTRAR_MAESTRO_ESTADO_REGISTRO";

	public static final String LBL_CERTIFICADO_TITULO 			= "LBL_CERTIFICADO_TITULO";
	public static final String LBL_EVALUACION_DESEMPENO 		= "LBL_EVALUACION_DESEMPENO";
	
	public static final String TTL_GI_RESUMEN_CARGOS_PUBLICOS 	= "TTL_GI_RESUMEN_CARGOS_PUBLICOS";
	
	public static final String TTL_OK 								= "TTL_OK";
	public static final String TTL_BR_TAP_BIENES_PATRIMONIALES 		= "TTL_BR_TAP_BIENES_PATRIMONIALES";
	public static final String TTL_BR_TAP_CONSANGUINIDAD_CONYUGUES 	= "TTL_BR_TAP_CONSANGUINIDAD_CONYUGUES";
	public static final String TTL_BR_TAP_CUENTAS 					= "TTL_BR_TAP_CUENTAS";
	public static final String TTL_BR_TAP_ACREECNCIAS_OBLIGACIONES 	= "TTL_BR_TAP_ACREECNCIAS_OBLIGACIONES";
	public static final String TTL_BR_TAP_PARTICIPACION_JUNTAS 		= "TTL_BR_TAP_PARTICIPACION_JUNTAS";
	public static final String TTL_BR_TAP_ACTIVIDAD_ECONOMICA_PRIV 	= "TTL_BR_TAP_ACTIVIDAD_ECONOMICA_PRIV";
	public static final String TTL_BR_TAP_OTROS_INGRESOS_RENTAS 	= "TTL_BR_TAP_OTROS_INGRESOS_RENTAS";
	public static final String TTL_BR_TAP_INGRESOS_RENTAS 			= "TTL_BR_TAP_INGRESOS_RENTAS";
	public static final String TTL_BR_BTN_ADICIONAR 				= "TTL_BR_BTN_ADICIONAR";
	public static final String ENT_ESC_MSG_CARGOS_SIN_REASIGNAR 	= "ENT_ESC_MSG_CARGOS_SIN_REASIGNAR";
	public static final String LBL_PLANTA_BOTON_CREAR 				= "LBL_PLANTA_BOTON_CREAR";					
	public static final String LBL_PLANTA_BOTON_EDITAR 				= "LBL_PLANTA_BOTON_EDITAR";	
	public static final String TTL_GUARDAR_CAMBIOS 					= "TTL_GUARDAR_CAMBIOS";
	
	public static final String LBL_NOMENCLATURA_CREAR 							= "LBL_NOMENCLATURA_CREAR";
	public static final String TTL_ENTIDADES_NOMENCLATURA_MODIFICAR 			= "TTL_ENTIDADES_NOMENCLATURA_MODIFICAR";
	public static final String TTL_ENTIDADES_NOMENCLATURA_CONSULTAR 			= "TTL_ENTIDADES_NOMENCLATURA_CONSULTAR";
	public static final String TTL_INCREMENTAR_ESCALA_SALARIAL 					= "TTL_INCREMENTAR_ESCALA_SALARIAL";
	public static final String TTL_ENTIDADES_NOMENCLATURA_EQUIVALENCIAS 		= "TTL_ENTIDADES_NOMENCLATURA_EQUIVALENCIAS";
	public static final String TTL_ENTIDADES_NOMENCLATURA_ASOCIAR_NOMENCLATURA 	= "TTL_ENTIDADES_NOMENCLATURA_ASOCIAR_NOMENCLATURA";
	
	public static final String TTL_CONSOLIDADO_GESTION_ENT_I_100 		= "TTL_CONSOLIDADO_GESTION_ENT_I_100";
	public static final String TTL_CONSOLIDADO_GESTION_ENT_MAI_90 		= "TTL_CONSOLIDADO_GESTION_ENT_MAI_90";
	public static final String TTL_CONSOLIDADO_GESTION_ENT_MAI_80 		= "TTL_CONSOLIDADO_GESTION_ENT_MAI_80";
	public static final String TTL_CONSOLIDADO_GESTION_ENT_MAI_70 		= "TTL_CONSOLIDADO_GESTION_ENT_MAI_70";
	public static final String TTL_CONSOLIDADO_GESTION_ENT_REP_ASESOR 	= "TTL_CONSOLIDADO_GESTION_ENT_REP_ASESOR";
	
	public static final String TTL_EVALUACIONES_DE_DESEMPENIO_ACUERDOS 	= "TTL_EVALUACIONES_DE_DESEMPENIO_ACUERDOS";
	public static final String TTL_REVERSAR_APROBACION 					= "TTL_REVERSAR_APROBACION";
	public static final String TTL_REALIZAR_APROBACION_UTL 				= "TTL_REALIZAR_APROBACION_UTL";
	public static final String TTL_REALIZAR_APROBACION_UTL_IGUAL 		= "TTL_REALIZAR_APROBACION_UTL_IGUAL";
	
	public static final String LBL_CT_MSG_GUARDAR_CONTRATO_EXITO 			= "LBL_CT_MSG_GUARDAR_CONTRATO_EXITO";
	public static final String LBL_CT_MSG_CRITERIO_BUSQUEDA 				= "LBL_CT_MSG_CRITERIO_BUSQUEDA";
	public static final String LBL_CT_MSG_GUARDAR_CONTRATO_ERROR 			= "LBL_CT_MSG_GUARDAR_CONTRATO_ERROR";
	public static final String LBL_CT_MSG_TIENE_DERECHO_EXCLUSIVIDAD 		= "LBL_CT_MSG_TIENE_DERECHO_EXCLUSIVIDAD";
	public static final String LBL_CT_MSG_MODIFICAR_CONTRATO_EXITO 			= "LBL_CT_MSG_MODIFICAR_CONTRATO_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_ADICION_EXITO 			= "LBL_CT_MSG_GUARDAR_ADICION_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_PRORROGA_EXITO 			= "LBL_CT_MSG_GUARDAR_PRORROGA_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_ALCANCE_EXITO 			= "LBL_CT_MSG_GUARDAR_ALCANCE_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_SUSPENSION_EXITO 			= "LBL_CT_MSG_GUARDAR_SUSPENSION_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_TERMINACION_EXITO 		= "LBL_CT_MSG_GUARDAR_TERMINACION_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_CESION_EXITO 				= "LBL_CT_MSG_GUARDAR_CESION_EXITO";
	public static final String LBL_CT_MSG_GUARDAR_TERMINACION_MANUAL_EXITO 	= "LBL_CT_MSG_GUARDAR_TERMINACION_MANUAL_EXITO";
	public static final String LBL_CT_REVERSAR_CONTRATO 					= "LBL_CT_REVERSAR_CONTRATO";
	public static final String LBL_CT_REVERSAR_MODIFICACIONES 				= "LBL_CT_REVERSAR_MODIFICACIONES";
	public static final String LBL_CT_FRM_SUSPENSION 						= "LBL_CT_FRM_SUSPENSION";
	public static final String LBL_CT_FRM_LEVANTAR_SUSPENSION 				= "LBL_CT_FRM_LEVANTAR_SUSPENSION";
	public static final String LBL_CT_FECHA_FIN_SUSPENSION 					= "LBL_CT_FECHA_FIN_SUSPENSION";
	public static final String LBL_CT_FECHA_FIN_SUSPENSION_3 				= "LBL_CT_FECHA_FIN_SUSPENSION_3";
	public static final String DLG_CT_BUSQUEDA_EXITOSA 						= "DLG_CT_BUSQUEDA_EXITOSA";
	public static final String DLG_CT_MENSAJE_VIGENCIA_ANTERIOR 			= "DLG_CT_MENSAJE_VIGENCIA_ANTERIOR";
	public static final String DLG_CT_MSG_BUSQUEDA_NO_RESULTADOS 			= "DLG_CT_MSG_BUSQUEDA_NO_RESULTADOS";
	public static final String DLG_CT_MSG_NO_CONTRATOS_SIGEPII 				= "DLG_CT_MSG_NO_CONTRATOS_SIGEPII";
	public static final String DLG_CT_MSG_USUARIO_NO_AUTORIZADO_ACCION_SIGEPII = "DLG_CT_MSG_USUARIO_NO_AUTORIZADO_ACCION_SIGEPII";
	public static final String DLG_CT_MSG_CONTRATO_NO_ACTIVO 				= "DLG_CT_MSG_CONTRATO_NO_ACTIVO";
	public static final String DLG_CT_MSG_CONTRATO_DIF_USUENTIDAD 			= "DLG_CT_MSG_CONTRATO_DIF_USUENTIDAD";
	public static final String DLG_CT_MSG_CONTRATO_NO_REGISTRO_EXCLUSIVIDAD = "DLG_CT_MSG_CONTRATO_NO_REGISTRO_EXCLUSIVIDAD";
	public static final String LBL_CT_TITULO_MODIFICAR 						= "LBL_CT_TITULO_MODIFICAR";
	public static final String TTL_CT_CONSULTAR_CONTRATO 					= "TTL_CT_CONSULTAR_CONTRATO";
	
	public static final String LBL_ADD_NEW									= "LBL_ADD_NEW";
	public static final String LBL_INSTITUCION	 							= "LBL_INSTITUCION";
	public static final String BTN_TABLA_VER 								= "BTN_TABLA_VER";
	public static final String LBL_TRANSVERSAL_PERSONAS_PRIMER_NOMBRE 		= "LBL_TRANSVERSAL_PERSONAS_PRIMER_NOMBRE";
	public static final String LBL_TRANSVERSAL_PERSONAS_PRIMER_APELLIDO 	= "LBL_TRANSVERSAL_PERSONAS_PRIMER_APELLIDO";
	public static final String TTL_TIPO_DOCUMENTO 							= "TTL_TIPO_DOCUMENTO";
	public static final String LBL_CRITERIO_MINIMO 							= "LBL_CRITERIO_MINIMO";
	public static final String LBL_CONSULTA_NUEVAMENTE 						= "LBL_CONSULTA_NUEVAMENTE";
	public static final String TTL_REGISTRAR_SA 							= "TTL_REGISTRAR_SA";
	public static final String TTL_MODIFICAR_SA	 							= "TTL_MODIFICAR_SA";
	public static final String TTL_CONSULTAR_SA 							= "TTL_CONSULTAR_SA";
	public static final String LBL_PROCESO_EXITOSO 							= "LBL_PROCESO_EXITOSO";
	public static final String LBL_TOTAL_PLANTA_PERMANENTE 					= "LBL_TOTAL_PLANTA_PERMANENTE";
	public static final String LBL_TOTAL_PLANTA_TEMPORAL 					= "LBL_TOTAL_PLANTA_TEMPORAL";
	public static final String LBL_TOTAL_PLANTA_TRANSITORIA 				= "LBL_TOTAL_PLANTA_TRANSITORIA";
	public static final String LBL_TOTAL_TO_REON 							= "LBL_TOTAL_TO_REON";
	public static final String LBL_TOTAL_DOCENTES_REON 						= "LBL_TOTAL_DOCENTES_REON";
	public static final String LBL_TOTAL_EP_TO 								= "LBL_TOTAL_EP_TO";
	public static final String LBL_TOTAL_EP_DOCENTES 						= "LBL_TOTAL_EP_DOCENTES";
	public static final String LBL_TOTAL_EP_TO_REON 						= "LBL_TOTAL_EP_TO_REON";
	public static final String LBL_TOTAL_PLANTAS_PRIVADAS_REON 				= "LBL_TOTAL_PLANTAS_PRIVADAS_REON";
	public static final String LBL_TOTAL_EMPLEADOS_PLANTA_PRIVADA 			= "LBL_TOTAL_EMPLEADOS_PLANTA_PRIVADA";
	public static final String TTL_GI_TABLERO_FECHA_CORTE	 				= "TTL_GI_TABLERO_FECHA_CORTE";
	public static final String TTL_GI_TABLERO_ENTIDADES_REPORTAR 			= "TTL_GI_TABLERO_ENTIDADES_REPORTAR";
	public static final String LBL_CANTIDAD_ENTIDADES_ACUERDOS_GESTION 		= "LBL_CANTIDAD_ENTIDADES_ACUERDOS_GESTION";
	public static final String TTL_GI_TABLERO_PORCENTAJE_ENTIDADES_REPORT 	= "TTL_GI_TABLERO_PORCENTAJE_ENTIDADES_REPORT";
	public static final String LBL_NUMERO_GERENTES_P 						= "LBL_NUMERO_GERENTES_P";
	public static final String TTL_GI_TABLERO_CEP_BEI_CANTIDAD_SERVIDORES 	= "TTL_GI_TABLERO_CEP_BEI_CANTIDAD_SERVIDORES";
	public static final String TTL_GI_TABLERO_CEP_CAP_ENTIDADES_PIC 		= "TTL_GI_TABLERO_CEP_CAP_ENTIDADES_PIC";
	public static final String LBL_CT_ENTIDAD_YA_EXISTE_CONTRATO 			= "LBL_CT_ENTIDAD_YA_EXISTE_CONTRATO";
	public static final String 	TTL_REGISTRAR_CONTRATO_SIGEP 				= "TTL_REGISTRAR_CONTRATO_SIGEP";
	public static final String 	TTL_REGISTRARCONTRATO 						= "TTL_REGISTRARCONTRATO";
	public static final String 	LBL_REGRESAR 								= "LBL_REGRESAR";
	public static final String 	TTL_NUMERO 									= "TTL_NUMERO";
	public static final String 	LBL_CT_CONTRATOS_REGISTRADOS_SIGEPII 		= "LBL_CT_CONTRATOS_REGISTRADOS_SIGEPII";
	public static final String 	LBL_CON 									= "LBL_CON";
	public static final String 	TTL_CT_CORREGIR_CONTRATO 					= "TTL_CT_CORREGIR_CONTRATO";
	public static final String 	TTL_CT_PRORROGAR_CONTRATO 					= "TTL_CT_PRORROGAR_CONTRATO";
	public static final String 	TTL_CT_ADICION_PRORROGA_ALCANCE_CONTRATO 	= "TTL_CT_ADICION_PRORROGA_ALCANCE_CONTRATO";
	public static final String 	TTL_CT_MODIFICAR_ALCANCE_CONTRATO 			= "TTL_CT_MODIFICAR_ALCANCE_CONTRATO";
	public static final String 	BTN_CT_VOLVER 								= "BTN_CT_VOLVER";
	public static final String 	TTL_CT_ADICIONAR_CONTRATO 					= "TTL_CT_ADICIONAR_CONTRATO";
	public static final String 	TTL_SUSPENDERCONTRATO 						= "TTL_SUSPENDERCONTRATO";
	public static final String 	LBL_CT_QUITAR_SUSPENSION 					= "LBL_CT_QUITAR_SUSPENSION";
	public static final String 	TTL_CT_QUITAR_SUSPENDER_CONTRATO 			= "TTL_CT_QUITAR_SUSPENDER_CONTRATO";
	public static final String 	TTL_TAB_CT_REGISTRAR_TERMINACION 			= "TTL_TAB_CT_REGISTRAR_TERMINACION";
	public static final String 	TTL_CT_CESION_CONTRATO 						= "TTL_CT_CESION_CONTRATO";
	
	
	public static String getStringMessagesBundle(String key) {
		try {
			Control control = Control.getControl(Control.FORMAT_PROPERTIES);
			return ResourceBundle.getBundle(TITLES_BUNDLE_CONSTANTS, control).getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	public static String getStringMessagesBundle(String key, Locale locale) {
		try {
			Control control = Control.getControl(Control.FORMAT_PROPERTIES);
			return ResourceBundle.getBundle(TITLES_BUNDLE_CONSTANTS, locale, control).getString(key);
		} catch (Exception e) {
			return key;
		}
	}

	/**
	 * Devuelve un arreglo con los nombres de las kies del bundle que contengan
	 * el texto en value
	 * 
	 * @param value
	 *            Texto a buscar en los values de las kies del bundle
	 * @return {@link List} de {@link String} de las kies de
	 *         {@link ConfigurationBundleConstants} en cuyo valor contiene el
	 *         value en el paramentro ingresado
	 * @throws FileNotFoundException
	 *             Captura de excepcion en caso de que el bundle no pueda ser
	 *             ubicado
	 */
	public static List<String> getKies(String value, Locale locale) throws FileNotFoundException {
		List<String> result = new LinkedList<>();

		if (value == null || value.isEmpty()) {
			return result;
		}
		String valueTemp = value.toUpperCase();

		ResourceBundle p = ResourceBundle.getBundle(TITLES_BUNDLE_CONSTANTS);

		Enumeration<String> keys = p.getKeys();

		while (keys.hasMoreElements()) {
			String key = keys.nextElement();

			if (key.startsWith("TTL_")) {
				String valueResult = getStringMessagesBundle(key, locale);
				if (valueResult != null && !valueResult.isEmpty()) {
					valueResult = valueResult.toUpperCase();
					valueResult = StringUtil.reemplazarAcentos(valueResult);

					valueTemp = StringUtil.reemplazarAcentos(valueTemp);

					if (valueResult.contains(valueTemp) && !result.contains(key)) {
						result.add(key);
					}
				}
			}
		}
		return result;
	}

	public static Map<String, Object> getProperties(String startWith) {
		Map<String, Object> properties = new HashMap<>();

		ResourceBundle p = ResourceBundle.getBundle(TITLES_BUNDLE_CONSTANTS);
		while (p.getKeys().hasMoreElements()) {
			String key = p.getKeys().nextElement();
			if (key.startsWith(startWith)) {
				properties.put(key, p.getObject(key));
			}
		}
		return properties;
	}
}
