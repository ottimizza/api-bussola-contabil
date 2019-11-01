package br.com.ottimizza.dashboard.repositories.balance;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.models.QBalance;
import br.com.ottimizza.dashboard.models.QCompany;
import br.com.ottimizza.dashboard.utils.StringUtil;

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
	public List<Balance> findAll(BalanceDTO balanceDTO) {

		JPAQuery<Balance> query = new JPAQuery<Balance>(em).from(balance)
                .innerJoin(company).on(company.id.eq(balance.companyId));
		
		if(balanceDTO.getCnpj() != null)		query.where(company.cnpj.eq(StringUtil.formatCnpj(balanceDTO.getCnpj())));
		if(balanceDTO.getDateBalance() != null)	query.where(balance.dateBalance.eq(balanceDTO.getDateBalance()));
		if(balanceDTO.getSyntheticId() != null) query.where(balance.syntheticId.eq(balanceDTO.getSyntheticId()));
		if(balanceDTO.getAnalyticId() != null)	query.where(balance.analyticId.eq(balanceDTO.getAnalyticId()));
		if(balanceDTO.getDescription() != null)	query.where(balance.description.eq(balanceDTO.getDescription()));

        return query.fetch();
	}

}
