
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int managerId;
		int numberUserStories;
		int numberProjects;
		int totalNumberMustUserStories;
		int totalNumberShouldUserStories;
		int totalNumberCouldUserStories;
		int totalNumberWontUserStories;
		Double averageEstimatedCostUserStories;
		Double deviationEstimatedCostUserStories;
		Integer minimunEstimatedCostUserStories;
		Integer maximumEstimatedCostUserStories;
		Double averageCostProjects;
		Double deviationCostProjects;
		Integer minimunCostProjects;
		Integer maximumCostProjects;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		numberUserStories = this.repository.findNumberUserStories(managerId);
		numberProjects = this.repository.findNumberProjects(managerId);
		totalNumberMustUserStories = this.repository.totalNumberOfMustUserStories(managerId);
		totalNumberShouldUserStories = this.repository.totalNumberOfShouldUserStories(managerId);
		totalNumberCouldUserStories = this.repository.totalNumberOfCouldUserStories(managerId);
		totalNumberWontUserStories = this.repository.totalNumberOfWontUserStories(managerId);

		if (numberUserStories >= 2) {
			averageEstimatedCostUserStories = this.repository.averageEstimatedCostUserStories(managerId);
			deviationEstimatedCostUserStories = this.repository.deviationEstimatedCostUserStories(managerId);
		} else {
			averageEstimatedCostUserStories = null;
			deviationEstimatedCostUserStories = null;

		}

		minimunEstimatedCostUserStories = this.repository.minimunEstimatedCostUserStories(managerId);
		maximumEstimatedCostUserStories = this.repository.maximumEstimatedCostUserStories(managerId);

		if (numberProjects >= 2) {
			averageCostProjects = this.repository.averageCostProjects(managerId);
			deviationCostProjects = this.repository.deviationCostProjects(managerId);
		} else {
			averageCostProjects = null;
			deviationCostProjects = null;
		}

		minimunCostProjects = this.repository.minimunCostProjects(managerId);
		maximumCostProjects = this.repository.maximumCostProjects(managerId);

		dashboard = new ManagerDashboard();
		dashboard.setTotalNumberMustUserStories(totalNumberMustUserStories);
		dashboard.setTotalNumberShouldUserStories(totalNumberShouldUserStories);
		dashboard.setTotalNumberCouldUserStories(totalNumberCouldUserStories);
		dashboard.setTotalNumberWontUserStories(totalNumberWontUserStories);
		dashboard.setAverageEstimatedCostUserStories(averageEstimatedCostUserStories);
		dashboard.setDeviationEstimatedCostUserStories(deviationEstimatedCostUserStories);
		dashboard.setMinimunEstimatedCostUserStories(minimunEstimatedCostUserStories);
		dashboard.setMaximumEstimatedCostUserStories(maximumEstimatedCostUserStories);
		dashboard.setAverageCostProjects(averageCostProjects);
		dashboard.setDeviationCostProjects(deviationCostProjects);
		dashboard.setMinimunCostProjects(minimunCostProjects);
		dashboard.setMaximumCostProjects(maximumCostProjects);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumberMustUserStories", "totalNumberShouldUserStories", // 
			"totalNumberCouldUserStories", "totalNumberWontUserStories", //
			"averageEstimatedCostUserStories", "deviationEstimatedCostUserStories", //
			"minimunEstimatedCostUserStories", "maximumEstimatedCostUserStories", "averageCostProjects", "deviationCostProjects", "minimunCostProjects", "maximumCostProjects");

		super.getResponse().addData(dataset);
	}

}
