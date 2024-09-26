package co.gov.dafp.sigep2.entity;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;

/**
 * EntidadBaseDTO que representa los recursos
 *
 * @author Jhon De Avila Mercado <jhon.mercado en ADA S.A.>
 */
public class RecursoDTO extends EntidadBaseDTO {
	private static final long serialVersionUID = -4483842547870371273L;

	private long id;

	private String descripcion;

	private String codigoVentana;

	private Long orden;

	private String tituloVentana;

	private String identificador;

	private String pagina;

	private Boolean esRecursoBPM;

	private Integer idActividadBPM;

	private Boolean mostrarEnIndiceAgrupado;

	private String iconoMenu;

	private String imagenMenu;

	private String colorMenu;

	private Boolean inhabilitarDuranteCierre = Boolean.FALSE;

	private Boolean bloqueado = Boolean.FALSE;

	private Long codigoPadre;

	public RecursoDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return CapitalCaseConverter.convert(descripcion);
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

	public Long getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(Long codigoPadre) {
		this.codigoPadre = codigoPadre;
	}

	@Override
	public String toString() {
		return "RecursoDTO [id=" + id + ", descripcion=" + descripcion + ", codigoVentana=" + codigoVentana + ", orden="
				+ orden + ", tituloVentana=" + tituloVentana + ", identificador=" + identificador + ", pagina=" + pagina
				+ ", esRecursoBPM=" + esRecursoBPM + ", idActividadBPM=" + idActividadBPM + ", mostrarEnIndiceAgrupado="
				+ mostrarEnIndiceAgrupado + ", iconoMenu=" + iconoMenu + ", imagenMenu=" + imagenMenu + ", colorMenu="
				+ colorMenu + ", inhabilitarDuranteCierre=" + inhabilitarDuranteCierre + ", bloqueado=" + bloqueado
				+ ", codigoPadre=" + codigoPadre + ", audFechaActualizacion=" + audFechaActualizacion
				+ ", audCodUsuario=" + audCodUsuario + ", audCodRol=" + audCodRol + ", audAccion=" + audAccion + "]";
	}

}
