/**
 * 
 */
package co.gov.dafp.sigep2.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import co.gov.dafp.sigep2.services.TokenService;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  UtilsConstantes.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class UtilsConstantes implements Serializable {

	private static final long serialVersionUID = 7424434494893872452L;
	
	public final static int 	LIMITINIT = 0;
	public final static int 	LIMITIEND = 100;
	public final static int 	MAX_REG = 1000;
	protected static Base64.Encoder enc;
	protected static Base64.Decoder dec;
	public final static String UTF_8 = "UTF-8";
	public final static String  FORMATO_FECHA = "dd/MM/yyyy";
	public final static String  FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
	public final static String  MSG_GUARDADO_CON_EXITO = "Registro Almacenado Existosamente.";
	public final static String  MSG_ELIMINADO_CON_EXITO = "Registro Eliminado Existosamente.";
	public final static String  MSG_ACTUALIZACION_CON_EXITO = "Actualizacion Existosa.";
	public final static String  MSG_NO_GUARDADO_CON_EXITO = "Registro No Almacenado Existosamente.";
	public final static String  MSG_NO_ACTUALIZACION_CON_EXITO = "Actualizacion no Realizada";
	public final static String  MSG_EXEPCION = "Se ha Producido un Error Inesperado Verifique el Mensaje Tecnico.";
	public final static String  MSG_OBLIGATORIOS = "Hace falta tipo de operacion AudAccion";
	public final static String  MSG_NO_SOPORTADO = "Operacion No Soportada AudAccion";
	public final static String  MSG_ELIMINACION_CON_EXITO = "Eliminacion Existosa.";
	public final static String  MSG_NO_ELIMINADO_CON_EXITO = "No Eliminado Existosamente.";
	public final static String  MSG_ERROR_SISTEMA_PARAMETRICA_NO_EXISTE = "Error del siestema Parametrica #param no existe";
	public final static String  MSG_ERROR_FALTA_DATO_OBIGATORIO= "Error Faltan Datos Obligatorios";
	public final static String  MSG_PERSONA_NO_VINCULADA= "La persona consultada no se encuentra vinculada a una Entidad del Sistema.";
	public final static String  MSG_VALOR_ENVIADO_NO_VALIDO= "El valor enviado no es valido";
	public final static String  MSG_USUARIO_INACTIVO= "Usuario En estado Inactivo";
	
	public final static String  MSG_ES_REQUIERE_NOMBRE_USUARIO = "MSG_ES_REQUIERE_NOMBRE_USUARIO";
	public final static String  MSG_US_REQUIERE_NOMBRE_USUARIO = "MSG_US_REQUIERE_NOMBRE_USUARIO";
	public final static String  MSG_ES_REQUIERE_TIPO_IDENTIFICACION = "MSG_ES_REQUIERE_TIPO_IDENTIFICACION";
	public final static String  MSG_US_REQUIERE_TIPO_IDENTIFICACION = "MSG_US_REQUIERE_TIPO_IDENTIFICACION";
	public final static String  MSG_ES_REQUIERE_CONTRASENA = "MSG_ES_REQUIERE_CONTRASENA";
	public final static String  MSG_US_REQUIERE_CONTRASENA = "MSG_US_REQUIERE_CONTRASENA";
	public final static String  MSG_ES_USUARIO_BLOQUEADO = "MSG_ES_USUARIO_BLOQUEADO";
	public final static String  MSG_US_USUARIO_BLOQUEADO = "MSG_US_USUARIO_BLOQUEADO";
	public final static String  MSG_ES_USUARIO_INACTIVO = "MSG_ES_USUARIO_INACTIVO";
	public final static String  MSG_ES_USUARIO_EXIPRO = "MSG_USUARIO_EXPIRO";
	public final static String  MSG_US_USUARIO_INACTIVO = "TMSG_US_USUARIO_INACTIVO";
	public final static String  MSG_ES_ERROR_PARAM_LOGIN = "MSG_ES_ERROR_PARAM_LOGIN";
	public final static String  MSG_US_ERROR_PARAM_LOGIN = "MSG_US_ERROR_PARAM_LOGIN";
	public final static String  MSG_ES_CONTRASENA_NO_COINCIDE = "MSG_ES_CONTRASENA_NO_COINCIDE";
	public final static String  MSG_US_CONTRASENA_NO_COINCIDE = "MSG_US_CONTRASENA_NO_COINCIDE";
	public final static String  MSG_ES_CUENTA_NO_EXISTE = "MSG_ES_CUENTA_NO_EXISTE";
	public final static String  MSG_US_CUENTA_NO_EXISTE = "MSG_US_CUENTA_NO_EXISTE";
	public final static String  CNS_TIMEOUT_SESION_APP_MOVIL = "2056";
	public final static String  TIMEOUT_SESION = "1800000";
	public final static String  MSG_USUARIO_EXTERNO = "Consulte con el Administrador del Sistema, ya que este Usuario esta configurado como Interno y no tienen acceso a estos Servicios";
	public final static String  MSG_USUARIO_ROL = "Consulte con el Administrador del Sistema, el Usuario que intenta autenticarse esta configurado con un Rol que no le permite hacer uso de este Sistema";
	public final static String  MSG_VALOR_VACIO = "Debe enviar un valor no se permiten campos vacios";
	public final static String  MSG_CONTRASENA_INVALIDA = "La contraseña no puede ser igual a la anterior";
	
	public final static String MSG_ERROR_ROW = "Error en el fila: ";
	public final static String MSG_ARCHIVO = "del archivo";
	public final static String MSG_ERROR_PROCESO = "No ha sido procesado dado que contiene errores.";
	
	public final static String  MSG_ES_TIPO_DOCUMENTO_INVALIDO = "2202";
	public final static String  MSG_US_TIPO_DOCUMENTO_INVALIDO = "2203";
	
	public final static String MSG_ERROR_TOKEN="{\"error\":true,\"mensaje\":\"Token Expiro Sesion Invalida\", \"codigo\":403}";
	public final static String MSG_ERROR_406="{\"error\":true, \"mensaje\":\"Invalid typeResponse -> #typeResponse\",\"codigo\":406}";
	public final static String MSG_FALTA_NUM_DOCUMENTO="Hace Falta Numerodocumento()";
	public final static String MSG_FALTA_TIPO_DOCUMENTO="Hace Falta TipoDocumento()";
	public final static String MSG_FALTA_TIPO_NUM_DOCUMENTO="Hace Falta TipoDocumento(), Numerodocumento()";
	public final static String MSG_FALTA_TIPO_DECLARACION="Hace Falta TipoDeclaracion()";
	public final static String MSG_ENCODE_XML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public final static String MSG_RESULTADO_TR="resultadoTransaccion";
	public final static String MSG_ERROR_TR="error";
	public final static String MSG_CODIGO_BUS="BUS";
	public final static String MSG_SIN_INFORMACION_USUARIO="No Se encontró Información para este Usuario";
	public final static String MSG_FALTA_TIPO_ID="Tipo Identificación  No Enviado";
	public final static String MSG_NO_EXISTE_TIPO_ID="TipoDocumento no existe";
	public final static String MSG_NO_ENVIADO_ESTADO_CIVIL="EstadoCivil  No Enviado";
	public final static String MSG_NO_EXITE_ESTADO_CIVIL="EstadoCivil no existe";
	public final static String MSG_NO_ENVIADO_GENERO="Genero  No Enviado";
	public final static String MSG_NO_EXISTE_GENERO="Genero no existe";
	public final static String MSG_ERROR_TECNICO="Ocurrio Error Tecnico.";
	public final static String MSG_FALTA_CODSIGEP="Hace Falta CodigoSigep()";
	public final static String MSG_NO_EXISTE_CODSIGEP="CodigoSigep no Existe";
	public final static String MSG_FALTA_COD_VINCULACION="Hace Falta codVinculacion()";
	public final static String MSG_NO_EXISTE_COD_VINCULACION="CodVinculacion  No Existe";
	public final static String MSG_NO_EXISTE_COD_INCAPACIDAD="CodTipoIncapacidad  No Existe";
	public final static String MSG_FALTA_COD_SITUACION="Hace Falta codSituacionAdministrativa()";
	public final static String MSG_NO_EXISTE_COD_SITUACION="CodSituacionAdministrativa  No Existe";
	public final static String MSG_FALTA_TIPO_ACTO_ADMON="Hace Falta tipoActoAdministrativo()";
	public final static String MSG_NO_EXISTE_TIPO_ACTO_ADMON="TipoActoAdministrativo no existe";
	public final static String MSG_FALTA_FECHA_INICIO="Hace Falta fechaInicio()";
	public final static String MSG_NO_EXISTE_TIPO_DECLA="TipoDeclaracion no existe";
	public final static String MSG_FALTA_COD_PLANTA_DETA="CodEntidadPlantaDetalle  No Enviado";
	public final static String MSG_NO_EXISTE_COD_PLANTA_DETA="CodEntidadPlantaDetalle  No Existe";
	public final static String MSG_NO_EXISTE_COD_DEPENDENCIA="CodDependenciaEntidad  No Existe";
	public final static String MSG_FALTA_COD_DEPENDENCIA="CodDependenciaEntidad  No Enviado";
	public final static String MSG_FALTA_TIPO_NOMBRA="Hace Falta TipoNombramiento()";
	public final static String MSG_NO_EXISTE_TIPO_NOMBRA="TipoNombramiento no existe";
	public final static String MSG_FALTA_TITULARIDAD_CARGO="FlgTitularidadCargo  No Enviado";
	public final static String MSG_FALTA_CAUSAL_DESVINCULACION="nombreCausalDesv  No Enviado";
	public final static String MSG_FALTA_NUMERO_ACTO_ADMON="NumActoAdminDesvinculacion  No Enviado";
	public final static String MSG_FALTA_FACTOR_RH="FactorRh  No Enviado";
	public final static String MSG_NO_EXISTE_FACTOR_RH="FactorRh no existe";
	public final static String MSG_NO_EXISTE_CAUSAL_DESVIN="nombreCausalDesv no existe";
	public final static BigDecimal OP_CERTIFICADO_INFORMACION = new BigDecimal(2268);
	public final static BigDecimal OP_CERTIFICADO_INFORMACION_CONTRATISTA = new BigDecimal(2269);
	public final static String MSG_NO_VINCULADO_NO_CONTRATO="El Usuario no Presenta Vinculacion con la Entidad Consultada Ni Contratos Vigentes";
	public final static String MSG="mensaje";
	public final static String MSG_CERTIFICADO="textoCertificado";
	public final static String MSG_DE_DATOS="Error de Inconcistencia de Datos Consultar el Administrador";
	public final static String MSG_FECHA_POSECION="No Se dispone de Fecha";
	public final static String MSG_TOKEN_INVALIDO="ERROR TOKEN INVALID PARA ESTA OPERACION";
	public final static String MSG_DESVIMCULACION_OK="Se realizo la Desvinculacion Exitosamente";
	public final static String MSG_ERROR_USUARIO_ENTIDAD="El Token Usado para esta Operacion no tiene Autorizacion Pertenece a Otra Entidad.";
	public final static String NIVEL_EDUCATIVO_NO_ENVIADO=" --- NivelEducativo  No Enviado";
	public final static String NIVEL_EDUCATIVO_NO_EXISTE=" --- NivelEducativo no existe ";
	public final static String AREA_CONOCIMIENTO_NO_ENVIADO=" --- AreaConocimiento  No Enviado";
	public final static String AREA_CONOCIMIENTO_NO_EXISTE=" --- AreaConocimiento  No Existe";
	public final static String EXPERIENCIA_DOCENTE=" ExperienciaDocente ";
	public final static String JORNADA_LABORAL_NO_ENVIADO=" --- JornadaLaboral  No Enviado";
	public final static String JORNADA_LABORAL_NO_EXISTE=" --- JornadaLaboral  No Existe";
	public final static String MOTIVO_RETIRO_NO_ENVIADO=" --- MotivoRetiro  No Enviado";
	public final static String MOTIVO_RETIRO_NO_EXISTE=" --- MotivoRetiro  No Existe";
	public final static String TIPO_ENTIDAD_NO_ENVIADO=" --- TipoEntidad  No Enviado";
	public final static String TIPO_ENTIDAD_NO_EXISTE=" --- TipoEntidad  No Existe";
	public final static String EXPERIENCIA_PROFESIONAL="ExperienciaProfesional ";
	public final static String NIVEL_FORMACION_NO_ENVIADO=" --- NivelFormacion  No Enviado";
	public final static String NIVEL_FORMACION_NO_EXISTE="--- NivelFormacion  No Existe";
	public final static String EDUCACION_FORMAL="EducacionFormal";
	public final static String TIPO_DISCAPACIDAD_NO_EXISTE=" ---TipoDiscapacidad no Existe ";
	public final static String CABEZA_HOGAR_NO_ENVIADO=" ---CabezaHogar  No Enviado";
	public final static String CABEZA_HOGAR_NO_EXISTE=" ---CabezaHogar  No Existe";
	public final static String ORIENTACION_SEXUAL_NO_ENVIADO=" --- OrientacionSexual  No Enviado";
	public final static String ORIENTACION_SEXUAL_NO_EXISTE=" --- OrientacionSexual  No Existe";
	public final static String VICTIMA_DESPLAZAMIENTO_NO_ENVIADO=" --- VictimaDesplazamiento  No Enviado";
	public final static String VICTIMA_DESPLAZAMIENTO_NO_EXISTE=" --- VictimaDesplazamiento  No Existe";
	public final static String DATO_ADICIONAL="DatoAdicional";
	public final static String NOMBRE_TIPO_ZONA_NO_ENVIADO=" --- NombreTipoZona  No Enviado";
	public final static String NOMBRE_TIPO_ZONA_NO_EXISTE=" --- NombreTipoZona  No Existe";
	public final static String DATO_CONTACTO="DatoContacto";
	public final static String CODIGO_PAIS_NO_ENVIADO="Codigo Iso del Pais No Enviado";
	public final static String CODIGO_PAIS_NO_EXISTE="Codigo Iso del Pais No Existe";
	public final static String CODIGO_DANE_DEPTO_NO_ENVIADO="Codigo Dane Departamento No Enviado";
	public final static String CODIGO_DANE_DEPTO_NO_EXISTE="Codigo Dane Departamento No Existe";
	public final static String CODIGO_DANE_MUN_NO_ENVIADO="Codigo Dane Municipio No Enviado";
	public final static String CODIGO_DANE_MUN_NO_EXISTE="Codigo Dane Municipio No Existe";
	public final static String CODIGO_INT_EDICATIVA_MEN_NO_ENVIADO="Codigo Institucion Educativa Men No Enviado";
	public final static String CODIGO_INT_EDICATIVA_MEN_NO_EXISTE="Codigo Institucion Educativa Men No Existe";
	public final static String CODIGO_PROGRAMA_ACADEMICO_NO_ENVIADO="Codigo Programa Academico No Enviado";
	public final static String CODIGO_PROGRAMA_ACADEMICO_NO_EXISTE="Codigo Programa Academico No Existe";
	public final static String IDENTIFICACION_NO_EXISTE="Identificacion: #numero No existe";
	public final static String ESTIDAD_NO_ACTIVA="La Entidad #nombre, se encuentra en Estado: #estado, por lo tando no se puede vincular";
	public final static String ESTIDAD_NO_ACTIVA_ERROR="Error al validar el estado de la entidad, no es posible continuar con la Vinculacion";
	public final static String ENTIDAD_NO_TIENE_PLANTA="En el momento la Entidad No tiene Plantas Disponibles para la Vinculacion del Funcionario";
	public final static String HOJA_DE_VIDA_NO_ACTIVA="El Usuario No Tiene Hoja de Vida Activa";
	public final static String HOJA_DE_VIDA_NO_APROBADA="La Hoja de Vida del Usurio Debe ser Aprobada";
	public final static String USUARIO_UTL="El Usuario Pertenese a una Utl Uan.";
	public final static String USUARIO_NO_TIENE_ROL="El Usuario No Tiene un Rol Asociado a la Entidad a la Cual se Intenta Vincular.";
	public final static String USUARIO_TIENE_CONTRATO_VIGENTE="Usuario Tiene contratos activos con una entidad no puede ser vinculado";
	public final static String VINCULACION_EXITOSA="Se ha realizado la vinculacion Exitosamente";
	public final static String VINCULACION_EXISTENTE="Usuario ya cuenta con una vinculaciona activa y no es Docente o Medico";
	public final static String PARAMETRICA_EXISTE="La Parametrica que intenta ingresar ya existe en el sistema Intentelo de Nuevo";
	public final static String ENFERMEDAD_ID_REPETIDO="2";
	public final static String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@";
	private static final String ORIGINAL     												= "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT  												= "AaEeIiOoUuNnUu";
	
	
	public static boolean tokenIsValid(String token, String timeout) {
		TokenService service = new TokenService();
		if(token == null) {
			return false;
		}
		return service.isValid(token,timeout);
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 8:51:35 a.m.
	* @return
	*
	 */
	public static String generarPing(){
		String data = "";
		String res = "";
		data =  datoAleatrorio();
		if(data.length() > 6) {
			data = data.substring(0, 6);
		}
		int inicio = 0;
		int fin    = 5;
	    for (int x = 0; x < 5 ; x++) {
	    	if(x%2 == 0) {
	    		res += String.valueOf(data.charAt(inicio));
	    		inicio++;
	    	}else {
	    		res += String.valueOf(data.charAt(fin));
	    		fin--;
	    	}
	    }
       return res;
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @return
	 * @Fecha :Mar 7, 2019
	 * UtilsConstantes.java
	 */
	public static String generarPingSms(){
		String data = "";
		String res = "";
		data =  datoAleatrorioSms();
		if(data.length() > 11) {
			data = data.substring(0, 10);
		}
		int inicio = 0;
		int fin    = 9;
	    for (int x = 0; x < 9 ; x++) {
	    	if(x%2 == 0) {
	    		res += String.valueOf(data.charAt(inicio));
	    		inicio++;
	    	}else {
	    		res += String.valueOf(data.charAt(fin));
	    		fin--;
	    	}
	    }
       return res;
	}
	/**
	 * 
	 * @return
	 */
	public static String datoAleatrorio(){
		  String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		  Date date = new Date();
		  SimpleDateFormat formatDate = new SimpleDateFormat("ddSS");
		  int rd = 0;
		  String resul ="";
		  for (int i = 0; i < 3; i++) {
			  rd = (int) (Math.random() *base.length());
			  resul+=base.charAt(rd);
		  }
		  String data = resul+""+formatDate.format(date);
		  return data;
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @return
	 * @Fecha :Mar 7, 2019
	 * UtilsConstantes.java
	 */
	public static String datoAleatrorioSms(){
		  String base = "ABCD*EFGHIJK*LMNOPQRSTU*VWXYZabcd*efghijklmnopq*";
		  Date date = new Date();
		  SimpleDateFormat formatDate = new SimpleDateFormat("ddSS");
		  int rd = 0;
		  String resul ="";
		  for (int i = 0; i < 6; i++) {
			  rd = (int) (Math.random() *base.length());
			  resul+=base.charAt(rd);
		  }
		  String data = resul+""+formatDate.format(date);
		  return data;
	}
	
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 21, 2019
	 * UtilsConstantes.java
	 * @param data
	 * @return
	 */
	public static String parseIntegerToBoolean(String data) {
		  data = data.trim();
		  data = data.replace("\"flgFinalizado\":1","\"flgFinalizado\":true");
		  data = data.replace("\"flgEducacionExterior\":1","\"flgEducacionExterior\":true");
		  data = data.replace("\"flgActualmente\":1", "\"flgActualmente\":true");
		  data = data.replace("\"flgVerificado\":1", "\"flgVerificado\":true");
		  data = data.replace("\"flgEducacionExterior\":1", "\"flgEducacionExterior\":true");
		  data = data.replace("\"estudioConvalidado\":1", "\"estudioConvalidado\":true");
		  data = data.replace("\"flgValidadoRrhh\":1", "\"flgValidadoRrhh\":true");
		  data = data.replace("\"flgPrepensionado\":1", "\"flgPrepensionado\":true");
		  data = data.replace("\"flgDiscapacidad\":1",  "\"flgDiscapacidad\":true");
		  data = data.replace("\"flgVictimaConflicto\":1", "\"flgVictimaConflicto\":true");
		  data = data.replace("\"expuestoPoliticamente\": ", "\"expuestoPoliticamente\":true");
		  data = data.replace("\"flgMedicoDocente\":1",  "\"flgMedicoDocente\":true");
		  data = data.replace("\"flgTitularidadCargo\":1",   "\"flgTitularidadCargo\":true");
		  data = data.replace("\"flgExtemporanea\":1",   "\"flgExtemporanea\": true");
		  data = data.replace("\"flgSociedadConyugalActiva\":1",   "\"flgSociedadConyugalActiva\":true");
		  data = data.replace("\"flgAprobado\":1", "\"flgAprobado\":true");
		  data = data.replace("\"flgActivo\":1", "\"flgActivo\":true");
		  data = data.replace("\"flgPEP\":1", "\"flgPEP\":true");

		  data = data.replace("\"flgFinalizado\":0","\"flgFinalizado\":false");
		  data = data.replace("\"flgEducacionExterior\":0","\"flgEducacionExterior\":false");
		  data = data.replace("\"flgActualmente\":0", "\"flgActualmente\":false");
		  data = data.replace("\"flgVerificado\":0", "\"flgVerificado\":false");
		  data = data.replace("\"flgEducacionExterior\":0", "\"flgEducacionExterior\":false");
		  data = data.replace("\"estudioConvalidado\":0", "\"estudioConvalidado\":false");
		  data = data.replace("\"flgValidadoRrhh\":0", "\"flgValidadoRrhh\":false");
		  data = data.replace("\"flgPrepensionado\":0", "\"flgPrepensionado\":false");
		  data = data.replace("\"flgDiscapacidad\":0",  "\"flgDiscapacidad\":false");
		  data = data.replace("\"flgVictimaConflicto\":0", "\"flgVictimaConflicto\":false");
		  data = data.replace("\"expuestoPoliticamente\":0", "\"expuestoPoliticamente\":false");
		  data = data.replace("\"flgMedicoDocente\":0",  "\"flgMedicoDocente\":false");
		  data = data.replace("\"flgTitularidadCargo\":0",   "\"flgTitularidadCargo\":false");
		  data = data.replace("\"flgExtemporanea\":0",   "\"flgExtemporanea\":false");
		  data = data.replace("\"flgSociedadConyugalActiva\":0",   "\"flgSociedadConyugalActiva\":false");
		  data = data.replace("\"flgAprobado\":0", "\"flgAprobado\":false");
		  data = data.replace("\"flgActivo\":0", "\"flgActivo\":false");
		  data = data.replace("\"flgPEP\":0", "\"flgPEP\":false");
		  
		  data = data.replace("imagenHojaVidaUrl", "hojaDeVidaURL");
		  return data;
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 21, 2019
	 * UtilsConstantes.java
	 * @param data
	 * @return
	 */
	public static String parseBooleanToInteger(String data) {
		  data = data.trim();
		  data = data.replace("\"flgFinalizado\":true","\"flgFinalizado\":1");
		  data = data.replace("\"flgEducacionExterior\":true","\"flgEducacionExterior\":1");
		  data = data.replace("\"flgActualmente\":true", "\"flgActualmente\":1");
		  data = data.replace("\"flgVerificado\":true", "\"flgVerificado\":1");
		  data = data.replace("\"flgEducacionExterior\":true", "\"flgEducacionExterior\":1");
		  data = data.replace("\"estudioConvalidado\":true", "\"estudioConvalidado\":1");
		  data = data.replace("\"flgValidadoRrhh\":true", "\"flgValidadoRrhh\":1");
		  data = data.replace("\"flgPrepensionado\":true", "\"flgPrepensionado\":1");
		  data = data.replace("\"flgDiscapacidad\":true",  "\"flgDiscapacidad\":1");
		  data = data.replace("\"flgVictimaConflicto\":true", "\"flgVictimaConflicto\":1");
		  data = data.replace("\"expuestoPoliticamente\":true", "\"expuestoPoliticamente\":1");
		  data = data.replace("\"flgMedicoDocente\":true",  "\"flgMedicoDocente\":1");
		  data = data.replace("\"flgTitularidadCargo\":true",   "\"flgTitularidadCargo\":1");
		  data = data.replace("\"flgExtemporanea\":true",   "\"flgExtemporanea\":1");
		  data = data.replace("\"flgSociedadConyugalActiva\": true",   "\"flgSociedadConyugalActiva\":1");
		  data = data.replace("\"flgAprobado\":true", "\"flgAprobado\":1");
		  data = data.replace("\"flgActivo\":true", "\"flgActivo\":1");
		  data = data.replace("\"flgPEP\":true", "\"flgPEP\":1");

		  data = data.replace("\"flgFinalizado\":false","\"flgFinalizado\":0");
		  data = data.replace("\"flgEducacionExterior\":false","\"flgEducacionExterior\":0");
		  data = data.replace("\"flgActualmente\":false", "\"flgActualmente\":0");
		  data = data.replace("\"flgVerificado\":false", "\"flgVerificado\":0");
		  data = data.replace("\"flgEducacionExterior\":false", "\"flgEducacionExterior\":0");
		  data = data.replace("\"estudioConvalidado\":false", "\"estudioConvalidado\":0");
		  data = data.replace("\"flgValidadoRrhh\":false", "\"flgValidadoRrhh\":0");
		  data = data.replace("\"flgPrepensionado\":false", "\"flgPrepensionado\":0");
		  data = data.replace("\"flgDiscapacidad\":false",  "\"flgDiscapacidad\":0");
		  data = data.replace("\"flgVictimaConflicto\":false", "\"flgVictimaConflicto\":0");
		  data = data.replace("\"expuestoPoliticamente\":false", "\"expuestoPoliticamente\":0");
		  data = data.replace("\"flgMedicoDocente\":false",  "\"flgMedicoDocente\":0");
		  data = data.replace("\"flgTitularidadCargo\":false",   "\"flgTitularidadCargo\":0");
		  data = data.replace("\"flgExtemporanea\":false",   "\"flgExtemporanea\":0");
		  data = data.replace("\"flgSociedadConyugalActiva\":false",   "\"flgSociedadConyugalActiva\":0");
		  data = data.replace("\"flgAprobado\":false", "\"flgAprobado\":0");
		  data = data.replace("\"flgActivo\":false", "\"flgActivo\":0");
		  data = data.replace("\"flgPEP\":false", "\"flgPEP\":0");
		  return data;
	}
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String stripAccents(String str) {
		if (str == null) {
		    return null;
		}
		char[] array = str.toCharArray();
		for (int index = 0; index < array.length; index++) {
		    int pos = ORIGINAL.indexOf(array[index]);
		    if (pos > -1) {
		        array[index] = REPLACEMENT.charAt(pos);
		    }
		}
		return new String(array);
    }
	
	
	public static String encodeBase64(byte[] str) {
		enc = Base64.getEncoder();
		try {
			byte[] strenc = enc.encode(str);
			return new String(strenc, UTF_8);
		} catch (UnsupportedEncodingException e) {
			return new String();
		}
	}

	public static String decodeBase64(String strenc) {
		dec = Base64.getDecoder();
		try {
			byte[] strdec = dec.decode(strenc);
			return new String(strdec, UTF_8);
		} catch (UnsupportedEncodingException e) {
			return new String();
		}

	}
	
}
