
package co.gov.dafp.sigep2.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author joseviscaya
 *
 */
public class Pruebas {
			public static void main(String[] args) throws Exception {
				calcularTiempoExperiencia(3);
			}
			
			public static void calcularTiempoExperiencia(int tim) {
				Date d = new Date(1998, 8, 1);
				LocalDate fechaInicial = d.toInstant()
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
				d = new Date(2019, 1, 1);
				LocalDate fechaFin = d.toInstant()
					      .atZone(ZoneId.systemDefault())
					      .toLocalDate();
				
				
				
				long years = ChronoUnit.YEARS.between(fechaInicial, fechaFin); 
				long monts = ChronoUnit.MONTHS.between(fechaInicial, fechaFin); 
				long days = ChronoUnit.DAYS.between(fechaInicial, fechaFin); 
				monts = (monts -(years*12));
				days = ((days-(365*years))-(30*monts));
				if(tim == 1) {
					System.out.println("Tiempo Completo");
					System.out.println("Anos "+years);
					System.out.println("Meses "+monts);
					System.out.println("Dias "+days);
				}else if(tim == 2) {
					years = years*12;
					years = years + monts;
					years = years/2;
					System.out.println("Medio Tiempo");
					monts = years%12;
					years = years/12;
					days = days/2;
					if(days < 0) {
						days = 0;
					}
					
					System.out.println("Anos "+years);
					System.out.println("Meses "+monts);
					System.out.println("Dias "+days);
				}else if(tim == 3) {
					System.out.println("Tiempo Parcial");
					long horasB = 192;
					long horasMes = 30;
					long hMonts = ChronoUnit.MONTHS.between(fechaInicial, fechaFin); 
					hMonts = hMonts * horasMes;
					monts = (hMonts/horasB);
					days = (hMonts%horasB);
					days = days/(horasB/30);
					years = monts/12;
					monts = monts%12;
					System.out.println("Anos "+years);
					System.out.println("Meses "+monts);
					System.out.println("Dias "+days);
					
				}
				
			}
}

//Anos 20
//Meses 5
//Dias 7
//Medio Tiempo
//Anos 10
//Meses 2
//Dias 3
//Tiempo Parcial
//Anos 3
//Meses 2
//Dias 1
