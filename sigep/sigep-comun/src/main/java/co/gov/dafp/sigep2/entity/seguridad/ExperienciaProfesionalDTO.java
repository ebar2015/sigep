package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.util.Date;

import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.view.MotivoRetiroDTO;
import co.gov.dafp.sigep2.entity.view.TipoEntidadDTO;
import co.gov.dafp.sigep2.entity.view.JornadaLaboralDTO;

public class ExperienciaProfesionalDTO extends EntidadBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1852999796445075806L;

	private long id;
	
	private Long codPersona;
	
	private PaisDTO codPaisEntidad;
	
	private DepartamentoDTO codDepartamentoEntidad;
	
	private MunicipioDTO codMunicipioEntidad;
	
	private JornadaLaboralDTO codJornadaLaboral;
	
	private MotivoRetiroDTO codMotivoRetiro;
	
	private TipoEntidadDTO codTipoEntidad;
	
	private Boolean isEntidadPublica;
	
	private Long indicativoTelefono;
	
	private EntidadDTO nombreEntidad;
	
	private String direccionEntidad;
	
	private Long telefonoEntidad;
	
	private CargoDTO dependencia;
	
	private CargoDTO cargoEntidad;
	
	private Boolean isActivaEntidad;
	
	private Date fechaIngreso;
	
	private Date fechaRetiro;
	
	private Long horasPromedioMes;
	
	private Long tiempoExperiencia;
	
	private String urlDocumentoSoporte;
	
	private String nombreJefe;
	
	private boolean flgValidaJefe;
	
	private Long codUsuarioValida;
	
	private Date fechaValidacion;
	
	private String justificacionActualizacion;
	
	private Date fechaActualizacion;
	
	private String nombreEntidadExp;
	
	private String cargoEntidadExp;
	
	private Long tipoEntidad;
	
	private String nombreTipoEntidad;
	
	private String dependenciaEntidadExp;
	
	public ExperienciaProfesionalDTO() {
		super();
	}
	
	public ExperienciaProfesionalDTO(Long id, Long codPersona, PaisDTO codPaisEntidad, DepartamentoDTO codDepartamentoEntidad,
			MunicipioDTO codMunicipioEntidad, JornadaLaboralDTO codJornadaLaboral, MotivoRetiroDTO codMotivoRetiro,
			TipoEntidadDTO codTipoEntidad, Boolean isEntidadPublica, Long indicativoTelefono, EntidadDTO nombreEntidad,
			String direccionEntidad, Long telefonoEntidad, CargoDTO dependencia, CargoDTO cargoEntidad, Boolean isActivaEntidad,
			Date fechaIngreso, Date fechaRetiro, Long horasPromedioMes, Long tiempoExperiencia,
			String urlDocumentoSoporte, String nombreJefe, boolean flgValidaJefe, Long codUsuarioValida,
			Date fechaValidacion, String justificacionActualizacion, Date fechaActualizacion) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codPaisEntidad = codPaisEntidad;
		this.codDepartamentoEntidad = codDepartamentoEntidad;
		this.codMunicipioEntidad = codMunicipioEntidad;
		this.codJornadaLaboral = codJornadaLaboral;
		this.codMotivoRetiro = codMotivoRetiro;
		this.codTipoEntidad = codTipoEntidad;
		this.isEntidadPublica = isEntidadPublica;
		this.indicativoTelefono = indicativoTelefono;
		this.nombreEntidad = nombreEntidad;
		this.direccionEntidad = direccionEntidad;
		this.telefonoEntidad = telefonoEntidad;
		this.dependencia = dependencia;
		this.cargoEntidad = cargoEntidad;
		this.isActivaEntidad = isActivaEntidad;
		this.fechaIngreso = fechaIngreso;
		this.fechaRetiro = fechaRetiro;
		this.horasPromedioMes = horasPromedioMes;
		this.tiempoExperiencia = tiempoExperiencia;
		this.urlDocumentoSoporte = urlDocumentoSoporte;
		this.nombreJefe = nombreJefe;
		this.flgValidaJefe = flgValidaJefe;
		this.codUsuarioValida = codUsuarioValida;
		this.fechaValidacion = fechaValidacion;
		this.justificacionActualizacion = justificacionActualizacion;
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public ExperienciaProfesionalDTO(long id, String nombreTipoEntidad, String nombreEntidadExp, String dependenciaEntidadExp,
			String cargoEntidadExp, Date fechaIngreso, Date fechaRetiro, boolean flgValidaJefe) {
		super();
		this.id=id;
		this.nombreTipoEntidad = nombreTipoEntidad;
		this.nombreEntidadExp = nombreEntidadExp;
		this.dependenciaEntidadExp = dependenciaEntidadExp;
		this.cargoEntidadExp = cargoEntidadExp;
		this.fechaIngreso = fechaIngreso;
		this.fechaRetiro = fechaRetiro;
		this.flgValidaJefe = flgValidaJefe;
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

	public PaisDTO getCodPaisEntidad() {
		return codPaisEntidad;
	}

	public void setCodPaisEntidad(PaisDTO codPaisEntidad) {
		this.codPaisEntidad = codPaisEntidad;
	}

	public DepartamentoDTO getCodDepartamentoEntidad() {
		return codDepartamentoEntidad;
	}

	public void setCodDepartamentoEntidad(DepartamentoDTO codDepartamentoEntidad) {
		this.codDepartamentoEntidad = codDepartamentoEntidad;
	}

	public MunicipioDTO getCodMunicipioEntidad() {
		return codMunicipioEntidad;
	}

	public void setCodMunicipioEntidad(MunicipioDTO codMunicipioEntidad) {
		this.codMunicipioEntidad = codMunicipioEntidad;
	}

	public JornadaLaboralDTO getCodJornadaLaboral() {
		return codJornadaLaboral;
	}

	public void setCodJornadaLaboral(JornadaLaboralDTO codJornadaLaboral) {
		this.codJornadaLaboral = codJornadaLaboral;
	}

	public MotivoRetiroDTO getCodMotivoRetiro() {
		return codMotivoRetiro;
	}

	public void setCodMotivoRetiro(MotivoRetiroDTO codMotivoRetiro) {
		this.codMotivoRetiro = codMotivoRetiro;
	}

	public TipoEntidadDTO getCodTipoEntidad() {
		return codTipoEntidad;
	}

	public void setCodTipoEntidad(TipoEntidadDTO codTipoEntidad) {
		this.codTipoEntidad = codTipoEntidad;
	}

	public Boolean getIsEntidadPublica() {
		return isEntidadPublica;
	}

	public void setIsEntidadPublica(Boolean isEntidadPublica) {
		this.isEntidadPublica = isEntidadPublica;
	}

	public Long getIndicativoTelefono() {
		return indicativoTelefono;
	}

	public void setIndicativoTelefono(Long indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}

	public EntidadDTO getnombreEntidad() {
		return nombreEntidad;
	}

	public void setnombreEntidad(EntidadDTO nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getDireccionEntidad() {
		return direccionEntidad;
	}

	public void setDireccionEntidad(String direccionEntidad) {
		this.direccionEntidad = direccionEntidad;
	}

	public Long getTelefonoEntidad() {
		return telefonoEntidad;
	}

	public void setTelefonoEntidad(Long telefonoEntidad) {
		this.telefonoEntidad = telefonoEntidad;
	}

	public CargoDTO getDependencia() {
		return dependencia;
	}

	public void setDependencia(CargoDTO dependencia) {
		this.dependencia = dependencia;
	}

	public CargoDTO getCargoEntidad() {
		return cargoEntidad;
	}

	public void setcargoEntidad(CargoDTO cargoEntidad) {
		this.cargoEntidad = cargoEntidad;
	}

	public Boolean getIsActivaEntidad() {
		return isActivaEntidad;
	}

	public void setIsActivaEntidad(Boolean isActivaEntidad) {
		this.isActivaEntidad = isActivaEntidad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public Long getHorasPromedioMes() {
		return horasPromedioMes;
	}

	public void setHorasPromedioMes(Long horasPromedioMes) {
		this.horasPromedioMes = horasPromedioMes;
	}

	public Long getTiempoExperiencia() {
		return tiempoExperiencia;
	}

	public void setTiempoExperiencia(Long tiempoExperiencia) {
		this.tiempoExperiencia = tiempoExperiencia;
	}

	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	public String getNombreJefe() {
		return nombreJefe;
	}

	public void setNombreJefe(String nombreJefe) {
		this.nombreJefe = nombreJefe;
	}


	public boolean getFlgValidaJefe() {
		return flgValidaJefe;
	}

	public void setFlgValidaJefe(boolean flgValidaJefe) {
		this.flgValidaJefe = flgValidaJefe;
	}

	public Long getCodUsuarioValida() {
		return codUsuarioValida;
	}

	public void setCodUsuarioValida(Long codUsuarioValida) {
		this.codUsuarioValida = codUsuarioValida;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}
	
	public String getJustificacionActualizacion() {
		return justificacionActualizacion;
	}


	public void setJustificacionActualizacion(String justificacionActualizacion) {
		this.justificacionActualizacion = justificacionActualizacion;
	}


	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}


	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public String getNombreEntidadExp() {
		return nombreEntidadExp;
	}


	public void setNombreEntidadExp(String nombreEntidadExp) {
		this.nombreEntidadExp = nombreEntidadExp;
	}


	public String getCargoEntidadExp() {
		return cargoEntidadExp;
	}


	public void setCargoEntidadExp(String cargoEntidadExp) {
		this.cargoEntidadExp = cargoEntidadExp;
	}


	public Long getTipoEntidad() {
		return tipoEntidad;
	}


	public void setTipoEntidad(Long tipoEntidad) {
		this.tipoEntidad = tipoEntidad;
	}
	
	public String getNombreTipoEntidad() {
		return nombreTipoEntidad;
	}

	public void setNombreTipoEntidad(String nombreTipoEntidad) {
		this.nombreTipoEntidad = nombreTipoEntidad;
	}

	public String getDependenciaEntidadExp() {
		return dependenciaEntidadExp;
	}

	public void setDependenciaEntidadExp(String dependenciaEntidadExp) {
		this.dependenciaEntidadExp = dependenciaEntidadExp;
	}

	@Override
	public String toString() {
		return "ExperienciaProfesionalDTO [id=" + id + ", codPersona=" + codPersona + "]";
	}
}