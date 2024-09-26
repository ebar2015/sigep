package co.gov.dafp.sigep2.scheduler.inicializator;

import java.io.Serializable;
import java.math.BigDecimal;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.scheduler.factoria.FactoriaJobCargasMasivas;
import co.gov.dafp.sigep2.scheduler.factoria.FactoriaJobContratos;
import co.gov.dafp.sigep2.scheduler.factoria.FactoriaJobGestionInformacion;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.utils.TipoParametro;

public class InicializaJobs implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public InicializaJobs() {
		/*try{
			iniciarJobContratos();
		}catch(Exception z){
		}
		try{
			iniciarJobGI();
		}catch(Exception z){
		}*/
		
		try{
			//iniciarJobCargaMasiva();
		}catch(Exception z){
			z.printStackTrace();
		}		
	}
	
	/**
	 * Inactivar contratos por vencimiento
	 */
	public void iniciarJobContratos() {
		FactoriaJobContratos.executeJobContratos();
	}
	
	/**
	 * Metodo que determina si se ejecuta el job de envio de notificaciones para el componente de GESTION DE LA INFORMACION
	 */
	public void iniciarJobGI() {
		String strAlertasGIActivas="N";
		ParametricaService parametricaService = new ParametricaService();
		Parametrica parametricasGI=null;
		BigDecimal bdCodAlertasGiActivas = BigDecimal.valueOf(TipoParametro.GI_ALERTAS_ACTIVAS.getValue());
		parametricasGI = parametricaService.getPrametricaById(bdCodAlertasGiActivas);
		if(parametricasGI!=null && parametricasGI.getValorParametro()!=null)
			strAlertasGIActivas = parametricasGI.getValorParametro();
		if("S".equals(strAlertasGIActivas))
			FactoriaJobGestionInformacion.executeFactoriaJobGestionInformacion();
	}
	
	/**
	 * Metodo que determina si se ejecuta el job de envio de notificaciones para el componente de CARGAS MASIVAS
	 */
	public void iniciarJobCargaMasiva() {
		String strActivaNotificacionCM ="N";
		Parametrica prActivaNotificacionCM=null;
		ParametricaService parametricaService = new ParametricaService();
		try{
			BigDecimal bdCodAlertasCMActivas = BigDecimal.valueOf(TipoParametro.CARGA_MASIVA_ACTIVA_NOTIFICACIONES.getValue());
			prActivaNotificacionCM = parametricaService.getPrametricaById(bdCodAlertasCMActivas);
			if(prActivaNotificacionCM!=null && prActivaNotificacionCM.getValorParametro()!=null)
				strActivaNotificacionCM = prActivaNotificacionCM.getValorParametro();
			if("S".equals(strActivaNotificacionCM))
				FactoriaJobCargasMasivas.executeJobCargasMasivas();
		}catch(Exception z){
			strActivaNotificacionCM = "N";
		}	
	}	
}
