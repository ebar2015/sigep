package co.gov.dafp.sigep2.util;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;

public final class StringUtil {

	/**
	 * Caracteres con acentos permitidos en el vocabulario del idioma español
	 */
	private static final String CNS_ACENTOS = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.CNS_ACENTOS);

	private StringUtil() {
	}

	public static String UppercaseFirstLetters(String str) {
		boolean prevWasWhiteSp = true;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isLetter(chars[i])) {
				if (prevWasWhiteSp) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
				prevWasWhiteSp = false;
			} else {
				prevWasWhiteSp = Character.isWhitespace(chars[i]);
			}
		}
		return new String(chars);
	}

	/**
	 * Reemplaza los caracteres con acentos en el texto contenido en
	 * <code>input</code>. Si al split por <code>,</code> de
	 * {@link StringUtil#CNS_ACENTOS} las longitudes de las cadenas obtenidas
	 * son diferentes devolverá la cadena <code>input</code> sin ningun cambio,
	 * evitando un {@link IndexOutOfBoundsException}, igualmente si el split
	 * arroja una longitud diferente a <code>2</code>. Importante que los
	 * acentos estén escritos en el archivo de configuración en
	 * <code>Unicode</code>
	 * 
	 * @param input
	 *            valor de la cadena a reemplazarle caracteres con acentos
	 * @return {@link String} Valor de la cadena sin acentos
	 */
	public static String reemplazarAcentos(String input) {
		if (CNS_ACENTOS.split(",").length != 2) {
			return input;
		}
		String acentos = CNS_ACENTOS.split(",")[0];
		String sinAcentos = CNS_ACENTOS.split(",")[1];
		String output = input;

		if (sinAcentos.length() != acentos.length()) {
			return input;
		}

		for (int i = 0; i < acentos.length(); i++) {
			output = output.replace(acentos.charAt(i), sinAcentos.charAt(i));
		}
		return output;
	}

	/**
	 * Reemplaza los caracteres con acentos en el texto contenido en
	 * <code>input</code>. Si al split por <code>,</code> de
	 * {@link StringUtil#CNS_ACENTOS} las longitudes de las cadenas obtenidas
	 * son diferentes devolverá la cadena <code>input</code> sin ningun cambio,
	 * evitando un {@link IndexOutOfBoundsException}, igualmente si el split
	 * arroja una longitud diferente a <code>2</code>. Importante que los
	 * acentos estén escritos en el archivo de configuración en
	 * <code>Unicode</code>
	 * 
	 * @param input
	 *            valor de la cadena a reemplazarle caracteres con acentos
	 * @return {@link String} Valor de la cadena sin acentos con estructura
	 *         inflexiva
	 */
	public static String textoInflexivo(String input) {
		if (CNS_ACENTOS.split(",").length != 2) {
			return input;
		}

		String original = CNS_ACENTOS.split(",")[0];
		String ascii = CNS_ACENTOS.split(",")[1];
		String ascii2 = "aeiounAEIOUN";
		String output = input;
		for (int i = 0; i < original.length(); i++) {
			output = output.replace(original.charAt(i), ascii.charAt(i));
		}
		int j = 0;
		for (int i = 0; i < ascii2.length(); i++) {
			String buscar = String.valueOf(ascii2.charAt(i));
			String reemplazarPor = "[" + original.charAt(j++) + "|";
			if (ascii2.charAt(i) != 'N' && ascii2.charAt(i) != 'n') {
				reemplazarPor = reemplazarPor
						+ (String.valueOf(original.charAt(j++)) + "|" + String.valueOf(original.charAt(j++)) + "|");
			}
			reemplazarPor = reemplazarPor + ascii2.charAt(i) + "]";
			output = output.replace(buscar, reemplazarPor);
		}
		return output;
	}
}
