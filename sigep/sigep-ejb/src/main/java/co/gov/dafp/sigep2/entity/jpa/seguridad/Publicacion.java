package co.gov.dafp.sigep2.entity.jpa.seguridad;
// Generated 24/11/2017 03:31:14 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
//import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.seguridad.PublicacionDTO;


@Entity(name = "Publicacion")
@Table(name = "PUBLICACION")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Publicacion.PUBLICACION_MAPPING, classes = {
		@ConstructorResult(targetClass = PublicacionDTO.class, columns = {
				@ColumnResult(name = "COD_PUBLICACION", type = Long.class),
				@ColumnResult(name = "COD_PERSONA", type = Long.class),
				@ColumnResult(name = "COD_TIPO_ARTICULO", type = Long.class),
				@ColumnResult(name = "NOMBRE_ARTICULO", type = String.class),
				@ColumnResult(name = "COD_TIPO_PUBLICACION", type = Long.class),
				@ColumnResult(name = "NOMBRE_LIBRO", type = String.class),
				@ColumnResult(name = "COD_OTRO_TIPO_PUBLICACION", type = Long.class),
				@ColumnResult(name = "NOMBRE_PUBLICACION", type = String.class) }) }) })

public class Publicacion extends EntidadBase implements java.io.Serializable {
	
	private static final long serialVersionUID = 1876566329593827343L;
	public static final String PUBLICACION_MAPPING = "co.gov.dafp.sigep2.entity.jpa.seguridad.mapping.Publicacion";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PUBLICACION", unique = true, nullable = false, precision = 22, scale = 0)
	private Long codPublicacion;
	
	@Column(name = "COD_PERSONA", nullable = false, length = 20)
	private Long codPersona;
	
	@Column(name = "COD_TIPO_ARTICULO", precision = 126, nullable = false)
	private Long codTipoArticulo;

	@Column(name = "NOMBRE_ARTICULO", length = 50)
	private String nombreArticulo;

	@Column(name = "COD_TIPO_PUBLICACION", length = 20)
	private Long codTipoPublicacion;

	@Column(name = "NOMBRE_LIBRO", length = 50)
	private String nombreLibro;

	@Column(name = "COD_OTRO_TIPO_PUBLICACION", nullable = false)
	private Long codOtroTipoPublicacion;

	@Column(name = "NOMBRE_PUBLICACION", nullable = false)
	private String nombrePublicacion;

	@Temporal(TemporalType.DATE)
	@Column(name = "AUD_FECHA_ACTUALIZACION", nullable = false, length = 7)
	private Date audFechaActualizacion;

	@Column(name = "AUD_COD_ROL", nullable = false, precision = 22, scale = 0)
	private Long audCodRol;

	@Column(name = "AUD_ACCION", nullable = false, precision = 22, scale = 0)
	private Long audAccion;

	@Column(name = "AUD_COD_USUARIO", nullable = false, precision = 22, scale = 0)
	private Long audCodUsuario;

	public Publicacion() {
	}

	@Override
	public Long getId() {
		return this.codPublicacion;
	}

	@Override
	public void setId(Long id) {	
		this.codPublicacion = id;
	}

	public Long getCodTipoArticulo() {
		return codTipoArticulo;
	}

	public void setCodTipoArticulo(Long codTipoArticulo) {
		this.codTipoArticulo = codTipoArticulo;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public Long getCodTipoPublicacion() {
		return codTipoPublicacion;
	}

	public void setCodTipoPublicacion(Long codTipoPublicacion) {
		this.codTipoPublicacion = codTipoPublicacion;
	}

	public String getNombrePublicacion() {
		return nombrePublicacion;
	}

	public void setNombrePublicacion(String nombrePublicacion) {
		this.nombrePublicacion = nombrePublicacion;
	}

	public Long getCodOtroTipoPublicacion() {
		return codOtroTipoPublicacion;
	}

	public void setCodOtroTipoPublicacion(Long codOtroTipoPublicacion) {
		this.codOtroTipoPublicacion = codOtroTipoPublicacion;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
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

	public Long getAudCodUsuario() {
		return audCodUsuario;
	}

	public void setAudCodUsuario(Long audCodUsuario) {
		this.audCodUsuario = audCodUsuario;
	}
	
	@Override
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	@Override
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadBaseDTO getDTO() {
		return new PublicacionDTO(codPublicacion, codPersona, codTipoArticulo, nombreArticulo, codTipoPublicacion, nombreLibro, codOtroTipoPublicacion, nombrePublicacion);
	}
}