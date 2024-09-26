package co.gov.dafp.sigep2.mbean.ext;

import java.util.List;

import co.gov.dafp.sigep2.entities.Estructura;
import co.gov.dafp.sigep2.entities.EstructuraDependenciaEntidad;

public class EstructuraExt extends Estructura {
    private static final long serialVersionUID = -3021001222170630325L;
    
    private List<DependenciaEntidadExt> lstDependencias;
    private EstructuraDependenciaEntidad estructuraDependenciaEntidad;


    
	public List<DependenciaEntidadExt> getLstDependencias() {
        return lstDependencias;
    }

    public void setLstDependencias(List<DependenciaEntidadExt> lstDependencias) {
        this.lstDependencias = lstDependencias;
    }

    public EstructuraDependenciaEntidad getEstructuraDependenciaEntidad() {
        return estructuraDependenciaEntidad;
    }

    public void setEstructuraDependenciaEntidad(EstructuraDependenciaEntidad estructuraDependenciaEntidad) {
        this.estructuraDependenciaEntidad = estructuraDependenciaEntidad;
    }

}