package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.view.AreaConocimientoDTO;
import co.gov.dafp.sigep2.entity.view.NivelEducativoDTO;

public class ExperienciaDocenteDTO extends EntidadBaseDTO implements Serializable {

	private static final long serialVersionUID = -4872604604410379882L;

	private long id;
	private Long codPersona;
	private PaisDTO codPais;
	private DepartamentoDTO codDepartamento;
	private MunicipioDTO codCiudad;
	private NivelEducativoDTO nivelEducativo;
	private AreaConocimientoDTO areaConocimiento;
	private InstitucionEducativaDTO codInstitucion;
	private String nombreInstitucion;
	private Date fechaIngreso;
	private Boolean isActivaActualmente;
	private Date fechaFinalizacion;
	private String direccion;
	private String telefono;
	private Long horasSemana;
	private String materiaImpartida;
	private String urlDocumentoSoporte;
	private Boolean flgVerificado;
	private Long codigoUsuarioVerifica;
	private Date fechaVerificacion;
	private Long flgEntidadPublica;
	private String indicativoTelefono;
	private String tiempoExperiencia;
	private Boolean flgActivo;
	private Long codTipoZona;
	
	private String nombre;
	private String nivEducativo;
	private String areaCon;
	private String pais;
	private String fechIngreso;
	private String fechFinalizacion;
	
	
	public ExperienciaDocenteDTO() {
		super();
	}

