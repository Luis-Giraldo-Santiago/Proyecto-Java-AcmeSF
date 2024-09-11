
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;
		int auditorId;

		auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManyCodeAuditByAuditorId(auditorId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;
		Mark mark;
		Dataset dataset;
		mark = object.getMark(this.repository.findManyMarksByCodeAuditId(object.getId()));
		dataset = super.unbind(object, "code", "executionDate", "type", "correctiveActions", "link", "published");
		dataset.put("mark", mark == null ? null : mark.getMark());
		super.getResponse().addData(dataset);
	}

}
