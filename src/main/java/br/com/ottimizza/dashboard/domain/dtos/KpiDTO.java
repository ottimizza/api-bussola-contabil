package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import br.com.ottimizza.dashboard.models.KpiDetail;
import lombok.Data;

@Data
public class KpiDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private BigInteger id;
	private BigInteger companyId;
	private String kpiAlias;
	private String title;
	private String subtitle;
	private String description;
	private Short graphType;
	private String columnX0Label;
	private String label;
	private String label2;
	private String label3;
	private String label4;
	private String valueType;
	private String labelStringArray;
	private List<String> labelArray;
	private List<KpiDetail> kpiDetail;
	private String chartType;
	private String chartOptions;
	private Integer graphOrder;
	private Boolean visible;
}
