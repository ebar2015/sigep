/**
 *
 */
package co.gov.dafp.sigep2.mbean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * @author joseviscaya
 */
public class PersonaExt extends Persona implements Serializable {

	/**
	*
	*/
	private static final long serialVersionUID = -291355843805010273L;

	private BigDecimal codUsuario;
	private BigDecimal codEntidad;
	private BigDecimal codTipoVinculacion;
	private BigDecimal codRol;
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
	private String nombrePaisNacimiento;
	private String direccionResidencia;
	private String nombrePertenenciaEtnica;
	private String imagenUsuario;
	private String fechaNacimientoString;
	private int flgAprobado;
	private String numCelular = "";
	private String numTelefonoOficina;
	private String telefonoResidencia = "";
	private String extencionTelefonoOficina;
	private Integer parameterMes;
	private String nombreCargo;
	private Date fechaPosesion;
	private Date fechaFinalizacion;
	private BigDecimal codVinculacion;
	private Integer vinculacion;
	private int flgActivoVinculacion;
	private Date fechaVinc;
	private Integer diasDesvinc;
	private Integer deshacerVinc;
	private Integer causalDesv;
	private Integer parDiasDesv;
	private Integer[] codRolList;
	private Integer totalResponsable;
	private Integer totalResponsableNacional;
	private Integer totalResponsableTerritorial;
	private Integer ordenNacional;
	private Integer ordenTerritorial;
	private Long codEntidadPlanta;
	private Long codDenominacion;
	private Long nombreDenominacion;
	private Long codNomenclaturaDenominacion;
	private Long codEntidadPlantaDetalle;
	
	private String nombrePersonaCompleto;
	private Date fechaAprobacionHV;
	private BigDecimal codHojaVida;
	
	/**
	 * @return the idEntidad
	 */
	public String getIdEntidad() {
		return idEntidad;
	}

