package co.gov.dafp.sigep2.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import co.gov.dafp.sigep2.utils.ErrorMensajes;

/**
 * Clase que implementa los registros del tablero de Cifras de Empleo Publico
 */
public class CifrasEmpleoPublico extends ErrorMensajes {
	private static final long serialVersionUID = 306692591949668968L;

	private long codCifrasEmpleoPublico;

	private String codEntidad;
	private String entidad;

	private BigInteger codSector;
	private String sector;

	private BigInteger codOrden;
	private String orden;

	private BigInteger codClasificacionOrganica;
	private String clasificacionOrganica;

	private BigInteger codNaturalezaJuridica;
	private String naturalezaJuridica;

	private BigInteger codSistemaCarrera;
	private String sistemaCarrera;

	private String situacion;

	private BigInteger codDepartamento;
	private String departamento;
	private BigInteger codMunicipio;
	private String municipio;
	private BigInteger codCategoria;
	private String categoria;

	private BigInteger codRegimenSalarial;
	private String regimenSalarial;
	
	private BigInteger categoriaMunicipio;

	// Tematicas
	private boolean planAnualVacantes = false;
	private boolean leyCuotas = false;
	private boolean empleos = false;
	private boolean naturaleza = false;
	private boolean seguimiento = false;
	private boolean tableroSeguimiento = false;
	private boolean acuerdosGestion = false;
	private boolean capacitacion = false;
	private boolean bienestarIncentivos = false;
	private boolean horariosFlexibles = false;
	private boolean teletrabajo = false;
	private boolean bilinguismo = false;

	private boolean avanceAsesor = false;
	private boolean consolidadoGestion = false;

	// Plan Anual de Vacantes
	private String debeReportarPAV;
	private String reportoPAV;
	private BigInteger vacDefinitivaAsesor;
	private BigInteger vacDefinitivaProfesional;
	private BigInteger vacDefinitivaTecnico;
	private BigInteger vacDefinitivaAsistencial;
	private BigInteger vacDefinitivaOtro;
	private BigInteger vacDefinitivaTotal;
	private BigInteger encargoAsesor;
	private BigInteger encargoProfesional;
	private BigInteger encargoTecnico;
	private BigInteger encargoAsistencial;
	private BigInteger encargoOtro;
	private BigInteger encargoTotal;
	private BigInteger provisionalidadAsesor;
	private BigInteger provisionalidadProfesional;
	private BigInteger provisionalidadTecnico;
	private BigInteger provisionalidadAsistencial;
	private BigInteger provisionalidadOtro;
	private BigInteger provisionalidadTotal;
	private BigInteger porProveerAsesor;
	private BigInteger porProveerProfesional;
	private BigInteger porProveerTecnico;
	private BigInteger porProveerAsistencial;
	private BigInteger porProveerOtro;
	private BigInteger porProveerTotal;
	private String observacionPAV;

	// Ley de Cuotas
	private String debeReportarLC;
	private String reportoLC;
	private BigInteger maximoNivelDecisorioTotal;
	private BigInteger maximoNivelDecisorioVacantes;
	private BigInteger maximoNivelDecisorioProvistos;
	private BigInteger maximoNivelDecisorioMujeres;
	private BigInteger maximoNivelDecisorioHombres;
	private BigDecimal maximoNivelDecisorioPMujeres;
	private BigDecimal maximoNivelDecisorioPHombres;
	private BigInteger otroNivelDecisorioTotal;
	private BigInteger otroNivelDecisorioVacantes;
	private BigInteger otroNivelDecisorioProvistos;
	private BigInteger otroNivelDecisorioMujeres;
	private BigInteger otroNivelDecisorioHombres;
	private BigDecimal otroNivelDecisorioPMujeres;
	private BigDecimal otroNivelDecisorioPHombres;
	private String observacionLC;

	// Empleos
	private String debeReportarEmp;
	private String seguimientoSigepEmp;
	private BigInteger totalPlantaEmp;
	private BigInteger plantaPermanenteEmp;
	private BigInteger plantaTemporalEmp;
	private BigInteger plantaTransitoriaEmp;
	private BigInteger trabajadoresOficialesEmp;
	private BigInteger plantaPrivadaEmp;
	private BigInteger plantaDocentesEmp;
	private BigInteger sistemaCarreraEmp;
	private BigInteger decretoSalarialEmp;
	private BigInteger escalaSalarialEmp;
	private BigInteger sistemaNomenclaturaEmp;
	private BigInteger decretoNomenclaturaEmp;
	private BigInteger nomenclaturaEmpleosEmp;

	// Naturaleza
	private BigInteger eleccionPopularEmp;
	private BigInteger empleosLibreNomRemEmp;
	private BigInteger empleosCarreraEmp;
	private BigInteger empleosCarreraDipEmp;
	private BigInteger periodoFijoEmp;
	private BigInteger empleosTemporalesEmp;
	private BigInteger totalEmp;
	private BigInteger granTotalEmp;
	private String observacionEmp;

	// Seguimiento
	private BigInteger totalPlantaNorma;
	private BigInteger plataAprobadaReal;
	private BigInteger plantaPermanenteDistribuida;
	private BigInteger plantaTemporalDistribuida;
	private BigInteger plantaTransitoriaDistribuida;
	private BigInteger totalNumeroEmpleosPlantaDistribuida;
	private BigInteger numeroEmpleadosVinculados;
	private BigInteger vacantesReportaEntidad;
	private BigInteger totalHVActivasEmpleadosPublicos;
	private BigInteger numeroDeclaracionesBienesRentasVigencia;
	private BigInteger totalActivasContratistas;
	private BigInteger numeroContratosVigentes;
	private BigDecimal numeroVinculadosSobreNumeroCargosPlanta;
	private BigDecimal pesoPorcentajeVinculacion;
	private BigDecimal porcentajeVinculacion;
	private BigDecimal numeroContratosVigentesSobreNumeroHVActivasContratistas;
	private BigDecimal pesoPorcentajeContrato;
	private BigDecimal porcentajeContratos;
	private BigDecimal ipvc;
	private String observacionesSEG;

	// Acuerdos de Gestion
	private String debeReportarAG;
	private String reportoAG;
	private BigInteger numeroGerentesPublicos;
	private String observacionAG;

	// Capacitacion
	private String debeReportarCap;
	private String adoptoCap;
	private String actoAdministrativoCap;
	private String entidadesPIC;
	private BigInteger cantidadPAE;
	private String describaPAE;
	private BigDecimal presupuestoAnualPIC;
	private String cantidadServidoresDiplomadosDAFP;
	private BigInteger cuantosServidoresDiplomados;
	private String diplomadosTomadosDAFP;
	private String ofertasCapacitacionEntidad;
	private String cuentaServidoresBenBecasDAFP;
	private BigInteger cuantosServidoresBenBecas;
	private String becasDAFPOtorgadas;
	private String observacionCap;

	// Bienestar e Incentivos
	private String debeReportarBeI;
	private String adoptoBeI;
	private String manifestoBeI;
	private BigInteger cantidadServidoresBeI;
	private String observacionBeI;

	// Horarios flexibles
	private String adoptoHF;
	private String actoAdministrativoHF;
	private BigInteger cantidadServidoresHF;
	private String observacionHF;

	// Teletrabajo
	private String adoptoTel;
	private String actoAdministrativoTel;
	private BigInteger cantidadServidoresTel;
	private String observacionTel;

	// Bilinguismo
	private String cartaBil;
	private Date inicioBil;
	private BigInteger cantidadPreInscBeguinner;
	private BigInteger cantidadPreInscA11;
	private BigInteger cantidadPreInscA12;
	private BigInteger cantidadPreInscA13;
	private BigInteger totalPreInscA1;
	private BigInteger cantidadPreInscA21;
	private BigInteger cantidadPreInscA22;
	private BigInteger cantidadPreInscA23;
	private BigInteger totalPreInscA2;
	private BigInteger cantidadPreInscB11;
	private BigInteger cantidadPreInscB12;
	private BigInteger cantidadPreInscB13;
	private BigInteger totalPreInscB1;
	private BigInteger cantidadIniBeguinner;
	private BigInteger cantidadIniA11;
	private BigInteger cantidadIniA12;
	private BigInteger cantidadIniA13;
	private BigInteger totalIniA1;
	private BigInteger cantidadIniA21;
	private BigInteger cantidadIniA22;
	private BigInteger cantidadIniA23;
	private BigInteger totalIniA2;
	private BigInteger cantidadIniB11;
	private BigInteger cantidadIniB12;
	private BigInteger cantidadIniB13;
	private BigInteger totalIniB1;
	private BigInteger cantidadTerBeguinner;
	private BigInteger cantidadTerA11;
	private BigInteger cantidadTerA12;
	private BigInteger cantidadTerA13;
	private BigInteger totalTerA1;
	private BigInteger cantidadTerA21;
	private BigInteger cantidadTerA22;
	private BigInteger cantidadTerA23;
	private BigInteger totalTerA2;
	private BigInteger cantidadTerB11;
	private BigInteger cantidadTerB12;
	private BigInteger cantidadTerB13;
	private BigInteger totalTerB1;
	private BigInteger cantidadAproBeguinner;
	private BigInteger cantidadAproA11;
	private BigInteger cantidadAproA12;
	private BigInteger cantidadAproA13;
	private BigInteger totalAproA1;
	private BigInteger cantidadAproA21;
	private BigInteger cantidadAproA22;
	private BigInteger cantidadAproA23;
	private BigInteger totalAproA2;
	private BigInteger cantidadAproB11;
	private BigInteger cantidadAproB12;
	private BigInteger cantidadAproB13;
	private BigInteger totalAproB1;
	private String observacionesBil;

