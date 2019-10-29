package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Balance;
import br.com.ottimizza.dashboard.repositories.balance.BalanceRepository;

@Service
public class BalanceService {

	@Inject
	private BalanceRepository repository;
	
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

//	public Balance patch(BigInteger id, BalanceDTO balanceDTO, Principal principal) throws Exception {
//		Balance current = findById(id);
//		current = balanceDTO.patch(current);
//		return repository.save(current);
//	}
}
