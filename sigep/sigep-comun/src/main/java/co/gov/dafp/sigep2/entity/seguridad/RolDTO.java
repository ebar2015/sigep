package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

/**
 * The persistent class for the USUARIO database table.
 *
 */
public class RolDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 9079321647521240196L;

	public static final String USUARIO_SISTEMA = "sistema";

	public static final String SUPER_ADMINISTRADOR = "SUPERADM";
	public static final String ADMINISTRADOR_FUNCIONAL = "ADMINFUNC";
	public static final String ADMINISTRADOR_TECNICO = "ADMINTEC";
	public static final String AUDITOR = "AUDITOR";
	public static final String ADMINISTRADOR_ENTIDADES = "ADMINENT";
	public static final String ADMINISTRADOR_ENTIDADES_PRIVADAS = "ADMINPRIV";
	public static final String ADMINISTRADOR_POLITICAS = "ADMINPOLI";
	public static final String OPERADOR_ENTIDADES = "OPERENT";
	public static final String JEFE_CONTRATOS = "JEFECONTRATOS";
	public static final String JEFE_TALENTO_HUMANO = "JEFETH";
	public static final String OPERADOR_TALENTO_HUMANO = "OPERTH";
	public static final String CONTRATISTA = "CONTRATISTA";
	public static final String SERVIDOR_PUBLICO = "SERVIDOR PUBLICO";
	public static final String JEFE_CONTROL_INTERNO = "JEFECONTROLINTERNO";
	public static final String OPERADOR_CONTRATOS = "OPERCONTRATOS";
	public static final String SISTEMA = "SISTEMA";
	public static final String CARACTERIZADOR_ENTIDAD ="CARACTERIZADORENT";
	public static final String CRUD ="CRUD";

	private long id;

	private BigDecimal codRol;

	private Long rolPadre;

	private BigDecimal codRolPadre;

	private String nombre;

	private String descripcionRol;

	private boolean flgActivo;

	private boolean flgEstado = Boolean.TRUE;
	
	/**
	 * Indica si el rol es heredable o no. <code>true</code> indica que es un
	 * rol base, lo cual implica que es heredable y no se puede modificar.
	 * <code>false</code> el rol no es heredable y se puede modificar. Los roles
	 * base son aquellos definidos por el alcance inicial de SIGEP II
	 */
	private boolean flgRolBase = Boolean.FALSE;

	private BigDecimal total;

	public RolDTO() {
		super();
	}

	public RolDTO(Long id, Long rolPadre, String nombre, String descripcionRol, boolean flgActivo, boolean flgEstado) {
		super();
		this.id = id;
		this.rolPadre = rolPadre;
		this.nombre = nombre;
		this.descripcionRol = descripcionRol;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
	}

	public RolDTO(Long id, Long rolPadre, String nombre, String descripcionRol, boolean flgActivo, boolean flgEstado,
			BigDecimal total) {
		super();
		this.id = id;
		this.rolPadre = rolPadre;
		this.nombre = nombre;
		this.descripcionRol = descripcionRol;
		this.flgActivo = flgActivo;
		this.flgEstado = flgEstado;
		this.total = total;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public Long getRolPadre() {
		return rolPadre;
	}

	public void setRolPadre(Long rolPadre) {
		this.rolPadre = rolPadre;
	}

	public String getNombre() {
		return CapitalCaseConverter.convert(nombre);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcionRol() {
		return CapitalCaseConverter.convert(descripcionRol);
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isFlgEstado() {
		return flgEstado;
	}

	public void setFlgEstado(boolean flgEstado) {
		this.flgEstado = flgEstado;
	}

	public boolean isFlgRolBase() {
		return flgRolBase;
	}

	public void setFlgRolBase(boolean flgRolBase) {
		this.flgRolBase = flgRolBase;
	}

	@Override
	public String toString() {
		return "RolDTO [id=" + id + ", codRol=" + codRol + ", rolPadre=" + rolPadre + ", codRolPadre=" + codRolPadre
				+ ", nombre=" + nombre + ", descripcionRol=" + descripcionRol + ", flgActivo=" + flgActivo
				+ ", flgEstado=" + flgEstado + ", flgRolBase=" + flgRolBase + ", total=" + total + "]";
	}

	/**
	 * @return the codRol
	 */
	public BigDecimal getCodRol() {
		return codRol;
	}

	/**
	 * @param codRol
	 *            the codRol to set
	 */
	public void setCodRol(BigDecimal codRol) {
		this.codRol = codRol;
	}

	/**
	 * @return the codRolPadre
	 */
	public BigDecimal getCodRolPadre() {
		return codRolPadre;
	}

	/**
	 * @param codRolPadre
	 *            the codRolPadre to set
	 */
	public void setCodRolPadre(BigDecimal codRolPadre) {
		this.codRolPadre = codRolPadre;
	}
}
