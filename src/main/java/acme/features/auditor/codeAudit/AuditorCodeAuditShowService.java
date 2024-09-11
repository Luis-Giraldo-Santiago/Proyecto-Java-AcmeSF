
package acme.features.auditor.codeAudit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.entities.student5.Type;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		Auditor auditor;
		int auditorRequestId;
		int codeAuditId;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);
		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		auditorRequestId = super.getRequest().getPrincipal().getActiveRoleId();
		if (auditor != null)
			status = super.getRequest().getPrincipal().hasRole(auditor) && auditor.getId() == auditorRequestId;
		else
			status = false;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int codeAuditId;

		codeAuditId = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(codeAuditId);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {

		Mark mark;
		assert object != null;

		Dataset dataset;

		mark = object.getMark(this.repository.findManyMarksByCodeAuditId(object.getId()));
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "published");
		dataset.put("auditor", object.getAuditor().getUserAccount().getUsername());
		dataset.put("mark", mark == null ? null : mark.getMark());
		dataset.put("types", SelectChoices.from(Type.class, object.getType()));
		super.getResponse().addData(dataset);

	}

}
