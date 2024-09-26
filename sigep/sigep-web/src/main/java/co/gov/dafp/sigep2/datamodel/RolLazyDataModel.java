package co.gov.dafp.sigep2.datamodel;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.ext.RolExt;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

public class RolLazyDataModel extends LazyDataModel<RolDTO> {
	private static final long serialVersionUID = -3189386520796667904L;
	RolDTO role;
	private static int ESTADO_ACTIVO = 1;

	public RolDTO getRole() {
		return role;
	}

	public void setRole(RolDTO role) {
		this.role = role;
	}

	public RolLazyDataModel(RolDTO rol) {
		this.role = rol;
	}

	@Override
	public List<RolDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			RolExt objRolExt = new RolExt();
			objRolExt.setNombre(role.getNombre() == null ? "" : role.getNombre());
			objRolExt.setLimitInit(first);
			objRolExt.setLimitEnd(pageSize);
			objRolExt.setFlgActivo((short)ESTADO_ACTIVO);
			objRolExt.setFieldName(sortField);
			objRolExt.setAscDesc(sortOrder.toString().equals("ASCENDING")?"ASC":"DESC" );
			
			List<RolDTO> listaRol = ComunicacionServiciosSis.getRolesSistemaXObjeto(objRolExt);
			if (listaRol != null && listaRol.size() > 0) {
				RolDTO r = (RolDTO) listaRol.get(0);
				this.setRowCount(r.getTotal().intValue());
			} else
				this.setRowCount(0);
			return listaRol;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}