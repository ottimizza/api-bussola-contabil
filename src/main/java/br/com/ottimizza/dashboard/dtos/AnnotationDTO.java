package br.com.ottimizza.dashboard.dtos;

import java.math.BigInteger;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDTO {

	private BigInteger companyId;
    private BigInteger userId;
    
   	private LocalDate createAt;
    private String kpiAlias;
    private String description;
}