	// Avance asesor
	private String nombreAsesor;
	private Date fechaCumplimientoMeta;
	private BigInteger cantidadEntidades;

	// Consolidado Gestion
	private String consolidado;
	private BigDecimal metaTerritorialAnual;
	private BigDecimal metaPrimerTrimestre;
	private BigDecimal avance;

	private BigInteger limitIni;
	private BigInteger limitFin;
	private BigInteger total;
	
	private BigDecimal codSuborden;
	private String suborden;	
	
	private String flgDependenciaEspecial;
	 
	public BigInteger getSistemaNomenclaturaEmp() {
		return sistemaNomenclaturaEmp;
	}

	public void setSistemaNomenclaturaEmp(BigInteger sistemaNomenclaturaEmp) {
		this.sistemaNomenclaturaEmp = sistemaNomenclaturaEmp;
	}

	public BigInteger getDecretoNomenclaturaEmp() {
		return decretoNomenclaturaEmp;
	}

	public void setDecretoNomenclaturaEmp(BigInteger decretoNomenclaturaEmp) {
		this.decretoNomenclaturaEmp = decretoNomenclaturaEmp;
	}

	public BigInteger getNomenclaturaEmpleosEmp() {
		return nomenclaturaEmpleosEmp;
	}

	public void setNomenclaturaEmpleosEmp(BigInteger nomenclaturaEmpleosEmp) {
		this.nomenclaturaEmpleosEmp = nomenclaturaEmpleosEmp;
	}

	public long getCodCifrasEmpleoPublico() {
		return codCifrasEmpleoPublico;
	}

	public void setCodCifrasEmpleoPublico(long codCifrasEmpleoPublico) {
		this.codCifrasEmpleoPublico = codCifrasEmpleoPublico;
	}

	public String getCodEntidad() {
		return codEntidad;
	}

