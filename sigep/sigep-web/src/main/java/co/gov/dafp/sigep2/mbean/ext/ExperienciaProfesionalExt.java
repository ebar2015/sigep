/**
 * 
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.entities.ExperienciaProfesional;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;

/**
 * @author joseviscaya
 *
 */
public class ExperienciaProfesionalExt extends ExperienciaProfesional implements Serializable {
	private static final long serialVersionUID = -8918110819050298377L;
	private static final long DIAS_ANIO = 365;
	private static final long DIAS_MES = 30;
	private String 	nombrePais;
	private String 	nombreDepartamento;
	private String 	nombreMunicipio;
	private String 	descripcionLab;
	private String 	descripcionRet;
	private String 	descripcionEnt;
	private String 	tipoEntidad;
	private Integer diferenciaDias;
	private Integer total;
	private String 	cargoNombre;
	private CargoExt cargoEnt;
	private String fechaInicio;
	private String fechaFin;
	private String nombreEntidadExt;
	private String jornadaLaboral;
	private String motivoRetiro;
	private String tiempoexperiencia;
	private String tiempoExperienciaLaboral;
	private Short codGrado;
	private Integer codNivelCargo;
	private Long codDenominacion;
	private String nombreNivelCargo;
	private String nombreDenominacion;
	private String nombreGrado;
	private String nombreCargoPriv;
	private String strDependenciaLike;
	private String strCargoLike;
	private String nombreDependencia;
	private String nombreCargo;
	private Short flgContratista;
	private String strDireccionGenerada;
	
	/**
	 * @return the nombrePais
	 */
	public String getNombrePais() {
		return nombrePais;
	}

	/**
	 * @param nombrePais
	 *            the nombrePais to set
	 */
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

