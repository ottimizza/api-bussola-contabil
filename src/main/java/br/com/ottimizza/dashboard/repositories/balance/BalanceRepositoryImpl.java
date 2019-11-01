package br.com.ottimizza.dashboard.repositories.balance;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.models.QBalance;
import br.com.ottimizza.dashboard.models.QCompany;

public class BalanceRepositoryImpl implements BalanceRepositoryCustom{

	@PersistenceContext
	EntityManager em;
	
	private QBalance balance = QBalance.balance;
	private QCompany company = QCompany.company;
	
	@Override
	public List<Balance> findBalanceByCnpjAndData(String cnpj, LocalDate dateBalance) {
		 JPAQuery<Balance> query = new JPAQuery<Balance>(em).from(balance)
	                .innerJoin(company).on(company.id.eq(balance.companyId))
	                .where(company.cnpj.in(cnpj)
            		.and(balance.dateBalance.eq(dateBalance)));
	        return query.fetch();
	}

	@Override
	public Optional<List<Balance>> findBalancesByCompanyId(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Balance> findBalancesByCnpj(BalanceDTO balanceDTO) {

		JPAQuery<Balance> query = new JPAQuery<Balance>(em).from(balance)
                .innerJoin(company).on(company.id.eq(balance.companyId));
		
		if(balanceDTO.getCnpj() != null)		query.where(company.cnpj.eq(balanceDTO.getCnpj()));
		if(balanceDTO.getDateBalance() != null)	query.where(balance.dateBalance.eq(balanceDTO.getDateBalance()));
		if(balanceDTO.getSyntheticId() != null) query.where(balance.syntheticId.eq(balanceDTO.getSyntheticId()));
		if(balanceDTO.getAnalyticId() != null)	query.where(balance.analyticId.eq(balanceDTO.getAnalyticId()));
		if(balanceDTO.getDescription() != null)	query.where(balance.description.eq(balanceDTO.getDescription()));

        return query.fetch();
	}

}
