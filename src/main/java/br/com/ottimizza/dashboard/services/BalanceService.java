package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.repositories.balance.BalanceRepository;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;

@Service
public class BalanceService {

	@Inject
	private BalanceRepository repository;
	
	@Inject
	private CompanyRepository companyRepository;
	
	public Balance save(Balance balance) throws Exception {
		return repository.save(balance);
	}
	
	public Balance findById(BigInteger id) throws Exception{
		return repository.findById(id).orElse(null);
	}
	
	public List<Balance> findAll() {
		return 	repository.findAll();
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
					response.put("status", "sucess");
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
	
	public List<Balance> findByCompanyId(BigInteger companyId) throws Exception {
		Optional<List<Balance>> optCompany = repository.findBalancesByCompanyId(companyId);

		try {
			return optCompany.get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return new ArrayList<Balance>();
	}

	public List<Balance> findByCnpj(String cnpj) {
		return repository.findByCnpj(cnpj);
	}

//	public Balance patch(BigInteger id, BalanceDTO balanceDTO, Principal principal) throws Exception {
//	Balance current = findById(id);
//	current = balanceDTO.patch(current);
//	return repository.save(current);
//}

}
