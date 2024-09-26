package co.gov.dafp.sigep2.entity.jpa.seguridad;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

//import org.hibernate.validator.constraints.Length;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.jpa.comun.Parametrica;

/**
 * Entity implementation class for Entity: TipoDocumentoIdentidad
 *
 */
@Entity(name = "TipoDocumentoIdentidad")
@Table(name = "PARAMETRICA")
public class TipoDocumentoIdentidad extends EntidadBase implements Serializable {
	private static final long serialVersionUID = 9215898830400041026L;

	@Id
	@Column(name = "COD_TABLA_PARAMETRICA", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Parametrica.class)
	@JoinColumn(name = "COD_PADRE_PARAMETRICA", referencedColumnName = "COD_TABLA_PARAMETRICA")
	private Parametrica codPadreParametrica;

	@Column(name = "NOMBRE_PARAMETRO")
	@Convert(converter = UpperCaseConverter.class)
	private String sigla;

	@Column(name = "FLG_ESTADO")
	private boolean flgEstado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	protected Date audFechaActualizacion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	protected Long audCodUsuario;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	protected Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	protected Long audAccion;

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Parametrica getCodPadreParametrica() {
		return codPadreParametrica;
	}

	public void setCodPadreParametrica(Parametrica codPadreParametrica) {
		this.codPadreParametrica = codPadreParametrica;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
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
		return "TipoDocumentoIdentidad [id=" + id + ", codPadreParametrica=" + codPadreParametrica + ", sigla=" + sigla
				+ ", flgEstado=" + flgEstado + ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario="
				+ audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
