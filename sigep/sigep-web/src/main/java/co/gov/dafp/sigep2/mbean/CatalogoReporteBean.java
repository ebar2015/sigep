package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.enterprise.context.NonexistentConversationException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.datamodel.PlantillaLazyDataModel;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaReporte;

@Named
@SessionScoped
public class CatalogoReporteBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1026880406086783051L;

	private String nombreXml;
	private XmlReporte xml;
	List<Parametrica> idsReportesCatalogoParametro = null;

	private PlantillaLazyDataModel listaReportes = null;

	private List<String> mallasReporte;

	public String getNombreXml() {
		return nombreXml;
	}

	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}

	public XmlReporte getXml() {
		return xml;
	}

	public void setXml(XmlReporte xml) {
		this.xml = xml;
	}

	public List<String> getMallasReporte() {
		if (mallasReporte == null) {
			mallasReporte = new LinkedList<>();
		}
		return mallasReporte;
	}

	public void setMallasReporte(List<String> mallasReporte) {
		this.mallasReporte = mallasReporte;
	}

	public PlantillaLazyDataModel getListaReportes() {
		return listaReportes;
	}

	public void setListaReportes(PlantillaLazyDataModel listaReportes) {
		this.listaReportes = listaReportes;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Inicializa los valores para el inicio de la configuracion del reporte a
	 * generar
	 */
	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
		int pageSize = ConfigurationBundleConstants.getpaginatorSize();
		PlantillaLazyDataModel dataModel = new PlantillaLazyDataModel(XmlReporte.getInstance());
		dataModel.setCargarSoloCatalogo(true);
		dataModel.load(0, ConfigurationBundleConstants.getpaginatorSize(), "", SortOrder.ASCENDING,
				new HashMap<String, Object>());

		xml = XmlReporte.getInstance();
		xml.setNombre(nombreXml);

		dataModel = new PlantillaLazyDataModel(xml);
		dataModel.setCargarSoloCatalogo(true);
		dataModel.load(0, pageSize, "", SortOrder.ASCENDING, new HashMap<String, Object>());

		if (dataModel.getListaPlantillas().size() == 1) {
			XmlReporte xmlSearch = dataModel.getListaPlantillas().get(0);
			String[] rolesPlantilla = new String[xmlSearch.getRol().size()];
			for (int i = 0; i < xmlSearch.getRol().size(); i++) {
				rolesPlantilla[i] = xmlSearch.getRol().get(i);
			}
			if (usuarioTieneRolAsignadoSinJerarquia(rolesPlantilla)) {
				xml = xmlSearch;
			}
		} else {
			for (XmlReporte xmlSearch : dataModel.getListaPlantillas()) {
				String[] rolesPlantilla = new String[xmlSearch.getRol().size()];
				for (int i = 0; i < xmlSearch.getRol().size(); i++) {
					rolesPlantilla[i] = xmlSearch.getRol().get(i);
				}
				if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL.equals(xmlSearch.getFormaConsulta())
						&& usuarioTieneRolAsignadoSinJerarquia(rolesPlantilla)
						&& nombreXml.equalsIgnoreCase(xmlSearch.getNombre())) {
					xml = xmlSearch;
					break;
				}
			}
		}

		mallasReporte = new LinkedList<>();
		printTable();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("verVideo","gestionInformacion");		
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_URL_INVALID);
		}

		try {
			nombreXml = "";
			init();
		} catch (SIGEP2SistemaException e) {
			logger.error("void retrieve()", e);
		}
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void printTable() {
		if (nombreXml == null) {
			nombreXml = "";
		}
		xml = XmlReporte.getInstance();
		xml.setNombre(nombreXml);
		listaReportes = new PlantillaLazyDataModel(xml);
		listaReportes.setCargarSoloCatalogo(true);
	}

	/**
	 * Metodo utilizado para la lista de autocompletar en los filtros de
	 * busqueda
	 * 
	 * @param query
	 *            Texto a buscar de forma inflexiva dentro de atributo nombre de
	 *            la plantilla xml
	 * @return {@link List} de {@link String} con las coincidencias
	 */
	public List<String> completeText(String query) {
		List<String> results = new LinkedList<>();

		if (query == null || query.isEmpty()) {
			return results;
		}

		int pageSize = ConfigurationBundleConstants.getpaginatorSize();

		PlantillaLazyDataModel dataModel = new PlantillaLazyDataModel(XmlReporte.getInstance());
		dataModel.setCargarSoloCatalogo(true);
		dataModel.load(0, ConfigurationBundleConstants.getpaginatorSize(), "", SortOrder.ASCENDING,
				new HashMap<String, Object>());

		xml = XmlReporte.getInstance();
		xml.setNombre(query);

		dataModel = new PlantillaLazyDataModel(xml);
		dataModel.setCargarSoloCatalogo(true);
		dataModel.load(0, pageSize, "", SortOrder.ASCENDING, new HashMap<String, Object>());

		for (XmlReporte xmlSearch : dataModel.getListaPlantillas()) {
			results.add(xmlSearch.getNombre());
		}

		Collections.sort(results, new Comparator<String>() {
			@Override
			public int compare(String c1, String c2) {
				return c1.compareTo(c2);
			}
		});

		if (results.isEmpty()) {
			nombreXml = null;
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_CATALOGO_SIN_RESULTADOS);
		}

		return results;
	}

	/**
	 * Una vez configurado los datos seleccionados por el usuario se procede con
	 * la generacion del reporte teniendo en cuenta el destino, ya sea por
	 * correo o descarga inmediata
	 */
	public void generarReporte() {
		if (xml.getFormatoReporte() == null) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_CATALOGO_SIN_RESULTADOS);
			return;
		}
		try {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('formatoSalidaDialog').hide();");
			context.execute("PF('cancelarGeneracionCatalogoDialog').show();");

			ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
			CatalogoBean catalogoBean = (CatalogoBean) eLContext.getELResolver().getValue(eLContext, null,
					"catalogoBean");

			catalogoBean.setHabilitarBotonesExportar(true);
			finalizarConversacion("/gestion-informacion/reporte/consultCatalogo.xhtml", null, null);
		} catch (IOException e) {
			logger.error("void generarReporte()", e);
		}
	}

	/**
	 * De acuerdo a los valores seleccioandos por el usuario, se procede a la
	 * carga de los valores necesarios para proceder con la generacion del
	 * reporte
	 */
	public void prepararReporte(XmlReporte xmlProcesar) {
		xml = xmlProcesar;
		List<MallaReporte> mallasSeleccionadas = new LinkedList<>();
		mallasSeleccionadas.addAll(xml.getMallaReporte());

		if (mallasSeleccionadas.isEmpty()) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_INFO_CATALOGO_SIN_RESULTADOS);
			return;
		}

		ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
		CatalogoBean catalogoBean = (CatalogoBean) eLContext.getELResolver().getValue(eLContext, null, "catalogoBean");
		PlantillaBean plantillaBean = (PlantillaBean) eLContext.getELResolver().getValue(eLContext, null,"plantillaBean");

		if (catalogoBean == null || plantillaBean == null) {
			return;
		}

		try {
			plantillaBean.setId(xml.getId().longValue());
			plantillaBean.setRecursoId(PlantillaBean.GESTIONAR_REPORTE_CATALOGO);
			plantillaBean.retrieve();
			xml.unsetMallaReporte();
			xml.getMallaReporte().addAll(mallasSeleccionadas);
			plantillaBean.setXml(xml);
			plantillaBean.setListaRol(xml.getRol());

			plantillaBean.setListaMallaReporte(mallasSeleccionadas);
			plantillaBean.procesarConfiguracion();

			catalogoBean.setXml(plantillaBean.getXml());
			catalogoBean.init(); /*Configuracion del reporte*/

			/*RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('formatoSalidaDialog').show();");*/
			finalizarConversacion("/gestion-informacion/reporte/consultCatalogo.xhtml", null, null);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public void cancelar() {
		String path = "/index.xhtml";
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect(context.getRequestContextPath() + "/" + ConfigurationBundleConstants.aliasSitio() + path);
		} catch (IOException e) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("PF('formatoSalidaDialog').hide();");
			context.execute("PF('cancelarGeneracionCatalogoDialog').hide();");
		}
	}

}
