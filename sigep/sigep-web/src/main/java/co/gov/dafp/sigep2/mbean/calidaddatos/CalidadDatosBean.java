package co.gov.dafp.sigep2.mbean.calidaddatos;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Persona;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosAdmin;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.CalidadDatosThread;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;


@ManagedBean(name = "calidadDatosBean")
@ViewScoped
@Named
public class CalidadDatosBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadedFile uploadFile = null;
	File file = null;
	private String nombreArchivo;
	private boolean lbArchivoCargado=false;

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() throws NotSupportedException, SIGEP2SistemaException {
		// TODO Auto-generated method stub
		
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
	
	
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void cargaArchivoUpload(FileUploadEvent e) throws IOException {
		byte[] bytes = null;
		uploadFile = e.getFile();
		bytes = uploadFile.getContents();
		file = new File(uploadFile.getFileName());
		file.setExecutable(true, false);
		file.setReadable(true, false);
		file.setWritable(true, false);
		FileUtils.writeByteArrayToFile(file, bytes);
		// verificamos si en el nombre del archivo no existen caracteres especiales
		this.nombreArchivo = new String(uploadFile.getFileName().getBytes("ISO-8859-1"),"UTF-8");
	}
	
	public void cargarArchivo(){
		if(uploadFile==null){
			lbArchivoCargado=false;
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.MSG_CALIDAD_DATOS_NO_ARCHIVO);
		}else{
			
			lbArchivoCargado = ComunicacionServiciosAdmin.setArchivoCalidadDatos(this.nombreArchivo, "xls", uploadFile.getContents());
			if(lbArchivoCargado){
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.MSG_CALIDAD_DATOS_ARCHIVO_CARGADO);				
			}else{
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.MSG_CALIDAD_DATOS_ARCHIVO_ERROR);
			}
		}
	}
	
	public void aplicarCorrecciones(){
		if(uploadFile==null){
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.MSG_CALIDAD_DATOS_NO_ARCHIVO);
		}else{
			Persona infoUsuario = ComunicacionServiciosHV.getPersonaPorId(getUsuarioSesion().getCodPersona());
			CalidadDatosThread hilo = new CalidadDatosThread(infoUsuario);
			hilo.run();			
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
			MessagesBundleConstants.MSG_CALIDAD_DATOS_EJECUTA_SEGUNDO_PLANO);	
		}
	}	

	public UploadedFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadedFile uploadFile) {
		this.uploadFile = uploadFile;
	}
}
