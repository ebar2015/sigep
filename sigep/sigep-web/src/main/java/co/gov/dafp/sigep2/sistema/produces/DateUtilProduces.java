package co.gov.dafp.sigep2.sistema.produces;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.util.DateUtils;

@RequestScoped
public class DateUtilProduces extends DateUtils {

	public DateUtilProduces() {
		super();
	}

	public DateUtilProduces(String... datos) {
		super(datos);
	}

	private Date fechaHora;

	/**
	 * obtiene los periodos.
	 */
	@PostConstruct
	public void retrieveAllTipoPeriodos() {
		fechaHora = Calendar.getInstance().getTime();
	}

	@Produces
	@Named
	public String anioFormato() {
		return DateUtilProduces.ANIO_FORMATO;
	}

	@Produces
	@Named
	public String mesFormato() {
		return DateUtilProduces.MES_FORMATO;
	}

	@Produces
	@Named
	public String mesLargoFormato() {
		return DateUtilProduces.MES_LARGO_FORMATO;
	}

	@Produces
	@Named
	public String diaFormato() {
		return DateUtilProduces.DIA_FORMATO;
	}

	@Produces
	@Named
	public Date getFechaHora() {
		return fechaHora;
	}

	@Produces
	@Named
	public String getFechaHoraFormato() {
		return FECHA_HORA_FORMATO_VO;
	}

	@Produces
	@Named
	public String getHoraCortaFormato() {
		return HORA_CORTA_FORMATO;
	}

	@Produces
	@Named
	public String getHoraLargaFormato() {
		return HORA_LARGA_FORMATO;
	}

	@Produces
	@Named
	public String getFechaFormato() {
		return FECHA_FORMATO_VO;
	}

	@Produces
	@Named
	public String getPeriodoFormato() {
		return PERIODO_FORMATO;
	}

	@Produces
	@Named
	public static List<String> getDias() {
		return DateUtils.getDias();
	}

	@Produces
	@Named
	public static List<String> getMeses() {
		return DateUtils.getMeses();
	}

	@Produces
	@Named
	public static List<String> getTrimestres() {
		return DateUtils.getTrimestres();
	}

	@Produces
	@Named
	public static List<String> getSemestres() {
		return DateUtils.getSemestres();
	}

	@Produces
	@Named
	public static List<BigDecimal> getAnios() {
		return DateUtils.getAnios();
	}

	@Produces
	@Named
	public static String getRangoAnios() {
		return DateUtils.getRangoAnios();
	}

	@Produces
	@Named
	public static String getHora() {
		return DateUtils.getHora();
	}

	@Produces
	@Named
	public static String getAhora() {
		return DateUtils.getAhora();
	}

	@Produces
	@Named
	public static String getHoy() {
		return DateUtils.getHoy();
	}

	@Produces
	@Named
	public static String getDiaActual() {
		return DateUtils.getDiaActual();
	}

	@Produces
	@Named
	public static String getMesActual() {
		return DateUtils.getMesActual();
	}

	@Produces
	@Named
	public static String getMesActualLargo() {
		return DateUtils.getMesActualLargo();
	}

	@Produces
	@Named
	public static String getAnioActual() {
		return DateUtils.getAnioActual();
	}

	@Produces
	@Named
	public static Date getFechaSistema() {
		return DateUtils.getFechaSistema();
	}

	@Produces
	@Named
	public static String getFechaSistemaString() {
		return formatearACadenaLarga(DateUtils.getFechaSistema());
	}

	@Produces
	@Named
	public static Date getFechaMayorEdad() {
		return sumarDias(DateUtils.getFechaSistema(), -365 * 18);
	}

	@Produces
	@Named
	public static Date getPrimerDiaAnio() throws Exception {
		try {
			return DateUtils.getPrimerDiaAnio();
		} catch (Exception e) {
			throw e;
		}
	}

	@Produces
	@Named
	public static Date getUltimoDiaAnio() throws Exception {
		try {
			return DateUtils.getUltimoDiaAnio();
		} catch (Exception e) {
			throw e;
		}
	}

	@Produces
	@Named
	public static String getSaludo() {
		try {
			return DateUtils.getSaludo();
		} catch (ParseException e) {
			return null;
		}
	}

	@Produces
	@Named
	public static List<String> getFormatosFechas() {
		return DateUtils.getFormatosFechas();
	}

	@Produces
	@Named
	public static Date getManiana() {
		return DateUtils.getManiana();
	}

	@Produces
	@Named
	public static Date getAyer() {
		return DateUtils.getAyer();
	}
}
