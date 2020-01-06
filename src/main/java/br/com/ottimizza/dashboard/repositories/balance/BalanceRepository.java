package br.com.ottimizza.dashboard.repositories.balance;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ottimizza.dashboard.models.Balance;

@Repository
public interface BalanceRepository extends PagingAndSortingRepository<Balance, BigInteger>, BalanceRepositoryCustom {

	Optional<Balance> findById(BigInteger id);
	
	@Modifying
	@Transactional
	@Query("UPDATE Balance SET active = false WHERE companyId = :companyId and dateBalance = :dateBalance")
	String notActive(@Param("companyId") BigInteger companyId, @Param("dateBalance") LocalDate dateBalance , String authorization);
	
}
