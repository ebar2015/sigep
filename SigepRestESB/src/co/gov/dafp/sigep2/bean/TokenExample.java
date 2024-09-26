package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TokenExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public TokenExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
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

		public Criteria andCodTokenIsNull() {
			addCriterion("COD_TOKEN is null");
			return (Criteria) this;
		}

		public Criteria andCodTokenIsNotNull() {
			addCriterion("COD_TOKEN is not null");
			return (Criteria) this;
		}

		public Criteria andCodTokenEqualTo(BigDecimal value) {
			addCriterion("COD_TOKEN =", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenNotEqualTo(BigDecimal value) {
			addCriterion("COD_TOKEN <>", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenGreaterThan(BigDecimal value) {
			addCriterion("COD_TOKEN >", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_TOKEN >=", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenLessThan(BigDecimal value) {
			addCriterion("COD_TOKEN <", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_TOKEN <=", value, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenIn(List<BigDecimal> values) {
			addCriterion("COD_TOKEN in", values, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenNotIn(List<BigDecimal> values) {
			addCriterion("COD_TOKEN not in", values, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_TOKEN between", value1, value2, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodTokenNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_TOKEN not between", value1, value2, "codToken");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioIsNull() {
			addCriterion("COD_USUARIO is null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioIsNotNull() {
			addCriterion("COD_USUARIO is not null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO =", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioNotEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO <>", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioGreaterThan(BigDecimal value) {
			addCriterion("COD_USUARIO >", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO >=", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioLessThan(BigDecimal value) {
			addCriterion("COD_USUARIO <", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO <=", value, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO in", values, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioNotIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO not in", values, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO between", value1, value2, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO not between", value1, value2, "codUsuario");
			return (Criteria) this;
		}

		public Criteria andCodEntidadIsNull() {
			addCriterion("COD_ENTIDAD is null");
			return (Criteria) this;
		}

		public Criteria andCodEntidadIsNotNull() {
			addCriterion("COD_ENTIDAD is not null");
			return (Criteria) this;
		}

		public Criteria andCodEntidadEqualTo(Long value) {
			addCriterion("COD_ENTIDAD =", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadNotEqualTo(Long value) {
			addCriterion("COD_ENTIDAD <>", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadGreaterThan(Long value) {
			addCriterion("COD_ENTIDAD >", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_ENTIDAD >=", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadLessThan(Long value) {
			addCriterion("COD_ENTIDAD <", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadLessThanOrEqualTo(Long value) {
			addCriterion("COD_ENTIDAD <=", value, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadIn(List<Long> values) {
			addCriterion("COD_ENTIDAD in", values, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadNotIn(List<Long> values) {
			addCriterion("COD_ENTIDAD not in", values, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadBetween(Long value1, Long value2) {
			addCriterion("COD_ENTIDAD between", value1, value2, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andCodEntidadNotBetween(Long value1, Long value2) {
			addCriterion("COD_ENTIDAD not between", value1, value2, "codEntidad");
			return (Criteria) this;
		}

		public Criteria andTokenIsNull() {
			addCriterion("TOKEN is null");
			return (Criteria) this;
		}

		public Criteria andTokenIsNotNull() {
			addCriterion("TOKEN is not null");
			return (Criteria) this;
		}

		public Criteria andTokenEqualTo(String value) {
			addCriterion("TOKEN =", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenNotEqualTo(String value) {
			addCriterion("TOKEN <>", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenGreaterThan(String value) {
			addCriterion("TOKEN >", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenGreaterThanOrEqualTo(String value) {
			addCriterion("TOKEN >=", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenLessThan(String value) {
			addCriterion("TOKEN <", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenLessThanOrEqualTo(String value) {
			addCriterion("TOKEN <=", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenLike(String value) {
			addCriterion("TOKEN like", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenNotLike(String value) {
			addCriterion("TOKEN not like", value, "token");
			return (Criteria) this;
		}

		public Criteria andTokenIn(List<String> values) {
			addCriterion("TOKEN in", values, "token");
			return (Criteria) this;
		}

		public Criteria andTokenNotIn(List<String> values) {
			addCriterion("TOKEN not in", values, "token");
			return (Criteria) this;
		}

		public Criteria andTokenBetween(String value1, String value2) {
			addCriterion("TOKEN between", value1, value2, "token");
			return (Criteria) this;
		}

		public Criteria andTokenNotBetween(String value1, String value2) {
			addCriterion("TOKEN not between", value1, value2, "token");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionIsNull() {
			addCriterion("FECHA_GENERACION is null");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionIsNotNull() {
			addCriterion("FECHA_GENERACION is not null");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionEqualTo(Date value) {
			addCriterion("FECHA_GENERACION =", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionNotEqualTo(Date value) {
			addCriterion("FECHA_GENERACION <>", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionGreaterThan(Date value) {
			addCriterion("FECHA_GENERACION >", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA_GENERACION >=", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionLessThan(Date value) {
			addCriterion("FECHA_GENERACION <", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionLessThanOrEqualTo(Date value) {
			addCriterion("FECHA_GENERACION <=", value, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionIn(List<Date> values) {
			addCriterion("FECHA_GENERACION in", values, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionNotIn(List<Date> values) {
			addCriterion("FECHA_GENERACION not in", values, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionBetween(Date value1, Date value2) {
			addCriterion("FECHA_GENERACION between", value1, value2, "fechaGeneracion");
			return (Criteria) this;
		}

		public Criteria andFechaGeneracionNotBetween(Date value1, Date value2) {
			addCriterion("FECHA_GENERACION not between", value1, value2, "fechaGeneracion");
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

		public Criteria andAudCodRolEqualTo(Integer value) {
			addCriterion("AUD_COD_ROL =", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotEqualTo(Integer value) {
			addCriterion("AUD_COD_ROL <>", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThan(Integer value) {
			addCriterion("AUD_COD_ROL >", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThanOrEqualTo(Integer value) {
			addCriterion("AUD_COD_ROL >=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThan(Integer value) {
			addCriterion("AUD_COD_ROL <", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThanOrEqualTo(Integer value) {
			addCriterion("AUD_COD_ROL <=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolIn(List<Integer> values) {
			addCriterion("AUD_COD_ROL in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotIn(List<Integer> values) {
			addCriterion("AUD_COD_ROL not in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolBetween(Integer value1, Integer value2) {
			addCriterion("AUD_COD_ROL between", value1, value2, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotBetween(Integer value1, Integer value2) {
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

		public Criteria andFechaAckIsNull() {
			addCriterion("FECHA_ACK is null");
			return (Criteria) this;
		}

		public Criteria andFechaAckIsNotNull() {
			addCriterion("FECHA_ACK is not null");
			return (Criteria) this;
		}

		public Criteria andFechaAckEqualTo(Date value) {
			addCriterion("FECHA_ACK =", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckNotEqualTo(Date value) {
			addCriterion("FECHA_ACK <>", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckGreaterThan(Date value) {
			addCriterion("FECHA_ACK >", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA_ACK >=", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckLessThan(Date value) {
			addCriterion("FECHA_ACK <", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckLessThanOrEqualTo(Date value) {
			addCriterion("FECHA_ACK <=", value, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckIn(List<Date> values) {
			addCriterion("FECHA_ACK in", values, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckNotIn(List<Date> values) {
			addCriterion("FECHA_ACK not in", values, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckBetween(Date value1, Date value2) {
			addCriterion("FECHA_ACK between", value1, value2, "fechaAck");
			return (Criteria) this;
		}

		public Criteria andFechaAckNotBetween(Date value1, Date value2) {
			addCriterion("FECHA_ACK not between", value1, value2, "fechaAck");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table TOKEN
	 * @mbg.generated  Fri Sep 07 08:55:22 COT 2018
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
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table TOKEN
     *
     * @mbg.generated do_not_delete_during_merge Thu Feb 08 11:01:20 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}