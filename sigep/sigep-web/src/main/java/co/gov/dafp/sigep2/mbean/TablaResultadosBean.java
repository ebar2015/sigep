package co.gov.dafp.sigep2.mbean;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.entities.Bilinguismo;
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entities.Empleo;
import co.gov.dafp.sigep2.entities.EstrategiaTalentoHumano;
import co.gov.dafp.sigep2.entities.LeyCuotas;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.PlanAnualVacantes;
import co.gov.dafp.sigep2.entities.Seguimiento;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosGI;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.StringUtil;

@Named
@ConversationScoped
@ManagedBean
public class TablaResultadosBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -5062897297221750023L;

	private CifrasEmpleoPublico cifraSeleccionada;
	private CifrasEmpleoPublico filtro;
	private List<CifrasEmpleoPublico> listaCifras;

	private List<PlanAnualVacantes> listaPlanAnualVacantes;
	private PlanAnualVacantes planAnualVacantes;

	private List<LeyCuotas> listaLeyCuotas;
	private LeyCuotas leyCuotas;

	private List<Empleo> listaEmpleos;
	private Empleo empleos;

	private List<Seguimiento> listaSeguimiento;
	private List<Seguimiento> listaSeguimientoClasificacion;
	private Seguimiento seguimiento;

	private List<EstrategiaTalentoHumano> listaAcuerdosGestion;
	private List<EstrategiaTalentoHumano> listaBienestarIncentivos;

	private List<EstrategiaTalentoHumano> listaCapacitacion;

	private List<EstrategiaTalentoHumano> listaHorariosFlexibles;

	private List<EstrategiaTalentoHumano> listaTeletrabajo;

	private List<EstrategiaTalentoHumano> listaBilinguismo;

	private List<Bilinguismo> listaBilinguismoDetalle;

	transient UIInput todosUIInput = null;

	private boolean habilitarConsulta = false;

	private boolean todos = false;

	private Long[] sectoresSeleccionados;

	private BarChartModel graficoPAV;
	private BarChartModel graficoLC;

	public CifrasEmpleoPublico getCifraSeleccionada() {
		return cifraSeleccionada;
	}

	public void setCifraSeleccionada(CifrasEmpleoPublico cifraSeleccionada) {
		this.cifraSeleccionada = cifraSeleccionada;
	}

	public CifrasEmpleoPublico getFiltro() {
		return filtro;
	}

	public void setFiltro(CifrasEmpleoPublico filtro) {
		this.filtro = filtro;
	}

	public List<CifrasEmpleoPublico> getListaCifras() {
		return listaCifras;
	}

	public void setListaCifras(List<CifrasEmpleoPublico> listaCifras) {
		this.listaCifras = listaCifras;
	}

	public List<PlanAnualVacantes> getListaPlanAnualVacantes() {
		return listaPlanAnualVacantes;
	}

	public void setListaPlanAnualVacantes(List<PlanAnualVacantes> listaPlanAnualVacantes) {
		this.listaPlanAnualVacantes = listaPlanAnualVacantes;
	}

	public PlanAnualVacantes getPlanAnualVacantes() {
		return planAnualVacantes;
	}

	public void setPlanAnualVacantes(PlanAnualVacantes planAnualVacantes) {
		this.planAnualVacantes = planAnualVacantes;
	}

	public List<LeyCuotas> getListaLeyCuotas() {
		return listaLeyCuotas;
	}

	public void setListaLeyCuotas(List<LeyCuotas> listaLeyCuotas) {
		this.listaLeyCuotas = listaLeyCuotas;
	}

	public LeyCuotas getLeyCuotas() {
		return leyCuotas;
	}

	public void setLeyCuotas(LeyCuotas leyCuotas) {
		this.leyCuotas = leyCuotas;
	}

	public List<Empleo> getListaEmpleos() {
		return listaEmpleos;
	}

	public void setListaEmpleos(List<Empleo> listaEmpleos) {
		this.listaEmpleos = listaEmpleos;
	}

	public Empleo getEmpleos() {
		return empleos;
	}

	public void setEmpleos(Empleo empleos) {
		this.empleos = empleos;
	}

	public List<Seguimiento> getListaSeguimiento() {
		return listaSeguimiento;
	}

	public void setListaSeguimiento(List<Seguimiento> listaSeguimiento) {
		this.listaSeguimiento = listaSeguimiento;
	}

	public List<Seguimiento> getListaSeguimientoClasificacion() {
		return listaSeguimientoClasificacion;
	}

	public void setListaSeguimientoClasificacion(List<Seguimiento> listaSeguimientoClasificacion) {
		this.listaSeguimientoClasificacion = listaSeguimientoClasificacion;
	}

	public Seguimiento getSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(Seguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}

	public List<EstrategiaTalentoHumano> getListaAcuerdosGestion() {
		return listaAcuerdosGestion;
	}

	public void setListaAcuerdosGestion(List<EstrategiaTalentoHumano> listaAcuerdosGestion) {
		this.listaAcuerdosGestion = listaAcuerdosGestion;
	}

	public List<EstrategiaTalentoHumano> getListaBienestarIncentivos() {
		return listaBienestarIncentivos;
	}

	public void setListaBienestarIncentivos(List<EstrategiaTalentoHumano> listaBienestarIncentivos) {
		this.listaBienestarIncentivos = listaBienestarIncentivos;
	}

	public List<EstrategiaTalentoHumano> getListaCapacitacion() {
		return listaCapacitacion;
	}

	public void setListaCapacitacion(List<EstrategiaTalentoHumano> listaCapacitacion) {
		this.listaCapacitacion = listaCapacitacion;
	}

	public List<EstrategiaTalentoHumano> getListaHorariosFlexibles() {
		return listaHorariosFlexibles;
	}

	public void setListaHorariosFlexibles(List<EstrategiaTalentoHumano> listaHorariosFlexibles) {
		this.listaHorariosFlexibles = listaHorariosFlexibles;
	}

	public List<EstrategiaTalentoHumano> getListaTeletrabajo() {
		return listaTeletrabajo;
	}

	public void setListaTeletrabajo(List<EstrategiaTalentoHumano> listaTeletrabajo) {
		this.listaTeletrabajo = listaTeletrabajo;
	}

	public List<EstrategiaTalentoHumano> getListaBilinguismo() {
		return listaBilinguismo;
	}

	public void setListaBilinguismo(List<EstrategiaTalentoHumano> listaBilinguismo) {
		this.listaBilinguismo = listaBilinguismo;
	}

	public List<Bilinguismo> getListaBilinguismoDetalle() {
		return listaBilinguismoDetalle;
	}

	public void setListaBilinguismoDetalle(List<Bilinguismo> listaBilinguismoDetalle) {
		this.listaBilinguismoDetalle = listaBilinguismoDetalle;
	}

	public UIInput getTodosUIInput() {
		return todosUIInput;
	}

	public void setTodosUIInput(UIInput todosUIInput) {
		this.todosUIInput = todosUIInput;
	}

	public boolean isHabilitarConsulta() {
		return habilitarConsulta;
	}

	public void setHabilitarConsulta(boolean habilitarConsulta) {
		this.habilitarConsulta = habilitarConsulta;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public Long[] getSectoresSeleccionados() {
		return sectoresSeleccionados;
	}

	public void setSectoresSeleccionados(Long[] sectoresSeleccionados) {
		this.sectoresSeleccionados = sectoresSeleccionados;
	}

	public BarChartModel getGraficoPAV() {
		
		if (listaPlanAnualVacantes != null) {
			if (graficoPAV == null && !listaPlanAnualVacantes.isEmpty()) {
				graficoPAV = new BarChartModel();
				graficoPAV.setTitle("PLAN ANUAL DE VACANTES");
				graficoPAV.setLegendPosition("n");		
				graficoPAV.setLegendPlacement(LegendPlacement.INSIDE);				
				
				
				for (PlanAnualVacantes pav : listaPlanAnualVacantes) {
												
					ChartSeries pavGrafico = new ChartSeries();
					
					pavGrafico.setLabel(pav.getEtiqueta());
					pavGrafico.set("Total", pav.getTotal());
					
					graficoPAV.addSeries(pavGrafico);
				}	
				
				
			}
					
			return graficoPAV;
		}
		return graficoPAV;			
	}
	
	public void setGraficoPAV(BarChartModel graficoPAV) {
		this.graficoPAV = graficoPAV;
	}

	public BarChartModel getGraficoLC() {
		if (listaPlanAnualVacantes != null) {
			if (graficoLC == null && !listaPlanAnualVacantes.isEmpty()) {
				graficoLC = new BarChartModel();

				graficoLC.setTitle("LEY DE CUOTAS");
				graficoLC.setLegendPosition("n");		
				graficoLC.setLegendPlacement(LegendPlacement.INSIDE);
				
				for (LeyCuotas lc : listaLeyCuotas) {
					ChartSeries lcHombresGrafico = new ChartSeries();
					lcHombresGrafico.setLabel(lc.getEtiqueta());
					lcHombresGrafico.set("Hombre", lc.getNivelDecisorioHombres());
					lcHombresGrafico.set("Mujeres", lc.getNivelDecisorioMujeres());
					lcHombresGrafico.set("Provisto", lc.getNivelDecisorioProvistos());
					lcHombresGrafico.setXaxis(AxisType.X);
					graficoLC.addSeries(lcHombresGrafico);
				}

			}
			return graficoLC;
		}
		return graficoLC;
	}

	public void setGraficoLC(BarChartModel graficoLC) {
		this.graficoLC = graficoLC;
	}

	/**
	 * Inicializa el buscador de cifras de empleo teniendo en cuenta las
	 * restricciones de seguridad por rol para la funcionalidad
	 */
	public void montarDatos() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}

		init();
	}

	/**
	 * Validador del formulario principal. Es invocado posterior a las validaciones
	 * de tipo definidas en cada componenete del mismo
	 * 
	 * @param event
	 *            Contiene los componentes procesados en el formulario una vez se
	 *            genera el submit
	 */
	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		UIComponent components = event.getComponent();
		todosUIInput = (UIInput) components.findComponent("todos");
	}

	/**
	 * Inicializa los valores necesarios para el tablero en el formulario
	 */
	public void init() {
		todos = true;

		this.graficoPAV = null;
		this.graficoLC = null;

		planAnualVacantes = new PlanAnualVacantes();
		leyCuotas = new LeyCuotas();
		empleos = new Empleo();
		seguimiento = new Seguimiento();

		try {

			CifrasEmpleoPublico filtroInit;
			if (this.filtro == null) {
				this.filtro = new CifrasEmpleoPublico();
			}

			if (usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				this.habilitarConsulta = true;
			} else if (usuarioTieneRolAsignado(RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO,
					RolDTO.OPERADOR_TALENTO_HUMANO)) {
				this.habilitarConsulta = false;
				this.filtro.setCodCifrasEmpleoPublico(this.getEntidadUsuario().getId());
			} else {
				return;
			}

			marcarTodos();
			filtroInit = new CifrasEmpleoPublico(this.filtro);

			if (filtroInit.getEntidad() == null || filtroInit.getEntidad().isEmpty()) {
				filtroInit.setEntidad("*");
			} else if (!filtroInit.getEntidad().equals("*") && !filtroInit.getEntidad().contains("|")) {
				filtroInit.setEntidad(StringUtil.textoInflexivo(filtroInit.getEntidad()));
			}

			if (filtroInit.getCodEntidad() == null || filtroInit.getCodEntidad().isEmpty()) {
				filtroInit.setCodEntidad("*");
			} else if (!filtroInit.getCodEntidad().equals("*") && !filtroInit.getCodEntidad().contains("|")) {
				filtroInit.setCodEntidad("^[0]*(" + filtroInit.getCodEntidad() + ")$");
			}

			listaCifras = ComunicacionServiciosGI.getTablaResultadosCifrasEmpleoPublico(filtroInit);

			filtroInit.setLimitIni(0);
			filtroInit.setLimitFin(listaCifras.get(0).getTotal().intValue());
			List<CifrasEmpleoPublico> reportoList = ComunicacionServiciosGI.getCifrasEmpleoPublico(filtroInit);

			final String ttlYes = "TTL_YES";
			BigInteger debeReportarPAV = BigInteger.ZERO;
			BigInteger reportoPAV = BigInteger.ZERO;
			BigInteger debeReportarLC = BigInteger.ZERO;
			BigInteger reportoLC = BigInteger.ZERO;
			BigInteger debeReportarEmp = BigInteger.ZERO;
			BigInteger reportoEmp = BigInteger.ZERO;
			BigInteger debeReportarSeg = BigInteger.ZERO;
			BigInteger reportoSeg = BigInteger.ZERO;
			BigInteger debeReportarAG = BigInteger.ZERO;
			BigInteger reportoAG = BigInteger.ZERO;
			BigInteger debeReportarBeI = BigInteger.ZERO;
			BigInteger adoptoBeI = BigInteger.ZERO;
			BigInteger debeReportarCap = BigInteger.ZERO;
			BigInteger adoptoCap = BigInteger.ZERO;
			BigInteger entidadesPIC = BigInteger.ZERO;
			BigInteger cuentaServidoresBenBecasDAFP = BigInteger.ZERO;
			BigInteger adoptoHF = BigInteger.ZERO;
			BigInteger adoptoTel = BigInteger.ZERO;
			BigInteger adoptoTelN = BigInteger.ZERO;
			BigInteger adoptoTelT = BigInteger.ZERO;
			BigInteger adoptoBil = BigInteger.ZERO;

			final BigInteger territorial = BigInteger.valueOf(1364l);
			final BigInteger nacional = BigInteger.valueOf(1363l);

			for (CifrasEmpleoPublico reporte : reportoList) {
				if (ttlYes.equals(reporte.getDebeReportarPAV())) {
					debeReportarPAV = debeReportarPAV.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getReportoPAV())) {
					reportoPAV = reportoPAV.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getDebeReportarLC())) {
					debeReportarLC = debeReportarLC.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getReportoLC())) {
					reportoLC = reportoLC.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getDebeReportarEmp())) {
					debeReportarEmp = debeReportarEmp.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getDebeReportarAG())) {
					debeReportarAG = debeReportarAG.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getReportoAG())) {
					reportoAG = reportoAG.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getDebeReportarCap())) {
					debeReportarCap = debeReportarCap.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getAdoptoCap())) {
					adoptoCap = adoptoCap.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getDebeReportarBeI())) {
					debeReportarBeI = debeReportarBeI.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getAdoptoCap())) {
					adoptoBeI = adoptoBeI.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getEntidadesPIC())) {
					entidadesPIC = entidadesPIC.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getCuentaServidoresBenBecasDAFP())) {
					cuentaServidoresBenBecasDAFP = cuentaServidoresBenBecasDAFP.add(BigInteger.ONE);
				}
				if (ttlYes.equals(reporte.getAdoptoHF())) {
					adoptoHF = adoptoHF.add(BigInteger.ONE);
				}

				if (ttlYes.equals(reporte.getAdoptoTel())) {
					adoptoTel = adoptoTel.add(BigInteger.ONE);
					if (nacional.equals(reporte.getCodOrden())) {
						adoptoTelN = adoptoTelN.add(BigInteger.ONE);
					} else if (territorial.equals(reporte.getCodOrden())) {
						adoptoTelT = adoptoTelT.add(BigInteger.ONE);
					}
				}

				if (ttlYes.equals(reporte.getCartaBil())) {
					adoptoBil = adoptoBil.add(BigInteger.ONE);
				}
			}

			CifrasEmpleoPublico cifra = listaCifras.get(0);

			// Plan Anual Vacantes
			listaPlanAnualVacantes = new LinkedList<>();
			PlanAnualVacantes encargo = new PlanAnualVacantes();
			encargo.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_ENCARGO, getLocale()));
			encargo.setAsesor(cifra.getEncargoAsesor());
			encargo.setTecnico(cifra.getEncargoTecnico());
			encargo.setProfesional(cifra.getEncargoProfesional());
			encargo.setAsistencial(cifra.getEncargoAsistencial());
			encargo.setTotal(cifra.getEncargoTotal());
			listaPlanAnualVacantes.add(encargo);

			PlanAnualVacantes provisionalidad = new PlanAnualVacantes();
			provisionalidad.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_PROVISIONALIDAD, getLocale()));
			provisionalidad.setAsesor(cifra.getProvisionalidadAsesor());
			provisionalidad.setTecnico(cifra.getProvisionalidadTecnico());
			provisionalidad.setProfesional(cifra.getProvisionalidadProfesional());
			provisionalidad.setAsistencial(cifra.getProvisionalidadAsistencial());
			provisionalidad.setTotal(cifra.getProvisionalidadTotal());
			listaPlanAnualVacantes.add(provisionalidad);

			PlanAnualVacantes porProveer = new PlanAnualVacantes();
			porProveer.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_POR_PROVEER, getLocale()));
			porProveer.setAsesor(cifra.getPorProveerAsesor());
			porProveer.setTecnico(cifra.getPorProveerTecnico());
			porProveer.setProfesional(cifra.getPorProveerProfesional());
			porProveer.setAsistencial(cifra.getPorProveerAsistencial());
			porProveer.setTotal(cifra.getPorProveerTotal());
			listaPlanAnualVacantes.add(porProveer);

			PlanAnualVacantes vacanciaDefinitiva = new PlanAnualVacantes();
			vacanciaDefinitiva.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_VACANCIA_DEFINITIVA, getLocale()));
			vacanciaDefinitiva.setAsesor(cifra.getVacDefinitivaAsesor());
			vacanciaDefinitiva.setTecnico(cifra.getVacDefinitivaTecnico());
			vacanciaDefinitiva.setProfesional(cifra.getVacDefinitivaProfesional());
			vacanciaDefinitiva.setAsistencial(cifra.getVacDefinitivaAsistencial());
			vacanciaDefinitiva.setTotal(cifra.getVacDefinitivaTotal());
			listaPlanAnualVacantes.add(vacanciaDefinitiva);

			// Ley Cuotas
			listaLeyCuotas = new LinkedList<>();
			LeyCuotas maximo = new LeyCuotas();
			maximo.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_MAXIMO_NIVEL_DECISORIO, getLocale()));
			maximo.setNivelDecisorioTotal(cifra.getMaximoNivelDecisorioTotal());
			maximo.setNivelDecisorioVacantes(cifra.getMaximoNivelDecisorioVacantes());
			maximo.setNivelDecisorioProvistos(cifra.getMaximoNivelDecisorioProvistos());
			maximo.setNivelDecisorioMujeres(cifra.getMaximoNivelDecisorioMujeres());
			maximo.setNivelDecisorioHombres(cifra.getMaximoNivelDecisorioHombres());
			maximo.setNivelDecisorioPMujeres(cifra.getMaximoNivelDecisorioPMujeres());
			maximo.setNivelDecisorioPHombres(cifra.getMaximoNivelDecisorioPHombres());
			listaLeyCuotas.add(maximo);

			LeyCuotas otros = new LeyCuotas();
			otros.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_OTRO_NIVEL_DECISORIO, getLocale()));
			otros.setNivelDecisorioTotal(cifra.getOtroNivelDecisorioTotal());
			otros.setNivelDecisorioVacantes(cifra.getOtroNivelDecisorioVacantes());
			otros.setNivelDecisorioProvistos(cifra.getOtroNivelDecisorioProvistos());
			otros.setNivelDecisorioMujeres(cifra.getOtroNivelDecisorioMujeres());
			otros.setNivelDecisorioHombres(cifra.getOtroNivelDecisorioHombres());
			otros.setNivelDecisorioPMujeres(cifra.getOtroNivelDecisorioPMujeres());
			otros.setNivelDecisorioPHombres(cifra.getOtroNivelDecisorioPHombres());
			listaLeyCuotas.add(otros);

			// Empleos
			listaEmpleos = new LinkedList<>();
			Empleo totalPlantaEmp = new Empleo();
			totalPlantaEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_EP_REON, getLocale()));
			totalPlantaEmp.setValor(cifra.getTotalPlantaEmp());
			listaEmpleos.add(totalPlantaEmp);

			Empleo plantaPermanenteEmp = new Empleo();
			plantaPermanenteEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_PLANTA_PERMANENTE, getLocale()));
			plantaPermanenteEmp.setValor(cifra.getPlantaPermanenteEmp());
			listaEmpleos.add(plantaPermanenteEmp);

			Empleo plantaTemporalEmp = new Empleo();
			plantaTemporalEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_PLANTA_TEMPORAL, getLocale()));
			plantaTemporalEmp.setValor(cifra.getPlantaTemporalEmp());
			listaEmpleos.add(plantaTemporalEmp);

			Empleo plantaTransitoriaEmp = new Empleo();
			plantaTransitoriaEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_PLANTA_TRANSITORIA, getLocale()));
			plantaTransitoriaEmp.setValor(cifra.getPlantaTransitoriaEmp());
			listaEmpleos.add(plantaTransitoriaEmp);

			Empleo trabajadoresOficialesEmp = new Empleo();
			trabajadoresOficialesEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_TO_REON, getLocale()));
			trabajadoresOficialesEmp.setValor(cifra.getTrabajadoresOficialesEmp());
			listaEmpleos.add(trabajadoresOficialesEmp);

			Empleo plantaDocentesEmp = new Empleo();
			plantaDocentesEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_DOCENTES_REON, getLocale()));
			plantaDocentesEmp.setValor(cifra.getPlantaDocentesEmp());
			listaEmpleos.add(plantaDocentesEmp);

			Empleo totalPlantaOficiales = new Empleo();
			totalPlantaOficiales.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_EP_TO, getLocale()));
			totalPlantaOficiales.setValor(cifra.getTotalPlantaEmp().add(cifra.getTrabajadoresOficialesEmp()));
			listaEmpleos.add(totalPlantaOficiales);

			Empleo totalPlantaDocentes = new Empleo();
			totalPlantaDocentes.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_EP_DOCENTES, getLocale()));
			totalPlantaDocentes.setValor(cifra.getTotalPlantaEmp().add(cifra.getPlantaDocentesEmp()));
			listaEmpleos.add(totalPlantaDocentes);

			Empleo totalEmp = new Empleo();
			totalEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_EP_TO_REON, getLocale()));
			totalEmp.setValor(cifra.getTotalEmp());
			listaEmpleos.add(totalEmp);

			Empleo plantaPrivadaEmp = new Empleo();
			plantaPrivadaEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_PLANTAS_PRIVADAS_REON, getLocale()));
			plantaPrivadaEmp.setValor(cifra.getPlantaPrivadaEmp());
			listaEmpleos.add(plantaPrivadaEmp);

			Empleo granTotalEmp = new Empleo();
			granTotalEmp.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_TOTAL_EMPLEADOS_PLANTA_PRIVADA, getLocale()));
			granTotalEmp.setValor(cifra.getGranTotalEmp());
			listaEmpleos.add(granTotalEmp);

			// Seguimiento
			listaSeguimiento = new LinkedList<>();
			listaSeguimientoClasificacion = new LinkedList<>();
			List<Parametrica> listaClasificacionOrganica = null;

			listaClasificacionOrganica = ComunicacionServiciosSis
					.getParametricaPorIdPadre(CifrasEmpleoPublicoBean.getClasificacionOrganica());

			Parametrica clasificacionOrganica = null;
			if (!habilitarConsulta) {
				clasificacionOrganica = ComunicacionServiciosSis
						.getParametricaporId(this.getEntidadUsuario().getCodClasificacionOrganica());
			}

			Seguimiento plantaAprobadaReal = new Seguimiento();
			plantaAprobadaReal.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_PLATA_APROBADA_REAL");

			Seguimiento numeroEmpleadosVinculados = new Seguimiento();
			numeroEmpleadosVinculados.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_NUMERO_EMPLEADOS_VINCULADOS");

			Seguimiento vacantesReportaEntidad = new Seguimiento();
			vacantesReportaEntidad.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_VACANTES_REPORTA_ENTIDAD");

			Seguimiento pesoPorcentajeVinculacion = new Seguimiento();
			pesoPorcentajeVinculacion.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_PESO_PORCENTAJE_VINCULACION");

			Seguimiento totalHVActivas = new Seguimiento();
			totalHVActivas.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_TOTAL_HV_ACTIVAS_C");

			Seguimiento numeroContratosVigentes = new Seguimiento();
			numeroContratosVigentes.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_NUMERO_CONTRATOS_VIGENTES");

			Seguimiento pesoPorcentajeContrato = new Seguimiento();
			pesoPorcentajeContrato.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_PESO_PORCENTAJE_CONTRATO");

			Seguimiento numeroVinculadosSobreNumeroCargosPlanta = new Seguimiento();
			numeroVinculadosSobreNumeroCargosPlanta
					.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_NUMERO_VINCULADOS_SOBRE_CARGOS_PLANTA");

			Seguimiento porcentajeVinculacion = new Seguimiento();
			porcentajeVinculacion.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_PORCENTAJE_VINCULACION");

			Seguimiento numeroContratosVigentesSobreNumeroHVActivasContratistas = new Seguimiento();
			numeroContratosVigentesSobreNumeroHVActivasContratistas
					.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_NUMERO_CONT_VIG_SOBRE_HV_ACT_CONT");

			Seguimiento porcentajeContratos = new Seguimiento();
			porcentajeContratos.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_PORCENTAJE_CONTRATOS");

			Seguimiento ipvc = new Seguimiento();
			ipvc.setEtiqueta("TTL_GI_TABLERO_CEP_SEG_IPVC");

			for (Parametrica co : listaClasificacionOrganica) {
				Seguimiento seguimientoClasificacion = new Seguimiento();
				seguimientoClasificacion.setCodCifrasEmpleoPublico(co.getCodTablaParametrica().longValue());
				seguimientoClasificacion
						.setEntidad(StringUtil.UppercaseFirstLetters(co.getNombreParametro().toLowerCase()));
				listaSeguimientoClasificacion.add(seguimientoClasificacion);

				CifrasEmpleoPublico filtroCEPClasificacion = new CifrasEmpleoPublico(filtroInit);
				filtroCEPClasificacion.setCodClasificacionOrganica(co.getCodTablaParametrica().toBigInteger());
				List<CifrasEmpleoPublico> listaCifrasClasificacion = ComunicacionServiciosGI
						.getTablaResultadosCifrasEmpleoPublico(filtroCEPClasificacion);

				CifrasEmpleoPublico cepClasificacion = null;
				if (!habilitarConsulta && clasificacionOrganica.getCodTablaParametrica() != null
						&& !co.getCodTablaParametrica().equals(clasificacionOrganica.getCodTablaParametrica())) {
					cepClasificacion = new CifrasEmpleoPublico();
					cepClasificacion.setPlataAprobadaReal(BigInteger.ZERO);
					cepClasificacion.setNumeroEmpleadosVinculados(BigInteger.ZERO);
					cepClasificacion.setVacantesReportaEntidad(BigInteger.ZERO);
					cepClasificacion.setPesoPorcentajeVinculacion(BigDecimal.ZERO);
					cepClasificacion.setTotalHVActivasEmpleadosPublicos(BigInteger.ZERO);
					cepClasificacion.setTotalActivasContratistas(BigInteger.ZERO);
					cepClasificacion.setNumeroContratosVigentes(BigInteger.ZERO);
					cepClasificacion.setPesoPorcentajeContrato(BigDecimal.ZERO);
					cepClasificacion.setNumeroVinculadosSobreNumeroCargosPlanta(BigDecimal.ZERO);
					cepClasificacion.setPorcentajeVinculacion(BigDecimal.ZERO);
					cepClasificacion.setNumeroContratosVigentesSobreNumeroHVActivasContratistas(BigDecimal.ZERO);
					cepClasificacion.setPorcentajeContratos(BigDecimal.ZERO);
					cepClasificacion.setIpvc(BigDecimal.ZERO);
				} else {
					cepClasificacion = listaCifrasClasificacion.get(0);
				}

				plantaAprobadaReal.getListaValor()
						.add(BigDecimal.valueOf(cepClasificacion.getPlataAprobadaReal().intValue()));
				numeroEmpleadosVinculados.getListaValor().add(numeroEmpleadosVinculados.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getNumeroEmpleadosVinculados().intValue())));
				vacantesReportaEntidad.getListaValor().add(vacantesReportaEntidad.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getVacantesReportaEntidad().intValue())));
				pesoPorcentajeVinculacion.getListaValor().add(pesoPorcentajeVinculacion.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getPesoPorcentajeVinculacion().intValue())));
				totalHVActivas.getListaValor().add(totalHVActivas.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getTotalHVActivasEmpleadosPublicos().intValue()))
						.add(BigDecimal.valueOf(cepClasificacion.getTotalActivasContratistas().intValue())));
				numeroContratosVigentes.getListaValor().add(numeroContratosVigentes.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getNumeroContratosVigentes().intValue())));
				pesoPorcentajeContrato.getListaValor().add(pesoPorcentajeContrato.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getPesoPorcentajeContrato().intValue())));

				numeroVinculadosSobreNumeroCargosPlanta.getListaValor()
						.add(numeroVinculadosSobreNumeroCargosPlanta.getValor()
								.add(BigDecimal.valueOf(
										cepClasificacion.getNumeroVinculadosSobreNumeroCargosPlanta().intValue()))
								.divide(BigDecimal.valueOf(100)));
				porcentajeVinculacion.getListaValor().add(porcentajeVinculacion.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getPorcentajeVinculacion().intValue())));
				numeroContratosVigentesSobreNumeroHVActivasContratistas.getListaValor()
						.add(numeroContratosVigentesSobreNumeroHVActivasContratistas.getValor()
								.add(BigDecimal.valueOf(cepClasificacion
										.getNumeroContratosVigentesSobreNumeroHVActivasContratistas().intValue())));
				porcentajeContratos.getListaValor().add(porcentajeContratos.getValor()
						.add(BigDecimal.valueOf(cepClasificacion.getPorcentajeContratos().intValue())));
				ipvc.getListaValor()
						.add(ipvc.getValor().add(BigDecimal.valueOf(cepClasificacion.getIpvc().intValue())));
			}

			listaSeguimiento.add(plantaAprobadaReal);
			listaSeguimiento.add(numeroEmpleadosVinculados);
			listaSeguimiento.add(vacantesReportaEntidad);
			listaSeguimiento.add(pesoPorcentajeVinculacion);
			listaSeguimiento.add(totalHVActivas);
			listaSeguimiento.add(numeroContratosVigentes);
			listaSeguimiento.add(pesoPorcentajeContrato);

			listaSeguimiento.add(numeroVinculadosSobreNumeroCargosPlanta);
			listaSeguimiento.add(porcentajeVinculacion);
			listaSeguimiento.add(numeroContratosVigentesSobreNumeroHVActivasContratistas);
			listaSeguimiento.add(porcentajeContratos);
			listaSeguimiento.add(ipvc);

			// Acuerdos de Gestion
			listaAcuerdosGestion = new LinkedList<>();
			EstrategiaTalentoHumano agFechaCorte = new EstrategiaTalentoHumano();
			agFechaCorte.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_FECHA_CORTE, getLocale()));
			agFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaAcuerdosGestion.add(agFechaCorte);

			EstrategiaTalentoHumano agCantidadDebeReportar = new EstrategiaTalentoHumano();
			agCantidadDebeReportar.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_ENTIDADES_REPORTAR, getLocale()));
			agCantidadDebeReportar.setDebeReportar(debeReportarAG);
			listaAcuerdosGestion.add(agCantidadDebeReportar);

			EstrategiaTalentoHumano agCantidadReporto = new EstrategiaTalentoHumano();
			agCantidadReporto.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CANTIDAD_ENTIDADES_ACUERDOS_GESTION, getLocale()));
			agCantidadReporto.setReporto(reportoAG);
			listaAcuerdosGestion.add(agCantidadReporto);

			EstrategiaTalentoHumano agPorcentaje = new EstrategiaTalentoHumano();
			agPorcentaje.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_PORCENTAJE_ENTIDADES_REPORT, getLocale()));
			agPorcentaje.setDebeReportar(cifra.getTotal());
			agPorcentaje.setReporto(cifra.getTotal());
			listaAcuerdosGestion.add(agPorcentaje);

			EstrategiaTalentoHumano agCantidadGerentesPublicos = new EstrategiaTalentoHumano();
			agCantidadGerentesPublicos.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_NUMERO_GERENTES_P, getLocale()));
			agCantidadGerentesPublicos.setValor(cifra.getNumeroGerentesPublicos());
			listaAcuerdosGestion.add(agCantidadGerentesPublicos);

			// Bienestar e Incentivos
			listaBienestarIncentivos = new LinkedList<>();
			EstrategiaTalentoHumano biFechaCorte = new EstrategiaTalentoHumano();
			biFechaCorte.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_FECHA_CORTE, getLocale()));
			biFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaBienestarIncentivos.add(biFechaCorte);

			EstrategiaTalentoHumano biCantidadDebeReportar = new EstrategiaTalentoHumano();
			biCantidadDebeReportar.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_ENTIDADES_REPORTAR, getLocale()));
			biCantidadDebeReportar.setDebeReportar(debeReportarBeI);
			listaBienestarIncentivos.add(biCantidadDebeReportar);

			EstrategiaTalentoHumano biCantidadReporto = new EstrategiaTalentoHumano();
			biCantidadReporto.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CANTIDAD_ENTIDADES_ACUERDOS_GESTION, getLocale()));
			biCantidadReporto.setReporto(cifra.getTotal());
			listaBienestarIncentivos.add(biCantidadReporto);

			EstrategiaTalentoHumano biPorcentaje = new EstrategiaTalentoHumano();
			biPorcentaje.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_PORCENTAJE_ENTIDADES_REPORT, getLocale()));
			biPorcentaje.setDebeReportar(debeReportarBeI);
			biPorcentaje.setReporto(adoptoBeI);
			listaBienestarIncentivos.add(biPorcentaje);

			EstrategiaTalentoHumano biCantidadServidoresInscritos = new EstrategiaTalentoHumano();
			biCantidadServidoresInscritos
					.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_CEP_BEI_CANTIDAD_SERVIDORES, getLocale()));
			biCantidadServidoresInscritos.setValor(cifra.getCantidadServidoresBeI());
			listaBienestarIncentivos.add(biCantidadServidoresInscritos);

			// Capacitación
			listaCapacitacion = new LinkedList<>();
			EstrategiaTalentoHumano cFechaCorte = new EstrategiaTalentoHumano();
			cFechaCorte.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_FECHA_CORTE, getLocale()));
			cFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaCapacitacion.add(cFechaCorte);

			EstrategiaTalentoHumano cCantidadDebeReportar = new EstrategiaTalentoHumano();
			cCantidadDebeReportar.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_ENTIDADES_REPORTAR, getLocale()));
			cCantidadDebeReportar.setDebeReportar(debeReportarCap);
			listaCapacitacion.add(cCantidadDebeReportar);

			EstrategiaTalentoHumano cCantidadReporto = new EstrategiaTalentoHumano();
			cCantidadReporto.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CANTIDAD_ENTIDADES_ACUERDOS_GESTION, getLocale()));
			cCantidadReporto.setReporto(adoptoCap);
			listaCapacitacion.add(cCantidadReporto);

			EstrategiaTalentoHumano cPorcentaje = new EstrategiaTalentoHumano();
			cPorcentaje.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_PORCENTAJE_ENTIDADES_REPORT, getLocale()));
			cPorcentaje.setDebeReportar(cifra.getTotal());
			cPorcentaje.setReporto(cifra.getTotal());
			listaCapacitacion.add(cPorcentaje);

			EstrategiaTalentoHumano cEntidadesTienenPIC = new EstrategiaTalentoHumano();
			cEntidadesTienenPIC.setEtiqueta(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_TABLERO_CEP_CAP_ENTIDADES_PIC, getLocale()));
			cEntidadesTienenPIC.setValor(entidadesPIC);
			listaCapacitacion.add(cEntidadesTienenPIC);

			EstrategiaTalentoHumano cAprendizajePAE = new EstrategiaTalentoHumano();
			cAprendizajePAE
					.setEtiqueta("Cantidad de Proyectos de Aprendizaje en Equipo (PAE) que tienen las entidades");
			cAprendizajePAE.setValor(cifra.getCantidadPAE());
			listaCapacitacion.add(cAprendizajePAE);

			EstrategiaTalentoHumano cPresupuestoAnual = new EstrategiaTalentoHumano();
			cPresupuestoAnual.setEtiqueta("Presupuesto anual para implementar los PIC");
			cPresupuestoAnual.setValor(cifra.getPresupuestoAnualPIC().toBigInteger());
			listaCapacitacion.add(cPresupuestoAnual);

			EstrategiaTalentoHumano cTomoDiplomadosDAFP = new EstrategiaTalentoHumano();
			cTomoDiplomadosDAFP.setEtiqueta("Cantidad de servidores que han tomado diplomados de la Función Pública");
			cTomoDiplomadosDAFP.setValor(cifra.getCuantosServidoresDiplomados());
			listaCapacitacion.add(cTomoDiplomadosDAFP);

			EstrategiaTalentoHumano cBecasDAFP = new EstrategiaTalentoHumano();
			cBecasDAFP.setEtiqueta("Cantidad de servidores que han sido beneficiados de becas de la Función Pública");
			cBecasDAFP.setValor(cuentaServidoresBenBecasDAFP);
			listaCapacitacion.add(cBecasDAFP);

			// Horarios Flexibles
			listaHorariosFlexibles = new LinkedList<>();
			EstrategiaTalentoHumano hfFechaCorte = new EstrategiaTalentoHumano();
			hfFechaCorte.setEtiqueta("Fecha de corte");
			hfFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaHorariosFlexibles.add(hfFechaCorte);

			EstrategiaTalentoHumano cCantidadEntidadesHF = new EstrategiaTalentoHumano();
			cCantidadEntidadesHF.setEtiqueta("Cantidad de entidades que adoptaron acciones de horarios flexibles");
			cCantidadEntidadesHF.setReporto(adoptoHF);
			listaHorariosFlexibles.add(cCantidadEntidadesHF);

			EstrategiaTalentoHumano hfCantidadServidoresFH = new EstrategiaTalentoHumano();
			hfCantidadServidoresFH
					.setEtiqueta("Cantidad de servidores inscritos en la estrategia de Horarios Flexibles");
			hfCantidadServidoresFH.setValor(cifra.getCantidadServidoresHF());
			listaHorariosFlexibles.add(hfCantidadServidoresFH);

			// Teletrabajo
			listaTeletrabajo = new LinkedList<>();
			EstrategiaTalentoHumano tFechaCorte = new EstrategiaTalentoHumano();
			tFechaCorte.setEtiqueta("Fecha de corte");
			tFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaTeletrabajo.add(tFechaCorte);

			EstrategiaTalentoHumano tAdoptoNacional = new EstrategiaTalentoHumano();
			tAdoptoNacional
					.setEtiqueta("Cantidad de entidades del orden nacional que adoptaron acciones de teletrabajo");
			tAdoptoNacional.setReporto(adoptoTelN);

			EstrategiaTalentoHumano tAdoptoTerritorial = new EstrategiaTalentoHumano();
			tAdoptoTerritorial
					.setEtiqueta("Cantidad de entidades del orden territorial que adoptaron acciones de teletrabajo");
			tAdoptoTerritorial.setReporto(adoptoTelT);

			EstrategiaTalentoHumano tAdopto = new EstrategiaTalentoHumano();
			tAdopto.setEtiqueta("Cantidad de entidades que adoptaron acciones de teletrabajo");
			tAdopto.setReporto(adoptoTel);

			listaTeletrabajo.add(tAdoptoNacional);
			listaTeletrabajo.add(tAdopto);
			listaTeletrabajo.add(tAdoptoTerritorial);

			EstrategiaTalentoHumano tCantidadServidores = new EstrategiaTalentoHumano();
			tCantidadServidores.setEtiqueta("Cantidad de servidores inscritos en la estrategia de Teletrabajo");
			tCantidadServidores.setValor(cifra.getCantidadServidoresTel());
			listaTeletrabajo.add(tCantidadServidores);

			// Bilinguismo
			listaBilinguismo = new LinkedList<>();
			EstrategiaTalentoHumano bFechaCorte = new EstrategiaTalentoHumano();
			bFechaCorte.setEtiqueta("Fecha de corte");
			bFechaCorte.setFechaCorte(DateUtils.getFechaSistema());
			listaBilinguismo.add(bFechaCorte);

			EstrategiaTalentoHumano bAdopto = new EstrategiaTalentoHumano();
			bAdopto.setEtiqueta("Cantidad de entidades que adoptaron la estrategia");
			bAdopto.setReporto(adoptoBil);
			listaBilinguismo.add(bAdopto);

			// Bilinguismo Detalle
			listaBilinguismoDetalle = new LinkedList<>();
			Bilinguismo bdNivelBeguinner = new Bilinguismo();
			bdNivelBeguinner.setEtiqueta("Nivel beguinner");
			bdNivelBeguinner.setCantidadPreInsc(cifra.getCantidadPreInscBeguinner());
			bdNivelBeguinner.setCantidadIni(cifra.getCantidadIniBeguinner());
			bdNivelBeguinner.setCantidadTer(cifra.getCantidadTerBeguinner());
			bdNivelBeguinner.setCantidadApro(cifra.getCantidadAproBeguinner());
			listaBilinguismoDetalle.add(bdNivelBeguinner);

			Bilinguismo bdNivelA11 = new Bilinguismo();
			bdNivelA11.setEtiqueta("Nivel A 1.1");
			bdNivelA11.setCantidadPreInsc(cifra.getCantidadPreInscA11());
			bdNivelA11.setCantidadIni(cifra.getCantidadIniA11());
			bdNivelA11.setCantidadTer(cifra.getCantidadTerA11());
			bdNivelA11.setCantidadApro(cifra.getCantidadAproA11());
			listaBilinguismoDetalle.add(bdNivelA11);

			Bilinguismo bdNivelA12 = new Bilinguismo();
			bdNivelA12.setEtiqueta("Nivel A 1.12");
			bdNivelA12.setCantidadPreInsc(cifra.getCantidadPreInscA12());
			bdNivelA12.setCantidadIni(cifra.getCantidadIniA12());
			bdNivelA12.setCantidadTer(cifra.getCantidadTerA12());
			bdNivelA12.setCantidadApro(cifra.getCantidadAproA12());
			listaBilinguismoDetalle.add(bdNivelA12);

			Bilinguismo bdNivelA13 = new Bilinguismo();
			bdNivelA13.setEtiqueta("Nivel A 1.3");
			bdNivelA13.setCantidadPreInsc(cifra.getCantidadPreInscA13());
			bdNivelA13.setCantidadIni(cifra.getCantidadIniA13());
			bdNivelA13.setCantidadTer(cifra.getCantidadTerA13());
			bdNivelA13.setCantidadApro(cifra.getCantidadAproA13());
			listaBilinguismoDetalle.add(bdNivelA13);

			Bilinguismo bdNivelA1 = new Bilinguismo();
			bdNivelA1.setEtiqueta("Total de personas pre-inscritas - Nivel A1");
			bdNivelA1.setCantidadPreInsc(cifra.getTotalPreInscA1());
			bdNivelA1.setCantidadIni(cifra.getTotalIniA1());
			bdNivelA1.setCantidadTer(cifra.getTotalTerA1());
			bdNivelA1.setCantidadApro(cifra.getTotalAproA1());
			listaBilinguismoDetalle.add(bdNivelA1);

			Bilinguismo bdNivelA21 = new Bilinguismo();
			bdNivelA21.setEtiqueta("Nivel A 2.1");
			bdNivelA21.setCantidadPreInsc(cifra.getCantidadPreInscA21());
			bdNivelA21.setCantidadIni(cifra.getCantidadIniA21());
			bdNivelA21.setCantidadTer(cifra.getCantidadTerA21());
			bdNivelA21.setCantidadApro(cifra.getCantidadAproA21());
			listaBilinguismoDetalle.add(bdNivelA21);

			Bilinguismo bdNivelA22 = new Bilinguismo();
			bdNivelA22.setEtiqueta("Nivel A 2.2");
			bdNivelA22.setCantidadPreInsc(cifra.getCantidadPreInscA22());
			bdNivelA22.setCantidadIni(cifra.getCantidadIniA22());
			bdNivelA22.setCantidadTer(cifra.getCantidadTerA22());
			bdNivelA22.setCantidadApro(cifra.getCantidadAproA22());
			listaBilinguismoDetalle.add(bdNivelA22);

			Bilinguismo bdNivelA23 = new Bilinguismo();
			bdNivelA23.setEtiqueta("Nivel A 2.3");
			bdNivelA23.setCantidadPreInsc(cifra.getCantidadPreInscA23());
			bdNivelA23.setCantidadIni(cifra.getCantidadIniA23());
			bdNivelA23.setCantidadTer(cifra.getCantidadTerA23());
			bdNivelA23.setCantidadApro(cifra.getCantidadAproA23());
			listaBilinguismoDetalle.add(bdNivelA23);

			Bilinguismo bdNivelA2 = new Bilinguismo();
			bdNivelA2.setEtiqueta("Total de personas pre-inscritas - Nivel A2");
			bdNivelA2.setCantidadPreInsc(cifra.getTotalPreInscA2());
			bdNivelA2.setCantidadIni(cifra.getTotalIniA2());
			bdNivelA2.setCantidadTer(cifra.getTotalTerA2());
			bdNivelA2.setCantidadApro(cifra.getTotalAproA2());
			listaBilinguismoDetalle.add(bdNivelA2);

			Bilinguismo bdNivelB11 = new Bilinguismo();
			bdNivelB11.setEtiqueta("Nivel B 1.1");
			bdNivelB11.setCantidadPreInsc(cifra.getCantidadPreInscB11());
			bdNivelB11.setCantidadIni(cifra.getCantidadIniB11());
			bdNivelB11.setCantidadTer(cifra.getCantidadTerB11());
			bdNivelB11.setCantidadApro(cifra.getCantidadAproB11());
			listaBilinguismoDetalle.add(bdNivelB11);

			Bilinguismo bdNivelB12 = new Bilinguismo();
			bdNivelB12.setEtiqueta("Nivel B 1.2");
			bdNivelB12.setCantidadPreInsc(cifra.getCantidadPreInscB12());
			bdNivelB12.setCantidadIni(cifra.getCantidadIniB12());
			bdNivelB12.setCantidadTer(cifra.getCantidadTerB12());
			bdNivelB12.setCantidadApro(cifra.getCantidadAproB12());
			listaBilinguismoDetalle.add(bdNivelB12);

			Bilinguismo bdNivelB13 = new Bilinguismo();
			bdNivelB13.setEtiqueta("Nivel B 1.3");
			bdNivelB13.setCantidadPreInsc(cifra.getCantidadPreInscB13());
			bdNivelB13.setCantidadIni(cifra.getCantidadIniB13());
			bdNivelB13.setCantidadTer(cifra.getCantidadTerB13());
			bdNivelB13.setCantidadApro(cifra.getCantidadAproB13());
			listaBilinguismoDetalle.add(bdNivelB13);

			Bilinguismo bdNivelB1 = new Bilinguismo();
			bdNivelB1.setEtiqueta("Total de personas pre-inscritas - Nivel B1");
			bdNivelB1.setCantidadPreInsc(cifra.getTotalPreInscB1());
			bdNivelB1.setCantidadIni(cifra.getTotalIniB1());
			bdNivelB1.setCantidadTer(cifra.getTotalTerB1());
			bdNivelB1.setCantidadApro(cifra.getTotalAproB1());
			listaBilinguismoDetalle.add(bdNivelB1);

			planAnualVacantes.setFechaCorte(DateUtils.getFechaSistema());
			planAnualVacantes.setDebeReportarPAV(debeReportarPAV);
			planAnualVacantes.setReportoPAV(reportoPAV);

			leyCuotas.setFechaCorte(DateUtils.getFechaSistema());
			leyCuotas.setDebeReportarLC(debeReportarLC);
			leyCuotas.setReportoLC(reportoLC);

			empleos.setFechaCorte(DateUtils.getFechaSistema());
			empleos.setDebeReportarEmp(debeReportarEmp);
			empleos.setReportoEmp(reportoEmp);

			seguimiento.setFechaCorte(DateUtils.getFechaSistema());
			seguimiento.setDebeReportarSeg(debeReportarSeg);
			seguimiento.setReportoSeg(reportoSeg);
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);			
		} catch (Exception e) {
			logger.error("init()", e);
		}
		if (listaEmpleos == null || listaEmpleos.isEmpty()) {
			listaEmpleos = new ArrayList<Empleo>();
			Empleo empleo = new Empleo();
			empleo.setCodCifrasEmpleoPublico(0);
			empleo.setCodigoEstado(0);
			BigInteger num = new BigDecimal(0).toBigInteger();
			empleo.setDebeReportarEmp(num);
			empleo.setEtiqueta("");
			listaEmpleos.add(empleo);
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * (BigInteger.ZERO); * Construye la conversación entre la vista y el
	 * controlador
	 */
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
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		filtro = new CifrasEmpleoPublico();

		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		init();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla en
	 * componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xlsx. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLSX(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 1,
				TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_CIFRAS_EMPLEO_PUBLICO"));

		int indexFirstColumn = 1;
		int indexLastColumn = 9;

		final int indexRow1 = 2;
		final int indexRow2 = 3;

		// Cifras de empleo
		Color awt = new Color(255, 255, 255, 0);
		XSSFColor color = new XSSFColor(awt);
		formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexLastColumn);

		// Plan Anual de Vacantes
		if (filtro.isPlanAnualVacantes()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 23;

			awt = new Color(252, 213, 180, 0);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Ley de Cuotas
		if (filtro.isLeyCuotas()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 2;

			awt = new Color(177, 160, 199);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Maximos
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 7;

			awt = new Color(228, 223, 236);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Otros
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 7;

			awt = new Color(204, 192, 218);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Observaciones
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 1;

			awt = new Color(177, 160, 199);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Empleos
		if (filtro.isEmpleos()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(216, 228, 188);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Planta
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 9;

			awt = new Color(146, 208, 80);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			// Empleos
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 8;

			awt = new Color(216, 228, 188);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}

		// Seguimiento
		if (filtro.isSeguimiento()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 20;

			awt = new Color(146, 205, 220);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}

		// Acuerdos de Gestion
		if (filtro.isAcuerdosGestion()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(220, 230, 241);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Capacitacion
		if (filtro.isCapacitacion()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 13;

			awt = new Color(184, 204, 228);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Bienestar
		if (filtro.isBienestarIncentivos()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 5;

			awt = new Color(149, 179, 215);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Horarios Flexibles
		if (filtro.isHorariosFlexibles()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Teletrabajo
		if (filtro.isTeletrabajo()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 4;

			awt = new Color(22, 54, 92);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
		// Bilinguismo
		if (filtro.isBilinguismo()) {
			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 2;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(54, 96, 146);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 3;

			awt = new Color(22, 54, 92);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);

			indexFirstColumn = indexLastColumn + 1;
			indexLastColumn = indexLastColumn + 1;

			awt = new Color(15, 36, 62);
			color = new XSSFColor(awt);
			formatearCabecerasXLSX(document, color, indexRow1, indexFirstColumn, indexFirstColumn);
			formatearCabecerasXLSX(document, color, indexRow2, indexFirstColumn, indexLastColumn);
		}
	}

	/**
	 * Valida formato de las celdas de xls posterior al exportado de la tabla en
	 * componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xls. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLS(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 1,
				TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_CIFRAS_EMPLEO_PUBLICO"));
	}

	public void formatearCabecerasXLSX(Object document, XSSFColor color, final int indexFirstRow,
			final int indexFirstColumn, final int indexLastColumn) {
		int indexRowTemp = indexFirstRow - 1;
		int indexFirstColumnTemp = indexFirstColumn - 1;
		int indexLastColumnTemp = indexLastColumn;

		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);

		XSSFRow row = sheet.getRow(indexRowTemp);
		for (int i = indexFirstColumnTemp; i < indexLastColumnTemp; i++) {
			XSSFCell cell = row.getCell(i);
			XSSFCellStyle cellStyle = wb.createCellStyle();
			if (cell != null) {
				if (cell.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
					cellStyle.setFillForegroundColor(color);
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setWrapText(true);
					cellStyle.setLocked(true);
					cell.setCellStyle(cellStyle);
				}
				cellStyle.setBorderBottom(BorderStyle.HAIR);
				cellStyle.setBorderLeft(BorderStyle.HAIR);
				cellStyle.setBorderRight(BorderStyle.HAIR);
				cellStyle.setBorderTop(BorderStyle.HAIR);
			}
		}
	}

	/**
	 * Marca en el valor {@link Boolean} de <code>todos</code> todas las tematicas
	 * de formulario de busqueda
	 */
	public void marcarTodos() {
		this.filtro.setPlanAnualVacantes(todos);
		this.filtro.setLeyCuotas(todos);
		this.filtro.setEmpleos(todos);
		this.filtro.setSeguimiento(todos);
		this.filtro.setAcuerdosGestion(todos);
		this.filtro.setCapacitacion(todos);
		this.filtro.setBienestarIncentivos(todos);
		this.filtro.setHorariosFlexibles(todos);
		this.filtro.setTeletrabajo(todos);
		this.filtro.setBilinguismo(todos);
	}
}
