package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.NacionalidadPerfil;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.DatosPersonalesBean;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaDeVidaPrintExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
@ManagedBean
public class ImprimirDescargarHojaVidaBean extends BaseBean implements Serializable {
    
    private static 	final long serialVersionUID = -6946980844817332237L;
    static final long 			MESES_ANIO 		= 12; // variable para obtener los MESES DEL ANIO
   
    
    private HojaDeVidaPrintExt hojaDeVidaToPrint 			= new HojaDeVidaPrintExt();
    private List<HojaDeVidaPrintExt> listaHojasVidaToPrint 	= new ArrayList<>();
    private List<String> urlsDocumentos 					= new ArrayList<>();
    private static final Logger logger 						= Logger.getInstance(ImprimirDescargarHojaVidaBean.class);
    private Boolean optionView 								= true;
    private Boolean incluirAnexos;
    private String jasperPath;
    private String urlImprimir;
    private String urlDescargar;
    private List<DocumentosAdicionalesHvExt> docsAdi;
    List<ExperienciaProfesionalExt> lstExperiencias;
    
    private Long codPersona = null;
        
    @PostConstruct
    public void init() {
        this.initialization();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String rutaImagen = externalContext.getRealPath("/resources/images/escudo-colombia.png");
        ExperienciaProfesionalExt exp = new ExperienciaProfesionalExt();
        exp.setCodPersona(new BigDecimal(codPersona));
        exp.setFlgActivo(true);
        this.docsAdi = new ArrayList<>();
        DocumentosAdicionalesHvExt buscador = new DocumentosAdicionalesHvExt();
        buscador.setCodPersona(new BigDecimal(codPersona));
        buscador.setFlgActivo((short) 1);
        
        
		LogroRecurso buscadorlogroRecurso = new LogroRecurso();
		buscadorlogroRecurso.setCodPersona( BigDecimal.valueOf(codPersona));
		buscadorlogroRecurso.setFlgActivo((short) 1);
		
		PublicacionExt buscadorPublicaciones = new PublicacionExt();
		buscadorPublicaciones.setCodPersona( BigDecimal.valueOf(codPersona));
		buscadorPublicaciones.setFlgActivo((short) 1);	
		
		EvaluacionDesempenoExt buscadorEvsDesempenno = new EvaluacionDesempenoExt();
		buscadorEvsDesempenno.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorEvsDesempenno.setFlgActivo((short) 1);
		
		ReconocimientoExt buscadorPremios = new ReconocimientoExt();
		buscadorPremios.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorPremios.setFlgActivo((short) 1);
		
		ParticipacionProyectoExt buscadorParticipacionproyectos = new ParticipacionProyectoExt();
		buscadorParticipacionproyectos.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorParticipacionproyectos.setFlgActivo((short) 1);
		
		ParticipacionInstitucion buscadorCorporaciones = new ParticipacionInstitucion();
		buscadorCorporaciones.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorCorporaciones.setFlgActivo((short) 1);
		
		
		NacionalidadPerfil buscadorNacionalidad = new  NacionalidadPerfil();
		buscadorNacionalidad.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorNacionalidad.setFlgActivo((short) 1);
        
        this.docsAdi = ComunicacionServiciosHV.getDocumentosAdicionalesHVFiltro(buscador);
        hojaDeVidaToPrint.setDetallePersona(ComunicacionServiciosHV.getpersonacontacto(codPersona));
        cargarDireccionDatoContacto();
        
        hojaDeVidaToPrint.setDetalleEducacion(ComunicacionServiciosHV.getEducacionFormal001(codPersona, true));
        for(EducacionFormalExt ed:hojaDeVidaToPrint.getDetalleEducacion()){
        	if(ed.getCodNivelFormacion()!=null && (
        			   ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_PRIMERO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEGUNDO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_TERCERO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_CUARTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_QUINTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEXTO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_SEPTIMO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_OCTAVO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_NOVENO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_DECIMO.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_ONCE.getValue())
        			|| ed.getCodNivelFormacion().equals(TipoParametro.NIVEL_FORMACION_BASICA_UNDECIMO.getValue())
        			)
        			){
        		hojaDeVidaToPrint.getDetalleEducacionPrimaria().add(ed);
        	}else{
        		hojaDeVidaToPrint.getDetalleEducacionProfesional().add(ed);
        	}
        }
        
