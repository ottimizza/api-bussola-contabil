package br.com.ottimizza.dashboard.models;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ottimizza.dashboard.models.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "annotations", indexes = {@Index(name = "annotations_index", columnList = "kpi_alias,fk_companies_id", unique = true)})
public class Annotation {

	@Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger id;

	@ManyToOne
    @JoinColumn(name = "fk_organizations_id", referencedColumnName = "id", nullable = false)
    private Company company;
	
    @Column(name = "create_at")
   	private LocalDate createAt;
    
//    @ManyToOne
//    @JoinColumn(name = "fk_user", referencedColumnName = "id", nullable = false)
//    private User user;
	
    @Column(name = "kpi_alias", nullable = false)
    private String kpiAlias;

    @Column(name = "description", nullable = true)
    private String description;

}
