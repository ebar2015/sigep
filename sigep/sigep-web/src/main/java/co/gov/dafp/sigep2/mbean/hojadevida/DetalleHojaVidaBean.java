package co.gov.dafp.sigep2.mbean.hojadevida;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.mbean.ext.HojaDeVidaPrintExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class DetalleHojaVidaBean extends BaseBean implements Serializable {
    private static final long serialVersionUID = 3174485411011282994L;
    private String idPersona;
    private List<HojaDeVidaPrintExt> listaHojasVidaToPrint;
    private HojaDeVidaPrintExt hojaDeVidaToPrint;
    private JasperPrint jPrint = null;

    public List<HojaDeVidaPrintExt> getListaHojasVidaToPrint() {
	return listaHojasVidaToPrint;
    }

    public void setListaHojasVidaToPrint(List<HojaDeVidaPrintExt> listaHojasVidaToPrint) {
	this.listaHojasVidaToPrint = listaHojasVidaToPrint;
    }

    public void setIdPersona(String idPersona) {
	this.idPersona = idPersona;
    }

    public HojaDeVidaPrintExt getHojaDeVidaToPrint() {
	return hojaDeVidaToPrint;
    }

    public void setHojaDeVidaToPrint(HojaDeVidaPrintExt hojaDeVidaToPrint) {
	this.hojaDeVidaToPrint = hojaDeVidaToPrint;
    }

    public JasperPrint getjPrint() {
	return jPrint;
    }

    public void setjPrint(JasperPrint jPrint) {
	this.jPrint = jPrint;
    }

    public String getIdPersona() {
	return idPersona;
    }

   @PostConstruct
   public void init() {
      hojaDeVidaToPrint = new HojaDeVidaPrintExt();
      listaHojasVidaToPrint = new ArrayList<>();
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      idPersona = request.getParameter("id");
      try {
         if (idPersona == null) {
            idPersona = "" + getUsuarioSesion().getCodPersona();
            hojaDeVidaToPrint.setDetallePersona(ComunicacionServiciosHV.getpersonacontacto(Long.parseLong(idPersona)));
            hojaDeVidaToPrint.setDetalleEducacion(ComunicacionServiciosHV.getEducacionFormal001(Long.parseLong(idPersona),true));
            hojaDeVidaToPrint.setDetalleLaboral(ComunicacionServiciosHV.getcargoentidadpersona(Long.parseLong(idPersona), true));
            hojaDeVidaToPrint.setDetalleIdioma(ComunicacionServiciosHV.getidiomapersonaporpersona(Long.parseLong(idPersona), true));
            listaHojasVidaToPrint.add(hojaDeVidaToPrint);
            String jsonToPrint = new Gson().toJson(listaHojasVidaToPrint);
            System.out.println("init() Bean --> " + jsonToPrint);
         } else {
            hojaDeVidaToPrint.setDetallePersona(ComunicacionServiciosHV.getpersonacontacto(Long.parseLong(idPersona)));
            hojaDeVidaToPrint.setDetalleEducacion(ComunicacionServiciosHV.getEducacionFormal001(Long.parseLong(idPersona),true));
            hojaDeVidaToPrint.setDetalleLaboral(ComunicacionServiciosHV.getcargoentidadpersona(Long.parseLong(idPersona), true));
            hojaDeVidaToPrint.setDetalleIdioma(ComunicacionServiciosHV.getidiomapersonaporpersona(Long.parseLong(idPersona), true));
            listaHojasVidaToPrint.add(hojaDeVidaToPrint);
            String jsonToPrint = new Gson().toJson(listaHojasVidaToPrint);
            System.out.println("init() Bean --> " + jsonToPrint);
         }
      } catch (Exception e) {
         System.out.println("CATCH DetalleHojaVidaBean " + e.getMessage());
         e.printStackTrace();
      }
   }

    private void inicializarJasper() {
	try {
	    String jasperPath = "/resources/jasper/hojaDeVidaFormatoUnico.jasper";
	    String relativePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(jasperPath);
	    File filePDF = new File(relativePath);
	    JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaHojasVidaToPrint, false);
	    jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);
	    System.out.println("inicializarJasper()");
	} catch (JRException e) {
	    System.out.println("CATCH inicializarJasper()");
	    e.printStackTrace();
	}
    }

    public void verPDF() {
	inicializarJasper();
	try {
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    ServletOutputStream stream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jPrint, stream);
	    stream.flush();
	    stream.close();
	    FacesContext.getCurrentInstance().responseComplete();
	} catch (Exception e) {
	    System.out.println("CATCH verPDF() " + e.getMessage());
	    e.printStackTrace();
	}
    }

    public void guardarPDF() {
	inicializarJasper();
	try {
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	    response.addHeader("Content-disposition", "attachment; filename=" + hojaDeVidaToPrint.getDetallePersona().getPrimerNombre()
		    + hojaDeVidaToPrint.getDetallePersona().getPrimerApellido() + ".pdf");
	    ServletOutputStream stream = response.getOutputStream();
	    JasperExportManager.exportReportToPdfStream(jPrint, stream);
	    stream.flush();
	    stream.close();
	    FacesContext.getCurrentInstance().responseComplete();
	} catch (Exception e) {
	    System.out.println("CATCH guardarPDF() " + e.getMessage());
	    e.printStackTrace();
	}
    }

    @Override
    public void retrieve() throws NotSupportedException {
	if (FacesContext.getCurrentInstance().isPostback()) {
	    return;
	}
	if (this.conversation.isTransient()) {
	    this.conversation.begin();
	    this.conversation.setTimeout(timeOut);
	}
	this.init();
    }

    @Override
    public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
    }

    @Override
    public String persist() throws NotSupportedException {
	return null;
    }

    @Override
    public String update() throws NotSupportedException {
	return null;
    }

    @Override
    public void delete() throws NotSupportedException {
    }
}