        hojaDeVidaToPrint.setDetalleLaboral(ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true));
        hojaDeVidaToPrint.setDetalleExpDocente(ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true));
        hojaDeVidaToPrint.setDetalleIdioma(ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true));
        hojaDeVidaToPrint.setLogroRecurso(ComunicacionServiciosHV.getLogrosRecursosFiltro(buscadorlogroRecurso));
        hojaDeVidaToPrint.setPublicacion(ComunicacionServiciosHV.getPublicacionesFiltro(buscadorPublicaciones));
        hojaDeVidaToPrint.setReconocimiento(ComunicacionServiciosHV.getPremiosReconocimientosHV(buscadorPremios));
        hojaDeVidaToPrint.setParticipacionProyecto(ComunicacionServiciosHV.getParticipacionProyectosHV(buscadorParticipacionproyectos));
        hojaDeVidaToPrint.setParticipacionInstitucion(ComunicacionServiciosHV.getParticipacionInstitucionHV(buscadorCorporaciones));
        hojaDeVidaToPrint.setEvaluacionDesempeno(ComunicacionServiciosHV.getEvDesempenno(buscadorEvsDesempenno));
        hojaDeVidaToPrint.setDatoAdicional(ComunicacionServiciosHV.getDatoContactoAdi(codPersona));
        hojaDeVidaToPrint.setOtroConocimientoExt(ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true));
        hojaDeVidaToPrint.setNacionalidadPersona(ComunicacionServiciosHV.getNacionalidadPersona(buscadorNacionalidad));
        
        hojaDeVidaToPrint.setRutaImagen(rutaImagen);
        
        this.calcularTiempoLaboral();
        listaHojasVidaToPrint.add(hojaDeVidaToPrint);
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaTexto = formato.format(new Date());
        hojaDeVidaToPrint.setFechaActual("" + fechaTexto);
        jasperPath = "/resources/jasper/hojaDeVidaFormatoUnico.jasper";
        
        this.validarAnexosExistentes();
        urlImprimir 	= "imprimirDescargarHojaVida.xhtml?id=" + codPersona + "&option=OPT_PRINT";
        urlDescargar 	= "imprimirDescargarHojaVida.xhtml?id=" + codPersona + "&option=OPT_DOWNLOAD";
    }
    
    public void initialization() {        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        String option = request.getParameter("option");
        
        if (option != null && option.equals("OPT_DOWNLOAD")) {
            optionView = false;
        }
        
        if (idPersona != null) {
            codPersona = Long.valueOf(idPersona);
            
            if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
                try {
                    Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA, RolDTO.SERVIDOR_PUBLICO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR,
                            RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS,
                            RolDTO.OPERADOR_TALENTO_HUMANO);
                    if (!flgValidRolPermission) {
                        this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
                    }
                } catch (SIGEP2SistemaException e) {
                    logger.error("void init() SIGEP2SistemaException", e);
                } catch (IOException e) {
                    logger.error("void init() IOException", e);
                }
            }
        } else {
            if (getUsuarioSesion() != null) {
                codPersona = getUsuarioSesion().getCodPersona();
                try {
                    Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA, RolDTO.SERVIDOR_PUBLICO);
                    if (!flgValidRolPermission) 
                        this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
                } catch (SIGEP2SistemaException e) {
                    logger.error("void init() usuarioTieneRolAsignado", e);
                } catch (IOException e) {
                    logger.error("void init() finalizarConversacion", e);
                }
            } else {
                try {
                    this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
                } catch (IOException e) {
                    logger.error("void init() finalizarConversacion", e);
                }
            }
        }
    }
    
	public void cargarDireccionDatoContacto() {
		if (this.hojaDeVidaToPrint.getDetallePersona().getDireccionResidencia() != null && !this.hojaDeVidaToPrint.getDetallePersona().getDireccionResidencia().isEmpty()) {
			Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
			try {
				EditarDireccionDTO direccion = gson.fromJson(this.hojaDeVidaToPrint.getDetallePersona().getDireccionResidencia(), EditarDireccionDTO.class);
				if (direccion != null) {
					hojaDeVidaToPrint.getDetallePersona().setDireccionResidencia(direccion.getDireccionGenerada());
				}
			} catch (JsonSyntaxException e) {

			}
		}
	}
    public void validarAnexosExistentes() {
        if (hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl() != null && !hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl().equals("")) 
            this.setUrlsDocumentos(WebUtils.getUrlFile(hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl()));      
        if (hojaDeVidaToPrint.getDetallePersona().getLibretaMilitarUrl() != null && !hojaDeVidaToPrint.getDetallePersona().getLibretaMilitarUrl().equals(""))
                this.setUrlsDocumentos(WebUtils.getUrlFile(hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl()));
        if (hojaDeVidaToPrint.getDetallePersona().getDocumentoDiscapacidadUrl() != null && !hojaDeVidaToPrint.getDetallePersona().getDocumentoDiscapacidadUrl().equals(""))
            this.setUrlsDocumentos(WebUtils.getUrlFile(hojaDeVidaToPrint.getDetallePersona().getDocumentoDiscapacidadUrl()));
                
        for (EducacionFormalExt educacionFormal : hojaDeVidaToPrint.getDetalleEducacion()) {
            if (educacionFormal.getUrlAnexo() != null && !educacionFormal.getUrlAnexo().equals("")) 
            	this.setUrlsDocumentos(WebUtils.getUrlFile(educacionFormal.getUrlAnexo()));
            		
        }
        for (OtroConocimientoExt otroConocimiento : hojaDeVidaToPrint.getOtroConocimientoExt()) {
            if (otroConocimiento.getUrlDocumentoSoporte() != null && !otroConocimiento.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.getUrlFile(otroConocimiento.getUrlDocumentoSoporte()));
        }
        for (IdiomaPersonaExt idiomaPersona : hojaDeVidaToPrint.getDetalleIdioma()) {
            if(idiomaPersona.getUrlCertificacion() != null && !idiomaPersona.getUrlCertificacion().equals("")) 
            	this.setUrlsDocumentos(WebUtils.getUrlFile(idiomaPersona.getUrlCertificacion()));
        }
        for (ExperienciaProfesionalExt experienciaProfesional : hojaDeVidaToPrint.getDetalleLaboral()) {
            if (experienciaProfesional.getUrlDocumentoSoporte() != null && !experienciaProfesional.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.getUrlFile(experienciaProfesional.getUrlDocumentoSoporte()));
        }
        for (ExperienciaDocenteExt experienciaDocente : hojaDeVidaToPrint.getDetalleExpDocente()) {
            if (experienciaDocente.getUrlDocumentoSoporte() != null && !experienciaDocente.getUrlDocumentoSoporte().equals(""))
            	this.setUrlsDocumentos(WebUtils.getUrlFile(experienciaDocente.getUrlDocumentoSoporte()));
        }
        for (DocumentosAdicionalesHvExt doc : this.docsAdi) {
            if (doc.getUrlDocumentoAdicional() != null && !doc.getUrlDocumentoAdicional().isEmpty())
            	this.setUrlsDocumentos( WebUtils.getUrlFile(doc.getUrlDocumentoAdicional()));
        }
    }
    
    public void imprimirHojaDeVida() {
        try {
        	String jasperPath 	= this.jasperPath;
            String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
            File filePDF 		= new File(relativePath);
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaHojasVidaToPrint, false);
            JasperPrint jPrint 	= JasperFillManager.fillReport(filePDF.getPath(), null, source);
            
            if (incluirAnexos != null && incluirAnexos && !this.getUrlsDocumentos().isEmpty()) {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.setContentType("application/pdf");
                Date fechaActual 	= new Date();
                String filename 	= fechaActual.getTime() + ".pdf";
                String ruta 		= ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_FILE_TEMP) + filename;
                String filePath 	= ConfigurationBundleConstants.getRutaArchivo(ruta);
                OutputStream stream = new FileOutputStream(new File(filePath));
                JasperExportManager.exportReportToPdfStream(jPrint, stream);
                stream.flush();
                stream.close();
                this.uploadMultix(filePath, false);
            } else {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "inline; filename=\"fileName.pdf\"");                
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jPrint, stream);
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (JRException e) {
            logger.error("void imprimirHojaDeVida() JRException", e);
        } catch (IOException e) {
            logger.error("void imprimirHojaDeVida() IOException", e);
        }
    }
    
    public void descargarHojaDeVida() {
        try {
        	String jasperPath = this.jasperPath;
            String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
            File filePDF = new File(relativePath);
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaHojasVidaToPrint, false);
            JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
            if (incluirAnexos != null && incluirAnexos && !this.getUrlsDocumentos().isEmpty()) {
                Date fechaActual = new Date();
                String filename = fechaActual.getTime() + ".pdf";
                String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_FILE_TEMP) + filename;
                String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
                OutputStream stream = new FileOutputStream(new File(filePath));
                JasperExportManager.exportReportToPdfStream(jPrint, stream);
                stream.flush();
                stream.close();
                this.uploadMultix(filePath, true);
            } else {
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=" + hojaDeVidaToPrint.getDetallePersona().getPrimerNombre()
                        + hojaDeVidaToPrint.getDetallePersona().getPrimerApellido() + DateUtils.formatearACadena(new Date(), "ddMMyyyy") + ".pdf");
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jPrint, stream);
                stream.flush();
                stream.close();
                FacesContext.getCurrentInstance().renderResponse();
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (JRException e) {
            logger.error("void descargarHojaDeVida() JRException", e);
        } catch (IOException e) {
            logger.error("void descargarHojaDeVida() IOException", e);
        }
    }
    
    public void calcularTiempoLaboral() {
    	cargarExperienciaProfesionalyDocente();
    	long aniopublico 		= 0; // variable para el valor año
    	long mespublico 		= 0;// variable para el valor mes
    	long anioprivado 		= 0; // variable para el valor año
    	long mesprivado 		= 0;// variable para el valor mes
    	long anioIndependiente 	= 0; // variable para el valor año
    	long mesIndependiente 	= 0;// variable para el valor mes
    	long aniototal 			= 0; // variable para el valor año
    	long mestotal 			= 0;// variable para el valor mes
    	Long[] tiempos = DatosPersonalesBean.calculartiempoExperienciaprofesional(lstExperiencias);
    	aniopublico 		= tiempos[9];	
    	mespublico  		= tiempos[10];	
    	anioprivado 		= tiempos[12];	
    	mesprivado  		= tiempos[13];	
    	anioIndependiente 	= tiempos[15];	
    	mesIndependiente  	= tiempos[16];
    	aniototal  			= tiempos[18];	
    	mestotal   			= tiempos[19];	
    	hojaDeVidaToPrint.setTiempoTrabajoPublicoano(aniopublico);
    	hojaDeVidaToPrint.setTiempoTrabajoPublicomes(mespublico);
    	hojaDeVidaToPrint.setTiempoTrabajoPrivadoano(anioprivado);
    	hojaDeVidaToPrint.setTiempoTrabajoPrivadomes(mesprivado);  
    	hojaDeVidaToPrint.setTiempoTrabajoIndependienteano(anioIndependiente);
    	hojaDeVidaToPrint.setTiempoTrabajoPrivadoIndependientemes(mesIndependiente);
        hojaDeVidaToPrint.setTiempoTrabajoAno(aniototal);
        hojaDeVidaToPrint.setTiempoTrabajoMes(mestotal);       
      }

    

    /**
     * Metodo que se encarga de consultar las experiencias profesionales y docentes activas de una persona.
     * Consulta el servicio getExperienciaProfesionalYDocente el cual debe de recibir como parametros obligatorios
     * el codigo de la persona y el flg_activo.
     * */
    public void cargarExperienciaProfesionalyDocente() {
    	lstExperiencias = new ArrayList<>();
    	ExperienciaProfesionalExt objConsulta = new ExperienciaProfesionalExt();
    	objConsulta.setCodPersona(new BigDecimal(codPersona));
    	objConsulta.setFlgActivo((short) 1);
    	lstExperiencias =  ComunicacionServiciosHV.getExperienciaProfesionalYDocente(objConsulta);
    }
    
    public void uploadMultix(String urlComplete, Boolean download) {
        List<InputStream> list = new ArrayList<InputStream>();
        try {
            list.add(new FileInputStream(new File(urlComplete)));
            for (String url : this.getUrlsDocumentos()) {
            	try {
					java.net.URL u = new java.net.URL( url);
					InputStream is = u.openStream();
					list.add(is);
            	}
            	catch(Exception ex) {
            		ImprimirDescargarHojaVidaBean.logger.error("void descargarHojaDeVida() Exception: " + ex.getMessage() + " " + ex.getStackTrace(), new Object());
            	}
            }
            this.doMerge(list, download);
        } catch (Exception e) {
        	ImprimirDescargarHojaVidaBean.logger.error("void descargarHojaDeVida() Exception" + e.getMessage() + " " + e.getStackTrace(), e);
        }
    }
    
    /**
     * Mezclar múltiples archivos PDF en uno
     * 
     * @param list
     *            Lista de InputStream de los archivos PDF a unir
     * @param outputStream
     *            Stream de archivo de salida del PDF que resulta de la unión de los streams de la lista de entrada
     * @throws DocumentException
     * @throws IOException
     */
    public void doMerge(List<InputStream> list, Boolean download) throws DocumentException, IOException {
        Document document = new Document();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        if (download) {
            response.addHeader("Content-disposition", "attachment; filename=" + hojaDeVidaToPrint.getDetallePersona().getPrimerNombre()
                    + hojaDeVidaToPrint.getDetallePersona().getPrimerApellido() + DateUtils.formatearACadena(new Date(), "ddMMyyyy") + ".pdf");
        }
        ServletOutputStream outputStream = response.getOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        PdfReader reader;
        for (InputStream in : list) {
            try{
            	if(hojaDeVidaToPrint.getDetallePersona().getNumeroIdentificacion()!=null)
            		reader = new PdfReader(in,hojaDeVidaToPrint.getDetallePersona().getNumeroIdentificacion().getBytes());
            	else
            		reader = new PdfReader(in);
                reader.isEncrypted();
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    document.newPage();
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    cb.addTemplate(page, 0, 0);
                }            	
            }catch(Exception z){
            	ImprimirDescargarHojaVidaBean.logger.error("void doMerge() Exception: " + z.getMessage() + " " + z.getStackTrace(), new Object());
            }

        }
        outputStream.flush();
        document.close();
        outputStream.close();
        FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().responseComplete();
    }

    
    @Override
    public String persist() throws NotSupportedException {
        return null;
    }
    
    @Override
    public void retrieve() throws NotSupportedException {
        // Auto-generated method stub
    }
    
    @Override
    public String update() throws NotSupportedException {
        return null;
    }
    
    @Override
    public void delete() throws NotSupportedException {
        // Auto-generated method stub
    }
    
    @Override
    public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
        // Auto-generated method stub
    }
    
    /*Metodos Get y Set*/
    
    /**
     * @return the optionView
     */
    public Boolean getOptionView() {
        return optionView;
    }
    
    /**
     * @param optionView
     *            the optionView to set
     */
    public void setOptionView(Boolean optionView) {
        this.optionView = optionView;
    }
    
    /**
     * @return the incluirAnexos
     */
    public Boolean getIncluirAnexos() {
        return incluirAnexos;
    }
    
    /**
     * @param incluirAnexos
     *            the incluirAnexos to set
     */
    public void setIncluirAnexos(Boolean incluirAnexos) {
        this.incluirAnexos = incluirAnexos;
    }
    
    /**
     * @return the urlImprimir
     */
    public String getUrlImprimir() {
        return urlImprimir;
    }
    
    /**
     * @return the urlDescargar
     */
    public String getUrlDescargar() {
        return urlDescargar;
    }
    
    /**
     * @return the urlsDocumentos
     */
    public List<String> getUrlsDocumentos() {
        return urlsDocumentos;
    }
    
    /**
     * @param urlsDocumentos
     *            the urlsDocumentos to set
     */
    public void setUrlsDocumentos(String urlsDocumentos) {
        this.urlsDocumentos.add(urlsDocumentos);
    }
    
    public List<DocumentosAdicionalesHvExt> getDocsAdi() {
		return docsAdi;
	}

	public void setDocsAdi(List<DocumentosAdicionalesHvExt> docsAdi) {
		this.docsAdi = docsAdi;
	}
	
	public HojaDeVidaPrintExt getHojaDeVidaToPrint() {
        return hojaDeVidaToPrint;
    }
    
    public void setHojaDeVidaToPrint(HojaDeVidaPrintExt hojaDeVidaToPrint) {
        this.hojaDeVidaToPrint = hojaDeVidaToPrint;
    }

	/**
	 * @return the lstExperiencias
	 */
	public List<ExperienciaProfesionalExt> getLstExperiencias() {
		return lstExperiencias;
	}

	/**
	 * @param lstExperiencias the lstExperiencias to set
	 */
	public void setLstExperiencias(List<ExperienciaProfesionalExt> lstExperiencias) {
		this.lstExperiencias = lstExperiencias;
	}

	
}