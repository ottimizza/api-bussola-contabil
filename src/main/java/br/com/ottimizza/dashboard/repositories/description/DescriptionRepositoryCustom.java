package br.com.ottimizza.dashboard.repositories.description;

import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;

public interface DescriptionRepositoryCustom {
//	DescriptionRepositoryImpl
	List<Description> findAll(DescriptionDTO descriptionDto);


}
