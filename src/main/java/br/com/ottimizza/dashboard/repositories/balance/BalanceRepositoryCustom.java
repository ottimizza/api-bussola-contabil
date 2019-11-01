package br.com.ottimizza.dashboard.repositories.balance;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.ottimizza.dashboard.models.Balance;

public interface BalanceRepositoryCustom {
//	BalanceRepositoryImpl
    List<Balance> findBalanceByCnpjAndData(String cnpj, LocalDate dateBalance);

	Optional<List<Balance>> findBalancesByCompanyId(BigInteger id);

	List<Balance> findBalancesByCnpj(String cnpj);

}
