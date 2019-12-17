package br.com.ottimizza.dashboard.repositories.balance;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.ottimizza.dashboard.domain.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Balance;

public interface BalanceRepositoryCustom {
//	BalanceRepositoryImpl
    List<Balance> findBalanceByCnpjAndData(String cnpj, LocalDate dateBalance);

//	Optional<List<Balance>> findBalancesByCompanyId(BigInteger id);

	Page<Balance> findAll(BalanceDTO filter, Pageable pageable);

}
