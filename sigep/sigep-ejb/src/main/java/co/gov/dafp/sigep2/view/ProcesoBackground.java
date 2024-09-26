package co.gov.dafp.sigep2.view;

import java.io.Serializable;

import javax.persistence.*;

import co.gov.dafp.sigep2.converter.EncryptCaseConverter;
import co.gov.dafp.sigep2.converter.UpperCaseConverter;
import co.gov.dafp.sigep2.entity.ProcesoBackgrounDTO;
import co.gov.dafp.sigep2.entity.VistaBase;
import co.gov.dafp.sigep2.entity.view.VistaBaseDTO;

/**
 * Entity implementation class for Entity: DetalleMaestro
 *
 */
@Entity
@Table(name = "V_PROCESO_BACKGROUND")
@SqlResultSetMappings({ @SqlResultSetMapping(name = ProcesoBackground.PROCESO_BACKGROUND_MAPPING, classes = {
		@ConstructorResult(targetClass = ProcesoBackgrounDTO.class, columns = {
				@ColumnResult(name = "COD_PROCESO_BACK", type = Long.class),
				@ColumnResult(name = "DESCRIPCION", type = String.class),
				@ColumnResult(name = "PLANTILLA_XML", type = String.class),
				@ColumnResult(name = "CLASE_JAVA", type = String.class) }) }) })
public class ProcesoBackground extends VistaBase implements Serializable {
	private static final long serialVersionUID = -3126885369978243937L;

	public static final String PROCESO_BACKGROUND_MAPPING = "co.gov.dafp.sigep2.view.mapping.ProcesoBackground";

	public static final String NOTIFICACION_GESTION_USUARIO = "NOTIFICACION GESTION USUARIO CREACION/EDICION";
	public static final String NOTIFICACION_GESTION_USUARIO_PLANTILLA = "notificacionGestionUsuario.xml";

	public static final String NOTIFICACION_GESTION_USUARIO_CONTRASENIA = "NOTIFICACION GESTION USUARIO - CONTRASENA";
	public static final String NOTIFICACION_GESTION_USUARIO_CONTRASENIA_PLANTILLA = "notificacionGestionUsuarioContrasenia.xml";

	@Id
	@Column(name = "COD_PROCESO_BACK", unique = true, nullable = false, precision = 38, insertable = false, updatable = false)
	protected Long id;

	@Column(insertable = false, updatable = false)
	@Convert(converter = UpperCaseConverter.class)
	private String descripcion;

	@Convert(converter = EncryptCaseConverter.class)
	@Column(name = "PLANTILLA_XML", insertable = false, updatable = false)
	private String nombrePlantilla;

	@Convert(converter = EncryptCaseConverter.class)
	@Column(name = "CLASE_JAVA", insertable = false, updatable = false)
	private String interfazProceso;

	public ProcesoBackground() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombrePlantilla() {
		return nombrePlantilla;
	}

	public void setNombrePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	public String getInterfazProceso() {
		return interfazProceso;
	}

	public void setInterfazProceso(String interfazProceso) {
		this.interfazProceso = interfazProceso;
	}

	@Override
	public String toString() {
		return "ProcesoBackground [id=" + id + ", descripcion=" + descripcion + ", nombrePlantilla=" + nombrePlantilla
				+ ", interfazProceso=" + interfazProceso + ", fechaCreacion=" + "]";
	}

	@Override
	public VistaBaseDTO getDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
