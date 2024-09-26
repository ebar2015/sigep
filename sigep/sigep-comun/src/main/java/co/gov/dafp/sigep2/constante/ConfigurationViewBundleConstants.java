package co.gov.dafp.sigep2.constante;

import java.util.ResourceBundle;

public final class ConfigurationViewBundleConstants {

	private ConfigurationViewBundleConstants() {
	}

	private static final String CONFIGURACION_BUNDLE = "co.gov.dafp.sigep2.configurationView";

	public static final String CNS_CHARSET 				= "CNS_CHARSET";
	public static final String CNS_ENCODING 			= "CNS_ENCODING";
	public static final String CNS_LOGO 				= "CNS_LOGO";
	public static final String CNS_MINISTERIO			= "CNS_MINISTERIO";
	public static final String CNS_SLOGAN 				= "CNS_SLOGAN";
	public static final String CNS_DAFP 				= "CNS_DAFP";
	public static final String CNS_CSS 					= "CNS_CSS";
	public static final String CNS_BUILDER_WEB 			= "CNS_BUILDER_WEB";
	public static final String CNS_BUILDER_NAME 		= "CNS_BUILDER_NAME";

	public static String getStringMessagesBundle(String key) {
		return ResourceBundle.getBundle(CONFIGURACION_BUNDLE).getString(key);
	}
}
