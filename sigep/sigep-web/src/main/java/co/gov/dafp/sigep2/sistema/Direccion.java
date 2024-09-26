/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.sistema;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.entities.Rol;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;

/**
 * Esta clase se crea para manejo de dirección como componente de acuerdo al
 * documento de reglas de negocio RN16
 *
 * @author Sergio
 */
public class Direccion implements Serializable {

    private static final long serialVersionUID = 7413591088862264860L;

    //<editor-fold defaultstate="collapsed" desc="Declaración de variables">
    private BigDecimal codTipoVia;
    private String numero;
    private BigDecimal codPrimerLetra;
    private boolean bis;
    private BigDecimal codOrientacion;
    private BigDecimal codSegundaLetra;
    private String segundoNumero;
    private BigDecimal codTercerLetra;
    private String tercerNumero;
    private BigDecimal codSegundaOrientacion;
    private String complemento;
    //</editor-fold>

    public Direccion() {
    }

    public Direccion(BigDecimal codTipoVia, String numero, BigDecimal codPrimerLetra, boolean bis, BigDecimal codOrientacion, BigDecimal codSegundaLetra, String segundoNumero, BigDecimal codTercerLetra, String tercerNumero, BigDecimal codSegundaOrientacion, String complemento) {
        this.codTipoVia = codTipoVia;
        this.numero = numero;
        this.codPrimerLetra = codPrimerLetra;
        this.bis = bis;
        this.codOrientacion = codOrientacion;
        this.codSegundaLetra = codSegundaLetra;
        this.segundoNumero = segundoNumero;
        this.codTercerLetra = codTercerLetra;
        this.tercerNumero = tercerNumero;
        this.codSegundaOrientacion = codSegundaOrientacion;
        this.complemento = complemento;
    }

