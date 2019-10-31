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

	private BigInteger companyId;
	private List<Balance> balances;
	
	private String cnpj;
	private LocalDate dateBalance;
	
	private String syntheticId;
	private String analyticId;
	private String description;
	private Double initialValue;
	private Double finalValue;
	private Double debitValue;
	private Double creditValue;

	
	public Balance patch(Balance balance) {
		if (this.syntheticId != null && !this.syntheticId.equals(""))	balance.setSyntheticId(this.syntheticId);
		if (this.analyticId != null && !this.analyticId.equals(""))		balance.setAnalyticId(this.analyticId);
		if (this.description != null && !this.description.equals(""))	balance.setDescription(this.description);
		if (this.initialValue != null)	balance.setInitialValue(this.initialValue);
		if (this.finalValue != null)	balance.setFinalValue(this.finalValue);
		if (this.debitValue != null)	balance.setDebitValue(this.debitValue);
		if (this.creditValue != null)	balance.setCreditValue(this.creditValue);
		if (this.dateBalance != null)	balance.setDateBalance(this.dateBalance);
		if (this.companyId != null)		balance.setCompanyId(this.companyId);

		return balance;
	}
}
