package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.math.BigInteger;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
    
    @Getter
    @Setter	
    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Kpi> kpis;

	
}
