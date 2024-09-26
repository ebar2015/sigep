package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.deledago.IngresoSistemaDelegate;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;
import co.gov.dafp.sigep2.util.xml.elemento.Proceso;

@ApplicationScoped
public class ProcesoArchivoProduces implements Serializable {
	private static final long serialVersionUID = -6398697318120622316L;

	private List<ProcesoArchivoDTO> tiposArchivoAdminUsuarios;

	private List<ProcesoArchivoDTO> tiposArchivo;

	private List<ProcesoArchivoDTO> plantillas;

	private List<String> nombresPlantilla;

	List<Archivo> archivos;

	private Proceso procesoArchivo;

	@Named
	@Produces
	public List<ProcesoArchivoDTO> getPlantillas() throws SIGEP2SistemaException {
		if (plantillas == null) {
			plantillas = IngresoSistemaDelegate.getPlantillas();
		}
		return plantillas;
	}

	@Named
	@Produces
	public List<ProcesoArchivoDTO> getProcesosArchivo() throws SIGEP2SistemaException {
		if (tiposArchivo == null) {
			tiposArchivo = IngresoSistemaDelegate.getProcesosArchivo();
		}
		return tiposArchivo;
	}

	@Named
	@Produces
	public List<ProcesoArchivoDTO> getProcesosArchivoModuloAdminUsuarios() throws SIGEP2SistemaException {
		if (tiposArchivoAdminUsuarios == null) {
			if (nombresPlantilla == null) {
				nombresPlantilla = ConfigurationBundleConstants
						.getListString(ConfigurationBundleConstants.CNS_ARCHIVO_PLANTILLAS_ADMINISTRACION_USUARIOS);
			}
			tiposArchivoAdminUsuarios = IngresoSistemaDelegate.getProcesosArchivo(nombresPlantilla);
		}
		return tiposArchivoAdminUsuarios;
	}

	public Proceso getProcesoArchivo() {
		return procesoArchivo;
	}

	public void setProcesoArchivo(Proceso procesoArchivo) {
		this.procesoArchivo = procesoArchivo;
	}

	public List<String> getNombresPlantilla() {
		return nombresPlantilla;
	}

	public void setNombresPlantilla(List<String> nombresPlantilla) {
		this.nombresPlantilla = nombresPlantilla;
	}

	@Named
	@Produces
	public List<Archivo> getArchivos() {
		archivos = procesoArchivo.getArchivo();
		return archivos;
	}
}
