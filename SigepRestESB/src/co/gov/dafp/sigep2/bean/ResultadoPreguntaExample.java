package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultadoPreguntaExample {
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
	 * This field was generated by MyBatis Generator. This field corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public ResultadoPreguntaExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
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

		public Criteria andCodResultadoPreguntaIsNull() {
			addCriterion("COD_RESULTADO_PREGUNTA is null");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaIsNotNull() {
			addCriterion("COD_RESULTADO_PREGUNTA is not null");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaEqualTo(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA =", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaNotEqualTo(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA <>", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaGreaterThan(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA >", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaGreaterThanOrEqualTo(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA >=", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaLessThan(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA <", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaLessThanOrEqualTo(Long value) {
			addCriterion("COD_RESULTADO_PREGUNTA <=", value, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaIn(List<Long> values) {
			addCriterion("COD_RESULTADO_PREGUNTA in", values, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaNotIn(List<Long> values) {
			addCriterion("COD_RESULTADO_PREGUNTA not in", values, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaBetween(Long value1, Long value2) {
			addCriterion("COD_RESULTADO_PREGUNTA between", value1, value2, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodResultadoPreguntaNotBetween(Long value1, Long value2) {
			addCriterion("COD_RESULTADO_PREGUNTA not between", value1, value2, "codResultadoPregunta");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionIsNull() {
			addCriterion("COD_PREGUNTA_OPINION is null");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionIsNotNull() {
			addCriterion("COD_PREGUNTA_OPINION is not null");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionEqualTo(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION =", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionNotEqualTo(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION <>", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionGreaterThan(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION >", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionGreaterThanOrEqualTo(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION >=", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionLessThan(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION <", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionLessThanOrEqualTo(Integer value) {
			addCriterion("COD_PREGUNTA_OPINION <=", value, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionIn(List<Integer> values) {
			addCriterion("COD_PREGUNTA_OPINION in", values, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionNotIn(List<Integer> values) {
			addCriterion("COD_PREGUNTA_OPINION not in", values, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionBetween(Integer value1, Integer value2) {
			addCriterion("COD_PREGUNTA_OPINION between", value1, value2, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPreguntaOpinionNotBetween(Integer value1, Integer value2) {
			addCriterion("COD_PREGUNTA_OPINION not between", value1, value2, "codPreguntaOpinion");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIsNull() {
			addCriterion("COD_PERSONA is null");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIsNotNull() {
			addCriterion("COD_PERSONA is not null");
			return (Criteria) this;
		}

		public Criteria andCodPersonaEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA =", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA <>", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaGreaterThan(BigDecimal value) {
			addCriterion("COD_PERSONA >", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA >=", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaLessThan(BigDecimal value) {
			addCriterion("COD_PERSONA <", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_PERSONA <=", value, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaIn(List<BigDecimal> values) {
			addCriterion("COD_PERSONA in", values, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotIn(List<BigDecimal> values) {
			addCriterion("COD_PERSONA not in", values, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PERSONA between", value1, value2, "codPersona");
			return (Criteria) this;
		}

		public Criteria andCodPersonaNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_PERSONA not between", value1, value2, "codPersona");
			return (Criteria) this;
		}

		public Criteria andPuntajeIsNull() {
			addCriterion("PUNTAJE is null");
			return (Criteria) this;
		}

		public Criteria andPuntajeIsNotNull() {
			addCriterion("PUNTAJE is not null");
			return (Criteria) this;
		}

		public Criteria andPuntajeEqualTo(Integer value) {
			addCriterion("PUNTAJE =", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeNotEqualTo(Integer value) {
			addCriterion("PUNTAJE <>", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeGreaterThan(Integer value) {
			addCriterion("PUNTAJE >", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeGreaterThanOrEqualTo(Integer value) {
			addCriterion("PUNTAJE >=", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeLessThan(Integer value) {
			addCriterion("PUNTAJE <", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeLessThanOrEqualTo(Integer value) {
			addCriterion("PUNTAJE <=", value, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeIn(List<Integer> values) {
			addCriterion("PUNTAJE in", values, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeNotIn(List<Integer> values) {
			addCriterion("PUNTAJE not in", values, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeBetween(Integer value1, Integer value2) {
			addCriterion("PUNTAJE between", value1, value2, "puntaje");
			return (Criteria) this;
		}

		public Criteria andPuntajeNotBetween(Integer value1, Integer value2) {
			addCriterion("PUNTAJE not between", value1, value2, "puntaje");
			return (Criteria) this;
		}

		public Criteria andComentarioIsNull() {
			addCriterion("COMENTARIO is null");
			return (Criteria) this;
		}

		public Criteria andComentarioIsNotNull() {
			addCriterion("COMENTARIO is not null");
			return (Criteria) this;
		}

		public Criteria andComentarioEqualTo(String value) {
			addCriterion("COMENTARIO =", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioNotEqualTo(String value) {
			addCriterion("COMENTARIO <>", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioGreaterThan(String value) {
			addCriterion("COMENTARIO >", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioGreaterThanOrEqualTo(String value) {
			addCriterion("COMENTARIO >=", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioLessThan(String value) {
			addCriterion("COMENTARIO <", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioLessThanOrEqualTo(String value) {
			addCriterion("COMENTARIO <=", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioLike(String value) {
			addCriterion("COMENTARIO like", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioNotLike(String value) {
			addCriterion("COMENTARIO not like", value, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioIn(List<String> values) {
			addCriterion("COMENTARIO in", values, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioNotIn(List<String> values) {
			addCriterion("COMENTARIO not in", values, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioBetween(String value1, String value2) {
			addCriterion("COMENTARIO between", value1, value2, "comentario");
			return (Criteria) this;
		}

		public Criteria andComentarioNotBetween(String value1, String value2) {
			addCriterion("COMENTARIO not between", value1, value2, "comentario");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table RESULTADO_PREGUNTA
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
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
     * This class corresponds to the database table RESULTADO_PREGUNTA
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 29 14:57:36 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}