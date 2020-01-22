package br.com.ottimizza.dashboard.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.apis.IsGdApi;
import br.com.ottimizza.dashboard.apis.SalesForceApi;
import br.com.ottimizza.dashboard.services.WebChartsService;

@RestController
@RequestMapping("/charts")
public class WebChartsController {

	@Inject
	WebChartsService wcs;

	@Value("${storage-config.upload-id-bussola}")
    private String UPLOAD_ID_BUSSOLA;
	
	@Value("${storage-config.upload-accounting-id}")
    private String UPLOAD_ACCOUNTING_ID;
	
	@RequestMapping(value = "/by_cnpj", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String download(@RequestHeader("Authorization") String authorization, @RequestBody String objRequest,
			HttpServletRequest request) throws IOException, Exception {
		authorization = authorization.replace("Bearer ", "");

		System.out.println("auth>" + authorization);
		JSONObject requestBody = new JSONObject(objRequest);
		JSONArray cnpjs = requestBody.optJSONArray("cnpj");
		//String email = requestBody.optJSONArray("email").getString(0);
		String urlLogo = requestBody.optJSONArray("urlLogo").getString(0);
		//int kind = requestBody.optJSONArray("kind").getInt(0);
		Locale ptBr = new Locale("pt", "BR");
		
		// variavel usada em FOR
		int totalCharts = 29;
		Integer[] ordenated = { 7, 12, 21, 1, 8, 9, 10, 11, 13, 2, 3, 6, 4, 5, 15, 16, 18, 14, 17, 19, 20, 22, 23, 24, 25, 26, 27, 28, 29 };

		String cnpjString = cnpjs.getString(0);

		// busca de dados
		String cnpjReq = "";
		List<String> cnpj = new ArrayList<String>();
		cnpj.add(cnpjString);

		JSONObject dataToCharts = new JSONObject();
//		montar aqui depois mover pra service ou nao

		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		sb.append("<!DOCTYPE html>"						).append(rn);
		sb.append("<html>"								).append(rn);
		sb.append("	<head>"								).append(rn);
		sb.append("		<meta charset=\"utf-8\"/>"		).append(rn);

		sb.append(wcs.getBasicStyle());

		List<Integer> chartsSequence = Arrays.asList(ordenated);
		dataToCharts = new JSONObject();
		String companyName = "";
		for (Integer charts : chartsSequence) {
			
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(charts)));
			if(!dataToCharts.optString("companyName").equals("")) companyName = dataToCharts.optString("companyName");
			
			if (dataToCharts.optBoolean("emptyTable") || !dataToCharts.optBoolean("visible")) {
				System.out.println("Tabela Vazia ou nao Visivelll ");
				sb.append("			#charts").append(charts).append("  { display: none; }").append(rn);
			}
		}

		sb.append("		</style>").append(rn);
		sb.append("	</head>").append(rn);
		sb.append("	<body>").append(rn);

		int cont = 0;
		sb.append("		<div id=\"container\">").append(rn);
		//SalesForceApi api = new SalesForceApi();
		//String urlLogo = api.getUrlLogotipoByEmail(email);

		// mudar link
		if (urlLogo.equals("")) {
			urlLogo = "https://www.ottimizza.com.br/bussola/logo_bussola.png";
		}
		
		sb.append("			<div id=\"head\" style=\"margin:0\">").append(rn);
		sb.append("				<img id=\"logo\" style=\"margin-left:240px; margin-right:40px\" src=\"").append(urlLogo).append("\">").append(rn);
		sb.append("				<div style=\"width: fit-content; margin: 0;\">").append(rn);
		sb.append("					<span style=\"margin-bottom: 5px;\">").append(cnpjString).append("</span>").append(rn);
		sb.append("					<span>").append(companyName).append("</span>").append(rn);
		sb.append("				</div>").append(rn);
		sb.append("			</div>").append(rn);
		
