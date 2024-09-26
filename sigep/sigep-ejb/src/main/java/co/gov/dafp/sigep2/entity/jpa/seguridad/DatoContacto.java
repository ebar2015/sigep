package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Departamento;
import co.gov.dafp.sigep2.entity.jpa.comun.Municipio;
import co.gov.dafp.sigep2.entity.jpa.comun.Pais;
import co.gov.dafp.sigep2.entity.seguridad.DatoContactoDTO;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;
import co.gov.dafp.sigep2.view.TipoZona;

@Entity(name = "DatoContacto")
@Table(name = "DATO_CONTACTO")
public class DatoContacto extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 5805070928150957024L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_DATOS_CONTACTO", insertable = false, updatable = false, unique = true, nullable = false)
	private long codDatosContacto;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PERSONA")
	private Persona codPersona;
	
	@Column(name = "TELEFONO_RESIDENCIA", precision = 22, scale = 0)
	private Long telefonoResidencia;
	
	@Column(name = "INDICATIVO", precision = 22, scale = 0)
	private Long indicativo;
	
	@Column(name = "NUM_CELULAR", precision = 22, scale = 0)
	private Long numCelular;
	
	@Column(name = "NUM_TELEFONO_OFICINA", precision = 22, scale = 0)
	private Long numTelefonoOficina;
	
	@Column(name = "EXTENCION_TELEFONO_OFICINA", precision = 22, scale = 0)
	private Long extencionTelefonoOficina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_PAIS_RESIDENCIA", nullable = true)
	private Pais codPaisResidencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_DEPARTAMENTO_RESIDENCIA", nullable = true)
	private Departamento codDepartamentoResidencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_MUNICIPIO_RESIDENCIA", nullable = true)
	private Municipio codMunicipioResidencia;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COD_TIPO_ZONA", nullable = true)
	private TipoZona codTipoZona;
	
	@Column(name = "DIRECCION_RESIDENCIA", nullable = false, length = 255)
	private String direccionResidencia;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public DatoContacto() {
	}
	
	public Long getId() {
		return this.codDatosContacto;
	}

	public void setId(Long id) {
		this.codDatosContacto = id;
	}

	public long getCodDatosContacto() {
		return this.codDatosContacto;
	}

	public void setCodDatosContacto(long codDatosContacto) {
		this.codDatosContacto = codDatosContacto;
	}

	public Persona getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Persona codPersona) {
		this.codPersona = codPersona;
	}

	public Long getTelefonoResidencia() {
		return telefonoResidencia;
	}

	public void setTelefonoResidencia(Long telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}

	public Long getIndicativo() {
		return indicativo;
	}

	public void setIndicativo(Long indicativo) {
		this.indicativo = indicativo;
	}

	public Long getNumCelular() {
		return numCelular;
	}

	public void setNumCelular(Long numCelular) {
		this.numCelular = numCelular;
	}

	public Long getNumTelefonoOficina() {
		return numTelefonoOficina;
	}

	public void setNumTelefonoOficina(Long numTelefonoOficina) {
		this.numTelefonoOficina = numTelefonoOficina;
	}

	public Long getExtencionTelefonoOficina() {
		return extencionTelefonoOficina;
	}

	public void setExtencionTelefonoOficina(Long extencionTelefonoOficina) {
		this.extencionTelefonoOficina = extencionTelefonoOficina;
	}

	public Pais getCodPaisResidencia() {
		return codPaisResidencia;
	}

	public void setCodPaisResidencia(Pais codPaisResidencia) {
		this.codPaisResidencia = codPaisResidencia;
	}

	public Departamento getCodDepartamentoResidencia() {
		return codDepartamentoResidencia;
	}

	public void setCodDepartamentoResidencia(Departamento codDepartamentoResidencia) {
		this.codDepartamentoResidencia = codDepartamentoResidencia;
	}

	public Municipio getCodMunicipioResidencia() {
		return codMunicipioResidencia;
	}

	public void setCodMunicipioResidencia(Municipio codMunicipioResidencia) {
		this.codMunicipioResidencia = codMunicipioResidencia;
	}

	public TipoZona getCodTipoZona() {
		return codTipoZona;
	}

	public void setCodTipoZona(TipoZona codTipoZona) {
		this.codTipoZona = codTipoZona;
	}

	public String getDireccionResidencia() {
		return direccionResidencia;
	}

	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}

	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}

	public Long getAudCodRol() {
		return audCodRol;
	}

	public void setAudCodRol(Long audCodRol) {
		this.audCodRol = audCodRol;
	}

	public Long getAudAccion() {
		return audAccion;
	}

	public void setAudAccion(Long audAccion) {
		this.audAccion = audAccion;
	}

	@Override
	public String toString() {
		return "DatoContacto [codDatosContacto=" + codDatosContacto + ", codPersona=" + codPersona
				+ ", telefonoResidencia=" + telefonoResidencia + ", indicativo=" + indicativo + ", numCelular="
				+ numCelular + ", numTelefonoOficina=" + numTelefonoOficina + ", extencionTelefonoOficina="
				+ extencionTelefonoOficina + ", codPaisResidencia=" + codPaisResidencia + ", codDepartamentoResidencia="
				+ codDepartamentoResidencia + ", codMunicipioResidencia=" + codMunicipioResidencia + ", codTipoZona="
				+ codTipoZona + ", direccionResidencia=" + direccionResidencia + ", audFechaActualizacion="
				+ audFechaActualizacion + ", audCodUsuario=" + audCodUsuario + ", audCodRol=" + audCodRol
				+ ", audAccion=" + audAccion + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		
		TipoZonaDTO tipoZona = codTipoZona != null ? (TipoZonaDTO)codTipoZona.getDTO() : null;
		PaisDTO paisResidencia = codPaisResidencia != null ? (PaisDTO)codPaisResidencia.getDTO() : null;
		DepartamentoDTO departamentoResidencia = codDepartamentoResidencia != null ? (DepartamentoDTO)codDepartamentoResidencia.getDTO() : null;
		MunicipioDTO municipioResidencia = codMunicipioResidencia != null ? (MunicipioDTO)codMunicipioResidencia.getDTO() : null;
		
		return new DatoContactoDTO(codDatosContacto, codPersona.getId(), telefonoResidencia, indicativo,
				 numCelular,  numTelefonoOficina, extencionTelefonoOficina,  paisResidencia, departamentoResidencia,
				 municipioResidencia, tipoZona, direccionResidencia);
	}
}
