/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Declaracion;

/**
 * 
* @author Jose Viscaya.
* @version 1.0
* @Class Clase  DeclaracionExt.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest
 */
public class DeclaracionExt extends Declaracion implements Serializable {

	private static final long serialVersionUID = 5499217050006972949L;
	
	private String nombreTipoDeclaracion;
	private String nombreTipoDocumento;
	private String nombres;
	private String apellidos;
	private String numeroIdentificacion;
	private Date fechaIniD;
	private Date fechaFniD;
	private long total;
	private Long codTipoDocumento;
	private Long codUsuario;
	private Long codRol;
	private Date periodoInicial, periodoFinal;
	private String fieldName;
	private String ascDesc;
	private String nombreCompleto;
	private BigDecimal codEntidad;
	private int flgAdm;
	


	public int getFlgAdm() {
		return flgAdm;
	}

	public void setFlgAdm(int flgAdm) {
		this.flgAdm = flgAdm;
	}

	
	/**
	 * @return the nombreTipoDeclaracion
	 */
	public String getNombreTipoDeclaracion() {
		return nombreTipoDeclaracion;
	}
	/**
	 * @param nombreTipoDeclaracion the nombreTipoDeclaracion to set
	 */
	public void setNombreTipoDeclaracion(String nombreTipoDeclaracion) {
		this.nombreTipoDeclaracion = nombreTipoDeclaracion;
	}
	/**
	 * @return the nombreTipoDocumento
	 */
	public String getNombreTipoDocumento() {
		return nombreTipoDocumento;
	}
	/**
	 * @param nombreTipoDocumento the nombreTipoDocumento to set
	 */
	public void setNombreTipoDocumento(String nombreTipoDocumento) {
		this.nombreTipoDocumento = nombreTipoDocumento;
	}
	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}
	/**
	 * @param nombres the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}
	/**
	 * @param apellidos the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	/**
	 * @param numeroIdentificacion the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	/**
	 * @return the fechaIniD
	 */
	public Date getFechaIniD() {
		return fechaIniD;
	}
	/**
	 * @param fechaIniD the fechaIniD to set
	 */
	public void setFechaIniD(Date fechaIniD) {
		this.fechaIniD = fechaIniD;
	}
	/**
	 * @return the fechaFniD
	 */
	public Date getFechaFniD() {
		return fechaFniD;
	}
	/**
	 * @param fechaFniD the fechaFniD to set
	 */
	public void setFechaFniD(Date fechaFniD) {
		this.fechaFniD = fechaFniD;
	}
	/**
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(long total) {
		this.total = total;
	}
	/**
	 * @return the codTipoDocumento
	 */
	public Long getCodTipoDocumento() {
		return codTipoDocumento;
	}
	/**
	 * @param codTipoDocumento the codTipoDocumento to set
	 */
	public void setCodTipoDocumento(Long codTipoDocumento) {
		this.codTipoDocumento = codTipoDocumento;
	}
	/**
	 * @return the codUsuario
	 */
	public Long getCodUsuario() {
		return codUsuario;
	}
	/**
	 * @param codUsuario the codUsuario to set
	 */
	public void setCodUsuario(Long codUsuario) {
		this.codUsuario = codUsuario;
	}
	/**
	 * @return the codRol
	 */
	public Long getCodRol() {
		return codRol;
	}
	/**
	 * @param codRol the codRol to set
	 */
	public void setCodRol(Long codRol) {
		this.codRol = codRol;
	}
	public Date getPeriodoInicial() {
		return periodoInicial;
	}
	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}
	public Date getPeriodoFinal() {
		return periodoFinal;
	}
	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the ascDesc
	 */
	public String getAscDesc() {
		return ascDesc;
	}
	/**
	 * @param ascDesc the ascDesc to set
	 */
	public void setAscDesc(String ascDesc) {
		this.ascDesc = ascDesc;
	}
	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public BigDecimal getCodEntidad() {
		return codEntidad;
	}
	public void setCodEntidad(BigDecimal codEntidad) {
		this.codEntidad = codEntidad;
	}
	
}
