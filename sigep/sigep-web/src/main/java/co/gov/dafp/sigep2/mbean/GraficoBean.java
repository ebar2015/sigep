package co.gov.dafp.sigep2.mbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.transaction.NotSupportedException;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.OhlcChartModel;
import org.primefaces.model.chart.OhlcChartSeries;
import org.primefaces.model.chart.PieChartModel;

import co.gov.dafp.sigep2.sistema.BaseBean;
import co.gov.dafp.sigep2.util.Parametro;
import co.gov.dafp.sigep2.util.xml.reporte.config.TipoGrafico;

@Named
@SessionScoped
public class GraficoBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = -8544035447765448265L;

	private List<Parametro> totales;

	private BigDecimal maxValue = BigDecimal.ZERO;
	private BigDecimal minValue = BigDecimal.ZERO;

	public List<Parametro> getTotales() {
		if (totales == null) {
			totales = new LinkedList<Parametro>();
		}
		return totales;
	}

	public void setTotales(List<Parametro> totales) {
		this.totales = totales;
	}

	@Override
	public void validateForm(ComponentSystemEvent event) throws ValidatorException, NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String persist() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void retrieve() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public String update() throws NotSupportedException {
		throw new NotSupportedException();
	}

	@Override
	public void delete() throws NotSupportedException {
		throw new NotSupportedException();
	}

	private LineChartModel lineModel1;
	private LineChartModel lineModel2;
	private LineChartModel zoomModel;
	private CartesianChartModel combinedModel;
	private CartesianChartModel fillToZero;
	private LineChartModel areaModel;
	private BarChartModel barModel;
	private HorizontalBarChartModel horizontalBarModel;
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;
	private DonutChartModel donutModel1;
	private DonutChartModel donutModel2;
	private MeterGaugeChartModel meterGaugeModel1;
	private MeterGaugeChartModel meterGaugeModel2;
	private BubbleChartModel bubbleModel1;
	private BubbleChartModel bubbleModel2;
	private OhlcChartModel ohlcModel;
	private OhlcChartModel ohlcModel2;
	private PieChartModel livePieModel;
	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;
	private LineChartModel multiAxisModel;
	private LineChartModel dateModel;

	public void init() {

	}

	public void init(TipoGrafico tipoGrafico, String titulo) {
		animatedModel1 = null;
		barModel = null;
		horizontalBarModel = null;
		pieModel1 = null;

		if (TipoGrafico.TTL_TIPO_GRAFICO_BARRA_VERTICAL.equals(tipoGrafico)) {
			createBarModel(titulo);
		} else if (TipoGrafico.TTL_TIPO_GRAFICO_BARRA_HORIZONTAL.equals(tipoGrafico)) {
			createHorizontalBarModel(titulo);
		} else if (TipoGrafico.TTL_TIPO_GRAFICO_TORTA.equals(tipoGrafico)) {
			createPieModels(titulo);
		} else if (TipoGrafico.TTL_TIPO_GRAFICO_LINEA_TENDENCIA.equals(tipoGrafico)) {
			createAnimatedModels(titulo);
		}
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
				"Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public LineChartModel getZoomModel() {
		return zoomModel;
	}

	public CartesianChartModel getCombinedModel() {
		return combinedModel;
	}

	public CartesianChartModel getAreaModel() {
		return areaModel;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public MeterGaugeChartModel getMeterGaugeModel1() {
		return meterGaugeModel1;
	}

	public MeterGaugeChartModel getMeterGaugeModel2() {
		return meterGaugeModel2;
	}

	public DonutChartModel getDonutModel1() {
		return donutModel1;
	}

	public DonutChartModel getDonutModel2() {
		return donutModel2;
	}

	public CartesianChartModel getFillToZero() {
		return fillToZero;
	}

	public BubbleChartModel getBubbleModel1() {
		return bubbleModel1;
	}

	public BubbleChartModel getBubbleModel2() {
		return bubbleModel2;
	}

	public OhlcChartModel getOhlcModel() {
		return ohlcModel;
	}

	public OhlcChartModel getOhlcModel2() {
		return ohlcModel2;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}

	public LineChartModel getMultiAxisModel() {
		return multiAxisModel;
	}

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public PieChartModel getLivePieModel() {
		int random1 = (int) (Math.random() * 1000);
		int random2 = (int) (Math.random() * 1000);

		livePieModel.getData().put("Candidate 1", random1);
		livePieModel.getData().put("Candidate 2", random2);

		livePieModel.setTitle("Votes");
		livePieModel.setLegendPosition("ne");

		return livePieModel;
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 90);
		girls.set("2008", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

	private void createLineModels() {
		lineModel1 = initLinearModel();
		lineModel1.setTitle("Linear Chart");
		lineModel1.setLegendPosition("e");
		Axis yAxis = lineModel1.getAxis(AxisType.Y);
		yAxis.setMin(minValue);
		yAxis.setMax(maxValue);

		lineModel2 = initCategoryModel();
		lineModel2.setTitle("Category Chart");
		lineModel2.setLegendPosition("e");
		lineModel2.setShowPointLabels(true);
		lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		yAxis = lineModel2.getAxis(AxisType.Y);
		yAxis.setLabel("Births");
		yAxis.setMin(0);
		yAxis.setMax(200);

		zoomModel = initLinearModel();
		zoomModel.setTitle("Zoom");
		zoomModel.setZoom(true);
		zoomModel.setLegendPosition("e");
		yAxis = zoomModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);
	}

	private void createAreaModel() {
		areaModel = new LineChartModel();

		LineChartSeries boys = new LineChartSeries();
		boys.setFill(true);
		boys.setLabel("Boys");
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		LineChartSeries girls = new LineChartSeries();
		girls.setFill(true);
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 90);
		girls.set("2008", 120);

		areaModel.addSeries(boys);
		areaModel.addSeries(girls);

		areaModel.setTitle("Area Chart");
		areaModel.setLegendPosition("ne");
		areaModel.setStacked(true);
		areaModel.setShowPointLabels(true);

		Axis xAxis = new CategoryAxis("Years");
		areaModel.getAxes().put(AxisType.X, xAxis);
		Axis yAxis = areaModel.getAxis(AxisType.Y);
		yAxis.setLabel("Births");
		yAxis.setMin(0);
		yAxis.setMax(300);
	}

	@SuppressWarnings("unchecked")
	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		List<String> etiquetas = (List<String>) getTotales().get(0).getValue();
		boolean cargoPrimero = false;

		for (Parametro columna : getTotales()) {
			if (!cargoPrimero) {
				cargoPrimero = true;
				continue;
			}
			ChartSeries axisY = new ChartSeries();
			axisY.setLabel(columna.getKey().toString());

			List<BigDecimal> valores = (List<BigDecimal>) columna.getValue();
			int i = 0;
			while (i < etiquetas.size()) {
				String label = etiquetas.get(i);
				BigDecimal value = valores.get(i++);
				axisY.set(label, value);

				if (value.compareTo(maxValue) > 0) {
					maxValue = value;
				}
				if (value.compareTo(minValue) <= 0) {
					minValue = value;
				}
			}

			model.addSeries(axisY);
		}

		return model;
	}

	private HorizontalBarChartModel initHorizontalBarModel() {
		HorizontalBarChartModel model = new HorizontalBarChartModel();

		List<String> etiquetas = (List<String>) getTotales().get(0).getValue();
		boolean cargoPrimero = false;

		for (Parametro columna : getTotales()) {
			if (!cargoPrimero) {
				cargoPrimero = true;
				continue;
			}
			ChartSeries axisY = new ChartSeries();
			axisY.setLabel(columna.getKey().toString());

			List<BigDecimal> valores = (List<BigDecimal>) columna.getValue();
			int i = 0;
			while (i < etiquetas.size()) {
				String label = etiquetas.get(i);
				BigDecimal value = valores.get(i++);
				axisY.set(label, value);

				if (value.compareTo(maxValue) > 0) {
					maxValue = value;
				}
				if (value.compareTo(minValue) <= 0) {
					minValue = value;
				}
			}

			model.addSeries(axisY);
		}

		return model;
	}

	private void createBarModels(String titulo) {

	}

	private void createBarModel(String titulo) {
		minValue = BigDecimal.ZERO;
		maxValue = BigDecimal.ZERO;

		barModel = initBarModel();

		barModel.setTitle(titulo);
		barModel.setLegendPosition("e");

		Axis xAxis = barModel.getAxis(AxisType.X);
		xAxis.setLabel(getTotales().get(0).getKey().toString());

		Axis yAxis = barModel.getAxis(AxisType.Y);
		yAxis.setLabel("Totales");
		yAxis.setMin(minValue);
		yAxis.setMax(maxValue);
	}

	private void createHorizontalBarModel(String titulo) {
		horizontalBarModel = initHorizontalBarModel();

		horizontalBarModel.setTitle(titulo);
		horizontalBarModel.setLegendPosition("e");
		horizontalBarModel.setStacked(true);

		Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
		xAxis.setLabel("Totales");
		xAxis.setMin(minValue);
		xAxis.setMax(maxValue.add(BigDecimal.valueOf(100)));

		Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
		yAxis.setLabel(getTotales().get(0).getKey().toString());
	}

	private void createCombinedModel() {
		combinedModel = new BarChartModel();

		BarChartSeries boys = new BarChartSeries();
		boys.setLabel("Boys");

		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		LineChartSeries girls = new LineChartSeries();
		girls.setLabel("Girls");

		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 135);
		girls.set("2008", 120);

		combinedModel.addSeries(boys);
		combinedModel.addSeries(girls);

		combinedModel.setTitle("Bar and Line");
		combinedModel.setLegendPosition("ne");
		combinedModel.setMouseoverHighlight(false);
		combinedModel.setShowDatatip(false);
		combinedModel.setShowPointLabels(true);
		Axis yAxis = combinedModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

	private void createMultiAxisModel() {
		multiAxisModel = new LineChartModel();

		BarChartSeries boys = new BarChartSeries();
		boys.setLabel("Boys");

		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		LineChartSeries girls = new LineChartSeries();
		girls.setLabel("Girls");
		girls.setXaxis(AxisType.X2);
		girls.setYaxis(AxisType.Y2);

		girls.set("A", 52);
		girls.set("B", 60);
		girls.set("C", 110);
		girls.set("D", 135);
		girls.set("E", 120);

		multiAxisModel.addSeries(boys);
		multiAxisModel.addSeries(girls);

		multiAxisModel.setTitle("Multi Axis Chart");
		multiAxisModel.setMouseoverHighlight(false);

		multiAxisModel.getAxes().put(AxisType.X, new CategoryAxis("Years"));
		multiAxisModel.getAxes().put(AxisType.X2, new CategoryAxis("Period"));

		Axis yAxis = multiAxisModel.getAxis(AxisType.Y);
		yAxis.setLabel("Birth");
		yAxis.setMin(0);
		yAxis.setMax(200);

		Axis y2Axis = new LinearAxis("Number");
		y2Axis.setMin(0);
		y2Axis.setMax(200);

		multiAxisModel.getAxes().put(AxisType.Y2, y2Axis);
	}

	private void createOhlcModels() {
		createOhlcModel1();
		createOhlcModel2();
	}

	private void createOhlcModel1() {
		ohlcModel = new OhlcChartModel();

		ohlcModel.add(new OhlcChartSeries(2007, 143.82, 144.56, 136.04, 136.97));
		ohlcModel.add(new OhlcChartSeries(2008, 138.7, 139.68, 135.18, 135.4));
		ohlcModel.add(new OhlcChartSeries(2009, 143.46, 144.66, 139.79, 140.02));
		ohlcModel.add(new OhlcChartSeries(2010, 140.67, 143.56, 132.88, 142.44));
		ohlcModel.add(new OhlcChartSeries(2011, 136.01, 139.5, 134.53, 139.48));
		ohlcModel.add(new OhlcChartSeries(2012, 124.76, 135.9, 124.55, 135.81));
		ohlcModel.add(new OhlcChartSeries(2013, 123.73, 129.31, 121.57, 122.5));

		ohlcModel.setTitle("OHLC Chart");
		ohlcModel.getAxis(AxisType.X).setLabel("Year");
		ohlcModel.getAxis(AxisType.Y).setLabel("Price Change $K/Unit");
	}

	private void createOhlcModel2() {
		ohlcModel2 = new OhlcChartModel();

		for (int i = 1; i < 41; i++) {
			ohlcModel2.add(new OhlcChartSeries(i, Math.random() * 80 + 80, Math.random() * 50 + 110,
					Math.random() * 20 + 80, Math.random() * 80 + 80));
		}

		ohlcModel2.setTitle("Candlestick");
		ohlcModel2.setCandleStick(true);
		ohlcModel2.getAxis(AxisType.X).setLabel("Sector");
		ohlcModel2.getAxis(AxisType.Y).setLabel("Index Value");
	}

	private void createBubbleModels() {
		bubbleModel1 = initBubbleModel();
		bubbleModel1.setTitle("Bubble Chart");
		bubbleModel1.getAxis(AxisType.X).setLabel("Price");
		Axis yAxis = bubbleModel1.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(250);
		yAxis.setLabel("Labels");

		bubbleModel2 = initBubbleModel();
		bubbleModel2.setTitle("Custom Options");
		bubbleModel2.setShadow(false);
		bubbleModel2.setBubbleGradients(true);
		bubbleModel2.setBubbleAlpha(0.8);
		bubbleModel2.getAxis(AxisType.X).setTickAngle(-50);
		yAxis = bubbleModel2.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(250);
		yAxis.setTickAngle(50);
	}

	private BubbleChartModel initBubbleModel() {
		BubbleChartModel model = new BubbleChartModel();

		model.add(new BubbleChartSeries("Acura", 70, 183, 55));
		model.add(new BubbleChartSeries("Alfa Romeo", 45, 92, 36));
		model.add(new BubbleChartSeries("AM General", 24, 104, 40));
		model.add(new BubbleChartSeries("Bugatti", 50, 123, 60));
		model.add(new BubbleChartSeries("BMW", 15, 89, 25));
		model.add(new BubbleChartSeries("Audi", 40, 180, 80));
		model.add(new BubbleChartSeries("Aston Martin", 70, 70, 48));

		return model;
	}

	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();

		List<String> etiquetas = (List<String>) getTotales().get(0).getValue();
		boolean cargoPrimero = false;

		for (Parametro columna : getTotales()) {
			if (!cargoPrimero) {
				cargoPrimero = true;
				continue;
			}
			LineChartSeries axisY = new LineChartSeries();
			axisY.setLabel(columna.getKey().toString());

			List<BigDecimal> valores = (List<BigDecimal>) columna.getValue();
			int i = 0;
			while (i < etiquetas.size()) {
				String label = etiquetas.get(i);
				BigDecimal value = valores.get(i++);
				axisY.set(label, value);

				if (value.compareTo(maxValue) > 0) {
					maxValue = value;
				}
				if (value.compareTo(minValue) <= 0) {
					minValue = value;
				}
			}

			model.addSeries(axisY);
		}

		return model;
	}

	private void createPieModels(String titulo) {
		createPieModel1(titulo);
	}

	private void createPieModel1(String titulo) {
		pieModel1 = new PieChartModel();

		List<String> etiquetas = (List<String>) getTotales().get(0).getValue();
		boolean cargoPrimero = false;

		for (Parametro columna : getTotales()) {
			if (!cargoPrimero) {
				cargoPrimero = true;
				continue;
			}
			ChartSeries axisY = new ChartSeries();
			axisY.setLabel(columna.getKey().toString());

			List<BigDecimal> valores = (List<BigDecimal>) columna.getValue();
			int i = 0;
			while (i < etiquetas.size()) {
				String label = etiquetas.get(i);
				BigDecimal value = valores.get(i++);
				pieModel1.set(label, value);

				if (value.compareTo(maxValue) > 0) {
					maxValue = value;
				}
				if (value.compareTo(minValue) <= 0) {
					minValue = value;
				}
			}
		}

		pieModel1.setTitle("Torta");
		pieModel1.setLegendPosition("w");
		pieModel1.setShadow(false);
	}

	private void createPieModel2() {
		pieModel2 = new PieChartModel();

		pieModel2.set("Brand 1", 540);
		pieModel2.set("Brand 2", 325);
		pieModel2.set("Brand 3", 702);
		pieModel2.set("Brand 4", 421);

		pieModel2.setTitle("Custom Pie");
		pieModel2.setLegendPosition("e");
		pieModel2.setFill(false);
		pieModel2.setShowDataLabels(true);
		pieModel2.setDiameter(150);
		pieModel2.setShadow(false);
	}

	private void createDonutModels() {
		donutModel1 = initDonutModel();
		donutModel1.setTitle("Donut Chart");
		donutModel1.setLegendPosition("w");

		donutModel2 = initDonutModel();
		donutModel2.setTitle("Custom Options");
		donutModel2.setLegendPosition("e");
		donutModel2.setSliceMargin(5);
		donutModel2.setShowDataLabels(true);
		donutModel2.setDataFormat("value");
		donutModel2.setShadow(false);
	}

	private DonutChartModel initDonutModel() {
		DonutChartModel model = new DonutChartModel();

		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
		circle1.put("Brand 1", 150);
		circle1.put("Brand 2", 400);
		circle1.put("Brand 3", 200);
		circle1.put("Brand 4", 10);
		model.addCircle(circle1);

		Map<String, Number> circle2 = new LinkedHashMap<String, Number>();
		circle2.put("Brand 1", 540);
		circle2.put("Brand 2", 125);
		circle2.put("Brand 3", 702);
		circle2.put("Brand 4", 421);
		model.addCircle(circle2);

		Map<String, Number> circle3 = new LinkedHashMap<String, Number>();
		circle3.put("Brand 1", 40);
		circle3.put("Brand 2", 325);
		circle3.put("Brand 3", 402);
		circle3.put("Brand 4", 421);
		model.addCircle(circle3);

		return model;
	}

	private void createLivePieModel() {
		livePieModel = new PieChartModel();

		livePieModel.set("Candidate 1", 540);
		livePieModel.set("Candidate 2", 325);
	}

	private void createFillToZero() {
		fillToZero = new CartesianChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("4, -3, 3, 6, 2, -2", 0);

		fillToZero.addSeries(series1);
	}

	@SuppressWarnings("serial")
	private MeterGaugeChartModel initMeterGaugeModel() {
		List<Number> intervals = new ArrayList<Number>() {
			{
				add(20);
				add(50);
				add(120);
				add(220);
			}
		};

		return new MeterGaugeChartModel(140, intervals);
	}

	private void createMeterGaugeModels() {
		meterGaugeModel1 = initMeterGaugeModel();
		meterGaugeModel1.setTitle("MeterGauge Chart");
		meterGaugeModel1.setGaugeLabel("km/h");
		meterGaugeModel1.setGaugeLabelPosition("bottom");

		meterGaugeModel2 = initMeterGaugeModel();
		meterGaugeModel2.setTitle("Custom Options");
		meterGaugeModel2.setSeriesColors("66cc66,93b75f,E7E658,cc6666");
		meterGaugeModel2.setGaugeLabel("km/h");
		meterGaugeModel2.setGaugeLabelPosition("bottom");
		meterGaugeModel2.setShowTickLabels(false);
		meterGaugeModel2.setLabelHeightAdjust(110);
		meterGaugeModel2.setIntervalOuterRadius(100);
	}

	private void createAnimatedModels(String titulo) {
		lineModel1 = initLinearModel();
		lineModel1.setTitle(titulo);
		lineModel1.setLegendPosition("e");
		Axis yAxis = lineModel1.getAxis(AxisType.Y);
		yAxis.setMin(minValue);
		yAxis.setMax(maxValue);
	}

	private void createDateModel() {
		dateModel = new LineChartModel();
		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Series 1");

		series1.set("2014-01-01", 51);
		series1.set("2014-01-06", 22);
		series1.set("2014-01-12", 65);
		series1.set("2014-01-18", 74);
		series1.set("2014-01-24", 24);
		series1.set("2014-01-30", 51);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Series 2");

		series2.set("2014-01-01", 32);
		series2.set("2014-01-06", 73);
		series2.set("2014-01-12", 24);
		series2.set("2014-01-18", 12);
		series2.set("2014-01-24", 74);
		series2.set("2014-01-30", 62);

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);

		dateModel.setTitle("Zoom for Details");
		dateModel.setZoom(true);
		dateModel.getAxis(AxisType.Y).setLabel("Values");
		DateAxis axis = new DateAxis("Dates");
		axis.setTickAngle(-50);
		axis.setMax("2014-02-01");
		axis.setTickFormat("%b %#d, %y");

		dateModel.getAxes().put(AxisType.X, axis);
	}

}
