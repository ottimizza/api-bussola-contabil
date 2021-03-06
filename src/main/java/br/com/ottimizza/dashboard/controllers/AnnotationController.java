package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ottimizza.dashboard.domain.dtos.AnnotationDTO;
import br.com.ottimizza.dashboard.models.Annotation;
import br.com.ottimizza.dashboard.services.AnnotationService;

@RestController
@RequestMapping("/annotations")
public class AnnotationController {
	
	@Inject
	AnnotationService annotationService;

	@PostMapping
	public ResponseEntity<Annotation> saveAnnotation(@RequestBody Annotation annotation) throws Exception {
		annotation.setCreateAt(LocalDateTime.now(ZoneId.of("Brazil/East")));
		return ResponseEntity.ok(annotationService.save(annotation));
	}
	
	@PatchMapping("{id}")
    public ResponseEntity<Annotation> patch(@PathVariable("id") BigInteger id, @RequestBody AnnotationDTO annotationDTO, Principal principal)
            throws Exception {
        return ResponseEntity.ok(annotationService.patch(id, annotationDTO, principal));
    }
	
	@GetMapping("{id}")
	public ResponseEntity<Annotation> findAnnotationByID(@PathVariable("id") BigInteger annotationId) throws Exception {
		return (annotationService.findById(annotationId) != null) ? ResponseEntity.ok(annotationService.findById(annotationId)) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> removeAnnotation(@PathVariable("id") BigInteger annotationId) throws Exception {
		return ResponseEntity.ok(annotationService.delete(annotationId).toString());
	}
	
	@GetMapping
	public ResponseEntity<List<Annotation>> findAllAnnotations(@ModelAttribute AnnotationDTO filter, Principal principal) throws Exception {
		return ResponseEntity.ok(annotationService.findAnnotationList(filter));
	}
	
}