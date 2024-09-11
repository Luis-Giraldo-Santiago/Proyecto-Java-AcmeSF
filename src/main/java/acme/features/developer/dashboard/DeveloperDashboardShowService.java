
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardShowService extends AbstractService<Developer, DeveloperDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		Integer totalNumberOfTrainingModulesWithAnUpdatedMoment;
		Integer totalNumberOfTrainingSessionsWithALink;
		Double averageTimeOfTrainingModules;
		Double devidationTimeOfTrainingModules;
		Double minimumTimeOfTheTrainingModules;
		Double maximumTimeOfTheTrainingModules;

		totalNumberOfTrainingModulesWithAnUpdatedMoment = this.repository.totalNumberOfTrainingModulesWithAnUpdatedMoment();
		totalNumberOfTrainingSessionsWithALink = this.repository.totalNumberOfTrainingSessionsWithALink();
		averageTimeOfTrainingModules = this.repository.averageTimeOfTrainingModules();
		devidationTimeOfTrainingModules = this.repository.averageTimeOfTrainingModules();
		minimumTimeOfTheTrainingModules = this.repository.minimumTimeOfTheTrainingModules();
		maximumTimeOfTheTrainingModules = this.repository.maximumTimeOfTheTrainingModules();

		dashboard = new DeveloperDashboard();
		dashboard.setTotalNumberOfTrainingModulesWithAnUpdatedMoment(totalNumberOfTrainingModulesWithAnUpdatedMoment);
		dashboard.setTotalNumberOfTrainingSessionsWithALink(totalNumberOfTrainingSessionsWithALink);
		dashboard.setAverageTimeOfTrainingModules(averageTimeOfTrainingModules);
		dashboard.setDevidationTimeOfTrainingModules(devidationTimeOfTrainingModules);
		dashboard.setMinimumTimeOfTheTrainingModules(minimumTimeOfTheTrainingModules);
		dashboard.setMaximumTimeOfTheTrainingModules(maximumTimeOfTheTrainingModules);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberOfTrainingModulesWithAnUpdatedMoment", "totalNumberOfTrainingSessionsWithALink", // 
			"averageTimeOfTrainingModules", "devidationTimeOfTrainingModules", //
			"minimumTimeOfTheTrainingModules", "maximumTimeOfTheTrainingModules");

		super.getResponse().addData(dataset);
	}

}
