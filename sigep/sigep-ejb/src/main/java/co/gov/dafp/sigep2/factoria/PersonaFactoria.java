/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.gov.dafp.sigep2.factoria;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import co.gov.dafp.sigep2.constante.SQLNames;
import co.gov.dafp.sigep2.entity.jpa.seguridad.Persona;
import co.gov.dafp.sigep2.entity.seguridad.PersonaDTO;
import co.gov.dafp.sigep2.util.logger.Logger;

/**
 *
 * @author jhon.deavila
 */
@Stateless
public class PersonaFactoria extends AbstractFactory<Persona> {
	private static final long serialVersionUID = -7803228311046869240L;
	transient Logger logger = Logger.getInstance(PersonaFactoria.class);

	public PersonaFactoria() {
		super(Persona.class);
	}

	public Persona findPersonaByName(String name) {
		try {
			return createNamedQuery(Persona.BY_LIKE_FULL_NAME, Persona.class).setParameter("primerApellido", name)
					.setParameter("segundoApellido", name).setParameter("primerNombre", name)
					.setParameter("segundoNombre", name).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public PersonaDTO findPersonaByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento,
			String numeroIdentificacion) {
		try {
			String query = SQLNames.getSQL(SQLNames.PERSONA_SQL)
					+ SQLNames.getSQL(SQLNames.PERSONA_FIND_BY_TIPODOCUMENTO_AND_NUMEROIDENTIFICACION);
			return (PersonaDTO) createNativeQuery(query, Persona.PERSONA_MAPPING)
					.setParameter("cod_tipo_identificacion", tipoDocumento)
					.setParameter("numero_identificacion", numeroIdentificacion).setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public PersonaDTO findPersonaByTipoDocumentoAndNumeroIdentificacion(Long tipoDocumento, String numeroIdentificacion,
			Long codEntidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.PERSONA_SQL) + " "
					+ SQLNames.getSQL(SQLNames.PERSONA_FILTER_BY_ENTIDAD) + " "
					+ SQLNames.getSQL(SQLNames.PERSONA_FIND_BY_TIPODOCUMENTO_AND_NUMEROIDENTIFICACION).replace("where",
							"and");
			return (PersonaDTO) createNativeQuery(query, Persona.PERSONA_MAPPING)
					.setParameter("cod_tipo_identificacion", tipoDocumento)
					.setParameter("numero_identificacion", numeroIdentificacion).setParameter("entidad", codEntidad)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public PersonaDTO buscarPersonaAsociadaEntidad(Long tipoDocumento, String numeroIdentificacion, Long codEntidad) {
		try {
			String query = SQLNames.getSQL(SQLNames.PERSONA_SQL) + " " + SQLNames.getSQL(SQLNames.USUARIO_PERSONA) + " "
					+ SQLNames.getSQL(SQLNames.USUARIOENTIDAD_USUARIO) + " "
					+ SQLNames.getSQL(SQLNames.ENTIDAD_USAURIOENTIDAD) + " "
					+ SQLNames.getSQL(SQLNames.USUARIOROLENTIDAD_USUARIOENTIDAD) + " "
					+ SQLNames.getSQL(SQLNames.PERSONA_ASOCIADA_ENTIDAD);
			return (PersonaDTO) createNativeQuery(query, Persona.PERSONA_MAPPING)
					.setParameter("cod_tipo_identificacion", tipoDocumento)
					.setParameter("numero_identificacion", numeroIdentificacion).setParameter("entidad", codEntidad)
					.setMaxResults(1).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PersonaDTO> obtenerUsuariosFiltros(long tipoDoc, String doc, String primerNombre, String segundoNombre,
			String primerApellido, String segundoApellido, Boolean busquedaDocumento) {
		StringBuilder query = new StringBuilder();
		query.append(SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO));
		logger.info("Dentro de factoría 1 + " + query + " " + query.toString());

		String word = " where ";
		try {
			if (!primerNombre.isEmpty() && primerNombre != null) {
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_NOMBRE).replace("and", word)));
				word = "and";
			}
			if (!segundoNombre.isEmpty() && segundoNombre != null) {
				query.append(" ").append(
						(SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_NOMBRE_SEGUNDO).replace("and", word)));
				word = "and";
			}
			if (!primerApellido.isEmpty() && primerApellido != null) {
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_APELLIDO).replace("and", word)));
				word = "and";
			}
			if (!segundoApellido.isEmpty() && segundoApellido != null) {
				query.append(" ").append(
						(SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_APELLIDO_SEGUNDO).replace("and", word)));
				word = "and";
			}
			if (busquedaDocumento) {
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_NUM_DOC).replace("and", word)));
				word = "and";
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_TIP_DOC).replace("and", word)));
			}
			System.out.println("FactoriaPersona 3 " + query);
			// query.append(';');
			// createNamedQuery(Persona.BY_LIKE_FULL_NAME, Persona.class);
			Query q = createNativeQuery(query.toString(), Persona.PERSONA_MAPPING);
			if (!primerNombre.isEmpty() && primerNombre != null)
				q.setParameter("primer_nombre", primerNombre);
			if (!segundoNombre.isEmpty() && segundoNombre != null)
				q.setParameter("segundo_nombre", segundoNombre);
			if (!primerApellido.isEmpty() && primerApellido != null)
				q.setParameter("primer_apellido", primerApellido);
			if (!segundoApellido.isEmpty() && segundoApellido != null)
				q.setParameter("segundo_apellido", segundoApellido);
			if (busquedaDocumento && tipoDoc != -999999999999998L && !doc.isEmpty() && doc != null) {
				q.setParameter("cod_tipo_identificacion", tipoDoc);
				q.setParameter("numero_identificacion", doc);
			}
			logger.info("Dentro de factoría 2 + " + query + " " + query.toString());
			return (List<PersonaDTO>) q.getResultList();
		} catch (NoResultException e) {
			System.out.println("Error en persona factoria obtener usuarios filtro + " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PersonaDTO> obtenerUsuariosFiltros(long entidadID, long tipoDoc, String doc, String primerNombre,
			String segundoNombre, String primerApellido, String segundoApellido, Boolean busquedaDocumento,
			boolean isLazy, int first, int pageSize, String sortField, String sortOrder) {
		StringBuilder query = new StringBuilder();
		query.append(SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO));
		logger.info("Dentro de factoría 1 + " + query + " " + query.toString());

		String word = " where ";
		try {
			if (entidadID != -1) {
				query.append(" ").append(SQLNames.getSQL(SQLNames.USUARIO_PERSONA));
				query.append(" ").append(SQLNames.getSQL(SQLNames.USUARIOENTIDAD_USUARIO));
				query.append(" ").append("inner join entidad e on ue.COD_ENTIDAD = e.COD_ENTIDAD");
				query.append(" ").append("inner join usuario_rol_entidad ure on ure.cod_usuario_entidad = ue.cod_usuario_entidad");
				if (entidadID != 0) {
					query.append(word);
					query.append((SQLNames.getSQL(SQLNames.USUARIO_ENTIDAD_ACTIVA).replaceFirst("and", "")));
					word = "and";
				}
			}
			if (!busquedaDocumento) {
				if (!primerNombre.isEmpty() && primerNombre != null) {
					query.append(" ")
							.append(("and lower(translate(primer_nombre, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) like translate(:primer_nombre,'áéíóúÁÉÍÓÚ','aeiouAEIOU')").replace("and", word));
					word = "and";
				}
				if (!segundoNombre.isEmpty() && segundoNombre != null) {
					query.append(" ").append(
							("and lower(translate(segundo_nombre, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) like translate(:segundo_nombre,'áéíóúÁÉÍÓÚ','aeiouAEIOU')").replace("and", word));
					word = "and";
				}
				if (!primerApellido.isEmpty() && primerApellido != null) {
					query.append(" ").append(
							("and lower(translate(primer_apellido, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) like translate(:primer_apellido,'áéíóúÁÉÍÓÚ','aeiouAEIOU')").replace("and", word));
					word = "and";
				}
				if (!segundoApellido.isEmpty() && segundoApellido != null) {
					query.append(" ").append(("and lower(translate(segundo_apellido, 'áéíóúÁÉÍÓÚ', 'aeiouAEIOU')) like translate(:segundo_apellido,'áéíóúÁÉÍÓÚ','aeiouAEIOU')")
							.replace("and", word));
					word = "and";
				}
			} else {
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_NUM_DOC).replace("and", word)));
				word = "and";
				query.append(" ")
						.append((SQLNames.getSQL(SQLNames.CONSULTAR_PERSONAS_FILTRO_TIP_DOC).replace("and", word)));
			}
			query.append(" ").append(" and ue.flg_activo = 1 and ue.flg_activo = 1 and ure.flg_activo = 1");
			
			if (entidadID != -1) {
				query.append(" and e.flg_activo = 1 ");
			}
			
			if( sortField!=null && !"".equals(sortField)  && sortOrder!=null && !"".equals(sortOrder) ){
	    		if(sortField.equals("numeroIdentificacion")){
	    			query = query.append("order by numero_identificacion "+(sortOrder.equals("ASCENDING")?"ASC":"DESC"));
	    		}else if(sortField.equals("nombreEntidad")){
	    			query = query.append("order by nombre_entidad "+(sortOrder.equals("ASCENDING")?"ASC":"DESC"));
	    		}else if(sortField.equals("tipoIdentificacionId.descripcion")){
	    			query = query.append("order by cod_tipo_identificacion "+(sortOrder.equals("ASCENDING")?"ASC":"DESC"));
	    		}
	    	}
			
			Query q = null;
			Query qCount = createNativeQuery(("select * from ("
					+ query.toString().replaceFirst("\\*", entidadID != -1 ? "distinct e.* " : "distinct p.* ")
					+ ") conteo").replaceFirst("\\*", "count(*) total"));
			if (isLazy) {
				String queryStr = query.toString();
				if (entidadID != -1) {
					queryStr = queryStr.replace("*",
							" distinct p.*, count(*) over() total, e.COD_ENTIDAD, e.NOMBRE_ENTIDAD, '' NOMBRE_DEPENDENCIA");
				} else {
					queryStr = queryStr.replace("*", " distinct p.*, count(*) over() total ");
				}
				queryStr = queryStr + " OFFSET " + first + " ROWS FETCH NEXT " + pageSize + " ROWS ONLY";
				if (entidadID != -1) {
					q = createNativeQuery(queryStr, Persona.MAPPING_PERSONA_ROLES);
				} else {
					q = createNativeQuery(queryStr, Persona.MAPPING_LAZYMODEL);
				}
			} else {
				q = createNativeQuery(query.toString(), Persona.PERSONA_MAPPING);
			}

			if (entidadID > 0) {
				q.setParameter("entidad_id", entidadID);

				qCount.setParameter("entidad_id", entidadID);
			}
			if (busquedaDocumento && tipoDoc != -999999999999998L && !doc.isEmpty() && doc != null) {
				q.setParameter("cod_tipo_identificacion", tipoDoc);
				q.setParameter("numero_identificacion", doc);

				qCount.setParameter("cod_tipo_identificacion", tipoDoc);
				qCount.setParameter("numero_identificacion", doc);
			} else {
				if (!primerNombre.isEmpty() && primerNombre != null) {
					q.setParameter("primer_nombre", primerNombre.toLowerCase());

					qCount.setParameter("primer_nombre", primerNombre.toLowerCase());
				}
				if (!segundoNombre.isEmpty() && segundoNombre != null) {
					q.setParameter("segundo_nombre", segundoNombre.toLowerCase());

					qCount.setParameter("segundo_nombre", segundoNombre.toLowerCase());
				}
				if (!primerApellido.isEmpty() && primerApellido != null) {
					q.setParameter("primer_apellido", primerApellido.toLowerCase());

					qCount.setParameter("primer_apellido", primerApellido.toLowerCase());
				}
				if (!segundoApellido.isEmpty() && segundoApellido != null) {
					q.setParameter("segundo_apellido", segundoApellido.toLowerCase());

					qCount.setParameter("segundo_apellido", segundoApellido.toLowerCase());
				}
			}
			logger.info("Dentro de factoría 2 + " + query + " " + query.toString());
			int count = Integer.parseInt(qCount.getSingleResult().toString());
			List<PersonaDTO> r = (List<PersonaDTO>) q.getResultList();
			if (count > 0) {
				r.get(0).setTotal(BigDecimal.valueOf(count));
			}
			return r;
		} catch (NoResultException e) {
			System.out.println("Error en persona factoria obtener usuarios filtro + " + e.getMessage());
			return null;
		}
	}

	public Persona consultarPersona(Long codPersona) {
		try {
			return (Persona) createNativeQuery("select * from persona where cod_persona=:codPersona", Persona.class)
					.setParameter("codPersona", codPersona).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}
}