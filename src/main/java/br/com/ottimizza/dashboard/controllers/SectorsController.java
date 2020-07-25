package br.com.ottimizza.dashboard.controllers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.domain.dtos.SectorDTO;

@RestController
@RequestMapping("sectors")
public class SectorsController {

	@Value("${setores}")
	private String SECTORS;
	
	@GetMapping
	public List<SectorDTO> fetchSetores() throws Exception {
		JSONArray sectors = new JSONArray(SECTORS);
		List<SectorDTO> response = new ArrayList<>();
		for(int i = 0 ; i < sectors.length(); i++) {
			JSONObject sector = sectors.getJSONObject(i);
			SectorDTO sec = new SectorDTO(sector.getString("label"),(short) sector.optLong("value"));
			response.add(sec);
		}
		return response;
	}
	
}
