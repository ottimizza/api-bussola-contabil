package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

	private BigInteger id;
	
	private BigInteger companyId;
	private List<Balance> balances;
	
	private String cnpj;
	
	@DateTimeFormat(iso = ISO.DATE)
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
	
	public static BalanceDTO fromEntity(Balance balance) {
	    // @formatter:off
		BalanceDTO dto = new BalanceDTO()
	        .withId(balance.getId())
	        .withSyntheticId(balance.getSyntheticId())
	        .withAnalyticId(balance.getAnalyticId())
	        .withDescription(balance.getDescription())
	        .withInitialValue(balance.getInitialValue())
	        .withFinalValue(balance.getFinalValue())
	        .withDebitValue(balance.getDebitValue())
	        .withCreditValue(balance.getCreditValue())
	        .withDateBalance(balance.getDateBalance())
	        .withCompanyId(balance.getCompanyId());
	    // @formatter:on
	    return dto;
	}
	
	BalanceDTO withId(BigInteger id) {
        this.id = id;
        return this;
    }
	
	BalanceDTO withSyntheticId(String syntheticId) {
        this.syntheticId = syntheticId;
        return this;
    }
	
	BalanceDTO withAnalyticId(String analyticId) {
        this.analyticId = analyticId;
        return this;
    }
	
	BalanceDTO withDescription(String description) {
        this.description = description;
        return this;
    }
	
	BalanceDTO withInitialValue(Double initialValue) {
        this.initialValue = initialValue;
        return this;
    }
	
	BalanceDTO withFinalValue(Double finalValue) {
        this.finalValue = finalValue;
        return this;
    }
	
	BalanceDTO withDebitValue(Double debitValue) {
        this.debitValue = debitValue;
        return this;
    }
	
	BalanceDTO withCreditValue(Double creditValue) {
        this.creditValue = creditValue;
        return this;
    }
	
	BalanceDTO withDateBalance(LocalDate dateBalance) {
        this.dateBalance = dateBalance;
        return this;
    }
	
	BalanceDTO withCompanyId(BigInteger companyId) {
        this.companyId = companyId;
        return this;
    }
	
	
}