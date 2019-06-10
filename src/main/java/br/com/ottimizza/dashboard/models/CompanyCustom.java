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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class CompanyCustom implements Serializable {
    
    @Id
    @Getter
    @Setter
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @Getter
    @Setter
    @Column(name = "cnpj", unique = true, nullable = false)
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "name", nullable = false)
    private String name;
    
    @Getter
    @Setter	
    @OneToMany(mappedBy = "CompanyCustom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<KpiCustom> kpisCustom = new ArrayList<>();
    

}
