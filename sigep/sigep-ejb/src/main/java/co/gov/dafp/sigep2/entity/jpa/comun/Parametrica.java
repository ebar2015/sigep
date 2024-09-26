package co.gov.dafp.sigep2.entity.jpa.comun;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import org.hibernate.validator.constraints.Length;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.ParametricaDTO;

/**
 * Entity implementation class for Entity: Parametrica
 *
 */
@Entity(name = "Parametrica")
@Table(name = "PARAMETRICA")
@NamedQuery(name = Parametrica.FIND_BY_NOMBRE_PARAMETRO, query = "select p from Parametrica p where p.nombreParametro = :nombreParametro and p.flgEstado = :flgEstado")
@SqlResultSetMappings({ @SqlResultSetMapping(name = Parametrica.PARAMETRICA_MAPPING, classes = {
		@ConstructorResult(targetClass = ParametricaDTO.class, columns = {
				@ColumnResult(name = "COD_TABLA_PARAMETRICA", type = Long.class),
				@ColumnResult(name = "COD_PADRE_PARAMETRICA", type = Long.class),
				@ColumnResult(name = "NOMBRE_PARAMETRO", type = String.class),
				@ColumnResult(name = "VALOR_PARAMETRO", type = String.class), 
				@ColumnResult(name = "FLG_ESTADO", type = Boolean.class),
				@ColumnResult(name = "AUD_FECHA_ACTUALIZACION", type = Date.class),
				@ColumnResult(name = "AUD_COD_USUARIO", type = Long.class),
				@ColumnResult(name = "AUD_COD_ROL", type = Long.class),
				@ColumnResult(name = "AUD_ACCION", type = String.class),
				@ColumnResult(name = "TIPO_PARAMETRO", type = String.class),
				@ColumnResult(name = "DESCRIPCION", type = String.class)
		}) }) })
public class Parametrica extends EntidadBase implements Serializable {
	private static final long serialVersionUID = 9215898830400041026L;

	public static final String FIND_ALL = "co.gov.dafp.sigep2.entity.Parametrica.findAll";
	public static final String FIND_BY_NOMBRE_PARAMETRO = "co.gov.dafp.sigep2.entity.jpa.comun.Parametrica.findByNombreTabla";

	public static final String FIND_BY_REGISTRO_DESCRIPCION_MAESTRO_ID = "co.gov.dafp.sigep2.entity.jpa.comun.Parametrica.findByRegistroMaestroId";

	public static final String PAR_CARGUE_ARCHIVO = "PAR_CARGUE_ARCHIVO";
	public static final String PAR_TIPO_DOCUMENTO_IDENTIDAD = "PAR_TIPO_DOCUMENTO_IDENTIDAD";
	public static final String PAR_CARGUE_ARCHIVO_EJECUTAR = "PAR_CARGUE_ARCHIVO_EJECUTAR";
	
	public static final String PAR_DIAS_DESCARGAR_INFO = "PAR_DIAS_DESCARGAR_INFO";
	
	public static final String PARAMETRICA_MAPPING = "co.gov.dafp.sigep2.entity.jpa.comun.mapping.Parametrica";
	
	@Id
	@Column(name = "COD_TABLA_PARAMETRICA", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Parametrica.class)
	@JoinColumn(name = "COD_PADRE_PARAMETRICA", referencedColumnName = "COD_TABLA_PARAMETRICA")
	private Parametrica codPadreParametrica;

	@Column(name = "NOMBRE_PARAMETRO")
	@Convert(converter = UpperCaseConverter.class)
	private String nombreParametro;

	@Column(name = "VALOR_PARAMETRO")
	@Convert(converter = UpperCaseConverter.class)
	private String valorParametro;

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
	
	/*
	 * Esta columna se hacer, por convención, de la siguiente manera:
	 * 'T' para tipo Tabla
	 * 'P' para tipo Parámetro
	 * 'S' para tipo Sistema
	 * 
	 * De esa manera se puede saber qué parámetros listar en el CU_601
	 * 
	 */
	@Column(name = "TIPO_PARAMETRO", nullable = false)
	private String tipoParametro;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public Parametrica() {
		super();
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

	public void setCodPadreParametrica(Parametrica maestroId) {
		this.codPadreParametrica = maestroId;
	}

	public String getNombreParametro() {
		return nombreParametro;
	}

	public void setNombreParametro(String registro) {
		this.nombreParametro = registro;
	}

	public String getValorParametro() {
		return valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
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
	
	public String getTipoParametro() {
		return tipoParametro;
	}

	public void setTipoParametro(String tipoParametro) {
		this.tipoParametro = tipoParametro;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "Parametrica [id=" + id + ", codPadreParametrica=" + codPadreParametrica + ", nombreParametro="
				+ nombreParametro + ", valorParametro=" + valorParametro + ", flgEstado=" + flgEstado
				+ ", audFechaActualizacion=" + audFechaActualizacion + ", audCodUsuario=" + audCodUsuario
				+ ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + ", tipoParametro=" + tipoParametro + "]";
	}
	
	@Override
	public EntidadBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