	public void setCodEntidad(String codEntidad) {
		this.codEntidad = codEntidad;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public BigInteger getCodSector() {
		return codSector;
	}

	public void setCodSector(BigInteger codSector) {
		this.codSector = codSector;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public BigInteger getCodOrden() {
		return codOrden;
	}

	public void setCodOrden(BigInteger codOrden) {
		this.codOrden = codOrden;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public BigInteger getCodClasificacionOrganica() {
		return codClasificacionOrganica;
	}

	public void setCodClasificacionOrganica(BigInteger codClasificacionOrganica) {
		this.codClasificacionOrganica = codClasificacionOrganica;
	}

	public String getClasificacionOrganica() {
		return clasificacionOrganica;
	}

	public void setClasificacionOrganica(String clasificacionOrganica) {
		this.clasificacionOrganica = clasificacionOrganica;
	}

	public BigInteger getCodNaturalezaJuridica() {
		return codNaturalezaJuridica;
	}

	public void setCodNaturalezaJuridica(BigInteger codNaturalezaJuridica) {
		this.codNaturalezaJuridica = codNaturalezaJuridica;
	}

	public String getNaturalezaJuridica() {
		return naturalezaJuridica;
	}

	public void setNaturalezaJuridica(String naturalezaJuridica) {
		this.naturalezaJuridica = naturalezaJuridica;
	}

	public BigInteger getCodSistemaCarrera() {
		return codSistemaCarrera;
	}

	public void setCodSistemaCarrera(BigInteger codSistemaCarrera) {
		this.codSistemaCarrera = codSistemaCarrera;
	}

	public String getSistemaCarrera() {
		return sistemaCarrera;
	}

	public void setSistemaCarrera(String sistemaCarrera) {
		this.sistemaCarrera = sistemaCarrera;
	}

	public String getSituacion() {
		return situacion;
	}

	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}

	public BigInteger getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(BigInteger codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public BigInteger getCodMunicipio() {
		return codMunicipio;
	}

	public void setCodMunicipio(BigInteger codMunicipio) {
		this.codMunicipio = codMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public BigInteger getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(BigInteger codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public BigInteger getCodRegimenSalarial() {
		return codRegimenSalarial;
	}

	public void setCodRegimenSalarial(BigInteger codRegimenSalarial) {
		this.codRegimenSalarial = codRegimenSalarial;
	}

	public String getRegimenSalarial() {
		return regimenSalarial;
	}

	public void setRegimenSalarial(String regimenSalarial) {
		this.regimenSalarial = regimenSalarial;
	}

	public boolean isPlanAnualVacantes() {
		return planAnualVacantes;
	}

	public void setPlanAnualVacantes(boolean planAnualVacantes) {
		this.planAnualVacantes = planAnualVacantes;
	}

	public boolean isLeyCuotas() {
		return leyCuotas;
	}

	public void setLeyCuotas(boolean leyCuotas) {
		this.leyCuotas = leyCuotas;
	}

	public boolean isEmpleos() {
		return empleos;
	}

	public void setEmpleos(boolean empleos) {
		this.empleos = empleos;
	}

	public boolean isNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(boolean naturaleza) {
		this.naturaleza = naturaleza;
	}

	public boolean isSeguimiento() {
		return seguimiento;
	}

	public void setSeguimiento(boolean seguimiento) {
		this.seguimiento = seguimiento;
	}

	public boolean isTableroSeguimiento() {
		return tableroSeguimiento;
	}

	public void setTableroSeguimiento(boolean tableroSeguimiento) {
		this.tableroSeguimiento = tableroSeguimiento;
	}

	public boolean isAcuerdosGestion() {
		return acuerdosGestion;
	}

	public void setAcuerdosGestion(boolean acuerdosGestion) {
		this.acuerdosGestion = acuerdosGestion;
	}

	public boolean isCapacitacion() {
		return capacitacion;
	}

	public void setCapacitacion(boolean capacitacion) {
		this.capacitacion = capacitacion;
	}

	public boolean isBienestarIncentivos() {
		return bienestarIncentivos;
	}

	public void setBienestarIncentivos(boolean bienestarIncentivos) {
		this.bienestarIncentivos = bienestarIncentivos;
	}

	public boolean isHorariosFlexibles() {
		return horariosFlexibles;
	}

	public void setHorariosFlexibles(boolean horariosFlexibles) {
		this.horariosFlexibles = horariosFlexibles;
	}

	public boolean isTeletrabajo() {
		return teletrabajo;
	}

	public void setTeletrabajo(boolean teletrabajo) {
		this.teletrabajo = teletrabajo;
	}

	public boolean isBilinguismo() {
		return bilinguismo;
	}

	public void setBilinguismo(boolean bilinguismo) {
		this.bilinguismo = bilinguismo;
	}

	public String getDebeReportarPAV() {
		return debeReportarPAV;
	}

	public void setDebeReportarPAV(String debeReportarPAV) {
		this.debeReportarPAV = debeReportarPAV;
	}

	public String getReportoPAV() {
		return reportoPAV;
	}

	public void setReportoPAV(String reportoPAV) {
		this.reportoPAV = reportoPAV;
	}

	public BigInteger getVacDefinitivaAsesor() {
		return vacDefinitivaAsesor;
	}

	public void setVacDefinitivaAsesor(BigInteger vacDefinitivaAsesor) {
		this.vacDefinitivaAsesor = vacDefinitivaAsesor;
	}

	public BigInteger getVacDefinitivaProfesional() {
		return vacDefinitivaProfesional;
	}

	public void setVacDefinitivaProfesional(BigInteger vacDefinitivaProfesional) {
		this.vacDefinitivaProfesional = vacDefinitivaProfesional;
	}

	public BigInteger getVacDefinitivaTecnico() {
		return vacDefinitivaTecnico;
	}

	public void setVacDefinitivaTecnico(BigInteger vacDefinitivaTecnico) {
		this.vacDefinitivaTecnico = vacDefinitivaTecnico;
	}

	public BigInteger getVacDefinitivaAsistencial() {
		return vacDefinitivaAsistencial;
	}

	public void setVacDefinitivaAsistencial(BigInteger vacDefinitivaAsistencial) {
		this.vacDefinitivaAsistencial = vacDefinitivaAsistencial;
	}

	public BigInteger getVacDefinitivaTotal() {
		return vacDefinitivaTotal;
	}

	public void setVacDefinitivaTotal(BigInteger vacDefinitivaTotal) {
		this.vacDefinitivaTotal = vacDefinitivaTotal;
	}

	public BigInteger getEncargoAsesor() {
		return encargoAsesor;
	}

	public void setEncargoAsesor(BigInteger encargoAsesor) {
		this.encargoAsesor = encargoAsesor;
	}

	public BigInteger getEncargoProfesional() {
		return encargoProfesional;
	}

	public void setEncargoProfesional(BigInteger encargoProfesional) {
		this.encargoProfesional = encargoProfesional;
	}

	public BigInteger getEncargoTecnico() {
		return encargoTecnico;
	}

	public void setEncargoTecnico(BigInteger encargoTecnico) {
		this.encargoTecnico = encargoTecnico;
	}

	public BigInteger getEncargoAsistencial() {
		return encargoAsistencial;
	}

	public void setEncargoAsistencial(BigInteger encargoAsistencial) {
		this.encargoAsistencial = encargoAsistencial;
	}

	public BigInteger getEncargoTotal() {
		return encargoTotal;
	}

	public void setEncargoTotal(BigInteger encargoTotal) {
		this.encargoTotal = encargoTotal;
	}

	public BigInteger getProvisionalidadAsesor() {
		return provisionalidadAsesor;
	}

	public void setProvisionalidadAsesor(BigInteger provisionalidadAsesor) {
		this.provisionalidadAsesor = provisionalidadAsesor;
	}

	public BigInteger getProvisionalidadProfesional() {
		return provisionalidadProfesional;
	}

	public void setProvisionalidadProfesional(BigInteger provisionalidadProfesional) {
		this.provisionalidadProfesional = provisionalidadProfesional;
	}

	public BigInteger getProvisionalidadTecnico() {
		return provisionalidadTecnico;
	}

	public void setProvisionalidadTecnico(BigInteger provisionalidadTecnico) {
		this.provisionalidadTecnico = provisionalidadTecnico;
	}

	public BigInteger getProvisionalidadAsistencial() {
		return provisionalidadAsistencial;
	}

	public void setProvisionalidadAsistencial(BigInteger provisionalidadAsistencial) {
		this.provisionalidadAsistencial = provisionalidadAsistencial;
	}

	public BigInteger getProvisionalidadTotal() {
		return provisionalidadTotal;
	}

	public void setProvisionalidadTotal(BigInteger provisionalidadTotal) {
		this.provisionalidadTotal = provisionalidadTotal;
	}

	public BigInteger getPorProveerAsesor() {
		return porProveerAsesor;
	}

	public void setPorProveerAsesor(BigInteger porProveerAsesor) {
		this.porProveerAsesor = porProveerAsesor;
	}

	public BigInteger getPorProveerProfesional() {
		return porProveerProfesional;
	}

	public void setPorProveerProfesional(BigInteger porProveerProfesional) {
		this.porProveerProfesional = porProveerProfesional;
	}

	public BigInteger getPorProveerTecnico() {
		return porProveerTecnico;
	}

	public void setPorProveerTecnico(BigInteger porProveerTecnico) {
		this.porProveerTecnico = porProveerTecnico;
	}

	public BigInteger getPorProveerAsistencial() {
		return porProveerAsistencial;
	}

	public void setPorProveerAsistencial(BigInteger porProveerAsistencial) {
		this.porProveerAsistencial = porProveerAsistencial;
	}

	public BigInteger getPorProveerTotal() {
		return porProveerTotal;
	}

	public void setPorProveerTotal(BigInteger porProveerTotal) {
		this.porProveerTotal = porProveerTotal;
	}

	public String getObservacionPAV() {
		return observacionPAV;
	}

	public void setObservacionPAV(String observacionPAV) {
		this.observacionPAV = observacionPAV;
	}

	public String getDebeReportarLC() {
		return debeReportarLC;
	}

	public void setDebeReportarLC(String debeReportarLC) {
		this.debeReportarLC = debeReportarLC;
	}

	public String getReportoLC() {
		return reportoLC;
	}

	public void setReportoLC(String reportoLC) {
		this.reportoLC = reportoLC;
	}

	public BigInteger getMaximoNivelDecisorioTotal() {
		return maximoNivelDecisorioTotal;
	}

	public void setMaximoNivelDecisorioTotal(BigInteger maximoNivelDecisorioTotal) {
		this.maximoNivelDecisorioTotal = maximoNivelDecisorioTotal;
	}

	public BigInteger getMaximoNivelDecisorioVacantes() {
		return maximoNivelDecisorioVacantes;
	}

	public void setMaximoNivelDecisorioVacantes(BigInteger maximoNivelDecisorioVacantes) {
		this.maximoNivelDecisorioVacantes = maximoNivelDecisorioVacantes;
	}

	public BigInteger getMaximoNivelDecisorioProvistos() {
		return maximoNivelDecisorioProvistos;
	}

	public void setMaximoNivelDecisorioProvistos(BigInteger maximoNivelDecisorioProvistos) {
		this.maximoNivelDecisorioProvistos = maximoNivelDecisorioProvistos;
	}

	public BigInteger getMaximoNivelDecisorioMujeres() {
		return maximoNivelDecisorioMujeres;
	}

	public void setMaximoNivelDecisorioMujeres(BigInteger maximoNivelDecisorioMujeres) {
		this.maximoNivelDecisorioMujeres = maximoNivelDecisorioMujeres;
	}

	public BigInteger getMaximoNivelDecisorioHombres() {
		return maximoNivelDecisorioHombres;
	}

	public void setMaximoNivelDecisorioHombres(BigInteger maximoNivelDecisorioHombres) {
		this.maximoNivelDecisorioHombres = maximoNivelDecisorioHombres;
	}

	public BigDecimal getMaximoNivelDecisorioPMujeres() {
		return maximoNivelDecisorioPMujeres;
	}

	public void setMaximoNivelDecisorioPMujeres(BigDecimal maximoNivelDecisorioPMujeres) {
		this.maximoNivelDecisorioPMujeres = maximoNivelDecisorioPMujeres;
	}

	public BigDecimal getMaximoNivelDecisorioPHombres() {
		return maximoNivelDecisorioPHombres;
	}

	public void setMaximoNivelDecisorioPHombres(BigDecimal maximoNivelDecisorioPHombres) {
		this.maximoNivelDecisorioPHombres = maximoNivelDecisorioPHombres;
	}

	public BigInteger getOtroNivelDecisorioTotal() {
		return otroNivelDecisorioTotal;
	}

	public void setOtroNivelDecisorioTotal(BigInteger otroNivelDecisorioTotal) {
		this.otroNivelDecisorioTotal = otroNivelDecisorioTotal;
	}

	public BigInteger getOtroNivelDecisorioVacantes() {
		return otroNivelDecisorioVacantes;
	}

	public void setOtroNivelDecisorioVacantes(BigInteger otroNivelDecisorioVacantes) {
		this.otroNivelDecisorioVacantes = otroNivelDecisorioVacantes;
	}

	public BigInteger getOtroNivelDecisorioProvistos() {
		return otroNivelDecisorioProvistos;
	}

	public void setOtroNivelDecisorioProvistos(BigInteger otroNivelDecisorioProvistos) {
		this.otroNivelDecisorioProvistos = otroNivelDecisorioProvistos;
	}

	public BigInteger getOtroNivelDecisorioMujeres() {
		return otroNivelDecisorioMujeres;
	}

	public void setOtroNivelDecisorioMujeres(BigInteger otroNivelDecisorioMujeres) {
		this.otroNivelDecisorioMujeres = otroNivelDecisorioMujeres;
	}

	public BigInteger getOtroNivelDecisorioHombres() {
		return otroNivelDecisorioHombres;
	}

	public void setOtroNivelDecisorioHombres(BigInteger otroNivelDecisorioHombres) {
		this.otroNivelDecisorioHombres = otroNivelDecisorioHombres;
	}

	public BigDecimal getOtroNivelDecisorioPMujeres() {
		return otroNivelDecisorioPMujeres;
	}

	public void setOtroNivelDecisorioPMujeres(BigDecimal otroNivelDecisorioPMujeres) {
		this.otroNivelDecisorioPMujeres = otroNivelDecisorioPMujeres;
	}

	public BigDecimal getOtroNivelDecisorioPHombres() {
		return otroNivelDecisorioPHombres;
	}

	public void setOtroNivelDecisorioPHombres(BigDecimal otroNivelDecisorioPHombres) {
		this.otroNivelDecisorioPHombres = otroNivelDecisorioPHombres;
	}

	public String getObservacionLC() {
		return observacionLC;
	}

	public void setObservacionLC(String observacionLC) {
		this.observacionLC = observacionLC;
	}

	public String getDebeReportarEmp() {
		return debeReportarEmp;
	}

	public void setDebeReportarEmp(String debeReportarEmp) {
		this.debeReportarEmp = debeReportarEmp;
	}

	public String getSeguimientoSigepEmp() {
		return seguimientoSigepEmp;
	}

	public void setSeguimientoSigepEmp(String seguimientoSigepEmp) {
		this.seguimientoSigepEmp = seguimientoSigepEmp;
	}

	public BigInteger getTotalPlantaEmp() {
		return totalPlantaEmp;
	}

	public void setTotalPlantaEmp(BigInteger totalPlantaEmp) {
		this.totalPlantaEmp = totalPlantaEmp;
	}

	public BigInteger getPlantaPermanenteEmp() {
		return plantaPermanenteEmp;
	}

	public void setPlantaPermanenteEmp(BigInteger plantaPermanenteEmp) {
		this.plantaPermanenteEmp = plantaPermanenteEmp;
	}

	public BigInteger getPlantaTemporalEmp() {
		return plantaTemporalEmp;
	}

	public void setPlantaTemporalEmp(BigInteger plantaTemporalEmp) {
		this.plantaTemporalEmp = plantaTemporalEmp;
	}

	public BigInteger getPlantaTransitoriaEmp() {
		return plantaTransitoriaEmp;
	}

	public void setPlantaTransitoriaEmp(BigInteger plantaTransitoriaEmp) {
		this.plantaTransitoriaEmp = plantaTransitoriaEmp;
	}

	public BigInteger getTrabajadoresOficialesEmp() {
		return trabajadoresOficialesEmp;
	}

	public void setTrabajadoresOficialesEmp(BigInteger trabajadoresOficialesEmp) {
		this.trabajadoresOficialesEmp = trabajadoresOficialesEmp;
	}

	public BigInteger getPlantaPrivadaEmp() {
		return plantaPrivadaEmp;
	}

	public void setPlantaPrivadaEmp(BigInteger plantaPrivadaEmp) {
		this.plantaPrivadaEmp = plantaPrivadaEmp;
	}

	public BigInteger getPlantaDocentesEmp() {
		return plantaDocentesEmp;
	}

	public void setPlantaDocentesEmp(BigInteger plantaDocentesEmp) {
		this.plantaDocentesEmp = plantaDocentesEmp;
	}

	public BigInteger getSistemaCarreraEmp() {
		return sistemaCarreraEmp;
	}

	public void setSistemaCarreraEmp(BigInteger sistemaCarreraEmp) {
		this.sistemaCarreraEmp = sistemaCarreraEmp;
	}

	public BigInteger getDecretoSalarialEmp() {
		return decretoSalarialEmp;
	}

	public void setDecretoSalarialEmp(BigInteger decretoSalarialEmp) {
		this.decretoSalarialEmp = decretoSalarialEmp;
	}

	public BigInteger getEscalaSalarialEmp() {
		return escalaSalarialEmp;
	}

	public void setEscalaSalarialEmp(BigInteger escalaSalarialEmp) {
		this.escalaSalarialEmp = escalaSalarialEmp;
	}

	public BigInteger getEleccionPopularEmp() {
		return eleccionPopularEmp;
	}

	public void setEleccionPopularEmp(BigInteger eleccionPopularEmp) {
		this.eleccionPopularEmp = eleccionPopularEmp;
	}

	public BigInteger getEmpleosLibreNomRemEmp() {
		return empleosLibreNomRemEmp;
	}

	public void setEmpleosLibreNomRemEmp(BigInteger empleosLibreNomRemEmp) {
		this.empleosLibreNomRemEmp = empleosLibreNomRemEmp;
	}

	public BigInteger getEmpleosCarreraEmp() {
		return empleosCarreraEmp;
	}

	public void setEmpleosCarreraEmp(BigInteger empleosCarreraEmp) {
		this.empleosCarreraEmp = empleosCarreraEmp;
	}

	public BigInteger getEmpleosCarreraDipEmp() {
		return empleosCarreraDipEmp;
	}

	public void setEmpleosCarreraDipEmp(BigInteger empleosCarreraDipEmp) {
		this.empleosCarreraDipEmp = empleosCarreraDipEmp;
	}

	public BigInteger getPeriodoFijoEmp() {
		return periodoFijoEmp;
	}

	public void setPeriodoFijoEmp(BigInteger periodoFijoEmp) {
		this.periodoFijoEmp = periodoFijoEmp;
	}

	public BigInteger getTotalEmp() {
		return totalEmp;
	}

	public void setTotalEmp(BigInteger totalEmp) {
		this.totalEmp = totalEmp;
	}

	public BigInteger getGranTotalEmp() {
		return granTotalEmp;
	}

	public void setGranTotalEmp(BigInteger granTotalEmp) {
		this.granTotalEmp = granTotalEmp;
	}

	public String getObservacionEmp() {
		return observacionEmp;
	}

	public void setObservacionEmp(String observacionEmp) {
		this.observacionEmp = observacionEmp;
	}

	public BigInteger getTotalPlantaNorma() {
		return totalPlantaNorma;
	}

	public void setTotalPlantaNorma(BigInteger totalPlantaNorma) {
		this.totalPlantaNorma = totalPlantaNorma;
	}

	public BigInteger getPlataAprobadaReal() {
		return plataAprobadaReal;
	}

	public void setPlataAprobadaReal(BigInteger plataAprobadaReal) {
		this.plataAprobadaReal = plataAprobadaReal;
	}

	public BigInteger getPlantaPermanenteDistribuida() {
		return plantaPermanenteDistribuida;
	}

	public void setPlantaPermanenteDistribuida(BigInteger plantaPermanenteDistribuida) {
		this.plantaPermanenteDistribuida = plantaPermanenteDistribuida;
	}

	public BigInteger getPlantaTemporalDistribuida() {
		return plantaTemporalDistribuida;
	}

	public void setPlantaTemporalDistribuida(BigInteger plantaTemporalDistribuida) {
		this.plantaTemporalDistribuida = plantaTemporalDistribuida;
	}

	public BigInteger getPlantaTransitoriaDistribuida() {
		return plantaTransitoriaDistribuida;
	}

	public void setPlantaTransitoriaDistribuida(BigInteger plantaTransitoriaDistribuida) {
		this.plantaTransitoriaDistribuida = plantaTransitoriaDistribuida;
	}

	public BigInteger getTotalNumeroEmpleosPlantaDistribuida() {
		return totalNumeroEmpleosPlantaDistribuida;
	}

	public void setTotalNumeroEmpleosPlantaDistribuida(BigInteger totalNumeroEmpleosPlantaDistribuida) {
		this.totalNumeroEmpleosPlantaDistribuida = totalNumeroEmpleosPlantaDistribuida;
	}

	public BigInteger getNumeroEmpleadosVinculados() {
		return numeroEmpleadosVinculados;
	}

	public void setNumeroEmpleadosVinculados(BigInteger numeroEmpleadosVinculados) {
		this.numeroEmpleadosVinculados = numeroEmpleadosVinculados;
	}

	public BigInteger getVacantesReportaEntidad() {
		return vacantesReportaEntidad;
	}

	public void setVacantesReportaEntidad(BigInteger vacantesReportaEntidad) {
		this.vacantesReportaEntidad = vacantesReportaEntidad;
	}

	public BigInteger getTotalHVActivasEmpleadosPublicos() {
		return totalHVActivasEmpleadosPublicos;
	}

	public void setTotalHVActivasEmpleadosPublicos(BigInteger totalHVActivasEmpleadosPublicos) {
		this.totalHVActivasEmpleadosPublicos = totalHVActivasEmpleadosPublicos;
	}

	public BigInteger getNumeroDeclaracionesBienesRentasVigencia() {
		return numeroDeclaracionesBienesRentasVigencia;
	}

	public void setNumeroDeclaracionesBienesRentasVigencia(BigInteger numeroDeclaracionesBienesRentasVigencia) {
		this.numeroDeclaracionesBienesRentasVigencia = numeroDeclaracionesBienesRentasVigencia;
	}

	public BigInteger getTotalActivasContratistas() {
		return totalActivasContratistas;
	}

	public void setTotalActivasContratistas(BigInteger totalActivasContratistas) {
		this.totalActivasContratistas = totalActivasContratistas;
	}

	public BigInteger getNumeroContratosVigentes() {
		return numeroContratosVigentes;
	}

	public void setNumeroContratosVigentes(BigInteger numeroContratosVigentes) {
		this.numeroContratosVigentes = numeroContratosVigentes;
	}

	public BigDecimal getNumeroVinculadosSobreNumeroCargosPlanta() {
		return numeroVinculadosSobreNumeroCargosPlanta;
	}

	public void setNumeroVinculadosSobreNumeroCargosPlanta(BigDecimal numeroVinculadosSobreNumeroCargosPlanta) {
		this.numeroVinculadosSobreNumeroCargosPlanta = numeroVinculadosSobreNumeroCargosPlanta;
	}

	public BigDecimal getPesoPorcentajeVinculacion() {
		return pesoPorcentajeVinculacion;
	}

	public void setPesoPorcentajeVinculacion(BigDecimal pesoPorcentajeVinculacion) {
		this.pesoPorcentajeVinculacion = pesoPorcentajeVinculacion;
	}

	public BigDecimal getPorcentajeVinculacion() {
		return porcentajeVinculacion;
	}

	public void setPorcentajeVinculacion(BigDecimal porcentajeVinculacion) {
		this.porcentajeVinculacion = porcentajeVinculacion;
	}

	public BigDecimal getNumeroContratosVigentesSobreNumeroHVActivasContratistas() {
		return numeroContratosVigentesSobreNumeroHVActivasContratistas;
	}

	public void setNumeroContratosVigentesSobreNumeroHVActivasContratistas(
			BigDecimal numeroContratosVigentesSobreNumeroHVActivasContratistas) {
		this.numeroContratosVigentesSobreNumeroHVActivasContratistas = numeroContratosVigentesSobreNumeroHVActivasContratistas;
	}

	public BigDecimal getPesoPorcentajeContrato() {
		return pesoPorcentajeContrato;
	}

	public void setPesoPorcentajeContrato(BigDecimal pesoPorcentajeContrato) {
		this.pesoPorcentajeContrato = pesoPorcentajeContrato;
	}

	public BigDecimal getPorcentajeContratos() {
		return porcentajeContratos;
	}

	public void setPorcentajeContratos(BigDecimal porcentajeContratos) {
		this.porcentajeContratos = porcentajeContratos;
	}

	public BigDecimal getIpvc() {
		return ipvc;
	}

	public void setIpvc(BigDecimal ipvc) {
		this.ipvc = ipvc;
	}

	public String getObservacionesSEG() {
		return observacionesSEG;
	}

	public void setObservacionesSEG(String observacionesSEG) {
		this.observacionesSEG = observacionesSEG;
	}

	public String getDebeReportarAG() {
		return debeReportarAG;
	}

	public void setDebeReportarAG(String debeReportarAG) {
		this.debeReportarAG = debeReportarAG;
	}

	public String getReportoAG() {
		return reportoAG;
	}

	public void setReportoAG(String reportoAG) {
		this.reportoAG = reportoAG;
	}

	public BigInteger getNumeroGerentesPublicos() {
		return numeroGerentesPublicos;
	}

	public void setNumeroGerentesPublicos(BigInteger numeroGerentesPublicos) {
		this.numeroGerentesPublicos = numeroGerentesPublicos;
	}

	public String getObservacionAG() {
		return observacionAG;
	}

	public void setObservacionAG(String observacionAG) {
		this.observacionAG = observacionAG;
	}

	public String getDebeReportarCap() {
		return debeReportarCap;
	}

	public void setDebeReportarCap(String debeReportarCap) {
		this.debeReportarCap = debeReportarCap;
	}

	public String getAdoptoCap() {
		return adoptoCap;
	}

	public void setAdoptoCap(String adoptoCap) {
		this.adoptoCap = adoptoCap;
	}

	public String getActoAdministrativoCap() {
		return actoAdministrativoCap;
	}

	public void setActoAdministrativoCap(String actoAdministrativoCap) {
		this.actoAdministrativoCap = actoAdministrativoCap;
	}

	public String getEntidadesPIC() {
		return entidadesPIC;
	}

	public void setEntidadesPIC(String entidadesPIC) {
		this.entidadesPIC = entidadesPIC;
	}

	public BigInteger getCantidadPAE() {
		return cantidadPAE;
	}

	public void setCantidadPAE(BigInteger cantidadPAE) {
		this.cantidadPAE = cantidadPAE;
	}

	public String getDescribaPAE() {
		return describaPAE;
	}

	public void setDescribaPAE(String describaPAE) {
		this.describaPAE = describaPAE;
	}

	public BigDecimal getPresupuestoAnualPIC() {
		return presupuestoAnualPIC;
	}

	public void setPresupuestoAnualPIC(BigDecimal presupuestoAnualPIC) {
		this.presupuestoAnualPIC = presupuestoAnualPIC;
	}

	public String getCantidadServidoresDiplomadosDAFP() {
		return cantidadServidoresDiplomadosDAFP;
	}

	public void setCantidadServidoresDiplomadosDAFP(String cantidadServidoresDiplomadosDAFP) {
		this.cantidadServidoresDiplomadosDAFP = cantidadServidoresDiplomadosDAFP;
	}

	public String getDiplomadosTomadosDAFP() {
		return diplomadosTomadosDAFP;
	}

	public void setDiplomadosTomadosDAFP(String diplomadosTomadosDAFP) {
		this.diplomadosTomadosDAFP = diplomadosTomadosDAFP;
	}

	public String getOfertasCapacitacionEntidad() {
		return ofertasCapacitacionEntidad;
	}

	public void setOfertasCapacitacionEntidad(String ofertasCapacitacionEntidad) {
		this.ofertasCapacitacionEntidad = ofertasCapacitacionEntidad;
	}

	public String getCuentaServidoresBenBecasDAFP() {
		return cuentaServidoresBenBecasDAFP;
	}

	public void setCuentaServidoresBenBecasDAFP(String cuentaServidoresBenBecasDAFP) {
		this.cuentaServidoresBenBecasDAFP = cuentaServidoresBenBecasDAFP;
	}

	public String getBecasDAFPOtorgadas() {
		return becasDAFPOtorgadas;
	}

	public void setBecasDAFPOtorgadas(String becasDAFPOtorgadas) {
		this.becasDAFPOtorgadas = becasDAFPOtorgadas;
	}

	public String getObservacionCap() {
		return observacionCap;
	}

	public void setObservacionCap(String observacionCap) {
		this.observacionCap = observacionCap;
	}

	public String getDebeReportarBeI() {
		return debeReportarBeI;
	}

	public void setDebeReportarBeI(String debeReportarBeI) {
		this.debeReportarBeI = debeReportarBeI;
	}

	public String getAdoptoBeI() {
		return adoptoBeI;
	}

	public void setAdoptoBeI(String adoptoBeI) {
		this.adoptoBeI = adoptoBeI;
	}

	public String getManifestoBeI() {
		return manifestoBeI;
	}

	public void setManifestoBeI(String manifestoBeI) {
		this.manifestoBeI = manifestoBeI;
	}

	public BigInteger getCantidadServidoresBeI() {
		return cantidadServidoresBeI;
	}

	public void setCantidadServidoresBeI(BigInteger cantidadServidoresBeI) {
		this.cantidadServidoresBeI = cantidadServidoresBeI;
	}

	public String getObservacionBeI() {
		return observacionBeI;
	}

	public void setObservacionBeI(String observacionBeI) {
		this.observacionBeI = observacionBeI;
	}

	public String getAdoptoHF() {
		return adoptoHF;
	}

	public void setAdoptoHF(String adoptoHF) {
		this.adoptoHF = adoptoHF;
	}

	public String getActoAdministrativoHF() {
		return actoAdministrativoHF;
	}

	public void setActoAdministrativoHF(String actoAdministrativoHF) {
		this.actoAdministrativoHF = actoAdministrativoHF;
	}

	public BigInteger getCantidadServidoresHF() {
		return cantidadServidoresHF;
	}

	public void setCantidadServidoresHF(BigInteger cantidadServidoresHF) {
		this.cantidadServidoresHF = cantidadServidoresHF;
	}

	public String getObservacionHF() {
		return observacionHF;
	}

	public void setObservacionHF(String observacionHF) {
		this.observacionHF = observacionHF;
	}

	public String getAdoptoTel() {
		return adoptoTel;
	}

	public void setAdoptoTel(String adoptoTel) {
		this.adoptoTel = adoptoTel;
	}

	public String getActoAdministrativoTel() {
		return actoAdministrativoTel;
	}

	public void setActoAdministrativoTel(String actoAdministrativoTel) {
		this.actoAdministrativoTel = actoAdministrativoTel;
	}

	public BigInteger getCantidadServidoresTel() {
		return cantidadServidoresTel;
	}

	public void setCantidadServidoresTel(BigInteger cantidadServidoresTel) {
		this.cantidadServidoresTel = cantidadServidoresTel;
	}

	public String getObservacionTel() {
		return observacionTel;
	}

	public void setObservacionTel(String observacionTel) {
		this.observacionTel = observacionTel;
	}

	public String getCartaBil() {
		return cartaBil;
	}

	public void setCartaBil(String cartaBil) {
		this.cartaBil = cartaBil;
	}

	public Date getInicioBil() {
		return inicioBil;
	}

	public void setInicioBil(Date inicioBil) {
		this.inicioBil = inicioBil;
	}

	public BigInteger getCantidadPreInscBeguinner() {
		return cantidadPreInscBeguinner;
	}

	public void setCantidadPreInscBeguinner(BigInteger cantidadPreInscBeguinner) {
		this.cantidadPreInscBeguinner = cantidadPreInscBeguinner;
	}

	public BigInteger getCantidadPreInscA11() {
		return cantidadPreInscA11;
	}

	public void setCantidadPreInscA11(BigInteger cantidadPreInscA11) {
		this.cantidadPreInscA11 = cantidadPreInscA11;
	}

	public BigInteger getCantidadPreInscA12() {
		return cantidadPreInscA12;
	}

	public void setCantidadPreInscA12(BigInteger cantidadPreInscA12) {
		this.cantidadPreInscA12 = cantidadPreInscA12;
	}

	public BigInteger getCantidadPreInscA13() {
		return cantidadPreInscA13;
	}

	public void setCantidadPreInscA13(BigInteger cantidadPreInscA13) {
		this.cantidadPreInscA13 = cantidadPreInscA13;
	}

	public BigInteger getTotalPreInscA1() {
		return totalPreInscA1;
	}

	public void setTotalPreInscA1(BigInteger totalPreInscA1) {
		this.totalPreInscA1 = totalPreInscA1;
	}

	public BigInteger getCantidadPreInscA21() {
		return cantidadPreInscA21;
	}

	public void setCantidadPreInscA21(BigInteger cantidadPreInscA21) {
		this.cantidadPreInscA21 = cantidadPreInscA21;
	}

	public BigInteger getCantidadPreInscA22() {
		return cantidadPreInscA22;
	}

	public void setCantidadPreInscA22(BigInteger cantidadPreInscA22) {
		this.cantidadPreInscA22 = cantidadPreInscA22;
	}

	public BigInteger getCantidadPreInscA23() {
		return cantidadPreInscA23;
	}

	public void setCantidadPreInscA23(BigInteger cantidadPreInscA23) {
		this.cantidadPreInscA23 = cantidadPreInscA23;
	}

	public BigInteger getTotalPreInscA2() {
		return totalPreInscA2;
	}

	public void setTotalPreInscA2(BigInteger totalPreInscA2) {
		this.totalPreInscA2 = totalPreInscA2;
	}

	public BigInteger getCantidadPreInscB11() {
		return cantidadPreInscB11;
	}

	public void setCantidadPreInscB11(BigInteger cantidadPreInscB11) {
		this.cantidadPreInscB11 = cantidadPreInscB11;
	}

	public BigInteger getCantidadPreInscB12() {
		return cantidadPreInscB12;
	}

	public void setCantidadPreInscB12(BigInteger cantidadPreInscB12) {
		this.cantidadPreInscB12 = cantidadPreInscB12;
	}

	public BigInteger getCantidadPreInscB13() {
		return cantidadPreInscB13;
	}

	public void setCantidadPreInscB13(BigInteger cantidadPreInscB13) {
		this.cantidadPreInscB13 = cantidadPreInscB13;
	}

	public BigInteger getTotalPreInscB1() {
		return totalPreInscB1;
	}

	public void setTotalPreInscB1(BigInteger totalPreInscB1) {
		this.totalPreInscB1 = totalPreInscB1;
	}

	public BigInteger getCantidadIniBeguinner() {
		return cantidadIniBeguinner;
	}

	public void setCantidadIniBeguinner(BigInteger cantidadIniBeguinner) {
		this.cantidadIniBeguinner = cantidadIniBeguinner;
	}

	public BigInteger getCantidadIniA11() {
		return cantidadIniA11;
	}

	public void setCantidadIniA11(BigInteger cantidadIniA11) {
		this.cantidadIniA11 = cantidadIniA11;
	}

	public BigInteger getCantidadIniA12() {
		return cantidadIniA12;
	}

	public void setCantidadIniA12(BigInteger cantidadIniA12) {
		this.cantidadIniA12 = cantidadIniA12;
	}

	public BigInteger getCantidadIniA13() {
		return cantidadIniA13;
	}

	public void setCantidadIniA13(BigInteger cantidadIniA13) {
		this.cantidadIniA13 = cantidadIniA13;
	}

	public BigInteger getTotalIniA1() {
		return totalIniA1;
	}

	public void setTotalIniA1(BigInteger totalIniA1) {
		this.totalIniA1 = totalIniA1;
	}

	public BigInteger getCantidadIniA21() {
		return cantidadIniA21;
	}

	public void setCantidadIniA21(BigInteger cantidadIniA21) {
		this.cantidadIniA21 = cantidadIniA21;
	}

	public BigInteger getCantidadIniA22() {
		return cantidadIniA22;
	}

	public void setCantidadIniA22(BigInteger cantidadIniA22) {
		this.cantidadIniA22 = cantidadIniA22;
	}

	public BigInteger getCantidadIniA23() {
		return cantidadIniA23;
	}

	public void setCantidadIniA23(BigInteger cantidadIniA23) {
		this.cantidadIniA23 = cantidadIniA23;
	}

	public BigInteger getTotalIniA2() {
		return totalIniA2;
	}

	public void setTotalIniA2(BigInteger totalIniA2) {
		this.totalIniA2 = totalIniA2;
	}

	public BigInteger getCantidadIniB11() {
		return cantidadIniB11;
	}

	public void setCantidadIniB11(BigInteger cantidadIniB11) {
		this.cantidadIniB11 = cantidadIniB11;
	}

	public BigInteger getCantidadIniB12() {
		return cantidadIniB12;
	}

	public void setCantidadIniB12(BigInteger cantidadIniB12) {
		this.cantidadIniB12 = cantidadIniB12;
	}

	public BigInteger getCantidadIniB13() {
		return cantidadIniB13;
	}

	public void setCantidadIniB13(BigInteger cantidadIniB13) {
		this.cantidadIniB13 = cantidadIniB13;
	}

	public BigInteger getTotalIniB1() {
		return totalIniB1;
	}

	public void setTotalIniB1(BigInteger totalIniB1) {
		this.totalIniB1 = totalIniB1;
	}

	public BigInteger getCantidadTerBeguinner() {
		return cantidadTerBeguinner;
	}

	public void setCantidadTerBeguinner(BigInteger cantidadTerBeguinner) {
		this.cantidadTerBeguinner = cantidadTerBeguinner;
	}

	public BigInteger getCantidadTerA11() {
		return cantidadTerA11;
	}

	public void setCantidadTerA11(BigInteger cantidadTerA11) {
		this.cantidadTerA11 = cantidadTerA11;
	}

	public BigInteger getCantidadTerA12() {
		return cantidadTerA12;
	}

	public void setCantidadTerA12(BigInteger cantidadTerA12) {
		this.cantidadTerA12 = cantidadTerA12;
	}

	public BigInteger getCantidadTerA13() {
		return cantidadTerA13;
	}

	public void setCantidadTerA13(BigInteger cantidadTerA13) {
		this.cantidadTerA13 = cantidadTerA13;
	}

	public BigInteger getTotalTerA1() {
		return totalTerA1;
	}

	public void setTotalTerA1(BigInteger totalTerA1) {
		this.totalTerA1 = totalTerA1;
	}

	public BigInteger getCantidadTerA21() {
		return cantidadTerA21;
	}

	public void setCantidadTerA21(BigInteger cantidadTerA21) {
		this.cantidadTerA21 = cantidadTerA21;
	}

	public BigInteger getCantidadTerA22() {
		return cantidadTerA22;
	}

	public void setCantidadTerA22(BigInteger cantidadTerA22) {
		this.cantidadTerA22 = cantidadTerA22;
	}

	public BigInteger getCantidadTerA23() {
		return cantidadTerA23;
	}

	public void setCantidadTerA23(BigInteger cantidadTerA23) {
		this.cantidadTerA23 = cantidadTerA23;
	}

	public BigInteger getTotalTerA2() {
		return totalTerA2;
	}

	public void setTotalTerA2(BigInteger totalTerA2) {
		this.totalTerA2 = totalTerA2;
	}

	public BigInteger getCantidadTerB11() {
		return cantidadTerB11;
	}

	public void setCantidadTerB11(BigInteger cantidadTerB11) {
		this.cantidadTerB11 = cantidadTerB11;
	}

	public BigInteger getCantidadTerB12() {
		return cantidadTerB12;
	}

	public void setCantidadTerB12(BigInteger cantidadTerB12) {
		this.cantidadTerB12 = cantidadTerB12;
	}

	public BigInteger getCantidadTerB13() {
		return cantidadTerB13;
	}

	public void setCantidadTerB13(BigInteger cantidadTerB13) {
		this.cantidadTerB13 = cantidadTerB13;
	}

	public BigInteger getTotalTerB1() {
		return totalTerB1;
	}

	public void setTotalTerB1(BigInteger totalTerB1) {
		this.totalTerB1 = totalTerB1;
	}

	public BigInteger getCantidadAproBeguinner() {
		return cantidadAproBeguinner;
	}

	public void setCantidadAproBeguinner(BigInteger cantidadAproBeguinner) {
		this.cantidadAproBeguinner = cantidadAproBeguinner;
	}

	public BigInteger getCantidadAproA11() {
		return cantidadAproA11;
	}

	public void setCantidadAproA11(BigInteger cantidadAproA11) {
		this.cantidadAproA11 = cantidadAproA11;
	}

	public BigInteger getCantidadAproA12() {
		return cantidadAproA12;
	}

	public void setCantidadAproA12(BigInteger cantidadAproA12) {
		this.cantidadAproA12 = cantidadAproA12;
	}

	public BigInteger getCantidadAproA13() {
		return cantidadAproA13;
	}

	public void setCantidadAproA13(BigInteger cantidadAproA13) {
		this.cantidadAproA13 = cantidadAproA13;
	}

	public BigInteger getTotalAproA1() {
		return totalAproA1;
	}

	public void setTotalAproA1(BigInteger totalAproA1) {
		this.totalAproA1 = totalAproA1;
	}

	public BigInteger getCantidadAproA21() {
		return cantidadAproA21;
	}

	public void setCantidadAproA21(BigInteger cantidadAproA21) {
		this.cantidadAproA21 = cantidadAproA21;
	}

	public BigInteger getCantidadAproA22() {
		return cantidadAproA22;
	}

	public void setCantidadAproA22(BigInteger cantidadAproA22) {
		this.cantidadAproA22 = cantidadAproA22;
	}

	public BigInteger getCantidadAproA23() {
		return cantidadAproA23;
	}

	public void setCantidadAproA23(BigInteger cantidadAproA23) {
		this.cantidadAproA23 = cantidadAproA23;
	}

	public BigInteger getTotalAproA2() {
		return totalAproA2;
	}

	public void setTotalAproA2(BigInteger totalAproA2) {
		this.totalAproA2 = totalAproA2;
	}

	public BigInteger getCantidadAproB11() {
		return cantidadAproB11;
	}

	public void setCantidadAproB11(BigInteger cantidadAproB11) {
		this.cantidadAproB11 = cantidadAproB11;
	}

	public BigInteger getCantidadAproB12() {
		return cantidadAproB12;
	}

	public void setCantidadAproB12(BigInteger cantidadAproB12) {
		this.cantidadAproB12 = cantidadAproB12;
	}

	public BigInteger getCantidadAproB13() {
		return cantidadAproB13;
	}

	public void setCantidadAproB13(BigInteger cantidadAproB13) {
		this.cantidadAproB13 = cantidadAproB13;
	}

	public BigInteger getTotalAproB1() {
		return totalAproB1;
	}

	public void setTotalAproB1(BigInteger totalAproB1) {
		this.totalAproB1 = totalAproB1;
	}

	public String getObservacionesBil() {
		return observacionesBil;
	}

	public void setObservacionesBil(String observacionesBil) {
		this.observacionesBil = observacionesBil;
	}

	public boolean isAvanceAsesor() {
		return avanceAsesor;
	}

	public void setAvanceAsesor(boolean avanceAsesor) {
		this.avanceAsesor = avanceAsesor;
	}

	public boolean isConsolidadoGestion() {
		return consolidadoGestion;
	}

	public void setConsolidadoGestion(boolean consolidadoGestion) {
		this.consolidadoGestion = consolidadoGestion;
	}

	public String getNombreAsesor() {
		return nombreAsesor;
	}

	public void setNombreAsesor(String nombreAsesor) {
		this.nombreAsesor = nombreAsesor;
	}

	public Date getFechaCumplimientoMeta() {
		return fechaCumplimientoMeta;
	}

	public void setFechaCumplimientoMeta(Date fechaCumplimientoMeta) {
		this.fechaCumplimientoMeta = fechaCumplimientoMeta;
	}

	public BigInteger getCantidadEntidades() {
		return cantidadEntidades;
	}

	public void setCantidadEntidades(BigInteger cantidadEntidades) {
		this.cantidadEntidades = cantidadEntidades;
	}

	public String getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(String consolidado) {
		this.consolidado = consolidado;
	}

	public BigDecimal getMetaTerritorialAnual() {
		return metaTerritorialAnual;
	}

	public void setMetaTerritorialAnual(BigDecimal metaTerritorialAnual) {
		this.metaTerritorialAnual = metaTerritorialAnual;
	}

	public BigDecimal getMetaPrimerTrimestre() {
		return metaPrimerTrimestre;
	}

	public void setMetaPrimerTrimestre(BigDecimal metaPrimerTrimestre) {
		this.metaPrimerTrimestre = metaPrimerTrimestre;
	}

	public BigDecimal getAvance() {
		return avance;
	}

	public void setAvance(BigDecimal avance) {
		this.avance = avance;
	}

	public BigInteger getLimitIni() {
		return limitIni;
	}

	public void setLimitIni(BigInteger limitIni) {
		this.limitIni = limitIni;
	}

	public BigInteger getLimitFin() {
		return limitFin;
	}

	public void setLimitFin(BigInteger limitFin) {
		this.limitFin = limitFin;
	}

	public BigInteger getTotal() {
		return total;
	}

	public void setTotal(BigInteger total) {
		this.total = total;
	}

	public BigInteger getVacDefinitivaOtro() {
		return vacDefinitivaOtro;
	}

	public void setVacDefinitivaOtro(BigInteger vacDefinitivaOtro) {
		this.vacDefinitivaOtro = vacDefinitivaOtro;
	}

	public BigInteger getEncargoOtro() {
		return encargoOtro;
	}

	public void setEncargoOtro(BigInteger encargoOtro) {
		this.encargoOtro = encargoOtro;
	}

	public BigInteger getProvisionalidadOtro() {
		return provisionalidadOtro;
	}

	public void setProvisionalidadOtro(BigInteger provisionalidadOtro) {
		this.provisionalidadOtro = provisionalidadOtro;
	}

	public BigInteger getPorProveerOtro() {
		return porProveerOtro;
	}

	public void setPorProveerOtro(BigInteger porProveerOtro) {
		this.porProveerOtro = porProveerOtro;
	}

	public BigInteger getEmpleosTemporalesEmp() {
		return empleosTemporalesEmp;
	}

	public void setEmpleosTemporalesEmp(BigInteger empleosTemporalesEmp) {
		this.empleosTemporalesEmp = empleosTemporalesEmp;
	}

	public BigInteger getCuantosServidoresDiplomados() {
		return cuantosServidoresDiplomados;
	}

	public void setCuantosServidoresDiplomados(BigInteger cuantosServidoresDiplomados) {
		this.cuantosServidoresDiplomados = cuantosServidoresDiplomados;
	}

	public BigInteger getCuantosServidoresBenBecas() {
		return cuantosServidoresBenBecas;
	}

	public void setCuantosServidoresBenBecas(BigInteger cuantosServidoresBenBecas) {
		this.cuantosServidoresBenBecas = cuantosServidoresBenBecas;
	}

	@Override
	public String toString() {
		return "CifrasEmpleoPublico [codCifrasEmpleoPublico=" + codCifrasEmpleoPublico + ", codEntidad=" + codEntidad
				+ ", entidad=" + entidad + ", codSector=" + codSector + ", sector=" + sector + ", codOrden=" + codOrden
				+ ", orden=" + orden + ", codClasificacionOrganica=" + codClasificacionOrganica
				+ ", clasificacionOrganica=" + clasificacionOrganica + ", codNaturalezaJuridica="
				+ codNaturalezaJuridica + ", naturalezaJuridica=" + naturalezaJuridica + ", codSistemaCarrera="
				+ codSistemaCarrera + ", sistemaCarrera=" + sistemaCarrera + ", situacion=" + situacion
				+ ", codDepartamento=" + codDepartamento + ", departamento=" + departamento + ", codMunicipio="
				+ codMunicipio + ", municipio=" + municipio + ", codCategoria=" + codCategoria + ", categoria="
				+ categoria + ", codRegimenSalarial=" + codRegimenSalarial + ", regimenSalarial=" + regimenSalarial
				+ ", planAnualVacantes=" + planAnualVacantes + ", leyCuotas=" + leyCuotas + ", empleos=" + empleos
				+ ", naturaleza=" + naturaleza + ", seguimiento=" + seguimiento + ", acuerdosGestion=" + acuerdosGestion
				+ ", capacitacion=" + capacitacion + ", bienestarIncentivos=" + bienestarIncentivos
				+ ", horariosFlexibles=" + horariosFlexibles + ", teletrabajo=" + teletrabajo + ", bilinguismo="
				+ bilinguismo + ", debeReportarPAV=" + debeReportarPAV + ", reportoPAV=" + reportoPAV
				+ ", vacDefinitivaAsesor=" + vacDefinitivaAsesor + ", vacDefinitivaProfesional="
				+ vacDefinitivaProfesional + ", vacDefinitivaTecnico=" + vacDefinitivaTecnico
				+ ", vacDefinitivaAsistencial=" + vacDefinitivaAsistencial + ", vacDefinitivaTotal="
				+ vacDefinitivaTotal + ", encargoAsesor=" + encargoAsesor + ", encargoProfesional=" + encargoProfesional
				+ ", encargoTecnico=" + encargoTecnico + ", encargoAsistencial=" + encargoAsistencial
				+ ", encargoTotal=" + encargoTotal + ", provisionalidadAsesor=" + provisionalidadAsesor
				+ ", provisionalidadProfesional=" + provisionalidadProfesional + ", provisionalidadTecnico="
				+ provisionalidadTecnico + ", provisionalidadAsistencial=" + provisionalidadAsistencial
				+ ", provisionalidadTotal=" + provisionalidadTotal + ", porProveerAsesor=" + porProveerAsesor
				+ ", porProveerProfesional=" + porProveerProfesional + ", porProveerTecnico=" + porProveerTecnico
				+ ", porProveerAsistencial=" + porProveerAsistencial + ", porProveerTotal=" + porProveerTotal
				+ ", observacionPAV=" + observacionPAV + ", debeReportarLC=" + debeReportarLC + ", reportoLC="
				+ reportoLC + ", maximoNivelDecisorioTotal=" + maximoNivelDecisorioTotal
				+ ", maximoNivelDecisorioVacantes=" + maximoNivelDecisorioVacantes + ", maximoNivelDecisorioProvistos="
				+ maximoNivelDecisorioProvistos + ", maximoNivelDecisorioMujeres=" + maximoNivelDecisorioMujeres
				+ ", maximoNivelDecisorioHombres=" + maximoNivelDecisorioHombres + ", maximoNivelDecisorioPMujeres="
				+ maximoNivelDecisorioPMujeres + ", maximoNivelDecisorioPHombres=" + maximoNivelDecisorioPHombres
				+ ", otroNivelDecisorioTotal=" + otroNivelDecisorioTotal + ", otroNivelDecisorioVacantes="
				+ otroNivelDecisorioVacantes + ", otroNivelDecisorioProvistos=" + otroNivelDecisorioProvistos
				+ ", otroNivelDecisorioMujeres=" + otroNivelDecisorioMujeres + ", otroNivelDecisorioHombres="
				+ otroNivelDecisorioHombres + ", otroNivelDecisorioPMujeres=" + otroNivelDecisorioPMujeres
				+ ", otroNivelDecisorioPHombres=" + otroNivelDecisorioPHombres + ", observacionLC=" + observacionLC
				+ ", debeReportarEmp=" + debeReportarEmp + ", seguimientoSigepEmp=" + seguimientoSigepEmp
				+ ", totalPlantaEmp=" + totalPlantaEmp + ", plantaPermanenteEmp=" + plantaPermanenteEmp
				+ ", plantaTemporalEmp=" + plantaTemporalEmp + ", plantaTransitoriaEmp=" + plantaTransitoriaEmp
				+ ", trabajadoresOficialesEmp=" + trabajadoresOficialesEmp + ", plantaPrivadaEmp=" + plantaPrivadaEmp
				+ ", plantaDocentesEmp=" + plantaDocentesEmp + ", sistemaCarreraEmp=" + sistemaCarreraEmp
				+ ", decretoSalarialEmp=" + decretoSalarialEmp + ", escalaSalarialEmp=" + escalaSalarialEmp
				+ ", eleccionPopularEmp=" + eleccionPopularEmp + ", empleosLibreNomRemEmp=" + empleosLibreNomRemEmp
				+ ", empleosCarreraEmp=" + empleosCarreraEmp + ", empleosCarreraDipEmp=" + empleosCarreraDipEmp
				+ ", periodoFijoEmp=" + periodoFijoEmp + ", totalEmp=" + totalEmp + ", granTotalEmp=" + granTotalEmp
				+ ", observacionEmp=" + observacionEmp + ", totalPlantaNorma=" + totalPlantaNorma
				+ ", plataAprobadaReal=" + plataAprobadaReal + ", plantaPermanenteDistribuida="
				+ plantaPermanenteDistribuida + ", plantaTemporalDistribuida=" + plantaTemporalDistribuida
				+ ", plantaTransitoriaDistribuida=" + plantaTransitoriaDistribuida
				+ ", totalNumeroEmpleosPlantaDistribuida=" + totalNumeroEmpleosPlantaDistribuida
				+ ", numeroEmpleadosVinculados=" + numeroEmpleadosVinculados + ", vacantesReportaEntidad="
				+ vacantesReportaEntidad + ", totalHVActivasEmpleadosPublicos=" + totalHVActivasEmpleadosPublicos
				+ ", numeroDeclaracionesBienesRentasVigencia=" + numeroDeclaracionesBienesRentasVigencia
				+ ", totalActivasContratistas=" + totalActivasContratistas + ", numeroContratosVigentes="
				+ numeroContratosVigentes + ", numeroVinculadosSobreNumeroCargosPlanta="
				+ numeroVinculadosSobreNumeroCargosPlanta + ", pesoPorcentajeVinculacion=" + pesoPorcentajeVinculacion
				+ ", porcentajeVinculacion=" + porcentajeVinculacion
				+ ", numeroContratosVigentesSobreNumeroHVActivasContratistas="
				+ numeroContratosVigentesSobreNumeroHVActivasContratistas + ", pesoPorcentajeContrato="
				+ pesoPorcentajeContrato + ", porcentajeContratos=" + porcentajeContratos + ", ipvc=" + ipvc
				+ ", observacionesSEG=" + observacionesSEG + ", debeReportarAG=" + debeReportarAG + ", reportoAG="
				+ reportoAG + ", numeroGerentesPublicos=" + numeroGerentesPublicos + ", observacionAG=" + observacionAG
				+ ", debeReportarCap=" + debeReportarCap + ", adoptoCap=" + adoptoCap + ", actoAdministrativoCap="
				+ actoAdministrativoCap + ", entidadesPIC=" + entidadesPIC + ", cantidadPAE=" + cantidadPAE
				+ ", describaPAE=" + describaPAE + ", presupuestoAnualPIC=" + presupuestoAnualPIC
				+ ", cantidadServidoresDiplomadosDAFP=" + cantidadServidoresDiplomadosDAFP + ", diplomadosTomadosDAFP="
				+ diplomadosTomadosDAFP + ", ofertasCapacitacionEntidad=" + ofertasCapacitacionEntidad
				+ ", cuentaServidoresBenBecasDAFP=" + cuentaServidoresBenBecasDAFP + ", becasDAFPOtorgadas="
				+ becasDAFPOtorgadas + ", observacionCap=" + observacionCap + ", debeReportarBeI=" + debeReportarBeI
				+ ", adoptoBeI=" + adoptoBeI + ", manifestoBeI=" + manifestoBeI + ", cantidadServidoresBeI="
				+ cantidadServidoresBeI + ", observacionBeI=" + observacionBeI + ", adoptoHF=" + adoptoHF
				+ ", actoAdministrativoHF=" + actoAdministrativoHF + ", cantidadServidoresHF=" + cantidadServidoresHF
				+ ", observacionHF=" + observacionHF + ", adoptoTel=" + adoptoTel + ", actoAdministrativoTel="
				+ actoAdministrativoTel + ", cantidadServidoresTel=" + cantidadServidoresTel + ", observacionTel="
				+ observacionTel + ", cartaBil=" + cartaBil + ", inicioBil=" + inicioBil + ", cantidadPreInscBeguinner="
				+ cantidadPreInscBeguinner + ", cantidadPreInscA11=" + cantidadPreInscA11 + ", cantidadPreInscA12="
				+ cantidadPreInscA12 + ", cantidadPreInscA13=" + cantidadPreInscA13 + ", totalPreInscA1="
				+ totalPreInscA1 + ", cantidadPreInscA21=" + cantidadPreInscA21 + ", cantidadPreInscA22="
				+ cantidadPreInscA22 + ", cantidadPreInscA23=" + cantidadPreInscA23 + ", totalPreInscA2="
				+ totalPreInscA2 + ", cantidadPreInscB11=" + cantidadPreInscB11 + ", cantidadPreInscB12="
				+ cantidadPreInscB12 + ", cantidadPreInscB13=" + cantidadPreInscB13 + ", totalPreInscB1="
				+ totalPreInscB1 + ", cantidadIniBeguinner=" + cantidadIniBeguinner + ", cantidadIniA11="
				+ cantidadIniA11 + ", cantidadIniA12=" + cantidadIniA12 + ", cantidadIniA13=" + cantidadIniA13
				+ ", totalIniA1=" + totalIniA1 + ", cantidadIniA21=" + cantidadIniA21 + ", cantidadIniA22="
				+ cantidadIniA22 + ", cantidadIniA23=" + cantidadIniA23 + ", totalIniA2=" + totalIniA2
				+ ", cantidadIniB11=" + cantidadIniB11 + ", cantidadIniB12=" + cantidadIniB12 + ", cantidadIniB13="
				+ cantidadIniB13 + ", totalIniB1=" + totalIniB1 + ", cantidadTerBeguinner=" + cantidadTerBeguinner
				+ ", cantidadTerA11=" + cantidadTerA11 + ", cantidadTerA12=" + cantidadTerA12 + ", cantidadTerA13="
				+ cantidadTerA13 + ", totalTerA1=" + totalTerA1 + ", cantidadTerA21=" + cantidadTerA21
				+ ", cantidadTerA22=" + cantidadTerA22 + ", cantidadTerA23=" + cantidadTerA23 + ", totalTerA2="
				+ totalTerA2 + ", cantidadTerB11=" + cantidadTerB11 + ", cantidadTerB12=" + cantidadTerB12
				+ ", cantidadTerB13=" + cantidadTerB13 + ", totalTerB1=" + totalTerB1 + ", cantidadAproBeguinner="
				+ cantidadAproBeguinner + ", cantidadAproA11=" + cantidadAproA11 + ", cantidadAproA12="
				+ cantidadAproA12 + ", cantidadAproA13=" + cantidadAproA13 + ", totalAproA1=" + totalAproA1
				+ ", cantidadAproA21=" + cantidadAproA21 + ", cantidadAproA22=" + cantidadAproA22 + ", cantidadAproA23="
				+ cantidadAproA23 + ", totalAproA2=" + totalAproA2 + ", cantidadAproB11=" + cantidadAproB11
				+ ", cantidadAproB12=" + cantidadAproB12 + ", cantidadAproB13=" + cantidadAproB13 + ", totalAproB1="
				+ totalAproB1 + ", observacionesBil=" + observacionesBil + ", limitIni=" + limitIni + ", limitFin="
				+ limitFin + ", total=" + total + ", vacDefinitivaOtro=" + vacDefinitivaOtro + ", encargoOtro="
				+ encargoOtro + ", provisionalidadOtro=" + provisionalidadOtro + ", porProveerOtro=" + porProveerOtro
				+ ", empleosTemporalesEmp=" + empleosTemporalesEmp + ", sistemaNomenclaturaEmp="
				+ sistemaNomenclaturaEmp + ", decretoNomenclaturaEmp=" + decretoNomenclaturaEmp
				+ ", nomenclaturaEmpleosEmp=" + nomenclaturaEmpleosEmp + ", cuantosServidoresDiplomados="
				+ cuantosServidoresDiplomados + ", cuantosServidoresBenBecas=" + cuantosServidoresBenBecas + "]";
	}
	
	public BigDecimal getCodSuborden() {
		return codSuborden;
	}

	public void setCodSuborden(BigDecimal codSuborden) {
		this.codSuborden = codSuborden;
	}
	
	public String getSuborden() {
		return suborden;
	}

	public void setSuborden(String suborden) {
		this.suborden = suborden;
	}
	
	public String getFlgDependenciaEspecial() {
		return flgDependenciaEspecial;
	}

	public void setFlgDependenciaEspecial(String flgDependenciaEspecial) {
		this.flgDependenciaEspecial = flgDependenciaEspecial;
	}
	 
	public BigInteger getCategoriaMunicipio() {
		return categoriaMunicipio;
	}

	public void setCategoriaMunicipio(BigInteger categoriaMunicipio) {
		this.categoriaMunicipio = categoriaMunicipio;
	}

}
