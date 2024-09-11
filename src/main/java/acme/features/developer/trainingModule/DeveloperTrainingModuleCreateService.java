
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student1.Project;
import acme.entities.student3.Difficulty;
import acme.entities.student3.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		TrainingModule object;
		Developer developer;
		List<Project> projects = this.repository.findAllProjectsPublished().stream().toList();

		developer = this.repository.findDeveloperById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new TrainingModule();
		object.setPublished(false);
		object.setCreationMoment(MomentHelper.getCurrentMoment());
		object.setDeveloper(developer);
		object.setProject(projects.get(0));
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "details", "difficultyLevel", "link", "totalTime", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule existing;

			existing = this.repository.findOneTrainingModuleByCode(object.getCode());
			super.state(existing == null, "code", "developer.trainingModule.form.error.duplicated");
		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		int developerId;
		Collection<Project> projects;
		SelectChoices projectChoices;
		Dataset dataset;

		projects = this.repository.findAllProjectsPublished();

		projectChoices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project", "developer", "published");
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);

		final SelectChoices choices;
		choices = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficulties", choices);

		super.getResponse().addData(dataset);
	}

}
