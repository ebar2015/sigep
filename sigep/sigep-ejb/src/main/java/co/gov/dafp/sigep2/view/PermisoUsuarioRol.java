package co.gov.dafp.sigep2.view;
// Generated 24/11/2017 03:31:14 PM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.jpa.seguridad.PermisoRol;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Recurso;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

@Entity(name = "PermisoUsuarioRol")
@Table(name = "V_PERMISO_USUARIO_ROL")
@SqlResultSetMappings({ @SqlResultSetMapping(name = PermisoUsuarioRol.PERMISO_USUARIO_ROL_MAPPING, classes = {
		@ConstructorResult(targetClass = PermisoUsuarioRolDTO.class, columns = {
				@ColumnResult(name = "COD_PERMISO_ROL_ACCIONES", type = Long.class),
				@ColumnResult(name = "COD_PERMISO_ROL", type = Long.class),
				@ColumnResult(name = "COD_ROL", type = Long.class),
				@ColumnResult(name = "COD_RECURSO", type = Long.class),
				@ColumnResult(name = "ICONO_MENU", type = String.class),
				@ColumnResult(name = "COD_USUARIO", type = Long.class),
				@ColumnResult(name = "ACCION", type = String.class),
				@ColumnResult(name = "PAGINA", type = String.class),
				@ColumnResult(name = "CONTROL_HTML", type = String.class),
				@ColumnResult(name = "CONTROL_HTML_PADRE", type = String.class) }) }) })
public class PermisoUsuarioRol extends VistaBase implements java.io.Serializable {
	private static final long serialVersionUID = -6496363493885808792L;

	public static final String PERMISO_USUARIO_ROL_MAPPING = "co.gov.dafp.sigep2.view.mapping.PermisoUsuarioRol";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COD_PERMISO_ROL_ACCIONES", insertable = false, updatable = false)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = PermisoRol.class)
	@JoinColumn(name = "COD_PERMISO_ROL", insertable = false, updatable = false)
	private PermisoRol permisoRol;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = PermisoRol.class)
	@JoinColumn(name = "COD_ROL", nullable = false)
	private Rol rol;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = PermisoRol.class)
	@JoinColumn(name = "COD_RECURSO", insertable = false, updatable = false)
	private Recurso recurso;

	@Column(name = "ICONO_MENU", insertable = false, updatable = false)
	private String icono;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = PermisoRol.class)
	@JoinColumn(name = "COD_USUARIO", insertable = false, updatable = false)
	private Usuario usuario;

	@Column(name = "ACCION", insertable = false, updatable = false)
	private String accion;

	@Column(insertable = false, updatable = false)
	private String pagina;

	@Column(name = "CONTROL_HTML", insertable = false, updatable = false)
	private String controlHtml;

	@Column(name = "CONTROL_HTML_PADRE", insertable = false, updatable = false)
	private String controlHtmlPadre;

	public PermisoUsuarioRol() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PermisoRol getPermisoRol() {
		return permisoRol;
	}

	public void setPermisoRol(PermisoRol permisoRol) {
		this.permisoRol = permisoRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getControlHtml() {
		return controlHtml;
	}

	public void setControlHtml(String controlHtml) {
		this.controlHtml = controlHtml;
	}

	public String getControlHtmlPadre() {
		return controlHtmlPadre;
	}

	public void setControlHtmlPadre(String controlHtmlPadre) {
		this.controlHtmlPadre = controlHtmlPadre;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PermisoUsuarioRol [id=" + id + ", permisoRol=" + permisoRol + ", rol=" + rol + ", recurso=" + recurso
				+ ", icono=" + icono + ", usuario=" + usuario + ", accion=" + accion + ", pagina=" + pagina
				+ ", controlHtml=" + controlHtml + ", controlHtmlPadre=" + controlHtmlPadre + "]";
	}

	public PermisoUsuarioRolDTO getPermisoUsuarioRolDTO() {
		return new PermisoUsuarioRolDTO(id, permisoRol.getId(), rol.getId(), recurso.getId(), icono, usuario.getId(),
				accion, pagina, controlHtml, controlHtmlPadre);
	}

	@Override
	public VistaBaseDTO getDTO() {
		return null;
	}

}
