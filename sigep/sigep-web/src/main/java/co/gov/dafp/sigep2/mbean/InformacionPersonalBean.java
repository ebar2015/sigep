package co.gov.dafp.sigep2.mbean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;

import org.primefaces.context.RequestContext;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entities.Contrato;
import co.gov.dafp.sigep2.entities.LogroRecurso;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.ParticipacionInstitucion;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.TipoDocumentoDTO;
import co.gov.dafp.sigep2.mbean.ext.ContratoExt;
import co.gov.dafp.sigep2.mbean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.mbean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.mbean.ext.EducacionFormalExt;
import co.gov.dafp.sigep2.mbean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaDocenteExt;
import co.gov.dafp.sigep2.mbean.ext.ExperienciaProfesionalExt;
import co.gov.dafp.sigep2.mbean.ext.IdiomaPersonaExt;
import co.gov.dafp.sigep2.mbean.ext.OtroConocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionProyectoExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PublicacionExt;
import co.gov.dafp.sigep2.mbean.ext.ReconocimientoExt;
import co.gov.dafp.sigep2.mbean.ext.UsuarioExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.mbean.hojadevida.CertificadoInformacionActual;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosCO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
@ManagedBean
public class InformacionPersonalBean extends BaseBean implements Serializable {

	private static final long serialVersionUID 					= -7798687380326240680L;
	private static final String CERTIFICADO_INF_IMG_LOGO 		= "CERTIFICADO_INF_IMG_LOGO";
	private static final String CERTIFICADO_INF_IMG_BACKGROUND 	= "CERTIFICADO_INF_IMG_BACKGROUND";
	private static final String CERTIFICADO_INF_IMG_FOOTER 		= "CERTIFICADO_INF_IMG_FOOTER";
	private Boolean validarRolServidorPublico					 = true;
	private PersonaExt persona;
	private UsuarioExt usuarioData;
	private int progresoHojadeVida;
	private long rol;
	private boolean rolaprovado = false;
	private String mensajeini = "";
	private String contenidoText;
	private ExternalContext contexto;
	private Parametrica footer;
	private Boolean blnMostrarBarraProgresoHV;

	private static Long codPersona;

	private CertificadoInformacionActual certificadoInformacion = new CertificadoInformacionActual();

	public UsuarioExt getUsuarioData() {
		return usuarioData;
	}

	public void setUsuarioData(UsuarioExt usuarioData) {
		this.usuarioData = usuarioData;
	}

	public PersonaExt getPersona() {
		return persona;
	}

	public void setPersona(PersonaExt persona) {
		this.persona = persona;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		// Do nothing because it isn't necessary
	}

	public int getProgresoHojadeVida() {
		return progresoHojadeVida;
	}

	public void setProgresoHojadeVida(int progresoHojadeVida) {
		this.progresoHojadeVida = progresoHojadeVida;
	}

	public Boolean getValidarRolServidorPublico() {
		return validarRolServidorPublico;
	}

	public void setValidarRolServidorPublico(Boolean validarRolServidorPublico) {
		this.validarRolServidorPublico = validarRolServidorPublico;
	}

	public CertificadoInformacionActual getCertificadoInformacion() {
		return certificadoInformacion;
	}

	public void setCertificadoInformacion(CertificadoInformacionActual certificadoInformacion) {
		this.certificadoInformacion = certificadoInformacion;
	}

	public String getContenidoText() {
		return contenidoText;
	}
	
	

	public Parametrica getFooter() {
		return footer;
	}

	public void setFooter(Parametrica footer) {
		this.footer = footer;
	}
	
	public Boolean getBlnMostrarBarraProgresoHV() {
		return blnMostrarBarraProgresoHV;
	}

	public void setBlnMostrarBarraProgresoHV(Boolean blnMostrarBarraProgresoHV) {
		this.blnMostrarBarraProgresoHV = blnMostrarBarraProgresoHV;
	}

