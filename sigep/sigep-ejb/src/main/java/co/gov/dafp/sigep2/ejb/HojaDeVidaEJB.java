package co.gov.dafp.sigep2.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.gov.dafp.sigep2.entity.seguridad.InstitucionEducativaDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.HojaDeVidaDTO;
import co.gov.dafp.sigep2.entity.view.TipoInstitucionDTO;
import co.gov.dafp.sigep2.entity.view.TipoLetraDTO;
import co.gov.dafp.sigep2.entity.view.TipoOrientacionDTO;
import co.gov.dafp.sigep2.entity.view.TipoViaDTO;
import co.gov.dafp.sigep2.factoria.ExperienciaDocenteFactoria;
import co.gov.dafp.sigep2.factoria.GestionarHojaDeVidaFactoria;
import co.gov.dafp.sigep2.factoria.InstitucionFactoria;
import co.gov.dafp.sigep2.factoria.PersonaFactoria;
import co.gov.dafp.sigep2.factoria.TipoInstitucionFactoria;
import co.gov.dafp.sigep2.factoria.TipoLetraFactoria;
import co.gov.dafp.sigep2.factoria.TipoOrientacionFactoria;
import co.gov.dafp.sigep2.factoria.TipoViaFactoria;
import co.gov.dafp.sigep2.interfaces.IHojaDeVidaRemote;

@Stateless
public class HojaDeVidaEJB implements IHojaDeVidaRemote {
   private static final long serialVersionUID = -3345538355024286776L;

   transient static final Logger logger = LoggerFactory.getLogger(HojaDeVidaEJB.class);

   @EJB
   PersonaFactoria personaFactoria;

   @EJB
   GestionarHojaDeVidaFactoria hojaFactoria;

   @EJB
   TipoViaFactoria tipoViaFactoria;

   @EJB
   TipoOrientacionFactoria tipoOrientacionFactoria;

   @EJB
   TipoLetraFactoria tipoLetraFactoria;

   @EJB
   TipoInstitucionFactoria tipoInstitucionFactoria;

   @EJB
   InstitucionFactoria institucionFactoria;

   @EJB
   ExperienciaDocenteFactoria experienciaDocenteFactoria;

   @Override
   public List<HojaDeVidaDTO> detallesHojaDeVida(String personaId) {
      return hojaFactoria.detallesHojaDeVida(personaId);
   }

   @Override
   public List<TipoViaDTO> buscarTipoVia() {
      return tipoViaFactoria.buscarTipoVia();
   }

   @Override
   public List<TipoOrientacionDTO> buscarTipoOrientacion() {
      return tipoOrientacionFactoria.buscarTipoOrientacion();
   }

   @Override
   public List<TipoLetraDTO> buscarTipoLetra() {
      return tipoLetraFactoria.buscarTipoLetra();
   }

   @Override
   public List<HojaDeVidaDTO> datosDeEducacionHojaDeVida(String id) {
      return hojaFactoria.datosDeEducacionHojaDeVida(id);
   }

   @Override
   public List<PersonaDTO> listarPersonasConHojaDeVida(int first, int pageSize, PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion,String sortField, String sortOrder) {
      return hojaFactoria.listarPersonasConHojaDeVida(first, pageSize, persona, usuario, codEntidadSesion, codTipoAsociacion, false,sortField,sortOrder);
   }

   @Override
   public int filasPersonasConHV(PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion) {
	   PersonaDTO conteo = hojaFactoria.listarPersonasConHojaDeVida(0, 0, persona, usuario, codEntidadSesion, codTipoAsociacion, true,null,null).get(0);
	   return conteo.getTotal().intValue();
   }

   @Override
   public PersonaDTO detallePersona(String idPersona) {
      return hojaFactoria.detallePersona(idPersona);
   }

   @Override
   public List<TipoInstitucionDTO> obtenerTipoInstitucion() {
      return tipoInstitucionFactoria.obtenerTipoInstitucionEducativa();
   }

   @Override
   public List<InstitucionEducativaDTO> obtenerInstitucion() {
      return institucionFactoria.obtenerInstitucionEducativa();
   }
}