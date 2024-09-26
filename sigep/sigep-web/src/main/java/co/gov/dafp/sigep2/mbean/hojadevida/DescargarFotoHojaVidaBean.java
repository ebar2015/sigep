package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

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
import co.gov.dafp.sigep2.entities.HistoricoModificacionHojaVida;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.mbean.DatosPersonalesBean;
import co.gov.dafp.sigep2.mbean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.HojaDeVidaPrintExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.logger.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
@ManagedBean
public class DescargarFotoHojaVidaBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> urlsDocumentos = new ArrayList<>();
	
	HojaDeVidaPrintExt 							hojaDeVidaToPrint;
	private List<DocumentosAdicionalesHvExt> 	docsAdi;
	private List<HojaDeVidaPrintExt> 			listaHojasVidaToPrint = new ArrayList<>();
	PersonaExt 						persona ;
	private String 					strNombrePersona;
	private String 					filePathPlantillaJson;
	HistoricoModificacionHojaVida 	historico;
	
	private static final Logger logger = Logger.getInstance(DescargarFotoHojaVidaBean.class);
	private static final String PATH_FOTO_HV = "co/gov/dafp/sigep2/cargue/fotoHojaVida/";
	private static final String JUSTIFICACION_MODIFICACION = "FOTO HOJA DE VIDA EN LA PARTE DE APROBACION";

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}
	
	 public DescargarFotoHojaVidaBean() {
		super();
	}



	@PostConstruct
    public void init() {
		Long codHojaVida,codPersona;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String strCodHojaVida = request.getParameter("codhojavida");		
        String strCodPersona = request.getParameter("codpersona");	
        codHojaVida = !strCodHojaVida.isEmpty() ? Long.parseLong(strCodHojaVida): null;
        codPersona = !strCodPersona.isEmpty() ?  Long.parseLong(strCodPersona) : null;
		String  json = "";
		filePathPlantillaJson = "";
		
		PersonaExt objPersona = new PersonaExt();
		objPersona.setCodHojaVida(BigDecimal.valueOf(codHojaVida));
		persona = ComunicacionServiciosHV.getHojaVida(objPersona);
		
		HistoricoModificacionHojaVida buscador = new HistoricoModificacionHojaVida();
		buscador.setCodHojaVida(BigDecimal.valueOf(codHojaVida));
		buscador.setFlgActivo((short) 1);
		buscador.setJustificacionModificacion(JUSTIFICACION_MODIFICACION);
		List<HistoricoModificacionHojaVida> lstModificaciones = ComunicacionServiciosHV.getHistoricoModificacionHojaVidaByFiltro (buscador);
		
		FotoHojaVida foto;
		
		if(lstModificaciones!=null && lstModificaciones.size()>0){
			historico = lstModificaciones.get(0);
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
	
	public void imprimirHojaDeVida() {
        try {
        	if(historico!=null && historico.getUrlPdf()!=null && !"".equals(historico)){
    			/*Se debe de modificar el path de acuerdo a la parametrica*/
    			filePathPlantillaJson= (WebUtils.WS_MULTIMEDIA_DOWNLOAD + historico.getUrlPdf()).replace("/getfile/", ""); 
    			RequestContext.getCurrentInstance().execute("window.open('" + filePathPlantillaJson + "')");
        	}else {
        		if (historico!= null && historico.getImagenHojaVidaUrl()!=null ) {
        			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
        					MessagesBundleConstants.MSG_REPORTE_HOJA_VIDA_DATOS_NO_ACTUALIZADOS_SIGEPII);
        			
        		}else {
        			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
        					MessagesBundleConstants.MSG_REPORTE_HOJA_VIDA_SIN_APROBAR_SIGEPII);
        		}
        		
        	}
        } catch (Exception e) {
            logger.error("void imprimirHojaDeVida() JRException", e);
        } 
    }	
    public void uploadMultix(String urlComplete, Boolean download) {
        List<InputStream> list = new ArrayList<InputStream>();
        try {
            list.add(new FileInputStream(new File(urlComplete)));
            for (String url : this.getUrlsDocumentos()) {
            	try {
					java.net.URL u = new java.net.URL(url);
					InputStream is = u.openStream();
					list.add(is);
            	}
            	catch(Exception ex) {
            		DescargarFotoHojaVidaBean.logger.error("void descargarHojaDeVida() Exception: " + ex.getMessage() + " " + ex.getStackTrace(), new Object());
            	}
            }
            this.doMerge(list, download);
        } catch (Exception e) {
        	DescargarFotoHojaVidaBean.logger.error("void descargarHojaDeVida() Exception" + e.getMessage() + " " + e.getStackTrace(), e);
        }
    }
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
            	DescargarFotoHojaVidaBean.logger.error("void doMerge() Exception: " + z.getMessage() + " " + z.getStackTrace(), new Object());
            }

        }
        outputStream.flush();
        document.close();
        outputStream.close();
        FacesContext.getCurrentInstance().renderResponse();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void validarAnexosExistentes() {
        if (hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl() != null && !hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl().equals("")) 
            this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + hojaDeVidaToPrint.getDetallePersona().getDocumentoIdentificacionUrl().replace("/getShowFile/", "/"));      
        if (hojaDeVidaToPrint.getDetallePersona().getLibretaMilitarUrl() != null && !hojaDeVidaToPrint.getDetallePersona().getLibretaMilitarUrl().equals(""))
                this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + hojaDeVidaToPrint.getDetallePersona().getLibretaMilitarUrl().replace("/getShowFile/", "/"));
        for (EducacionFormalExt educacionFormal : hojaDeVidaToPrint.getDetalleEducacion()) {
            if (educacionFormal.getUrlAnexo() != null && !educacionFormal.getUrlAnexo().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + educacionFormal.getUrlAnexo().replace("/getShowFile/", "/"));
        }
        for (OtroConocimientoExt otroConocimiento : hojaDeVidaToPrint.getOtroConocimientoExt()) {
            if (otroConocimiento.getUrlDocumentoSoporte() != null && !otroConocimiento.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + otroConocimiento.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (IdiomaPersonaExt idiomaPersona : hojaDeVidaToPrint.getDetalleIdioma()) {
            if(idiomaPersona.getUrlCertificacion() != null && !idiomaPersona.getUrlCertificacion().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + idiomaPersona.getUrlCertificacion().replace("/getShowFile/", "/"));
        }
        for (ExperienciaProfesionalExt experienciaProfesional : hojaDeVidaToPrint.getDetalleLaboral()) {
            if (experienciaProfesional.getUrlDocumentoSoporte() != null && !experienciaProfesional.getUrlDocumentoSoporte().equals("")) 
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + experienciaProfesional.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (ExperienciaDocenteExt experienciaDocente : hojaDeVidaToPrint.getDetalleExpDocente()) {
            if (experienciaDocente.getUrlDocumentoSoporte() != null && !experienciaDocente.getUrlDocumentoSoporte().equals(""))
            	this.setUrlsDocumentos(WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + experienciaDocente.getUrlDocumentoSoporte().replace("/getShowFile/", "/"));
        }
        for (DocumentosAdicionalesHvExt doc : this.docsAdi) {
            if (doc.getUrlDocumentoAdicional() != null && !doc.getUrlDocumentoAdicional().isEmpty())
            	this.setUrlsDocumentos( WebUtils.WS_MULTIMEDIA_DOWNLOAD + (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("token") + doc.getUrlDocumentoAdicional().replace("/getShowFile/", "/"));
        }
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

	public List<String> getUrlsDocumentos() {
		return urlsDocumentos;
	}

	public void setUrlsDocumentos(List<String> urlsDocumentos) {
		this.urlsDocumentos = urlsDocumentos;
	}
    public void setUrlsDocumentos(String urlsDocumentos) {
        this.urlsDocumentos.add(urlsDocumentos);
    }

	public List<DocumentosAdicionalesHvExt> getDocsAdi() {
		return docsAdi;
	}

	public void setDocsAdi(List<DocumentosAdicionalesHvExt> docsAdi) {
		this.docsAdi = docsAdi;
	}

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	public String getStrNombrePersona() {
		return strNombrePersona;
	}

	public void setStrNombrePersona(String strNombrePersona) {
		this.strNombrePersona = strNombrePersona;
	}	
	
	public static String ucFirst(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		} else {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}

	public HojaDeVidaPrintExt getHojaDeVidaToPrint() {
		return hojaDeVidaToPrint;
	}

	public void setHojaDeVidaToPrint(HojaDeVidaPrintExt hojaDeVidaToPrint) {
		this.hojaDeVidaToPrint = hojaDeVidaToPrint;
	}

	public List<HojaDeVidaPrintExt> getListaHojasVidaToPrint() {
		return listaHojasVidaToPrint;
	}

	public void setListaHojasVidaToPrint(List<HojaDeVidaPrintExt> listaHojasVidaToPrint) {
		this.listaHojasVidaToPrint = listaHojasVidaToPrint;
	}

	public String getFilePathPlantillaJson() {
		return filePathPlantillaJson;
	}

	public void setFilePathPlantillaJson(String filePathPlantillaJson) {
		this.filePathPlantillaJson = filePathPlantillaJson;
	}

	public HistoricoModificacionHojaVida getHistorico() {
		return historico;
	}

	public void setHistorico(HistoricoModificacionHojaVida historico) {
		this.historico = historico;
	}
	
	
	
	
    
}
