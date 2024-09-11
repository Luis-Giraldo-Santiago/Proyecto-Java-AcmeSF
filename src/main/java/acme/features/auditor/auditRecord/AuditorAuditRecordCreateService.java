
package acme.features.auditor.auditRecord;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student5.AuditRecord;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository	repository;

	private Date							lowestMoment	= Date.from(Instant.parse("1999-12-31T23:00:00Z"));

	private Date							maximumMoment	= Date.from(Instant.parse("2022-07-29T22:00:00Z"));

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;

		masterId = super.getRequest().getData("masterId", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);
		status = codeAudit != null && !codeAudit.isPublished() && super.getRequest().getPrincipal().hasRole(codeAudit.getAuditor());

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		AuditRecord object;
		CodeAudit codeAudit;

		codeAudit = this.repository.findOneCodeAuditById(super.getRequest().getData("masterId", int.class));
		object = new AuditRecord();
		object.setCodeAudit(codeAudit);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		super.bind(object, "code", "auditPeriodStart", "auditPeriodEnd", "mark", "link");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		int masterId;
		CodeAudit codeAudit;
		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(super.getRequest().getData("masterId", int.class));

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord existing;

			existing = this.repository.findOneAuditRecordByCode(object.getCode());
			super.state(existing == null, "code", "auditor.auditRecord.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("auditPeriodStart")) {
			Date startDate = object.getAuditPeriodStart();

			super.state(MomentHelper.isAfterOrEqual(startDate, this.lowestMoment), "auditPeriodStart", "auditor.auditRecord.form.error.startDateError");

			super.state(MomentHelper.isBeforeOrEqual(startDate, this.maximumMoment), "auditPeriodStart", "auditor.codeAudit.form.error.maxStartDateError");
		}
		if (!super.getBuffer().getErrors().hasErrors("auditPeriodEnd")) {
			Date endDate = object.getAuditPeriodEnd();

			super.state(MomentHelper.isAfterOrEqual(endDate, this.lowestMoment), "auditPeriodEnd", "auditor.auditRecord.form.error.endDateError");

			super.state(MomentHelper.isBeforeOrEqual(endDate, this.maximumMoment), "auditPeriodEnd", "auditor.codeAudit.form.error.maxEndDateError");
		}
		if (!super.getBuffer().getErrors().hasErrors("auditPeriodEnd") && !super.getBuffer().getErrors().hasErrors("startDate")) {
			Date startDate = object.getAuditPeriodStart();
			Date endDate = object.getAuditPeriodEnd();

			super.state(this.isPassedOneHourAtLeast(endDate, startDate), "auditPeriodEnd", "auditor.auditRecord.form.error.notTimeEnough");
		}
		if (!super.getBuffer().getErrors().hasErrors("auditPeriodStart")) {
			Date startDate = object.getAuditPeriodStart();

			super.state(MomentHelper.isAfterOrEqual(startDate, this.lowestMoment) && MomentHelper.isAfterOrEqual(startDate, codeAudit.getExecutionDate()), "auditPeriodStart", "auditor.auditRecord.form.error.codeAuditDate");

		}

	}

	private boolean isPassedOneHourAtLeast(final Date date1, final Date date2) {
		boolean res = false;
		long diffInMillies = date1.getTime() - date2.getTime();
		long diffInHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		if (diffInHours >= 1L)
			res = true;
		return res;
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		CodeAudit codeAudit;

		codeAudit = this.repository.findOneCodeAuditById(super.getRequest().getData("masterId", int.class));

		object.setPublished(false);
		object.setCodeAudit(codeAudit);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices choices;
		Dataset dataset;
		CodeAudit codeAudit;

		choices = SelectChoices.from(Mark.class, object.getMark());
		codeAudit = this.repository.findOneCodeAuditById(super.getRequest().getData("masterId", int.class));

		dataset = super.unbind(object, "code", "auditPeriodStart", "auditPeriodEnd", "mark", "link", "published");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));
		dataset.put("codeAuditCode", codeAudit.getCode());
		dataset.put("mark", choices.getSelected().getKey());
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}

}
