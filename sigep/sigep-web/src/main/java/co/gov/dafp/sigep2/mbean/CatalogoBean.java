package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.NonexistentConversationException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.datamodel.ReporteCatalogoLazyDataModel;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.produces.EntidadProduces;
import co.gov.dafp.sigep2.sistema.produces.XmlReporteProduces;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Parametro.ConfiguracionParametro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaConfiguracion;
import co.gov.dafp.sigep2.util.xml.reporte.config.MallaReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;
import co.gov.dafp.sigep2.util.xml.reporte.config.ValorMalla;

@Named
@SessionScoped
public class CatalogoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -8170121234118380526L;

	transient MallaReporte xmlBloque;
	private XmlReporte xml;
	private List<Parametro> parametros;

	private List<ColumnModel> columns;
	private ReporteCatalogoLazyDataModel resultados;
	private List<Parametro> totales;
	transient List<co.gov.dafp.sigep2.util.Registro> datasource;
	transient List<Registro> registrosFiltrados;
	private String parametrosDesdePadre;
	private UsuarioDTO usuarioId;

	private boolean habilitarBotonesExportar = true;
	private boolean actualizarFormularioConsultarDetalle = false;

	private static final String PARAMETRO_USUARIO_ID = "cod_usuario";
	private static final String PARAMETRO_ENTIDAD_ID = "cod_entidad";
	private static final String PARAMETRO_REPORTES_CARGUE_ARCHIVO = "reportesCargueBloque";

	private boolean tieneTotales = false;

	private String mensajeValidaciones = MessagesBundleConstants
			.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CATALOGO_SIN_RESULTADOS, getLocale());
	
	/*Variables para filtro por defecto con entidades del usuario en sesión*/
	private boolean lbAplicarFiltroTodasEntidades,lbMostrarFiltroTodasEntidades;
	public static String  strFiltroEntidadesSesion,strColumnaFiltrarEntidades;
	List<EntidadDTO> lstEntidadesUsuario;

	@Inject
	private DetalleReporteBean detalleReporteBean;

	public XmlReporte getXml() {
		return xml;
	}

	public void setXml(XmlReporte xml) {
		this.xml = xml;
	}

	public MallaReporte getXmlBloque() {
		return xmlBloque;
	}

	public void setXmlBloque(MallaReporte xmlBloque) {
		this.xmlBloque = xmlBloque;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public ReporteCatalogoLazyDataModel getResultados() {
		return resultados;
	}

	public void setResultados(ReporteCatalogoLazyDataModel resultados) {
		this.resultados = resultados;
	}

	public List<Parametro> getTotales() {
		return totales;
	}

	public void setTotales(List<Parametro> totales) {
		this.totales = totales;
	}

	public List<Registro> getRegistrosFiltrados() {
		return registrosFiltrados;
	}

	public void setRegistrosFiltrados(List<Registro> registrosFiltrados) {
		this.registrosFiltrados = registrosFiltrados;
	}

	public List<co.gov.dafp.sigep2.util.Registro> getDatasource() {
		return datasource;
	}

	public void setDatasource(List<co.gov.dafp.sigep2.util.Registro> datasource) {
		this.datasource = datasource;
	}

	public String getParametrosDesdePadre() {
		return parametrosDesdePadre;
	}

	public void setParametrosDesdePadre(String parametrosDesdePadre) {
		this.parametrosDesdePadre = parametrosDesdePadre;
	}

	public UsuarioDTO getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(UsuarioDTO usuarioId) {
		this.usuarioId = usuarioId;
	}

	public boolean isTieneTotales() {
		return tieneTotales;
	}

	public void setTieneTotales(boolean tieneTotales) {
		this.tieneTotales = tieneTotales;
	}

	public String getMensajeValidaciones() {
		return mensajeValidaciones;
	}

	public void setMensajeValidaciones(String mensajeValidaciones) {
		this.mensajeValidaciones = mensajeValidaciones;
	}

	public boolean isHabilitarBotonesExportar() {
		return habilitarBotonesExportar;
	}

	public void setHabilitarBotonesExportar(boolean habilitarBotonesExportar) {
		this.habilitarBotonesExportar = habilitarBotonesExportar;
	}

	public boolean isActualizarFormularioConsultarDetalle() {
		return actualizarFormularioConsultarDetalle;
	}

	public void setActualizarFormularioConsultarDetalle(boolean actualizarFormularioConsultarDetalle) {
		this.actualizarFormularioConsultarDetalle = actualizarFormularioConsultarDetalle;
	}

	@Override
	public StreamedContent getFileDownload() {
		// this.generarBloques(xml, columns, xmlBloque, datasource);
		return fileDownload;
	}

	public DetalleReporteBean getDetalleReporteBean() {
		return detalleReporteBean;
	}

	public void setDetalleReporteBean(DetalleReporteBean detalleReporteBean) {
		this.detalleReporteBean = detalleReporteBean;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException {
		return;
	}

	/**
	 * Inicializa la plantilla seleccionada para el reporte a ejecutar
	 */
	public void init() {
		String msg = "void init()";
		if (xml == null) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
			return;
		}

		parametros = new LinkedList<>();
		registrosFiltrados = new LinkedList<>();
		boolean blColumnaFiltroEntidad = false;

		if (!this.xml.getMallaReporte().isEmpty()) {

			EntidadProduces entidadProduces = new EntidadProduces();
			try {
					lstEntidadesUsuario = entidadProduces.getEntidadesUsuario();
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}
			
			try {
				XmlReporteProduces xmlReporteProduces = new XmlReporteProduces();
				try {
					boolean tienePermiso = FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
							.equals(xml.getFormaConsulta());
					if (!tienePermiso) {
						for (String rol : xml.getRol()) {
							if (usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
									rol)) {
								tienePermiso = true;
								break;
							}
						}
					}
					if (!tienePermiso) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
						xml = null;
						return;
					}
				} catch (Exception e) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							MessagesBundleConstants.DLG_PROCESO_FALLIDO);
					xml = null;
					return;
				}
				this.xml.unsetOrdenamiento();
				this.resultados = new ReporteCatalogoLazyDataModel(null);

				if (xml.getTipoPlantilla().equals(TipoPlantilla.REPORTE)) {
					XmlReporte xmlConfiguracion = xmlReporteProduces.getConfiguracion();
					List<Columna> columnaReporte = new LinkedList<>();
					for (MallaReporte mallaReporte : xml.getMallaReporte()) {
						boolean columnasCreadas = false;
						for (Registro registro : xml.getRegistro()) {
							if (TipoRegistro.DETALLE.equals(registro.getTipoRegistro())) {
								for (co.gov.dafp.sigep2.util.xml.reporte.config.Parametro parametro : registro.getSQL()
										.getParametro()) {
									Parametro parametroVista = new Parametro(parametro.getNombre().replace(":", ""),
											null);
									parametroVista.setType(parametro.getTipoDato().value());

									for (MallaConfiguracion mallaConfiguracion : xmlConfiguracion
											.getMallaConfiguracion()) {
										if (mallaConfiguracion.getId().equals(mallaReporte.getId())) {
											for (Columna columnaQuery : mallaConfiguracion.getColumna()) {
												
												
												if(!blColumnaFiltroEntidad && columnaQuery.getNombreColumna().equalsIgnoreCase(PARAMETRO_ENTIDAD_ID)){
													blColumnaFiltroEntidad=true;
													strColumnaFiltrarEntidades = mallaConfiguracion.getPrefijoTabla().concat(".").concat(columnaQuery.getNombreColumna());
												}												
												
												
												if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_BOOLEAN
														.equals(parametro.getTipoDato())) {
													parametroVista.setValue("nodata");
												} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_QUERY
														.equals(parametro.getTipoDato())
														&& columnaQuery.getNombreColumna().equalsIgnoreCase(
																parametro.getNombre().replace(":", ""))) {
													if (columnaQuery.getSentencia() != null
															&& !columnaQuery.getSentencia().isEmpty()) {
														parametro.setSentencia(columnaQuery.getSentencia());
													} else {
														parametroVista.setType(
																TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value());
													}
												}
												if (columnaQuery.getId().equals(parametro.getId())) {
													parametro.setEtiquetaColumna(parametro.getEtiquetaColumna());

													for (ValorMalla valorMalla : mallaReporte.getValorMalla()) {
														if (valorMalla.isEsParametro()
																&& valorMalla.getId().equals(columnaQuery.getId())) {

															StringBuilder valorParametro = new StringBuilder("");
															if (valorMalla.getCalificadorParametro() != null
																	&& valorMalla
																			.getValorComparacionParametro() != null) {
																valorParametro.append(
																		valorMalla.getValorComparacionParametro());
																if (valorMalla.getValorComparacionParametro()
																		.isEmpty()) {
																	valorParametro = new StringBuilder();
																	valorParametro.append(":" + columnaQuery
																			.getNombreColumna().toLowerCase());
																} else {
																	parametroVista.setValue(
																			valorMalla.getValorComparacionParametro());
																	parametroVista.setRendered(false);

																	int index = parametros.indexOf(parametroVista);
																	if (index >= 0) {
																		parametros.get(index).setValue(valorMalla
																				.getValorComparacionParametro());
																		parametros.get(index).setRendered(false);
																	}
																}

																if (valorMalla.isRequerido()) {
																	parametroVista
																			.setRequired(valorMalla.isRequerido());
																	int index = parametros.indexOf(parametroVista);
																	if (index >= 0) {
																		parametros.get(index)
																				.setRequired(valorMalla.isRequerido());
																	}
																}
															} else {
																valorParametro.append(":" + columnaQuery
																		.getNombreColumna().toLowerCase());
															}
															ConfiguracionParametro configuracionParametro = parametroVista.new ConfiguracionParametro(
																	parametro.getTipoDato(),
																	mallaConfiguracion.getPrefijoTabla(),
																	columnaQuery.getNombreColumna(),
																	valorParametro.toString(),
																	valorMalla.getCalificadorParametro());
															parametroVista
																	.setConfiguracionParametro(configuracionParametro);

															int index = parametros.indexOf(parametroVista);
															if (index >= 0) {
																parametros.get(index).setConfiguracionParametro(
																		configuracionParametro);
															}

															// Control
															// valores
															// por defecto de
															// sesion

															if (parametro.getNombre()
																	.equals(":" + ReporteBean.PARAMETRO_USUARIO_ID)) {
																parametroVista
																		.setValue(this.getUsuarioSesion().getId());
															}

															if (parametro.getNombre()
																	.equals(":" + ReporteBean.PARAMETRO_ROL_ID)) {
																parametroVista
																		.setValue(this.getRolesUsuarioSesionString());
															}
															break;
														}
													}
													break;
												}
											}
											break;
										}
									}

									parametroVista.setLabel(parametro.getEtiquetaColumna());
									parametroVista.setSource(parametro.getSentencia());
									if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL.equals(xml.getFormaConsulta())
											&& !usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)
											&& PARAMETRO_ENTIDAD_ID.equals(parametroVista.getKey())) {
										parametroVista.setRequired(true);
										parametroVista.setValue(this.getEntidadUsuario().getId());
									} else {
										parametroVista.setRequired(parametro.isRequerido());
									}
									if (!parametros.contains(parametroVista)) {
										parametros.add(parametroVista);
									}
								}

								for (MallaConfiguracion mallaConfiguracion : xmlConfiguracion.getMallaConfiguracion()) {
									for (ValorMalla valorMalla : mallaReporte.getValorMalla()) {
										for (Columna columnaQuery : mallaConfiguracion.getColumna()) {
											if (valorMalla.isMostrarEnReporte()
													&& valorMalla.getId().equals(columnaQuery.getId())
													&& !columnaReporte.contains(columnaQuery)) {
												columnaQuery.setEtiquetaColumna(valorMalla.getNombre());
												columnaQuery.setOrden(valorMalla.getOrden());
												columnaReporte.add(columnaQuery);
												break;
											}
										}
									}
								}

								if (columnaReporte.isEmpty()) {
									mostrarMensaje(FacesMessage.SEVERITY_FATAL,
											MessagesBundleConstants.DLG_HEADER_MENSAJES,
											MessagesBundleConstants.DLG_PROCESO_FALLIDO);
									parametros = new LinkedList<>();
									xml = null;
									return;
								}

								if (registro.getSQL().getParametro().isEmpty()) {
									if (!columnasCreadas) {
										xmlBloque = mallaReporte;
										createDynamicColumns(columnaReporte);
										columnasCreadas = true;
									}
									this.generarReporte();
								}
								if (!columnasCreadas) {
									xmlBloque = mallaReporte;
									createDynamicColumns(columnaReporte);
									columnasCreadas = true;
								}
							}
						}
					}
					
					try {
						lbMostrarFiltroTodasEntidades = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) ;
					} catch (SIGEP2SistemaException e) {
						lbMostrarFiltroTodasEntidades = false;
					}	
					if(!lbMostrarFiltroTodasEntidades && strColumnaFiltrarEntidades!=null && !"".equals(strColumnaFiltrarEntidades))
						strFiltroEntidadesSesion = ReporteBean.generar(strColumnaFiltrarEntidades);
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
				}
			} catch (Exception e) {
				logger.info(msg, e);
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("catalogoBean", this);
		}

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
				ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,
				ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);
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
			return;
		}

		this.init();

	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	public void generarReporte() {
		this.resultados = new ReporteCatalogoLazyDataModel(null);
	}

	public List<Parametro> getListaCSV(String stringCSV) {
		List<Parametro> result = new LinkedList<>();
		String[] stringCSVSplit = stringCSV.split(";");
		for (String item : stringCSVSplit) {
			String[] itemSplit = item.split(",");
			Object key = null;
			Object value = null;
			if (itemSplit.length > 1) {
				key = itemSplit[1].toString();
			} else {
				key = itemSplit[0].toString();
			}
			try {
				value = Long.valueOf(itemSplit[0].toString());
			} catch (NumberFormatException nFEx) {
				value = itemSplit[0].toString();
			} catch (NullPointerException nPEx) {
			}
			Parametro parametro = new Parametro(key, value);
			result.add(parametro);
		}
		return result;
	}

	/**
	 * Valida la configuracion de seguridad del usuario y ejecuta el query para el
	 * parametro del menu que lo requiera
	 * 
	 * @param stringQuery Sentencia SQL a ejecutar
	 * @return {@link List} de {@link Parametro} Lista de registros resultado de la
	 *         ejecucion del query. El resultado viene emparentado <code>id</code> y
	 *         <code>descripcion</code> para ser montado en una lista de seleccion
	 */
	public List<Parametro> getListaQuery(String stringQuery) throws SIGEP2SistemaException {
		if (stringQuery != null && !stringQuery.isEmpty()) {
			Map<Object, Object> parameters = new HashMap<>();

			EntidadProduces entidadProduces = new EntidadProduces();
			List<Parametro> result = new LinkedList<>();

			List<EntidadDTO> entidadesUsuario = null;
			if (FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL.equals(xml.getFormaConsulta())
					|| this.usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
				entidadesUsuario = entidadProduces.getEntidades();
			} else {
				entidadesUsuario = Arrays.asList(this.getEntidadUsuario());
			}

			if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_ENTIDAD_ID)) {
				for (EntidadDTO entidadUsuario : entidadesUsuario) {
					if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_USUARIO_ID)) {
						parameters.put(CatalogoBean.PARAMETRO_USUARIO_ID, this.getUsuarioSesion().getId());
					}

					if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_ENTIDAD_ID)) {
						parameters.put(CatalogoBean.PARAMETRO_ENTIDAD_ID, entidadUsuario.getId());
					}

					StringBuilder reportesCargueBloque = new StringBuilder();

					if (!reportesCargueBloque.toString().isEmpty()) {
						parameters.put(CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(
								reportesCargueBloque.toString().split(SeparadorCsvCaracter.PUNTO_COMA.value())));
					} else if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO)) {
						parameters.put(CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(""));
					}

					List<Object[]> resultTemp = GestionInformacionDelegate.ejecutarQuery(stringQuery, parameters, 0);

					for (Object[] itemTemp : resultTemp) {
						if (itemTemp != null && itemTemp[0] != null && itemTemp[1] != null) {
							Object key = null;
							Object value = null;
							if (itemTemp.length > 1) {
								key = itemTemp[1].toString();
							} else {
								key = itemTemp[0].toString();
							}
							try {
								value = Long.valueOf(itemTemp[0].toString());
							} catch (NumberFormatException nFEx) {
								value = itemTemp[0].toString();
							} catch (NullPointerException nPEx) {
							}
							Parametro item = new Parametro(key, value);
							if (!result.contains(item)) {
								result.add(item);
							}
						}
					}
				}
			} else {
				if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_USUARIO_ID)) {
					parameters.put(CatalogoBean.PARAMETRO_USUARIO_ID, this.getUsuarioSesion().getId());
				}

				StringBuilder reportesCargueBloque = new StringBuilder();

				if (!reportesCargueBloque.toString().isEmpty()) {
					parameters.put(CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays
							.asList(reportesCargueBloque.toString().split(SeparadorCsvCaracter.PUNTO_COMA.value())));
				} else if (stringQuery.contains(":" + CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO)) {
					parameters.put(CatalogoBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(""));
				}

				List<Object[]> resultTemp = GestionInformacionDelegate.ejecutarQuery(stringQuery, parameters, 0);

				for (Object[] itemTemp : resultTemp) {
					if (itemTemp != null && itemTemp[0] != null && itemTemp[1] != null) {
						Object key = null;
						Object value = null;
						if (itemTemp.length > 1) {
							key = itemTemp[1].toString();
						} else {
							key = itemTemp[0].toString();
						}
						try {
							value = Long.valueOf(itemTemp[0].toString());
						} catch (NumberFormatException nFEx) {
							value = itemTemp[0].toString();
						} catch (NullPointerException nPEx) {
						}
						Parametro item = new Parametro(key, value);
						if (!result.contains(item)) {
							result.add(item);
						}
					}
				}
			}
			return result;
		}
		return new LinkedList<>();
	}

	public String getExecQuery(String stringQuery) throws SIGEP2SistemaException {
		String result = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CATALOGO_SIN_RESULTADOS, getLocale());
		if (stringQuery != null && !stringQuery.isEmpty() && stringQuery.startsWith("select")) {
			List<Object[]> resultTemp = GestionInformacionDelegate.ejecutarQuery(stringQuery, null, 0);

			List<String> resultados = new LinkedList<>();
			for (int i = 0; i < resultTemp.size(); i++) {
				Object item = (Object) resultTemp.get(i);
				resultados.add(item.toString());
			}

			if (!resultTemp.isEmpty()) {
				result = "";

				result = result + HTMLUtil.abreListaNoOrdenada;
				for (Object itemTemp : resultados) {
					Object value = null;
					value = itemTemp.toString();
					result = result + HTMLUtil.abreItem + value.toString() + HTMLUtil.cierraItem;
				}
				result = result + HTMLUtil.cierraListaNoOrdenada;
			}
			return result;
		}
		return result;
	}

	private void createDynamicColumns(List<Columna> columns) {
		this.columns = new LinkedList<>();

		int index = 0;
		for (Columna columnKey : columns) {
			this.columns.add(new ColumnModel(index++, columnKey.getEtiquetaColumna(), columnKey.getDescripcion(),
					columnKey.getDescripcion(), columnKey.getNombreColumna(), columnKey.getTipoDato().value(),
					columnKey.getMascara(), columnKey.isTotalizado(),
					columnKey.getOrden() != null ? columnKey.getOrden().longValue() : index++));
			if (!tieneTotales && columnKey.isTotalizado()) {
				tieneTotales = true;
			}
		}

		Collections.sort(this.columns, new Comparator<ColumnModel>() {
			@Override
			public int compare(ColumnModel c1, ColumnModel c2) {
				return c1.getOrder().compareTo(c2.getOrder());
			}
		});

		index = 0;
		for (ColumnModel columnModel : this.columns) {
			columnModel.setIndex(index++);
		}
	}

	public void abrirDetalle(String idTipoRegistroDetalle) {
		this.parametrosDesdePadre = idTipoRegistroDetalle;
		Map<String, List<String>> params = new HashMap<>();
		params.put("id", Arrays.asList(String.valueOf(this.id)));
		params.put("parametrosDesdePadre", Arrays.asList(this.parametrosDesdePadre));
		openDialog("detail", params);
	}

	/**
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla en
	 * componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document Documento Excel en formato xlsx. Es importante que el
	 *                 atributo <code>type</code> del componente de exportación
	 *                 contenga exlusivamente uno de esos dos valores
	 */
	public void postProcessXLS(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 5, xml.getNombre());
	}

	/**
	 * Da formato al documento posterior al exportado de la tabla en componentes de
	 * exportacion de Primefaces a traves de la propiedad <code>postProcessor</code>
	 * 
	 * @param document Documento pdf. Es importante que el atributo
	 *                 <code>type</code> del componente de exportacion contenga
	 *                 exlusivamente este valor
	 */
	public void postProcessPDF(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessPDF(document, 1, xml.getNombre());
	}

	public static final String SELECT = "select";
	public static final String INNER = "inner";
	public static final String LEFT = "left";
	public static final String RIGTH = "rigth";
	public static final String WHERE = "where";
	public static final String AND = "and";
	public static final String OR = "or";
	public static final String UNION = " union ";
	public static final String UNION_ALL = " union all ";
	public static final String GROUP_BY = " group by ";

	/**
	 * Valida la configuracion y concatena el where de la sentencia
	 * 
	 * @param where          {@link StringBuilder} que lleva el valor del where en
	 *                       proceso
	 * @param tipoDato       {@link TipoDato} tipo de dato de la columna
	 * @param prefijoTabla   {@link String} alias de la tabla para relacionar sus
	 *                       columnas
	 * @param nombreColumna  {@link String} nombre de la columa a concatenar
	 * @param valorParametro {@link String} valor que llevaria la columna en el
	 *                       where de la columna
	 * 
	 * @return {@link StringBuilder} valores concatenados al where
	 */
	public StringBuilder concatenarWhere(StringBuilder where, TipoDato tipoDato, String prefijoTabla,
			String nombreColumna, String valorParametro, CalificadorComparacion calificadorComparacion) {
		if (where == null || prefijoTabla == null || nombreColumna == null || valorParametro == null) {
			throw new IllegalArgumentException();
		}
		final String comparador = " COMPARADOR ";
		final String operadorLogico = AND + " ";
		if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_QUERY.equals(tipoDato)) {
			where.append(operadorLogico);
			where.append(prefijoTabla.toLowerCase());
			where.append(".");
			where.append(nombreColumna.toLowerCase());
			if (CalificadorComparacion.TTL_REPORTES_DIFERENTE_A.equals(calificadorComparacion)) {
				where.append(" not");
			}
			where.append(" in ");
			where.append("(" + valorParametro + ")");
			where.append(" ");
		} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.equals(tipoDato)) {
			if (!CalificadorComparacion.TTL_REPORTES_IGUAL.equals(calificadorComparacion)
					&& !CalificadorComparacion.TTL_REPORTES_DIFERENTE_A.equals(calificadorComparacion)) {
				where.append(operadorLogico);
				if (CalificadorComparacion.TTL_REPORTES_NO_CONTIENE.equals(calificadorComparacion)) {
					where.append("not ");
				}
				where.append("regexp_like (upper(nvl(");
				where.append(prefijoTabla.toLowerCase());
				where.append(".");
				where.append(nombreColumna.toLowerCase());
				where.append(", '*')), upper(");

				String valorParametroTemp = valorParametro;

				if (CalificadorComparacion.TTL_REPORTES_EMPIEZA_POR.equals(calificadorComparacion)) {
					valorParametroTemp = "^" + valorParametroTemp + "[.]*";
				} else if (CalificadorComparacion.TTL_REPORTES_ACABA_POR.equals(calificadorComparacion)) {
					valorParametroTemp = "[.]*" + valorParametroTemp + "$";
				} else if (CalificadorComparacion.TTL_REPORTES_NO_EMPIEZA_POR.equals(calificadorComparacion)) {
					valorParametroTemp = "^[^(" + valorParametroTemp + ")][.]*";
				} else if (CalificadorComparacion.TTL_REPORTES_NO_ACABA_POR.equals(calificadorComparacion)) {
					valorParametroTemp = "[.]*[^(" + valorParametroTemp + ")]$";
				}

				where.append("nvl('" + valorParametroTemp + "', '*')");
				where.append(")) ");
			} else {
				where.append(operadorLogico);
				where.append("upper(");
				where.append(prefijoTabla.toLowerCase());
				where.append(".");
				where.append(nombreColumna.toLowerCase());
				where.append(")");
				where.append(comparador);
				where.append("upper(nvl('" + valorParametro + "', " + prefijoTabla.toLowerCase() + "."
						+ nombreColumna.toLowerCase() + "))");
				where.append(" ");
			}
		} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.equals(tipoDato)) {
			where.append(operadorLogico);
			where.append(prefijoTabla.toLowerCase());
			where.append(".");
			where.append(nombreColumna.toLowerCase());
			where.append(comparador);
			where.append("nvl(to_timestamp('" + valorParametro + "', 'DD-MM-YYYY'), " + prefijoTabla.toLowerCase() + "."
					+ nombreColumna.toLowerCase() + ")");
			where.append(" ");
		} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE_LARGE.equals(tipoDato)) {
			where.append(operadorLogico);
			where.append(prefijoTabla.toLowerCase());
			where.append(".");
			where.append(nombreColumna.toLowerCase());
			where.append(comparador);
			where.append("nvl(to_timestamp('" + valorParametro + "', 'DD-MM-YYYY HH24:MI:SS'), "
					+ prefijoTabla.toLowerCase() + "." + nombreColumna.toLowerCase() + ")");
			where.append(" ");
		} else {
			where.append(operadorLogico);
			where.append(prefijoTabla.toLowerCase());
			where.append(".");
			where.append(nombreColumna.toLowerCase());
			where.append(comparador);
			where.append("nvl(" + valorParametro + ", " + prefijoTabla.toLowerCase() + "." + nombreColumna.toLowerCase()
					+ ")");
			where.append(" ");
		}
		return where;
	}

	public boolean isLbAplicarFiltroTodasEntidades() {
		return lbAplicarFiltroTodasEntidades;
	}
	public void setLbAplicarFiltroTodasEntidades(boolean lbAplicarFiltroTodasEntidades) {
		this.lbAplicarFiltroTodasEntidades = lbAplicarFiltroTodasEntidades;
	}
	public boolean isLbMostrarFiltroTodasEntidades() {
		return lbMostrarFiltroTodasEntidades;
	}
	public void setLbMostrarFiltroTodasEntidades(boolean lbMostrarFiltroTodasEntidades) {
		this.lbMostrarFiltroTodasEntidades = lbMostrarFiltroTodasEntidades;
	}

	public List<EntidadDTO> getLstEntidadesUsuario() {
		return lstEntidadesUsuario;
	}
	public void setLstEntidadesUsuario(List<EntidadDTO> lstEntidadesUsuario) {
		this.lstEntidadesUsuario = lstEntidadesUsuario;
	}
	
	public void abrinVinculo(String strVinculo){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(strVinculo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	
	
	

}
