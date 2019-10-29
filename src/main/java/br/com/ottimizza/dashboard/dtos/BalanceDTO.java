package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import br.com.ottimizza.dashboard.models.Balance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private BigInteger idOrganization;
	private List<Balance> balances;
	
	private String cnpj;
	private LocalDate dateBalance;
}
