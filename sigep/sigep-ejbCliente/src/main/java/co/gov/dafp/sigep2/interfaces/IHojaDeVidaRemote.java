package co.gov.dafp.sigep2.interfaces;

import java.util.List;

import javax.ejb.Remote;

import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.HojaDeVidaDTO;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;
import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;

@Remote
public interface IHojaDeVidaRemote extends IServiceRemote {

   List<HojaDeVidaDTO> detallesHojaDeVida(String personaId);

   List<TipoViaDTO> buscarTipoVia();

   List<TipoOrientacionDTO> buscarTipoOrientacion();

   List<TipoLetraDTO> buscarTipoLetra();

   List<HojaDeVidaDTO> datosDeEducacionHojaDeVida(String id);

   List<TipoInstitucionDTO> obtenerTipoInstitucion();

   List<InstitucionEducativaDTO> obtenerInstitucion();

   List<PersonaDTO> listarPersonasConHojaDeVida(int first, int pageSize, PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion,String sortField, String sortOrder);

   int filasPersonasConHV(PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion);

   PersonaDTO detallePersona(String idPersona);
}