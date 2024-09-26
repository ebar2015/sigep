package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioRolDependenciaExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public UsuarioRolDependenciaExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
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

		public Criteria andCodUsuarioRolDependenciaIsNull() {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA is null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaIsNotNull() {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA is not null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA =", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaNotEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA <>", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaGreaterThan(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA >", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA >=", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaLessThan(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA <", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA <=", value, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA in", values, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaNotIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA not in", values, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA between", value1, value2, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolDependenciaNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO_ROL_DEPENDENCIA not between", value1, value2, "codUsuarioRolDependencia");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadIsNull() {
			addCriterion("COD_USUARIO_ROL_ENTIDAD is null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadIsNotNull() {
			addCriterion("COD_USUARIO_ROL_ENTIDAD is not null");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD =", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadNotEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD <>", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadGreaterThan(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD >", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD >=", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadLessThan(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD <", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD <=", value, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD in", values, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadNotIn(List<BigDecimal> values) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD not in", values, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD between", value1, value2, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodUsuarioRolEntidadNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_USUARIO_ROL_ENTIDAD not between", value1, value2, "codUsuarioRolEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadIsNull() {
			addCriterion("COD_DEPENDENCIA_ENTIDAD is null");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadIsNotNull() {
			addCriterion("COD_DEPENDENCIA_ENTIDAD is not null");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadEqualTo(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD =", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadNotEqualTo(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD <>", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadGreaterThan(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD >", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD >=", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadLessThan(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD <", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD <=", value, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadIn(List<BigDecimal> values) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD in", values, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadNotIn(List<BigDecimal> values) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD not in", values, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD between", value1, value2, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andCodDependenciaEntidadNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_DEPENDENCIA_ENTIDAD not between", value1, value2, "codDependenciaEntidad");
			return (Criteria) this;
		}

		public Criteria andFechaInicioIsNull() {
			addCriterion("FECHA_INICIO is null");
			return (Criteria) this;
		}

		public Criteria andFechaInicioIsNotNull() {
			addCriterion("FECHA_INICIO is not null");
			return (Criteria) this;
		}

		public Criteria andFechaInicioEqualTo(Date value) {
			addCriterion("FECHA_INICIO =", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioNotEqualTo(Date value) {
			addCriterion("FECHA_INICIO <>", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioGreaterThan(Date value) {
			addCriterion("FECHA_INICIO >", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA_INICIO >=", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioLessThan(Date value) {
			addCriterion("FECHA_INICIO <", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioLessThanOrEqualTo(Date value) {
			addCriterion("FECHA_INICIO <=", value, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioIn(List<Date> values) {
			addCriterion("FECHA_INICIO in", values, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioNotIn(List<Date> values) {
			addCriterion("FECHA_INICIO not in", values, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioBetween(Date value1, Date value2) {
			addCriterion("FECHA_INICIO between", value1, value2, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaInicioNotBetween(Date value1, Date value2) {
			addCriterion("FECHA_INICIO not between", value1, value2, "fechaInicio");
			return (Criteria) this;
		}

		public Criteria andFechaFinIsNull() {
			addCriterion("FECHA_FIN is null");
			return (Criteria) this;
		}

		public Criteria andFechaFinIsNotNull() {
			addCriterion("FECHA_FIN is not null");
			return (Criteria) this;
		}

		public Criteria andFechaFinEqualTo(Date value) {
			addCriterion("FECHA_FIN =", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinNotEqualTo(Date value) {
			addCriterion("FECHA_FIN <>", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinGreaterThan(Date value) {
			addCriterion("FECHA_FIN >", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinGreaterThanOrEqualTo(Date value) {
			addCriterion("FECHA_FIN >=", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinLessThan(Date value) {
			addCriterion("FECHA_FIN <", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinLessThanOrEqualTo(Date value) {
			addCriterion("FECHA_FIN <=", value, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinIn(List<Date> values) {
			addCriterion("FECHA_FIN in", values, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinNotIn(List<Date> values) {
			addCriterion("FECHA_FIN not in", values, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinBetween(Date value1, Date value2) {
			addCriterion("FECHA_FIN between", value1, value2, "fechaFin");
			return (Criteria) this;
		}

		public Criteria andFechaFinNotBetween(Date value1, Date value2) {
			addCriterion("FECHA_FIN not between", value1, value2, "fechaFin");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table USUARIO_ROL_DEPENDENCIA
	 * @mbg.generated  Wed Jan 09 08:39:01 COT 2019
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
     * This class corresponds to the database table USUARIO_ROL_DEPENDENCIA
     *
     * @mbg.generated do_not_delete_during_merge Wed Jan 09 08:37:45 COT 2019
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}