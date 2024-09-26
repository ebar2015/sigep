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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import co.gov.dafp.sigep2.bean.Contrato;
import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.bean.ext.NomenclaturaDenominacionExt;
import co.gov.dafp.sigep2.entity.seguridad.EditarDireccionDTO;
import co.gov.dafp.sigep2.factory.SessionFactory;
import co.gov.dafp.sigep2.interfaces.EntidadMapper;
import co.gov.dafp.sigep2.interfaces.EntidadSistemaRegimenMapper;
import co.gov.dafp.sigep2.interfaces.HojaVidaMapper;
import co.gov.dafp.sigep2.interfaces.PortalSigepMapper;
import co.gov.dafp.sigep2.portal.BusquedaPortal;
import co.gov.dafp.sigep2.portal.CargoReferenciaFiltro;
import co.gov.dafp.sigep2.portal.DepartamentoFiltro;
import co.gov.dafp.sigep2.portal.EntidadPortal;
import co.gov.dafp.sigep2.portal.ExperianciaLaboralPortal;
import co.gov.dafp.sigep2.portal.ExpuestoPoliticamenteFiltro;
import co.gov.dafp.sigep2.portal.FormacionProfesionalPortal;
import co.gov.dafp.sigep2.portal.HojaVidaPortal;
import co.gov.dafp.sigep2.portal.InstitucionFiltro;
import co.gov.dafp.sigep2.portal.MunicipioFiltro;
import co.gov.dafp.sigep2.portal.NaturalezaEmpleo;
import co.gov.dafp.sigep2.portal.NivelJerarquico;
import co.gov.dafp.sigep2.portal.NivelJerarquicoEscala;
import co.gov.dafp.sigep2.portal.NivelJerarquicoFiltro;
import co.gov.dafp.sigep2.portal.NomenclaturaPortal;
import co.gov.dafp.sigep2.portal.RangoSalariosPortal;
import co.gov.dafp.sigep2.portal.RegimenSalarial;
import co.gov.dafp.sigep2.portal.Resultados;
import co.gov.dafp.sigep2.portal.SistemaCarrera;
import co.gov.dafp.sigep2.portal.TipoContratoFiltro;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;

