package co.gov.dafp.sigep2.entities;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

public class Vinculacion extends ErrorMensajes{
	
	 /**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_VINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codVinculacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_PERSONA
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codPersona;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_Entidad_Planta_Detalle
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codEntidadPlantaDetalle;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_DEPENDENCIA_ENTIDAD
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codDependenciaEntidad;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FECHA_POSESION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Date fechaPosesion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FECHA_FINALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Date fechaFinalizacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_CAUSAL_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codCausalDesvinculacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_TIPO_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal codTipoActoAdminDesvinculacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.NUM_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private String numActoAdminDesvinculacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FECHA_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Date fechaActoAdminDesvinculacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FLG_MEDICO_DOCENTE
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Short flgMedicoDocente;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Date audFechaActualizacion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.AUD_COD_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal audCodUsuario;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.AUD_COD_ROL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private BigDecimal audCodRol;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.AUD_ACCION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Integer audAccion;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.COD_TIPO_NOMBRAMIENTO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Integer codTipoNombramiento;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FLG_TITULARIDAD_CARGO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Short flgTitularidadCargo;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FLG_ACTIVO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Short flgActivo;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FLG_GUARDADO_PARCIAL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Short flgGuardadoParcial;

		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.FECHA_INACTIVA_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Date fechaInactivaUsuario;
		
		/**
		 * This field was generated by MyBatis Generator. This field corresponds to the database column VINCULACION.flg_PEP
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		private Short flgPEP;


		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_VINCULACION
		 * @return  the value of VINCULACION.COD_VINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodVinculacion() {
			return codVinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_VINCULACION
		 * @param codVinculacion  the value for VINCULACION.COD_VINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodVinculacion(BigDecimal codVinculacion) {
			this.codVinculacion = codVinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_PERSONA
		 * @return  the value of VINCULACION.COD_PERSONA
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodPersona() {
			return codPersona;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_PERSONA
		 * @param codPersona  the value for VINCULACION.COD_PERSONA
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodPersona(BigDecimal codPersona) {
			this.codPersona = codPersona;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_Entidad_Planta_Detalle
		 * @return  the value of VINCULACION.COD_Entidad_Planta_Detalle
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodEntidadPlantaDetalle() {
			return codEntidadPlantaDetalle;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_Entidad_Planta_Detalle
		 * @param codEntidadPlantaDetalle  the value for VINCULACION.COD_Entidad_Planta_Detalle
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodEntidadPlantaDetalle(BigDecimal codEntidadPlantaDetalle) {
			this.codEntidadPlantaDetalle = codEntidadPlantaDetalle;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_DEPENDENCIA_ENTIDAD
		 * @return  the value of VINCULACION.COD_DEPENDENCIA_ENTIDAD
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodDependenciaEntidad() {
			return codDependenciaEntidad;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_DEPENDENCIA_ENTIDAD
		 * @param codDependenciaEntidad  the value for VINCULACION.COD_DEPENDENCIA_ENTIDAD
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodDependenciaEntidad(BigDecimal codDependenciaEntidad) {
			this.codDependenciaEntidad = codDependenciaEntidad;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FECHA_POSESION
		 * @return  the value of VINCULACION.FECHA_POSESION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Date getFechaPosesion() {
			return fechaPosesion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FECHA_POSESION
		 * @param fechaPosesion  the value for VINCULACION.FECHA_POSESION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFechaPosesion(Date fechaPosesion) {
			this.fechaPosesion = fechaPosesion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FECHA_FINALIZACION
		 * @return  the value of VINCULACION.FECHA_FINALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Date getFechaFinalizacion() {
			return fechaFinalizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FECHA_FINALIZACION
		 * @param fechaFinalizacion  the value for VINCULACION.FECHA_FINALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFechaFinalizacion(Date fechaFinalizacion) {
			this.fechaFinalizacion = fechaFinalizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_CAUSAL_DESVINCULACION
		 * @return  the value of VINCULACION.COD_CAUSAL_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodCausalDesvinculacion() {
			return codCausalDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_CAUSAL_DESVINCULACION
		 * @param codCausalDesvinculacion  the value for VINCULACION.COD_CAUSAL_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodCausalDesvinculacion(BigDecimal codCausalDesvinculacion) {
			this.codCausalDesvinculacion = codCausalDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_TIPO_ACTO_ADMIN_DESVINCULACION
		 * @return  the value of VINCULACION.COD_TIPO_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getCodTipoActoAdminDesvinculacion() {
			return codTipoActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_TIPO_ACTO_ADMIN_DESVINCULACION
		 * @param codTipoActoAdminDesvinculacion  the value for VINCULACION.COD_TIPO_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodTipoActoAdminDesvinculacion(BigDecimal codTipoActoAdminDesvinculacion) {
			this.codTipoActoAdminDesvinculacion = codTipoActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.NUM_ACTO_ADMIN_DESVINCULACION
		 * @return  the value of VINCULACION.NUM_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public String getNumActoAdminDesvinculacion() {
			return numActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.NUM_ACTO_ADMIN_DESVINCULACION
		 * @param numActoAdminDesvinculacion  the value for VINCULACION.NUM_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setNumActoAdminDesvinculacion(String numActoAdminDesvinculacion) {
			this.numActoAdminDesvinculacion = numActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FECHA_ACTO_ADMIN_DESVINCULACION
		 * @return  the value of VINCULACION.FECHA_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Date getFechaActoAdminDesvinculacion() {
			return fechaActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FECHA_ACTO_ADMIN_DESVINCULACION
		 * @param fechaActoAdminDesvinculacion  the value for VINCULACION.FECHA_ACTO_ADMIN_DESVINCULACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFechaActoAdminDesvinculacion(Date fechaActoAdminDesvinculacion) {
			this.fechaActoAdminDesvinculacion = fechaActoAdminDesvinculacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FLG_MEDICO_DOCENTE
		 * @return  the value of VINCULACION.FLG_MEDICO_DOCENTE
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Short getFlgMedicoDocente() {
			return flgMedicoDocente;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FLG_MEDICO_DOCENTE
		 * @param flgMedicoDocente  the value for VINCULACION.FLG_MEDICO_DOCENTE
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFlgMedicoDocente(Short flgMedicoDocente) {
			this.flgMedicoDocente = flgMedicoDocente;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.AUD_FECHA_ACTUALIZACION
		 * @return  the value of VINCULACION.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Date getAudFechaActualizacion() {
			return audFechaActualizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.AUD_FECHA_ACTUALIZACION
		 * @param audFechaActualizacion  the value for VINCULACION.AUD_FECHA_ACTUALIZACION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setAudFechaActualizacion(Date audFechaActualizacion) {
			this.audFechaActualizacion = audFechaActualizacion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.AUD_COD_USUARIO
		 * @return  the value of VINCULACION.AUD_COD_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getAudCodUsuario() {
			return audCodUsuario;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.AUD_COD_USUARIO
		 * @param audCodUsuario  the value for VINCULACION.AUD_COD_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setAudCodUsuario(BigDecimal audCodUsuario) {
			this.audCodUsuario = audCodUsuario;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.AUD_COD_ROL
		 * @return  the value of VINCULACION.AUD_COD_ROL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public BigDecimal getAudCodRol() {
			return audCodRol;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.AUD_COD_ROL
		 * @param audCodRol  the value for VINCULACION.AUD_COD_ROL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setAudCodRol(BigDecimal audCodRol) {
			this.audCodRol = audCodRol;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.AUD_ACCION
		 * @return  the value of VINCULACION.AUD_ACCION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Integer getAudAccion() {
			return audAccion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.AUD_ACCION
		 * @param audAccion  the value for VINCULACION.AUD_ACCION
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setAudAccion(Integer audAccion) {
			this.audAccion = audAccion;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.COD_TIPO_NOMBRAMIENTO
		 * @return  the value of VINCULACION.COD_TIPO_NOMBRAMIENTO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Integer getCodTipoNombramiento() {
			return codTipoNombramiento;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.COD_TIPO_NOMBRAMIENTO
		 * @param codTipoNombramiento  the value for VINCULACION.COD_TIPO_NOMBRAMIENTO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setCodTipoNombramiento(Integer codTipoNombramiento) {
			this.codTipoNombramiento = codTipoNombramiento;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FLG_TITULARIDAD_CARGO
		 * @return  the value of VINCULACION.FLG_TITULARIDAD_CARGO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Short getFlgTitularidadCargo() {
			return flgTitularidadCargo;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FLG_TITULARIDAD_CARGO
		 * @param flgTitularidadCargo  the value for VINCULACION.FLG_TITULARIDAD_CARGO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFlgTitularidadCargo(Short flgTitularidadCargo) {
			this.flgTitularidadCargo = flgTitularidadCargo;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FLG_ACTIVO
		 * @return  the value of VINCULACION.FLG_ACTIVO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Short getFlgActivo() {
			return flgActivo;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FLG_ACTIVO
		 * @param flgActivo  the value for VINCULACION.FLG_ACTIVO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFlgActivo(Short flgActivo) {
			this.flgActivo = flgActivo;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FLG_GUARDADO_PARCIAL
		 * @return  the value of VINCULACION.FLG_GUARDADO_PARCIAL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Short getFlgGuardadoParcial() {
			return flgGuardadoParcial;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FLG_GUARDADO_PARCIAL
		 * @param flgGuardadoParcial  the value for VINCULACION.FLG_GUARDADO_PARCIAL
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFlgGuardadoParcial(Short flgGuardadoParcial) {
			this.flgGuardadoParcial = flgGuardadoParcial;
		}

		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FECHA_INACTIVA_USUARIO
		 * @return  the value of VINCULACION.FECHA_INACTIVA_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public Date getFechaInactivaUsuario() {
			return fechaInactivaUsuario;
		}

		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FECHA_INACTIVA_USUARIO
		 * @param fechaInactivaUsuario  the value for VINCULACION.FECHA_INACTIVA_USUARIO
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		public void setFechaInactivaUsuario(Date fechaInactivaUsuario) {
			this.fechaInactivaUsuario = fechaInactivaUsuario;
		}

		
		/**
		 * This method was generated by MyBatis Generator. This method returns the value of the database column VINCULACION.FECHA_INACTIVA_USUARIO
		 * @return  the value of VINCULACION.FLG_PEP
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		
		public Short getFlgPEP() {
			return flgPEP;
		}

		
		/**
		 * This method was generated by MyBatis Generator. This method sets the value of the database column VINCULACION.FECHA_INACTIVA_USUARIO
		 * @param fechaInactivaUsuario  the value for VINCULACION.FLG_PEP
		 * @mbg.generated  Tue Sep 04 17:49:11 COT 2018
		 */
		
		public void setFlgPEP(Short flgPEP) {
			this.flgPEP = flgPEP;
		}
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1055249380022260700L;

		private Long total;


		private Integer limitInit;
	    
	    
	    private Integer limitEnd;
	    
	    
	    
	    /**
		 * @return the limitInit
		 */
		public Integer getLimitInit() {
			return limitInit;
		}

		/**
		 * @param limitInit the limitInit to set
		 */
		public void setLimitInit(Integer limitInit) {
			this.limitInit = limitInit;
		}
	    
	    
		/**
		 * @return the limitEnd
		 */
		public Integer getLimitEnd() {
			return limitEnd;
		}

		/**
		 * @param limitEnd the limitEnd to set
		 */
		public void setLimitEnd(Integer limitEnd) {
			this.limitEnd = limitEnd;
		}

		/**
		 * @return the total
		 */
		public Long getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(Long total) {
			this.total = total;
		}
}