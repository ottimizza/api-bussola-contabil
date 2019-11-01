package br.com.ottimizza.dashboard.repositories.balance;

import java.time.LocalDate;
import java.util.List;

import br.com.ottimizza.dashboard.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Balance;

public interface BalanceRepositoryCustom {
//	BalanceRepositoryImpl
    List<Balance> findBalanceByCnpjAndData(String cnpj, LocalDate dateBalance);

//	Optional<List<Balance>> findBalancesByCompanyId(BigInteger id);

	List<Balance> findAll(BalanceDTO balanceDTO);

}
