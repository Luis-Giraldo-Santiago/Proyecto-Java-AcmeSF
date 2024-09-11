
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface ManagerDashboardRepository extends AbstractRepository {

	// Proporciona el número total de "Must" historias de usuario del manager
	@Query("SELECT COUNT(u) FROM UserStory u WHERE (u.priority = 0 AND u.manager.id = :managerId AND u.published = true)")
	int totalNumberOfMustUserStories(int managerId);

	// Proporciona el número total de "Should" historias de usuario del manager
	@Query("SELECT COUNT(u) FROM UserStory u WHERE (u.priority = 1 AND u.manager.id = :managerId AND u.published = true)")
	int totalNumberOfShouldUserStories(int managerId);

	// Proporciona el número total de "Could" historias de usuario del manager
	@Query("SELECT COUNT(u) FROM UserStory u WHERE (u.priority = 2 AND u.manager.id = :managerId AND u.published = true)")
	int totalNumberOfCouldUserStories(int managerId);

	// Proporciona el número total de "Wont" historias de usuario del manager
	@Query("SELECT COUNT(u) FROM UserStory u WHERE (u.priority = 3 AND u.manager.id = :managerId AND u.published = true)")
	int totalNumberOfWontUserStories(int managerId);

	// Proporciona el coste medio estimado de todas las historias de usuario del manager
	@Query("SELECT AVG(u.estimatedCost) FROM UserStory u WHERE (u.manager.id = :managerId AND u.published = true)")
	Double averageEstimatedCostUserStories(int managerId);

	// Proporciona la desviación del coste estimado de todas las historias de usuario del manager
	@Query("SELECT STDDEV(u.estimatedCost) FROM UserStory u WHERE (u.manager.id = :managerId AND u.published = true)")
	Double deviationEstimatedCostUserStories(int managerId);

	// Proporciona el mínimo coste estimado de todas las historias de usuario del manager
	@Query("SELECT MIN(u.estimatedCost) FROM UserStory u WHERE (u.manager.id = :managerId AND u.published = true)")
	Integer minimunEstimatedCostUserStories(int managerId);

	// Proporciona el máximo coste estimado de todas las historias de usuario del manager
	@Query("SELECT MAX(u.estimatedCost) FROM UserStory u WHERE (u.manager.id = :managerId AND u.published = true)")
	Integer maximumEstimatedCostUserStories(int managerId);

	// Proporciona la media de coste de todos los proyectos del manager
	@Query("SELECT AVG(p.cost) FROM Project p WHERE (p.manager.id = :managerId AND p.published = true)")
	Double averageCostProjects(int managerId);

	// Proporciona la desviación del coste de todos los proyectos del manager
	@Query("SELECT STDDEV(p.cost) FROM Project p WHERE (p.manager.id = :managerId AND p.published = true)")
	Double deviationCostProjects(int managerId);

	// Proporciona el mínimo coste de todos los proyectos del manager
	@Query("SELECT MIN(p.cost) FROM Project p WHERE (p.manager.id = :managerId AND p.published = true)")
	Integer minimunCostProjects(int managerId);

	// Proporciona el máximo coste de todos los proyectos del manager
	@Query("SELECT MAX(p.cost) FROM Project p WHERE (p.manager.id = :managerId AND p.published = true)")
	Integer maximumCostProjects(int managerId);

	// Proporciona el numero de historias de usuarios total del manager
	@Query("select count(u) from UserStory u WHERE (u.manager.id = :managerId AND u.published = true)")
	int findNumberUserStories(int managerId);

	// Proporciona el numero de proyectos total del manager
	@Query("select count(p) from Project p WHERE (p.manager.id = :managerId AND p.published = true)")
	int findNumberProjects(int managerId);

}
