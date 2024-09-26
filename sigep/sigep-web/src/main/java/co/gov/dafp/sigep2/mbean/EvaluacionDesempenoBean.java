package co.gov.dafp.sigep2.mbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.hojadevida.EvaluacionDesempenoPdf;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class EvaluacionDesempenoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 3695472896249884567L;

	private PersonaExt persona;
	private EvaluacionDesempenoExt evaluacionDesempeno;
	private EvaluacionDesempenoPdf evaluacionDesempenoPdf = new EvaluacionDesempenoPdf();
	private Boolean visible = true;
	private Boolean cargoPublico = Boolean.TRUE;
	private JasperPrint jPrint = null;
	private String usuarioConDesempeno;
	private String usuarioSinDesempeno;
	private String srtDevolerPagina; //variable para recibir la ruta del boton retornar

	private Long codPersona = null;

	public Boolean getCargoPublico() {
		return cargoPublico;
	}

	public void setCargoPublico(Boolean cargoPublico) {
		this.cargoPublico = cargoPublico;
	}

	public JasperPrint getjPrint() {
		return jPrint;
	}

	public void setjPrint(JasperPrint jPrint) {
		this.jPrint = jPrint;
	}

	public EvaluacionDesempenoExt getEvaluacionDesempeno() {
		return this.evaluacionDesempeno;
	}

	public void setEvaluacionDesempeno(EvaluacionDesempenoExt evaluacionDesempeno) {
		this.evaluacionDesempeno = evaluacionDesempeno;
	}

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	public String getUsuarioSinDesempeno() {
		return usuarioSinDesempeno;
	}

	public void setUsuarioSinDesempeno(String usuarioSinDesempeno) {
		this.usuarioSinDesempeno = usuarioSinDesempeno;
	}

	public String getUsuarioConDesempeno() {
		return usuarioConDesempeno;
	}

	public void setUsuarioConDesempeno(String usuarioConDesempeno) {
		this.usuarioConDesempeno = usuarioConDesempeno;
	}

	public EvaluacionDesempenoPdf getEvaluacionDesempenoPdf() {
		return evaluacionDesempenoPdf;
	}

	public void setEvaluacionDesempenoPdf(EvaluacionDesempenoPdf evaluacionDesempenoPdf) {
		this.evaluacionDesempenoPdf = evaluacionDesempenoPdf;
	}

	/**
	 * @return the srtDevolerPagina
	 */
	public String getSrtDevolerPagina() {
		return srtDevolerPagina;
	}

	/**
	 * @param srtDevolerPagina the srtDevolerPagina to set
	 */
	public void setSrtDevolerPagina(String srtDevolerPagina) {
		this.srtDevolerPagina = srtDevolerPagina;
	}

	@PostConstruct
	public void init() {

		this.initialization();

		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		evaluacionDesempeno = ComunicacionServiciosHV.getEvaluacionDesempenoPorPersona(codPersona, this.getEntidadUsuario().getId());
		
		TipoDocumentoDTO documento;

		try {
			documento = AdministracionDelegate.findTipoDocumentoId(Long.parseLong(persona.getCodTipoIdentificacion()));
			persona.setNombreTipoDocuento(documento.getSigla());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}

		if (evaluacionDesempeno.getCodEvaluacionDesempeno() == null) {
			visible = false;
		} else {

			if (evaluacionDesempeno.getCargoEntidadPrivada()!=null
					&& !"".equals(evaluacionDesempeno.getCargoEntidadPrivada())
					&& !evaluacionDesempeno.getCargoEntidadPrivada().trim().equals("0")) {
				cargoPublico = Boolean.FALSE;

			} else {
				cargoPublico = Boolean.TRUE;
			}
		}

		usuarioConDesempeno = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_USUARIO_CON_DESEMPENO, getLocale())
				.replace("%tipo%", persona.getNombreTipoDocuento())
				.replace("%numero%", persona.getNumeroIdentificacion())
				.replace("%nombreusuario%", persona.getNombreUsuario());

		usuarioSinDesempeno = MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.MSG_USUARIO_SIN_DESEMPENO, getLocale())
				.replace("%tipo%", persona.getNombreTipoDocuento())
				.replace("%numero%", persona.getNumeroIdentificacion())
				.replace("%nombreusuario%", persona.getNombreUsuario());

		this.configuracionParametros();
	}

	public void initialization() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String idPersona = request.getParameter("id");
		String optBackView = request.getParameter("backView");
		
		if(optBackView != null && optBackView.equals("gestionarHojaDeVida")) {
			this.setSrtDevolerPagina("gestionarHojaDeVida.xhtml?recursoId=InformacionPersonasTag#no-back-button");
		}else {
			this.setSrtDevolerPagina("informacionPersonal.xhtml?recursoId=HojaDeVidaSubMenu#no-back-button");
		}
		
		if (idPersona != null) {
			codPersona = Long.valueOf(idPersona);

			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != codPersona) {
				try {
					Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTROL_INTERNO,
							RolDTO.AUDITOR, RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
							RolDTO.JEFE_CONTRATOS, RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_CONTRATOS,
							RolDTO.OPERADOR_TALENTO_HUMANO);
					if (flgValidRolPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
				} catch (SIGEP2SistemaException e) {
					logger.error("void init() usuarioTieneRolAsignado", e);
				} catch (IOException e) {
					logger.error("void init() finalizarConversacion", e);
				}
			}
		} else {
			if (getUsuarioSesion() != null) {
				codPersona = getUsuarioSesion().getCodPersona();
				try {
					Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO,
							RolDTO.CONTRATISTA);
					if (flgValidRolPermission == false) {
						this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
					}
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

	@Override
	public String persist() {
		return null;
	}

	@Override
	public void retrieve() {
	}

	@Override
	public String update() {
		return null;
	}

	@Override
	public void delete() {
	}

	public void descargarCertificado() {
		try {
			List<EvaluacionDesempenoPdf> listaCertificado = new ArrayList<>();
			listaCertificado.add(evaluacionDesempenoPdf);

			String relativePath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/evaluacionDesempenoPdf.jasper");
			File filePDF = new File(relativePath);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaCertificado, false);
			JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=Evaluaciones-Acuerdos_"
					+ persona.getNombreUsuario() + "_" + DateUtils.formatearACadena(new Date(), "ddMMyyyy") + ".pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().renderResponse();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void imprimirCertificado() {
		try {

			List<EvaluacionDesempenoPdf> listaCertificado = new ArrayList<>();
			listaCertificado.add(evaluacionDesempenoPdf);

			String relativePath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/evaluacionDesempenoPdf.jasper");
			File filePDF = new File(relativePath);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaCertificado, false);
			JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().renderResponse();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void configuracionParametros() {

		evaluacionDesempenoPdf.setEvaluacionDesempeno(evaluacionDesempeno);
		evaluacionDesempenoPdf.setUrlLogo("/resources/images/Certificado de informacion actual/hoja de vida-32.png");
		evaluacionDesempenoPdf
				.setUrlBackground("/resources/images/Certificado de informacion actual/back certificado-31.jpg");
		evaluacionDesempenoPdf.setUrlFooter("/resources/images/Certificado de informacion actual/hoja de vida-326.png");
		evaluacionDesempenoPdf.setTitulo(TitlesBundleConstants
				.getStringMessagesBundle(TitlesBundleConstants.LBL_EVALUACION_DESEMPENO, getLocale()));
		evaluacionDesempenoPdf.setContenido(usuarioConDesempeno);
	}
	
	/**
	 * Metodo para redireccionar al usuario
	 */
    public void volver() {
    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(this.getSrtDevolerPagina());
		} catch (IOException e) {
			logger.error("void redireccionInformacionPersonal()",e);
		}
    }
}
