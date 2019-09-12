package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.Optional;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
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
	
	public Annotation createAnnotation(BigInteger companyId, Annotation annotation) {
		Company company = new Company();
		Optional<Company> optionalCompany = companyRepository.findById(companyId);

//		try {
//			company = optionalCompany.get();
//			annotation.setCompany(company);
//		} catch (Exception e) { }
		return repository.save(annotation);	
	}
	
	public Annotation findById(BigInteger id) throws Exception{
		Annotation note = new Annotation();
		try {
			note = repository.findById(id).get();
		}catch (Exception e) { }
		
		return note;
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
}
