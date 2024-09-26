package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VRecursoActivoPerfilUsuarioExample {
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
	 * This field was generated by MyBatis Generator. This field corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public VRecursoActivoPerfilUsuarioExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
	 * @mbg.generated  Tue Jan 30 14:07:49 COT 2018
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
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

		public Criteria andCodRecursoIsNull() {
			addCriterion("COD_RECURSO is null");
			return (Criteria) this;
		}

		public Criteria andCodRecursoIsNotNull() {
			addCriterion("COD_RECURSO is not null");
			return (Criteria) this;
		}

		public Criteria andCodRecursoEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO =", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoNotEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO <>", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoGreaterThan(BigDecimal value) {
			addCriterion("COD_RECURSO >", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO >=", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoLessThan(BigDecimal value) {
			addCriterion("COD_RECURSO <", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO <=", value, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoIn(List<BigDecimal> values) {
			addCriterion("COD_RECURSO in", values, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoNotIn(List<BigDecimal> values) {
			addCriterion("COD_RECURSO not in", values, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_RECURSO between", value1, value2, "codRecurso");
			return (Criteria) this;
		}

		public Criteria andCodRecursoNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_RECURSO not between", value1, value2, "codRecurso");
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

		public Criteria andCodigoVentanaIsNull() {
			addCriterion("CODIGO_VENTANA is null");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaIsNotNull() {
			addCriterion("CODIGO_VENTANA is not null");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaEqualTo(String value) {
			addCriterion("CODIGO_VENTANA =", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaNotEqualTo(String value) {
			addCriterion("CODIGO_VENTANA <>", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaGreaterThan(String value) {
			addCriterion("CODIGO_VENTANA >", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaGreaterThanOrEqualTo(String value) {
			addCriterion("CODIGO_VENTANA >=", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaLessThan(String value) {
			addCriterion("CODIGO_VENTANA <", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaLessThanOrEqualTo(String value) {
			addCriterion("CODIGO_VENTANA <=", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaLike(String value) {
			addCriterion("CODIGO_VENTANA like", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaNotLike(String value) {
			addCriterion("CODIGO_VENTANA not like", value, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaIn(List<String> values) {
			addCriterion("CODIGO_VENTANA in", values, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaNotIn(List<String> values) {
			addCriterion("CODIGO_VENTANA not in", values, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaBetween(String value1, String value2) {
			addCriterion("CODIGO_VENTANA between", value1, value2, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andCodigoVentanaNotBetween(String value1, String value2) {
			addCriterion("CODIGO_VENTANA not between", value1, value2, "codigoVentana");
			return (Criteria) this;
		}

		public Criteria andOrdenIsNull() {
			addCriterion("ORDEN is null");
			return (Criteria) this;
		}

		public Criteria andOrdenIsNotNull() {
			addCriterion("ORDEN is not null");
			return (Criteria) this;
		}

		public Criteria andOrdenEqualTo(BigDecimal value) {
			addCriterion("ORDEN =", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenNotEqualTo(BigDecimal value) {
			addCriterion("ORDEN <>", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenGreaterThan(BigDecimal value) {
			addCriterion("ORDEN >", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("ORDEN >=", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenLessThan(BigDecimal value) {
			addCriterion("ORDEN <", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenLessThanOrEqualTo(BigDecimal value) {
			addCriterion("ORDEN <=", value, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenIn(List<BigDecimal> values) {
			addCriterion("ORDEN in", values, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenNotIn(List<BigDecimal> values) {
			addCriterion("ORDEN not in", values, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ORDEN between", value1, value2, "orden");
			return (Criteria) this;
		}

		public Criteria andOrdenNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("ORDEN not between", value1, value2, "orden");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreIsNull() {
			addCriterion("COD_RECURSO_PADRE is null");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreIsNotNull() {
			addCriterion("COD_RECURSO_PADRE is not null");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE =", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreNotEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE <>", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreGreaterThan(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE >", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE >=", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreLessThan(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE <", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreLessThanOrEqualTo(BigDecimal value) {
			addCriterion("COD_RECURSO_PADRE <=", value, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreIn(List<BigDecimal> values) {
			addCriterion("COD_RECURSO_PADRE in", values, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreNotIn(List<BigDecimal> values) {
			addCriterion("COD_RECURSO_PADRE not in", values, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_RECURSO_PADRE between", value1, value2, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andCodRecursoPadreNotBetween(BigDecimal value1, BigDecimal value2) {
			addCriterion("COD_RECURSO_PADRE not between", value1, value2, "codRecursoPadre");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaIsNull() {
			addCriterion("TITULO_VENTANA is null");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaIsNotNull() {
			addCriterion("TITULO_VENTANA is not null");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaEqualTo(String value) {
			addCriterion("TITULO_VENTANA =", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaNotEqualTo(String value) {
			addCriterion("TITULO_VENTANA <>", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaGreaterThan(String value) {
			addCriterion("TITULO_VENTANA >", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaGreaterThanOrEqualTo(String value) {
			addCriterion("TITULO_VENTANA >=", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaLessThan(String value) {
			addCriterion("TITULO_VENTANA <", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaLessThanOrEqualTo(String value) {
			addCriterion("TITULO_VENTANA <=", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaLike(String value) {
			addCriterion("TITULO_VENTANA like", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaNotLike(String value) {
			addCriterion("TITULO_VENTANA not like", value, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaIn(List<String> values) {
			addCriterion("TITULO_VENTANA in", values, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaNotIn(List<String> values) {
			addCriterion("TITULO_VENTANA not in", values, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaBetween(String value1, String value2) {
			addCriterion("TITULO_VENTANA between", value1, value2, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andTituloVentanaNotBetween(String value1, String value2) {
			addCriterion("TITULO_VENTANA not between", value1, value2, "tituloVentana");
			return (Criteria) this;
		}

		public Criteria andIdentificadorIsNull() {
			addCriterion("IDENTIFICADOR is null");
			return (Criteria) this;
		}

		public Criteria andIdentificadorIsNotNull() {
			addCriterion("IDENTIFICADOR is not null");
			return (Criteria) this;
		}

		public Criteria andIdentificadorEqualTo(String value) {
			addCriterion("IDENTIFICADOR =", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorNotEqualTo(String value) {
			addCriterion("IDENTIFICADOR <>", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorGreaterThan(String value) {
			addCriterion("IDENTIFICADOR >", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorGreaterThanOrEqualTo(String value) {
			addCriterion("IDENTIFICADOR >=", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorLessThan(String value) {
			addCriterion("IDENTIFICADOR <", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorLessThanOrEqualTo(String value) {
			addCriterion("IDENTIFICADOR <=", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorLike(String value) {
			addCriterion("IDENTIFICADOR like", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorNotLike(String value) {
			addCriterion("IDENTIFICADOR not like", value, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorIn(List<String> values) {
			addCriterion("IDENTIFICADOR in", values, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorNotIn(List<String> values) {
			addCriterion("IDENTIFICADOR not in", values, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorBetween(String value1, String value2) {
			addCriterion("IDENTIFICADOR between", value1, value2, "identificador");
			return (Criteria) this;
		}

		public Criteria andIdentificadorNotBetween(String value1, String value2) {
			addCriterion("IDENTIFICADOR not between", value1, value2, "identificador");
			return (Criteria) this;
		}

		public Criteria andPaginaIsNull() {
			addCriterion("PAGINA is null");
			return (Criteria) this;
		}

		public Criteria andPaginaIsNotNull() {
			addCriterion("PAGINA is not null");
			return (Criteria) this;
		}

		public Criteria andPaginaEqualTo(String value) {
			addCriterion("PAGINA =", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaNotEqualTo(String value) {
			addCriterion("PAGINA <>", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaGreaterThan(String value) {
			addCriterion("PAGINA >", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaGreaterThanOrEqualTo(String value) {
			addCriterion("PAGINA >=", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaLessThan(String value) {
			addCriterion("PAGINA <", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaLessThanOrEqualTo(String value) {
			addCriterion("PAGINA <=", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaLike(String value) {
			addCriterion("PAGINA like", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaNotLike(String value) {
			addCriterion("PAGINA not like", value, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaIn(List<String> values) {
			addCriterion("PAGINA in", values, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaNotIn(List<String> values) {
			addCriterion("PAGINA not in", values, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaBetween(String value1, String value2) {
			addCriterion("PAGINA between", value1, value2, "pagina");
			return (Criteria) this;
		}

		public Criteria andPaginaNotBetween(String value1, String value2) {
			addCriterion("PAGINA not between", value1, value2, "pagina");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuIsNull() {
			addCriterion("MOSTRAR_EN_MENU is null");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuIsNotNull() {
			addCriterion("MOSTRAR_EN_MENU is not null");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuEqualTo(Short value) {
			addCriterion("MOSTRAR_EN_MENU =", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuNotEqualTo(Short value) {
			addCriterion("MOSTRAR_EN_MENU <>", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuGreaterThan(Short value) {
			addCriterion("MOSTRAR_EN_MENU >", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuGreaterThanOrEqualTo(Short value) {
			addCriterion("MOSTRAR_EN_MENU >=", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuLessThan(Short value) {
			addCriterion("MOSTRAR_EN_MENU <", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuLessThanOrEqualTo(Short value) {
			addCriterion("MOSTRAR_EN_MENU <=", value, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuIn(List<Short> values) {
			addCriterion("MOSTRAR_EN_MENU in", values, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuNotIn(List<Short> values) {
			addCriterion("MOSTRAR_EN_MENU not in", values, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuBetween(Short value1, Short value2) {
			addCriterion("MOSTRAR_EN_MENU between", value1, value2, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andMostrarEnMenuNotBetween(Short value1, Short value2) {
			addCriterion("MOSTRAR_EN_MENU not between", value1, value2, "mostrarEnMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuIsNull() {
			addCriterion("ICONO_MENU is null");
			return (Criteria) this;
		}

		public Criteria andIconoMenuIsNotNull() {
			addCriterion("ICONO_MENU is not null");
			return (Criteria) this;
		}

		public Criteria andIconoMenuEqualTo(String value) {
			addCriterion("ICONO_MENU =", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuNotEqualTo(String value) {
			addCriterion("ICONO_MENU <>", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuGreaterThan(String value) {
			addCriterion("ICONO_MENU >", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuGreaterThanOrEqualTo(String value) {
			addCriterion("ICONO_MENU >=", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuLessThan(String value) {
			addCriterion("ICONO_MENU <", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuLessThanOrEqualTo(String value) {
			addCriterion("ICONO_MENU <=", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuLike(String value) {
			addCriterion("ICONO_MENU like", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuNotLike(String value) {
			addCriterion("ICONO_MENU not like", value, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuIn(List<String> values) {
			addCriterion("ICONO_MENU in", values, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuNotIn(List<String> values) {
			addCriterion("ICONO_MENU not in", values, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuBetween(String value1, String value2) {
			addCriterion("ICONO_MENU between", value1, value2, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andIconoMenuNotBetween(String value1, String value2) {
			addCriterion("ICONO_MENU not between", value1, value2, "iconoMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuIsNull() {
			addCriterion("IMAGEN_MENU is null");
			return (Criteria) this;
		}

		public Criteria andImagenMenuIsNotNull() {
			addCriterion("IMAGEN_MENU is not null");
			return (Criteria) this;
		}

		public Criteria andImagenMenuEqualTo(String value) {
			addCriterion("IMAGEN_MENU =", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuNotEqualTo(String value) {
			addCriterion("IMAGEN_MENU <>", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuGreaterThan(String value) {
			addCriterion("IMAGEN_MENU >", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuGreaterThanOrEqualTo(String value) {
			addCriterion("IMAGEN_MENU >=", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuLessThan(String value) {
			addCriterion("IMAGEN_MENU <", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuLessThanOrEqualTo(String value) {
			addCriterion("IMAGEN_MENU <=", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuLike(String value) {
			addCriterion("IMAGEN_MENU like", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuNotLike(String value) {
			addCriterion("IMAGEN_MENU not like", value, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuIn(List<String> values) {
			addCriterion("IMAGEN_MENU in", values, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuNotIn(List<String> values) {
			addCriterion("IMAGEN_MENU not in", values, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuBetween(String value1, String value2) {
			addCriterion("IMAGEN_MENU between", value1, value2, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andImagenMenuNotBetween(String value1, String value2) {
			addCriterion("IMAGEN_MENU not between", value1, value2, "imagenMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuIsNull() {
			addCriterion("COLOR_MENU is null");
			return (Criteria) this;
		}

		public Criteria andColorMenuIsNotNull() {
			addCriterion("COLOR_MENU is not null");
			return (Criteria) this;
		}

		public Criteria andColorMenuEqualTo(String value) {
			addCriterion("COLOR_MENU =", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuNotEqualTo(String value) {
			addCriterion("COLOR_MENU <>", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuGreaterThan(String value) {
			addCriterion("COLOR_MENU >", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuGreaterThanOrEqualTo(String value) {
			addCriterion("COLOR_MENU >=", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuLessThan(String value) {
			addCriterion("COLOR_MENU <", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuLessThanOrEqualTo(String value) {
			addCriterion("COLOR_MENU <=", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuLike(String value) {
			addCriterion("COLOR_MENU like", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuNotLike(String value) {
			addCriterion("COLOR_MENU not like", value, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuIn(List<String> values) {
			addCriterion("COLOR_MENU in", values, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuNotIn(List<String> values) {
			addCriterion("COLOR_MENU not in", values, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuBetween(String value1, String value2) {
			addCriterion("COLOR_MENU between", value1, value2, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andColorMenuNotBetween(String value1, String value2) {
			addCriterion("COLOR_MENU not between", value1, value2, "colorMenu");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoIsNull() {
			addCriterion("FLG_BLOQUEADO is null");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoIsNotNull() {
			addCriterion("FLG_BLOQUEADO is not null");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoEqualTo(Short value) {
			addCriterion("FLG_BLOQUEADO =", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoNotEqualTo(Short value) {
			addCriterion("FLG_BLOQUEADO <>", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoGreaterThan(Short value) {
			addCriterion("FLG_BLOQUEADO >", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoGreaterThanOrEqualTo(Short value) {
			addCriterion("FLG_BLOQUEADO >=", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoLessThan(Short value) {
			addCriterion("FLG_BLOQUEADO <", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoLessThanOrEqualTo(Short value) {
			addCriterion("FLG_BLOQUEADO <=", value, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoIn(List<Short> values) {
			addCriterion("FLG_BLOQUEADO in", values, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoNotIn(List<Short> values) {
			addCriterion("FLG_BLOQUEADO not in", values, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoBetween(Short value1, Short value2) {
			addCriterion("FLG_BLOQUEADO between", value1, value2, "flgBloqueado");
			return (Criteria) this;
		}

		public Criteria andFlgBloqueadoNotBetween(Short value1, Short value2) {
			addCriterion("FLG_BLOQUEADO not between", value1, value2, "flgBloqueado");
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

		public Criteria andLoginIsNull() {
			addCriterion("LOGIN is null");
			return (Criteria) this;
		}

		public Criteria andLoginIsNotNull() {
			addCriterion("LOGIN is not null");
			return (Criteria) this;
		}

		public Criteria andLoginEqualTo(Short value) {
			addCriterion("LOGIN =", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginNotEqualTo(Short value) {
			addCriterion("LOGIN <>", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginGreaterThan(Short value) {
			addCriterion("LOGIN >", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginGreaterThanOrEqualTo(Short value) {
			addCriterion("LOGIN >=", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginLessThan(Short value) {
			addCriterion("LOGIN <", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginLessThanOrEqualTo(Short value) {
			addCriterion("LOGIN <=", value, "login");
			return (Criteria) this;
		}

		public Criteria andLoginIn(List<Short> values) {
			addCriterion("LOGIN in", values, "login");
			return (Criteria) this;
		}

		public Criteria andLoginNotIn(List<Short> values) {
			addCriterion("LOGIN not in", values, "login");
			return (Criteria) this;
		}

		public Criteria andLoginBetween(Short value1, Short value2) {
			addCriterion("LOGIN between", value1, value2, "login");
			return (Criteria) this;
		}

		public Criteria andLoginNotBetween(Short value1, Short value2) {
			addCriterion("LOGIN not between", value1, value2, "login");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
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
     * This class corresponds to the database table V_RECURSO_ACTIVO_PERFIL_USUARIO
     *
     * @mbg.generated do_not_delete_during_merge Tue Jan 30 10:29:13 COT 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}