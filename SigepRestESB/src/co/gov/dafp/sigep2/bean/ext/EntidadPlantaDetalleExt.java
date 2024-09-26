package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;

import co.gov.dafp.sigep2.bean.EntidadPlantaDetalle;
/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  EntidadPlantaDetalleExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class EntidadPlantaDetalleExt extends EntidadPlantaDetalle implements Serializable {
	
	private static final long serialVersionUID = 1568463770239721783L;
	private Integer 	total;
	private String 		nombreCargo;
	private String 		nombreNaturalezaEmpleo;
	private String 		nombreTipoPlanta;
	private String 		nombreDependencia;
	private String 		codigoDafp;
	private String 		nombreDenominacion;
	private Long 		codEntidad;
	private Long 		codClasificacionPlanta;
	private Long 		codClasePlanta;
	private Long 		codNorma;
	private Long 		codCargo;
	private Long 		codGrado;
	private Long 		codCodigo;
	private Long 		codNivelCargo;
	private Double 		asignacionSalarial;
	private Integer 	limitInit;
    private Integer 	limitEnd;    
	private Long 		codDenominacion;
    private boolean 	flgRepresentanteEmpleo;
    private boolean 	flgGerenciaPublica;
    private Long 		vacantes;
    private String 		primerNombre;
    private String 		dependenciaVinculacion;
    private Long 		cargosVinculacion;    
    private Long 		codGrcantidadCargoApropiacionPresupuestal;
	private Long 		codDenominacionDestino; 
	private Long 		totalCargosDirectivos;
	private Short 		flgGuardadoParcial;
	private String 		nombrePlanta;
	private String 		nombrePersona;
	private String 		nombreEntidad;
	private Long 		vacantesCargo;
	private String 		nombreCodigo;
	private String 		nombreGrado;
	private String 		nombreNivel;
	
	
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
	 * @return el flgRepresentanteEmpleo
	 */
	public boolean isFlgRepresentanteEmpleo() {
		return flgRepresentanteEmpleo;
	}
	/**
	 * @param flgRepresentanteEmpleo el flgRepresentanteEmpleo a establecer
	 */
	public void setFlgRepresentanteEmpleo(boolean flgRepresentanteEmpleo) {
		this.flgRepresentanteEmpleo = flgRepresentanteEmpleo;
	}
	/**
	 * @return el flgGerenciaPublica
	 */
	public boolean isFlgGerenciaPublica() {
		return flgGerenciaPublica;
	}
	/**
	 * @param flgGerenciaPublica el flgGerenciaPublica a establecer
	 */
	public void setFlgGerenciaPublica(boolean flgGerenciaPublica) {
		this.flgGerenciaPublica = flgGerenciaPublica;
	}
	/**
	 * @return el vacantes
	 */
	public Long getVacantes() {
		return vacantes;
	}
	/**
	 * @param vacantes el vacantes a establecer
	 */
	public void setVacantes(Long vacantes) {
		this.vacantes = vacantes;
	}
	/**
	 * @return el primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre el primerNombre a establecer
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	/**
	 * @return el dependenciaVinculacion
	 */
	public String getDependenciaVinculacion() {
		return dependenciaVinculacion;
	}
	/**
	 * @param dependenciaVinculacion el dependenciaVinculacion a establecer
	 */
	public void setDependenciaVinculacion(String dependenciaVinculacion) {
		this.dependenciaVinculacion = dependenciaVinculacion;
	}
	/**
	 * @return el cargosVinculacion
	 */
	public Long getCargosVinculacion() {
		return cargosVinculacion;
	}
	/**
	 * @param cargosVinculacion el cargosVinculacion a establecer
	 */
	public void setCargosVinculacion(Long cargosVinculacion) {
		this.cargosVinculacion = cargosVinculacion;
	}
	
	
	/**
	 * @return el codGrcantidadCargoApropiacionPresupuestal
	 */
	public Long getCodGrcantidadCargoApropiacionPresupuestal() {
		return codGrcantidadCargoApropiacionPresupuestal;
	}
	/**
	 * @param codGrcantidadCargoApropiacionPresupuestal el codGrcantidadCargoApropiacionPresupuestal a establecer
	 */
	public void setCodGrcantidadCargoApropiacionPresupuestal(Long codGrcantidadCargoApropiacionPresupuestal) {
		this.codGrcantidadCargoApropiacionPresupuestal = codGrcantidadCargoApropiacionPresupuestal;
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
	 * @return the nombreNaturalezaEmpleo
	 */
	public String getNombreNaturalezaEmpleo() {
		return nombreNaturalezaEmpleo;
	}
	/**
	 * @param nombreNaturalezaEmpleo the nombreNaturalezaEmpleo to set
	 */
	public void setNombreNaturalezaEmpleo(String nombreNaturalezaEmpleo) {
		this.nombreNaturalezaEmpleo = nombreNaturalezaEmpleo;
	}
	/**
	 * @return the nombreTipoPlanta
	 */
	public String getNombreTipoPlanta() {
		return nombreTipoPlanta;
	}
	/**
	 * @param nombreTipoPlanta the nombreTipoPlanta to set
	 */
	public void setNombreTipoPlanta(String nombreTipoPlanta) {
		this.nombreTipoPlanta = nombreTipoPlanta;
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
	 * @return the codEntidad
	 */
	public Long getCodEntidad() {
		return codEntidad;
	}
	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}
	/**
	 * @return the codClasificacionPlanta
	 */
	public Long getCodClasificacionPlanta() {
		return codClasificacionPlanta;
	}
	/**
	 * @param codClasificacionPlanta the codClasificacionPlanta to set
	 */
	public void setCodClasificacionPlanta(Long codClasificacionPlanta) {
		this.codClasificacionPlanta = codClasificacionPlanta;
	}
	/**
	 * @return the codClasePlanta
	 */
	public Long getCodClasePlanta() {
		return codClasePlanta;
	}
	/**
	 * @param codClasePlanta the codClasePlanta to set
	 */
	public void setCodClasePlanta(Long codClasePlanta) {
		this.codClasePlanta = codClasePlanta;
	}
	/**
	 * @return the limitInit
	 */
	public Integer getLimitInit() {
		return limitInit;
	}
	/**
	 * @param limitInit the limitInit to set
	 */
	public void setLimitInit(Integer limitInit) {
		this.limitInit = limitInit;
	}
	/**
	 * @return the limitEnd
	 */
	public Integer getLimitEnd() {
		return limitEnd;
	}
	/**
	 * @param limitEnd the limitEnd to set
	 */
	public void setLimitEnd(Integer limitEnd) {
		this.limitEnd = limitEnd;
	}
	/**
	 * @return the codigoDafp
	 */
	public String getCodigoDafp() {
		return codigoDafp;
	}
	/**
	 * @param codigoDafp the codigoDafp to set
	 */
	public void setCodigoDafp(String codigoDafp) {
		this.codigoDafp = codigoDafp;
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
	 * @return the codNorma
	 */
	public Long getCodNorma() {
		return codNorma;
	}
	/**
	 * @param codNorma the codNorma to set
	 */
	public void setCodNorma(Long codNorma) {
		this.codNorma = codNorma;
	}
	/**
	 * @return the codCargo
	 */
	public Long getCodCargo() {
		return codCargo;
	}
	/**
	 * @param codCargo the codCargo to set
	 */
	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}
	/**
	 * @return the codGrado
	 */
	public Long getCodGrado() {
		return codGrado;
	}
	/**
	 * @param codGrado the codGrado to set
	 */
	public void setCodGrado(Long codGrado) {
		this.codGrado = codGrado;
	}
	/**
	 * @return the codNivelCargo
	 */
	public Long getCodNivelCargo() {
		return codNivelCargo;
	}
	/**
	 * @param codNivelCargo the codNivelCargo to set
	 */
	public void setCodNivelCargo(Long codNivelCargo) {
		this.codNivelCargo = codNivelCargo;
	}
	/**
	 * @return the asignacionSalarial
	 */
	public Double getAsignacionSalarial() {
		return asignacionSalarial;
	}
	/**
	 * @param asignacionSalarial the asignacionSalarial to set
	 */
	public void setAsignacionSalarial(Double asignacionSalarial) {
		this.asignacionSalarial = asignacionSalarial;
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
	 * @return the codDenominacionDestino
	 */
	public Long getCodDenominacionDestino() {
		return codDenominacionDestino;
	}
	/**
	 * @param codDenominacionDestino the codDenominacionDestino to set
	 */
	public void setCodDenominacionDestino(Long codDenominacionDestino) {
		this.codDenominacionDestino = codDenominacionDestino;
	}
	/**
	 * @return the totalCargosDirectivos
	 */
	public final Long getTotalCargosDirectivos() {
		return totalCargosDirectivos;
	}
	/**
	 * @param totalCargosDirectivos the totalCargosDirectivos to set
	 */
	public final void setTotalCargosDirectivos(Long totalCargosDirectivos) {
		this.totalCargosDirectivos = totalCargosDirectivos;
	}
	/**
	 * @return the flgGuardadoParcial
	 */
	public final Short getFlgGuardadoParcial() {
		return flgGuardadoParcial;
	}
	/**
	 * @param flgGuardadoParcial the flgGuardadoParcial to set
	 */
	public final void setFlgGuardadoParcial(Short flgGuardadoParcial) {
		this.flgGuardadoParcial = flgGuardadoParcial;
	}
	/**
	 * @return the nombrePlanta
	 */
	public String getNombrePlanta() {
		return nombrePlanta;
	}
	/**
	 * @param nombrePlanta the nombrePlanta to set
	 */
	public void setNombrePlanta(String nombrePlanta) {
		this.nombrePlanta = nombrePlanta;
	}
	/**
	 * @return the nombrePersona
	 */
	public String getNombrePersona() {
		return nombrePersona;
	}
	/**
	 * @param nombrePersona the nombrePersona to set
	 */
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}
	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	/**
	 * @param nombreEntidad the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	/**
	 * @return the vacantesCargo
	 */
	public Long getVacantesCargo() {
		return vacantesCargo;
	}
	/**
	 * @param vacantesCargo the vacantesCargo to set
	 */
	public void setVacantesCargo(Long vacantesCargo) {
		this.vacantesCargo = vacantesCargo;
	}
	/**
	 * @return the nombreCodigo
	 */
	public String getNombreCodigo() {
		return nombreCodigo;
	}
	/**
	 * @param nombreCodigo the nombreCodigo to set
	 */
	public void setNombreCodigo(String nombreCodigo) {
		this.nombreCodigo = nombreCodigo;
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
	 * @return the codCodigo
	 */
	public Long getCodCodigo() {
		return codCodigo;
	}
	/**
	 * @param codCodigo the codCodigo to set
	 */
	public void setCodCodigo(Long codCodigo) {
		this.codCodigo = codCodigo;
	}
	public String getNombreNivel() {
		return nombreNivel;
	}
	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}
}