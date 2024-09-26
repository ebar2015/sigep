/**
 * 
 */
package co.gov.dafp.sigep2.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.gov.dafp.sigep2.bean.UsuarioSession;
import co.gov.dafp.sigep2.services.UsuarioSessionService;
import co.gov.dafp.sigep2.utils.TipoParametro;

/**
 * @author Jose Viscaya
 * Jan 14, 2019
 */
public class CerrarSessiones{
	/**
	 * 
	 * @author: Jose Viscaya
	 *
	 */
	public static void starTask(){
		TaskSessiones taskSub = new TaskSessiones();
		taskSub.start();
	}

}
/**
 * 
 * @author: Jose Viscaya
 *
 */
class TaskSessiones extends Thread{
	UsuarioSessionService service = new UsuarioSessionService();
	UsuarioSession usuarioSession = new UsuarioSession();
	public void run() {
		 usuarioSession.setFlgActivo((short) 1);
		 while(true){
			 long tUsuarios = service.getUsuarioSessionTotal();
			// List<UsuarioSession> usuarios = service.getUsuarioSession(usuarioSession);
			 List<UsuarioSession> usuarios  = new ArrayList<>();
			 if(!usuarios.isEmpty()) {
				 Date hoy = new Date();
				 for (UsuarioSession usuSession : usuarios) {
					 if(getDiferenciaFechas(usuSession.getFechaInicioSession(),hoy) > 5) {
						 usuSession.setFechaFinSession(hoy);
						 usuSession.setAudFechaActualizacion(hoy);
						 usuSession.setFlgActivo((short) 0);
						 usuSession.setAudAccion(Integer.valueOf(TipoParametro.AUDITORIA_UPDATE.getValue()));
						 service.updateUsuarioSession(usuSession);
					 }
				}
			 }
		 }
		 
    }
	
	/**
	 * @author Jose Viscaya
	 * @fecha Jan 14, 2019
	 * @param dinicio
	 * @param dfinal
	 * @return
	 */
	public static long getDiferenciaFechas(Date dinicio, Date dfinal){
        long milis1;
        long milis2; 
        long diff;
        Calendar cinicio = Calendar.getInstance();
        Calendar cfinal = Calendar.getInstance();
        cinicio.setTime(dinicio);
        cfinal.setTime(dfinal);
        milis1 = cinicio.getTimeInMillis();
        milis2 = cfinal.getTimeInMillis();
        diff = milis2-milis1;
        long diffMinutos =  Math.abs (diff / (60 * 1000));
       return diffMinutos;
   }
}
