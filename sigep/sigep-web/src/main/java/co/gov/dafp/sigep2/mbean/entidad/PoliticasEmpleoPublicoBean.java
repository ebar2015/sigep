package co.gov.dafp.sigep2.mbean.entidad;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import com.google.gson.Gson;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.PepCuestionario;
import co.gov.dafp.sigep2.entities.PepPreguntasCuestionario;
import co.gov.dafp.sigep2.entities.PepPreguntasCuestionarioDetalle;
import co.gov.dafp.sigep2.entities.PepRespuestaCuestionario;
import co.gov.dafp.sigep2.entities.PepRespuestaCuestionarioDetalle;
import co.gov.dafp.sigep2.entities.PepRespuestasPreguntaCuestionario;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.PepCuestionarioExt;
import co.gov.dafp.sigep2.mbean.ext.PepPreguntasCuestionarioDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.PepPreguntasCuestionarioExt;
import co.gov.dafp.sigep2.rest.ConnectionHttp;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.TipoParametro;
import co.gov.dafp.sigep2.sistema.WebUtils;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.utils.ErrorMensajes;

@Named
@ViewScoped
@ManagedBean

public class PoliticasEmpleoPublicoBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> lstTiposCuestionarios;
	private List<PepPreguntasCuestionarioExt> lstpreguntasCuestionarios;
	private List<PepCuestionarioExt> lstCuestionarios;
	private String strEncabezadoPregunta, dsRespuesta03, dsRespuesta0405, dsRespuesta06;
	private PepRespuestaCuestionario respuestaCuestionario;
	private List<PepPreguntasCuestionarioDetalleExt> lstpreguntasCuestionariosDetalle;
	PepPreguntasCuestionarioDetalleExt selectedRespuestaUnica;
	private List<PepPreguntasCuestionarioDetalleExt> lstselectedRespuestaMultiple;
	private Long idCuestionario;
	Integer codAudCodRol, codAudAccionInsert = 3, codAudAccionUpdate = 2, codAudAccionDelete = 1;
	private Integer liRespuesta03, idEntidadSelect;
	private static final Logger logger = Logger.getInstance(PoliticasEmpleoPublicoBean.class);

	private boolean lbtipopregunta01, lbtipopregunta02, lbtipopregunta03, lbtipopregunta0405, lbtipopregunta06,
			lbtipopregunta07, lbtipopregunta08, lbRespuesta03;
	private EntidadDTO entidadUsuario = new EntidadDTO();
	private UsuarioDTO usuarioSesion = new UsuarioDTO();

	PepPreguntasCuestionarioExt preguntaSelected;

	private boolean lbAdminFUNCPolEmpleo;
	private boolean lbRolesPermitidos;
	private UploadedFile cargarDodumento = null;
	private String dsNombreArchivo;
	private long dsTamanoParametro;
	private boolean lbHabilitarCuestionario;
	private boolean lbHabilitarEntidad;
	private String nombreArchivoDocumento;
	private Integer pocentajeCargosMujeres; // Variable que almacena el valor en
											// porcentaje de a cantidad de altos
											// cargos ejercidos por mujeres
	private static final int PORCENTAJE_LEY_CUOTAS_DEFECTO = 30;

	private boolean lbVerDetalleRespuestaOtros;
	private String strDetalleRespuestaOtros;

	public boolean isLbVerDetalleRespuestaOtros() {
		return lbVerDetalleRespuestaOtros;
	}

	public void setLbVerDetalleRespuestaOtros(boolean lbVerDetalleRespuestaOtros) {
		this.lbVerDetalleRespuestaOtros = lbVerDetalleRespuestaOtros;
	}

	public String getStrDetalleRespuestaOtros() {
		return strDetalleRespuestaOtros;
	}

	public void setStrDetalleRespuestaOtros(String strDetalleRespuestaOtros) {
		this.strDetalleRespuestaOtros = strDetalleRespuestaOtros;
	}

	public String getNombreArchivoDocumento() {
		return nombreArchivoDocumento;
	}

	public void setNombreArchivoDocumento(String nombreArchivoDocumento) {
		this.nombreArchivoDocumento = nombreArchivoDocumento;
	}

	public long getDsTamanoParametro() {
		return dsTamanoParametro;
	}

	public void setDsTamanoParametro(long dsTamanoParametro) {
		this.dsTamanoParametro = dsTamanoParametro;
	}

	private Long tamPdf = (long) 400000;

	public PepPreguntasCuestionarioExt getPreguntaSelected() {
		return preguntaSelected;
	}

	public void setPreguntaSelected(PepPreguntasCuestionarioExt preguntaSelected) {
		this.preguntaSelected = preguntaSelected;
	}

	public boolean isLbAdminFUNCPolEmpleo() {
		return lbAdminFUNCPolEmpleo;
	}

	public void setLbAdminFUNCPolEmpleo(boolean lbAdminFUNCPolEmpleo) {
		this.lbAdminFUNCPolEmpleo = lbAdminFUNCPolEmpleo;
	}

	public boolean isLbUsuariosPolEmpleo() {
		return lbUsuariosPolEmpleo;
	}

	public void setLbUsuariosPolEmpleo(boolean lbUsuariosPolEmpleo) {
		this.lbUsuariosPolEmpleo = lbUsuariosPolEmpleo;
	}

	private boolean lbUsuariosPolEmpleo;
	private List<SelectItem> lstEntidadesRol;

	/**
	 * @author {Natalia Montoya}
	 * @version {??}
	 * @throws SIGEP2SistemaException
	 * @Class {Permite el guardado del cuestionario
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */

	public void guardar() throws SIGEP2SistemaException {
		PepRespuestasPreguntaCuestionario respuesta;
		respuestaCuestionario = new PepRespuestaCuestionario();
		respuestaCuestionario.setAudAccion(codAudAccionInsert);
		respuestaCuestionario.setAudCodRol(codAudCodRol);
		respuestaCuestionario.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
		respuestaCuestionario.setAudFechaActualizacion(new Date());

		// Se diligencia la encuenta a nombre de la entidad segun el rol del
		// usuario en sesion
		if (idEntidadSelect != null && usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL)) {
			respuestaCuestionario.setCodEntidad(new BigDecimal(idEntidadSelect.longValue()));
		} else {
			respuestaCuestionario.setCodEntidad(new BigDecimal(entidadUsuario.getId()));
		}

		respuestaCuestionario.setFlgActivo((short) 1);
		respuestaCuestionario.setIdTipoCuestionario(idCuestionario);
		PepRespuestaCuestionario res = ComunicacionServiciosEnt.setRespuestaCuentionario(respuestaCuestionario);

		if (lstpreguntasCuestionarios != null) {
			for (PepPreguntasCuestionarioExt pregunta : lstpreguntasCuestionarios) {
				System.out.println(pregunta.getDsPregunta());
				respuesta = new PepRespuestasPreguntaCuestionario();
				respuesta.setIdRespuestaCuestionario(res.getIdRespuestaCuestionario());
				respuesta.setIdPreguntaCuestionario(pregunta.getIdPreguntaCuestionario());
				respuesta.setDsRespuesta(pregunta.getDsRespuesta());
				respuesta.setAudAccion(codAudAccionInsert);
				respuesta.setAudCodRol(codAudCodRol);
				respuesta.setAudCodUsuario(BigDecimal.valueOf(usuarioSesion.getId()));
				respuesta.setAudFechaActualizacion(new Date());
				PepRespuestasPreguntaCuestionario resPregunta = ComunicacionServiciosEnt
						.setPepRespuestasPreguntaCuestionario(respuesta);
				if (pregunta.getIdTipoPregunta() == 2 || pregunta.getIdTipoPregunta() == 4
						|| pregunta.getIdTipoPregunta() == 5 || pregunta.getIdTipoPregunta() == 6
						|| pregunta.getIdTipoPregunta() == 7 || pregunta.getIdTipoPregunta() == 8
						|| pregunta.getIdTipoPregunta() == 9 || pregunta.getIdTipoPregunta() == 13
						|| pregunta.getIdTipoPregunta() == 14) {
					if (pregunta.getLstRespuestaDetalle() != null && pregunta.getLstRespuestaDetalle().size() > 0) {
						for (PepRespuestaCuestionarioDetalle resDetalle : pregunta.getLstRespuestaDetalle()) {
							resDetalle.setIdRespuestaPreguntaCuestionario(
									resPregunta.getIdRespuestaPreguntaCuestionario());
							resDetalle.setAudAccion(codAudAccionInsert);
							resDetalle.setAudCodRol(Integer.valueOf(String.valueOf(getRolAuditoria().getId())));
							resDetalle.setAudCodUsuario(BigDecimal.valueOf(getUsuarioSesion().getId()));
							resDetalle.setAudFechaActualizacion(new Date());
							resDetalle.setFlgActivo((short) 1);
							ComunicacionServiciosEnt.setPepRespuestaCuestionarioDetalle(resDetalle);
						}
					}
				}

			}
		}

		/*
		 * Para el dialogo emerGente cuando se guarda la encuesta exitosamente
		 */
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.execute("PF('dlgRespuestaGuardada').show();");

		lstpreguntasCuestionariosDetalle = null;
		lstpreguntasCuestionarios = null;
		idEntidadSelect = null;
		idCuestionario = null;
		lbHabilitarEntidad = false;
		lbHabilitarCuestionario = false;
		lbtipopregunta02 = false;
		lbtipopregunta06 = false;
		lbtipopregunta08 = false;
		lbtipopregunta08 = false;

		RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
	}

	public void ValidarLeyCuotas() {
		int sumatoriaDesisorios = 0;
		int sumatoriaOtrosDesisorios = 0;
		int totalDesisorios = 0;
		int totalOtrosDesisorios = 0;
		int totalDirectivos = 0;
		double sumaHombresMujereres = 0;
		double mujeres = 0;
		double division = 0;
		double porcentaje = 0;
		if (idCuestionario != null) {
			PepCuestionario objeto = new PepCuestionario();
			objeto.setIdCuestionario(idCuestionario.longValue());
			PepCuestionario infoCuestionario = ComunicacionServiciosEnt.getCuestionarioById(objeto);
			if (infoCuestionario != null && infoCuestionario.getIdParametricaCuestionario() != null && infoCuestionario
					.getIdParametricaCuestionario().intValue() == TipoParametro.PEP_LEY_CUOTAS.getValue()) {

				if (lstpreguntasCuestionarios != null) {
					for (int i = 0; i < lstpreguntasCuestionarios.size(); i++) {
						if (lstpreguntasCuestionarios.get(i).getDsRespuesta() == null) {
							mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
									MessagesBundleConstants.DLG_LLENAR_CAMPOS);
							return;
						} else {
							int rta = Integer.parseInt(lstpreguntasCuestionarios.get(i).getDsRespuesta());
							if (lstpreguntasCuestionarios.get(i).getDsRespuesta() != null && rta >= 0) {
								if (i == 0) {
									totalDirectivos += rta;
								}
								if (i == 1) {
									totalDesisorios += rta;
								}
								if (i == 3) {
									mujeres = rta;
								}

								if (i == 2 || i == 3 || i == 4) {
									sumatoriaDesisorios += rta;
								}

								if (i == 3 || i == 4) {
									sumaHombresMujereres += rta;
								}

								if (i == 6 || i == 7 || i == 8) {
									sumatoriaOtrosDesisorios += rta;
								}

								if (i == 5) {
									totalOtrosDesisorios += rta;
								}

							} else {
								mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
										MessagesBundleConstants.DLG_LLENAR_CAMPOS);
								return;
							}

						}

					}
					if (sumatoriaDesisorios != totalDesisorios) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_SUM_VACANTES_MH_NO_IGUAL_TOTALD);
						return;
					}
					if (sumatoriaOtrosDesisorios != totalOtrosDesisorios) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_SUM_VACANTES_MH_NO_IGUAL_OTROSD);
						return;
					}

					if ((totalDesisorios + totalOtrosDesisorios) != totalDirectivos) {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_SUM_DECISORIO_DIFERENTE_DIRECTIVOS);
						return;
					}
					division = (mujeres / sumaHombresMujereres);
					porcentaje = division * 100;
					if (porcentaje > pocentajeCargosMujeres) {
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_DATOS_EMPLEO_PUBLICO_LEY_CUOTAS);
					} else {
						mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.MSG_DATOS_EMPLEO_PUBLICO_NO_LEY_CUOTAS);
					}
				}
			}

		}
		try {
			guardar();
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * @author {Natalia Montoya}
	 * @version {??}
	 * @Class {Para guardar las respuestas que tiene detalle otra respuestas.
	 *        Tipo de pregunta 2 corresponde a las preguntas que tienen cajas de
	 *        texto para responder Tipo de pregunta 4 corresponde a las
	 *        preguntas que tienen respuesta un radio button si/no y dependiendo
	 *        de la respuesta se desprende un detalle de caja de texto}
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */

	public void guardarDetalleRespuesta() {
		PepRespuestaCuestionarioDetalle respuestadetalle;
		boolean lbFaltanRespuestas = false;
		if (preguntaSelected.getIdTipoPregunta() == 2 || preguntaSelected.getIdTipoPregunta() == 4
				|| preguntaSelected.getIdTipoPregunta() == 5 || preguntaSelected.getIdTipoPregunta() == 13) { 
			if (lstpreguntasCuestionariosDetalle != null && lstpreguntasCuestionariosDetalle.size() > 0) {
				if (preguntaSelected.getLstRespuestaDetalle() == null)
					preguntaSelected.setLstRespuestaDetalle(new ArrayList<PepRespuestaCuestionarioDetalle>());

				for (PepPreguntasCuestionarioDetalleExt preguntaLista : lstpreguntasCuestionariosDetalle) {
					if (preguntaLista.getDsDetalleRespuesta() == null
							|| preguntaLista.getDsDetalleRespuesta().equals("")) {
						lbFaltanRespuestas = true;
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_LLENAR_CAMPOS);
						return;
					}

					if (preguntaLista.getIdTipoPreguntaDetalle() == 14
							&& preguntaLista.getDsDetalleRespuesta().equals("1")
							&& preguntaLista.getDsDetalleRespuestaDetalle().equals("")) {
						lbFaltanRespuestas = true;
						mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
								MessagesBundleConstants.DLG_LLENAR_CAMPOS);
						return;
					}

					respuestadetalle = new PepRespuestaCuestionarioDetalle();
					respuestadetalle.setDsRespuestaDetalle(preguntaLista.getDsDetalleRespuesta());
					respuestadetalle.setIdPreguntaCuestionarioDetalle(preguntaLista.getIdPreguntaCuestionarioDetalle());

					if (preguntaLista.getIdTipoPreguntaDetalle() != null
							&& preguntaLista.getIdTipoPreguntaDetalle() == 14)
						respuestadetalle.setDsRespuestaDetalleDetalle(preguntaLista.getDsDetalleRespuestaDetalle());
					preguntaSelected.getLstRespuestaDetalle().add(respuestadetalle);
				}              
			}
		} else if (preguntaSelected.getIdTipoPregunta() == 3) {
			preguntaSelected.setDsRespuesta(dsRespuesta03);
		}

		else if (preguntaSelected.getIdTipoPregunta() == 6 || preguntaSelected.getIdTipoPregunta() == 7) {
			if (preguntaSelected.getLstRespuestaDetalle() == null)
				preguntaSelected.setLstRespuestaDetalle(new ArrayList<PepRespuestaCuestionarioDetalle>());
			preguntaSelected.setDsRespuesta(dsRespuesta06);
			respuestadetalle = new PepRespuestaCuestionarioDetalle();
			respuestadetalle.setDsRespuestaDetalle(selectedRespuestaUnica.getDsDetallePregunta());
			respuestadetalle
					.setIdPreguntaCuestionarioDetalle(selectedRespuestaUnica.getIdPreguntaCuestionarioDetalle());
			/* Detalle 'Otros' */
			if (selectedRespuestaUnica.getIdTipoPreguntaDetalle() != null
					&& selectedRespuestaUnica.getIdTipoPreguntaDetalle() == 13)
				respuestadetalle.setDsRespuestaDetalleDetalle(selectedRespuestaUnica.getDsDetalleRespuestaDetalle());
			preguntaSelected.getLstRespuestaDetalle().add(respuestadetalle);
		}

		else if (preguntaSelected.getIdTipoPregunta() == 8) {
			if (preguntaSelected.getLstRespuestaDetalle() == null)
				preguntaSelected.setLstRespuestaDetalle(new ArrayList<PepRespuestaCuestionarioDetalle>());
			for (PepPreguntasCuestionarioDetalleExt preguntaLista : lstselectedRespuestaMultiple) {
				respuestadetalle = new PepRespuestaCuestionarioDetalle();
				respuestadetalle.setDsRespuestaDetalle(preguntaLista.getDsDetalleRespuesta());
				respuestadetalle.setIdPreguntaCuestionarioDetalle(preguntaLista.getIdPreguntaCuestionarioDetalle());
				preguntaSelected.getLstRespuestaDetalle().add(respuestadetalle);
			}
		}

		mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
				MessagesBundleConstants.DLG_GUARDADA_RTA);
		return;

	}

	/**
	 * @author {Natalia Montoya}
	 * @version {??}
	 * @Class { Consulta las preguntas relacionadas con cada cuestionario}
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */
	public void consultarPreguntasCuestionario() {
		PepPreguntasCuestionario buscador = new PepPreguntasCuestionario();
		buscador.setIdCuestionario(idCuestionario.longValue());
		lstpreguntasCuestionarios = ComunicacionServiciosEnt.getPreguntasCuestionarioFiltro(buscador);
		lbHabilitarEntidad = true;
		lbHabilitarCuestionario = true;
		validarTotalCargosDirectivosEntidad();
	}

	/**
	 * @author {Natalia Montoya}
	 * @version {??}
	 * @Class { Consulta las preguntas relacionadas con cada cuestionario
	 *        llevando las condiciones: Primera de que tipo es -
	 *        PepPreguntasCuestionario Segunda La variable que recibe del
	 *        formulario - pregunta // Es de tipo lista o vector
	 *        PepPreguntasCuestionario}
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */

	public void consultarDetallePreguntas(PepPreguntasCuestionarioExt pregunta) {
		preguntaSelected = pregunta;
		System.out.println("Ingreso a Metodo Consultar consultarDetallePreguntas");
		strEncabezadoPregunta = "Favor Contestar el detalle para la pregunta: " + pregunta.getDsPregunta();
		if (pregunta.getIdTipoPregunta() == 2) {
			lbtipopregunta01 = false;
			lbtipopregunta02 = true;
			lbtipopregunta03 = false;
			lbtipopregunta0405 = false;
			lbtipopregunta06 = false;
			lbtipopregunta07 = false;
			lbtipopregunta08 = false;
			PepPreguntasCuestionarioDetalle buscador = new PepPreguntasCuestionarioDetalle();
			buscador.setIdPreguntaCuestionario(pregunta.getIdPreguntaCuestionario());
			lstpreguntasCuestionariosDetalle = ComunicacionServiciosEnt.getPreguntasCuestionarioDetFiltro(buscador);
		} else if (pregunta.getIdTipoPregunta() == 3) {
			lbtipopregunta01 = false;
			lbtipopregunta02 = false;
			lbtipopregunta03 = true;
			lbtipopregunta0405 = false;
			lbtipopregunta06 = false;
			lbtipopregunta07 = false;
			lbtipopregunta08 = false;
		} else if (pregunta.getIdTipoPregunta() == 4 || pregunta.getIdTipoPregunta() == 5) {
			lbtipopregunta01 = false;
			lbtipopregunta02 = false;
			lbtipopregunta03 = false;
			lbtipopregunta0405 = true;
			lbtipopregunta06 = false;
			lbtipopregunta07 = false;
			lbtipopregunta08 = false;
		} else if (pregunta.getIdTipoPregunta() == 6 || pregunta.getIdTipoPregunta() == 7) {
			lbtipopregunta01 = false;
			lbtipopregunta02 = false;
			lbtipopregunta03 = false;
			lbtipopregunta0405 = false;
			lbtipopregunta06 = true;
			lbtipopregunta07 = false;
			lbtipopregunta08 = false;
			PepPreguntasCuestionarioDetalle buscador = new PepPreguntasCuestionarioDetalle();
			buscador.setIdPreguntaCuestionario(pregunta.getIdPreguntaCuestionario());
			lstpreguntasCuestionariosDetalle = ComunicacionServiciosEnt.getPreguntasCuestionarioDetFiltro(buscador);
		} else if (pregunta.getIdTipoPregunta() == 8) {
			lbtipopregunta01 = false;
			lbtipopregunta02 = false;
			lbtipopregunta03 = false;
			lbtipopregunta0405 = false;
			lbtipopregunta06 = false;
			lbtipopregunta07 = false;
			lbtipopregunta08 = true;
			PepPreguntasCuestionarioDetalle buscador = new PepPreguntasCuestionarioDetalle();
			buscador.setIdPreguntaCuestionario(pregunta.getIdPreguntaCuestionario());
			lstpreguntasCuestionariosDetalle = ComunicacionServiciosEnt.getPreguntasCuestionarioDetFiltro(buscador);
		}
	}

	/**
	 * @author {Natalia Montoya}
	 * @version {??}
	 * @Class { Consulta el detalle de la pregunta seleccionada, es decir
	 *        muestra las respuestas de las que se genera otra respuesta}
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */

	public void consultarDetallePreguntaSeleccionada(PepPreguntasCuestionarioExt pregunta) {
		preguntaSelected = pregunta;
		if ((preguntaSelected.getIdTipoPregunta() == 4 && preguntaSelected.getDsRespuesta().equals("1"))
				|| (preguntaSelected.getIdTipoPregunta() == 5 && preguntaSelected.getDsRespuesta().equals("2"))
				|| (preguntaSelected.getIdTipoPregunta() == 6) && preguntaSelected.getDsRespuesta().equals("1")) {
			PepPreguntasCuestionarioDetalle buscador = new PepPreguntasCuestionarioDetalle();
			buscador.setIdPreguntaCuestionario(preguntaSelected.getIdPreguntaCuestionario());
			lstpreguntasCuestionariosDetalle = ComunicacionServiciosEnt.getPreguntasCuestionarioDetFiltro(buscador);
			lbtipopregunta02 = true;
			RequestContext.getCurrentInstance().execute(
					"window.location.href='#frGestionarPerfuntasForm:formprueba:tblDetallePreguntasUnica_head'");
			if (preguntaSelected.getIdTipoPregunta() == 6 || pregunta.getIdTipoPregunta() == 7
					|| pregunta.getIdTipoPregunta() == 8) {
				lbtipopregunta02 = false;
				lbtipopregunta06 = true;
			}
		} else {
			lstpreguntasCuestionariosDetalle = null;
		}

	}

	/**
	 * @author {Natalia Montoya}
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {16 julio de 2018}
	 */

	public boolean isLbtipopregunta01() {
		return lbtipopregunta01;
	}

	public void setLbtipopregunta01(boolean lbtipopregunta01) {
		this.lbtipopregunta01 = lbtipopregunta01;
	}

	public boolean isLbtipopregunta02() {
		return lbtipopregunta02;
	}

	public void setLbtipopregunta02(boolean lbtipopregunta02) {
		this.lbtipopregunta02 = lbtipopregunta02;
	}

	public boolean isLbtipopregunta03() {
		return lbtipopregunta03;
	}

	public void setLbtipopregunta03(boolean lbtipopregunta03) {
		this.lbtipopregunta03 = lbtipopregunta03;
	}

	public boolean isLbtipopregunta0405() {
		return lbtipopregunta0405;
	}

	public void setLbtipopregunta0405(boolean lbtipopregunta0405) {
		this.lbtipopregunta0405 = lbtipopregunta0405;
	}

	/* Datos de tipo de pregunta 06 */
	/*
	 * True si del tipo pregunta 06, false si no corresponde al tipo de pregunta
	 * 06
	 */
	public boolean isLbtipopregunta06() {
		return lbtipopregunta06;
	}

	public void setLbtipopregunta06(boolean lbtipopregunta06) {
		this.lbtipopregunta06 = lbtipopregunta06;
	}

	public boolean isLbtipopregunta07() {
		return lbtipopregunta07;
	}

	public void setLbtipopregunta07(boolean lbtipopregunta07) {
		this.lbtipopregunta07 = lbtipopregunta07;
	}

	public boolean isLbtipopregunta08() {
		return lbtipopregunta08;
	}

	public void setLbtipopregunta08(boolean lbtipopregunta08) {
		this.lbtipopregunta08 = lbtipopregunta08;
	}

	/* Lista las preguntas del cuestionario que tiene detalle */

	public List<PepPreguntasCuestionarioDetalleExt> getLstpreguntasCuestionariosDetalle() {
		return lstpreguntasCuestionariosDetalle;
	}

	public void setLstpreguntasCuestionariosDetalle(
			List<PepPreguntasCuestionarioDetalleExt> lstpreguntasCuestionariosDetalle) {
		this.lstpreguntasCuestionariosDetalle = lstpreguntasCuestionariosDetalle;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
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

	/* Lista el tipo de cuestionario */

	public List<SelectItem> getLstTiposCuestionarios() {

		lstTiposCuestionarios = new ArrayList<SelectItem>();
		for (PepCuestionarioExt cuestionarios : lstCuestionarios) {

			lstTiposCuestionarios
					.add(new SelectItem(cuestionarios.getIdCuestionario(), cuestionarios.getNombreCuestionario()));
		}
		return lstTiposCuestionarios;
	}

	public void setLstTiposCuestionarios(List<SelectItem> lstTiposCuestionarios) {
		this.lstTiposCuestionarios = lstTiposCuestionarios;
	}

	public List<PepPreguntasCuestionarioExt> getLstpreguntasCuestionarios() {
		return lstpreguntasCuestionarios;
	}

	public void setLstpreguntasCuestionarios(List<PepPreguntasCuestionarioExt> lstpreguntasCuestionarios) {
		this.lstpreguntasCuestionarios = lstpreguntasCuestionarios;
	}

	/**
	 * @author {Natalia Montoya}
	 * @Descripción {Permite validar los usuarios que tiene permiso para
	 *              ingresar al cuestionario
	 * @Proyect {DAFP}
	 * @Company {Ada.sa}
	 * @Module {Entidades} Fecha: {18 julio de 2018}
	 */
	@PostConstruct
	public void init() {
		// TODO Auto-generated method stub
		Parametrica parametrica;
		cerrarSessionFuncion(AMBITO_POLITICAS);
		Parametrica parametricaPorcentaje = ComunicacionServiciosVin
				.getParametricaporId(new BigDecimal(TipoParametro.PORCENTAJE_LEY_CUOTAS.getValue()));
		pocentajeCargosMujeres = parametricaPorcentaje.getValorParametro() != null
				? Integer.parseInt(parametricaPorcentaje.getValorParametro()) : PORCENTAJE_LEY_CUOTAS_DEFECTO;

		try {
			lbAdminFUNCPolEmpleo = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbAdminFUNCPolEmpleo = false;
		}

		try {
			lbUsuariosPolEmpleo = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbUsuariosPolEmpleo = false;
		}

		// Roles Permitidos
		try {
			lbRolesPermitidos = this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.JEFE_TALENTO_HUMANO,
					RolDTO.OPERADOR_TALENTO_HUMANO, RolDTO.ADMINISTRADOR_FUNCIONAL);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
			lbRolesPermitidos = false;
		}

		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		codAudAccionUpdate = Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue());
		codAudAccionDelete = Integer.valueOf(TipoParametro.AUDITORIA_DELETE.getValue());
		codAudAccionInsert = Integer.valueOf(TipoParametro.AUDITORIA_INSERT.getValue());
		lbtipopregunta01 = lbtipopregunta02 = lbtipopregunta03 = lbtipopregunta0405 = lbtipopregunta06 = lbtipopregunta07 = lbtipopregunta08 = false;
		entidadUsuario = (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
		codAudCodRol = (int) this.getRolAuditoria().getId();
		usuarioSesion = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		lstCuestionarios = ComunicacionServiciosEnt.getCuestionarios();
		if (!lbRolesPermitidos) {
			mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
			return;
		}

		lbHabilitarEntidad = false;
		lbHabilitarCuestionario = false;

		/* Para cambiar el tamaño del archivo cargado */

		// this.setTamPdf((long) 400000);

		String tamano_parametrica = "TAMANO_PARAMETRICA_CUESTIONARIO";
		try {
			parametrica = ComunicacionServiciosSis.getParametricaIntetos(tamano_parametrica);
			if (parametrica != null && parametrica.getValorParametro() != null
					&& !"".equals(parametrica.getValorParametro())) {
				dsTamanoParametro = Long.valueOf(parametrica.getValorParametro());
			} else
				dsTamanoParametro = 30L;

		} catch (Exception z) {
			dsTamanoParametro = 30L;
		}
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_ENTIDADES);		

	}

	/**
	 * Para el formulario "Ley de cuotas" Indique el número total de cargos
	 * directivos de su entidad: (valor numérico, el sistema coloca por default
	 * el valor que calcula de la planta que tiene asociada la entidad, si no
	 * tiene planta creada coloca 0)
	 * 
	 * @return
	 */
	private void validarTotalCargosDirectivosEntidad() {
		// Si es ley de cuotas
		if (idCuestionario != null) {
			PepCuestionario objeto = new PepCuestionario();
			objeto.setIdCuestionario(idCuestionario.longValue());
			PepCuestionario infoCuestionario = ComunicacionServiciosEnt.getCuestionarioById(objeto);
			if (infoCuestionario != null && infoCuestionario.getIdParametricaCuestionario() != null && infoCuestionario
					.getIdParametricaCuestionario().intValue() == TipoParametro.PEP_LEY_CUOTAS.getValue()) {
				if (idEntidadSelect != null) {
					EntidadPlantaExt objFilter = new EntidadPlantaExt();
					objFilter.setCodEntidad(idEntidadSelect.longValue());
					objFilter.setFlgActivo((short) 1);
					objFilter.setFlgGuardadoParcial((short) 0);
					List<EntidadPlantaExt> list = ComunicacionServiciosVin.getEntidadPlantaFilter(objFilter);
					// Si no hay plantas creadas
					if (list.isEmpty()) {
						lstpreguntasCuestionarios.get(0).setDsRespuesta("0");
					} else {
						// Si hay planta creadas suma todos los cargos que hayan
						EntidadPlantaDetalleExt obje = new EntidadPlantaDetalleExt();
						obje.setCodEntidad(idEntidadSelect.longValue());
						obje.setCodNivelCargo((long) TipoParametro.NIVEL_JERARQUICO_DIRECTIVO.getValue());
						obje.setFlgActivo((short) 1);
						obje.setFlgGuardadoParcial((short) 0);
						EntidadPlantaDetalleExt value = ComunicacionServiciosEnt.getTotalCargosDirectivos(obje);
						if (value != null && value.getTotalCargosDirectivos() != null)
							lstpreguntasCuestionarios.get(0).setDsRespuesta(value.getTotalCargosDirectivos() + "");
						else
							lstpreguntasCuestionarios.get(0).setDsRespuesta("0");
					}
				}

			}

		}
	}

	public String getStrEncabezadoPregunta() {
		return strEncabezadoPregunta;
	}

	public void setStrEncabezadoPregunta(String strEncabezadoPregunta) {
		this.strEncabezadoPregunta = strEncabezadoPregunta;
	}

	public Long getIdCuestionario() {
		return idCuestionario;
	}

	public void setIdCuestionario(Long idCuestionario) {
		this.idCuestionario = idCuestionario;
	}

	public Integer getLiRespuesta03() {
		return liRespuesta03;
	}

	public void setLiRespuesta03(Integer liRespuesta03) {
		this.liRespuesta03 = liRespuesta03;
	}

	public boolean isLbRespuesta03() {
		return lbRespuesta03;
	}

	public void setLbRespuesta03(boolean lbRespuesta03) {
		this.lbRespuesta03 = lbRespuesta03;
	}

	public String getDsRespuesta03() {
		return dsRespuesta03;
	}

	public void setDsRespuesta03(String dsRespuesta03) {
		this.dsRespuesta03 = dsRespuesta03;
	}

	public String getDsRespuesta0405() {
		return dsRespuesta0405;
	}

	public void setDsRespuesta0405(String dsRespuesta0405) {
		this.dsRespuesta0405 = dsRespuesta0405;
	}

	public String getDsRespuesta06() {
		return dsRespuesta06;
	}

	public void setDsRespuesta06(String dsRespuesta06) {
		this.dsRespuesta06 = dsRespuesta06;
	}

	public PepPreguntasCuestionarioDetalleExt getSelectedRespuestaUnica() {
		return selectedRespuestaUnica;
	}

	public void setSelectedRespuestaUnica(PepPreguntasCuestionarioDetalleExt selectedRespuestaUnica) {
		this.selectedRespuestaUnica = selectedRespuestaUnica;
	}

	public List<PepPreguntasCuestionarioDetalleExt> getLstselectedRespuestaMultiple() {
		return lstselectedRespuestaMultiple;
	}

	public void setLstselectedRespuestaMultiple(List<PepPreguntasCuestionarioDetalleExt> lstselectedRespuestaMultiple) {
		this.lstselectedRespuestaMultiple = lstselectedRespuestaMultiple;
	}

	public void seleccionarentidad() {
		System.out.println("seeleccionarEntidad");
	}

	public Integer getIdEntidadSelect() {
		return idEntidadSelect;
	}

	public void setIdEntidadSelect(Integer idEntidadSelect) {
		this.idEntidadSelect = idEntidadSelect;
	}

	public List<SelectItem> getLstEntidadesRol() {

		lstEntidadesRol = new ArrayList<SelectItem>();
		List<EntidadExt> lstEntidades = ComunicacionServiciosEnt.getEntidadesUsuario((int) usuarioSesion.getId());

		for (EntidadExt entidad : lstEntidades) {
			lstEntidadesRol.add(new SelectItem(entidad.getCodEntidad(), entidad.getNombreEntidad()));
		}

		return lstEntidadesRol;
	}

	public void setLstEntidadesRol(List<SelectItem> lstEntidadesRol) {
		this.lstEntidadesRol = lstEntidadesRol;
	}

	public boolean isLbRolesPermitidos() {
		return lbRolesPermitidos;
	}

	public void setLbRolesPermitidos(boolean lbRolesPermitidos) {
		this.lbRolesPermitidos = lbRolesPermitidos;
	}

	/**/
	public void documentoUpload(FileUploadEvent e) throws IOException {
		cargarDodumento = e.getFile();
		logger.info(e.getFile().getFileName(), e);
		String nombreArchivo = "";
		byte[] bytes = null;
		if (null != cargarDodumento) {
			preguntaSelected = (PepPreguntasCuestionarioExt) e.getComponent().getAttributes().get("pregunta");
			bytes = cargarDodumento.getContents();
			String ext = FilenameUtils.getExtension(cargarDodumento.getFileName());
			String filename = preguntaSelected.getIdPreguntaCuestionario() + "." + ext;
			String ruta = ConfigurationBundleConstants.getString(ConfigurationBundleConstants.CNS_RUTA_DOCUMENTO)
					+ filename;
			String filePath = ConfigurationBundleConstants.getRutaArchivo(ruta);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
			stream.write(bytes);
			stream.close();
			String response = ConnectionHttp.sentFile(WebUtils.WS_MULTIMEDIA_UPLOAD, new File(filePath),
													ComunicacionServiciosHV.getTokenService(), WebUtils.CNS_RUTA_DOCUMENTO,
													WebUtils.TP_DOCUMENTOS_ADICIONALES, usuarioSesion.getNumeroIdentificacion());
			Gson gson = new Gson();
			ErrorMensajes resp = gson.fromJson(response, ErrorMensajes.class);
			if (!resp.isError()) {
				preguntaSelected.setDsRespuesta(resp.getUrlArchivo());
			} else {
				preguntaSelected.setDsRespuesta(ruta);
			}
			nombreArchivo = new String(cargarDodumento.getFileName().getBytes(Charset.defaultCharset()), "UTF-8");
			mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants
							.getStringMessagesBundle(MessagesBundleConstants.MSG_ARCHIVO_CARGA_EXITOSA, getLocale())
							.replace("%nombrearchivo%", nombreArchivo));

		}

	}

	public boolean isLbHabilitarCuestionario() {
		return lbHabilitarCuestionario;
	}

	public void setLbHabilitarCuestionario(boolean lbHabilitarCuestionario) {
		this.lbHabilitarCuestionario = lbHabilitarCuestionario;
	}

	public boolean isLbHabilitarEntidad() {
		return lbHabilitarEntidad;
	}

	public void setLbHabilitarEntidad(boolean lbHabilitarEntidad) {
		this.lbHabilitarEntidad = lbHabilitarEntidad;
	}

	public void habilitarCuestionario() {

	}

	public void cancelar() {
		lstpreguntasCuestionariosDetalle = null;
		lstpreguntasCuestionarios = null;
		idEntidadSelect = null;
		idCuestionario = null;
		lbHabilitarEntidad = false;
		lbHabilitarCuestionario = false;
		RequestContext.getCurrentInstance().execute("window.scrollTo(0,0);");
	}

	public void confirmarCancelar() {
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').show();");
	}

	/**
	 * Metodo para hacer back hacia la página <b>index.xhtml</b>
	 */
	public void cancelar_cuestionario() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').hide();");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../persona/informacionPersonal.xhtml?");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void cancelarCancelar() {

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('ConfirmarCancelar3').hide();");
	}

	public Long getTamPdf() {
		return tamPdf;
	}

	public void setTamPdf(Long tamPdf) {
		this.tamPdf = tamPdf;
	}

	public void onRowSelectSingle(SelectEvent event) {
		if (selectedRespuestaUnica != null && selectedRespuestaUnica.getIdTipoPreguntaDetalle() != null
				&& selectedRespuestaUnica.getIdTipoPreguntaDetalle() == 13) {
			lbVerDetalleRespuestaOtros = true;
		} else {
			lbVerDetalleRespuestaOtros = false;
		}
	}

	/**
	 * @return the pocentajeCargosMujeres
	 */
	public final Integer getPocentajeCargosMujeres() {
		return pocentajeCargosMujeres;
	}

	/**
	 * @param pocentajeCargosMujeres
	 *            the pocentajeCargosMujeres to set
	 */
	public final void setPocentajeCargosMujeres(Integer pocentajeCargosMujeres) {
		this.pocentajeCargosMujeres = pocentajeCargosMujeres;
	}

}
