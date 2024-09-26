package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;

public class EditarDireccionDTO implements Serializable {

	private static final long serialVersionUID = 4058407159718891478L;
	
	private TipoViaDTO tipoVia;
	private String numero;
	private TipoLetraDTO primerLetra;
	private boolean bis;
	private TipoOrientacionDTO orientacion;
	private TipoLetraDTO segundaLetra;
	private String segundoNumero;
	private TipoLetraDTO tercerLetra;
	private String tercerNumero;
	private TipoOrientacionDTO segundaOrientacion;
	private String complemento;
	private String direccionGenerada;
	
	public TipoViaDTO getTipoVia() {
		return tipoVia;
	}
	
	public void setTipoVia(TipoViaDTO tipoVia) {
		this.tipoVia = tipoVia;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public boolean isBis() {
		return bis;
	}
	
	public void setBis(boolean bis) {
		this.bis = bis;
	}
	
	public TipoOrientacionDTO getOrientacion() {
		return orientacion;
	}
	
	public void setOrientacion(TipoOrientacionDTO orientacion) {
		this.orientacion = orientacion;
	}

	public TipoLetraDTO getPrimerLetra() {
		return primerLetra;
	}

	public void setPrimerLetra(TipoLetraDTO primerLetra) {
		this.primerLetra = primerLetra;
	}

	public TipoLetraDTO getSegundaLetra() {
		return segundaLetra;
	}

	public void setSegundaLetra(TipoLetraDTO segundaLetra) {
		this.segundaLetra = segundaLetra;
	}

	public String getSegundoNumero() {
		return segundoNumero;
	}

	public void setSegundoNumero(String segundoNumero) {
		this.segundoNumero = segundoNumero;
	}

	public TipoLetraDTO getTercerLetra() {
		return tercerLetra;
	}

	public void setTercerLetra(TipoLetraDTO tercerLetra) {
		this.tercerLetra = tercerLetra;
	}

	public String getTercerNumero() {
		return tercerNumero;
	}

	public void setTercerNumero(String tercerNumero) {
		this.tercerNumero = tercerNumero;
	}

	public TipoOrientacionDTO getSegundaOrientacion() {
		return segundaOrientacion;
	}

	public void setSegundaOrientacion(TipoOrientacionDTO segundaOrientacion) {
		this.segundaOrientacion = segundaOrientacion;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDireccionGenerada() {
		return direccionGenerada;
	}

	public void setDireccionGenerada(String direccionGenerada) {
		this.direccionGenerada = direccionGenerada;
	}
}