/*
 * Esta clase es propiedad de la fábrica de software Bogotá de ADA S.A.
 * Todos los derechos reservados (c) 2015.
 */
package co.gov.dafp.sigep2.view;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.BooleanToNumberConverter;
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Entidad;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;

/**
 * Entidad que representa los recursos
 *
 * @author Jhon De Avila Mercado <jhon.mercado en ADA S.A.>
 */
@Entity(name = "RecursoActivoPerfilUsuario")
@Table(name = "V_RECURSO_ACTIVO_PERFIL_USUARIO")
@SqlResultSetMappings({
		@SqlResultSetMapping(name = RecursoActivoPerfilUsuario.RECURSO_ACTIVO_PERFIL_USUARIO_MAPPING, classes = {
				@ConstructorResult(targetClass = RecursoActivoPerfilUsuarioDTO.class, columns = {
						@ColumnResult(name = "COD_RECURSO", type = Long.class),
						@ColumnResult(name = "DESCRIPCION", type = String.class),
						@ColumnResult(name = "CODIGO_VENTANA", type = String.class),
						@ColumnResult(name = "ORDEN", type = Long.class),
						@ColumnResult(name = "TITULO_VENTANA", type = String.class),
						@ColumnResult(name = "IDENTIFICADOR", type = String.class),
						@ColumnResult(name = "PAGINA", type = String.class),
						@ColumnResult(name = "MOSTRAR_EN_MENU", type = Boolean.class),
						@ColumnResult(name = "ICONO_MENU", type = String.class),
						@ColumnResult(name = "IMAGEN_MENU", type = String.class),
						@ColumnResult(name = "COLOR_MENU", type = String.class),
						@ColumnResult(name = "FLG_BLOQUEADO", type = Boolean.class),
						@ColumnResult(name = "FLG_ESTADO", type = Boolean.class),
						@ColumnResult(name = "LOGIN", type = String.class),
						@ColumnResult(name = "COD_RECURSO_PADRE", type = Long.class),
						@ColumnResult(name = "ENTIDAD", type = Long.class),
						@ColumnResult(name = "ROL", type = Long.class) }) }) })
public class RecursoActivoPerfilUsuario extends VistaBase {
	private static final long serialVersionUID = -2841192108427991565L;

	public static final String RECURSO_ACTIVO_PERFIL_USUARIO_MAPPING = "co.gov.dafp.sigep2.view.mapping.RecursoActivoPerfilUsuario";

	public static final String FIND_RECURSOS_POR_LOGIN_USUARIO = "SELECT r FROM RecursoActivoPerfilUsuario ra, Recurso r "
			+ "WHERE ra.id = r.id AND (ra.login=:login or ra.login is null) AND ra.codigoPadre IS NOT NULL ORDER BY r.codigoPadre, r.orden";

	@Id
	private Long id;
	@Size(max = 50)
	@Column(name = "DESCRIPCION", insertable = false, updatable = false)
	private String descripcion;
	@Size(max = 50)
	@NotNull
	@Column(name = "CODIGO_VENTANA", insertable = false, updatable = false)
	private String codigoVentana;
	@Column(name = "ORDEN")
	private Long orden;
	@Size(max = 80)
	@Column(name = "TITULO_VENTANA", insertable = false, updatable = false)
	private String tituloVentana;
	@Size(max = 200)
	@Column(name = "IDENTIFICADOR", insertable = false, updatable = false)
	private String identificador;
	@Size(max = 500)
	@Column(name = "PAGINA", insertable = false, updatable = false)
	private String pagina;
	@Column(name = "MOSTRAR_EN_MENU", insertable = false, updatable = false)
	private Boolean mostrarEnMenu;
	@Column(name = "ES_RECURSO_BPM", insertable = false, updatable = false)
	@NotNull
	private Boolean esRecursoBPM;
	@Column(name = "ACTIVIDAD_BPM_ID", insertable = false, updatable = false)
	private Integer idActividadBPM;
	@Column(name = "MOSTRAR_EN_INDICE_AGRUPADO", insertable = false, updatable = false)
	@NotNull
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean mostrarEnIndiceAgrupado;
	@Column(name = "ICONO_MENU", insertable = false, updatable = false)
	@Size(max = 100)
	private String iconoMenu;
	@Column(name = "IMAGEN_MENU", insertable = false, updatable = false)
	@Size(max = 100)
	private String imagenMenu;
	@Column(name = "COLOR_MENU", insertable = false, updatable = false)
	@Pattern(regexp = ExpresionesRegularesConstants.REGEX_NUMERO_HEXAGECIMAL, message = MessagesBundleConstants.MSG_FATAL_COLOR_HEX)
	@Size(max = 30)
	private String colorMenu;
	@Column(name = "INHABILITAR_DURANTE_CIERRE", insertable = false, updatable = false)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean inhabilitarDuranteCierre = Boolean.FALSE;
	@Basic(optional = false)
	@NotNull
	@Column(name = "BLOQUEADO", insertable = false, updatable = false)
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean bloqueado = Boolean.FALSE;
	@Basic(optional = false)
	@NotNull
	@Column(name = "ESTADO")
	@Convert(converter = BooleanToNumberConverter.class)
	private Boolean estado = Boolean.TRUE;
	@Column(insertable = false, updatable = false)
	private String login;
	@ManyToOne(targetEntity = RecursoActivoPerfilUsuario.class)
	@JoinColumn(name = "CODIGO_PADRE", referencedColumnName = "ID", insertable = false, updatable = false)
	private RecursoActivoPerfilUsuario codigoPadre;
	@ManyToOne(targetEntity = Entidad.class)
	@JoinColumn(name = "ENTIDAD", referencedColumnName = "COD_ENTIDAD", insertable = false, updatable = false)
	private Entidad entidad;
	@ManyToOne(targetEntity = Rol.class)
	@JoinColumn(name = "ROL", referencedColumnName = "COD_ROL", insertable = false, updatable = false)
	private Rol rol;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoVentana() {
		return codigoVentana;
	}

