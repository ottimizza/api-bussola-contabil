package br.com.ottimizza.dashboard.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.domain.dtos.ChartOptionDTO;
import br.com.ottimizza.dashboard.models.ChartOption;
import br.com.ottimizza.dashboard.repositories.chart_option.ChartOptionRepository;

@Service
public class ChartOptionService {
	
	@Inject
	ChartOptionRepository repository;

	public ChartOptionDTO save(ChartOptionDTO optionDto, String authorization) {
		return ChartOptionDTO.fromEntity(repository.save(ChartOptionDTO.fromDto(optionDto)));
	}

	public List<ChartOption> findAll(ChartOptionDTO optionDto) {
		return 	repository.findAll(optionDto);
	}
	
}
