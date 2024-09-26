package co.gov.dafp.sigep2.mbean.hojadevida;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaProfesionalDTO;

import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

public class LazyModelExperienciaProfesional extends LazyDataModel<ExperienciaProfesionalDTO> {

	private static final long serialVersionUID = 1511663979129199769L;

	private String nombreTipoEntidad, nombreEntidad, dependencia, cargoEntidadExp;
	private long codPersona;
	Boolean flgValidaJefe;
	Long id;
	Date fechaIngreso, fechaRetiro;

	public String getNombreTipoEntidad() {
		return nombreTipoEntidad;
	}

	public void setNombreTipoEntidad(String nombreTipoEntidad) {
		this.nombreTipoEntidad = nombreTipoEntidad;
	}

	public String getNombreEntidad() {
		return nombreEntidad;
	}

	public void setNombreEntidad(String nombreEntidad) {
		this.nombreEntidad = nombreEntidad;
	}

	public String getDependencia() {
		return dependencia;
	}

	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}

	public String getCargoEntidadExp() {
		return cargoEntidadExp;
	}

	public void setCargoEntidadExp(String cargoEntidadExp) {
		this.cargoEntidadExp = cargoEntidadExp;
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

	public Boolean getFlgValidaJefe() {
		return flgValidaJefe;
	}

	public void setFlgValidaJefe(Boolean flgValidaJefe) {
		this.flgValidaJefe = flgValidaJefe;
	}

	public long getCodPersona() {
		return codPersona;
	}

	public void setCodPersona(long codPersona) {
		this.codPersona = codPersona;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LazyModelExperienciaProfesional(long codPersona) {
		this.codPersona = codPersona;
		try {
			this.setRowCount(HojaDeVidaDelegate.filasExperienciaProfesionalPorPersona(codPersona));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ExperienciaProfesionalDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			return HojaDeVidaDelegate.listarExperienciaProfesional(first, pageSize, codPersona);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return null;
	}
}
