package co.gov.dafp.sigep2.mbean;

import java.awt.Color;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entities.EstrategiaTalentoHumano;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ResumenCargosPublicos;
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
public class ResumenCargosPublicosBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 306375992870372940L;

	private CifrasEmpleoPublico cifraSeleccionada;
	private CifrasEmpleoPublico filtro;
	private List<CifrasEmpleoPublico> listaCifras;

	private List<ResumenCargosPublicos> listaResumenCargosPublicos;
	private List<ResumenCargosPublicos> listaTotalesResumenCargosPublicos;
	private List<Seguimiento> listaSeguimientoClasificacion;

	transient UIInput todosUIInput = null;

	private boolean habilitarConsulta = false;

	private boolean todos = false;

	private Long[] sectoresSeleccionados;

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

	public List<ResumenCargosPublicos> getListaResumenCargosPublicos() {
		return listaResumenCargosPublicos;
	}

	public void setListaResumenCargosPublicos(List<ResumenCargosPublicos> listaResumenCargosPublicos) {
		this.listaResumenCargosPublicos = listaResumenCargosPublicos;
	}

	public List<ResumenCargosPublicos> getListaTotalesResumenCargosPublicos() {
		return listaTotalesResumenCargosPublicos;
	}

	public void setListaTotalesResumenCargosPublicos(List<ResumenCargosPublicos> listaTotalesResumenCargosPublicos) {
		this.listaTotalesResumenCargosPublicos = listaTotalesResumenCargosPublicos;
	}

	public List<Seguimiento> getListaSeguimientoClasificacion() {
		return listaSeguimientoClasificacion;
	}

	public void setListaSeguimientoClasificacion(List<Seguimiento> listaSeguimientoClasificacion) {
		this.listaSeguimientoClasificacion = listaSeguimientoClasificacion;
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
	 * Validador del formulario principal. Es invocado posterior a las
	 * validaciones de tipo definidas en cada componenete del mismo
	 * 
	 * @param event
	 *            Contiene los componentes procesados en el formulario una vez
	 *            se genera el submit
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

			// Empleos
			listaResumenCargosPublicos = new LinkedList<>();
			listaTotalesResumenCargosPublicos = new LinkedList<>();
			listaSeguimientoClasificacion = new LinkedList<>();

			ResumenCargosPublicos espacio = new ResumenCargosPublicos();
			espacio.setEtiqueta("");
			espacio.setTabulacion("");

			ResumenCargosPublicos ordenNacional = new ResumenCargosPublicos();
			ordenNacional.setEtiqueta("ORDEN NACIONAL");
			ordenNacional.setTabulacion(ResumenCargosPublicos.NIVEL1);
			ordenNacional.setValorAdministrativosUniformados(BigInteger.ZERO);
			ordenNacional.setValorDocentes(BigInteger.ZERO);
			ordenNacional.setTotalEmpleosPublicos(BigInteger.ZERO);
			ordenNacional.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ordenNacional.setGranTotal(BigInteger.ZERO);
			ordenNacional.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ramaEjecutiva = new ResumenCargosPublicos();
			ramaEjecutiva.setCodCifrasEmpleoPublico(1l);
			ramaEjecutiva.setEtiqueta("RAMA EJECUTIVA");
			ramaEjecutiva.setValorAdministrativosUniformados(BigInteger.ZERO);
			ramaEjecutiva.setValorDocentes(BigInteger.ZERO);
			ramaEjecutiva.setTotalEmpleosPublicos(BigInteger.ZERO);
			ramaEjecutiva.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ramaEjecutiva.setGranTotal(BigInteger.ZERO);
			ramaEjecutiva.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ministerios = new ResumenCargosPublicos();
			ministerios.setEtiqueta(
					"Ministerios, Departamentos Administrativos, Superintendencias, Establecimientos Públicos, Empresas Industriales y Comerciales del Estado (E.I.C.E.), Sociedades de Economía Mixta (S.E.M.), Unidades Administrativas Especiales (U.A.E.) y Empresas Sociales del Estado (1)");
			ministerios.setTabulacion(ResumenCargosPublicos.NIVEL3);
			ministerios.setValorAdministrativosUniformados(cifra.getTotalPlantaEmp());
			ministerios.setValorDocentes(cifra.getPlantaDocentesEmp());
			ministerios.setTotalEmpleosPublicos(
					ministerios.getValorAdministrativosUniformados().add(ministerios.getValorDocentes()));
			ministerios.setValorTrabajadoresOficiales(cifra.getTrabajadoresOficialesEmp());
			ministerios.setGranTotal(
					ministerios.getTotalEmpleosPublicos().add(ministerios.getValorTrabajadoresOficiales()));
			ministerios.setValorRegimenPrivado(cifra.getPlantaPrivadaEmp());

			ResumenCargosPublicos sectorEducacionTerritorial = new ResumenCargosPublicos();
			sectorEducacionTerritorial.setEtiqueta("SECTOR EDUCACIÓN TERRITORIAL");
			sectorEducacionTerritorial.setTabulacion(ResumenCargosPublicos.NIVEL3);
			sectorEducacionTerritorial.setValorAdministrativosUniformados(BigInteger.ZERO);
			sectorEducacionTerritorial.setValorDocentes(BigInteger.ZERO);
			sectorEducacionTerritorial.setTotalEmpleosPublicos(BigInteger.ZERO);
			sectorEducacionTerritorial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			sectorEducacionTerritorial.setGranTotal(BigInteger.ZERO);
			sectorEducacionTerritorial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos docentes = new ResumenCargosPublicos();
			docentes.setEtiqueta("DOCENTES");
			docentes.setTabulacion(ResumenCargosPublicos.NIVEL4);
			docentes.setValorAdministrativosUniformados(BigInteger.ZERO);
			docentes.setValorDocentes(BigInteger.ZERO);
			docentes.setTotalEmpleosPublicos(BigInteger.ZERO);
			docentes.setValorTrabajadoresOficiales(BigInteger.ZERO);
			docentes.setGranTotal(BigInteger.ZERO);
			docentes.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos directivosDocentes = new ResumenCargosPublicos();
			directivosDocentes.setEtiqueta("DIRECTIVOS DOCENTES");
			directivosDocentes.setTabulacion(ResumenCargosPublicos.NIVEL4);
			directivosDocentes.setValorAdministrativosUniformados(BigInteger.ZERO);
			directivosDocentes.setValorDocentes(BigInteger.ZERO);
			directivosDocentes.setTotalEmpleosPublicos(BigInteger.ZERO);
			directivosDocentes.setValorTrabajadoresOficiales(BigInteger.ZERO);
			directivosDocentes.setGranTotal(BigInteger.ZERO);
			directivosDocentes.setValorRegimenPrivado(BigInteger.ZERO);

			sectorEducacionTerritorial
					.setValorDocentes(docentes.getValorDocentes().add(directivosDocentes.getValorDocentes()));
			sectorEducacionTerritorial.setTotalEmpleosPublicos(sectorEducacionTerritorial
					.getValorAdministrativosUniformados().add(sectorEducacionTerritorial.getValorDocentes()));

			ResumenCargosPublicos personalFuerzaPublica = new ResumenCargosPublicos();
			personalFuerzaPublica.setEtiqueta("PERSONAL FUERZA PÚBLICA");
			personalFuerzaPublica.setValorAdministrativosUniformados(BigInteger.ZERO);
			personalFuerzaPublica.setValorDocentes(BigInteger.ZERO);
			personalFuerzaPublica.setTotalEmpleosPublicos(BigInteger.ZERO);
			personalFuerzaPublica.setValorTrabajadoresOficiales(BigInteger.ZERO);
			personalFuerzaPublica.setGranTotal(BigInteger.ZERO);
			personalFuerzaPublica.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos uniformadosFuerzasMilitares = new ResumenCargosPublicos();
			uniformadosFuerzasMilitares.setEtiqueta("UNIFORMADOS FUERZAS MILITARES");
			uniformadosFuerzasMilitares.setTabulacion(ResumenCargosPublicos.NIVEL3);
			uniformadosFuerzasMilitares.setValorAdministrativosUniformados(BigInteger.ZERO);
			uniformadosFuerzasMilitares.setValorDocentes(BigInteger.ZERO);
			uniformadosFuerzasMilitares.setTotalEmpleosPublicos(BigInteger.ZERO);
			uniformadosFuerzasMilitares.setValorTrabajadoresOficiales(BigInteger.ZERO);
			uniformadosFuerzasMilitares.setGranTotal(BigInteger.ZERO);
			uniformadosFuerzasMilitares.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ufmOficial = new ResumenCargosPublicos();
			ufmOficial.setEtiqueta("Oficial");
			ufmOficial.setTabulacion(ResumenCargosPublicos.NIVEL4);
			ufmOficial.setValorAdministrativosUniformados(BigInteger.ZERO);
			ufmOficial.setValorDocentes(BigInteger.ZERO);
			ufmOficial.setTotalEmpleosPublicos(BigInteger.ZERO);
			ufmOficial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ufmOficial.setGranTotal(BigInteger.ZERO);
			ufmOficial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ufmSubOficial = new ResumenCargosPublicos();
			ufmSubOficial.setEtiqueta("Suboficial");
			ufmSubOficial.setTabulacion(ResumenCargosPublicos.NIVEL4);
			ufmSubOficial.setValorAdministrativosUniformados(BigInteger.ZERO);
			ufmSubOficial.setValorDocentes(BigInteger.ZERO);
			ufmSubOficial.setTotalEmpleosPublicos(BigInteger.ZERO);
			ufmSubOficial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ufmSubOficial.setGranTotal(BigInteger.ZERO);
			ufmSubOficial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ufmAlumno = new ResumenCargosPublicos();
			ufmAlumno.setEtiqueta("Alumno");
			ufmAlumno.setTabulacion(ResumenCargosPublicos.NIVEL4);
			ufmAlumno.setValorAdministrativosUniformados(BigInteger.ZERO);
			ufmAlumno.setValorDocentes(BigInteger.ZERO);
			ufmAlumno.setTotalEmpleosPublicos(BigInteger.ZERO);
			ufmAlumno.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ufmAlumno.setGranTotal(BigInteger.ZERO);
			ufmAlumno.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ufmSoldado = new ResumenCargosPublicos();
			ufmSoldado.setEtiqueta("Soldado");
			ufmSoldado.setTabulacion(ResumenCargosPublicos.NIVEL4);
			ufmSoldado.setValorAdministrativosUniformados(BigInteger.ZERO);
			ufmSoldado.setValorDocentes(BigInteger.ZERO);
			ufmSoldado.setTotalEmpleosPublicos(BigInteger.ZERO);
			ufmSoldado.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ufmSoldado.setGranTotal(BigInteger.ZERO);
			ufmSoldado.setValorRegimenPrivado(BigInteger.ZERO);

			uniformadosFuerzasMilitares.setValorAdministrativosUniformados(ufmOficial
					.getValorAdministrativosUniformados().add(ufmSubOficial.getValorAdministrativosUniformados())
					.add(ufmAlumno.getValorAdministrativosUniformados())
					.add(ufmSoldado.getValorAdministrativosUniformados()));
			uniformadosFuerzasMilitares
					.setValorDocentes(ufmOficial.getValorDocentes().add(ufmSubOficial.getValorDocentes())
							.add(ufmAlumno.getValorDocentes()).add(ufmSoldado.getValorDocentes()));
			uniformadosFuerzasMilitares.setGranTotal(uniformadosFuerzasMilitares.getTotalEmpleosPublicos()
					.add(uniformadosFuerzasMilitares.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos uniformadosPoliciaNacional = new ResumenCargosPublicos();
			uniformadosPoliciaNacional.setEtiqueta("UNIFORMADOS POLICIA NACIONAL");
			uniformadosPoliciaNacional.setTabulacion(ResumenCargosPublicos.NIVEL3);
			uniformadosPoliciaNacional.setValorAdministrativosUniformados(BigInteger.ZERO);
			uniformadosPoliciaNacional.setValorDocentes(BigInteger.ZERO);
			uniformadosPoliciaNacional.setTotalEmpleosPublicos(BigInteger.ZERO);
			uniformadosPoliciaNacional.setValorTrabajadoresOficiales(BigInteger.ZERO);
			uniformadosPoliciaNacional.setGranTotal(BigInteger.ZERO);
			uniformadosPoliciaNacional.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnOficial = new ResumenCargosPublicos();
			upnOficial.setEtiqueta("Oficial");
			upnOficial.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnOficial.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnOficial.setValorDocentes(BigInteger.ZERO);
			upnOficial.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnOficial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnOficial.setGranTotal(BigInteger.ZERO);
			upnOficial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnNivelEjecutivo = new ResumenCargosPublicos();
			upnNivelEjecutivo.setEtiqueta("Nivel Ejecutivo");
			upnNivelEjecutivo.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnNivelEjecutivo.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnNivelEjecutivo.setValorDocentes(BigInteger.ZERO);
			upnNivelEjecutivo.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnNivelEjecutivo.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnNivelEjecutivo.setGranTotal(BigInteger.ZERO);
			upnNivelEjecutivo.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnAgente = new ResumenCargosPublicos();
			upnAgente.setEtiqueta("Agente");
			upnAgente.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnAgente.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnAgente.setValorDocentes(BigInteger.ZERO);
			upnAgente.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnAgente.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnAgente.setGranTotal(BigInteger.ZERO);
			upnAgente.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnSubOficial = new ResumenCargosPublicos();
			upnSubOficial.setEtiqueta("Suboficial");
			upnSubOficial.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnSubOficial.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnSubOficial.setValorDocentes(BigInteger.ZERO);
			upnSubOficial.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnSubOficial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnSubOficial.setGranTotal(BigInteger.ZERO);
			upnSubOficial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnAlumno = new ResumenCargosPublicos();
			upnAlumno.setEtiqueta("Alumno");
			upnAlumno.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnAlumno.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnAlumno.setValorDocentes(BigInteger.ZERO);
			upnAlumno.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnAlumno.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnAlumno.setGranTotal(BigInteger.ZERO);
			upnAlumno.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnAuxBachiller = new ResumenCargosPublicos();
			upnAuxBachiller.setEtiqueta("Aux. Bachiller");
			upnAuxBachiller.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnAuxBachiller.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnAuxBachiller.setValorDocentes(BigInteger.ZERO);
			upnAuxBachiller.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnAuxBachiller.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnAuxBachiller.setGranTotal(BigInteger.ZERO);
			upnAuxBachiller.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos upnAuxRegular = new ResumenCargosPublicos();
			upnAuxRegular.setEtiqueta("Aux. Regular");
			upnAuxRegular.setTabulacion(ResumenCargosPublicos.NIVEL4);
			upnAuxRegular.setValorAdministrativosUniformados(BigInteger.ZERO);
			upnAuxRegular.setValorDocentes(BigInteger.ZERO);
			upnAuxRegular.setTotalEmpleosPublicos(BigInteger.ZERO);
			upnAuxRegular.setValorTrabajadoresOficiales(BigInteger.ZERO);
			upnAuxRegular.setGranTotal(BigInteger.ZERO);
			upnAuxRegular.setValorRegimenPrivado(BigInteger.ZERO);

			uniformadosPoliciaNacional.setValorAdministrativosUniformados(upnOficial
					.getValorAdministrativosUniformados().add(upnNivelEjecutivo.getValorAdministrativosUniformados())
					.add(upnSubOficial.getValorAdministrativosUniformados())
					.add(upnAgente.getValorAdministrativosUniformados())
					.add(upnAlumno.getValorAdministrativosUniformados())
					.add(upnAuxBachiller.getValorAdministrativosUniformados())
					.add(upnAuxRegular.getValorAdministrativosUniformados()));

			uniformadosPoliciaNacional.setValorDocentes(upnOficial.getValorDocentes()
					.add(upnNivelEjecutivo.getValorDocentes()).add(upnAgente.getValorDocentes())
					.add(upnSubOficial.getValorDocentes()).add(upnAlumno.getValorDocentes())
					.add(upnAuxBachiller.getValorDocentes()).add(upnAuxRegular.getValorDocentes()));
			uniformadosPoliciaNacional.setTotalEmpleosPublicos(uniformadosPoliciaNacional
					.getValorAdministrativosUniformados().add(uniformadosPoliciaNacional.getValorDocentes()));
			uniformadosPoliciaNacional.setGranTotal(uniformadosPoliciaNacional.getTotalEmpleosPublicos()
					.add(uniformadosPoliciaNacional.getValorTrabajadoresOficiales()));

			personalFuerzaPublica
					.setValorAdministrativosUniformados(uniformadosFuerzasMilitares.getValorAdministrativosUniformados()
							.add(uniformadosPoliciaNacional.getValorAdministrativosUniformados()));
			personalFuerzaPublica.setValorDocentes(
					uniformadosFuerzasMilitares.getValorDocentes().add(uniformadosPoliciaNacional.getValorDocentes()));
			personalFuerzaPublica.setTotalEmpleosPublicos(personalFuerzaPublica.getValorAdministrativosUniformados()
					.add(personalFuerzaPublica.getValorDocentes()));
			personalFuerzaPublica.setGranTotal(personalFuerzaPublica.getTotalEmpleosPublicos()
					.add(personalFuerzaPublica.getValorTrabajadoresOficiales()));

			ramaEjecutiva.setValorAdministrativosUniformados(ministerios.getValorAdministrativosUniformados()
					.add(personalFuerzaPublica.getValorAdministrativosUniformados()));
			ramaEjecutiva.setValorDocentes(ministerios.getValorDocentes()
					.add(sectorEducacionTerritorial.getValorDocentes()).add(personalFuerzaPublica.getValorDocentes()));
			ramaEjecutiva.setTotalEmpleosPublicos(
					ramaEjecutiva.getValorAdministrativosUniformados().add(ramaEjecutiva.getValorDocentes()));
			ramaEjecutiva.setValorTrabajadoresOficiales(ministerios.getValorTrabajadoresOficiales());
			ramaEjecutiva.setGranTotal(
					ramaEjecutiva.getTotalEmpleosPublicos().add(ramaEjecutiva.getValorTrabajadoresOficiales()));
			ramaEjecutiva.setValorRegimenPrivado(
					ministerios.getValorRegimenPrivado().add(personalFuerzaPublica.getValorRegimenPrivado()));

			ResumenCargosPublicos ramaLegislativa = new ResumenCargosPublicos();
			ramaLegislativa.setCodCifrasEmpleoPublico(2);
			ramaLegislativa.setEtiqueta("RAMA LEGISLATIVA");
			ramaLegislativa.setValorAdministrativosUniformados(BigInteger.ZERO);
			ramaLegislativa.setValorDocentes(BigInteger.ZERO);
			ramaLegislativa.setTotalEmpleosPublicos(BigInteger.ZERO);
			ramaLegislativa.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ramaLegislativa.setGranTotal(BigInteger.ZERO);
			ramaLegislativa.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos camaraRepresentantesEmpleados = new ResumenCargosPublicos();
			camaraRepresentantesEmpleados.setEtiqueta("Cámara de Representantes (Empleados)");
			camaraRepresentantesEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			camaraRepresentantesEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			camaraRepresentantesEmpleados.setValorDocentes(BigInteger.ZERO);
			camaraRepresentantesEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			camaraRepresentantesEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			camaraRepresentantesEmpleados.setGranTotal(BigInteger.ZERO);
			camaraRepresentantesEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos senadoRepublicaEmpleados = new ResumenCargosPublicos();
			senadoRepublicaEmpleados.setEtiqueta("Senado de la República (Empleados)");
			senadoRepublicaEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			senadoRepublicaEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			senadoRepublicaEmpleados.setValorDocentes(BigInteger.ZERO);
			senadoRepublicaEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			senadoRepublicaEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			senadoRepublicaEmpleados.setGranTotal(BigInteger.ZERO);
			senadoRepublicaEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos senadoresRepresentantesCamara = new ResumenCargosPublicos();
			senadoresRepresentantesCamara.setEtiqueta("Senadores y Representantes a la Cámara");
			senadoresRepresentantesCamara.setTabulacion(ResumenCargosPublicos.NIVEL3);
			senadoresRepresentantesCamara.setValorAdministrativosUniformados(BigInteger.ZERO);
			senadoresRepresentantesCamara.setValorDocentes(BigInteger.ZERO);
			senadoresRepresentantesCamara.setTotalEmpleosPublicos(BigInteger.ZERO);
			senadoresRepresentantesCamara.setValorTrabajadoresOficiales(BigInteger.ZERO);
			senadoresRepresentantesCamara.setGranTotal(BigInteger.ZERO);
			senadoresRepresentantesCamara.setValorRegimenPrivado(BigInteger.ZERO);

			ramaLegislativa.setValorAdministrativosUniformados(
					camaraRepresentantesEmpleados.getValorAdministrativosUniformados()
							.add(senadoRepublicaEmpleados.getValorAdministrativosUniformados())
							.add(senadoresRepresentantesCamara.getValorAdministrativosUniformados()));
			ramaLegislativa.setValorDocentes(
					camaraRepresentantesEmpleados.getValorDocentes().add(senadoRepublicaEmpleados.getValorDocentes())
							.add(senadoresRepresentantesCamara.getValorDocentes()));
			ramaLegislativa.setValorTrabajadoresOficiales(camaraRepresentantesEmpleados.getValorTrabajadoresOficiales()
					.add(senadoRepublicaEmpleados.getValorTrabajadoresOficiales())
					.add(senadoresRepresentantesCamara.getValorTrabajadoresOficiales()));
			ramaLegislativa.setGranTotal(
					ramaLegislativa.getTotalEmpleosPublicos().add(ramaLegislativa.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos ramaJudicial = new ResumenCargosPublicos();
			ramaJudicial.setCodCifrasEmpleoPublico(3);
			ramaJudicial.setEtiqueta("RAMA JUDICIAL");
			ramaJudicial.setValorAdministrativosUniformados(BigInteger.ZERO);
			ramaJudicial.setValorDocentes(BigInteger.ZERO);
			ramaJudicial.setTotalEmpleosPublicos(BigInteger.ZERO);
			ramaJudicial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ramaJudicial.setGranTotal(BigInteger.ZERO);
			ramaJudicial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos direccionEjecutivaAdmonJudicialMagistrados = new ResumenCargosPublicos();
			direccionEjecutivaAdmonJudicialMagistrados
					.setEtiqueta("DIRECCION EJECUTIVA DE ADMINISTRACION JUDICIAL (Magistrados) (2)");
			direccionEjecutivaAdmonJudicialMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			direccionEjecutivaAdmonJudicialMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialMagistrados.setValorDocentes(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialMagistrados.setGranTotal(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos direccionEjecutivaAdmonJudicialEmpl = new ResumenCargosPublicos();
			direccionEjecutivaAdmonJudicialEmpl
					.setEtiqueta("DIRECCION EJECUTIVA DE ADMINISTRACION JUDICIAL (Empleados) ");
			direccionEjecutivaAdmonJudicialEmpl.setTabulacion(ResumenCargosPublicos.NIVEL3);
			direccionEjecutivaAdmonJudicialEmpl.setValorAdministrativosUniformados(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialEmpl.setValorDocentes(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialEmpl.setTotalEmpleosPublicos(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialEmpl.setValorTrabajadoresOficiales(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialEmpl.setGranTotal(BigInteger.ZERO);
			direccionEjecutivaAdmonJudicialEmpl.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoSuperiorSalaAdministrativaMagistrados = new ResumenCargosPublicos();
			consejoSuperiorSalaAdministrativaMagistrados
					.setEtiqueta("CONSEJO SUPERIOR-SALA ADMINISTRATIVA (Magistrados)");
			consejoSuperiorSalaAdministrativaMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoSuperiorSalaAdministrativaMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaMagistrados.setValorDocentes(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaMagistrados.setGranTotal(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoSuperiorSalaAdministrativaEmpleados = new ResumenCargosPublicos();
			consejoSuperiorSalaAdministrativaEmpleados.setEtiqueta("CONSEJO SUPERIOR-SALA ADMINISTRATIVA (Empleados)");
			consejoSuperiorSalaAdministrativaEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoSuperiorSalaAdministrativaEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaEmpleados.setValorDocentes(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaEmpleados.setGranTotal(BigInteger.ZERO);
			consejoSuperiorSalaAdministrativaEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoSuperiorSalaDisciplinariaMagistrados = new ResumenCargosPublicos();
			consejoSuperiorSalaDisciplinariaMagistrados
					.setEtiqueta("CONSEJO SUPERIOR-SALA DISCIPLINARIA (Magistrados)");
			consejoSuperiorSalaDisciplinariaMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoSuperiorSalaDisciplinariaMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaMagistrados.setValorDocentes(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaMagistrados.setGranTotal(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoSuperiorSalaDisciplinariaEmpleados = new ResumenCargosPublicos();
			consejoSuperiorSalaDisciplinariaEmpleados.setEtiqueta("CONSEJO SUPERIOR-SALA DISCIPLINARIA (Empleados)");
			consejoSuperiorSalaDisciplinariaEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoSuperiorSalaDisciplinariaEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaEmpleados.setValorDocentes(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaEmpleados.setGranTotal(BigInteger.ZERO);
			consejoSuperiorSalaDisciplinariaEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoEstadoMagistrados = new ResumenCargosPublicos();
			consejoEstadoMagistrados.setEtiqueta("CONSEJO DE ESTADO (Magistrados)");
			consejoEstadoMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoEstadoMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoEstadoMagistrados.setValorDocentes(BigInteger.ZERO);
			consejoEstadoMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoEstadoMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoEstadoMagistrados.setGranTotal(BigInteger.ZERO);
			consejoEstadoMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos consejoEstadoEmpleados = new ResumenCargosPublicos();
			consejoEstadoEmpleados.setEtiqueta("CONSEJO DE ESTADO (Empleados)");
			consejoEstadoEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			consejoEstadoEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			consejoEstadoEmpleados.setValorDocentes(BigInteger.ZERO);
			consejoEstadoEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			consejoEstadoEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			consejoEstadoEmpleados.setGranTotal(BigInteger.ZERO);
			consejoEstadoEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos corteConstitucionalMagistrados = new ResumenCargosPublicos();
			corteConstitucionalMagistrados.setEtiqueta("CORTE CONSTITUCIONAL (Magistrados)");
			corteConstitucionalMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			corteConstitucionalMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			corteConstitucionalMagistrados.setValorDocentes(BigInteger.ZERO);
			corteConstitucionalMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			corteConstitucionalMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			corteConstitucionalMagistrados.setGranTotal(BigInteger.ZERO);
			corteConstitucionalMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos corteConstitucionalEmpleados = new ResumenCargosPublicos();
			corteConstitucionalEmpleados.setEtiqueta("CORTE CONSTITUCIONAL (Empleados)");
			corteConstitucionalEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			corteConstitucionalEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			corteConstitucionalEmpleados.setValorDocentes(BigInteger.ZERO);
			corteConstitucionalEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			corteConstitucionalEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			corteConstitucionalEmpleados.setGranTotal(BigInteger.ZERO);
			corteConstitucionalEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos corteSupremaJusticiaMagistrados = new ResumenCargosPublicos();
			corteSupremaJusticiaMagistrados.setEtiqueta("CORTE SUPREMA DE JUSTICIA (Magistrados)");
			corteSupremaJusticiaMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			corteSupremaJusticiaMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			corteSupremaJusticiaMagistrados.setValorDocentes(BigInteger.ZERO);
			corteSupremaJusticiaMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			corteSupremaJusticiaMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			corteSupremaJusticiaMagistrados.setGranTotal(BigInteger.ZERO);
			corteSupremaJusticiaMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos corteSupremaJusticiaEmpleados = new ResumenCargosPublicos();
			corteSupremaJusticiaEmpleados.setEtiqueta("CORTE SUPREMA DE JUSTICIA (Empleados)");
			corteSupremaJusticiaEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL3);
			corteSupremaJusticiaEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			corteSupremaJusticiaEmpleados.setValorDocentes(BigInteger.ZERO);
			corteSupremaJusticiaEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			corteSupremaJusticiaEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			corteSupremaJusticiaEmpleados.setGranTotal(BigInteger.ZERO);
			corteSupremaJusticiaEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos servidoresJudiciales = new ResumenCargosPublicos();
			servidoresJudiciales.setEtiqueta("*SERVIDORES JUDICIALES SECCIONALES");
			servidoresJudiciales.setTabulacion(ResumenCargosPublicos.NIVEL5);
			servidoresJudiciales.setValorAdministrativosUniformados(BigInteger.ZERO);
			servidoresJudiciales.setValorDocentes(BigInteger.ZERO);
			servidoresJudiciales.setTotalEmpleosPublicos(BigInteger.ZERO);
			servidoresJudiciales.setValorTrabajadoresOficiales(BigInteger.ZERO);
			servidoresJudiciales.setGranTotal(BigInteger.ZERO);
			servidoresJudiciales.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos servidoresJudicialesJuecesMagistrados = new ResumenCargosPublicos();
			servidoresJudicialesJuecesMagistrados.setEtiqueta("Jueces y Magistrados");
			servidoresJudicialesJuecesMagistrados.setTabulacion(ResumenCargosPublicos.NIVEL4);
			servidoresJudicialesJuecesMagistrados.setValorAdministrativosUniformados(BigInteger.ZERO);
			servidoresJudicialesJuecesMagistrados.setValorDocentes(BigInteger.ZERO);
			servidoresJudicialesJuecesMagistrados.setTotalEmpleosPublicos(BigInteger.ZERO);
			servidoresJudicialesJuecesMagistrados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			servidoresJudicialesJuecesMagistrados.setGranTotal(BigInteger.ZERO);
			servidoresJudicialesJuecesMagistrados.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos servidoresJudicialesEmpleados = new ResumenCargosPublicos();
			servidoresJudicialesEmpleados.setEtiqueta("Empleados");
			servidoresJudicialesEmpleados.setTabulacion(ResumenCargosPublicos.NIVEL4);
			servidoresJudicialesEmpleados.setValorAdministrativosUniformados(BigInteger.ZERO);
			servidoresJudicialesEmpleados.setValorDocentes(BigInteger.ZERO);
			servidoresJudicialesEmpleados.setTotalEmpleosPublicos(BigInteger.ZERO);
			servidoresJudicialesEmpleados.setValorTrabajadoresOficiales(BigInteger.ZERO);
			servidoresJudicialesEmpleados.setGranTotal(BigInteger.ZERO);
			servidoresJudicialesEmpleados.setValorRegimenPrivado(BigInteger.ZERO);

			servidoresJudiciales.setValorAdministrativosUniformados(
					servidoresJudicialesJuecesMagistrados.getValorAdministrativosUniformados()
							.add(servidoresJudicialesEmpleados.getValorAdministrativosUniformados()));
			servidoresJudiciales.setValorDocentes(servidoresJudicialesJuecesMagistrados.getValorDocentes()
					.add(servidoresJudicialesJuecesMagistrados.getValorDocentes()));

			servidoresJudiciales
					.setValorTrabajadoresOficiales(servidoresJudicialesJuecesMagistrados.getValorTrabajadoresOficiales()
							.add(servidoresJudicialesEmpleados.getValorTrabajadoresOficiales()));

			servidoresJudiciales.setValorRegimenPrivado(servidoresJudicialesJuecesMagistrados.getValorRegimenPrivado()
					.add(servidoresJudicialesEmpleados.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos fiscaliaGeneralNacion = new ResumenCargosPublicos();
			fiscaliaGeneralNacion.setEtiqueta("FISCALIA GENERAL DE LA NACION");
			fiscaliaGeneralNacion.setTabulacion(ResumenCargosPublicos.NIVEL3);
			fiscaliaGeneralNacion.setValorAdministrativosUniformados(BigInteger.ZERO);
			fiscaliaGeneralNacion.setValorDocentes(BigInteger.ZERO);
			fiscaliaGeneralNacion.setTotalEmpleosPublicos(BigInteger.ZERO);
			fiscaliaGeneralNacion.setValorTrabajadoresOficiales(BigInteger.ZERO);
			fiscaliaGeneralNacion.setGranTotal(BigInteger.ZERO);
			fiscaliaGeneralNacion.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos institutoMedicinaLegal = new ResumenCargosPublicos();
			institutoMedicinaLegal.setEtiqueta("INSTITUTO NAL DE MEDICINA LEGAL");
			institutoMedicinaLegal.setTabulacion(ResumenCargosPublicos.NIVEL3);
			institutoMedicinaLegal.setValorAdministrativosUniformados(BigInteger.ZERO);
			institutoMedicinaLegal.setValorDocentes(BigInteger.ZERO);
			institutoMedicinaLegal.setTotalEmpleosPublicos(BigInteger.ZERO);
			institutoMedicinaLegal.setValorTrabajadoresOficiales(BigInteger.ZERO);
			institutoMedicinaLegal.setGranTotal(BigInteger.ZERO);
			institutoMedicinaLegal.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos conocimientoInnovacionJusticia = new ResumenCargosPublicos();
			conocimientoInnovacionJusticia.setEtiqueta("CONOCIMIENTO E INNOVACION PARA LA JUSTICIA");
			conocimientoInnovacionJusticia.setTabulacion(ResumenCargosPublicos.NIVEL3);
			conocimientoInnovacionJusticia.setValorAdministrativosUniformados(BigInteger.ZERO);
			conocimientoInnovacionJusticia.setValorDocentes(BigInteger.ZERO);
			conocimientoInnovacionJusticia.setTotalEmpleosPublicos(BigInteger.ZERO);
			conocimientoInnovacionJusticia.setValorTrabajadoresOficiales(BigInteger.ZERO);
			conocimientoInnovacionJusticia.setGranTotal(BigInteger.ZERO);
			conocimientoInnovacionJusticia.setValorRegimenPrivado(BigInteger.ZERO);

			ramaJudicial.setValorAdministrativosUniformados(
					direccionEjecutivaAdmonJudicialMagistrados.getValorAdministrativosUniformados()
							.add(direccionEjecutivaAdmonJudicialEmpl.getValorAdministrativosUniformados())
							.add(consejoSuperiorSalaAdministrativaMagistrados.getValorAdministrativosUniformados())
							.add(consejoSuperiorSalaAdministrativaEmpleados.getValorAdministrativosUniformados())
							.add(consejoSuperiorSalaDisciplinariaMagistrados.getValorAdministrativosUniformados())
							.add(consejoSuperiorSalaDisciplinariaEmpleados.getValorAdministrativosUniformados())
							.add(consejoEstadoMagistrados.getValorAdministrativosUniformados())
							.add(consejoEstadoEmpleados.getValorAdministrativosUniformados())
							.add(corteConstitucionalMagistrados.getValorAdministrativosUniformados())
							.add(corteConstitucionalEmpleados.getValorAdministrativosUniformados())
							.add(corteSupremaJusticiaMagistrados.getValorAdministrativosUniformados())
							.add(corteSupremaJusticiaEmpleados.getValorAdministrativosUniformados())
							.add(servidoresJudiciales.getValorAdministrativosUniformados())
							.add(fiscaliaGeneralNacion.getValorAdministrativosUniformados())
							.add(institutoMedicinaLegal.getValorAdministrativosUniformados())
							.add(conocimientoInnovacionJusticia.getValorAdministrativosUniformados()));
			ramaJudicial.setValorDocentes(direccionEjecutivaAdmonJudicialMagistrados.getValorDocentes()
					.add(direccionEjecutivaAdmonJudicialEmpl.getValorDocentes())
					.add(consejoSuperiorSalaAdministrativaMagistrados.getValorDocentes())
					.add(consejoSuperiorSalaAdministrativaEmpleados.getValorDocentes())
					.add(consejoSuperiorSalaDisciplinariaMagistrados.getValorDocentes())
					.add(consejoSuperiorSalaDisciplinariaEmpleados.getValorDocentes())
					.add(consejoEstadoMagistrados.getValorDocentes()).add(consejoEstadoEmpleados.getValorDocentes())
					.add(corteConstitucionalMagistrados.getValorDocentes())
					.add(corteConstitucionalEmpleados.getValorDocentes())
					.add(corteSupremaJusticiaMagistrados.getValorDocentes())
					.add(corteSupremaJusticiaEmpleados.getValorDocentes()).add(servidoresJudiciales.getValorDocentes())
					.add(fiscaliaGeneralNacion.getValorDocentes()).add(institutoMedicinaLegal.getValorDocentes())
					.add(conocimientoInnovacionJusticia.getValorDocentes()));

			ramaJudicial.setValorTrabajadoresOficiales(
					direccionEjecutivaAdmonJudicialMagistrados.getValorTrabajadoresOficiales()
							.add(direccionEjecutivaAdmonJudicialEmpl.getValorTrabajadoresOficiales())
							.add(consejoSuperiorSalaAdministrativaMagistrados.getValorTrabajadoresOficiales())
							.add(consejoSuperiorSalaAdministrativaEmpleados.getValorTrabajadoresOficiales())
							.add(consejoSuperiorSalaDisciplinariaMagistrados.getValorTrabajadoresOficiales())
							.add(consejoSuperiorSalaDisciplinariaEmpleados.getValorTrabajadoresOficiales())
							.add(consejoEstadoMagistrados.getValorTrabajadoresOficiales())
							.add(consejoEstadoEmpleados.getValorTrabajadoresOficiales())
							.add(corteConstitucionalMagistrados.getValorTrabajadoresOficiales())
							.add(corteConstitucionalEmpleados.getValorTrabajadoresOficiales())
							.add(corteSupremaJusticiaMagistrados.getValorTrabajadoresOficiales())
							.add(corteSupremaJusticiaEmpleados.getValorTrabajadoresOficiales())
							.add(servidoresJudiciales.getValorTrabajadoresOficiales())
							.add(fiscaliaGeneralNacion.getValorTrabajadoresOficiales())
							.add(institutoMedicinaLegal.getValorTrabajadoresOficiales())
							.add(conocimientoInnovacionJusticia.getValorTrabajadoresOficiales()));

			ramaJudicial.setGranTotal(
					ramaJudicial.getTotalEmpleosPublicos().add(ramaJudicial.getValorTrabajadoresOficiales()));

			ramaJudicial.setValorRegimenPrivado(direccionEjecutivaAdmonJudicialMagistrados.getValorRegimenPrivado()
					.add(direccionEjecutivaAdmonJudicialEmpl.getValorTrabajadoresOficiales())
					.add(consejoSuperiorSalaAdministrativaMagistrados.getValorTrabajadoresOficiales())
					.add(consejoSuperiorSalaAdministrativaEmpleados.getValorTrabajadoresOficiales())
					.add(consejoSuperiorSalaDisciplinariaMagistrados.getValorTrabajadoresOficiales())
					.add(consejoSuperiorSalaDisciplinariaEmpleados.getValorTrabajadoresOficiales())
					.add(consejoEstadoMagistrados.getValorTrabajadoresOficiales())
					.add(consejoEstadoEmpleados.getValorTrabajadoresOficiales())
					.add(corteConstitucionalMagistrados.getValorTrabajadoresOficiales())
					.add(corteConstitucionalEmpleados.getValorTrabajadoresOficiales())
					.add(corteSupremaJusticiaMagistrados.getValorTrabajadoresOficiales())
					.add(corteSupremaJusticiaEmpleados.getValorTrabajadoresOficiales())
					.add(servidoresJudiciales.getValorTrabajadoresOficiales())
					.add(fiscaliaGeneralNacion.getValorTrabajadoresOficiales())
					.add(institutoMedicinaLegal.getValorTrabajadoresOficiales())
					.add(conocimientoInnovacionJusticia.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos organosControl = new ResumenCargosPublicos();
			organosControl.setCodCifrasEmpleoPublico(4);
			organosControl.setEtiqueta("Órganos de Control");
			organosControl.setValorAdministrativosUniformados(BigInteger.ZERO);
			organosControl.setValorDocentes(BigInteger.ZERO);
			organosControl.setTotalEmpleosPublicos(BigInteger.ZERO);
			organosControl.setValorTrabajadoresOficiales(BigInteger.ZERO);
			organosControl.setGranTotal(BigInteger.ZERO);
			organosControl.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos procuraduriaGeneralNacion = new ResumenCargosPublicos();
			procuraduriaGeneralNacion.setEtiqueta("PROCURADURIA GENERAL DE LA NACION (3)");
			procuraduriaGeneralNacion.setTabulacion(ResumenCargosPublicos.NIVEL3);
			procuraduriaGeneralNacion.setValorAdministrativosUniformados(BigInteger.ZERO);
			procuraduriaGeneralNacion.setValorDocentes(BigInteger.ZERO);
			procuraduriaGeneralNacion.setTotalEmpleosPublicos(BigInteger.ZERO);
			procuraduriaGeneralNacion.setValorTrabajadoresOficiales(BigInteger.ZERO);
			procuraduriaGeneralNacion.setGranTotal(BigInteger.ZERO);
			procuraduriaGeneralNacion.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos defensoriaPueblo = new ResumenCargosPublicos();
			defensoriaPueblo.setEtiqueta("DEFENSORIA DEL PUEBLO");
			defensoriaPueblo.setTabulacion(ResumenCargosPublicos.NIVEL3);
			defensoriaPueblo.setValorAdministrativosUniformados(BigInteger.ZERO);
			defensoriaPueblo.setValorDocentes(BigInteger.ZERO);
			defensoriaPueblo.setTotalEmpleosPublicos(BigInteger.ZERO);
			defensoriaPueblo.setValorTrabajadoresOficiales(BigInteger.ZERO);
			defensoriaPueblo.setGranTotal(BigInteger.ZERO);
			defensoriaPueblo.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos contraloriaGeneralRepublica = new ResumenCargosPublicos();
			contraloriaGeneralRepublica.setEtiqueta("CONTRALORIA GENERAL DE LA REPUBLICA");
			contraloriaGeneralRepublica.setTabulacion(ResumenCargosPublicos.NIVEL3);
			contraloriaGeneralRepublica.setValorAdministrativosUniformados(BigInteger.ZERO);
			contraloriaGeneralRepublica.setValorDocentes(BigInteger.ZERO);
			contraloriaGeneralRepublica.setTotalEmpleosPublicos(BigInteger.ZERO);
			contraloriaGeneralRepublica.setValorTrabajadoresOficiales(BigInteger.ZERO);
			contraloriaGeneralRepublica.setGranTotal(BigInteger.ZERO);
			contraloriaGeneralRepublica.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos fondoBienestarSocial = new ResumenCargosPublicos();
			fondoBienestarSocial.setEtiqueta("FONDO DE BIENESTAR SOCIAL DE LA CONTRAL");
			fondoBienestarSocial.setTabulacion(ResumenCargosPublicos.NIVEL3);
			fondoBienestarSocial.setValorAdministrativosUniformados(BigInteger.ZERO);
			fondoBienestarSocial.setValorDocentes(BigInteger.ZERO);
			fondoBienestarSocial.setTotalEmpleosPublicos(BigInteger.ZERO);
			fondoBienestarSocial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			fondoBienestarSocial.setGranTotal(BigInteger.ZERO);
			fondoBienestarSocial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos auditoriaGeneralRepublica = new ResumenCargosPublicos();
			auditoriaGeneralRepublica.setEtiqueta("AUDITORIA GENERAL DE LA REPUBLICA");
			auditoriaGeneralRepublica.setTabulacion(ResumenCargosPublicos.NIVEL3);
			auditoriaGeneralRepublica.setValorAdministrativosUniformados(BigInteger.ZERO);
			auditoriaGeneralRepublica.setValorDocentes(BigInteger.ZERO);
			auditoriaGeneralRepublica.setTotalEmpleosPublicos(BigInteger.ZERO);
			auditoriaGeneralRepublica.setValorTrabajadoresOficiales(BigInteger.ZERO);
			auditoriaGeneralRepublica.setGranTotal(BigInteger.ZERO);
			auditoriaGeneralRepublica.setValorRegimenPrivado(BigInteger.ZERO);

			organosControl.setValorAdministrativosUniformados(procuraduriaGeneralNacion
					.getValorAdministrativosUniformados().add(defensoriaPueblo.getValorAdministrativosUniformados())
					.add(contraloriaGeneralRepublica.getValorAdministrativosUniformados())
					.add(fondoBienestarSocial.getValorAdministrativosUniformados())
					.add(auditoriaGeneralRepublica.getValorAdministrativosUniformados()));
			organosControl.setValorDocentes(procuraduriaGeneralNacion.getValorDocentes()
					.add(defensoriaPueblo.getValorDocentes()).add(contraloriaGeneralRepublica.getValorDocentes())
					.add(fondoBienestarSocial.getValorDocentes()).add(auditoriaGeneralRepublica.getValorDocentes()));

			organosControl.setValorTrabajadoresOficiales(procuraduriaGeneralNacion.getValorTrabajadoresOficiales()
					.add(defensoriaPueblo.getValorTrabajadoresOficiales())
					.add(contraloriaGeneralRepublica.getValorTrabajadoresOficiales())
					.add(fondoBienestarSocial.getValorTrabajadoresOficiales())
					.add(auditoriaGeneralRepublica.getValorTrabajadoresOficiales()));

			organosControl.setValorRegimenPrivado(procuraduriaGeneralNacion.getValorRegimenPrivado()
					.add(defensoriaPueblo.getValorTrabajadoresOficiales())
					.add(contraloriaGeneralRepublica.getValorTrabajadoresOficiales())
					.add(fondoBienestarSocial.getValorTrabajadoresOficiales())
					.add(auditoriaGeneralRepublica.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos organizacionElectoral = new ResumenCargosPublicos();
			organizacionElectoral.setCodCifrasEmpleoPublico(5);
			organizacionElectoral.setEtiqueta("Organización electoral");
			organizacionElectoral.setValorAdministrativosUniformados(BigInteger.ZERO);
			organizacionElectoral.setValorDocentes(BigInteger.ZERO);
			organizacionElectoral.setTotalEmpleosPublicos(BigInteger.ZERO);
			organizacionElectoral.setValorTrabajadoresOficiales(BigInteger.ZERO);
			organizacionElectoral.setGranTotal(BigInteger.ZERO);
			organizacionElectoral.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos registraduriaNacionalEstadoCivil = new ResumenCargosPublicos();
			registraduriaNacionalEstadoCivil.setEtiqueta("REGISTRADURÍA NACIONAL DEL ESTADO CIVIL");
			registraduriaNacionalEstadoCivil.setTabulacion(ResumenCargosPublicos.NIVEL3);
			registraduriaNacionalEstadoCivil.setValorAdministrativosUniformados(BigInteger.ZERO);
			registraduriaNacionalEstadoCivil.setValorDocentes(BigInteger.ZERO);
			registraduriaNacionalEstadoCivil.setTotalEmpleosPublicos(BigInteger.ZERO);
			registraduriaNacionalEstadoCivil.setValorTrabajadoresOficiales(BigInteger.ZERO);
			registraduriaNacionalEstadoCivil.setGranTotal(BigInteger.ZERO);
			registraduriaNacionalEstadoCivil.setValorRegimenPrivado(BigInteger.ZERO);

			organizacionElectoral.setValorAdministrativosUniformados(
					registraduriaNacionalEstadoCivil.getValorAdministrativosUniformados());
			organizacionElectoral.setValorDocentes(registraduriaNacionalEstadoCivil.getValorDocentes());

			organizacionElectoral
					.setValorTrabajadoresOficiales(registraduriaNacionalEstadoCivil.getValorTrabajadoresOficiales());
			organizacionElectoral.setValorRegimenPrivado(registraduriaNacionalEstadoCivil.getValorRegimenPrivado());

			ResumenCargosPublicos entesAutonomos = new ResumenCargosPublicos();
			entesAutonomos.setCodCifrasEmpleoPublico(6);
			entesAutonomos.setEtiqueta("Entes Autónomos");
			entesAutonomos.setValorAdministrativosUniformados(BigInteger.ZERO);
			entesAutonomos.setValorDocentes(BigInteger.ZERO);
			entesAutonomos.setTotalEmpleosPublicos(BigInteger.ZERO);
			entesAutonomos.setValorTrabajadoresOficiales(BigInteger.ZERO);
			entesAutonomos.setGranTotal(BigInteger.ZERO);
			entesAutonomos.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos autoridadNacionalTelevision = new ResumenCargosPublicos();
			autoridadNacionalTelevision.setEtiqueta("AUTORIDAD NACIONAL DE TELEVISION - ANTV");
			autoridadNacionalTelevision.setTabulacion(ResumenCargosPublicos.NIVEL3);
			autoridadNacionalTelevision.setValorAdministrativosUniformados(BigInteger.ZERO);
			autoridadNacionalTelevision.setValorDocentes(BigInteger.ZERO);
			autoridadNacionalTelevision.setTotalEmpleosPublicos(BigInteger.ZERO);
			autoridadNacionalTelevision.setValorTrabajadoresOficiales(BigInteger.ZERO);
			autoridadNacionalTelevision.setGranTotal(BigInteger.ZERO);
			autoridadNacionalTelevision.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos bancoRepublica = new ResumenCargosPublicos();
			bancoRepublica.setEtiqueta("BANCO DE LA REPUBLICA");
			bancoRepublica.setTabulacion(ResumenCargosPublicos.NIVEL3);
			bancoRepublica.setValorAdministrativosUniformados(BigInteger.ZERO);
			bancoRepublica.setValorDocentes(BigInteger.ZERO);
			bancoRepublica.setTotalEmpleosPublicos(BigInteger.ZERO);
			bancoRepublica.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bancoRepublica.setGranTotal(BigInteger.ZERO);
			bancoRepublica.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos corporacionesAutonomasRegionales = new ResumenCargosPublicos();
			corporacionesAutonomasRegionales.setEtiqueta("CORPORACIONES AUTÓNOMAS REGIONALES");
			corporacionesAutonomasRegionales.setTabulacion(ResumenCargosPublicos.NIVEL3);
			corporacionesAutonomasRegionales.setValorAdministrativosUniformados(BigInteger.ZERO);
			corporacionesAutonomasRegionales.setValorDocentes(BigInteger.ZERO);
			corporacionesAutonomasRegionales.setTotalEmpleosPublicos(BigInteger.ZERO);
			corporacionesAutonomasRegionales.setValorTrabajadoresOficiales(BigInteger.ZERO);
			corporacionesAutonomasRegionales.setGranTotal(BigInteger.ZERO);
			corporacionesAutonomasRegionales.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos entesUniversitariosAutonomos = new ResumenCargosPublicos();
			entesUniversitariosAutonomos.setEtiqueta("ENTES UNIVERSITARIOS AUTÓNOMOS");
			entesUniversitariosAutonomos.setTabulacion(ResumenCargosPublicos.NIVEL3);
			entesUniversitariosAutonomos.setValorAdministrativosUniformados(BigInteger.ZERO);
			entesUniversitariosAutonomos.setValorDocentes(BigInteger.ZERO);
			entesUniversitariosAutonomos.setTotalEmpleosPublicos(BigInteger.ZERO);
			entesUniversitariosAutonomos.setValorTrabajadoresOficiales(BigInteger.ZERO);
			entesUniversitariosAutonomos.setGranTotal(BigInteger.ZERO);
			entesUniversitariosAutonomos.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos comisionNacionalServicioCivil = new ResumenCargosPublicos();
			comisionNacionalServicioCivil.setEtiqueta("COMISIÓN NACIONAL DEL SERVICIO CIVIL");
			comisionNacionalServicioCivil.setTabulacion(ResumenCargosPublicos.NIVEL3);
			comisionNacionalServicioCivil.setValorAdministrativosUniformados(BigInteger.ZERO);
			comisionNacionalServicioCivil.setValorDocentes(BigInteger.ZERO);
			comisionNacionalServicioCivil.setTotalEmpleosPublicos(BigInteger.ZERO);
			comisionNacionalServicioCivil.setValorTrabajadoresOficiales(BigInteger.ZERO);
			comisionNacionalServicioCivil.setGranTotal(BigInteger.ZERO);
			comisionNacionalServicioCivil.setValorRegimenPrivado(BigInteger.ZERO);

			entesAutonomos.setValorAdministrativosUniformados(autoridadNacionalTelevision
					.getValorAdministrativosUniformados().add(bancoRepublica.getValorAdministrativosUniformados())
					.add(corporacionesAutonomasRegionales.getValorAdministrativosUniformados())
					.add(entesUniversitariosAutonomos.getValorAdministrativosUniformados())
					.add(comisionNacionalServicioCivil.getValorAdministrativosUniformados()));
			entesAutonomos.setValorDocentes(autoridadNacionalTelevision.getValorDocentes()
					.add(bancoRepublica.getValorDocentes()).add(corporacionesAutonomasRegionales.getValorDocentes())
					.add(entesUniversitariosAutonomos.getValorDocentes())
					.add(comisionNacionalServicioCivil.getValorDocentes()));

			entesAutonomos.setValorTrabajadoresOficiales(autoridadNacionalTelevision.getValorTrabajadoresOficiales()
					.add(bancoRepublica.getValorTrabajadoresOficiales())
					.add(corporacionesAutonomasRegionales.getValorTrabajadoresOficiales())
					.add(entesUniversitariosAutonomos.getValorTrabajadoresOficiales())
					.add(comisionNacionalServicioCivil.getValorTrabajadoresOficiales()));

			entesAutonomos.setValorRegimenPrivado(autoridadNacionalTelevision.getValorRegimenPrivado()
					.add(bancoRepublica.getValorTrabajadoresOficiales())
					.add(corporacionesAutonomasRegionales.getValorTrabajadoresOficiales())
					.add(entesUniversitariosAutonomos.getValorTrabajadoresOficiales())
					.add(comisionNacionalServicioCivil.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos sistemaIntegralVerdad = new ResumenCargosPublicos();
			sistemaIntegralVerdad.setCodCifrasEmpleoPublico(7);
			sistemaIntegralVerdad
					.setEtiqueta("SISTEMA INTEGRAL DE VERDAD, JUSTICIA, VERDAD, REPARACION Y NO REPETICION");
			sistemaIntegralVerdad.setValorAdministrativosUniformados(BigInteger.ZERO);
			sistemaIntegralVerdad.setValorDocentes(BigInteger.ZERO);
			sistemaIntegralVerdad.setTotalEmpleosPublicos(BigInteger.ZERO);
			sistemaIntegralVerdad.setValorTrabajadoresOficiales(BigInteger.ZERO);
			sistemaIntegralVerdad.setGranTotal(BigInteger.ZERO);
			sistemaIntegralVerdad.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos justiciaEspecialPaz = new ResumenCargosPublicos();
			justiciaEspecialPaz.setEtiqueta("JUSTICIA ESPECIAL PARA LA PAZ");
			justiciaEspecialPaz.setTabulacion(ResumenCargosPublicos.NIVEL3);
			justiciaEspecialPaz.setValorAdministrativosUniformados(BigInteger.ZERO);
			justiciaEspecialPaz.setValorDocentes(BigInteger.ZERO);
			justiciaEspecialPaz.setTotalEmpleosPublicos(BigInteger.ZERO);
			justiciaEspecialPaz.setValorTrabajadoresOficiales(BigInteger.ZERO);
			justiciaEspecialPaz.setGranTotal(BigInteger.ZERO);
			justiciaEspecialPaz.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos comisionEsclarecimientoVerdad = new ResumenCargosPublicos();
			comisionEsclarecimientoVerdad
					.setEtiqueta("COMISION PARA EL ESCLARECIMIENTO DE LA VERDAD, LA CONVIVENCIA Y LA NO REPETICION");
			comisionEsclarecimientoVerdad.setTabulacion(ResumenCargosPublicos.NIVEL3);
			comisionEsclarecimientoVerdad.setValorAdministrativosUniformados(BigInteger.ZERO);
			comisionEsclarecimientoVerdad.setValorDocentes(BigInteger.ZERO);
			comisionEsclarecimientoVerdad.setTotalEmpleosPublicos(BigInteger.ZERO);
			comisionEsclarecimientoVerdad.setValorTrabajadoresOficiales(BigInteger.ZERO);
			comisionEsclarecimientoVerdad.setGranTotal(BigInteger.ZERO);
			comisionEsclarecimientoVerdad.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos unidadBusquedaPersonas = new ResumenCargosPublicos();
			unidadBusquedaPersonas.setEtiqueta(
					"UNIDAD DE BUSQUEDA DE PERSONAS DADAS POR DESAPARECIDAS EN EL CONTEXTO Y EN RAZON DEL CONFLICTO ARMADO");
			unidadBusquedaPersonas.setTabulacion(ResumenCargosPublicos.NIVEL3);
			unidadBusquedaPersonas.setValorAdministrativosUniformados(BigInteger.ZERO);
			unidadBusquedaPersonas.setValorDocentes(BigInteger.ZERO);
			unidadBusquedaPersonas.setTotalEmpleosPublicos(BigInteger.ZERO);
			unidadBusquedaPersonas.setValorTrabajadoresOficiales(BigInteger.ZERO);
			unidadBusquedaPersonas.setGranTotal(BigInteger.ZERO);
			unidadBusquedaPersonas.setValorRegimenPrivado(BigInteger.ZERO);

			sistemaIntegralVerdad
					.setValorAdministrativosUniformados(justiciaEspecialPaz.getValorAdministrativosUniformados()
							.add(comisionEsclarecimientoVerdad.getValorAdministrativosUniformados())
							.add(unidadBusquedaPersonas.getValorAdministrativosUniformados()));
			sistemaIntegralVerdad.setValorDocentes(
					justiciaEspecialPaz.getValorDocentes().add(comisionEsclarecimientoVerdad.getValorDocentes())
							.add(unidadBusquedaPersonas.getValorDocentes()));

			sistemaIntegralVerdad.setValorTrabajadoresOficiales(justiciaEspecialPaz.getValorTrabajadoresOficiales()
					.add(comisionEsclarecimientoVerdad.getValorTrabajadoresOficiales())
					.add(unidadBusquedaPersonas.getValorTrabajadoresOficiales()));

			sistemaIntegralVerdad.setValorRegimenPrivado(justiciaEspecialPaz.getValorRegimenPrivado()
					.add(comisionEsclarecimientoVerdad.getValorTrabajadoresOficiales())
					.add(unidadBusquedaPersonas.getValorTrabajadoresOficiales()));

			ResumenCargosPublicos ordenTerritorial = new ResumenCargosPublicos();
			ordenTerritorial.setEtiqueta("ORDEN TERRITORIAL");
			ordenTerritorial.setValorAdministrativosUniformados(BigInteger.ZERO);
			ordenTerritorial.setTabulacion(ResumenCargosPublicos.NIVEL1);
			ordenTerritorial.setValorDocentes(BigInteger.ZERO);
			ordenTerritorial.setTotalEmpleosPublicos(BigInteger.ZERO);
			ordenTerritorial.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ordenTerritorial.setGranTotal(BigInteger.ZERO);
			ordenTerritorial.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos ordenTerritorialSinBogota = new ResumenCargosPublicos();
			ordenTerritorialSinBogota.setEtiqueta("Orden territorial sin Bogotá");
			ordenTerritorialSinBogota.setTabulacion(ResumenCargosPublicos.NIVEL2);
			ordenTerritorialSinBogota.setValorAdministrativosUniformados(BigInteger.ZERO);
			ordenTerritorialSinBogota.setValorDocentes(BigInteger.ZERO);
			ordenTerritorialSinBogota.setTotalEmpleosPublicos(BigInteger.ZERO);
			ordenTerritorialSinBogota.setValorTrabajadoresOficiales(BigInteger.ZERO);
			ordenTerritorialSinBogota.setGranTotal(BigInteger.ZERO);
			ordenTerritorialSinBogota.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos empleosDepartamentales = new ResumenCargosPublicos();
			empleosDepartamentales.setEtiqueta("EMPLEOS DEPARTAMENTOS (Gobernaciones y asambleas)");
			empleosDepartamentales.setTabulacion(ResumenCargosPublicos.NIVEL3);
			empleosDepartamentales.setValorAdministrativosUniformados(BigInteger.ZERO);
			empleosDepartamentales.setValorDocentes(BigInteger.ZERO);
			empleosDepartamentales.setTotalEmpleosPublicos(BigInteger.ZERO);
			empleosDepartamentales.setValorTrabajadoresOficiales(BigInteger.ZERO);
			empleosDepartamentales.setGranTotal(BigInteger.ZERO);
			empleosDepartamentales.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos empleosMunicipios = new ResumenCargosPublicos();
			empleosMunicipios.setEtiqueta("EMPLEOS MUNICIPIOS (Alcaldías y concejos)");
			empleosMunicipios.setTabulacion(ResumenCargosPublicos.NIVEL3);
			empleosMunicipios.setValorAdministrativosUniformados(BigInteger.ZERO);
			empleosMunicipios.setValorDocentes(BigInteger.ZERO);
			empleosMunicipios.setTotalEmpleosPublicos(BigInteger.ZERO);
			empleosMunicipios.setValorTrabajadoresOficiales(BigInteger.ZERO);
			empleosMunicipios.setGranTotal(BigInteger.ZERO);
			empleosMunicipios.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos personerias = new ResumenCargosPublicos();
			personerias.setEtiqueta("PERSONERÍAS");
			personerias.setTabulacion(ResumenCargosPublicos.NIVEL3);
			personerias.setValorAdministrativosUniformados(BigInteger.ZERO);
			personerias.setValorDocentes(BigInteger.ZERO);
			personerias.setTotalEmpleosPublicos(BigInteger.ZERO);
			personerias.setValorTrabajadoresOficiales(BigInteger.ZERO);
			personerias.setGranTotal(BigInteger.ZERO);
			personerias.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos contraloriasTerritoriales = new ResumenCargosPublicos();
			contraloriasTerritoriales.setEtiqueta("CONTRALORÍAS TERRITORIALES");
			contraloriasTerritoriales.setTabulacion(ResumenCargosPublicos.NIVEL3);
			contraloriasTerritoriales.setValorAdministrativosUniformados(BigInteger.ZERO);
			contraloriasTerritoriales.setValorDocentes(BigInteger.ZERO);
			contraloriasTerritoriales.setTotalEmpleosPublicos(BigInteger.ZERO);
			contraloriasTerritoriales.setValorTrabajadoresOficiales(BigInteger.ZERO);
			contraloriasTerritoriales.setGranTotal(BigInteger.ZERO);
			contraloriasTerritoriales.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos descentralizadas = new ResumenCargosPublicos();
			descentralizadas.setEtiqueta("DESCENTRALIZADAS");
			descentralizadas.setTabulacion(ResumenCargosPublicos.NIVEL3);
			descentralizadas.setValorAdministrativosUniformados(BigInteger.ZERO);
			descentralizadas.setValorDocentes(BigInteger.ZERO);
			descentralizadas.setTotalEmpleosPublicos(BigInteger.ZERO);
			descentralizadas.setValorTrabajadoresOficiales(BigInteger.ZERO);
			descentralizadas.setGranTotal(BigInteger.ZERO);
			descentralizadas.setValorRegimenPrivado(BigInteger.ZERO);

			ordenTerritorialSinBogota.setValorAdministrativosUniformados(empleosDepartamentales
					.getValorAdministrativosUniformados().add(empleosMunicipios.getValorAdministrativosUniformados())
					.add(personerias.getValorAdministrativosUniformados())
					.add(contraloriasTerritoriales.getValorAdministrativosUniformados())
					.add(descentralizadas.getValorAdministrativosUniformados()));
			ordenTerritorialSinBogota.setValorDocentes(empleosDepartamentales.getValorDocentes()
					.add(empleosMunicipios.getValorDocentes()).add(personerias.getValorDocentes())
					.add(contraloriasTerritoriales.getValorDocentes()).add(descentralizadas.getValorDocentes()));
			ordenTerritorialSinBogota.setTotalEmpleosPublicos(empleosDepartamentales.getTotalEmpleosPublicos()
					.add(empleosMunicipios.getTotalEmpleosPublicos()).add(personerias.getTotalEmpleosPublicos())
					.add(contraloriasTerritoriales.getTotalEmpleosPublicos())
					.add(descentralizadas.getTotalEmpleosPublicos()));
			ordenTerritorialSinBogota.setValorTrabajadoresOficiales(empleosDepartamentales
					.getValorTrabajadoresOficiales().add(empleosMunicipios.getValorTrabajadoresOficiales())
					.add(personerias.getValorTrabajadoresOficiales())
					.add(contraloriasTerritoriales.getValorTrabajadoresOficiales())
					.add(descentralizadas.getValorTrabajadoresOficiales()));
			ordenTerritorialSinBogota.setGranTotal(empleosDepartamentales.getTotalEmpleosPublicos()
					.add(empleosMunicipios.getValorTrabajadoresOficiales()));
			ordenTerritorialSinBogota.setValorRegimenPrivado(empleosDepartamentales.getValorRegimenPrivado()
					.add(empleosMunicipios.getValorRegimenPrivado()).add(personerias.getValorRegimenPrivado())
					.add(contraloriasTerritoriales.getValorRegimenPrivado())
					.add(descentralizadas.getValorRegimenPrivado()));

			ResumenCargosPublicos bogota = new ResumenCargosPublicos();
			bogota.setEtiqueta("Bogotá");
			bogota.setTabulacion(ResumenCargosPublicos.NIVEL2);
			bogota.setValorAdministrativosUniformados(BigInteger.ZERO);
			bogota.setValorDocentes(BigInteger.ZERO);
			bogota.setTotalEmpleosPublicos(BigInteger.ZERO);
			bogota.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bogota.setGranTotal(BigInteger.ZERO);
			bogota.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos bogotaSectorCentralConcejo = new ResumenCargosPublicos();
			bogotaSectorCentralConcejo.setEtiqueta("Sector Central y Concejo");
			bogotaSectorCentralConcejo.setTabulacion(ResumenCargosPublicos.NIVEL3);
			bogotaSectorCentralConcejo.setValorAdministrativosUniformados(BigInteger.ZERO);
			bogotaSectorCentralConcejo.setValorDocentes(BigInteger.ZERO);
			bogotaSectorCentralConcejo.setTotalEmpleosPublicos(BigInteger.ZERO);
			bogotaSectorCentralConcejo.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bogotaSectorCentralConcejo.setGranTotal(BigInteger.ZERO);
			bogotaSectorCentralConcejo.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos bogotaPersonaria = new ResumenCargosPublicos();
			bogotaPersonaria.setEtiqueta("Personería");
			bogotaPersonaria.setTabulacion(ResumenCargosPublicos.NIVEL3);
			bogotaPersonaria.setValorAdministrativosUniformados(BigInteger.ZERO);
			bogotaPersonaria.setValorDocentes(BigInteger.ZERO);
			bogotaPersonaria.setTotalEmpleosPublicos(BigInteger.ZERO);
			bogotaPersonaria.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bogotaPersonaria.setGranTotal(BigInteger.ZERO);
			bogotaPersonaria.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos bogotaContraloria = new ResumenCargosPublicos();
			bogotaContraloria.setEtiqueta("Contraloría");
			bogotaContraloria.setTabulacion(ResumenCargosPublicos.NIVEL3);
			bogotaContraloria.setValorAdministrativosUniformados(BigInteger.ZERO);
			bogotaContraloria.setValorDocentes(BigInteger.ZERO);
			bogotaContraloria.setTotalEmpleosPublicos(BigInteger.ZERO);
			bogotaContraloria.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bogotaContraloria.setGranTotal(BigInteger.ZERO);
			bogotaContraloria.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos bogotaDescentralizadas = new ResumenCargosPublicos();
			bogotaDescentralizadas.setEtiqueta("Descentralizadas");
			bogotaDescentralizadas.setTabulacion(ResumenCargosPublicos.NIVEL3);
			bogotaDescentralizadas.setValorAdministrativosUniformados(BigInteger.ZERO);
			bogotaDescentralizadas.setValorDocentes(BigInteger.ZERO);
			bogotaDescentralizadas.setTotalEmpleosPublicos(BigInteger.ZERO);
			bogotaDescentralizadas.setValorTrabajadoresOficiales(BigInteger.ZERO);
			bogotaDescentralizadas.setGranTotal(BigInteger.ZERO);
			bogotaDescentralizadas.setValorRegimenPrivado(BigInteger.ZERO);

			bogota.setValorAdministrativosUniformados(bogotaSectorCentralConcejo.getValorAdministrativosUniformados()
					.add(bogotaPersonaria.getValorAdministrativosUniformados())
					.add(bogotaContraloria.getValorAdministrativosUniformados())
					.add(bogotaDescentralizadas.getValorAdministrativosUniformados()));

			ordenNacional.setValorAdministrativosUniformados(ramaEjecutiva.getValorAdministrativosUniformados()
					.add(ramaLegislativa.getValorAdministrativosUniformados())
					.add(ramaJudicial.getValorAdministrativosUniformados())
					.add(organosControl.getValorAdministrativosUniformados())
					.add(organizacionElectoral.getValorAdministrativosUniformados())
					.add(entesAutonomos.getValorAdministrativosUniformados())
					.add(sistemaIntegralVerdad.getValorAdministrativosUniformados()));
			ordenNacional.setValorDocentes(ramaEjecutiva.getValorDocentes().add(ramaLegislativa.getValorDocentes())
					.add(ramaJudicial.getValorDocentes()).add(organosControl.getValorDocentes())
					.add(entesAutonomos.getValorDocentes()));
			ordenNacional.setTotalEmpleosPublicos(
					ordenNacional.getValorAdministrativosUniformados().add(ordenNacional.getValorDocentes()));
			ordenNacional.setValorTrabajadoresOficiales(
					ramaEjecutiva.getValorTrabajadoresOficiales().add(ramaLegislativa.getValorTrabajadoresOficiales())
							.add(ramaJudicial.getValorTrabajadoresOficiales())
							.add(organosControl.getValorTrabajadoresOficiales())
							.add(entesAutonomos.getValorTrabajadoresOficiales()));
			ordenNacional.setGranTotal(
					ordenNacional.getTotalEmpleosPublicos().add(ordenNacional.getValorTrabajadoresOficiales()));
			ordenNacional.setValorRegimenPrivado(ramaEjecutiva.getValorRegimenPrivado()
					.add(ramaLegislativa.getValorRegimenPrivado()).add(ramaJudicial.getValorRegimenPrivado())
					.add(organosControl.getValorRegimenPrivado()).add(organizacionElectoral.getValorRegimenPrivado())
					.add(entesAutonomos.getValorRegimenPrivado()));

			ordenTerritorial.setValorAdministrativosUniformados(ordenTerritorialSinBogota
					.getValorAdministrativosUniformados().add(bogota.getValorAdministrativosUniformados()));
			ordenTerritorial
					.setValorDocentes(ordenTerritorialSinBogota.getValorDocentes().add(bogota.getValorDocentes()));
			ordenTerritorial.setTotalEmpleosPublicos(
					ordenTerritorial.getValorAdministrativosUniformados().add(ordenTerritorial.getValorDocentes()));
			ordenTerritorial.setValorTrabajadoresOficiales(ordenTerritorialSinBogota.getValorTrabajadoresOficiales()
					.add(bogota.getValorTrabajadoresOficiales()));
			ordenTerritorial.setGranTotal(
					ordenTerritorial.getTotalEmpleosPublicos().add(ordenTerritorial.getValorTrabajadoresOficiales()));
			ordenTerritorial.setValorRegimenPrivado(ordenTerritorialSinBogota.getValorAdministrativosUniformados()
					.add(bogota.getValorAdministrativosUniformados()));

			listaResumenCargosPublicos.add(ordenNacional);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(ramaEjecutiva);
			listaResumenCargosPublicos.add(ministerios);
			listaResumenCargosPublicos.add(sectorEducacionTerritorial);
			listaResumenCargosPublicos.add(docentes);
			listaResumenCargosPublicos.add(directivosDocentes);
			listaResumenCargosPublicos.add(personalFuerzaPublica);
			listaResumenCargosPublicos.add(uniformadosFuerzasMilitares);
			listaResumenCargosPublicos.add(ufmOficial);
			listaResumenCargosPublicos.add(ufmSubOficial);
			listaResumenCargosPublicos.add(ufmAlumno);
			listaResumenCargosPublicos.add(ufmSoldado);
			listaResumenCargosPublicos.add(uniformadosPoliciaNacional);
			listaResumenCargosPublicos.add(upnOficial);
			listaResumenCargosPublicos.add(upnNivelEjecutivo);
			listaResumenCargosPublicos.add(upnSubOficial);
			listaResumenCargosPublicos.add(upnAgente);
			listaResumenCargosPublicos.add(upnAlumno);
			listaResumenCargosPublicos.add(upnAuxBachiller);
			listaResumenCargosPublicos.add(upnAuxRegular);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(ramaLegislativa);
			listaResumenCargosPublicos.add(camaraRepresentantesEmpleados);
			listaResumenCargosPublicos.add(senadoRepublicaEmpleados);
			listaResumenCargosPublicos.add(senadoresRepresentantesCamara);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(ramaJudicial);
			listaResumenCargosPublicos.add(direccionEjecutivaAdmonJudicialMagistrados);
			listaResumenCargosPublicos.add(direccionEjecutivaAdmonJudicialEmpl);
			listaResumenCargosPublicos.add(consejoSuperiorSalaAdministrativaMagistrados);
			listaResumenCargosPublicos.add(consejoSuperiorSalaAdministrativaEmpleados);
			listaResumenCargosPublicos.add(consejoSuperiorSalaDisciplinariaMagistrados);
			listaResumenCargosPublicos.add(consejoSuperiorSalaDisciplinariaEmpleados);
			listaResumenCargosPublicos.add(consejoEstadoMagistrados);
			listaResumenCargosPublicos.add(consejoEstadoEmpleados);
			listaResumenCargosPublicos.add(corteConstitucionalMagistrados);
			listaResumenCargosPublicos.add(corteConstitucionalEmpleados);
			listaResumenCargosPublicos.add(corteSupremaJusticiaMagistrados);
			listaResumenCargosPublicos.add(corteSupremaJusticiaEmpleados);
			listaResumenCargosPublicos.add(servidoresJudiciales);
			listaResumenCargosPublicos.add(servidoresJudicialesEmpleados);
			listaResumenCargosPublicos.add(servidoresJudicialesJuecesMagistrados);
			listaResumenCargosPublicos.add(fiscaliaGeneralNacion);
			listaResumenCargosPublicos.add(institutoMedicinaLegal);
			listaResumenCargosPublicos.add(conocimientoInnovacionJusticia);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(organosControl);
			listaResumenCargosPublicos.add(procuraduriaGeneralNacion);
			listaResumenCargosPublicos.add(defensoriaPueblo);
			listaResumenCargosPublicos.add(contraloriaGeneralRepublica);
			listaResumenCargosPublicos.add(fondoBienestarSocial);
			listaResumenCargosPublicos.add(auditoriaGeneralRepublica);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(organizacionElectoral);
			listaResumenCargosPublicos.add(registraduriaNacionalEstadoCivil);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(entesAutonomos);
			listaResumenCargosPublicos.add(autoridadNacionalTelevision);
			listaResumenCargosPublicos.add(bancoRepublica);
			listaResumenCargosPublicos.add(corporacionesAutonomasRegionales);
			listaResumenCargosPublicos.add(entesUniversitariosAutonomos);
			listaResumenCargosPublicos.add(comisionNacionalServicioCivil);
			listaResumenCargosPublicos.add(espacio);
			listaResumenCargosPublicos.add(sistemaIntegralVerdad);
			listaResumenCargosPublicos.add(justiciaEspecialPaz);
			listaResumenCargosPublicos.add(comisionEsclarecimientoVerdad);
			listaResumenCargosPublicos.add(unidadBusquedaPersonas);
			listaResumenCargosPublicos.add(espacio);

			listaResumenCargosPublicos.add(ordenTerritorial);
			listaResumenCargosPublicos.add(ordenTerritorialSinBogota);
			listaResumenCargosPublicos.add(empleosDepartamentales);
			listaResumenCargosPublicos.add(empleosMunicipios);
			listaResumenCargosPublicos.add(personerias);
			listaResumenCargosPublicos.add(contraloriasTerritoriales);
			listaResumenCargosPublicos.add(descentralizadas);
			listaResumenCargosPublicos.add(bogota);
			listaResumenCargosPublicos.add(bogotaSectorCentralConcejo);
			listaResumenCargosPublicos.add(bogotaPersonaria);
			listaResumenCargosPublicos.add(bogotaContraloria);
			listaResumenCargosPublicos.add(bogotaDescentralizadas);

			ResumenCargosPublicos totalesNacionalTerritorial = new ResumenCargosPublicos();
			totalesNacionalTerritorial.setEtiqueta("TOTALES ORDEN NACIONAL Y TERRITORIAL");
			totalesNacionalTerritorial.setTabulacion(ResumenCargosPublicos.NIVEL1);
			totalesNacionalTerritorial.setValorAdministrativosUniformados(ordenNacional
					.getValorAdministrativosUniformados().add(ordenTerritorial.getValorAdministrativosUniformados()));
			totalesNacionalTerritorial
					.setValorDocentes(ordenNacional.getValorDocentes().add(ordenTerritorial.getValorDocentes()));
			totalesNacionalTerritorial.setTotalEmpleosPublicos(
					ordenNacional.getTotalEmpleosPublicos().add(ordenTerritorial.getTotalEmpleosPublicos()));
			totalesNacionalTerritorial.setValorTrabajadoresOficiales(ordenNacional.getValorTrabajadoresOficiales()
					.add(ordenTerritorial.getValorTrabajadoresOficiales()));
			totalesNacionalTerritorial.setGranTotal(ordenNacional.getGranTotal().add(ordenTerritorial.getGranTotal()));
			totalesNacionalTerritorial.setValorRegimenPrivado(
					ordenNacional.getValorRegimenPrivado().add(ordenTerritorial.getValorRegimenPrivado()));

			ResumenCargosPublicos totalEmpleosPublicos = new ResumenCargosPublicos();
			totalEmpleosPublicos.setEtiqueta("TOTAL EMPLEOS PÚBLICOS (Nacional y territorial)=");
			totalEmpleosPublicos.setTabulacion(ResumenCargosPublicos.NIVEL1_1);
			totalEmpleosPublicos.setValorAdministrativosUniformados(BigInteger.ZERO);
			totalEmpleosPublicos.setValorDocentes(totalesNacionalTerritorial.getGranTotal());
			totalEmpleosPublicos.setTotalEmpleosPublicos(BigInteger.ZERO);
			totalEmpleosPublicos.setValorTrabajadoresOficiales(BigInteger.ZERO);
			totalEmpleosPublicos.setGranTotal(BigInteger.ZERO);
			totalEmpleosPublicos.setValorRegimenPrivado(BigInteger.ZERO);

			ResumenCargosPublicos totalPlantasPrivadas = new ResumenCargosPublicos();
			totalPlantasPrivadas.setEtiqueta("+ PLANTAS PRIVADAS=");
			totalPlantasPrivadas.setTabulacion(ResumenCargosPublicos.NIVEL1_1);
			totalPlantasPrivadas.setValorAdministrativosUniformados(BigInteger.ZERO);
			totalPlantasPrivadas.setValorDocentes(BigInteger.ZERO);
			totalPlantasPrivadas.setTotalEmpleosPublicos(BigInteger.ZERO);
			totalPlantasPrivadas.setValorTrabajadoresOficiales(BigInteger.ZERO);
			totalPlantasPrivadas.setGranTotal(BigInteger.ZERO);
			totalPlantasPrivadas.setValorRegimenPrivado(
					totalEmpleosPublicos.getValorDocentes().add(totalesNacionalTerritorial.getValorRegimenPrivado()));

			ResumenCargosPublicos fechaCortePlantasPrivadas = new ResumenCargosPublicos();
			fechaCortePlantasPrivadas
					.setEtiqueta("Fecha de Corte: " + DateUtils.formatearACadenaLarga(DateUtils.getFechaSistema()));

			ResumenCargosPublicos notas = new ResumenCargosPublicos();
			notas.setEtiqueta("NOTAS:");
			notas.setTabulacion(ResumenCargosPublicos.NIVEL1_3);

			ResumenCargosPublicos uno = new ResumenCargosPublicos();
			uno.setEtiqueta(
					"1: EL TOTAL DE EMPLEOS PÚBLICOS INCLUYE UN TOTAL DE 2.187 EMPLEOS DE CARÁCTER TEMPORAL QUE SE ENCUENTRAN ACTIVOS A 1 DE MARZO DE 2018");
			uno.setTabulacion(ResumenCargosPublicos.NIVEL1_4);

			ResumenCargosPublicos dos = new ResumenCargosPublicos();
			dos.setEtiqueta(
					"2:La Directora Ejecutvia se encuentra en el mismo nivel de Magistrado de Alta corte. Ley 270/96.");
			dos.setTabulacion(ResumenCargosPublicos.NIVEL1_4);

			ResumenCargosPublicos tres = new ResumenCargosPublicos();
			tres.setEtiqueta(
					"3: LA PROCURADURÍA GENERAL DE LA NACIÓN INCLUYE EL INSTITUTO DE ESTUDIOS DEL MINISTERIO PÚBLICO (EL CUAL FUNCIONA CON LA PLANTA DE PERSONAL DE LA PROCURADURÍA)");
			tres.setTabulacion(ResumenCargosPublicos.NIVEL1_4);

			ResumenCargosPublicos fuentes = new ResumenCargosPublicos();
			fuentes.setEtiqueta("Fuentes:");
			fuentes.setTabulacion(ResumenCargosPublicos.NIVEL1_5);

			ResumenCargosPublicos empleoRamaEjecutiva = new ResumenCargosPublicos();
			empleoRamaEjecutiva.setEtiqueta("Empleo Rama Ejecutiva, Legislativa y Órganos Autónomos: DAFP-SIGEP");
			empleoRamaEjecutiva.setTabulacion(ResumenCargosPublicos.NIVEL1_6);

			ResumenCargosPublicos personalUniformado = new ResumenCargosPublicos();
			personalUniformado.setEtiqueta("Personal Uniformado Sector Defensa: Ministerio de Defensa Nacional");
			personalUniformado.setTabulacion(ResumenCargosPublicos.NIVEL1_6);

			ResumenCargosPublicos personalDocente = new ResumenCargosPublicos();
			personalDocente.setEtiqueta(
					"Personal Docente y administrativo sector educativo de orden territorial: Ministerio de Educación Nacional");
			personalDocente.setTabulacion(ResumenCargosPublicos.NIVEL1_6);

			ResumenCargosPublicos personalRamaJuridicial = new ResumenCargosPublicos();
			personalRamaJuridicial.setEtiqueta("Personal Rama Judicial: Consejo Superior de la Judicatura");
			personalRamaJuridicial.setTabulacion(ResumenCargosPublicos.NIVEL1_6);

			ResumenCargosPublicos empleosTerritoriales = new ResumenCargosPublicos();
			empleosTerritoriales.setEtiqueta(
					"Empleos territoriales: reporte SHIP Contraloria General de la República 2016 y Departamento Administrativo del Servicio Civil Distrital -DASCD Diciembre 2017");
			empleosTerritoriales.setTabulacion(ResumenCargosPublicos.NIVEL1_6);

			ResumenCargosPublicos separador = new ResumenCargosPublicos();
			separador.setEtiqueta(" ");
			separador.setTabulacion(ResumenCargosPublicos.NIVEL1_5);

			listaTotalesResumenCargosPublicos.add(totalesNacionalTerritorial);
			listaTotalesResumenCargosPublicos.add(espacio);
			listaTotalesResumenCargosPublicos.add(totalEmpleosPublicos);
			listaTotalesResumenCargosPublicos.add(totalPlantasPrivadas);
			listaTotalesResumenCargosPublicos.add(fechaCortePlantasPrivadas);
			listaTotalesResumenCargosPublicos.add(espacio);
			listaTotalesResumenCargosPublicos.add(notas);
			listaTotalesResumenCargosPublicos.add(uno);
			listaTotalesResumenCargosPublicos.add(dos);
			listaTotalesResumenCargosPublicos.add(tres);
			listaTotalesResumenCargosPublicos.add(separador);
			listaTotalesResumenCargosPublicos.add(fuentes);
			listaTotalesResumenCargosPublicos.add(empleoRamaEjecutiva);
			listaTotalesResumenCargosPublicos.add(personalUniformado);
			listaTotalesResumenCargosPublicos.add(personalDocente);
			listaTotalesResumenCargosPublicos.add(personalRamaJuridicial);
			listaTotalesResumenCargosPublicos.add(empleosTerritoriales);

			List<Parametrica> listaClasificacionOrganica = null;

			listaClasificacionOrganica = ComunicacionServiciosSis
					.getParametricaPorIdPadre(CifrasEmpleoPublicoBean.getClasificacionOrganica());

			Parametrica clasificacionOrganica = null;
			if (!habilitarConsulta) {
				clasificacionOrganica = ComunicacionServiciosSis
						.getParametricaporId(this.getEntidadUsuario().getCodClasificacionOrganica());
			}

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
			}

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
			
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);			
		} catch (Exception e) {
			logger.error("init()", e);
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
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla
	 * en componentes de exportación de Primefaces a través de la propiedad
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
	 * Marca en el valor {@link Boolean} de <code>todos</code> todas las
	 * tematicas de formulario de busqueda
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
