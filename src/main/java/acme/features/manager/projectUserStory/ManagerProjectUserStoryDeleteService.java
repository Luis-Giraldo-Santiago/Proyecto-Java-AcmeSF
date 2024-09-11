
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student1.Project;
import acme.entities.student1.ProjectUserStory;
import acme.entities.student1.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryDeleteService extends AbstractService<Manager, ProjectUserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Manager manager;
		int managerRequestId;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		manager = project == null ? null : project.getManager();
		managerRequestId = super.getRequest().getPrincipal().getActiveRoleId();
		if (manager != null)
			status = !project.isPublished() && super.getRequest().getPrincipal().hasRole(manager) && //
				manager.getId() == managerRequestId;
		else
			status = false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStory object;
		Project project;
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		object = new ProjectUserStory();
		object.setProject(project);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStory object) {
		assert object != null;

		UserStory userStory;
		int userStoryId;

		userStoryId = super.getRequest().getData("userStory", int.class);
		userStory = this.repository.findUserStoryById(userStoryId);

		object.setUserStory(userStory);
	}

	@Override
	public void validate(final ProjectUserStory object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("userStory"))
			super.state(object.getUserStory() != null, "userStory", "manager.projectUserStory.form.error.notSelected");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!object.getProject().isPublished(), "*", "manager.projectUserStory.form.error.published");

		if (!super.getBuffer().getErrors().hasErrors("*") && !super.getBuffer().getErrors().hasErrors("userStory")) {
			ProjectUserStory existing = this.repository.findAssociationBetweenProjectIdAndUserStoryId(object.getProject().getId(), object.getUserStory().getId());
			super.state(existing != null, "*", "manager.projectUserStory.form.error.mustBeDuplicated");
		}
	}

	@Override
	public void perform(final ProjectUserStory object) {
		assert object != null;

		ProjectUserStory projectUserStory = this.repository.findAssociationBetweenProjectIdAndUserStoryId(object.getProject().getId(), object.getUserStory().getId());

		this.repository.delete(projectUserStory);
	}

	@Override
	public void unbind(final ProjectUserStory object) {
		assert object != null;

		Dataset dataset;
		int projectId;
		Collection<UserStory> userStoriesAssociated;
		Project project;
		SelectChoices choices;

		dataset = super.unbind(object, "userStory", "project");

		projectId = super.getRequest().getData("projectId", int.class);
		dataset.put("projectId", projectId);

		userStoriesAssociated = this.repository.findManyUserStoriesByProjectId(projectId);

		project = this.repository.findProjectById(projectId);
		dataset.put("project", project);
		dataset.put("published", project.isPublished());

		choices = new SelectChoices();

		if (object.getUserStory() == null)
			choices.add("0", "-----", true);
		else
			choices.add("0", "-----", false);

		for (final UserStory us : userStoriesAssociated)
			if (object.getUserStory() != null && object.getUserStory().getId() == us.getId())
				choices.add(Integer.toString(us.getId()), us.getTitle() + " - " + Integer.toString(us.getEstimatedCost()) + " - " + us.getPriority(), true);
			else
				choices.add(Integer.toString(us.getId()), us.getTitle() + " - " + Integer.toString(us.getEstimatedCost()) + " - " + us.getPriority(), false);

		dataset.put("userStory", choices.getSelected().getKey());
		dataset.put("userStories", choices);

		super.getResponse().addData(dataset);
	}

}
