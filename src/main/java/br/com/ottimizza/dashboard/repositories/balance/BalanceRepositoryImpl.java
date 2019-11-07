package br.com.ottimizza.dashboard.repositories.balance;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
	public Page<Balance> findAll(BalanceDTO filter, Pageable pageable) {
		long totalElements = 0;
		JPAQuery<Balance> query = new JPAQuery<Balance>(em).from(balance)
                .innerJoin(company).on(company.id.eq(balance.companyId));
		
		if(filter.getCnpj() != null)		query.where(company.cnpj.eq(StringUtil.formatCnpj(filter.getCnpj())));
		if(filter.getDateBalance() != null)	query.where(balance.dateBalance.eq(filter.getDateBalance()));
		if(filter.getSyntheticId() != null) query.where(balance.syntheticId.eq(filter.getSyntheticId()));
		if(filter.getAnalyticId() != null)	query.where(balance.analyticId.eq(filter.getAnalyticId()));
		if(filter.getDescription() != null)	query.where(balance.description.contains(filter.getDescription()));

		totalElements = query.fetchCount();
		query.limit(pageable.getPageSize());
		query.offset(pageable.getPageSize() * pageable.getPageNumber());
        return new PageImpl<Balance>(query.fetch(), pageable, totalElements);
	}

}
