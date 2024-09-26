/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.ProgramaAcademico;

/**
 * @author joseviscaya
 *
 */
public class ProgramaAcademicoExt extends ProgramaAcademico implements Serializable{
	private static final long serialVersionUID = 3675943661114420104L;
	private Short flg_activo;
	private String programaAcademico;
	private String nombreInstitucion;
    
	public Short getFlg_activo() {
		return flg_activo;
	}
	public void setFlg_activo(Short flg_activo) {
		this.flg_activo = flg_activo;
	}
	public String getProgramaAcademico() {
		return programaAcademico;
	}
	public void setProgramaAcademico(String programaAcademico) {
		this.programaAcademico = programaAcademico;
	}
	public String getNombreInstitucion() {
		return nombreInstitucion;
	}
	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}
}