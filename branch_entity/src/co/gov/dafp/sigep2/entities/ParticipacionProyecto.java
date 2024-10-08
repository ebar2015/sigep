package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class ParticipacionProyecto extends ErrorMensajes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6867680974899399498L;

	 /**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.COD_PARTICIPACION_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Long codParticipacionProyecto;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.COD_PERSONA
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private BigDecimal codPersona;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.NOMBRE_ENTIDAD
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private String nombreEntidad;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.NOMBRE_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private String nombreProyecto;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.ROL_LABORADO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private String rolLaborado;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.COD_PAIS
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Integer codPais;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.COD_DEPARTAMENTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Integer codDepartamento;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.COD_MUNICIPIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Integer codMunicipio;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.FECHA_INICIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Date fechaInicio;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.FECHA_TERMINACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Date fechaTerminacion;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Date audFechaActualizacion;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.AUD_COD_USUARIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private BigDecimal audCodUsuario;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.AUD_COD_ROL
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Integer audCodRol;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.AUD_ACCION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Integer audAccion;


		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column PARTICIPACION_PROYECTO.FLG_ACTIVO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		private Short flgActivo;



		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.COD_PARTICIPACION_PROYECTO
		 * @return  the value of PARTICIPACION_PROYECTO.COD_PARTICIPACION_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Long getCodParticipacionProyecto() {
			return codParticipacionProyecto;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.COD_PARTICIPACION_PROYECTO
		 * @param codParticipacionProyecto  the value for PARTICIPACION_PROYECTO.COD_PARTICIPACION_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setCodParticipacionProyecto(Long codParticipacionProyecto) {
			this.codParticipacionProyecto = codParticipacionProyecto;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.COD_PERSONA
		 * @return  the value of PARTICIPACION_PROYECTO.COD_PERSONA
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public BigDecimal getCodPersona() {
			return codPersona;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.COD_PERSONA
		 * @param codPersona  the value for PARTICIPACION_PROYECTO.COD_PERSONA
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setCodPersona(BigDecimal codPersona) {
			this.codPersona = codPersona;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.NOMBRE_ENTIDAD
		 * @return  the value of PARTICIPACION_PROYECTO.NOMBRE_ENTIDAD
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public String getNombreEntidad() {
			return nombreEntidad;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.NOMBRE_ENTIDAD
		 * @param nombreEntidad  the value for PARTICIPACION_PROYECTO.NOMBRE_ENTIDAD
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setNombreEntidad(String nombreEntidad) {
			this.nombreEntidad = nombreEntidad;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.NOMBRE_PROYECTO
		 * @return  the value of PARTICIPACION_PROYECTO.NOMBRE_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public String getNombreProyecto() {
			return nombreProyecto;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.NOMBRE_PROYECTO
		 * @param nombreProyecto  the value for PARTICIPACION_PROYECTO.NOMBRE_PROYECTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setNombreProyecto(String nombreProyecto) {
			this.nombreProyecto = nombreProyecto;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.ROL_LABORADO
		 * @return  the value of PARTICIPACION_PROYECTO.ROL_LABORADO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public String getRolLaborado() {
			return rolLaborado;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.ROL_LABORADO
		 * @param rolLaborado  the value for PARTICIPACION_PROYECTO.ROL_LABORADO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setRolLaborado(String rolLaborado) {
			this.rolLaborado = rolLaborado;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.COD_PAIS
		 * @return  the value of PARTICIPACION_PROYECTO.COD_PAIS
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Integer getCodPais() {
			return codPais;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.COD_PAIS
		 * @param codPais  the value for PARTICIPACION_PROYECTO.COD_PAIS
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setCodPais(Integer codPais) {
			this.codPais = codPais;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.COD_DEPARTAMENTO
		 * @return  the value of PARTICIPACION_PROYECTO.COD_DEPARTAMENTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Integer getCodDepartamento() {
			return codDepartamento;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.COD_DEPARTAMENTO
		 * @param codDepartamento  the value for PARTICIPACION_PROYECTO.COD_DEPARTAMENTO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setCodDepartamento(Integer codDepartamento) {
			this.codDepartamento = codDepartamento;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.COD_MUNICIPIO
		 * @return  the value of PARTICIPACION_PROYECTO.COD_MUNICIPIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Integer getCodMunicipio() {
			return codMunicipio;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.COD_MUNICIPIO
		 * @param codMunicipio  the value for PARTICIPACION_PROYECTO.COD_MUNICIPIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setCodMunicipio(Integer codMunicipio) {
			this.codMunicipio = codMunicipio;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.FECHA_INICIO
		 * @return  the value of PARTICIPACION_PROYECTO.FECHA_INICIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Date getFechaInicio() {
			return fechaInicio;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.FECHA_INICIO
		 * @param fechaInicio  the value for PARTICIPACION_PROYECTO.FECHA_INICIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setFechaInicio(Date fechaInicio) {
			this.fechaInicio = fechaInicio;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.FECHA_TERMINACION
		 * @return  the value of PARTICIPACION_PROYECTO.FECHA_TERMINACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Date getFechaTerminacion() {
			return fechaTerminacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.FECHA_TERMINACION
		 * @param fechaTerminacion  the value for PARTICIPACION_PROYECTO.FECHA_TERMINACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setFechaTerminacion(Date fechaTerminacion) {
			this.fechaTerminacion = fechaTerminacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.AUD_FECHA_ACTUALIZACION
		 * @return  the value of PARTICIPACION_PROYECTO.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Date getAudFechaActualizacion() {
			return audFechaActualizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.AUD_FECHA_ACTUALIZACION
		 * @param audFechaActualizacion  the value for PARTICIPACION_PROYECTO.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setAudFechaActualizacion(Date audFechaActualizacion) {
			this.audFechaActualizacion = audFechaActualizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.AUD_COD_USUARIO
		 * @return  the value of PARTICIPACION_PROYECTO.AUD_COD_USUARIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public BigDecimal getAudCodUsuario() {
			return audCodUsuario;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.AUD_COD_USUARIO
		 * @param audCodUsuario  the value for PARTICIPACION_PROYECTO.AUD_COD_USUARIO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setAudCodUsuario(BigDecimal audCodUsuario) {
			this.audCodUsuario = audCodUsuario;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.AUD_COD_ROL
		 * @return  the value of PARTICIPACION_PROYECTO.AUD_COD_ROL
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Integer getAudCodRol() {
			return audCodRol;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.AUD_COD_ROL
		 * @param audCodRol  the value for PARTICIPACION_PROYECTO.AUD_COD_ROL
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setAudCodRol(Integer audCodRol) {
			this.audCodRol = audCodRol;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.AUD_ACCION
		 * @return  the value of PARTICIPACION_PROYECTO.AUD_ACCION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Integer getAudAccion() {
			return audAccion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.AUD_ACCION
		 * @param audAccion  the value for PARTICIPACION_PROYECTO.AUD_ACCION
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setAudAccion(Integer audAccion) {
			this.audAccion = audAccion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column PARTICIPACION_PROYECTO.FLG_ACTIVO
		 * @return  the value of PARTICIPACION_PROYECTO.FLG_ACTIVO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public Short getFlgActivo() {
			return flgActivo;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column PARTICIPACION_PROYECTO.FLG_ACTIVO
		 * @param flgActivo  the value for PARTICIPACION_PROYECTO.FLG_ACTIVO
		 * @mbg.generated  Fri Dec 28 13:38:48 COT 2018
		 */
		public void setFlgActivo(Short flgActivo) {
			this.flgActivo = flgActivo;
		}

		private int limitInit;
	    
	    
	    private int limitEnd;
	    
	    
	    
	    /**
		 * @return the limitInit
		 */
		public int getLimitInit() {
			return limitInit;
		}

		/**
		 * @param limitInit the limitInit to set
		 */
		public void setLimitInit(int limitInit) {
			this.limitInit = limitInit;
		}
	    
	    
		/**
		 * @return the limitEnd
		 */
		public int getLimitEnd() {
			return limitEnd;
		}

		/**
		 * @param limitEnd the limitEnd to set
		 */
		public void setLimitEnd(int limitEnd) {
			this.limitEnd = limitEnd;
		}
}