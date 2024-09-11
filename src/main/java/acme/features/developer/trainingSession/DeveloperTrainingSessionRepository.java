
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student3.TrainingModule;
import acme.entities.student3.TrainingSession;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.id = :id")
	TrainingModule findOneTrainingModuleById(int id);

	@Query("select ts.trainingModule from TrainingSession ts where ts.id = :id")
	TrainingModule findOneTrainingModuleByTrainingSessionId(int id);

	@Query("select ts from TrainingSession ts where ts.id = :id")
	TrainingSession findOneTrainingSessionById(int id);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id")
	Collection<TrainingSession> findManyTrainingSessionByMasterId(int id);

	@Query("select ts from TrainingSession ts where ts.code = :code")
	TrainingSession findOneTrainingSessionByCode(String code);

}
