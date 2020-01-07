package br.com.ottimizza.dashboard.repositories.company;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.models.Company;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepositoryCustom {
//    CompanyRepositoryImpl
    List<Company> findAll(CompanyDTO filter, Integer pageSize, Integer pageIndex);
    
    List<Company> findCompaniesByCNPJ(List<String> cnpj);
    
}
