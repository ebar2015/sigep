/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import co.gov.dafp.sigep2.entity.seguridad.DatoAdicionalDTO;
import co.gov.dafp.sigep2.entity.seguridad.EvaluacionDesempenoDTO;
import co.gov.dafp.sigep2.entity.seguridad.FormacionTrabajoDTO;
import co.gov.dafp.sigep2.entity.seguridad.LogroRecursoDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionInstitucionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ParticipacionProyectoDTO;
import co.gov.dafp.sigep2.entity.seguridad.PublicacionDTO;
import co.gov.dafp.sigep2.entity.seguridad.ReconocimientoDTO;
import co.gov.dafp.sigep2.entity.view.CabezaFamiliaDTO;
import co.gov.dafp.sigep2.entity.view.DesplazamientoDTO;
import co.gov.dafp.sigep2.entity.view.OrientacionSexualDTO;
import co.gov.dafp.sigep2.entity.view.ProduccionBibliograficaDTO;
import co.gov.dafp.sigep2.entity.view.TiposLibroInvestigacionDTO;
import co.gov.dafp.sigep2.entity.view.TiposRevistaDTO;


/**
 *
 * @author Andrés Jaramillo
 */

@Remote 
public interface IGerenciaPublicaRemote extends IServiceLocal{
	public Boolean guardarRedesSociales(long idPersona, DatoAdicionalDTO dato);
	public Boolean guardarDatosAdicionales(long idPersona, DatoAdicionalDTO dato);
	public Boolean guardarReconocimiento(long idPersona, ReconocimientoDTO dato);
	public Boolean guardarLogroRecurso(long idPersona, LogroRecursoDTO logro);
	public Boolean guardarParticipacionInstitucion(long idPersona, ParticipacionInstitucionDTO participacion);
	public Boolean guardarParticipacionProyecto(long idPersona, ParticipacionProyectoDTO participacion);
	public Boolean guardarPublicacion(long idPersona, PublicacionDTO publicacion);
	public Boolean guardarFormacionTrabajo(long idPersona, FormacionTrabajoDTO formacion);
	public Boolean guardarEvaluacionDesempeno(long idPersona, EvaluacionDesempenoDTO evaluacion, long idEntidad);
	public List<CabezaFamiliaDTO> obtenerParametricasCabezaFamilia();
	public List<DesplazamientoDTO> obtenerParametricasDesplazamiento();
	public List<OrientacionSexualDTO> obtenerParametricasOrientacionSexual();
	public List<TiposLibroInvestigacionDTO> obtenerParametricasLibrosInvestigacion();
	public List<TiposRevistaDTO> obtenerParametricasTiposRevista();
	public List<ProduccionBibliograficaDTO> obtenerParametricasProduccionBibliografica();
	public LogroRecursoDTO obtenerLogroRecursoPorCodPersona(long idPersona);
	public PublicacionDTO obtenerArticulosPorPersona(long codPersona);
	public EvaluacionDesempenoDTO obtenerEvaluacionPorPersona(long idPersona);
	public ParticipacionInstitucionDTO obtenerParticipacionInstitucionPersona(long idPersona);
	public EvaluacionDesempenoDTO obtenerEvaluacionPorPersona(long idPersona, long idEntidad, Date fechaInicio, Date fechaFin);
}
