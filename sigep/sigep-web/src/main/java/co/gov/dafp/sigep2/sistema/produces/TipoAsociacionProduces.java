package co.gov.dafp.sigep2.sistema.produces;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.el.ELContext;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import co.gov.dafp.sigep2.deledago.AdministracionDelegate;
import co.gov.dafp.sigep2.entity.seguridad.RolDTO;
import co.gov.dafp.sigep2.entity.view.TipoAsociacionDTO;
import co.gov.dafp.sigep2.mbean.SesionBean;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2SistemaException;

@SessionScoped
public class TipoAsociacionProduces implements Serializable {
	private static final long serialVersionUID = -5831536365462054561L;

	private List<TipoAsociacionDTO> tipoAsociacion;

	private List<TipoAsociacionDTO> tipoAsociacionActivarUsuario;

	private SesionBean sesionBean;

	@SuppressWarnings("unchecked")
	public TipoAsociacionProduces() {
		try {
			ELContext eLContext = FacesContext.getCurrentInstance().getELContext();
			if (sesionBean == null) {
				sesionBean = (SesionBean) eLContext.getELResolver().getValue(eLContext, null, "sesionBean");
			}
			if (tipoAsociacionActivarUsuario == null) {
				
				List<TipoAsociacionDTO> tipoAsociacionActivarUsuarioTemp = AdministracionDelegate
						.findTipoAsociacion();
				tipoAsociacionActivarUsuario = new LinkedList<>();
				
				if (sesionBean.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_TALENTO_HUMANO, RolDTO.OPERADOR_TALENTO_HUMANO)) {
					for (TipoAsociacionDTO tipoAsociacion : tipoAsociacionActivarUsuarioTemp) {
						if (!tipoAsociacion.getDescripcion().equalsIgnoreCase(TipoAsociacionDTO.CONTRATISTA)) {
							tipoAsociacionActivarUsuario.add(tipoAsociacion);
						}
					}
				} 
				if (sesionBean.usuarioTieneRolAsignadoSinJerarquia(RolDTO.JEFE_CONTRATOS, RolDTO.OPERADOR_CONTRATOS)) {
					for (TipoAsociacionDTO tipoAsociacion : tipoAsociacionActivarUsuarioTemp) {
						if (tipoAsociacion.getDescripcion().equalsIgnoreCase(TipoAsociacionDTO.CONTRATISTA)) {
							tipoAsociacionActivarUsuario.add(tipoAsociacion);
							break;
						}
					}
				}
				if (sesionBean.usuarioTieneRolAsignadoSinJerarquia(RolDTO.ADMINISTRADOR_FUNCIONAL, RolDTO.ADMINISTRADOR_ENTIDADES, RolDTO.SUPER_ADMINISTRADOR)) {
					tipoAsociacionActivarUsuario = AdministracionDelegate.findTipoAsociacion();
				}
			}
			if (tipoAsociacion == null) {
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				tipoAsociacion = (List<TipoAsociacionDTO>) contexto.getSessionMap().get("tipoAsociacion");

				if (tipoAsociacion == null) {
					tipoAsociacion = AdministracionDelegate.findTipoAsociacion();
					contexto.getSessionMap().put("tipoAsociacion", tipoAsociacion);
				}
			}
		} catch (SIGEP2SistemaException e) {
		}
	}

	@Named
	@Produces
	public List<TipoAsociacionDTO> getTipoAsociacion() {
		return tipoAsociacion;
	}

	@Named
	@Produces
	public List<TipoAsociacionDTO> getTipoAsociacionActivarUsuario() {
		return tipoAsociacionActivarUsuario;
	}
}
