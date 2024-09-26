/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this tthisplate file, choose Tools | Tthisplates
 * and open the tthisplate in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Recurso;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Rol;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.RecursoActivoPerfilUsuarioDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;
import co.gov.dafp.sigep2.view.RecursoActivoPerfilUsuario;

/**
 *
 * @author Jhon De Avila
 *
 * @param <T>
 */
@Stateless
public class RecursoFactoria extends AbstractFactory<Recurso> {
	private static final long serialVersionUID = 3353953462106517880L;

	public RecursoFactoria() {
		super(Recurso.class);
	}

	public void crearRecurso(Recurso entity) throws SIGEP2SistemaException {
		this.persist(entity, null);
	}

	public RecursoActivoPerfilUsuarioDTO findByCodigoVentanaDTO(String codigoVentana, Long usuarioId) {
		try {
			String query = SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_SQL)
					+ SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_FIND_BY_CODIGO_VENTANA_SQL)
					+ SQLNames.getSQL(SQLNames.ROL_USUARIO_ENTIDAD_FIND_BY_LOGIN_SQL).replace(SQLNames.WHERE,
							SQLNames.AND);
			return (RecursoActivoPerfilUsuarioDTO) createNativeQuery(query,
					RecursoActivoPerfilUsuario.RECURSO_ACTIVO_PERFIL_USUARIO_MAPPING).setMaxResults(1)
							.setParameter("codigoVentana", codigoVentana).setParameter("login", usuarioId)
							.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Recurso findByCodigoVentana(String codigoVentana)", e);
		} catch (Exception e) {
			logger.error("Recurso findByCodigoVentana(String codigoVentana)", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<RecursoActivoPerfilUsuarioDTO> obtenerRecursosActivosPorUsuario(Long usuarioId, Long entidadId) {
		String query = SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_SQL)
				+ SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_FIND_BY_CODIGO_PADRE_SQL)
				+ SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_FIND_BY_LOGIN_SQL).replace(SQLNames.WHERE,
						SQLNames.AND)
				+ SQLNames.getSQL(SQLNames.RECURSO_ACTIVO_PERFIL_USUARIO_VW_ORDER_BY);
		return createNativeQuery(query, RecursoActivoPerfilUsuario.RECURSO_ACTIVO_PERFIL_USUARIO_MAPPING)
				.setParameter("login", usuarioId).setParameter("entidad", entidadId).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RolDTO> obtenerRolesPorUsuario(Long usuarioId, Long entidadId) {
	
		/*return createNativeQuery("" + "SELECT r.*, sys_connect_by_path(r.COD_ROL, '->') path" + "     FROM rol r"
				+ "      where 1=1" + "        and exists" + "        (" + "       select * from ROL r1"
				+ "         inner join USUARIO_ROL_ENTIDAD ure" + "         on r1.COD_ROL = ure.COD_ROL"
				+ "         and ure.FLG_ACTIVO=1" + "         inner join usuario_entidad ue"
				+ "         on ure.COD_USUARIO_ENTIDAD = ue.COD_USUARIO_ENTIDAD"
				+ " 		left join USUARIO_ROL_DEPENDENCIA urd    "
				+ " 		on ure.COD_USUARIO_ROL_ENTIDAD = urd.COD_USUARIO_ROL_ENTIDAD " + "       where 1=1"
				+ " 		and (urd.FECHA_INICIO is null or (urd.FECHA_INICIO is not null and TO_DATE(TO_CHAR (sysdate, 'DD/MM/YYYY')) between urd.FECHA_INICIO and nvl(urd.FECHA_FIN, sysdate) and urd.flg_activo = 1) or r.nombre in ('SERVIDOR PUBLICO', 'CONTRATISTA')) "
				+ "       and ue.COD_USUARIO=:login" + "       and ue.COD_ENTIDAD=:entidad"
				+ "       and r1.COD_ROL=r.COD_ROL" + "     )" + "   START WITH COD_ROL = cod_rol_padre"
				+ "   CONNECT BY cod_rol_padre = PRIOR COD_ROL" + "              AND COD_ROL != PRIOR COD_ROL"
				+ " order by level", Rol.ROL_MAPPING).setParameter("login", usuarioId)
						.setParameter("entidad", entidadId).getResultList();*/
		
		return createNativeQuery(" WITH RECURSIVE rol_hierarchy AS ( " + 
				"                 SELECT r.*, CAST(r.COD_ROL AS text) AS path, 1 AS level " + 
				"                 FROM rol r " + 
				"                 WHERE COD_ROL = cod_rol_padre " + 
				"                 UNION ALL " + 
				"                 SELECT r.*, h.path || '->' || CAST(r.COD_ROL AS text), h.level + 1 " + 
				"                 FROM rol r " + 
				"                 JOIN rol_hierarchy h ON r.cod_rol_padre = h.COD_ROL " + 
				"                 WHERE r.COD_ROL != h.COD_ROL " + 
				"               ), " + 
				"               user_roles AS ( " + 
				"                 SELECT r1.COD_ROL " + 
				"                 FROM ROL r1 " + 
				"                 INNER JOIN USUARIO_ROL_ENTIDAD ure ON r1.COD_ROL = ure.COD_ROL AND ure.FLG_ACTIVO = 1 " + 
				"                 INNER JOIN USUARIO_ENTIDAD ue ON ure.COD_USUARIO_ENTIDAD = ue.COD_USUARIO_ENTIDAD " + 
				"                 LEFT JOIN USUARIO_ROL_DEPENDENCIA urd ON ure.COD_USUARIO_ROL_ENTIDAD = urd.COD_USUARIO_ROL_ENTIDAD " + 
				"                 WHERE ( " + 
				"                   urd.FECHA_INICIO IS NULL " + 
				"                   OR ( " + 
				"                     urd.FECHA_INICIO IS NOT NULL " + 
				"                     AND CURRENT_DATE BETWEEN urd.FECHA_INICIO AND COALESCE(urd.FECHA_FIN, CURRENT_DATE) " + 
				"                     AND urd.flg_activo = 1 " + 
				"                   ) " + 
				"                   OR r1.nombre IN ('SERVIDOR PUBLICO', 'CONTRATISTA') " + 
				"                 ) " + 
				"                 AND ue.COD_USUARIO = :login " + 
				"                 AND ue.COD_ENTIDAD = :entidad " + 
				"               ) " + 
				"               SELECT r.*, h.path " + 
				"               FROM rol_hierarchy h " + 
				"               JOIN rol r ON h.COD_ROL = r.COD_ROL " + 
				"               WHERE EXISTS ( " + 
				"                 SELECT 1 " + 
				"                 FROM user_roles ur " + 
				"                 WHERE ur.COD_ROL = r.COD_ROL " + 
				"               ) " + 
				"               ORDER BY h.level", Rol.ROL_MAPPING).setParameter("login", usuarioId).setParameter("entidad", entidadId).getResultList();		
	}

}
