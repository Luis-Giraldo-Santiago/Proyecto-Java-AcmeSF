
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface DeveloperDashboardRepository extends AbstractRepository {

	// Proporciona el número total de módulos de entrenamiento con un momento de actualización
	@Query("SELECT COUNT(tm) FROM TrainingModule tm WHERE tm.updateMoment IS NOT NULL")
	Integer totalNumberOfTrainingModulesWithAnUpdatedMoment();

	// Proporciona el número total de sesiones de entrenamiento con un link
	@Query("SELECT COUNT(ts) FROM TrainingSession ts WHERE ts.link IS NOT NULL")
	Integer totalNumberOfTrainingSessionsWithALink();

	// Proporciona el promedio del tiempo de los módulos de entrenamiento
	@Query("SELECT AVG(tm.totalTime) FROM TrainingModule tm")
	Double averageTimeOfTrainingModules();

	// Proporciona la desviación del tiempo de los módulos de entrenamiento
	@Query("SELECT STDDEV(tm.totalTime) FROM TrainingModule tm")
	Double devidationTimeOfTrainingModules();

	// Proporciona el el tiempo mínimo de los módulos de entrenamiento
	@Query("SELECT MIN(tm.totalTime) FROM TrainingModule tm")
	Double minimumTimeOfTheTrainingModules();

	// Proporciona el tiempo máximo de los módulos de entrenamiento
	@Query("SELECT MAX(tm.totalTime) FROM TrainingModule tm")
	Double maximumTimeOfTheTrainingModules();

}
