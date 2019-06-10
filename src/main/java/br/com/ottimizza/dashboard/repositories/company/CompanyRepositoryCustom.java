package br.com.ottimizza.dashboard.repositories.company;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.CompanyCustom;

import java.util.List;

public interface CompanyRepositoryCustom {
    
    List<Company> findAll(Integer pageSize, Integer pageIndex);
    
    List<Company> findCompaniesByCNPJ(List<String> cnpj);
    
    List<CompanyCustom> findCompaniesByCNPJCustom(List<String> cnpj);
    
    
    CompanyCustom findByIdCustom (Long id);
//    List<Company> findCompanyByName(String name);
//
//    List<Company> findCompanyByName(String name, Integer pageSize, Integer pageIndex);
//    
}
