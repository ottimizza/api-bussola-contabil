package br.com.ottimizza.dashboard.repositories.description;

import java.math.BigInteger;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.Description;

@Repository
public interface DescriptionRepository extends PagingAndSortingRepository<Description, BigInteger>, DescriptionRepositoryCustom{


}
