package co.gov.dafp.sigep2.util.xml;

import java.io.Serializable;

public class CuentaCorreo extends co.gov.dafp.sigep2.util.xml.elemento.CuentaCorreo implements Serializable {
	private static final long serialVersionUID = 5336973972973535726L;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreCuenta == null) ? 0 : nombreCuenta.hashCode());
		result = prime * result + ((porPerfil == null) ? 0 : porPerfil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaCorreo other = (CuentaCorreo) obj;
		if (nombreCuenta == null) {
			if (other.nombreCuenta != null)
				return false;
		} else if (!nombreCuenta.equals(other.nombreCuenta))
			return false;
		if (porPerfil == null) {
			if (other.porPerfil != null)
				return false;
		} else if (!porPerfil.equals(other.porPerfil))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CuentaCorreo [bandeja=" + bandeja + ", nombreCuenta=" + nombreCuenta + ", porPerfil=" + porPerfil + "]";
	}
}
