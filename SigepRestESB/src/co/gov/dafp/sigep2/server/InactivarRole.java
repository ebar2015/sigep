/**
 * @Author: Jose Viscaya
 * @Date  : Jun 6, 2019, 8:21:43 AM
 */
package co.gov.dafp.sigep2.server;

import java.math.BigDecimal;
import java.util.Date;

import co.gov.dafp.sigep2.bean.Parametrica;
import co.gov.dafp.sigep2.services.ParametricaService;
import co.gov.dafp.sigep2.services.RolService;

/**
 * 
 * @author: Jose Viscaya
 * @Date  : Jun 6, 2019, 8:24:42 AM
 */
public class InactivarRole {
	
	public static void starTask(){
		TaskRoles taskSub = new TaskRoles();
		taskSub.start();
	}
	
}
	/**
	 * 
	 * @author: Jose Viscaya
	 * @Date  : Jun 6, 2019, 8:24:46 AM
	 */
	class TaskRoles extends Thread{
		BigDecimal FRECUENCIA_DIAS = new BigDecimal(2116);
		ParametricaService serviceP = new ParametricaService();
		RolService serviceR = new RolService();
		Date ejecutado = new Date();
		int dias = 0;
		public void run() {
			while(true){
				Parametrica param = serviceP.getPrametricaById(FRECUENCIA_DIAS);
				try {
					Thread.sleep(50000);
					dias =  Integer.parseInt(param.getValorParametro());
				} catch (NumberFormatException | InterruptedException e) {
					dias = 5;
				}
				if(getDias(ejecutado) >= dias) {
					serviceR.desactivarRoles();
					ejecutado = new Date();
				}else if(getDias(ejecutado) == 0){
					serviceR.desactivarRoles();
					ejecutado = new Date();
					int d = ejecutado.getDay();
					d++;
					ejecutado.setDate(d);
				}
			}
		}
		/**
		 * 
		 * @Author: Jose Viscaya
		 * @Date:   Jun 6, 2019, 9:18:51 AM
		 * @File:   InactivarRole.java
		 * @param dinicio
		 * @return
		 */
		public static int getDias(Date dinicio){
			Date dfinal = new Date();
			int dias=(int) ((dinicio.getTime()-dfinal.getTime())/86400000);
	       return dias;
	   }
	}


