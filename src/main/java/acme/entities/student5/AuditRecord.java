
package acme.entities.student5;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "code_audit_id")
})
public class AuditRecord extends AbstractEntity {

	// Serialisation identifier -----------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------
	@Pattern(regexp = "AU-[0-9]{4}-[0-9]{3}", message = "{validation.auditRecord.code}")
	@NotBlank
	@Column(unique = true)
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				auditPeriodStart;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				auditPeriodEnd;

	@NotNull
	private Mark				mark;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				published;

	// Relationships ----------------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private CodeAudit			codeAudit;

}
