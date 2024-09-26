package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
public class TemplateHojaDeVidaBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = -7798687380326240680L;
	
	
	private Long tamPdfCedula 							= (long) 400000;
	private Long tamPdfLibretaMilitar 					= (long) 400000;
	private Long tamPdfDocumentoDiscapacidad			= (long) 400000;
	private Long tamPdfEducacionEducacionFormal 		= (long) 400000;
	private Long tamPdfEducacionTarjetaprofesional 		= (long) 400000;
	private Long tamPdfEducacionEducacionDesarrolloHumano = (long) 400000;
	private Long tamPdfEducacionEducacionidioma 		= (long) 400000;
	private Long tamPdfExpeienciaLaboral 				= (long) 400000;
	private Long tamPdfExpeienciaLaboralDocente 		= (long) 400000;
	private Long tamPdfDocumentosAdicionales 			= (long) 400000;
	private Boolean flgValidRolPermission 				= false;
	private Long codPersona 							= null;
	private String urlImprimir;
	private String urlDescargar;
	

	public Boolean getFlgValidRolPermission() {
		return flgValidRolPermission;
	}

	public void setFlgValidRolPermission(Boolean flgValidRolPermission) {
		this.flgValidRolPermission = flgValidRolPermission;
	}

	public String getUrlImprimir() {
		return urlImprimir;
	}

	public String getUrlDescargar() {
		return urlDescargar;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {}

	@PostConstruct
	public void init() {
		
		this.initialization();
        
		Parametrica parametrica;
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_CEDULA.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfCedula(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfCedula((long) 400000);
		}
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_LIBRETA.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfLibretaMilitar(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfLibretaMilitar((long) 400000);
		}
		
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_DOCUMENTO_DISCAPACIDAD.getValue()));
		this.setTamPdfDocumentoDiscapacidad(parametrica != null && parametrica.getValorParametro() != null  ? Long.parseLong(parametrica.getValorParametro()) : 400000L);
		
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_ED_TARJETAPROFESIONAL.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfEducacionTarjetaprofesional(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfEducacionTarjetaprofesional((long) 400000);
		}
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_ED_FORMAL.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfEducacionEducacionFormal(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfEducacionEducacionFormal((long) 400000);
		}
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_ED_DESARROLLOHUMANO.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfEducacionEducacionDesarrolloHumano(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfEducacionEducacionDesarrolloHumano((long) 400000);
		}	
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_ED_IDIOMA.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfEducacionEducacionidioma(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfEducacionEducacionidioma((long) 400000);
		}
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_EXPERIENCIA_LABORAL.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfExpeienciaLaboral(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfExpeienciaLaboral((long) 400000);
		}	
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_EXPERIENCIA_LABORAL_DOCENTE.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfExpeienciaLaboralDocente(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfExpeienciaLaboralDocente((long) 400000);
		}	
		parametrica = null;
		parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(TipoParametro.TAM_SOPORTE_PDF_HV_DOCUMENTOS_ADICIONALES.getValue()));
		if(parametrica!=null && parametrica.getValorParametro()!=null) {
			this.setTamPdfDocumentosAdicionales(Long.parseLong(parametrica.getValorParametro()));
		}else {
			this.setTamPdfDocumentosAdicionales((long) 400000);
		}				
		
		urlImprimir = "imprimirDescargarHojaVida.xhtml?id="+codPersona+"&option=OPT_PRINT";
		urlDescargar = "imprimirDescargarHojaVida.xhtml?id="+codPersona+"&option=OPT_DOWNLOAD";
	}
	
	public void initialization() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String idPersona = request.getParameter("id");
        
        if(idPersona != null) {
        	codPersona = Long.valueOf(idPersona);
        	
        	if(getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona){
        		try {
        			flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO, RolDTO.AUDITOR, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO);
        			if(flgValidRolPermission == false) {
        				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
        			}
        		} catch (SIGEP2SistemaException e) {
        			logger.error("void init() usuarioTieneRolAsignado", e);
        		} catch (IOException e) {
        			logger.error("void init() finalizarConversacion", e);
				}
        	}
        }else {
        	if(getUsuarioSesion() != null) {
        		codPersona = getUsuarioSesion().getCodPersona();
        		try {
	        		Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA, RolDTO.SERVIDOR_PUBLICO);
	    			if(flgValidRolPermission == false) {
	    				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
	    			}
        		} catch (SIGEP2SistemaException e) {
        			logger.error("void init() usuarioTieneRolAsignado", e);
        		} catch (IOException e) {
        			logger.error("void init() finalizarConversacion", e);
				}
        	}else {
        		try {
					this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
        	}
        }
	}
	
	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {}
	
	
	public String get64(String input) {
	//	return WebUtils.getUrlFileHadoop(input, null, null, null);
		return WebUtils.getUrlFile(input);
	}

	public Long getTamPdfCedula() {
		return tamPdfCedula;
	}

	public void setTamPdfCedula(Long tamPdfCedula) {
		this.tamPdfCedula = tamPdfCedula;
	}

	public Long getTamPdfLibretaMilitar() {
		return tamPdfLibretaMilitar;
	}

	public void setTamPdfLibretaMilitar(Long tamPdfLibretaMilitar) {
		this.tamPdfLibretaMilitar = tamPdfLibretaMilitar;
	}

	public Long getTamPdfEducacionEducacionFormal() {
		return tamPdfEducacionEducacionFormal;
	}

	public void setTamPdfEducacionEducacionFormal(Long tamPdfEducacionEducacionFormal) {
		this.tamPdfEducacionEducacionFormal = tamPdfEducacionEducacionFormal;
	}

	public Long getTamPdfEducacionTarjetaprofesional() {
		return tamPdfEducacionTarjetaprofesional;
	}

	public void setTamPdfEducacionTarjetaprofesional(Long tamPdfEducacionTarjetaprofesional) {
		this.tamPdfEducacionTarjetaprofesional = tamPdfEducacionTarjetaprofesional;
	}

	public Long getTamPdfEducacionEducacionDesarrolloHumano() {
		return tamPdfEducacionEducacionDesarrolloHumano;
	}

	public void setTamPdfEducacionEducacionDesarrolloHumano(Long tamPdfEducacionEducacionDesarrolloHumano) {
		this.tamPdfEducacionEducacionDesarrolloHumano = tamPdfEducacionEducacionDesarrolloHumano;
	}

	public Long getTamPdfEducacionEducacionidioma() {
		return tamPdfEducacionEducacionidioma;
	}

	public void setTamPdfEducacionEducacionidioma(Long tamPdfEducacionEducacionidioma) {
		this.tamPdfEducacionEducacionidioma = tamPdfEducacionEducacionidioma;
	}

	public Long getTamPdfExpeienciaLaboral() {
		return tamPdfExpeienciaLaboral;
	}

	public void setTamPdfExpeienciaLaboral(Long tamPdfExpeienciaLaboral) {
		this.tamPdfExpeienciaLaboral = tamPdfExpeienciaLaboral;
	}

	public Long getTamPdfExpeienciaLaboralDocente() {
		return tamPdfExpeienciaLaboralDocente;
	}

	public void setTamPdfExpeienciaLaboralDocente(Long tamPdfExpeienciaLaboralDocente) {
		this.tamPdfExpeienciaLaboralDocente = tamPdfExpeienciaLaboralDocente;
	}

	public Long getTamPdfDocumentosAdicionales() {
		return tamPdfDocumentosAdicionales;
	}

	public void setTamPdfDocumentosAdicionales(Long tamPdfDocumentosAdicionales) {
		this.tamPdfDocumentosAdicionales = tamPdfDocumentosAdicionales;
	}

	public Long getTamPdfDocumentoDiscapacidad() {
		return tamPdfDocumentoDiscapacidad;
	}

	public void setTamPdfDocumentoDiscapacidad(Long tamPdfDocumentoDiscapacidad) {
		this.tamPdfDocumentoDiscapacidad = tamPdfDocumentoDiscapacidad;
	}
	
	
}