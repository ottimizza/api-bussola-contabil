package br.com.ottimizza.dashboard.models;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.ottimizza.dashboard.models.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "annotations")
public class Annotation {

	
    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(name = "annotations_sequence", sequenceName = "annotations_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annotations_sequence")
    private BigInteger id;
	
	@Column(name = "fk_organizations_id", nullable = false)
    private BigInteger companyId;
	
   	private LocalDateTime createAt;
    
    @Column(name = "created_by", nullable = false)
    private String createdBy;
	
    @Column(name = "kpi_alias", nullable = false)
    private String kpiAlias;

    @Column(name = "description", nullable = true)
    private String description;

}
