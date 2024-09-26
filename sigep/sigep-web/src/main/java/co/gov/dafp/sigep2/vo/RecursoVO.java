package co.gov.dafp.sigep2.vo;

public class RecursoVO extends EntidadVO {
	private static final long serialVersionUID = 7029923794843789129L;

	private String descripcion;
	private String codigoVentana;
	private Long orden;
	private RecursoVO codigoPadre;
	private String tituloVentana;
	private String identificador;
	private String pagina;
	private Boolean esRecursoBPM;
	private Integer idActividadBPM;
	private Boolean mostrarEnIndiceAgrupado;
	private String iconoMenu;
	private String imagenMenu;
	private String colorMenu;
	private Boolean bloqueado;
	private Boolean estado;

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

	public RecursoVO getCodigoPadre() {
		return codigoPadre;
	}

	public void setCodigoPadre(RecursoVO codigoPadre) {
		this.codigoPadre = codigoPadre;
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
		return "RecursoVO [descripcion=" + descripcion + ", codigoVentana=" + codigoVentana + ", orden=" + orden
				+ ", codigoPadre=" + codigoPadre + ", tituloVentana=" + tituloVentana + ", identificador="
				+ identificador + ", pagina=" + pagina + ", esRecursoBPM=" + esRecursoBPM + ", idActividadBPM="
				+ idActividadBPM + ", mostrarEnIndiceAgrupado=" + mostrarEnIndiceAgrupado + ", iconoMenu=" + iconoMenu
				+ ", imagenMenu=" + imagenMenu + ", colorMenu=" + colorMenu + ", bloqueado=" + bloqueado + ", estado="
				+ estado + "]";
	}
}
