package br.com.ottimizza.dashboard.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class KpiDTO implements Serializable{

	private String title;
	private Double value;

}
