package co.gov.dafp.sigep2.factoria;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entity.EntidadBase;
import co.gov.dafp.sigep2.entity.ParametricaDTO;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;

/**
 *
 * @author Jhon De Avila
 *
 * @param <T>
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public abstract class AbstractFactory<T extends Serializable> implements Serializable {
	transient Logger logger;

	private static final long serialVersionUID = 3877511198151883151L;
	protected static final int FIRST_RESULT = 0;
	protected static final int TOP_ONE_RESULT = 1;

	protected static int rowsCount = 0;

	protected final Class<T> clazz;

	transient Map<String, Object> parameters = new HashMap<>();

	@EJB
	private ParametricaFactoria parametricaFactoria;

	@EJB
	private UsuarioFactoria usuarioFactoria;

	/**
	 * Manejador entidades
	 */
	@PersistenceContext(unitName = "sigep2-persistence-unit")
	transient EntityManager entityManager;

	public AbstractFactory(Class<T> clazz) {
		this.clazz = clazz;
		this.logger = Logger.getInstance(this.getClass());
	}

	public int getRowsCount() {
		int rowsCount = AbstractFactory.rowsCount;
		setRowsCount(0);
		return rowsCount;
	}

	/**
	 * Búsqueda por defecto del manejador de entidades por llave primaria
	 * 
	 * @param id
	 * @return T
	 */
	public T find(long id) {
		T t = entityManager.find(clazz, id);
		if (t != null) {
			this.refresh(t);
		}
		logger.log().debug("find(Object id)", "Encontrado " + t);
		return t;
	}

	@SuppressWarnings("unused")
	private List<T> findAll() {
		final CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		List<T> lista = entityManager.createQuery(criteriaQuery).getResultList();
		logger.log().debug("findAll()", lista.size() + " registros devueltos");
		return lista;

	}

	public T merge(final T entity, Usuario usuarioId) throws SIGEP2SistemaException {
		try {
			boolean persistido = true;
			T entityMerged = null;
			if (((EntidadBase) entity).getId() == 0) {
				entityMerged = this.persist(entity, usuarioId);
				persistido = false;
			}
			if (persistido) {
				if (usuarioId == null) {
					usuarioId = usuarioFactoria.getUsuarioSistema();
				}
				if (usuarioId != null) {
					((EntidadBase) entity).setAudFechaActualizacion(DateUtils.getFechaSistema());
					((EntidadBase) entity).setAudCodUsuario(usuarioId.getId());
					((EntidadBase) entity).setAudCodRol(usuarioId.getCodRol());
					((EntidadBase) entity).setAudAccion(Long.valueOf(ParametricaDTO.AUDITORIA_UPDATE));
				} else {
					throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
				}
				entityMerged = this.merge(entity);
			}
			return entityMerged;
		} catch (Exception e) {
			logger.log().error("T merge(final T entity, Usuario usuarioId)", e);
			throw new SIGEP2SistemaException(e);
		}
	}

	public T merge(final T entity, Usuario usuarioId, String accion) throws SIGEP2SistemaException {
		try {
			boolean persistido = true;
			T entityMerged = null;
			if (((EntidadBase) entity).getId() == 0) {
				entityMerged = this.persist(entity, usuarioId);
				persistido = false;
			}
			if (persistido) {
				if (usuarioId == null) {
					usuarioId = usuarioFactoria.getUsuarioSistema();
				}
				if (usuarioId != null) {
					((EntidadBase) entity).setAudFechaActualizacion(DateUtils.getFechaSistema());
					((EntidadBase) entity).setAudCodUsuario(usuarioId.getId());
					((EntidadBase) entity).setAudCodRol(usuarioId.getCodRol());

					if (accion != null) {
						((EntidadBase) entity).setAudAccion(Long.valueOf(accion));
					} else {
						((EntidadBase) entity).setAudAccion(new Long(0));
					}
				} else {
					throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
				}
				entityMerged = this.merge(entity);
			}
			return entityMerged;
		} catch (Exception e) {
			logger.log().error("T merge(final T entity, Usuario usuarioId)", e);
			throw new SIGEP2SistemaException(e);
		}
	}

	private T merge(final T entity) throws SIGEP2SistemaException {
		try {
			((EntidadBase) entity).setAudFechaActualizacion(DateUtils.getFechaSistema());
			return entityManager.merge(entity);
		} catch (Exception e) {
			logger.log().error("T merge(final T entity)", e);
			throw new SIGEP2SistemaException(e);
		} finally {
			this.entityManager.flush();
			logger.log().debug("T merge(final T entity)", "Entidad actualizada : " + entity);
		}
	}

	private T persist(final T entity) throws SIGEP2SistemaException {
		String msg = "T persist(final T entity)";
		try {
			this.entityManager.persist(entity);
			return entity;
		} catch (Exception e) {
			logger.log().error(msg, e.getMessage());
			throw new SIGEP2SistemaException(e);
		} finally {
			this.entityManager.flush();
			logger.log().debug(msg, "Entidad creada : " + entity);
		}
	}

	public T persist(final T entity, Usuario usuarioId) throws SIGEP2SistemaException {
		try {
			if (usuarioId == null) {
				usuarioId = usuarioFactoria.getUsuarioSistema();
			}
			if (usuarioId != null) {
				((EntidadBase) entity).setAudFechaActualizacion(DateUtils.getFechaSistema());
				((EntidadBase) entity).setAudCodUsuario(usuarioId.getId());
				((EntidadBase) entity).setAudCodRol(usuarioId.getCodRol());
				((EntidadBase) entity).setAudAccion(Long.valueOf(ParametricaDTO.AUDITORIA_INSERT));
			} else {
				throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
			}
			T entityPersisted = this.persist(entity);
			return entityPersisted;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log().error("T persist(final T entity)", e);
			throw new SIGEP2SistemaException(e);
		}
	}

	public void persistAutoCommit(final T entity, Usuario usuarioId) {
		String msg = "void persistAutoCommit(final T entity, Usuario usuarioId)";
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			entityManager.persist(entity);
			// AbstractFactory.logAuditoria(entityManager, entity, usuarioId,
			// false);
			tx.commit();
			logger.log().debug(msg, "Entidad creada : " + entity);
		} catch (RuntimeException ex) {
			if (tx.isActive()) {
				tx.rollback();
			}
			logger.log().error(msg, "Entidad abortada : " + entity);
			throw ex;
		} finally {
			entityManager.close();
			logger.log().error(msg, "Conexión cerrada correctamente");
		}
	}

	public void remove(final T entity, Usuario usuarioId) throws SIGEP2SistemaException {
		if (usuarioId == null) {
			usuarioId = usuarioFactoria.getUsuarioSistema();
		}
		if (usuarioId != null) {
			((EntidadBase) entity).setAudFechaActualizacion(DateUtils.getFechaSistema());
			((EntidadBase) entity).setAudCodUsuario(usuarioId.getId());
			((EntidadBase) entity).setAudCodRol(usuarioId.getCodRol());
			((EntidadBase) entity).setAudAccion(Long.valueOf(ParametricaDTO.AUDITORIA_DELETE));
		} else {
			throw new SIGEP2SistemaException(MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
		}
		remove(entity);
		logger.log().warn("final T entity, Usuario usuarioId", "Entidad removida : " + entity);
	}

	public void remove(final T entity) {
		entityManager.merge(entity);
		logger.log().warn("remove(final T entity)", "Entidad removida : " + entity);
	}

	public boolean contains(final T entity) {
		return entityManager.contains(entity);
	}

	public Connection getConnection() throws Exception {
		InitialContext initialContext = new InitialContext();
		DataSource dataSource = (DataSource) initialContext.lookup("java:/SIGEP2DS");
		return dataSource.getConnection();
	}

	protected StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		return entityManager.createNamedStoredProcedureQuery(name);
	}
	
	protected StoredProcedureQuery createStoredProcedureQuery(String name) {
		return entityManager.createStoredProcedureQuery(name);
	}	
	
	

	@SuppressWarnings("hiding")
	protected <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return this.entityManager.createNamedQuery(name, resultClass);
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	protected <T> TypedQuery<T> createNamedQuery(String name) {
		return (TypedQuery<T>) this.entityManager.createNamedQuery(name);
	}

	@SuppressWarnings("hiding")
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return this.entityManager.find(entityClass, primaryKey);
	}

	@SuppressWarnings("unchecked")
	public List<T> all() {
		String qlString = "select e from " + this.clazz.getCanonicalName() + " e";
		return this.entityManager.createQuery(qlString).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> allActives() {
		String qlString = "select e from " + this.clazz.getCanonicalName() + " e where e.estado = :estado";
		return this.entityManager.createQuery(qlString).setParameter("estado", Boolean.TRUE).getResultList();
	}

	@SuppressWarnings("hiding")
	protected <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return this.entityManager.createQuery(qlString, resultClass);
	}

	protected Query createNativeQuery(String sqlString) {
		logger.debug("Query createNativeQuery(String sqlString)", sqlString);
		return this.entityManager.createNativeQuery(sqlString);
	}

	protected Query createNativeQuery(String sqlString, Class<?> resultClass) {
		logger.debug("Query createNativeQuery(String sqlString, Class<?> resultClass)", sqlString);
		return this.entityManager.createNativeQuery(sqlString, resultClass);
	}

	protected Query createNativeQuery(String sqlString, String resultSetMapping) {
		logger.debug("Query createNativeQuery(String sqlString, String resultSetMapping)", sqlString);
		this.entityManager.flush();
		return this.entityManager.createNativeQuery(sqlString, resultSetMapping);
	}

	protected Query createQuery(String sqlString) {
		logger.debug("Query createQuery(String sqlString)", sqlString);
		return this.entityManager.createQuery(sqlString);
	}

	protected void executeBatchFromFile(String filePath, String userName, String password, String server,
			String nameDDBB) throws Exception {
		String msg = "void executeBatchFromFile(String filePath)";

		String comando = "cmd /C \"" + ConfigurationBundleConstants.getRutaSyBase() + "isql\" -i " + filePath + "  -U"
				+ userName + " " + "-P" + password + " " + "-S" + server + " -D" + nameDDBB + " -o"
				+ filePath.replace(".sql", ".log");

		try {
			if (userName == null || password == null || server == null || nameDDBB == null) {
				throw new SIGEP2SistemaException(
						"No ha sido posible detectar los paramentros necesarios para la ejecución del comando: "
								+ comando.replace(password, "*****"));
			}
			logger.info(msg, "Iniciando ejecución de comando: " + comando.replace(password, "*****"));
			Process process = Runtime.getRuntime().exec(comando);
			boolean isAlive = true;
			while (isAlive) {
				try {
					process.exitValue();
					isAlive = false;
					logger.info(msg, "Ejecución comando finalizada para el archivo de cargue '" + filePath + "'");
					logger.warn(msg, "En caso de error en la ejecución, debería encontrar el archivo : '"
							+ filePath.replace(".sql", ".log") + "'");
				} catch (Exception e) {
					isAlive = true;
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Método para reemplazamiento de los parámetros identificados con el
	 * caracter ":" en una sentencia SQL nativa
	 * 
	 * @param sqlString
	 * @return String
	 */
	protected String replaceParameters(String sqlString) {
		String newSqlString = sqlString;
		for (String key : parameters.keySet()) {
			newSqlString = sqlString.replace(":" + key, parameters.get(key).toString());
		}
		return newSqlString;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> executeNativeQuery(String sqlString, Map<Object, Object> parameters, int maxResult) {
		Query query = this.entityManager.createNativeQuery(sqlString);
		if (parameters != null) {
			for (Object key : parameters.keySet()) {
				if (key instanceof String) {
					query.setParameter(key.toString(), parameters.get(key));
				} else {
					query.setParameter(Integer.valueOf(key.toString()), parameters.get(key));
				}
			}
		}
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		return query.getResultList();
	}

	public Object executeNativeQuery(String sqlString, Map<Object, Object> parameters) {
		Query query = this.entityManager.createNativeQuery(sqlString);
		if (parameters != null) {
			for (Object key : parameters.keySet()) {
				if (key instanceof String) {
					query.setParameter(key.toString(), parameters.get(key));
				} else {
					query.setParameter(Integer.valueOf(key.toString()), parameters.get(key));
				}
			}
		}
		return query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> executeNativeQuery(String sqlString, Map<Object, Object> parameters, int first,
			int pageSize) {

		String sqlStringTemp = sqlString;
		List<Object> parametrosEliminados = new LinkedList<>();
		for (Object key : parameters.keySet()) {
			Object valueParameter = parameters.get(key);
			if (valueParameter == null || "nodata".equalsIgnoreCase(valueParameter.toString())) {
				sqlStringTemp = sqlStringTemp.replace(":" + key, "null");
				parametrosEliminados.add(key);
			}
		}

		Query query = this.entityManager.createNativeQuery(sqlStringTemp);
		Map<Object, Object> parametersExec = new HashMap<Object, Object>();
		if (parameters != null) {
			for (Object key : parameters.keySet()) {
				if (!parametrosEliminados.contains(key)) {
					if (parameters.get(key) instanceof String) {
						String booleano = parameters.get(key).toString();
						String valueParameter = (String) parameters.get(key);
						if ("false".equalsIgnoreCase(booleano)) {
							valueParameter = "0";
						} else if ("true".equalsIgnoreCase(booleano)) {
							valueParameter = "1";
						}
						if (valueParameter != null) {
							query.setParameter(key.toString(), valueParameter);
							parametersExec.put(key, valueParameter);
						}
					} else if (parameters.get(key) instanceof Date) {
						Date valueParameter = (Date) parameters.get(key);
						if (valueParameter != null) {
							query.setParameter(key.toString(), valueParameter);
							parametersExec.put(key, valueParameter);
						}
					} else if (parameters.get(key) instanceof Double) {
						Double valueParameter = Double.valueOf(parameters.get(key).toString());
						if (valueParameter != null) {
							query.setParameter(key.toString(), valueParameter);
							parametersExec.put(key, valueParameter);
						}
					} else {
						String booleano = parameters.get(key).toString();
						Integer valueParameter = null;
						if ("false".equalsIgnoreCase(booleano)) {
							valueParameter = 0;
						} else if ("true".equalsIgnoreCase(booleano)) {
							valueParameter = 1;
						} else {
							valueParameter = Integer.valueOf(parameters.get(key).toString());
						}
						if (valueParameter != null) {
							query.setParameter(key.toString(), valueParameter);
							parametersExec.put(key, valueParameter);
						}
					}
				}
			}
		}
		/**
		 * @pqr pruebas de reports con alter sesio,
		 * configurar bd y quitar**/
		Query querysession = this.entityManager.createNativeQuery("ALTER SESSION SET NLS_DATE_FORMAT = 'DD/MM/YYYY'");
		querysession.executeUpdate();
		/**
		 * @pqr pruebas de reports con alter sesio,
		 * configurar bd y quitar**/
		
		List<Object[]> result = query.setFirstResult(first).setMaxResults(pageSize).getResultList();
		setRowsCount(executeCountNativeQuery(sqlStringTemp, parametersExec));
		return result;
	}

	public int executeCountNativeQuery(String sqlString, Map<Object, Object> parameters) {
		String sqlCountString = sqlString.replaceFirst("\\*", "count(*) total");
		sqlCountString = "select count(1) from (".concat(sqlString).concat(")");
		Query query = this.entityManager.createNativeQuery(sqlCountString);
		if (parameters != null) {
			for (Object key : parameters.keySet()) {
				if (key instanceof String) {
					query.setParameter(key.toString(), parameters.get(key));
				} else {
					query.setParameter(Integer.valueOf(key.toString()), parameters.get(key));
				}
			}
		}
		return Integer.parseInt(query.getSingleResult().toString());
	}

	/**
	 * Metodo que contiene el último rowCount a una consulta en DDBB
	 */
	private static void setRowsCount(int rowsCount) {
		AbstractFactory.rowsCount = rowsCount;
	}

	/**
	 * Flush de la sesión activa
	 */
	public void flush() {
		this.entityManager.flush();
	}

	/**
	 * Metodo de refresco de entidad persistida
	 */
	public void refresh(T entity) {
		this.entityManager.refresh(entity);
	}
}
