package br.com.ottimizza.dashboard.repositories.balance;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, BigInteger>, BalanceRepositoryCustom {

	Optional<Balance> findById(BigInteger id);
}
