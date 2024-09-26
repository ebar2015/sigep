package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.NonexistentConversationException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.converter.ExportacionDocumentoConverter;
import co.gov.dafp.sigep2.datamodel.CifrasLazyDataModel;
import co.gov.dafp.sigep2.entities.CifrasEmpleoPublico;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@Named
@ConversationScoped
@ManagedBean
public class SeguimientoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID 	= -461788711703559084L;
	static final double CNS_SEMAFORO_ROJO 		= 0.69;
	static final double CNS_SEMAFORO_AMARILLO 	= 0.89;
	static final double CNS_SEMAFORO_VERDE 		= 1;

	private CifrasEmpleoPublico cifraSeleccionada;
	private CifrasEmpleoPublico filtro;
	private CifrasLazyDataModel listaCifras;

	static final Long SECTOR 					= 1695l;
	static final Long ORDEN 					= 1362l;
	static final Long CLASIFICACION_ORGANICA 	= 1370l;
	static final Long NATURALEZA 				= 1707l;
	static final Long CATEGORIA 				= 1437l;

	private UIInput todosUIInput = null;

	private boolean habilitarConsulta = false;

	private boolean todos = false;

	private Long[] sectoresSeleccionados;

	private boolean habilitarRolesAdmin = false;

	public CifrasEmpleoPublico getFiltro() {
		return filtro;
	}

	public void setFiltro(CifrasEmpleoPublico filtro) {
		this.filtro = filtro;
	}

	public CifrasEmpleoPublico getCifraSeleccionada() {
		return cifraSeleccionada;
	}

	public void setCifraSeleccionada(CifrasEmpleoPublico cifraSeleccionada) {
		this.cifraSeleccionada = cifraSeleccionada;
	}

	public CifrasLazyDataModel getListaCifras() {
		return listaCifras;
	}

	public void setListaCifras(CifrasLazyDataModel listaCifras) {
		this.listaCifras = listaCifras;
	}

	public Long[] getSectoresSeleccionados() {
		return sectoresSeleccionados;
	}

	public void setSectoresSeleccionados(Long[] sectoresSeleccionados) {
		this.sectoresSeleccionados = sectoresSeleccionados;
	}

	public boolean isHabilitarRolesAdmin() {
		return habilitarRolesAdmin;
	}

	public void setHabilitarRolesAdmin(boolean habilitarRolesAdmin) {
		this.habilitarRolesAdmin = habilitarRolesAdmin;
	}

	public boolean isTodos() {
		return todos;
	}

	public void setTodos(boolean todos) {
		this.todos = todos;
	}

	public boolean isHabilitarConsulta() {
		return habilitarConsulta;
	}

	public void setHabilitarConsulta(boolean habilitarConsulta) {
		this.habilitarConsulta = habilitarConsulta;
	}

	/**
	 * Inicializa el buscador de cifras de empleo teniendo en cuenta las
	 * restricciones de seguridad por rol para la funcionalidad
	 */
	public void montarDatos() {
		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}

		filtro.setSeguimiento(true);
		filtro.setTableroSeguimiento(true);

		// Se valida que al menos se haya seleccionado alguna de las tematicas
		if (filtro != null && !filtro.isSeguimiento()) {
			habilitarConsulta = false;
			this.listaCifras.setListaCifras(new LinkedList<>());
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(
					MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_VALOR_REQUERIDO,getLocale()) + ". "
							+ TitlesBundleConstants
									.getStringMessagesBundle(TitlesBundleConstants.TTL_GI_RESUMEN_CARGOS_PUBLICOS));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(todosUIInput.getClientId(), msg);
			fc.renderResponse();
		} else {
			if (todos) {
				marcarTodos();
			}
			this.habilitarConsulta = true;
			listaCifras = new CifrasLazyDataModel(filtro);
		}
	}

	/**
	 * Validador del formulario principal. Es invocado posterior a las
	 * validaciones de tipo definidas en cada componenete del mismo
	 * 
	 * @param event
	 *            Contiene los componentes procesados en el formulario una vez
	 *            se genera el submit
	 */
	@Override
	public void validateForm(ComponentSystemEvent event) throws NotSupportedException {
		UIComponent components = event.getComponent();
		todosUIInput = (UIInput) components.findComponent("todos");
	}

	/**
	 * Inicializa los valores necesarios para el tablero en el formulario
	 */
	public void init() {
		filtro = new CifrasEmpleoPublico();
		filtro.setSeguimiento(true);
		filtro.setTableroSeguimiento(true);

		this.habilitarConsulta = false;
		if (!habilitarRolesAdmin) {
			filtro.setCodEntidad(getEntidadUsuario().getCodigoSigep());
			if (getEntidadUsuario().getCodOrden() != null) {
				filtro.setCodOrden(getEntidadUsuario().getCodOrden().toBigInteger());
			}
		}

		try {
			if (usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES)) {
				this.habilitarConsulta = true;
			} else if (usuarioTieneRolAsignado(RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO,
					RolDTO.OPERADOR_CONTRATOS)) {
				this.habilitarConsulta = false;
				this.filtro.setCodCifrasEmpleoPublico(this.getEntidadUsuario().getId());
			} else {
				return;
			}
		} catch (SIGEP2SistemaException e) {
			logger.error("void init()", e);
		}

		CifrasEmpleoPublico filtroInit = new CifrasEmpleoPublico(this.filtro);
		listaCifras = new CifrasLazyDataModel(filtroInit);
		
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		contexto.getSessionMap().put(ConfigurationBundleConstants.OPT_VIDEO_TUTORIAL_SESSION,ConfigurationBundleConstants.OPT_VIDEO_GESTIONINFORMACION);		
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Construye la conversación entre la vista y el controlador
	 */
	@Override
	public void retrieve() throws NotSupportedException {
		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}
		try {
			if (this.conversation.isTransient()) {
				this.conversation.begin();
				this.conversation.setTimeout(timeOut);
			}
		} catch (NonexistentConversationException e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_URL_INVALID);
		}
		filtro = new CifrasEmpleoPublico();

		try {
			if (!usuarioTieneRolAsignado(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES,
					RolDTO.JEFE_TALENTO_HUMANO, RolDTO.JEFE_CONTROL_INTERNO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
				mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}
			habilitarRolesAdmin = usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL,
					RolDTO.ADMINISTRADOR_ENTIDADES);
		} catch (Exception e) {
			mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_PROCESO_FALLIDO,
					MessagesBundleConstants.DLG_PROCESO_FALLIDO);
			return;
		}
		this.init();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	/**
	 * Valida formato de las celdas de xlsx posterior al exportado de la tabla
	 * en componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xlsx. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLSX(Object document) {
		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow header = sheet.getRow(0);
		
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}

	/**
	 * Valida formato de las celdas de xls posterior al exportado de la tabla en
	 * componentes de exportación de Primefaces a través de la propiedad
	 * <code>postProcessor</code>
	 * 
	 * @param document
	 *            Documento Excel en formato xls. Es importante que el atributo
	 *            <code>type</code> del componente de exportación contenga
	 *            exlusivamente uno de esos dos valores
	 */
	public void postProcessXLS(Object document) {
		ExportacionDocumentoConverter export = new ExportacionDocumentoConverter();
		export.postProcessExcel(document, 1,
				TitlesBundleConstants.getStringMessagesBundle("TTL_GI_BREADCRUMB_SEGUIMIENTO"));
	}

	public void formatearCabecerasXLSX(Object document, XSSFColor color, final int indexFirstRow,
			final int indexFirstColumn, final int indexLastColumn) {
		int indexRowTemp = indexFirstRow - 1;
		int indexFirstColumnTemp = indexFirstColumn - 1;
		int indexLastColumnTemp = indexLastColumn;

		XSSFWorkbook wb = (XSSFWorkbook) document;
		XSSFSheet sheet = wb.getSheetAt(0);

		XSSFRow row = sheet.getRow(indexRowTemp);
		for (int i = indexFirstColumnTemp; i < indexLastColumnTemp; i++) {
			XSSFCell cell = row.getCell(i);
			XSSFCellStyle cellStyle = wb.createCellStyle();
			if (cell != null) {
				if (cell.getCellType() != XSSFCell.CELL_TYPE_NUMERIC) {
					cellStyle.setFillForegroundColor(color);
					cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
					cellStyle.setWrapText(true);
					cellStyle.setLocked(true);
					cell.setCellStyle(cellStyle);
				}
				cellStyle.setBorderBottom(BorderStyle.HAIR);
				cellStyle.setBorderLeft(BorderStyle.HAIR);
				cellStyle.setBorderRight(BorderStyle.HAIR);
				cellStyle.setBorderTop(BorderStyle.HAIR);
			}
		}
	}

	/**
	 * Marca en el valor {@link Boolean} de <code>todos</code> todas las
	 * tematicas de formulario de busqueda
	 */
	public void marcarTodos() {
		this.filtro.setPlanAnualVacantes(todos);
		this.filtro.setLeyCuotas(todos);
		this.filtro.setEmpleos(todos);
		this.filtro.setSeguimiento(todos);
		this.filtro.setAcuerdosGestion(todos);
		this.filtro.setCapacitacion(todos);
		this.filtro.setBienestarIncentivos(todos);
		this.filtro.setHorariosFlexibles(todos);
		this.filtro.setTeletrabajo(todos);
		this.filtro.setBilinguismo(todos);
	}

	/**
	 * Devuelve el color del semaforo de acuerdo a <code>valor</code> teniendo
	 * en cuenta la escala de valores definidas
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} color del semaforo de acuerdo al rango al que
	 *         aplique <code>valor</code>
	 */
	public static String getSemaforo(BigDecimal valor) {
		if (valor == null) {
			return "";
		}

		if (valor.doubleValue() <= CNS_SEMAFORO_ROJO) {
			return "rojo";
		} else if (valor.doubleValue() <= CNS_SEMAFORO_AMARILLO) {
			return "amarillo";
		} else if (valor.doubleValue() <= CNS_SEMAFORO_VERDE) {
			return "verde";
		} else {
			return "";
		}
	}

	/**
	 * Determina si <code>valor</code> se encuentra contiene un valor que se
	 * considera un error de calculo. El texto devuelto se utiliza para la
	 * marcacion de los estilos css de la celda en la que se contine el dato a
	 * evaluar
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} nombre de la clase css que marca el error en la
	 *         celda
	 */
	public String getError(BigDecimal valor) {
		if (valor != null) {
			Double noVinculado = valor.doubleValue() * 100;
			if (noVinculado.intValue() > 100) {
				return " seguimiento-error";
			}
		} else {
			return "";
		}
		return "";
	}

	/**
	 * Determina si <code>valor</code> se encuentra contiene un valor que se
	 * considera un error de calculo si este es mayor a <code>100%</code>. El
	 * texto devuelto se utiliza para la marcacion de los estilos css de la
	 * celda en la que se contine el dato a evaluar
	 * 
	 * @param valor
	 *            Valor a evaluar
	 * @return {@link String} nombre de la clase css que marca el error en la
	 *         celda
	 */
	public String getNoVinculado(BigDecimal valor) {
		if (valor != null) {
			Double noVinculado = valor.doubleValue() * 100;
			if (noVinculado.intValue() == -1) {
				return " seguimiento-no-vinculado";
			}
		} else {
			return "";
		}
		return "";
	}

	/**
	 * Devuelve la lista de Sectores Administrativos
	 * 
	 * @return {@link List} de {@link Parametrica} de Sectores Administrativos
	 */
	public List<Parametrica> getListaSectores() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(SECTOR);
	}

	/**
	 * Devuelve la lista de Orden de Entidad
	 * 
	 * @return {@link List} de {@link Parametrica} de Orden de Entidad
	 */
	public List<Parametrica> getListaOrden() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(ORDEN);
	}

	/**
	 * Devuelve la lista de CLasificacion Organica
	 * 
	 * @return {@link List} de {@link Parametrica} de CLasificacion Organica
	 */
	public List<Parametrica> getListaClasificacionOrganica() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(CLASIFICACION_ORGANICA);
	}

	/**
	 * Devuelve la lista de Naturaleza
	 * 
	 * @return {@link List} de {@link Parametrica} de Naturaleza
	 */
	public List<Parametrica> getListaNaturalezaJuridica() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(NATURALEZA);
	}

	/**
	 * Devuelve la lista de Naturaleza
	 * 
	 * @return {@link List} de {@link Parametrica} de Categoria de la Entidad
	 */
	public List<Parametrica> getListaCategoria() {
		return ComunicacionServiciosSis.getParametricaPorIdPadre(CATEGORIA);
	}
}
