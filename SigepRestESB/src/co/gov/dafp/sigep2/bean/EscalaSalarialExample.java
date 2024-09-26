package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EscalaSalarialExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public EscalaSalarialExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
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

		public Criteria andCodEscalaSalarialIsNull() {
			addCriterion("COD_ESCALA_SALARIAL is null");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialIsNotNull() {
			addCriterion("COD_ESCALA_SALARIAL is not null");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialEqualTo(Long value) {
			addCriterion("COD_ESCALA_SALARIAL =", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialNotEqualTo(Long value) {
			addCriterion("COD_ESCALA_SALARIAL <>", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialGreaterThan(Long value) {
			addCriterion("COD_ESCALA_SALARIAL >", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_ESCALA_SALARIAL >=", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialLessThan(Long value) {
			addCriterion("COD_ESCALA_SALARIAL <", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialLessThanOrEqualTo(Long value) {
			addCriterion("COD_ESCALA_SALARIAL <=", value, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialIn(List<Long> values) {
			addCriterion("COD_ESCALA_SALARIAL in", values, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialNotIn(List<Long> values) {
			addCriterion("COD_ESCALA_SALARIAL not in", values, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialBetween(Long value1, Long value2) {
			addCriterion("COD_ESCALA_SALARIAL between", value1, value2, "codEscalaSalarial");
			return (Criteria) this;
		}

		public Criteria andCodEscalaSalarialNotBetween(Long value1, Long value2) {
			addCriterion("COD_ESCALA_SALARIAL not between", value1, value2, "codEscalaSalarial");
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

		public Criteria andCodNivelCargoIsNull() {
			addCriterion("COD_NIVEL_CARGO is null");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoIsNotNull() {
			addCriterion("COD_NIVEL_CARGO is not null");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoEqualTo(Long value) {
			addCriterion("COD_NIVEL_CARGO =", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotEqualTo(Long value) {
			addCriterion("COD_NIVEL_CARGO <>", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoGreaterThan(Long value) {
			addCriterion("COD_NIVEL_CARGO >", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NIVEL_CARGO >=", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoLessThan(Long value) {
			addCriterion("COD_NIVEL_CARGO <", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoLessThanOrEqualTo(Long value) {
			addCriterion("COD_NIVEL_CARGO <=", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoIn(List<Long> values) {
			addCriterion("COD_NIVEL_CARGO in", values, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotIn(List<Long> values) {
			addCriterion("COD_NIVEL_CARGO not in", values, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoBetween(Long value1, Long value2) {
			addCriterion("COD_NIVEL_CARGO between", value1, value2, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotBetween(Long value1, Long value2) {
			addCriterion("COD_NIVEL_CARGO not between", value1, value2, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNormaIsNull() {
			addCriterion("COD_NORMA is null");
			return (Criteria) this;
		}

		public Criteria andCodNormaIsNotNull() {
			addCriterion("COD_NORMA is not null");
			return (Criteria) this;
		}

		public Criteria andCodNormaEqualTo(Long value) {
			addCriterion("COD_NORMA =", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaNotEqualTo(Long value) {
			addCriterion("COD_NORMA <>", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaGreaterThan(Long value) {
			addCriterion("COD_NORMA >", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NORMA >=", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaLessThan(Long value) {
			addCriterion("COD_NORMA <", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaLessThanOrEqualTo(Long value) {
			addCriterion("COD_NORMA <=", value, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaIn(List<Long> values) {
			addCriterion("COD_NORMA in", values, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaNotIn(List<Long> values) {
			addCriterion("COD_NORMA not in", values, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaBetween(Long value1, Long value2) {
			addCriterion("COD_NORMA between", value1, value2, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodNormaNotBetween(Long value1, Long value2) {
			addCriterion("COD_NORMA not between", value1, value2, "codNorma");
			return (Criteria) this;
		}

		public Criteria andCodGradoIsNull() {
			addCriterion("COD_GRADO is null");
			return (Criteria) this;
		}

		public Criteria andCodGradoIsNotNull() {
			addCriterion("COD_GRADO is not null");
			return (Criteria) this;
		}

		public Criteria andCodGradoEqualTo(Long value) {
			addCriterion("COD_GRADO =", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotEqualTo(Long value) {
			addCriterion("COD_GRADO <>", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoGreaterThan(Long value) {
			addCriterion("COD_GRADO >", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_GRADO >=", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoLessThan(Long value) {
			addCriterion("COD_GRADO <", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoLessThanOrEqualTo(Long value) {
			addCriterion("COD_GRADO <=", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoIn(List<Long> values) {
			addCriterion("COD_GRADO in", values, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotIn(List<Long> values) {
			addCriterion("COD_GRADO not in", values, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoBetween(Long value1, Long value2) {
			addCriterion("COD_GRADO between", value1, value2, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotBetween(Long value1, Long value2) {
			addCriterion("COD_GRADO not between", value1, value2, "codGrado");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaIsNull() {
			addCriterion("ASIGNACION_BASICA is null");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaIsNotNull() {
			addCriterion("ASIGNACION_BASICA is not null");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaEqualTo(Long value) {
			addCriterion("ASIGNACION_BASICA =", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaNotEqualTo(Long value) {
			addCriterion("ASIGNACION_BASICA <>", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaGreaterThan(Long value) {
			addCriterion("ASIGNACION_BASICA >", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaGreaterThanOrEqualTo(Long value) {
			addCriterion("ASIGNACION_BASICA >=", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaLessThan(Long value) {
			addCriterion("ASIGNACION_BASICA <", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaLessThanOrEqualTo(Long value) {
			addCriterion("ASIGNACION_BASICA <=", value, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaIn(List<Long> values) {
			addCriterion("ASIGNACION_BASICA in", values, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaNotIn(List<Long> values) {
			addCriterion("ASIGNACION_BASICA not in", values, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaBetween(Long value1, Long value2) {
			addCriterion("ASIGNACION_BASICA between", value1, value2, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andAsignacionBasicaNotBetween(Long value1, Long value2) {
			addCriterion("ASIGNACION_BASICA not between", value1, value2, "asignacionBasica");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionIsNull() {
			addCriterion("GASTOS_REPRESENTACION is null");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionIsNotNull() {
			addCriterion("GASTOS_REPRESENTACION is not null");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionEqualTo(Long value) {
			addCriterion("GASTOS_REPRESENTACION =", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionNotEqualTo(Long value) {
			addCriterion("GASTOS_REPRESENTACION <>", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionGreaterThan(Long value) {
			addCriterion("GASTOS_REPRESENTACION >", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionGreaterThanOrEqualTo(Long value) {
			addCriterion("GASTOS_REPRESENTACION >=", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionLessThan(Long value) {
			addCriterion("GASTOS_REPRESENTACION <", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionLessThanOrEqualTo(Long value) {
			addCriterion("GASTOS_REPRESENTACION <=", value, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionIn(List<Long> values) {
			addCriterion("GASTOS_REPRESENTACION in", values, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionNotIn(List<Long> values) {
			addCriterion("GASTOS_REPRESENTACION not in", values, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionBetween(Long value1, Long value2) {
			addCriterion("GASTOS_REPRESENTACION between", value1, value2, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andGastosRepresentacionNotBetween(Long value1, Long value2) {
			addCriterion("GASTOS_REPRESENTACION not between", value1, value2, "gastosRepresentacion");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialIsNull() {
			addCriterion("COD_FACTOR_SALARIAL is null");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialIsNotNull() {
			addCriterion("COD_FACTOR_SALARIAL is not null");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialEqualTo(Long value) {
			addCriterion("COD_FACTOR_SALARIAL =", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialNotEqualTo(Long value) {
			addCriterion("COD_FACTOR_SALARIAL <>", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialGreaterThan(Long value) {
			addCriterion("COD_FACTOR_SALARIAL >", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_FACTOR_SALARIAL >=", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialLessThan(Long value) {
			addCriterion("COD_FACTOR_SALARIAL <", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialLessThanOrEqualTo(Long value) {
			addCriterion("COD_FACTOR_SALARIAL <=", value, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialIn(List<Long> values) {
			addCriterion("COD_FACTOR_SALARIAL in", values, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialNotIn(List<Long> values) {
			addCriterion("COD_FACTOR_SALARIAL not in", values, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialBetween(Long value1, Long value2) {
			addCriterion("COD_FACTOR_SALARIAL between", value1, value2, "codFactorSalarial");
			return (Criteria) this;
		}

		public Criteria andCodFactorSalarialNotBetween(Long value1, Long value2) {
			addCriterion("COD_FACTOR_SALARIAL not between", value1, value2, "codFactorSalarial");
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

		public Criteria andNivelJerarquicoIsNull() {
			addCriterion("NIVEL_JERARQUICO is null");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoIsNotNull() {
			addCriterion("NIVEL_JERARQUICO is not null");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoEqualTo(Long value) {
			addCriterion("NIVEL_JERARQUICO =", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoNotEqualTo(Long value) {
			addCriterion("NIVEL_JERARQUICO <>", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoGreaterThan(Long value) {
			addCriterion("NIVEL_JERARQUICO >", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoGreaterThanOrEqualTo(Long value) {
			addCriterion("NIVEL_JERARQUICO >=", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoLessThan(Long value) {
			addCriterion("NIVEL_JERARQUICO <", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoLessThanOrEqualTo(Long value) {
			addCriterion("NIVEL_JERARQUICO <=", value, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoIn(List<Long> values) {
			addCriterion("NIVEL_JERARQUICO in", values, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoNotIn(List<Long> values) {
			addCriterion("NIVEL_JERARQUICO not in", values, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoBetween(Long value1, Long value2) {
			addCriterion("NIVEL_JERARQUICO between", value1, value2, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andNivelJerarquicoNotBetween(Long value1, Long value2) {
			addCriterion("NIVEL_JERARQUICO not between", value1, value2, "nivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesIsNull() {
			addCriterion("OTRAS_ASIGNACIONES is null");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesIsNotNull() {
			addCriterion("OTRAS_ASIGNACIONES is not null");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesEqualTo(Long value) {
			addCriterion("OTRAS_ASIGNACIONES =", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesNotEqualTo(Long value) {
			addCriterion("OTRAS_ASIGNACIONES <>", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesGreaterThan(Long value) {
			addCriterion("OTRAS_ASIGNACIONES >", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesGreaterThanOrEqualTo(Long value) {
			addCriterion("OTRAS_ASIGNACIONES >=", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesLessThan(Long value) {
			addCriterion("OTRAS_ASIGNACIONES <", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesLessThanOrEqualTo(Long value) {
			addCriterion("OTRAS_ASIGNACIONES <=", value, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesIn(List<Long> values) {
			addCriterion("OTRAS_ASIGNACIONES in", values, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesNotIn(List<Long> values) {
			addCriterion("OTRAS_ASIGNACIONES not in", values, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesBetween(Long value1, Long value2) {
			addCriterion("OTRAS_ASIGNACIONES between", value1, value2, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andOtrasAsignacionesNotBetween(Long value1, Long value2) {
			addCriterion("OTRAS_ASIGNACIONES not between", value1, value2, "otrasAsignaciones");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionIsNull() {
			addCriterion("VALOR_ASIGNACION is null");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionIsNotNull() {
			addCriterion("VALOR_ASIGNACION is not null");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionEqualTo(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION =", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotEqualTo(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION <>", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionGreaterThan(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION >", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION >=", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionLessThan(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION <", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionLessThanOrEqualTo(BigDecimal value) {
			addCriterion("VALOR_ASIGNACION <=", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionIn(List<BigDecimal> values) {
			addCriterion("VALOR_ASIGNACION in", values, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotIn(List<BigDecimal> values) {
			addCriterion("VALOR_ASIGNACION not in", values, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("VALOR_ASIGNACION between", value1, value2, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("VALOR_ASIGNACION not between", value1, value2, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionIsNull() {
			addCriterion("PORCENTAJE_ASIGNACION is null");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionIsNotNull() {
			addCriterion("PORCENTAJE_ASIGNACION is not null");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionEqualTo(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION =", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotEqualTo(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION <>", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionGreaterThan(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION >", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionGreaterThanOrEqualTo(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION >=", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionLessThan(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION <", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionLessThanOrEqualTo(Short value) {
			addCriterion("PORCENTAJE_ASIGNACION <=", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionIn(List<Short> values) {
			addCriterion("PORCENTAJE_ASIGNACION in", values, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotIn(List<Short> values) {
			addCriterion("PORCENTAJE_ASIGNACION not in", values, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionBetween(Short value1, Short value2) {
			addCriterion("PORCENTAJE_ASIGNACION between", value1, value2, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotBetween(Short value1, Short value2) {
			addCriterion("PORCENTAJE_ASIGNACION not between", value1, value2, "porcentajeAsignacion");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ESCALA_SALARIAL
	 * @mbg.generated  Tue Aug 28 17:54:58 COT 2018
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
     * This class corresponds to the database table ESCALA_SALARIAL
     *
     * @mbg.generated do_not_delete_during_merge Tue Aug 28 11:08:28 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}