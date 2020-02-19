package br.com.ottimizza.dashboard.repositories.chart_option;

import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.ChartOptionDTO;
import br.com.ottimizza.dashboard.models.ChartOption;

public interface ChartOptionRepositoryCustom { //ChartOptionRepositoryImpl

	List<ChartOption> findAll(ChartOptionDTO dto);

}
