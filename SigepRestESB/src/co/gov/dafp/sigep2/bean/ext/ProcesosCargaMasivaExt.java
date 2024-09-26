/**
 * 
 */
package co.gov.dafp.sigep2.bean.ext;

import co.gov.dafp.sigep2.bean.ProcesosCargaMasiva;

/**
 * @author Jose Viscaya.
 * @version 1.0
 * @Class Clase que se extiende de procesos carga masiva para e envio de datos adicooanels
 *  a las consultas de los servicios web
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module exposicion de servicios fuse
 * @Fecha: Martes 10 de julio de 2018
 */
public class ProcesosCargaMasivaExt extends ProcesosCargaMasiva {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5035429388322354577L;
	
	
	private String nombreEntidad;
	private String nombreTipoCargue;
	private String tipoDocumento;
	private String numeroIdentificacion;
	private String nombreCompleto;
	private String nombreEstado;
	private Integer total;
	private Long   codPersona;
	private String correoElectronico;
	private String fieldName;
	private String ascDesc;
	
	/**
	 * @return the nombreEntidad
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}
	/**
	 * @param nombreEntidad the nombreEntidad to set
	 */
	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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
	/**
	 * @return the nombreTipoCargue
	 */
	public String getNombreTipoCargue() {
		return nombreTipoCargue;
	}
	/**
	 * @param nombreTipoCargue the nombreTipoCargue to set
	 */
	public void setNombreTipoCargue(String nombreTipoCargue) {
		this.nombreTipoCargue = nombreTipoCargue;
	}
	/**
	 * @return the nombreEstado
	 */
	public String getNombreEstado() {
		return nombreEstado;
	}
	/**
	 * @param nombreEstado the nombreEstado to set
	 */
	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}
	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * @return the codPersona
	 */
	public Long getCodPersona() {
		return codPersona;
	}
	/**
	 * @param codPersona the codPersona to set
	 */
	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}
	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
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
}