	public ExperienciaDocenteDTO(long id, Long codPersona, PaisDTO codPais, DepartamentoDTO codDepartamento,
			MunicipioDTO codCiudad, NivelEducativoDTO nivelEducativo, AreaConocimientoDTO areaConocimiento,
			InstitucionEducativaDTO codInstitucion, String nombreInstitucion, Date fechaIngreso,
			Boolean isActivaActualmente, Date fechaFinalizacion, String direccion, String telefono, Long horasSemana,
			String materiaImpartida, String urlDocumentoSoporte, Boolean flgVerificado, Long codigoUsuarioVerifica,
			Date fechaVerificacion, Long flgEntidadPublica, String indicativoTelefono, String tiempoExperiencia,
			Boolean flgActivo, Long codTipoZona) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.codPais = codPais;
		this.codDepartamento = codDepartamento;
		this.codCiudad = codCiudad;
		this.nivelEducativo = nivelEducativo;
		this.areaConocimiento = areaConocimiento;
		this.codInstitucion = codInstitucion;
		this.nombreInstitucion = nombreInstitucion;
		this.fechaIngreso = fechaIngreso;
		this.isActivaActualmente = isActivaActualmente;
		this.fechaFinalizacion = fechaFinalizacion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.horasSemana = horasSemana;
		this.materiaImpartida = materiaImpartida;
		this.urlDocumentoSoporte = urlDocumentoSoporte;
		this.flgVerificado = flgVerificado;
		this.codigoUsuarioVerifica = codigoUsuarioVerifica;
		this.fechaVerificacion = fechaVerificacion;
		this.flgEntidadPublica = flgEntidadPublica;
		this.indicativoTelefono = indicativoTelefono;
		this.tiempoExperiencia = tiempoExperiencia;
		this.flgActivo = flgActivo;
		this.codTipoZona = codTipoZona;
	}

	public ExperienciaDocenteDTO(long id, String nombre, String nivEducativo, String areaCon, String pais, Date fechIngreso,
			Date fechFinalizacion, boolean flgVerificado) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.id = id;
		this.flgVerificado = flgVerificado;
		this.nombre = nombre;
		this.nivEducativo = nivEducativo;
		this.areaCon = areaCon;
		this.pais = pais;
		this.fechIngreso = sdf.format(fechIngreso);
		
		if(fechFinalizacion == null) {
		this.fechFinalizacion = "";
		}else {
			this.fechFinalizacion = sdf.format(fechFinalizacion);
		}
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

	public DepartamentoDTO getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(DepartamentoDTO codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public MunicipioDTO getCodCiudad() {
		return codCiudad;
	}

	public void setCodCiudad(MunicipioDTO codCiudad) {
		this.codCiudad = codCiudad;
	}

	public NivelEducativoDTO getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(NivelEducativoDTO nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public AreaConocimientoDTO getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(AreaConocimientoDTO areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public InstitucionEducativaDTO getCodInstitucion() {
		return codInstitucion;
	}

	public void setCodInstitucion(InstitucionEducativaDTO codInstitucion) {
		this.codInstitucion = codInstitucion;
	}

	public Boolean getIsActivaActualmente() {
		return isActivaActualmente;
	}

	public void setIsActivaActualmente(Boolean isActivaActualmente) {
		this.isActivaActualmente = isActivaActualmente;
	}

	public Long getCodigoUsuarioVerifica() {
		return codigoUsuarioVerifica;
	}

	public void setCodigoUsuarioVerifica(Long codigoUsuarioVerifica) {
		this.codigoUsuarioVerifica = codigoUsuarioVerifica;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Boolean getFlgActualmente() {
		return isActivaActualmente;
	}

	public void setFlgActualmente(Boolean flgActualmente) {
		this.isActivaActualmente = flgActualmente;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getHorasSemana() {
		return horasSemana;
	}

	public void setHorasSemana(Long horasSemana) {
		this.horasSemana = horasSemana;
	}

	public String getMateriaImpartida() {
		return materiaImpartida;
	}

	public void setMateriaImpartida(String materiaImpartida) {
		this.materiaImpartida = materiaImpartida;
	}

	public String getUrlDocumentoSoporte() {
		return urlDocumentoSoporte;
	}

	public void setUrlDocumentoSoporte(String urlDocumentoSoporte) {
		this.urlDocumentoSoporte = urlDocumentoSoporte;
	}

	public Boolean getFlgVerificado() {
		return flgVerificado;
	}

	public void setFlgVerificado(Boolean flgVerificado) {
		this.flgVerificado = flgVerificado;
	}

	public Long getCodUsuarioVerifica() {
		return codigoUsuarioVerifica;
	}

	public void setCodUsuarioVerifica(Long codUsuarioVerifica) {
		this.codigoUsuarioVerifica = codUsuarioVerifica;
	}

	public Date getFechaVerificacion() {
		return fechaVerificacion;
	}

	public void setFechaVerificacion(Date fechaVerificacion) {
		this.fechaVerificacion = fechaVerificacion;
	}

	public Long getFlgEntidadPublica() {
		return flgEntidadPublica;
	}

	public void setFlgEntidadPublica(Long flgEntidadPublica) {
		this.flgEntidadPublica = flgEntidadPublica;
	}

	public String getNombreInstitucion() {
		return nombreInstitucion;
	}

	public void setNombreInstitucion(String nombreInstitucion) {
		this.nombreInstitucion = nombreInstitucion;
	}

	public String getIndicativoTelefono() {
		return indicativoTelefono;
	}

	public void setIndicativoTelefono(String indicativoTelefono) {
		this.indicativoTelefono = indicativoTelefono;
	}

	public String getTiempoExperiencia() {
		return tiempoExperiencia;
	}

	public void setTiempoExperiencia(String tiempoExperiencia) {
		this.tiempoExperiencia = tiempoExperiencia;
	}

	public Boolean getFlgActivo() {
		return flgActivo;
	}

	public void setFlgActivo(Boolean flgActivo) {
		this.flgActivo = flgActivo;
	}

	public Long getCodTipoZona() {
		return codTipoZona;
	}

	public void setCodTipoZona(Long codTipoZona) {
		this.codTipoZona = codTipoZona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNivEducativo() {
		return nivEducativo;
	}

	public void setNivEducativo(String nivEducativo) {
		this.nivEducativo = nivEducativo;
	}

	public String getAreaCon() {
		return areaCon;
	}

	public void setAreaCon(String areaCon) {
		this.areaCon = areaCon;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
	
	public String getFechIngreso() {
		return fechIngreso;
	}

	public void setFechIngreso(String fechIngreso) {
		this.fechIngreso = fechIngreso;
	}
	
	public String getFechFinalizacion() {
		return fechFinalizacion;
	}

	public void setFechFinalizacion(String fechFinalizacion) {
		this.fechFinalizacion = fechFinalizacion;
	}

	@Override
	public String toString() {
		return "ExperienciaDocenteDTO [id=" + id + ", codPersona=" + codPersona + ", codPais=" + codPais
				+ ", codDepartamento=" + codDepartamento + ", codCiudad=" + codCiudad + ", nivelEducativo="
				+ nivelEducativo + ", areaConocimiento=" + areaConocimiento + ", codInstitucion=" + codInstitucion
				+ ", nombreInstitucion=" + nombreInstitucion + ", fechaIngreso=" + fechaIngreso
				+ ", isActivaActualmente=" + isActivaActualmente + ", fechaFinalizacion=" + fechaFinalizacion
				+ ", direccion=" + direccion + ", telefono=" + telefono + ", horasSemana=" + horasSemana
				+ ", materiaImpartida=" + materiaImpartida + ", urlDocumentoSoporte=" + urlDocumentoSoporte
				+ ", flgVerificado=" + flgVerificado + ", codigoUsuarioVerifica=" + codigoUsuarioVerifica
				+ ", fechaVerificacion=" + fechaVerificacion + ", flgEntidadPublica=" + flgEntidadPublica
				+ ", indicativoTelefono=" + indicativoTelefono + ", tiempoExperiencia=" + tiempoExperiencia
				+ ", flgActivo=" + flgActivo + ", codTipoZona=" + codTipoZona + ", nombre=" + nombre + ", nivEducativo="
				+ nivEducativo + ", areaCon=" + areaCon + ", pais=" + pais + ", fechIngreso=" + fechIngreso
				+ ", fechFinalizacion=" + fechFinalizacion + "]";
	}
}