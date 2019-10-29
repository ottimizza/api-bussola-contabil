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
@Table(name = "ot_contabilidades")
@NoArgsConstructor @AllArgsConstructor
public class Accounting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id 
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;

    @Getter @Setter
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Getter @Setter
    @Column(name = "nome_resumido", nullable = false)
    private String nomeResumido;

    @Getter @Setter
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

}
