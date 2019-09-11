package br.com.ottimizza.dashboard.services;

import java.math.BigInteger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.repositories.annotation.AnnotationRepository;

@Service
public class AnnotationService {

	@Inject
	private AnnotationRepository repository;
	
	public Annotation save(Annotation annotation) throws Exception {
		return repository.save(annotation);
	}
	
	public Annotation findById(BigInteger id) throws Exception{
		Annotation note = new Annotation();
		try {
			note = repository.findById(id).get();
		}catch (Exception e) { }
		
		return note;
	}
}
