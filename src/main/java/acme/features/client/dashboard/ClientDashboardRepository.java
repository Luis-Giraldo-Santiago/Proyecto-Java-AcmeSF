/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.client.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Client;

@Repository
public interface ClientDashboardRepository extends AbstractRepository {

	// Consulta para obtener el porcentaje del número total de registros de progreso con completitud por debajo del 25%
	@Query("select count(pl) from ProgressLog pl where pl.contract.client = :client and pl.completeness <=25 and pl.published=true")
	Optional<Integer> percentageOfTotalNumberCompleteness25(Client client);

	// Consulta para obtener el porcentaje del número total de registros de progreso con completitud entre 25% y 50%
	@Query("select count(pl) from ProgressLog pl where pl.contract.client = :client and pl.completeness <= 50 and 25 <= pl.completeness and pl.published=true")
	Optional<Integer> percentageOfTotalNumberCompleteness25At50(Client client);

	// Consulta para obtener el porcentaje del número total de registros de progreso con completitud entre 50% y 75%
	@Query("select count(pl) from ProgressLog pl where pl.contract.client = :client and pl.completeness <= 75 and 50 <= pl.completeness and pl.published=true")
	Optional<Integer> percentageOfTotalNumberCompleteness50at75(Client client);

	// Consulta para obtener el porcentaje del número total de registros de progreso con completitud por encima del 75%
	@Query("select count(pl) from ProgressLog pl where pl.contract.client = :client and 75 <= pl.completeness and pl.published=true")
	Optional<Integer> percentageOfTotalNumberCompletenessMore75(Client client);

	// Consulta para obtener el promedio del presupuesto de los contratos
	@Query("select avg(c.budget) from Contract c where c.client = :client and c.published=true")
	Optional<Double> averageBudgetOfContract(Client client);

	// Consulta para obtener la desviación del presupuesto de los contratos
	@Query("select stddev(c.budget) from Contract c where c.client = :client and c.published=true")
	Optional<Double> deviationBudgetOfContract(Client client);

	// Consulta para obtener el presupuesto mínimo de los contratos
	@Query("select min(c.budget) from Contract c where c.client = :client and c.published=true")
	Optional<Double> minimumBudgetOfContract(Client client);

	// Consulta para obtener el presupuesto máximo de los contratos
	@Query("select max(c.budget) from Contract c where c.client = :client and c.published=true")
	Optional<Double> maximumBudgetOfContract(Client client);

	@Query("select c from Client c where c.userAccount.id = :id")
	Client findOneClientByUserAccountId(int id);

	// Proporciona el numero de historias de usuarios total del manager
	@Query("select count(c) from Contract c WHERE (c.client = :client AND c.published = true)")
	int findNumberContracts(Client client);

	// Proporciona el numero de proyectos total del manager
	@Query("select count(pl) from ProgressLog pl WHERE (pl.contract.client = :client AND pl.published = true)")
	int findNumberProgressLogs(Client client);

}
