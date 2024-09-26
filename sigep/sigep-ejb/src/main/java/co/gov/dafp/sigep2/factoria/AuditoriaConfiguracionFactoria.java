package co.gov.dafp.sigep2.factoria;

import java.util.Date;

import javax.ejb.Stateless;

import co.gov.dafp.sigep2.entity.jpa.seguridad.AuditoriaConfiguracion;
import co.gov.dafp.sigep2.entity.seguridad.AuditoriaConfiguracionDTO;
import co.gov.dafp.sigep2.util.exceptions.SIGEP2NegocioException;

@Stateless
public class AuditoriaConfiguracionFactoria extends AbstractFactory<AuditoriaConfiguracion> {
	
	private static final long serialVersionUID = -2577559914223645739L;

	public AuditoriaConfiguracionFactoria() {
		super(AuditoriaConfiguracion.class);
	}
	
	@SuppressWarnings("unchecked")
	public boolean actualizarAuditoriaConfiguracion(AuditoriaConfiguracionDTO auditoria) throws SIGEP2NegocioException {
		try {
			AuditoriaConfiguracion audit=new AuditoriaConfiguracion(auditoria.getCodTablaAuditoria(),auditoria.getNombreTabla()
					,auditoria.getDescripcionTabla(),auditoria.getFlgInsert(),auditoria.getFlgDelete(),auditoria.getFlgUpdate(),auditoria.getFlgSelect()
					,auditoria.getFechaInicioAuditoria(),auditoria.getAudFechaActualizacion());
			audit.setFechaInicioAuditoria(new Date());
			audit.setFechaFinAuditoria(new Date());
			audit.setAudAccion(1L);
			audit.setAudCodRol(1L);
			audit.setAudFechaActualizacion(new Date());
			audit.setAudCodUsuario(1L);
			this.merge(audit, null);
			return true;
		} catch (Exception e) {
			throw new SIGEP2NegocioException(e.getMessage());
		}
	}

	
}
