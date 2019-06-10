package br.com.ottimizza.dashboard.repositories.kpi_detail;

import br.com.ottimizza.dashboard.models.KpiDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiDetailRepository extends JpaRepository<KpiDetail, Long>, KpiDetailRepositoryCustom{
    
}
