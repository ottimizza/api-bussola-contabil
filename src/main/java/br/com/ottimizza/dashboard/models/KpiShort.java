package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kpis")
public class KpiShort implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @Getter
    @Setter
    @Column(name = "kpi_alias", nullable = false)
    private String kpiAlias;

    @Getter
    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @Getter
    @Setter
    @Column(name = "subtitle", nullable = true)
    private String subtitle;

    @Getter
    @Setter
    @Column(name = "description", nullable = true)
    private String description;

    @Getter
    @Setter
    @Column(name = "graph_type", nullable = false)
    private Short graphType;

    @Getter
    @Setter
    @Column(name = "column_x0_label", nullable = false)
    private String columnX0Label;

//    @Getter
//    @Setter
//    @Fetch(FetchMode.SELECT)
//    @OneToMany(mappedBy="kpis",
//        cascade = CascadeType.ALL,
//        targetEntity = KpiDetail.class,
//        fetch = FetchType.EAGER,
//        orphanRemoval = true)
//    private List<KpiDetail> kpiDetails;
//    //@JoinColumn(name = "kpi_id")
    
}
