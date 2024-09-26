package co.gov.dafp.sigep2.mbean.entidad;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import co.gov.dafp.sigep2.constante.MessagesBundleConstants;
import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Cargo;
import co.gov.dafp.sigep2.entities.Denominacion;
import co.gov.dafp.sigep2.entities.Entidad;
import co.gov.dafp.sigep2.entities.EntidadEscision;
import co.gov.dafp.sigep2.entities.NomenclaturaDenominacion;
import co.gov.dafp.sigep2.entities.Norma;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.EntidadExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaDetalleExt;
import co.gov.dafp.sigep2.mbean.ext.EntidadPlantaExt;
import co.gov.dafp.sigep2.mbean.ext.VinculacionExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosEnt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosHV;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosVin;
import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@ManagedBean(name = "escisionEntidadBean")
@ViewScoped
@Named
public class EscisionEntidadBean extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Entidad> listaEntidades;
	private List<Entidad> entidadesSeleccionadas;
	private boolean estSeccPpals;
	private boolean estadoBtnEscindir;
	private Entidad entidadProceso;
	private Entidad entidadContexto;
	private List<VinculacionExt> listaEmpleados;
	private int codEntidadSeleccionada;
	private Map<String,String> entidadContextoMostrar;
	private List<VinculacionExt> listaAsignaciones;
	private VinculacionExt empleadoSeleccionado;
	private List<VinculacionExt> listaEmpleadosAux;
	private List<VinculacionExt> listaDesasignaciones;
	private boolean guardaParcialDisponible;
	private Integer codTipoNorma; 
	private Date fechaNorma;
	private Integer numeroNorma;
	private Integer codNorma;
	private static final String TEXTO_ERROR = "Error";
	private boolean cargosVacantes; //false = vacantes
	private Norma filtroNorma;
	
	public boolean isEstadoBotonRegresar() {
		return estadoBotonRegresar;
	}

	public void setEstadoBotonRegresar(boolean estadoBotonRegresar) {
		this.estadoBotonRegresar = estadoBotonRegresar;
	}

	private boolean estadoBotonRegresar;
	
	public VinculacionExt getEmpleadoSeleccionado() {
		return empleadoSeleccionado;
		
	}
	
	public void setEmpleadoSeleccionado(VinculacionExt empleadoSeleccionado) {
		this.empleadoSeleccionado = empleadoSeleccionado;
	}
	
	public List<VinculacionExt> getListaAsignaciones() {
		return listaAsignaciones;
	}

	public void setListaAsignaciones(List<VinculacionExt> listaAsignaciones) {
		this.listaAsignaciones = listaAsignaciones;
	}

	public Map<String, String> getEntidadContextoMostrar() {
		return entidadContextoMostrar;
	}

	public void setEntidadContextoMostrar(Map<String, String> entidadContextoMostrar) {
		this.entidadContextoMostrar = entidadContextoMostrar;
	}

	public List<Entidad> getListaEntidades() {
		
		return listaEntidades;
	}

	public void setListaEntidades(List<Entidad> listaEntidades) {
		this.listaEntidades = listaEntidades;
	}

	public List<Entidad> getEntidadesSeleccionadas() {
		return entidadesSeleccionadas;
	}

	public void setEntidadesSeleccionadas(List<Entidad> entidadesSeleccionadas) {
		this.entidadesSeleccionadas = entidadesSeleccionadas;
	}

	public boolean isEstSeccPpals() {
		return estSeccPpals;
	}

	public void setEstSeccPpals(boolean estSeccPpals) {
		this.estSeccPpals = estSeccPpals;
	}

	public Entidad getEntidadProceso() {
		return entidadProceso;
	}

	public void setEntidadProceso(Entidad entidadProceso) {
		this.entidadProceso = entidadProceso;
	}

	public Entidad getEntidadContexto() {
		return entidadContexto;
	}

	public void setEntidadContexto(EntidadExt entidadContexto) {
		this.entidadContexto = entidadContexto;
	}

	public List<VinculacionExt> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(List<VinculacionExt> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}
	
	public int getCodEntidadSeleccionada() {
		return codEntidadSeleccionada;
	}

	public void setCodEntidadSeleccionada(int codEntidadSeleccionada) {
		this.codEntidadSeleccionada = codEntidadSeleccionada;
	}
	
	public boolean isEstadoBtnEscindir() {
		return estadoBtnEscindir;
	}

	public void setEstadoBtnEscindir(boolean estadoBtnEscindir) {
		this.estadoBtnEscindir = estadoBtnEscindir;
	}
	
	public List<VinculacionExt> getListaEmpleadosAux() {
		return listaEmpleadosAux;
	}

	public void setListaEmpleadosAux(List<VinculacionExt> listaEmpleadosAux) {
		this.listaEmpleadosAux = listaEmpleadosAux;
	}
	
	public boolean isGuardaParcialDisponible() {
		return guardaParcialDisponible;
	}

	public void setGuardaParcialDisponible(boolean guardaParcialDisponible) {
		this.guardaParcialDisponible = guardaParcialDisponible;
	}
	
	public List<VinculacionExt> getListaDesasignaciones() {
		return listaDesasignaciones;
	}

	public void setListaDesasignaciones(List<VinculacionExt> listaDesasignaciones) {
		this.listaDesasignaciones = listaDesasignaciones;
	}
	
	public Integer getCodNorma() {
		return codNorma;
	}

	public void setCodNorma(Integer codNorma) {
		this.codNorma = codNorma;
	}
	
	public Integer getCodTipoNorma() {
		return codTipoNorma;
	}

	public void setCodTipoNorma(Integer codTipoNorma) {
		this.codTipoNorma = codTipoNorma;
	}

	public Date getFechaNorma() {
		return fechaNorma;
	}

	public void setFechaNorma(Date fechaNorma) {
		this.fechaNorma = fechaNorma;
	}

	public Integer getNumeroNorma() {
		return numeroNorma;
	}

	public void setNumeroNorma(Integer numeroNorma) {
		this.numeroNorma = numeroNorma;
	}
	
	@Override
	public String persist() throws NotSupportedException {
		this.guardadoParcial();
		return null;
	}

	@Override
	public void retrieve() throws NotSupportedException {
		this.cargaGuardadoParcial();
	}

	@Override
	public String update() throws NotSupportedException {
		this.escindir();
		return null;
	}

	@Override
	public void delete() throws NotSupportedException {
		// TODO Auto-generated method stub
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		// TODO Auto-generated method stub
	}
	
	public Norma getFiltroNorma() {
		return filtroNorma;
	}

	public void setFiltroNorma(Norma filtroNorma) {
		this.filtroNorma = filtroNorma;
	}

	@Override
	public void init() {
		if(!validarPermisoRolEscindirEntidad())
    		mostrarMensajeBotonAccion(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_FATAL_RECURSO_NO_AUTORIZADO, TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('entidad/../../gestionarEntidad.xhtml?faces-redirect=true')"); 	
	}

	public EscisionEntidadBean() {
		cerrarSessionFuncion(AMBITO_POLITICAS);
		try {
			init();
			this.estadoBotonRegresar = true;
			Entidad entidadFiltro = new Entidad();
			entidadFiltro.setCodEstadoEntidad(new BigDecimal(1480));
			this.listaEntidades = ComunicacionServiciosEnt.getEntidadesFiltro(entidadFiltro);
			Entidad iter = new Entidad();
			BigDecimal codEnt = (BigDecimal) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("codEntidad");
			String codEntidad = codEnt.toString(); 
			this.entidadProceso = ComunicacionServiciosEnt.getEntidadPorId(Integer.parseInt(codEntidad));
			if(this.entidadProceso == null) { 
				this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "No se ha logrado obtener la entidad en proceso, no se puede realizar la escisión. Consulte con un administrador.",TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('../../index.xhtml?faces-redirect=true')");
				return;
			}
			if(this.entidadProceso.getCodEstadoEntidad().intValue() == 1481) {
				this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "La entidad se encuentra inactiva y no se puede escindir.",TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('../../index.xhtml?faces-redirect=true')");
				return;
			}
			for(int i = 0; i < this.listaEntidades.size(); i++) {
			iter = this.listaEntidades.get(i);
				if(iter.getCodEntidad().longValue() == this.entidadProceso.getCodEntidad().longValue()) {
					this.listaEntidades.remove(i);
					break;
				}
			}	
			this.entidadContextoMostrar = new HashMap<>();
			this.listaAsignaciones = new ArrayList<>();
			this.listaDesasignaciones = new ArrayList<>();
			if(this.entidadProceso.getCodSubestadoEntidad() != null)
				this.guardaParcialDisponible = this.entidadProceso.getCodSubestadoEntidad().longValue() == 1980;
			else
				this.guardaParcialDisponible = false;
			this.filtroNorma = new Norma();
			if(this.guardaParcialDisponible) {
				this.cargaGuardadoParcial();
				return;
			}
			this.estSeccPpals = true;
			this.estadoBtnEscindir = false;
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_ERROR_CARGA  + ex.getMessage());
			EscisionEntidadBean.logger.error("Error EscisionEntidadBean.java public EscisionEntidadBean(): " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}
	
	public void regresarMenu() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
		} catch (IOException ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, TEXTO_ERROR, "Error: " + ex.getMessage());
		}
	}
	
	public void pasoAtras() {
		this.estSeccPpals = true;
		this.listaAsignaciones = this.listaDesasignaciones = this.listaEmpleados = null;
	}
	
	private void configurarMenu() {
		for(Entidad e : this.entidadesSeleccionadas) {
			if(e.getCodSubestadoEntidad() != null && e.getCodSubestadoEntidad() == 1980) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_EN_ESTADO_ESCISION);
				return;
			}
			VinculacionExt filtroBusqueda = new VinculacionExt();
			filtroBusqueda.setCodEntidad(e.getCodEntidad().longValue());
			filtroBusqueda.setFlgActivo((short) 0);
			List<VinculacionExt> vinTemporales = ComunicacionServiciosVin.getVinculacionExportacion(filtroBusqueda);
			for(VinculacionExt filtroBus : vinTemporales) {
				if(filtroBus.getFlgGuardadoParcial() != null && filtroBus.getFlgGuardadoParcial() == 1) {
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Cargos temporales sin resolver" , "La entidad destino tine cargos con guardado parcial, procesos sin terminar por favor verifique finalize los procesos que tiene pendientes en esta o seleccione otra entidad");
					return;
				}
			}
			this.entidadContextoMostrar.put(e.getNombreEntidad(), e.getCodEntidad().toString());
		}
	}

	public void continuarProcesoEscinsion(){
		this.entidadContextoMostrar.clear();
		try {
			if(this.entidadesSeleccionadas.isEmpty()) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_SELECCIONE_ENTIDAD );
				return;
			}
			if(this.entidadesSeleccionadas.size() <= 1) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Debe seleccionar mínimo dos entidades de destino" );
				return;
			}
			for(Entidad enSel : this.entidadesSeleccionadas) {
				if(this.validacionEntidadSinPlanta(enSel.getCodEntidad().longValue())) {
					this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, enSel.getNombreEntidad() + ": Esta entidad no tiene planta por favor seleccione otra o cree una planta para esta entidad y vuelva a intentarlo");
					return;
				}
			}
			List<Entidad> entidadProce = new ArrayList<>();
			entidadProce.add(this.entidadProceso);
			this.cargosVacantes = true;
			if(this.validarDisponibilidadVacantes(this.entidadesSeleccionadas) < this.validarDisponibilidadVacantes(entidadProce)) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "La cantidad de plazas en las entidades de destino es inferior a la cantidad de cargos en la entidad de origen");
				return;
			}
			this.cargosVacantes = false;
			if(this.validarDisponibilidadVacantes(this.entidadesSeleccionadas) < this.validarDisponibilidadVacantes(entidadProce)) {
				this.mostrarMensaje(FacesMessage.SEVERITY_WARN, "Estimado usuario", "La cantidad de vacantes en las entidades de destino no es suficiente para cubrir todos los cargos de la entidad");
				return;
			}
			this.configurarMenu();
			this.estSeccPpals = false;
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage() + " " + ex.getCause());
			return;
		}
		VinculacionExt vin = new VinculacionExt();
		vin.setCodEntidad(this.entidadProceso.getCodEntidad().longValue());
		vin.setFlgActivo((short) 1);
		this.listaEmpleados = ComunicacionServiciosVin.getVinculacionExportacion(vin);
		vin.setFlgActivo((short) 0);
		this.listaEmpleadosAux = ComunicacionServiciosVin.getVinculacionExportacion(vin);
	}
	
	public void establecerEntidadContexto() {
		this.entidadContexto = ComunicacionServiciosEnt.getEntidadPorId(this.codEntidadSeleccionada);
		for(VinculacionExt v : this.listaEmpleados)
			v.setCodCargoDestino(null);
	}
	
	
	public Long validarCargosPlanta(Long codPlanta){
		try {
			EntidadPlantaDetalleExt plantaDet = new EntidadPlantaDetalleExt();
			plantaDet.setCodEntidadPlanta(new BigDecimal(codPlanta));
			plantaDet.setFlgActivo((short) 1);
			List<EntidadPlantaDetalleExt> listaPla = ComunicacionServiciosVin.getEntidadPlantaDetalleFilter(plantaDet);
			if(!listaPla.isEmpty()) {
				long contador = 0;
				for(EntidadPlantaDetalleExt det : listaPla) {
					if(!cargosVacantes)
						contador += det.getVacantes();
					else 
						contador += det.getCantidadCargo();
				}
				return contador;
			}
			return 0L;
		}
		catch(Exception ex) {
			return 0L;
		}
	}
	
	public Long validarDisponibilidadVacantes(List<Entidad> entidEnProce){
		long cantVacanDes = 0;
		for(Entidad e : entidEnProce) {
			EntidadPlantaExt enPlaFil = new EntidadPlantaExt();
			enPlaFil.setCodEntidad(e.getCodEntidad().longValue());
			enPlaFil.setFlgActivo((short) 1);
			List<EntidadPlantaExt> listaPlantas = ComunicacionServiciosVin.getEntidadPlantaFilter(enPlaFil);
			for(EntidadPlantaExt planta : listaPlantas)
				cantVacanDes += this.validarCargosPlanta(planta.getCodEntidadPlanta());
		}
		return cantVacanDes;
	}
	
	public long obtenerCodEntidadPlantaDetalle(Long codCargo) throws EntidadExcepcion {
		try {
			return 1;
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_ERROR_SIN_PLANTA );
			throw new EntidadExcepcion("Error sin planta de personal method: obtenerCodEntidadPlantaDetalle() class: EscisionEntidadBean");
		}
	}
	
	public void almacenarPersonaLista(VinculacionExt v) {
		try {
			this.empleadoSeleccionado = v;
			if(this.entidadContexto == null || (this.entidadContexto != null && this.entidadContexto.getCodEntidad() == null) ) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_ERROR_MINIMO_UNA_ENTIDAD );
				return;
			}
			VinculacionExt vin = new VinculacionExt();
			vin.setFlgActivo((short) 0);
			vin.setCodPersona(this.empleadoSeleccionado.getCodPersona());
			vin.setCodEntidadPlantaDetalle(new BigDecimal(obtenerCodEntidadPlantaDetalle(v.getCodCargo())));
			vin.setCodCargoDestino(this.empleadoSeleccionado.getCodCargoDestino());
			vin.setFlgMedicoDocente(this.empleadoSeleccionado.getFlgMedicoDocente());
			this.configurarAuditoria(vin);
			this.listaAsignaciones.add(vin);
			this.retirEmpleaEntidAnter();
		}catch(EntidadExcepcion ex) {
			logger.error("Error almacenarPersonaLista() EscisionEntidadBean" , ex);
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
			logger.error("Error almacenarPersonaLista() EscisionEntidadBean" , ex);
		}
	}
	
	private void configurarAuditoria(VinculacionExt vin) {
		vin.setAudAccion(785);
		vin.setAudFechaActualizacion(new Date());
		vin.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
		vin.setAudCodRol(new BigDecimal(this.getUsuarioSesion().getCodRol()));
	}
	
	private void retirEmpleaEntidAnter() {
		VinculacionExt v;
		try {
			if(this.listaEmpleados.size() == 1) {
				configurarAuditoria(this.listaEmpleados.get(0));
				this.listaEmpleados.get(0).setFechaFinalizacion(new Date());
				this.listaEmpleados.get(0).setFlgActivo((short) 0);
				this.listaDesasignaciones.add(this.listaEmpleados.get(0));
				this.listaEmpleados.remove(0);
			}
			for(int i = 0; i < (this.listaEmpleados.size() - 1); i++) {
				v = this.listaEmpleados.get(i);
				if(v.getCodPersona() == this.empleadoSeleccionado.getCodPersona()) {
					configurarAuditoria(v);
					v.setFechaFinalizacion(new Date());
					v.setFlgActivo((short) 0);
					this.listaDesasignaciones.add(this.listaEmpleados.get(i));
					this.listaEmpleados.remove(i);
					break;
				}
			}
		}
		catch(IndexOutOfBoundsException ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.ENT_ESC_MSG_ERROR_SIN_REGISTROS_PARA_ASIGNAR + ex.getMessage());
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_WARN, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
		}
	}
	
	public void escindir() {
		try {
			if((this.listaAsignaciones.size() == 0 && this.listaEmpleados.size() != 0) || (this.listaDesasignaciones.size() != this.listaAsignaciones.size())) {
				this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES , "No ha realizado la reasignación de todos los cargos.");
				return;
			}
			if(!this.verificarCamposNorma())
				return;
			this.procesarListaAsignaciones();
			Entidad entidadActual = ComunicacionServiciosEnt.getEntidadPorId(entidadProceso.getCodEntidad().longValue());
			entidadActual.setCodEstadoEntidad(new BigDecimal(1481));
			entidadActual.setCodSubestadoEntidad(1487);
			for(VinculacionExt v : this.listaDesasignaciones) {
				v.setFlgGuardadoParcial((short)0);
				v.setFlgActivo((short)0);
				ComunicacionServiciosVin.setVinculacion(v);
			}
			for(VinculacionExt v : this.listaAsignaciones) {
				v.setFlgGuardadoParcial((short)0);
				v.setFlgActivo((short)1);
				ComunicacionServiciosVin.setVinculacion(v);
				if(!this.configurarHojaVida(v))
					throw new SIGEP2SistemaException();
			}
			this.guardarNorma();
			EntidadEscision es;
			for(Entidad en : this.entidadesSeleccionadas) {
				Date d1 = new Date(); 
				es = new EntidadEscision();
				es.setCodEntidadOrigen(this.entidadProceso.getCodEntidad());
				es.setCodEntidadDestino(en.getCodEntidad());
				es.setCodEstadoProceso(new BigDecimal(1981));
				es.setFechaProceso(d1);
				es.setCodNorma(this.filtroNorma.getCodNorma());
				es.setAudAccion(this.guardaParcialDisponible? 63 : 785);
				es.setAudFechaActualizacion(new Date());
				es.setAudCodRol(this.getUsuarioSesion().getAudCodRol());
				es.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				es.setCodNorma(this.filtroNorma.getCodNorma());
				en.setCodEstadoEntidad(new BigDecimal(1480));
				en.setCodSubestadoEntidad(1484);
				ComunicacionServiciosEnt.setEntidade(en);
				ComunicacionServiciosEnt.setEntidadEscision(es);
			}
			ComunicacionServiciosEnt.setEntidade(entidadActual);
			this.mostrarMensajeBotonAccion(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.ENT_ESC_MSG_ESCISION_EXITOSO, this.getLocale()) ,TitlesBundleConstants.getStringMessagesBundle(TitlesBundleConstants.TTL_ACEPTAR,getLocale()), "window.location.assign('../../index.xhtml?faces-redirect=true')");
		}
		catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Se ha generado un error mientras se realizaba la escisión por favor contacte con una administrador");
			EscisionEntidadBean.logger.error("Error EscisionEntidadBean.java public void escindir(): " + ex.getMessage() + " " + ex.getStackTrace(), ex);
		}
	}
	
	private boolean configurarHojaVida(VinculacionExt v){
		co.gov.dafp.sigep2.mbean.ext.HojaVidaExt hv = new co.gov.dafp.sigep2.mbean.ext.HojaVidaExt();
		hv.setCodPersona(v.getCodPersona());
		hv.setCodEntidad(v.getCodEntidad());
		hv.setFlgActivo((short) 0);
		List<co.gov.dafp.sigep2.mbean.ext.HojaVidaExt> listHv = ComunicacionServiciosHV.getHojaVidafiltro(hv);
		if(listHv == null || (listHv != null && listHv.isEmpty())) {
			hv.setCodEntidad(null);
			listHv = ComunicacionServiciosHV.getHojaVidafiltro(hv);
			if(listHv != null && !listHv.isEmpty()) {
				hv = listHv.get(0);
				hv.setCodEntidad(v.getCodEntidad());
				hv.setCodHojaVida(null);
				hv.setAudAccion(785);
				ComunicacionServiciosHV.setHojaVida(hv);
				return true;
			}
			return false;
		}
		return true;
	}
	
	public void guardadoParcial() {
		try {
			Entidad entidadActual = ComunicacionServiciosEnt.getEntidadPorId(entidadProceso.getCodEntidad().longValue());
			if(!this.verificarCamposNorma())
				return;
			entidadActual.setCodEstadoEntidad(new BigDecimal(1480)); 
			entidadActual.setCodSubestadoEntidad(1980);
			this.guardarNorma();
			EntidadEscision es;
			for(Entidad en : this.entidadesSeleccionadas) {
				es = new EntidadEscision();
				es.setCodEntidadOrigen(this.entidadProceso.getCodEntidad());
				es.setCodEntidadDestino(en.getCodEntidad());
				es.setCodEstadoProceso(new BigDecimal(1983));
				es.setFechaProceso(new Date());
				es.setAudAccion(785);
				es.setAudFechaActualizacion(new Date());
				es.setAudCodRol(this.getUsuarioSesion().getAudCodRol());
				es.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
				es.setCodNorma(this.numeroNorma == null ? null : this.numeroNorma.longValue());
				en.setCodEstadoEntidad(new BigDecimal(1481));
				en.setCodSubestadoEntidad(1980);
				ComunicacionServiciosEnt.setEntidade(en);
				ComunicacionServiciosEnt.setEntidadEscision(es);
				es.setCodNorma(this.filtroNorma.getCodNorma());
			}
			ComunicacionServiciosEnt.setEntidade(entidadActual);
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_ENTIDAD_ESCINDIR_GUARDADO_PARCIAL);
		}catch(Exception ex) {
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, MessagesBundleConstants.DLG_HEADER_MENSAJES, "Error: " + ex.getMessage());
		}
	}	
	
	private void procesarListaAsignaciones(){
		for(VinculacionExt v: this.listaAsignaciones)
			v.setCodEntidadPlantaDetalle(new BigDecimal(v.getCodCargoDestino()));
	}
	
	private boolean verificarCamposNorma() {
		if(this.filtroNorma != null && this.filtroNorma.getCodTipoNorma() != null && this.filtroNorma.getFechaNorma() != null && this.filtroNorma.getNumeroNorma() != null && !this.filtroNorma.getNumeroNorma().isEmpty())
			return true;
		else {
			this.filtroNorma = new Norma();
			this.mostrarMensaje(FacesMessage.SEVERITY_INFO, "Estimado usuario", "Debe diligenciar los campos de la norma");
			return false;
		}
	}
	
	//Se llama en el contructor
	private boolean cargaGuardadoParcial() {
		try {
			EntidadEscision filtroBusqueda = new EntidadEscision();
			filtroBusqueda.setCodEntidadOrigen(this.entidadContexto.getCodEntidad());
			filtroBusqueda.setCodEstadoProceso(new BigDecimal(1480));
			List<EntidadEscision> listEscision = ComunicacionServiciosEnt.getEntidadEscision(new EntidadEscision());
			this.listaEntidades = new ArrayList<>();
			if(listEscision.isEmpty()) {
				this.entidadProceso.setCodSubestadoEntidad(1484);
				ComunicacionServiciosEnt.setEntidade(entidadProceso);
				return false;
			}
			for(EntidadEscision es: listEscision)
				this.listaEntidades.add(ComunicacionServiciosEnt.getEntidadPorId(es.getCodEntidadDestino().longValue()));
			this.estSeccPpals = false;
			this.estadoBtnEscindir = true;
			this.estadoBotonRegresar = false;
			
			this.configurarMenu();
			return true;
		}catch(Exception ex) {
			return false;
		}
	}
	
	public List<SelectItem> getNormasFiltro(){
    	List<SelectItem> list = new ArrayList<>();
		Norma normaFiltro = new Norma();
		normaFiltro.setFechaNorma(this.fechaNorma);
		normaFiltro.setCodTipoNorma(this.codTipoNorma);
	    List<Norma> listN = ComunicacionServiciosEnt.getFiltroNorma(normaFiltro);
			try {	
			    if (!listN.isEmpty()) {
					for (Norma aux : listN) {
					    if(!aux.isError()) 
					    	list.add(new SelectItem(aux.getCodTipoNorma(), aux.getNumeroNorma()));
					}
			    }
			} catch (NullPointerException ex) {
			    logger.error("public List<SelectItem> getNormasFiltro() EscisionEntidadBean.java: " + ex.getStackTrace(), ex);
			    return new ArrayList<>();
			}
		return list;
    }
	
	public List<String> getNumeroNorma(Integer codTipoNorma, Date fechaNorma) {
		List<String> lista = new ArrayList<>();
		Norma ax = new Norma();
		ax.setCodTipoNorma(codTipoNorma);
		ax.setFechaNorma(fechaNorma);
		List<Norma> listN = ComunicacionServiciosEnt.getFiltroNorma(ax);
		try {
			if (!listN.isEmpty()) {
				for (Norma aux : listN) {
					if (!aux.isError()) 
						lista.add(aux.getNumeroNorma());
				}
			}
		} catch (NullPointerException ex) {
			logger.error("Error public List<String> getNumeroNorma(Integer codTipoNorma, Date fechaNorma) EscisionEntidadBean" + ex.getMessage(), ex);
			return new ArrayList<>();
		}
		return lista;
	}
	
	public List<Norma> buscarNormasCoincidencia(String numNorma) {
		 Norma norm = new Norma();
		 norm.setCodTipoNorma(this.filtroNorma.getCodTipoNorma());
		 norm.setFechaNorma(this.filtroNorma.getFechaNorma());
		 norm.setNumeroNorma(numNorma);
		 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNormaLike(norm);
	     if(normas == null)
	    	 return new ArrayList<>();
	     return normas;
	}
	
	public void acNombreNormaSelectListener(ValueChangeEvent  estructuraExt) {
		 Norma estr = (Norma) estructuraExt.getNewValue();
	        if (estr != null && estr.getNumeroNorma() != null)
	        	this.filtroNorma = estr;
	}
	
	public List<SelectItem> getCargosEntidad(Long codEntidad) {
		List<SelectItem> list = new ArrayList<>();
		if (codEntidad != null) {
			List<Cargo> listN = ComunicacionServiciosEnt.getCargosEntidad(codEntidad);
			try {
				if (!listN.isEmpty()) {
					for (Cargo aux : listN) {
						if (!aux.isError()) 
							list.add(new SelectItem(aux.getCodCargo(), aux.getNombreCargo()));
					}
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
				return new ArrayList<>();
			}
		}
		return list;
	}
	
	public List<SelectItem> obtenerCargosPlanta(Long codPlanta) {
		List<SelectItem> list = new ArrayList<>();
		EntidadPlantaDetalleExt enPlaDe = new EntidadPlantaDetalleExt();
		if(codPlanta == null)
			return new ArrayList<>();
		enPlaDe.setCodEntidadPlanta(new BigDecimal(codPlanta));
		if (codPlanta != null) {
			List<EntidadPlantaDetalleExt> listN = ComunicacionServiciosVin.getEntidadPlantaDetalleFilter(enPlaDe);
			try {
				if (!listN.isEmpty()) {
					for (EntidadPlantaDetalleExt aux : listN) {
						if (aux.getCodEntidadPlantaDetalle() != null && aux.getCodNomenclaturaDenominacion() != null) {
							NomenclaturaDenominacion filtroNomDen = new NomenclaturaDenominacion();
							filtroNomDen.setCodNomenclaturaDenominacion(aux.getCodNomenclaturaDenominacion());
							filtroNomDen.setFlgActivo((short) 1);
							List<NomenclaturaDenominacion> listaNomenclaturaDenominacion = ComunicacionServiciosEnt.obtenerNomenclaturaDenominacion(filtroNomDen);
							if(listaNomenclaturaDenominacion != null && !listaNomenclaturaDenominacion.isEmpty()) {
								Denominacion denom = new Denominacion();
								denom.setCodDenominacion(new BigDecimal(listaNomenclaturaDenominacion.get(0).getCodDenominacion()));
								List<Denominacion> denoms = ComunicacionServiciosEnt.getDenomincacionesFiltro(denom);
									if(denoms != null && !denoms.isEmpty())
										list.add(new SelectItem(aux.getCodEntidadPlantaDetalle(), denoms.get(0).getNombreDenominacion()));
							}
						}
					}
				}
			}catch (Exception ex) {
				logger.error("public List<SelectItem> obtenerCargosPlanta()", ex);
				return new ArrayList<>();
			}
		}
		return list;
	}
	
	
	public List<SelectItem> obtenerPlantasPorEntidad(){
		List<SelectItem> list = new ArrayList<>();
		if (this.codEntidadSeleccionada >= 0) {
			EntidadPlantaExt entFiltro = new EntidadPlantaExt();
			entFiltro.setCodEntidad((long) this.codEntidadSeleccionada);
			List<EntidadPlantaExt> listN = ComunicacionServiciosVin.getEntidadPlantaFilter(entFiltro);
			try {
				if (!listN.isEmpty()) {
					for (EntidadPlantaExt aux : listN) 
						list.add(new SelectItem(aux.getCodEntidadPlanta(), aux.getNombrePlanta()));
				}
			} catch (Exception ex) {
				logger.error("error List<SelectItem> obtenerPlantasPorEntidad(Long codEntidad)" , ex);
				return new ArrayList<>();
			}
		}
		return list;
	}
	
	 public List<Norma> buscarNorma(String numNorma) {
	    	try {
		    	 Norma norm = new Norma();
				 norm.setCodTipoNorma(this.filtroNorma.getCodTipoNorma());
				 norm.setFechaNorma(this.filtroNorma.getFechaNorma());
				 norm.setNumeroNorma(numNorma);
				 List<Norma> normas =  ComunicacionServiciosEnt.getFiltroNorma(norm);
			     if(normas == null)
			    	 return new ArrayList<>();
			     return normas;
	    	}
	    	catch(Exception ex) {
	    		logger.error("public List<Norma> buscarNorma(String numNorma) GruposTrabajoBean: " + ex.getMessage(), ex);
	    		return null;
	    	}
	    }
	
	private boolean validacionEntidadSinPlanta(Long codEntidad) {
			EntidadPlantaExt plantaFil = new EntidadPlantaExt();
			plantaFil.setCodEntidad(codEntidad);
			plantaFil.setFlgActivo((short) 1);
			List<EntidadPlantaExt> list = ComunicacionServiciosVin.getEntidadPlantaFilter(plantaFil);
			return list.isEmpty();
	}
	
    private boolean validarPermisoRolEscindirEntidad(){
    	try {
    		return usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES);
		} 
    	catch (SIGEP2SistemaException ex) {
			logger.error("Error public boolean validarPermisoRolEscindirEntidad", ex);
		}
    	return false;
    }
    
    public void guardarNorma() {
		try { 
			if(this.filtroNorma != null && this.filtroNorma.getNumeroNorma() != null && this.filtroNorma.getCodNorma() == null) {
				List<Norma> lista = this.buscarNorma(this.filtroNorma.getNumeroNorma());
				 if(lista.isEmpty()) {
					 this.filtroNorma.setAudAccion(785);
					 this.filtroNorma.setAudFechaActualizacion(new Date());
					 this.filtroNorma.setAudCodRol((int) this.getUsuarioSesion().getCodRol());
					 this.filtroNorma.setAudCodUsuario(new BigDecimal(this.getUsuarioSesion().getId()));
					 this.filtroNorma = ComunicacionServiciosEnt.setNorma(this.filtroNorma);
				 }
				 else
					 this.filtroNorma.setCodNorma(lista.get(0).getCodNorma());
			 }
		}
		catch(Exception ex){
			this.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Estimado usuario", "Error al procesar la norma");
		}
	}
    
  /**mostrarMensaje(FacesMessage.SEVERITY_FATAL, MessagesBundleConstants.DLG_HEADER_MENSAJES, MessagesBundleConstants.MSG_INFO_VINCULAR_ENTIDAD_SIN_PLANTA);*/

}