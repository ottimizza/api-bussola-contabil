package br.com.ottimizza.dashboard.repositories.balance;

import java.time.LocalDate;
import java.util.List;

import br.com.ottimizza.dashboard.models.Balance;

public interface BalanceRepositoryCustom {
	
    List<Balance> findBalanceByCnpjAndData(String cnpj, LocalDate dateBalance);

}
