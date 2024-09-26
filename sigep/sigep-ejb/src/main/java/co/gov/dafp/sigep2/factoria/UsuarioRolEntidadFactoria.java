/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.UsuarioRolEntidad;
import co.gov.dafp.sigep2.entity.seguridad.EntidadDTO;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.entity.seguridad.UsuarioDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import javax.persistence.Query;

/**
 * @author jhon.deavila
 */
@Stateless
public class UsuarioRolEntidadFactoria extends AbstractFactory<UsuarioRolEntidad> {
	private static final long serialVersionUID = -7403924307168485730L;

	public UsuarioRolEntidadFactoria() {
		super(UsuarioRolEntidad.class);
	}

	/**
	 * Asocia un usuario a una entidad según las definiciones del CU de asociar
	 * usuario a entidad
	 * 
	 * @param persona
	 * @param entidad
	 * @param tipoAsociacion
	 * @param usuarioAud
	 */
	public void asociarUsuario(PersonaDTO persona, EntidadDTO entidad, TipoAsociacionDTO tipoAsociacion,
			UsuarioDTO usuarioAud) throws SIGEP2SistemaException {
		String msg = "void asociarUsuario(PersonaDTO persona, EntidadDTO entidad, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioAud)";
		StoredProcedureQuery storedProcedure = this
				.createNamedStoredProcedureQuery(UsuarioRolEntidad.SP_ACTIVAR_USUARIO);
		try {
			storedProcedure.registerStoredProcedureParameter(1, BigInteger.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(12, Date.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(14, String.class, ParameterMode.IN);

			storedProcedure.setParameter(1, BigInteger.ZERO);
			storedProcedure.setParameter(2, entidad.getId());
			storedProcedure.setParameter(3, usuarioAud.getId());
			storedProcedure.setParameter(4, usuarioAud.getCodRol());
			storedProcedure.setParameter(5, persona.getTipoIdentificacionId().getSigla());
			storedProcedure.setParameter(6, persona.getNumeroIdentificacion());
			storedProcedure.setParameter(7, persona.getPrimerNombre());
			storedProcedure.setParameter(8, persona.getSegundoNombre());
			storedProcedure.setParameter(9, persona.getPrimerApellido());
			storedProcedure.setParameter(10, persona.getSegundoApellido());
			storedProcedure.setParameter(11, tipoAsociacion.getDescripcion());
			storedProcedure.setParameter(12, persona.getFechaNacimiento());
			storedProcedure.setParameter(13, persona.getGenero().getSigla());
			String correos = persona.getCorreoElectronico()
					+ (persona.getCorreoAlternativo() != null && !persona.getCorreoAlternativo().isEmpty()
							? ";" + persona.getCorreoAlternativo() : "");

			storedProcedure.setParameter(14, correos);

			storedProcedure.execute();
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(e);
		}
	}

	/**
	 * Crea un usuario y el registro de persona si no existe, teniendo en cuenta
	 * las validando las definiciones del CU de creación de usuario
	 * 
	 * @param persona
	 * @param entidad
	 * @param tipoAsociacion
	 * @param usuarioAud
	 * @throws SIGEP2SistemaException
	 */
	public void crearUsuario(PersonaDTO persona, EntidadDTO entidad, TipoAsociacionDTO tipoAsociacion,
			UsuarioDTO usuarioAud) throws SIGEP2SistemaException {
		String msg = "void crearUsuario(PersonaDTO persona, EntidadDTO entidad, TipoAsociacionDTO tipoAsociacion, UsuarioDTO usuarioAud)";
		StoredProcedureQuery storedProcedure = this.createNamedStoredProcedureQuery(UsuarioRolEntidad.SP_CREAR_USUARIO);
		try {
			storedProcedure.registerStoredProcedureParameter(1, BigInteger.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(3, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(12, Date.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
			storedProcedure.registerStoredProcedureParameter(14, String.class, ParameterMode.IN);

			storedProcedure.setParameter(1, BigInteger.ZERO);
			storedProcedure.setParameter(2, entidad.getId());
			storedProcedure.setParameter(3, usuarioAud.getId());
			storedProcedure.setParameter(4, usuarioAud.getCodRol());
			storedProcedure.setParameter(5, persona.getTipoIdentificacionId().getSigla());
			storedProcedure.setParameter(6, persona.getNumeroIdentificacion());

			String correos = persona.getCorreoElectronico()
					+ (persona.getCorreoAlternativo() != null && !persona.getCorreoAlternativo().isEmpty()
							? ";" + persona.getCorreoAlternativo() : "");

			storedProcedure.setParameter(7, correos);
			storedProcedure.setParameter(8, persona.getPrimerNombre());
			storedProcedure.setParameter(9, persona.getSegundoNombre());
			storedProcedure.setParameter(10, persona.getPrimerApellido());
			storedProcedure.setParameter(11, persona.getSegundoApellido());
			storedProcedure.setParameter(12, persona.getFechaNacimiento());
			storedProcedure.setParameter(13, persona.getGenero().getSigla());
			storedProcedure.setParameter(14, tipoAsociacion.getSigla());

			
            Query query = entityManager.createNativeQuery("CALL sp_crear_usuario(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");	
            query.setParameter(1, BigInteger.ZERO);
            query.setParameter(2, entidad.getId());
            query.setParameter(3, usuarioAud.getId());
            query.setParameter(4, usuarioAud.getCodRol());
            query.setParameter(5, persona.getTipoIdentificacionId().getSigla());
            query.setParameter(6, persona.getNumeroIdentificacion());
            query.setParameter(7, correos);
            query.setParameter(8, persona.getPrimerNombre());
            query.setParameter(9, persona.getSegundoNombre());
            query.setParameter(10, persona.getPrimerApellido());
            query.setParameter(11, persona.getSegundoApellido());
            query.setParameter(12, persona.getFechaNacimiento());
            query.setParameter(13, persona.getGenero().getSigla());
            query.setParameter(14, tipoAsociacion.getSigla());

            // Ejecutar la consulta
            query.executeUpdate();

            // Confirmar la transacción
           // entityManager.getTransaction().commit();            
			
			
			//storedProcedure.execute();
		} catch (Exception e) {
			logger.log().error(msg, e);
			throw new SIGEP2SistemaException(e);
		}
	}

	/**
	 * Se valida si el usuario existe.
	 *
	 * @param persona
	 * @param idEntidad
	 * @param tipoAsociacion
	 * @return true si existe.
	 */
	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad, long tipoAsociacion) {
		String query = SQLNames.getSQL(SQLNames.V_USUARIO_ROL_ENTIDAD_SQL)
				+ SQLNames.getSQL(SQLNames.V_USUARIO_ROL_ENTIDAD_BY_ASOCIACION);
		try {
			createNativeQuery(query).setParameter("personaId", persona.getId())
					.setParameter("tipoAsociacion", tipoAsociacion).setParameter("idEntidad", idEntidad)
					.setMaxResults(1).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * Se valida si el usuario existe en la entidad sin importar el tipod de
	 * asociación.
	 *
	 * @param persona
	 * @param idEntidad
	 * @return true si existe.
	 */
	public boolean usuarioExisteyActivo(PersonaDTO persona, long idEntidad) {
		String query = SQLNames.getSQL(SQLNames.V_USUARIO_ROL_ENTIDAD_SQL)
				+ SQLNames.getSQL(SQLNames.V_USUARIO_ROL_ENTIDAD_BY_ENTIDAD);
		try {
			createNativeQuery(query).setParameter("personaId", persona.getId()).setParameter("idEntidad", idEntidad)
					.setMaxResults(1).getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	/**
	 * El metodo crea una persona y asigna usuario de acuerdo a las
	 * validaciones.
	 */
	public void crearPersona(PersonaDTO persona, Long tipoAsociacion, Long idUsuarioSesion, Long idEntidad) {
		if (!usuarioExisteyActivo(persona, idEntidad, idEntidad)) {
			String query = null;
			String estado = null;
			List<?> lista1 = null;
			List<?> lista2 = null;

			query = "select flg_estado from usuario where cod_persona = :personaId";
			lista1 = createNativeQuery(query).setParameter("personaId", persona.getId()).getResultList();
			for (Object object : lista1) {
				estado = object.toString();
			}

			query = "SELECT cod_entidad FROM usuario_entidad WHERE cod_usuario = (select cod_usuario from usuario where cod_persona = :personaId) AND cod_entidad = :idEntidad";
			lista2 = createNativeQuery(query).setParameter("idEntidad", idEntidad)
					.setParameter("personaId", persona.getId()).getResultList();
			if (lista2.size() < 1 && persona.getId() != 0) {
				// Asignar nueva entidad a usuario
				query = "UPDATE persona SET fecha_nacimiento = :nacimiento, cod_genero = :codGenero, primer_nombre = UPPER(:primerNombre), segundo_nombre = UPPER(:segundoNombre), primer_apellido = UPPER(:primerApellido), segundo_apellido = UPPER(:segundoApellido), correo_electronico = UPPER(:correoElectronico), correo_alternativo = UPPER(:correoAlternativo), aud_fecha_actualizacion = sysdate, flg_activo = 1, flg_estado = 1, aud_accion = 63, aud_cod_usuario = :idUsuarioSesion WHERE cod_persona = :personaId";
				createNativeQuery(query).setParameter("codGenero", persona.getGenero().getId())
						.setParameter("idUsuarioSesion", idUsuarioSesion)
						.setParameter("nacimiento", persona.getFechaNacimiento())
						.setParameter("primerNombre", persona.getPrimerNombre())
						.setParameter("segundoNombre", persona.getSegundoNombre())
						.setParameter("primerApellido", persona.getPrimerApellido())
						.setParameter("segundoApellido", persona.getSegundoApellido())
						.setParameter("correoElectronico", persona.getCorreoElectronico())
						.setParameter("correoAlternativo", persona.getCorreoAlternativo())
						.setParameter("personaId", persona.getId()).executeUpdate();
				query = "INSERT INTO usuario_entidad (cod_usuario, cod_entidad, flg_activo, cod_tipo_vinculacion, aud_fecha_actualizacion, aud_cod_usuario, aud_cod_rol, aud_accion) VALUES ((select cod_usuario from usuario where cod_persona = :personaId), :idEntidad, 1, :tipoAsociacion, SYSDATE, :idUsuarioSesion, 1, 201)";
				createNativeQuery(query).setParameter("personaId", persona.getId()).setParameter("idEntidad", idEntidad)
						.setParameter("tipoAsociacion", tipoAsociacion).setParameter("idUsuarioSesion", idUsuarioSesion)
						.executeUpdate();
				query = "INSERT INTO HOJA_VIDA (COD_PERSONA, COD_ENTIDAD, FLG_APROBADO, AUD_FECHA_ACTUALIZACION, AUD_COD_USUARIO, AUD_COD_ROL, AUD_ACCION, FLG_ACTIVO) VALUES (:codPersona, :codEntidad, '0', SYSDATE, :codPersonaAud, '1', '63', '1')";
				createNativeQuery(query).setParameter("codPersona", persona.getId())
						.setParameter("codEntidad", idEntidad).setParameter("codPersonaAud", idUsuarioSesion)
						.executeUpdate();
				System.out.println("** SE HA ASIGNADO NUEVA ENTIDAD A USUARIO **");
			} else if ((lista1.size() > 0 && persona.getId() != idUsuarioSesion)
					|| (lista1.size() > 0 && estado.trim() == "0")) {
				query = "UPDATE persona SET fecha_nacimiento = :nacimiento, cod_genero = :codGenero, primer_nombre = UPPER(:primerNombre), segundo_nombre = UPPER(:segundoNombre), primer_apellido = UPPER(:primerApellido), segundo_apellido = UPPER(:segundoApellido), correo_electronico = UPPER(:correoElectronico), correo_alternativo = UPPER(:correoAlternativo), aud_fecha_actualizacion = sysdate, flg_activo = 1, flg_estado = 1, aud_accion = 63, aud_cod_usuario = :idUsuarioSesion WHERE cod_persona = :personaId";
				System.out
						.println("FACTORIA method --> crearUsuario()\n nacimiento --> " + persona.getFechaNacimiento());
				createNativeQuery(query).setParameter("codGenero", persona.getGenero().getId())
						.setParameter("idUsuarioSesion", idUsuarioSesion)
						.setParameter("nacimiento", persona.getFechaNacimiento())
						.setParameter("primerNombre", persona.getPrimerNombre())
						.setParameter("segundoNombre", persona.getSegundoNombre())
						.setParameter("primerApellido", persona.getPrimerApellido())
						.setParameter("segundoApellido", persona.getSegundoApellido())
						.setParameter("correoElectronico", persona.getCorreoElectronico())
						.setParameter("correoAlternativo", persona.getCorreoAlternativo())
						.setParameter("personaId", persona.getId()).executeUpdate();
				query = "UPDATE usuario SET flg_bloqueado = 0, fecha_vence = '01/01/2019', flg_estado = 1, aud_fecha_actualizacion = sysdate, aud_cod_usuario = :idUsuarioSesion, aud_cod_rol = 1, aud_accion = 63 WHERE cod_persona = :personaId";
				createNativeQuery(query).setParameter("idUsuarioSesion", idUsuarioSesion)
						.setParameter("personaId", persona.getId()).executeUpdate();
				query = "UPDATE usuario_entidad SET flg_activo = 1, cod_entidad = :idEntidad, cod_tipo_vinculacion = :tipoAsociacion, aud_fecha_actualizacion = sysdate, aud_cod_usuario = :idUsuarioSesion, aud_cod_rol = 1, aud_accion = 63 WHERE cod_usuario_entidad = (SELECT cod_usuario_entidad FROM usuario_entidad WHERE usuario_entidad.cod_usuario = (SELECT cod_usuario FROM usuario WHERE cod_persona = :personaId) AND cod_entidad = :idEntidad)";
				createNativeQuery(query).setParameter("tipoAsociacion", tipoAsociacion)
						.setParameter("idUsuarioSesion", idUsuarioSesion).setParameter("idEntidad", idEntidad)
						.setParameter("personaId", persona.getId()).executeUpdate();
				query = "UPDATE hoja_vida SET flg_activo = 1, flg_aprobado = 0 WHERE cod_persona = :codPersona AND cod_entidad = :codEntidad";
				createNativeQuery(query).setParameter("codPersona", persona.getId())
						.setParameter("codEntidad", idEntidad).executeUpdate();
				System.out.println("** Hace un ACTIVAR USUARIO **");
			} else {
				// Si usuario no existe lo crea y se le asigna la entidad.
				StoredProcedureQuery storedProcedure = this
						.createNamedStoredProcedureQuery(UsuarioRolEntidad.SP_CREAR_USUARIO1);
				storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(9, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(10, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(11, String.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(12, Date.class, ParameterMode.IN);
				storedProcedure.registerStoredProcedureParameter(13, String.class, ParameterMode.IN);
				storedProcedure.setParameter(1, persona.getPrimerNombre());
				storedProcedure.setParameter(2, persona.getSegundoNombre());
				storedProcedure.setParameter(3, persona.getPrimerApellido());
				storedProcedure.setParameter(4, persona.getSegundoApellido());
				storedProcedure.setParameter(5, persona.getCorreoElectronico());
				storedProcedure.setParameter(6, persona.getCorreoAlternativo());
				storedProcedure.setParameter(7, persona.getNumeroIdentificacion().toString());
				storedProcedure.setParameter(8, persona.getTipoIdentificacionId().getId().toString());
				storedProcedure.setParameter(9, idEntidad.toString());
				storedProcedure.setParameter(10, tipoAsociacion.toString());
				storedProcedure.setParameter(11, idUsuarioSesion.toString());
				storedProcedure.setParameter(12, persona.getFechaNacimiento());
				storedProcedure.setParameter(13, persona.getGenero().getId().toString());
				storedProcedure.execute();
				System.out.println("** Hace un CREAR PERSONA y CREAR USUARIO **");
			}
		}
	}

	public UsuarioRolEntidad buscarUsuarioRolEntidad(Long codUsuarioEntidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_ENTITY_SQL)
					+ "where ure.cod_usuario_entidad = :cod_usuario_entidad";
			return (UsuarioRolEntidad) createNativeQuery(query, UsuarioRolEntidad.class)
					.setParameter("cod_usuario_entidad", codUsuarioEntidad).getSingleResult();
		} catch (NoResultException e) {
			logger.debug("factoria - UsuarioRolEntidad buscarUsuarioRolEntidad(Long codUsuarioEntidad)", e);
			return null;
		}
	}

	/**
	 * Consulta de usuario rol entidad por asociacion de usuario entidad y rol
	 * 
	 * @param codUsuarioEntidad
	 * @param codRol
	 * @return {@link UsuarioRolEntidad}
	 */
	public UsuarioRolEntidad buscarUsuarioRolEntidad(Long codUsuarioEntidad, Long codRol) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_ENTITY_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_BY_USUARIO_ENTIDAD);
			return (UsuarioRolEntidad) createNativeQuery(query, UsuarioRolEntidad.class)
					.setParameter("cod_usuario_entidad", codUsuarioEntidad).setParameter("cod_rol", codRol)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.debug("UsuarioRolEntidad buscarUsuarioRolEntidad(Long codUsuarioEntidad, Long codRol)", e);
		}
		return null;
	}

	/**
	 * Consulta de usuario rol entidad por rol
	 * 
	 * @param codRol
	 * @return {@link UsuarioRolEntidad}
	 */
	@SuppressWarnings("unchecked")
	public List<UsuarioRolEntidad> buscarUsuarioRol(Long codRol) {
		try {
			String query = SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_ENTITY_SQL)
					+ SQLNames.getSQL(SQLNames.USUARIO_ROL_ENTIDAD_BY_ROL);
			return createNativeQuery(query, UsuarioRolEntidad.class).setParameter("cod_rol", codRol).getResultList();
		} catch (NoResultException e) {
			logger.debug("List<UsuarioRolEntidad> buscarUsuarioRol(Long codRol)", e);
		}
		return new LinkedList<>();
	}
}