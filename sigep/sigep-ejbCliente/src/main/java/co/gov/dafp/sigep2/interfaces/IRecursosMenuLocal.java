/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Local;

import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;

/**
 *
 * @author JDavila
 */
@Local
public interface IRecursosMenuLocal extends IServiceLocal {
	public RecursoActivoPerfilUsuarioDTO findByCodigoVentana(String codigoVentana, Long usuarioId);

	public List<RecursoActivoPerfilUsuarioDTO> obtenerRecursosActivosPorUsuario(Long usuarioId, Long entidadId);
}
