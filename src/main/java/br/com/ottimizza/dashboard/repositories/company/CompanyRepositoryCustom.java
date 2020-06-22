package br.com.ottimizza.dashboard.repositories.company;

import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.models.Company;

public interface CompanyRepositoryCustom { // CompanyRepositoryImpl
	
    List<Company> findAll(CompanyDTO filter);
    
    List<Company> findCompaniesByCNPJ(List<String> cnpj);
    
}
