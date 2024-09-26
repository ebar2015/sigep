package co.gov.dafp.sigep2.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CapitalCaseConverter implements AttributeConverter<String, String> {

	

	public String convertToDatabaseColumn(String attribute) {
		return attribute != null && !attribute.isEmpty() ? CapitalCaseConverter.convert(attribute) : attribute;
	}

	public String convertToEntityAttribute(String dbData) {
		if(dbData != null){
			dbData = CapitalCaseConverter.convert(dbData);
		}
		return dbData;
	}
	
	
	
	public static String convert(Object data){
		if(data != null){
			data = data.toString().toLowerCase().replaceAll("</br>", " ");
			String[] datas = data.toString().split(" ");
			data = "";
			for(String dataTemp : datas){
				if(dataTemp != null){
					if (!isConectorOrArticulo(dataTemp)) {
						
						if(dataTemp.length() > 1){
							dataTemp = dataTemp.substring(0, 1).toUpperCase() + (dataTemp.length() > 1 ? dataTemp.toLowerCase().substring(1) : "") + " ";
						} else {
							dataTemp = dataTemp.toLowerCase() + " ";
						}
					
					}
					data = data+ " " + dataTemp.trim();
				}
			}
		} else {
			data = "";
		}
		return data.toString().trim();
	}
	
	private static boolean isConectorOrArticulo(String cadena) {
		boolean isConector = false;
		String[] articulos = new String[9] ;
		articulos[0]="el";
		articulos[1]="los";
		articulos[2]="la";
		articulos[3]="las";
		articulos[4]="un";
		articulos[5]="unos";
		articulos[6]="una";
		articulos[7]="unas";
		articulos[8]="de";
		for(String conn:articulos) {
			if(conn.toUpperCase().equals(cadena.toUpperCase())) {
				isConector= true;
				break;
			}
		}
		
		return isConector;
		
	
	}
	
	

}
