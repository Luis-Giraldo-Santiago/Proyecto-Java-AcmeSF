
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;
		Integer totalInvoicesTaxLessEqual21;
		Integer totalSponsorshipsWithLink;
		Double averageSponsorships;
		Double deviationSponsorships;
		Double minimumSponsorships;
		Double maximunSponsorships;
		Double averageInvoices;
		Double deviationInvoices;
		Double minimumInvoices;
		Double maximunInvoices;

		totalInvoicesTaxLessEqual21 = this.repository.totalInvoicesTaxLessEqual21();
		totalSponsorshipsWithLink = this.repository.totalSponsorshipsWithLink();
		averageSponsorships = this.repository.averageSponsorships();
		deviationSponsorships = this.repository.deviationSponsorships();
		minimumSponsorships = this.repository.minimumSponsorships();
		maximunSponsorships = this.repository.maximunSponsorships();
		averageInvoices = this.repository.averageInvoices();
		deviationInvoices = this.repository.deviationInvoices();
		minimumInvoices = this.repository.minimumInvoices();
		maximunInvoices = this.repository.maximunInvoices();

		dashboard = new SponsorDashboard();
		dashboard.setTotalInvoicesTaxLessEqual21(totalInvoicesTaxLessEqual21);
		dashboard.setTotalSponsorshipsWithLink(totalSponsorshipsWithLink);
		dashboard.setAverageSponsorships(averageSponsorships);
		dashboard.setDeviationSponsorships(deviationSponsorships);
		dashboard.setMinimumSponsorships(minimumSponsorships);
		dashboard.setMaximunSponsorships(maximunSponsorships);
		dashboard.setAverageInvoices(averageInvoices);
		dashboard.setDeviationInvoices(deviationInvoices);
		dashboard.setMinimumInvoices(minimumInvoices);
		dashboard.setMaximunInvoices(maximunInvoices);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalInvoicesTaxLessEqual21", "totalSponsorshipsWithLink", // 
			"averageSponsorships", "deviationSponsorships", //
			"minimumSponsorships", "maximunSponsorships", //
			"averageInvoices", "deviationInvoices", //
			"minimumInvoices", "maximunInvoices");

		super.getResponse().addData(dataset);
	}
}
