package co.gov.dafp.sigep2.util;

public class HTMLUtil {
	public static final String espacioEnBlanco 			= "&nbsp;";
	public static final String retornoCarro 			= "<br>";
	public static final String abreParrafo 				= "<p>";
	public static final String cierraParrafo 			= "</p>";
	public static final String abreListaNoOrdenada 		= "<ul>";
	public static final String cierraListaNoOrdenada 	= "</ul>";
	public static final String abreListaOrdenada 		= "<ol>";
	public static final String cierraListaOrdenada 		= "</ol>";
	public static final String abreItem 				= "<li>";
	public static final String cierraItem 				= "</li>";
	public static final String tab 						= "&nbsp;&nbsp;&nbsp;&nbsp;";

	public static final String IDENTIFICADOR_FIN_LISTA 	= "::::";
	public static final String IDENTIFICADOR_FIN_ITEM 	= "::";
	
	public static final String inicioTabla 				= "<table border = '1'>";
	public static final String finTabla 				= "</table>";
	public static final String abreFilaTabla			= "<tr>";
	public static final String cierraFilaTabla			= "</tr>";
	public static final String abreColumnaTabla			= "<td>";
	public static final String cierraColumnaTabla		= "</td>";
	public static final String abreTituloColumna		= "</th>";
	public static final String cierraTituloColumna		= "</th>";

	private HTMLUtil() {
	}

	public static final String listaNoOrdenada(String cadenaSinFormato) {
		return lista(cadenaSinFormato, HTMLUtil.abreListaNoOrdenada, HTMLUtil.cierraListaNoOrdenada);
	}

	public static final String listaOrdenada(String cadenaSinFormato) {
		return lista(cadenaSinFormato, HTMLUtil.abreListaOrdenada, HTMLUtil.cierraListaOrdenada);
	}

	private static final String lista(String cadenaSinFormatoP, String etiquetaAbreLista, String etiquetaCierraLista) {
		String validacionTemp = cadenaSinFormatoP;
		StringBuilder cadenaSinFormato = new StringBuilder();

		String[] validacionesTemp = null;
		if (cadenaSinFormatoP != null) {
			cadenaSinFormato.append(cadenaSinFormatoP);
			if (cadenaSinFormato.toString().contains(HTMLUtil.IDENTIFICADOR_FIN_LISTA)) {
				validacionesTemp = validacionTemp.split(HTMLUtil.IDENTIFICADOR_FIN_LISTA);
				if (!validacionTemp.isEmpty() && validacionesTemp.length > 0) {
					cadenaSinFormato = new StringBuilder();
					cadenaSinFormato.append(etiquetaAbreLista);
					for (String validacion : validacionesTemp) {
						if (!validacion.isEmpty()) {
							String[] itemsValidacion = validacion.split(HTMLUtil.IDENTIFICADOR_FIN_ITEM);
							if (itemsValidacion.length > 0) {
								cadenaSinFormato.append(HTMLUtil.abreItem);
								cadenaSinFormato.append(itemsValidacion[0]);
								cadenaSinFormato.append(HTMLUtil.abreListaNoOrdenada);
								for (int i = 1; i < itemsValidacion.length; i++) {
									String item = itemsValidacion[i];
									cadenaSinFormato.append(HTMLUtil.abreItem);
									cadenaSinFormato.append(item);
									cadenaSinFormato.append(HTMLUtil.cierraItem);
								}
								cadenaSinFormato.append(HTMLUtil.cierraListaNoOrdenada);
								cadenaSinFormato.append(HTMLUtil.cierraItem);
							}
						}
					}
					cadenaSinFormato.append(HTMLUtil.cierraListaNoOrdenada);
				}
			} else if (cadenaSinFormato.toString().contains(HTMLUtil.IDENTIFICADOR_FIN_ITEM)) {
				validacionesTemp = validacionTemp.split(HTMLUtil.IDENTIFICADOR_FIN_ITEM);
				if (!validacionTemp.isEmpty() && validacionesTemp.length > 0) {
					cadenaSinFormato = new StringBuilder();
					cadenaSinFormato.append(HTMLUtil.abreListaNoOrdenada);
					for (String validacion : validacionesTemp) {
						cadenaSinFormato.append(HTMLUtil.abreItem);
						cadenaSinFormato.append(validacion);
						cadenaSinFormato.append(HTMLUtil.cierraItem);
					}
					cadenaSinFormato.append(etiquetaCierraLista);
				}
			}
		}

		return cadenaSinFormato.toString();
	}
}
