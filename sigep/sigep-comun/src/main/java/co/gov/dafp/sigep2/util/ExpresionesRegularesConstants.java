package co.gov.dafp.sigep2.util;

public final class ExpresionesRegularesConstants {

	private ExpresionesRegularesConstants() {
	}

	public static final String REGEX_NUMBERS = "\\d+";
	public static final String REGEX_TEXT_ONLY = "^[a-zA-Z ]*$";
	public static final String REGEX_TEXT_TILDES_ONLY = "^[a-zA-Z ñÑáéíóúÁÉÍÓÚ]*$";
	public static final String REGEX_TEXT_TILDES_ALFANUMERICO = "^[a-zA-Z 0-9 ñÑáéíóúÁÉÍÓÚ]*$";
	public static final String REGEX_CUENTA_BANCARIA = "[0-9-_\\s]+";
	public static final String REGEX_PLAN_CUENTA_NOMBRE = "[A-Z\\s]+";
	public static final String REGEX_DIRECCION_IP = "[0-255].[0-255].[0-255].[0-255]";
	public static final String REGEX_DIRECCION_MAC = "[0-255].[0-255].[0-255].[0-255]+";
	public static final String REGEX_NOMBRE_PLANTILLA_ARCHIVO_CARGUE = "[a-zA-Z ñÑáéíóúÁÉÍÓÚ 0-9]+.xml";
	public static final String REGEX_NUMERO_HEXAGECIMAL = "0[xX][0-9a-fA-F]+";
	public static final String REGEX_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String REGEX_URL = "^(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{2,3}.?([a-z]+)?";
	public static final String REGEX_URL_PROTOCOL = "(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{2,3}.?([a-z]+)?";
	public static final String REGEX_PATH_WINDOWS = "^[A-Za-z]{1}:/([A-Za-z0-9._]+[/]{1})+$";
	public static final String REGEX_PATH_FTP = "^(ftp|ftps|FTP|FTPS)[/]{2}([A-Za-z0-9._]+[/.]{1})+$";
	public static final String REGEX_PATH_NETWORK = "^[/]{2}([A-Za-z0-9._]+[/.]{1})+$";
	public static final String REGEX_NICK_NAME = "^([0-9]{3,17}|sistema|superadmin)+$";
	public static final String REGEX_NICK_NAME_PASSPORT = "^([A-Za-z0-9]{3,17}|sistema|superadmin)+$";
	public static final String ONLY_NUMBER = "^-?[0-9]+$";
	public static final String REGEX_ALFANUMERICO = "^[a-zA-Z 0-9]+$";
	public static final String REGEX_ALFA_NUM_ESPECIALES = "^[a-zA-Z 0-9 ñÑáéíóúÁÉÍÓÚ @ (\\- /._,:)]+$" ;
	public static final String REGEX_DECIMAL_ONLY = "^-?[0-9]+.[0-9]+$";
	
}
