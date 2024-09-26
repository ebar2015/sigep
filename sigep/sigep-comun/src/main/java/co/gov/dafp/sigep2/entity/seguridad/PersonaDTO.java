package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.view.ClaseLibretaMilitarDTO;
import co.gov.dafp.sigep2.entity.view.EstadoCivilDTO;
import co.gov.dafp.sigep2.entity.view.GeneroDTO;
import co.gov.dafp.sigep2.entity.view.PoblacionEtnicaDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.util.DateUtils;

/**
 * The persistent class for the PERSONA database table.
 */
public class PersonaDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 4040037313631254729L;

	public static final String NAME_SUPER_ADMIN = "SUPER ADMIN";

	private Long codGenero;
	private EstadoCivilDTO codEstadoCivil;
	private PaisDTO codPaisNacimiento;
	private DepartamentoDTO codDepartamentoNacimiento;
	private MunicipioDTO codMunicipioNacimiento;
	private Long codFactorRh;
	private Long codTipoIdentificacion;
	private String resumenProfesional;
	private String numeroLibretaMilitar;
	private String distritoMilitar;
	private ClaseLibretaMilitarDTO codClaseLibretaMilitar;
	private String libretaMilitarUrl;
	private String fotoUrl;
	private String documentoIdentificacionUrl;
	private Date fechaNacimiento;
	private Boolean expuestoPoliticamente;
	private String correoAlternativo;
	private Boolean flgActivo;
	private Boolean flgEstado;
	private PoblacionEtnicaDTO codPertenenciaEtnica;
	private long id;
	private String apartadoAereo;
	private MunicipioDTO ciudadId;
	private String correoElectronico;
	private String direccion;
	private String direccionWeb;
	private Date fechaExpedicionIdentificacio;
	private String numeroIdentificacion;
	private String primerApellido;
	private String primerNombre;
	private String segundoApellido;
	private String segundoNombre;
	private String telefono;
	private TipoDocumentoDTO tipoIdentificacionId;
	private GeneroDTO genero;
	private MunicipioDTO ciudadExpedicion;
	private Boolean estado = Boolean.TRUE;
	private Long codEntidad;
	private String nombreEntidad;
	private String nombreDependencia;

	private BigDecimal total;

	public PersonaDTO() {
	}

	public PersonaDTO(long idPersona, Long codEntidad, Long codTipoIdentificacion, String numeroIdentificacion,
			String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
		super();
		this.id = idPersona;
		this.codEntidad = codEntidad;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
	}

	public PersonaDTO(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido,
			String correoElectronico, String correoAlternativo) {
		super();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
	}

	public PersonaDTO(String numeroLibretaMilitar, long id, String numeroIdentificacion, String primerApellido,
			String primerNombre) {
		super();
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.id = id;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerApellido = primerApellido;
		this.primerNombre = primerNombre;
	}

	public PersonaDTO(long id, String apartadoAereo, MunicipioDTO ciudadId, String correoElectronico, String direccion,
			String direccionWeb, Date fechaExpedicionIdentificacio, String numeroIdentificacion, String primerApellido,
			String primerNombre, String segundoApellido, String segundoNombre, String telefono,
			TipoDocumentoDTO tipoIdentificacionId, MunicipioDTO ciudadExpedicion, Boolean estado) {
		super();
		this.id = id;
		this.apartadoAereo = apartadoAereo;
		this.ciudadId = ciudadId;
		this.correoElectronico = correoElectronico;
		this.direccion = direccion;
		this.direccionWeb = direccionWeb;
		this.fechaExpedicionIdentificacio = fechaExpedicionIdentificacio;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerApellido = primerApellido;
		this.primerNombre = primerNombre;
		this.segundoApellido = segundoApellido;
		this.segundoNombre = segundoNombre;
		this.telefono = telefono;
		this.tipoIdentificacionId = tipoIdentificacionId;
		this.ciudadExpedicion = ciudadExpedicion;
		this.estado = estado;
	}

	public PersonaDTO(long id, Long codGenero, Long codFactorRh, Long codTipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String resumenProfesional, String numeroLibretaMilitar, String distritoMilitar,
			String libretaMilitarUrl, String fotoUrl, String documentoIdentificacionUrl, Date fechaNacimiento,
			Boolean expuestoPoliticamente, String correoElectronico, String correoAlternativo, Boolean flgActivo,
			Boolean flgEstado) {
		this.id = id;
		this.codGenero = codGenero;
		this.codFactorRh = codFactorRh;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.resumenProfesional = resumenProfesional;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.libretaMilitarUrl = libretaMilitarUrl;
		this.fotoUrl = fotoUrl;
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
		this.fechaNacimiento = fechaNacimiento;
		this.expuestoPoliticamente = expuestoPoliticamente;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
	}

	public PersonaDTO(long id, Long codGenero, EstadoCivilDTO codEstadoCivil, PaisDTO codPaisNacimiento,
			DepartamentoDTO codDepartamentoNacimiento, MunicipioDTO codMunicipioNacimiento, Long codFactorRh,
			Long codTipoIdentificacion, String numeroIdentificacion, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, String resumenProfesional, String numeroLibretaMilitar,
			ClaseLibretaMilitarDTO codClaseLibretaMilitar, String distritoMilitar, String libretaMilitarUrl,
			String fotoUrl, String documentoIdentificacionUrl, Date fechaNacimiento, Boolean expuestoPoliticamente,
			String correoElectronico, String correoAlternativo, Boolean flgActivo, Boolean flgEstado,
			PoblacionEtnicaDTO codPertenenciaEtnica) {
		this.id = id;
		this.codGenero = codGenero;
		this.codEstadoCivil = codEstadoCivil;
		this.codPaisNacimiento = codPaisNacimiento;
		this.codDepartamentoNacimiento = codDepartamentoNacimiento;
		this.codMunicipioNacimiento = codMunicipioNacimiento;
		this.codFactorRh = codFactorRh;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.resumenProfesional = resumenProfesional;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.codClaseLibretaMilitar = codClaseLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.libretaMilitarUrl = libretaMilitarUrl;
		this.fotoUrl = fotoUrl;
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
		this.fechaNacimiento = fechaNacimiento;
		this.expuestoPoliticamente = expuestoPoliticamente;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
		this.codPertenenciaEtnica = codPertenenciaEtnica;
	}

	public PersonaDTO(long id, Long codGenero, Long codFactorRh, Long codTipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String resumenProfesional, String numeroLibretaMilitar, String distritoMilitar,
			String libretaMilitarUrl, String fotoUrl, String documentoIdentificacionUrl, Date fechaNacimiento,
			Boolean expuestoPoliticamente, String correoElectronico, String correoAlternativo, Boolean flgActivo,
			Boolean flgEstado, BigDecimal total, String nombreEntidad, String nombreDependencia) {
		this.id = id;
		this.codGenero = codGenero;
		this.codFactorRh = codFactorRh;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.resumenProfesional = resumenProfesional;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.libretaMilitarUrl = libretaMilitarUrl;
		this.fotoUrl = fotoUrl;
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
		this.fechaNacimiento = fechaNacimiento;
		this.expuestoPoliticamente = expuestoPoliticamente;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
		this.total = total;
		this.nombreEntidad = nombreEntidad;
		this.nombreDependencia = nombreDependencia;
	}

	public PersonaDTO(long id, Long codGenero, Long codFactorRh, Long codTipoIdentificacion,
			String numeroIdentificacion, String primerNombre, String segundoNombre, String primerApellido,
			String segundoApellido, String resumenProfesional, String numeroLibretaMilitar, String distritoMilitar,
			String libretaMilitarUrl, String fotoUrl, String documentoIdentificacionUrl, Date fechaNacimiento,
			Boolean expuestoPoliticamente, String correoElectronico, String correoAlternativo, Boolean flgActivo,
			Boolean flgEstado, BigDecimal total, Long codEntidad, String nombreEntidad, String nombreDependencia) {
		this.id = id;
		this.codGenero = codGenero;
		this.codFactorRh = codFactorRh;
		this.codTipoIdentificacion = codTipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion.toUpperCase();
		this.primerNombre = primerNombre;
		this.segundoNombre = segundoNombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.resumenProfesional = resumenProfesional;
		this.numeroLibretaMilitar = numeroLibretaMilitar;
		this.distritoMilitar = distritoMilitar;
		this.libretaMilitarUrl = libretaMilitarUrl;
		this.fotoUrl = fotoUrl;
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
		this.fechaNacimiento = fechaNacimiento;
		this.expuestoPoliticamente = expuestoPoliticamente;
		this.correoElectronico = correoElectronico;
		this.correoAlternativo = correoAlternativo;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
		this.total = total;
		this.codEntidad = codEntidad;
		this.nombreEntidad = nombreEntidad;
		this.nombreDependencia = nombreDependencia;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApartadoAereo() {
		return apartadoAereo;
	}

	public void setApartadoAereo(String apartadoAereo) {
		this.apartadoAereo = apartadoAereo;
	}

	public MunicipioDTO getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(MunicipioDTO ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getDireccion() {
		return CapitalCaseConverter.convert(direccion);
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionWeb() {
		return direccionWeb;
	}

	public void setDireccionWeb(String direccionWeb) {
		this.direccionWeb = direccionWeb;
	}

	public Date getFechaExpedicionIdentificacio() {
		return fechaExpedicionIdentificacio;
	}

	public void setFechaExpedicionIdentificacio(Date fechaExpedicionIdentificacio) {
		this.fechaExpedicionIdentificacio = fechaExpedicionIdentificacio;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion != null ? numeroIdentificacion.toUpperCase()
				: numeroIdentificacion;
	}

	// public String getPrimerApellido() {
	// return primerApellido.substring(0, 1).toUpperCase() +
	// primerApellido.substring(1).toLowerCase();
	// }

	public String getPrimerApellido() {
		return CapitalCaseConverter.convert(primerApellido);
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getPrimerNombre() {
		return CapitalCaseConverter.convert(primerNombre);
	}

	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	public String getSegundoApellido() {
		return CapitalCaseConverter.convert(segundoApellido);
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getSegundoNombre() {
		return CapitalCaseConverter.convert(segundoNombre);
	}

	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public TipoDocumentoDTO getTipoIdentificacionId() {
		return tipoIdentificacionId;
	}

	public void setTipoIdentificacionId(TipoDocumentoDTO tipoIdentificacionId) {
		this.tipoIdentificacionId = tipoIdentificacionId;
	}

	public GeneroDTO getGenero() {
		return genero;
	}

	public void setGenero(GeneroDTO genero) {
		this.genero = genero;
	}

	public MunicipioDTO getCiudadExpedicion() {
		return ciudadExpedicion;
	}

	public void setCiudadExpedicion(MunicipioDTO ciudadExpedicion) {
		this.ciudadExpedicion = ciudadExpedicion;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		boolean first = true;
		StringBuilder ret = new StringBuilder();
		if (primerNombre != null && !primerNombre.isEmpty()) {
			first = false;
			ret.append(primerNombre);
		}
		if (segundoNombre != null && !segundoNombre.isEmpty()) {
			if (first) {
				first = false;
				ret.append(segundoNombre);
			} else {
				ret.append(" ").append(segundoNombre);
			}
		}
		if (primerApellido != null && !primerApellido.isEmpty()) {
			if (first) {
				first = false;
				ret.append(primerApellido);
			} else {
				ret.append(" ").append(primerApellido);
			}
		}
		if (segundoApellido != null && !segundoApellido.isEmpty()) {
			if (first) {
				ret.append(segundoApellido);
			} else {
				ret.append(" ").append(segundoApellido);
			}
		}
		return ret.toString();

	}

	public String displayName() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.primerNombre);
		if (this.segundoNombre != null && !this.segundoNombre.trim().isEmpty()) {
			sb.append(" ");
			sb.append(this.segundoNombre.trim());
		}
		sb.append(" ");
		sb.append(this.primerApellido);
		if (this.segundoApellido != null && !this.segundoApellido.trim().isEmpty()) {
			sb.append(" ");
			sb.append(this.segundoApellido.trim());
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", apartadoAereo=" + apartadoAereo + ", ciudadId=" + ciudadId
				+ ", correoElectronico=" + correoElectronico + ", direccion=" + direccion + ", direccionWeb="
				+ direccionWeb + ", fechaExpedicionIdentificacio="
				+ DateUtils.formatearACadena(fechaExpedicionIdentificacio, DateUtils.FECHA_HORA_FORMATO_VO)
				+ ", numeroIdentificacion=" + numeroIdentificacion + ", primerApellido=" + primerApellido
				+ ", primerNombre=" + primerNombre + ", segundoApellido=" + segundoApellido + ", segundoNombre="
				+ segundoNombre + ", telefono=" + telefono + ", tipoIdentificacionId=" + tipoIdentificacionId
				+ ", ciudadExpedicion=" + ciudadExpedicion + ", estado=" + estado + "]";
	}

	public Long getCodGenero() {
		return codGenero;
	}

	public void setCodGenero(Long codGenero) {
		this.codGenero = codGenero;
	}

	public EstadoCivilDTO getCodEstadoCivil() {
		return codEstadoCivil;
	}

	public void setCodEstadoCivil(EstadoCivilDTO codEstadoCivil) {
		this.codEstadoCivil = codEstadoCivil;
	}

	public PaisDTO getCodPaisNacimiento() {
		return codPaisNacimiento;
	}

	public void setCodPaisNacimiento(PaisDTO codPaisNacimiento) {
		this.codPaisNacimiento = codPaisNacimiento;
	}

	public DepartamentoDTO getCodDepartamentoNacimiento() {
		return codDepartamentoNacimiento;
	}

	public void setCodDepartamentoNacimiento(DepartamentoDTO codDepartamentoNacimiento) {
		this.codDepartamentoNacimiento = codDepartamentoNacimiento;
	}

	public MunicipioDTO getCodMunicipioNacimiento() {
		return codMunicipioNacimiento;
	}

	public void setCodMunicipioNacimiento(MunicipioDTO codMunicipioNacimiento) {
		this.codMunicipioNacimiento = codMunicipioNacimiento;
	}

	public Long getCodFactorRh() {
		return codFactorRh;
	}

	public void setCodFactorRh(Long codFactorRh) {
		this.codFactorRh = codFactorRh;
	}

	public Long getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	public void setCodTipoIdentificacion(Long codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	public String getResumenProfesional() {
		return resumenProfesional;
	}

	public void setResumenProfesional(String resumenProfesional) {
		this.resumenProfesional = resumenProfesional;
	}

	public String getNumeroLibretaMilitar() {
		return numeroLibretaMilitar;
	}

	public void setNumeroLibretaMilitar(String numeroLibretaMilitar) {
		this.numeroLibretaMilitar = numeroLibretaMilitar;
	}

	public String getDistritoMilitar() {
		return distritoMilitar;
	}

	public void setDistritoMilitar(String distritoMilitar) {
		this.distritoMilitar = distritoMilitar;
	}

	public ClaseLibretaMilitarDTO getCodClaseLibretaMilitar() {
		return codClaseLibretaMilitar;
	}

	public void setCodClaseLibretaMilitar(ClaseLibretaMilitarDTO codClaseLibretaMilitar) {
		this.codClaseLibretaMilitar = codClaseLibretaMilitar;
	}

	public String getLibretaMilitarUrl() {
		return libretaMilitarUrl;
	}

	public void setLibretaMilitarUrl(String libretaMilitarUrl) {
		this.libretaMilitarUrl = libretaMilitarUrl;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public String getDocumentoIdentificacionUrl() {
		return documentoIdentificacionUrl;
	}

	public void setDocumentoIdentificacionUrl(String documentoIdentificacionUrl) {
		this.documentoIdentificacionUrl = documentoIdentificacionUrl;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getExpuestoPoliticamente() {
		return expuestoPoliticamente;
	}

	public void setExpuestoPoliticamente(Boolean expuestoPoliticamente) {
		this.expuestoPoliticamente = expuestoPoliticamente;
	}

	public String getCorreoAlternativo() {
		return correoAlternativo;
	}

	public void setCorreoAlternativo(String correoAlternativo) {
		this.correoAlternativo = correoAlternativo;
	}

	public Boolean getFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(Boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public Boolean getFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(Boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public PoblacionEtnicaDTO getCodPertenenciaEtnica() {
		return codPertenenciaEtnica;
	}

	public void setCodPertenenciaEtnica(PoblacionEtnicaDTO codPertenenciaEtnica) {
		this.codPertenenciaEtnica = codPertenenciaEtnica;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Long getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(Long codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getNombreDependencia() {
		return nombreDependencia;
	}

	public void setNombreDependencia(String nombreDependencia) {
		this.nombreDependencia = nombreDependencia;
	}

}
