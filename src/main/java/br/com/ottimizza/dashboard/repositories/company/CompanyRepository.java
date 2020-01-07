package br.com.ottimizza.dashboard.repositories.company;

import br.com.ottimizza.dashboard.models.Company;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, BigInteger>, CompanyRepositoryCustom {
	
	Optional<Company> findById(BigInteger idCompany);

    Company findCompanyByCnpj(String cnpj);

//    @Query("select c from Company c where cnpj = :cnpj")
	Company findByCnpj(String cnpj);

}
