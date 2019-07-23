package br.com.ottimizza.dashboard.repositories.company;

import br.com.ottimizza.dashboard.models.Company;

import java.math.BigInteger;
import java.util.List;

public interface CompanyRepositoryCustom {
    
    List<Company> findAll(Integer pageSize, Integer pageIndex);
    
    List<Company> findCompaniesByCNPJ(List<String> cnpj);
    
    Company findById(BigInteger idCompany);
    
//    List<Company> findCompanyByName(String name);
//
//    List<Company> findCompanyByName(String name, Integer pageSize, Integer pageIndex);
//    
}
