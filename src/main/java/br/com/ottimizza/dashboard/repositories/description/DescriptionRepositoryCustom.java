package br.com.ottimizza.dashboard.repositories.description;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import br.com.ottimizza.dashboard.models.Description;

public interface DescriptionRepositoryCustom {
//	DescriptionRepositoryImpl
	List<Description> findAll(DescriptionDTO descriptionDto);

	Page<Description> findByOrganizationIdScriptType(DescriptionDTO descriptionDTO, Pageable pageable);

	Description findByOrganizationIdScriptType(DescriptionDTO descriptionDTO);
}
