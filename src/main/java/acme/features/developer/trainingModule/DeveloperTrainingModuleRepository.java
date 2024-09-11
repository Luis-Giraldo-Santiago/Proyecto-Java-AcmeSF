
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student1.Project;
import acme.entities.student3.TrainingModule;
import acme.entities.student3.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select tm from TrainingModule tm where tm.developer.id = :developerId")
	Collection<TrainingModule> findManyTrainingModuleByDeveloperId(int developerId);

	@Query("select tm from TrainingModule tm where tm.id = :trainingModuleId")
	TrainingModule findOneTrainingModuleById(int trainingModuleId);

	@Query("select d from Developer d where d.id = :developerId")
	Developer findDeveloperById(int developerId);

	@Query("select tm from TrainingModule tm where tm.code = :code")
	TrainingModule findOneTrainingModuleByCode(String code);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select tm.project from TrainingModule tm where tm.developer.id = :developerId")
	Collection<Project> findManyProjectsByDeveloperId(int developerId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :trainingModuleId")
	Collection<TrainingSession> findManyTrainingSessionByTrainingModuleId(int trainingModuleId);

	@Query("select p from Project p where p.published = true")
	Collection<Project> findAllProjectsPublished();

	@Query("select tm from TrainingModule tm where tm.project.id = :id")
	Collection<TrainingModule> findTrainingModulesFromProject(int id);

}