/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de realizar las consultas a la base de datos del directorio de servidores publicos
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
* Fecha: MArtes 26 de Junio de 2018
*/
public class PortalServices implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7303169328529250478L;
	
	
	/**
	 * Methodo que implememta la busqueda de servidores publicos desde el portal
	 * @param busqueda objeto con 3 atributos criterio, limite inical, limite fnal
	 * @return
	 */
	public List<Resultados> buscarServidores(BusquedaPortal busqueda){
		List<Resultados> asoc = new ArrayList<>();
		SqlSession session = null;
		busqueda.setCriterio1("");
		
		if(busqueda.getCriterioBusqueda() != null) {
			busqueda.setCriterio1(busqueda.getCriterioBusqueda().toUpperCase());	
		}try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidor(busqueda);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch (Exception e) {
			LoggerSigep logger = new LoggerSigep();
			logger.info("Error servicio List<Resultados> buscarServidores(BusquedaPortal busqueda)  : "+ new Date(), PortalServices.class);
			return new ArrayList<>();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	
	/**
	 * 
	 * @param busqueda
	 * @return
	 */
	public Integer buscarServidoresT(BusquedaPortal busqueda){
		Resultados asoc = new Resultados();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorT(busqueda);
			if(asoc != null){
				return asoc.getTotalRegistros();
			}else{
				return 0;
			}
		}catch (Exception e) {
			return 0;
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * Methodo que implememta la busqueda de servidores publicos desde el portal
	 * @param busqueda objeto con 3 atributos criterio, limite inical, limite fnal
	 * @return
	 */
	public List<EntidadPortal> buscarEntiddes(BusquedaPortal busqueda)throws Exception{
		List<EntidadPortal> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper  mapper = session.getMapper(EntidadMapper.class);
			asoc =  mapper.entidadesPrtal(busqueda);
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
	 * Metodo que carga el filtro de departamentos
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<DepartamentoFiltro> busquedaServidorDepFiltro(BusquedaPortal busqueda){
		List<DepartamentoFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorDepFiltro(busqueda);
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
	 * Metodo que carga el filtro de municipios
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<MunicipioFiltro> busquedaServidorMunFiltro(BusquedaPortal busqueda){
		List<MunicipioFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorMunFiltro(busqueda);
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
	 * Metodo que carga el filtro de entidaddes
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<InstitucionFiltro> busquedaServidorEntFiltro(BusquedaPortal busqueda){
		List<InstitucionFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorEntFiltro(busqueda);
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
	 * Metodo que carga el filtro de tipos de contrato
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<TipoContratoFiltro> busquedaServidorTipoConFiltro(BusquedaPortal busqueda){
		List<TipoContratoFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorTipoConFiltro(busqueda);
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
	 * Metodo que carga el filtro de expuesto politicamente
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<ExpuestoPoliticamenteFiltro> busquedaServidorExpuestoFiltro(BusquedaPortal busqueda){
		List<ExpuestoPoliticamenteFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorExpuestoFiltro(busqueda);
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
	 * Metodo que carga el filtro de Nivel Jerarquico
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<NivelJerarquicoFiltro> busquedaServidorNivelJerarquicoFiltro(BusquedaPortal busqueda){
		List<NivelJerarquicoFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorNivelJerarquicoFiltro(busqueda);
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
	 * Metodo que carga el filtro de Cargo referencial
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public List<CargoReferenciaFiltro> busquedaServidorCargoReferencialFiltro(BusquedaPortal busqueda){
		List<CargoReferenciaFiltro> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			PortalSigepMapper  mapper = session.getMapper(PortalSigepMapper.class);
			asoc =  mapper.busquedaServidorCargoReferenciaFiltro(busqueda);
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
	 * Metodo devuelve al portal la hoja de vida de una persona
	 *  @param busqueda objeto con 3 atributos criterio, limite inical, limit
	 * @return
	 */
	public HojaVidaPortal hojavidaportal(BusquedaPortal busqueda){
		HojaVidaPortal asoc = new HojaVidaPortal();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.hojavidaportal(busqueda);
			if(asoc != null){
				 asoc.setError(false);
				return asoc;
			}else{
				 asoc = new HojaVidaPortal();
				 asoc.setError(true);
				return asoc;
			}
		}catch(Exception ex){
			return new HojaVidaPortal();
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * Metodo devuelve al portal la formacion formal
	 *  @param busqueda objeto con codPersona requerido
	 * @return
	 */
	public List<FormacionProfesionalPortal> formacionPortal(BusquedaPortal busqueda){
		List<FormacionProfesionalPortal> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.formacionPortal(busqueda);
			if(asoc != null){
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
	 * Metodo devuelve al portal la experiancia laboral de la persona
	 *  @param busqueda objeto con codPersona requerido
	 * @return
	 */
	public List<ExperianciaLaboralPortal> experianciaLaboralPortal(BusquedaPortal busqueda){
		List<ExperianciaLaboralPortal> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			HojaVidaMapper  mapper = session.getMapper(HojaVidaMapper.class);
			asoc =  mapper.experianciaLaboralPortal(busqueda);
			if(asoc != null){
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
	 * Metodo devuelve listado niveles Jerarquicos asociados a una nomenclatura
	 * @param busqueda objeto con codEntidad requerido
	 * @return
	 * ESTE SERVICIO ESTA SIMULADO HASTA TANTO SE DESARROLLO LOS COMPOENTES DE ENTIDADES 
	 */
	public List<RangoSalariosPortal> escalaSalarialPortal(BusquedaPortal busqueda){
		NomenclaturaDenominacionService serviceN = new NomenclaturaDenominacionService();
		List<RangoSalariosPortal> asoc = new ArrayList<>();
		try {
			List<NomenclaturaPortal> nomenclaturaEscala = serviceN.getNomenclaturaEscala(busqueda.getCodEntidad().longValue()); 
			if(nomenclaturaEscala.isEmpty()) {
				return new ArrayList<>();
			}else {
				for (NomenclaturaPortal nomenclaturaPortal : nomenclaturaEscala) {
					RangoSalariosPortal rango = new RangoSalariosPortal();
					rango = getNivelJerarquico(nomenclaturaPortal);
					asoc.add(rango);
				}
			}
			return asoc;
		} catch (Exception e) {
			return new ArrayList<>();
		}
	}
	
	/**
	 * Metodo que se encarga de obtener los niveles jerarquicos pertenecientes a una nomenclatura
	 * @param nomenclatura
	 * @return RangoSalariosPortal
	 */
	public RangoSalariosPortal getNivelJerarquico(NomenclaturaPortal nomenclatura){
		List<NivelJerarquicoEscala> escalaNivel = new ArrayList<>();
		NivelJerarquicoEscala  nivelJerarquicoEscala = new NivelJerarquicoEscala();
		NomenclaturaDenominacionService serviceN = new NomenclaturaDenominacionService();
		RangoSalariosPortal rango = new RangoSalariosPortal();
		try {
			List<NivelJerarquico> nivelJerarquico = serviceN.getNivelJerarquicoNomenclaturaPortal(nomenclatura);
			if(!nivelJerarquico.isEmpty()) {
				for (NivelJerarquico varNivel : nivelJerarquico) {
					nivelJerarquicoEscala = new NivelJerarquicoEscala();
					NomenclaturaDenominacionExt objNomenclatura = new NomenclaturaDenominacionExt();
					objNomenclatura.setCodNomenclatura(new BigDecimal(nomenclatura.getCodNomenclatura()));
					objNomenclatura.setCodNivelJerarquico(varNivel.getCodNivelJerarquico().longValue());
					objNomenclatura.setCodNomenclaturaAsociada(varNivel.getCodNomenclaturaAsociada());
					List<NivelJerarquico> nivelJerarquicoDetalle = serviceN.getNivelJerarquicoDetallePortal(objNomenclatura);
					nivelJerarquicoEscala.setCodNivelJerarquico(varNivel.getCodNivelJerarquico());
					nivelJerarquicoEscala.setNombreNivelJerarquico(varNivel.getNombreNivelJerarquico());
					nivelJerarquicoEscala.setNivelJerarquico(nivelJerarquicoDetalle);
					escalaNivel.add(nivelJerarquicoEscala);
				}
			}
			rango.setCodNomenclatura(nomenclatura.getCodNomenclatura());
			rango.setCodNomenclaturaAsociada(nomenclatura.getCodNomenclaturaAsociada());
			rango.setNombreNomenclatura(nomenclatura.getNombreNomenclatura());
			rango.setNombreNomenclatura(nomenclatura.getNombreNomenclatura());
			rango.setFlgGenerica(nomenclatura.getFlgGenerica());
			rango.setNivelJerarquico(escalaNivel);
			return rango;
		} catch (Exception e) {
			return rango;
		}
	}
	
	/**
	 * @param contrato
	 * @return
	 */
	public List<ContratoExt> getContratoPersona(Contrato contrato){
		ContratoService service = new ContratoService();
		List<ContratoExt> cot = service.getContratoPersona(contrato);
		if(!cot.isEmpty()) {
			for (int i = 0; i < cot.size(); i++) {
				cot.get(i).setAudAccion(null);
				cot.get(i).setAudCodRol(null);
				cot.get(i).setAudCodUsuario(null);
				cot.get(i).setAudFechaActualizacion(null);
				cot.get(i).setCodAdministracionRecurso(null);
				cot.get(i).setCodContrato(null);
				cot.get(i).setCodDepartamento(null);
				cot.get(i).setCodMunicipio(null);
				cot.get(i).setLimitEnd(null);
				cot.get(i).setPrimerNombre(null);
				cot.get(i).setSegundoNombre(null);
				cot.get(i).setPrimerApellido(null);
				cot.get(i).setSegundoApellido(null);
				cot.get(i).setCodEntidad(null);
				cot.get(i).setCodPersona(null);
				cot.get(i).setCodMonedaMonto(null);
			}
		}
		return cot;
	}
	/**
	 * 
	 * @author: Jose Viscaya 
	 * @fecha 5:26:31 p.m. 2018
	 * @param codEntidad
	 * @return
	 */
	public EntidadPortal detallelentidad(BigDecimal codEntidad){
		EntidadPortal asoc = new EntidadPortal();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadMapper  mapper = session.getMapper(EntidadMapper.class);
			asoc =  mapper.selectEntidadDetalle(codEntidad);
			if(asoc != null){
				asoc.setError(false);
				if(asoc.getDireccionFisica() != null && !asoc.getDireccionFisica().equals(' ')) {
					asoc.setDireccionFisica(parseDireccion(asoc.getDireccionFisica()));
				}
				return asoc;
			}else{
				asoc = new EntidadPortal();
				asoc.setError(true);
				asoc.setMensaje("No se encontro informacion para esta entidad");
				return asoc;	
			}
		}catch (Exception e) {
			asoc = new EntidadPortal();
			asoc.setError(true);
			asoc.setMensaje(UtilsConstantes.MSG_EXEPCION);
			asoc.setMensajeTecnico(e.getMessage());
			return asoc;	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	/**
	 * 
	* @author: Jose Viscaya 
	* @fecha 14/08/2018 3:16:38 p.m.
	* @param codEntidad
	* @return
	*
	 */
	public List<RegimenSalarial> getsistemaRegimen(BigDecimal codEntidad){
		List<RegimenSalarial> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSistemaRegimenMapper  mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
			asoc =  mapper.selectRegimen(codEntidad);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch (Exception e) {
			return new ArrayList<>();	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	 * 
	* @author: Maria Alejandra 
	* @fecha 31/07/2020
	* @param codEntidad
	* @return
	*
	 */
	public List<NaturalezaEmpleo> getNaturalezaEmpleo(BigDecimal codEntidad){
		List<NaturalezaEmpleo> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSistemaRegimenMapper  mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
			asoc =  mapper.selectNaturalezaEmpleo(codEntidad);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch (Exception e) {
			return new ArrayList<>();	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	/**
	* @author: Maria Alejandra 
	* @fecha 31/07/2020
	* @param codEntidad
	* @return
	*/
	public List<SistemaCarrera> getSistemaCarrera(BigDecimal codEntidad){
		List<SistemaCarrera> asoc = new ArrayList<>();
		SqlSession session = null;
		try{
			session = SessionFactory.getInstance().getSqlSessionFactory().openSession(false);
			EntidadSistemaRegimenMapper  mapper = session.getMapper(EntidadSistemaRegimenMapper.class);
			asoc =  mapper.selectSistemaCarrera(codEntidad);
			if(!asoc.isEmpty()){
				return asoc;
			}else{
				return new ArrayList<>();
			}
		}catch (Exception e) {
			return new ArrayList<>();	
		}finally{
			if (session != null) {
				session.close();
			}
		}
	}
	
	public String parseDireccion(String data1) {
		 EditarDireccionDTO direccion=null; 
		Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		 try {
  		   direccion = gson.fromJson(data1, EditarDireccionDTO.class);
  		   if(direccion != null) {
			   return direccion.getDireccionGenerada();
		   }
	      } catch(JsonSyntaxException e) {
	    	  return data1;
	      }
		return data1;
	}

}
