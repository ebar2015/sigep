package co.gov.dafp.sigep2.util;

import java.util.LinkedList;
import java.util.List;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

public class StringPassword {
	private StringPassword() {
	}

	public static List<String> validarPassword(String password1, String password2) {
		List<String> validates = new LinkedList<>();
		String pas1 = password1;
		String pas2 = password2;
		// pas1 = password1;
		// pas2 = password2;

		String er = ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR);
		int lenMin = ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO);
		int lenMax = ConfigurationBundleConstants.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MAXIMO);
		er = er.replace(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS,
				ConfigurationBundleConstants
						.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS));
		er = er.replace(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO, String.valueOf(lenMin));
		er = er.replace(ConfigurationBundleConstants.PASS_VALIDATE_MAXIMO, String.valueOf(lenMax));
		int minimoMayusculas = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO_MAYUSCULAS);
		int minimoNumeros = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO_NUMEROS);
		int minimoSimbolos = ConfigurationBundleConstants
				.getInt(ConfigurationBundleConstants.PASS_VALIDATE_MINIMO_SIMBOLOS);
		String erSimbolos = "[" + ConfigurationBundleConstants
				.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS) + "]";

		if (pas1.isEmpty() || pas2.isEmpty()) {
			validates.add(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_INVALIDOS));
		}

		if (!pas1.equals(pas2)) {
			validates.add(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_NO_COINCIDEN));
		}

		if (lenMin > 0 && pas1.length() < lenMin) {
			validates.add(MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_REQ_MIN_SEG));
		}

		if (lenMax >= 0 && pas1.length() > lenMax) {
			if (lenMax >= lenMin) {
				validates.add(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_REQ_MIN_SEG));
			} else if (lenMax < lenMin) {
				return validates;
			}
		}

		if (validates.isEmpty()) {

			if (!er.startsWith("[")) {
				er = "[" + er;
			}

			if (pas1.matches(er)) {
				char clave;
				byte conNumero = 0;
				byte conSimbolos = 0;
				byte conMayusculas = 0;
				byte invalido = 0;
				for (byte i = 0; i < pas1.length(); i++) {
					clave = pas1.charAt(i);
					pas2 = String.valueOf(clave);
					if (pas2.matches("[A-Z]")) {
						conMayusculas++;
					} else if (pas2.matches("[0-9]")) {
						conNumero++;
					} else if (pas2.matches(erSimbolos)) {
						conSimbolos++;
					} else if (!pas2.matches("[a-z]")) {
						invalido++;
					}
				}

				if (invalido > 0) {
					validates.add(MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CHAR_INVALIDOS));
				}

				if (minimoMayusculas >= 0 && conMayusculas < minimoMayusculas) {
					validates.add(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_MIN_MAYUSCULAS) + " " + minimoMayusculas);
				}

				if (minimoSimbolos >= 0 && conSimbolos < minimoSimbolos) {
					validates.add(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_MIN_SIMBOLOS) + " " + minimoSimbolos);
				}

				if (minimoNumeros >= 0 && conNumero < minimoNumeros) {
					validates.add(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_MIN_NUMEROS) + " " + minimoNumeros);
				}
			} else {
				validates.add(MessagesBundleConstants
						.getStringMessagesBundle(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_INVALIDOS)
						+ " "
						+ ConfigurationBundleConstants
								.getString(ConfigurationBundleConstants.PASS_VALIDATE_EXPRESION_REGULAR_SIMBOLOS)
						+ MessagesBundleConstants
								.getStringMessagesBundle(MessagesBundleConstants.MSG_SIN_ESPACIOS));
			}
		}

		return validates;
	}
}
