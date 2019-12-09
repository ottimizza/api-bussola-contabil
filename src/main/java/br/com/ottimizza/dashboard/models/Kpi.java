package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kpis", indexes = {@Index(name = "kpi_index", columnList = "kpi_alias,fk_companies_id", unique = true)})
public class Kpi implements Serializable {

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
    @ManyToOne
    @JoinColumn(name = "fk_companies_id", referencedColumnName = "id", nullable = false)
    private Company company;

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
    
    @Getter
    @Setter
    @Column(name = "visible", nullable = true)
    private Boolean visible = true;
    
    @Getter
    @Setter
    @Column(name = "label", nullable = true)
    private String label;
    
    @Getter
    @Setter
    @Column(name = "label_2", nullable = true)
    private String label2;
    
    @Getter
    @Setter
    @Column(name = "label_3", nullable = true)
    private String label3;
    
    @Getter
    @Setter
    @Column(name = "label_4", nullable = true)
    private String label4;
    
    //tipo de dados valor, indice
    @Getter
	@Setter
    @Column(name = "value_type", nullable = true)
	private String valueType;

    @Getter
    @Setter
    @Column(name = "label_string_array", nullable = true)
    private String labelStringArray;

    @Setter
    @Transient
    private List<String> labelArray;
    
	public List<String> getLabelArray() {
		return Arrays.asList(labelStringArray.split(";"));
	}
	
	@Getter
    @Setter	
    @OneToMany(mappedBy = "kpiID", fetch = FetchType.EAGER)
	@OrderBy("column_x ASC")
    private List<KpiDetail> kpiDetail;

	//column pie line
	@Getter
	@Setter
    @Column(name = "chart_type", nullable = true)
	private String chartType;

	//parametors para o grafico
	@Getter
	@Setter
    @Column(name = "chart_options", nullable = true, length = 600)
	private String chartOptions;
	
	//para ordenacao dos kpis
	@Getter
	@Setter
    @Column(name = "graph_order", nullable = true)
	private Integer graphOrder;
	
}
