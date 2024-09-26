/**
 * 
 */
package co.gov.dafp.sigep2.sistema;

/**
 * @author joseviscaya
 *
 */
public enum TipoAccion {
	CREAR(201),
	INSERTAR(785),
	ELIMINAR(62),
	ACTUALIZAR(63);
	
	
private int value;
	
    /**
     * Constructor.
     * @param value value
     */
	TipoAccion(int value) {
        this.setValue(value);
    }
	
	
	public void setValue(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

}
