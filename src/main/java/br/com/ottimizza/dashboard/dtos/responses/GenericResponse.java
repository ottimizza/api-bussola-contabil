package br.com.ottimizza.dashboard.dtos.responses;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T record;
	
	private List<T> records;
	
	public GenericResponse(T record) {
		this.record = record;
	}

	public GenericResponse(List<T> records) {
		this.records = records;
	}
	
}
