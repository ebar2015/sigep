/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean.entidad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Autonomia;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadSistemaRegimen;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.mbean.ext.AutonomiaExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPoliticaPublicaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.Direccion;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 *
 * @author Sergio
 */
@Named(value = "consultaEntidadBean")
@ViewScoped
public class ConsultaEntidadBean extends CreacionEntidadBean {

    /**
	 * 
	 */
    @Inject
    protected GestionEntidadBean gestionEntidadBean;
    private HashMap<String, Object> mapaValoresReporte;
    private final String jasperPath = "/resources/jasper/entidades/consultaDatosEntidad.jrxml";
    private final String filePDF 	= "/resources/template/download/DetalleInformaciónEntidad.pdf";
    private final String fileXLSX 	= "/resources/template/download/DetalleInformaciónEntidad.xlsx";
    private final SimpleDateFormat simpleDateFormatFile = new SimpleDateFormat("ddMMyyyy");
    private List<EntidadPoliticaPublicaExt> politicasEntidad; //Variable que obtiene las politicas publicas que tiene asociada la Entidad
    private boolean noAplicaSistemaCarrera = false;
    private List<String> autonomia;
	
	@Override
    @PostConstruct
    public void init() {
        try {
        	cerrarSessionFuncion(AMBITO_POLITICAS);
        	boolean lbEsConsultarEntidad = true;
        	ExternalContext externalContext= FacesContext.getCurrentInstance().getExternalContext();
        	if(externalContext!=null && externalContext.getSessionMap()!=null && externalContext.getSessionMap().get("lbEsConsultarEntidad")!=null){
        		lbEsConsultarEntidad = (boolean) externalContext.getSessionMap().get("lbEsConsultarEntidad");
        	}        	
        	
        	if(!validarRolesConsultaEntidad() && lbEsConsultarEntidad){
        		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
    					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO,
    					TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()),
    					"window.location.assign('entidad/../gestionarEntidad.xhtml?faces-redirect=true')"); 
        		return;
        	}
            super.init();
            setEntidadN(gestionEntidadBean.getEntFiltro());
            if(this.entidadN.getCodNorma() != null) {
            	this.tipoNorma = ComunicacionServiciosEnt.getNormaPorId(this.entidadN.getCodNorma().longValue());
            }else {
            	this.tipoNorma = new Norma();
            }
            