	@PostConstruct
	public void init() {
		String stresLoggin;
		contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("numeroIdentificacion", "0");
		boolean rolValido;
		MostrarOcultarBarraProgresoHv();
		try {
			rolValido = usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO);
			if (!rolValido) {
				RequestContext.getCurrentInstance().execute("$('#miDeclaRenta').attr('style','display:none');");
			}
		} catch (Exception e) {
			logger.error("context", e);
		}
		mensajeini = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_DECLARACION_012,getLocale());
		if (this.getRolesUsuarioSesion().size() > 1) {
			for (int i = 0; i < this.getRolesUsuarioSesion().size(); i++) {
				int index = (int) this.getRolesUsuarioSesion().get(i).getId();
				if (index == 16 || index == 15 || index == 7 || index == 10) {
					rolaprovado = true;
				}
			}
		} else {
			try {
				int index = (int) this.getRolesUsuarioSesion().get(0).getId();
				rol = index;
				if (index == 16 || index == 15 || index == 7 || index == 10) {
					rolaprovado = true;
				}
			} catch (IndexOutOfBoundsException e) {
				rolaprovado = false;
			}

		}
		this.initialization();

		try {
			boolean valid = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.CONTRATISTA);
			if (valid) {
				validarRolServidorPublico = false;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado");
		}
		try {
			if (getUsuarioSesion() != null && getUsuarioSesion().getCodPersona() != null && getEntidadUsuario() != null) {
				persona = ComunicacionServiciosHV.getPersonaporIdExt(getUsuarioSesion().getCodPersona());
				if(persona!=null && !persona.getNumeroIdentificacion().isEmpty()) {
					contexto.getSessionMap().put("numeroIdentificacion", persona.getNumeroIdentificacion() );
				}
				codPersona = getUsuarioSesion().getCodPersona();
				usuarioData = ComunicacionServiciosHV.getusuarioext(getUsuarioSesion().getCodPersona(),getEntidadUsuario().getId());
							
				
				stresLoggin = (String) contexto.getSessionMap().get("inicioSesionLoggin");
				if(stresLoggin==null || "".equals(stresLoggin) ||!"1".equals(stresLoggin)) {
					this.progresoHojadeVida();
				}else {
					if(persona!=null && persona.getPorcentajeHV()!=null) {
						progresoHojadeVida = persona.getPorcentajeHV().intValue();
					}
					contexto.getSessionMap().put("inicioSesionLoggin","0");
				}
				this.configuracionParametrosCertificado();
			}
		} catch (Exception e) {
			logger.error("void init() Persona");
		}
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("verVideo","ingresoSistema");			
	}

	public void initialization() {
		try {
			Boolean flgValidRolPermission = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO, RolDTO.CONTRATISTA,
					RolDTO.OPERADOR_CONTRATOS, RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.ADMINISTRADOR_FUNCIONAL);
			if (!flgValidRolPermission) {
				this.finalizarConversacion(true, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, null);
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init() usuarioTieneRolAsignado", e);
		} catch (IOException e) {
			logger.error("void init() finalizarConversacion", e);
		}
	}

	/* El Metodo MostrarOcultarBarraProgresoHv() se encarga de ocultar 
	 * o mostrar la barra del progreso de la hoja de vida,
	 * ubicada en el SideBar.
	 * */
	public void MostrarOcultarBarraProgresoHv() {
		try{
			Long parametricaMostrarBarraProgeso = (long) TipoParametro.HABILITAR_BARRA_PROGRESO_HV.getValue();
			if(parametricaMostrarBarraProgeso!=null && parametricaMostrarBarraProgeso >0){
				Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(BigDecimal.valueOf(parametricaMostrarBarraProgeso));
				if(parametrica!=null && parametrica.getValorParametro()!=null && "S".equals(parametrica.getValorParametro().toUpperCase()) && 
						parametrica.getFlgActivo() !=null && parametrica.getFlgEstado() == 1){
					blnMostrarBarraProgresoHV = true;
				}else{
					blnMostrarBarraProgresoHV = false;
				}
			}else{
				blnMostrarBarraProgresoHV = false;
			}
		}catch(Exception z){
			blnMostrarBarraProgresoHV = false;
			logger.error(z.getMessage(), z.getMessage());
		}
	}
	
	
	@Override
	public String persist() throws NotSupportedException {
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		// Do nothing because it isn't necessary
	}

	@Override
	public String update() throws NotSupportedException {
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// Do nothing because it isn't necessary
	}
	public void  progresoHojadeVida(){
		int contadorObligatoriosUsuario = 0, contadorOpcionalesUsuario = 0;
		int totalObligatorios,totalObligatoriosDatosPersonales,totalObligatoriosEdFormacionAc,
		    totalObligatoriosEdTrabajo, totalObligatoriosIdioma, totalObligatoriosEP,
		    totalObligatoriosED,totalObligatoriosGPLogros,totalObligatoriosGPPublicaciones,
		    totalObligatoriosGPEvD,totalObligatoriosGPPremios,totalObligatoriosGPParticipaProyectos,
		    totalObligatoriosGPParticipaCorp,
		    totalOpcionales,totalOpcionalesDatosPersonales;
		BigDecimal    pesoObligatorio, pesoOpcionales, sumaFormulaObligatorios, sumaFormulaOpcionales, totalProgeso;
		DatoContactoExt datosContacto;
		DatoAdicionalExt datosAdicionales;
		
		/*Busqueda de informacion*/
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		datosContacto = ComunicacionServiciosHV.getDatoContacto(codPersona);
		datosAdicionales = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		List<EducacionFormalExt> listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
		List<OtroConocimientoExt> listaOtroConocimientoPorPersona = ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true);
		List<IdiomaPersonaExt> listaIdiomasPorPersona = ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true);
		List<ExperienciaProfesionalExt> listExperienciaProfesionalExt = ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true);
		List<ExperienciaDocenteExt> listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
		LogroRecurso buscador = new LogroRecurso();
		buscador.setCodPersona(BigDecimal.valueOf(codPersona));
		buscador.setFlgActivo((short) 1);
		List<LogroRecurso> lstLogrosYRecursos = ComunicacionServiciosHV.getLogrosRecursosFiltro(buscador);
		PublicacionExt buscadorp = new PublicacionExt();
		buscadorp.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorp.setFlgActivo((short) 1);
		List<PublicacionExt> lstPublicaciones = ComunicacionServiciosHV.getPublicacionesFiltro(buscadorp);
		EvaluacionDesempenoExt buscador3 = new EvaluacionDesempenoExt();
		buscador3.setCodPersona(BigDecimal.valueOf(codPersona));
		buscador3.setFlgActivo((short) 1);
		List<EvaluacionDesempenoExt> lstEvsDedesempenno = ComunicacionServiciosHV.getEvDesempenno(buscador3);
		ReconocimientoExt premio = new ReconocimientoExt();
		premio.setCodPersona(BigDecimal.valueOf(codPersona));
		premio.setFlgActivo((short) 1);
		List<ReconocimientoExt> lstPremiosReconocimientos = ComunicacionServiciosHV.getPremiosReconocimientosHV(premio);
		ParticipacionProyectoExt pp = new ParticipacionProyectoExt();
		pp.setCodPersona(BigDecimal.valueOf(codPersona));
		pp.setFlgActivo((short) 1);
		List<ParticipacionProyectoExt> lstParticipacionproyectos = ComunicacionServiciosHV.getParticipacionProyectosHV(pp);		
		ParticipacionInstitucion buscadori = new ParticipacionInstitucion();
		buscadori.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadori.setFlgActivo((short) 1);
		List<ParticipacionInstitucion> lstParticipacionCorporaciones = ComunicacionServiciosHV.getParticipacionInstitucionHV(buscadori);			
		
		pesoObligatorio = new BigDecimal(80);
		pesoOpcionales= new BigDecimal(20);
		
		totalObligatoriosDatosPersonales = 21;
		totalOpcionalesDatosPersonales   = 16;
		totalObligatoriosEdFormacionAc   = 1;
		totalObligatoriosEdTrabajo       = 1;
		totalObligatoriosIdioma          = 1;
		totalObligatoriosEP              = 1;
		totalObligatoriosED              = 1;
		totalObligatoriosGPLogros        = 1;
		totalObligatoriosGPPublicaciones = 1;
		totalObligatoriosGPEvD           = 1;
		totalObligatoriosGPPremios       = 1;
		totalObligatoriosGPParticipaProyectos = 1;
		totalObligatoriosGPParticipaCorp      = 1;
		
		/*Se inicia el total obligatorios con el total obligatorios de datos personales*/
		totalObligatorios = totalObligatoriosDatosPersonales+totalObligatoriosEdFormacionAc+totalObligatoriosEdTrabajo+
				            +totalObligatoriosIdioma+totalObligatoriosEP+totalObligatoriosED+totalObligatoriosGPLogros
				            +totalObligatoriosGPPublicaciones+totalObligatoriosGPEvD+totalObligatoriosGPPremios+totalObligatoriosGPParticipaProyectos
				            +totalObligatoriosGPParticipaCorp;
		/*Se inicia el total opcionales con el total opcionales de datos personales*/
		totalOpcionales   = totalOpcionalesDatosPersonales;
		
		/*Obligatorios datos básicos*/
		if(persona!= null && persona.getPrimerNombre()!=null && !"".equals(persona.getPrimerNombre()))
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCodTipoIdentificacion()!=null && !"".equals(persona.getCodTipoIdentificacion()))
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getNumeroIdentificacion() !=null && !"".equals(persona.getNumeroIdentificacion()))
			contadorObligatoriosUsuario++;	
		if(persona!= null && persona.getFechaNacimiento() !=null )
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCorreoElectronico() !=null && !"".equals(persona.getCorreoElectronico()))
			contadorObligatoriosUsuario++;			
		if(persona!= null && persona.getCodGenero()!=null)
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getUrlArchivo()!=null && !"".equals(persona.getUrlArchivo()))
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCodGenero()!=null && persona.getCodGenero() ==55  ){
			if(persona.getLibretaMilitarUrl()!=null &&!"".equals(persona.getLibretaMilitarUrl()))
				contadorObligatoriosUsuario++;
			if (persona.getLibretaMilitarUrl() != null) 
				contadorObligatoriosUsuario++;
			if (persona.getDistritoMilitar() != null)
				contadorObligatoriosUsuario++;
			if (persona.getNumeroLibretaMilitar() != null)
				contadorObligatoriosUsuario++;
			if (persona.getClaseLibretaMilitar() != null) 
				contadorObligatoriosUsuario++;
		}
		/*Opcionales datos básicos*/
		if(persona!= null && persona.getSegundoNombre()!=null && !"".equals(persona.getSegundoNombre()))
			contadorOpcionalesUsuario++;
		if(persona!= null && persona.getSegundoApellido()!=null && !"".equals(persona.getSegundoApellido()))
			contadorOpcionalesUsuario++;
		if(persona.getCorreoAlternativo()!=null &&!"".equals(persona.getCorreoAlternativo()))
			contadorOpcionalesUsuario++;
		/*Obligatorios datos demograficos*/
		if(persona!= null && persona.getCodEstadoCivil()!=null)
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCodPaisNacimiento()!=null)
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCodDepartamentoNacimiento()!=null)
			contadorObligatoriosUsuario++;
		if(persona!= null && persona.getCodMunicipioNacimiento()!=null)
			contadorObligatoriosUsuario++;
		/*Opcionales datos demograficos*/
		if(persona!= null && persona.getCodPertenenciaEtnica()!=null)
			contadorOpcionalesUsuario++;
		
		if(datosContacto!=null){
			/*Datos obligatorios datos contacto*/
			if(datosContacto.getCodPaisResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodDepartamentoResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodMunicipioResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodTipoZona()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getDireccionResidencia()!=null)
				contadorObligatoriosUsuario++;
			/*Datos opcionales datos contacto*/
			if(datosContacto.getTelefonoResidencia()!=null &&!"".equals(datosContacto.getTelefonoResidencia()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getNumCelular()!=null &&!"".equals(datosContacto.getNumCelular()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getIndicativoOficina()!=null &&!"".equals(datosContacto.getIndicativoOficina()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getNumTelefonoOficina()!=null &&!"".equals(datosContacto.getNumTelefonoOficina()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getExtencionTelefonoOficina()!=null)
				contadorOpcionalesUsuario++;
		}
		if(datosAdicionales!=null){
			/*Datos Adicionales Obligatorios*/
			if(datosAdicionales.getCodVictimaDesplazamiento()!=null)
				contadorObligatoriosUsuario++;
			/*Datos Adicionales Opcionales*/
			if(datosAdicionales.getCodCabezaHogar()!=null)
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getCodOrientacionSexual()!=null)
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getOtraOrientacionSexual()!=null &&!"".equals(datosAdicionales.getOtraOrientacionSexual()))
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getUrlFacebook()!=null && !"".equals(datosAdicionales.getUrlFacebook()))
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getUrlLinkedin()!=null && !"".equals(datosAdicionales.getUrlLinkedin()))
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getUrlInstagram()!=null && !"".equals(datosAdicionales.getUrlInstagram()))
				contadorOpcionalesUsuario++;
			if(datosAdicionales.getUrlTwitter()!=null && !"".equals(datosAdicionales.getUrlTwitter()))
				contadorOpcionalesUsuario++;
		}
		
		if(listaEducacionFormalPorPersona!=null && listaEducacionFormalPorPersona.size()>0)
			contadorObligatoriosUsuario++;
		if(listaOtroConocimientoPorPersona!=null && listaOtroConocimientoPorPersona.size()>0)
			contadorObligatoriosUsuario++;		
		if(listaIdiomasPorPersona!=null && listaIdiomasPorPersona.size()>0)
			contadorObligatoriosUsuario++;	
		
		if(listExperienciaProfesionalExt!=null && listExperienciaProfesionalExt.size()>0)
			contadorObligatoriosUsuario++;	
		if(listExperienciaDocenteExt!=null && listExperienciaDocenteExt.size()>0)
			contadorObligatoriosUsuario++;	
		if(lstLogrosYRecursos!=null && lstLogrosYRecursos.size()>0)
			contadorObligatoriosUsuario++;	
		if(lstPublicaciones!=null && lstPublicaciones.size()>0)
			contadorObligatoriosUsuario++;		
		if(lstEvsDedesempenno!=null && lstEvsDedesempenno.size()>0)
			contadorObligatoriosUsuario++;	
		if(lstPremiosReconocimientos!=null && lstPremiosReconocimientos.size()>0)
			contadorObligatoriosUsuario++;
		if(lstParticipacionproyectos!=null && lstParticipacionproyectos.size()>0)
			contadorObligatoriosUsuario++;
		if(lstParticipacionCorporaciones!=null && lstParticipacionCorporaciones.size()>0)
			contadorObligatoriosUsuario++;			
		
		sumaFormulaObligatorios = (new BigDecimal(contadorObligatoriosUsuario).divide(new BigDecimal(totalObligatorios),2, RoundingMode.HALF_UP));
		sumaFormulaObligatorios = sumaFormulaObligatorios.multiply(pesoObligatorio.divide(new BigDecimal(100)));
		sumaFormulaOpcionales   = (new BigDecimal(contadorOpcionalesUsuario).divide(new BigDecimal(totalOpcionales),2, RoundingMode.HALF_UP));
		sumaFormulaOpcionales   = sumaFormulaOpcionales.multiply(pesoOpcionales.divide(new BigDecimal(100)));
		totalProgeso = sumaFormulaObligatorios.add(sumaFormulaOpcionales);
		totalProgeso = totalProgeso.round(new MathContext(2, RoundingMode.HALF_UP));
		totalProgeso = totalProgeso.multiply(new BigDecimal(100));
		progresoHojadeVida = totalProgeso.intValue();
	}	
	public void  progresoHojadeVidaListasProporcional(){
		int contadorObligatoriosUsuario = 0, contadorOpcionalesUsuario = 0;
		int totalObligatorios,totalObligatoriosDatosPersonales,totalObligatoriosEdFormacionAc,
		    totalObligatoriosEdTrabajo, totalObligatoriosIdioma, totalObligatoriosEP,
		    totalObligatoriosED,totalObligatoriosGPLogros,totalObligatoriosGPPublicaciones,
		    totalObligatoriosGPEvD,totalObligatoriosGPPremios,totalObligatoriosGPParticipaProyectos,
		    totalObligatoriosGPParticipaCorp,
		    totalOpcionales,totalOpcionalesDatosPersonales,totalOpcionalesEdFormacionAc,
		    totalOpcionalesEdTrabajo, totalOpcionalesIdioma, totalOpcionalesEP,
		    totalOpcionalesED,totalOpcionalesGPLogros,totalOpcionalesGPPublicaciones,
		    totalOpcionalesGPEvD,totalOpcionalesGPPremios,totalOpcionalesGPParticipaProyectos,
		    totalOpcionalesGPParticipaCorp;
		BigDecimal    pesoObligatorio, pesoOpcionales, sumaFormulaObligatorios, sumaFormulaOpcionales, totalProgeso;
		DatoContactoExt datosContacto;
		DatoAdicionalExt datosAdicionales;
		
		/*Busqueda de informacion*/
		persona = ComunicacionServiciosHV.getPersonaporIdExt(codPersona);
		datosContacto = ComunicacionServiciosHV.getDatoContacto(codPersona);
		datosAdicionales = ComunicacionServiciosHV.getDatoContactoAdi(codPersona);
		List<EducacionFormalExt> listaEducacionFormalPorPersona = ComunicacionServiciosHV.getEducacionFormal001(codPersona, true);
		List<OtroConocimientoExt> listaOtroConocimientoPorPersona = ComunicacionServiciosHV.getotroconocimientoporpersona(codPersona, true);
		List<IdiomaPersonaExt> listaIdiomasPorPersona = ComunicacionServiciosHV.getidiomapersonaporpersona(codPersona, true);
		List<ExperienciaProfesionalExt> listExperienciaProfesionalExt = ComunicacionServiciosHV.getcargoentidadpersona(codPersona, true);
		List<ExperienciaDocenteExt> listExperienciaDocenteExt = ComunicacionServiciosHV.geExperienciaDocenteBycodPer(codPersona, true);
		LogroRecurso buscador = new LogroRecurso();
		buscador.setCodPersona(BigDecimal.valueOf(codPersona));
		buscador.setFlgActivo((short) 1);
		List<LogroRecurso> lstLogrosYRecursos = ComunicacionServiciosHV.getLogrosRecursosFiltro(buscador);
		PublicacionExt buscadorp = new PublicacionExt();
		buscadorp.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadorp.setFlgActivo((short) 1);
		List<PublicacionExt> lstPublicaciones = ComunicacionServiciosHV.getPublicacionesFiltro(buscadorp);
		EvaluacionDesempenoExt buscador3 = new EvaluacionDesempenoExt();
		buscador3.setCodPersona(BigDecimal.valueOf(codPersona));
		buscador3.setFlgActivo((short) 1);
		List<EvaluacionDesempenoExt> lstEvsDedesempenno = ComunicacionServiciosHV.getEvDesempenno(buscador3);
		ReconocimientoExt premio = new ReconocimientoExt();
		premio.setCodPersona(BigDecimal.valueOf(codPersona));
		premio.setFlgActivo((short) 1);
		List<ReconocimientoExt> lstPremiosReconocimientos = ComunicacionServiciosHV.getPremiosReconocimientosHV(premio);
		ParticipacionProyectoExt pp = new ParticipacionProyectoExt();
		pp.setCodPersona(BigDecimal.valueOf(codPersona));
		pp.setFlgActivo((short) 1);
		List<ParticipacionProyectoExt> lstParticipacionproyectos = ComunicacionServiciosHV.getParticipacionProyectosHV(pp);		
		ParticipacionInstitucion buscadori = new ParticipacionInstitucion();
		buscadori.setCodPersona(BigDecimal.valueOf(codPersona));
		buscadori.setFlgActivo((short) 1);
		List<ParticipacionInstitucion> lstParticipacionCorporaciones = ComunicacionServiciosHV.getParticipacionInstitucionHV(buscadori);			
		
		pesoObligatorio = new BigDecimal(80);
		pesoOpcionales= new BigDecimal(20);
		
		totalObligatoriosDatosPersonales = 22;
		totalOpcionalesDatosPersonales   = 17;
		
		totalObligatoriosEdFormacionAc   = 9;
		totalOpcionalesEdFormacionAc     = 5;
		
		totalObligatoriosEdTrabajo       = 3;
		totalOpcionalesEdTrabajo         = 5;
		
		totalObligatoriosIdioma          = 1;
		totalOpcionalesIdioma            = 5;
		
		totalObligatoriosEP              = 6;
		totalOpcionalesEP 				 = 6;
		
		totalObligatoriosED              =10;
		totalOpcionalesED                = 4;
		
		totalObligatoriosGPLogros        = 3;
		totalOpcionalesGPLogros          = 1;
		
		totalObligatoriosGPPublicaciones = 3;
		totalOpcionalesGPPublicaciones   = 3;
		
		totalObligatoriosGPEvD           = 4;
		totalOpcionalesGPEvD             = 0;
		
		totalObligatoriosGPPremios       = 3;
		totalOpcionalesGPPremios         = 2;
		
		totalObligatoriosGPParticipaProyectos = 6;
		totalOpcionalesGPParticipaProyectos   = 2;
		
		totalObligatoriosGPParticipaCorp      = 3;
		totalOpcionalesGPParticipaCorp        = 0;
		
		/*Se inicia el total obligatorios con el total obligatorios de datos personales*/
		totalObligatorios = totalObligatoriosDatosPersonales;
		/*Se inicia el total opcionales con el total opcionales de datos personales*/
		totalOpcionales   = totalOpcionalesDatosPersonales;
		
		/*Obligatorios datos básicos*/
		if(persona.getPrimerNombre()!=null && !"".equals(persona.getPrimerNombre()))
			contadorObligatoriosUsuario++;
		if(persona.getCodTipoIdentificacion()!=null && !"".equals(persona.getCodTipoIdentificacion()))
			contadorObligatoriosUsuario++;
		if(persona.getNumeroIdentificacion() !=null && !"".equals(persona.getNumeroIdentificacion()))
			contadorObligatoriosUsuario++;	
		if(persona.getFechaNacimiento() !=null )
			contadorObligatoriosUsuario++;
		if(persona.getCorreoElectronico() !=null && !"".equals(persona.getCorreoElectronico()))
			contadorObligatoriosUsuario++;			
		if(persona.getCodGenero()!=null)
			contadorObligatoriosUsuario++;
		if(persona.getUrlArchivo()!=null && !"".equals(persona.getUrlArchivo()))
			contadorObligatoriosUsuario++;
		if(persona.getCodGenero()!=null && persona.getCodGenero() ==55  ){
			if(persona.getLibretaMilitarUrl()!=null &&!"".equals(persona.getLibretaMilitarUrl()))
				contadorObligatoriosUsuario++;
			if (persona.getLibretaMilitarUrl() != null) 
				contadorObligatoriosUsuario++;
			if (persona.getDistritoMilitar() != null)
				contadorObligatoriosUsuario++;
			if (persona.getNumeroLibretaMilitar() != null)
				contadorObligatoriosUsuario++;
			if (persona.getClaseLibretaMilitar() != null) 
				contadorObligatoriosUsuario++;
		}
		/*Opcionales datos básicos*/
		if(persona.getSegundoNombre()!=null && !"".equals(persona.getSegundoNombre()))
			contadorOpcionalesUsuario++;
		if(persona.getSegundoApellido()!=null && !"".equals(persona.getSegundoApellido()))
			contadorOpcionalesUsuario++;
		if(persona.getCorreoAlternativo()!=null &&!"".equals(persona.getCorreoAlternativo()))
			contadorOpcionalesUsuario++;
		/*Obligatorios datos demograficos*/
		if(persona.getCodEstadoCivil()!=null)
			contadorObligatoriosUsuario++;
		if(persona.getCodPaisNacimiento()!=null)
			contadorObligatoriosUsuario++;
		if(persona.getCodDepartamentoNacimiento()!=null)
			contadorObligatoriosUsuario++;
		if(persona.getCodMunicipioNacimiento()!=null)
			contadorObligatoriosUsuario++;
		/*Opcionales datos demograficos*/
		if(persona.getCodPertenenciaEtnica()!=null)
			contadorOpcionalesUsuario++;
		
		if(datosContacto!=null){
			/*Datos obligatorios datos contacto*/
			if(datosContacto.getCodPaisResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodDepartamentoResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodMunicipioResidencia()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getCodTipoZona()!=null)
				contadorObligatoriosUsuario++;
			if(datosContacto.getDireccionResidencia()!=null)
				contadorObligatoriosUsuario++;
			/*Datos opcionales datos contacto*/
			if(datosContacto.getIndicativoString()!=null && !"".equals(datosContacto.getIndicativoString()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getTelefonoResidencia()!=null &&!"".equals(datosContacto.getTelefonoResidencia()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getNumCelular()!=null &&!"".equals(datosContacto.getNumCelular()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getIndicativoOficina()!=null &&!"".equals(datosContacto.getIndicativoOficina()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getNumTelefonoOficina()!=null &&!"".equals(datosContacto.getNumTelefonoOficina()))
				contadorOpcionalesUsuario++;
			if(datosContacto.getExtencionTelefonoOficina()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Datos Adicionales Obligatorios*/
		if(datosAdicionales.getCodVictimaDesplazamiento()!=null)
			contadorObligatoriosUsuario++;
		/*Datos Opcionales Obligatorios*/
		if(datosAdicionales.getCodCabezaHogar()!=null)
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getCodOrientacionSexual()!=null)
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getOtraOrientacionSexual()!=null &&!"".equals(datosAdicionales.getOtraOrientacionSexual()))
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getUrlFacebook()!=null && !"".equals(datosAdicionales.getUrlFacebook()))
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getUrlLinkedin()!=null && !"".equals(datosAdicionales.getUrlLinkedin()))
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getUrlInstagram()!=null && !"".equals(datosAdicionales.getUrlInstagram()))
			contadorOpcionalesUsuario++;
		if(datosAdicionales.getUrlTwitter()!=null && !"".equals(datosAdicionales.getUrlTwitter()))
			contadorOpcionalesUsuario++;
		
		/*Educación Formal*/
		for(EducacionFormalExt ed: listaEducacionFormalPorPersona){
			/*Al total obligatorios se suma el total obligatorios de educacion formal*/
			totalObligatorios = totalObligatorios + totalObligatoriosEdFormacionAc;
			/*Al total opcionales  se suma el total opcionales de educacion formal*/
			totalOpcionales = totalOpcionales + totalOpcionalesEdFormacionAc;
			/*se suman los datos completados*/
			if(ed.getCodNivelEducativo()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodNivelFormacion()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodInstitucionEducativa()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodProgramaAcademico()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodTituloObtenido()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getFechaFinalizacion()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getFechaGrado()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getUrlTarjetaProfesional()!=null &&!"".equals(ed.getUrlTarjetaProfesional()))
				contadorObligatoriosUsuario++;
			
			if(ed.getCodAreaConocimiento()!=null)
				contadorOpcionalesUsuario++;
			if(ed.getSemestresAprobado()!=null)
				contadorOpcionalesUsuario++;
			if(ed.getFechaConvalidacionEstudio()!=null)
				contadorOpcionalesUsuario++;
			if(ed.getNumTarjetaProfesional()!=null &&!"".equals(ed.getNumTarjetaProfesional()))
				contadorOpcionalesUsuario++;
			if(ed.getUrlAnexo()!=null && !"".equals(ed.getUrlAnexo()))
				contadorOpcionalesUsuario++;
		}
		/*otros Conocimientos*/
		for(OtroConocimientoExt oc:listaOtroConocimientoPorPersona){
			/*Al total obligatorios se suma el total obligatorios de otros conocimientos*/
			totalObligatorios = totalObligatorios + totalObligatoriosEdTrabajo;
			/*Al total opcionales  se suma el total opcionales de otros conocimientos*/
			totalOpcionales = totalOpcionales + totalOpcionalesEdTrabajo;	
			if(oc.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(oc.getNombreCurso()!=null &&!"".equals(oc.getNombreCurso()))
				contadorObligatoriosUsuario++;
			if(oc.getInstitucionFormacion()!=null &&!"".equals(oc.getInstitucionFormacion()))
				contadorObligatoriosUsuario++;
			
			if(oc.getFechaFinalizacion()!=null)
				contadorOpcionalesUsuario++;
			if(oc.getTotHoras()!=null)
				contadorOpcionalesUsuario++;
			if(oc.getCodMedioCapacitacion()!=null)
				contadorOpcionalesUsuario++;
			if(oc.getCodModalidad()!=null)
				contadorOpcionalesUsuario++;
			if(oc.getUrlDocumentoSoporte()!=null &&!"".equals(oc.getUrlDocumentoSoporte()))
				contadorOpcionalesUsuario++;
		}
		/*Idiomas*/
		for(IdiomaPersonaExt idioma:listaIdiomasPorPersona){
			/*Al total obligatorios se suma el total obligatorios de idiomas*/
			totalObligatorios = totalObligatorios + totalObligatoriosIdioma;
			/*Al total opcionales  se suma el total opcionales de idiomas*/
			totalOpcionales = totalOpcionales + totalOpcionalesIdioma;	
			if(idioma.getCodIdioma()!=null)
				contadorObligatoriosUsuario++;
			if(idioma.getFechaCertificacion()!=null)
				contadorOpcionalesUsuario++;
			if(idioma.getCodNivelConversacion()!=null)
				contadorOpcionalesUsuario++;
			if(idioma.getCodNivelEscritura()!=null)
				contadorOpcionalesUsuario++;
			if(idioma.getCodNivelLectura()!=null)
				contadorOpcionalesUsuario++;
			if(idioma.getUrlCertificacion()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Experiencia Profesional*/
		
		for(ExperienciaProfesionalExt ep: listExperienciaProfesionalExt){
			/*Al total obligatorios se suma el total obligatorios de experiencia profesional*/
			totalObligatorios = totalObligatorios + totalObligatoriosEP;
			/*Al total opcionales  se suma el total opcionales de experiencia profesional*/
			totalOpcionales = totalOpcionales + totalOpcionalesEP;
			if(ep.getCodTipoEntidad()!=null)
				contadorObligatoriosUsuario++;
			if(ep.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(ep.getCodDepartamento()!=null)
				contadorObligatoriosUsuario++;
			if(ep.getDireccionEntidad()!=null)
				contadorObligatoriosUsuario++;
			if(ep.getFechaIngreso()!=null)
				contadorObligatoriosUsuario++;
			if(ep.getDependencia()!=null && !"".equals(ep.getDependencia()))
				contadorObligatoriosUsuario++;
			
			if(ep.getIndicativoTelefono()!=null && !"".equals(ep.getIndicativoTelefono()))
				contadorOpcionalesUsuario++;
			if(ep.getTelefono()!=null &&!"".equals(ep.getTelefono()))
				contadorOpcionalesUsuario++;
			if(ep.getCodJornadaLaboral()!=null)
				contadorOpcionalesUsuario++;
			if(ep.getFechaRetiro()!=null)
				contadorOpcionalesUsuario++;
			if(ep.getCodMotivoRetiro()!=null)
				contadorOpcionalesUsuario++;
			if(ep.getUrlDocumentoSoporte()!=null &&!"".equals(ep.getUrlDocumentoSoporte()))
				contadorOpcionalesUsuario++;
		}
		
		/*Experiencia Docente*/
		
		for(ExperienciaDocenteExt ed:listExperienciaDocenteExt){
			/*Al total obligatorios se suma el total obligatorios de experiencia profesional*/
			totalObligatorios = totalObligatorios + totalObligatoriosED;
			/*Al total opcionales  se suma el total opcionales de experiencia profesional*/
			totalOpcionales = totalOpcionales + totalOpcionalesED;
			if(ed.getNombreInstitucion()!=null && !"".equals(ed.getNombreInstitucion()))
				contadorObligatoriosUsuario++;
			if(ed.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodDepartamento()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodCiudad()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodNivelEducativo()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodAreaConocimiento()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getFechaIngreso()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getCodTipoZona()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getDireccion()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getHorasSemana()!=null)
				contadorObligatoriosUsuario++;
			
			if(ed.getFechaFinalizacion()!=null)
				contadorOpcionalesUsuario++;
			if(ed.getTelefono()!=null &&!"".equals(ed.getTelefono()))
				contadorOpcionalesUsuario++;	
			if(ed.getMateriaImpartida()!=null &&!"".equals(ed.getMateriaImpartida()))
				contadorOpcionalesUsuario++;
			if(ed.getUrlDocumentoSoporte()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Gerencia Pública Logros*/
		for(LogroRecurso lr:lstLogrosYRecursos){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPLogros;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPLogros;	
			if(lr.getLogroSobresaliente()!=null && !"".equals(lr.getLogroSobresaliente()))
				contadorObligatoriosUsuario++;
			if(lr.getNumEmpleados()!=null)
				contadorObligatoriosUsuario++;
			if(lr.getNumPersonasCargo()!=null)
				contadorObligatoriosUsuario++;
			if(lr.getValorRecursoEconomico()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Gerencia Publica Publicaciones*/

		for(PublicacionExt p:lstPublicaciones){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPPublicaciones;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPPublicaciones;
			if(p.getNombreArticulo()!=null && !"".equals(p.getNombreArticulo()))
				contadorObligatoriosUsuario++;
			if(p.getNombreLibro()!=null && !"".equals(p.getNombreLibro()))
				contadorObligatoriosUsuario++;
			if(p.getNombrePublicacion()!=null && !"".equals(p.getNombrePublicacion()))
					contadorObligatoriosUsuario++;
			if(p.getCodTipoArticulo()!=null)
				contadorOpcionalesUsuario++;
			if(p.getCodTipoPublicacion()!=null)
				contadorOpcionalesUsuario++;
			if(p.getCodOtroTipoPublicacion()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Gerencia Publica Evs Desempeño*/

		for(EvaluacionDesempenoExt ed:lstEvsDedesempenno){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPEvD;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPEvD;
			if(ed.getResultadoEvaluacion()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getEscalaEvaluacion()!=null &&!"".equals(ed.getEscalaEvaluacion()))
				contadorObligatoriosUsuario++;
			if(ed.getFechaInicioEvaluacion()!=null)
				contadorObligatoriosUsuario++;
			if(ed.getFechaFinEvaluacion()!=null)
				contadorObligatoriosUsuario++;
		}
		/*Gerencia Publica premios*/

		for(ReconocimientoExt r:lstPremiosReconocimientos){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPPremios;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPPremios;
			if(r.getNombreEntidad()!=null &&!"".equals(r.getNombreEntidad()))
				contadorObligatoriosUsuario++;
			if(r.getFechaReconocimiento()!=null)
				contadorObligatoriosUsuario++;
			if(r.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(r.getCodDepartamento()!=null)
				contadorOpcionalesUsuario++;
			if(r.getCodMunicipio()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Gerencia publica participacion proyectos*/

		for(ParticipacionProyectoExt parti:lstParticipacionproyectos){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPParticipaProyectos;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPParticipaProyectos;	
			if(parti.getNombreProyecto()!=null && !"".equals(parti.getNombreProyecto()))
				contadorObligatoriosUsuario++;
			if(parti.getRolLaborado()!=null && !"".equals(parti.getRolLaborado()))
				contadorObligatoriosUsuario++;
			if(parti.getNombreEntidad()!=null &&!"".equals(parti.getNombreEntidad()))
				contadorObligatoriosUsuario++;
			if(parti.getCodPais()!=null)
				contadorObligatoriosUsuario++;
			if(parti.getFechaInicio()!=null)
				contadorObligatoriosUsuario++;
			if(parti.getFechaTerminacion()!=null)
				contadorObligatoriosUsuario++;
			if(parti.getCodDepartamento()!=null)
				contadorOpcionalesUsuario++;
			if(parti.getCodMunicipio()!=null)
				contadorOpcionalesUsuario++;
		}
		/*Gerencia Publica participacion corporaciones*/

		for(ParticipacionInstitucion pi:lstParticipacionCorporaciones){
			/*Al total obligatorios se suma el total obligatorios de GP - Logros*/
			totalObligatorios = totalObligatorios + totalObligatoriosGPParticipaCorp;
			/*Al total opcionales  se suma el total opcionales de GP - Logros*/
			totalOpcionales = totalOpcionales + totalOpcionalesGPParticipaCorp;	
			if(pi.getNombreEntidadOrganizacion()!=null && !"".equals(pi.getNombreEntidadOrganizacion()))
				contadorObligatoriosUsuario++;
			if(pi.getNombreRazonSocialInstitucion()!=null && !"".equals(pi.getNombreRazonSocialInstitucion()))
				contadorObligatoriosUsuario++;		
			if(pi.getNombreInstitucion()!=null && !"".equals(pi.getNombreInstitucion()))
				contadorObligatoriosUsuario++;				
		}
		sumaFormulaObligatorios = (new BigDecimal(contadorObligatoriosUsuario).divide(new BigDecimal(totalObligatorios),2, RoundingMode.HALF_UP));
		sumaFormulaObligatorios = sumaFormulaObligatorios.multiply(pesoObligatorio.divide(new BigDecimal(100)));
		sumaFormulaOpcionales   = (new BigDecimal(contadorOpcionalesUsuario).divide(new BigDecimal(totalOpcionales),2, RoundingMode.HALF_UP));
		sumaFormulaOpcionales   = sumaFormulaOpcionales.multiply(pesoOpcionales.divide(new BigDecimal(100)));
		totalProgeso = sumaFormulaObligatorios.add(sumaFormulaOpcionales);
		totalProgeso = totalProgeso.round(new MathContext(2, RoundingMode.HALF_UP));
		totalProgeso = totalProgeso.multiply(new BigDecimal(100));
		progresoHojadeVida = totalProgeso.intValue();
	}	

	/**
	 * @return the rol
	 */
	public long getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(long rol) {
		this.rol = rol;
	}

	/**
	 * @return the rolaprovado
	 */
	public boolean isRolaprovado() {
		return rolaprovado;
	}

	/**
	 * @param rolaprovado
	 *            the rolaprovado to set
	 */
	public void setRolaprovado(boolean rolaprovado) {
		this.rolaprovado = rolaprovado;
	}

	/**
	 * @return the mensajeini
	 */
	public String getMensajeini() {
		return mensajeini;
	}

	/**
	 * @param mensajeini
	 *            the mensajeini to set
	 */
	public void setMensajeini(String mensajeini) {
		this.mensajeini = mensajeini;
	}

	public void descargarCertificado() {
		try {
			List<CertificadoInformacionActual> listaCertificado = new ArrayList<>();
			listaCertificado.add(certificadoInformacion);

			String relativePath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/descargarcertificadoempleadojrxml.jasper");
			File filePDF = new File(relativePath);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaCertificado, false);
			JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=Certificado de Información Actual SIGEP-"
					+ DateUtils.formatearACadena(new Date(), "ddMMyyyy") + ".pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().renderResponse();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			logger.error("JRException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	public void imprimirCertificado() {
		try {

			List<CertificadoInformacionActual> listaCertificado = new ArrayList<>();
			listaCertificado.add(certificadoInformacion);

			String relativePath = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/jasper/descargarcertificadoempleadojrxml.jasper");
			File filePDF = new File(relativePath);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(listaCertificado, false);
			JasperPrint jPrint = JasperFillManager.fillReport(filePDF.getPath(), null, source);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            /*response.reset();*/
            response.setContentType("application/pdf");			
			response.addHeader("Content-disposition", "inline; filename=" + "certificadoInformacion" + ".pdf");
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().renderResponse();
			FacesContext.getCurrentInstance().responseComplete();

		} catch (JRException e) {
			logger.error("JRException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	public void configuracionParametrosCertificado() {
		if(persona!=null) {
			Parametrica logo = ComunicacionServiciosSis.getParametricaIntetos(CERTIFICADO_INF_IMG_LOGO);
			Parametrica background = ComunicacionServiciosSis.getParametricaIntetos(CERTIFICADO_INF_IMG_BACKGROUND);
			footer = ComunicacionServiciosSis.getParametricaIntetos(CERTIFICADO_INF_IMG_FOOTER);

			EntidadDTO entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
			List<ContratoExt> lstContratos = new ArrayList<ContratoExt>();
			List<VinculacionExt> lstVinculacionesActivas;
			VinculacionExt vinculacionActual = null;

			VinculacionExt objFilter = new VinculacionExt();
			objFilter.setCodPersona(persona.getCodPersona());
			objFilter.setFlgActivo((short)1);
			objFilter.setCodEntidad(entidadUsuario.getId());
			lstVinculacionesActivas = ComunicacionServiciosVin.getvinculacionactual(objFilter);
			Contrato contratoFilter = new Contrato();
			contratoFilter.setCodPersona(persona.getCodPersona());
			contratoFilter.setCodEntidad(entidadUsuario.getId());
			contratoFilter.setFlgActivo((short) 1);
			contratoFilter.setLimitEnd(2000);
			lstContratos = ComunicacionServiciosCO.getContratoFiltro(contratoFilter);
			Date   ldfechaInicio;
			String strContenido="", strVinculacion="", strContratos="",strCargo,strGrado = "", strPie,strEncabezadoContratos, strTextoContrato;
			strPie = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_PIE, getLocale());

			if(!lstVinculacionesActivas.isEmpty() || !lstContratos.isEmpty()){
				strContenido = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_ENCABEZADO, getLocale());
				if(!lstVinculacionesActivas.isEmpty() && lstContratos.isEmpty()){/*Sólo vinculación*/
					vinculacionActual = lstVinculacionesActivas.get(0);
					strVinculacion =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_VINCULACION, getLocale());
					strContenido = strContenido.concat(" ").concat(strVinculacion);
				}else if(lstVinculacionesActivas.isEmpty() && !lstContratos.isEmpty()){/*Sólo contratos*/
					strEncabezadoContratos =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_SOLO_CONTRATOS, getLocale());
					strContratos = strContratos.concat(strEncabezadoContratos);
					for (ContratoExt contratoExt : lstContratos) {
						if (contratoExt.getFlgActivo()!=null && contratoExt.getFlgActivo() == 1) {
							strTextoContrato = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_TEXTO_CONTRATOS, getLocale());
							strTextoContrato = new String("\n").concat(strTextoContrato);
							strTextoContrato = strTextoContrato.replace("%numerocontrato%", contratoExt.getNumeroContrato())
									.replace("%objetocontrato%", contratoExt.getObjetoContrato())
									.replace("%entidadcontrato%", entidadUsuario.getNombreEntidad());

							strContratos = strContratos + new String(strTextoContrato);
						}
					}
					strContenido= strContenido.concat(strContratos);
				}else if(!lstVinculacionesActivas.isEmpty() && !lstContratos.isEmpty()){/*vinculación y contratos*/
					strEncabezadoContratos =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_CONTRATOS_MAS_VINCULACION, getLocale());
					vinculacionActual = lstVinculacionesActivas.get(0);
					strVinculacion =  MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_VINCULACION, getLocale());
					strContenido = strContenido.concat(" ").concat(strVinculacion);
					strContratos = strContratos.concat(new String("\n").concat(new String(strEncabezadoContratos)));
					for (ContratoExt contratoExt : lstContratos) {
						if (contratoExt.getFlgActivo()!=null && contratoExt.getFlgActivo() == 1) {
							strTextoContrato = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_TEXTO_CONTRATOS, getLocale());
							strTextoContrato = new String("\n").concat(strTextoContrato);
							strTextoContrato = strTextoContrato.replace("%numerocontrato%", contratoExt.getNumeroContrato())
									.replace("%objetocontrato%", contratoExt.getObjetoContrato())
									.replace("%entidadcontrato%", entidadUsuario.getNombreEntidad());

							strContratos = strContratos + new String(strTextoContrato);
						}
					}	
					strContenido = strContenido.concat(strContratos);
				}
			}else{/*sin vinculación y sin contratos*/
				strContenido = MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_INFO_CERTIFICADO_INFORMACION_SIN_VINCULACION, getLocale());
			}
			TipoDocumentoDTO documento;
			try {
				documento = AdministracionDelegate.findTipoDocumentoId(Long.parseLong(persona.getCodTipoIdentificacion()));
				persona.setNombreTipoDocuento(documento.getSigla());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SIGEP2SistemaException e) {
				e.printStackTrace();
			}
			if(vinculacionActual!=null && vinculacionActual.getNombreCargo()!=null && !"".equals(vinculacionActual.getNombreCargo())){
				strCargo = vinculacionActual.getNombreCargo();
			}else{
				strCargo = usuarioData!=null&&usuarioData.getTipoAsociacion()!=null?usuarioData.getTipoAsociacion():"";
			}
			if(vinculacionActual!=null && vinculacionActual.getFechaPosesion()!=null){
				ldfechaInicio = vinculacionActual.getFechaPosesion();
			}else{
				ldfechaInicio = usuarioData.getFechaActivacion();
			}
			if(vinculacionActual!=null && vinculacionActual.getNombreGrado()!=null){
				strGrado = vinculacionActual.getNombreGrado();
			}

			strContenido = strContenido.concat("\n").concat(strPie);
			strContenido = strContenido
					.replace("%tipo%", persona.getNombreTipoDocuento())
					.replace("%numero%", persona.getNumeroIdentificacion())
					.replace("%nombreusuario%", persona.getNombreUsuario())
					.replace("%entidad%",usuarioData!=null&&usuarioData.getNombreEntidad()!=null? usuarioData.getNombreEntidad():"")
					.replace("%cargo%",strCargo)
					.replace("%gradocargo%", strGrado)
					.replace("%fechaActivacion%",DateUtils.formatearACadena(ldfechaInicio, "dd/MM/yyyy") )
					.replace("%diasexpedicion%",  DateUtils.formatearACadena(new Date(), "d"))
					.replace("%mesexpedicion%", DateUtils.formatearACadena(new Date(), "MMMM"))
					.replace("%añoexpedicion%", DateUtils.formatearACadena(new Date(), "yyyy"))
					.replace("%horaexpedicion%", DateUtils.formatearACadena(new Date(), "HH:mm"));
			certificadoInformacion.setUrlLogo(logo.getValorParametro());
			certificadoInformacion.setUrlBackground(background.getValorParametro());
			certificadoInformacion.setUrlFooter(footer.getValorParametro());
			certificadoInformacion.setTitulo(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.LBL_CERTIFICADO_TITULO, getLocale()));
			certificadoInformacion.setContenido(strContenido);
			contenidoText = certificadoInformacion.getContenido().replace("\n", "<br />");
		}


	}

	
}