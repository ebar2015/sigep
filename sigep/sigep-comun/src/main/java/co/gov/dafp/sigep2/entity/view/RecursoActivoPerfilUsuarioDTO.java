package co.gov.dafp.sigep2.entity.view;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;

/**
 * Entidad que representa los recursos
 *
 * @author Jhon De Avila Mercado <jhon.mercado en ADA S.A.>
 */

public class RecursoActivoPerfilUsuarioDTO extends VistaBaseDTO {
	private static final long serialVersionUID = -6448533663368629528L;

	public static final String FIND_RECURSOS_POR_LOGIN_USUARIO = "SELECT r FROM RecursoActivoPerfilUsuario ra, Recurso r "
			+ "WHERE ra.id = r.id AND (ra.login=:login or ra.login is null) AND ra.codigoPadre IS NOT NULL ORDER BY r.codigoPadre, r.orden";

	private Long id;

	private String descripcion;

	private String codigoVentana;

	private Long orden;

	private String tituloVentana;

	private String identificador;

	private String pagina;

	private Boolean mostrarEnMenu;

	private String iconoMenu;

	private String imagenMenu;

	private String colorMenu;

	private Boolean bloqueado = Boolean.FALSE;

	private Boolean estado = Boolean.TRUE;

	private String login;

	private Long codigoPadre;

	private Long entidad;

	private Long rol;

	public RecursoActivoPerfilUsuarioDTO() {
		super();
	}

	public RecursoActivoPerfilUsuarioDTO(Long id, String descripcion, String codigoVentana, Long orden,
			String tituloVentana, String identificador, String pagina, Boolean mostrarEnMenu, String iconoMenu,
			String imagenMenu, String colorMenu, Boolean bloqueado, Boolean estado, String login, Long codigoPadre,
			Long entidad, Long rol) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.codigoVentana = codigoVentana;
		this.orden = orden;
		this.tituloVentana = tituloVentana;
		this.identificador = identificador;
		this.pagina = pagina;
		this.mostrarEnMenu = mostrarEnMenu;
		this.iconoMenu = iconoMenu;
		this.imagenMenu = imagenMenu;
		this.colorMenu = colorMenu;
		this.bloqueado = bloqueado;
		this.estado = estado;
		this.login = login;
		this.codigoPadre = codigoPadre;
		this.entidad = entidad;
		this.rol = rol;
	}

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

	public String getTituloVentana() {
		return CapitalCaseConverter.convert(tituloVentana);
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

	public boolean isMostrarEnMenu() {
		return mostrarEnMenu;
	}

	public void setMostrarEnMenu(boolean mostrarEnMenu) {
		this.mostrarEnMenu = mostrarEnMenu;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Long getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(Long codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	public Long getEntidad() {
		return entidad;
	}

	public void setEntidad(Long entidad) {
		this.entidad = entidad;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "RecursoActivoPerfilUsuarioDTO [id=" + id + ", descripcion=" + descripcion + ", codigoVentana="
				+ codigoVentana + ", orden=" + orden + ", tituloVentana=" + tituloVentana + ", identificador="
				+ identificador + ", pagina=" + pagina + ", mostrarEnMenu=" + mostrarEnMenu + ", iconoMenu=" + iconoMenu
				+ ", imagenMenu=" + imagenMenu + ", colorMenu=" + colorMenu + ", bloqueado=" + bloqueado + ", estado="
				+ estado + ", login=" + login + ", codigoPadre=" + codigoPadre + ", entidad=" + entidad + ", rol=" + rol
				+ "]";
	}
}
