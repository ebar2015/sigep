/**
 * 
 */
package co.gov.dafp.sigep2.portal;

import java.util.List;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de responder las peticiones de busquedas de Servidores Publicos desde el Portal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class ServidoresPublicos extends ErrorMensajes {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3187786426263526226L;
	
	// Variable Lista para la carga de los resultados de la busqueda de servidores Publicos
	private List<Resultados> resultados;
	// Variable para el envio de los filtros disponibles sobre los resultados de la busqueda
	private Filtros filtros;
	// Variable  para el envio del total de resultados de la busqueda
	private Integer totalRegistros;
	// Variable  que hace eco del numero de datos solicitados
	private Integer limiteFinal;
	//variable para devolver la hoja de vida de un funcionario
	private HojaVidaPortal hojaDeVida;
	
	
	
	

	/**
	 * @return the resultados
	 */
	public List<Resultados> getResultados() {
		return resultados;
	}

	/**
	 * @param resultados the resultados to set
	 */
	public void setResultados(List<Resultados> resultados) {
		this.resultados = resultados;
	}


	/**
	 * @return the totalRegistros
	 */
	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	/**
	 * @return the limiteFinal
	 */
	public Integer getLimiteFinal() {
		return limiteFinal;
	}

	/**
	 * @param limiteFinal the limiteFinal to set
	 */
	public void setLimiteFinal(Integer limiteFinal) {
		this.limiteFinal = limiteFinal;
	}

	/**
	 * @return the filtros
	 */
	public Filtros getFiltros() {
		return filtros;
	}

	/**
	 * @param filtros the filtros to set
	 */
	public void setFiltros(Filtros filtros) {
		this.filtros = filtros;
	}

	/**
	 * @return the hojaDeVida
	 */
	public HojaVidaPortal getHojaDeVida() {
		return hojaDeVida;
	}

	/**
	 * @param hojaDeVida the hojaDeVida to set
	 */
	public void setHojaDeVida(HojaVidaPortal hojaDeVida) {
		this.hojaDeVida = hojaDeVida;
	}
	
	

}
