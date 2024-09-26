/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.gov.dafp.sigep2.entities.ExperienciaDocente;

/**
 * @author joseviscaya
 *
 */
public class ExperienciaDocenteExt extends ExperienciaDocente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8443128577971859535L;
	private static final long  DIAS_ANIO = 365;
	private static final long DIAS_MES = 30;
	
	private String descripcionInt;
	private String nombreInstitucionIe;
	private String nivelEducativo;
	private String areaConocimiento;
	private Long codTipoInstitucion;
	private String nombrePais;
	private String descripcionNivelEdu;
	private String descripcionAreaCon;
	private int total;
	private String fechaInicio;
	private String fechaFinal;
	private String tiempoExperienciaExpDoc;
	private String tiempoExperienciaDocente;
	private String nombreMunicipio;
	private String nombreDepartamento;
	
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
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
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
	
	public String getFechaInicio() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.fechaInicio = sdf.format(this.getFechaIngreso());
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFinal() {
		
		this.fechaFinal = "Laborando Actualmente";
		
		if(this.getFechaFinalizacion() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaFinal = sdf.format(this.getFechaFinalizacion());
		}
		
		return  this.fechaFinal;
	}
	
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
	public String getTiempoExperienciaExpDoc() {
		return tiempoExperienciaExpDoc;
	}

	public void setTiempoExperienciaExpDoc(String tiempoExperienciaExpDoc) {
		this.tiempoExperienciaExpDoc = tiempoExperienciaExpDoc;
	}
	
	public String getTiempoExperienciaDocente() {
		
		Date fechaInicial = this.getFechaIngreso();
		Date fechaFin = this.getFechaFinalizacion() != null ? this.getFechaFinalizacion() : new Date();
		
		if(fechaInicial != null) {
			
			Long diferencia = (fechaFin.getTime()- fechaInicial.getTime());
			Long  horasExp = ((diferencia)/1000/60/60);			
			Long tiempoExp = horasExp/24;
			Long diaExp = 0l;
			Long mesExp = 0l;
			Long anioExp = 0l;
			Long auxMes = 0l;
			
			if (tiempoExp >= DIAS_ANIO) {
				anioExp = tiempoExp / DIAS_ANIO;
				auxMes = tiempoExp % DIAS_ANIO;
				if (auxMes >= DIAS_MES) {
					mesExp = auxMes / DIAS_MES;
					diaExp = auxMes % DIAS_MES;
				} else {
					diaExp = auxMes;
				}
			} else {
				mesExp = tiempoExp / DIAS_MES;
				diaExp = tiempoExp % DIAS_MES;
			}

			if (tiempoExp < DIAS_MES) {
				diaExp = tiempoExp;
			}
			
			tiempoExperienciaDocente = anioExp + " Años " + mesExp + " Meses " + diaExp + " Días";
		}
		
		return tiempoExperienciaDocente;
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
}
