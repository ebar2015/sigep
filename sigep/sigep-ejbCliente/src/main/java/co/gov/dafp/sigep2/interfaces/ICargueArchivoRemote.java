/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.ArchivoCargueDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.ProcesoArchivoDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.elemento.Archivo;

/**
 *
 * @author JDavila
 */
@Remote
public interface ICargueArchivoRemote extends IServiceRemote {
	public void init(final ProcesoArchivoDTO proceso, Archivo plantillaArchivo, final long entidadCargue, final UsuarioDTO usuarioArchivoCargue,
			final long rolCargueId, final List<File> archivoCague, final Date fechaCargue,
			final String rutaArchivoEnCargue, final boolean sincronico) throws SIGEP2SistemaException, InterruptedException;

	public ArchivoCargueDTO findByNombreArchivoCargue(String nombreArchivo) throws SIGEP2SistemaException;

	public List<ProcesoArchivoDTO> getProcesosArchivoPlantillas() throws SIGEP2SistemaException;

	public List<ProcesoArchivoDTO> getProcesosArchivo() throws SIGEP2SistemaException;

	public List<ProcesoArchivoDTO> getPlantillas() throws SIGEP2SistemaException;

	public List<ProcesoArchivoDTO> getProcesosArchivo(List<String> nombresPlantilla) throws SIGEP2SistemaException;

	public Thread getHiloActual() throws SIGEP2SistemaException;
}
