package br.com.ottimizza.dashboard.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.services.WebChartsService;

@RestController
@RequestMapping("/charts")
public class WebChartsController {

	@Inject
	WebChartsService wcs;

	@RequestMapping(value = "/by_cnpj", method = RequestMethod.POST, produces = MediaType.APPLICATION_XHTML_XML_VALUE)
//	@RequestMapping(value = "/by_cnpj", method = RequestMethod.POST)
	public HttpEntity<byte[]> download(@RequestBody String objRequest) throws IOException {

		JSONObject requestBody = new JSONObject(objRequest);
		JSONArray cnpjs = requestBody.optJSONArray("cnpj");
		Locale ptBr = new Locale("pt", "BR");

		// variavel usada em FOR
		int totalCharts = 21;
		
		// cria arquivo temporario
		File tmp = File.createTempFile("temporario", ".html");
		tmp.deleteOnExit();

		// tratamento de arquivo para download
		OutputStream fos = new FileOutputStream(tmp);
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);

		String c = cnpjs.getString(0);

		// busca de dados
		String cnpjReq = "";
		List<String> cnpj = new ArrayList<String>();
		cnpj.add(c);

		JSONObject dataToCharts = new JSONObject();
//		montar aqui depois mover pra service ou nao

		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		String width = "800";
		String height = "400";

		sb.append("<!DOCTYPE html>										").append(rn);
		sb.append("<html>												").append(rn);
		sb.append("	<head>												").append(rn);
		sb.append("		<meta charset=\"utf-8\"/>						").append(rn);
		
		sb.append("		<style>             							").append(rn);
		sb.append("			* {               							").append(rn);
		sb.append("				font-family: 'IBM Plex Sans', sans-serif;	").append(rn);
		sb.append("			}               									").append(rn);
		sb.append("			@media print{											").append(rn);
		sb.append("				#container{											").append(rn);
		sb.append("					align-items: center;							").append(rn);
		sb.append("				}												").append(rn);
		sb.append("				#separator{									").append(rn);
		sb.append("					page-break-before: always;			").append(rn);
		sb.append("				}										").append(rn);
		sb.append("				#charts7{								").append(rn);
		sb.append("					border-style: solid;					").append(rn);
		sb.append("					border-color: #4285f4;						").append(rn);
		sb.append("					border-width: 1px;								").append(rn);
		sb.append("				}													").append(rn);
		sb.append("				#charts12{											").append(rn);
		sb.append("					border-style: solid;						").append(rn);
		sb.append("					border-color: darkred;					").append(rn);
		sb.append("					border-width: 1px;					").append(rn);
		sb.append("				}										").append(rn);
		sb.append("			}											").append(rn);
		sb.append("		    #container{										").append(rn);
		sb.append("				align-items: center;							").append(rn);
		sb.append("			}														").append(rn);
		sb.append("		    #charts21, #charts1, #charts8, #charts9,        		").append(rn);
		sb.append("		    #charts13, #charts2, #charts3, #charts6,        		").append(rn);
		sb.append("		    #charts4, #charts5, #charts15, #charts16,       	").append(rn);
		sb.append("		    #charts18, #charts10, #charts11, #charts14,		").append(rn);
		sb.append("			#charts17, #charts19, #charts20 {			").append(rn);
		sb.append("		        width: 80%;                   			").append(rn);
		sb.append("		        height: 400px;                        	").append(rn);
		sb.append("		    }                                        		").append(rn);
		sb.append("			#epi{												").append(rn);
		sb.append("				padding: 10px;										").append(rn);
		sb.append("				text-align: center;									").append(rn);
		sb.append("				width: 80%;											").append(rn);
		sb.append("				height: 150px;									").append(rn);
		sb.append("				position: relative;							").append(rn);
		sb.append("			}											").append(rn);
		sb.append("			#charts7 {									").append(rn);
		sb.append("				width: 69%;								").append(rn);
		sb.append("				height: 100%;								").append(rn);
//		sb.append("				display: inline-block;							").append(rn);
		sb.append("				border-radius: 20px;							").append(rn);
		sb.append("				border-style: solid;								").append(rn);
		sb.append("				border-color: #4285f4;								").append(rn);
		sb.append("				border-width: 1px;									").append(rn);
		sb.append("				margin-left: 13%;								").append(rn);
		sb.append("			}												").append(rn);
		sb.append("			#charts12 {									").append(rn);
		sb.append("				width: 69%;								").append(rn);
		sb.append("				height: 100%;							").append(rn);
		sb.append("				display: inline-block;						").append(rn);
		sb.append("				border-radius: 20px;							").append(rn);
		sb.append("				border-style: solid;								").append(rn);
		sb.append("				border-color: darkred;								").append(rn);
		sb.append("				border-width: 1px;									").append(rn);
		sb.append("				margin-left: 13%;								").append(rn);
		sb.append("			}												").append(rn);
		sb.append("			#charts7 #endo {							").append(rn);
//		sb.append("				position: absolute;						").append(rn);
//		sb.append("				top: 50%;								").append(rn);
//		sb.append("				left: 50%;								").append(rn);
//		sb.append("				transform: translate(-50%, -50%);		").append(rn);
		sb.append("				font-size: 2em;							").append(rn);
		sb.append("				color: #4285f4;							").append(rn);
		sb.append("			}												").append(rn);
		sb.append("			#charts12 #endo {									").append(rn);
//		sb.append("				position: absolute;									").append(rn);
//		sb.append("				top: 50%;											").append(rn);
//		sb.append("				left: 50%;											").append(rn);
//		sb.append("				transform: translate(-50%, -50%);					").append(rn);
		sb.append("				font-size: 2em;										").append(rn);
		sb.append("				color: darkred;										").append(rn);
		sb.append("			}														").append(rn);
		
