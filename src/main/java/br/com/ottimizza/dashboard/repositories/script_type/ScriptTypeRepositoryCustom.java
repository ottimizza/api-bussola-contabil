package br.com.ottimizza.dashboard.repositories.script_type;

import java.util.List;

import br.com.ottimizza.dashboard.dtos.ScriptTypeDTO;
import br.com.ottimizza.dashboard.models.ScriptType;

public interface ScriptTypeRepositoryCustom {
//	ScriptTypeRepositoryImpl
	List<ScriptType> findAll(ScriptTypeDTO dto);

}
