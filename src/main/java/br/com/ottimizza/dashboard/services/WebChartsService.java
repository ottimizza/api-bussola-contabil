package br.com.ottimizza.dashboard.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.KpiDetail;

@Service
public class WebChartsService {

	@Inject
	KpiDetailService kpiDetailService;
	
	public static final String CHART_TYPE_COLUMN = "ColumnChart";
	public static final String CHART_TYPE_LINE = "LineChart";
	public static final String CHART_TYPE_PIE = "PieChart";
	public static final String CHART_TYPE_BAR = "BarChart";
	public static final String CHART_TYPE_GAUGE = "Gauge";
	
	
	public JSONObject getDataToCharts(List<String> cnpj, int param) {

		JSONObject records = new JSONObject();
		if(param > 21 || param  < 1) return records;
		
        List<KpiDetail> kpiList = new ArrayList<KpiDetail>();
        try {kpiList = kpiDetailService.findByListCNPJ(cnpj);}
        catch (Exception e) { e.printStackTrace();}
        
        String rn = "\r\n";
        String s = "";
        
        StringBuffer sbGraphType = new StringBuffer();
        
        // tratamento de JSON
		if(!kpiList.isEmpty()) {
			boolean openC = false;
        	for (KpiDetail kpiDetail : kpiList) {
        		
        		int graphType = 0;
        		try {graphType = (int) kpiDetail.getKpiID().getGraphType();}
        		catch (Exception e) { 
        			e.printStackTrace();
        			graphType = 0;
    			}
        		
				if(graphType == param){
					
					if(sbGraphType.length() == 0 || sbGraphType.equals("") || sbGraphType == null) {
						openC = true;
						records.put("title", kpiDetail.getKpiID().getTitle());
						records.put("subtitle", kpiDetail.getKpiID().getSubtitle());
						records.put("cnpj", kpiDetail.getKpiID().getCompany().getCnpj());
						records.put("companyName", kpiDetail.getKpiID().getCompany().getName());
						records.put("visible", kpiDetail.getKpiID().getVisible());
						
						records.put("graphType", kpiDetail.getKpiID().getGraphType());
						String k  = String.valueOf(kpiDetail.getKpiID().getGraphType());
						records.put("div", k);
												
						if(graphType == 7 || graphType == 12) records.put("value", kpiDetail.getValorKPI());
						
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
					if(graphType != 7) sbGraphType.append(kpiDetail.getColumnX());
					sbGraphType.append("'").append(s);
					if(graphType == 7) {
						double vld = kpiDetail.getValorKPI();
						int valor = (int) vld;
						sbGraphType.append(valor);
					} else {
						sbGraphType.append(kpiDetail.getValorKPI());
						if(!kpiDetail.getKpiID().getLabel2().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getValorKPI2());
						}
						if(!kpiDetail.getKpiID().getLabel3().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getValorKPI3());
						}
						if(!kpiDetail.getKpiID().getLabel4().equals("")) {
							sbGraphType.append(s);
							sbGraphType.append(kpiDetail.getValorKPI4());
						}
					}
				}
			}
        	if(openC) sbGraphType.append("]").append(rn);
		}
		
		if(sbGraphType.toString().length() == 0) records.put("emptyTable", true);
		else records.put("emptyTable", false);
		
