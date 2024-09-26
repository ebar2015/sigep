package co.gov.dafp.sigep2.sistema.produces;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormatoReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaConfiguracion;
import co.gov.dafp.sigep2.util.xml.reporte.config.PeriodoGeneracion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Reporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.SeparadorCsv;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoBandeja;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoCruceFecha;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDatoReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoValidacion;

@SessionScoped
public class XmlReporteProduces implements Serializable {
	private static final long serialVersionUID = -376397974884258605L;

	private TipoPlantilla tipoPlantilla;

	private List<ProcesoArchivoDTO> tiposArchivo;

	private List<XmlReporte> plantillas;

	private List<String> nombresPlantilla;

	List<MallaConfiguracion> archivos;

	private Reporte procesoArchivo;

	public void setTipoPlantilla(TipoPlantilla tipoCargue) {
		this.tipoPlantilla = tipoCargue;
	}

	@Named
	@Produces
	public List<FormatoReporte> getFormatosReporte() {
		return Arrays.asList(FormatoReporte.values());
	}

	@Named
	@Produces
	public List<CalificadorComparacion> getCalificadoresComparacionReporte() {
		return Arrays.asList(CalificadorComparacion.values());
	}

	@Named
	@Produces
	public List<CalificadorComparacion> getCalificadoresNumericos() {
		return addCalificadoresNumericos(CalificadorComparacion.TTL_REPORTES_DIFERENTE_A,
				CalificadorComparacion.TTL_REPORTES_IGUAL, CalificadorComparacion.TTL_REPORTES_MAYOR_IGUAL_QUE,
				CalificadorComparacion.TTL_REPORTES_MAYOR_QUE, CalificadorComparacion.TTL_REPORTES_MENOR_IGUAL_QUE,
				CalificadorComparacion.TTL_REPORTES_MENOR_QUE);
	}

	@Named
	@Produces
	public List<CalificadorComparacion> getCalificadoresFechas() {
		return addCalificadoresNumericos(CalificadorComparacion.TTL_REPORTES_DIFERENTE_A,
				CalificadorComparacion.TTL_REPORTES_IGUAL, CalificadorComparacion.TTL_REPORTES_MAYOR_IGUAL_QUE,
				CalificadorComparacion.TTL_REPORTES_MAYOR_QUE, CalificadorComparacion.TTL_REPORTES_MENOR_IGUAL_QUE,
				CalificadorComparacion.TTL_REPORTES_MENOR_QUE);
	}

	@Named
	@Produces
	public List<CalificadorComparacion> getCalificadoresCadenas() {
		return addCalificadoresNumericos(CalificadorComparacion.TTL_REPORTES_IGNORAR,
				CalificadorComparacion.TTL_REPORTES_DIFERENTE_A, CalificadorComparacion.TTL_REPORTES_IGUAL,
				CalificadorComparacion.TTL_REPORTES_EMPIEZA_POR, CalificadorComparacion.TTL_REPORTES_ACABA_POR,
				CalificadorComparacion.TTL_REPORTES_NO_EMPIEZA_POR, CalificadorComparacion.TTL_REPORTES_NO_ACABA_POR,
				CalificadorComparacion.TTL_REPORTES_NO_CONTIENE);
	}

	private List<CalificadorComparacion> addCalificadoresNumericos(CalificadorComparacion... calificadorComparacions) {
		return Arrays.asList(calificadorComparacions);
	}

	@Named
	@Produces
	public List<TipoCruceFecha> getTiposCruceFechaReporte() {
		return Arrays.asList(TipoCruceFecha.values());
	}

	@Named
	@Produces
	public List<TipoBandeja> getTiposBandejaReporte() {
		return Arrays.asList(TipoBandeja.values());
	}

