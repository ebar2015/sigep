/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Usuario;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class RolFactoria extends AbstractFactory<Rol> {
	private static final long serialVersionUID = -305718042519822675L;

	public RolFactoria() {
		super(Rol.class);
	}

	public void crearRoles(Rol... roles) throws SIGEP2SistemaException {
		if (roles != null) {
			for (Rol nuevoRol : roles) {
				if (findByNombre(nuevoRol.getNombre()) == null) {
					persist(nuevoRol, null);

					logger.log().info("void crearRoles(Rol... roles)", nuevoRol.getNombre() + " creado");
				}
			}
		}
	}

	public void crearRoles(Usuario usuarioAud, Rol... roles) throws SIGEP2SistemaException {
		if (roles != null) {
			for (Rol nuevoRol : roles) {
				if (findByNombre(nuevoRol.getNombre()) == null) {
					persist(nuevoRol, usuarioAud);

					logger.log().info("void crearRoles(Usuario usuarioAud, Rol... roles)",
							nuevoRol.getNombre() + " creado");
				}
			}
		}
	}

	public void actualizarRoles(Usuario usuarioAud, Rol... roles) throws SIGEP2SistemaException {
		if (roles != null) {
			for (Rol nuevoRol : roles) {
				if ("3".equals(nuevoRol.getAudAccion())) {
					remove(nuevoRol, usuarioAud);
				} else {
					merge(nuevoRol, usuarioAud);
				}
				logger.log().info("void actualizarRoles(Usuario usuarioAud, Rol... roles)",
						nuevoRol.getNombre() + " actualizado");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<RolDTO> getRoles() {
		return this.createNativeQuery(SQLNames.getSQL(SQLNames.ROL_SQL), Rol.ROL_MAPPING).getResultList();
	}

	public RolDTO findByNombre(String nombre) {
		try {
			return (RolDTO) this.createNativeQuery(
					SQLNames.getSQL(SQLNames.ROL_SQL) + SQLNames.getSQL(SQLNames.ROL_FIND_BY_NOMBRE_SQL),
					Rol.ROL_MAPPING).setParameter("nombre", nombre).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<RolDTO> obtenerRolesPorDescripcion(String... nombre) {
		try {
			if (nombre == null) {
				return null;
			}
			return this.createNativeQuery(
					SQLNames.getSQL(SQLNames.ROL_SQL) + SQLNames.getSQL(SQLNames.ROL_FIND_BY_DESCRIPCION_SQL),
					Rol.ROL_MAPPING).setParameter("nombre", Arrays.asList(nombre)).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Rol obtenerRolPorDescripcion(String nombre) {
		try {
			if (nombre == null) {
				return null;
			}
			RolDTO rolDTO = (RolDTO) this.createNativeQuery(
					SQLNames.getSQL(SQLNames.ROL_SQL) + SQLNames.getSQL(SQLNames.ROL_FIND_BY_DESCRIPCION_SQL),
					Rol.ROL_MAPPING).setParameter("nombre", Arrays.asList(nombre)).getSingleResult();
			return find(rolDTO.getId());
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<RolDTO> obtenerRolesPorPersona(long personaId, long entidadId) {
		try {
			
			return this.createNativeQuery(
					" select DISTINCT r.* from ROL r" + 
					"  INNER JOIN USUARIO_ROL_ENTIDAD ure" + 
					"  on r.COD_ROL = ure.COD_ROL" + 
					"  and ure.FLG_ACTIVO=1" + 
					"  and ure.FLG_ESTADO=1" + 
					"  INNER JOIN USUARIO_ENTIDAD ue" + 
					"  on ure.COD_USUARIO_ENTIDAD = ue.COD_USUARIO_ENTIDAD" + 
					"  and ue.FLG_ACTIVO=1" + 
					"  INNER JOIN USUARIO u" + 
					"  on ue.COD_USUARIO = u.COD_USUARIO" + 
					"  and u.FLG_ESTADO=1" + 
					" where 1=1" + 
					" and u.COD_PERSONA=:codPersona" + 
					" and ue.COD_ENTIDAD=:codEntidad" + 
					" ORDER BY r.NOMBRE",
					Rol.ROL_MAPPING)
					.setParameter("codPersona", personaId)
					.setParameter("codEntidad", entidadId)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
