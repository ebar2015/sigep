package co.gov.dafp.sigep2.factoria;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.seguridad.Persona;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.HojaDeVidaDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.view.DetalleEducativo;
import co.gov.dafp.sigep2.view.HojaDeVida;

@Stateless
public class GestionarHojaDeVidaFactoria extends AbstractFactory<HojaDeVida> {
    private static final long serialVersionUID = -2311532025436489212L;

    public GestionarHojaDeVidaFactoria() {
	super(HojaDeVida.class);
    }

    @SuppressWarnings("unchecked")
    public List<HojaDeVidaDTO> detallesHojaDeVida(String personaId) {
	try {
	    String query = "SELECT * FROM V_HOJA_VIDA_DETALLE HV WHERE HV.PERSONA_ID = :personaId";
	    List<HojaDeVidaDTO> resultList = createNativeQuery(query, HojaDeVida.MAPPING_DETALLE_HOJA_VIDA).setParameter("personaId", personaId).getResultList();
	    return resultList;
	} catch (Exception e) {
	    System.out.println("En el FACTORIA de DETALLE PERSONA se ha arrojado esta excepcion: " + e + " ++++++++++");
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public List<HojaDeVidaDTO> datosDeEducacionHojaDeVida(String personaId) {
	try {
	    String query = "SELECT * FROM V_HOJA_VIDA_EDUCACION HV WHERE HV.PERSONA_ID = :personaId";
	    List<HojaDeVidaDTO> resultado = createNativeQuery(query, DetalleEducativo.MAPPING_HOJA_VIDA_EDUCACION).setParameter("personaId", personaId).getResultList();
	    return resultado;
	} catch (Exception e) {
	    System.out.println("En el FACTORIA de EDUCACION DE PERSONA se ha arrojado esta excepcion: " + e + " +++++++++++++");
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public List<PersonaDTO> listarPersonasConHojaDeVida(int first, int pageSize, PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion, 
    		boolean contar,String sortField, String sortOrder) {
    	String codPersonaParametro = "codPersona";
    	String concatenador = "') || '%' ";
    	List<PersonaDTO> resultado;
    	Long[] valores = {(long)1, (long)2, (long)3};
    	String query = "SELECT " + (!contar ?
	    			"PE.COD_PERSONA," + 
	    			"UE.COD_ENTIDAD," + 
	    			"PE.COD_TIPO_IDENTIFICACION," + 
	    			"PE.NUMERO_IDENTIFICACION," + 
	    			"PE.PRIMER_NOMBRE," + 
	    			"PE.SEGUNDO_NOMBRE," + 
	    			"PE.PRIMER_APELLIDO," + 
	    			"PE.SEGUNDO_APELLIDO " : "COUNT(*) ") + 
	    			"FROM PERSONA PE " + 
	    			"INNER JOIN HOJA_VIDA HV ON PE.COD_PERSONA = HV.COD_PERSONA " + 
	    			"INNER JOIN USUARIO US ON PE.COD_PERSONA = US.COD_PERSONA " + 
	    			"INNER JOIN USUARIO_ENTIDAD UE ON US.COD_USUARIO = UE.COD_USUARIO " + 
	    			"WHERE (PE.COD_PERSONA <> :codPersona AND HV.COD_PERSONA <> :codPersona) "+
	    			"AND (PE.FLG_ACTIVO = 1 AND US.FLG_ESTADO = 1 AND HV.FLG_ACTIVO = 1 AND UE.FLG_ACTIVO = 1) "+
	    			"AND (UE.COD_ENTIDAD = "+codEntidadSesion+" AND HV.COD_ENTIDAD = "+codEntidadSesion+")";
    	
    	if(persona.getCodFactorRh() != null && Arrays.asList(valores).contains(persona.getCodFactorRh())) {
			if(persona.getCodFactorRh().equals((long)2)) {
				codTipoAsociacion = TipoAsociacionDTO.CONTRATISTA_ID.toString();
			} else if(persona.getCodFactorRh().equals((long)3)) {
				codTipoAsociacion = TipoAsociacionDTO.SERVICIO_PUBLICO_ID.toString();
			}
		}
    	if (persona.getTipoIdentificacionId() != null) {
    		query = query + " AND PE.COD_TIPO_IDENTIFICACION = "+persona.getTipoIdentificacionId().getId();
    	}
    	if (persona.getNumeroIdentificacion() != null && !persona.getNumeroIdentificacion().equals("")) {
    		query = query + " AND upper(PE.NUMERO_IDENTIFICACION) LIKE '%' || upper('"+persona.getNumeroIdentificacion()+"') || '%'";
    	}
    	if (persona.getPrimerNombre() != null && !persona.getPrimerNombre().equals("")) {
    		
    		query = query + " AND (" + 
    				"upper(PE.PRIMER_NOMBRE||PE.SEGUNDO_NOMBRE||PE.PRIMER_APELLIDO||PE.SEGUNDO_APELLIDO) "
    				+ "LIKE '%' || upper('"+persona.getPrimerNombre()+"')||'%')";    		
    	}
    	if (codTipoAsociacion != null && !codTipoAsociacion.trim().equals("0")) {
    		query = query + " AND UE.COD_TIPO_VINCULACION = "+codTipoAsociacion;
    	}
    	
    	if( sortField!=null && !"".equals(sortField)  && sortOrder!=null && !"".equals(sortOrder) ){
    		if(sortField.equals("numeroIdentificacion")){
    			query = query + "order by pe.numero_identificacion "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}else if(sortField.equals("primerNombre")){
    			query = query + "order by pe.primer_nombre "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}else if(sortField.equals("segundoNombre")){
    			query = query + "order by pe.segundo_nombre "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}else if(sortField.equals("primerApellido")){
    			query = query + "order by pe.primer_apellido "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}else if(sortField.equals("segundoApellido")){
    			query = query + "order by pe.segundo_apellido "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}else if(sortField.equals("tipoIdentificacionId.descripcion")){
    			query = query + "order by pe.cod_tipo_identificacion "+(sortOrder.equals("ASCENDING")?"ASC":"DESC");
    		}
    		
    		
    	}
    	
    	if(!contar){
    		query = query + " OFFSET :first ROWS FETCH NEXT :pageSize ROWS ONLY";
    	}
    	if(!contar){
    		resultado = createNativeQuery(query, Persona.MAPPING_DATOS_DE_IDENTIFICACION)
					.setParameter("first", first)
					.setParameter("pageSize", pageSize)
					.setParameter(codPersonaParametro, usuario.getCodPersona())
					.getResultList();
    	} else {
    		int conteo = Integer.parseInt(createNativeQuery(query)
					.setParameter(codPersonaParametro, usuario.getCodPersona())
					.getSingleResult().toString());
    		PersonaDTO personaDTO = new PersonaDTO();
    		personaDTO.setTotal(BigDecimal.valueOf(conteo));
    		resultado = new LinkedList<>();
    		resultado.add(personaDTO);
    	}
		
		return resultado;
    }

    public int filasPersonasConHV(PersonaDTO persona, UsuarioDTO usuario, long codEntidadSesion, String codTipoAsociacion) {
		String concatenador = "') || '%' ";
    	int resultado;
    	Long[] valores = {(long)1, (long)2, (long)3};
    	
		String query = "SELECT count(*) " +
    			"FROM PERSONA PE " + 
    			"INNER JOIN HOJA_VIDA HV ON PE.COD_PERSONA = HV.COD_PERSONA " + 
    			"INNER JOIN USUARIO US ON PE.COD_PERSONA = US.COD_PERSONA " + 
    			"INNER JOIN USUARIO_ENTIDAD UE ON US.COD_USUARIO = UE.COD_USUARIO " + 
    			"WHERE (PE.COD_PERSONA <> :codPersona AND HV.COD_PERSONA <> :codPersona) "+
    			"AND (PE.FLG_ACTIVO = 1 AND US.FLG_ESTADO = 1 AND HV.FLG_ACTIVO = 1 AND UE.FLG_ACTIVO = 1)";
		
		if(persona.getCodFactorRh() != null && Arrays.asList(valores).contains(persona.getCodFactorRh())) {
			query = query + " AND (UE.COD_ENTIDAD = "+codEntidadSesion+" AND HV.COD_ENTIDAD = "+codEntidadSesion+")";
			
			if(persona.getCodFactorRh().equals((long)2)) {
				codTipoAsociacion = TipoAsociacionDTO.CONTRATISTA_ID.toString();
			} else if(persona.getCodFactorRh().equals((long)3)) {
				codTipoAsociacion = TipoAsociacionDTO.SERVICIO_PUBLICO_ID.toString();
			}
		}
		
		if (persona.getTipoIdentificacionId() != null) {
			query = query + " AND PE.COD_TIPO_IDENTIFICACION = "+persona.getTipoIdentificacionId().getId();
		}
		
		if (persona.getNumeroIdentificacion() != null && !persona.getNumeroIdentificacion().equals("")) {
			query = query + " AND upper(PE.NUMERO_IDENTIFICACION) LIKE '%' || upper('"+persona.getNumeroIdentificacion()+"') || '%'";
		}
		
		if (persona.getPrimerNombre() != null && !persona.getPrimerNombre().equals("")) {
			query = query + " AND (" + 
					"upper(PE.PRIMER_NOMBRE) LIKE '%' || upper('"+persona.getPrimerNombre()+concatenador + 
					"OR upper(PE.SEGUNDO_NOMBRE) LIKE '%' || upper('"+persona.getPrimerNombre()+concatenador + 
					"OR upper(PE.PRIMER_APELLIDO) LIKE '%' || upper('"+persona.getPrimerNombre()+concatenador + 
					"OR upper(PE.SEGUNDO_APELLIDO) LIKE '%' || upper('"+persona.getPrimerNombre()+concatenador + 
					")";
		}
		
		if (codTipoAsociacion != null && !codTipoAsociacion.trim().equals("0")) {
			query = query + " AND UE.COD_TIPO_VINCULACION = "+codTipoAsociacion;
		}
		
		resultado = ((BigDecimal) createNativeQuery(query)
							.setParameter("codPersona", usuario.getCodPersona())
							.getSingleResult()).intValue();		
		return resultado;
    }

    public PersonaDTO detallePersona(String idPersona) {
		String query = "SELECT cod_persona, cod_tipo_identificacion, numero_identificacion, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido FROM PERSONA WHERE COD_PERSONA = :idPersona";
		return (PersonaDTO) createNativeQuery(query, Persona.MAPPING_DATOS_DE_IDENTIFICACION).getSingleResult();
    }
}