/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.ExperienciaDocente;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  ExperienciaDocenteExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class ExperienciaDocenteExt extends ExperienciaDocente implements Serializable {

	private static final long serialVersionUID = 8443128577971859535L;
	
	private String descripcionInt;
	private String nombreInstitucionIe;
	private String nivelEducativo;
	private String areaConocimiento;
	private Long codTipoInstitucion;
	private String nombrePais;
	private String descripcionNivelEdu;
	private String descripcionAreaCon;
	private String nombreMunicipio;
	private String nombreDepartamento;
	private Integer total;
	 private String codIsoPais;
	 private Integer codDaneDepartamento;
	 private Integer codDaneMunicipio;
	 private Integer codInstitucionMen;
	/**
	 * @return the descripcionInt
	 */
	public String getDescripcionInt() {
		return descripcionInt;
	}
	/**
	 * @param descripcionInt the descripcionInt to set
	 */
	public void setDescripcionInt(String descripcionInt) {
		this.descripcionInt = descripcionInt;
	}
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
	/**
	 * @return the descripcionNivelEdu
	 */
	public String getDescripcionNivelEdu() {
		return descripcionNivelEdu;
	}
	/**
	 * @param descripcionNivelEdu the descripcionNivelEdu to set
	 */
	public void setDescripcionNivelEdu(String descripcionNivelEdu) {
		this.descripcionNivelEdu = descripcionNivelEdu;
	}
	/**
	 * @return the descripcionAreaCon
	 */
	public String getDescripcionAreaCon() {
		return descripcionAreaCon;
	}
	/**
	 * @param descripcionAreaCon the descripcionAreaCon to set
	 */
	public void setDescripcionAreaCon(String descripcionAreaCon) {
		this.descripcionAreaCon = descripcionAreaCon;
	}
	/**
	 * @return the codTipoInstitucion
	 */
	public Long getCodTipoInstitucion() {
		return codTipoInstitucion;
	}
	/**
	 * @param codTipoInstitucion the codTipoInstitucion to set
	 */
	public void setCodTipoInstitucion(Long codTipoInstitucion) {
		this.codTipoInstitucion = codTipoInstitucion;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
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
	 * @return the nombreInstitucionIe
	 */
	public String getNombreInstitucionIe() {
		return nombreInstitucionIe;
	}
	/**
	 * @param nombreInstitucionIe the nombreInstitucionIe to set
	 */
	public void setNombreInstitucionIe(String nombreInstitucionIe) {
		this.nombreInstitucionIe = nombreInstitucionIe;
	}
	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}
	/**
	 * @param nombreMunicipio the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}
	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	/**
	 * @param nombreDepartamento the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
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
		/**
		 * @return the codInstitucionMen
		 */
		public Integer getCodInstitucionMen() {
			return codInstitucionMen;
		}
		/**
		 * @param codInstitucionMen the codInstitucionMen to set
		 */
		public void setCodInstitucionMen(Integer codInstitucionMen) {
			this.codInstitucionMen = codInstitucionMen;
		}
}
