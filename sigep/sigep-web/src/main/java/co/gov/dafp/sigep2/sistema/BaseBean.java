package co.gov.dafp.sigep2.sistema;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.NotSupportedException;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.converter.CapitalCaseConverter;
import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entities.Departamento;
import co.gov.dafp.sigep2.entities.Municipio;
import co.gov.dafp.sigep2.entities.Pais;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Recurso;
import co.gov.dafp.sigep2.entities.RecursoAccion;
import co.gov.dafp.sigep2.entities.UsuarioSession;
import co.gov.dafp.sigep2.entity.RecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PermisoUsuarioRolDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioRolEntidadDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.mbean.MenuBean;
import co.gov.dafp.sigep2.mbean.ext.RecursoAccionExt;
import co.gov.dafp.sigep2.mbean.ext.RecursoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.util.FileUtil;
import co.gov.dafp.sigep2.util.HTMLUtil;
import co.gov.dafp.sigep2.util.NumeroUtil;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.xml.SeparadorCsvCaracter;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.Registro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;

public abstract class BaseBean {

	protected static final String COMA = ",";
	private boolean forzarRedireccionamientoIndex = false;
	protected long timeOut = ConfigurationBundleConstants.getTimeOutConversation();
	protected long timeOutIdle = ConfigurationBundleConstants.getTimeOutIdle();
	private static final String CREATE = "create?faces-redirect=true";
	private static final String SEARCH = "search?faces-redirect=true";
	private static final String EDIT = "edit?faces-redirect=true";
	public static final String AMBITO_POLITICAS = "AMBITO_POLITICAS";
	private static final String recursosHabilitadosString = "recursosHabilitados";
	private PermisoUsuarioRolDTO permisoUsuarioRol;

	@Inject
	protected Conversation conversation;
	protected static Logger logger = Logger.getInstance(BaseBean.class);

	protected Long id;
	protected String dialog;
	protected String orientacionMenu = "horizontal";
	protected String recurso;
	protected String recursoId;
	protected String dialogName;
	protected Boolean sesionValida = false;
	protected String nombreArchivoDescarga = null;

	protected StreamedContent fileDownload;
	private RecursoDTO moduloSeleccionado;
	private List<RecursoActivoPerfilUsuarioDTO> recursosHabilitados;
	private EntidadDTO entidadUsuario;
	private UsuarioDTO usuarioSesion;
	private List<RolDTO> rolesUsuarioSesion;
	private UsuarioRolEntidadDTO usuarioRolEntidadSesion;

	public void setForzarRedireccionamientoIndex(boolean forzarRedireccionamientoIndex) {
		this.forzarRedireccionamientoIndex = forzarRedireccionamientoIndex;
	}

	@PostConstruct
	public void validarSesion() {
		try {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			Boolean ticketValido = (Boolean) contexto.getSessionMap().get("ticketValido");
			if (ticketValido != null && ticketValido) {
				if (!FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo()
						.contains("restablecerPassword.xhtml")) {
					Object session = contexto.getSession(false);
					HttpSession httpSession = (HttpSession) session;
					httpSession.invalidate();
				}
			}
		} catch (Exception e) {
			logger.error("void validarSesion()", e);
		}
	}

	public PermisoUsuarioRolDTO getPermisoUsuarioRol() {
		return permisoUsuarioRol;
	}

	public void setPermisoUsuarioRol(PermisoUsuarioRolDTO permisoUsuarioRol) {
		this.permisoUsuarioRol = permisoUsuarioRol;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getRecursoId() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		recursoId = (String) contexto.getSessionMap().get("recursoId");
		return recursoId;
	}

