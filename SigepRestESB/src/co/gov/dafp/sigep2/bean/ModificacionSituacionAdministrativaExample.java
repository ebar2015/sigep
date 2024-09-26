package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModificacionSituacionAdministrativaExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public ModificacionSituacionAdministrativaExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
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

        public Criteria andCodModificacionIsNull() {
            addCriterion("COD_MODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andCodModificacionIsNotNull() {
            addCriterion("COD_MODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andCodModificacionEqualTo(Long value) {
            addCriterion("COD_MODIFICACION =", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionNotEqualTo(Long value) {
            addCriterion("COD_MODIFICACION <>", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionGreaterThan(Long value) {
            addCriterion("COD_MODIFICACION >", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionGreaterThanOrEqualTo(Long value) {
            addCriterion("COD_MODIFICACION >=", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionLessThan(Long value) {
            addCriterion("COD_MODIFICACION <", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionLessThanOrEqualTo(Long value) {
            addCriterion("COD_MODIFICACION <=", value, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionIn(List<Long> values) {
            addCriterion("COD_MODIFICACION in", values, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionNotIn(List<Long> values) {
            addCriterion("COD_MODIFICACION not in", values, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionBetween(Long value1, Long value2) {
            addCriterion("COD_MODIFICACION between", value1, value2, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodModificacionNotBetween(Long value1, Long value2) {
            addCriterion("COD_MODIFICACION not between", value1, value2, "codModificacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionIsNull() {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION is null");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionIsNotNull() {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION is not null");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionEqualTo(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION =", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionNotEqualTo(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION <>", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionGreaterThan(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION >", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionGreaterThanOrEqualTo(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION >=", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionLessThan(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION <", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionLessThanOrEqualTo(Long value) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION <=", value, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionIn(List<Long> values) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION in", values, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionNotIn(List<Long> values) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION not in", values, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionBetween(Long value1, Long value2) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION between", value1, value2, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andCodSituacionAdministrativaVinculacionNotBetween(Long value1, Long value2) {
            addCriterion("COD_SITUACION_ADMINISTRATIVA_VINCULACION not between", value1, value2, "codSituacionAdministrativaVinculacion");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaIsNull() {
            addCriterion("FECHA_INICIAL_PRORROGA is null");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaIsNotNull() {
            addCriterion("FECHA_INICIAL_PRORROGA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaEqualTo(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA =", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaNotEqualTo(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA <>", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaGreaterThan(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA >", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA >=", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaLessThan(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA <", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_INICIAL_PRORROGA <=", value, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaIn(List<Date> values) {
            addCriterion("FECHA_INICIAL_PRORROGA in", values, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaNotIn(List<Date> values) {
            addCriterion("FECHA_INICIAL_PRORROGA not in", values, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaBetween(Date value1, Date value2) {
            addCriterion("FECHA_INICIAL_PRORROGA between", value1, value2, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaInicialProrrogaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_INICIAL_PRORROGA not between", value1, value2, "fechaInicialProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaIsNull() {
            addCriterion("FECHA_FIN_PRORROGA is null");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaIsNotNull() {
            addCriterion("FECHA_FIN_PRORROGA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaEqualTo(Date value) {
            addCriterion("FECHA_FIN_PRORROGA =", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaNotEqualTo(Date value) {
            addCriterion("FECHA_FIN_PRORROGA <>", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaGreaterThan(Date value) {
            addCriterion("FECHA_FIN_PRORROGA >", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_FIN_PRORROGA >=", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaLessThan(Date value) {
            addCriterion("FECHA_FIN_PRORROGA <", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_FIN_PRORROGA <=", value, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaIn(List<Date> values) {
            addCriterion("FECHA_FIN_PRORROGA in", values, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaNotIn(List<Date> values) {
            addCriterion("FECHA_FIN_PRORROGA not in", values, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaBetween(Date value1, Date value2) {
            addCriterion("FECHA_FIN_PRORROGA between", value1, value2, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaFinProrrogaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_FIN_PRORROGA not between", value1, value2, "fechaFinProrroga");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionIsNull() {
            addCriterion("DESCRIPCION_MODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionIsNotNull() {
            addCriterion("DESCRIPCION_MODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionEqualTo(String value) {
            addCriterion("DESCRIPCION_MODIFICACION =", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionNotEqualTo(String value) {
            addCriterion("DESCRIPCION_MODIFICACION <>", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionGreaterThan(String value) {
            addCriterion("DESCRIPCION_MODIFICACION >", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION_MODIFICACION >=", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionLessThan(String value) {
            addCriterion("DESCRIPCION_MODIFICACION <", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPCION_MODIFICACION <=", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionLike(String value) {
            addCriterion("DESCRIPCION_MODIFICACION like", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionNotLike(String value) {
            addCriterion("DESCRIPCION_MODIFICACION not like", value, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionIn(List<String> values) {
            addCriterion("DESCRIPCION_MODIFICACION in", values, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionNotIn(List<String> values) {
            addCriterion("DESCRIPCION_MODIFICACION not in", values, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionBetween(String value1, String value2) {
            addCriterion("DESCRIPCION_MODIFICACION between", value1, value2, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andDescripcionModificacionNotBetween(String value1, String value2) {
            addCriterion("DESCRIPCION_MODIFICACION not between", value1, value2, "descripcionModificacion");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaIsNull() {
            addCriterion("FECHA_INGRESO_PRORROGA is null");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaIsNotNull() {
            addCriterion("FECHA_INGRESO_PRORROGA is not null");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaEqualTo(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA =", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaNotEqualTo(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA <>", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaGreaterThan(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA >", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaGreaterThanOrEqualTo(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA >=", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaLessThan(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA <", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaLessThanOrEqualTo(Date value) {
            addCriterion("FECHA_INGRESO_PRORROGA <=", value, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaIn(List<Date> values) {
            addCriterion("FECHA_INGRESO_PRORROGA in", values, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaNotIn(List<Date> values) {
            addCriterion("FECHA_INGRESO_PRORROGA not in", values, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaBetween(Date value1, Date value2) {
            addCriterion("FECHA_INGRESO_PRORROGA between", value1, value2, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andFechaIngresoProrrogaNotBetween(Date value1, Date value2) {
            addCriterion("FECHA_INGRESO_PRORROGA not between", value1, value2, "fechaIngresoProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaIsNull() {
            addCriterion("USUARIO_PRORROGA is null");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaIsNotNull() {
            addCriterion("USUARIO_PRORROGA is not null");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaEqualTo(Long value) {
            addCriterion("USUARIO_PRORROGA =", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaNotEqualTo(Long value) {
            addCriterion("USUARIO_PRORROGA <>", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaGreaterThan(Long value) {
            addCriterion("USUARIO_PRORROGA >", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaGreaterThanOrEqualTo(Long value) {
            addCriterion("USUARIO_PRORROGA >=", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaLessThan(Long value) {
            addCriterion("USUARIO_PRORROGA <", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaLessThanOrEqualTo(Long value) {
            addCriterion("USUARIO_PRORROGA <=", value, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaIn(List<Long> values) {
            addCriterion("USUARIO_PRORROGA in", values, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaNotIn(List<Long> values) {
            addCriterion("USUARIO_PRORROGA not in", values, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaBetween(Long value1, Long value2) {
            addCriterion("USUARIO_PRORROGA between", value1, value2, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andUsuarioProrrogaNotBetween(Long value1, Long value2) {
            addCriterion("USUARIO_PRORROGA not between", value1, value2, "usuarioProrroga");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionIsNull() {
            addCriterion("AUD_FECHA_MODIFICACION is null");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionIsNotNull() {
            addCriterion("AUD_FECHA_MODIFICACION is not null");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionEqualTo(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION =", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionNotEqualTo(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION <>", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionGreaterThan(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION >", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionGreaterThanOrEqualTo(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION >=", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionLessThan(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION <", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionLessThanOrEqualTo(Date value) {
            addCriterion("AUD_FECHA_MODIFICACION <=", value, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionIn(List<Date> values) {
            addCriterion("AUD_FECHA_MODIFICACION in", values, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionNotIn(List<Date> values) {
            addCriterion("AUD_FECHA_MODIFICACION not in", values, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionBetween(Date value1, Date value2) {
            addCriterion("AUD_FECHA_MODIFICACION between", value1, value2, "audFechaModificacion");
            return (Criteria) this;
        }

        public Criteria andAudFechaModificacionNotBetween(Date value1, Date value2) {
            addCriterion("AUD_FECHA_MODIFICACION not between", value1, value2, "audFechaModificacion");
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
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated do_not_delete_during_merge Mon Apr 30 14:47:40 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table MODIFICACION_SITUACION_ADMINISTRATIVA
     *
     * @mbg.generated Mon Apr 30 14:47:40 COT 2018
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
}