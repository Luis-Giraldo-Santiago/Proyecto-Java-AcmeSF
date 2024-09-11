
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student1.Project;
import acme.entities.student5.AuditRecord;
import acme.entities.student5.CodeAudit;
import acme.entities.student5.Mark;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select ca from CodeAudit ca where ca.auditor.id = :auditorId")
	Collection<CodeAudit> findManyCodeAuditByAuditorId(int auditorId);

	@Query("select ca from CodeAudit ca where ca.id = :codeAuditId")
	CodeAudit findOneCodeAuditById(int codeAuditId);

	@Query("select a from Auditor a where a.id= :auditorId")
	Auditor findAuditorById(int auditorId);

	@Query("select ca from CodeAudit ca where ca.code= :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select ar from AuditRecord ar where ar.published = 1 and ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyPublishedAuditRecordByCodeAuditId(int codeAuditId);

	@Query("select ar.mark from AuditRecord ar where ar.published = 1 and ar.codeAudit.id = :codeAuditId")
	Collection<Mark> findManyMarksByCodeAuditId(int codeAuditId);

	@Query("select p from Project p where p.published = true")
	Collection<Project> findAllProjectsPublished();
}
