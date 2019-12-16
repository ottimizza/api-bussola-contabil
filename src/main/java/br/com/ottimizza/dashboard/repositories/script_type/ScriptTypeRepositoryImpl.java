package br.com.ottimizza.dashboard.repositories.script_type;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.ottimizza.dashboard.domain.dtos.ScriptTypeDTO;
import br.com.ottimizza.dashboard.models.QScriptType;
import br.com.ottimizza.dashboard.models.ScriptType;

@Repository
public class ScriptTypeRepositoryImpl implements ScriptTypeRepositoryCustom {

	@PersistenceContext
	EntityManager em;
	
	QScriptType scriptType = QScriptType.scriptType;
	
	@Override
	public List<ScriptType> findAll(ScriptTypeDTO dto) {

		JPAQuery<ScriptType> query = new JPAQuery<ScriptType>(em).from(scriptType);
		if(dto.getId() != null)			 query.where(scriptType.id.eq(dto.getId()));
		if(dto.getAccounting() != null)	 query.where(scriptType.accounting.eq(dto.getAccounting()));
		if(dto.getDescription() != null) query.where(scriptType.description.eq(dto.getDescription()));

		return query.fetch();
	}

}
