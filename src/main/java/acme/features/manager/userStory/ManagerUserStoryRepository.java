
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student1.Project;
import acme.entities.student1.ProjectUserStory;
import acme.entities.student1.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select us from UserStory us where us.id = :userStoryId")
	UserStory findUserStoryById(int userStoryId);

	@Query("select pu.userStory from ProjectUserStory pu where pu.project.id = :projectId")
	Collection<UserStory> findManyUserStoriesByProjectId(int projectId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("select pu from ProjectUserStory pu where pu.userStory.id = :userStoryId")
	Collection<ProjectUserStory> findManyProjectUserStoryByUserStoryId(int userStoryId);

	@Query("select us from UserStory us where us.manager.id = :managerId")
	Collection<UserStory> findManyUserStoriesByManagerId(int managerId);
}
