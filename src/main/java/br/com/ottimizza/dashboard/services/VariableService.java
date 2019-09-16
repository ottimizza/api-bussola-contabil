package br.com.ottimizza.dashboard.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import br.com.ottimizza.dashboard.repositories.VariableRepository;

@Service
public class VariableService {

	@Inject
	VariableRepository repository;

}