	@Named
	@Produces
	public List<TipoDato> getTiposDatoReporte() {
		List<TipoDato> tiposDato = new LinkedList<TipoDato>();
		for (TipoDato tipoDato : TipoDato.values()) {
			tiposDato.add(tipoDato);
		}
		List<TipoDatoReporte> tiposDatoReporte = Arrays.asList(TipoDatoReporte.values());
		if (!TipoPlantilla.REPORTE.equals(tipoPlantilla)) {
			for (TipoDato tipoDato : TipoDato.values()) {
				for (TipoDatoReporte tipoDatoReporte : tiposDatoReporte) {
					if (tipoDato.value().equals(tipoDatoReporte.value())) {
						tiposDato.remove(tipoDato);
						break;
					}
				}
			}
		}
		return tiposDato;
	}

	@Named
	@Produces
	public List<TipoRegistro> getTiposRegistroReporte() {
		return Arrays.asList(TipoRegistro.values());
	}

	@Named
	@Produces
	public List<TipoValidacion> getTiposValidacionReporte() {
		return Arrays.asList(TipoValidacion.values());
	}

	@Named
	@Produces
	public List<SeparadorCsv> getSeparadoresCSVReporte() {
		return Arrays.asList(SeparadorCsv.values());
	}

	@Named
	@Produces
	public List<TipoPlantilla> getTiposCargueReporte() {
		return Arrays.asList(TipoPlantilla.values());
	}

	@Named
	@Produces
	public List<PeriodoGeneracion> getPeriodosGeneracion() {
		return Arrays.asList(PeriodoGeneracion.values());
	}

	@Named
	@Produces
	public List<FormaConsulta> getFormasConsulta() {
		return Arrays.asList(FormaConsulta.values());
	}

	@Named
	@Produces
	public List<RolDTO> getRolesSistema() {
		return ComunicacionServiciosSis.getRolesSistema("", 0, 0, 1);
	}

	/**
	 * Retorna los reportes del sistema
	 * 
	 * @return {@link List} de {@link XmlReporte} Plantillas de reportes del sistema
	 */
	@Named
	@Produces
	public List<XmlReporte> getPlantillasReportes() throws Exception {
		plantillas = new LinkedList<>();

		String ruta = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML);
		File carpetaPlantillas = new File(ruta);
		if (carpetaPlantillas.exists()) {
			for (File nombrePlantilla : carpetaPlantillas.listFiles()) {
				if (!nombrePlantilla.isDirectory() && nombrePlantilla.getPath().endsWith(FileUtil.XML)) {
					try {
						XmlReporte plantilla = getPlantilla(nombrePlantilla.getName(), "");
						if (!plantilla.isEliminado()) {
							if (plantilla.getId() == null) {
								plantilla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							this.plantillas.add(plantilla);
						}
					} catch (Exception e) {
					}
				}
			}
		}

		Collections.sort(this.plantillas, new Comparator<XmlReporte>() {
			@Override
			public int compare(XmlReporte r1, XmlReporte r2) {
				return r1.getId().compareTo(r2.getId());
			}
		});

		return plantillas;
	}

	/**
	 * Retorna los reportes publicados actualmente en el catalogo de reportes
	 * 
	 * @return {@link List} de {@link XmlReporte} Plantillas de reportes en el
	 *         catalogo
	 */
	@Named
	@Produces
	public List<XmlReporte> getReportesPublicadosCatalogo() throws Exception {
		plantillas = new LinkedList<>();

		String ruta = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO);
		File carpetaPlantillas = new File(ruta);
		if (carpetaPlantillas.exists()) {
			for (File nombrePlantilla : carpetaPlantillas.listFiles()) {
				if (!nombrePlantilla.isDirectory() && nombrePlantilla.getPath().endsWith(FileUtil.XML)) {
					try {
						XmlReporte plantilla = getPlantilla(nombrePlantilla.getName(), ConfigurationBundleConstants
								.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_CATALOGO));
						if (!plantilla.isEliminado() && FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL
								.equals(plantilla.getFormaConsulta())) {
							if (plantilla.getId() == null) {
								plantilla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							this.plantillas.add(plantilla);
						}
					} catch (Exception e) {
					}
				}
			}
		}

