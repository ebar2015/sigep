package co.gov.dafp.sigep2.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import co.gov.dafp.sigep2.constante.ConfigurationBundleConstants;
import co.gov.dafp.sigep2.constante.MessagesBundleConstants;

/**
 * Clase utilitaria para fechas
 *
 * @author Jhon De Avila
 */
public class DateUtils {
	public static final String ESPANIOL = "ES";
	public static final String FECHA_HORA_FORMATO = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.CNS_FECHA_HORA_FORMATO);
	public static final String FECHA_FORMATO = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.CNS_FECHA_FORMATO);
	public static final String FECHA_HORA_FORMATO_VO = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.CNS_FECHA_HORA_FORMATO_VO);
	public static final String FECHA_FORMATO_VO = ConfigurationBundleConstants
			.getString(ConfigurationBundleConstants.CNS_FECHA_FORMATO_VO);

	public static final String yyyyMMdd = "yyyyMMdd";

	public static final String ANIO_FORMATO = "yyyy";
	public static final String MES_FORMATO = "MM";
	public static final String PERIODO_FORMATO = "yyyy/MM";
	public static final String MES_LARGO_FORMATO = "MMMM";
	public static final String DIA_FORMATO = "dd";
	public static final String HORA_CORTA_FORMATO = "HH:mm";
	public static final String HORA_LARGA_FORMATO = "HH:mm:ss";

	private String[] datos = null;

	protected DateUtils() {
		super();
	}

	protected DateUtils(String... datos) {
		this.setDatos(datos);
	}

	protected static final DateUtils Dias = new DateUtils("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
			"29", "30", "31");
	protected static final DateUtils Meses = new DateUtils("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
			"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
	protected static final DateUtils Trimestre = new DateUtils("01", "02", "03", "04");
	protected static final DateUtils Semestre = new DateUtils("01", "02");

	/**
	 * Genera una lista con los nombres de los meses del año
	 *
	 * @return
	 */
	public static List<String> obtenerMeses() {
		List<String> meses = new LinkedList<>();
		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");
		return meses;
	}

	/**
	 * Retorna el a�o actual
	 *
	 * @return
	 */
	public static int anioActual() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int numeroMes(String mes) {
		List<String> meses = obtenerMeses();
		for (int i = 0; i <= meses.size(); i++) {
			if (meses.get(i).equals(mes)) {
				return i + 1;
			}
		}
		return 0;
	}

	public static List<Integer> obtenerAnios(int anoInicial) {
		List<Integer> anos = new LinkedList<>();
		for (int i = anoInicial; i <= anioActual(); i++) {
			anos.add(i);
		}
		return anos;
	}

	public static String formatearACadenaLarga(Date fecha) {
		if (fecha == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_HORA_FORMATO_VO);
		return format.format(fecha);
	}

	public static String formatearACadena(Date fecha) {
		if (fecha == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO_VO);
		return format.format(fecha);
	}

	public static String formatearACadena(Date fecha, String formato) {
		try {
			if (fecha == null) {
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat(formato);
			String strFecha = MessagesBundleConstants.getStringMessagesBundle(format.format(fecha),Locale.ROOT);
			return strFecha;
		} catch (Exception e) {
			return "";
		}
	}

	public static Date formatearAFecha(String formato, String fecha) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		return format.parse(fecha);
	}

	public String[] getDatos() {
		return datos;
	}

	public void setDatos(String[] datos) {
		this.datos = datos;
	}

	public static String getSaludo() throws ParseException {
		String mediaNoche = getHoy() + " 00:00:00";
		String medioDia = getHoy() + " 12:00:00";
		String tardeNoche = getHoy() + " 18:00:00";

		Date dia = formatearAFecha(DateUtils.FECHA_HORA_FORMATO, mediaNoche);
		Date tarde = formatearAFecha(DateUtils.FECHA_HORA_FORMATO, medioDia);
		Date noche = formatearAFecha(DateUtils.FECHA_HORA_FORMATO, tardeNoche);

		Date ahora = DateUtils.getFechaSistema();

		if (ahora.after(dia) && ahora.before(tarde)) {
			return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SALUDO_BUENOS_DIAS,Locale.ROOT);
		} else if (ahora.after(tarde) && ahora.before(noche)) {
			return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SALUDO_BUENAS_TARDES, Locale.ROOT);
		} else if (ahora.after(noche) && ahora.before(DateUtils.sumarDias(dia, 1))) {
			return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SALUDO_BUENAS_NOCHES,Locale.ROOT);
		}
		return MessagesBundleConstants.getStringMessagesBundle(MessagesBundleConstants.MSG_SALUDO_BUENOS_DIAS,Locale.ROOT);
	}

	public static String getAhora() {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_HORA_FORMATO);
		Date fecha = DateUtils.getFechaSistema();
		return format.format(fecha);
	}

	public static Date sumarDias(Date fechaOriginal, int diasSumar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaOriginal);
		calendar.add(Calendar.DATE, diasSumar);
		return calendar.getTime();
	}

	public static Date sumarMinutos(Date fechaOriginal, int minutosSumar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaOriginal);
		calendar.add(Calendar.MINUTE, minutosSumar);
		return calendar.getTime();
	}

	public static Date sumarHoras(Date fechaOriginal, int horasSumar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaOriginal);
		calendar.add(Calendar.HOUR, horasSumar);
		return calendar.getTime();
	}

	public static Date sumarSegundos(Date fechaOriginal, int horasSumar) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaOriginal);
		calendar.add(Calendar.SECOND, horasSumar);
		return calendar.getTime();
	}

	public static String getHoy() {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
		Date fecha = DateUtils.getFechaSistema();
		return format.format(fecha);
	}

	public static String getDiaActual() {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		Date fecha = DateUtils.getFechaSistema();
		Long diaActual = Long.valueOf(format.format(fecha));
		return diaActual.toString();
	}

	public static String getMesActual() {
		SimpleDateFormat format = new SimpleDateFormat("MM");
		Date fecha = DateUtils.getFechaSistema();
		Long mesActual = Long.valueOf(format.format(fecha));
		return mesActual.toString();
	}

	public static String getMesActualLargo() {
		return DateUtils.getMeses().get(Integer.valueOf(DateUtils.getMesActual()) - 1);
	}

	public static String getAnioActual() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date fecha = DateUtils.getFechaSistema();
		Long anioActual = Long.valueOf(format.format(fecha));
		return anioActual.toString();
	}

	public static String getAnio(Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Long anioActual = Long.valueOf(format.format(fecha));
		return anioActual.toString();
	}

	public static String getHora() {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.HORA_CORTA_FORMATO);
		Date fecha = DateUtils.getFechaSistema();
		return format.format(fecha);
	}

	public static Date getFechaSistema() {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_HORA_FORMATO);
		String enEsteInstante = format.format(Calendar.getInstance().getTime());
		try {
			return format.parse(enEsteInstante);
		} catch (ParseException e) {
			return Calendar.getInstance().getTime();
		}
	}

	public static Date getPrimerDiaAnio() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
		String fecha = "01/01/" + getAnioActual();
		Date hora = null;
		try {
			hora = format.parse(fecha);
		} catch (Exception e) {
			throw e;
		}
		return hora;
	}

	public static Date getPrimerDiaAnio(Date fecha) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
		String fechaString = "01/01/" + getAnio(fecha);
		Date hora = null;
		try {
			hora = format.parse(fechaString);
		} catch (Exception e) {
			throw e;
		}
		return hora;
	}

	public static Date getUltimoDiaAnio() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
		String fecha = "31/12/" + getAnioActual();
		Date hora = null;
		try {
			hora = format.parse(fecha);
		} catch (Exception e) {
			throw e;
		}
		return hora;
	}

	public static Date getUltimoDiaAnio(Date fecha) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_FORMATO);
		String fechaString = "31/12/" + getAnio(fecha);
		Date hora = null;
		try {
			hora = format.parse(fechaString);
		} catch (Exception e) {
			throw e;
		}
		return hora;
	}

	public static int getUltimoDiaMes(int mes, int anioP) {
		int anio = anioP;
		int ultimoDia = 0;
		if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12)
			ultimoDia = 31;
		else if (mes == 4 || mes == 6 || mes == 9 || mes == 11)
			ultimoDia = 30;
		else if (mes == 2) {
			// Detectando a�o bisiesto
			GregorianCalendar calendar = new GregorianCalendar();
			anio = (anio > 0) ? anio : new Integer(DateUtils.getAnioActual());
			if (calendar.isLeapYear(anio))
				ultimoDia = 29;
			else
				ultimoDia = 28;
		}

		return ultimoDia;
	}

	public static List<BigDecimal> getAnios() {
		List<BigDecimal> anios = new LinkedList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		Date fecha = DateUtils.getFechaSistema();
		Long anioActual = Long.valueOf(format.format(fecha));
		Long anioInicio = anioActual
				- ConfigurationBundleConstants.getLong(ConfigurationBundleConstants.CNS_ANIOS_ATRAS_MOSTRAR_SISTEMA);
		for (long i = anioActual; i >= anioInicio; i--) {
			BigDecimal anioAgregar = BigDecimal.valueOf(i);
			anios.add(anioAgregar);
		}
		return anios;
	}

	public static String getRangoAnios() {
		return "c-" + ConfigurationBundleConstants.getLong(ConfigurationBundleConstants.CNS_ANIOS_ATRAS_MOSTRAR_SISTEMA)
				+ ":c+"
				+ ConfigurationBundleConstants.getLong(ConfigurationBundleConstants.CNS_ANIOS_ADELANTE_MOSTRAR_SISTEMA);
	}

	public static List<String> getDias() {
		return Arrays.asList(Dias.getDatos());
	}

	public static List<String> getMeses() {
		return Arrays.asList(Meses.getDatos());
	}

	public static List<String> getTrimestres() {
		return Arrays.asList(Trimestre.getDatos());
	}

	public static List<String> getSemestres() {
		return Arrays.asList(Semestre.getDatos());
	}

	public static List<String> getFormatosFechas() {
		return ConfigurationBundleConstants.getListString(ConfigurationBundleConstants.CNS_FORMATOS_FECHAS);
	}

	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat(DateUtils.FECHA_HORA_FORMATO);
		return format.format(Calendar.getInstance().getTime());
	}

	public static Date getManiana() {
		try {
			Date hoy = DateUtils.formatearAFecha(DateUtils.FECHA_FORMATO, DateUtils.getHoy());
			return sumarDias(hoy, 1);
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date getAyer() {
		try {
			Date hoy = DateUtils.formatearAFecha(DateUtils.FECHA_FORMATO, DateUtils.getHoy());
			return DateUtils.sumarDias(hoy, -1);
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * Calcula la diferencia entre dos fechas. Devuelve el resultado en días,
	 * meses o años según sea el valor del parámetro 'tipo'
	 * 
	 * @param fechaInicio
	 *            Fecha inicial
	 * @param fechaFin
	 *            Fecha final
	 * @param tipo
	 *            0=TotalAños; 1=TotalMeses; 2=TotalDías; 3=MesesDelAnio;
	 *            4=DiasDelMes
	 * @return numero de días, meses o años de diferencia
	 */
	public static long diferenciaEntreFechas(Date fechaFin, Date fechaInicio, int tipo) {
		// Fecha inicio
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(fechaInicio);
		int diaInicio = calendarInicio.get(Calendar.DAY_OF_MONTH);
		int mesInicio = calendarInicio.get(Calendar.MONTH) + 1; // 0 Enero, 11
																// Diciembre
		int anioInicio = calendarInicio.get(Calendar.YEAR);

		// Fecha fin
		Calendar calendarFin = Calendar.getInstance();
		calendarFin.setTime(fechaFin);
		int diaFin = calendarFin.get(Calendar.DAY_OF_MONTH);
		int mesFin = calendarFin.get(Calendar.MONTH) + 1; // 0 Enero, 11
															// Diciembre
		int anioFin = calendarFin.get(Calendar.YEAR);

		int anios = 0;
		int mesesPorAnio = 0;
		int diasPorMes = 0;
		int diasTipoMes = 0;

		//
		// Calculo de días del mes
		//
		if (mesInicio == 2) {
			// Febrero
			if ((anioFin % 4 == 0) && ((anioFin % 100 != 0) || (anioFin % 400 == 0))) {
				// Bisiesto
				diasTipoMes = 29;
			} else {
				// No bisiesto
				diasTipoMes = 28;
			}
		} else if (mesInicio <= 7) {
			// De Enero a Julio los meses pares tienen 30 y los impares 31
			if (mesInicio % 2 == 0) {
				diasTipoMes = 30;
			} else {
				diasTipoMes = 31;
			}
		} else if (mesInicio > 7) {
			// De Julio a Diciembre los meses pares tienen 31 y los impares 30
			if (mesInicio % 2 == 0) {
				diasTipoMes = 31;
			} else {
				diasTipoMes = 30;
			}
		}

		//
		// Calculo de diferencia de año, mes y dia
		//
		if ((anioInicio > anioFin) || (anioInicio == anioFin && mesInicio > mesFin)
				|| (anioInicio == anioFin && mesInicio == mesFin && diaInicio > diaFin)) {
			// La fecha de inicio es posterior a la fecha fin
			return -1;
		} else {
			if (mesInicio <= mesFin) {
				anios = anioFin - anioInicio;
				if (diaInicio <= diaFin) {
					mesesPorAnio = mesFin - mesInicio;
					diasPorMes = diaFin - diaInicio;
				} else {
					if (mesFin == mesInicio) {
						anios = anios - 1;
					}
					mesesPorAnio = (mesFin - mesInicio - 1 + 12) % 12;
					diasPorMes = diasTipoMes - (diaInicio - diaFin);
				}
			} else {
				anios = anioFin - anioInicio - 1;
				if (diaInicio > diaFin) {
					mesesPorAnio = mesFin - mesInicio - 1 + 12;
					diasPorMes = diasTipoMes - (diaInicio - diaFin);
				} else {
					mesesPorAnio = mesFin - mesInicio + 12;
					diasPorMes = diaFin - diaInicio;
				}
			}
		}

		//
		// Totales
		//
		long returnValue = -1;

		switch (tipo) {
		case 0:
			// Total Años
			returnValue = anios;
			break;

		case 1:
			// Total Meses
			returnValue = anios * 12 + mesesPorAnio;
			break;

		case 2:
			// Total Dias (se calcula a partir de los milisegundos por día)
			long millsecsPerDay = 86400000; // Milisegundos al día
			returnValue = (fechaFin.getTime() - fechaInicio.getTime()) / millsecsPerDay;
			break;

		case 3:
			// Meses del año
			returnValue = mesesPorAnio;
			break;

		case 4:
			// Dias del mes
			returnValue = diasPorMes;
			break;

		default:
			break;
		}

		return returnValue;
	}
}
