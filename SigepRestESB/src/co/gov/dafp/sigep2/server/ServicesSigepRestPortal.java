  /**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.core.util.Base64;

import co.gov.dafp.sigep2.bean.Contrato;
import co.gov.dafp.sigep2.bean.ext.ContratoExt;
import co.gov.dafp.sigep2.portal.BusquedaPortal;
import co.gov.dafp.sigep2.portal.CargoReferenciaFiltro;
import co.gov.dafp.sigep2.portal.DepartamentoFiltro;
import co.gov.dafp.sigep2.portal.EntidadPortal;
import co.gov.dafp.sigep2.portal.EntidadesPublicas;
import co.gov.dafp.sigep2.portal.ExperianciaLaboralPortal;
import co.gov.dafp.sigep2.portal.ExpuestoPoliticamenteFiltro;
import co.gov.dafp.sigep2.portal.Filtros;
import co.gov.dafp.sigep2.portal.FormacionProfesionalPortal;
import co.gov.dafp.sigep2.portal.HojaVidaPortal;
import co.gov.dafp.sigep2.portal.InstitucionFiltro;
import co.gov.dafp.sigep2.portal.MunicipioFiltro;
import co.gov.dafp.sigep2.portal.NivelJerarquicoFiltro;
import co.gov.dafp.sigep2.portal.RangoSalariosPortal;
import co.gov.dafp.sigep2.portal.Resultados;
import co.gov.dafp.sigep2.portal.ServidoresPublicos;
import co.gov.dafp.sigep2.portal.TipoContratoFiltro;
import co.gov.dafp.sigep2.services.PortalServices;
import co.gov.dafp.sigep2.utils.LoggerSigep;
import co.gov.dafp.sigep2.utils.UtilsConstantes;


/**
* @author Jose Viscaya.
* @version 1.0
* @Class Clase que se encarga de gestionar servicios rest para el portal
* @Proyect SIGEPII
* @Company ADA S.A
* @Module exposicion de servicios Rest 
*/
@Path("/sigepportal")
public class ServicesSigepRestPortal implements Serializable{

	
	private static final long serialVersionUID = -6503436010566070869L;
	public Gson gson;
	
