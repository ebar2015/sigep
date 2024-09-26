package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;

/**
 * The persistent class for the DATOS CONTACTO database table.
 * 
 */
public class NacionalidadPerfilDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 4040037313631254729L;

	private long id;
	
	private Long codPersona;
	
	private PaisDTO codPais;
	
	private String adjuntoUrl;
	
	private boolean flgActivo;

	public NacionalidadPerfilDTO() {
	}	

	public NacionalidadPerfilDTO(long id,Long codPersona,PaisDTO codPais,String adjuntoUrl, boolean flgActivo) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codPais = codPais;
		this.adjuntoUrl = adjuntoUrl;
		this.flgActivo = flgActivo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public PaisDTO getCodPais() {
		return codPais;
	}

	public void setCodPais(PaisDTO codPais) {
		this.codPais = codPais;
	}

	public String getAdjuntoUrl() {
		return adjuntoUrl;
	}

	public void setAdjuntoUrl(String adjuntoUrl) {
		this.adjuntoUrl = adjuntoUrl;
	}

	public boolean isFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	@Override
	public String toString() {
		return "NacionalidadPerfilDTO [id=" + id + ", codPersona=" + codPersona + ", codPais=" + codPais
				+ ", adjuntoUrl=" + adjuntoUrl + ", flgActivo=" + flgActivo + "]";
	}
}