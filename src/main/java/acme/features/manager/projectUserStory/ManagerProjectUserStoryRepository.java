
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student1.Project;
import acme.entities.student1.ProjectUserStory;
import acme.entities.student1.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select us from UserStory us where us.id = :userStoryId")
	UserStory findUserStoryById(int userStoryId);

	@Query("select pu from ProjectUserStory pu where (pu.project.id = :projectId and pu.userStory.id = :userStoryId)")
	ProjectUserStory findAssociationBetweenProjectIdAndUserStoryId(int projectId, int userStoryId);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select aw.userStory from ProjectUserStory aw where aw.project.id = :projectId")
	Collection<UserStory> findManyUserStoriesByProjectId(int projectId);

	@Query("select us from UserStory us where us.manager.id = :managerId")
	Collection<UserStory> findUserStoriesByManagerId(int managerId);

}