	public ServicesSigepRestPortal() {
		LoggerSigep.getInstance().configureAppender();
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param msg
	 * @return
	 */
	@GET()
    @Path("testAlive/{msg}")
	@Produces("text/plain")
	public Response cliente(@PathParam("msg") String msg) {    	
		return Response.status(201).entity("Hello: Services Context sigepPortal... "+msg).build();	
	}
	
	/**
	 * @author: Jose Viscaya
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/buscarservidor" ) 
	@Consumes("text/plain")
	public Response buscarservidor(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ServidoresPublicos serv = new ServidoresPublicos();
		try {
			PortalServices serviceP = new PortalServices();
			BusquedaPortal user = gson.fromJson(json, BusquedaPortal.class);
			String data = UtilsConstantes.stripAccents(user.getCriterioBusqueda());
			user.setCriterioBusqueda(data);
			List<Resultados> usere = serviceP.buscarServidores(user);
			serv.setResultados(usere);
			serv.setLimiteFinal(user.getLimiteFinal());
			if(!usere.isEmpty()) {
				serv.setTotalRegistros(usere.get(0).getTotalRegistros());
				List<DepartamentoFiltro> depfiltro =  serviceP.busquedaServidorDepFiltro(user);
				List<MunicipioFiltro>	munfiltro =  serviceP.busquedaServidorMunFiltro(user);
				List<InstitucionFiltro>	entifiltro =  serviceP.busquedaServidorEntFiltro(user);
				List<TipoContratoFiltro> tipocfiltro =  serviceP.busquedaServidorTipoConFiltro(user);
				List<ExpuestoPoliticamenteFiltro> expuestofiltro =  serviceP.busquedaServidorExpuestoFiltro(user);
				List<NivelJerarquicoFiltro> nivelfiltro =  serviceP.busquedaServidorNivelJerarquicoFiltro(user);
				List<CargoReferenciaFiltro> cargofiltro =  serviceP.busquedaServidorCargoReferencialFiltro(user);
				Filtros filtros = new Filtros();
				filtros.setDepartamento(depfiltro);
				filtros.setMunicipios(munfiltro);
				filtros.setEntidad(entifiltro);
				filtros.setTipoContrato(tipocfiltro);
				filtros.setExpuestoP(expuestofiltro);
				filtros.setNivelJerarquico(nivelfiltro);
				filtros.setCargoReferencia(cargofiltro);
				serv.setFiltros(filtros);
			}else {
				serv.setMensaje("No Se Encontraron Resultados");
			}
			return Response.status(201).entity(gson.toJson(serv)).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestPortal.class);
			serv.setError(true);
			serv.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(serv)).build();
		}
	}
	/**
	 * @author: Jose Viscaya
	 *
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/hojadevida" ) 
	@Consumes("text/plain")
	public Response hojavidaportal(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		ServidoresPublicos serv = new ServidoresPublicos();
		try {
			PortalServices serviceP = new PortalServices();
			BusquedaPortal user = gson.fromJson(json, BusquedaPortal.class);
			HojaVidaPortal usere = serviceP.hojavidaportal(user);
			if(!usere.isError()) {
				usere.setError(null);
				Contrato contrato = new Contrato();
				contrato.setCodPersona(usere.getCodPersona());
				contrato.setFlgActivo((short) 1);
				List<FormacionProfesionalPortal> forma = serviceP.formacionPortal(user);
				List<ExperianciaLaboralPortal>  expe = serviceP.experianciaLaboralPortal(user);
				List<RangoSalariosPortal> escala = serviceP.escalaSalarialPortal(user);
				List<ContratoExt> contratos = serviceP.getContratoPersona(contrato);
				if(contratos.isEmpty()) {
					usere.setContratos(null);
				}else {
					usere.setContratos(contratos);
				}
				usere.setEducacionFormal(forma);
				usere.setExperenciaLaboral(expe);
				usere.setEscalaSalarial(escala);
				serv.setHojaDeVida(usere);
			}else {
				usere.setError(null);
			}
			return Response.status(201).entity(gson.toJson(serv)).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestPortal.class);
			serv.setError(true);
			serv.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(serv)).build();
		}
	}
	
	/**
	 * @author: Jose Viscaya
	 *
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/buscarsentidad" ) 
	@Consumes("text/plain")
	public Response transaccion(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadesPublicas serv = new EntidadesPublicas();
		try {
			PortalServices serviceP = new PortalServices();
			BusquedaPortal user = gson.fromJson(json, BusquedaPortal.class);
			String data = UtilsConstantes.stripAccents(user.getCriterioBusqueda());
			user.setCriterioBusqueda(data);
			List<EntidadPortal> usere = serviceP.buscarEntiddes(user);
			serv.setEntidadesPortal(usere);
			serv.setLimiteFinal(user.getLimiteFinal());
			if(!usere.isEmpty()) {
				serv.setTotalRegistros(usere.get(0).getTotalRegistros());
				for (int i = 0; i < usere.size(); i++) {
					usere.get(i).setTotalRegistros(null);
				}
			}else {
				serv.setMensaje("No Se Encontraron Resultados");
			}
			return Response.status(201).entity(gson.toJson(serv)).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestPortal.class);
			serv.setError(true);
			serv.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(serv)).build();
		}
	}
	
	
	/**
	 * @author: Jose Viscaya
	 * @param json
	 * @return
	 */
	@POST
	@Path ( "/detalleEntidad" ) 
	@Consumes("text/plain")
	public Response detalleEntidad(String json) {  
		json = new String(Base64.decode(json.getBytes()));
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(UtilsConstantes.FORMATO_FECHA);
		gson = gsonBuilder.create();
		EntidadesPublicas serv = new EntidadesPublicas();
		try {
			PortalServices serviceP = new PortalServices();
			BusquedaPortal user = gson.fromJson(json, BusquedaPortal.class);
			EntidadPortal usere = serviceP.detallelentidad(user.getCodEntidad());
			if (user.getCodEntidad() != null) {
				usere.setNaturalezaEmpleo(serviceP.getNaturalezaEmpleo(user.getCodEntidad()));
				usere.setSistemasCarrera(serviceP.getSistemaCarrera(user.getCodEntidad()));
				usere.setRegimenEscalaSalarial(serviceP.getsistemaRegimen(user.getCodEntidad()));
			}
			String out = gson.toJson(usere);
			out = out.replace("\"entidadPostConflicto\":\"0\"", "\"entidadPostConflicto\":\"NO\"");
			out = out.replace("\"entidadPostConflicto\":\"1\"", "\"entidadPostConflicto\":\"SI\"");
			out = out.replace("\"personeriaJuridica\":\"0\"", "\"personeriaJuridica\":\"NO\"");
			out = out.replace("\"personeriaJuridica\":\"1\"", "\"personeriaJuridica\":\"SI\"");
			out = out.replace("null,", "");
			return Response.status(201).entity(out).build();
		} catch (Exception e) {
			LoggerSigep.getInstance().error(e.getMessage(), ServicesSigepRestPortal.class);
			serv = new EntidadesPublicas();
			serv.setError(true);
			serv.setMensajeTecnico(e.getMessage());
			return Response.status(201).entity(gson.toJson(serv)).build();
		}
	}
}