	public void setCodigoVentana(String codigoVentana) {
		this.codigoVentana = codigoVentana;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
		this.orden = orden;
	}

	public RecursoActivoPerfilUsuario getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(RecursoActivoPerfilUsuario codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public Entidad getEntidad() {
		return entidad;
	}

	public void setEntidad(Entidad entidad) {
		this.entidad = entidad;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getTituloVentana() {
		return tituloVentana;
	}

	public void setTituloVentana(String tituloVentana) {
		this.tituloVentana = tituloVentana;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public Boolean getEsRecursoBPM() {
		return esRecursoBPM;
	}

	public void setEsRecursoBPM(Boolean esRecursoBPM) {
		this.esRecursoBPM = esRecursoBPM;
	}

	public Integer getIdActividadBPM() {
		return idActividadBPM;
	}

	public void setIdActividadBPM(Integer idActividadBPM) {
		this.idActividadBPM = idActividadBPM;
	}

	public Boolean getMostrarEnIndiceAgrupado() {
		return mostrarEnIndiceAgrupado;
	}

	public void setMostrarEnIndiceAgrupado(Boolean mostrarEnIndiceAgrupado) {
		this.mostrarEnIndiceAgrupado = mostrarEnIndiceAgrupado;
	}

	public String getIconoMenu() {
		return iconoMenu;
	}

	public void setIconoMenu(String iconoMenu) {
		this.iconoMenu = iconoMenu;
	}

	public String getImagenMenu() {
		return imagenMenu;
	}

	public void setImagenMenu(String imagenMenu) {
		this.imagenMenu = imagenMenu;
	}

	public String getColorMenu() {
		return colorMenu;
	}

	public void setColorMenu(String colorMenu) {
		this.colorMenu = colorMenu;
	}

	public Boolean getInhabilitarDuranteCierre() {
		return inhabilitarDuranteCierre;
	}

	public void setInhabilitarDuranteCierre(Boolean inhabilitarDuranteCierre) {
		this.inhabilitarDuranteCierre = inhabilitarDuranteCierre;
	}

	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Recurso [id=" + id + ", descripcion=" + descripcion + ", codigoVentana=" + codigoVentana + ", orden="
				+ orden + ", codigoPadre=" + (codigoPadre != null ? codigoPadre.getId() : null) + ", tituloVentana="
				+ tituloVentana + ", identificador=" + identificador + ", pagina=" + pagina + ", esRecursoBPM="
				+ esRecursoBPM + ", idActividadBPM=" + idActividadBPM + ", mostrarEnIndiceAgrupado="
				+ mostrarEnIndiceAgrupado + ", iconoMenu=" + iconoMenu + ", imagenMenu=" + imagenMenu + ", colorMenu="
				+ colorMenu + ", inhabilitarDuranteCierre=" + inhabilitarDuranteCierre + ", bloqueado=" + bloqueado
				+ ", estado=" + estado + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		return new RecursoActivoPerfilUsuarioDTO(id, descripcion, codigoVentana, orden, tituloVentana, identificador,
				pagina, mostrarEnMenu, iconoMenu, imagenMenu, colorMenu, bloqueado, estado, login,
				codigoPadre != null ? codigoPadre.getId() : null, entidad != null ? entidad.getId() : null,
				rol != null ? rol.getId() : null);
	}

}