	public void setRecursoId(String recursoId) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("recursoId", recursoId);
		this.recursoId = recursoId;
	}

	public Locale getLocale() {
		String localeCodeCookieString = "localeCode";
		Object localeCodeCookie = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get(localeCodeCookieString);
		Cookie cookie = ((Cookie) localeCodeCookie);
		String localeCode = LenguajeBean.ESPANIOL_COLOMBIA;
		if (localeCodeCookie != null) {
			localeCode = cookie.getValue();
			if (localeCode == null || localeCode.isEmpty()) {
				localeCode = LenguajeBean.ESPANIOL_COLOMBIA;
			}
		}
		Locale locale = null;
		if (LenguajeBean.ESPANIOL_COLOMBIA.equals(localeCode)) {
			locale = Locale.ROOT;
		} else {
			locale = Locale.US;
		}
		return locale;
	}

	public void setProgress(Integer progress) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("progress", progress);
	}

	@Named
	@Produces
	public String getOrientacionMenu() {
		return orientacionMenu;
	}

	public void setOrientacionMenu(String orientacionMenu) {
		this.orientacionMenu = orientacionMenu;
	}

	public String getRecursoDTO() {
		return recurso;
	}

	public void setRecursoDTO(String recurso) {
		this.recurso = recurso;
	}

	public String getRecursoDTOId() {
		return recursoId;
	}

	public void setRecursoDTOId(String recursoId) {
		this.recursoId = recursoId;
	}

	public void setSesionValida(Boolean sesionValida) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("sesionValida", sesionValida);
	}

	public Boolean getSesionValida() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		Boolean sesionValida = (Boolean) contexto.getSessionMap().get("sesionValida");
		if (sesionValida == null) {
			this.sesionValida = Boolean.FALSE;
			contexto.getSessionMap().put("sesionValida", this.sesionValida);
		} else {
			this.sesionValida = sesionValida;
		}
		return this.sesionValida;
	}

	public abstract void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException;

	public abstract void init() throws NotSupportedException, SIGEP2SistemaException;

	public String create() {
		return BaseBean.CREATE;
	}

	public String search() {
		return SEARCH;
	}

	public String edit() {
		return EDIT;
	}

	public abstract String persist() throws NotSupportedException;

	public abstract void retrieve() throws NotSupportedException;

	public abstract String update() throws NotSupportedException;

	public abstract void delete() throws NotSupportedException;

	public void showDialog() {
		String title = "";
		if (this.dialog != null) {
			if (this.getSesionValida() || dialog.equals(MessagesBundleConstants.MSG_FATAL_RECURSO_SESION_NO_INICIADA)
					|| dialog.equals(MessagesBundleConstants.MSG_CUENTA_TICKET_INVALIDO)
					|| dialog.equals(MessagesBundleConstants.MSG_CUENTA_USUARIO_NO_AUTORIZADA_CAMBIO_PASS)
					|| dialog.equals(MessagesBundleConstants.MSG_CUENTA_USUARIO_BLOQUEADA)
					|| dialog.equals(MessagesBundleConstants.MSG_CUENTA_USUARIO_PASS_CAMBIO_EXITOSO)) {
				title = MessagesBundleConstants.DLG_HEADER_MENSAJES;
				mostrarMensaje(FacesMessage.SEVERITY_INFO, title, this.dialog);
			}
		}
		this.dialog = null;
	}

	public void mostrarMensaje(Severity severidad, String titleP, String msgP) {
		String tituloLog = "void mostrarMensaje(Severity severidad, String title, String msg)";
		String msg = msgP;
		String title = titleP;
		try {
			title = MessagesBundleConstants.getStringMessagesBundle(title, getLocale());
			msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
		} catch (Exception e) {
			logger.warn(tituloLog, e);
			String exception = "Exception:";
			if (msg != null && msg.contains(exception)) {
				msg = msg.substring(msg.lastIndexOf(exception), msg.length()).replace(exception, "").trim();
			}
			try {
				msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
			} catch (Exception e2) {
			}
		}
		String level = "info";
		if (FacesMessage.SEVERITY_INFO.equals(severidad)) {
			logger.log().info(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_WARN.equals(severidad)) {
			level = "warning";
			logger.log().warn(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_FATAL.equals(severidad) || FacesMessage.SEVERITY_ERROR.equals(severidad)) {
			level = "error";
			logger.log().error(tituloLog, title, msg);
		}
		String buttonName = MessagesBundleConstants.getStringMessagesBundle("BUTTON_OK", getLocale());
		String swal = "swal({title:'" + title + "', text:'" + msg + "', type:'" + level + "', confirmButtonText:'"
				+ buttonName + "', closeOnConfirm: false});";
		RequestContext.getCurrentInstance().execute(swal);
	}

	/**
	 * 
	 * @param severidad
	 * @param title
	 * @param msg
	 * @param acccionSI
	 * @param accionNO
	 * @param btnSi
	 * @param btnNo
	 */
	public void mostrarMensajeSiNo(Severity severidad, String title, String msg, String acccionSI, String accionNO,
			String btnSi, String btnNo) {
		String tituloLog = "void mostrarMensajeSiNo(Severity severidad, String title, String msg, String accion, String btnSi, String btnNo)";
		try {
			title = MessagesBundleConstants.getStringMessagesBundle(title, getLocale());
			msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
		} catch (Exception e) {
			logger.warn(tituloLog, e);
			String exception = "Exception:";
			if (msg != null && msg.contains(exception)) {
				msg = msg.substring(msg.lastIndexOf(exception), msg.length()).replace(exception, "").trim();
			}
			try {
				msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
			} catch (Exception e2) {
			}
		}
		String level = "info";
		if (FacesMessage.SEVERITY_INFO.equals(severidad)) {
			logger.log().info(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_WARN.equals(severidad)) {
			level = "warning";
			logger.log().warn(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_FATAL.equals(severidad) || FacesMessage.SEVERITY_ERROR.equals(severidad)) {
			level = "error";
			logger.log().error(tituloLog, title, msg);
		}
		String swal = "swal({title:'" + title + "',text:'" + msg + "',type:'" + level + "',confirmButtonText:'" + btnSi
				+ "', cancelButtonText:'" + btnNo
				+ "', showCancelButton: true, closeOnConfirm: true,closeOnCancel: true },function(isConfirm) { if (isConfirm) {"
				+ acccionSI + "} else {" + accionNO + "}});";
		RequestContext.getCurrentInstance().execute(swal);
	}

	public void mostrarMensajeBotonAccion(Severity severidad, String title, String msg, String btnSi, String acccion) {
		String tituloLog = "void mostrarMensajeBotonAccion(Severity severidad, String title, String msg, String accion)";
		try {
			title = MessagesBundleConstants.getStringMessagesBundle(title, getLocale());
			msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
		} catch (Exception e) {
			logger.warn(tituloLog, e);
			String exception = "Exception:";
			if (msg != null && msg.contains(exception)) {
				msg = msg.substring(msg.lastIndexOf(exception), msg.length()).replace(exception, "").trim();
			}
			try {
				msg = MessagesBundleConstants.getStringMessagesBundle(msg, getLocale());
			} catch (Exception e2) {
			}
		}
		String level = "info";
		if (FacesMessage.SEVERITY_INFO.equals(severidad)) {
			logger.log().info(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_WARN.equals(severidad)) {
			level = "warning";
			logger.log().warn(tituloLog, title, msg);
		} else if (FacesMessage.SEVERITY_FATAL.equals(severidad) || FacesMessage.SEVERITY_ERROR.equals(severidad)) {
			level = "error";
			logger.log().error(tituloLog, title, msg);
		}
		String swal = "swal({title: '" + title + "',text: '" + msg + "',type: '" + level
				+ "',showCancelButton: false,confirmButtonColor: '#3085d6',cancelButtonColor: '#d33',confirmButtonText: '"
				+ btnSi + "'}).then((result) => {" + acccion + ";})";

		RequestContext.getCurrentInstance().execute(swal);
	}

	public void onComplete() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(MessagesBundleConstants
				.getStringMessagesBundle(MessagesBundleConstants.DLG_PROCESO_EXITOSO, getLocale())));
	}

	protected void openDialog(String dialogName) {
		openDialog(dialogName, null);
	}

	protected void openDialog(String dialogName, Map<String, List<String>> params) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("dialogName", dialogName);
		this.dialogName = dialogName;

		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("closeOnEscape", true);
		RequestContext.getCurrentInstance().openDialog(dialogName, options, params);
	}

	public void closeDialog() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		dialogName = (String) contexto.getSessionMap().get("dialogName");

		RequestContext.getCurrentInstance().closeDialog(dialogName);
	}

	protected void closeDialog(Object data) {
		RequestContext.getCurrentInstance().closeDialog(data);
	}

	public RecursoDTO getModuloSeleccionado() {
		if (moduloSeleccionado == null) {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			moduloSeleccionado = (RecursoDTO) contexto.getSessionMap().get("moduloSeleccionado");
		}
		return moduloSeleccionado;
	}

	/**
	 * @param moduloSeleccionado
	 *            the moduloSeleccionado to set
	 */
	public void setModuloSeleccionado(RecursoDTO moduloSeleccionadoP) {
		RecursoDTO moduloSeleccionado = moduloSeleccionadoP;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		moduloSeleccionado = (RecursoDTO) contexto.getSessionMap().put("moduloSeleccionado", moduloSeleccionado);
		this.moduloSeleccionado = moduloSeleccionado;
	}

	@SuppressWarnings("unchecked")
	public List<RecursoActivoPerfilUsuarioDTO> getRecursoDTOsHabilitados() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		recursosHabilitados = (List<RecursoActivoPerfilUsuarioDTO>) contexto.getSessionMap()
				.get(recursosHabilitadosString);
		return recursosHabilitados;
	}

	public void setRecursoDTOsHabilitados(List<RecursoActivoPerfilUsuarioDTO> recursosHabilitados) {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(recursosHabilitadosString, recursosHabilitados);

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		request.getSession().setAttribute(recursosHabilitadosString, recursosHabilitados);
		this.recursosHabilitados = recursosHabilitados;
	}

	public void seleccionarDetalle(SelectEvent event) {

	}

	public Conversation getConversation() {
		return conversation;
	}

	public String getNombreArchivoDescarga() {
		return nombreArchivoDescarga;
	}

	public void setNombreArchivoDescarga(String nombreArchivoDescarga) {
		this.nombreArchivoDescarga = nombreArchivoDescarga;
	}

	public StreamedContent getFileDownload() {
		return fileDownload;
	}

	public UsuarioDTO getUsuarioSesion() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		UsuarioDTO usuarioSesionRol = (UsuarioDTO) contexto.getSessionMap().get("usuarioSesion");
		if (usuarioSesionRol != null && getRolAuditoria() != null) {
			usuarioSesionRol.setCodRol(getRolAuditoria().getId());
		}
		return usuarioSesionRol;
	}

	public void setUsuarioSesion(UsuarioDTO usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("usuarioSesion", this.usuarioSesion);
	}

	@SuppressWarnings("unchecked")
	public List<RolDTO> getRolesUsuarioSesion() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<RolDTO> roles = (List<RolDTO>) contexto.getSessionMap().get("rolesUsuarioSesion");
		if (roles != null) {
			return roles;
		} else {
			return new LinkedList<>();
		}
	}

	public String getRolesUsuarioSesionString() {
		List<RolDTO> roles = this.getRolesUsuarioSesion();
		if (roles == null || roles.isEmpty()) {
			return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_SIN_REGISTROS,
					getLocale());
		}
		String rolesString = "";
		for (RolDTO rol : roles) {
			rolesString = rolesString + CapitalCaseConverter.convert(rol.getDescripcionRol()) + HTMLUtil.retornoCarro;
		}
		return rolesString.substring(0, rolesString.lastIndexOf(HTMLUtil.retornoCarro));
	}

	public void setRolesUsuarioSesion(List<RolDTO> rolesUsuarioSesion) {
		this.rolesUsuarioSesion = rolesUsuarioSesion;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("rolesUsuarioSesion", this.rolesUsuarioSesion);
	}

	public EntidadDTO getEntidadUsuario() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		return (EntidadDTO) contexto.getSessionMap().get("entidadUsuario");
	}

	public void setEntidadUsuario(EntidadDTO entidadUsuario) {
		this.entidadUsuario = entidadUsuario;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("entidadUsuario", this.entidadUsuario);
		MenuBean menuBean = (MenuBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("menuBean");
		if (menuBean != null) {
			menuBean.setRecursoDTOsHabilitados(null);
			menuBean.init();
		}
	}

	public UsuarioRolEntidadDTO getUsuarioRolEntidadSesion() {
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		return (UsuarioRolEntidadDTO) contexto.getSessionMap().get("usuarioRolEntidadSesion");
	}

	public void setUsuarioRolEntidadSesion(UsuarioRolEntidadDTO usuarioRolEntidadSesion) {
		this.usuarioRolEntidadSesion = usuarioRolEntidadSesion;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put("usuarioRolEntidadSesion", this.usuarioRolEntidadSesion);
	}

	static public class ColumnModel implements Serializable {
		private static final long serialVersionUID = -4102582015941011921L;
		private int index;
		private String header;
		private String title;
		private String headerAlias;
		private String type;
		private String mask;
		private String property;
		private boolean totalized;
		private Long order = 0l;

		public ColumnModel(int index, String header, String title, String headerAlias, String property, String type,
				String mask, boolean totalized) {
			this.index = index;
			this.type = type;
			this.mask = mask;
			this.header = header;
			this.title = title;
			this.headerAlias = headerAlias;
			this.property = property;
			this.totalized = totalized;
			this.order = (long) index;
		}

		public ColumnModel(int index, String header, String title, String headerAlias, String property, String type,
				String mask, boolean totalized, long order) {
			this.index = index;
			this.type = type;
			this.mask = mask;
			this.header = header;
			this.title = title;
			this.headerAlias = headerAlias;
			this.property = property;
			this.totalized = totalized;
			this.order = order;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getHeader() {
			return header;
		}

		public String getTitle() {
			return title;
		}

		public String getHeaderAlias() {
			return headerAlias;
		}

		public String getProperty() {
			return property;
		}

		public int getIndex() {
			return index;
		}

		public String getType() {
			return type;
		}

		public String getMask() {
			return mask;
		}

		public boolean isTotalized() {
			return totalized;
		}

		public Long getOrder() {
			return order;
		}

		public void setOrder(Long order) {
			this.order = order;
		}
	}

	public void generarArchivos(List<ColumnModel> columns, XmlReporte xmlArchivo,
			List<co.gov.dafp.sigep2.util.Registro> datasource) {
		String contentType = null;
		String extension = null;
		int extensionInt = xmlArchivo.hashCode();
		File file = null;
		InputStream stream = null;
		try {
			if (extensionInt == FileUtil.EXT_HTML) {
				contentType = FileUtil.HTML_CONTENT_TYPE;
				extension = FileUtil.HTML;
				file = File.createTempFile(nombreArchivoDescarga + extension, null);
				stream = generarTxt(file, columns, xmlArchivo, datasource);
			} else {
				contentType = FileUtil.TEXT_CONTENT_TYPE;
				extension = FileUtil.TXT;
				file = File.createTempFile(nombreArchivoDescarga + extension, null);
				stream = generarTxt(file, columns, xmlArchivo, datasource);
			}
			if (contentType != null) {
				fileDownload = new DefaultStreamedContent(stream, contentType, nombreArchivoDescarga + extension);
			}
		} catch (Exception e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
		} finally {
			if (file != null) {
				file.deleteOnExit();
			}
		}
	}

	private InputStream generarTxt(File file, List<ColumnModel> columns, XmlReporte xmlArchivo,
			List<co.gov.dafp.sigep2.util.Registro> datasource) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file, true));
			Registro xmlRegistro = xmlArchivo.getRegistro().get(0);
			String separador = null;
			int separadorCsvInt = 0;
			if (xmlRegistro.getCaracterCsv() == null) {
				separadorCsvInt = 0;
			} else {
				separadorCsvInt = xmlRegistro.getCaracterCsv().value().hashCode();
			}

			if (separadorCsvInt == FileUtil.SEPARADOR_COMA) {
				separador = SeparadorCsvCaracter.COMA.value();
			} else if (separadorCsvInt == FileUtil.SEPARADOR_PUNTO_COMA) {
				separador = SeparadorCsvCaracter.PUNTO_COMA.value();
			} else if (separadorCsvInt == FileUtil.SEPARADOR_TABULADOR) {
				separador = SeparadorCsvCaracter.TABULADOR.value();
			} else if (separadorCsvInt == FileUtil.SEPARADOR_RETORNO_CARRO) {
				separador = SeparadorCsvCaracter.RETORNO_CARRO.value();
			} else {
				separador = SeparadorCsvCaracter.PUNTO_COMA.value();
			}

			for (co.gov.dafp.sigep2.util.Registro registro : datasource) {
				String value = "";
				for (ColumnModel columna : columns) {
					if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_INTEGER.value().equals(columna.getType())) {
						value = value + registro.getItem().get(columna.getIndex()).getLongValue().toString();
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE.value().equals(columna.getType())) {
						value = value + DateUtils.formatearACadena(
								registro.getItem().get(columna.getIndex()).getDateValue(), DateUtils.FECHA_FORMATO);
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DATE_LARGE.value().equals(columna.getType())) {
						value = value
								+ DateUtils.formatearACadena(registro.getItem().get(columna.getIndex()).getDateValue(),
										DateUtils.FECHA_HORA_FORMATO);
					} else if (TipoDato.TTL_REPORTES_FORMA_TIPO_DATO_DECIMAL.value().equals(columna.getType())) {
						value = value + registro.getItem().get(columna.getIndex()).getBigDecimalValue().toString();
					} else {
						value = value + registro.getItem().get(columna.getIndex()).getStringValue();
					}
					value = value + separador;
				}
				out.write(value + SeparadorCsvCaracter.RETORNO_CARRO.value());
			}
			out.newLine();
		} catch (IOException e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return new FileInputStream(file);
	}

	/**
	 * Finaliza la conversacion activa y redirecciona de acuerdo a
	 * <b>redireccionarAIndex</b>. Si es <b>true</b> redirecciona a
	 * <b>/index.xhtml</b>, de lo contrario redirecciona a la url del contexto
	 * 
	 * @param redireccionarAIndex
	 *            Permite redireccionar a <b>/index.xhtml</b> luego de finalizada la
	 *            transaccion
	 * @param mensajeConfirmacion
	 *            Mensaje de confirmacion para mostrar luego de finalizar la
	 *            transacciÃ¯Â¿Â½n
	 * @param parametrosAdicionales
	 *            Si se requiere, se pueden adicionar parÃ¯Â¿Â½metros en la url de
	 *            retorno
	 * @throws IOException
	 */
	protected void finalizarConversacion(boolean redireccionarAIndex, String mensajeConfirmacion,
			String parametrosAdicionales) throws IOException {
		try {
			conversation.end();
		} catch (Exception e) {
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String path = "/index.xhtml";
		String pathInfo = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		if (!redireccionarAIndex) {
			path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		}
		String mensaje = "";
		if (mensajeConfirmacion != null && !mensajeConfirmacion.isEmpty()) {
			mensaje = "&dialog=" + mensajeConfirmacion;
		}
		String parametros = "?faces-redirect=true";
		if (parametrosAdicionales != null && !parametrosAdicionales.isEmpty()) {
			if (!parametrosAdicionales.startsWith("&")) {
				parametros = parametros + "&";
			}
			parametros = parametros + parametrosAdicionales;
		}

		boolean redireccionar = false;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		String pathInfoAnterior = (String) contexto.getSessionMap().get("pathInfoAnterior");
		if (!pathInfo.equals(pathInfoAnterior) && !pathInfo.contains("/restablecerPassword.xhtml")) {
			redireccionar = true;
			if (pathInfoAnterior != null) {
				contexto.getSessionMap().put("pathInfoAnterior", pathInfoAnterior);
			} else {
				contexto.getSessionMap().put("pathInfoAnterior", pathInfo);
			}
		}
		boolean redireccionok = false;
		if (pathInfo.contains("/logout.xhtml")) {
			Parametrica parametroRedireccona = ComunicacionServiciosSis
					.getParametricaporId(BigDecimal.valueOf(TipoParametro.PATH_REDIRECT_CIERRA_SESION.getValue()));
			if (parametroRedireccona != null && parametroRedireccona.getValorParametro() != null
					&& !"".equals(parametroRedireccona.getValorParametro().trim())) {
				redireccionok = true;
				context.redirect(parametroRedireccona.getValorParametro());
			}
		}
		if (!redireccionok) {
			if (redireccionar || forzarRedireccionamientoIndex) {
				forzarRedireccionamientoIndex = false;
				context.redirect(context.getRequestContextPath() + "/" + ConfigurationBundleConstants.aliasSitio()
						+ path + parametros + mensaje);
			}
		}

	}

	/**
	 * Finaliza la conversacion activa y redirecciona hacia <b>path</b>.
	 * 
	 * @param path
	 *            Lugar en redireccionamiento
	 * @param mensajeConfirmacion
	 *            Mensaje de confirmacion para mostrar luego de finalizar la
	 *            transacción
	 * @param parametrosAdicionales
	 *            Si se requiere, se pueden adicionar pararámetros en la url de
	 *            retorno
	 * @throws IOException
	 */
	protected void finalizarConversacion(String path, String mensajeConfirmacion, String parametrosAdicionales)
			throws IOException {
		if (path != null && path.contains("index.xhtml")) {
			finalizarConversacion(true, mensajeConfirmacion, parametrosAdicionales);
		}
		try {
			conversation.end();
		} catch (Exception e) {
		}
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		String mensaje = "";
		if (mensajeConfirmacion != null && !mensajeConfirmacion.isEmpty()) {
			mensaje = "&dialog=" + mensajeConfirmacion;
		}
		String parametros = "?faces-redirect=true";
		if (parametrosAdicionales != null && !parametrosAdicionales.isEmpty()) {
			if (!parametrosAdicionales.startsWith("&")) {
				parametros = parametros + "&";
			}
			parametros = parametros + parametrosAdicionales;
		}
		context.redirect(context.getRequestContextPath() + "/" + ConfigurationBundleConstants.aliasSitio() + path
				+ parametros + mensaje);
	}

	/**
	 * Finaliza la conversacion activa y redirecciona a <b>/index.xhtml</b>
	 * 
	 * @deprecated reemplazado por {@link finalizarConversacion(boolean
	 *             redireccionarAIndex, String mensajeConfirmacion, String
	 *             parametrosAdicionales)} con valor en redireccionarAIndex = true
	 * @param mensajeConfirmacion
	 *            Mensaje de confirmacion para mostrar luego de finalizar la
	 *            transacción
	 * @param parametrosAdicionales
	 *            Si se requiere, se pueden adicionar pararámetros en la url de
	 *            retorno
	 * @throws IOException
	 */
	@Deprecated
	protected void irALogin(String mensajeConfirmacion, String parametrosAdicionales) throws IOException {
		try {
			conversation.end();
		} catch (Exception e) {
		}
		String path = "index.xhtml";
		String mensaje = "";
		if (mensajeConfirmacion != null && !mensajeConfirmacion.isEmpty()) {
			mensaje = "&dialog=" + mensajeConfirmacion;
		}
		String parametros = "?faces-redirect=true";
		if (parametrosAdicionales != null && !parametrosAdicionales.isEmpty()) {
			if (!parametrosAdicionales.startsWith("&")) {
				parametros = parametros + "&";
			}
			parametros = parametros + parametrosAdicionales;
		}
		String pathInfo = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
		boolean redireccionar = false;
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		String pathInfoAnterior = (String) contexto.getSessionMap().get("pathInfoAnterior");
		if (pathInfoAnterior == null || !pathInfoAnterior.equals(pathInfo)) {
			redireccionar = true;
			contexto.getSessionMap().put("pathInfoAnterior", pathInfo);
		}
		if (redireccionar && !pathInfo.contains(path) && !pathInfo.contains("/restablecerPassword.xhtml")) {
			finalizarConversacion(true, mensaje, parametros);
		}
	}

	public void abrirAccion(long idRegistro, String paginaP) {
		String pagina = paginaP.replace(FileUtil.XHTML, "");
		if (pagina.startsWith(FileUtil.SLASH)) {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
			path = path.substring(path.indexOf(FileUtil.SLASH), path.lastIndexOf(FileUtil.SLASH) + 1);
			if (pagina.startsWith(path)) {
				pagina = pagina.replace(path, FileUtil.SLASH);
			}
			pagina = pagina.substring(pagina.indexOf(FileUtil.SLASH) + 1, pagina.length());
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(pagina + FileUtil.XHTML + "?id=" + idRegistro);
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_NO_CONFIGURADA_PAGINA_NO_AUTORIZADO);
		}
		this.permisoUsuarioRol = null;
	}

	public void abrirAccion(long idRegistro, String paginaP, String paramentrosAdicionales) {
		String pagina = paginaP.replace(FileUtil.XHTML, "");
		if (pagina.startsWith(FileUtil.SLASH)) {
			String path = FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo();
			path = path.substring(path.indexOf(FileUtil.SLASH), path.lastIndexOf(FileUtil.SLASH) + 1);
			if (pagina.startsWith(path)) {
				pagina = pagina.replace(path, FileUtil.SLASH);
			}
			pagina = pagina.substring(pagina.indexOf(FileUtil.SLASH) + 1, pagina.length());
		}
		try {
			StringBuilder parametrosURL = new StringBuilder();
			if (paramentrosAdicionales != null) {
				String[] parametros = paramentrosAdicionales.split(";");
				for (String paramentro : parametros) {
					parametrosURL.append("&");
					parametrosURL.append(paramentro);
				}
			}
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(pagina + FileUtil.XHTML + "?id=" + idRegistro + parametrosURL);
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.MSG_FATAL_NO_CONFIGURADA_PAGINA_NO_AUTORIZADO);
		}
		this.permisoUsuarioRol = null;
	}

	/**
	 * Permite validar si el usuario en sesion y sus roles asociados a la entidad
	 * seleccionada tiene permiso para usar una determinada funcionalidad
	 * 
	 * @param roles
	 * @return {@link Boolean}
	 */
	public boolean usuarioTieneRolAsignado(String... roles) throws SIGEP2SistemaException {
		boolean usuarioTieneRolAsignado = true;
		return usuarioTieneRolAsignado;
	}

	/**
	 * Permite validar si el usuario en sesion y sus roles asociados a la entidad
	 * seleccionada tiene permiso para usar una determinada funcionalidad sin tener
	 * en cuenta la jeraquia de roles
	 * 
	 * @param roles
	 * @return {@link Boolean}
	 */
	public boolean usuarioTieneRolAsignadoSinJerarquia(String... roles) throws SIGEP2SistemaException {
		boolean usuarioTieneRolAsignado = false;
		List<RolDTO> rolesDAFP = AdministracionDelegate.obtenerRolesPorDescripcion(roles);
		for (RolDTO rolUsuario : getRolesUsuarioSesion()) {
			if (Arrays.asList(roles).contains(RolDTO.SISTEMA)) {
				usuarioTieneRolAsignado = false;
				break;
			}

			if (rolesDAFP.contains(rolUsuario)) {
				usuarioTieneRolAsignado = true;
				break;
			}
		}
		return usuarioTieneRolAsignado;
	}

	/**
	 * Devuelve el rol con el que se va a trazar la auditoria del usuario en el
	 * sistema. Se caracteriza por seleccionar el rol con mayor jerarquÃ­a entre los
	 * que tiene asignado en la relación usuario/entidad. La jerarquía está¡
	 * determinada por el padre del rol. La lista se encuentra ordenada por este
	 * atributo.
	 * 
	 * @return {@link RolDTO}
	 */
	public RolDTO getRolAuditoria() {
		if (getRolesUsuarioSesion() != null && getRolesUsuarioSesion().size() > 0) {
			return getRolesUsuarioSesion().get(0);
		} else {
			return null;
		}
	}

	/**
	 * Calcula el tiempo de exiparación de una cookie, teniendo en cuenta el
	 * productos de los valores asignados
	 * 
	 * @param dias
	 * @param horas
	 * @param minutos
	 * @param segundos
	 * @return int
	 */
	public int getVigenciaCookie(int dias, int horas, int minutos, int segundos) {
		if (dias > 7) {
			throw new IllegalArgumentException("Valor dias invalido");
		}
		if (horas >= 24) {
			throw new IllegalArgumentException("Valor horas invalido");
		}
		if (minutos >= 60) {
			throw new IllegalArgumentException("Valor minutos invalido");
		}
		if (segundos >= 60) {
			throw new IllegalArgumentException("Valor segundos invalido");
		}
		long vigenciaCookie = 0;
		Date fechaExpiracion = null;
		Date ahora = DateUtils.getFechaSistema();
		if (dias > 1) {
			fechaExpiracion = DateUtils.sumarSegundos(ahora, segundos);
			fechaExpiracion = DateUtils.sumarMinutos(fechaExpiracion, minutos);
			fechaExpiracion = DateUtils.sumarHoras(fechaExpiracion, horas);
			fechaExpiracion = DateUtils.sumarDias(fechaExpiracion, dias);
		} else if (horas > 1) {
			fechaExpiracion = DateUtils.sumarSegundos(ahora, segundos);
			fechaExpiracion = DateUtils.sumarMinutos(fechaExpiracion, minutos);
			fechaExpiracion = DateUtils.sumarHoras(fechaExpiracion, horas);
		} else if (minutos > 1) {
			fechaExpiracion = DateUtils.sumarSegundos(ahora, segundos);
			fechaExpiracion = DateUtils.sumarMinutos(fechaExpiracion, minutos);
		} else {
			fechaExpiracion = DateUtils.sumarSegundos(ahora, segundos);
		}
		vigenciaCookie = 100000;
		return Integer.parseInt(String.valueOf(vigenciaCookie));
	}

	/**
	 * Refresca en el navegador el estado de la cookie. En caso de refrescar
	 * exitosamente la cookie devolera <b>{@link Cookie}</b>, de lo contrario
	 * <b>null</b>
	 * 
	 * @param cookie
	 * @return {@link Cookie}
	 */
	public Cookie refrescarCookie(Cookie cookie) {
		try {
			Map<String, Object> properties = new HashMap<>();
			properties.put("maxAge", cookie.getMaxAge());
			if (cookie.getPath() != null) {
				properties.put("path", cookie.getPath());
			}
			if (cookie.getComment() != null) {
				properties.put("comment", cookie.getComment());
			}
			if (cookie.getDomain() != null) {
				properties.put("domain", cookie.getDomain());
			}
			properties.put("secure", cookie.getSecure());
			if (cookie.getVersion() > 0) {
				properties.put("version", cookie.getVersion());
			}

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.addResponseCookie(cookie.getName(), URLEncoder.encode(cookie.getValue(), "UTF-8"),
					properties);
			return getCookie(cookie.getName());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Crear en el navegador la cookie. En caso de crearla exitosamente la cookie
	 * devolera <b>{@link Cookie}</b>, de lo contrario <b>null</b>
	 * 
	 * @param nombreCookie
	 * @param valorCookie
	 * @param vigenciaCookie
	 * @return {@link Cookie}
	 */
	public Cookie crearCookie(String nombreCookie, String valorCookie, int vigenciaCookie) {
		try {
			Map<String, Object> properties = new HashMap<>();
			properties.put("maxAge", vigenciaCookie);
			properties.put("path", "/");
			properties.put("secure", false);

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.addResponseCookie(nombreCookie, URLEncoder.encode(valorCookie, "UTF-8"), properties);
			return getCookie(nombreCookie);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Retorna el estado de una cookie en el navegador
	 * 
	 * @param nombreCookie
	 * @return {@link Cookie}
	 */
	public Cookie getCookie(String nombreCookie) {
		Object localeCodeCookie = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap()
				.get(nombreCookie);
		if (localeCodeCookie != null) {
			return (Cookie) ((Cookie) localeCodeCookie).clone();
		} else {
			return null;
		}
	}

	/**
	 * Metodo para hacer back hacia la página <b>index.xhtml</b>
	 */
	public void cancelar() {
		try {
			finalizarConversacion(true, null, null);
		} catch (IOException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
		}
	}

	/**
	 * Obtiene un parametro de la sesion, si este aún no ha sido puesto en sesión se
	 * obtiene de la tabla parametrica
	 * 
	 * @param nombreParametro
	 * @return {@link Parametrica}
	 */
	public Parametrica getParametroSesion(String nombreParametro) {
		try {
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			Parametrica parametro = (Parametrica) contexto.getSessionMap().get(nombreParametro);
			if (parametro == null) {
				parametro = ComunicacionServiciosSis.getParametricaIntetos(nombreParametro);
				if (parametro == null) {
					throw new IllegalArgumentException("Consulta parametro: " + nombreParametro + ". "
							+ MessagesBundleConstants.getStringMessagesBundle(
									MessagesBundleConstants.DLG_CONFIGURATION_INVALID, getLocale()));
				}
				contexto.getSessionMap().put(nombreParametro, parametro);
			}
			return parametro;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	// Obtiene la parametrica de la version.
	private Parametrica paramVersionActual = ComunicacionServiciosSis.getParametricaporId(new BigDecimal("1716"));

	public Parametrica getParamVersionActual() {
		return paramVersionActual;
	}

	public void setParamVersionActual(Parametrica paramVersionActual) {
		this.paramVersionActual = paramVersionActual;
	}

	/**
	 * Actualiza la version del sistema en BD.
	 * 
	 * @author jesus.torres
	 * @return String
	 */
	public String verVersion() {
		if (paramVersionActual.getValorParametro() == null || "".equals(paramVersionActual.getValorParametro()))
			paramVersionActual = ComunicacionServiciosSis.getParametricaporId(new BigDecimal("1716"));
		return "v. " + paramVersionActual.getValorParametro() + " ";
	}

	/**
	 * Valida si es necesario hacer redireccionamiento a la pagina principal de hoja
	 * de vida como home del sistema
	 * 
	 * {@link Boolean} <code>true</code> Si se redirecciona a la pagina de hoja de
	 * vida, <code>false</code> en caso contrario
	 */
	public boolean isHomeHojaDeVida() {
		try {
			return this.getEntidadUsuario() != null
					&& this.usuarioTieneRolAsignadoSinJerarquia(RolDTO.SERVIDOR_PUBLICO, RolDTO.CONTRATISTA);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Obtiene Colombia como país, para los casos en los que se necesita en todo el
	 * sistema SIGEP
	 *
	 * @return El país Colombia
	 */
	protected Pais obtenerPaisColombia() {
		return ComunicacionServiciosSis.getpisporid(169L);
	}

	public String obtenerIndicativoPais(BigDecimal codPais) {
		if (codPais == null) {
			return "";
		}
		Pais pais = ComunicacionServiciosSis.getpisporid(codPais.longValue());
		if (pais == null) {
			return null;
		}
		return "" + pais.getIndicativoPais();
	}

	/**
	 * Obtiene el indicativo de un departamento según su código
	 *
	 * @param codDepartamento
	 *            El código de departamento cuyo indicativo se desea obtener
	 * @return El indicativo del departamento
	 */
	public String obtenerIndicativoDepartamento(BigDecimal codDepartamento) {
		if (codDepartamento == null) {
			return null;
		}
		Departamento depto = ComunicacionServiciosSis.getdeptoporid(codDepartamento.longValue());
		if (depto == null) {
			return null;
		}
		return depto.getIndicativo();
	}

	/**
	 * Buscar un bean controller en el {@link FacesContext#getCurrentInstance()}
	 * 
	 * @param beanName
	 *            Nombre del {@link BaseBean} a buscar
	 * @return {@link BaseBean} Instancia del bean encontrado
	 */
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		StringBuilder nemoBeanName = new StringBuilder();
		nemoBeanName.append("#{");
		nemoBeanName.append(beanName);
		nemoBeanName.append("}");
		T bean = (T) context.getApplication().evaluateExpressionGet(context, nemoBeanName.toString(), Object.class);
		if (bean instanceof BaseBean) {
			return bean;
		} else {
			return null;
		}
	}

	/**
	 * @author Jhon Garcia Método que verifica si un botón está habilitado para el
	 *         usuario de acuerdo al rol y a la funcionalidad
	 */
	protected boolean validarFuncionalidadDeshabilitada(String idBoton) {
		/* Consultamos los componentes del módulo con un servicio a bd() */
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String recursoId = paramMap.get("recursoId");

		List<RecursoExt> idCompoments = new ArrayList<RecursoExt>();
		List<RolDTO> rolesUsuario = getRolesUsuarioSesion();
		int[] idRoles = new int[rolesUsuario.size()];

		for (int i = 0; i < rolesUsuario.size(); i++) {
			idRoles[i] = (int) rolesUsuario.get(i).getId();
		}

		RecursoExt recurso = new RecursoExt();
		recurso.setCodigoVentana(recursoId);
		recurso.setCodRolList(idRoles);
		idCompoments = ComunicacionServiciosSis.getRecursoList(recurso);

		for (RecursoExt componentId : idCompoments) {
			if (idBoton.equals(componentId.getIdBoton())) {
				if (componentId.getFlgEstado() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @author Jhon Garcia Método para validar los permisos sobre las acciones
	 */
	protected void validaPermisosAcciones(String recursoId) {
		/* Consultamos los componentes del módulo con un servicio a bd() */
		List<RecursoExt> idCompoments = new ArrayList<RecursoExt>();
		List<RolDTO> rolesUsuario = getRolesUsuarioSesion();
		int[] idRoles = new int[rolesUsuario.size()];

		for (int i = 0; i < rolesUsuario.size(); i++) {
			idRoles[i] = (int) rolesUsuario.get(i).getId();
		}

		RecursoExt recurso = new RecursoExt();
		recurso.setCodigoVentana(recursoId);
		recurso.setCodRolList(idRoles);
		idCompoments = ComunicacionServiciosSis.getRecursoList(recurso);
		for (RecursoExt componentId : idCompoments) {
			FacesContext context = FacesContext.getCurrentInstance();
			UIViewRoot root = context.getViewRoot();
			Iterator<UIComponent> kids = root.getFacetsAndChildren();
			while (kids.hasNext()) {
				UIComponent found = findComponent(kids.next(), componentId.getIdBoton());
				if (found != null) {
					// found.setRendered(false);
					if (componentId.getFlgEstado() == 0) {
						if (found instanceof CommandButton) {
							CommandButton button = (CommandButton) found;
							button.setDisabled(true);
							button.setTitle("Deshabilitado por seguridad roles");
						} else if (found instanceof Panel) {
							Panel panel = (Panel) found;
							panel.setRendered(false);
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Método para encontrar un componente por el ID
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	private UIComponent findComponent(UIComponent c, String id) {
		if (id.equals(c.getId())) {
			return c;
		}
		Iterator<UIComponent> kids = c.getFacetsAndChildren();
		while (kids.hasNext()) {
			UIComponent found = findComponent(kids.next(), id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	public String obtenerNombreDepartamento(BigDecimal codDepartamento) {
		if (codDepartamento == null) {
			return null;
		}
		Departamento depto = ComunicacionServiciosSis.getdeptoporid(codDepartamento.longValue());
		if (depto == null) {
			return null;
		}
		return depto.getNombreDepartamento();
	}

	/**
	 * Devuelve un valor en formato porcentaje
	 * 
	 * @param valor
	 *            Valor numérico a formatear
	 * @return {@link String} valor en formato porcentaje
	 */
	public String formatoPorcentaje(BigDecimal valor) {
		if (valor == null) {
			return NumeroUtil.formatoPorcentaje(0.0);
		}
		return NumeroUtil.formatoPorcentaje(valor.doubleValue());
	}

	/**
	 * Devuelve un valor válido en formato fecha corta
	 * 
	 * @param fecha
	 *            Valor fecha a formatear
	 * @return {@link String} valor en formato fecha corta
	 */
	public String formatoFecha(Date fecha) {
		if (fecha == null) {
			return "";
		}
		return DateUtils.formatearACadena(fecha, DateUtils.FECHA_FORMATO_VO);
	}

	/**
	 * Devuelve un valor válido en formato fecha larga
	 * 
	 * @param fecha
	 *            Valor fecha a formatear
	 * @return {@link String} valor en formato fecha larga
	 */
	public String formatoFechaLarga(Date fecha) {
		if (fecha == null) {
			return "";
		}
		return DateUtils.formatearACadena(fecha, DateUtils.FECHA_HORA_FORMATO_VO);
	}

	public String obtenerNombreMunicipio(BigDecimal codMunicipio) {
		if (codMunicipio == null) {
			return null;
		}
		Municipio mpio = ComunicacionServiciosSis.getMunicipiosid(codMunicipio.longValue());
		if (mpio == null) {
			return null;
		}
		return mpio.getNombreMunicipio();
	}

	/**
	 * Devuelve un valor en formato moneda
	 * 
	 * @param valor
	 *            Valor numérico a formatear
	 * @return {@link String} valor en formato moneda
	 */
	public String formatoMoneda(BigDecimal valor) {
		return NumeroUtil.formatoMoneda(valor);
	}

	/**
	 * Pablo Quintana - 26/09/2018 Método utilizdo desde los extends para concatenar
	 * la ruta de los adjuntos
	 * 
	 * @param ruta
	 * @return
	 */
	public String obtieneRutaVisualizarArchivo(String ruta) {
		if (ruta == null || ruta == "") {
			return null;
		}
		String urlDoc = "../../MostrarPdf?path=";
		String configPath = System.getProperty("CONFIG_PATH") + "" + ruta;
		ruta = new String(Base64.getEncoder().encodeToString(configPath.getBytes()));
		return urlDoc + ruta;
	}

	/**
	 * Busca un componente JSF en la vista del contexto
	 * 
	 * @param id
	 *            Identificador del componente en la vista en contexto
	 * @return {@link UIComponent} componente identificado en la vista en contexto
	 */
	public UIComponent findComponent(String id) {
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		for (UIComponent component : viewRoot.getChildren()) {
			UIComponent found = findComponent(component, id);
			if (found != null) {
				return found;
			}
		}
		return null;
	}

	/**
	 * 
	 * Elaborado por: Jose Viscaya Jan 14, 2019
	 * 
	 * @param funcion
	 */
	public void cerrarSessionFuncion(String funcion) {
		UsuarioSession usuarioSession = new UsuarioSession();
		usuarioSession.setNombreFuncion(funcion);
		usuarioSession.setFlgActivo((short) 1);
		usuarioSession.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		List<UsuarioSession> ltsUsr = ComunicacionServiciosSis.getUsuarioSession(usuarioSession);
		if (!ltsUsr.isEmpty()) {
			for (UsuarioSession usuSession : ltsUsr) {
				usuSession.setAudAccion(63);
				usuSession.setAudFechaActualizacion(new Date());
				usuSession.setFechaFinSession(new Date());
				usuSession.setFechaFinSession(null);
				usuSession.setFlgActivo((short) 0);
				ComunicacionServiciosSis.setUsuarioSession(usuSession);
			}
		}
	}

	/**
	 * Método que valida si el recurso al que se está accediendo puede ser accedido
	 * por los roles del usuario
	 * 
	 * @return
	 */
	public boolean validarRecursoxRol() {
		boolean rolvalido = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String strRecursoId = paramMap.get("recursoId");
		Recurso recursoSeleccionado = ComunicacionServiciosSis.recursoPorCodVentana(strRecursoId);
		List<RolDTO> roles = getRolesUsuarioSesion();
		RecursoAccion buscador = new RecursoAccion();
		buscador.setFlgEstado(true);
		buscador.setId(recursoSeleccionado.getCodRecurso().longValue());
		List<RecursoAccion> listaExiste;
		for (RolDTO rol : roles) {
			buscador.setCodRol(BigInteger.valueOf(rol.getId()));
			listaExiste = ComunicacionServiciosSis.getVistaRecursoAccionFiltro(buscador);
			if (!listaExiste.isEmpty()) {
				rolvalido = true;
				break;
			}
		}
		return rolvalido;
	}

	/**
	 * Métdod que valida si un el usuario tiene permisos para acceder al recurso
	 * seleccionadod
	 * 
	 * @return
	 */
	public boolean validarRecursoxusuario() {
		boolean rolvalido = false;
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		String strRecursoId = paramMap.get("recursoId");
		Recurso recursoSeleccionado = ComunicacionServiciosSis.recursoPorCodVentana(strRecursoId);
		RecursoAccionExt buscador = new RecursoAccionExt();
		buscador.setId(recursoSeleccionado.getCodRecurso().longValue());
		buscador.setCodUsuario(new BigDecimal(getUsuarioSesion().getId()));
		List<RecursoAccion> listaExiste = ComunicacionServiciosSis.getVistaRecursoUsuarioAccionFiltro(buscador);
		if (!listaExiste.isEmpty()) {
			rolvalido = true;
		}
		return rolvalido;
	}

}