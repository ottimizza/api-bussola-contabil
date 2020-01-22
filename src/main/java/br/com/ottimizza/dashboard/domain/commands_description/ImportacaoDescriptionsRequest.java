package br.com.ottimizza.dashboard.domain.commands_description;

import java.io.Serializable;
import java.util.List;

import br.com.ottimizza.dashboard.domain.dtos.DescriptionDTO;
import lombok.Data;

@Data
public class ImportacaoDescriptionsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cnpj;

    private String organizationId;

    private List<DescriptionDTO> descriptions;

}