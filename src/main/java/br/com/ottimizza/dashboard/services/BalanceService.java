package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.client.OAuthClient;
import br.com.ottimizza.dashboard.domain.dtos.BalanceDTO;
import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.repositories.balance.BalanceRepository;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;

@Service
public class BalanceService {

	@Inject
	private BalanceRepository repository;
	
	@Inject
	private CompanyRepository companyRepository;
	
	@Inject
	OAuthClient oauthClient;
	
	public Balance save(Balance balance) throws Exception {
		return repository.save(balance);
	}
	
	public Balance findById(BigInteger id) throws Exception{
		return repository.findById(id).orElse(null);
	}
	
	public JSONObject delete(BigInteger balanceId) {
		JSONObject response = new JSONObject();
        try {
            repository.deleteById(balanceId);
            response.put("status", "Success");
            response.put("message", "Registro excluído com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
	}

	public JSONObject deleteByCnpjAndDate(String cnpj, LocalDate dateBalance) throws Exception {
		JSONObject response = new JSONObject();
		List<Balance> balances = repository.findBalanceByCnpjAndData(cnpj, dateBalance);

		for (Balance balance : balances) {
			try {
				repository.delete(balance);
				if(response.has("status") && !response.optString("status").contains("Error")) {
					response.put("status", "Success");
					response.put("message", "Excluído com sucesso!");
				}
			} catch (Exception e) {
				response.put("status", "Error");
				response.put("message", "Houve um problema ao excluir!");
			}
		}
		return response;
	}
	
	public JSONObject updateById(BigInteger balanceId, Balance balance) throws NoResultException, Exception {
		JSONObject response = new JSONObject();
		try {
			balance.setId(repository.findById(balanceId).get().getId());
			repository.save(balance);

			response.put("status", "Success");
			response.put("message", "Atualizado com sucesso!");

		} catch (NoResultException e0) {
			response.put("status", "error");
			response.put("message", "Problema ao encontrar!");
			throw new NoResultException(response.toString());
		} catch (Exception e1) {
			response.put("status", "Error");
			response.put("message", "Houve um problema ao atualizar!");
			throw new Exception(response.toString());
		}
		return response;
	}
	
	public Balance createBalance(BigInteger companyId, Balance balance) {
		
		try {
			balance.setCompanyId(companyId);
			return repository.save(balance);
		} catch (Exception e) { }
		
		return new Balance();
	}
	
	public Page<BalanceDTO> findAll(BalanceDTO filter, int pageIndex, int pageSize, String authorization) {
		return repository.findAll(filter, PageRequest.of(pageIndex, pageSize)).map(BalanceDTO::fromEntity);
	}

	public JSONObject notActive(BalanceDTO filter, String authorization) {
		JSONObject response = new JSONObject();
		BigInteger companyId = companyRepository.findByCnpj(filter.getCnpj()).getId();
		try {
			repository.notActive(companyId, filter.getDateBalance());
			response.put("status", "Success");
			response.put("message", "Realizado com sucesso!");
		}catch (Exception e) {
			response.put("status", "Error");
			response.put("message", "Houve um problema ao excluir!");
		}
		return response;
	}
}
