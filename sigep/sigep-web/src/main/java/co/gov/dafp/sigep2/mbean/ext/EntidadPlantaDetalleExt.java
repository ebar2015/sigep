package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.entities.EntidadPlantaDetalle;

public class EntidadPlantaDetalleExt extends EntidadPlantaDetalle implements Serializable {

	private static final long serialVersionUID = -7648554481713646242L;
	private String 		nombreCargo;
	private String 		nombreNaturalezaEmpleo;
	private String 		nombreTipoPlanta;
	private String 		nombreDependencia;
	private BigDecimal 	total;
	private Long 		codEntidad;
	private Long 		codClasificacionPlanta;
	private Long 		codClasePlanta;
	private Long 		codNorma;
	private String 		nombreNorma;
	private Long 		codCargo;
	private Long 		codCodigo;
	private Long 		codigoDafp;
	private BigDecimal 	asignacionSalarial;
	private Long 		codNivelCargo;
	private Long 		codNivel;
	private String 		nombreDenominacion;
	private Long 		codDenominacion;
	private int 		limitInit;
	private int 		limitEnd;
	private boolean 	flgRepresentanteEmpleo;
	private boolean 	flgGerenciaPublica;
	private boolean 	flgIsExpuestoPoliticamente;
	private Long 		vacantes;
	private String 		primerNombre;
	private String 		dependenciaVinculacion;
	private Long 		cargosVinculacion;
	private Long 		codGrado;
	private Long 		codGrcantidadCargoApropiacionPresupuestal;
	private String 		tipoVacancia;
	private BigDecimal 	codVinculacion;
	private BigDecimal 	codPersona;
	private String 		nombreTipoNombramiento;
	private Long 		codDenominacionDestino;
	private Long 		totalCargosDirectivos;
	private Short 		flgGuardadoParcial;
	private Integer 	cantCargo;
	private Integer 	apropiacionPresu;
	private String 		nombrePlanta;
	private String 		nombrePersona;
	private String 		nombreEntidad;
	private Long 		vacantesCargo;
	private String 		nombreGrado;
	private String 		nombreCodigo;
	private Integer	 	codSituacionAdminRelacionLaboral;
	private Date 		fechaInicioSA;
    private Date 		fechaFinSA;
    private String 		nombreNivel;
    
	
	
	public Integer getApropiacionPresu() {
		return apropiacionPresu;
	}

	public void setApropiacionPresu(Integer apropiacionPresu) {
		this.apropiacionPresu = apropiacionPresu;
	}
	/**
	 * @return el nombreTipoNombramiento
	 */
	
	/**
	 * @return el nombreTipoNombramiento
	 */
	public String getNombreTipoNombramiento() {
		return nombreTipoNombramiento;
	}

	/**
	 * @param nombreTipoNombramiento el nombreTipoNombramiento a establecer
	 */
	public void setNombreTipoNombramiento(String nombreTipoNombramiento) {
		this.nombreTipoNombramiento = nombreTipoNombramiento;
	}

	/**
	 * @return el codVinculacion
	 */
	public BigDecimal getCodVinculacion() {
		return codVinculacion;
	}

	/**
	 * @param codVinculacion el codVinculacion a establecer
	 */
	public void setCodVinculacion(BigDecimal codVinculacion) {
		this.codVinculacion = codVinculacion;
	}

	/**
	 * @return el codPersona
	 */
	public BigDecimal getCodPersona() {
		return codPersona;
	}

	/**
	 * @param codPersona el codPersona a establecer
	 */
	public void setCodPersona(BigDecimal codPersona) {
		this.codPersona = codPersona;
	}

	/**
	 * @return el tipoVacancia
	 */
	public String getTipoVacancia() {
		return tipoVacancia;
	}

	/**
	 * @param tipoVacancia
	 *            el tipoVacancia a establecer
	 */
	public void setTipoVacancia(String tipoVacancia) {
		this.tipoVacancia = tipoVacancia;
	}

	/**
	 * @return el codGrcantidadCargoApropiacionPresupuestal
	 */
	public Long getCodGrcantidadCargoApropiacionPresupuestal() {
		return codGrcantidadCargoApropiacionPresupuestal;
	}

	/**
	 * @param codGrcantidadCargoApropiacionPresupuestal
	 *            el codGrcantidadCargoApropiacionPresupuestal a establecer
	 */
	public void setCodGrcantidadCargoApropiacionPresupuestal(Long codGrcantidadCargoApropiacionPresupuestal) {
		this.codGrcantidadCargoApropiacionPresupuestal = codGrcantidadCargoApropiacionPresupuestal;
	}

	/**
	 * @return el vacantes
	 */
	public Long getVacantes() {
		return vacantes;
	}

	/**
	 * @param vacantes
	 *            el vacantes a establecer
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
	 * @param primerNombre
	 *            el primerNombre a establecer
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
	 * @param dependenciaVinculacion
	 *            el dependenciaVinculacion a establecer
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
	 * @param cargosVinculacion
	 *            el cargosVinculacion a establecer
	 */
	public void setCargosVinculacion(Long cargosVinculacion) {
		this.cargosVinculacion = cargosVinculacion;
	}

	/**
	 * @return el codGrado
	 */
	public Long getCodGrado() {
		return codGrado;
	}

	/**
	 * @param codGrado
	 *            el codGrado a establecer
	 */
	public void setCodGrado(Long codGrado) {
		this.codGrado = codGrado;
	}

