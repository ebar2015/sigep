/**
 *
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.DependenciaEntidad;

/**
 * @author joseviscaya
 */
public class DependenciaEntidadExt extends DependenciaEntidad implements Serializable {
    private static final long serialVersionUID = -2154389874537009875L;

    private Long codPersona, codEstructura;
    private String nombreEntidad;
    private String nombreClaseDependencia;
    private boolean flgDependenciaEspecial;
    private Long codPredecesorNull;
    private String ordenDependencias;
    private String dependenciasPadre;

    public Long getCodEstructura() {
		return codEstructura;
	}

	public void setCodEstructura(Long codEstructura) {
		this.codEstructura = codEstructura;
	}

	public boolean isFlgDependenciaEspecial() {
        /*if(getFlgActivo()!=null && getFlgActivo()==1)
        	return true;
        else
        	return false;*/
		//this.flgDependenciaEspecial = this.getFlgEspecial() == 1;
		return this.flgDependenciaEspecial;
    }

    public void setFlgDependenciaEspecial(boolean flgDependenciaEspecial) {
        this.flgDependenciaEspecial = flgDependenciaEspecial;
    }

    public Long getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getNombreClaseDependencia() {
        return nombreClaseDependencia;
    }

    public void setNombreClaseDependencia(String nombreClaseDependencia) {
        this.nombreClaseDependencia = nombreClaseDependencia;
    }

	public Long getCodPredecesorNull() {
		return codPredecesorNull;
	}

	public void setCodPredecesorNull(Long codPredecesorNull) {
		this.codPredecesorNull = codPredecesorNull;
	}

	public String getOrdenDependencias() {
		return ordenDependencias;
	}

	public void setOrdenDependencias(String ordenDependencias) {
		this.ordenDependencias = ordenDependencias;
	}

	public String getDependenciasPadre() {
		return dependenciasPadre;
	}

	public void setDependenciasPadre(String dependenciasPadre) {
		this.dependenciasPadre = dependenciasPadre;
	}

}