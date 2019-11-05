package br.com.ottimizza.dashboard.models.kpis_details;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
public class KpiDetailID implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter @Setter
    @Column(name = "fk_kpis_id")
    private BigInteger kpiId;

    @Getter @Setter
    @Column(name = "column_x0")
    private String columnX0;

}
