package co.gov.dafp.sigep2.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.mbean.SesionBean;
import co.gov.dafp.sigep2.servicios.ComunicacionServiciosSis;

@Named("rolConverter")
public class RolConverter implements Converter {
	List<RolDTO> rolesUsuario = null;
	private static int ESTADO_ACTIVO = 1;
	
	@Inject
	SesionBean sesionBean;

	@SuppressWarnings("unchecked")
	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null) {
			return null;
		}
		ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
		List<RolDTO> rolesUsuarioAdm = null;
		try {
			if (sesionBean!=null && sesionBean.getRolAuditoria()!=null) {
				rolesUsuarioAdm = ComunicacionServiciosSis.getRolesSistema("", 0,100, ESTADO_ACTIVO);
				contexto.getSessionMap().put("rolesUsuarioAdmSesion", rolesUsuarioAdm);
			}
		} catch (Exception e) {
			return null;
		}
		RolDTO rol = new RolDTO();
		rol.setId(Long.valueOf(value));
		return rolesUsuarioAdm.get(rolesUsuarioAdm.indexOf(rol));
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof String) {
			return value.toString();
		}

		Object id = String.valueOf(((RolDTO) value).getId());
		if (id == null || id.toString().isEmpty()) {
			return "";
		}
		return id.toString();
	}
}
