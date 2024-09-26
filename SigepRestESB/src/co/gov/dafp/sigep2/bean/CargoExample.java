package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CargoExample {
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public CargoExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
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

		public Criteria andCodigoDafpIsNull() {
			addCriterion("CODIGO_DAFP is null");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpIsNotNull() {
			addCriterion("CODIGO_DAFP is not null");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpEqualTo(Long value) {
			addCriterion("CODIGO_DAFP =", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpNotEqualTo(Long value) {
			addCriterion("CODIGO_DAFP <>", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpGreaterThan(Long value) {
			addCriterion("CODIGO_DAFP >", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpGreaterThanOrEqualTo(Long value) {
			addCriterion("CODIGO_DAFP >=", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpLessThan(Long value) {
			addCriterion("CODIGO_DAFP <", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpLessThanOrEqualTo(Long value) {
			addCriterion("CODIGO_DAFP <=", value, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpIn(List<Long> values) {
			addCriterion("CODIGO_DAFP in", values, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpNotIn(List<Long> values) {
			addCriterion("CODIGO_DAFP not in", values, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpBetween(Long value1, Long value2) {
			addCriterion("CODIGO_DAFP between", value1, value2, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andCodigoDafpNotBetween(Long value1, Long value2) {
			addCriterion("CODIGO_DAFP not between", value1, value2, "codigoDafp");
			return (Criteria) this;
		}

		public Criteria andNombreCargoIsNull() {
			addCriterion("NOMBRE_CARGO is null");
			return (Criteria) this;
		}

		public Criteria andNombreCargoIsNotNull() {
			addCriterion("NOMBRE_CARGO is not null");
			return (Criteria) this;
		}

		public Criteria andNombreCargoEqualTo(String value) {
			addCriterion("NOMBRE_CARGO =", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoNotEqualTo(String value) {
			addCriterion("NOMBRE_CARGO <>", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoGreaterThan(String value) {
			addCriterion("NOMBRE_CARGO >", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBRE_CARGO >=", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoLessThan(String value) {
			addCriterion("NOMBRE_CARGO <", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoLessThanOrEqualTo(String value) {
			addCriterion("NOMBRE_CARGO <=", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoLike(String value) {
			addCriterion("NOMBRE_CARGO like", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoNotLike(String value) {
			addCriterion("NOMBRE_CARGO not like", value, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoIn(List<String> values) {
			addCriterion("NOMBRE_CARGO in", values, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoNotIn(List<String> values) {
			addCriterion("NOMBRE_CARGO not in", values, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoBetween(String value1, String value2) {
			addCriterion("NOMBRE_CARGO between", value1, value2, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andNombreCargoNotBetween(String value1, String value2) {
			addCriterion("NOMBRE_CARGO not between", value1, value2, "nombreCargo");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialIsNull() {
			addCriterion("ASIGNACION_SALARIAL is null");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialIsNotNull() {
			addCriterion("ASIGNACION_SALARIAL is not null");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialEqualTo(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL =", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialNotEqualTo(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL <>", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialGreaterThan(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL >", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL >=", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialLessThan(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL <", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialLessThanOrEqualTo(BigDecimal value) {
			addCriterion("ASIGNACION_SALARIAL <=", value, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialIn(List<BigDecimal> values) {
			addCriterion("ASIGNACION_SALARIAL in", values, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialNotIn(List<BigDecimal> values) {
			addCriterion("ASIGNACION_SALARIAL not in", values, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ASIGNACION_SALARIAL between", value1, value2, "asignacionSalarial");
			return (Criteria) this;
		}

		public Criteria andAsignacionSalarialNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ASIGNACION_SALARIAL not between", value1, value2, "asignacionSalarial");
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

		public Criteria andCodGradoIsNull() {
			addCriterion("COD_GRADO is null");
			return (Criteria) this;
		}

		public Criteria andCodGradoIsNotNull() {
			addCriterion("COD_GRADO is not null");
			return (Criteria) this;
		}

		public Criteria andCodGradoEqualTo(Short value) {
			addCriterion("COD_GRADO =", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotEqualTo(Short value) {
			addCriterion("COD_GRADO <>", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoGreaterThan(Short value) {
			addCriterion("COD_GRADO >", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoGreaterThanOrEqualTo(Short value) {
			addCriterion("COD_GRADO >=", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoLessThan(Short value) {
			addCriterion("COD_GRADO <", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoLessThanOrEqualTo(Short value) {
			addCriterion("COD_GRADO <=", value, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoIn(List<Short> values) {
			addCriterion("COD_GRADO in", values, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotIn(List<Short> values) {
			addCriterion("COD_GRADO not in", values, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoBetween(Short value1, Short value2) {
			addCriterion("COD_GRADO between", value1, value2, "codGrado");
			return (Criteria) this;
		}

		public Criteria andCodGradoNotBetween(Short value1, Short value2) {
			addCriterion("COD_GRADO not between", value1, value2, "codGrado");
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

		public Criteria andCodNivelCargoEqualTo(Integer value) {
			addCriterion("COD_NIVEL_CARGO =", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotEqualTo(Integer value) {
			addCriterion("COD_NIVEL_CARGO <>", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoGreaterThan(Integer value) {
			addCriterion("COD_NIVEL_CARGO >", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoGreaterThanOrEqualTo(Integer value) {
			addCriterion("COD_NIVEL_CARGO >=", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoLessThan(Integer value) {
			addCriterion("COD_NIVEL_CARGO <", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoLessThanOrEqualTo(Integer value) {
			addCriterion("COD_NIVEL_CARGO <=", value, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoIn(List<Integer> values) {
			addCriterion("COD_NIVEL_CARGO in", values, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotIn(List<Integer> values) {
			addCriterion("COD_NIVEL_CARGO not in", values, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoBetween(Integer value1, Integer value2) {
			addCriterion("COD_NIVEL_CARGO between", value1, value2, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andCodNivelCargoNotBetween(Integer value1, Integer value2) {
			addCriterion("COD_NIVEL_CARGO not between", value1, value2, "codNivelCargo");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionIsNull() {
			addCriterion("NOMBRE_DENOMINACION is null");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionIsNotNull() {
			addCriterion("NOMBRE_DENOMINACION is not null");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionEqualTo(String value) {
			addCriterion("NOMBRE_DENOMINACION =", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionNotEqualTo(String value) {
			addCriterion("NOMBRE_DENOMINACION <>", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionGreaterThan(String value) {
			addCriterion("NOMBRE_DENOMINACION >", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionGreaterThanOrEqualTo(String value) {
			addCriterion("NOMBRE_DENOMINACION >=", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionLessThan(String value) {
			addCriterion("NOMBRE_DENOMINACION <", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionLessThanOrEqualTo(String value) {
			addCriterion("NOMBRE_DENOMINACION <=", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionLike(String value) {
			addCriterion("NOMBRE_DENOMINACION like", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionNotLike(String value) {
			addCriterion("NOMBRE_DENOMINACION not like", value, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionIn(List<String> values) {
			addCriterion("NOMBRE_DENOMINACION in", values, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionNotIn(List<String> values) {
			addCriterion("NOMBRE_DENOMINACION not in", values, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionBetween(String value1, String value2) {
			addCriterion("NOMBRE_DENOMINACION between", value1, value2, "nombreDenominacion");
			return (Criteria) this;
		}

		public Criteria andNombreDenominacionNotBetween(String value1, String value2) {
			addCriterion("NOMBRE_DENOMINACION not between", value1, value2, "nombreDenominacion");
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

		public Criteria andCodDenominacionIsNull() {
			addCriterion("COD_DENOMINACION is null");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionIsNotNull() {
			addCriterion("COD_DENOMINACION is not null");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionEqualTo(Long value) {
			addCriterion("COD_DENOMINACION =", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionNotEqualTo(Long value) {
			addCriterion("COD_DENOMINACION <>", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionGreaterThan(Long value) {
			addCriterion("COD_DENOMINACION >", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_DENOMINACION >=", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionLessThan(Long value) {
			addCriterion("COD_DENOMINACION <", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionLessThanOrEqualTo(Long value) {
			addCriterion("COD_DENOMINACION <=", value, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionIn(List<Long> values) {
			addCriterion("COD_DENOMINACION in", values, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionNotIn(List<Long> values) {
			addCriterion("COD_DENOMINACION not in", values, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionBetween(Long value1, Long value2) {
			addCriterion("COD_DENOMINACION between", value1, value2, "codDenominacion");
			return (Criteria) this;
		}

		public Criteria andCodDenominacionNotBetween(Long value1, Long value2) {
			addCriterion("COD_DENOMINACION not between", value1, value2, "codDenominacion");
			return (Criteria) this;
		}
	}
	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table CARGO
	 * @mbg.generated  Mon Jul 30 16:45:00 COT 2018
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
     * This class corresponds to the database table CARGO
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 29 14:57:36 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}