package br.com.ottimizza.dashboard.domain.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfoResponseObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean hasNext;
	
	private boolean hasPrevious;

	private int pageIndex;
    
	private int totalPages;
    
	private long totalElements;

	public BalanceInfoResponseObject(Page<T> page) {
		super();
		this.hasNext = page.hasNext;
		this.hasPrevious = page.hasPrevious;
		this.pageIndex = page.getPageIndex;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

}
