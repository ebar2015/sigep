package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Vinculacion;
/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  VinculacionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class VinculacionExt extends Vinculacion implements Serializable {

	private static final long serialVersionUID = 1177083338378664209L;
	
	private Integer codClasificacionPlanta;
	private Integer codClasePlanta;
	private Integer codNaturalezaEmpleo;
	private Integer codDenominacion;
	private String 	nombreNaturalezaEmpleo;
	private String 	vacantes;
	private String 	nombreTipoNombramiento;
	private Long 	codEntidad;
	private Long 	codCargo;
	private String 	codigoSigep;
	private String 	nombreEntidad;
	private String 	strNombreDependencia;
	private Integer codTipoPlanta;
    private Date 	fechaIniPosesion;
    private Date 	fechafinPosesion;
    private String 	nombreDocumento;
    private String 	nombrePlanta;
    private String 	nombreCargo;
    private String 	tipoNombramiento;
    private Integer codTipoIentificacion;
    private String 	primerNombre;
    private String 	segundoNombre;
    private String 	primerApellido;
    private String 	numeroIdentificacion;
    private String 	objetivoEntidad;
    private String 	nombreDependencia;
    private String 	codigoEntidad;
    private String 	codigoDafp;
    private String 	nombreTipoActoAdministrativo;
    private String 	tipoActoAdministrativo;
    private String 	dependenciaVinculacion;
    private String 	tipoDocumento;
    private String 	codigoDane;
    private Integer dias;
    private Integer parCausalDesv;
    private Integer cargosVinculacion;
    private Integer codGrado;
    private Integer codGrcantidadCargoApropiacionPresupuestal;
    private String 	tipoVacancia;
    private String 	nombreCausalDesv;
    private Long 	codPlantaPersonal;
    private Long 	codCodigoDenominacion;
    private Long 	codNomenclaturaDenominacion;
    private String 	nombreUsuario;
    private String  nombreRol;
    private String  detalle;
    private Short   flgVinculacion;
    private Integer diasNotificacion;
    private Date 	fechaInicialValidaCargo;
    private String  nombreGrado;
    private String  nombreCodigo;
    private Date 	fechaInicioSA;
    private Date 	fechaFinSA;
    private Integer puestosOcupadosVinculacion;
    private Integer puestosOcupadosSA;
    private Integer codSituacionAdminRelacionLaboral;
    
    private Long codEntidadPlantaDetalleEncargoSA;
    private Long codNomenclaturaDenominacionSA;
    private Long codDenominacionSAEncargo;
    private Long codCodigoSAEncargo;
    private Long codGradoSAEncargo;
    private String nombreDenominacionSAEncargo;
    private String nombreCodigoSAEncargo;
    private String nombreGradoSAEncargo;
    private String nombreDependenciaEncargoSA;
    private String nombreEntidadEncargo;
    
	/**
	 * @return the primerNombre
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}
	/**
	 * @param primerNombre the primerNombre to set
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	/**
	 * @return the segundoNombre
	 */
	public String getSegundoNombre() {
		return segundoNombre;
	}
	/**
	 * @param segundoNombre the segundoNombre to set
	 */
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}
	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}
	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	private String segundoApellido;
	
	/**
	 * @return the codTipoPlanta
	 */
	public Integer getCodTipoPlanta() {
		return codTipoPlanta;
	}
	/**
	 * @param codTipoPlanta the codTipoPlanta to set
	 */
	public void setCodTipoPlanta(Integer codTipoPlanta) {
		this.codTipoPlanta = codTipoPlanta;
	}
	/**
	 * @return the fechaIniPosesion
	 */
	public Date getFechaIniPosesion() {
		return fechaIniPosesion;
	}
	/**
	 * @param fechaIniPosesion the fechaIniPosesion to set
	 */
	public void setFechaIniPosesion(Date fechaIniPosesion) {
		this.fechaIniPosesion = fechaIniPosesion;
	}
	/**
	 * @return the fechafinPosesion
	 */
	public Date getFechafinPosesion() {
		return fechafinPosesion;
	}
	/**
	 * @param fechafinPosesion the fechafinPosesion to set
	 */
	public void setFechafinPosesion(Date fechafinPosesion) {
		this.fechafinPosesion = fechafinPosesion;
	}
	/**
	 * @return the codClasificacionPlanta
	 */
	public Integer getCodClasificacionPlanta() {
		return codClasificacionPlanta;
	}
	/**
	 * @param codClasificacionPlanta the codClasificacionPlanta to set
	 */
	public void setCodClasificacionPlanta(Integer codClasificacionPlanta) {
		this.codClasificacionPlanta = codClasificacionPlanta;
	}
	/**
	 * @return the codClasePlanta
	 */
	public Integer getCodClasePlanta() {
		return codClasePlanta;
	}
	/**
	 * @param codClasePlanta the codClasePlanta to set
	 */
	public void setCodClasePlanta(Integer codClasePlanta) {
		this.codClasePlanta = codClasePlanta;
	}
	/**
	 * @return the codNaturalezaEmpleo
	 */
	public Integer getCodNaturalezaEmpleo() {
		return codNaturalezaEmpleo;
	}
	/**
	 * @param codNaturalezaEmpleo the codNaturalezaEmpleo to set
	 */
	public void setCodNaturalezaEmpleo(Integer codNaturalezaEmpleo) {
		this.codNaturalezaEmpleo = codNaturalezaEmpleo;
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
	 * @return the vacantes
	 */
	public String getVacantes() {
		return vacantes;
	}
	/**
	 * @param vacantes the vacantes to set
	 */
	public void setVacantes(String vacantes) {
		this.vacantes = vacantes;
	}
	/**
	 * @return the nombreTipoNombramiento
	 */
	public String getNombreTipoNombramiento() {
		return nombreTipoNombramiento;
	}
	/**
	 * @param nombreTipoNombramiento the nombreTipoNombramiento to set
	 */
	public void setNombreTipoNombramiento(String nombreTipoNombramiento) {
		this.nombreTipoNombramiento = nombreTipoNombramiento;
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
	 * @return the strNombreDependencia
	 */
	public String getStrNombreDependencia() {
		return strNombreDependencia;
	}
	/**
	 * @param strNombreDependencia the strNombreDependencia to set
	 */
	public void setStrNombreDependencia(String strNombreDependencia) {
		this.strNombreDependencia = strNombreDependencia;
	}
	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	/**
	 * @param nombreDocumento the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
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
	 * @return the codTipoIentificacion
	 */
	public Integer getCodTipoIentificacion() {
		return codTipoIentificacion;
	}
	/**
	 * @param codTipoIentificacion the codTipoIentificacion to set
	 */
	public void setCodTipoIentificacion(Integer codTipoIentificacion) {
		this.codTipoIentificacion = codTipoIentificacion;
	}
	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
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
	 * @return the codigoEntidad
	 */
	public String getCodigoEntidad() {
		return codigoEntidad;
	}
	/**
	 * @param codigoEntidad the codigoEntidad to set
	 */
	public void setCodigoEntidad(String codigoEntidad) {
		this.codigoEntidad = codigoEntidad;
	}
	/**
	 * @return the objetivoEntidad
	 */
	public String getObjetivoEntidad() {
		return objetivoEntidad;
	}
	/**
	 * @param objetivoEntidad the objetivoEntidad to set
	 */
	public void setObjetivoEntidad(String objetivoEntidad) {
		this.objetivoEntidad = objetivoEntidad;
	}
	/**
	 * @return the dias
	 */
	public Integer getDias() {
		return dias;
	}
	/**
	 * @param dias the dias to set
	 */
	public void setDias(Integer dias) {
		this.dias = dias;
	}
	public Integer getCodDenominacion() {
		return codDenominacion;
	}
	public void setCodDenominacion(Integer codDenominacion) {
		this.codDenominacion = codDenominacion;
	}
	/**
	 * @return the nombreTipoActoAdministrativo
	 */
	public String getNombreTipoActoAdministrativo() {
		return nombreTipoActoAdministrativo;
	}
	/**
	 * @param nombreTipoActoAdministrativo the nombreTipoActoAdministrativo to set
	 */
	public void setNombreTipoActoAdministrativo(String nombreTipoActoAdministrativo) {
		this.nombreTipoActoAdministrativo = nombreTipoActoAdministrativo;
	}
	/**
	 * @return the parCausalDesv
	 */
	public Integer getParCausalDesv() {
		return parCausalDesv;
	}
	/**
	 * @param parCausalDesv the parCausalDesv to set
	 */
	public void setParCausalDesv(Integer parCausalDesv) {
		this.parCausalDesv = parCausalDesv;
	}
	/**
	 * @return the cargosVinculacion
	 */
	public Integer getCargosVinculacion() {
		return cargosVinculacion;
	}
	/**
	 * @param cargosVinculacion the cargosVinculacion to set
	 */
	public void setCargosVinculacion(Integer cargosVinculacion) {
		this.cargosVinculacion = cargosVinculacion;
	}
	/**
	 * @return the codGrado
	 */
	public Integer getCodGrado() {
		return codGrado;
	}
	/**
	 * @param codGrado the codGrado to set
	 */
	public void setCodGrado(Integer codGrado) {
		this.codGrado = codGrado;
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
	 * @return the dependenciaVinculacion
	 */
	public String getDependenciaVinculacion() {
		return dependenciaVinculacion;
	}
	/**
	 * @param dependenciaVinculacion the dependenciaVinculacion to set
	 */
	public void setDependenciaVinculacion(String dependenciaVinculacion) {
		this.dependenciaVinculacion = dependenciaVinculacion;
	}
	/**
	 * @return the codGrcantidadCargoApropiacionPresupuestal
	 */
	public Integer getCodGrcantidadCargoApropiacionPresupuestal() {
		return codGrcantidadCargoApropiacionPresupuestal;
	}
	/**
	 * @param codGrcantidadCargoApropiacionPresupuestal the codGrcantidadCargoApropiacionPresupuestal to set
	 */
	public void setCodGrcantidadCargoApropiacionPresupuestal(Integer codGrcantidadCargoApropiacionPresupuestal) {
		this.codGrcantidadCargoApropiacionPresupuestal = codGrcantidadCargoApropiacionPresupuestal;
	}
	public String getTipoVacancia() {
		return tipoVacancia;
	}
	public void setTipoVacancia(String tipoVacancia) {
		this.tipoVacancia = tipoVacancia;
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
	 * @return the tipoActoAdministrativo
	 */
	public String getTipoActoAdministrativo() {
		return tipoActoAdministrativo;
	}
	/**
	 * @param tipoActoAdministrativo the tipoActoAdministrativo to set
	 */
	public void setTipoActoAdministrativo(String tipoActoAdministrativo) {
		this.tipoActoAdministrativo = tipoActoAdministrativo;
	}
	/**
	 * @return the tipoNombramiento
	 */
	public String getTipoNombramiento() {
		return tipoNombramiento;
	}
	/**
	 * @param tipoNombramiento the tipoNombramiento to set
	 */
	public void setTipoNombramiento(String tipoNombramiento) {
		this.tipoNombramiento = tipoNombramiento;
	}
	/**
	 * @return the nombreCausalDesv
	 */
	public String getNombreCausalDesv() {
		return nombreCausalDesv;
	}
	/**
	 * @param nombreCausalDesv the nombreCausalDesv to set
	 */
	public void setNombreCausalDesv(String nombreCausalDesv) {
		this.nombreCausalDesv = nombreCausalDesv;
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
	 * @return the codPlantaPersonal
	 */
	public Long getCodPlantaPersonal() {
		return codPlantaPersonal;
	}
	/**
	 * @param codPlantaPersonal the codPlantaPersonal to set
	 */
	public void setCodPlantaPersonal(Long codPlantaPersonal) {
		this.codPlantaPersonal = codPlantaPersonal;
	}
	/**
	 * @return the codigoDane
	 */
	public String getCodigoDane() {
		return codigoDane;
	}
	/**
	 * @param codigoDane the codigoDane to set
	 */
	public void setCodigoDane(String codigoDane) {
		this.codigoDane = codigoDane;
	}
	/**
	 * @return the codCodigoDenominacion
	 */
	public Long getCodCodigoDenominacion() {
		return codCodigoDenominacion;
	}
	/**
	 * @param codCodigoDenominacion the codCodigoDenominacion to set
	 */
	public void setCodCodigoDenominacion(Long codCodigoDenominacion) {
		this.codCodigoDenominacion = codCodigoDenominacion;
	}
	/**
	 * @return the codNomenclaturaDenominacion
	 */
	public Long getCodNomenclaturaDenominacion() {
		return codNomenclaturaDenominacion;
	}
	/**
	 * @param codNomenclaturaDenominacion the codNomenclaturaDenominacion to set
	 */
	public void setCodNomenclaturaDenominacion(Long codNomenclaturaDenominacion) {
		this.codNomenclaturaDenominacion = codNomenclaturaDenominacion;
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
	 * @return the nombreRol
	 */
	public String getNombreRol() {
		return nombreRol;
	}
	/**
	 * @param nombreRol the nombreRol to set
	 */
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	/**
	 * @return the detalle
	 */
	public String getDetalle() {
		return detalle;
	}
	/**
	 * @param detalle the detalle to set
	 */
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	/**
	 * @return the flgVinculacion
	 */
	public Short getFlgVinculacion() {
		return flgVinculacion;
	}
	/**
	 * @param flgVinculacion the flgVinculacion to set
	 */
	public void setFlgVinculacion(Short flgVinculacion) {
		this.flgVinculacion = flgVinculacion;
	}
	/**
	 * @return the diasNotificacion
	 */
	public Integer getDiasNotificacion() {
		return diasNotificacion;
	}
	/**
	 * @param diasNotificacion the diasNotificacion to set
	 */
	public void setDiasNotificacion(Integer diasNotificacion) {
		this.diasNotificacion = diasNotificacion;
	}
	/**
	 * @return the fechaInicialValidaCargo
	 */
	public Date getFechaInicialValidaCargo() {
		return fechaInicialValidaCargo;
	}
	/**
	 * @param fechaInicialValidaCargo the fechaInicialValidaCargo to set
	 */
	public void setFechaInicialValidaCargo(Date fechaInicialValidaCargo) {
		this.fechaInicialValidaCargo = fechaInicialValidaCargo;
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
	 * @return the puestosOcupadosVinculacion
	 */
	public Integer getPuestosOcupadosVinculacion() {
		return puestosOcupadosVinculacion;
	}
	/**
	 * @param puestosOcupadosVinculacion the puestosOcupadosVinculacion to set
	 */
	public void setPuestosOcupadosVinculacion(Integer puestosOcupadosVinculacion) {
		this.puestosOcupadosVinculacion = puestosOcupadosVinculacion;
	}
	/**
	 * @return the puestosOcupadosSA
	 */
	public Integer getPuestosOcupadosSA() {
		return puestosOcupadosSA;
	}
	/**
	 * @param puestosOcupadosSA the puestosOcupadosSA to set
	 */
	public void setPuestosOcupadosSA(Integer puestosOcupadosSA) {
		this.puestosOcupadosSA = puestosOcupadosSA;
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
	 * @return the codEntidadPlantaDetalleEncargoSA
	 */
	public Long getCodEntidadPlantaDetalleEncargoSA() {
		return codEntidadPlantaDetalleEncargoSA;
	}
	/**
	 * @param codEntidadPlantaDetalleEncargoSA the codEntidadPlantaDetalleEncargoSA to set
	 */
	public void setCodEntidadPlantaDetalleEncargoSA(Long codEntidadPlantaDetalleEncargoSA) {
		this.codEntidadPlantaDetalleEncargoSA = codEntidadPlantaDetalleEncargoSA;
	}
	/**
	 * @return the codNomenclaturaDenominacionSA
	 */
	public Long getCodNomenclaturaDenominacionSA() {
		return codNomenclaturaDenominacionSA;
	}
	/**
	 * @param codNomenclaturaDenominacionSA the codNomenclaturaDenominacionSA to set
	 */
	public void setCodNomenclaturaDenominacionSA(Long codNomenclaturaDenominacionSA) {
		this.codNomenclaturaDenominacionSA = codNomenclaturaDenominacionSA;
	}
	/**
	 * @return the codDenominacionSAEncargo
	 */
	public Long getCodDenominacionSAEncargo() {
		return codDenominacionSAEncargo;
	}
	/**
	 * @param codDenominacionSAEncargo the codDenominacionSAEncargo to set
	 */
	public void setCodDenominacionSAEncargo(Long codDenominacionSAEncargo) {
		this.codDenominacionSAEncargo = codDenominacionSAEncargo;
	}
	/**
	 * @return the codCodigoSAEncargo
	 */
	public Long getCodCodigoSAEncargo() {
		return codCodigoSAEncargo;
	}
	/**
	 * @param codCodigoSAEncargo the codCodigoSAEncargo to set
	 */
	public void setCodCodigoSAEncargo(Long codCodigoSAEncargo) {
		this.codCodigoSAEncargo = codCodigoSAEncargo;
	}
	/**
	 * @return the codGradoSAEncargo
	 */
	public Long getCodGradoSAEncargo() {
		return codGradoSAEncargo;
	}
	/**
	 * @param codGradoSAEncargo the codGradoSAEncargo to set
	 */
	public void setCodGradoSAEncargo(Long codGradoSAEncargo) {
		this.codGradoSAEncargo = codGradoSAEncargo;
	}
	/**
	 * @return the nombreDenominacionSAEncargo
	 */
	public String getNombreDenominacionSAEncargo() {
		return nombreDenominacionSAEncargo;
	}
	/**
	 * @param nombreDenominacionSAEncargo the nombreDenominacionSAEncargo to set
	 */
	public void setNombreDenominacionSAEncargo(String nombreDenominacionSAEncargo) {
		this.nombreDenominacionSAEncargo = nombreDenominacionSAEncargo;
	}
	/**
	 * @return the nombreCodigoSAEncargo
	 */
	public String getNombreCodigoSAEncargo() {
		return nombreCodigoSAEncargo;
	}
	/**
	 * @param nombreCodigoSAEncargo the nombreCodigoSAEncargo to set
	 */
	public void setNombreCodigoSAEncargo(String nombreCodigoSAEncargo) {
		this.nombreCodigoSAEncargo = nombreCodigoSAEncargo;
	}
	/**
	 * @return the nombreGradoSAEncargo
	 */
	public String getNombreGradoSAEncargo() {
		return nombreGradoSAEncargo;
	}
	/**
	 * @param nombreGradoSAEncargo the nombreGradoSAEncargo to set
	 */
	public void setNombreGradoSAEncargo(String nombreGradoSAEncargo) {
		this.nombreGradoSAEncargo = nombreGradoSAEncargo;
	}
	/**
	 * @return the nombreDependenciaEncargoSA
	 */
	public String getNombreDependenciaEncargoSA() {
		return nombreDependenciaEncargoSA;
	}
	/**
	 * @param nombreDependenciaEncargoSA the nombreDependenciaEncargoSA to set
	 */
	public void setNombreDependenciaEncargoSA(String nombreDependenciaEncargoSA) {
		this.nombreDependenciaEncargoSA = nombreDependenciaEncargoSA;
	}
	/**
	 * @return the nombreEntidadEncargo
	 */
	public String getNombreEntidadEncargo() {
		return nombreEntidadEncargo;
	}
	/**
	 * @param nombreEntidadEncargo the nombreEntidadEncargo to set
	 */
	public void setNombreEntidadEncargo(String nombreEntidadEncargo) {
		this.nombreEntidadEncargo = nombreEntidadEncargo;
	}
	
}