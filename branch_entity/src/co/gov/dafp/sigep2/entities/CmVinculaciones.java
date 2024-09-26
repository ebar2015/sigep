package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;

import co.gov.dafp.sigep2.utils.ErrorMensajes;
import java.util.Date;

public class CmVinculaciones extends ErrorMensajes{
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.COD_CM_VINCULACIONES
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private Long codCmVinculaciones;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.COD_TIPO_IDENTIFICACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String codTipoIdentificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String numeroIdentificacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.PRIMER_NOMBRE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String primerNombre;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.PRIMER_APELLIDO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String primerApellido;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.CLASIFICACION_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String clasificacionPlanta;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.CLASE_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String clasePlanta;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.TIPO_NOMBRAMIENTO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String tipoNombramiento;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String cargo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String dependencia;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.FECHA_POSESION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String fechaPosesion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.FLG_MEDICO_DOCENTE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String flgMedicoDocente;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.FLG_TITULARIDAD_CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String flgTitularidadCargo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private Date audFechaActualizacion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.AUD_COD_ROL
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private Integer audCodRol;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.AUD_ACCION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private Integer audAccion;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private Long codProcesoCargaMasiva;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column CM_VINCULACIONES.RESULTADO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	private String resultado;
	private String grado;
	private String codigo;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.COD_CM_VINCULACIONES
	 * @return  the value of CM_VINCULACIONES.COD_CM_VINCULACIONES
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public Long getCodCmVinculaciones() {
		return codCmVinculaciones;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.COD_CM_VINCULACIONES
	 * @param codCmVinculaciones  the value for CM_VINCULACIONES.COD_CM_VINCULACIONES
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setCodCmVinculaciones(Long codCmVinculaciones) {
		this.codCmVinculaciones = codCmVinculaciones;
	}

	/**
	 * @return the codTipoIdentificacion
	 */
	public String getCodTipoIdentificacion() {
		return codTipoIdentificacion;
	}

	/**
	 * @param codTipoIdentificacion the codTipoIdentificacion to set
	 */
	public void setCodTipoIdentificacion(String codTipoIdentificacion) {
		this.codTipoIdentificacion = codTipoIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.NUMERO_IDENTIFICACION
	 * @return  the value of CM_VINCULACIONES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.NUMERO_IDENTIFICACION
	 * @param numeroIdentificacion  the value for CM_VINCULACIONES.NUMERO_IDENTIFICACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.PRIMER_NOMBRE
	 * @return  the value of CM_VINCULACIONES.PRIMER_NOMBRE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getPrimerNombre() {
		return primerNombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.PRIMER_NOMBRE
	 * @param primerNombre  the value for CM_VINCULACIONES.PRIMER_NOMBRE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.PRIMER_APELLIDO
	 * @return  the value of CM_VINCULACIONES.PRIMER_APELLIDO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.PRIMER_APELLIDO
	 * @param primerApellido  the value for CM_VINCULACIONES.PRIMER_APELLIDO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.CLASIFICACION_PLANTA
	 * @return  the value of CM_VINCULACIONES.CLASIFICACION_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getClasificacionPlanta() {
		return clasificacionPlanta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.CLASIFICACION_PLANTA
	 * @param clasificacionPlanta  the value for CM_VINCULACIONES.CLASIFICACION_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setClasificacionPlanta(String clasificacionPlanta) {
		this.clasificacionPlanta = clasificacionPlanta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.CLASE_PLANTA
	 * @return  the value of CM_VINCULACIONES.CLASE_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getClasePlanta() {
		return clasePlanta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.CLASE_PLANTA
	 * @param clasePlanta  the value for CM_VINCULACIONES.CLASE_PLANTA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setClasePlanta(String clasePlanta) {
		this.clasePlanta = clasePlanta;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.TIPO_NOMBRAMIENTO
	 * @return  the value of CM_VINCULACIONES.TIPO_NOMBRAMIENTO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getTipoNombramiento() {
		return tipoNombramiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.TIPO_NOMBRAMIENTO
	 * @param tipoNombramiento  the value for CM_VINCULACIONES.TIPO_NOMBRAMIENTO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setTipoNombramiento(String tipoNombramiento) {
		this.tipoNombramiento = tipoNombramiento;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.CARGO
	 * @return  the value of CM_VINCULACIONES.CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getCargo() {
		return cargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.CARGO
	 * @param cargo  the value for CM_VINCULACIONES.CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.DEPENDENCIA
	 * @return  the value of CM_VINCULACIONES.DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getDependencia() {
		return dependencia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.DEPENDENCIA
	 * @param dependencia  the value for CM_VINCULACIONES.DEPENDENCIA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.FECHA_POSESION
	 * @return  the value of CM_VINCULACIONES.FECHA_POSESION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getFechaPosesion() {
		return fechaPosesion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.FECHA_POSESION
	 * @param fechaPosesion  the value for CM_VINCULACIONES.FECHA_POSESION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setFechaPosesion(String fechaPosesion) {
		this.fechaPosesion = fechaPosesion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.FLG_MEDICO_DOCENTE
	 * @return  the value of CM_VINCULACIONES.FLG_MEDICO_DOCENTE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getFlgMedicoDocente() {
		return flgMedicoDocente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.FLG_MEDICO_DOCENTE
	 * @param flgMedicoDocente  the value for CM_VINCULACIONES.FLG_MEDICO_DOCENTE
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setFlgMedicoDocente(String flgMedicoDocente) {
		this.flgMedicoDocente = flgMedicoDocente;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.FLG_TITULARIDAD_CARGO
	 * @return  the value of CM_VINCULACIONES.FLG_TITULARIDAD_CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getFlgTitularidadCargo() {
		return flgTitularidadCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.FLG_TITULARIDAD_CARGO
	 * @param flgTitularidadCargo  the value for CM_VINCULACIONES.FLG_TITULARIDAD_CARGO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setFlgTitularidadCargo(String flgTitularidadCargo) {
		this.flgTitularidadCargo = flgTitularidadCargo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.AUD_FECHA_ACTUALIZACION
	 * @return  the value of CM_VINCULACIONES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public Date getAudFechaActualizacion() {
		return audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.AUD_FECHA_ACTUALIZACION
	 * @param audFechaActualizacion  the value for CM_VINCULACIONES.AUD_FECHA_ACTUALIZACION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setAudFechaActualizacion(Date audFechaActualizacion) {
		this.audFechaActualizacion = audFechaActualizacion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.AUD_COD_ROL
	 * @return  the value of CM_VINCULACIONES.AUD_COD_ROL
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public Integer getAudCodRol() {
		return audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.AUD_COD_ROL
	 * @param audCodRol  the value for CM_VINCULACIONES.AUD_COD_ROL
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setAudCodRol(Integer audCodRol) {
		this.audCodRol = audCodRol;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.AUD_ACCION
	 * @return  the value of CM_VINCULACIONES.AUD_ACCION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public Integer getAudAccion() {
		return audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.AUD_ACCION
	 * @param audAccion  the value for CM_VINCULACIONES.AUD_ACCION
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setAudAccion(Integer audAccion) {
		this.audAccion = audAccion;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.COD_PROCESO_CARGA_MASIVA
	 * @return  the value of CM_VINCULACIONES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public Long getCodProcesoCargaMasiva() {
		return codProcesoCargaMasiva;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.COD_PROCESO_CARGA_MASIVA
	 * @param codProcesoCargaMasiva  the value for CM_VINCULACIONES.COD_PROCESO_CARGA_MASIVA
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setCodProcesoCargaMasiva(Long codProcesoCargaMasiva) {
		this.codProcesoCargaMasiva = codProcesoCargaMasiva;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column CM_VINCULACIONES.RESULTADO
	 * @return  the value of CM_VINCULACIONES.RESULTADO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column CM_VINCULACIONES.RESULTADO
	 * @param resultado  the value for CM_VINCULACIONES.RESULTADO
	 * @mbg.generated  Mon Sep 10 15:16:32 COT 2018
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the grado
	 */
	public String getGrado() {
		return grado;
	}

	/**
	 * @param grado the grado to set
	 */
	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -1643561041902301417L;
}