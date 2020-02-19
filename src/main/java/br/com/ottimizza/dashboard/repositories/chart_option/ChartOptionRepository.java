package br.com.ottimizza.dashboard.repositories.chart_option;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ottimizza.dashboard.models.ChartOption;

public interface ChartOptionRepository extends JpaRepository<ChartOption, BigInteger>, ChartOptionRepositoryCustom {

}
