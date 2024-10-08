package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NacionalidadPerfilExample {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public NacionalidadPerfilExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andCodNacionalidadPerfilIsNull() {
			addCriterion("COD_NACIONALIDAD_PERFIL is null");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilIsNotNull() {
			addCriterion("COD_NACIONALIDAD_PERFIL is not null");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilEqualTo(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL =", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilNotEqualTo(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL <>", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilGreaterThan(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL >", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL >=", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilLessThan(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL <", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilLessThanOrEqualTo(Long value) {
			addCriterion("COD_NACIONALIDAD_PERFIL <=", value, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilIn(List<Long> values) {
			addCriterion("COD_NACIONALIDAD_PERFIL in", values, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilNotIn(List<Long> values) {
			addCriterion("COD_NACIONALIDAD_PERFIL not in", values, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilBetween(Long value1, Long value2) {
			addCriterion("COD_NACIONALIDAD_PERFIL between", value1, value2, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodNacionalidadPerfilNotBetween(Long value1, Long value2) {
			addCriterion("COD_NACIONALIDAD_PERFIL not between", value1, value2, "codNacionalidadPerfil");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIsNull() {
			addCriterion("COD_PERSONA is null");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIsNotNull() {
			addCriterion("COD_PERSONA is not null");
			return (Criteria) this;
		}

		public Criteria andCodPersonaEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA =", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA <>", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaGreaterThan(BigDecimal value) {
			addCriterion("COD_PERSONA >", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA >=", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaLessThan(BigDecimal value) {
			addCriterion("COD_PERSONA <", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA <=", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIn(List<BigDecimal> values) {
			addCriterion("COD_PERSONA in", values, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotIn(List<BigDecimal> values) {
			addCriterion("COD_PERSONA not in", values, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PERSONA between", value1, value2, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PERSONA not between", value1, value2, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPaisIsNull() {
			addCriterion("COD_PAIS is null");
			return (Criteria) this;
		}

		public Criteria andCodPaisIsNotNull() {
			addCriterion("COD_PAIS is not null");
			return (Criteria) this;
		}

		public Criteria andCodPaisEqualTo(Integer value) {
			addCriterion("COD_PAIS =", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotEqualTo(Integer value) {
			addCriterion("COD_PAIS <>", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisGreaterThan(Integer value) {
			addCriterion("COD_PAIS >", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisGreaterThanOrEqualTo(Integer value) {
			addCriterion("COD_PAIS >=", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisLessThan(Integer value) {
			addCriterion("COD_PAIS <", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisLessThanOrEqualTo(Integer value) {
			addCriterion("COD_PAIS <=", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisIn(List<Integer> values) {
			addCriterion("COD_PAIS in", values, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotIn(List<Integer> values) {
			addCriterion("COD_PAIS not in", values, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisBetween(Integer value1, Integer value2) {
			addCriterion("COD_PAIS between", value1, value2, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotBetween(Integer value1, Integer value2) {
			addCriterion("COD_PAIS not between", value1, value2, "codPais");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlIsNull() {
			addCriterion("ADJUNTO_URL is null");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlIsNotNull() {
			addCriterion("ADJUNTO_URL is not null");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlEqualTo(String value) {
			addCriterion("ADJUNTO_URL =", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlNotEqualTo(String value) {
			addCriterion("ADJUNTO_URL <>", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlGreaterThan(String value) {
			addCriterion("ADJUNTO_URL >", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlGreaterThanOrEqualTo(String value) {
			addCriterion("ADJUNTO_URL >=", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlLessThan(String value) {
			addCriterion("ADJUNTO_URL <", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlLessThanOrEqualTo(String value) {
			addCriterion("ADJUNTO_URL <=", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlLike(String value) {
			addCriterion("ADJUNTO_URL like", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlNotLike(String value) {
			addCriterion("ADJUNTO_URL not like", value, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlIn(List<String> values) {
			addCriterion("ADJUNTO_URL in", values, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlNotIn(List<String> values) {
			addCriterion("ADJUNTO_URL not in", values, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlBetween(String value1, String value2) {
			addCriterion("ADJUNTO_URL between", value1, value2, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAdjuntoUrlNotBetween(String value1, String value2) {
			addCriterion("ADJUNTO_URL not between", value1, value2, "adjuntoUrl");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionIsNull() {
			addCriterion("AUD_FECHA_ACTUALIZACION is null");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionIsNotNull() {
			addCriterion("AUD_FECHA_ACTUALIZACION is not null");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionEqualTo(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION =", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionNotEqualTo(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION <>", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionGreaterThan(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION >", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionGreaterThanOrEqualTo(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION >=", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionLessThan(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION <", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionLessThanOrEqualTo(Date value) {
			addCriterion("AUD_FECHA_ACTUALIZACION <=", value, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionIn(List<Date> values) {
			addCriterion("AUD_FECHA_ACTUALIZACION in", values, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionNotIn(List<Date> values) {
			addCriterion("AUD_FECHA_ACTUALIZACION not in", values, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionBetween(Date value1, Date value2) {
			addCriterion("AUD_FECHA_ACTUALIZACION between", value1, value2, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudFechaActualizacionNotBetween(Date value1, Date value2) {
			addCriterion("AUD_FECHA_ACTUALIZACION not between", value1, value2, "audFechaActualizacion");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioIsNull() {
			addCriterion("AUD_COD_USUARIO is null");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioIsNotNull() {
			addCriterion("AUD_COD_USUARIO is not null");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioEqualTo(Long value) {
			addCriterion("AUD_COD_USUARIO =", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotEqualTo(Long value) {
			addCriterion("AUD_COD_USUARIO <>", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioGreaterThan(Long value) {
			addCriterion("AUD_COD_USUARIO >", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioGreaterThanOrEqualTo(Long value) {
			addCriterion("AUD_COD_USUARIO >=", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioLessThan(Long value) {
			addCriterion("AUD_COD_USUARIO <", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioLessThanOrEqualTo(Long value) {
			addCriterion("AUD_COD_USUARIO <=", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioIn(List<Long> values) {
			addCriterion("AUD_COD_USUARIO in", values, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotIn(List<Long> values) {
			addCriterion("AUD_COD_USUARIO not in", values, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioBetween(Long value1, Long value2) {
			addCriterion("AUD_COD_USUARIO between", value1, value2, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotBetween(Long value1, Long value2) {
			addCriterion("AUD_COD_USUARIO not between", value1, value2, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodRolIsNull() {
			addCriterion("AUD_COD_ROL is null");
			return (Criteria) this;
		}

		public Criteria andAudCodRolIsNotNull() {
			addCriterion("AUD_COD_ROL is not null");
			return (Criteria) this;
		}

		public Criteria andAudCodRolEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_ROL =", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_ROL <>", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThan(BigDecimal value) {
			addCriterion("AUD_COD_ROL >", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_ROL >=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThan(BigDecimal value) {
			addCriterion("AUD_COD_ROL <", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThanOrEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_ROL <=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolIn(List<BigDecimal> values) {
			addCriterion("AUD_COD_ROL in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotIn(List<BigDecimal> values) {
			addCriterion("AUD_COD_ROL not in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("AUD_COD_ROL between", value1, value2, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("AUD_COD_ROL not between", value1, value2, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudAccionIsNull() {
			addCriterion("AUD_ACCION is null");
			return (Criteria) this;
		}

		public Criteria andAudAccionIsNotNull() {
			addCriterion("AUD_ACCION is not null");
			return (Criteria) this;
		}

		public Criteria andAudAccionEqualTo(Integer value) {
			addCriterion("AUD_ACCION =", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionNotEqualTo(Integer value) {
			addCriterion("AUD_ACCION <>", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionGreaterThan(Integer value) {
			addCriterion("AUD_ACCION >", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionGreaterThanOrEqualTo(Integer value) {
			addCriterion("AUD_ACCION >=", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionLessThan(Integer value) {
			addCriterion("AUD_ACCION <", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionLessThanOrEqualTo(Integer value) {
			addCriterion("AUD_ACCION <=", value, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionIn(List<Integer> values) {
			addCriterion("AUD_ACCION in", values, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionNotIn(List<Integer> values) {
			addCriterion("AUD_ACCION not in", values, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionBetween(Integer value1, Integer value2) {
			addCriterion("AUD_ACCION between", value1, value2, "audAccion");
			return (Criteria) this;
		}

		public Criteria andAudAccionNotBetween(Integer value1, Integer value2) {
			addCriterion("AUD_ACCION not between", value1, value2, "audAccion");
			return (Criteria) this;
		}

		public Criteria andFlgActivoIsNull() {
			addCriterion("FLG_ACTIVO is null");
			return (Criteria) this;
		}

		public Criteria andFlgActivoIsNotNull() {
			addCriterion("FLG_ACTIVO is not null");
			return (Criteria) this;
		}

		public Criteria andFlgActivoEqualTo(Short value) {
			addCriterion("FLG_ACTIVO =", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotEqualTo(Short value) {
			addCriterion("FLG_ACTIVO <>", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoGreaterThan(Short value) {
			addCriterion("FLG_ACTIVO >", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoGreaterThanOrEqualTo(Short value) {
			addCriterion("FLG_ACTIVO >=", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoLessThan(Short value) {
			addCriterion("FLG_ACTIVO <", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoLessThanOrEqualTo(Short value) {
			addCriterion("FLG_ACTIVO <=", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoIn(List<Short> values) {
			addCriterion("FLG_ACTIVO in", values, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotIn(List<Short> values) {
			addCriterion("FLG_ACTIVO not in", values, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoBetween(Short value1, Short value2) {
			addCriterion("FLG_ACTIVO between", value1, value2, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotBetween(Short value1, Short value2) {
			addCriterion("FLG_ACTIVO not between", value1, value2, "flgActivo");
			return (Criteria) this;
		}
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NACIONALIDAD_PERFIL
	 * @mbg.generated  Thu Oct 18 11:29:01 COT 2018
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
	/**
	 * 
	 */
	private int limitInit;
	/**
	 * 
	 */
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
    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table NACIONALIDAD_PERFIL
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 29 14:57:36 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}