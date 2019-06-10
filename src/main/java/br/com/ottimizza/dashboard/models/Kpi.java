package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "kpis", indexes = {@Index(name = "kpi_index", columnList = "kpi_alias,fk_company_id", unique = true)})
public class Kpi implements Serializable {

    @Id
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "kpi_alias", nullable = false)
    private String kpiAlias;

    @Getter
    @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_company_id")
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
    @OneToMany(mappedBy = "kpiID", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KpiDetail> kpiDetail = new ArrayList<>();
  
}
