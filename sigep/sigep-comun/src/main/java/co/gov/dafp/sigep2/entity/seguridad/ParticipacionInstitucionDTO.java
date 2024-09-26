package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;

public class ParticipacionInstitucionDTO extends EntidadBaseDTO implements Serializable{
	private static final long serialVersionUID = -5791364205407419573L;

	private long id;
	private long codPersona;
	private String nombreInstitucion;
	private String nombreRazonSocialInstitucion;
	private String nombreEntidadOrganizacion;
	
	public ParticipacionInstitucionDTO(){
		super();
	}
	
	public ParticipacionInstitucionDTO(long id, long codPersona, String nombreInstitucion, String nombreRazonSocialInstitucion, String nombreEntidadOrganizacion){
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.nombreInstitucion = nombreInstitucion;
		this.nombreRazonSocialInstitucion = nombreRazonSocialInstitucion;
		this.nombreEntidadOrganizacion = nombreEntidadOrganizacion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getNombreRazonSocialInstitucion() {
		return nombreRazonSocialInstitucion;
	}

	public void setNombreRazonSocialInstitucion(String nombreRazonSocialInstitucion) {
		this.nombreRazonSocialInstitucion = nombreRazonSocialInstitucion;
	}
	
	public String getNombreEntidadOrganizacion(){
		return nombreEntidadOrganizacion;
	}

	public void setNombreEntidadOrganizacion(String nombreEntidadOrganizacion) {
		this.nombreEntidadOrganizacion = nombreEntidadOrganizacion;
	}

	@Override
	public String toString() {
		return id + ',' + codPersona + ',' + nombreInstitucion + ',' + nombreRazonSocialInstitucion + ',' + nombreEntidadOrganizacion;
	}
}