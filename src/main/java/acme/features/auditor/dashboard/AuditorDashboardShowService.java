
package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.student5.Type;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorId;
		AuditorDashboard dashboard;
		int numStaticCodeAudits;
		int numDynamicCodeAudits;
		Double averageAuditRecords;
		Double deviationAuditRecords;
		Integer minNumAuditRecords;
		Integer maxNumAuditRecords;
		Double averageAuditRecordsPeriodLength;
		Double deviationAuditRecordsPeriodLength;
		Integer minAuditRecordsPeriodLength;
		Integer maxAuditRecordsPeriodLength;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		numStaticCodeAudits = this.repository.findNumCodeAuditsByTypeAndAuditorId(auditorId, Type.STATIC);
		numDynamicCodeAudits = this.repository.findNumCodeAuditsByTypeAndAuditorId(auditorId, Type.DYNAMIC);
		if (numStaticCodeAudits + numDynamicCodeAudits >= 2) {
			deviationAuditRecords = this.repository.findDeviationOfAuditRecordsPerCodeAuditsByAuditorId(auditorId);
			deviationAuditRecordsPeriodLength = this.repository.findDeviationTimePerAuditRecordByAuditorId(auditorId);
		} else {
			deviationAuditRecords = null;
			deviationAuditRecordsPeriodLength = null;
		}
		if (numStaticCodeAudits + numDynamicCodeAudits >= 1) {
			averageAuditRecords = this.repository.findAverageNumOfAuditRecordsPerCodeAudiByAuditorId(auditorId);
			minNumAuditRecords = this.repository.findMinNumOfAuditRecordsPerCodeAuditsByAuditorId(auditorId);
			maxNumAuditRecords = this.repository.findMaxNumOfAuditRecordsPerCodeAuditsByAuditorId(auditorId);
			averageAuditRecordsPeriodLength = this.repository.findAverageTimePerAuditRecordByAuditorId(auditorId);
			minAuditRecordsPeriodLength = this.repository.findMinTimePerAuditRecordByAuditorId(auditorId);
			maxAuditRecordsPeriodLength = this.repository.findMaxTimePerAuditRecordByAuditorId(auditorId);
		} else {
			averageAuditRecords = null;
			minNumAuditRecords = null;
			maxNumAuditRecords = null;
			averageAuditRecordsPeriodLength = null;
			minAuditRecordsPeriodLength = null;
			maxAuditRecordsPeriodLength = null;
		}

		dashboard = new AuditorDashboard();
		dashboard.setStaticCodeAudits(numStaticCodeAudits);
		dashboard.setDynamicCodeAudits(numDynamicCodeAudits);
		dashboard.setAverageNumAuditRecords(averageAuditRecords);
		dashboard.setDeviationNumAuditRecords(deviationAuditRecords);
		dashboard.setMinNumAuditRecords(minNumAuditRecords);
		dashboard.setMaxNumAuditRecords(maxNumAuditRecords);
		dashboard.setAverageTimeAuditRecords(averageAuditRecordsPeriodLength);
		dashboard.setDeviationTimeAuditRecords(deviationAuditRecordsPeriodLength);
		dashboard.setMinTimeAuditRecords(minAuditRecordsPeriodLength);
		dashboard.setMaxTimeAuditRecords(maxAuditRecordsPeriodLength);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "staticCodeAudits", "dynamicCodeAudits", "averageNumAuditRecords", "deviationNumAuditRecords", "minNumAuditRecords", "maxNumAuditRecords", "averageTimeAuditRecords", "deviationTimeAuditRecords", "minTimeAuditRecords",
			"maxTimeAuditRecords");

		super.getResponse().addData(dataset);
	}
}