	/**
	 * @param idEntidad
	 *            the idEntidad to set
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
	 * @param tipoAsociacion
	 *            the tipoAsociacion to set
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
	 * @param idPersonaAud
	 *            the idPersonaAud to set
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
	 * @param codUsuario
	 *            the codUsuario to set
	 */
	public void setCodUsuario(BigDecimal codUsuario) {
		this.codUsuario = codUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
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
	 * @param codEntidad
	 *            the codEntidad to set
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
	 * @param nombreEntidad
	 *            the nombreEntidad to set
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
	 * @return the nombreEstadoCivil
	 */
	public String getNombreEstadoCivil() {
		return nombreEstadoCivil;
	}

	/**
	 * @param nombreEstadoCivil
	 *            the nombreEstadoCivil to set
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
	 * @param nombreLibretaMilitar
	 *            the nombreLibretaMilitar to set
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
	 * @param nombreGenero
	 *            the nombreGenero to set
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
	 * @param nombreTipoDocuento
	 *            the nombreTipoDocuento to set
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
	 * @param nombreMunicipioResidencia
	 *            the nombreMunicipioResidencia to set
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
	 * @param nombreDepartamentoResidencia
	 *            the nombreDepartamentoResidencia to set
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
	 * @param nombrePaisResidencia
	 *            the nombrePaisResidencia to set
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
	 * @param direccionResidencia
	 *            the direccionResidencia to set
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
	 * @param nombrePertenenciaEtnica
	 *            the nombrePertenenciaEtnica to set
	 */
	public void setNombrePertenenciaEtnica(String nombrePertenenciaEtnica) {
		this.nombrePertenenciaEtnica = nombrePertenenciaEtnica;
	}

	public String getImagenUsuario() {
		imagenUsuario = this.getFotoUrl();
		if (imagenUsuario == null) {
			if (this.getNombreGenero() != null && this.getNombreGenero().equals("MASCULINO")) {
				imagenUsuario = "../resources/images/man.png";
			} else {
				imagenUsuario = "../resources/images/woman.png";
			}
			return imagenUsuario;
		} else {
//			String url = WebUtils.getUrlFileHadoop(imagenUsuario, this.getNumeroIdentificacion(), this.getCodPersona()+"", WebUtils.TP_FOTO_USUARIO);
			String url = WebUtils.getUrlFile(imagenUsuario);
			if(url == null) {
				if (this.getNombreGenero() != null && this.getNombreGenero().equals("MASCULINO")) {
					imagenUsuario = "../resources/images/man.png";
				} else {
					imagenUsuario = "../resources/images/woman.png";
				}
				return imagenUsuario;
			}
			return url;
		}
	}

	public void setImagenUsuario(String imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	public String getFechaNacimientoString() {
		fechaNacimientoString = DateUtils.formatearACadena(getFechaNacimiento(), "d 'de' MMMM 'del' yyyy");
		return fechaNacimientoString;
	}

	public void setFechaNacimientoString(String fechaNacimientoString) {
		this.fechaNacimientoString = fechaNacimientoString;
	}

	public String getNombreUsuario() {

		if (this.getPrimerNombre() != null) {
			nombreUsuario = this.getPrimerNombre();
		} else {
			nombreUsuario = "";
		}

		if (this.getSegundoNombre() != null) {
			nombreUsuario += " " + this.getSegundoNombre();
		}

		if (this.getPrimerApellido() != null) {
			nombreUsuario += " " + this.getPrimerApellido();
		}

		if (this.getSegundoApellido() != null) {
			nombreUsuario += " " + this.getSegundoApellido();
		}
		return nombreUsuario;
	}

	/**
	 * @return the flgAprobado
	 */
	public int getFlgAprobado() {
		return flgAprobado;
	}

	/**
	 * @param flgAprobado
	 *            the flgAprobado to set
	 */
	public void setFlgAprobado(int flgAprobado) {
		this.flgAprobado = flgAprobado;
	}

	/**
	 * @return the codTipoVinculacion
	 */
	public BigDecimal getCodTipoVinculacion() {
		return codTipoVinculacion;
	}

	/**
	 * @param codTipoVinculacion
	 *            the codTipoVinculacion to set
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
	 * @param numCelular
	 *            the numCelular to set
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
	 * @param numTelefonoOficina
	 *            the numTelefonoOficina to set
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
	 * @param telefonoResidencia
	 *            the telefonoResidencia to set
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
	 * @param extencionTelefonoOficina
	 *            the extencionTelefonoOficina to set
	 */
	public void setExtencionTelefonoOficina(String extencionTelefonoOficina) {
		this.extencionTelefonoOficina = extencionTelefonoOficina;
	}

	public Integer getParameterMes() {
		return parameterMes;
	}

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
	 * @param codRol
	 *            the codRol to set
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
	 * @param nombreCargo
	 *            the nombreCargo to set
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
	 * @param fechaPosesion
	 *            the fechaPosesion to set
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
	 * @param fechaFinalizacion
	 *            the fechaFinalizacion to set
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
	 * @param codVinculacion
	 *            the codVinculacion to set
	 */
	public void setCodVinculacion(BigDecimal codVinculacion) {
		this.codVinculacion = codVinculacion;
	}

	/**
	 * @return the vinculacion
	 */
	public Integer getVinculacion() {
		return vinculacion;
	}

	public String getNombrePaisNacimiento() {
		return nombrePaisNacimiento;
	}

	/**
	 * @param vinculacion
	 *            the vinculacion to set
	 */
	public void setVinculacion(Integer vinculacion) {
		this.vinculacion = vinculacion;
	}

	/**
	 * @return el flgActivoVinculacion
	 */
	public int getFlgActivoVinculacion() {
		return flgActivoVinculacion;
	}

	/**
	 * @param flgActivoVinculacion
	 *            el flgActivoVinculacion a establecer
	 */
	public void setFlgActivoVinculacion(int flgActivoVinculacion) {
		this.flgActivoVinculacion = flgActivoVinculacion;
	}

	/**
	 * @return the fechaVinc
	 */
	public Date getFechaVinc() {
		return fechaVinc;
	}

	/**
	 * @param fechaVinc
	 *            the fechaVinc to set
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
	 * @param diasDesvinc
	 *            the diasDesvinc to set
	 */
	public void setDiasDesvinc(Integer diasDesvinc) {
		this.diasDesvinc = diasDesvinc;
	}

	/**
	 * @return the deshacerVinc
	 */
	public Integer getDeshacerVinc() {
		return deshacerVinc;
	}

	/**
	 * @param deshacerVinc
	 *            the deshacerVinc to set
	 */
	public void setDeshacerVinc(Integer deshacerVinc) {
		this.deshacerVinc = deshacerVinc;
	}

	/**
	 * @return the causalDesv
	 */
	public Integer getCausalDesv() {
		return causalDesv;
	}

	/**
	 * @param causalDesv
	 *            the causalDesv to set
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
	 * @param parDiasDesv
	 *            the parDiasDesv to set
	 */
	public void setParDiasDesv(Integer parDiasDesv) {
		this.parDiasDesv = parDiasDesv;
	}

	public void setNombrePaisNacimiento(String nombrePaisNacimiento) {
		this.nombrePaisNacimiento = nombrePaisNacimiento;
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

	public Long getCodEntidadPlanta() {
		return codEntidadPlanta;
	}

	public void setCodEntidadPlanta(Long codEntidadPlanta) {
		this.codEntidadPlanta = codEntidadPlanta;
	}

	public Long getCodDenominacion() {
		return codDenominacion;
	}

	public void setCodDenominacion(Long codDenominacion) {
		this.codDenominacion = codDenominacion;
	}

	public Long getNombreDenominacion() {
		return nombreDenominacion;
	}

	public void setNombreDenominacion(Long nombreDenominacion) {
		this.nombreDenominacion = nombreDenominacion;
	}

	public Long getCodNomenclaturaDenominacion() {
		return codNomenclaturaDenominacion;
	}

	public void setCodNomenclaturaDenominacion(Long codNomenclaturaDenominacion) {
		this.codNomenclaturaDenominacion = codNomenclaturaDenominacion;
	}

	public Long getCodEntidadPlantaDetalle() {
		return codEntidadPlantaDetalle;
	}

	public void setCodEntidadPlantaDetalle(Long codEntidadPlantaDetalle) {
		this.codEntidadPlantaDetalle = codEntidadPlantaDetalle;
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