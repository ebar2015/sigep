package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Date;
import co.gov.dafp.sigep2.entities.Vinculacion;

public class VinculacionExt extends Vinculacion implements Serializable {
	private static final long serialVersionUID = 1336167124666768884L;
	
	private Integer codClasificacionPlanta;
	private Integer codClasePlanta;
	private Integer codNaturalezaEmpleo;
	private String 	nombreNaturalezaEmpleo;
	private String 	vacantes;
	private String 	nombreTipoNombramiento;
	private Long 	codEntidad;
	private String 	nombreEntidad;
	private String 	strNombreDependencia;
	private String 	primerNombre;
	private String 	segundoNombre;
	private String 	primerApellido;
	private String 	segundoApellido;
	private String 	numeroIdentificacion;
	private String 	nombreDocumento;
	private String 	nombreCargo;
	private String 	objetivoEntidad;
	private String 	nombrePlanta;
	private Long 	codCargo;
	private Long 	codTipoPlanta;
	private Long 	codTipoIdentificacion;
	private String 	codigoDane;
	private Long 	codTipoVinculacion;
	private String 	nombreDependencia;
    private String 	codigoEntidad;
    private String 	titularidad;
    private String 	strfechaPosesion;
    private Date 	fechaIniPosesion;
    private Date 	fechafinPosesion;
    private String 	strFechaDesvinculacion;
	private Long 	dias;
    private Integer codTipoIentificacion;
    private String 	objetoEntidad;
    private Integer parCausalDesv;
    private Short 	personaExpuestaPublicamente;
	private Long 	codigoDafp;
    private Long 	codGrado;
    private String 	nombreCausalDesv;
    private Long 	codPlantaPersonal;
    private Long 	codCargoDestino;
    private Long 	codPlantaDestino;
    private Long 	codCodigoDenominacion;
    private Long 	codUtlUan;
    private Long 	codNomenclaturaDenominacion;
    private String 	codigoSigep;
    private String 	nombreUsuario;
    private String 	nombreRol;
    private String 	detalle;
    private Short 	flgVinculacion;
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
     
	public String getObjetivoEntidad() {
		return objetivoEntidad;
	}
	public void setObjetivoEntidad(String objetivoEntidad) {
		this.objetivoEntidad = objetivoEntidad;
	}
	public Long getCodCargo() {
		return codCargo;
	}
	public void setCodCargo(Long codCargo) {
		this.codCargo = codCargo;
	}
	public Long getCodTipoPlanta() {
		return codTipoPlanta;
	}
	public void setCodTipoPlanta(Long codTipoPlanta) {
		this.codTipoPlanta = codTipoPlanta;
	}
	public Long getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}
	public void setCodTipoIdentificacion(Long codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}
	public Long getCodTipoVinculacion() {
		return codTipoVinculacion;
	}
	public void setCodTipoVinculacion(Long codTipoVinculacion) {
		this.codTipoVinculacion = codTipoVinculacion;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setStrFechaDesvinculacion(String strFechaDesvinculacion) {
		this.strFechaDesvinculacion = strFechaDesvinculacion;
	}
	/**
	 * @return the titularidad
	 */
	public String getTitularidad() {
		titularidad = "";
		if(this.getFlgTitularidadCargo() !=null) {
			if(this.getFlgTitularidadCargo() == 1) 
				titularidad= "SI";
			else 
				titularidad= "NO";
		}else {
			titularidad= "NO";
		}
		
		return titularidad;
	}
	/**
	 * @param titularidad the titularidad to set
	 */
	public void setTitularidad(String titularidad) {
		this.titularidad = titularidad;
	}
	/**
	 * @return the strfechaPosesion
	 */
	public String getStrfechaPosesion() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(this.getFechaPosesion()!=null) {
			this.strfechaPosesion = sdf.format(this.getFechaPosesion());
		}else {
			this.strfechaPosesion = "";
		}
		
		return strfechaPosesion;
	}
	/**
	 * @param strfechaPosesion the strfechaPosesion to set
	 */
	public void setStrfechaPosesion(String strfechaPosesion) {
		this.strfechaPosesion = strfechaPosesion;
	}
	/**
	 * @return the strFechaDesvinculacion
	 */
	public String getStrFechaDesvinculacion() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if(this.getFechaFinalizacion()!=null) {
			this.strFechaDesvinculacion = sdf.format(this.getFechaFinalizacion());
		}else {
			this.strFechaDesvinculacion = "";
		}
		
		return strFechaDesvinculacion;
	}
	/**
	 * @return the dias
	 */
	public Long getDias() {
		return dias;
	}
	/**
	 * @param dias the dias to set
	 */
	public void setDias(Long dias) {
		this.dias = dias;
	}
	
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
	 * @return the objetoEntidad
	 */
	public String getObjetoEntidad() {
		return objetoEntidad;
	}
	/**
	 * @param objetoEntidad the objetoEntidad to set
	 */
	public void setObjetoEntidad(String objetoEntidad) {
		this.objetoEntidad = objetoEntidad;
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
	 * @return the personaExpuestaPublicamente
	 */
	public Short getPersonaExpuestaPublicamente() {
		return personaExpuestaPublicamente;
	}
	/**
	 * @param personaExpuestaPublicamente the personaExpuestaPublicamente to set
	 */
	public void setPersonaExpuestaPublicamente(Short personaExpuestaPublicamente) {
		this.personaExpuestaPublicamente = personaExpuestaPublicamente;
	}
	public Long getCodigoDafp() {
		return codigoDafp;
	}
	public void setCodigoDafp(Long codigoDafp) {
		this.codigoDafp = codigoDafp;
	}
	public Long getCodGrado() {
		return codGrado;
	}
	public void setCodGrado(Long codGrado) {
		this.codGrado = codGrado;
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
	public Long getCodCargoDestino() {
		return codCargoDestino;
	}
	public void setCodCargoDestino(Long codCargoDestino) {
		this.codCargoDestino = codCargoDestino;
	}
	public Long getCodPlantaDestino() {
		return codPlantaDestino;
	}
	public void setCodPlantaDestino(Long codPlantaDestino) {
		this.codPlantaDestino = codPlantaDestino;
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
	 * @return the codUtlUan
	 */
	public Long getCodUtlUan() {
		return codUtlUan;
	}
	/**
	 * @param codUtlUan the codUtlUan to set
	 */
	public void setCodUtlUan(Long codUtlUan) {
		this.codUtlUan = codUtlUan;
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