
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student1.Project;
import acme.entities.student3.Difficulty;
import acme.entities.student3.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

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
		status = super.getRequest().getPrincipal().hasRole(developer) && trainingModule != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int trainingModuleId;

		trainingModuleId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(trainingModuleId);

		super.getBuffer().addData(object);
	}

	//	@Override
	//	public void unbind(final TrainingModule object) {
	//		assert object != null;
	//
	//		int developerId;
	//		Collection<Project> projects;
	//		//SelectChoices choices;
	//		//SelectChoices difficultychoices;
	//		Dataset dataset;
	//
	//		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project", "developer", "published");
	//		dataset.put("project", object.getProject().getTitle());
	//
	//		super.getResponse().addData(dataset);
	//	}

	//	@Override
	//	public void unbind(final TrainingModule object) {
	//		assert object != null;
	//
	//		SelectChoices choices;
	//		SelectChoices projectChoices = new SelectChoices();
	//		Collection<Project> projects;
	//		projects = this.repository.findAllProjectsPublished();
	//		Dataset dataset;
	//
	//		choices = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
	//		projectChoices = SelectChoices.from(projects, "title", object.getProject());
	//
	//		dataset = super.unbind(object, "code", "creationMoment", "details", "updateMoment", "link", "totalTime", "published");
	//		dataset.put("developer", object.getDeveloper().getUserAccount().getUsername());
	//		dataset.put("difficultyLevel", choices.getSelected().getKey());
	//		dataset.put("difficulties", choices);
	//		dataset.put("projectTitle", object.getProject().getTitle());
	//		dataset.put("project", projectChoices.getSelected().getKey());
	//		dataset.put("projects", projectChoices);
	//
	//		super.getResponse().addData(dataset);
	//	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projectChoices;
		Collection<Project> projects;
		projects = this.repository.findAllProjectsPublished();
		Dataset dataset;

		choices = SelectChoices.from(Difficulty.class, object.getDifficultyLevel());
		projectChoices = SelectChoices.from(projects, "title", object.getProject());

		dataset = super.unbind(object, "code", "creationMoment", "details", "updateMoment", "link", "totalTime", "published");
		dataset.put("developer", object.getDeveloper().getUserAccount().getUsername());
		dataset.put("difficultyLevel", choices.getSelected().getKey());
		dataset.put("difficulties", choices);
		dataset.put("projectTitle", object.getProject().getTitle());
		dataset.put("project", projectChoices.getSelected().getKey());
		dataset.put("projects", projectChoices);

		super.getResponse().addData(dataset);
	}

}
