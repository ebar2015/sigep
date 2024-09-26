package co.gov.dafp.sigep2.datamodel;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.constante.TitlesBundleConstants;
import co.gov.dafp.sigep2.entities.Rol;
import co.gov.dafp.sigep2.entity.seguridad.RecursoAccionDTO;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.GestionarPermisoBean;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;
import co.gov.dafp.sigep2.sistema.BaseBean;

/**
 * Representa el comportamiento <code>lazy</code> {@link RecursoAccionDTO} desde
 * la funcionalidad <code>gestionarPermisos.xhtml</code>
 */
public class PermisoLazyDataModel extends LazyDataModel<RecursoAccionDTO> {
	private static final long serialVersionUID = 6138076303376282506L;

	/**
	 * Listado de recursos {@link RecursoAccionDTO} resultado de la consulta
	 * segun los valores en <code>rol</code> y <code>recurso</code>
	 */
	private List<RecursoAccionDTO> listaRecursos = null;

	/**
	 * Filtro de busqueda por {@link Rol#getNombre()} y
	 * {@link Rol#getDescripcionRol()}. En caso de ser nulo o vacio el valor por
	 * defecto sera "%%"
	 */
	private RolDTO rol;
	/**
	 * filtra por {@link RecursoAccionDTO#getRecurso()} y
	 * {@link RecursoAccionDTO#getSeccion()}
	 */
	private String recurso;

	private boolean resetearBusqueda = false;

	public RolDTO getRol() {
		return rol;
	}

	public void setRol(RolDTO rol) {
		this.rol = rol;
	}

	public PermisoLazyDataModel(RolDTO rol, String recurso) {
		this.rol = rol;
		this.recurso = recurso;
		this.resetearBusqueda = true;
	}

	/**
	 * Realiza la carga <code>lazy</code> segun los valores en los filtros de
	 * busqueda
	 * 
	 * @param first
	 *            Indica el comienzo de la pagina
	 * @param pageSize
	 *            Indica el tamaño de la pagina
	 * @param sortField
	 *            No implementado
	 * @param sortOrder
	 *            No implementado
	 * @param filters
	 *            No implementado
	 * @return {@link List} de {@link RecursoAccionDTO} Listado requerido por el
	 *         <code>dataTable</code> en la vista para la construccion de la
	 *         pagina en dicha tabla
	 */
	@Override
	public List<RecursoAccionDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			int firstTemp = first;
			if (resetearBusqueda) {
				firstTemp = 0;
				resetearBusqueda = false;
			}
			GestionarPermisoBean gestionarPermisoBean = BaseBean.findBean("gestionarPermisoBean");

			listaRecursos = new LinkedList<>();

