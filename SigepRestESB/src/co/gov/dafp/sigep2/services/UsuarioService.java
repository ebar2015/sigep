
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Persona;
import co.gov.dafp.sigep2.bean.Token;
import co.gov.dafp.sigep2.bean.Usuario;
import co.gov.dafp.sigep2.bean.UsuarioEntidad;
import co.gov.dafp.sigep2.bean.UsuarioExample;
import co.gov.dafp.sigep2.bean.ext.UsuarioExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.UsuarioMapper;
import co.gov.dafp.sigep2.utils.StringEncrypt;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla UsuarioService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class UsuarioService implements Serializable {

	private static final long serialVersionUID = 5256831832567470185L;
	private static final String URL_LOGIN_EXTERNO ="URL_LOGIN_EXTERNO";
	private static final String MAXIMO_INTENTOS_FALLIDOS_LOGIN = "MAXIMO_INTENTOS_FALLIDOS_LOGIN";
	/**
	 * 
	 * @param SigepLoginService
	 * @return
	 */
	public Usuario insertUsuario (Usuario usuario){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			id =  mapper.insert(usuario);
			if(id > 0){
				usuario = new Usuario();
				usuario.setCodPersona(new BigDecimal(id));
				session.commit();
				return usuario;
			}else{
				usuario = new Usuario();
				usuario.setCodPersona(new BigDecimal(0));
				return usuario;
			}
		}catch (Exception ex) {
			usuario = new Usuario();
			usuario.setCodPersona(new BigDecimal(0));
			return usuario;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Usuario
	 * @return
	 */
	public boolean updateUsuario(Usuario usuario){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			id = mapper.updateByPrimaryKey(usuario);
			session.commit();
			return (id==1)?true:false;
		}catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param usuario
	 * @return
	 * @Fecha :26 feb. 2019
	 * UsuarioService.java
	 */
	public Usuario updateUsuarioOP(BigDecimal usuario){
		Usuario user = new Usuario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			id = mapper.updateRolOP(usuario);
			session.commit();
			if(id > 0){
				user.setError(false);
				user.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else{
				user.setError(false);
				user.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return user;
		}catch (Exception ex) {
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(ex.getMessage());
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Usuario updateUsuarioSelective(Usuario usuario){
		Usuario user = new Usuario();
		UsuarioExample dtoObject = new UsuarioExample();
		if(usuario!=null && usuario.getCodUsuario()!=null) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(usuario.getCodUsuario());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			usuario.setCodUsuario(null);
			id = mapper.updateByExampleSelective(usuario, dtoObject);
			session.commit();
			if(id > 0){
				user.setError(false);
				user.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			}else{
				user.setError(false);
				user.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return user;
		}catch (Exception ex) {
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(ex.getMessage());
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 11:51:08 a.m.
	* @param usuario
	* @return
	*
	 */
	public Usuario recuperarContrasena(Usuario usuario){
		Usuario user = new Usuario();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			usuario.setCodUsuario(null);
			id = mapper.recuperarContrasena(usuario);
			session.commit();
			if(id > 0){
				user.setError(false);
				user.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
				PersonaService service = new PersonaService();
				Persona persona = service.personaByCodUsuario(usuario);
				user.setMensajeTecnico(persona.getCorreoElectronico());
			}else{
				user.setError(true);
				user.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return user;
		}catch (Exception ex) {
			user.setError(true);
			user.setMensaje(UtilsConstantes.MSG_EXEPCION);
			user.setMensajeTecnico(ex.getMessage());
			return user;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @return
	 */
	public List<Usuario> getUsuarioByAll(){
		List<Usuario> asoc = new ArrayList<>();
		UsuarioExample dtoObject = new UsuarioExample();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				if(asoc.size() > UtilsConstantes.MAX_REG) {
					System.out.println(this.getClass().getName());
					asoc = null;
					return new ArrayList<>();
				}
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param usuarioEntidad
	 * @return
	 */
	public List<Usuario> getUsuariosPorEntidad(UsuarioEntidad usuarioEntidad){
		List<Usuario> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectUsByEntidad(usuarioEntidad);
			if(!asoc.isEmpty()){
				if(asoc.size() > UtilsConstantes.MAX_REG) {
					System.out.println(this.getClass().getName());
					asoc = null;
					return new ArrayList<>();
				}
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param usuario
	 * @return
	 * @Fecha :26 feb. 2019
	 * UsuarioService.java
	 */
	public List<UsuarioExt> getUsuariosPorEntidad(Usuario usuario){
		List<UsuarioExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByUsuarioRolOP(usuario);
			if(!asoc.isEmpty()){
				if(asoc.size() > UtilsConstantes.MAX_REG) {
					System.out.println(this.getClass().getName());
					asoc = null;
					return new ArrayList<>();
				}
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public UsuarioExt usuarioLoginMobile(Usuario usuario){
		List<UsuarioExt> result = new ArrayList<>();
		SqlSession session = null;
		UsuarioExt asoc = new UsuarioExt();
		ParametricaService service = new ParametricaService();
		List<Parametrica> param = service.getParametricaIntentos(MAXIMO_INTENTOS_FALLIDOS_LOGIN);
		if(param.isEmpty()) {
			asoc.setError(true);
			String par = UtilsConstantes.MSG_ERROR_SISTEMA_PARAMETRICA_NO_EXISTE;
			par = par.replace("#param", MAXIMO_INTENTOS_FALLIDOS_LOGIN);
			asoc.setMensaje(par);
			return asoc;
		}
		try {
			if(usuario.getNombreUsuario() !=null || !usuario.getNombreUsuario().isEmpty()) {
				usuario.setNombreUsuario(usuario.getNombreUsuario().toUpperCase());
			}else {
				asoc.setError(true);
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
				asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
				return asoc;
			}
		} catch (NullPointerException e) {
			asoc.setError(true);
			asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
			asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
			return asoc;
		}
		
		
		if(usuario.getCodTipoIdentificacion() == null || usuario.getCodTipoIdentificacion().longValue() == 0) {
			asoc.setError(true);
			asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
			asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_TIPO_IDENTIFICACION).get(0).getValorParametro());
			return asoc;
		}
		
		try {
			if(!usuario.getContrasena().isEmpty() || usuario.getContrasena() !=null) {
				usuario.setContrasena(StringEncrypt.encrypt(usuario.getContrasena()));
			}else {
				asoc.setError(true);
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
				asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
				return asoc;
			}
		} catch (NullPointerException e) {
			asoc.setError(true);
			asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
			asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
			return asoc;
		}
		
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			result =  mapper.selectLogin(usuario);
			if(result.size() == 1) {
					if(result.get(0).getCodTipoIdentificacion().longValue() == usuario.getCodTipoIdentificacion().longValue()) {
						if(result.get(0).getContrasena().equals(usuario.getContrasena())) {
							if(result.get(0).getFlgBloqueado() == 1) {
								asoc.setError(true);
								asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
								asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
								return asoc;
							}
							if(!result.get(0).getFlgEstado()) {
								asoc.setError(true);
								asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_INACTIVO).get(0).getValorParametro());
								asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
								return asoc;
							}
							try {
								if(result.get(0).getFechaVence() != null) {
									if(result.get(0).getFechaVence().before(new Date())) {
										asoc.setError(true);
										asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_EXIPRO).get(0).getValorParametro());
										asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
										return asoc;
									}
								}
							} catch (Exception e) {}
							
							TokenService serviceT = new TokenService();
							String token = serviceT.getToken();
							Token tok = new Token();
							tok.setAudAccion(785);
							tok.setAudCodRol(result.get(0).getAudCodRol().intValue());
							tok.setAudCodUsuario(result.get(0).getCodUsuario().longValue());
							tok.setAudFechaActualizacion(new Date());
							tok.setCodUsuario(result.get(0).getCodUsuario());
							tok.setError(false);
							tok.setOrigen((short) 2);
							tok.setFechaGeneracion(new Date());
							tok.setFlgActivo((short) 1);
							tok.setToken(token);
							Token res = serviceT.insertToken(tok);
							if(!res.isError()) {
								asoc.setError(false);
								asoc.setTicket(token);
								asoc.setOrigen("1");
								asoc.setCodPersona(result.get(0).getCodPersona());
								asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
								asoc.setCodUsuario(result.get(0).getCodUsuario());
								asoc.setFlgAceptoHabeasData(result.get(0).getFlgAceptoHabeasData());
								asoc.setCodRol(result.get(0).getCodRol());
								asoc.setFechaAceptoHabeas(result.get(0).getFechaAceptoHabeas());
								result.get(0).setAudFechaActualizacion(new Date());
								result.get(0).setFechaUltimoIngreso(new Date());
								result.get(0).setFlgAceptoHabeasData(null);
								updateUsuarioSelective(result.get(0));
								return asoc;
							}else {
								asoc.setError(true);
								asoc.setMensaje(res.getMensaje());
								asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
								asoc.setMensajeTecnico(res.getMensajeTecnico());
								return asoc;
							}
						}else {
							asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_CONTRASENA_NO_COINCIDE).get(0).getValorParametro());
							if(param.isEmpty()) {
								asoc.setError(true);
								String par = UtilsConstantes.MSG_ERROR_SISTEMA_PARAMETRICA_NO_EXISTE;
								par = par.replace("#param", MAXIMO_INTENTOS_FALLIDOS_LOGIN);
								asoc.setMensaje(par);
								asoc.setFlgBloqueado((short) 0);
								return asoc;
							}
							int intMax = Integer.parseInt(param.get(0).getValorParametro());
							int inlog = result.get(0).getNumeroReintentos().intValue();
							if(inlog == intMax) {
								result.get(0).setFlgBloqueado((short) 1);
								result.get(0).setFechaVence(new Date());
								result.get(0).setAudFechaActualizacion(new Date());
								updateUsuarioSelective(result.get(0));
								asoc.setError(true);
								asoc.setFlgBloqueado((short) 1);
								asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
							}else {
								inlog++;
								result.get(0).setFlgBloqueado((short) 0);
								result.get(0).setNumeroReintentos(new BigDecimal(0));
								result.get(0).setNumeroReintentos(new BigDecimal(inlog));
								result.get(0).setAudFechaActualizacion(new Date());
								updateUsuarioSelective(result.get(0));
								asoc.setError(true);
								asoc.setFlgBloqueado((short) 0);
							}
							return asoc;
						}
					}else {
						asoc.setError(true);
						asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_TIPO_IDENTIFICACION).get(0).getValorParametro());
						asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
						return asoc;
					}
			}else {
				asoc.setError(true);
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_CUENTA_NO_EXISTE).get(0).getValorParametro());
				asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
				return asoc;
			}
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			asoc.setNumeroReintentos(new BigDecimal(param.get(0).getValorParametro()));
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public UsuarioExt usuarioLogin(UsuarioExt usuario){
		List<UsuarioExt> result = new ArrayList<>();
		SqlSession session = null;
		UsuarioExt asoc = new UsuarioExt();
		String idioma = "";
		try {
			idioma = usuario.getIdioma().toUpperCase();
			if(idioma == null || idioma.isEmpty()) {
				idioma = "ES";
			}
			if(!idioma.equals("ES") && !idioma.equals("US")) {
				idioma = "ES";
			}
		} catch (Exception e) {
			idioma = "ES";
		}
		
		ParametricaService service = new ParametricaService();
		List<Parametrica> param = service.getParametricaIntentos(MAXIMO_INTENTOS_FALLIDOS_LOGIN);
		if(param.isEmpty()) {
			asoc.setError(true);
			String par = UtilsConstantes.MSG_ERROR_SISTEMA_PARAMETRICA_NO_EXISTE;
			par = par.replace("#param", MAXIMO_INTENTOS_FALLIDOS_LOGIN);
			asoc.setMensaje(par);
			asoc.setFlgBloqueado((short) 0);
			return asoc;
		}
		int intMax = Integer.parseInt(param.get(0).getValorParametro());
		try {
			if(usuario.getNombreUsuario() != null ){ 
				if(usuario.getNombreUsuario().length() > 4) {
					usuario.setNombreUsuario(usuario.getNombreUsuario().toUpperCase());
					if(usuario.getCodTipoIdentificacion()!= null) {
						Long codId = usuario.getCodTipoIdentificacion().longValue();
						if(codId.equals(38) || codId.equals(39) || codId.equals(42)) {
							if(idioma.equals("ES")) {
								asoc.setMensaje(service.getPrametricaById(new BigDecimal(UtilsConstantes.MSG_ES_TIPO_DOCUMENTO_INVALIDO)).getValorParametro());
							}else {
								asoc.setMensaje(service.getPrametricaById(new BigDecimal(UtilsConstantes.MSG_US_TIPO_DOCUMENTO_INVALIDO)).getValorParametro());
							}
							asoc.setError(true);
							return asoc;
						}
					}
				}else {
					asoc.setError(true);
					if(idioma.equals("ES")) {
						asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
					}else {
						asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
					}
				asoc.setFlgBloqueado((short) 0);
				return asoc;
				}
			}else {
				asoc.setError(true);
				if(idioma.equals("ES")) {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
				}else {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
				}
				asoc.setFlgBloqueado((short) 0);
				return asoc;
			}
		} catch (NullPointerException e) {
			asoc.setError(true);
			if(idioma.equals("ES")) {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
			}else {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_NOMBRE_USUARIO).get(0).getValorParametro());
			}
			asoc.setFlgBloqueado((short) 0);
			return asoc;
		}
		
		
		if(usuario.getCodTipoIdentificacion() == null || usuario.getCodTipoIdentificacion().longValue() == 0) {
			asoc.setError(true);
			if(idioma.equals("ES")) {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_TIPO_IDENTIFICACION).get(0).getValorParametro());
			}else {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_TIPO_IDENTIFICACION).get(0).getValorParametro());
			}
			asoc.setFlgBloqueado((short) 0);
			return asoc;
		}
		
		try {
			if(!usuario.getContrasena().isEmpty() || usuario.getContrasena() !=null) {
				usuario.setContrasena(StringEncrypt.encrypt(usuario.getContrasena()));
			}else {
				asoc.setError(true);
				if(idioma.equals("ES")) {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
				}else {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_CONTRASENA).get(0).getValorParametro());
				}
				asoc.setFlgBloqueado((short) 0);
				return asoc;
			}
		} catch (NullPointerException e) {
			asoc.setError(true);
			if(idioma.equals("ES")) {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_REQUIERE_CONTRASENA).get(0).getValorParametro());
			}else {
				asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_REQUIERE_CONTRASENA).get(0).getValorParametro());
			}
			asoc.setFlgBloqueado((short) 0);
			return asoc;
		}
		
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			Usuario user = new Usuario();
			user = usuario;
			result =  mapper.selectLogin(user);
			if(result.size() == 1) {
					if(result.get(0).isFlgBloqueado()) {
						asoc.setError(true);
						if(idioma.equals("ES")) {
							asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
						}else {
							asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_USUARIO_BLOQUEADO).get(0).getValorParametro());
						}
						asoc.setFlgBloqueado((short) 1);
						return asoc;
					 }
						if(result.get(0).getContrasena().equals(usuario.getContrasena())) {
							if(result.get(0).isFlgBloqueado()) {
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_USUARIO_BLOQUEADO).get(0).getValorParametro());
								}
								asoc.setFlgBloqueado((short) 1);
								return asoc;
							}
							if(!result.get(0).getFlgEstado()) {
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_INACTIVO).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_USUARIO_INACTIVO).get(0).getValorParametro());
								}
								asoc.setFlgBloqueado((short) 1);
								return asoc;
							}
							if(result.get(0).getFlgActivo() == 0) {
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_INACTIVO).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_USUARIO_INACTIVO).get(0).getValorParametro());
								}
								asoc.setFlgBloqueado((short) 1);
								return asoc;
							}
							param = service.getParametricaIntentos(URL_LOGIN_EXTERNO);
							if(param.size() == 1) {
								String url = param.get(0).getValorParametro();
								long datatime = new Date().getTime();
								asoc.setError(false);
								String encry = StringEncrypt.encrypt(datatime+"");
								url+=encry;
								url = url.replace("\\u003d", "=");
								asoc.setTicket(url);
								asoc.setOrigen("1");
								result.get(0).setTicket(encry);
								result.get(0).setAudFechaActualizacion(new Date());
								result.get(0).setFechaUltimoIngreso(new Date());
								result.get(0).setFlgEsInterno((short) 1);
								result.get(0).setNumeroReintentos(new BigDecimal(0));
								updateUsuarioSelective(result.get(0));
								return asoc;
							}else {
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_ERROR_PARAM_LOGIN).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_ERROR_PARAM_LOGIN).get(0).getValorParametro());
								}
								return asoc;
							}
						}else {
							int inlog = result.get(0).getNumeroReintentos().intValue();
							if(inlog == intMax) {
								result.get(0).setFlgBloqueado((short) 1);
								result.get(0).setFechaVence(new Date());
								result.get(0).setAudFechaActualizacion(new Date());
								updateUsuarioSelective(result.get(0));
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_BLOQUEADO).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_USUARIO_BLOQUEADO).get(0).getValorParametro());
								}
								asoc.setFlgBloqueado((short) 1);
							}else {
								inlog++;
								result.get(0).setFlgBloqueado((short) 0);
								result.get(0).setNumeroReintentos(new BigDecimal(inlog));
								result.get(0).setAudFechaActualizacion(new Date());
								updateUsuarioSelective(result.get(0));
								asoc.setError(true);
								if(idioma.equals("ES")) {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_CONTRASENA_NO_COINCIDE).get(0).getValorParametro());
								}else {
									asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_CONTRASENA_NO_COINCIDE).get(0).getValorParametro());
								}
								asoc.setFlgBloqueado((short) 0);
							}
							return asoc;
						}
			}else {
				asoc.setError(true);
				if(idioma.equals("ES")) {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_CUENTA_NO_EXISTE).get(0).getValorParametro());
				}else {
					asoc.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_US_CUENTA_NO_EXISTE).get(0).getValorParametro());
				}
				asoc.setFlgBloqueado((short) 0);
				return asoc;
			}
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public BigDecimal getCodUsuario(BigDecimal codPersona){
		List<Usuario> asoc = new ArrayList<>();
		UsuarioExample dtoObject = new UsuarioExample();
		dtoObject.createCriteria().andCodPersonaEqualTo(codPersona);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0).getCodUsuario();
			}else{
				return new BigDecimal(0);
			}
		}catch(Exception ex){
			return new BigDecimal(0);
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * Elaborado Por: Jose Viscaya
	 * 12 feb. 2019
	 * UsuarioService.java
	 * @param codPersona
	 * @return
	 */
	public Usuario getUsuarioByPersona(BigDecimal codPersona){
		List<Usuario> asoc = new ArrayList<>();
		UsuarioExample dtoObject = new UsuarioExample();
		if(codPersona!=null) {
			dtoObject.createCriteria().andCodPersonaEqualTo(codPersona);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return null;
			}
		}catch(Exception ex){
			return null;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param codPersona
	 * @return
	 */
	public UsuarioExt loginWeb(Usuario usuario){
		UsuarioExt asoc = new UsuarioExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectLoginWeb(usuario);
			if(asoc !=null){
				return asoc;
			}else{
				return new UsuarioExt();
			}
		}catch(Exception ex){
			return new UsuarioExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @return
	 */
	public String getToken(){
		String token = "";
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			token =  mapper.getToken();
			return token;
		}catch(Exception ex){
			return "";
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public Usuario usuariobyPersona(Usuario usuario){
		List<Usuario> asoc = new ArrayList<>();
		UsuarioExample dtoObject = new UsuarioExample();
		if(usuario.getLimitEnd() > 0) {
			dtoObject.setLimitEnd(usuario.getLimitEnd());
		} 		
		if(usuario.getCodPersona() != null) {
			dtoObject.createCriteria().andCodPersonaEqualTo(usuario.getCodPersona());
		}else if(usuario.getCodUsuario() != null) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(usuario.getCodUsuario());
		}else {
			Usuario us =	 new Usuario();
			us.setError(true);
			us.setMensaje("No se Encuantra CodPersona() ni CodUsuario()");
			return us;
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new Usuario();
			}
		}catch(Exception ex){
			return new Usuario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public Usuario usuarioPassword(Usuario usuario){
		List<Usuario> asoc = new ArrayList<>();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectUserPassword(usuario);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new Usuario();
			}
		}catch(Exception ex){
			return new Usuario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public UsuarioExt selectUsuarioExt(UsuarioEntidad usuario){
		UsuarioExt asoc = new UsuarioExt();
		
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectUsuarioExt(usuario);
			if(asoc!=null){
				return asoc;
			}else{
				return new UsuarioExt();
			}
		}catch(Exception ex){
			return new UsuarioExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Id Search
	 * @return
	 */
	public Usuario getUsuario(BigDecimal codUsuario){
		Usuario asoc = new Usuario();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByPrimaryKey(codUsuario);
			if(asoc!=null){
				return asoc;
			}else{
				return new Usuario();
			}
		}catch(Exception ex){
			return new Usuario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 12/09/2018 9:06:47 a.m.
	* @param usuario
	* @return
	*
	 */
	public Usuario generarPing(Persona persona){
		Usuario asoc = new Usuario();
		ParametricaService service = new ParametricaService();
		SqlSession session = null;
		PersonaService seriveP = new PersonaService();
		persona = seriveP.personaByTipoIdNumId(persona);
		if(persona.getCodPersona() != null){
			Usuario p = getUsuarioByPersona(persona.getCodPersona());
			if(!p.getFlgEstado()) {
				asoc.setError(true);
				asoc.setMensaje(UtilsConstantes.MSG_USUARIO_INACTIVO);
				return asoc;
			}
			asoc.setError(false);
			asoc.setTicket(UtilsConstantes.generarPing());
			asoc.setCodPersona(persona.getCodPersona());
	        asoc.setAudFechaActualizacion(new Date());
		}else{
			asoc = new Usuario();
			asoc.setError(true);
			asoc.setMensaje(service.getPrametricaById(new BigDecimal(2082)).getValorParametro());
			return asoc;
		}
		try{
			if(!asoc.isError()) {
				session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
				UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
				int id =  mapper.guardarPing(asoc);
				if(id > 0){
					Usuario asocR = new Usuario();
					asocR.setError(false);
					asocR.setMensaje(service.getPrametricaById(new BigDecimal(2083)).getValorParametro());
					asocR.setCodPersona(asoc.getCodPersona());
					asocR.setCodUsuario(asoc.getCodUsuario());
					asocR.setTicket(asoc.getTicket());
					asocR.setMensajeTecnico(persona.getCorreoElectronico());
					session.commit();
					return asocR;
				}else{
					Usuario asocR = new Usuario();
					asocR.setError(true);
					asocR.setMensaje(service.getPrametricaById(new BigDecimal(2084)).getValorParametro());
					return asocR;
				}
			}else {
				asoc = new Usuario();
				asoc.setError(true);
				asoc.setMensaje(service.getPrametricaById(new BigDecimal(2082)).getValorParametro());
				return asoc;
			}
		}catch(Exception ex){
			Usuario asocR = new Usuario();
			asocR.setError(true);
			asocR.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asocR.setMensajeTecnico(ex.getMessage());
			return asocR;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 12/09/2018 10:31:23 a.m.
	 * @param usuario
	 * @return
	 *
	 */
	public Usuario validarPing(Usuario usuario){
		Usuario asoc = new Usuario();
		Usuario resp = new Usuario();
		ParametricaService service = new ParametricaService();
		Parametrica timePing = service.getPrametricaById(new BigDecimal(2073));
		long timep = 1000;
		try {
			timep = Long.parseLong(timePing.getValorParametro());
		} catch (NumberFormatException e) {
			timep = 6;
		}
		Usuario dtoObject = new Usuario();
		dtoObject.setTicket(usuario.getTicket());
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper  mapper = session.getMapper(UsuarioMapper.class);
			asoc =  mapper.selectByticket(dtoObject);
			if(asoc != null){
					if(asoc.getNombreUsuario().equals(usuario.getNombreUsuario())){
						if(asoc.getContrasena().equals(usuario.getContrasena())) {
							resp.setError(true);
							resp.setMensaje(UtilsConstantes.MSG_CONTRASENA_INVALIDA);
							return resp;
						}
						if(!asoc.getFlgEstado()) {
							resp.setError(true);
							resp.setMensaje(service.getParametricaIntentos(UtilsConstantes.MSG_ES_USUARIO_INACTIVO).get(0).getValorParametro());
							return resp;
						}
					   long tiempo = TokenService.getDiferenciaFechas(asoc.getAudFechaActualizacion(), new Date());
					   if(tiempo < (timep/60000)){
						   resp.setError(false); 
						   resp.setMensaje(service.getPrametricaById(new BigDecimal(2078)).getValorParametro());
						   asoc.setContrasena(usuario.getContrasena());
						   asoc.setAudFechaActualizacion(new Date());
						   mapper.updateByPrimaryKeySelective(asoc);
						   session.commit();
					   }else{
						   resp.setError(true);
						   resp.setMensaje(service.getPrametricaById(new BigDecimal(2078)).getValorParametro());
					   }
					}else{
						resp.setError(true);
						resp.setMensaje(service.getPrametricaById(new BigDecimal(2082)).getValorParametro());
					}
			}else{
				resp.setError(true);
				resp.setMensaje(service.getPrametricaById(new BigDecimal(2075)).getValorParametro());
			}
			return resp;
		}catch(Exception ex){
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return new Usuario();
		}finally{
			if (session != null) {
				session.close();
			}
		}
		
	}
	
	/**
	 * Metodo que obtiene un usuario por numero de identificacion
	 * y  tipo de identificacion de la persona 
	 * @param usuarioExt
	 * @return Usuario
	 */
	public Usuario getUsuarioByPersona(UsuarioExt usuarioExt) {
		Usuario asoc = new Usuario();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper mapper = session.getMapper(UsuarioMapper.class);
			asoc = mapper.selectUsuarioByPersona(usuarioExt);
			if (asoc == null) {
				asoc = new Usuario();
				asoc.setError(true);
				asoc.setMensaje(UtilsConstantes.MSG_SIN_INFORMACION_USUARIO);
			}else{
				asoc.setError(false);
			}
			return asoc;
		} catch (Exception ex) {
			asoc = new Usuario();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * Metodo que obtiene si el usuario tiene roles admin por numero de identificacion
	 * y  tipo de identificacion de la persona 
	 * @param usuarioExt
	 * @return Usuario
	 */
	public UsuarioExt getUsuarioAdmin(UsuarioExt usuarioExt) {
		UsuarioExt asoc = new UsuarioExt();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			UsuarioMapper mapper = session.getMapper(UsuarioMapper.class);
			asoc = mapper.selectUsuarioAdmin(usuarioExt);
			if (asoc == null) {
				asoc = new UsuarioExt();
				asoc.setError(true);
				asoc.setMensaje(UtilsConstantes.MSG_SIN_INFORMACION_USUARIO);
			}else{
				asoc.setError(false);
			}
			return asoc;
		} catch (Exception ex) {
			asoc = new UsuarioExt();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(ex.getMessage());
			return asoc;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
