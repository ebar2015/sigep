package co.gov.dafp.sigep2.datamodel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.deledago.GestionInformacionDelegate;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.GraficoBean;
import co.gov.dafp.sigep2.mbean.ReporteBean;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.AsyncReport;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.StringUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.Columna;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;

@ManagedBean
public class ReporteLazyDataModel extends LazyDataModel<Registro> {
	private static final long serialVersionUID = 9020784816789104644L;

	transient List<Registro> datasource;

	private ReporteBean reporteBean;

	private boolean resetearBusqueda = false;

	private List<SortMeta> multiSortMetaTable = new LinkedList<>();
	
	public ReporteLazyDataModel() {
		this.resetearBusqueda = true;
		this.multiSortMetaTable = new LinkedList<>();
		ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
		if (reporteBean == null) {
			reporteBean = (ReporteBean) eLContext.getELResolver().getValue(eLContext, null, "reporteBean");
		}
		DataTable resultadosDataTable = (DataTable) reporteBean.findComponent("resultados");
		resultadosDataTable.setMultiSortMeta(null);
	}

	public ReporteLazyDataModel(List<Registro> datasource) {
		this.resetearBusqueda = true;
		this.datasource = datasource;
		this.resetearBusqueda = true;
		this.multiSortMetaTable = new LinkedList<>();
		if (this.datasource == null) {
			this.datasource = new LinkedList<>();
		}
		ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
		if (reporteBean == null) {
			reporteBean = (ReporteBean) eLContext.getELResolver().getValue(eLContext, null, "reporteBean");
		}
		DataTable resultadosDataTable = (DataTable) reporteBean.findComponent("resultados");
		resultadosDataTable.setMultiSortMeta(null);
	}

