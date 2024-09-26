/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.ejb;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.factoria.ReporteFactoria;
import co.gov.dafp.sigep2.factoria.RolFactoria;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.interfaces.IReporteRemote;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.sistema.ProcesoReporte;
import co.gov.dafp.sigep2.util.sistema.ReporteDefault;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.view.Reporte;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class ReporteEJB implements IReporteRemote {
	private static final long serialVersionUID = 8009684309248263560L;

	private Logger logger = Logger.getInstance(ReporteEJB.class);
	private ProcesoReporte procesoReporte;

	@EJB
	private IEnvioCorreoLocal envioCorreo;

	@EJB
	private ReporteFactoria reporteFactoria;

	@EJB
	private RolFactoria rolFactoria;

	@Override
	public List<Registro> exec(final co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistro,
			final XmlReporte proceso, final UsuarioDTO usuarioReporte, List<Parametro> parametros, Date fechaReporte,
			String rutaArchivoSalida, int first, int pageSize, boolean generarParaCorreo)
			throws SIGEP2NegocioException {
		String tituloLog = "void init(Reporte proceso, Usuario usuarioReporte, List<Parametro> parametros, Date fechaReporte, String rutaArchivoSalida)";
		List<Registro> resultados = new LinkedList<>();
		try {
			procesoReporte = (ProcesoReporte) Class.forName(proceso.getClaseJava()).newInstance();
		} catch (Exception e) {
			logger.log()
					.info("void init(Reporte proceso, Usuario usuarioReporte, List<Parametro> parametros, Date fechaCargue, String rutaArchivoEnCargue)",
							"La interfaz implementada no es soportada: '" + proceso.getClaseJava()
									+ "', se implemeta la interfaz por defecto: '" + ReporteDefault.class.getName()
									+ "'",
							e);
			procesoReporte = new ReporteDefault();
		}
		this.procesoReporte.setEnvioCorreo(envioCorreo);
		this.procesoReporte.setReporteFactoria(reporteFactoria);
		this.procesoReporte.setUsuarioReporte(usuarioReporte);
		this.procesoReporte.setXml(proceso);
		this.procesoReporte.setParametros(parametros);
		this.procesoReporte.setFirst(first);
		this.procesoReporte.setPageSize(pageSize);

		try {
			logger.log().info(tituloLog, "Validando filtros");
			String validacion = this.procesoReporte.validarFiltrosReporte();
			if (!validacion.isEmpty()) {
				throw new SIGEP2NegocioException(validacion);
			}

			logger.log().info(tituloLog, procesoReporte.getClass());
			this.procesoReporte.cargarConfiguracionXML();
			logger.log().info(tituloLog, "Generar Reportes");
			this.procesoReporte.generarReporte(tipoRegistro, rutaArchivoSalida, generarParaCorreo);
			if (!generarParaCorreo) {
				resultados = this.procesoReporte.getResultados();
			}
			logger.log().info(tituloLog,
					"La generación del reporte ha finalizado. Validar log para confirmar el resultado.");
		} catch (Exception e) {
			logger.log().error(tituloLog, e);
			throw new SIGEP2NegocioException(e);
		}
		return resultados;
	}

	@Override
	public String validate(ReporteDTO proceso, List<Parametro> parametros) {
		String tituloLog = "String validate(Reporte proceso, List<Parametro> parametros)";
		try {
			procesoReporte = (ProcesoReporte) Class.forName(proceso.getClaseJava()).newInstance();
		} catch (Exception e) {
			logger.log().error(tituloLog, "La interfaz implementada no es soportada: '" + proceso.getClaseJava()
					+ "', se implemeta la interfaz por defecto: '" + ReporteDefault.class.getName() + "'", e);
			procesoReporte = new ReporteDefault();
		}
		logger.log().info(tituloLog, "Validando filtros");
		this.procesoReporte.setParametros(parametros);
		return this.procesoReporte.validarFiltrosReporte();
	}

	public RolDTO getRol(String nombre) throws SIGEP2SistemaException {
		return this.rolFactoria.findByNombre(nombre);
	}

	public RolDTO getRol(long id) throws SIGEP2SistemaException {
		Rol rol = this.rolFactoria.find(id);
		if (rol != null) {
			return (RolDTO) rol.getDTO();
		}
		return null;
	}

	public ReporteDTO getReporte(Long id) throws SIGEP2SistemaException {
		Reporte reporte = this.reporteFactoria.find(id);
		if (reporte != null) {
			return (ReporteDTO) reporte.getDTO();
		}
		return null;
	}

	public ReporteDTO getReporte(String descripcion) throws SIGEP2SistemaException {
		return this.reporteFactoria.findReporteByDescripcion(descripcion);
	}

	public int getRowsCount() {
		return this.reporteFactoria.getRowsCount();
	}

	public List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int maxResult)
			throws SIGEP2SistemaException {
		return this.reporteFactoria.executeNativeQuery(sqlString, parameters, maxResult);
	}

}
