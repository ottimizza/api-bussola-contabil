package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.dtos.AnnotationDTO;
import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.repositories.annotation.AnnotationRepository;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;

@Service
public class AnnotationService {

	@Inject
	private AnnotationRepository repository;
	
	@Inject
	private CompanyRepository companyRepository;
	
	public Annotation save(Annotation annotation) throws Exception {
		return repository.save(annotation);
	}
	
	
	public Annotation findById(BigInteger id) throws Exception{
		return repository.findAnnotationById(id);
	}
	
	public List<Annotation> findAll() {
		return 	repository.findAll();
	}
	
	public JSONObject delete(BigInteger annotationId) {
		JSONObject response = new JSONObject();
        try {
            repository.deleteById(annotationId);
            response.put("status", "sucess");
            response.put("message", "Anotação excluída com sucesso!");
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Houve um problema ao excluir!");
            return response;
        }
        return response;
	}

	public List<Annotation> findAnnotationList(AnnotationDTO annotation) {
		if(annotation.getCnpj() != null) {
			BigInteger companyId = companyRepository.findCompanyByCnpj(annotation.getCnpj()).getId();
			annotation.setOrganizationId(companyId.toString());
		}
		return repository.findAnnotations(annotation);
	}
	
}
