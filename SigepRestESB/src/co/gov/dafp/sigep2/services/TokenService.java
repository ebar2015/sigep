package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.Token;
import co.gov.dafp.sigep2.bean.TokenExample;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.TokenMapper;
import co.gov.dafp.sigep2.util.DateUtils;
import co.gov.dafp.sigep2.utils.TipoParametro;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author Jose Viscaya.
 * @version 1.0
 * @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion
 *        de datos de la tabla TokenService.java
 * @Proyect SIGEPII
 * @Company ADA S.A
 * @Module exposicion de servicios Rest
 * @fecha 27/07/2018 2:56:50 p. m.
 */
public class TokenService implements Serializable {
	private static final long serialVersionUID = -6139022025257247383L;
	private static final BigDecimal ACK_PARAM = new BigDecimal(2214);

	/**
	 * 
	 * @param Token
	 * @return
	 */
	public Token insertToken(Token token) {
		Token tokenR = new Token();
		token.setFechaAck(DateUtils.getFechaSistema());
		token.setFlgActivo((short) 1);
		SqlSession session = null;
		int id = 0;

		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			id = mapper.insert(token);
			if (id > 0) {
				session.commit();
				tokenR.setError(false);
				tokenR.setToken(token.getToken());
				tokenR.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				return tokenR;
			} else {
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
				return tokenR;
			}
		} catch (Exception ex) {
			tokenR.setError(true);
			tokenR.setMensaje(UtilsConstantes.MSG_EXEPCION);
			tokenR.setMensajeTecnico(ex.getMessage());
			return tokenR;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param Token
	 * @return
	 */
	public Token updateToken(Token token) {
		Token tokenR = new Token();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			id = mapper.updateByPrimaryKey(token);
			session.commit();
			if (id == 1) {
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			} else {
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return tokenR;
		} catch (Exception ex) {
			tokenR.setError(true);
			tokenR.setMensaje(UtilsConstantes.MSG_EXEPCION);
			tokenR.setMensajeTecnico(ex.getMessage());
			return tokenR;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean updateTokenUsEnt(Token token) {
		TokenExample dtoObject = new TokenExample();
		if(token!=null) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(token.getCodUsuario())
					.andCodEntidadEqualTo(token.getCodEntidad());
		}
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			id = mapper.updateByExample(token, dtoObject);
			session.commit();
			return (id == 1) ? true : false;
		} catch (Exception ex) {
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public Token updateTokenuser(Token token) {
		Token tokenR = new Token();
		SqlSession session = null;
		token.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			id = mapper.updateEstado(token);
			session.commit();
			if (id == 1) {
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			} else {
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return tokenR;
		} catch (Exception ex) {
			tokenR.setError(true);
			tokenR.setMensaje(UtilsConstantes.MSG_EXEPCION);
			tokenR.setMensajeTecnico(ex.getMessage());
			return tokenR;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean updateTokenAck(String token) {
		ParametricaService service = new ParametricaService();
		Parametrica param = service.getPrametricaById(ACK_PARAM);
		int ascTime = 18;
		try {
			ascTime = Integer.parseInt(param.getValorParametro());
		} catch (NumberFormatException e) {
			ascTime = 18;
		}

		Token asoc = new Token();
		Token tokenOb = new Token();
		Token tokenR = new Token();
		tokenOb.setToken(token);
		tokenOb.setFechaAck(DateUtils.getFechaSistema());
		SqlSession session = null;
		int id = 0;
		Token tok = new Token();
		tok.setToken(token);
		tok.setFlgActivo((short) 1);
		boolean resp = false;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectBytoken(tok);
			Date horarioVenceSesion = DateUtils.sumarMinutos(asoc.getFechaAck(), ascTime);
			if (DateUtils.getFechaSistema().before(horarioVenceSesion)) {
				resp = false;
				id = mapper.updateEstadoAck(tokenOb);
				session.commit();
				if (id == 1) {
					tokenR.setError(false);
					tokenR.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				} else {
					tokenR.setError(true);
					tokenR.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
				}
			} else {
				resp = true;
				tokenR.setError(false);
				tokenR.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return resp;
		} catch (Exception ex) {
			tokenR.setError(true);
			tokenR.setMensaje(UtilsConstantes.MSG_EXEPCION);
			tokenR.setMensajeTecnico(ex.getMessage());
			return false;
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
	public List<Token> getTokenByAll() {
		List<Token> asoc = new ArrayList<>();
		TokenExample dtoObject = new TokenExample();
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {
			return new ArrayList<>();
		} finally {
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
	public Token getTokenBytokenName(String token) {
		List<Token> asoc = new ArrayList<>();
		TokenExample dtoObject = new TokenExample();
		dtoObject.createCriteria().andTokenEqualTo(token).andFlgActivoEqualTo((short) 1);
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc.get(0);
			} else {
				return new Token();
			}
		} catch (Exception ex) {

			return new Token();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	public List<Token> getTokenByCodUsuario(Token token) {
		List<Token> asoc = new ArrayList<>();
		TokenExample dtoObject = new TokenExample();
		if(token!=null) {
			dtoObject.createCriteria().andCodUsuarioEqualTo(token.getCodUsuario()).andFlgActivoEqualTo((short) 1);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc;
			} else {
				return new ArrayList<>();
			}
		} catch (Exception ex) {

			return new ArrayList<>();
		} finally {
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
	public Token getTokenBytokenName(String token, BigDecimal id) {
		List<Token> asoc = new ArrayList<>();
		TokenExample dtoObject = new TokenExample();
		if(token!=null && id!=null) {
			dtoObject.createCriteria().andTokenEqualTo(token).andCodUsuarioEqualTo(id);
		}
		SqlSession session = null;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectByExample(dtoObject);
			if (!asoc.isEmpty()) {
				return asoc.get(0);
			} else {
				return new Token();
			}
		} catch (Exception ex) {

			return new Token();
		} finally {
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
	public boolean isValid(String token, String timeout) {
		Token asoc = new Token();
		Token tok = new Token();
		SqlSession session = null;
		tok.setToken(token);
		tok.setFlgActivo((short) 1);
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			asoc = mapper.selectBytoken(tok);
			long minutos = getDiferenciaFechas(asoc.getAudFechaActualizacion(), DateUtils.getFechaSistema());
			ParametricaService serviceP = new ParametricaService();
			if (timeout == null) {
				if (asoc.getOrigen() == 2) {
					timeout = serviceP.getPrametricaById(new BigDecimal(UtilsConstantes.CNS_TIMEOUT_SESION_APP_MOVIL))
							.getValorParametro();
				} else {
					timeout = UtilsConstantes.TIMEOUT_SESION;
				}

			} else if (timeout.isEmpty() || timeout.equals("0")) {
				if (asoc.getOrigen() == 2) {
					timeout = serviceP.getPrametricaById(new BigDecimal(UtilsConstantes.CNS_TIMEOUT_SESION_APP_MOVIL))
							.getValorParametro();
				} else {
					timeout = UtilsConstantes.TIMEOUT_SESION;
				}
			}

			long time = Integer.parseInt(timeout) / 60000;
			if (asoc.getCodUsuario() != null) {
				List<Token> list = getTokenByCodUsuario(asoc);
				if (!list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						if (getDiferenciaFechas(list.get(i).getAudFechaActualizacion(),
								DateUtils.getFechaSistema()) > time) {
							list.get(i).setFlgActivo((short) 0);
							list.get(i).setAudFechaActualizacion(DateUtils.getFechaSistema());
							list.get(i).setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
							updateToken(list.get(i));
						}
					}
				}
			}
			if (minutos > time) {
				asoc.setFlgActivo((short) 0);
				asoc.setAudFechaActualizacion(DateUtils.getFechaSistema());
				asoc.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
				updateToken(asoc);
				return false;
			} else {
				asoc.setAudFechaActualizacion(DateUtils.getFechaSistema());
				asoc.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
				asoc.setFlgActivo((short) 1);
				updateToken(asoc);
				return true;
			}
		} catch (Exception ex) {
			return false;
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
	public String getToken() {
		SqlSession session = null;
		String token = "";
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			TokenMapper mapper = session.getMapper(TokenMapper.class);
			token = mapper.getToken();
			return token;
		} catch (Exception ex) {
			return "";
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * 
	 * @param dinicio
	 * @param dfinal
	 * @return
	 */
	public static long getDiferenciaFechas(Date dinicio, Date dfinal) {
		long milis1;
		long milis2;
		long diff;
		Calendar cinicio = Calendar.getInstance();
		Calendar cfinal = Calendar.getInstance();
		cinicio.setTime(dinicio);
		cfinal.setTime(dfinal);
		milis1 = cinicio.getTimeInMillis();
		milis2 = cfinal.getTimeInMillis();
		diff = milis2 - milis1;
		long diffMinutos = Math.abs(diff / (60 * 1000));
		return diffMinutos;
	}

	public long getDiferenciaFechasS(Date dinicio, Date dfinal) {
		long milis1;
		long milis2;
		long diff;
		Calendar cinicio = Calendar.getInstance();
		Calendar cfinal = Calendar.getInstance();
		cinicio.setTime(dinicio);
		cfinal.setTime(dfinal);
		milis1 = cinicio.getTimeInMillis();
		milis2 = cfinal.getTimeInMillis();
		diff = milis2 - milis1;
		long diffSegundos = Math.abs(diff / 1000);
		return diffSegundos;
	}

}
