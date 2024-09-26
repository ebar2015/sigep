package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsignacionSalarialExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public AsignacionSalarialExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
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

		public Criteria andCodAsignacionSalarialIsNull() {
			addCriterion("COD_ASIGNACION_SALARIAL is null");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialIsNotNull() {
			addCriterion("COD_ASIGNACION_SALARIAL is not null");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialEqualTo(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL =", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialNotEqualTo(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL <>", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialGreaterThan(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL >", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL >=", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialLessThan(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL <", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialLessThanOrEqualTo(Long value) {
			addCriterion("COD_ASIGNACION_SALARIAL <=", value, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialIn(List<Long> values) {
			addCriterion("COD_ASIGNACION_SALARIAL in", values, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialNotIn(List<Long> values) {
			addCriterion("COD_ASIGNACION_SALARIAL not in", values, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialBetween(Long value1, Long value2) {
			addCriterion("COD_ASIGNACION_SALARIAL between", value1, value2, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodAsignacionSalarialNotBetween(Long value1, Long value2) {
			addCriterion("COD_ASIGNACION_SALARIAL not between", value1, value2, "codAsignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoIsNull() {
			addCriterion("COD_NIVEL_JERARQUICO is null");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoIsNotNull() {
			addCriterion("COD_NIVEL_JERARQUICO is not null");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoEqualTo(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO =", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoNotEqualTo(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO <>", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoGreaterThan(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO >", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO >=", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoLessThan(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO <", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoLessThanOrEqualTo(Long value) {
			addCriterion("COD_NIVEL_JERARQUICO <=", value, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoIn(List<Long> values) {
			addCriterion("COD_NIVEL_JERARQUICO in", values, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoNotIn(List<Long> values) {
			addCriterion("COD_NIVEL_JERARQUICO not in", values, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoBetween(Long value1, Long value2) {
			addCriterion("COD_NIVEL_JERARQUICO between", value1, value2, "codNivelJerarquico");
			return (Criteria) this;
		}

		public Criteria andCodNivelJerarquicoNotBetween(Long value1, Long value2) {
			addCriterion("COD_NIVEL_JERARQUICO not between", value1, value2, "codNivelJerarquico");
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

		public Criteria andValorAsignacionEqualTo(Long value) {
			addCriterion("VALOR_ASIGNACION =", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotEqualTo(Long value) {
			addCriterion("VALOR_ASIGNACION <>", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionGreaterThan(Long value) {
			addCriterion("VALOR_ASIGNACION >", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionGreaterThanOrEqualTo(Long value) {
			addCriterion("VALOR_ASIGNACION >=", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionLessThan(Long value) {
			addCriterion("VALOR_ASIGNACION <", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionLessThanOrEqualTo(Long value) {
			addCriterion("VALOR_ASIGNACION <=", value, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionIn(List<Long> values) {
			addCriterion("VALOR_ASIGNACION in", values, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotIn(List<Long> values) {
			addCriterion("VALOR_ASIGNACION not in", values, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionBetween(Long value1, Long value2) {
			addCriterion("VALOR_ASIGNACION between", value1, value2, "valorAsignacion");
			return (Criteria) this;
		}

		public Criteria andValorAsignacionNotBetween(Long value1, Long value2) {
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

		public Criteria andPorcentajeAsignacionEqualTo(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION =", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotEqualTo(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION <>", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionGreaterThan(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION >", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionGreaterThanOrEqualTo(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION >=", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionLessThan(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION <", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionLessThanOrEqualTo(Long value) {
			addCriterion("PORCENTAJE_ASIGNACION <=", value, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionIn(List<Long> values) {
			addCriterion("PORCENTAJE_ASIGNACION in", values, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotIn(List<Long> values) {
			addCriterion("PORCENTAJE_ASIGNACION not in", values, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionBetween(Long value1, Long value2) {
			addCriterion("PORCENTAJE_ASIGNACION between", value1, value2, "porcentajeAsignacion");
			return (Criteria) this;
		}

		public Criteria andPorcentajeAsignacionNotBetween(Long value1, Long value2) {
			addCriterion("PORCENTAJE_ASIGNACION not between", value1, value2, "porcentajeAsignacion");
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

		public Criteria andCodCargoIsNull() {
			addCriterion("COD_CARGO is null");
			return (Criteria) this;
		}

		public Criteria andCodCargoIsNotNull() {
			addCriterion("COD_CARGO is not null");
			return (Criteria) this;
		}

		public Criteria andCodCargoEqualTo(Long value) {
			addCriterion("COD_CARGO =", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoNotEqualTo(Long value) {
			addCriterion("COD_CARGO <>", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoGreaterThan(Long value) {
			addCriterion("COD_CARGO >", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_CARGO >=", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoLessThan(Long value) {
			addCriterion("COD_CARGO <", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoLessThanOrEqualTo(Long value) {
			addCriterion("COD_CARGO <=", value, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoIn(List<Long> values) {
			addCriterion("COD_CARGO in", values, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoNotIn(List<Long> values) {
			addCriterion("COD_CARGO not in", values, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoBetween(Long value1, Long value2) {
			addCriterion("COD_CARGO between", value1, value2, "codCargo");
			return (Criteria) this;
		}

		public Criteria andCodCargoNotBetween(Long value1, Long value2) {
			addCriterion("COD_CARGO not between", value1, value2, "codCargo");
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

		public Criteria andCodNomenclaturaDenominacionIsNull() {
			addCriterion("COD_NOMENCLATURA_DENOMINACION is null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionIsNotNull() {
			addCriterion("COD_NOMENCLATURA_DENOMINACION is not null");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION =", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionNotEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION <>", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionGreaterThan(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION >", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION >=", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionLessThan(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION <", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionLessThanOrEqualTo(Long value) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION <=", value, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION in", values, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionNotIn(List<Long> values) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION not in", values, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION between", value1, value2, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodNomenclaturaDenominacionNotBetween(Long value1, Long value2) {
			addCriterion("COD_NOMENCLATURA_DENOMINACION not between", value1, value2, "codNomenclaturaDenominacion");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table ASIGNACION_SALARIAL
	 * @mbg.generated  Thu Dec 20 08:43:08 COT 2018
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
     * This class corresponds to the database table ASIGNACION_SALARIAL
     *
     * @mbg.generated do_not_delete_during_merge Thu Jul 26 17:26:19 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}