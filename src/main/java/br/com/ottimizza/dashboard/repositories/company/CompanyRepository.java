package br.com.ottimizza.dashboard.repositories.company;

import br.com.ottimizza.dashboard.models.Company;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, BigInteger>, CompanyRepositoryCustom {

	
	Optional<Company> findById(BigInteger idCompany);
    
}
