
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
import acme.entities.student1.Project;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.entities.student5.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository	repository;

	private Date						lowestMoment	= Date.from(Instant.parse("1999-12-31T23:00:00Z"));

	private Date						maximumMoment	= Date.from(Instant.parse("2022-07-29T22:00:00Z"));

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;

		auditor = this.repository.findAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setPublished(false);
		object.setAuditor(auditor);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "mark", "link", "project");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null, "code", "auditor.codeAudit.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("executionDate")) {
			Date executionDate = object.getExecutionDate();

			super.state(MomentHelper.isAfterOrEqual(executionDate, this.lowestMoment), "executionDate", "auditor.codeAudit.form.error.executionDateError");

			super.state(MomentHelper.isBeforeOrEqual(executionDate, this.maximumMoment), "executionDate", "auditor.codeAudit.form.error.maxExecutionDateError");
		}
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Collection<Project> projects;
		SelectChoices choices;
		Mark mark;
		Dataset dataset;
		projects = this.repository.findAllProjectsPublished();
		choices = SelectChoices.from(projects, "code", object.getProject());
		mark = object.getMark(this.repository.findManyMarksByCodeAuditId(object.getId()));
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
		dataset.put("mark", mark == null ? null : mark.getMark());
		dataset.put("types", SelectChoices.from(Type.class, object.getType()));
		dataset.put("auditor", object.getAuditor().getUserAccount().getUsername());
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);
		super.getResponse().addData(dataset);
	}
}
