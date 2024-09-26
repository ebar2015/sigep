package co.gov.dafp.sigep2.mbean.hojadevida;


import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.deledago.HojaDeVidaDelegate;
import co.gov.dafp.sigep2.entity.seguridad.ExperienciaDocenteDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

public class LazyModelExperienciaDocente extends LazyDataModel<ExperienciaDocenteDTO> {

	private static final long serialVersionUID = 1L;
	String institucion, nivelEducativo, areaConocimiento, pais, fechaInicio, fechaFin;
	UsuarioDTO cod_persona;
	boolean verificado;
	long id;
	
	public UsuarioDTO getCod_persona() {
		return cod_persona;
	}

	public void setCod_persona(UsuarioDTO cod_persona) {
		this.cod_persona = cod_persona;
	}

	public String getInstitucion() {
		return institucion;
	}

	public void setInstitucion(String institucion) {
		this.institucion = institucion;
	}

	public String getNivelEducativo() {
		return nivelEducativo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNivelEducativo(String nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public String getAreaConocimiento() {
		return areaConocimiento;
	}

	public void setAreaConocimiento(String areaConocimiento) {
		this.areaConocimiento = areaConocimiento;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public boolean isVerificado() {
		return verificado;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public LazyModelExperienciaDocente(UsuarioDTO cod_persona) {
		this.cod_persona = cod_persona;
		
		try {
			this.setRowCount(HojaDeVidaDelegate.obtenerFilasExperienciaDocente(cod_persona));
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ExperienciaDocenteDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters ) {
		try {
			return HojaDeVidaDelegate.listarExperienciaDocente(first, pageSize, cod_persona);
		} catch (SIGEP2SistemaException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}