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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.datamodel.ReporteLazyDataModel;
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
@ConversationScoped
public class ReporteBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -1313157976177138029L;

	transient MallaReporte xmlBloque;
	private XmlReporte xml;
	private List<Parametro> parametros;

	private List<ColumnModel> columns;
	private ReporteLazyDataModel resultados;
	private List<Parametro> totales;
	transient List<co.gov.dafp.sigep2.util.Registro> datasource;
	transient List<Registro> registrosFiltrados;
	private String parametrosDesdePadre;
	private UsuarioDTO usuarioId;

	private boolean habilitarBotonesExportar = true;
	private boolean actualizarFormularioConsultarDetalle = false;

	static final String PARAMETRO_USUARIO_ID = "cod_usuario";
	static final String PARAMETRO_ENTIDAD_ID = "cod_entidad";
	static final String PARAMETRO_ROL_ID = "rol_id";

	private static final String PARAMETRO_REPORTES_CARGUE_ARCHIVO = "reportesCargueBloque";

	private boolean tieneTotales = false;

	private String mensajeValidaciones = MessagesBundleConstants
			.getStringMessagesBundle(MessagesBundleConstants.DLG_SIN_REGISTROS, getLocale());
	
	
	/*Variables para filtro por defecto con entidades del usuario en sesión*/
	private boolean lbAplicarFiltroTodasEntidades,lbMostrarFiltroTodasEntidades;
	public static String  strFiltroEntidadesSesion,strColumnaFiltrarEntidades;
	List<EntidadDTO> lstEntidadesUsuario;
	
	private String strTextoTituloEncabezadoReporte, strTextoSubTituloEncabezadoReporte;
	

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

	public ReporteLazyDataModel getResultados() {
		return resultados;
	}

	public void setResultados(ReporteLazyDataModel resultados) {
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
	@PostConstruct
	public void init() {
		String msg = "void init()";
		parametros = new LinkedList<>();
		registrosFiltrados = new LinkedList<>();
		boolean blColumnaFiltroEntidad = false;

		if (this.id != null) {
			try {
				XmlReporteProduces xmlReporteProduces = new XmlReporteProduces();
				List<XmlReporte> reportes;
				if (recursoId.startsWith("GestionarReporte")) {
					if (recursoId.equals("GestionarReporteConsultarTag")) {
						reportes = xmlReporteProduces.getPlantillasReportes();
					} else {
						return;
					}
				} else if (recursoId.equals("ReporteSIGEPCifrasTag")) {
					reportes = xmlReporteProduces.getReportesPublicadosPortal();
				} else {
					reportes = xmlReporteProduces.getReportesPublicadosCatalogo();
				}
				if (!reportes.isEmpty()) {
					for (XmlReporte reporte : reportes) {
						if (reporte.getId().longValue() == id.longValue()) {
							this.xml = reporte;
							break;
						}
					}
				}

				if (xml == null) {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.DLG_CONFIGURATION_INVALID);
					return;
				}

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
				this.resultados = new ReporteLazyDataModel(null);

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

									for (MallaConfiguracion mallaConfiguracion : xmlConfiguracion.getMallaConfiguracion()) {
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
				} else {
					mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_CARGAR_PLANTILLA_REPORTE);
				}
			} catch (Exception e) {
				logger.info(msg, e);
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reporteBeanS", this);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("detalleReporteBeanS",
					this.getDetalleReporteBean());
		}
		try {
			strFiltroEntidadesSesion="";
			lbMostrarFiltroTodasEntidades = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL) ;
		} catch (SIGEP2SistemaException e) {
			lbMostrarFiltroTodasEntidades = false;
		}
		
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
		EntidadProduces entidadProduces = new EntidadProduces();
		try {
			
			
			
			if(lstEntidadesUsuario==null || lstEntidadesUsuario.isEmpty())
				lstEntidadesUsuario = entidadProduces.getEntidadesUsuario();
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		if(!lbMostrarFiltroTodasEntidades && strColumnaFiltrarEntidades!=null &&!"".equals(strColumnaFiltrarEntidades))
			//strFiltroEntidadesSesion = getStringColumnaEntidadesSesion(lstEntidadesUsuario, strColumnaFiltrarEntidades);
			strFiltroEntidadesSesion = generar(strColumnaFiltrarEntidades);
		this.resultados = new ReporteLazyDataModel(null);
	}

	/**Metodo utilizado para obtener el usuario que se encuentra actualmente en sesion*/
	public static String generar(String filtro) {
		strColumnaFiltrarEntidades = filtro;
		Long v_usarioSesion = null;
		String strRetorno="";
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		UsuarioDTO usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		if (usuarioSesion != null) {
			v_usarioSesion = usuarioSesion.getId();
			strRetorno = strRetorno.concat(strColumnaFiltrarEntidades).concat(" in (").concat("select cod_entidad from usuario_entidad where cod_usuario = ").concat(v_usarioSesion +  "").concat(")");
		}
		return strRetorno;	
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

			if (stringQuery.contains(":" + ReporteBean.PARAMETRO_ENTIDAD_ID)) {
				for (EntidadDTO entidadUsuario : entidadesUsuario) {
					if (stringQuery.contains(":" + ReporteBean.PARAMETRO_USUARIO_ID)) {
						parameters.put(ReporteBean.PARAMETRO_USUARIO_ID, this.getUsuarioSesion().getId());
					}

					if (stringQuery.contains(":" + ReporteBean.PARAMETRO_ENTIDAD_ID)) {
						parameters.put(ReporteBean.PARAMETRO_ENTIDAD_ID, entidadUsuario.getId());
					}

					StringBuilder reportesCargueBloque = new StringBuilder();

					if (!reportesCargueBloque.toString().isEmpty()) {
						parameters.put(ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(
								reportesCargueBloque.toString().split(SeparadorCsvCaracter.PUNTO_COMA.value())));
					} else if (stringQuery.contains(":" + ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO)) {
						parameters.put(ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(""));
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
				if (stringQuery.contains(":" + ReporteBean.PARAMETRO_USUARIO_ID)) {
					parameters.put(ReporteBean.PARAMETRO_USUARIO_ID, this.getUsuarioSesion().getId());
				}

				StringBuilder reportesCargueBloque = new StringBuilder();

				if (!reportesCargueBloque.toString().isEmpty()) {
					parameters.put(ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays
							.asList(reportesCargueBloque.toString().split(SeparadorCsvCaracter.PUNTO_COMA.value())));
				} else if (stringQuery.contains(":" + ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO)) {
					parameters.put(ReporteBean.PARAMETRO_REPORTES_CARGUE_ARCHIVO, Arrays.asList(""));
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
		String result = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_SIN_REGISTROS,
				getLocale());
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
	
	public void abrinVinculo(String strVinculo){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(strVinculo);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	/*
	public static String getStringColumnaEntidadesSesion(List<EntidadDTO> lstEntidades, String strColumnaFiltro){
		String codigosEntidad, strRetorno="";
		int contador =0;
		boolean reiniciarAnd= false;
		if(lstEntidades.size()==1){
			codigosEntidad = String.valueOf((lstEntidades.get(0).getId()));
			strRetorno = strRetorno.concat(strColumnaFiltro).concat(" in (").concat(codigosEntidad).concat(")");
		}else{
			for(EntidadDTO entidad:lstEntidades){
				codigosEntidad = String.valueOf((entidad.getId()));
				if(contador==0){
					strRetorno = strRetorno.concat(strColumnaFiltro).concat(" in (").concat(codigosEntidad).concat(",");
				}else{
					if(contador<lstEntidades.size()  && !(contador % 800 ==0) ){
						if(reiniciarAnd){
							strRetorno = strRetorno.concat(" or ".concat(strColumnaFiltro).concat("   in ( ").concat(codigosEntidad).concat(","));
							reiniciarAnd= false;
						}else{
							
							strRetorno = strRetorno.concat(codigosEntidad).concat(",");	
						}
					}else{
						strRetorno = strRetorno.concat(codigosEntidad).concat(")");
						reiniciarAnd=true;
					}
				}
				contador++;
			}
			if(!reiniciarAnd)
				strRetorno = strRetorno.concat("null)");
		}
		return strRetorno;
	}
	*/
}
