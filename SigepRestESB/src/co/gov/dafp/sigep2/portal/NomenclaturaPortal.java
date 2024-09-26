/**
 * 
 */
package co.gov.dafp.sigep2.portal;
import java.io.Serializable;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga publicar las esclas saariales de una entidad en el portal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: MArtes 26 de Junio de 2018
*/
public class NomenclaturaPortal implements Serializable {

	private static final long serialVersionUID = -4597455928585766344L;

	private Long codNomenclatura;
	private String nombreNomenclatura;
	private Short flgGenerica;
	private Long codNomenclaturaAsociada;
	
	/**
	 * @return the codNomenclatura
	 */
	public Long getCodNomenclatura() {
		return codNomenclatura;
	}
	/**
	 * @param codNomenclatura the codNomenclatura to set
	 */
	public void setCodNomenclatura(Long codNomenclatura) {
		this.codNomenclatura = codNomenclatura;
	}
	/**
	 * @return the nombreNomenclatura
	 */
	public String getNombreNomenclatura() {
		return nombreNomenclatura;
	}
	/**
	 * @param nombreNomenclatura the nombreNomenclatura to set
	 */
	public void setNombreNomenclatura(String nombreNomenclatura) {
		this.nombreNomenclatura = nombreNomenclatura;
	}
	/**
	 * @return the flgGenerica
	 */
	public Short getFlgGenerica() {
		return flgGenerica;
	}
	/**
	 * @param flgGenerica the flgGenerica to set
	 */
	public void setFlgGenerica(Short flgGenerica) {
		this.flgGenerica = flgGenerica;
	}
	/**
	 * @return the codNomenclaturaAsociada
	 */
	public Long getCodNomenclaturaAsociada() {
		return codNomenclaturaAsociada;
	}
	/**
	 * @param codNomenclaturaAsociada the codNomenclaturaAsociada to set
	 */
	public void setCodNomenclaturaAsociada(Long codNomenclaturaAsociada) {
		this.codNomenclaturaAsociada = codNomenclaturaAsociada;
	}

}
