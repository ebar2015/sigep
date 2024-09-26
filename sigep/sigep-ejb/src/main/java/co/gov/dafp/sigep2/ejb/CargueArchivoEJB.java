/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.ejb;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.ArchivoCargueDTO;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.factoria.ArchivoCargueFactoria;
import co.gov.dafp.sigep2.factoria.ArchivoCargueLogFactoria;
import co.gov.dafp.sigep2.factoria.ParametricaFactoria;
import co.gov.dafp.sigep2.factoria.ProcesoArchivoFactoria;
import co.gov.dafp.sigep2.factoria.RolFactoria;
import co.gov.dafp.sigep2.factoria.UsuarioFactoria;
import co.gov.dafp.sigep2.interfaces.ICargueArchivoRemote;
import co.gov.dafp.sigep2.interfaces.IEnvioCorreoLocal;
import co.gov.dafp.sigep2.sistema.HCargueArchivo;
import co.gov.dafp.sigep2.sistema.SCargueArchivo;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.logger.Logger;
import co.gov.dafp.sigep2.util.sistema.CargueArchivoDefault;
import co.gov.dafp.sigep2.util.sistema.ProcesoCargueArchivo;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class CargueArchivoEJB implements ICargueArchivoRemote {
	private static final long serialVersionUID = -395427923407413769L;

	transient Logger logger = Logger.getInstance(CargueArchivoEJB.class);

	@EJB
	private IEnvioCorreoLocal mailService;

	@EJB
	private ProcesoArchivoFactoria procesoArchivoFactoria;

	@EJB
	private ArchivoCargueFactoria archivoCargueFactoria;

	@EJB
	private ArchivoCargueLogFactoria archivoCargueLogFactoria;

	@EJB
	private ParametricaFactoria parametricaFactoria;

	@EJB
	private UsuarioFactoria usuarioFactoria;
	
	@EJB
	private RolFactoria rolFactoria;
	
	transient Thread hiloActual;

	@Override
	public void init(final ProcesoArchivoDTO procesoArchivo, Archivo plantillaArchivo, final long entidadCargue,
			final UsuarioDTO usuarioArchivoCargue, final long rolCargueId, final List<File> archivosCague,
			Date fechaCargue, final String rutaArchivoEnCargue, final boolean sincronico) throws SIGEP2SistemaException, InterruptedException {
		String msg = "void init(final ProcesoArchivo procesoArchivo, final Usuario usuarioArchivoCargue, final List<File> archivosCague, Date fechaCargue, final String rutaArchivoEnCargue)";
		
		RolDTO rol = (RolDTO) rolFactoria.find(rolCargueId).getDTO();
		ProcesoCargueArchivo cargueArchivoService;
		try {
			cargueArchivoService = (ProcesoCargueArchivo) Class.forName(procesoArchivo.getInterfazProceso())
					.newInstance();
		} catch (Exception e) {
			logger.log().info(msg,
					"La interfaz implementada no es soportada: '" + procesoArchivo.getInterfazProceso()
							+ "', se implemeta la interfaz por defecto: '" + CargueArchivoDefault.class.getName() + "'",
					e);
			cargueArchivoService = new CargueArchivoDefault();

		}
		Usuario usuarioCargue = usuarioFactoria.find(usuarioArchivoCargue.getId());
		cargueArchivoService.setFechaCargue(fechaCargue);
		cargueArchivoService.setArchivosCague(archivosCague);
		cargueArchivoService.setMailService(mailService);
		cargueArchivoService.setProcesoArchivoFactoria(procesoArchivoFactoria);
		cargueArchivoService.setArchivoCargueDao(archivoCargueFactoria);
		cargueArchivoService.setArchivoCargueLogDao(archivoCargueLogFactoria);
		cargueArchivoService.setParametricaFactoria(parametricaFactoria);
		cargueArchivoService.setUsuarioArchivoCargue(usuarioCargue);
		cargueArchivoService.setRolCargueId(rol);
		cargueArchivoService.setProcesoArchivo(procesoArchivo);
		cargueArchivoService.setEntidadId(entidadCargue);
		cargueArchivoService.setPlantillaArchivo(plantillaArchivo);
		
		if(sincronico){
			this.initSincronico(cargueArchivoService);
		}else{
			this.initAsincronico(cargueArchivoService);
		}
	}

	private void initSincronico(ProcesoCargueArchivo cargueArchivoService) throws SIGEP2SistemaException {
		String msg = "void initSincronico(ProcesoCargueArchivo cargueArchivoService)";
		SCargueArchivo hiloActual = new SCargueArchivo(cargueArchivoService);
		hiloActual.run();
		logger.log().info(msg, "Inicio de Cargue en ejecución");
	}

	private void initAsincronico(ProcesoCargueArchivo cargueArchivoService) throws SIGEP2SistemaException {
		String msg = "void initAsincronico(ProcesoCargueArchivo cargueArchivoService)";
		HCargueArchivo hiloActual = new HCargueArchivo(cargueArchivoService);
		hiloActual.start();
		logger.log().info(msg, "Inicio de Cargue en ejecución");
	}

	public Thread getHiloActual() {
		return hiloActual;
	}

	@Override
	public ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivo) {
		return archivoCargueFactoria.findByNombreArchivoCargue(nombreArchivo);
	}

	@Override
	public List<ProcesoArchivoDTO> getProcesosArchivoPlantillas() {
		return procesoArchivoFactoria.getProcesosArchivoPlantillas();
	}

	@Override
	public List<ProcesoArchivoDTO> getProcesosArchivo() {
		return procesoArchivoFactoria.getProcesosArchivo();
	}

	@Override
	public List<ProcesoArchivoDTO> getPlantillas() {
		return procesoArchivoFactoria.getPlantillas();
	}

	@Override
	public List<ProcesoArchivoDTO> getProcesosArchivo(List<String> nombresPlantilla) {
		return procesoArchivoFactoria.getReportesByPlantilla(nombresPlantilla);
	}

}
