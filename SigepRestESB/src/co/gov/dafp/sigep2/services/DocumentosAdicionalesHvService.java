/**
 * 
 */
package co.gov.dafp.sigep2.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import co.gov.dafp.sigep2.bean.DocumentosAdicionalesHv;
import co.gov.dafp.sigep2.bean.ext.DocumentosAdicionalesHvExt;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.DocumentosAdicionalesHvMapper;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
 * @author joseviscaya
 * 2019, 3:01:01 PM
 * DocumentosAdicionalesHvService.java
 */
public class DocumentosAdicionalesHvService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6594447206970255942L;
	
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param documentosAdicionalesHv
	 * @return
	 * @Fecha :Mar 12, 2019
	 * DocumentosAdicionalesHvService.java
	 */
	public DocumentosAdicionalesHv insertDocumentosAdicionalesHv (DocumentosAdicionalesHv documentosAdicionalesHv){
		DocumentosAdicionalesHv audi = new DocumentosAdicionalesHv();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DocumentosAdicionalesHvMapper  mapper = session.getMapper(DocumentosAdicionalesHvMapper.class);
			id =  mapper.insert(documentosAdicionalesHv);
			if(id > 0){
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_GUARDADO_CON_EXITO);
				session.commit();
			}else{
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_NO_GUARDADO_CON_EXITO);
			}
			return audi;
		}catch (Exception ex) {
			audi.setError(true);
			audi.setMensaje(UtilsConstantes.MSG_EXEPCION);
			audi.setMensajeTecnico(ex.getMessage());
			return audi;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param documentosAdicionalesHv
	 * @return
	 * @Fecha :Mar 12, 2019
	 * DocumentosAdicionalesHvService.java
	 */
	public DocumentosAdicionalesHv updateDocumentosAdicionalesHv(DocumentosAdicionalesHv documentosAdicionalesHv){
		DocumentosAdicionalesHv audi = new DocumentosAdicionalesHv();
		SqlSession session = null;
		int id = 0;
		try {
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DocumentosAdicionalesHvMapper  mapper = session.getMapper(DocumentosAdicionalesHvMapper.class);
			id = mapper.updateByPrimaryKey(documentosAdicionalesHv);
			session.commit();
			if(id > 0){
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_ACTUALIZACION_CON_EXITO);
				session.commit();
			}else{
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_NO_ACTUALIZACION_CON_EXITO);
			}
			return audi;
		}catch (Exception ex) {
			audi.setError(true);
			audi.setMensaje(UtilsConstantes.MSG_EXEPCION);
			audi.setMensajeTecnico(ex.getMessage());
			return audi;	
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	 * @Elaborado_Por: Jose Viscaya
	 * @param documentosAdicionalesHv
	 * @return
	 * @Fecha :Mar 12, 2019
	 * DocumentosAdicionalesHvService.java
	 */
	public List<DocumentosAdicionalesHvExt> getDocumentosAdicionalesHvFiltro(DocumentosAdicionalesHv documentosAdicionalesHv){
		List<DocumentosAdicionalesHvExt> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DocumentosAdicionalesHvMapper  mapper = session.getMapper(DocumentosAdicionalesHvMapper.class);
			asoc =  mapper.selectByFiltro(documentosAdicionalesHv);
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
	 * @Elaborado_Por: Jose Viscaya
	 * @param id
	 * @return
	 * @Fecha :Mar 12, 2019
	 * DocumentosAdicionalesHvService.java
	 */
	public DocumentosAdicionalesHvExt getDocumentosAdicionalesHvById(long id){
		DocumentosAdicionalesHvExt asoc = new DocumentosAdicionalesHvExt();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DocumentosAdicionalesHvMapper  mapper = session.getMapper(DocumentosAdicionalesHvMapper.class);
			asoc =  mapper.selectByPrimaryKeyExt(id);
			if(asoc !=null){
				return asoc;
			}else{
				return new DocumentosAdicionalesHvExt();
			}
		}catch(Exception ex){
			return new DocumentosAdicionalesHvExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	/**
	 * 
	 * @Author: Jose Viscaya
	 * @Date:   Mar 29, 2019, 1:36:46 PM
	 * @File:   DocumentosAdicionalesHvService.java
	 * @param codId
	 * @return
	 */
	public DocumentosAdicionalesHvExt delDocumentosAdicionalesHvById(long codId){
		DocumentosAdicionalesHvExt audi = new DocumentosAdicionalesHvExt();
		DocumentosAdicionalesHv deldoc = new DocumentosAdicionalesHv();
		deldoc.setCodDocumentoAdicional(codId);
		deldoc.setFlgActivo((short) 0);
		deldoc.setAudFechaActualizacion(new Date());
		SqlSession session = null;
		int id = 0;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			DocumentosAdicionalesHvMapper  mapper = session.getMapper(DocumentosAdicionalesHvMapper.class);
			id =  mapper.deleteDoc(deldoc);
			if(id > 0){
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_ELIMINADO_CON_EXITO);
				session.commit();
			}else{
				audi.setError(false);
				audi.setMensaje(UtilsConstantes.MSG_NO_ELIMINADO_CON_EXITO);
			}
			return audi;
		}catch(Exception ex){
			return new DocumentosAdicionalesHvExt();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	

}
