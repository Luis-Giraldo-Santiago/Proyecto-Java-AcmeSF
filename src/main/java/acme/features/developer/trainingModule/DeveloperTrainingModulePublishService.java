
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student1.Project;
import acme.entities.student3.Difficulty;
import acme.entities.student3.TrainingModule;
import acme.entities.student3.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingModuleRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		TrainingModule trainingModule;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		trainingModule = this.repository.findOneTrainingModuleById(masterId);
		developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && !trainingModule.isPublished() && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);
		object.setCreationMoment(MomentHelper.getCurrentMoment());

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "details", "difficultyLevel", "link", "totalTime");
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		Collection<TrainingSession> trainingSessions = this.repository.findManyTrainingSessionByTrainingModuleId(object.getId());
		super.state(!trainingSessions.isEmpty(), "*", "developer.trainingModule.form.error.noTrainingSession");

		if (!trainingSessions.isEmpty()) {
			int numTrainingSessionPublish = trainingSessions.stream().filter(TrainingSession::isPublished).toList().size();
			boolean allTrainingSessionsPublish = trainingSessions.size() == numTrainingSessionPublish;
			super.state(allTrainingSessionsPublish, "*", "developer.trainingModule.form.error.trainingSessionnotpublish");
		}

	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		Collection<TrainingSession> trainingSessions;

		trainingSessions = this.repository.findManyTrainingSessionByTrainingModuleId(object.getId());
		trainingSessions.stream().forEach(x -> x.setPublished(true));

		object.setPublished(true);
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
