
package acme.features.auditor.codeAudit;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student5.AuditRecord;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.entities.student5.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository	repository;

	private Date						lowestMoment	= Date.from(Instant.parse("1999-12-31T23:00:00Z"));

	private Date						maximumMoment	= Date.from(Instant.parse("2022-07-29T22:00:00Z"));

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		CodeAudit codeAudit;
		Auditor auditor;

		masterId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(masterId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && !codeAudit.isPublished() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link");

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "auditor.codeAudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("executionDate")) {
			Date executionDate = object.getExecutionDate();

			super.state(MomentHelper.isAfterOrEqual(executionDate, this.lowestMoment), "executionDate", "auditor.codeAudit.form.error.executionDateError");

			super.state(MomentHelper.isBeforeOrEqual(executionDate, this.maximumMoment), "executionDate", "auditor.codeAudit.form.error.maxExecutionDateError");
		}
		if (!super.getBuffer().getErrors().hasErrors("mark")) {
			Mark mark = object.getMark(this.repository.findManyMarksByCodeAuditId(object.getId()));
			Collection<AuditRecord> all = this.repository.findManyAuditRecordsByCodeAuditId(object.getId());
			Collection<AuditRecord> published = this.repository.findManyPublishedAuditRecordByCodeAuditId(object.getId());

			super.state(mark != null && !mark.equals(Mark.F) && !mark.equals(Mark.F_MINUS), "mark", "auditor.codeAudit.form.error.low");
			super.state(all.size() == published.size(), "mark", "auditor.codeAudit.form.error.auditRecordsNotPublished");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;
		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Mark mark;
		Dataset dataset;

		mark = object.getMark(this.repository.findManyMarksByCodeAuditId(object.getId()));
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "published");
		dataset.put("mark", mark == null ? null : mark.getMark());
		dataset.put("types", SelectChoices.from(Type.class, object.getType()));
		super.getResponse().addData(dataset);
	}
}
