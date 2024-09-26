package co.gov.dafp.sigep2.vo;

import java.io.Serializable;

import javax.persistence.Convert;

import co.gov.dafp.sigep2.converter.UpperCaseConverter;

public class MaestroVO extends EntidadVO implements Serializable {
	private static final long serialVersionUID = -5829811044260828291L;

	@Convert(converter = UpperCaseConverter.class)
	private String nombreTabla;

	public String getNombreTabla() {
		return nombreTabla;
	}

	public void setNombreTabla(String nombreTabla) {
		String prefijo = "MST_";
		if (nombreTabla != null) {
			if (!nombreTabla.toUpperCase().startsWith(prefijo)) {
				nombreTabla = prefijo + nombreTabla.toUpperCase();
			}
			nombreTabla = nombreTabla.replace(" ", "_").toUpperCase();
		}
		this.nombreTabla = nombreTabla;
	}

	@Override
	public String toString() {
		return "MaestroVO [nombreTabla=" + nombreTabla + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nombreTabla == null) ? 0 : nombreTabla.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaestroVO other = (MaestroVO) obj;
		if (nombreTabla == null) {
			if (other.nombreTabla != null)
				return false;
		} else if (!nombreTabla.toUpperCase().equals(other.nombreTabla.toUpperCase()))
			return false;
		return true;
	}

}
