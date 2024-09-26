package co.gov.dafp.sigep2.vo;

import java.io.Serializable;

import co.gov.dafp.sigep2.util.Parametro;

public class ColumnaReporteVO extends EntidadVO implements Serializable {
	private static final long serialVersionUID = -4111201924564064077L;
	
	private Parametro valor;

	public Parametro getValor() {
		return valor;
	}

	public void setValor(Parametro valor) {
		this.valor = valor;
	}

}
