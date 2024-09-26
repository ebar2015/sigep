package co.gov.dafp.sigep2.util.xml;

public enum SeparadorCsvCaracter {

	COMA("COMA", ","), PUNTO_COMA("PUNTO COMA", ";"), RETORNO_CARRO("RETORNO CARRO", "\n"), TABULADOR("TABULADOR", "\t");
	private final String key;
	private final String value;

	SeparadorCsvCaracter(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String key() {
		return key;
	}

	public String value() {
		return value;
	}

}
