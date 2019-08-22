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
@Table(name = "companies")
public class Company implements Serializable {
    
    @Id
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;
    
    @Getter
    @Setter
    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;
    
//    @Getter
//    @Setter
//    @OneToMany(mappedBy="companies")
//    private List<Kpi> kpis; 
//    @Fetch(FetchMode.SELECT)
//        targetEntity = Kpi.class,
//        cascade = CascadeType.ALL,
//        fetch = FetchType.EAGER,
//        orphanRemoval = true)
//    //@JoinColumn(name = "fk_company_id")
    
}