		Collections.sort(this.plantillas, new Comparator<XmlReporte>() {
			@Override
			public int compare(XmlReporte r1, XmlReporte r2) {
				return r1.getId().compareTo(r2.getId());
			}
		});

		return plantillas;
	}

	/**
	 * Retorna los reportes publicados actualmente en el portal en SIGEP en Cifras
	 * 
	 * @return {@link List} de {@link XmlReporte} Plantillas de reportes en elportal
	 *         en SIGEP en Cifras
	 */
	@Named
	@Produces
	public List<XmlReporte> getReportesPublicadosPortal() throws Exception {
		plantillas = new LinkedList<>();

		String ruta = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML)
				+ ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL);
		File carpetaPlantillas = new File(ruta);
		if (carpetaPlantillas.exists()) {
			for (File nombrePlantilla : carpetaPlantillas.listFiles()) {
				if (!nombrePlantilla.isDirectory() && nombrePlantilla.getPath().endsWith(FileUtil.XML)) {
					try {
						XmlReporte plantilla = getPlantilla(nombrePlantilla.getName(), ConfigurationBundleConstants
								.getString(ConfigurationBundleConstants.CNS_URL_XML_REPORTES_PORTAL));
						if (!plantilla.isEliminado() && FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
								.equals(plantilla.getFormaConsulta())) {
							if (plantilla.getId() == null) {
								plantilla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							this.plantillas.add(plantilla);
						}
					} catch (Exception e) {
					}
				}
			}
		}

		Collections.sort(this.plantillas, new Comparator<XmlReporte>() {
			@Override
			public int compare(XmlReporte r1, XmlReporte r2) {
				return r1.getId().compareTo(r2.getId());
			}
		});

		return plantillas;
	}

	@Named
	@Produces
	public List<ProcesoArchivoDTO> getReportes() throws SIGEP2SistemaException {
		if (tiposArchivo == null) {
			tiposArchivo = IngresoSistemaDelegate.getProcesosArchivo();
		}
		return tiposArchivo;
	}

	public Reporte getProcesoArchivo() {
		return procesoArchivo;
	}

	public void setProcesoArchivo(Reporte procesoArchivo) {
		this.procesoArchivo = procesoArchivo;
	}

	public List<String> getNombresPlantilla() {
		return nombresPlantilla;
	}

	public void setNombresPlantilla(List<String> nombresPlantilla) {
		this.nombresPlantilla = nombresPlantilla;
	}

	@Named
	@Produces
	public List<MallaConfiguracion> getBloques() {
		archivos = procesoArchivo.getMallaConfiguracion();
		return archivos;
	}

	public static XmlReporte getPlantilla(String nombrePlantilla, String subcarpeta) throws Exception {
		return XmlReporte.getEstructura(XmlReporte.getXml(nombrePlantilla, subcarpeta));
	}

	/**
	 * Retorna la plantilla base de configuracion
	 * 
	 * @return {@link XmlReporte} Plantilla base de configuracion
	 */
	@Named
	@Produces
	public XmlReporte getConfiguracion() throws Exception {
		String ruta = ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_PLANTILLAS_XML);
		File carpetaPlantillas = new File(ruta);
		if (carpetaPlantillas.exists()) {
			for (File nombrePlantilla : carpetaPlantillas.listFiles()) {
				if (!nombrePlantilla.isDirectory() && nombrePlantilla.getPath().endsWith(FileUtil.XML)) {
					try {
						XmlReporte plantilla = getPlantilla(nombrePlantilla.getName(), "");
						if (TipoPlantilla.CONFIGURACION.equals(plantilla.getTipoPlantilla())) {
							if (plantilla.getId() == null) {
								plantilla.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							return plantilla;
						}
					} catch (Exception e) {
					}
				}
			}
		}

		return null;
	}
}
