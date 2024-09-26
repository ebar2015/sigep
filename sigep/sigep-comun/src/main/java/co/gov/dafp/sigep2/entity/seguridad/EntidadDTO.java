package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;

/**
 * Entity implementation class for Entity: EntidadBaseDTO
 *
 */
public class EntidadDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = -1636969809789308631L;

	public static final String DAFP = "DAFP";

	private long id;

	private TipoEntidadDTO tipoEntidad;

	private Long entidad;

	private String codigoSigep;

	private BigDecimal codPais;

	private BigDecimal codDepartamento;

	private BigDecimal codMunicipio;

	private BigDecimal codClasificacionOrganica;

	private BigDecimal codOrden;

	private BigDecimal codSuborden;

	private BigDecimal codNaturaleza;

	private BigDecimal codNivelAdministrativo;

	private BigDecimal codTipoAdscripcion;

	private BigDecimal codAdscritaVinculada;

	private BigDecimal codSectorAdministrativo;

	private BigDecimal codSistemaCarrera;

	private BigDecimal codCategoria;

	private BigDecimal codCodigodane;

	private BigDecimal codTipoNorma;

	private BigDecimal codNorma;

	private BigDecimal codResponsableGestion;

	private String nit;

	private BigDecimal digitoVerificacion;

	private String nombreEntidad;

	private String sigla;

	private String objetivoEntidad;

	private Date fechaInicio;

	private Date fechaFin;

	private String codRepresentanteLegal;

	private String emailEntidad;

	private String paginaWeb;

	private String funciones;

	private BigDecimal latitud;

	private BigDecimal longitud;

	private String direccion;

	private String telefonoEntidad;

	private String fax;

	private BigDecimal codEstadoEntidad;

	private boolean flgEsCabeza;

	private boolean flgDependenciaEspecial;

	private Boolean estatuto;

	private Boolean personeriaJuridica;

	private BigDecimal codRegimenSalarial;

	private Boolean estructura;

	private Boolean planta;

	private Boolean manualFunciones;

	private boolean flgAplica1785;

	private List<UsuarioEntidadDTO> usuarioEntidads;

	public EntidadDTO() {
		super();
	}

	public EntidadDTO(long id, Long entidad, String codigoSigep, BigDecimal codPais, BigDecimal codDepartamento,
			BigDecimal codMunicipio, BigDecimal codClasificacionOrganica, BigDecimal codOrden, BigDecimal codSuborden,
			BigDecimal codNaturaleza, BigDecimal codNivelAdministrativo, BigDecimal codTipoAdscripcion,
			BigDecimal codAdscritaVinculada, BigDecimal codSectorAdministrativo, BigDecimal codSistemaCarrera,
			BigDecimal codCategoria, BigDecimal codCodigodane, BigDecimal codTipoNorma, BigDecimal codNorma,
			BigDecimal codResponsableGestion, String nit, BigDecimal digitoVerificacion, String nombreEntidad,
			String sigla, String objetivoEntidad, Date fechaInicio, Date fechaFin, String codRepresentanteLegal,
			String emailEntidad, String paginaWeb, String funciones, BigDecimal latitud, BigDecimal longitud,
			String direccion, String telefonoEntidad, String fax, BigDecimal codEstadoEntidad, boolean flgEsCabeza,
			boolean flgDependenciaEspecial, Boolean estatuto, Boolean personeriaJuridica, BigDecimal codRegimenSalarial,
			Boolean estructura, Boolean planta, Boolean manualFunciones, boolean flgAplica1785,
			List<UsuarioEntidadDTO> usuarioEntidads) {
		super();
		this.id = id;
		this.entidad = entidad;
		this.codigoSigep = codigoSigep;
		this.codPais = codPais;
		this.codDepartamento = codDepartamento;
		this.codMunicipio = codMunicipio;
		this.codClasificacionOrganica = codClasificacionOrganica;
		this.codOrden = codOrden;
		this.codSuborden = codSuborden;
		this.codNaturaleza = codNaturaleza;
		this.codNivelAdministrativo = codNivelAdministrativo;
		this.codTipoAdscripcion = codTipoAdscripcion;
		this.codAdscritaVinculada = codAdscritaVinculada;
		this.codSectorAdministrativo = codSectorAdministrativo;
		this.codSistemaCarrera = codSistemaCarrera;
		this.codCategoria = codCategoria;
		this.codCodigodane = codCodigodane;
		this.codTipoNorma = codTipoNorma;
		this.codNorma = codNorma;
		this.codResponsableGestion = codResponsableGestion;
		this.nit = nit;
		this.digitoVerificacion = digitoVerificacion;
		this.nombreEntidad = nombreEntidad;
		this.sigla = sigla;
		this.objetivoEntidad = objetivoEntidad;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.codRepresentanteLegal = codRepresentanteLegal;
		this.emailEntidad = emailEntidad;
		this.paginaWeb = paginaWeb;
		this.funciones = funciones;
		this.latitud = latitud;
		this.longitud = longitud;
		this.direccion = direccion;
		this.telefonoEntidad = telefonoEntidad;
		this.fax = fax;
		this.codEstadoEntidad = codEstadoEntidad;
		this.flgEsCabeza = flgEsCabeza;
		this.flgDependenciaEspecial = flgDependenciaEspecial;
		this.estatuto = estatuto;
		this.personeriaJuridica = personeriaJuridica;
		this.codRegimenSalarial = codRegimenSalarial;
		this.estructura = estructura;
		this.planta = planta;
		this.manualFunciones = manualFunciones;
		this.flgAplica1785 = flgAplica1785;
		this.usuarioEntidads = usuarioEntidads;
	}

	public EntidadDTO(Long id, String nit, String nombreEntidad) {
		super();
		this.id = id;
		this.nit = nit;
		this.nombreEntidad = nombreEntidad;
	}

	public EntidadDTO(Long id, String nit, String nombreEntidad, String codigoSigep) {
		super();
		this.id = id;
		this.nit = nit;
		this.nombreEntidad = nombreEntidad;
		this.codigoSigep = codigoSigep;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TipoEntidadDTO getTipoEntidad() {
		return tipoEntidad;
	}

	public void setTipoEntidad(TipoEntidadDTO tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}

	public Long getEntidad() {
		return this.entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public String getCodigoSigep() {
		return this.codigoSigep;
	}

	public void setCodigoSigep(String codigoSigep) {
		this.codigoSigep = codigoSigep;
	}

	public BigDecimal getCodPais() {
		return this.codPais;
	}

	public void setCodPais(BigDecimal codPais) {
		this.codPais = codPais;
	}

	public BigDecimal getCodDepartamento() {
		return this.codDepartamento;
	}

	public void setCodDepartamento(BigDecimal codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public BigDecimal getCodMunicipio() {
		return this.codMunicipio;
	}

	public void setCodMunicipio(BigDecimal codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public BigDecimal getCodClasificacionOrganica() {
		return this.codClasificacionOrganica;
	}

	public void setCodClasificacionOrganica(BigDecimal codClasificacionOrganica) {
		this.codClasificacionOrganica = codClasificacionOrganica;
	}

	public BigDecimal getCodOrden() {
		return this.codOrden;
	}

	public void setCodOrden(BigDecimal codOrden) {
		this.codOrden = codOrden;
	}

	public BigDecimal getCodSuborden() {
		return this.codSuborden;
	}

	public void setCodSuborden(BigDecimal codSuborden) {
		this.codSuborden = codSuborden;
	}

	public BigDecimal getCodNaturaleza() {
		return this.codNaturaleza;
	}

	public void setCodNaturaleza(BigDecimal codNaturaleza) {
		this.codNaturaleza = codNaturaleza;
	}

	public BigDecimal getCodNivelAdministrativo() {
		return this.codNivelAdministrativo;
	}

	public void setCodNivelAdministrativo(BigDecimal codNivelAdministrativo) {
		this.codNivelAdministrativo = codNivelAdministrativo;
	}

	public BigDecimal getCodTipoAdscripcion() {
		return this.codTipoAdscripcion;
	}

	public void setCodTipoAdscripcion(BigDecimal codTipoAdscripcion) {
		this.codTipoAdscripcion = codTipoAdscripcion;
	}

	public BigDecimal getCodAdscritaVinculada() {
		return this.codAdscritaVinculada;
	}

	public void setCodAdscritaVinculada(BigDecimal codAdscritaVinculada) {
		this.codAdscritaVinculada = codAdscritaVinculada;
	}

	public BigDecimal getCodSectorAdministrativo() {
		return this.codSectorAdministrativo;
	}

	public void setCodSectorAdministrativo(BigDecimal codSectorAdministrativo) {
		this.codSectorAdministrativo = codSectorAdministrativo;
	}

	public BigDecimal getCodSistemaCarrera() {
		return this.codSistemaCarrera;
	}

	public void setCodSistemaCarrera(BigDecimal codSistemaCarrera) {
		this.codSistemaCarrera = codSistemaCarrera;
	}

	public BigDecimal getCodCategoria() {
		return this.codCategoria;
	}

	public void setCodCategoria(BigDecimal codCategoria) {
		this.codCategoria = codCategoria;
	}

	public BigDecimal getCodCodigodane() {
		return this.codCodigodane;
	}

	public void setCodCodigodane(BigDecimal codCodigodane) {
		this.codCodigodane = codCodigodane;
	}

	public BigDecimal getCodTipoNorma() {
		return this.codTipoNorma;
	}

	public void setCodTipoNorma(BigDecimal codTipoNorma) {
		this.codTipoNorma = codTipoNorma;
	}

	public BigDecimal getCodNorma() {
		return this.codNorma;
	}

	public void setCodNorma(BigDecimal codNorma) {
		this.codNorma = codNorma;
	}

	public BigDecimal getCodResponsableGestion() {
		return this.codResponsableGestion;
	}

	public void setCodResponsableGestion(BigDecimal codResponsableGestion) {
		this.codResponsableGestion = codResponsableGestion;
	}

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public BigDecimal isDigitoVerificacion() {
		return this.digitoVerificacion;
	}

	public void setDigitoVerificacion(BigDecimal digitoVerificacion) {
		this.digitoVerificacion = digitoVerificacion;
	}

	public String getNombreEntidad() {
		return this.nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getObjetivoEntidad() {
		return this.objetivoEntidad;
	}

	public void setObjetivoEntidad(String objetivoEntidad) {
		this.objetivoEntidad = objetivoEntidad;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getCodRepresentanteLegal() {
		return this.codRepresentanteLegal;
	}

	public void setCodRepresentanteLegal(String codRepresentanteLegal) {
		this.codRepresentanteLegal = codRepresentanteLegal;
	}

	public String getEmailEntidad() {
		return this.emailEntidad;
	}

	public void setEmailEntidad(String emailEntidad) {
		this.emailEntidad = emailEntidad;
	}

	public String getPaginaWeb() {
		return this.paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getFunciones() {
		return this.funciones;
	}

	public void setFunciones(String funciones) {
		this.funciones = funciones;
	}

	public BigDecimal getLatitud() {
		return this.latitud;
	}

	public void setLatitud(BigDecimal latitud) {
		this.latitud = latitud;
	}

	public BigDecimal getLongitud() {
		return this.longitud;
	}

	public void setLongitud(BigDecimal longitud) {
		this.longitud = longitud;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoEntidad() {
		return this.telefonoEntidad;
	}

	public void setTelefonoEntidad(String telefonoEntidad) {
		this.telefonoEntidad = telefonoEntidad;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public BigDecimal getCodEstadoEntidad() {
		return this.codEstadoEntidad;
	}

	public void setCodEstadoEntidad(BigDecimal codEstadoEntidad) {
		this.codEstadoEntidad = codEstadoEntidad;
	}

	public boolean isFlgEsCabeza() {
		return this.flgEsCabeza;
	}

	public void setFlgEsCabeza(boolean flgEsCabeza) {
		this.flgEsCabeza = flgEsCabeza;
	}

	public boolean isFlgDependenciaEspecial() {
		return this.flgDependenciaEspecial;
	}

	public void setFlgDependenciaEspecial(boolean flgDependenciaEspecial) {
		this.flgDependenciaEspecial = flgDependenciaEspecial;
	}

	public Boolean getEstatuto() {
		return this.estatuto;
	}

	public void setEstatuto(Boolean estatuto) {
		this.estatuto = estatuto;
	}

	public Boolean getPersoneriaJuridica() {
		return this.personeriaJuridica;
	}

	public void setPersoneriaJuridica(Boolean personeriaJuridica) {
		this.personeriaJuridica = personeriaJuridica;
	}

	public BigDecimal getCodRegimenSalarial() {
		return this.codRegimenSalarial;
	}

	public void setCodRegimenSalarial(BigDecimal codRegimenSalarial) {
		this.codRegimenSalarial = codRegimenSalarial;
	}

	public Boolean getEstructura() {
		return this.estructura;
	}

	public void setEstructura(Boolean estructura) {
		this.estructura = estructura;
	}

	public Boolean getPlanta() {
		return this.planta;
	}

	public void setPlanta(Boolean planta) {
		this.planta = planta;
	}

	public Boolean getManualFunciones() {
		return this.manualFunciones;
	}

	public void setManualFunciones(Boolean manualFunciones) {
		this.manualFunciones = manualFunciones;
	}

	public boolean isFlgAplica1785() {
		return this.flgAplica1785;
	}

	public void setFlgAplica1785(boolean flgAplica1785) {
		this.flgAplica1785 = flgAplica1785;
	}

	public List<UsuarioEntidadDTO> getUsuarioEntidads() {
		return this.usuarioEntidads;
	}

	public void setUsuarioEntidads(List<UsuarioEntidadDTO> usuarioEntidads) {
		this.usuarioEntidads = usuarioEntidads;
	}

	@Override
	public String toString() {
		return "EntidadDTO [id=" + id + ", entidad=" + entidad + ", codigoSigep=" + codigoSigep + ", codPais=" + codPais
				+ ", codDepartamento=" + codDepartamento + ", codMunicipio=" + codMunicipio
				+ ", codClasificacionOrganica=" + codClasificacionOrganica + ", codOrden=" + codOrden + ", codSuborden="
				+ codSuborden + ", codNaturaleza=" + codNaturaleza + ", codNivelAdministrativo="
				+ codNivelAdministrativo + ", codTipoAdscripcion=" + codTipoAdscripcion + ", codAdscritaVinculada="
				+ codAdscritaVinculada + ", codSectorAdministrativo=" + codSectorAdministrativo + ", codSistemaCarrera="
				+ codSistemaCarrera + ", codCategoria=" + codCategoria + ", codCodigodane=" + codCodigodane
				+ ", codTipoNorma=" + codTipoNorma + ", codNorma=" + codNorma + ", codResponsableGestion="
				+ codResponsableGestion + ", nit=" + nit + ", digitoVerificacion=" + digitoVerificacion
				+ ", nombreEntidad=" + nombreEntidad + ", sigla=" + sigla + ", objetivoEntidad=" + objetivoEntidad
				+ ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", codRepresentanteLegal="
				+ codRepresentanteLegal + ", emailEntidad=" + emailEntidad + ", paginaWeb=" + paginaWeb + ", funciones="
				+ funciones + ", latitud=" + latitud + ", longitud=" + longitud + ", direccion=" + direccion
				+ ", telefonoEntidad=" + telefonoEntidad + ", fax=" + fax + ", codEstadoEntidad=" + codEstadoEntidad
				+ ", flgEsCabeza=" + flgEsCabeza + ", flgDependenciaEspecial=" + flgDependenciaEspecial + ", estatuto="
				+ estatuto + ", personeriaJuridica=" + personeriaJuridica + ", codRegimenSalarial=" + codRegimenSalarial
				+ ", estructura=" + estructura + ", planta=" + planta + ", manualFunciones=" + manualFunciones
				+ ", flgAplica1785=" + flgAplica1785 + ", usuarioEntidads=" + usuarioEntidads + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nombreEntidad == null) ? 0 : nombreEntidad.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntidadDTO other = (EntidadDTO) obj;
		if (id != other.id)
			return false;
		if (nombreEntidad == null) {
			if (other.nombreEntidad != null)
				return false;
		} else if (!nombreEntidad.equals(other.nombreEntidad))
			return false;
		return true;
	}

}
