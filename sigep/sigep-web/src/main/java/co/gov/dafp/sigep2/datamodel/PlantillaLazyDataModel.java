package co.gov.dafp.sigep2.datamodel;

import java.io.File;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ibm.icu.impl.LocaleDisplayNamesImpl.DataTable;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.PlantillaBean;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.produces.XmlReporteProduces;
import co.gov.dafp.sigep2.util.ExpresionesRegularesConstants;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.StringUtil;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;

public class PlantillaLazyDataModel extends LazyDataModel<XmlReporte> {
	private static final long serialVersionUID = 2727560751079364798L;

	transient Logger logger = Logger.getInstance(getClass());

	private XmlReporte plantilla;

	private XmlReporte total;

	private boolean resetearBusqueda = false;

	private boolean cargarSoloCatalogo = false;

	transient List<XmlReporte> listaPlantillas = null;

	public XmlReporte getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(XmlReporte plantilla) {
		this.plantilla = plantilla;
	}

	public PlantillaLazyDataModel(XmlReporte plantilla) {
		this.plantilla = plantilla;
		this.resetearBusqueda = true;
	}

	public boolean isCargarSoloCatalogo() {
		return cargarSoloCatalogo;
	}

	public void setCargarSoloCatalogo(boolean cargarSoloCatalogo) {
		this.cargarSoloCatalogo = cargarSoloCatalogo;
	}

	/**
	 * Carga lo valores <code>lazy</code> de acuerdo a los parametros contenidos en
	 * {@link PlantillaLazyDataModel#plantilla}
	 * 
	 * @param first     Indica el numero de la pagina desde la cual se mostraran los
	 *                  registros
	 * @param pageSize  Indica el tamanio de la pagina
	 * @param sortField No utilizado
	 * @param sortOrder No utilizado
	 * 
	 * @return {@link LinkedList} de {@link XmlReporte} con los registros que
	 *         coinciden segun {@link PlantillaLazyDataModel#plantilla}
	 */
	@Override
	public List<XmlReporte> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String title = "List<XmlReporte> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)";
		listaPlantillas = new LinkedList<>();
		total = XmlReporte.getInstance();

		PlantillaBean plantillaBean = BaseBean.findBean("plantillaBean");
		if (plantillaBean == null) {
			return new LinkedList<>();
		}
		try {
			int firstTemp = first;
			if (resetearBusqueda) {
				firstTemp = 0;
				resetearBusqueda = false;
			}
			if (plantilla != null) {
				if (plantilla.getNombre() == null || plantilla.getNombre().isEmpty()) {
					plantilla.setNombre(ExpresionesRegularesConstants.REGEX_TEXT_TILDES_ALFANUMERICO);
				} else if (!plantilla.getNombre().equals(ExpresionesRegularesConstants.REGEX_TEXT_TILDES_ALFANUMERICO)
						&& !plantilla.getNombre().contains("|")) {
					plantilla.setNombre(".*" + StringUtil.textoInflexivo(plantilla.getNombre().trim()).replace("[", "(")
							.replace("]", ")") + "+.*");
				}
			} else {
				plantilla = XmlReporte.getInstance();
			}

			XmlReporteProduces xmlReporteProduces = new XmlReporteProduces();
			List<XmlReporte> listaPlantillasTemp;
			if (!cargarSoloCatalogo)
				listaPlantillasTemp = xmlReporteProduces.getPlantillasReportes();
			else
				listaPlantillasTemp = xmlReporteProduces.getReportesPublicadosCatalogo();

			Pattern nombrePattern = Pattern.compile(this.plantilla.getNombre().toLowerCase());

			int excluidos = 0;
			for (int i = firstTemp; i < listaPlantillasTemp.size(); i++) {
				XmlReporte plantillaTemp = listaPlantillasTemp.get(i);
				if (cargarSoloCatalogo
						&& (!FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL.equals(plantillaTemp.getFormaConsulta())
								|| TipoPlantilla.CONFIGURACION.equals(plantillaTemp.getTipoPlantilla()))) {
					excluidos++;
					continue;
				}
				if (nombrePattern.matcher(plantillaTemp.getNombre().toLowerCase()).matches()) {
					File nombrePlantilla = new File(plantillaTemp.getPlantillaXML());
					if (nombrePlantilla.getPath().endsWith(FileUtil.BACKSLASH + ConfigurationBundleConstants
							.getString(ConfigurationBundleConstants.CNS_URL_XML_CONFIGURACION_BASE_REPORTES))) {
						if (plantillaBean != null
								&& plantillaBean.usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR)) {
							if (plantillaTemp.getId() == null) {
								plantillaTemp.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							this.listaPlantillas.add(plantillaTemp);
						} else {
							excluidos++;
						}
					} else {
						if (cargarSoloCatalogo) {
							String[] rolesPlantilla = new String[plantillaTemp.getRol().size()];

							for (int j = 0; j < plantillaTemp.getRol().size(); j++) {
								rolesPlantilla[j] = plantillaTemp.getRol().get(j);
							}
							if (plantillaBean.usuarioTieneRolAsignadoSinJerarquia(rolesPlantilla)) {
								if (plantillaTemp.getId() == null) {
									plantillaTemp.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
								}
								this.listaPlantillas.add(plantillaTemp);
							} else {
								excluidos++;
							}
						} else {
							if (plantillaTemp.getId() == null) {
								plantillaTemp.setId(BigInteger.valueOf(Calendar.getInstance().getTimeInMillis()));
							}
							this.listaPlantillas.add(plantillaTemp);
						}
					}

					if (this.listaPlantillas.size() >= pageSize) {
						break;
					}
				} else {
					excluidos++;
				}
			}

			if (!listaPlantillasTemp.isEmpty()) {
				this.setRowCount(listaPlantillasTemp.size() - excluidos);
			} else
				this.setRowCount(0);

			return listaPlantillas;
		} catch (Exception e) {
			logger.error(title, e);
		}

		return listaPlantillas;
	}

	/**
	 * Obtiene el registro de {@link PlantillaLazyDataModel#listaPlantillas} de
	 * acuerdo a <code>rowKey</code> quien contiene el identificador del registro
	 * desde el {@link DataTable}
	 */
	@Override
	public XmlReporte getRowData(String rowKey) {
		try {
			XmlReporte plantilla = XmlReporte.getInstance();
			plantilla.setId(BigInteger.valueOf(Long.valueOf(rowKey)));
			if (this.listaPlantillas.contains(plantilla)) {
				return this.listaPlantillas.get(this.listaPlantillas.indexOf(plantilla));
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}

	public List<XmlReporte> getListaPlantillas() {
		return listaPlantillas;
	}

	public void setListaPlantillas(List<XmlReporte> listaPlantillas) {
		this.listaPlantillas = listaPlantillas;
	}

	/**
	 * Devuelve el registro de totales de la tabla
	 */
	public XmlReporte getTotal() {
		return total;
	}

	public void setTotal(XmlReporte total) {
		this.total = total;
	}
}