package br.com.ottimizza.dashboard.domain.responses;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenericPageableResponse<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<T> records;
	private PageInfoResponseObject pageInfo;
	
	public GenericPageableResponse(Page<T> page) {
		this.records = page.getContent();
		this.pageInfo = new PageInfoResponseObject(page);
	}
	
	
}