		records.put("data", sbGraphType.toString());
		
		
		// definicao do tipo de grafico a ser gerado
		if (param == 1)		records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 2)		records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 3)		records.put("chartType",CHART_TYPE_PIE);
		if (param == 4)		records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 5)		records.put("chartType",CHART_TYPE_LINE);
		if (param == 6)		records.put("chartType",CHART_TYPE_LINE);
		if (param == 7)		records.put("chartType",CHART_TYPE_COLUMN);	// etiqueta
		if (param == 8)		records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 9)		records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 10)	records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 11)	records.put("chartType",CHART_TYPE_COLUMN);
		if (param == 12)	records.put("chartType",CHART_TYPE_COLUMN);	// etiqueta
		if (param == 13)	records.put("chartType",CHART_TYPE_PIE);	//pie
		if (param == 14)	records.put("chartType",CHART_TYPE_PIE);	//pie
		if (param == 15)	records.put("chartType",CHART_TYPE_COLUMN);	// colun %
		if (param == 16)	records.put("chartType",CHART_TYPE_COLUMN);	// colun %
		if (param == 17)	records.put("chartType",CHART_TYPE_COLUMN);	// colun %
		if (param == 18)	records.put("chartType",CHART_TYPE_LINE);	//line (4)
		if (param == 19)	records.put("chartType",CHART_TYPE_LINE);	//line (4)
		if (param == 20)	records.put("chartType",CHART_TYPE_LINE);	//line (4)
		if (param == 21)	records.put("chartType",CHART_TYPE_COLUMN);
		
		return records;
	}

	
	public String getTable(JSONObject dataToCharts){
		
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		String k  = dataToCharts.optString("div");
		
		sb.append("				let dataTable").append(k).append(" = google.visualization.arrayToDataTable([").append(rn);
		sb.append(dataToCharts.optString("data"));
		sb.append("				]);").append(rn).append(rn);
		
		return sb.toString();
	}
	
	
	public String putOptions(JSONObject dataToCharts){
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		sb.append("				let options").append(dataToCharts.optString("div")).append(" = Object.assign({}, optionsTo").append(dataToCharts.optString("chartType")).append(");").append(rn);
		sb.append("				options").append(dataToCharts.optString("div")).append(".title = '").append(dataToCharts.optString("title")).append("';").append(rn);

		return sb.toString();
	}


	public String putChart(JSONObject dataToCharts){
		StringBuffer sb = new StringBuffer();
		String rn  = "\r\n";
		String k   = dataToCharts.optString("div");
		String div = "charts"+k;
		sb.append("				let grafico").append(k).append(" = new google.visualization.").append(dataToCharts.optString("chartType")).append("(").append(rn);
		sb.append("					document.getElementById('").append(div).append("'));").append(rn);
		sb.append("				grafico").append(k).append(".draw(dataTable").append(k).append(", options").append(k).append(");").append(rn).append(rn);
		return sb.toString();
	}
	
	
	public String getbasicOptions(boolean isNew) {
		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";
		
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("options = {      				").append(rn);
		sb.append("					legend: 'none', 	").append(rn);
		sb.append("					vAxis: {         		").append(rn);
		sb.append("						gridlines: {       		").append(rn);
		sb.append("							count:0,               	").append(rn);
		sb.append("							color:'transparent'    		").append(rn);
		sb.append("        				},                      	  		").append(rn);
		sb.append("						format: 'short',			 			").append(rn);
		sb.append("    					minValue: 0	    						").append(rn);
		sb.append("    				},                       	   				").append(rn);
		sb.append("    				hAxis: {                    				").append(rn);
		sb.append("						textStyle: { fontSize: 8 }	 			").append(rn);
		sb.append("					},                              			").append(rn);
		sb.append("					colors: cores								").append(rn);
		sb.append("				};                                 				").append(rn);
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("optionsToBarChart = Object.assign({},options);  	 			").append(rn);
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("optionsToPieChart = Object.assign({},options);   			").append(rn);
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("optionsToLineChart = Object.assign({},options);    			").append(rn);
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("optionsToColumnChart = Object.assign({},options);   			").append(rn);
		sb.append("				");
		if(isNew) sb.append("let ");
		sb.append("optionsToGauge = Object.assign({},options);   				").append(rn);
		sb.append("				optionsToColumnChart.bar = {groupWidth: '80%'};	").append(rn);
		sb.append("				optionsToLineChart.curveType = 'function'		").append(rn);
		sb.append("				optionsToPieChart.legend = {position: 'bottom'};").append(rn);
		sb.append("				optionsToPieChart.pieSliceTextStyle = {fontSize: 9};").append(rn);
		sb.append("				optionsToPieChart.pieStartAngle = 90;			").append(rn);
		sb.append("				optionsToGauge.minorTicks = 99;					").append(rn).append(rn);
      
		
		return sb.toString();
	}
	
	
}
