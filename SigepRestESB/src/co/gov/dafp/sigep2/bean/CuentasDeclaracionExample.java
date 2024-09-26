package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CuentasDeclaracionExample {

    protected String orderByClause;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    protected boolean distinct;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to the
     * database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public CuentasDeclaracionExample() {
	oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public void setOrderByClause(String orderByClause) {
	this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public String getOrderByClause() {
	return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public void setDistinct(boolean distinct) {
	this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public boolean isDistinct() {
	return distinct;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public List<Criteria> getOredCriteria() {
	return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public void or(Criteria criteria) {
	oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public Criteria or() {
	Criteria criteria = createCriteriaInternal();
	oredCriteria.add(criteria);
	return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public Criteria createCriteria() {
	Criteria criteria = createCriteriaInternal();
	if (oredCriteria.size() == 0) {
	    oredCriteria.add(criteria);
	}
	return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    protected Criteria createCriteriaInternal() {
	Criteria criteria = new Criteria();
	return criteria;
    }

    /**
     * This method was generated by MyBatis Generator. This method corresponds to
     * the database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
     */
    public void clear() {
	oredCriteria.clear();
	orderByClause = null;
	distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the
     * database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
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

	public Criteria andCodCuentaDeclaracionIsNull() {
	    addCriterion("COD_CUENTA_DECLARACION is null");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionIsNotNull() {
	    addCriterion("COD_CUENTA_DECLARACION is not null");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionEqualTo(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION =", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionNotEqualTo(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION <>", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionGreaterThan(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION >", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionGreaterThanOrEqualTo(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION >=", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionLessThan(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION <", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionLessThanOrEqualTo(Long value) {
	    addCriterion("COD_CUENTA_DECLARACION <=", value, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionIn(List<Long> values) {
	    addCriterion("COD_CUENTA_DECLARACION in", values, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionNotIn(List<Long> values) {
	    addCriterion("COD_CUENTA_DECLARACION not in", values, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionBetween(Long value1, Long value2) {
	    addCriterion("COD_CUENTA_DECLARACION between", value1, value2, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodCuentaDeclaracionNotBetween(Long value1, Long value2) {
	    addCriterion("COD_CUENTA_DECLARACION not between", value1, value2, "codCuentaDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionIsNull() {
	    addCriterion("COD_DECLARACION is null");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionIsNotNull() {
	    addCriterion("COD_DECLARACION is not null");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionEqualTo(BigDecimal value) {
	    addCriterion("COD_DECLARACION =", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionNotEqualTo(BigDecimal value) {
	    addCriterion("COD_DECLARACION <>", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionGreaterThan(BigDecimal value) {
	    addCriterion("COD_DECLARACION >", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionGreaterThanOrEqualTo(BigDecimal value) {
	    addCriterion("COD_DECLARACION >=", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionLessThan(BigDecimal value) {
	    addCriterion("COD_DECLARACION <", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionLessThanOrEqualTo(BigDecimal value) {
	    addCriterion("COD_DECLARACION <=", value, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionIn(List<BigDecimal> values) {
	    addCriterion("COD_DECLARACION in", values, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionNotIn(List<BigDecimal> values) {
	    addCriterion("COD_DECLARACION not in", values, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionBetween(BigDecimal value1, BigDecimal value2) {
	    addCriterion("COD_DECLARACION between", value1, value2, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodDeclaracionNotBetween(BigDecimal value1, BigDecimal value2) {
	    addCriterion("COD_DECLARACION not between", value1, value2, "codDeclaracion");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaIsNull() {
	    addCriterion("COD_TIPO_CUENTA is null");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaIsNotNull() {
	    addCriterion("COD_TIPO_CUENTA is not null");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaEqualTo(Integer value) {
	    addCriterion("COD_TIPO_CUENTA =", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaNotEqualTo(Integer value) {
	    addCriterion("COD_TIPO_CUENTA <>", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaGreaterThan(Integer value) {
	    addCriterion("COD_TIPO_CUENTA >", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaGreaterThanOrEqualTo(Integer value) {
	    addCriterion("COD_TIPO_CUENTA >=", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaLessThan(Integer value) {
	    addCriterion("COD_TIPO_CUENTA <", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaLessThanOrEqualTo(Integer value) {
	    addCriterion("COD_TIPO_CUENTA <=", value, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaIn(List<Integer> values) {
	    addCriterion("COD_TIPO_CUENTA in", values, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaNotIn(List<Integer> values) {
	    addCriterion("COD_TIPO_CUENTA not in", values, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaBetween(Integer value1, Integer value2) {
	    addCriterion("COD_TIPO_CUENTA between", value1, value2, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andCodTipoCuentaNotBetween(Integer value1, Integer value2) {
	    addCriterion("COD_TIPO_CUENTA not between", value1, value2, "codTipoCuenta");
	    return (Criteria) this;
	}

	public Criteria andEntidadIsNull() {
	    addCriterion("ENTIDAD is null");
	    return (Criteria) this;
	}

	public Criteria andEntidadIsNotNull() {
	    addCriterion("ENTIDAD is not null");
	    return (Criteria) this;
	}

	public Criteria andEntidadEqualTo(String value) {
	    addCriterion("ENTIDAD =", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadNotEqualTo(String value) {
	    addCriterion("ENTIDAD <>", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadGreaterThan(String value) {
	    addCriterion("ENTIDAD >", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadGreaterThanOrEqualTo(String value) {
	    addCriterion("ENTIDAD >=", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadLessThan(String value) {
	    addCriterion("ENTIDAD <", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadLessThanOrEqualTo(String value) {
	    addCriterion("ENTIDAD <=", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadLike(String value) {
	    addCriterion("ENTIDAD like", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadNotLike(String value) {
	    addCriterion("ENTIDAD not like", value, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadIn(List<String> values) {
	    addCriterion("ENTIDAD in", values, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadNotIn(List<String> values) {
	    addCriterion("ENTIDAD not in", values, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadBetween(String value1, String value2) {
	    addCriterion("ENTIDAD between", value1, value2, "entidad");
	    return (Criteria) this;
	}

	public Criteria andEntidadNotBetween(String value1, String value2) {
	    addCriterion("ENTIDAD not between", value1, value2, "entidad");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaIsNull() {
	    addCriterion("NUMERO_CUENTA is null");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaIsNotNull() {
	    addCriterion("NUMERO_CUENTA is not null");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaEqualTo(String value) {
	    addCriterion("NUMERO_CUENTA =", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaNotEqualTo(String value) {
	    addCriterion("NUMERO_CUENTA <>", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaGreaterThan(String value) {
	    addCriterion("NUMERO_CUENTA >", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaGreaterThanOrEqualTo(String value) {
	    addCriterion("NUMERO_CUENTA >=", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaLessThan(String value) {
	    addCriterion("NUMERO_CUENTA <", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaLessThanOrEqualTo(String value) {
	    addCriterion("NUMERO_CUENTA <=", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaLike(String value) {
	    addCriterion("NUMERO_CUENTA like", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaNotLike(String value) {
	    addCriterion("NUMERO_CUENTA not like", value, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaIn(List<String> values) {
	    addCriterion("NUMERO_CUENTA in", values, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaNotIn(List<String> values) {
	    addCriterion("NUMERO_CUENTA not in", values, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaBetween(String value1, String value2) {
	    addCriterion("NUMERO_CUENTA between", value1, value2, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andNumeroCuentaNotBetween(String value1, String value2) {
	    addCriterion("NUMERO_CUENTA not between", value1, value2, "numeroCuenta");
	    return (Criteria) this;
	}

	public Criteria andSaldoIsNull() {
	    addCriterion("SALDO is null");
	    return (Criteria) this;
	}

	public Criteria andSaldoIsNotNull() {
	    addCriterion("SALDO is not null");
	    return (Criteria) this;
	}

	public Criteria andSaldoEqualTo(Long value) {
	    addCriterion("SALDO =", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoNotEqualTo(Long value) {
	    addCriterion("SALDO <>", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoGreaterThan(Long value) {
	    addCriterion("SALDO >", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoGreaterThanOrEqualTo(Long value) {
	    addCriterion("SALDO >=", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoLessThan(Long value) {
	    addCriterion("SALDO <", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoLessThanOrEqualTo(Long value) {
	    addCriterion("SALDO <=", value, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoIn(List<Long> values) {
	    addCriterion("SALDO in", values, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoNotIn(List<Long> values) {
	    addCriterion("SALDO not in", values, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoBetween(Long value1, Long value2) {
	    addCriterion("SALDO between", value1, value2, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSaldoNotBetween(Long value1, Long value2) {
	    addCriterion("SALDO not between", value1, value2, "saldo");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaIsNull() {
	    addCriterion("SEDE_CUENTA is null");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaIsNotNull() {
	    addCriterion("SEDE_CUENTA is not null");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaEqualTo(String value) {
	    addCriterion("SEDE_CUENTA =", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaNotEqualTo(String value) {
	    addCriterion("SEDE_CUENTA <>", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaGreaterThan(String value) {
	    addCriterion("SEDE_CUENTA >", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaGreaterThanOrEqualTo(String value) {
	    addCriterion("SEDE_CUENTA >=", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaLessThan(String value) {
	    addCriterion("SEDE_CUENTA <", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaLessThanOrEqualTo(String value) {
	    addCriterion("SEDE_CUENTA <=", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaLike(String value) {
	    addCriterion("SEDE_CUENTA like", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaNotLike(String value) {
	    addCriterion("SEDE_CUENTA not like", value, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaIn(List<String> values) {
	    addCriterion("SEDE_CUENTA in", values, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaNotIn(List<String> values) {
	    addCriterion("SEDE_CUENTA not in", values, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaBetween(String value1, String value2) {
	    addCriterion("SEDE_CUENTA between", value1, value2, "sedeCuenta");
	    return (Criteria) this;
	}

	public Criteria andSedeCuentaNotBetween(String value1, String value2) {
	    addCriterion("SEDE_CUENTA not between", value1, value2, "sedeCuenta");
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
    }

    /**
     * This class was generated by MyBatis Generator. This class corresponds to the
     * database table CUENTAS_DECLARACION
     * 
     * @mbg.generated Thu Mar 22 07:42:52 COT 2018
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
     * This class was generated by MyBatis Generator. This class corresponds to the
     * database table CUENTAS_DECLARACION
     *
     * @mbg.generated do_not_delete_during_merge Wed Feb 28 10:04:35 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

	protected Criteria() {
	    super();
	}
    }
}