	/**
	 * @return the nombreDepartamento
	 */
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}

	/**
	 * @param nombreDepartamento
	 *            the nombreDepartamento to set
	 */
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}

	/**
	 * @return the nombreMunicipio
	 */
	public String getNombreMunicipio() {
		return nombreMunicipio;
	}

	/**
	 * @param nombreMunicipio
	 *            the nombreMunicipio to set
	 */
	public void setNombreMunicipio(String nombreMunicipio) {
		this.nombreMunicipio = nombreMunicipio;
	}

	/**
	 * @return the descripcionLab
	 */
	public String getDescripcionLab() {
		return descripcionLab;
	}

	/**
	 * @param descripcionLab
	 *            the descripcionLab to set
	 */
	public void setDescripcionLab(String descripcionLab) {
		this.descripcionLab = descripcionLab;
	}

	/**
	 * @return the descripcionRet
	 */
	public String getDescripcionRet() {
		return descripcionRet;
	}

	/**
	 * @param descripcionRet
	 *            the descripcionRet to set
	 */
	public void setDescripcionRet(String descripcionRet) {
		this.descripcionRet = descripcionRet;
	}

	/**
	 * @return the descripcionEnt
	 */
	public String getDescripcionEnt() {
		return descripcionEnt;
	}

	/**
	 * @param descripcionEnt
	 *            the descripcionEnt to set
	 */
	public void setDescripcionEnt(String descripcionEnt) {
		this.descripcionEnt = descripcionEnt;
	}

	/**
	 * @return the diferenciaDias
	 */
	public Integer getDiferenciaDias() {
		return diferenciaDias;
	}

	public String getFechaInicio() {

		if (this.getFechaIngreso() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaInicio = sdf.format(this.getFechaIngreso());
		}

		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		if (!this.getFlgActivoEntidad() && this.getFechaRetiro() == null) {
			this.fechaFin = "";
		} else {
			this.fechaFin = "Laborando Actualmente";
		}

		if (this.getFechaRetiro() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			this.fechaFin = sdf.format(this.getFechaRetiro());
		}

		return this.fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	
	
	public String getStrDireccionGenerada() {
		if(this.getDireccionEntidad() == null) {
			setStrDireccionGenerada("");
		}else {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.getDireccionEntidad(), EditarDireccionDTO.class);
				if (direccion != null) {
					setStrDireccionGenerada(direccion.getDireccionGenerada());
				}
			} catch (JsonSyntaxException e) {
	
			}
		}
		return strDireccionGenerada;
	}

	public void setStrDireccionGenerada(String strDependenciaGenerada) {
		this.strDireccionGenerada = strDependenciaGenerada;
	}

	/**
	 * @param diferenciaDias
	 *            the diferenciaDias to set
	 */
	public void setDiferenciaDias(Integer diferenciaDias) {
		this.diferenciaDias = diferenciaDias;
	}

	public CargoExt getCargoEnt() {
		return cargoEnt;
	}

	public void setCargoEnt(CargoExt cargoEnt) {
		this.cargoEnt = cargoEnt;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the cargoNombre
	 */
	public String getCargoNombre() {
		return cargoNombre;
	}

	/**
	 * @param cargoNombre
	 *            the cargoNombre to set
	 */
	public void setCargoNombre(String cargoNombre) {
		this.cargoNombre = cargoNombre;
	}

	/**
	 * @return the tipoEntidad
	 */
	public String getTipoEntidad() {
		return tipoEntidad;
	}

	/**
	 * @param tipoEntidad
	 *            the tipoEntidad to set
	 */
	public void setTipoEntidad(String tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	/**
	 * @return the nombreEntidadExt
	 */
	public String getNombreEntidadExt() {
		return nombreEntidadExt;
	}

	/**
	 * @param nombreEntidadExt
	 *            the nombreEntidadExt to set
	 */
	public void setNombreEntidadExt(String nombreEntidadExt) {
		this.nombreEntidadExt = nombreEntidadExt;
	}

	public String getTiempoexperiencia() {
		return tiempoexperiencia;
	}

	public void setTiempoexperiencia(String tiempoexperiencia) {
		this.tiempoexperiencia = tiempoexperiencia;
	}

	public String getTiempoExperienciaLaboral() {

		Date fechaInicial = this.getFechaIngreso();
		Date fechaFin = this.getFechaRetiro() != null ? this.getFechaRetiro() : new Date();

		if (fechaInicial != null) {
			Long diferencia = (fechaFin.getTime() - fechaInicial.getTime());
			Long horasExp = ((diferencia) / 1000 / 60 / 60);
			Long tiempoExp = horasExp / 24;
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

			tiempoExperienciaLaboral = anioExp + " Años " + mesExp + " Meses " + diaExp + " Días";
		}
		return tiempoExperienciaLaboral;
	}

	public String getJornadaLaboral() {
		return jornadaLaboral;
	}

	public void setJornadaLaboral(String jornadaLaboral) {
		this.jornadaLaboral = jornadaLaboral;
	}

	public String getMotivoRetiro() {
		return motivoRetiro;
	}

	public void setMotivoRetiro(String motivoRetiro) {
		this.motivoRetiro = motivoRetiro;
	}

	public void setTiempoExperienciaLaboral(String tiempoExperienciaLaboral) {
		this.tiempoExperienciaLaboral = tiempoExperienciaLaboral;
	}

	/**
	 * @return el codGrado
	 */
	public Short getCodGrado() {
		return codGrado;
	}

	/**
	 * @param codGrado el codGrado a establecer
	 */
	public void setCodGrado(Short codGrado) {
		this.codGrado = codGrado;
	}

	/**
	 * @return el codNivelCargo
	 */
	public Integer getCodNivelCargo() {
		return codNivelCargo;
	}

	/**
	 * @param codNivelCargo el codNivelCargo a establecer
	 */
	public void setCodNivelCargo(Integer codNivelCargo) {
		this.codNivelCargo = codNivelCargo;
	}

	/**
	 * @return el codDenominacion
	 */
	public Long getCodDenominacion() {
		return codDenominacion;
	}

	/**
	 * @param codDenominacion el codDenominacion a establecer
	 */
	public void setCodDenominacion(Long codDenominacion) {
		this.codDenominacion = codDenominacion;
	}

	/**
	 * @return the nombreNivelCargo
	 */
	public String getNombreNivelCargo() {
		return nombreNivelCargo;
	}

	/**
	 * @param nombreNivelCargo the nombreNivelCargo to set
	 */
	public void setNombreNivelCargo(String nombreNivelCargo) {
		this.nombreNivelCargo = nombreNivelCargo;
	}

	/**
	 * @return the nombreDenominacion
	 */
	public String getNombreDenominacion() {
		return nombreDenominacion;
	}

	/**
	 * @param nombreDenominacion the nombreDenominacion to set
	 */
	public void setNombreDenominacion(String nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
	}

	/**
	 * @return the nombreGrado
	 */
	public String getNombreGrado() {
		return nombreGrado;
	}

	/**
	 * @param nombreGrado the nombreGrado to set
	 */
	public void setNombreGrado(String nombreGrado) {
		this.nombreGrado = nombreGrado;
	}

	/**
	 * @return the nombreCargoPriv
	 */
	public String getNombreCargoPriv() {
		return nombreCargoPriv;
	}

	/**
	 * @param nombreCargoPriv the nombreCargoPriv to set
	 */
	public void setNombreCargoPriv(String nombreCargoPriv) {
		this.nombreCargoPriv = nombreCargoPriv;
	}

	/**
	 * @return the strDependenciaLike
	 */
	public String getStrDependenciaLike() {
		return strDependenciaLike;
	}

	/**
	 * @param strDependenciaLike the strDependenciaLike to set
	 */
	public void setStrDependenciaLike(String strDependenciaLike) {
		this.strDependenciaLike = strDependenciaLike;
	}

	/**
	 * @return the strCargoLike
	 */
	public String getStrCargoLike() {
		return strCargoLike;
	}

	/**
	 * @param strCargoLike the strCargoLike to set
	 */
	public void setStrCargoLike(String strCargoLike) {
		this.strCargoLike = strCargoLike;
	}

	/**
	 * @return the nombreDependencia
	 */
	public String getNombreDependencia() {
		return nombreDependencia;
	}

	/**
	 * @param nombreDependencia the nombreDependencia to set
	 */
	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}

	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}

	/**
	 * @param nombreCargo the nombreCargo to set
	 */
	public void setNombreCargo(String nombreCargo) {
		this.nombreCargo = nombreCargo;
	}

	/**
	 * @return the flgContratista
	 */
	public Short getFlgContratista() {
		return flgContratista;
	}

	/**
	 * @param flgContratista the flgContratista to set
	 */
	public void setFlgContratista(Short flgContratista) {
		this.flgContratista = flgContratista;
	}
}