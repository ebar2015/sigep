package co.gov.dafp.sigep2.entity.seguridad;

import java.io.Serializable;

import co.gov.dafp.sigep2.entity.DepartamentoDTO;
import co.gov.dafp.sigep2.entity.EntidadBaseDTO;
import co.gov.dafp.sigep2.entity.MunicipioDTO;
import co.gov.dafp.sigep2.entity.PaisDTO;
import co.gov.dafp.sigep2.entity.view.TipoZonaDTO;

/**
 * The persistent class for the DATOS CONTACTO database table.
 * 
 */
public class DatoContactoDTO extends EntidadBaseDTO implements Serializable {
	private static final long serialVersionUID = 4040037313631254729L;

	private long id;
	
	private Long codPersona;
	
	private Long telefonoResidencia;
	
	private Long indicativo;
	
	private Long numCelular;
	
	private Long numTelefonoOficina;
	
	private Long extencionTelefonoOficina;
	
	private PaisDTO codPaisResidencia;
	
	private DepartamentoDTO codDepartamentoResidencia;
	
	private MunicipioDTO codMunicipioResidencia;
	
	private TipoZonaDTO codTipoZona;
	
	private String direccionResidencia;

	public DatoContactoDTO() {
	}	

	public DatoContactoDTO(long id,Long codPersona,Long telefonoResidencia,Long indicativo,
			Long numCelular, Long numTelefonoOficina, Long extencionTelefonoOficina, PaisDTO codPaisResidencia, DepartamentoDTO codDepartamentoResidencia,
			MunicipioDTO codMunicipioResidencia, TipoZonaDTO codTipoZona, String direccionResidencia) {
		super();
		this.id = id;
		this.codPersona = codPersona;
		this.telefonoResidencia = telefonoResidencia;
		this.indicativo = indicativo;
		this.numCelular = numCelular;
		this.numTelefonoOficina = numTelefonoOficina;
		this.extencionTelefonoOficina = extencionTelefonoOficina;
		this.codPaisResidencia = codPaisResidencia;
		this.codDepartamentoResidencia = codDepartamentoResidencia;
		this.codMunicipioResidencia = codMunicipioResidencia;
		this.codTipoZona = codTipoZona;
		this.direccionResidencia = direccionResidencia;
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

	public Long getTelefonoResidencia() {
		return telefonoResidencia;
	}

	public void setTelefonoResidencia(Long telefonoResidencia) {
		this.telefonoResidencia = telefonoResidencia;
	}

	public Long getIndicativo() {
		return indicativo;
	}

	public void setIndicativo(Long indicativo) {
		this.indicativo = indicativo;
	}

	public Long getNumCelular() {
		return numCelular;
	}

	public void setNumCelular(Long numCelular) {
		this.numCelular = numCelular;
	}

	public Long getNumTelefonoOficina() {
		return numTelefonoOficina;
	}

	public void setNumTelefonoOficina(Long numTelefonoOficina) {
		this.numTelefonoOficina = numTelefonoOficina;
	}

	public Long getExtencionTelefonoOficina() {
		return extencionTelefonoOficina;
	}

	public void setExtencionTelefonoOficina(Long extencionTelefonoOficina) {
		this.extencionTelefonoOficina = extencionTelefonoOficina;
	}

	public PaisDTO getCodPaisResidencia() {
		return codPaisResidencia;
	}

	public void setCodPaisResidencia(PaisDTO codPaisResidencia) {
		this.codPaisResidencia = codPaisResidencia;
	}

	public DepartamentoDTO getCodDepartamentoResidencia() {
		return codDepartamentoResidencia;
	}

	public void setCodDepartamentoResidencia(DepartamentoDTO codDepartamentoResidencia) {
		this.codDepartamentoResidencia = codDepartamentoResidencia;
	}

	public MunicipioDTO getCodMunicipioResidencia() {
		return codMunicipioResidencia;
	}

	public void setCodMunicipioResidencia(MunicipioDTO codMunicipioResidencia) {
		this.codMunicipioResidencia = codMunicipioResidencia;
	}

	public TipoZonaDTO getCodTipoZona() {
		return codTipoZona;
	}

	public void setCodTipoZona(TipoZonaDTO codTipoZona) {
		this.codTipoZona = codTipoZona;
	}

	public String getDireccionResidencia() {
		return direccionResidencia;
	}

	public void setDireccionResidencia(String direccionResidencia) {
		this.direccionResidencia = direccionResidencia;
	}

	@Override
	public String toString() {
		return "DatosContactoDTO [id=" + id + ", codPersona=" + codPersona + ", telefonoResidencia=" + telefonoResidencia
				+ ", indicativo=" + indicativo + ", numCelular=" + numCelular + ", numTelefonoOficina="
				+ numTelefonoOficina + ", codPaisResidencia=" + codPaisResidencia.getId()
				+ ", codDepartamentoResidencia=" + codDepartamentoResidencia.getId() + ", codMunicipioResidencia=" + codMunicipioResidencia.getId()
				+ ", codTipoZona=" + codTipoZona.getId() + ", direccionResidencia=" + direccionResidencia + "]";
	}
}