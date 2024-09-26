package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalidadDeDatosExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public CalidadDeDatosExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
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
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
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

        public Criteria andCodCalidadDeDatosIsNull() {
            addCriterion("COD_CALIDAD_DE_DATOS is null");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosIsNotNull() {
            addCriterion("COD_CALIDAD_DE_DATOS is not null");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosEqualTo(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS =", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosNotEqualTo(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS <>", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosGreaterThan(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS >", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS >=", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosLessThan(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS <", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COD_CALIDAD_DE_DATOS <=", value, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosIn(List<BigDecimal> values) {
            addCriterion("COD_CALIDAD_DE_DATOS in", values, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosNotIn(List<BigDecimal> values) {
            addCriterion("COD_CALIDAD_DE_DATOS not in", values, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COD_CALIDAD_DE_DATOS between", value1, value2, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andCodCalidadDeDatosNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COD_CALIDAD_DE_DATOS not between", value1, value2, "codCalidadDeDatos");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenIsNull() {
            addCriterion("DATO_ORIGEN is null");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenIsNotNull() {
            addCriterion("DATO_ORIGEN is not null");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenEqualTo(String value) {
            addCriterion("DATO_ORIGEN =", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenNotEqualTo(String value) {
            addCriterion("DATO_ORIGEN <>", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenGreaterThan(String value) {
            addCriterion("DATO_ORIGEN >", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenGreaterThanOrEqualTo(String value) {
            addCriterion("DATO_ORIGEN >=", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenLessThan(String value) {
            addCriterion("DATO_ORIGEN <", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenLessThanOrEqualTo(String value) {
            addCriterion("DATO_ORIGEN <=", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenLike(String value) {
            addCriterion("DATO_ORIGEN like", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenNotLike(String value) {
            addCriterion("DATO_ORIGEN not like", value, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenIn(List<String> values) {
            addCriterion("DATO_ORIGEN in", values, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenNotIn(List<String> values) {
            addCriterion("DATO_ORIGEN not in", values, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenBetween(String value1, String value2) {
            addCriterion("DATO_ORIGEN between", value1, value2, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoOrigenNotBetween(String value1, String value2) {
            addCriterion("DATO_ORIGEN not between", value1, value2, "datoOrigen");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoIsNull() {
            addCriterion("DATO_DESTINO is null");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoIsNotNull() {
            addCriterion("DATO_DESTINO is not null");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoEqualTo(String value) {
            addCriterion("DATO_DESTINO =", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoNotEqualTo(String value) {
            addCriterion("DATO_DESTINO <>", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoGreaterThan(String value) {
            addCriterion("DATO_DESTINO >", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoGreaterThanOrEqualTo(String value) {
            addCriterion("DATO_DESTINO >=", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoLessThan(String value) {
            addCriterion("DATO_DESTINO <", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoLessThanOrEqualTo(String value) {
            addCriterion("DATO_DESTINO <=", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoLike(String value) {
            addCriterion("DATO_DESTINO like", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoNotLike(String value) {
            addCriterion("DATO_DESTINO not like", value, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoIn(List<String> values) {
            addCriterion("DATO_DESTINO in", values, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoNotIn(List<String> values) {
            addCriterion("DATO_DESTINO not in", values, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoBetween(String value1, String value2) {
            addCriterion("DATO_DESTINO between", value1, value2, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andDatoDestinoNotBetween(String value1, String value2) {
            addCriterion("DATO_DESTINO not between", value1, value2, "datoDestino");
            return (Criteria) this;
        }

        public Criteria andTipoIsNull() {
            addCriterion("TIPO is null");
            return (Criteria) this;
        }

        public Criteria andTipoIsNotNull() {
            addCriterion("TIPO is not null");
            return (Criteria) this;
        }

        public Criteria andTipoEqualTo(String value) {
            addCriterion("TIPO =", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotEqualTo(String value) {
            addCriterion("TIPO <>", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoGreaterThan(String value) {
            addCriterion("TIPO >", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoGreaterThanOrEqualTo(String value) {
            addCriterion("TIPO >=", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoLessThan(String value) {
            addCriterion("TIPO <", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoLessThanOrEqualTo(String value) {
            addCriterion("TIPO <=", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoLike(String value) {
            addCriterion("TIPO like", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotLike(String value) {
            addCriterion("TIPO not like", value, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoIn(List<String> values) {
            addCriterion("TIPO in", values, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotIn(List<String> values) {
            addCriterion("TIPO not in", values, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoBetween(String value1, String value2) {
            addCriterion("TIPO between", value1, value2, "tipo");
            return (Criteria) this;
        }

        public Criteria andTipoNotBetween(String value1, String value2) {
            addCriterion("TIPO not between", value1, value2, "tipo");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated do_not_delete_during_merge Fri Nov 12 20:20:17 COT 2021
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table CALIDAD_DE_DATOS
     *
     * @mbg.generated Fri Nov 12 20:20:17 COT 2021
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