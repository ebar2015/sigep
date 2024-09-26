package co.gov.dafp.sigep2.sistema.produces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import co.gov.dafp.sigep2.util.xml.reporte.config.CalificadorComparacion;
import co.gov.dafp.sigep2.util.xml.reporte.config.SeparadorCsv;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoBandeja;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoPlantilla;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoCruceFecha;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDato;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoDatoReporte;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoGrafico;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoRegistro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoValidacion;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SessionScoped
public class XmlProduces implements Serializable {
	private static final long serialVersionUID = 2636248278563621752L;

	private TipoPlantilla tipoCargue;

	@Named
	@Produces
	public TipoPlantilla getTipoCargue() {
		return tipoCargue;
	}

	public void setTipoCargue(TipoPlantilla tipoCargue) {
		this.tipoCargue = tipoCargue;
	}

	@Named
	@Produces
	public List<CalificadorComparacion> getCalificadoresComparacion() {
		return Arrays.asList(CalificadorComparacion.values());
	}

	@Named
	@Produces
	public List<TipoCruceFecha> getTiposCruceFecha() {
		return Arrays.asList(TipoCruceFecha.values());
	}

	@Named
	@Produces
	public List<TipoBandeja> getTiposBandeja() {
		return Arrays.asList(TipoBandeja.values());
	}

	@Named
	@Produces
	public List<TipoDato> getTiposDato() {
		List<TipoDato> tiposDato = new LinkedList<TipoDato>();
		for (TipoDato tipoDato : TipoDato.values()) {
			tiposDato.add(tipoDato);
		}
		List<TipoDatoReporte> tiposDatoReporte = Arrays.asList(TipoDatoReporte.values());
		if (!TipoPlantilla.REPORTE.equals(tipoCargue)) {
			for (TipoDato tipoDato : TipoDato.values()) {
				for (TipoDatoReporte tipoDatoReporte : tiposDatoReporte) {
					if (tipoDato.value().equals(tipoDatoReporte.value())) {
						tiposDato.remove(tipoDato);
						break;
					}
				}
			}
		}
		return tiposDato;
	}

	@Named
	@Produces
	public List<TipoRegistro> getTiposRegistro() {
		return Arrays.asList(TipoRegistro.values());
	}

	@Named
	@Produces
	public List<TipoValidacion> getTiposValidacion() {
		return Arrays.asList(TipoValidacion.values());
	}

	@Named
	@Produces
	public List<SeparadorCsv> getSeparadoresCSV() {
		return Arrays.asList(SeparadorCsv.values());
	}

	@Named
	@Produces
	public List<TipoPlantilla> getTiposCargue() {
		return Arrays.asList(TipoPlantilla.values());
	}

	@Named
	@Produces
	public List<TipoGrafico> getTiposGraficos() {
		return Arrays.asList(TipoGrafico.values());
	}
}
