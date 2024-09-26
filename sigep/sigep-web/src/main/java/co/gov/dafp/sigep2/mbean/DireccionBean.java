/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.mbean;

import co.gov.dafp.sigep2.entities.Parametrica;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.Direccion;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 * Este <code>JSF ManagedBean</code> se crea con la finalidad de ser empleado en
 * los casos en los que se requiera seleccionar una dirección de acuerdo al
 * documento de reglas de negocio RN16.
 *
 * @author Sergio
 */
@Named(value = "direccionBean")
@ViewScoped
public class DireccionBean implements Serializable {

    private static final long serialVersionUID = 5906432091062262533L;

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables">
    //<editor-fold defaultstate="collapsed" desc="Variables generales">
    private Direccion direccionSeleccionada;
    private String direccionGenerada;
    //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Inicialización">
    @PostConstruct
    public void init() {
        direccionSeleccionada = new Direccion();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Acciones de componentes">
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Lógica">
    /**
     * Arma la dirección a partir de los datos ingresados en la interfaz
     *
     * @throws Exception
     */
    public void mostrarDireccion() throws Exception {
        StringBuilder sbDireccion = new StringBuilder();
        Parametrica parametrica;
        if (getDireccionSeleccionada().getCodTipoVia() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodTipoVia());
            if (parametrica != null) {
                if (parametrica.getNombreParametro().contains("-")) {
                    sbDireccion.append(parametrica.getNombreParametro().substring(parametrica.getNombreParametro().indexOf("-") + 1));
                } else {
                    sbDireccion.append(parametrica.getNombreParametro());
                }
                sbDireccion.append(" ");
            }
        }
        if (getDireccionSeleccionada().getNumero() != null) {
            sbDireccion.append(getDireccionSeleccionada().getNumero()).append(" ");
        }
        if (getDireccionSeleccionada().getCodPrimerLetra() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodPrimerLetra());
            if (parametrica != null) {
                sbDireccion.append(parametrica.getNombreParametro()).append(" ");
            }
        }
        if (getDireccionSeleccionada().isBis()) {
            sbDireccion.append("BIS").append(" ");
        }
        if (getDireccionSeleccionada().getCodOrientacion() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodOrientacion());
            if (parametrica != null) {
                sbDireccion.append(parametrica.getNombreParametro()).append(" ");
            }
        }
        if (getDireccionSeleccionada().getCodSegundaLetra() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodSegundaLetra());
            if (parametrica != null) {
                sbDireccion.append(parametrica.getNombreParametro()).append(" ");
            }
        }
        if (getDireccionSeleccionada().getSegundoNumero() != null) {
            sbDireccion.append(" # ").append(getDireccionSeleccionada().getSegundoNumero()).append(" ");
        }
        if (getDireccionSeleccionada().getCodTercerLetra() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodTercerLetra());
            if (parametrica != null) {
                sbDireccion.append(parametrica.getNombreParametro()).append(" ");
            }
        }
        if (getDireccionSeleccionada().getTercerNumero() != null) {
            sbDireccion.append(getDireccionSeleccionada().getTercerNumero()).append(" ");
        }
        if (getDireccionSeleccionada().getCodSegundaOrientacion() != null) {
            parametrica = ComunicacionServiciosSis.getParametricaporId(getDireccionSeleccionada().getCodSegundaOrientacion());
            if (parametrica != null) {
                sbDireccion.append(parametrica.getNombreParametro()).append(" ");
            }
        }
        if (getDireccionSeleccionada().getComplemento() != null) {
            sbDireccion.append(getDireccionSeleccionada().getComplemento()).append(" ");
        }
        setDireccionGenerada(sbDireccion.toString());
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Getter's y setter's">

    public Direccion getDireccionSeleccionada() {
        return direccionSeleccionada;
    }

    public void setDireccionSeleccionada(Direccion direccionSeleccionada) {
        this.direccionSeleccionada = direccionSeleccionada;
    }

    public String getDireccionGenerada() {
        return direccionGenerada;
    }

    public void setDireccionGenerada(String direccionGenerada) {
        this.direccionGenerada = direccionGenerada;
    }

    //</editor-fold>
}
