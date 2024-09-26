package co.gov.dafp.sigep2.mbean.bienesrentas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import co.gov.dafp.sigep2.entities.AcreenciaObligacion;
import co.gov.dafp.sigep2.entities.CuentasDeclaracion;
import co.gov.dafp.sigep2.mbean.ext.ActividadPrivadaExt;
import co.gov.dafp.sigep2.mbean.ext.BienesPatrimonioExt;
import co.gov.dafp.sigep2.mbean.ext.DeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.IngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.OtrosIngresosDeclaracionExt;
import co.gov.dafp.sigep2.mbean.ext.ParticipacionJuntaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaExt;
import co.gov.dafp.sigep2.mbean.ext.PersonaParentescoExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class DeclaracionRentaBeanPrint implements Serializable {
    private static final long serialVersionUID = -4778591090396006637L;

    private PersonaExt datosPersonales = new PersonaExt();
    private String rutaRelativaLogoIzq, nombreCompleto;
    private BigDecimal totalIngresoLaboral, totalCesantias, totalGastosRepresentacion, totalArriendos, totalHonorarios, totalOtrosIngresos, ingresosTotalizados;
    private DeclaracionExt detalleDeclaracion 						= new DeclaracionExt();
    private PersonaParentescoExt conyugue 							= new PersonaParentescoExt();
    private List<PersonaParentescoExt> listaParentesco 				= new ArrayList<>();
    private List<IngresosDeclaracionExt> listaIngresosLaborales 	= new ArrayList<>();
    private List<OtrosIngresosDeclaracionExt> listaOtrosIngresos 	= new ArrayList<>();
    private List<CuentasDeclaracion> listaCuentasDeclaracion 		= new ArrayList<>();
    private List<BienesPatrimonioExt> listaBienesPatrimoniales 		= new ArrayList<>();
    private List<ActividadPrivadaExt> listaActividadesEconomicas 	= new ArrayList<>();
    private List<AcreenciaObligacion> listaAcreenciasObligaciones 	= new ArrayList<>();
    private List<ParticipacionJuntaExt> listaParticipacionesJuntas 	= new ArrayList<>();
    private List<ParticipacionJuntaExt> listaParticipacionesSocio 	= new ArrayList<>();

    public List<ParticipacionJuntaExt> getListaParticipacionesSocio() {
	return listaParticipacionesSocio;
    }

    public void setListaParticipacionesSocio(List<ParticipacionJuntaExt> listaParticipacionesSocio) {
	this.listaParticipacionesSocio = listaParticipacionesSocio;
    }

    public BigDecimal getTotalIngresoLaboral() {
	return totalIngresoLaboral;
    }

    public BigDecimal getTotalCesantias() {
	return totalCesantias;
    }

    public BigDecimal getTotalGastosRepresentacion() {
	return totalGastosRepresentacion;
    }

    public BigDecimal getTotalArriendos() {
	return totalArriendos;
    }

    public BigDecimal getTotalHonorarios() {
	return totalHonorarios;
    }

    public BigDecimal getIngresosTotalizados() {
	return ingresosTotalizados;
    }

    public void setTotalIngresoLaboral(BigDecimal totalIngresoLaboral) {
	this.totalIngresoLaboral = totalIngresoLaboral;
    }

    public void setTotalCesantias(BigDecimal totalCesantias) {
	this.totalCesantias = totalCesantias;
    }

    public void setTotalGastosRepresentacion(BigDecimal totalGastosRepresentacion) {
	this.totalGastosRepresentacion = totalGastosRepresentacion;
    }

    public void setTotalArriendos(BigDecimal totalArriendos) {
	this.totalArriendos = totalArriendos;
    }

    public void setTotalHonorarios(BigDecimal totalHonorarios) {
	this.totalHonorarios = totalHonorarios;
    }

    public void setIngresosTotalizados(BigDecimal ingresosTotalizados) {
	this.ingresosTotalizados = ingresosTotalizados;
    }

    public BigDecimal getTotalOtrosIngresos() {
	if (totalOtrosIngresos != null) {
	    return totalOtrosIngresos.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	} else {
	    return totalOtrosIngresos;
	}
    }

    public void setTotalOtrosIngresos(BigDecimal totalOtrosIngresos) {
	this.totalOtrosIngresos = totalOtrosIngresos;
    }

    public DeclaracionExt getDetalleDeclaracion() {
	return detalleDeclaracion;
    }

    public void setDetalleDeclaracion(DeclaracionExt detalleDeclaracion) {
	this.detalleDeclaracion = detalleDeclaracion;
    }

    public String getRutaRelativaLogoIzq() {
	return rutaRelativaLogoIzq;
    }

    public void setRutaRelativaLogoIzq(String rutaRelativaLogoIzq) {
	this.rutaRelativaLogoIzq = rutaRelativaLogoIzq;
    }

    public String getNombreCompleto() {
	if (datosPersonales.getSegundoNombre() == null || datosPersonales.getSegundoNombre() == "") {
	    nombreCompleto = datosPersonales.getPrimerNombre() + " " + datosPersonales.getPrimerApellido() + " " + datosPersonales.getSegundoApellido();
	} else if (datosPersonales.getSegundoApellido() == null || datosPersonales.getSegundoApellido() == "") {
	    nombreCompleto = datosPersonales.getPrimerNombre() + " " + datosPersonales.getSegundoNombre() + " " + datosPersonales.getPrimerApellido();
	} else {
	    nombreCompleto = datosPersonales.getPrimerNombre() + " " + datosPersonales.getSegundoNombre() + " " + datosPersonales.getPrimerApellido() + " " + datosPersonales.getSegundoApellido();
	}
	return nombreCompleto.toUpperCase();
    }

    public void setNombreCompleto(String nombreCompleto) {
	this.nombreCompleto = nombreCompleto;
    }

    public PersonaExt getDatosPersonales() {
	return datosPersonales;
    }

    public void setDatosPersonales(PersonaExt datosPersonales) {
	this.datosPersonales = datosPersonales;
    }

    public List<PersonaParentescoExt> getListaParentesco() {
	return listaParentesco;
    }

    public void setListaParentesco(List<PersonaParentescoExt> listaParentesco) {
	this.listaParentesco = listaParentesco;
    }

    public List<IngresosDeclaracionExt> getListaIngresosLaborales() {
	return listaIngresosLaborales;
    }

    public void setListaIngresosLaborales(List<IngresosDeclaracionExt> listaIngresosLaborales) {
	this.listaIngresosLaborales = listaIngresosLaborales;
    }

    public List<OtrosIngresosDeclaracionExt> getListaOtrosIngresos() {
	return listaOtrosIngresos;
    }

    public void setListaOtrosIngresos(List<OtrosIngresosDeclaracionExt> listaOtrosIngresos) {
	this.listaOtrosIngresos = listaOtrosIngresos;
    }

    public List<CuentasDeclaracion> getListaCuentasDeclaracion() {
	return listaCuentasDeclaracion;
    }

    public void setListaCuentasDeclaracion(List<CuentasDeclaracion> listaCuentasDeclaracion) {
	this.listaCuentasDeclaracion = listaCuentasDeclaracion;
    }

    public List<BienesPatrimonioExt> getListaBienesPatrimoniales() {
	return listaBienesPatrimoniales;
    }

    public void setListaBienesPatrimoniales(List<BienesPatrimonioExt> listaBienesPatrimoniales) {
	this.listaBienesPatrimoniales = listaBienesPatrimoniales;
    }

    public List<ActividadPrivadaExt> getListaActividadesEconomicas() {
	return listaActividadesEconomicas;
    }

    public void setListaActividadesEconomicas(List<ActividadPrivadaExt> listaActividadesEconomicas) {
	this.listaActividadesEconomicas = listaActividadesEconomicas;
    }

    public List<AcreenciaObligacion> getListaAcreenciasObligaciones() {
	return listaAcreenciasObligaciones;
    }

    public void setListaAcreenciasObligaciones(List<AcreenciaObligacion> listaAcreenciasObligaciones) {
	this.listaAcreenciasObligaciones = listaAcreenciasObligaciones;
    }

    public List<ParticipacionJuntaExt> getListaParticipacionesJuntas() {
	return listaParticipacionesJuntas;
    }

    public void setListaParticipacionesJuntas(List<ParticipacionJuntaExt> listaParticipacionesJuntas) {
	this.listaParticipacionesJuntas = listaParticipacionesJuntas;
    }

    public PersonaParentescoExt getConyugue() {
	return conyugue;
    }

    public void setConyugue(PersonaParentescoExt conyugue) {
	this.conyugue = conyugue;
    }

    public DeclaracionRentaBeanPrint() {
	String rutaImg = "/" + ComunicacionServiciosSis.getParametricaIntetos("LOGO_DECLARACION_BIENES_RENTAS").getValorParametro();
	rutaImg=rutaImg.toLowerCase();
	rutaRelativaLogoIzq = FacesContext.getCurrentInstance().getExternalContext().getRealPath(rutaImg);
    }

    private BigDecimal noNulo(BigDecimal valor) {
	if (valor == null) {
	    valor = new BigDecimal(0);
	}
	return valor;
    }

    public BigDecimal sumarTotales() {
	ingresosTotalizados = noNulo(totalIngresoLaboral).add(noNulo(totalOtrosIngresos)).add(noNulo(totalCesantias)).add(noNulo(totalGastosRepresentacion)).add(noNulo(totalArriendos)).add(noNulo(totalHonorarios));
	return noNulo(ingresosTotalizados).setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }
}