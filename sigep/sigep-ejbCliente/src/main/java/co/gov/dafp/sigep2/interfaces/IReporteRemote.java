/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.ReporteDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.Registro;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.util.xml.reporte.XmlReporte;

/**
 *
 * @author JDavila
 */
@Remote
public interface IReporteRemote extends IServiceRemote {
	public String validate(final ReporteDTO proceso, final List<Parametro> parametros);

	public List<Registro> exec(final co.gov.dafp.sigep2.util.xml.reporte.config.Registro tipoRegistro,
			final XmlReporte proceso, final UsuarioDTO usuarioReporte, final List<Parametro> parametros,
			final Date fechaReporte, final String rutaArchivoSalida, int first, int pageSize, boolean generarParaCorreo)
			throws SIGEP2NegocioException;

	public ReporteDTO getReporte(Long id) throws SIGEP2SistemaException;

	public ReporteDTO getReporte(String descripcion) throws SIGEP2SistemaException;

	public int getRowsCount();

	public RolDTO getRol(String nombre) throws SIGEP2SistemaException;

	public List<Object[]> ejecutarQuery(String sqlString, Map<Object, Object> parameters, int maxResult)
			throws SIGEP2SistemaException;

}
