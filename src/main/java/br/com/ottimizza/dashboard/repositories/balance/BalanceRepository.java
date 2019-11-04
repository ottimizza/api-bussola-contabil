package br.com.ottimizza.dashboard.repositories.balance;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Balance;

@Repository
public interface BalanceRepository extends PagingAndSortingRepository<Balance, BigInteger>, BalanceRepositoryCustom {

	Optional<Balance> findById(BigInteger id);
}
