package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.KpiDetail;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;

import java.math.BigInteger;
import java.util.List;

@Service
public class KpiDetailService {
    
    @Inject
    private KpiDetailRepository repository;
    
    public KpiDetail save(KpiDetail kpi) throws Exception{
        return repository.save(kpi);
    }
    
    public Optional<KpiDetail> findById(BigInteger idKpi) throws Exception{
        return repository.findById(idKpi);
    }
    
    public List<KpiDetail> findByListCNPJ(List<String> cnpj)throws Exception{
        return repository.findKpiDetailsByCNPJ(cnpj);
    }
    
    public List<KpiDetail> findByListCNPJAndGraphType(List<String> cnpj, Short graphType,String kind)throws Exception{
        return repository.findKpiDetailsByCNPJAndGraphType(cnpj, graphType, kind);
    }
    
    public Boolean deleteById(BigInteger idKpiDetail)throws Exception{
        try{
        	repository.deleteById(idKpiDetail);
        	return true;
        }catch (Exception e) {
			return false;
		}
        
    }
    
    public JSONObject updateById(BigInteger idKpi, KpiDetail kpi) throws Exception{
        JSONObject response = new JSONObject();
        try {
        	Optional<KpiDetail> kpiOptional = repository.findById(idKpi);
            
            if(!kpiOptional.isPresent()) throw new NoResultException();
                
            kpi.setId(idKpi);
            repository.save(kpi);
            
            response.put("status","sucess");
            response.put("message","Atualizado detalhe do kpi com sucesso!");
            
        } catch (NoResultException e0) {
            response.put("status","error");
            response.put("message","Problema ao encontrar o detalhe do kpi!");
            throw new NoResultException(response.toString()) ;
        } catch (Exception e1) {
            response.put("status","Error");
            response.put("message","Houve um problema ao atualizar!");
            throw new Exception(response.toString()) ;
        }
        return response;
    }
    
    public JSONObject delete(BigInteger idKpi)throws Exception{
        JSONObject response = new JSONObject();
        try {
            repository.deleteById(idKpi);
            response.put("status","sucess");
            response.put("message","Excluído kpi detail com sucesso!");
        } catch (Exception e) {
            response.put("status","Error");
            response.put("message","Houve um problema ao excluir!");
            return response;
        }
        return response;
    }
    
}
