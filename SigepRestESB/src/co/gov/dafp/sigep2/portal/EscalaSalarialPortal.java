/**
 * 
 */
package co.gov.dafp.sigep2.portal;
import java.util.List;
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
public class EscalaSalarialPortal implements Serializable {

	private static final long serialVersionUID = -4597455928585766344L;
	
	private List<RangoSalariosPortal> rangoSalarioPortal;

	/**
	 * @return the rangoSalarioPortal
	 */
	public List<RangoSalariosPortal> getRangoSalarioPortal() {
		return rangoSalarioPortal;
	}

	/**
	 * @param rangoSalarioPortal the rangoSalarioPortal to set
	 */
	public void setRangoSalarioPortal(List<RangoSalariosPortal> rangoSalarioPortal) {
		this.rangoSalarioPortal = rangoSalarioPortal;
	}
}
