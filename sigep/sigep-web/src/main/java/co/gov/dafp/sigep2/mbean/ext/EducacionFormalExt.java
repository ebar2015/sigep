package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.entities.EducacionFormal;
import co.gov.dafp.sigep2.util.DateUtils;

public class EducacionFormalExt extends EducacionFormal implements Serializable {
    private static final long serialVersionUID = -6525509324188753952L;

    private String nombrePais;
    private String nombreInstitucionAcademica;
    private String tituloObtenido, fechaFinalizadoString, fechaGradoString, fechaConvalidadoString;
    private String nombreProgramaAcademico;
    private String nivelEducativo;
    private String nivelFormacion;
    private String areaConocimiento;
    private String anoFinalizacion;
    private String mesFinalizacion;
    private Long codInstitucionEducativa;

    public String getNombrePais() {
	return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
	this.nombrePais = nombrePais;
    }

    public String getNombreInstitucionAcademica() {
	return nombreInstitucionAcademica;
    }

    public void setNombreInstitucionAcademica(String nombreInstitucionAcademica) {
	this.nombreInstitucionAcademica = nombreInstitucionAcademica;
    }

    public String getTituloObtenido() {
	return tituloObtenido;
    }

    public void setTituloObtenido(String tituloObtenido) {
	this.tituloObtenido = tituloObtenido;
    }

    /**
     * @return the nombreProgramaAcademico
     */
    public String getNombreProgramaAcademico() {
	return nombreProgramaAcademico;
    }

    /**
     * @param nombreProgramaAcademico the nombreProgramaAcademico to set
     */
    public void setNombreProgramaAcademico(String nombreProgramaAcademico) {
	this.nombreProgramaAcademico = nombreProgramaAcademico;
    }

    /**
     * @return the nivelEducativo
     */
    public String getNivelEducativo() {
	return nivelEducativo;
    }

    /**
     * @param nivelEducativo the nivelEducativo to set
     */
    public void setNivelEducativo(String nivelEducativo) {
	this.nivelEducativo = nivelEducativo;
    }

    /**
     * @return the nivelFormacion
     */
    public String getNivelFormacion() {
	return nivelFormacion;
    }

    /**
     * @param nivelFormacion the nivelFormacion to set
     */
    public void setNivelFormacion(String nivelFormacion) {
	this.nivelFormacion = nivelFormacion;
    }

    /**
     * @return the areaConocimiento
     */
    public String getAreaConocimiento() {
	return areaConocimiento;
    }

    /**
     * @param areaConocimiento the areaConocimiento to set
     */
    public void setAreaConocimiento(String areaConocimiento) {
	this.areaConocimiento = areaConocimiento;
    }

    /**
     * @return the codInstitucionEducativa
     */
    public Long getCodInstitucionEducativa() {
	return codInstitucionEducativa;
    }

    /**
     * @param codInstitucionEducativa the codInstitucionEducativa to set
     */
    public void setCodInstitucionEducativa(Long codInstitucionEducativa) {
	this.codInstitucionEducativa = codInstitucionEducativa;
    }

    public String getAnoFinalizacion() {
	anoFinalizacion = DateUtils.formatearACadena(this.getFechaFinalizacion(), "Y");
	return anoFinalizacion;
    }

    public void setAnoFinalizacion(String anoFinalizacion) {
	this.anoFinalizacion = anoFinalizacion;
    }

    public String getMesFinalizacion() {
	mesFinalizacion = DateUtils.formatearACadena(this.getFechaFinalizacion(), "M");
	return mesFinalizacion;
    }

    public void setMesFinalizacion(String mesFinalizacion) {
	this.mesFinalizacion = mesFinalizacion;
    }

    public String getFechaFinalizadoString() {
	fechaFinalizadoString = DateUtils.formatearACadena(getFechaFinalizacion(), "d 'de' MMMM 'del' yyyy");
	return fechaFinalizadoString;
    }

    public void setFechaFinalizadoString(String fechaFinalizadoString) {
	this.fechaFinalizadoString = fechaFinalizadoString;
    }

    public String getFechaGradoString() {
	fechaGradoString = DateUtils.formatearACadena(getFechaGrado(), "d 'de' MMMM 'del' yyyy");
        return fechaGradoString;
    }

    public void setFechaGradoString(String fechaGradoString) {
        this.fechaGradoString = fechaGradoString;
    }

    public String getFechaConvalidadoString() {
	fechaConvalidadoString = DateUtils.formatearACadena(getFechaConvalidacionEstudio(), "d 'de' MMMM 'del' yyyy");
        return fechaConvalidadoString;
    }

    public void setFechaConvalidadoString(String fechaConvalidadoString) {
        this.fechaConvalidadoString = fechaConvalidadoString;
    }
}