package br.com.ottimizza.dashboard.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.ottimizza.dashboard.models.OrganizationVariable;
import br.com.ottimizza.dashboard.models.Variable;
import lombok.Data;

@Data
public class VariableDTO implements Serializable{

	/*
	 * classe trabalha tanto com 'Variables' quanto com 'OrganizationVariables'
	 * */
	private static final long serialVersionUID = 1L;
	// from Variables
	
	private BigInteger id;				//empresa
	
	private BigInteger companyId;		//empresa
	private String variableCode;
	private String name;
	
	private BigInteger variableId;

	private BigInteger scriptId;
	
	private Short originValue;
	private Boolean absoluteValue;
	
	//from organizationVariable
	private BigInteger accountingId;	//contabilidade  accounting
	private String accountingCode;
	
	private String kpiAlias;
	private String description;
	
	// IN
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private String scriptDescription;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String cnpj;
	
    @JsonInclude(JsonInclude.Include.NON_NULL)
	private Integer type;
	
	

	public static Variable variableDtoToVariable(VariableDTO variableDto) {
		Variable variable = new Variable();
		
		if(variableDto.getId() != null)				variable.setId(variableDto.getId());
		if(variableDto.getVariableCode() != null)	variable.setVariableCode(variableDto.getVariableCode());
		if(variableDto.getName() != null)			variable.setName(variableDto.getName());
		if(variableDto.getScriptId() != null)		variable.setScriptId(variableDto.getScriptId());
		if(variableDto.getAccountingCode() != null)	variable.setAccountingCode(variableDto.getAccountingCode());
		if(variableDto.getOriginValue() != null)	variable.setOriginValue(variableDto.getOriginValue());
		if(variableDto.getKpiAlias() != null)		variable.setKpiAlias(variableDto.getKpiAlias());
		if(variableDto.getDescription() != null)	variable.setDescription(variableDto.getDescription());
		if(variableDto.getAccountingId() != null)	variable.setAccountingId(variableDto.getAccountingId());
		if(variableDto.getAbsoluteValue() != null)	variable.setAbsoluteValue(variableDto.getAbsoluteValue());

		return variable;
	}

	public static VariableDTO variableToVariableDto(Variable variable) {
		VariableDTO variableDto = new VariableDTO();
		
		if(variable.getId() != null)			variableDto.setId(variable.getId());
		if(variable.getVariableCode() != null)	variableDto.setVariableCode(variable.getVariableCode());
		if(variable.getName() != null)			variableDto.setName(variable.getName());
		if(variable.getScriptId() != null)		variableDto.setScriptId(variable.getScriptId());
		if(variable.getAccountingCode() != null) variableDto.setAccountingCode(variable.getAccountingCode());
		if(variable.getOriginValue() != null)	variableDto.setOriginValue(variable.getOriginValue());
		if(variable.getKpiAlias() != null)		variableDto.setKpiAlias(variable.getKpiAlias());
		if(variable.getDescription() != null)	variableDto.setDescription(variable.getDescription());
		if(variable.getAccountingCode() != null)variableDto.setAccountingId(variable.getAccountingId());
		if(variable.getAbsoluteValue() != null)	variableDto.setAbsoluteValue(variable.getAbsoluteValue());

		return variableDto;
	}
	
	public static OrganizationVariable variableDtoToOrganizationVariable(VariableDTO variableDto) {
		OrganizationVariable orgVariable = new OrganizationVariable();

		if(variableDto.getId() != null)				orgVariable.setId(variableDto.getId());
		if(variableDto.getVariableId() != null)		orgVariable.setVariableId(variableDto.getVariableId());
		if(variableDto.getCompanyId() != null)		orgVariable.setOrganizationId(variableDto.getCompanyId()); // company = organization
		if(variableDto.getScriptId() != null)		orgVariable.setScriptId(variableDto.getScriptId());
		if(variableDto.getAccountingCode() != null)	orgVariable.setAccountingCode(variableDto.getAccountingCode());
		if(variableDto.getOriginValue() != null)	orgVariable.setOriginValue(variableDto.getOriginValue());
		if(variableDto.getAbsoluteValue() != null)	orgVariable.setAbsoluteValue(variableDto.getAbsoluteValue());
		if(variableDto.getVariableCode() != null)	orgVariable.setVariableCode(variableDto.getVariableCode());
		return orgVariable;
	}

	public static VariableDTO organizationVariableToVariableDto(OrganizationVariable organizationVariable) {
		VariableDTO variableDto = new VariableDTO();
		
		if(organizationVariable.getId() != null)				variableDto.setId(organizationVariable.getId());		
		if(organizationVariable.getVariableId() != null)		variableDto.setVariableId(organizationVariable.getVariableId());
		if(organizationVariable.getOrganizationId() != null)	variableDto.setCompanyId(organizationVariable.getOrganizationId()); // company = organization
		if(organizationVariable.getScriptId() != null)			variableDto.setScriptId(organizationVariable.getScriptId());
		if(organizationVariable.getAccountingCode() != null)	variableDto.setAccountingCode(organizationVariable.getAccountingCode());
		if(organizationVariable.getOriginValue() != null)		variableDto.setOriginValue(organizationVariable.getOriginValue());
		if(organizationVariable.getAbsoluteValue() != null)		variableDto.setAbsoluteValue(organizationVariable.getAbsoluteValue());
		if(organizationVariable.getVariableCode() != null)		variableDto.setVariableCode(organizationVariable.getVariableCode());
		return variableDto;
	} 
	
	public VariableDTO() {
		super();
	}

	public VariableDTO(BigInteger id, BigInteger companyId, String variableCode, String name, BigInteger variableId,
			BigInteger scriptId, Short originValue, Boolean absoluteValue, BigInteger accountingId,
			String accountingCode, String kpiAlias, String description) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.variableCode = variableCode;
		this.name = name;
		this.variableId = variableId;
		this.scriptId = scriptId;
		this.originValue = originValue;
		this.absoluteValue = absoluteValue;
		this.accountingId = accountingId;
		this.accountingCode = accountingCode;
		this.kpiAlias = kpiAlias;
		this.description = description;
	}

	public VariableDTO(BigInteger id, BigInteger companyId, String variableCode, String name, BigInteger variableId,
			BigInteger scriptId, Short originValue, Boolean absoluteValue, BigInteger accountingId,
			String accountingCode, String kpiAlias, String description, String scriptDescription, String cnpj) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.variableCode = variableCode;
		this.name = name;
		this.variableId = variableId;
		this.scriptId = scriptId;
		this.originValue = originValue;
		this.absoluteValue = absoluteValue;
		this.accountingId = accountingId;
		this.accountingCode = accountingCode;
		this.kpiAlias = kpiAlias;
		this.description = description;
		this.scriptDescription = scriptDescription;
		this.cnpj = cnpj;
	}

}