		Integer [] ordenated = {7,12,21,1,8,9,13,2,3,6,4,5,15,16,18,10,11,14,17,19,20};
		List <Integer> chartsSequence = Arrays.asList(ordenated);
		
		for (Integer charts : chartsSequence) {
			dataToCharts = wcs.getDataToCharts(cnpj, charts);

			if(dataToCharts.optBoolean("emptyTable") || !dataToCharts.optBoolean("visible"))	{
				sb.append("			#charts").append(charts).append("  { display: none; }").append(rn); 
			}
		}

		sb.append("		</style>").append(rn);
		sb.append("	</head>").append(rn);
		sb.append("	<body>").append(rn);

		int cont = 0;
		
		sb.append("		<div id=\"container\">").append(rn);
		
		for (Integer charts : chartsSequence) {
			dataToCharts = wcs.getDataToCharts(cnpj, charts);
			if(!dataToCharts.optBoolean("emptyTable"))	{
				if(charts == 7 || charts == 12) {
			        String valorString = DecimalFormat.getCurrencyInstance(ptBr).format(dataToCharts.optDouble("value"));
			        
					sb.append("			<div id=\"epi\">").append(rn);
					sb.append("				<div id=\"charts").append(charts).append("\")>").append(rn);
					sb.append("					<h4>").append(dataToCharts.optString("title")).append("</h4>").append(rn);
					sb.append("					<span id=\"endo\"><strong>").append(valorString).append("</strong></span>").append(rn);
					sb.append("				</div>").append(rn);
					sb.append("			</div>").append(rn);

				}else {
					sb.append("			<div id=\"charts").append(charts).append("\"></div>" ).append(rn);
				}
				cont++;
			}
			if(cont > 3 && (cont-1)%3 == 0) {
				sb.append("			<div id=\"separator\"></div>").append(rn).append(rn);
			}	
		}
		sb.append("		</div>").append(rn).append(rn);
		sb.append("	</body>").append(rn);
		sb.append("	<footer>").append(rn);
		sb.append("		<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>").append(rn);
		sb.append("		<script>").append(rn);
		sb.append("			function drawCharts() {").append(rn);
		
		sb.append("				let cores = ['#4285f4','#00ce93','#ce0000','#af00ce','#ce8900'];	").append(rn);
        sb.append(wcs.getbasicOptions(true));
		
        for (int kk = 1; kk <= totalCharts; kk++) {
    		dataToCharts = wcs.getDataToCharts(cnpj, kk);
    		
    		if(dataToCharts.optBoolean("visible") && !dataToCharts.optString("data").equals("")) {
    			if(kk != 7 && kk != 12) {
        			sb.append(wcs.getTable(dataToCharts));
        			if(kk == 3 || kk == 13 || kk == 14)
        				sb.append("				dataTable").append(dataToCharts.optString("div")).append(".sort([{ column: 1, desc: true }]);").append(rn);
        			else
        				sb.append("				dataTable").append(dataToCharts.optString("div")).append(".sort([{ column: 0, desc: false }]);").append(rn);
    				
        			sb.append(wcs.putOptions(dataToCharts));
    				// opcoes adicionais
    				 if(kk == 3 || kk == 13 || kk == 14)  {	//DONUTs
    					 sb.append("				options").append(kk).append(".pieHole = 0.4;").append(rn).append(rn);
    					 sb.append("				options").append(kk).append(".legend = {position: 'right'};").append(rn).append(rn);
    				 }
    				 if(kk == 15 || kk == 16 || kk == 17) { //COLUMN (acomuladas)
    					 sb.append("				options").append(kk).append(".isStacked = 'percent';").append(rn).append(rn);
    					 sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn).append(rn);
    					 sb.append("				options").append(kk).append(".vAxis.format = 'percent';").append(rn).append(rn);
    				 }
    				 if(kk == 18 || kk == 19 || kk == 20) { //LINE (4 linhas)
    					 sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn).append(rn);
    				 }
    				 if(kk == 7) { //GAUGE
    					 double vld = 0;
    					 try {vld = Double.parseDouble(dataToCharts.optString("value"));}
    					 catch (NumberFormatException ne) { }
      					 int max = (int) (vld * 1.8);
      					 sb.append("				options").append(kk).append(".max = ").append(max).append(";").append(rn);
    				 }
    				 
    				 sb.append(wcs.putChart(dataToCharts));
    				 
    				 sb.append(wcs.getbasicOptions(false));
    			}
    		}
		}

		sb.append("			}").append(rn);
		sb.append("		</script>").append(rn);

		sb.append("		<script>").append(rn);
		sb.append("			google.charts.load('current', {").append(rn);
		sb.append("				'packages': ['corechart', 'gauge', 'bar'],").append(rn);
		sb.append("				'language': 'pt'").append(rn);
		sb.append("			});").append(rn);
		sb.append("			google.charts.setOnLoadCallback(drawCharts);").append(rn);
		sb.append("		</script>").append(rn);
		sb.append("	</footer>").append(rn);
		
		sb.append("</html>").append(rn);

		// escrevendo arquivo
		bw.write(sb.toString());
		bw.close();

		// baixar
		String fName = "arquivo_pra_baixar.html";

		byte[] arquivo = Files.readAllBytes(Paths.get(tmp.getAbsolutePath()));
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=\"" + fName + "\"");
		HttpEntity<byte[]> entity = new HttpEntity<byte[]>(arquivo, httpHeaders);

		return entity;
	}
}
