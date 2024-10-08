package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParametricaExample {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public ParametricaExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
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

		public Criteria andCodTablaParametricaIsNull() {
			addCriterion("COD_TABLA_PARAMETRICA is null");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaIsNotNull() {
			addCriterion("COD_TABLA_PARAMETRICA is not null");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaEqualTo(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA =", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaNotEqualTo(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA <>", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaGreaterThan(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA >", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA >=", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaLessThan(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA <", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_TABLA_PARAMETRICA <=", value, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaIn(List<BigDecimal> values) {
			addCriterion("COD_TABLA_PARAMETRICA in", values, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaNotIn(List<BigDecimal> values) {
			addCriterion("COD_TABLA_PARAMETRICA not in", values, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_TABLA_PARAMETRICA between", value1, value2, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodTablaParametricaNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_TABLA_PARAMETRICA not between", value1, value2, "codTablaParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaIsNull() {
			addCriterion("COD_PADRE_PARAMETRICA is null");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaIsNotNull() {
			addCriterion("COD_PADRE_PARAMETRICA is not null");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaEqualTo(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA =", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaNotEqualTo(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA <>", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaGreaterThan(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA >", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA >=", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaLessThan(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA <", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PADRE_PARAMETRICA <=", value, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaIn(List<BigDecimal> values) {
			addCriterion("COD_PADRE_PARAMETRICA in", values, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaNotIn(List<BigDecimal> values) {
			addCriterion("COD_PADRE_PARAMETRICA not in", values, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PADRE_PARAMETRICA between", value1, value2, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andCodPadreParametricaNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PADRE_PARAMETRICA not between", value1, value2, "codPadreParametrica");
			return (Criteria) this;
		}

		public Criteria andNombreParametroIsNull() {
			addCriterion("NOMBRE_PARAMETRO is null");
			return (Criteria) this;
		}

		public Criteria andNombreParametroIsNotNull() {
			addCriterion("NOMBRE_PARAMETRO is not null");
			return (Criteria) this;
		}

		public Criteria andNombreParametroEqualTo(String value) {
			addCriterion("NOMBRE_PARAMETRO =", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroNotEqualTo(String value) {
			addCriterion("NOMBRE_PARAMETRO <>", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroGreaterThan(String value) {
			addCriterion("NOMBRE_PARAMETRO >", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBRE_PARAMETRO >=", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroLessThan(String value) {
			addCriterion("NOMBRE_PARAMETRO <", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroLessThanOrEqualTo(String value) {
			addCriterion("NOMBRE_PARAMETRO <=", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroLike(String value) {
			addCriterion("NOMBRE_PARAMETRO like", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroNotLike(String value) {
			addCriterion("NOMBRE_PARAMETRO not like", value, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroIn(List<String> values) {
			addCriterion("NOMBRE_PARAMETRO in", values, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroNotIn(List<String> values) {
			addCriterion("NOMBRE_PARAMETRO not in", values, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroBetween(String value1, String value2) {
			addCriterion("NOMBRE_PARAMETRO between", value1, value2, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andNombreParametroNotBetween(String value1, String value2) {
			addCriterion("NOMBRE_PARAMETRO not between", value1, value2, "nombreParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroIsNull() {
			addCriterion("VALOR_PARAMETRO is null");
			return (Criteria) this;
		}

		public Criteria andValorParametroIsNotNull() {
			addCriterion("VALOR_PARAMETRO is not null");
			return (Criteria) this;
		}

		public Criteria andValorParametroEqualTo(String value) {
			addCriterion("VALOR_PARAMETRO =", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroNotEqualTo(String value) {
			addCriterion("VALOR_PARAMETRO <>", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroGreaterThan(String value) {
			addCriterion("VALOR_PARAMETRO >", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroGreaterThanOrEqualTo(String value) {
			addCriterion("VALOR_PARAMETRO >=", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroLessThan(String value) {
			addCriterion("VALOR_PARAMETRO <", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroLessThanOrEqualTo(String value) {
			addCriterion("VALOR_PARAMETRO <=", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroLike(String value) {
			addCriterion("VALOR_PARAMETRO like", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroNotLike(String value) {
			addCriterion("VALOR_PARAMETRO not like", value, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroIn(List<String> values) {
			addCriterion("VALOR_PARAMETRO in", values, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroNotIn(List<String> values) {
			addCriterion("VALOR_PARAMETRO not in", values, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroBetween(String value1, String value2) {
			addCriterion("VALOR_PARAMETRO between", value1, value2, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andValorParametroNotBetween(String value1, String value2) {
			addCriterion("VALOR_PARAMETRO not between", value1, value2, "valorParametro");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoIsNull() {
			addCriterion("FLG_ESTADO is null");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoIsNotNull() {
			addCriterion("FLG_ESTADO is not null");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoEqualTo(Short value) {
			addCriterion("FLG_ESTADO =", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoNotEqualTo(Short value) {
			addCriterion("FLG_ESTADO <>", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoGreaterThan(Short value) {
			addCriterion("FLG_ESTADO >", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoGreaterThanOrEqualTo(Short value) {
			addCriterion("FLG_ESTADO >=", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoLessThan(Short value) {
			addCriterion("FLG_ESTADO <", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoLessThanOrEqualTo(Short value) {
			addCriterion("FLG_ESTADO <=", value, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoIn(List<Short> values) {
			addCriterion("FLG_ESTADO in", values, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoNotIn(List<Short> values) {
			addCriterion("FLG_ESTADO not in", values, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoBetween(Short value1, Short value2) {
			addCriterion("FLG_ESTADO between", value1, value2, "flgEstado");
			return (Criteria) this;
		}

		public Criteria andFlgEstadoNotBetween(Short value1, Short value2) {
			addCriterion("FLG_ESTADO not between", value1, value2, "flgEstado");
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

		public Criteria andAudCodRolEqualTo(Short value) {
			addCriterion("AUD_COD_ROL =", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotEqualTo(Short value) {
			addCriterion("AUD_COD_ROL <>", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThan(Short value) {
			addCriterion("AUD_COD_ROL >", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolGreaterThanOrEqualTo(Short value) {
			addCriterion("AUD_COD_ROL >=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThan(Short value) {
			addCriterion("AUD_COD_ROL <", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolLessThanOrEqualTo(Short value) {
			addCriterion("AUD_COD_ROL <=", value, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolIn(List<Short> values) {
			addCriterion("AUD_COD_ROL in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotIn(List<Short> values) {
			addCriterion("AUD_COD_ROL not in", values, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolBetween(Short value1, Short value2) {
			addCriterion("AUD_COD_ROL between", value1, value2, "audCodRol");
			return (Criteria) this;
		}

		public Criteria andAudCodRolNotBetween(Short value1, Short value2) {
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

		public Criteria andTipoParametroIsNull() {
			addCriterion("TIPO_PARAMETRO is null");
			return (Criteria) this;
		}

		public Criteria andTipoParametroIsNotNull() {
			addCriterion("TIPO_PARAMETRO is not null");
			return (Criteria) this;
		}

		public Criteria andTipoParametroEqualTo(String value) {
			addCriterion("TIPO_PARAMETRO =", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroNotEqualTo(String value) {
			addCriterion("TIPO_PARAMETRO <>", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroGreaterThan(String value) {
			addCriterion("TIPO_PARAMETRO >", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroGreaterThanOrEqualTo(String value) {
			addCriterion("TIPO_PARAMETRO >=", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroLessThan(String value) {
			addCriterion("TIPO_PARAMETRO <", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroLessThanOrEqualTo(String value) {
			addCriterion("TIPO_PARAMETRO <=", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroLike(String value) {
			addCriterion("TIPO_PARAMETRO like", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroNotLike(String value) {
			addCriterion("TIPO_PARAMETRO not like", value, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroIn(List<String> values) {
			addCriterion("TIPO_PARAMETRO in", values, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroNotIn(List<String> values) {
			addCriterion("TIPO_PARAMETRO not in", values, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroBetween(String value1, String value2) {
			addCriterion("TIPO_PARAMETRO between", value1, value2, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andTipoParametroNotBetween(String value1, String value2) {
			addCriterion("TIPO_PARAMETRO not between", value1, value2, "tipoParametro");
			return (Criteria) this;
		}

		public Criteria andDescripcionIsNull() {
			addCriterion("DESCRIPCION is null");
			return (Criteria) this;
		}

		public Criteria andDescripcionIsNotNull() {
			addCriterion("DESCRIPCION is not null");
			return (Criteria) this;
		}

		public Criteria andDescripcionEqualTo(String value) {
			addCriterion("DESCRIPCION =", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionNotEqualTo(String value) {
			addCriterion("DESCRIPCION <>", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionGreaterThan(String value) {
			addCriterion("DESCRIPCION >", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionGreaterThanOrEqualTo(String value) {
			addCriterion("DESCRIPCION >=", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionLessThan(String value) {
			addCriterion("DESCRIPCION <", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionLessThanOrEqualTo(String value) {
			addCriterion("DESCRIPCION <=", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionLike(String value) {
			addCriterion("DESCRIPCION like", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionNotLike(String value) {
			addCriterion("DESCRIPCION not like", value, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionIn(List<String> values) {
			addCriterion("DESCRIPCION in", values, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionNotIn(List<String> values) {
			addCriterion("DESCRIPCION not in", values, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionBetween(String value1, String value2) {
			addCriterion("DESCRIPCION between", value1, value2, "descripcion");
			return (Criteria) this;
		}

		public Criteria andDescripcionNotBetween(String value1, String value2) {
			addCriterion("DESCRIPCION not between", value1, value2, "descripcion");
			return (Criteria) this;
		}

		public Criteria andFlgParticularIsNull() {
			addCriterion("FLG_PARTICULAR is null");
			return (Criteria) this;
		}

		public Criteria andFlgParticularIsNotNull() {
			addCriterion("FLG_PARTICULAR is not null");
			return (Criteria) this;
		}

		public Criteria andFlgParticularEqualTo(Short value) {
			addCriterion("FLG_PARTICULAR =", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularNotEqualTo(Short value) {
			addCriterion("FLG_PARTICULAR <>", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularGreaterThan(Short value) {
			addCriterion("FLG_PARTICULAR >", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularGreaterThanOrEqualTo(Short value) {
			addCriterion("FLG_PARTICULAR >=", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularLessThan(Short value) {
			addCriterion("FLG_PARTICULAR <", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularLessThanOrEqualTo(Short value) {
			addCriterion("FLG_PARTICULAR <=", value, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularIn(List<Short> values) {
			addCriterion("FLG_PARTICULAR in", values, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularNotIn(List<Short> values) {
			addCriterion("FLG_PARTICULAR not in", values, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularBetween(Short value1, Short value2) {
			addCriterion("FLG_PARTICULAR between", value1, value2, "flgParticular");
			return (Criteria) this;
		}

		public Criteria andFlgParticularNotBetween(Short value1, Short value2) {
			addCriterion("FLG_PARTICULAR not between", value1, value2, "flgParticular");
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
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table PARAMETRICA
	 * @mbg.generated  Thu Nov 08 08:51:42 COT 2018
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
     * This class corresponds to the database table PARAMETRICA
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 29 14:57:36 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}