		for (Integer charts : chartsSequence) {
			System.out.println("buscando  chart");
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(charts)));
			if (!dataToCharts.optBoolean("emptyTable") && dataToCharts.optBoolean("visible")) {
				System.out.println("ta dentro do if de validacaoooooo");
				if (charts == 7 || charts == 12) {
					String valorString = DecimalFormat.getCurrencyInstance(ptBr)
							.format(dataToCharts.optDouble("value"));

					sb.append("			<div id=\"epi\">").append(rn);
					sb.append("				<div id=\"charts").append(charts).append("\">").append(rn);
					sb.append("					<p>").append(dataToCharts.optString("title")).append("</p>")
							.append(rn);
					sb.append("					<span id=\"endo\"><strong>").append(valorString)
							.append("</strong></span>").append(rn);
					sb.append("				</div>").append(rn);
					sb.append("			</div>").append(rn);

				} else {
					sb.append("			<div id=\"charts").append(charts).append("\"></div>").append(rn);
					System.out.println("entrou no else");
				}
				cont++;
			}

			if (cont == 3) {
				sb.append("			<div id=\"separator\"></div>").append(rn).append(rn);
				cont = 0;
			}
		}
		sb.append("		</div>").append(rn).append(rn);

		sb.append("	<footer>").append(rn);
		sb.append("		<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>")
				.append(rn);
		sb.append("		<script>").append(rn);
		sb.append("			function drawCharts() {").append(rn);

		sb.append("				let cores = ['#4285f4','#00ce93','#ce0000','#af00ce','#ce8900'];	").append(rn);
		sb.append(wcs.getbasicOptions(true));

		for (int kk = 1; kk <= totalCharts; kk++) {
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(kk)));
			
			if (dataToCharts.optBoolean("visible") && !dataToCharts.optString("data").equals("")) {
				
				if(!dataToCharts.optString("chartType").equals("Gauge")) { //(kk != 7 && kk != 12)
					sb.append(wcs.getTable(dataToCharts));
					
					if(dataToCharts.optString("chartType").equals("PieChart")) { //(kk == 3 || kk == 13 || kk == 14)
						sb.append("				dataTable").append(dataToCharts.optString("div"))
								.append(".sort([{ column: 1, desc: true }]);").append(rn);
					} else {
						sb.append("				dataTable").append(dataToCharts.optString("div")).append(rn);
					}
					sb.append(wcs.putOptions(dataToCharts));

					// opcoes adicionais
					if(dataToCharts.optString("chartType").equals("PieChart")) { //(kk == 3 || kk == 13 || kk == 14)
						sb.append("				options").append(kk).append(".pieHole = 0.4;").append(rn);
						sb.append("				options").append(kk).append(".sliceVisibilityThreshold = .0;").append(rn);
						sb.append("				options").append(kk).append(".legend = {position: 'right'};").append(rn).append(rn);
					}

					if(dataToCharts.optString("chartType").equals("ColumnChart")) {
						if (kk == 15 || kk == 16 || kk == 17) { // COLUMN (acomuladas)
							sb.append("				options").append(kk).append(".isStacked = 'percent';").append(rn);
							sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn);
							sb.append("				options").append(kk).append(".vAxis.format = 'percent';").append(rn).append(rn);
						} else {
							sb.append("				options").append(kk).append(".focusTarget = 'category';").append(rn).append(rn);							
						}
					}
					if(dataToCharts.optString("chartType").equals("LineChart")) {
						
						if (kk == 5 || kk == 6) { // LINE (1 linha)
							sb.append("				options").append(kk).append(".pointSize = 2;").append(rn).append(rn);
						}
						if (kk == 18 || kk == 19 || kk == 20 || kk > 21) { // LINE (4 linhas)
							sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn);
							sb.append("				options").append(kk).append(".pointSize = 2;").append(rn).append(rn);
						}
					}
					// NAO estamos usando o gauge.
					if(dataToCharts.optString("chartType").equals("Gauge")) {
//					if (kk == 7) { // GAUGE
						double vld = 0;
						try {
							vld = Double.parseDouble(dataToCharts.optString("value"));
						} catch (NumberFormatException ne) {
						}
						int max = (int) (vld * 1.8);
						sb.append("				options").append(kk).append(".max = ").append(max).append(";")
								.append(rn);
					}
					sb.append(wcs.putChart(dataToCharts));

					sb.append(wcs.getbasicOptions(false));
				}
			}
		}

		sb.append("			}").append(rn);
		sb.append("		</script>").append(rn);

		LocalDateTime now = LocalDateTime.now();
	    Timestamp timestamp = Timestamp.valueOf(now);
		
		sb.append("		<script>").append(rn);
		sb.append("			google.charts.load('current', {").append(rn);
		sb.append("				'packages': ['corechart', 'gauge', 'bar'],").append(rn);
		sb.append("				'language': 'pt'").append(rn);
		sb.append("			});").append(rn);
		sb.append("			google.charts.setOnLoadCallback(drawCharts);").append(rn);
		sb.append("		</script>").append(rn);
		sb.append("    <p class=\"data\"> Compartilhado em ").append(timestamp).append("</div>");
		sb.append("	</footer>").append(rn);
		sb.append("	</body>").append(rn);
		sb.append("</html>").append(rn);

		// cria arquivo temporario
		File tmp = File.createTempFile("Bussola", ".html");
		tmp.deleteOnExit();

		// tratamento de arquivo para download
		OutputStream fos = new FileOutputStream(tmp);
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);

		// escrevendo arquivo
		bw.write(sb.toString());
		bw.close();

		String application_id = UPLOAD_ID_BUSSOLA;
		String accounting_id = UPLOAD_ACCOUNTING_ID;
		String resourceId = "";

		try {
			//Ajuste para evitar de ir em branco o link
			for (int kk = 0; kk < 5; kk++) {

				JSONObject response = new JSONObject(sendToStorage(tmp, authorization, application_id, accounting_id));
				JSONObject record = response.optJSONObject("record");
				resourceId = record.optString("id");
				
				String downloadURL = "";
				String toShortURL = String.format("https://s4.ottimizzacontabil.com:55325/storage/%s", resourceId);
				
				//encurtador de URL
				IsGdApi gd = new IsGdApi();
				downloadURL = gd.shortURL(toShortURL);
				if(downloadURL.equals("")) downloadURL = toShortURL;
				
				record.put("id", downloadURL);
				response.put("record", record);

				if (!resourceId.equals("") && !downloadURL.equals(""))
					return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "{}";
	}

	@RequestMapping(value = "/html_by_cnpj", method = RequestMethod.POST)
	public ResponseEntity<Resource> downloadHTML(@RequestBody String objRequest, HttpServletRequest request) throws IOException, Exception{

		JSONObject requestBody = new JSONObject(objRequest);
		JSONArray cnpjs	= requestBody.optJSONArray("cnpj");
		//String email = requestBody.optJSONArray("email").getString(0);
		String urlLogo = requestBody.optJSONArray("urlLogo").getString(0);
		Locale ptBr = new Locale("pt", "BR");
		

		// variavel usada em FOR
		int totalCharts = 21;
		Integer[] ordenated = { 7, 12, 21, 1, 8, 9, 10, 11, 13, 2, 3, 6, 4, 5, 15, 16, 18, 14, 17, 19, 20 };

		String cnpjString = cnpjs.getString(0);

		// busca de dados
		String cnpjReq = "";
		List<String> cnpj = new ArrayList<String>();
		cnpj.add(cnpjString);

		JSONObject dataToCharts = new JSONObject();
//		montar aqui depois mover pra service ou nao

		StringBuffer sb = new StringBuffer();
		String rn = "\r\n";

		sb.append("<!DOCTYPE html>"					).append(rn);
		sb.append("<html>"							).append(rn);
		sb.append("	<head>"							).append(rn);
		sb.append("		<meta charset=\"utf-8\"/>"	).append(rn);

		sb.append(wcs.getBasicStyleWeb());

		List<Integer> chartsSequence = Arrays.asList(ordenated);
		dataToCharts = new JSONObject();
		String companyName = "";
		for (Integer charts : chartsSequence) {
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(charts)));

			if(!dataToCharts.optString("companyName").equals("")) companyName = dataToCharts.optString("companyName");

			if (dataToCharts.optBoolean("emptyTable") || !dataToCharts.optBoolean("visible")) {
				sb.append("			#charts").append(charts).append("  { display: none; }").append(rn);
			}
		}
		
		sb.append("		</style>").append(rn);
		sb.append("	</head>").append(rn);
		sb.append("	<body>").append(rn);

		int cont = 0;
		sb.append("		<div id=\"container\">").append(rn);
		//SalesForceApi api = new SalesForceApi();
		//String urlLogo = api.getUrlLogotipoByEmail(email);

		// mudar link
		if (urlLogo.equals(""))
			urlLogo = "https://www.ottimizza.com.br/bussola/logo_bussola.png";

		sb.append("			<div id=\"head\" style=\"margin:0\">").append(rn);
		sb.append("				<img id=\"logo\" style=\"margin-left:240px; margin-right:40px\" src=\"").append(urlLogo).append("\">").append(rn);
		sb.append("				<div style=\"width: fit-content; margin: 0;\">").append(rn);
		sb.append("					<span style=\"margin-bottom: 5px;\">").append(cnpjString).append("</span>").append(rn);
		sb.append("					<span>").append(companyName).append("</span>").append(rn);
		sb.append("				</div>").append(rn);
		sb.append("			</div>").append(rn);

		for (Integer charts : chartsSequence) {
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(charts)));
			if (!dataToCharts.optBoolean("emptyTable") && dataToCharts.optBoolean("visible")) {
				if (charts == 7 || charts == 12) {
					String valorString = DecimalFormat.getCurrencyInstance(ptBr)
							.format(dataToCharts.optDouble("value"));

					sb.append("			<div id=\"epi\">").append(rn);
					sb.append("				<div id=\"charts").append(charts).append("\">").append(rn);
					sb.append("					<p>").append(dataToCharts.optString("title")).append("</p>").append(rn);
					sb.append("					<span id=\"endo\"><strong>").append(valorString).append("</strong></span>").append(rn);
					sb.append("				</div>").append(rn);
					sb.append("			</div>").append(rn);

				} else {
					sb.append("			<div id=\"charts").append(charts).append("\"></div>").append(rn);
				}
				cont++;
			}

			if (cont == 3) {
				sb.append("			<div id=\"separator\"></div>").append(rn).append(rn);
				cont = 0;
			}
			
		}
		sb.append("		</div>").append(rn).append(rn);

		sb.append("	<footer>").append(rn);
		sb.append("		<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>")
				.append(rn);
		sb.append("		<script>").append(rn);
		sb.append("			function drawCharts() {").append(rn);

		sb.append("				let cores = ['#4285f4','#00ce93','#ce0000','#af00ce','#ce8900'];	").append(rn);
		sb.append(wcs.getbasicOptions(true));

		for (int kk = 1; kk <= totalCharts; kk++) {
			dataToCharts = wcs.getDataToCharts(cnpj, Short.parseShort(String.valueOf(kk)));
			if (dataToCharts.optBoolean("visible") && !dataToCharts.optString("data").equals("")) {

				if(!dataToCharts.optString("chartType").equals("Gauge")) { //(kk != 7 && kk != 12)
					sb.append(wcs.getTable(dataToCharts));
					
					if(dataToCharts.optString("chartType").equals("PieChart")) { //(kk == 3 || kk == 13 || kk == 14)
						sb.append("				dataTable").append(dataToCharts.optString("div"))
								.append(".sort([{ column: 1, desc: true }]);").append(rn);
					} else {
						sb.append("				dataTable").append(dataToCharts.optString("div")).append(rn);
					}
					sb.append(wcs.putOptions(dataToCharts));

					// opcoes adicionais
					if(dataToCharts.optString("chartType").equals("PieChart")) { //(kk == 3 || kk == 13 || kk == 14)
						sb.append("				options").append(kk).append(".pieHole = 0.4;").append(rn);
						sb.append("				options").append(kk).append(".sliceVisibilityThreshold = .0;").append(rn);
						sb.append("				options").append(kk).append(".legend = {position: 'right'};").append(rn).append(rn);
					}

					if(dataToCharts.optString("chartType").equals("ColumnChart")) {
						if (kk == 15 || kk == 16 || kk == 17) { // COLUMN (acomuladas)
							sb.append("				options").append(kk).append(".isStacked = 'percent';").append(rn);
							sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn);
							sb.append("				options").append(kk).append(".vAxis.format = 'percent';").append(rn).append(rn);
						} else {
							sb.append("				options").append(kk).append(".focusTarget = 'category';").append(rn).append(rn);							
						}
					}
					if(dataToCharts.optString("chartType").equals("LineChart")) {
						if (kk == 5 || kk == 6) { // LINE (1 linha)
							sb.append("				options").append(kk).append(".pointSize = 2;").append(rn).append(rn);
						}
						if (kk == 18 || kk == 19 || kk == 20) { // LINE (4 linhas)
							sb.append("				options").append(kk).append(".legend = {position: 'bottom'};").append(rn);
							sb.append("				options").append(kk).append(".pointSize = 2;").append(rn).append(rn);
						}
					}
					// NAO estamos usando o gauge.
					if(dataToCharts.optString("chartType").equals("Gauge")) {
//					if (kk == 7) { // GAUGE
						double vld = 0;
						try {
							vld = Double.parseDouble(dataToCharts.optString("value"));
						} catch (NumberFormatException ne) {
						}
						int max = (int) (vld * 1.8);
						sb.append("				options").append(kk).append(".max = ").append(max).append(";")
								.append(rn);
					}
					sb.append(wcs.putChart(dataToCharts));

					sb.append(wcs.getbasicOptions(false));
				}
			}
		}

		sb.append("			}").append(rn);
		sb.append("		</script>").append(rn);

		LocalDateTime now = LocalDateTime.now();
	    Timestamp timestamp = Timestamp.valueOf(now);
		
		sb.append("		<script>").append(rn);
		sb.append("			google.charts.load('current', {").append(rn);
		sb.append("				'packages': ['corechart', 'gauge', 'bar'],").append(rn);
		sb.append("				'language': 'pt'").append(rn);
		sb.append("			});").append(rn);
		sb.append("			google.charts.setOnLoadCallback(drawCharts);").append(rn);
		sb.append("		</script>").append(rn);
		sb.append("    <p class=\"data\"> Compartilhado em ").append(timestamp).append("</div>");
		sb.append("	</footer>").append(rn);
		sb.append("	</body>").append(rn);
		sb.append("</html>").append(rn);

		// cria arquivo temporario
		File tmp = File.createTempFile("Bussola", ".html");
		tmp.deleteOnExit();

		// tratamento de arquivo para download
		OutputStream fos = new FileOutputStream(tmp);
		Writer osw = new OutputStreamWriter(fos);
		BufferedWriter bw = new BufferedWriter(osw);

		// escrevendo arquivo
		bw.write(sb.toString());
		bw.close();

		Resource resource = loadFileAsResource(tmp);
		

		String contentDisposition = getContentDisposition(resource, "attachment");
        String contentType = "application/octet-stream"; //getContentType(resource, request);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
		
	}

	public String sendToStorage(File file, String authorization, String application_id, String accounting_id) {

		String url = String.format("https://s4.ottimizzacontabil.com:55325/storage/%s/accounting/%s/store",
				application_id, accounting_id);

		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			// Request Headers
			post.setHeader("Authorization", authorization);

			// Request Body
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			builder.addPart("file", new FileBody(file));

			HttpEntity entity = builder.build();

			post.setEntity(entity);
			HttpResponse httpResponse = client.execute(post);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			HttpEntity responseEntity = httpResponse.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");

			return responseString;
		} catch (Exception e) {
			e.printStackTrace();
			return "{}";
		} finally {

		}

	}

	public Resource loadFileAsResource(File file) throws Exception {
		try {
			Path filePath = Paths.get(file.getAbsolutePath()).toAbsolutePath().normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new Exception("File not found " + file);
			}
		} catch (MalformedURLException ex) {
			throw new Exception("File not found " + file, ex);
		}
	}

	public String getContentType(Resource resource, HttpServletRequest request) throws Exception {
		String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		return (contentType == null) ? "application/octet-stream" : contentType;
	}

	public String getContentDisposition(Resource resource, String contentDisposition) throws Exception {
		return String.format("%s;filename=\"%s\"", contentDisposition, resource.getFilename());
	}

	public String getContentDisposition(Resource resource) throws Exception {
		return getContentDisposition(resource, "inline");
	}
}
