/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import co.gov.dafp.sigep2.entities.ProgramaAcademico;

/**
 * @author joseviscaya
 *
 */
public class ProgramaAcademicoExt extends ProgramaAcademico{

    private static final long serialVersionUID = -7055799966484978612L;
    
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