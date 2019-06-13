package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiShort;
import br.com.ottimizza.dashboard.repositories.kpi.KpiRepository;
import br.com.ottimizza.dashboard.repositories.kpi.kpi_short.KpiShortRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class KpiService {

    @Inject
    private KpiRepository repository;

    @Inject
    private KpiShortRepository kpiShortRepository;

    // <editor-fold defaultstate="collapsed" desc="Save">
    public Kpi save(Kpi kpi) throws Exception {
        return repository.save(kpi);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Find by Id">
    public Optional<Kpi> findById(Long idKpi) throws Exception {
        return repository.findById(idKpi);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Find by List CNPJ">
    public List<KpiShort> findByListCNPJ(List<String> cnpj) throws Exception {
        return kpiShortRepository.findKpisByCNPJ(cnpj);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Update by Id">
    public JSONObject updateById(BigInteger idKpi, Kpi kpi) throws Exception {
        JSONObject response = new JSONObject();
        try {
            Optional<Kpi> kpiOptional = repository.findById(idKpi);

            if (!kpiOptional.isPresent())
                throw new NoResultException();

            kpi.setId(idKpi);
            repository.save(kpi);

            response.put("status", "sucess");
            response.put("message", "Atualizado kpi com sucesso!");

        } catch (NoResultException e0) {
            response.put("status", "error");
            response.put("message", "Problema ao encontrar a kpi!");
            throw new NoResultException(response.toString());
        } catch (Exception e1) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao atualizar!");
            throw new Exception(response.toString());
        }
        return response;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Delete by Id">
    public JSONObject delete(Long idKpi) throws Exception {
        JSONObject response = new JSONObject();
        try {
            repository.deleteById(idKpi);
            response.put("status", "sucess");
            response.put("message", "Excluído kpi com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
    }
    // </editor-fold>

}
