/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.EducacionFormal;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  EducacionFormalExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class EducacionFormalExt extends EducacionFormal implements Serializable {

	private static final long serialVersionUID = -6525509324188753952L;
	
	private String nombrePais;
	private String nombreInstitucionAcademica;
	private String tituloObtenido;
	private String nombreProgramaAcademico;
	private String nivelEducativo;
	private String nivelFormacion;
	private String areaConocimiento;
	private Long codInstitucionEducativa;
	
	 private String codIsoPais;
	 private Integer codDaneDepartamento;
	 private Integer codDaneMunicipio;
	
	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}
	/**
	 * @param nombrePais the nombrePais to set
	 */
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
	/**
	 * @return the codIsoPais
	 */
	public String getCodIsoPais() {
		return codIsoPais;
	}
	/**
	 * @param codIsoPais the codIsoPais to set
	 */
	public void setCodIsoPais(String codIsoPais) {
		this.codIsoPais = codIsoPais;
	}
	/**
	 * @return the codDaneDepartamento
	 */
	public Integer getCodDaneDepartamento() {
		return codDaneDepartamento;
	}
	/**
	 * @param codDaneDepartamento the codDaneDepartamento to set
	 */
	public void setCodDaneDepartamento(Integer codDaneDepartamento) {
		this.codDaneDepartamento = codDaneDepartamento;
	}
	/**
	 * @return the codDaneMunicipio
	 */
	public Integer getCodDaneMunicipio() {
		return codDaneMunicipio;
	}
	/**
	 * @param codDaneMunicipio the codDaneMunicipio to set
	 */
	public void setCodDaneMunicipio(Integer codDaneMunicipio) {
		this.codDaneMunicipio = codDaneMunicipio;
	}
	
}