    //<editor-fold defaultstate="collapsed" desc="Métodos Serializable">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.codTipoVia);
        hash = 23 * hash + Objects.hashCode(this.numero);
        hash = 23 * hash + Objects.hashCode(this.codPrimerLetra);
        hash = 23 * hash + (this.bis ? 1 : 0);
        hash = 23 * hash + Objects.hashCode(this.codOrientacion);
        hash = 23 * hash + Objects.hashCode(this.codSegundaLetra);
        hash = 23 * hash + Objects.hashCode(this.segundoNumero);
        hash = 23 * hash + Objects.hashCode(this.codTercerLetra);
        hash = 23 * hash + Objects.hashCode(this.tercerNumero);
        hash = 23 * hash + Objects.hashCode(this.codSegundaOrientacion);
        hash = 23 * hash + Objects.hashCode(this.complemento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Direccion other = (Direccion) obj;
        if (this.bis != other.bis) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.segundoNumero, other.segundoNumero)) {
            return false;
        }
        if (!Objects.equals(this.tercerNumero, other.tercerNumero)) {
            return false;
        }
        if (!Objects.equals(this.complemento, other.complemento)) {
            return false;
        }
        if (!Objects.equals(this.codTipoVia, other.codTipoVia)) {
            return false;
        }
        if (!Objects.equals(this.codPrimerLetra, other.codPrimerLetra)) {
            return false;
        }
        if (!Objects.equals(this.codOrientacion, other.codOrientacion)) {
            return false;
        }
        if (!Objects.equals(this.codSegundaLetra, other.codSegundaLetra)) {
            return false;
        }
        if (!Objects.equals(this.codTercerLetra, other.codTercerLetra)) {
            return false;
        }
        if (!Objects.equals(this.codSegundaOrientacion, other.codSegundaOrientacion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Direccion{" + "codTipoVia=" + codTipoVia + ", numero=" + numero + ", codPrimerLetra=" + codPrimerLetra + ", bis=" + bis + ", codOrientacion=" + codOrientacion + ", codSegundaLetra=" + codSegundaLetra + ", segundoNumero=" + segundoNumero + ", codTercerLetra=" + codTercerLetra + ", tercerNumero=" + tercerNumero + ", codSegundaOrientacion=" + codSegundaOrientacion + ", complemento=" + complemento + '}';
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Accesores y mutadores">
    public BigDecimal getCodTipoVia() {
        return codTipoVia;
    }

    public void setCodTipoVia(BigDecimal codTipoVia) {
        this.codTipoVia = codTipoVia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getCodPrimerLetra() {
        return codPrimerLetra;
    }

    public void setCodPrimerLetra(BigDecimal codPrimerLetra) {
        this.codPrimerLetra = codPrimerLetra;
    }

    public boolean isBis() {
        return bis;
    }

    public void setBis(boolean bis) {
        this.bis = bis;
    }

    public BigDecimal getCodOrientacion() {
        return codOrientacion;
    }

    public void setCodOrientacion(BigDecimal codOrientacion) {
        this.codOrientacion = codOrientacion;
    }

    public BigDecimal getCodSegundaLetra() {
        return codSegundaLetra;
    }

    public void setCodSegundaLetra(BigDecimal codSegundaLetra) {
        this.codSegundaLetra = codSegundaLetra;
    }

    public String getSegundoNumero() {
        return segundoNumero;
    }

    public void setSegundoNumero(String segundoNumero) {
        this.segundoNumero = segundoNumero;
    }

    public BigDecimal getCodTercerLetra() {
        return codTercerLetra;
    }

    public void setCodTercerLetra(BigDecimal codTercerLetra) {
        this.codTercerLetra = codTercerLetra;
    }

    public String getTercerNumero() {
        return tercerNumero;
    }

    public void setTercerNumero(String tercerNumero) {
        this.tercerNumero = tercerNumero;
    }

    public BigDecimal getCodSegundaOrientacion() {
        return codSegundaOrientacion;
    }

    public void setCodSegundaOrientacion(BigDecimal codSegundaOrientacion) {
        this.codSegundaOrientacion = codSegundaOrientacion;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    //</editor-fold>   
    //<editor-fold defaultstate="collapsed" desc="Lógica">
    /**
     * Mediante este método se alimentan los datos de un objeto
     * <code>Direccion</code> a partir de un String, el cual debe provenir del
     * método <code>toString</code> de esta clase. La idea es guardar el dato de
     * dirección con el string generado en el método <code>toString</code> y
     * luego recuperar el objeto mediante este método
     *
     * @param toString El <code>String</code> a partir del cual se arma el
     * objeto <code>Direccion</code>, debe ser el que se generó con el método
     * <code>toString</code> de esta clase
     * @throws java.lang.Exception Cuando existe alguna falla en la conversión
     */
    public void llenarDatosDesdeToString(String toString) throws Exception {
        String stCodTipoVia = obtenerDato(toString, "codTipoVia=", ", numero=");
        String stNumero = obtenerDato(toString, ", numero=", ", codPrimerLetra=");
        String stCodPrimerLetra = obtenerDato(toString, ", codPrimerLetra=", ", bis=");
        String stBis = obtenerDato(toString, ", bis=", ", codOrientacion=");
        String stCodOrientacion = obtenerDato(toString, ", codOrientacion=", ", codSegundaLetra=");
        String stCodSegundaLetra = obtenerDato(toString, ", codSegundaLetra=", ", segundoNumero=");
        String stSegundoNumero = obtenerDato(toString, ", segundoNumero=", ", codTercerLetra=");
        String stCodTercerLetra = obtenerDato(toString, ", codTercerLetra=", ", tercerNumero=");
        String stTercerNumero = obtenerDato(toString, ", tercerNumero=", ", codSegundaOrientacion=");
        String stCodSegundaOrientacion = obtenerDato(toString, ", codSegundaOrientacion=", ", complemento=");
        String stComplemento = obtenerDato(toString, ", complemento=", "}");
        if (!stCodTipoVia.equals("null") && !"".equals(stCodTipoVia)) {
            this.codTipoVia = BigDecimal.valueOf(Double.parseDouble(stCodTipoVia));
        }
        if (!stNumero.equals("null") && !"".equals(stNumero) ) {
            this.numero = stNumero;
        }
        if (!stCodPrimerLetra.equals("null") && !"".equals(stCodPrimerLetra)) {
            this.codPrimerLetra = BigDecimal.valueOf(Double.parseDouble(stCodPrimerLetra));
        }
        this.setBis(Boolean.parseBoolean(stBis)  && !"".equals(stBis) );
        if (!stCodOrientacion.equals("null") && !"".equals(stCodOrientacion) ) {
            this.codOrientacion = BigDecimal.valueOf(Double.parseDouble(stCodOrientacion));
        }
        if (!stCodSegundaLetra.equals("null") && !"".equals(stCodSegundaLetra)) {
            this.codSegundaLetra = BigDecimal.valueOf(Double.parseDouble(stCodSegundaLetra));
        }
        if (!stSegundoNumero.equals("null") && !"".equals(stSegundoNumero)) {
            this.segundoNumero = stSegundoNumero;
        }
        if (!stCodTercerLetra.equals("null")  && !"".equals(stCodTercerLetra) ) {
            this.codTercerLetra = BigDecimal.valueOf(Double.parseDouble(stCodTercerLetra));
        }
        if (!stTercerNumero.equals("null") && !"".equals(stTercerNumero) ) {
            this.tercerNumero = stTercerNumero;
        }
        if (!stCodSegundaOrientacion.equals("null") && !"".equals(stCodSegundaOrientacion) ) {
            this.codSegundaOrientacion = BigDecimal.valueOf(Double.parseDouble(stCodSegundaOrientacion));
        }
        if (!stComplemento.equals("null")) {
            this.complemento = stComplemento;
        }
    }

    /**
     * Obtiene un dato de un String que se encuentre entre un
     * <code>String</code> que se toma como token de inicio y un
     * <code>String</code> que se toma como token de fin
     *
     * @param objString El <code>String</code> a partir del cual se toma el dato
     * @param tokenInicio <code>String</code> que se toma como token de inicio
     * @param tokenFin <code>String</code> que se toma como token de fin
     * @return El dato encontrado
     * @throws java.lang.Exception En caso de no encontrar alguno de los token
     */
    public String obtenerDato(String objString, String tokenInicio, String tokenFin) throws Exception {
    	String dato="";
    	if(objString.length() > 0 && objString.contains(tokenInicio) && objString.contains(tokenFin)){
    		dato =  objString.substring(objString.indexOf(tokenInicio) + tokenInicio.length(), objString.indexOf(tokenFin));	
    	}
        return dato;
    }

    
    /**
     * llenar datos desde JSON
     */
    public void llenarDatosDesdeJson(String json) throws Exception {
    	Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    	try {
	    	if(json != null && !json.isEmpty()) {
	    		EditarDireccionDTO dir = gson.fromJson(json, EditarDireccionDTO.class);
	    		if(dir != null) {
	    			if(dir.getTipoVia() != null) {
	    				this.codTipoVia = new BigDecimal(dir.getTipoVia().getId());
	    			}
	    			
	    			if(dir.getNumero() != null && !dir.getNumero().isEmpty()) {
	    				this.numero = dir.getNumero();
	    			}
	    			
	    			if(dir.getPrimerLetra() != null) {
	    				this.codPrimerLetra = new BigDecimal(dir.getPrimerLetra().getId());
	    			}
	    			
	    			this.setBis(dir.isBis());
	    			
	    			if(dir.getOrientacion() != null) {
	    				this.codOrientacion = new BigDecimal(dir.getOrientacion().getId());
	    			}
	    			
	    			if(dir.getSegundaLetra() != null) {
	    				this.codSegundaLetra = new BigDecimal(dir.getSegundaLetra().getId());
	    			}
	    			
	    			if(dir.getSegundoNumero() != null && !dir.getSegundoNumero().isEmpty()) {
	    				this.segundoNumero = dir.getSegundoNumero();
	    			}
	    			
	    			if(dir.getTercerLetra() != null) {
	    				this.codTercerLetra = new BigDecimal(dir.getTercerLetra().getId());
	    			}
	    			
	    			if(dir.getTercerNumero() != null && !dir.getTercerNumero().isEmpty()) {
	    				this.tercerNumero = dir.getTercerNumero();
	    			}
	    			
	    			if(dir.getSegundaOrientacion() != null) {
	    				this.codSegundaOrientacion = new BigDecimal(dir.getSegundaOrientacion().getId());
	    			}
	    			
	    			if(dir.getComplemento() != null && !dir.getComplemento().isEmpty()) {
	    				this.complemento = dir.getComplemento();
	    			}
	    		}
	    	}
    	} catch(JsonSyntaxException e) {
    		
    	}
    } 
    
    
    //</editor-fold>
}