	@Override
	public Object getRowKey(Registro object) {
		return object.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Registro getRowData(String rowKey) {
		List<Registro> registros = (List<Registro>) getWrappedData();
		for (Registro ar : registros) {
			if (String.valueOf(ar.getId()).equals(rowKey))
				return ar;
		}
		return null;
	}

	@Override
	public List<Registro> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		Logger logger = Logger.getInstance(getClass());
		try {
			boolean tienePermiso = FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_PORTAL
					.equals(reporteBean.getXml().getFormaConsulta());
			if (!tienePermiso) {
				for (String rol : reporteBean.getXml().getRol()) {
					if (reporteBean.usuarioTieneRolAsignado(RolDTO.SUPER_ADMINISTRADOR, rol)) {
						tienePermiso = true;
						break;
					}
				}
			}
			if (!tienePermiso) {
				reporteBean.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.getStringMessagesBundle(
								MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, reporteBean.getLocale()));
				return new LinkedList<>();
			}
		} catch (Exception e) {
			reporteBean.mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_FALLIDO,
							reporteBean.getLocale()));
			return new LinkedList<>();
		}

		int firstTemp = first;
		List<SortMeta> multiSortMetaTemp = multiSortMeta;
		if (resetearBusqueda) {
			firstTemp = 0;
			resetearBusqueda = false;
			multiSortMetaTemp = new LinkedList<>();
		}
		co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroDetalle = null;
		co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistroTotal = null;
		List<Columna> columnas = null;
		try {
			boolean parametrosDiligenciados = resetearBusqueda;
			reporteBean.setTotales(new LinkedList<>());
			
			for (co.gov.dafp.sigep2.util.xml.reporte.config.Registro r : reporteBean.getXml().getRegistro()) {
				if (TipoRegistro.DETALLE.equals(r.getTipoRegistro())) {
					tipoRegistroDetalle = r;
				} else if (TipoRegistro.PIE.equals(r.getTipoRegistro())) {
					tipoRegistroTotal = r;
				}
			}
			columnas = reporteBean.getXml().getRegistro().get(0).getColumna();
			for (Columna col : columnas) {
				Parametro total = new Parametro(col.getEtiquetaColumna(), 0);
				reporteBean.getTotales().add(total);
			}
			boolean hayCamposObligatorios = false;
			String unoAuno = " where 1 = 1 ";
			StringBuilder where = new StringBuilder(unoAuno);

			List<Parametro> parametrosConsulta = new LinkedList<>();
			boolean reporteSinParametros = reporteBean.getParametros().isEmpty();
			boolean reporteConUnParametros = reporteBean.getParametros().size() == 1;
			for (Parametro p : reporteBean.getParametros()) {
				Parametro parametroConsulta = new Parametro(p);
				boolean agregarAlWhere = false;
				boolean agregarParametro = true;

				if (parametroConsulta.getValue() != null && !parametroConsulta.toString().isEmpty()) {
					if (parametroConsulta.isRendered()) {
						parametrosDiligenciados = true;
					}
					agregarAlWhere = true;
					if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_STRING.value().equals(parametroConsulta.getType())) {
						if (!parametroConsulta.toString().equals("*") && !parametroConsulta.toString().contains("|")) {
							if (!CalificadorComparacion.TTL_REPORTES_IGUAL
									.equals(parametroConsulta.getConfiguracionParametro().getCalificadorComparacion())
									&& !CalificadorComparacion.TTL_REPORTES_DIFERENTE_A.equals(
											parametroConsulta.getConfiguracionParametro().getCalificadorComparacion())
									&& !CalificadorComparacion.TTL_REPORTES_NO_EMPIEZA_POR.equals(
											parametroConsulta.getConfiguracionParametro().getCalificadorComparacion())
									&& !CalificadorComparacion.TTL_REPORTES_NO_ACABA_POR.equals(parametroConsulta
											.getConfiguracionParametro().getCalificadorComparacion())) {
								parametroConsulta.setValue(StringUtil.textoInflexivo(parametroConsulta.toString()));
							} else {
								parametroConsulta.setValue(p.toString());
							}
						}
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.value().equals(parametroConsulta.getType())
							|| TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_PERCENT.value()
									.equals(parametroConsulta.getType())) {
						parametroConsulta.setValue(Double.valueOf(parametroConsulta.getValue().toString()));
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value().equals(parametroConsulta.getType())
							|| TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_ANIO.value().equals(parametroConsulta.getType())) {
						parametroConsulta.setValue(Integer.valueOf(parametroConsulta.getValue().toString()));
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value().equals(parametroConsulta.getType())) {
						SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
						parametroConsulta.setValue(format.format((Date) parametroConsulta.getValue()));
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE_LARGE.value()
							.equals(parametroConsulta.getType())) {
						SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_HORA_FORMATO);
						parametroConsulta.setValue(format.format((Date) parametroConsulta.getValue()));
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_BOOLEAN.value()
							.equals(parametroConsulta.getType())) {
						if (parametroConsulta.getValue() != null) {
							try {
								Boolean bol = Boolean.valueOf(parametroConsulta.getValue().toString());
								if (bol) {
									parametroConsulta.setValue(BigInteger.ONE);
								} else {
									parametroConsulta.setValue(BigInteger.ZERO);
								}
							} catch (Exception e) {
								parametroConsulta.setValue(parametroConsulta.getValue().toString().toLowerCase());
							}
							agregarParametro = false;
						} else {
							parametroConsulta.setValue("nodata");
						}
					}
				} else {
					agregarParametro = false;
				}

				if (agregarAlWhere) {
					parametroConsulta.getConfiguracionParametro().setValorParametro(parametroConsulta.toString());
					where = reporteBean.concatenarWhere(where,
							parametroConsulta.getConfiguracionParametro().getTipoDato(),
							parametroConsulta.getConfiguracionParametro().getPrefijoTabla(),
							parametroConsulta.getConfiguracionParametro().getNombreColumna(),
							parametroConsulta.getConfiguracionParametro().getValorParametro(),
							parametroConsulta.getConfiguracionParametro().getCalificadorComparacion());

					try {
						String whereTemp = parametroConsulta.getConfiguracionParametro().getComparador() != null
								&& where.toString().contains("COMPARADOR")
										? where.toString().replace("COMPARADOR",
												parametroConsulta.getConfiguracionParametro().getComparador())
										: where.toString();
						where = new StringBuilder(whereTemp);
					} catch (IndexOutOfBoundsException e) {

					}
					if (agregarParametro) {
						parametrosConsulta.add(parametroConsulta);
					}
				}
				if (parametroConsulta.isRequired() && !hayCamposObligatorios) {
					hayCamposObligatorios = true;
				}
				if (reporteConUnParametros && !parametroConsulta.isRequired()) {
					hayCamposObligatorios = true;
					parametrosDiligenciados = true;
				}
			}

			if (reporteSinParametros) {
				parametrosDiligenciados = true;
			}

			// Ordenamiento de los registros en la vista
			if (multiSortMetaTemp != null) {
				reporteBean.getXml().unsetOrdenamiento();

				for (SortMeta sortMeta : multiSortMetaTemp) {
					if (!multiSortMetaTable.contains(sortMeta)) {
						multiSortMetaTable.add(sortMeta);
					}
				}

				for (SortMeta sortMeta : multiSortMetaTable) {
					reporteBean.getXml().getOrdenamiento().addOrdenamiento(sortMeta.getSortField(),
							sortMeta.getSortOrder().name());
				}
			}

			DataTable resultadosDataTable = (DataTable) reporteBean.findComponent("resultados");
			resultadosDataTable.setMultiSortMeta(multiSortMetaTable);

			if (parametrosDiligenciados ||reporteBean.isLbAplicarFiltroTodasEntidades()) {
				reporteBean.setTotales(new LinkedList<Parametro>());
				reporteBean.setParametros(reporteBean.getParametros());
				String select = tipoRegistroDetalle.getSQL().getSentencia().replace("select distinct * from (select ",
						"select * from (select distinct ");
				final String report = ") reporte";
				if (select.contains(ReporteBean.WHERE)) {
					try {
						String whereSubString = select.substring(select.indexOf(ReporteBean.WHERE),
								select.indexOf(report));
						if (unoAuno.equals(where.toString())) {
							where = new StringBuilder();
						}

						/*concatenar entidades de ser necesario*/
						if(!reporteBean.isLbMostrarFiltroTodasEntidades() && ReporteBean.strFiltroEntidadesSesion !=null
								&& !"".equals(ReporteBean.strFiltroEntidadesSesion)){
							where.append(" and ".concat(ReporteBean.strFiltroEntidadesSesion));	
						}
						
						select = select.replace(whereSubString,
								where + reporteBean.getXml().getOrdenamiento().toString());
					} catch (IndexOutOfBoundsException e) {
					}
				} else if (!where.toString().equals(unoAuno)) {
					/*concatenar entidades de ser necesario*/
					if(!reporteBean.isLbMostrarFiltroTodasEntidades() && ReporteBean.strFiltroEntidadesSesion !=null
							&& !"".equals(ReporteBean.strFiltroEntidadesSesion)){
						where.append(" and ".concat(ReporteBean.strFiltroEntidadesSesion));
						
					}					
					
					if (select.contains(ReporteBean.GROUP_BY)) {
						select = select.replace(report, "").replace(ReporteBean.GROUP_BY,
								where + reporteBean.getXml().getOrdenamiento().toString() + ReporteBean.GROUP_BY)
								+ report;
					} else {
						select = select.replace(report,
								where + reporteBean.getXml().getOrdenamiento().toString() + report);
					}
				}
				
				
				
				
				
				tipoRegistroDetalle.getSQL().setSentencia(select);

				datasource = GestionInformacionDelegate.exec(tipoRegistroDetalle, reporteBean.getXml(),
						reporteBean.getUsuarioSesion(), reporteBean.getParametros(), DateUtils.getFechaSistema(),
						ConfigurationBundleConstants.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE),
						firstTemp, pageSize, false);
				setRowCount(GestionInformacionDelegate.getRowsCount());

				List<Registro> totales = new LinkedList<>();
				if (tipoRegistroTotal != null) {
					select = tipoRegistroDetalle.getSQL().getSentencia();
					if (select.contains(ReporteBean.WHERE)) {
						try {
							String whereSubString = select.substring(select.indexOf(ReporteBean.WHERE),
									select.indexOf(report));
							if (unoAuno.equals(where.toString())) {
								where = new StringBuilder();
							}
							select = select.replace(whereSubString, where);
						} catch (IndexOutOfBoundsException e) {
						}
					} else if (!where.toString().equals(unoAuno)) {
						if (select.contains(ReporteBean.GROUP_BY)) {
							select = select.replace(report, "").replace(ReporteBean.GROUP_BY,
									where + ReporteBean.GROUP_BY) + report;
						} else {
							select = select.replace(report, where + report);
						}
					}

					totales = GestionInformacionDelegate.exec(
							tipoRegistroTotal, reporteBean.getXml(), reporteBean.getUsuarioSesion(),
							reporteBean.getParametros(), DateUtils.getFechaSistema(), ConfigurationBundleConstants
									.getRutaArchivo(ConfigurationBundleConstants.CNS_ARCHIVO_REPORTE),
							firstTemp, 0, false);
				}
				if (!datasource.isEmpty()) {
//					setRowCount(datasource.get(0).getTotalRegistros().intValue());
				} else {
					setRowCount(0);
				}

				ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
				GraficoBean graficoBean = (GraficoBean) eLContext.getELResolver().getValue(eLContext, null,
						"graficoBean");
				graficoBean.setTotales(null);

				boolean hayTotales = false;
				for (Columna col : columnas) {
					Object sumatoria = null;
					if (col.isTotalizado() && !col.isGraficar()) {
						if (!hayTotales) {
							hayTotales = true;
						}
						sumatoria = 0;
						for (Registro registro : totales) {
							sumatoria = NumeroUtil.sumatoria(sumatoria,
									registro.getItem().get(columnas.indexOf(col)).getValue());
						}
					} else if (col.isGraficar()) {
						sumatoria = new LinkedList<String>();
						for (Registro registro : totales) {
							((List<String>) sumatoria)
									.add((String) registro.getItem().get(columnas.indexOf(col)).getValue());
						}
					}
					Parametro total = new Parametro(col.getEtiquetaColumna(), sumatoria);
					total.setType(col.getTipoDato().value());
					reporteBean.getTotales().add(total);
					graficoBean.getTotales().add(total);
				}

				if (!hayTotales) {
					reporteBean.setTotales(new LinkedList<Parametro>());
				}
			} else {
				datasource = new LinkedList<>();
				if (!hayCamposObligatorios) {
					throw new SIGEP2NegocioException(MessagesBundleConstants.getStringMessagesBundle(
							MessagesBundleConstants.MSG_WARN_FILTROS_VACIOS, reporteBean.getLocale()));
				} else {
					reporteBean.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.MSG_WARN_FILTROS_VACIOS, reporteBean.getLocale()));
				}

				return datasource;
			}
		} catch (Exception e) {
			datasource = new LinkedList<>();
			if (e instanceof SIGEP2NegocioException) {
				reporteBean.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						e.getMessage());
			} else {
				reporteBean.mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			}
			logger.error(getClass() + ".load", e);
			return datasource;
		}
		if (datasource.isEmpty()) {
			reporteBean.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_SIN_REGISTROS,
							reporteBean.getLocale()));
		}
		reporteBean.setDatasource(datasource);
		int totalReg = 0;
		try {
			totalReg = Integer.parseInt(ComunicacionServiciosSis.
					getParametricaporId(new BigDecimal(TipoParametro.MAX_REG_REPORTE.getValue())).getValorParametro());
		} catch (NumberFormatException e) {
			totalReg = 100;
		}
		if(getRowCount() > totalReg) {
			String MSG_MAX_REG_REPORTE = ComunicacionServiciosSis.
					getParametricaporId(new BigDecimal(TipoParametro.MSG_MAX_REG_REPORTE.getValue())).getValorParametro().replace("#total", totalReg+"");
			
			reporteBean.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.getStringMessagesBundle(MSG_MAX_REG_REPORTE, reporteBean.getLocale()));
			    UsuarioDTO usr = reporteBean.getUsuarioSesion();
				Persona persona = ComunicacionServiciosHV.getPersonaPorId(reporteBean.getUsuarioSesion().getCodPersona());
				AsyncReport.starTask(tipoRegistroDetalle, columnas, reporteBean.getXml().getNombre(), persona, reporteBean, getRowCount(), usr);
			datasource = new LinkedList<>();
			
		}
		return datasource;
	}
}
