/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de represntar los datos disponibles por municipio
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class MunicipioFiltro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7733645679724463372L;
	
	// Variable pra almacenar el id del municipio
		private BigDecimal codMunicipio;
		// Variable pra almacenar el nombre del municipio
		private String nombre;
		
		private BigDecimal cod;
		
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public Integer getTotalRegistros() {
			return totalRegistros;
		}
		public void setTotalRegistros(Integer totalRegistros) {
			this.totalRegistros = totalRegistros;
		}
		/**
		 * @return the codMunicipio
		 */
		public BigDecimal getCodMunicipio() {
			return codMunicipio;
		}
		/**
		 * @param codMunicipio the codMunicipio to set
		 */
		public void setCodMunicipio(BigDecimal codMunicipio) {
			this.cod = codMunicipio;
			this.codMunicipio = codMunicipio;
		}
		/**
		 * @return the cod
		 */
		public BigDecimal getCod() {
			return cod;
		}
		/**
		 * @param cod the cod to set
		 */
		public void setCod(BigDecimal cod) {
			this.cod = cod;
		}
		// Variable pra almacenar el total de registros encontrados en la consulta
		private Integer totalRegistros;

}
