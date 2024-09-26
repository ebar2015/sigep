package co.gov.dafp.sigep2.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.PermisoProduces;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.entities.Recurso;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.sistema.produces.XmlReporteProduces;
import co.gov.dafp.sigep2.util.StringEncrypt;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.FormaConsulta;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;

@Named
@SessionScoped
public class MenuBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -6477935567048530468L;

	
	
	private Boolean mostrarMenuResumen = false;

	transient MenuModel menu;

	transient MenuModel menuResponsive;

	public Boolean getMostrarMenuResumen() {
		return mostrarMenuResumen;
	}

	public void setMostrarMenuResumen(Boolean mostrarMenuResumen) {
		this.mostrarMenuResumen = mostrarMenuResumen;
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public MenuModel getMenuResponsive() {
		return menuResponsive;
	}

	public void setMenuResponsive(MenuModel menuResponsive) {
		this.menuResponsive = menuResponsive;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@PostConstruct
	public void init() {
		try {
			this.generarMenu();
		} catch (SIGEP2SistemaException e) {
			logger.error(MessagesBundleConstants.DLG_PROCESO_FALLIDO, e);
		}
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	private void generarMenu() throws SIGEP2SistemaException {
	
		try {
			UsuarioDTO usuarioActual = this.getUsuarioSesion();


			if (usuarioActual == null) {
				return;
			}

			if (getEntidadUsuario() == null) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuBean", this);
				return;
			}

			/*LFP - Obtiene los roles del usuario ordenados por la jerarquia*/
			this.setRolesUsuarioSesion(IngresoSistemaDelegate.obtenerRolesPorUsuario(usuarioActual.getId(), this.getEntidadUsuario().getId()));

			if (this.getRecursoDTOsHabilitados() == null) {
				List<RecursoActivoPerfilUsuarioDTO> recursosHabilitadosTemp = IngresoSistemaDelegate
						.obtenerRecursosActivosPorUsuario(usuarioActual.getId(), this.getEntidadUsuario().getId());
				List<RecursoActivoPerfilUsuarioDTO> recursosHabilitados = new ArrayList<>();
				for (RecursoActivoPerfilUsuarioDTO recursoHabilitado : recursosHabilitadosTemp) {
					if (!recursosHabilitados.contains(recursoHabilitado)) {
						
						if ("TTL_SUB_MI_HOJA_VIDA".equalsIgnoreCase(recursoHabilitado.getDescripcion()) && recursoHabilitado.getRol() == 3) { 
														
							PersonaExt persona = ComunicacionServiciosHV.getPersonaporIdExt(getUsuarioSesion().getCodPersona());
							String password = getUsuarioSesion().getContrasena();	
							Parametrica parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5945));
							String urlByrHv = parametrica.getValorParametro().trim() ;
							String data = getUsuarioSesion().getNumeroIdentificacion().trim() + ";" + persona.getCodTipoIdentificacion().trim() + ";" + password.trim(); 
							String datoEncriptado = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), data);
							
							/* Se codifica la cadena encriptada con UTF-8 para evitar errores en la codificacion-decodificacion*/
							String datoEncriptadoEncoded = URLEncoder.encode(datoEncriptado, "UTF-8");
							String nroIdentificacion  =      datoEncriptadoEncoded ;	
						
							recursoHabilitado.setPagina(urlByrHv + nroIdentificacion);												
							
						}else if ( ("TTL_MIDECLARACIONEHISTORICODEBIENESYRENTAS".equalsIgnoreCase(recursoHabilitado.getDescripcion()) ||
								"TTL_SUB_MI_HOJA_VIDA".equalsIgnoreCase(recursoHabilitado.getDescripcion()) ) &&
								recursoHabilitado.getRol() == 4) { 
							
								PersonaExt persona = ComunicacionServiciosHV.getPersonaporIdExt(getUsuarioSesion().getCodPersona());
								String password = getUsuarioSesion().getContrasena();							
								Parametrica parametrica = null;
								if("TTL_SUB_MI_HOJA_VIDA".equalsIgnoreCase(recursoHabilitado.getDescripcion())) {
									parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5945));
								}else if ("TTL_MIDECLARACIONEHISTORICODEBIENESYRENTAS".equalsIgnoreCase(recursoHabilitado.getDescripcion())){
									parametrica = ComunicacionServiciosSis.getParametricaporId(new BigDecimal(5946));
								}							
								String urlByrHv = parametrica.getValorParametro().trim() ;
								String data = getUsuarioSesion().getNumeroIdentificacion().trim() + ";" + persona.getCodTipoIdentificacion().trim() + ";" + password.trim(); 
								String datoEncriptado = StringEncrypt.encrypt(ConfigurationBundleConstants.key(), ConfigurationBundleConstants.iv(), data);
								String datoEncriptadoEncoded = URLEncoder.encode(datoEncriptado, "UTF-8");
								String nroIdentificacion  =      datoEncriptadoEncoded ;	
								
								
								recursoHabilitado.setPagina(urlByrHv + nroIdentificacion);							
								
						}				
						recursosHabilitados.add(recursoHabilitado);
					}
				}
				this.setRecursoDTOsHabilitados(recursosHabilitados);
			}

			this.menu = new DefaultMenuModel();
			this.menuResponsive = new DefaultMenuModel();

			try {
				if (this.getRolesUsuarioSesion() != null && this.getRolesUsuarioSesion().isEmpty()) {
					RolDTO r = new RolDTO();
					r.setNombre(MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.DLG_SIN_REGISTROS,
							getLocale()));
					this.getRolesUsuarioSesion().add(r);
					finalizarConversacion(true, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
					return;
				}

				if (this.getRecursoDTOsHabilitados() != null && this.getRecursoDTOsHabilitados().isEmpty()) {
					finalizarConversacion(true, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
					return;
				}

				if (usuarioTieneRolAsignadoSinJerarquia(RolDTO.SISTEMA)) {
					finalizarConversacion(true, MessagesBundleConstants.DLG_HEADER_MENSAJES,
							MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
					return;
				}
			} catch (IOException e1) {
				mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES,
						MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO);
				return;
			}

			RecursoActivoPerfilUsuarioDTO inicialResponsive = IngresoSistemaDelegate
					.findByCodigoVentana(PermisoProduces.MENU_INICIO, getUsuarioSesion().getId());
			inicialResponsive.setDescripcion("");

			// Se cargan los reportes publicados y que tengan configurados roles
			// para el usuario
			List<RecursoActivoPerfilUsuarioDTO> recursosReporte = new LinkedList<>();
			try {
				XmlReporteProduces xmlReporteProduces = new XmlReporteProduces();
				List<XmlReporte> reportes = xmlReporteProduces.getReportesPublicadosCatalogo();
				boolean cargarMenuReportes = false;
				for (XmlReporte reporte : reportes) {
					for (String rolParaReporte : reporte.getRol()) {
						if (usuarioTieneRolAsignadoSinJerarquia(rolParaReporte)) {
							cargarMenuReportes = true;
							break;
						}
					}
					if (cargarMenuReportes) {
						break;
					}
				}
				if (cargarMenuReportes) {
					RecursoActivoPerfilUsuarioDTO gestionarInformacionSubMenu = IngresoSistemaDelegate
							.findByCodigoVentana("GestionarInformacionSubMenu", getUsuarioSesion().getId());
					if (!this.getRecursoDTOsHabilitados().contains(gestionarInformacionSubMenu) && gestionarInformacionSubMenu!=null) {
						recursosReporte.add(gestionarInformacionSubMenu);
					}

					RecursoActivoPerfilUsuarioDTO gestionarReportesCatalogoSubMenu = IngresoSistemaDelegate
							.findByCodigoVentana("GestionarReportesCatalogoSubMenu", getUsuarioSesion().getId());
					if (!this.getRecursoDTOsHabilitados().contains(gestionarReportesCatalogoSubMenu) && gestionarReportesCatalogoSubMenu!=null) {
						recursosReporte.add(gestionarReportesCatalogoSubMenu);
					}

					Recurso reporteCatalogoTag = ComunicacionServiciosSis.recursoPorCodVentana("ReporteCatalogoTag");
					Recurso catalogoReportesSubMenu = ComunicacionServiciosSis
							.recursoPorCodVentana("CatalogoReportesSubMenu");
					long orden = reporteCatalogoTag.getOrden().longValue() + 100;

					boolean agregadoCatalogoMenu = false;
					for (XmlReporte reporte : reportes) {
						if (!reporte.getRol().isEmpty()) {
							for (String rol : reporte.getRol()) {
								if (TipoPlantilla.REPORTE.equals(reporte.getTipoPlantilla())
										&& FormaConsulta.TTL_REPORTES_FORMA_CONSULTA_LOCAL
												.equals(reporte.getFormaConsulta())
										&& usuarioTieneRolAsignadoSinJerarquia(rol)) {

									if (agregadoCatalogoMenu) {
										break;
									}

									RecursoActivoPerfilUsuarioDTO recursoReporteTemp = new RecursoActivoPerfilUsuarioDTO();
									recursoReporteTemp.setEntidad(getEntidadUsuario().getId());
									recursoReporteTemp.setId(reporte.getId().longValue());
									recursoReporteTemp.setOrden(orden);
									recursoReporteTemp.setTituloVentana(reporte.getNombre());

									recursoReporteTemp.setCodigoVentana(catalogoReportesSubMenu.getCodigoVentana());
									recursoReporteTemp.setDescripcion(catalogoReportesSubMenu.getDescripcion());
									recursoReporteTemp.setPagina(catalogoReportesSubMenu.getPagina());
									recursoReporteTemp
											.setCodigoPadre(catalogoReportesSubMenu.getCodRecursoPadre().longValue());
									recursosReporte.add(recursoReporteTemp);
									agregadoCatalogoMenu = true;
									orden++;
									break;
								}
							}
						}
						if (agregadoCatalogoMenu) {
							break;
						}
					}
					this.getRecursoDTOsHabilitados().addAll(recursosReporte);

					Collections.sort(this.getRecursoDTOsHabilitados(), new Comparator<RecursoActivoPerfilUsuarioDTO>() {
						@Override
						public int compare(RecursoActivoPerfilUsuarioDTO c1, RecursoActivoPerfilUsuarioDTO c2) {
							return c1.getOrden().compareTo(c2.getOrden());
						}
					});
				}
			} catch (Exception e) {
				logger.error("void generarMenu()", e);
			}

			// Agregar recursos para el usuario
			if (this.getRecursoDTOsHabilitados() != null && !this.getRecursoDTOsHabilitados().isEmpty()) {
				if (this.getRecursoDTOsHabilitados().size() == 1) {
					DefaultMenuItem elemento = crearItem(this.getRecursoDTOsHabilitados().get(0));
					if (elemento != null) {
						this.menu.addElement(elemento);
						this.menuResponsive.addElement(elemento);
					}
				} else {
					// Evaluar si hay arbol y crearlo
					List<RecursoActivoPerfilUsuarioDTO> aux = new ArrayList<>();
					aux.addAll(this.getRecursoDTOsHabilitados());

					List<RecursoActivoPerfilUsuarioDTO> auxResponsive = new LinkedList<>();
					auxResponsive.add(inicialResponsive);
					auxResponsive.addAll(aux);

					while (!aux.isEmpty()) {
						RecursoActivoPerfilUsuarioDTO inicial = aux.get(0);
						MenuElement elemento = evaluarElemento(inicial, aux);
						if (elemento != null)
							this.menu.addElement(elemento);
					}

					while (!auxResponsive.isEmpty()) {
						RecursoActivoPerfilUsuarioDTO inicial = auxResponsive.get(0);
						MenuElement elementoResponsive = evaluarElemento(inicial, auxResponsive);
						if (elementoResponsive != null)
							this.menuResponsive.addElement(elementoResponsive);
					}
				}
				try {
					boolean nulos = false;
					for (MenuElement e : menu.getElements()) {
						if (e == null) {
							nulos = true;
							break;
						}
						logger.info("", e.toString());
					}
					if (!nulos) {
						menu.generateUniqueIds();
						menuResponsive.generateUniqueIds();
					}
				} catch (Exception e) {
					logger.error("", e);
				}
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("menuBean", this);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para crear un Item de Menu a partir de un recurso asociado
	 *
	 * @param r
	 *            RecursoActivoPerfilUsuarioDTO para crear el item del menu
	 * @return Item creado
	 */
	private DefaultMenuItem crearItem(RecursoActivoPerfilUsuarioDTO r) {
		DefaultMenuItem i = new DefaultMenuItem();
		String label = TitlesBundleConstants.getStringMessagesBundle(r.getDescripcion(), getLocale());
		String title = TitlesBundleConstants.getStringMessagesBundle(r.getDescripcion(), getLocale());

		i.setId(r.getId().toString());
		i.setValue(label);
		if (r.getPagina() == null) {
			i.setIcon(r.getIconoMenu());
		}

		i.setTitle(TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_GO_TO, getLocale()) + " "
				+ title);
		if (r.getPagina() != null) {
			logger.log().debug("DefaultMenuItem crearItem(RecursoActivoPerfilUsuarioDTO r)", r.getPagina());
			if ("CatalogoReportesSubMenu".equals(r.getCodigoVentana())) {
				i.setHref(r.getPagina());
			}else if ("TTL_SUB_MI_HOJA_VIDA".equalsIgnoreCase(r.getDescripcion()) && r.getRol() == 3L) {								
				i.setHref(r.getPagina());									
			}else if ( ("TTL_MIDECLARACIONEHISTORICODEBIENESYRENTAS".equalsIgnoreCase(r.getDescripcion()) ||
					"TTL_SUB_MI_HOJA_VIDA".equalsIgnoreCase(r.getDescripcion())) && r.getRol() == 4L) {				
				i.setHref(r.getPagina());					
			}else {
				i.setOutcome(r.getPagina());
			}
			i.setResetValues(true);
			i.setParam("recursoId", r.getCodigoVentana());
			i.setUpdate("@form");
			i.setAjax(true);
		}
		return i;
	}

	/**
	 * Metodo para hacer la evaluacion de un elemento en la lista suministrada.
	 * Retorna un arbol (DefaultSubMenu que agrega otros elementos de tipo
	 * MenuElement), o una hoja (DefaultMenuItem), dependiendo de que hayan
	 * elementos en la lista que dependan de ï¿½l.
	 *
	 * @param posiblePadre
	 *            RecursoActivoPerfilUsuarioDTO para el cual se hace la
	 *            evaluaciï¿½n del tipo de elemento
	 * @param listaRecursoDTOs
	 *            Listado de RecursoDTOs donde se hace la busqueda de los
	 *            elementos dependientes
	 * @return MenuElement que serï¿½ agregado al Menu principal
	 */
	private MenuElement evaluarElemento(RecursoActivoPerfilUsuarioDTO posiblePadre,
			List<RecursoActivoPerfilUsuarioDTO> listaRecursoDTOs) {
		if (!listaRecursoDTOs.isEmpty()) {
			List<RecursoActivoPerfilUsuarioDTO> hijos = buscarHijos(posiblePadre, listaRecursoDTOs);
			if (!hijos.isEmpty()) {
				DefaultSubMenu arbol = crearMenu(posiblePadre);
				listaRecursoDTOs.remove(posiblePadre);
				for (RecursoActivoPerfilUsuarioDTO r : hijos) {
					MenuElement element = evaluarElemento(r, listaRecursoDTOs);
					arbol.addElement(element);
				}
				return arbol;
			} else {
				DefaultMenuItem hoja = crearItem(posiblePadre);
				if (mostrarMenuResumen && getModuloSeleccionado() != null) {
					hoja.setOutcome(posiblePadre.getPagina() + "?modulo=" + getModuloSeleccionado().getId());
				}
				listaRecursoDTOs.remove(posiblePadre);
				return hoja;
			}
		}
		return null;
	}

	/**
	 * Metodo para hacer la busqueda de los hijos de un
	 * RecursoActivoPerfilUsuarioDTO en un listado de recursos
	 *
	 * @param padre
	 *            RecursoActivoPerfilUsuarioDTO para el cual se hace la busqueda
	 *            de RecursoDTOs
	 * @param listaRecursoDTOs
	 *            Lista de recursos para buscar
	 * @return Listado de recursos encontrados, que son hijos del
	 *         RecursoActivoPerfilUsuarioDTO padre
	 */
	private List<RecursoActivoPerfilUsuarioDTO> buscarHijos(RecursoActivoPerfilUsuarioDTO padre,
			List<RecursoActivoPerfilUsuarioDTO> listaRecursoDTOs) {
		if (padre == null) {
			return new ArrayList<>();
		}
		List<RecursoActivoPerfilUsuarioDTO> listado = new ArrayList<>();
		for (RecursoActivoPerfilUsuarioDTO recurso : listaRecursoDTOs) {
			if (recurso.getCodigoPadre() == null) {
				continue;
			}
			if (Objects.equals(recurso.getCodigoPadre(), padre.getId())) {
				listado.add(recurso);
			}
		}
		return listado;
	}

	/**
	 * Metodo para crear un SubMenu a partir de un recurso asociado
	 *
	 * @param g
	 *            RecursoActivoPerfilUsuarioDTO para crear el menu
	 * @return Menu creado
	 */
	private DefaultSubMenu crearMenu(RecursoActivoPerfilUsuarioDTO g) {
		DefaultSubMenu m = new DefaultSubMenu();
		m.setLabel(TitlesBundleConstants.getStringMessagesBundle(g.getDescripcion(), getLocale()));
		m.setIcon(g.getIconoMenu());
		return m;
	}
}
