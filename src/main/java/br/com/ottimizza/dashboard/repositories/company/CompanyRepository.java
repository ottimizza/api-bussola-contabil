package br.com.ottimizza.dashboard.repositories.company;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.domain.dtos.CompanyDTO;
import br.com.ottimizza.dashboard.models.Company;

public interface CompanyRepository extends JpaRepository<Company, BigInteger>, CompanyRepositoryCustom {
	
	Optional<Company> findById(BigInteger idCompany);

    Company findCompanyByCnpj(String cnpj);

	Company findByCnpj(String cnpj);

}
