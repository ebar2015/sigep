package co.gov.dafp.sigep2.util.enums;

public enum TipoRolEnum {
	
	SISTEMA(6, "SISTEMA", "SISTEMA"),
	SUPERADM(7,	"SUPERADM", "SUPER ADMINISTRADOR"),
	AUDITOR(8, "AUDITOR", "AUDITOR"),
	ADMINFUNC(9, "ADMINFUNC", "ADMINISTRADOR FUNCIONAL"),
	ADMINTEC(10, "ADMINTEC", "ADMINISTRADOR TECNICO"),
	ADMINENT(11, "ADMINENT", "ADMINISTRADOR DE ENTIDADES"),
	ADMINPRIV(12, "ADMINPRIV", "ADMINISTRADOR DE ENTIDADES PRIVADAS"),
	ADMINPOLI(13, "ADMINPOLI", "ADMINISTRADOR DE POLITICAS"),
	OPERENT(14,	"OPERENT", "OPERADOR DE ENTIDAD"),
	JEFECONTRATOS(15, "JEFECONTRATOS",	"JEFE DE CONTRATOS"),
	JEFECONTROLINTERNO(16, "JEFECONTROLINTERNO", "JEFE DE CONTROL INTERNO"),
	JEFETH(17, "JEFETH", "JEFE DE TALENTO HUMANO"),
	OPERCONTRATOS(18, "OPERCONTRATOS", "OPERADOR DE CONTRATOS"),
	OPERTH(19, "OPERTH","OPERADOR DE TALENTO HUMANO"),
	CONTRATISTA(3, "CONTRATISTA", "CONTRATISTA"),
	SERVIDORPUBLICO(4, "SERVIDOR PUBLICO", "SERVIDOR PUBLICO");
	
	private TipoRolEnum(int codRol, String nombre, String descripcion) {
		this.codRol = codRol;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}
	
	private int codRol;
	private String nombre;
	private String descripcion;
	
	public int getCodRol() {
		return codRol;
	}
	public void setCodRol(int codRol) {
		this.codRol = codRol;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
