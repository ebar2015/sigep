/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.bean.ParametricaExample;
import co.gov.dafp.sigep2.bean.SequenciasSigep;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.ParametricaMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla ParametricaService.java
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
 */
public class ParametricaService implements Serializable {

	private static final long serialVersionUID = -433951773416940762L;
	
	/**
	 * 
	 * @param Parametrica
	 * @return
	 */
	public Parametrica insertParametrica (Parametrica parametrica){
		Parametrica param = new Parametrica();
		SqlSession session = null;
		int id = 0;
		try {
			if(getParametroRepetido(parametrica)) {
				parametrica.setAudFechaActualizacion(new Date());
				if(parametrica.getCodTablaParametrica() == null) {
					SequenciasSigepService service = new SequenciasSigepService();
					SequenciasSigep sequ = service.getSequenciasSigep("ID_TABLA_PARAMETRICA");
					parametrica.setCodTablaParametrica(new BigDecimal(sequ.getSecuencia()));
				}
				session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
				ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
				id =  mapper.insertSelective(parametrica);
				if(id > 0){
					param.setError(false);
					param.setMensaje("");
					param.setCodTablaParametrica(parametrica.getCodTablaParametrica());
					session.commit();
				}else{
					param.setError(false);
					param.setMensaje("");
				}
				return param;
			}else {
				param.setError(true);
				param.setMensaje("1");
				return param;
			}
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje("2");
			param.setMensajeTecnico(ex.getMessage());
			return param;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Parametrica
	 * @return
	 */
	public String deleteParametrica(BigDecimal codParametrica){
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			id =  mapper.deleteByPrimaryKey(codParametrica);
			if(id > 0){
				session.commit();
				return "true";
			}else{
				return "false";
			}
		}catch (Exception ex) {
			return "false";
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param Parametrica
	 * @return
	 */
	public Parametrica updateParametrica(Parametrica parametrica){
		Parametrica param = new Parametrica();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			id = mapper.updateByPrimaryKey(parametrica);
			if(id > 0){
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				param.setError(false);
				param.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return param;
		}catch (Exception ex) {
			param.setError(true);
			param.setMensaje(UtilsConstantes.MSG_EXEPCION);
			param.setMensajeTecnico(ex.getMessage());
			return param;
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
	public List<Parametrica> getParametricaByAll(int limitIni, int limitEnd){
		List<Parametrica> asoc = new ArrayList<>();
		ParametricaExample dtoObject = new ParametricaExample();
		if(limitEnd > 1 ) {
			dtoObject.setLimitInit(limitIni);
			dtoObject.setLimitEnd(limitEnd);
		}else {
			dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
			dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
		}
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
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
	 * @param parametrica
	 * @return
	 */
	public List<Parametrica> getParametricatByNameLike(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			if(parametrica != null) {
				if(parametrica.getNombreParametro()!= null && !parametrica.getNombreParametro().equals("null")) {
					parametrica.setNombreParametro(parametrica.getNombreParametro().toUpperCase());
				}else {
					parametrica.setNombreParametro("");
				}
				if(parametrica.getLimitEnd() == 0) {
					parametrica.setLimitEnd(10);
				}
			}
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByNameLike(parametrica);
			if(!asoc.isEmpty()){
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
	 * @param id
	 * @return
	 */
	public Parametrica getPrametricaById(BigDecimal id){
		Parametrica asoc = new Parametrica();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByPrimaryKey(id);
			if(asoc != null){
				return asoc;
			}else{
				return new Parametrica();
			}
		}catch(Exception ex){
			return new Parametrica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadreiId(Parametrica user){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectById(user);
			if(!asoc.isEmpty()){
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
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getParametricaByPadreiIdOnlyNumero(Parametrica pGrados){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByIdOnlyNumero(pGrados);
			if(!asoc.isEmpty()){
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
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadreMovil(BigDecimal id){
		List<Parametrica> asoc = new ArrayList<>();
		ParametricaExample dtoObject = new ParametricaExample();
		dtoObject.createCriteria().andCodPadreParametricaEqualTo(id).andFlgEstadoEqualTo((short) 1);
		dtoObject.setOrderByClause("COD_TABLA_PARAMETRICA");
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectPadreMovil(id);
			if(!asoc.isEmpty()){
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
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaByPadre(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectPadreTodo(parametrica);
			if(!asoc.isEmpty()){
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
	 * @param name
	 * @return
	 */
	public List<Parametrica> getParametricaPorPadrel(String name){
		List<Parametrica> asoc = new ArrayList<>();
		ParametricaExample dtoObject = new ParametricaExample();
		SqlSession session = null;
		try{
		    dtoObject.createCriteria().andNombreParametroEqualTo(name).andFlgEstadoEqualTo((short) 1);
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			dtoObject = new ParametricaExample();
			if(!asoc.isEmpty()) {
			asoc =  mapper.parametricasporcodigo(asoc.get(0));
				if(!asoc.isEmpty()){
					return asoc;
				}else{
					return new ArrayList<>();
				}
			}else {
				return new ArrayList<>();
			}
		}catch(Exception ex){
			List<Parametrica> d = new ArrayList<>();
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Variable Null");
			p.setMensajeTecnico(ex.getMessage());
			d.add(p);
			return d;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Parametrica> getParametricaIntentos(String name){
		List<Parametrica> asoc = new ArrayList<>();
		ParametricaExample dtoObject = new ParametricaExample();
		dtoObject.createCriteria().andNombreParametroEqualTo(name).andFlgEstadoEqualTo((short) 1);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByExample(dtoObject);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			List<Parametrica> d = new ArrayList<>();
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Variable Null");
			p.setMensajeTecnico(ex.getMessage());
			p.setValorParametro("ERROR DE SISTEMA CONSULTAR ADMINISTRADOR");
			d.add(p);
			return d;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param codEntidad
	 * @return
	 */
	public List<Parametrica> getDenomincacionEntidad(BigDecimal codEntidad){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectDenomincacionEntidad(codEntidad);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch(Exception ex){
			List<Parametrica> d = new ArrayList<>();
			Parametrica p = new Parametrica();
			p.setError(true);
			p.setMensaje("Variable Null");
			p.setMensajeTecnico(ex.getMessage());
			p.setValorParametro("ERROR DE SISTEMA CONSULTAR ADMINISTRADOR");
			d.add(p);
			return d;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 28/08/2018 2:22:01 p.m.
	* @param id
	* @return
	*
	 */
	public Parametrica getPrametricaByValue(String value, BigDecimal padreId){
		List<Parametrica> asoc = new ArrayList<>();
		Parametrica pa = new Parametrica();
		pa.setCodPadreParametrica(padreId);
		pa.setValorParametro(value);
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByParamValue(pa);
			if(!asoc.isEmpty()){
				return asoc.get(0);
			}else{
				return new Parametrica();
			}
		}catch(Exception ex){
			return  new Parametrica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	* Elaborado por Maria Alejandra C
	* @fecha 22/03/2019 2:22:01 p.m.
	* @param id
	* @return
	*
	 */
	public List<Parametrica> getParametricaByOrden(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectParaByOrden(parametrica);
			if(!asoc.isEmpty()){
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
	 * @Author: Jose Viscaya
	 * @Date:   May 6, 2019, 3:01:45 PM
	 * @File:   ParametricaService.java
	 * @param parametrica
	 * @return
	 */
	public boolean getParametroRepetido(Parametrica parametrica){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectParamRepetido(parametrica);
			if(!asoc.isEmpty()){
				return false;
			}else{
				return true;
			}
		}catch(Exception ex){
			return false;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 *  
	* @fecha 21/11/2023
	* @param id
	* @return
	*
	 */
	public Parametrica getParametricaByname(Parametrica parametrica){
		Parametrica asoc = new Parametrica();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectByParamName(parametrica);
			if(asoc !=null){
				return asoc;
			}else{
				return new Parametrica();
			}
		}catch(Exception ex){
			return  new Parametrica();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public List<Parametrica>  getPrametricaActivaByPadreiId(Parametrica user){
		List<Parametrica> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			ParametricaMapper  mapper = session.getMapper(ParametricaMapper.class);
			asoc =  mapper.selectParametricaActivaByIdPadre(user);
			if(!asoc.isEmpty()){
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
	
}
