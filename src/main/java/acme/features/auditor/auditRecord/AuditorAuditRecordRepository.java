
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student5.AuditRecord;
import acme.entities.student5.CodeAudit;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select ar from AuditRecord ar where ar.id = :auditRecordId")
	AuditRecord findOneAuditRecordById(int auditRecordId);

	@Query("select ca from CodeAudit ca where ca.id = :codeAuditId")
	CodeAudit findOneCodeAuditById(int codeAuditId);

	@Query("select ar from AuditRecord ar where ar.code = :code")
	AuditRecord findOneAuditRecordByCode(String code);

	@Query("select ar.codeAudit from AuditRecord ar where ar.id = :auditRecordId")
	CodeAudit findOneCodeAuditByAuditRecordId(int auditRecordId);
}
