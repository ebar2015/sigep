/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.EvaluacionDesempeno;
import co.gov.dafp.sigep2.bean.EvaluacionDesempenoExample;
import co.gov.dafp.sigep2.bean.ext.EvaluacionDesempenoExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EvaluacionDesempenoMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar la el almacenamiento y reguperacion de datos de la tabla EvaluacionDesempeno
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
public class EvaluacionDesempenoService implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 68544602018394341L;

    /**
     * 
     * @param EvaluacionDesempeno
     * @return
     */
    public EvaluacionDesempeno insertEvaluacionDesempeno(EvaluacionDesempeno evaluacionDesempeno) {
    	EvaluacionDesempeno eval = new EvaluacionDesempeno();
	SqlSession session = null;
	int id = 0;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
	    id = mapper.insert(evaluacionDesempeno);
	    if(id > 0){
	    	    eval.setError(false);
	    	    eval.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
			session.commit();
		}else{
			eval.setError(false);
			eval.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
		}
		return eval;
	}catch (Exception ex) {
		eval.setError(true);
		eval.setMensaje(UtilsConstantes.MSG_EXEPCION);
		eval.setMensajeTecnico(ex.getMessage());
		return eval;	
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }

    /**
     * 
     * @param EvaluacionDesempeno
     * @return
     */
    public EvaluacionDesempeno updateEvaluacionDesempeno(EvaluacionDesempeno evaluacionDesempeno) {
    EvaluacionDesempeno eval = new EvaluacionDesempeno();
	SqlSession session = null;
	int id = 0;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
	    id = mapper.updateByPrimaryKey(evaluacionDesempeno);
	    session.commit();
	    if(id > 0){
	    	    eval.setError(false);
	    	    eval.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
			session.commit();
		}else{
			eval.setError(false);
			eval.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
		}
	    return eval;
	}catch (Exception ex) {
		eval.setError(true);
		eval.setMensaje(UtilsConstantes.MSG_EXEPCION);
		eval.setMensajeTecnico(ex.getMessage());
		return eval;	
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
    public List<EvaluacionDesempeno> getEvaluacionDesempenoByAll(int limitIni, int limitEnd) {
	List<EvaluacionDesempeno> asoc = new ArrayList<>();
	EvaluacionDesempenoExample dtoObject = new EvaluacionDesempenoExample();
	if (limitEnd > 1) {
	    dtoObject.setLimitInit(limitIni);
	    dtoObject.setLimitEnd(limitEnd);
	} else {
	    dtoObject.setLimitInit(UtilsConstantes.LIMITINIT);
	    dtoObject.setLimitEnd(UtilsConstantes.LIMITIEND);
	}
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
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
    public List<EvaluacionDesempeno> evaluacionDesempenoById(long id) {
	List<EvaluacionDesempeno> asoc = new ArrayList<>();
	EvaluacionDesempenoExample dtoObject = new EvaluacionDesempenoExample();
	dtoObject.createCriteria().andCodEvaluacionDesempenoEqualTo(id);
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
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
     * @param codPersona
     * @param codEntida
     * @return
     */
    public List<EvaluacionDesempeno> evaluacionDesempenoPorUSEN(BigDecimal codPersona, long codEntida) {
	List<EvaluacionDesempeno> asoc = new ArrayList<>();
	EvaluacionDesempenoExample dtoObject = new EvaluacionDesempenoExample();
	dtoObject.createCriteria().andCodPersonaEqualTo(codPersona).andCodEntidadEqualTo(codEntida);
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
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
     * @param codPersona
     * @param codEntida
     * @return
     */
    public EvaluacionDesempeno evaluacionDesempenoPorUSENfe(BigDecimal codPersona, long codEntida) {
	List<EvaluacionDesempeno> asoc = new ArrayList<>();
	EvaluacionDesempenoExample dtoObject = new EvaluacionDesempenoExample();
	dtoObject.setLimitInit(0);
	dtoObject.setLimitEnd(1);
	dtoObject.createCriteria().andCodPersonaEqualTo(codPersona).andCodEntidadEqualTo(codEntida);
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
	    asoc = mapper.selectByExample(dtoObject);
	    if (!asoc.isEmpty()) {
		return asoc.get(0);
	    } else {
		return new EvaluacionDesempeno();
	    }
	} catch (Exception ex) {

	    return new EvaluacionDesempeno();
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }

    /**
     * @param codPersona
     * @return
     */
    public EvaluacionDesempenoExt evaluacionDesempenoPorpersona(EvaluacionDesempeno codPersona) {
	EvaluacionDesempenoExt asoc = new EvaluacionDesempenoExt();
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
	    asoc = mapper.selectByCodPersona(codPersona);
	    if (asoc != null) {
		return asoc;
	    } else {
		return new EvaluacionDesempenoExt();
	    }
	} catch (Exception ex) {

	    return new EvaluacionDesempenoExt();
	} finally {
	    if (session != null) {
		session.close();
	    }
	}
    }
    /**
     * 
     * Elaborado por:
     * Jose Viscaya Dec 27, 2018
     * @param evaluacionDesempeno
     * @return
     */
    public List<EvaluacionDesempenoExt> getevaluacionFiltro(EvaluacionDesempeno evaluacionDesempeno) {
    	List<EvaluacionDesempenoExt> asoc = new ArrayList<>();
    	SqlSession session = null;
    	try {
    		session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
    		EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
    		asoc = mapper.selectByFiltro(evaluacionDesempeno);
    		if (asoc != null) {
    			return asoc;
    		} else {
    			return new ArrayList<>();
    		}
    	} catch (Exception ex) {
    		return  new ArrayList<>();
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
    public List<EvaluacionDesempeno> getEvaluacionDesempenoPersona(BigDecimal codPersona) {
	List<EvaluacionDesempeno> asoc = new ArrayList<>();
	EvaluacionDesempenoExample dtoObject = new EvaluacionDesempenoExample();
	dtoObject.createCriteria().andCodPersonaEqualTo(codPersona);
	SqlSession session = null;
	try {
	    session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
	    EvaluacionDesempenoMapper mapper = session.getMapper(EvaluacionDesempenoMapper.class);
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
    

}
