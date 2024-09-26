package co.gov.dafp.sigep2.util;

import java.io.Serializable;
import java.util.List;

public class Registro implements Serializable {
	private static final long serialVersionUID = 5042744511515742126L;

	long id;

	private List<Parametro> item;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Parametro> getItem() {
		return item;
	}

	public void setItem(List<Parametro> item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Registro other = (Registro) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registro [id=" + id + ", item=" + (item != null ? item.size() : 0) + "]";
	}
}
