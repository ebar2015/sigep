/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Persona;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  PersonaExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class PersonaExt extends Persona implements Serializable {

	private static final long serialVersionUID = -291355843805010273L;
	 
	 private BigDecimal codUsuario;
	 private BigDecimal codEntidad;
	 private BigDecimal codTipoVinculacion;
	 private BigDecimal codVinculacion;
	 private BigDecimal codRol;
	 private BigDecimal codEntidadPlanta;
	 private BigDecimal codEntidadPlantaDetalle;
	 private BigDecimal codNomenclaturaDenominacion;
	 private BigDecimal codDenominacion;
	 private String nombreDenominacion;
	 private String nombreUsuario;
	 private String idEntidad;
	 private String tipoAsociacion;
	 private String idPersonaAud;
	 private String nombreEntidad;
	 private String nombrePais;
	 private String nombreDepartamento;
	 private String nombreMunicipio;
	 private String nombreEstadoCivil;
	 private String nombreLibretaMilitar;
	 private String nombreGenero;
	 private String nombreTipoDocuento;
	 private String nombreMunicipioResidencia;
	 private String nombreDepartamentoResidencia;
	 private String nombrePaisResidencia;
	 private String direccionResidencia;
	 private String nombrePertenenciaEtnica;
	 private Integer flgAprobado;
	 
	 private String numCelular;
	 private String numTelefonoOficina;
	 private String telefonoResidencia;
	 private String extencionTelefonoOficina;
	 private Integer parameterMes;
	 private String nombreCargo;
	 private Date fechaPosesion;
	 private Date fechaFinalizacion;
	 private Integer tipoCargue;
	 private Date fechaInicio;
	 private Date fechaFin;
	 private Integer estado;
	 private Integer vinculacion;
	 private Short flgActivoVinculacion;
	 private Integer deshacerVinc;
	 private Date fechaVinc;
	 private Integer diasDesvinc;
	 private Integer causalDesv;
	 private Integer parDiasDesv;
	 private String tipoDocumento;
	 private String genero;
	 private String factorRh;
	 private String estadoCivil;
	 private String areaConocimiento;
	 private Integer[] codRolList;
	 private String ticket;
	 
	 private Integer totalResponsable;
	 private Integer totalResponsableNacional;
	 private Integer totalResponsableTerritorial;
	 private Integer ordenNacional;
	 private Integer ordenTerritorial;
	 
	 private String codIsoPais;
	 private Integer codDaneDepartamento;
	 private Integer codDaneMunicipio;
	 
	 private String codigoSigep;
	 private String nombrePersonaCompleto;
	 private Short flgNotificado;
	 
	 private String fieldName;
	 private String ascDesc;
	 
	 private Date fechaAprobacionHV;
	 private BigDecimal codHojaVida;
	 
	/**
	 * @return the idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}
	/**
	 * @param idEntidad the idEntidad to set
	 */
	public void setIdEntidad(String idEntidad) {
		this.idEntidad = idEntidad;
	}
	/**
	 * @return the tipoAsociacion
	 */
	public String getTipoAsociacion() {
		return tipoAsociacion;
	}
	/**
	 * @param tipoAsociacion the tipoAsociacion to set
	 */
	public void setTipoAsociacion(String tipoAsociacion) {
		this.tipoAsociacion = tipoAsociacion;
	}
	/**
	 * @return the idPersonaAud
	 */
	public String getIdPersonaAud() {
		return idPersonaAud;
	}
	/**
	 * @param idPersonaAud the idPersonaAud to set
	 */
	public void setIdPersonaAud(String idPersonaAud) {
		this.idPersonaAud = idPersonaAud;
	}
	/**
	 * @return the codUsuario
	 */
	public BigDecimal getCodUsuario() {
		return codUsuario;
	}
	/**
	 * @param codUsuario the codUsuario to set
	 */
	public void setCodUsuario(BigDecimal codUsuario) {
		this.codUsuario = codUsuario;
	}
	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	/**
	 * @param nombreUsuario the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	/**
	 * @return the codEntidad
	 */
	public BigDecimal getCodEntidad() {
		return codEntidad;
	}
	/**
	 * @param codEntidad the codEntidad to set
	 */
	public void setCodEntidad(BigDecimal codEntidad) {
		this.codEntidad = codEntidad;
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
	 * @return the nombreEstadoCivil
	 */
	public String getNombreEstadoCivil() {
		return nombreEstadoCivil;
	}
	/**
	 * @param nombreEstadoCivil the nombreEstadoCivil to set
	 */
	public void setNombreEstadoCivil(String nombreEstadoCivil) {
		this.nombreEstadoCivil = nombreEstadoCivil;
	}
	/**
	 * @return the nombreLibretaMilitar
	 */
	public String getNombreLibretaMilitar() {
		return nombreLibretaMilitar;
	}
	/**
	 * @param nombreLibretaMilitar the nombreLibretaMilitar to set
	 */
	public void setNombreLibretaMilitar(String nombreLibretaMilitar) {
		this.nombreLibretaMilitar = nombreLibretaMilitar;
	}
	/**
	 * @return the nombreGenero
	 */
	public String getNombreGenero() {
		return nombreGenero;
	}
	/**
	 * @param nombreGenero the nombreGenero to set
	 */
	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}
	/**
	 * @return the nombreTipoDocuento
	 */
	public String getNombreTipoDocuento() {
		return nombreTipoDocuento;
	}
	/**
	 * @param nombreTipoDocuento the nombreTipoDocuento to set
	 */
	public void setNombreTipoDocuento(String nombreTipoDocuento) {
		this.nombreTipoDocuento = nombreTipoDocuento;
	}
	/**
	 * @return the nombreMunicipioResidencia
	 */
	public String getNombreMunicipioResidencia() {
		return nombreMunicipioResidencia;
	}
	/**
	 * @param nombreMunicipioResidencia the nombreMunicipioResidencia to set
	 */
	public void setNombreMunicipioResidencia(String nombreMunicipioResidencia) {
		this.nombreMunicipioResidencia = nombreMunicipioResidencia;
	}
	/**
	 * @return the nombreDepartamentoResidencia
	 */
	public String getNombreDepartamentoResidencia() {
		return nombreDepartamentoResidencia;
	}
	/**
	 * @param nombreDepartamentoResidencia the nombreDepartamentoResidencia to set
	 */
	public void setNombreDepartamentoResidencia(String nombreDepartamentoResidencia) {
		this.nombreDepartamentoResidencia = nombreDepartamentoResidencia;
	}
	/**
	 * @return the nombrePaisResidencia
	 */
	public String getNombrePaisResidencia() {
		return nombrePaisResidencia;
	}
	/**
	 * @param nombrePaisResidencia the nombrePaisResidencia to set
	 */
	public void setNombrePaisResidencia(String nombrePaisResidencia) {
		this.nombrePaisResidencia = nombrePaisResidencia;
	}
	/**
	 * @return the direccionResidencia
	 */
	public String getDireccionResidencia() {
		return direccionResidencia;
	}
	/**
	 * @param direccionResidencia the direccionResidencia to set
	 */
	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}
	/**
	 * @return the nombrePertenenciaEtnica
	 */
	public String getNombrePertenenciaEtnica() {
		return nombrePertenenciaEtnica;
	}
	/**
	 * @param nombrePertenenciaEtnica the nombrePertenenciaEtnica to set
	 */
	public void setNombrePertenenciaEtnica(String nombrePertenenciaEtnica) {
		this.nombrePertenenciaEtnica = nombrePertenenciaEtnica;
	}
	/**
	 * @return the flgAprobado
	 */
	public Integer getFlgAprobado() {
		return flgAprobado;
	}
	/**
	 * @param flgAprobado the flgAprobado to set
	 */
	public void setFlgAprobado(Integer flgAprobado) {
		this.flgAprobado = flgAprobado;
	}
	/**
	 * @return the codTipoVinculacion
	 */
	public BigDecimal getCodTipoVinculacion() {
		return codTipoVinculacion;
	}
	/**
	 * @param codTipoVinculacion the codTipoVinculacion to set
	 */
	public void setCodTipoVinculacion(BigDecimal codTipoVinculacion) {
		this.codTipoVinculacion = codTipoVinculacion;
	}
	/**
	 * @return the numCelular
	 */
	public String getNumCelular() {
		return numCelular;
	}
	/**
	 * @param numCelular the numCelular to set
	 */
	public void setNumCelular(String numCelular) {
		this.numCelular = numCelular;
	}
	/**
	 * @return the numTelefonoOficina
	 */
	public String getNumTelefonoOficina() {
		return numTelefonoOficina;
	}
	/**
	 * @param numTelefonoOficina the numTelefonoOficina to set
	 */
	public void setNumTelefonoOficina(String numTelefonoOficina) {
		this.numTelefonoOficina = numTelefonoOficina;
	}
	/**
	 * @return the telefonoResidencia
	 */
	public String getTelefonoResidencia() {
		return telefonoResidencia;
	}
	/**
	 * @param telefonoResidencia the telefonoResidencia to set
	 */
	public void setTelefonoResidencia(String telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}
	/**
	 * @return the extencionTelefonoOficina
	 */
	public String getExtencionTelefonoOficina() {
		return extencionTelefonoOficina;
	}
	/**
	 * @param extencionTelefonoOficina the extencionTelefonoOficina to set
	 */
	public void setExtencionTelefonoOficina(String extencionTelefonoOficina) {
		this.extencionTelefonoOficina = extencionTelefonoOficina;
	}
	/**
	 * @return the parameterMes
	 */
	public Integer getParameterMes() {
		return parameterMes;
	}
	/**
	 * @param parameterMes the parameterMes to set
	 */
	public void setParameterMes(Integer parameterMes) {
		this.parameterMes = parameterMes;
	}
	/**
	 * @return the codRol
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}
	/**
	 * @param codRol the codRol to set
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
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
	 * @return the fechaPosesion
	 */
	public Date getFechaPosesion() {
		return fechaPosesion;
	}
	/**
	 * @param fechaPosesion the fechaPosesion to set
	 */
	public void setFechaPosesion(Date fechaPosesion) {
		this.fechaPosesion = fechaPosesion;
	}
	/**
	 * @return the fechaFinalizacion
	 */
	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}
	/**
	 * @param fechaFinalizacion the fechaFinalizacion to set
	 */
	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}
	/**
	 * @return the codVinculacion
	 */
	public BigDecimal getCodVinculacion() {
		return codVinculacion;
	}
	/**
	 * @param codVinculacion the codVinculacion to set
	 */
	public void setCodVinculacion(BigDecimal codVinculacion) {
		this.codVinculacion = codVinculacion;
	}
	/**
	 * @return the tipoCargue
	 */
	public Integer getTipoCargue() {
		return tipoCargue;
	}
	/**
	 * @param tipoCargue the tipoCargue to set
	 */
	public void setTipoCargue(Integer tipoCargue) {
		this.tipoCargue = tipoCargue;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the vinculacion
	 */
	public Integer getVinculacion() {
		return vinculacion;
	}
	/**
	 * @param vinculacion the vinculacion to set
	 */
	public void setVinculacion(Integer vinculacion) {
		this.vinculacion = vinculacion;
	}
	/**
	 * @return the flgActivoVinculacion
	 */
	public Short getFlgActivoVinculacion() {
		return flgActivoVinculacion;
	}
	/**
	 * @param flgActivoVinculacion the flgActivoVinculacion to set
	 */
	public void setFlgActivoVinculacion(Short flgActivoVinculacion) {
		this.flgActivoVinculacion = flgActivoVinculacion;
	}
	/**
	 * @return the deshacerVinc
	 */
	public Integer getDeshacerVinc() {
		return deshacerVinc;
	}
	/**
	 * @param deshacerVinc the deshacerVinc to set
	 */
	public void setDeshacerVinc(Integer deshacerVinc) {
		this.deshacerVinc = deshacerVinc;
	}
	/**
	 * @return the fechaVinc
	 */
	public Date getFechaVinc() {
		return fechaVinc;
	}
	/**
	 * @param fechaVinc the fechaVinc to set
	 */
	public void setFechaVinc(Date fechaVinc) {
		this.fechaVinc = fechaVinc;
	}
	/**
	 * @return the diasDesvinc
	 */
	public Integer getDiasDesvinc() {
		return diasDesvinc;
	}
	/**
	 * @param diasDesvinc the diasDesvinc to set
	 */
	public void setDiasDesvinc(Integer diasDesvinc) {
		this.diasDesvinc = diasDesvinc;
	}
	/**
	 * @return the causalDesv
	 */
	public Integer getCausalDesv() {
		return causalDesv;
	}
	/**
	 * @param causalDesv the causalDesv to set
	 */
	public void setCausalDesv(Integer causalDesv) {
		this.causalDesv = causalDesv;
	}
	/**
	 * @return the parDiasDesv
	 */
	public Integer getParDiasDesv() {
		return parDiasDesv;
	}
	/**
	 * @param parDiasDesv the parDiasDesv to set
	 */
	public void setParDiasDesv(Integer parDiasDesv) {
		this.parDiasDesv = parDiasDesv;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}
	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}
	/**
	 * @return the factorRh
	 */
	public String getFactorRh() {
		return factorRh;
	}
	/**
	 * @param factorRh the factorRh to set
	 */
	public void setFactorRh(String factorRh) {
		this.factorRh = factorRh;
	}
	/**
	 * @return the estadoCivil
	 */
	public String getEstadoCivil() {
		return estadoCivil;
	}
	/**
	 * @param estadoCivil the estadoCivil to set
	 */
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
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
	 * @return el codRolList
	 */
	public Integer[] getCodRolList() {
		return codRolList;
	}
	/**
	 * @param codRolList el codRolList a establecer
	 */
	public void setCodRolList(Integer[] codRolList) {
		this.codRolList = codRolList;
	}
	/**
	 * @return the ticket
	 */
	public String getTicket() {
		return ticket;
	}
	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
	/**
	 * @return the totalResponsable
	 */
	public Integer getTotalResponsable() {
		return totalResponsable;
	}
	/**
	 * @param totalResponsable the totalResponsable to set
	 */
	public void setTotalResponsable(Integer totalResponsable) {
		this.totalResponsable = totalResponsable;
	}
	/**
	 * @return the totalResponsableNacional
	 */
	public Integer getTotalResponsableNacional() {
		return totalResponsableNacional;
	}
	/**
	 * @param totalResponsableNacional the totalResponsableNacional to set
	 */
	public void setTotalResponsableNacional(Integer totalResponsableNacional) {
		this.totalResponsableNacional = totalResponsableNacional;
	}
	/**
	 * @return the totalResponsableTerritorial
	 */
	public Integer getTotalResponsableTerritorial() {
		return totalResponsableTerritorial;
	}
	/**
	 * @param totalResponsableTerritorial the totalResponsableTerritorial to set
	 */
	public void setTotalResponsableTerritorial(Integer totalResponsableTerritorial) {
		this.totalResponsableTerritorial = totalResponsableTerritorial;
	}
	/**
	 * @return the ordenNacional
	 */
	public Integer getOrdenNacional() {
		return ordenNacional;
	}
	/**
	 * @param ordenNacional the ordenNacional to set
	 */
	public void setOrdenNacional(Integer ordenNacional) {
		this.ordenNacional = ordenNacional;
	}
	/**
	 * @return the ordenTerritorial
	 */
	public Integer getOrdenTerritorial() {
		return ordenTerritorial;
	}
	/**
	 * @param ordenTerritorial the ordenTerritorial to set
	 */
	public void setOrdenTerritorial(Integer ordenTerritorial) {
		this.ordenTerritorial = ordenTerritorial;
	}
	/**
	 * @return the codEntidadPlanta
	 */
	public BigDecimal getCodEntidadPlanta() {
		return codEntidadPlanta;
	}
	/**
	 * @param codEntidadPlanta the codEntidadPlanta to set
	 */
	public void setCodEntidadPlanta(BigDecimal codEntidadPlanta) {
		this.codEntidadPlanta = codEntidadPlanta;
	}
	/**
	 * @return the codEntidadPlantaDetalle
	 */
	public BigDecimal getCodEntidadPlantaDetalle() {
		return codEntidadPlantaDetalle;
	}
	/**
	 * @param codEntidadPlantaDetalle the codEntidadPlantaDetalle to set
	 */
	public void setCodEntidadPlantaDetalle(BigDecimal codEntidadPlantaDetalle) {
		this.codEntidadPlantaDetalle = codEntidadPlantaDetalle;
	}
	/**
	 * @return the codNomenclaturaDenominacion
	 */
	public BigDecimal getCodNomenclaturaDenominacion() {
		return codNomenclaturaDenominacion;
	}
	/**
	 * @param codNomenclaturaDenominacion the codNomenclaturaDenominacion to set
	 */
	public void setCodNomenclaturaDenominacion(BigDecimal codNomenclaturaDenominacion) {
		this.codNomenclaturaDenominacion = codNomenclaturaDenominacion;
	}
	/**
	 * @return the codDenominacion
	 */
	public BigDecimal getCodDenominacion() {
		return codDenominacion;
	}
	/**
	 * @param codDenominacion the codDenominacion to set
	 */
	public void setCodDenominacion(BigDecimal codDenominacion) {
		this.codDenominacion = codDenominacion;
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
	 * @return the codigoSigep
	 */
	public String getCodigoSigep() {
		return codigoSigep;
	}
	/**
	 * @param codigoSigep the codigoSigep to set
	 */
	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}
	
	/**
	 * @return the nombrePersonaCompleto
	 */
	public String getNombrePersonaCompleto() {
		return nombrePersonaCompleto;
	}
	/**
	 * @param nombrePersonaCompleto the nombrePersonaCompleto to set
	 */
	public void setNombrePersonaCompleto(String nombrePersonaCompleto) {
		this.nombrePersonaCompleto = nombrePersonaCompleto;
	}
	
	/**
	 * @return the flgNotificado
	 */
	public Short getFlgNotificado() {
		return flgNotificado;
	}
	/**
	 * @param flgNotificado the flgNotificado to set
	 */
	public void setFlgNotificado(Short flgNotificado) {
		this.flgNotificado = flgNotificado;
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * Feb 19, 2019
	 * ContratoExt.java
	 * @return
	 */
	public String getNombrePersona() {
		String nom ="";
		if(getPrimerNombre() != null) {
			nom+=getPrimerNombre();
		}
		if(getSegundoNombre() != null) {
			nom+=" "+getSegundoNombre();
		}
		if(getPrimerApellido() != null) {
			nom+=" "+getPrimerApellido();
		}
		if(getSegundoApellido() != null) {
			nom+=" "+getSegundoApellido();
		}
		return nom;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the ascDesc
	 */
	public String getAscDesc() {
		return ascDesc;
	}
	/**
	 * @param ascDesc the ascDesc to set
	 */
	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
	public Date getFechaAprobacionHV() {
		return fechaAprobacionHV;
	}
	public void setFechaAprobacionHV(Date fechaAprobacionHV) {
		this.fechaAprobacionHV = fechaAprobacionHV;
	}
	public BigDecimal getCodHojaVida() {
		return codHojaVida;
	}
	public void setCodHojaVida(BigDecimal codHojaVida) {
		this.codHojaVida = codHojaVida;
	}
}
