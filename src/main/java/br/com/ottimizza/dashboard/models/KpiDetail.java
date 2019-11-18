package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kpis_details", indexes = {@Index(name = "kpi_detail_index_seq", columnList = "fk_kpis_id,column_x_seq", unique = false)})
public class KpiDetail implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

    @ManyToOne
    @Getter
    @Setter
    @JoinColumn(name = "fk_kpis_id", referencedColumnName = "id", nullable = false)
    private Kpi kpiID;

    @Getter
    @Setter
    @Column(name = "column_x", length = 40, nullable = false)
    private String columnX;

    @Getter
    @Setter
    @Column(name = "column_y", length = 40, nullable = false)
    private String columnY;

    @Getter
    @Setter
    @Column(name = "column_z", length = 40, nullable = false)
    private String columnZ;
    
    @Getter
    @Setter
    @Column(name = "value_kpi_1", precision = 10, scale = 2, nullable = false)
    private double valorKPI;

    @Getter
    @Setter
    @Column(name = "value_kpi_2", precision = 10, scale = 2, nullable = true)
    private Double valorKPI2;
    
    @Getter
    @Setter
    @Column(name = "value_kpi_3", precision = 10, scale = 2, nullable = true)
    private Double valorKPI3;
    
    @Getter
    @Setter
    @Column(name = "value_kpi_4", precision = 10, scale = 2, nullable = true)
    private Double valorKPI4;
    
    @Getter
    @Setter
    @Column(name = "column_x_seq", nullable = true)
    private String columnXSeq = "";

    @Getter
    @Setter
    @Column(name = "column_x_bidding", length = 40, nullable = true)
    private String xBinding;
    
    @Getter
    @Setter
    @Column(name = "value_string_array", nullable = true)
    private String valorStringArray;
    
    @Setter
    @Transient
    private List<Double> valorArray;
    
	public List<Double> getValorArray() {
		List<Double> doubles = new ArrayList<Double>();
		if(valorStringArray != null && !valorStringArray.isEmpty()) {
			for (String string : valorStringArray.split(";")) {
				doubles.add(Double.parseDouble(string));
			}	
		}
		return doubles;
	}
}
