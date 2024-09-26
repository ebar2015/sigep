/**
 * 
 */
package co.gov.dafp.sigep2.movile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.bean.DatoAdicional;
import co.gov.dafp.sigep2.bean.DatoContacto;
import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.ext.DatoAdicionalExt;
import co.gov.dafp.sigep2.bean.ext.DatoContactoExt;
import co.gov.dafp.sigep2.bean.ext.PersonaExt;
import co.gov.dafp.sigep2.bean.ext.RolExt;
import co.gov.dafp.sigep2.services.DatoAdicionalService;
import co.gov.dafp.sigep2.services.DatoContactoService;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.PersonaService;
import co.gov.dafp.sigep2.services.RolService;
import co.gov.dafp.sigep2.services.UsuarioService;
import co.gov.dafp.sigep2.utils.ErrorMensajes;
import co.gov.dafp.sigep2.utils.TipoParametro;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar los servicios de la aplicacion movile
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* @Fecha: Martes 24 de Julio de 2018
*/
public class MovileService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8054605798043310457L;
	
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param Usuario
     * @return Este Servicio actualiza la tabla usuario de forma selectiva por medio del id de la tabla
     */
	public Usuario updateUsuarioSelective(Usuario usuario){
		usuario.setFlgEstado((short) 1);
		UsuarioService service = new UsuarioService();
		return service.updateUsuarioSelective(usuario);
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param codUsuario
     * @return Este Servicio devuelve un Usuario por codigo de tabla
     */
	public Usuario getusuarioId(BigDecimal codUsuario){
		UsuarioService service = new UsuarioService();
		return service.getUsuario(codUsuario);
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 24 Juli 2018
     * @param CodPersona
     * @return Este Servicio devuelve un persona
     */
	public PersonaExt getPersonaPorCodPersona(BigDecimal codPersona){
		PersonaService service = new PersonaService();
		PersonaExt pext = service.personaBycodPersona(codPersona);
		pext.setAudAccion(null);
		pext.setAudCodRol(null);
		pext.setAudCodUsuario(null);
		pext.setAudFechaActualizacion(null);
		pext.setCodDepartamentoNacimiento(null);
		pext.setCodEstadoCivil(null);
		pext.setCodMunicipioNacimiento(null);
		pext.setCodPaisNacimiento(null);
		pext.setCodRol(null);
		return pext;
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 8/08/2018 8:46:45 a.m. 2018
	 * @param persona
	 * @return
	 */
	public ErrorMensajes setPersonaSelective(Persona persona){
		PersonaService service = new PersonaService();
		RolService serviceR = new RolService();
		List<RolExt> roloes = serviceR.getRolByPerosna((PersonaExt) persona);
		ErrorMensajes erro = new ErrorMensajes();
		if(!roloes.isEmpty()){
			if(roloes.get(0).getCodRol().longValue() > 0){
				persona.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
				persona.setAudCodRol(roloes.get(0).getCodRol());
				persona.setAudCodUsuario(new BigDecimal(roloes.get(0).getCodUsuario()));
				persona.setAudFechaActualizacion(new Date());
				erro = service.updatePersonaSelective(persona);
			}else{
				erro.setError(true);
				erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
			}
		}else{
			erro.setError(true);
			erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
		}
		return erro;
	}
	
	
	/**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param codPersona
     * @return Este Servicio devuelve datos de contacto de un usuario
     */
	public DatoContactoExt getDatoContactoPorIdPersona(long codPersona){
		DatoContactoService service = new DatoContactoService();
		DatoContactoExt datoext = service.getDatoContactoPorIdPersona(codPersona);
		datoext.setAudAccion(null);
		datoext.setAudCodRol(null);
		datoext.setAudCodUsuario(null);
		datoext.setAudFechaActualizacion(null);
		datoext.setCodDepartamentoResidencia(null);
		datoext.setCodigoEstado(null);
		datoext.setCodMunicipioResidencia(null);
		datoext.setCodPaisResidencia(null);
		datoext.setCodTipoZona(null);
		datoext.setLimitEnd(null);
		datoext.setLimitInit(null);
		return datoext;
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 8/08/2018 9:11:55 a.m. 2018
	 * @param datocontacto
	 * @return
	 */
	public ErrorMensajes setDatoContactoSelective(DatoContacto datocontacto){
		DatoContactoService service = new DatoContactoService();
		RolService serviceR = new RolService();
		PersonaExt persona = new PersonaExt();
		persona.setCodPersona(datocontacto.getCodPersona());
		List<RolExt> roloes = serviceR.getRolByPerosna( persona);
		DatoContacto erro = new DatoContacto();
		if(!roloes.isEmpty()){
			if(roloes.get(0).getCodRol().longValue() > 0){
				datocontacto.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
				datocontacto.setAudCodRol(roloes.get(0).getCodRol().intValue());
				datocontacto.setAudCodUsuario(new BigDecimal(roloes.get(0).getCodUsuario()));
				datocontacto.setAudFechaActualizacion(new Date());
				erro = service.updateDatoContactoSelective(datocontacto);
			}else{
				erro.setError(true);
				erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
			}
		}else{
			erro.setError(true);
			erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
		}
		return erro;
	}
	
	/**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param codPersona
     * @return Este Servicio devuelve Datos Adicionales
     */
	public DatoAdicionalExt getatoAdicionalByCodPersona(long codPersona){
		DatoAdicionalService service = new DatoAdicionalService();
		DatoAdicionalExt datoAdext = service.getatoAdicionalByCodPersona(codPersona);
		datoAdext.setAudAccion(null);
		datoAdext.setAudCodRol(null);
		datoAdext.setAudCodUsuario(null);
		datoAdext.setAudFechaActualizacion(null);
		datoAdext.setCodCabezaHogar(null);
		datoAdext.setCodigoEstado(null);
		datoAdext.setCodOrientacionSexual(null);
		datoAdext.setCodPais(null);
		datoAdext.setCodTipoDiscapacidad(null);
		datoAdext.setLimitEnd(null);
		datoAdext.setLimitInit(null);
		datoAdext.setCodVictimaDesplazamiento(null);
		return datoAdext;
	}
	
	 /**
     * @author: Jose Viscaya 
     * @fecha 25 Juli 2018
     * @param codUsuario
     * @return Este Servicio devuelve listado de parametricas por padre id
     */
	public List<Parametrica> getPrametricaByPadreiId(BigDecimal codUPadreParametrica){
		ParametricaService service = new ParametricaService();
		Parametrica param = new Parametrica();
		param.setCodPadreParametrica(codUPadreParametrica);
		List<Parametrica> params = service.getPrametricaByPadreiId(param);
		if(!params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				params.get(i).setAudAccion(null);
				params.get(i).setAudCodRol(null);
				params.get(i).setAudCodUsuario(null);
				params.get(i).setAudFechaActualizacion(null);
				params.get(i).setCodigoEstado(null);
				params.get(i).setCodPadreParametrica(null);
				params.get(i).setFlgEstado(null);
				params.get(i).setLimitEnd(null);
				params.get(i).setLimitInit(null);
				params.get(i).setTotal(null);
				params.get(i).setTipoParametro(null);
				params.get(i).setDescripcion(null);
			}
		}
		return params;
	}
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 8/08/2018 9:11:55 a.m. 2018
	 * @param datocontacto
	 * @return
	 */
	public ErrorMensajes setDatoAdicional(DatoAdicional datoAdicional){
		DatoAdicionalService service = new DatoAdicionalService();
		RolService serviceR = new RolService();
		PersonaExt persona = new PersonaExt();
		persona.setCodPersona(datoAdicional.getCodPersona());
		List<RolExt> roloes = serviceR.getRolByPerosna( persona);
		DatoAdicional erro = new DatoAdicional();
		if(!roloes.isEmpty()){
			if(roloes.get(0).getCodRol().longValue() > 0){
				datoAdicional.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
				datoAdicional.setAudCodRol(roloes.get(0).getCodRol().intValue());
				datoAdicional.setAudCodUsuario(new BigDecimal(roloes.get(0).getCodUsuario()));
				datoAdicional.setAudFechaActualizacion(new Date());
				erro = service.updateDatoAdicionalSelective(datoAdicional);
			}else{
				erro.setError(true);
				erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
			}
		}else{
			erro.setError(true);
			erro.setMensaje("Usuario no existe o no se encuentra activo en el Sistema");
		}
		return erro;
	}

}
