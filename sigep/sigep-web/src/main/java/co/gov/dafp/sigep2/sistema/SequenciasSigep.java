/**
 * 
 */
package co.gov.dafp.sigep2.sistema;

/**
 * @author joseviscaya
 *
 */
public enum SequenciasSigep {
	ENTIDAD_CODIGO_SIGEP("ENTIDAD_CODIGO_SIGEP"),
	ID_TABLA_PARAMETRICA("ID_TABLA_PARAMETRICA");

    private String value;

    /**
     * Constructor.
     * 
     * @param value value
     */
    SequenciasSigep(String value) {
    		this.setValue(value);
    }

    public void setValue(String value){
    		this.value = value;
    }

    public String getValue(){
	    return value;
    }
}
