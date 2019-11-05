package br.com.ottimizza.dashboard.domain.responses;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResponseObject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean hasNext;
	
	private boolean hasPrevious;

	private int pageIndex;
	
	private int pageSize;
    
	private int totalPages;
    
	private long totalElements;

	public PageInfoResponseObject(Page<?> page) {
		this.hasNext = page.hasNext();
		this.hasPrevious = page.hasPrevious();
		
		this.pageSize = page.getSize();
		this.pageIndex = page.getNumber();

		this.totalPages = page.getTotalPages();
		this.totalElements = page.getTotalElements();
	}

}
