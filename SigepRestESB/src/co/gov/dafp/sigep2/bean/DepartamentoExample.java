package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DepartamentoExample {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public DepartamentoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
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

		public Criteria andCodDepartamentoIsNull() {
			addCriterion("COD_DEPARTAMENTO is null");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoIsNotNull() {
			addCriterion("COD_DEPARTAMENTO is not null");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoEqualTo(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO =", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoNotEqualTo(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO <>", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoGreaterThan(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO >", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO >=", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoLessThan(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO <", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_DEPARTAMENTO <=", value, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoIn(List<BigDecimal> values) {
			addCriterion("COD_DEPARTAMENTO in", values, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoNotIn(List<BigDecimal> values) {
			addCriterion("COD_DEPARTAMENTO not in", values, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_DEPARTAMENTO between", value1, value2, "codDepartamento");
			return (Criteria) this;
		}

		public Criteria andCodDepartamentoNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_DEPARTAMENTO not between", value1, value2, "codDepartamento");
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

		public Criteria andCodPaisEqualTo(BigDecimal value) {
			addCriterion("COD_PAIS =", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotEqualTo(BigDecimal value) {
			addCriterion("COD_PAIS <>", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisGreaterThan(BigDecimal value) {
			addCriterion("COD_PAIS >", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PAIS >=", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisLessThan(BigDecimal value) {
			addCriterion("COD_PAIS <", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PAIS <=", value, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisIn(List<BigDecimal> values) {
			addCriterion("COD_PAIS in", values, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotIn(List<BigDecimal> values) {
			addCriterion("COD_PAIS not in", values, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PAIS between", value1, value2, "codPais");
			return (Criteria) this;
		}

		public Criteria andCodPaisNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PAIS not between", value1, value2, "codPais");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoIsNull() {
			addCriterion("NOMBRE_DEPARTAMENTO is null");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoIsNotNull() {
			addCriterion("NOMBRE_DEPARTAMENTO is not null");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoEqualTo(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO =", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoNotEqualTo(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO <>", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoGreaterThan(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO >", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO >=", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoLessThan(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO <", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoLessThanOrEqualTo(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO <=", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoLike(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO like", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoNotLike(String value) {
			addCriterion("NOMBRE_DEPARTAMENTO not like", value, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoIn(List<String> values) {
			addCriterion("NOMBRE_DEPARTAMENTO in", values, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoNotIn(List<String> values) {
			addCriterion("NOMBRE_DEPARTAMENTO not in", values, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoBetween(String value1, String value2) {
			addCriterion("NOMBRE_DEPARTAMENTO between", value1, value2, "nombreDepartamento");
			return (Criteria) this;
		}

		public Criteria andNombreDepartamentoNotBetween(String value1, String value2) {
			addCriterion("NOMBRE_DEPARTAMENTO not between", value1, value2, "nombreDepartamento");
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

		public Criteria andFlgActivoEqualTo(BigDecimal value) {
			addCriterion("FLG_ACTIVO =", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotEqualTo(BigDecimal value) {
			addCriterion("FLG_ACTIVO <>", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoGreaterThan(BigDecimal value) {
			addCriterion("FLG_ACTIVO >", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("FLG_ACTIVO >=", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoLessThan(BigDecimal value) {
			addCriterion("FLG_ACTIVO <", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoLessThanOrEqualTo(BigDecimal value) {
			addCriterion("FLG_ACTIVO <=", value, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoIn(List<BigDecimal> values) {
			addCriterion("FLG_ACTIVO in", values, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotIn(List<BigDecimal> values) {
			addCriterion("FLG_ACTIVO not in", values, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("FLG_ACTIVO between", value1, value2, "flgActivo");
			return (Criteria) this;
		}

		public Criteria andFlgActivoNotBetween(BigDecimal value1, BigDecimal value2) {
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

		public Criteria andIndicativoIsNull() {
			addCriterion("INDICATIVO is null");
			return (Criteria) this;
		}

		public Criteria andIndicativoIsNotNull() {
			addCriterion("INDICATIVO is not null");
			return (Criteria) this;
		}

		public Criteria andIndicativoEqualTo(String value) {
			addCriterion("INDICATIVO =", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoNotEqualTo(String value) {
			addCriterion("INDICATIVO <>", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoGreaterThan(String value) {
			addCriterion("INDICATIVO >", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoGreaterThanOrEqualTo(String value) {
			addCriterion("INDICATIVO >=", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoLessThan(String value) {
			addCriterion("INDICATIVO <", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoLessThanOrEqualTo(String value) {
			addCriterion("INDICATIVO <=", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoLike(String value) {
			addCriterion("INDICATIVO like", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoNotLike(String value) {
			addCriterion("INDICATIVO not like", value, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoIn(List<String> values) {
			addCriterion("INDICATIVO in", values, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoNotIn(List<String> values) {
			addCriterion("INDICATIVO not in", values, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoBetween(String value1, String value2) {
			addCriterion("INDICATIVO between", value1, value2, "indicativo");
			return (Criteria) this;
		}

		public Criteria andIndicativoNotBetween(String value1, String value2) {
			addCriterion("INDICATIVO not between", value1, value2, "indicativo");
			return (Criteria) this;
		}
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table DEPARTAMENTO
	 * @mbg.generated  Wed Feb 14 16:56:18 COT 2018
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
     * This class corresponds to the database table DEPARTAMENTO
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 29 14:57:36 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}