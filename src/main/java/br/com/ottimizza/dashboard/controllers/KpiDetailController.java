package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.CompanyCustom;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.KpiDetail;
import br.com.ottimizza.dashboard.models.KpiDetailCustom;
import br.com.ottimizza.dashboard.services.KpiDetailService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kpi/detail")
public class KpiDetailController {
    
    @Inject
    KpiDetailService kpiService;
    
    @PostMapping("save")
    //<editor-fold defaultstate="collapsed" desc="Save kpi">
        public ResponseEntity<KpiDetail> saveKpi(@RequestBody KpiDetail kpi) throws Exception{
            return ResponseEntity.ok(kpiService.save(kpi));
        }
    //</editor-fold>
        
    @GetMapping("find/{id}")
    //<editor-fold defaultstate="collapsed" desc="Find kpi by id">
        public ResponseEntity<Optional<KpiDetail>> findKpiByID(@PathVariable("id") Long idKpi) throws Exception{
            return ResponseEntity.ok(kpiService.findById(idKpi));
        }
    //</editor-fold>
        
    @RequestMapping(value = "/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
    //<editor-fold defaultstate="collapsed" desc="Find company by ID">
        public ResponseEntity<List<KpiDetail>> findKpiDetailByCNPJ(@RequestBody Map<String, List<String>> body) throws Exception{
            List<String> listaCNPJ = body.get("cnpj");
            return ResponseEntity.ok(kpiService.findByListCNPJ(listaCNPJ));
        }
    //</editor-fold>
    @RequestMapping(value = "/custom/find/cnpj", method = RequestMethod.POST, consumes = "application/json")
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<List<CompanyCustom>> findCompaniesByCNPJCustom(@RequestBody Map<String, List<String>> body)
            throws Exception {
        List<String> listaCNPJ = body.get("cnpj");
        
        return ResponseEntity.ok(kpiService.findByListCNPJCustom(listaCNPJ));
    }
    
    
    // </editor-fold>
    @DeleteMapping("delete/{id}")
    //<editor-fold defaultstate="collapsed" desc="Delete kpi">
        public ResponseEntity<String> removeKpi(@PathVariable("id") Long idKpi) throws Exception{
            return ResponseEntity.ok(kpiService.delete(idKpi).toString());
        }
    //</editor-fold>
        
}
