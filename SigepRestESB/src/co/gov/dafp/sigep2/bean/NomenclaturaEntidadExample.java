package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NomenclaturaEntidadExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public NomenclaturaEntidadExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
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

		public Criteria andCodNomenclaturaEntidIsNull() {
			addCriterion("COD_NOMENCLATURA_ENTID is null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidIsNotNull() {
			addCriterion("COD_NOMENCLATURA_ENTID is not null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID =", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidNotEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID <>", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidGreaterThan(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID >", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID >=", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidLessThan(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID <", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidLessThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_ENTID <=", value, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA_ENTID in", values, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidNotIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA_ENTID not in", values, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA_ENTID between", value1, value2, "codNomenclaturaEntid");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEntidNotBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA_ENTID not between", value1, value2, "codNomenclaturaEntid");
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

		public Criteria andCodNomenclaturaIsNull() {
			addCriterion("COD_NOMENCLATURA is null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaIsNotNull() {
			addCriterion("COD_NOMENCLATURA is not null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA =", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaNotEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA <>", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaGreaterThan(Long value) {
			addCriterion("COD_NOMENCLATURA >", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA >=", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaLessThan(Long value) {
			addCriterion("COD_NOMENCLATURA <", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaLessThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA <=", value, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA in", values, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaNotIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA not in", values, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA between", value1, value2, "codNomenclatura");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaNotBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA not between", value1, value2, "codNomenclatura");
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

		public Criteria andAudCodUsuarioEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO =", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO <>", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioGreaterThan(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO >", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO >=", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioLessThan(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO <", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioLessThanOrEqualTo(BigDecimal value) {
			addCriterion("AUD_COD_USUARIO <=", value, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioIn(List<BigDecimal> values) {
			addCriterion("AUD_COD_USUARIO in", values, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotIn(List<BigDecimal> values) {
			addCriterion("AUD_COD_USUARIO not in", values, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("AUD_COD_USUARIO between", value1, value2, "audCodUsuario");
			return (Criteria) this;
		}

		public Criteria andAudCodUsuarioNotBetween(BigDecimal value1, BigDecimal value2) {
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table NOMENCLATURA_ENTIDAD
	 * @mbg.generated  Thu Jul 26 17:26:19 COT 2018
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
     * This class corresponds to the database table NOMENCLATURA_ENTIDAD
     *
     * @mbg.generated do_not_delete_during_merge Thu Jul 26 17:26:19 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}