            inicializacionConsulta();
            setLbEsConsultaEntidad(true);
            externalContext.getSessionMap().put("lbEsAmbitoPoliticasEntidad", false);            
            if(lbEsConsultarEntidad) {
        		generarDireccion();
        	}
        } catch (Exception ex) {
            Logger.getLogger(ModificacionEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
	 * @return the noAplicaSistemaCarrera
	 */
	public boolean isNoAplicaSistemaCarrera() {
		return noAplicaSistemaCarrera;
	}
	/**
	 * @param noAplicaSistemaCarrera the noAplicaSistemaCarrera to set
	 */
	public void setNoAplicaSistemaCarrera(boolean noAplicaSistemaCarrera) {
		this.noAplicaSistemaCarrera = noAplicaSistemaCarrera;
	}
	/**
     * Mediante este método se alimentan los datos del formulario de consulta
     * que no proviened directamente del objeto <code>Entidad</code>
     * seleccionado en el <code>GestionEntidadBean</code>
     *
     * @throws Exception
     */
    public void inicializacionConsulta() throws Exception {
        setDisponibleDatosBasicos(true);
        setDisponibleCaracterizacion(true);
        setDisponibleAmbitoAplicacion(true);
        this.consultarPoliticasEntidad();
        setAdscrito(getEntidadN().getCodEntidadAdscrita() != null);
        if (isAdscrito()) {
            setEntidadAdscritaSeleccionada(ComunicacionServiciosEnt.getEntidadPorId(getEntidadN().getCodEntidadAdscrita().longValue()));
        }
        somCategoriaEntidadListener();
        if (getEntidadN().getJustificacion() == null || getEntidadN().getJustificacion().isEmpty()) {
            setNorma(getNORMA_SI());
        } else {
            setNorma(getNORMA_NO());
        }
        
        
        Autonomia aut = new Autonomia();
		autonomia = new ArrayList<>();
		aut.setCodEntidad(this.entidadN.getCodEntidad());
		aut.setFlgActivo((short) 1);
		List<AutonomiaExt> lstExt = ComunicacionServiciosEnt.getAutonomia(aut);
		if(!lstExt.isEmpty()) {
			for (AutonomiaExt autonomiaExt : lstExt) {
				autonomia.add(String.valueOf(autonomiaExt.getCodTipoAutonomia()));
			}
		}
        List<EntidadSistemaRegimen> esrList = ComunicacionServiciosEnt.getEntidadesSistemaRegimenPorIdEntidad(getEntidadN().getCodEntidad());
        esrList = this.removerInactivos(esrList);
        if (esrList != null && !esrList.isEmpty()) {
            setCodigosEscalaSalarialSeleccionados(new ArrayList<>());
            setCodigosSistemaCarreraSeleccionados(new ArrayList<>());
            esrList.stream().map((esr) -> {
                if (esr.getCodEscalaSalarial() != null) {
       				getCodigosEscalaSalarialSeleccionados().add("" + esr.getCodEscalaSalarial().intValue());
                }
                return esr;
            }).filter((esr) -> (esr.getCodSistemaCarrera() != null)).forEach((esr) -> {
            	if(esr.getCodSistemaCarrera().intValue() == 1459) {
						noAplicaSistemaCarrera = true;
            	}else {
            		getCodigosSistemaCarreraSeleccionados().add("" + esr.getCodSistemaCarrera().intValue());
            	}
                
            });
        }
    }
    
    public void generarDireccion()  {
    	try {
    		 if (entidadN!= null && entidadN.getDireccion() != null && !entidadN.getDireccion().isEmpty() ) {
    	 			Direccion dir = new Direccion();
    	 			dir.llenarDatosDesdeJson(entidadN.getDireccion());
    	 			direccionBean.setDireccionSeleccionada(dir);
    	             direccionBean.mostrarDireccion();
    	             entidadN.setDireccion(direccionBean.getDireccionGenerada());
    	      }
		} catch (Exception e) {
				e.printStackTrace();
		}
    }
    
    public void llenarMapaValores(String tipoArchivo) {
    	Entidad entidad = ComunicacionServiciosEnt.getEntidadPorId(this.getEntidadN().getCodEntidad().longValue());
    	Pais pais = null;
    	String indicativoPais = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        mapaValoresReporte = new HashMap<>();
        mapaValoresReporte.put("FuncionPublicaImagen", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/logos/gob-colombia.png"));
        mapaValoresReporte.put("TodoNuevoPaisImagen", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images/logos/lema-gobierno.png"));
        if (this.getEntidadN().getCodTipoEntidad() != null) {
            mapaValoresReporte.put("TipoEntidad", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(this.getEntidadN().getCodTipoEntidad()))).getNombreParametro());
        } else {
            mapaValoresReporte.put("TipoEntidad", "");
        }
        if (this.getEntidadN().getCodigoSigep() != null) {
            mapaValoresReporte.put("CodSIGEP", this.getEntidadN().getCodigoSigep());
        } else {
            mapaValoresReporte.put("CodSIGEP", "");
        }
        if (this.getEntidadN().getNombreEntidad() != null) {
            mapaValoresReporte.put("NombreEntidad", this.getEntidadN().getNombreEntidad());
        } else {
            mapaValoresReporte.put("NombreEntidad", "");
        }
        if (this.getEntidadN().getNit() != null) {
            mapaValoresReporte.put("NIT", this.getEntidadN().getNit());
        } else {
            mapaValoresReporte.put("NIT", "");
        }
        if (this.getEntidadN().getCodDepartamento() != null) {
        	Departamento departamento = ComunicacionServiciosSis.getdeptoporid(this.getEntidadN().getCodDepartamento().longValue());
        	if(departamento != null) {
        		mapaValoresReporte.put("DepartamentoEntidad", departamento.getNombreDepartamento());
        	}else {
        		mapaValoresReporte.put("DepartamentoEntidad", "");
        	}
        } else {
            mapaValoresReporte.put("DepartamentoEntidad", "");
        }
        if (this.getEntidadN().getCodMunicipio() != null) {
        	Municipio municipio = ComunicacionServiciosSis.getMunicipiosid(this.getEntidadN().getCodMunicipio().longValue());
            if(municipio != null) {
        		mapaValoresReporte.put("MunicipioEntidad", municipio.getNombreMunicipio());
            }else {
            	mapaValoresReporte.put("MunicipioEntidad", "");
            }
        } else {
            mapaValoresReporte.put("MunicipioEntidad", "");
        }
        if (this.getEntidadN().getSigla() != null) {
            mapaValoresReporte.put("Sigla", this.getEntidadN().getSigla());
        } else {
            mapaValoresReporte.put("Sigla", "");
        }
        if (this.getTipoNorma().getCodTipoNorma() != null) {
            mapaValoresReporte.put("TipoNorma", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(this.getTipoNorma().getCodTipoNorma()))).getNombreParametro());
        } else {
            mapaValoresReporte.put("TipoNorma", "");
        }
        if (this.getTipoNorma().getNumeroNorma() != null) {
            mapaValoresReporte.put("NumeroNorma", this.getTipoNorma().getNumeroNorma());
        } else {
            mapaValoresReporte.put("NumeroNorma", "");
        }
        if (this.getTipoNorma().getFechaNorma() != null) {
            mapaValoresReporte.put("FechaNorma", format.format(this.getTipoNorma().getFechaNorma()));
        } else {
            mapaValoresReporte.put("FechaNorma", "");
        }
        if (this.getTipoNorma().getObjetoNorma() != null) {
            mapaValoresReporte.put("ObjetoNorma", this.getTipoNorma().getObjetoNorma());
        } else {
            mapaValoresReporte.put("ObjetoNorma", "");
        }
        if (this.getEntidadN().getCodOrden() != null) {
            mapaValoresReporte.put("CodOrden", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(this.getEntidadN().getCodOrden())).getNombreParametro());
        } else {
            mapaValoresReporte.put("CodOrden", "");
        }
        if (this.getEntidadN().getCodSectorAdministrativo() != null) {
            mapaValoresReporte.put("CodSectorAdmin", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(this.getEntidadN().getCodSectorAdministrativo())).getNombreParametro());
        } else {
            mapaValoresReporte.put("CodSectorAdmin", "");
        }
        if (this.getEntidadN().getCodSuborden() != null) {
            mapaValoresReporte.put("CodSubOrden", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(this.getEntidadN().getCodSuborden())).getNombreParametro());
        } else {
            mapaValoresReporte.put("CodSubOrden", "");
        }
    	 mapaValoresReporte.put("EsAdscrito", this.isAdscrito()?"SI":"NO");
         if (this.isAdscrito()
                 && this.getEntidadAdscritaSeleccionada() != null
                 && this.getEntidadAdscritaSeleccionada().getNombreEntidad() != null) {
             mapaValoresReporte.put("NombreEntidadAdscrita", this.getEntidadAdscritaSeleccionada().getNombreEntidad());
         } else {
             mapaValoresReporte.put("NombreEntidadAdscrita", "");
         }
       
        if (this.getEntidadN().getCodCategoria() != null) {
            mapaValoresReporte.put("CodCategoria", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(this.getEntidadN().getCodCategoria())).getNombreParametro());
        } else {
            mapaValoresReporte.put("CodCategoria", "");
        }
        if (this.getCodigoDNP() != null) {
            mapaValoresReporte.put("CodDNP", ((Parametrica) ComunicacionServiciosSis.getParametricaporId(this.getCodigoDNP())).getNombreParametro());
        } else {
            mapaValoresReporte.put("CodDNP", "");
        }
        if(this.getEntidadN().getEntidadPostconfilcto()!= null) {
    	   mapaValoresReporte.put("EsEntidadPostConflicto", (this.getEntidadN().getEntidadPostconfilcto().equals(1))?"SI":"NO");
        }else {
    	   mapaValoresReporte.put("EsEntidadPostConflicto", "NO");
        }
        if(this.getEntidadN().getPersoneriaJuridica() != null) {
        	mapaValoresReporte.put("CodPersoneriaJuridica", getSINO(this.getEntidadN().getPersoneriaJuridica()));
        }
        if (this.getCodigosSistemaCarreraSeleccionados() != null) {
        	String sistemaCarrera = getNombresParametros(this.getCodigosSistemaCarreraSeleccionados());
        	mapaValoresReporte.put("CodSistemaCarrera", sistemaCarrera);
        } else {
            mapaValoresReporte.put("CodSistemaCarrera", "");
        }
        if (this.getCodigosEscalaSalarialSeleccionados() != null) {
        	String escalaSalarial = getNombresParametros(this.getCodigosEscalaSalarialSeleccionados());
            mapaValoresReporte.put("CodEscalaSalarial", escalaSalarial);
        } else {
            mapaValoresReporte.put("CodEscalaSalarial", "");
        }
        if (this.getCodigosNaturalezaEmpleoSeleccionados() != null) {
        	String naturalezaEmpleo = getNombresParametros(this.getCodigosNaturalezaEmpleoSeleccionados());
            mapaValoresReporte.put("CodNaturalezaEmpleo", naturalezaEmpleo);
        } else {
            mapaValoresReporte.put("CodNaturalezaEmpleo", "");
        }
        if (this.getTextoParametrica(this.getEntidadN().getCodEstadoEntidad()) != null) {
            mapaValoresReporte.put("CodEstadoEntidad", this.getTextoParametrica(this.getEntidadN().getCodEstadoEntidad()));
        } else {
            mapaValoresReporte.put("CodEstadoEntidad", "");
        }
        if (this.getTextoParametrica(this.getEntidadN().getCodSubestadoEntidad() != null ? BigDecimal.valueOf(this.getEntidadN().getCodSubestadoEntidad()): null) != null) {
            mapaValoresReporte.put("CodSubEstadoEntidad", this.getTextoParametrica(BigDecimal.valueOf(this.getEntidadN().getCodSubestadoEntidad())));
        } else {
            mapaValoresReporte.put("CodSubEstadoEntidad", "");
        }
        if (this.getEntidadN().getDigitoVerificacion() != null) {
            mapaValoresReporte.put("DigitoVerificacion", this.getEntidadN().getDigitoVerificacion().toString());
        } else {
            mapaValoresReporte.put("DigitoVerificacion", "");
        }
        if (this.getEntidadN().getCodigoDane() != null) {
            mapaValoresReporte.put("CodDANE", this.getEntidadN().getCodigoDane());
        } else {
            mapaValoresReporte.put("CodDANE", "");
        }
        if (this.getEntidadN().getCodigoCuin() != null) {
            mapaValoresReporte.put("CodCUIN", this.getEntidadN().getCodigoCuin());
        } else {
            mapaValoresReporte.put("CodCUIN", "");
        }
        
        if(this.getEntidadN().getEmailEntidad() != null && !this.getEntidadN().getEmailEntidad().isEmpty()) {
        	mapaValoresReporte.put("CorreoInstitucional", this.getEntidadN().getEmailEntidad());
        } else {
            mapaValoresReporte.put("CorreoInstitucional", "");
        }
        
        if(this.getEntidadN().getPaginaWeb() != null && !this.getEntidadN().getPaginaWeb().isEmpty()) {
        	mapaValoresReporte.put("PaginaWeb", this.getEntidadN().getPaginaWeb());
        } else {
            mapaValoresReporte.put("PaginaWeb", "");
        }
        
        if(this.getEntidadN().getTipoZona()!=null && this.getEntidadN().getTipoZona() == TipoParametro.TIPO_ZONA_URBANA.getValue()) {
        	mapaValoresReporte.put("TipoZona", "Zona Urbana");
        } else if(this.getEntidadN().getTipoZona()!=null && this.getEntidadN().getTipoZona() == TipoParametro.TIPO_ZONA_RURAL.getValue()) {
            mapaValoresReporte.put("TipoZona", "Zona Rural");
        }else{
        	mapaValoresReporte.put("TipoZona", "Zona No Especificada");
        }
        
        if (entidad.getDireccion() != null && !entidad.getDireccion().isEmpty()) {
            Direccion dir = new Direccion();
            try {
				dir.llenarDatosDesdeJson(entidad.getDireccion());
				direccionBean.setDireccionSeleccionada(dir);
				direccionBean.mostrarDireccion();
				mapaValoresReporte.put("DireccionFisica", direccionBean.getDireccionGenerada());
			} catch (Exception e) {
				e.printStackTrace();
			}
        }else {
        	mapaValoresReporte.put("DireccionFisica", "");
        }
        
        if(this.getEntidadN().getCodigoPostal() != null && !this.getEntidadN().getCodigoPostal().isEmpty()) {
        	mapaValoresReporte.put("CodigoPostal", this.getEntidadN().getCodigoPostal());
        } else {
            mapaValoresReporte.put("CodigoPostal", "");
        }
        
        if(this.getEntidadN().getCodPais() != null) {
        	pais = ComunicacionServiciosSis.getpisporid(this.getEntidadN().getCodPais().longValue());
        	if(pais != null && pais.getIndicativoPais() != null) {
        		indicativoPais = pais.getIndicativoPais().toString();
        	}
        }
        
        if(this.getEntidadN().getTelefonoEntidad() != null) {
        	String telefono = "";
        	String indicativoDepto = obtenerIndicativoDepartamento(this.getEntidadN().getCodDepartamento());
        	if(indicativoPais.equals("57")) {
        		telefono = "+" + indicativoPais + indicativoDepto + " " + this.getEntidadN().getTelefonoEntidad();
        	}else {
        		telefono = "+" + indicativoPais + " " + this.getEntidadN().getTelefonoEntidad();
        	}
        	
        	mapaValoresReporte.put("Telefono", telefono);
        } else {
            mapaValoresReporte.put("Telefono", "");
        }
        
        if(this.getEntidadN().getCelular() != null) {
        	String celular = "+" + indicativoPais + " " + this.getEntidadN().getCelular();
        	
        	mapaValoresReporte.put("Celular", celular);
        } else {
            mapaValoresReporte.put("Celular", "");
        }
        
        if(this.getEntidadN().getFax() != null) {
        	String fax = "";
        	String indicativoDepto = obtenerIndicativoDepartamento(this.getEntidadN().getCodDepartamento());
        	if(indicativoPais.equals("57")) {
        		fax = "+" + indicativoPais + indicativoDepto + " " + this.getEntidadN().getFax();
        	}else {
        		fax = "+" + indicativoPais + " " + this.getEntidadN().getFax();
        	}
        	
        	
        	mapaValoresReporte.put("Fax", fax);
        } else {
            mapaValoresReporte.put("Fax", "");
        }
        
        if(this.getEntidadN().getCodRepresentanteLegal() != null && !this.getEntidadN().getCodRepresentanteLegal().isEmpty()) {
        	mapaValoresReporte.put("RepresentanteLegal", this.getEntidadN().getCodRepresentanteLegal());
        } else {
            mapaValoresReporte.put("RepresentanteLegal", "");
        }
        
        consultarPoliticasEntidad();

        try {
            String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
            JasperReport jasperReport = JasperCompileManager.compileReport(relativePath);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(politicasEntidad);
            mapaValoresReporte.put("ItemDataSource", dataSource);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, mapaValoresReporte, new JREmptyDataSource());
            
            if (tipoArchivo.equals("PDF")) {
                String relativePDF = FacesContext.getCurrentInstance().getExternalContext().getRealPath(filePDF);
                try (OutputStream outputStream = new FileOutputStream(relativePDF)) {
                    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                    outputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConsultaEntidadBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (tipoArchivo.endsWith("XLSX")) {
                String relativeXLSX = FacesContext.getCurrentInstance().getExternalContext().getRealPath(fileXLSX);
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                File file = new File(relativeXLSX);
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setDetectCellType(true);
                configuration.setCollapseRowSpan(false);
                xlsxExporter.setConfiguration(configuration);
                xlsxExporter.exportReport();
            }
        } catch (JRException ex) {
            Logger.getLogger(ConsultaEntidadBean.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage(), ex);
        }
    }
    
    /**
     * Transforma el valor del codPersoneriaJuridica
     * @param personeriaJuridica
     * @return
     */
    public String getSINO(Short valor) {
	    if(valor != null) {	
    		if(valor == 0) {
	    		return "No";
	    	}else {
	    		return "Si";
	    	}
	    }else {
	    	return "No";
	    }
	}
	/**
     * Transforma un listado de códigos de paramétrica en su valor correspondiente. 
     * @param arregloValores
     * @return
     */
    public String getNombresParametros(List<String> codigoParametros) {
    	String resultado = "";
    	List<String> codigoParametrosSinDuplicados = null;
    	if(codigoParametros != null) {	
    		codigoParametrosSinDuplicados = codigoParametros.stream().distinct().collect(Collectors.toList());
    		for(String valor : codigoParametrosSinDuplicados) {
    			Parametrica p = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(valor));
    			resultado = resultado + p.getNombreParametro() + ", ";
    		}
    		
    		if(!"".equals(resultado)) {
        		resultado = resultado.substring(0, resultado.length() - 2);
        	}
	    }
    	return resultado;
	}
	public DefaultStreamedContent getDownloadPDF() throws Exception {
        String relativePDF = FacesContext.getCurrentInstance().getExternalContext().getRealPath(filePDF);
        File file = new File(relativePDF);
        InputStream inputStream = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return new DefaultStreamedContent(inputStream, externalContext.getMimeType(file.getName()), "DetalleInformaciónEntidad" + this.getEntidadN().getNombreEntidad() + "-" + simpleDateFormatFile.format(Calendar.getInstance().getTime()) + ".pdf");
    }

    public DefaultStreamedContent getDownloadXLSX() throws Exception {
        String relativeXLSX = FacesContext.getCurrentInstance().getExternalContext().getRealPath(fileXLSX);
        File file = new File(relativeXLSX);
        InputStream inputStream = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return new DefaultStreamedContent(inputStream, externalContext.getMimeType(file.getName()), "DetalleInformaciónEntidad" + this.getEntidadN().getNombreEntidad() + "-" + simpleDateFormatFile.format(Calendar.getInstance().getTime()) + ".xlsx");
    }
    
    /**
     * Metodo que permite obtener las politicas con las que cuenta actualmente la entidad consultada
     */
    public void consultarPoliticasEntidad() {
    	if(getEntidadN() !=null) {
    		EntidadPoliticaPublicaExt objFilter = new EntidadPoliticaPublicaExt();
    		objFilter.setCodEntidad(entidadN.getCodEntidad().longValue());
    		politicasEntidad = ComunicacionServiciosEnt.getPoliticasPublicasEntidad(objFilter);
    	}
    }
    
    
    
    
    /**
     * Sobreescribe el método para el botón regresar
     *
     * @return
     */
    @Override
    public String btRegresarAction() {
        gestionEntidadBean.endConversation();
        return "/entidad/gestionarEntidad?faces-redirect=true";
    }
	/**
	 * @return the politicasEntidad
	 */
	public List<EntidadPoliticaPublicaExt> getPoliticasEntidad() {
		return politicasEntidad;
	}
	/**
	 * @param politicasEntidad the politicasEntidad to set
	 */
	public void setPoliticasEntidad(List<EntidadPoliticaPublicaExt> politicasEntidad) {
		this.politicasEntidad = politicasEntidad;
	}    

	public String getPersoneriaJuridica() {
		if(this.entidadN!= null) {
			if(this.entidadN.getPersoneriaJuridica()!=null) {
				if(this.entidadN.getPersoneriaJuridica().equals(1)) {
					return "Si";
				}else {
					return "No";
				}
			}
		}
		return "No";
		
	}
	
	public String obtenerAdscrita() {
		Parametrica p = new Parametrica();
		if(entidadN != null && entidadN.getCodAdscritaVinculada() != null) {
			p = ComunicacionServiciosSis.getParametricaporId(entidadN.getCodAdscritaVinculada());
			return p.getValorParametro();
		}
		return "Sin dato";
	}
	
	/**
	 * @return the autonomia
	 */
	public List<String> getAutonomia() {
		return autonomia;
	}
	/**
	 * @param autonomia the autonomia to set
	 */
	public void setAutonomia(List<String> autonomia) {
		this.autonomia = autonomia;
	}
}