	public Long getCodNorma() {
		return codNorma;
	}

	public void setCodNorma(Long codNorma) {
		this.codNorma = codNorma;
	}

	public Long getCodCargo() {
		return codCargo;
	}

	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}

	public Long getCodigoDafp() {
		return codigoDafp;
	}

	public void setCodigoDafp(Long codigoDafp) {
		this.codigoDafp = codigoDafp;
	}

	public BigDecimal getAsignacionSalarial() {
		return asignacionSalarial;
	}

	public void setAsignacionSalarial(BigDecimal asignacionSalarial) {
		this.asignacionSalarial = asignacionSalarial;
	}

	public Long getCodNivelCargo() {
		return codNivelCargo;
	}

	public void setCodNivelCargo(Long codNivelCargo) {
		this.codNivelCargo = codNivelCargo;
	}

	public String getNombreDenominacion() {
		return nombreDenominacion;
	}

	public void setNombreDenominacion(String nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
	}

	/**
	 * @return the nombreCargo
	 */
	public String getNombreCargo() {
		return nombreCargo;
	}

	/**
	 * @param nombreCargo
	 *            the nombreCargo to set
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
	 * @param nombreNaturalezaEmpleo
	 *            the nombreNaturalezaEmpleo to set
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
	 * @param nombreTipoPlanta
	 *            the nombreTipoPlanta to set
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
	 * @param nombreDependencia
	 *            the nombreDependencia to set
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
	 * @param codEntidad
	 *            the codEntidad to set
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
	 * @param codClasificacionPlanta
	 *            the codClasificacionPlanta to set
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
	 * @param codClasePlanta
	 *            the codClasePlanta to set
	 */
	public void setCodClasePlanta(Long codClasePlanta) {
		this.codClasePlanta = codClasePlanta;
	}

	/**
	 * @return the limitInit
	 */
	public int getLimitInit() {
		return limitInit;
	}

	/**
	 * @param limitInit
	 *            the limitInit to set
	 */
	public void setLimitInit(int limitInit) {
		this.limitInit = limitInit;
	}

	/**
	 * @return the limitEnd
	 */
	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getCodDenominacion() {
		return codDenominacion;
	}

	public void setCodDenominacion(Long codDenominacion) {
		this.codDenominacion = codDenominacion;
	}

	public boolean isFlgRepresentanteEmpleo() {
		return flgRepresentanteEmpleo;
	}

	public void setFlgRepresentanteEmpleo(boolean flgRepresentanteEmpleo) {
		this.flgRepresentanteEmpleo = flgRepresentanteEmpleo;
	}

	public boolean isFlgGerenciaPublica() {
		return flgGerenciaPublica;
	}

	public void setFlgGerenciaPublica(boolean flgGerenciaPublica) {
		this.flgGerenciaPublica = flgGerenciaPublica;
	}

	public String getNombreNorma() {
		return nombreNorma;
	}

	public void setNombreNorma(String nombreNorma) {
		this.nombreNorma = nombreNorma;
	}

	/**
	 * @return el flgIsExpuestoPoliticamente
	 */
	public boolean isFlgIsExpuestoPoliticamente() {
		return flgIsExpuestoPoliticamente;
	}

	/**
	 * @param flgIsExpuestoPoliticamente el flgIsExpuestoPoliticamente a establecer
	 */
	public void setFlgIsExpuestoPoliticamente(boolean flgIsExpuestoPoliticamente) {
		this.flgIsExpuestoPoliticamente = flgIsExpuestoPoliticamente;
	}

	public Long getCodNivel() {
		return codNivel;
	}

	public void setCodNivel(Long codNivel) {
		this.codNivel = codNivel;
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

	public Integer getCantCargo() {
		return cantCargo;
	}

	public void setCantCargo(Integer cantCargo) {
		this.cantCargo = cantCargo;
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
		String nombre = "";
		if(nombrePersona!=null) 
			nombre = nombrePersona;
		else
			nombre = "Sin persona vinculada";
		
		return nombre;
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

	public Long getVacantesCargo() {
		return vacantesCargo;
	}

	public void setVacantesCargo(Long vacantesCargo) {
		this.vacantesCargo = vacantesCargo;
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
	 * @return the codSituacionAdminRelacionLaboral
	 */
	public Integer getCodSituacionAdminRelacionLaboral() {
		return codSituacionAdminRelacionLaboral;
	}

	/**
	 * @param codSituacionAdminRelacionLaboral the codSituacionAdminRelacionLaboral to set
	 */
	public void setCodSituacionAdminRelacionLaboral(Integer codSituacionAdminRelacionLaboral) {
		this.codSituacionAdminRelacionLaboral = codSituacionAdminRelacionLaboral;
	}

	/**
	 * @return the fechaInicioSA
	 */
	public Date getFechaInicioSA() {
		return fechaInicioSA;
	}

	/**
	 * @param fechaInicioSA the fechaInicioSA to set
	 */
	public void setFechaInicioSA(Date fechaInicioSA) {
		this.fechaInicioSA = fechaInicioSA;
	}

	/**
	 * @return the fechaFinSA
	 */
	public Date getFechaFinSA() {
		return fechaFinSA;
	}

	/**
	 * @param fechaFinSA the fechaFinSA to set
	 */
	public void setFechaFinSA(Date fechaFinSA) {
		this.fechaFinSA = fechaFinSA;
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