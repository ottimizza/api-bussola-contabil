package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.domain.dtos.ChartOptionDTO;
import br.com.ottimizza.dashboard.domain.dtos.WebChartDTO;
import br.com.ottimizza.dashboard.models.ChartOption;
import br.com.ottimizza.dashboard.models.KpiDetail;

@Service
public class WebChartsService {

	@Inject
	KpiDetailService kpiDetailService;

	@Inject
	KpiService kpiService;

	@Inject
	ChartOptionService chartOptionService;

	public static final String CHART_TYPE_COLUMN = "ColumnChart";
	public static final String CHART_TYPE_LINE = "LineChart";
	public static final String CHART_TYPE_PIE = "PieChart";
	public static final String CHART_TYPE_BAR = "BarChart";
	public static final String CHART_TYPE_GAUGE = "Gauge";

	public JSONObject getDataToCharts(List<String> cnpj, Short param, String kind) {

		JSONObject records = new JSONObject();
		if (param > 81 || param < 1)
			return records;
		if (kind.equals("") || kind == null)
			kind = "0";

		List<KpiDetail> kpiList = new ArrayList<KpiDetail>();

		try {
			kpiList = kpiDetailService.findByListCNPJAndGraphType(cnpj, param, kind);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String rn = "\r\n";
		String s = "";

		StringBuffer sbGraphType = new StringBuffer();

		// tratamento de JSON
		if (!kpiList.isEmpty()) {
			boolean openC = false;
			String cName = "";
			String cCnpj = "";

			for (KpiDetail kpiDetail : kpiList) {
				int graphType = 0;
				try {
					graphType = (int) kpiDetail.getKpiID().getGraphType();
				} catch (Exception e) {
					e.printStackTrace();
					graphType = 0;
				}

				if (cName.equals(""))
					cName = kpiDetail.getKpiID().getCompany().getName();
				if (cCnpj.equals(""))
					cCnpj = kpiDetail.getKpiID().getCompany().getCnpj();

				if (!cName.equals(""))
					records.put("companyName", cName);
				if (!cCnpj.equals(""))
					records.put("cnpj", cCnpj);

				if (graphType == param) {

					if (sbGraphType.length() == 0 || sbGraphType.equals("") || sbGraphType == null) {
						openC = true;

						records.put("title", kpiDetail.getKpiID().getTitle());
						records.put("subtitle", kpiDetail.getKpiID().getSubtitle());
						records.put("visible", kpiDetail.getKpiID().getVisible());

						records.put("graphType", kpiDetail.getKpiID().getGraphType());
						String k = String.valueOf(kpiDetail.getKpiID().getGraphType());
						records.put("div", k);

						if (graphType == 7 || graphType == 12)
							records.put("value", kpiDetail.getValorKPI());

						s = "','";
						sbGraphType.append("					['");
						sbGraphType.append(kpiDetail.getKpiID().getSubtitle());
						sbGraphType.append(s);

						sbGraphType.append(kpiDetail.getKpiID().getLabel());
						if (!kpiDetail.getKpiID().getLabel2().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getKpiID().getLabel2());
						}
						if (!kpiDetail.getKpiID().getLabel3().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getKpiID().getLabel3());
						}
						if (!kpiDetail.getKpiID().getLabel4().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getKpiID().getLabel4());
						}

						sbGraphType.append("'");
					}

					s = ",";

					sbGraphType.append("],");
					sbGraphType.append(rn);

					sbGraphType.append("					['");
					if (graphType != 7)
						sbGraphType.append(kpiDetail.getXBinding()); // retirado getColumnX() pra nao ir dia
					sbGraphType.append("'").append(s);
					if (graphType == 7) {

						double vld = kpiDetail.getValorKPI();
						int valor = (int) vld;
						sbGraphType.append(valor);

					} else {
						String[] arrayVal = kpiDetail.getValorStringArray().split(";");
						sbGraphType.append(arrayVal[0].toString());

						if (arrayVal.length > 2) {
							sbGraphType.append(s);
							sbGraphType.append(arrayVal[2].toString());

						}
						if (arrayVal.length > 4) {
							sbGraphType.append(s);
							sbGraphType.append(arrayVal[4].toString());
						}
						if (arrayVal.length > 6) {
							sbGraphType.append(s);
							sbGraphType.append(arrayVal[6].toString());
						}
					}
				}
			}
			if (openC)
				sbGraphType.append("]").append(rn);
		}
		if (sbGraphType.toString().length() == 0) {
			records.put("emptyTable", true);
		} else {
			records.put("emptyTable", false);
		}

		records.put("data", sbGraphType.toString());

		// definicao do tipo de grafico a ser gerado
		if (param == 1)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 2)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 3)
			records.put("chartType", CHART_TYPE_PIE);
		if (param == 4)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 5)
			records.put("chartType", CHART_TYPE_LINE);
		if (param == 6)
			records.put("chartType", CHART_TYPE_LINE);
		if (param == 7)
			records.put("chartType", CHART_TYPE_GAUGE); // etiqueta
		if (param == 8)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 9)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 10)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 11)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param == 12)
			records.put("chartType", CHART_TYPE_GAUGE); // etiqueta
		if (param == 13)
			records.put("chartType", CHART_TYPE_PIE); // pie
		if (param == 14)
			records.put("chartType", CHART_TYPE_PIE); // pie
		if (param == 15)
			records.put("chartType", CHART_TYPE_COLUMN); // colun %
		if (param == 16)
			records.put("chartType", CHART_TYPE_COLUMN); // colun %
		if (param == 17)
			records.put("chartType", CHART_TYPE_COLUMN); // colun %
		if (param == 18)
			records.put("chartType", CHART_TYPE_LINE); // line (4)
		if (param == 19)
			records.put("chartType", CHART_TYPE_LINE); // line (4)
		if (param == 20)
			records.put("chartType", CHART_TYPE_LINE); // line (4)
		if (param == 21)
			records.put("chartType", CHART_TYPE_COLUMN);
		if (param > 21)
			records.put("chartType", CHART_TYPE_LINE);

		return records;
	}

	public JSONObject getDataToChartsNovo(WebChartDTO chart) {

		WebChartDTO webChart = chart;
		JSONObject records = new JSONObject();
		BigInteger kpiId = webChart.getId();
		String kpiAliasS = webChart.getKpiAlias();
		int kpiAlias = Integer.parseInt(kpiAliasS);
		String title = webChart.getTitle();
		String subtitle = webChart.getSubtitle();
		String chartType = webChart.getChartType();
		List<KpiDetail> kpiDetailList = kpiDetailService.findByKpiId(kpiId);

		String rn = "\r\n";
		String s = "";

		StringBuffer sbGraphType = new StringBuffer();

		// tratamento de JSON
		if (webChart != null) {
			boolean openC = false;
			String cName = "";
			String cCnpj = "";

			for (KpiDetail kpiD : kpiDetailList) {

				if (cName.equals(""))
					cName = webChart.getCompanyName();
				if (cCnpj.equals(""))
					cCnpj = webChart.getCnpj();

				if (!cName.equals(""))
					records.put("companyName", cName);
				if (!cCnpj.equals(""))
					records.put("cnpj", cCnpj);

				if (sbGraphType.length() == 0 || sbGraphType.equals("") || sbGraphType == null) {
					openC = true;

					if (chartType.contains("Pie") || chartType.contains("Donut") || chartType.contains("Card")) {
						
						String titleSubtitle = title + " " + subtitle;
						records.put("title", titleSubtitle);
					
					} else {
						
						records.put("title", title);
						records.put("subtitle", subtitle);
					}

					records.put("visible", webChart.isVisible());

					records.put("div", kpiAliasS);

					if (kpiAlias == 7 || kpiAlias == 12) {
						records.put("value", kpiD.getValorKPI());
					}

					s = "','";
					sbGraphType.append("					['");
					sbGraphType.append(webChart.getSubtitle());
					sbGraphType.append(s);

					String[] labels = webChart.getLabelArray().split(";");

					sbGraphType.append(labels[0]);
					if (labels.length > 1 && labels[1] != "") {
						sbGraphType.append(s);
						sbGraphType.append(labels[1]);
					}
					if (labels.length > 2 && !labels[2].equals("")) {
						sbGraphType.append(s);
						sbGraphType.append(labels[2]);
					}
					if (labels.length > 3 && !labels[3].equals("")) {
						sbGraphType.append(s);
						sbGraphType.append(labels[3]);
					}

					sbGraphType.append("'");
				}

				s = ",";

				sbGraphType.append("],");
				sbGraphType.append(rn);

				sbGraphType.append("					['");
				if (kpiAlias != 7)
					sbGraphType.append(kpiD.getXBinding()); // retirado getColumnX() pra nao ir dia
				sbGraphType.append("'").append(s);
				if (kpiAlias == 7) {

					double vld = kpiD.getValorKPI();
					int valorKpi = (int) vld;
					sbGraphType.append(valorKpi);

				} else {
					String[] arrayVal = kpiD.getValorStringArray().split(";");
					sbGraphType.append(arrayVal[0].toString());

					if (arrayVal.length > 2) {
						sbGraphType.append(s);
						sbGraphType.append(arrayVal[2].toString());

					}
					if (arrayVal.length > 4) {
						sbGraphType.append(s);
						sbGraphType.append(arrayVal[4].toString());
					}
					if (arrayVal.length > 6) {
						sbGraphType.append(s);
						sbGraphType.append(arrayVal[6].toString());
					}
				}
			}
			if (openC)
				sbGraphType.append("]").append(rn);
		}
		if (sbGraphType.toString().length() == 0) {
			records.put("emptyTable", true);
		} else {
			records.put("emptyTable", false);
		}

		records.put("data", sbGraphType.toString());

		records.put("chartType", chartType);

		return records;
	}

	public String getTable(JSONObject dataToCharts) {

		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		int k = Integer.parseInt(dataToCharts.optString("div"));

		sb.append("				let dataTable").append(k).append(" = google.visualization.arrayToDataTable([")
				.append(rn);
		sb.append(dataToCharts.optString("data"));
		sb.append("				]);").append(rn).append(rn);

		return sb.toString();
	}

	public String putOptions(JSONObject dataToCharts) {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		int k = Integer.parseInt(dataToCharts.optString("div"));

		sb.append("				let options").append(k).append(" = Object.assign({}, optionsTo")
				.append(dataToCharts.optString("chartType")).append(");").append(rn);
		sb.append("				options").append(k).append(".title = '").append(dataToCharts.optString("title"))
				.append("';").append(rn);

		return sb.toString();
	}

	public String putChart(JSONObject dataToCharts) {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		int k = Integer.parseInt(dataToCharts.optString("div"));
		String div = "charts" + k;
		String chartType = dataToCharts.optString("chartType");

		chartType = chartType.replace("Stacked", "");
		chartType = chartType.replace("Multiple", "");
		chartType = chartType.replace("Donut", "Pie");

		sb.append("				let grafico").append(k).append(" = new google.visualization.").append(chartType)
				.append("(").append(rn);
		sb.append("					document.getElementById('").append(div).append("'));").append(rn);
		sb.append("				grafico").append(k).append(".draw(dataTable").append(k).append(", options").append(k)
				.append(");").append(rn).append(rn);
		return sb.toString();
	}

	public String getbasicOptions(boolean isNew) {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("options = {").append(rn);
		sb.append("					legend: 'none',").append(rn);
		sb.append("					titleTextStyle: {").append(rn);
		sb.append("						color: '#464444',").append(rn);
		sb.append("						fontSize: 16,").append(rn);
		sb.append("						bold: false,").append(rn);
		sb.append("					},").append(rn);
		sb.append("					vAxis: {").append(rn);
		sb.append("						gridlines: {").append(rn);
		sb.append("							count:0,").append(rn);
		sb.append("							color:'transparent'").append(rn);
		sb.append("        				},").append(rn);
		sb.append("						format: 'short',").append(rn);
		sb.append("    					minValue: 0").append(rn);
		sb.append("    				},").append(rn);
		sb.append("    				hAxis: {").append(rn);
		sb.append("						textStyle: { fontSize: 8 }").append(rn);
		sb.append("					},").append(rn);
		sb.append("					colors: cores").append(rn);
		sb.append("				};").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToBarChart = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToPieChart = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToLineChart = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToColumnChart = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToGauge = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToDonutChart = Object.assign({},options);").append(rn);
		sb.append("				");
		if (isNew)
			sb.append("let ");
		sb.append("optionsToLineChartMultiple = Object.assign({},options);").append(rn);
		sb.append("				optionsToColumnChart.bar = {groupWidth: '80%'};").append(rn);
		sb.append("				optionsToLineChart.curveType = 'function'").append(rn);
		sb.append("				optionsToPieChart.legend = {position: 'bottom'};").append(rn);
		sb.append("				optionsToPieChart.pieSliceTextStyle = {fontSize: 9};").append(rn);
		sb.append("				optionsToPieChart.pieStartAngle = 90;").append(rn);
		sb.append("				optionsToGauge.minorTicks = 99;").append(rn).append(rn);

		return sb.toString();
	}

	public String getOptions() {
		StringBuffer sb = new StringBuffer();
		List<ChartOption> charts = chartOptionService.findAll(ChartOptionDTO.builder().id(null).build());
		String rn = "\r\n";

		for (ChartOption chart : charts) {
			String ct = chart.getChartType();
			String co = chart.getOption();
			sb.append("				");
			sb.append("let ");
			sb.append("options").append(ct).append(" = ").append(co).append(";").append(rn);
			sb.append("				");
			sb.append("let ");
			sb.append("optionsTo").append(ct).append(" = Object.assign({},options").append(ct).append(");").append(rn);

		}
		return sb.toString();
	}

	// usado nos graficos gerados compartilhados apartir do celular
	public String getBasicStyle() {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		sb.append("		<style>").append(rn);
		sb.append("			* {").append(rn);
		sb.append("				font-family: 'IBM Plex Sans', sans-serif;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			p {").append(rn);
		sb.append("				font-size: 16px;").append(rn);
		sb.append("				color: #464444;").append(rn);
		sb.append("			}").append(rn);
		sb.append("         p.dataFinal {").append(rn);
		sb.append("				font-size: 16px;").append(rn);
		sb.append("             text-align: center;").append(rn);
		sb.append("				color: #464444;").append(rn);
		sb.append("             visibility: visible; ").append(rn);
		sb.append("         }").append(rn);
		sb.append("         p.data {").append(rn);
		sb.append("             visibility: hidden; ").append(rn);
		sb.append("         }").append(rn);
		sb.append("			#container div {").append(rn);
		sb.append("				margin: auto;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			/*#container div div div {").append(rn);
		sb.append("				margin-left: 15px;").append(rn);
		sb.append("			}*/").append(rn);
		sb.append("			@media print {").append(rn);
		sb.append("				#container div {").append(rn);
		sb.append("					margin: none;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#separator{").append(rn);
		sb.append("					page-break-before: always;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#charts7{").append(rn);
		sb.append("					border-style: solid;").append(rn);
		sb.append("					border-color: #4285f4;").append(rn);
		sb.append("					border-width: 1px;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#charts12{").append(rn);
		sb.append("					border-style: solid;").append(rn);
		sb.append("					border-color: darkred;").append(rn);
		sb.append("					border-width: 1px;").append(rn);
		sb.append("				}").append(rn);
		sb.append("        		p.data {").append(rn);
		sb.append("					font-size: 16px;").append(rn);
		sb.append("             	text-align: center;").append(rn);
		sb.append("					color: #464444;").append(rn);
		sb.append("            		visibility: visible; ").append(rn);
		sb.append("         	}").append(rn);
		sb.append("         	p.dataFinal {").append(rn);
		sb.append("             visibility: hidden; ").append(rn);
		sb.append("        		}").append(rn);
		sb.append("			}").append(rn);
		sb.append("		    #container{").append(rn);
		sb.append("				align-items: center;").append(rn);
		sb.append("				width: 1200px;").append(rn);
		sb.append("				margin: auto;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			.chart {").append(rn);
		sb.append("		        width: 80%;").append(rn);
		sb.append("		        height: 400px;").append(rn);
		sb.append("		    }").append(rn);
		sb.append("			#epi{").append(rn);
		sb.append("				padding: 10px;").append(rn);
		sb.append("				text-align: center;").append(rn);
		sb.append("				width: 100%;").append(rn);
		sb.append("				height: 150px;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7, #charts12 {").append(rn);
		sb.append("				width: 69%;").append(rn);
		sb.append("				height: 100%;").append(rn);
		sb.append("				border-radius: 20px;").append(rn);
		sb.append("				border-style: solid;").append(rn);
		sb.append("				border-width: 1px;").append(rn);
		sb.append("				margin-left: 13%;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7 {").append(rn);
		sb.append("				border-color: #4285f4;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts12 {").append(rn);
		sb.append("				border-color: darkred;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7 #endo {").append(rn);
		sb.append("				font-size: 2em;").append(rn);
		sb.append("				color: #4285f4;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts12 #endo {").append(rn);
		sb.append("				font-size: 2em;").append(rn);
		sb.append("				color: darkred;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#head {").append(rn);
		sb.append("				width: fit-content;").append(rn);
		sb.append("				display: flex;").append(rn);
		sb.append("				margin: 0;").append(rn);
		sb.append("				padding: 30px;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#container #head div{").append(rn);
		sb.append("				display: grid;").append(rn);
		sb.append("				padding: 10px 10px 10px 10px;").append(rn);
		sb.append("				width: 100%;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#container #head #logo {").append(rn);
		sb.append("				max-height: 100px;").append(rn);
		sb.append("				max-width: 200px;").append(rn);
		sb.append("			}").append(rn);

		return sb.toString();
	}

	// usado nos graficos gerados para bussolaWEB 1.0
	public String getBasicStyleWeb() {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		sb.append("		<style>").append(rn);
		sb.append("			* {").append(rn);
		sb.append("				font-family: 'IBM Plex Sans', sans-serif;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			p {").append(rn);
		sb.append("				font-size: 16px;").append(rn);
		sb.append("				color: #464444;").append(rn);
		sb.append("			}").append(rn);
		sb.append("         p.dataFinal {").append(rn);
		sb.append("				font-size: 16px;").append(rn);
		sb.append("             text-align: center;").append(rn);
		sb.append("				color: #464444;").append(rn);
		sb.append("             visibility: visible; ").append(rn);
		sb.append("         }").append(rn);
		sb.append("         p.data {").append(rn);
		sb.append("             visibility: hidden; ").append(rn);
		sb.append("         }").append(rn);
		sb.append("			#container div {").append(rn);
		sb.append("				margin: auto;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			/*#container div div div {").append(rn);
		sb.append("				margin-left: 15px;").append(rn);
		sb.append("			}*/").append(rn);
		sb.append("			@media print {").append(rn);
		sb.append("				#container div {").append(rn);
		sb.append("					margin: none;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#separator{").append(rn);
		sb.append("					page-break-before: always;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#charts7{").append(rn);
		sb.append("					border-style: solid;").append(rn);
		sb.append("					border-color: #4285f4;").append(rn);
		sb.append("					border-width: 1px;").append(rn);
		sb.append("				}").append(rn);
		sb.append("				#charts12{").append(rn);
		sb.append("					border-style: solid;").append(rn);
		sb.append("					border-color: darkred;").append(rn);
		sb.append("					border-width: 1px;").append(rn);
		sb.append("				}").append(rn);
		sb.append("        		p.data {").append(rn);
		sb.append("					font-size: 16px;").append(rn);
		sb.append("             	text-align: center;").append(rn);
		sb.append("					color: #464444;").append(rn);
		sb.append("            		visibility: visible; ").append(rn);
		sb.append("         	}").append(rn);
		sb.append("         	p.dataFinal {").append(rn);
		sb.append("             visibility: hidden; ").append(rn);
		sb.append("        		}").append(rn);
		sb.append("			}").append(rn);
		sb.append("		    #container{").append(rn);
		sb.append("				align-items: center;").append(rn);
		sb.append("				width: 1200px;").append(rn);
		sb.append("				margin: auto;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			.chart {").append(rn);
		sb.append("		        width: 80%;").append(rn);
		sb.append("		        height: 400px;").append(rn);
		sb.append("		    }").append(rn);
		sb.append("			#epi{").append(rn);
		sb.append("				padding: 10px;").append(rn);
		sb.append("				text-align: center;").append(rn);
		sb.append("				width: 100%;").append(rn);
		sb.append("				height: 150px;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7, #charts12 {").append(rn);
		sb.append("				width: 69%;").append(rn);
		sb.append("				height: 100%;").append(rn);
		sb.append("				border-radius: 20px;").append(rn);
		sb.append("				border-style: solid;").append(rn);
		sb.append("				border-width: 1px;").append(rn);
		sb.append("				margin-left: 13%;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7 {").append(rn);
		sb.append("				border-color: #4285f4;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts12 {").append(rn);
		sb.append("				border-color: darkred;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts7 #endo {").append(rn);
		sb.append("				font-size: 2em;").append(rn);
		sb.append("				color: #4285f4;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#charts12 #endo {").append(rn);
		sb.append("				font-size: 2em;").append(rn);
		sb.append("				color: darkred;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#head {").append(rn);
		sb.append("				width: fit-content;").append(rn);
		sb.append("				display: flex;").append(rn);
		sb.append("				margin: 0;").append(rn);
		sb.append("				padding: 30px;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#container #head div{").append(rn);
		sb.append("				display: grid;").append(rn);
		sb.append("				padding: 10px 10px 10px 10px;").append(rn);
		sb.append("				width: 100%;").append(rn);
		sb.append("			}").append(rn);
		sb.append("			#container #head #logo {").append(rn);
		sb.append("				max-height: 100px;").append(rn);
		sb.append("				max-width: 200px;").append(rn);
		sb.append("			}").append(rn);

		return sb.toString();
	}

}
