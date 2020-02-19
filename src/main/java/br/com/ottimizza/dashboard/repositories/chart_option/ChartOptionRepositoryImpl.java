package br.com.ottimizza.dashboard.repositories.chart_option;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.ChartOptionDTO;
import br.com.ottimizza.dashboard.models.ChartOption;
import br.com.ottimizza.dashboard.models.QChartOption;

public class ChartOptionRepositoryImpl implements ChartOptionRepositoryCustom {

	@PersistenceContext
	EntityManager em;
	
	private QChartOption chartOption = QChartOption.chartOption;
	
	@Override
	public List<ChartOption> findAll(ChartOptionDTO dto) {
		
		JPAQuery<ChartOption> query = new JPAQuery<ChartOption>(em).from(chartOption);
		if(dto.getId() != null)				query.where(chartOption.id.eq(dto.getId()));
		if(dto.getChartType() != null)		query.where(chartOption.chartType.eq(dto.getChartType()));
		if(dto.getStyle() != null)			query.where(chartOption.style.eq(dto.getStyle()));

		return query.fetch();
	}

}