			if (gestionarPermisoBean != null) {
				StringBuilder recursosBuscar = new StringBuilder("");
				if (this.recurso != null && !this.recurso.isEmpty()) {
					List<String> recursos = TitlesBundleConstants.getKies(recurso, gestionarPermisoBean.getLocale());

					for (String recursoBuscar : recursos) {
						recursosBuscar.append("'" + recursoBuscar + "'" + ",");
					}
					if (!recursosBuscar.toString().isEmpty()) {
						recursosBuscar = new StringBuilder(
								recursosBuscar.toString().substring(0, recursosBuscar.lastIndexOf(",")));
					} else {
						recursosBuscar = new StringBuilder("''");
					}
				}
				listaRecursos = ComunicacionServiciosSis.getRecursosAccion(
						rol == null || rol.getNombre() == null ? "%%" : rol.getNombre(), recursosBuscar.toString(),
						firstTemp, pageSize);

				if (listaRecursos == null) {
					listaRecursos = new LinkedList<>();
				}

				boolean agregoDelPadre = false;
				int total = 0;
				if (listaRecursos.isEmpty() && rol != null && rol.getCodRolPadre() != null && !rol.isFlgRolBase()) {
					RolDTO rolGestionar = ComunicacionServiciosSis.getRolPorId(rol.getCodRolPadre().longValue());

					if (rolGestionar != null) {
						List<RecursoAccionDTO> listaRecursosPadre = ComunicacionServiciosSis
								.getRecursosAccion(rolGestionar == null || rolGestionar.getNombre() == null ? "%%"
										: rolGestionar.getNombre(), recursosBuscar.toString(), firstTemp, 0);

						if (listaRecursosPadre != null && !listaRecursosPadre.isEmpty()) {
							for (RecursoAccionDTO recursoPadre : listaRecursosPadre) {
								recursoPadre.setCodRol(BigInteger.valueOf(rol.getId()));
								recursoPadre.setCodPermisoRol(null);
								recursoPadre.setDescripcionRol(rolGestionar.getDescripcionRol());
								recursoPadre.setCodPermisoRolAcciones(BigInteger.ZERO);
								recursoPadre.setFlgEstado(false);

								if (!agregoDelPadre) {
									total = listaRecursosPadre.get(0).getTotal().intValue();
								}
								agregoDelPadre = true;
							}

							gestionarPermisoBean.setRecursosSeleccionados(listaRecursosPadre);
							gestionarPermisoBean.setHabilitarGuardar(false);
							gestionarPermisoBean.persist();
							gestionarPermisoBean.init();
							resetearBusqueda = false;
						}
						listaRecursos = ComunicacionServiciosSis.getRecursosAccion(
								rol == null || rol.getNombre() == null ? "%%" : rol.getNombre(), recursosBuscar.toString(),
								firstTemp, pageSize);
					}
				}

				if (listaRecursos != null && !listaRecursos.isEmpty()) {
					RecursoAccionDTO r = listaRecursos.get(0);
					if (!agregoDelPadre) {
						total = r.getTotal().intValue();
					}

					this.setRowCount(total);
					if (gestionarPermisoBean.getRecursosSeleccionados() != null
							&& !gestionarPermisoBean.getRecursosSeleccionados().isEmpty()) {
						for (RecursoAccionDTO seleccionado : gestionarPermisoBean.getRecursosSeleccionados()) {
							int index = this.listaRecursos.indexOf(seleccionado);
							if (index >= 0) {
								this.listaRecursos.get(index).setFlgEstado(seleccionado.isFlgEstado());
							}
						}
					}
				} else
					this.setRowCount(0);

				return listaRecursos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LinkedList<>();
	}

	/**
	 * Devuelve el registro seleccionado de acuerdo el <code>rowKey</code>
	 * definido en la vista
	 * 
	 * @param rowKey
	 *            Valor del identificador del registro definido en el
	 *            <code>dataTable</code> en la vista
	 * @return {@link RecursoAccionDTO} Registro del <code>dataTable</code> en
	 *         la vista que conincide con <code>rowKey</code>
	 */
	@Override
	public RecursoAccionDTO getRowData(String rowKey) {
		try {
			RecursoAccionDTO recursoProceso = new RecursoAccionDTO();
			recursoProceso.setId(Long.valueOf(rowKey));
			if (this.listaRecursos.contains(recursoProceso)) {
				return this.listaRecursos.get(this.listaRecursos.indexOf(recursoProceso));
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}

	/**
	 * Devuelve el resultado de la consulta segun los valores de los filtros de
	 * busqueda y el tamaño de la paginacion
	 * 
	 * @return {@link List} de {@link RecursoAccionDTO} Resultado de la consulta
	 */
	public List<RecursoAccionDTO> getListaRecursos() {
		return listaRecursos;
	}

	/**
	 * Si se requiere setea el listado del resutlado de la busqueda
	 * 
	 * @param listaRecursos
	 *            Nuevo listado
	 */
	public void setListaRecursos(List<RecursoAccionDTO> listaRecursos) {
		this.listaRecursos = listaRecursos;
	}
}