package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class PublicacionDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long codPublicacion;
	
	private long codPersona;
	
	private long codTipoArticulo;
	
	private String nombreArticulo;
	
	private long codTipoPublicacion;
	
	private String nombreLibro;
	
	private Long codOtroTipoPublicacion;
	
	private String nombrePublicacion;
	
	
	public PublicacionDTO(){
		super();
	}
	
	
	public PublicacionDTO(long codPublicacion, long codPersona, long codTipoArticulo, String nombreArticulo, long codTipoPublicacion, String nombreLibro, long codOtroTipoPublicacion, String nombrePublicacion){
		super();
		this.codPublicacion = codPublicacion;
		this.codPersona = codPersona;
		this.codTipoArticulo = codTipoArticulo;
		this.nombreArticulo = nombreArticulo;
		this.codTipoPublicacion = codTipoPublicacion;
		this.nombreLibro = nombreLibro;
		this.codOtroTipoPublicacion = codOtroTipoPublicacion;
		this.nombrePublicacion = nombrePublicacion;
		
		
		
	}

	public long getId() {
		return codPublicacion;
	}

	public void setId(long codPublicacion) {
		this.codPublicacion = codPublicacion;
	}

	public Long getCodTipoArticulo() {
		return codTipoArticulo;
	}

	public void setCodTipoArticulo(Long codTipoArticulo) {
		this.codTipoArticulo = codTipoArticulo;
	}

	public Long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(Long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreArticulo() {
		return nombreArticulo;
	}

	public void setNombreArticulo(String nombreArticulo) {
		this.nombreArticulo = nombreArticulo;
	}

	public Long getCodTipoPublicacion() {
		return codTipoPublicacion;
	}

	public void setCodTipoPublicacion(Long codTipoPublicacion) {
		this.codTipoPublicacion = codTipoPublicacion;
	}

	public String getNombrePublicacion() {
		return nombrePublicacion;
	}

	public void setNombrePublicacion(String nombrePublicacion) {
		this.nombrePublicacion = nombrePublicacion;
	}

	public Long getCodOtroTipoPublicacion() {
		return codOtroTipoPublicacion;
	}

	public void setCodOtroTipoPublicacion(Long codOtroTipoPublicacion) {
		this.codOtroTipoPublicacion = codOtroTipoPublicacion;
	}

	public String getNombreLibro() {
		return nombreLibro;
	}

	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	@Override
	public String toString() {
		return "PublicacionDTO [id=" + this.getId() + "codPersona=" + this.getCodPersona() + "]";
	}
}