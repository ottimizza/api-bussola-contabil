package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.models.Company;
import br.com.ottimizza.dashboard.models.Kpi;
import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.repositories.annotation.AnnotationRepository;
import br.com.ottimizza.dashboard.repositories.company.CompanyRepository;
import br.com.ottimizza.dashboard.repositories.user.UserRepository;

@Service
public class AnnotationService {

	@Inject
	private AnnotationRepository repository;
	
	@Inject
	private CompanyRepository companyRepository;
	
	@Inject
	private UserRepository userRepository;
	
	public Annotation save(Annotation annotation) throws Exception {
		try {
			Company company = companyRepository.findById(annotation.getCompany().getId()).get();
			annotation.setCompany(company);
		}catch (Exception e) { }
		
		try {
			User user = userRepository.findById(annotation.getUser().getId()).get();
			annotation.setUser(user);
		}catch (Exception e) { }
		
		return repository.save(annotation);
	}
	
//	public Annotation createAnnotation(BigInteger companyId, Annotation annotation) {
//		Company company = new Company();
//		Optional<Company> optionalCompany = companyRepository.findById(companyId);
//
//		try {
//			company = optionalCompany.get();
//			annotation.setCompany(company);
//		} catch (Exception e) { }
//		return repository.save(annotation);	
//	}
	
	public Annotation findById(BigInteger id) throws Exception{
		
		System.out.println(" ###### ");
		System.out.println("  >>>>>>> " + id);
		
		Annotation annotation = repository.findAnnotationById(id); //.orElse(null);
		System.out.println("  >>>>>>> " + id);
		
		System.out.println(" >>>>>>> " + (annotation == null || annotation.getId() == null));
		System.out.println(" ###### ");
		
		return annotation;
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

	public List<Annotation> findAll() {
		return 	repository.findAll();
	}
}
