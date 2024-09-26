package co.gov.dafp.sigep2.services;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.bean.ColumnaTablaGestionInformacion;
import co.gov.dafp.sigep2.bean.TablaGestionInformacion;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.CifrasEmpleoPublicoMapper;

public class CifrasEmpleoPublicoServices {

	/**
	 * Devuelve el listado de registros de cifras de empleo publico de acuerdo a
	 * los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	public List<CifrasEmpleoPublico> cifrasEmpleoPublico(CifrasEmpleoPublico cifrasEmpleoPublico) {
		List<CifrasEmpleoPublico> listado = new LinkedList<>();
		List<CifrasEmpleoPublico> nwlistado = new LinkedList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasEmpleoPublicoMapper mapper = session.getMapper(CifrasEmpleoPublicoMapper.class);			
			listado = mapper.selectCifras(cifrasEmpleoPublico);
			
			/*if (listado != null) {
				for (CifrasEmpleoPublico cifra : listado) {
					cifra.setIpvc(getIPVC(cifra.getPesoPorcentajeVinculacion(), cifra.getPesoPorcentajeContrato(),
							cifra.getPorcentajeVinculacion(), cifra.getPorcentajeContratos()));
					nwlistado.add(cifra);
				}
			}*/
		} catch (Exception ex) {
			listado = new LinkedList<>();
			CifrasEmpleoPublico respuesta = new CifrasEmpleoPublico();
			respuesta.setCodigoEstado(500);
			respuesta.setMensajeTecnico(ex.getMessage());

			listado.add(respuesta);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listado;
	}

	/**
	 * Devuelve el listado de registros de tabla de resultados para cifras de
	 * empleo publico de acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link CifrasEmpleoPublico} Registros
	 *         seleccionados
	 */
	public List<CifrasEmpleoPublico> tablaResultadoCifrasEmpleoPublico(CifrasEmpleoPublico cifrasEmpleoPublico) {
		List<CifrasEmpleoPublico> listado = new LinkedList<>();
		List<CifrasEmpleoPublico> nwlistado = new LinkedList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasEmpleoPublicoMapper mapper = session.getMapper(CifrasEmpleoPublicoMapper.class);
			listado = mapper.selectTablaResultados(cifrasEmpleoPublico);

			if (listado != null) {
				for (CifrasEmpleoPublico cifra : listado) {
					cifra.setIpvc(getIPVC(cifra.getPesoPorcentajeVinculacion(), cifra.getPesoPorcentajeContrato(),
							cifra.getPorcentajeVinculacion(), cifra.getPorcentajeContratos()));
					nwlistado.add(cifra);
				}
			}
		} catch (Exception ex) {
			listado = new LinkedList<>();
			CifrasEmpleoPublico respuesta = new CifrasEmpleoPublico();
			respuesta.setCodigoEstado(500);
			respuesta.setMensajeTecnico(ex.getMessage());

			listado.add(respuesta);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return nwlistado;
	}

	/**
	 * Devuelve el totalizado de los registros de cifras de empleo publico de
	 * acuerdo a los filtros definidos
	 * 
	 * @param cifrasEmpleoPublico
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros para el totalizado
	 * 
	 * @return {@link CifrasEmpleoPublico} Totalizado de los registros
	 *         seleccionados
	 */
	public CifrasEmpleoPublico getTotalCifrasEmpleoPublico(CifrasEmpleoPublico cifrasEmpleoPublico) {
		CifrasEmpleoPublico respuesta = new CifrasEmpleoPublico();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasEmpleoPublicoMapper mapper = session.getMapper(CifrasEmpleoPublicoMapper.class);
			respuesta = mapper.selectTotalCifras(cifrasEmpleoPublico);
			respuesta.setIpvc(getIPVC(respuesta.getPesoPorcentajeVinculacion(), respuesta.getPesoPorcentajeContrato(),
					respuesta.getPorcentajeVinculacion(), respuesta.getPorcentajeContratos()));
		} catch (Exception ex) {
			respuesta.setCodigoEstado(500);
			respuesta.setMensajeTecnico(ex.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return respuesta;
	}

	/**
	 * Devuelve las tablas y vistas del sistema para configuracion de los
	 * reportes en gestion de la informacion
	 * 
	 * @return {@link List} de {@link TablaGestionInformacion} Tablas y vistas
	 *         del sistema
	 */
	public List<TablaGestionInformacion> tablasGestionInformacion() {
		List<TablaGestionInformacion> listado = new LinkedList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasEmpleoPublicoMapper mapper = session.getMapper(CifrasEmpleoPublicoMapper.class);
			listado = mapper.selectTablas();
		} catch (Exception ex) {
			TablaGestionInformacion respuesta = new TablaGestionInformacion();
			respuesta.setCodigoEstado(500);
			respuesta.setMensajeTecnico(ex.getMessage());

			listado.add(respuesta);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listado;
	}

	/**
	 * Devuelve el listado de columnas de la tabla filtrada
	 * 
	 * @param tablaGestionInformacion
	 *            Contiene los parametros con los cuales se hara la seleccion de
	 *            registros
	 * 
	 * @return {@link List} de {@link ColumnaTablaGestionInformacion} Registros
	 *         seleccionados para la tabla seleccionada
	 */
	public List<ColumnaTablaGestionInformacion> columnasTablasGestionInformacion(
			TablaGestionInformacion tablaGestionInformacion) {
		List<ColumnaTablaGestionInformacion> listado = new LinkedList<>();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			CifrasEmpleoPublicoMapper mapper = session.getMapper(CifrasEmpleoPublicoMapper.class);
			listado = mapper.selectColumnasTabla(tablaGestionInformacion);
		} catch (Exception ex) {
			ColumnaTablaGestionInformacion respuesta = new ColumnaTablaGestionInformacion();
			respuesta.setCodigoEstado(500);
			respuesta.setMensajeTecnico(ex.getMessage());

			listado.add(respuesta);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return listado;
	}

	/**
	 * Calculo del valor del IPVC a partir de otros factores
	 * 
	 * @param pesoPorcentajeVinculacion
	 * @param pesoPorcentajeContrato
	 * @param porcentajeVinculacion
	 * @param porcentajeContratos
	 * 
	 * @return {@link BigDecimal} Valor del IPVC calculado
	 */
	private BigDecimal getIPVC(BigDecimal pesoPorcentajeVinculacion, BigDecimal pesoPorcentajeContrato,
			BigDecimal porcentajeVinculacion, BigDecimal porcentajeContratos) {

		// =+SI(CF175=CI175;"NO APLICA";SUMA(v1;SI(CJ175>CI175;CI175;CJ175)))
		// CF177=pesoPorcentajeVinculacion
		// CI177=pesoPorcentajeContrato
		// CG177=porcentajeVinculacion
		// CJ177=porcentajeContratos

		BigDecimal ipvc = null;

		if (pesoPorcentajeVinculacion == null || pesoPorcentajeContrato == null) {
			return ipvc;
		} else if (pesoPorcentajeVinculacion.equals(pesoPorcentajeContrato)) {
			ipvc = BigDecimal.ONE.negate();
		} else {
			ipvc = suma(
					porcentajeVinculacion.doubleValue() > pesoPorcentajeVinculacion.doubleValue()
							? pesoPorcentajeVinculacion : porcentajeVinculacion,
					porcentajeContratos.doubleValue() > pesoPorcentajeContrato.doubleValue() ? pesoPorcentajeContrato
							: porcentajeContratos);
		}
		return ipvc;
	}

	private BigDecimal suma(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return null;
		}
		return v1.add(v2);
	}
}
