package co.gov.dafp.sigep2.mbean.vinculacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.component.export.ExcelOptions;
import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.mbean.ext.DependenciaEntidadExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;

@Named
@ViewScoped
@ManagedBean

public class ExportarExcelVinculacionesBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean lbExportaVinculaciones;
	private boolean estadoPanelTabla;
	
	private ExcelOptions excelOpt;
	
	private List<VinculacionExt> lstVinculacionesExportar;
	private List<VinculacionExt> lstDesVinculacionesExportar;
	private VinculacionExt 		 filtroBusqueda;
	
	private String strNombreArchivo;
	private String strTargetExportar;
	private String area;
	private String cargo;
	
	private Long planta;
	private Long causal;
	private Long dependencia;
	private Long entidadProceso;
	private Long naturalezaEmpleo;
	private Integer tipoNombramiento;
	
	private Map<String, Long> tipsNombra;
	
	private Date fechaIni;
	private Date fechaFin;
	
	public Long getDependencia() {
		return dependencia;
	}

	public void setDependencia(Long dependencia) {
		this.dependencia = dependencia;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Long getPlanta() {
		return planta;
	}

	public void setPlanta(Long planta) {
		this.planta = planta;
	}

	public Integer getTipoNombramiento() {
		return tipoNombramiento;
	}

	public void setTipoNombramiento(Integer tipoNombramiento) {
		this.tipoNombramiento = tipoNombramiento;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public ExportarExcelVinculacionesBean() {
		super();
	}
	
	public Long getCausal() {
		return causal;
	}
	
	public void setCausal(Long causal) {
		this.causal = causal;
	}

	public Long getEntidadProceso() {
		return entidadProceso;
	}

	public void setEntidadProceso(Long entidadProceso) {
		this.entidadProceso = entidadProceso;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}

	@PostConstruct
	public void init() {
		this.naturalezaEmpleo = 0l;
		lbExportaVinculaciones = true;
		strNombreArchivo = "vinculaciones";
		strTargetExportar="dataTableVinculaciones";
		this.estadoPanelTabla = false;
		this.entidadProceso = this.getEntidadUsuario().getId();
		this.filtroBusqueda = new VinculacionExt();
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_VINCULACIONES);		
	}

	@Override
	public String persist() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// TODO Auto-generated method stub
	}

	@Override
	public String update() throws NotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
	}

	public boolean isLbExportaVinculaciones() {
		return lbExportaVinculaciones;
	}

	public void setLbExportaVinculaciones(boolean lbExportaVinculaciones) {
		this.lbExportaVinculaciones = lbExportaVinculaciones;
	}
	
	public String getStrNombreArchivo() {
		return strNombreArchivo;
	}

	public void setStrNombreArchivo(String strNombreArchivo) {
		this.strNombreArchivo = strNombreArchivo;
	}
	
	public List<VinculacionExt> getLstVinculacionesExportar() {
		return lstVinculacionesExportar;
	}

	public void setLstVinculacionesExportar(List<VinculacionExt> lstVinculacionesExportar) {
		this.lstVinculacionesExportar = lstVinculacionesExportar;
	}

	public List<VinculacionExt> getLstDesVinculacionesExportar() {
		return lstDesVinculacionesExportar;
	}

	public void setLstDesVinculacionesExportar(List<VinculacionExt> lstDesVinculacionesExportar) {
		this.lstDesVinculacionesExportar = lstDesVinculacionesExportar;
	}
	
	public String getStrTargetExportar() {
		return strTargetExportar;
	}

	public void setStrTargetExportar(String strTargetExportar) {
		this.strTargetExportar = strTargetExportar;
	}
	
	public void setEstadoPanelTabla(boolean estadoPanelTabla) {
		this.estadoPanelTabla = estadoPanelTabla;
	}
	
	public boolean isEstadoPanelTabla() {
		return this.estadoPanelTabla;
	}

	public void changerdbVinculaciones(){
		dependencia		= null;
		cargo			= null;
		planta			= null;
		fechaIni 		= null;
		fechaFin 		= null;
		naturalezaEmpleo = null;
		tipoNombramiento = null;
		
		if(this.lbExportaVinculaciones){
			strNombreArchivo = "vinculaciones";
			strTargetExportar= "dataTableVinculaciones";
		}
		else
		{	strNombreArchivo = "desvinculaciones";
			strTargetExportar= "dataTableDesvinculaciones";
		}
			
	}

	public void postProcessXLS(Object document) {
		
		excelOpt = new ExcelOptions();
	    excelOpt.setFacetBgColor("#F88017");
	    excelOpt.setFacetFontSize("10");
	    excelOpt.setFacetFontColor("#0000ff");
	    excelOpt.setFacetFontStyle("BOLD");
	    excelOpt.setCellFontColor("#00ff00");
	    excelOpt.setCellFontSize("8");
	        
		HSSFWorkbook wb = (HSSFWorkbook) document;
	    HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow header = sheet.getRow(0);
	         
	    HSSFCellStyle cellStyle = wb.createCellStyle();  
	    cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
	    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
            cell.setCellStyle(cellStyle);
        }
		consultar();
	}
	   
	public void consultar(){
		
		if((fechaIni != null) && (fechaIni.compareTo(new Date()) > 0)){
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES , MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_FECHA_INI_MAYOR_SYSDATE, getLocale()));
			return;
		}
		if(fechaFin != null && (fechaFin.compareTo(new Date()) > 0)) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES , MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_FECHA_FINI_MAYOR_SYSDATE, getLocale()));
			return;
		}
		if((fechaIni != null && fechaFin != null) && fechaIni.compareTo(fechaFin) > 0) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES , MessagesBundleConstants
					.getStringMessagesBundle(MessagesBundleConstants.MSG_WARN_RANGO_FECHAS, getLocale()));
			return;
		}
		filtroBusqueda.setFechafinPosesion(fechaFin);
		filtroBusqueda.setFechaIniPosesion(fechaIni);

		String mensaje = "";
		if(this.lbExportaVinculaciones){
			filtroBusqueda.setNombreCargo(this.cargo.trim().equals("") ? null : this.cargo);
			filtroBusqueda.setCodTipoPlanta(this.planta);
			if(this.dependencia == null)
				filtroBusqueda.setCodDependenciaEntidad(null);
			else
				filtroBusqueda.setCodDependenciaEntidad(new BigDecimal(this.dependencia));
			filtroBusqueda.setCodTipoNombramiento(this.tipoNombramiento);
			filtroBusqueda.setCodNaturalezaEmpleo(this.naturalezaEmpleo != null ? this.naturalezaEmpleo.intValue(): null);
			filtroBusqueda.setCodEntidad(this.getEntidadUsuario().getId());
			filtroBusqueda.setFlgActivo((short) 1);
	    	lstVinculacionesExportar = ComunicacionServiciosVin.getVinculacionExportacion(filtroBusqueda);
	    	if(lstVinculacionesExportar.isEmpty())
	    		mensaje =MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_BUSQUEDA, getLocale()) ;
	    		
	    }else{
	    	filtroBusqueda.setParCausalDesv(TipoParametro.CAUSAL_DESVINCULACION_AUTOMATICA.getValue());
			filtroBusqueda.setFechaFinalizacion(fechaFin);
			filtroBusqueda.setCodTipoPlanta(null);
			filtroBusqueda.setNombreCargo(null);
			filtroBusqueda.setCodTipoNombramiento(null);
			filtroBusqueda.setCodDependenciaEntidad(null);
			filtroBusqueda.setCodEntidad(this.getEntidadUsuario().getId());
			filtroBusqueda.setFlgActivo((short)0);
			if(filtroBusqueda.getFechaIniPosesion() != null && filtroBusqueda.getFechaFinalizacion() == null)
				filtroBusqueda.setFechaFinalizacion(new Date());
			if(this.causal == null)
				filtroBusqueda.setCodCausalDesvinculacion(null);
			else
				filtroBusqueda.setCodCausalDesvinculacion(new BigDecimal(this.causal));
	    	lstDesVinculacionesExportar = ComunicacionServiciosVin.getVinculacionExportacion(filtroBusqueda);
	    	if(lstDesVinculacionesExportar.isEmpty())
	    		mensaje =MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_MENSAJE_BUSQUEDA, getLocale()) ;
	    }
		this.estadoPanelTabla = true;
		this.mostrarMensaje(FacesMessage.SEVERITY_INFO ,  MessagesBundleConstants.DLG_HEADER_MENSAJES,  MessagesBundleConstants.MSG_PROCESO_FINALIZADO);
		RequestContext.getCurrentInstance().scrollTo("divScrollVinculacion");
		filtroBusqueda = new VinculacionExt();
	}
	
	public void regresar(){
		try {
			ExternalContext declarationSesion;
			declarationSesion = FacesContext.getCurrentInstance().getExternalContext();
			declarationSesion.redirect("../persona/informacionPersonal.xhtml");
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Error", "Se ha generado un error: " + ex.getMessage());
		}
	}
	
	public void cancelarExportar(){
		lstVinculacionesExportar = null;
		lstDesVinculacionesExportar = null;
		RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
	}
	
	/**
	 *Método vreado para facilitar la obtención de los nombres de los parametros
	 */
   	public String obtenerParametrica(Long id) {
   		if(id == null)
   			return "Dato no configurado";
   		Parametrica param = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(id));
   		if( param == null || param.getNombreParametro() == null)
   			return "Nombre sin configurar";
   		return param.getValorParametro();
   	}
   	
   	public String obtenerNombreDependencia(Long idDepen) {
   		try {
	   		if(idDepen == null)
	   			return "Dato no configurado";
	   		DependenciaEntidadExt depen = new DependenciaEntidadExt();
	   		depen.setCodDependenciaEntidad(idDepen);
	   		return ComunicacionServiciosVin.getDependenciaEntidadFilter(depen).get(0).getNombreDependencia();
   		}catch(Exception ex) {
   			return "Error recuperando dato";
   		}
   		
   	}
   	
   	public String obtenerNombreEntidad(Long id) {
   		try {
   			if(id == null)
   				return "Entidad no configurada";
   			return ComunicacionServiciosEnt.getEntidadPorId(id).getNombreEntidad();
   		}catch(Exception ex) {
   			logger.error("Error recuperando el nombre de la entidad, public String obtenerNombreEntidad(Long id) ExportarExcelVinculacionesBean", ex);
   			return "Error recuperando el nombre de la entidad";
   		}
   	}

	public Map<String, Long> getTipsNombra() {
		return tipsNombra;
	}

	public Long getNaturalezaEmpleo() {
		return naturalezaEmpleo;
	}

	public void setNaturalezaEmpleo(Long naturalezaEmpleo) {
		this.naturalezaEmpleo = naturalezaEmpleo;
	}

	public void setTipsNombra(Map<String, Long> tipsNombra) {
		this.tipsNombra = tipsNombra;
	}
	
	public void actualizarLista() {
		System.out.println("actualizarLista()");
	}

	public VinculacionExt getFiltroBusqueda() {
		return filtroBusqueda;
	}

	public void setFiltroBusqueda(VinculacionExt filtroBusqueda) {
		this.filtroBusqueda = filtroBusqueda;
	}

	
}
