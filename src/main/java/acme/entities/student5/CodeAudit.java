
package acme.entities.student5;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.student1.Project;
import acme.roles.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "code"), @Index(columnList = "auditor_id"), @Index(columnList = "project_id")
})
public class CodeAudit extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes --------------------------------------------------------------

	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}", message = "{validation.codeAudit.code} ")
	@NotBlank
	@NotNull
	@Column(unique = true)
	private String				code;

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	private Date				executionDate;

	@NotNull
	private Type				type;

	@NotBlank
	@Length(max = 100)
	private String				correctiveActions;

	@URL
	@Length(max = 255)
	private String				link;

	private boolean				published;


	// Derived Attributes  ----------------------------------------------------------
	@Transient
	public Mark getMark(final Collection<Mark> auditRecordsMark) {
		Mark res = null;

		Map<Mark, Integer> marks = auditRecordsMark.stream().collect(Collectors.groupingBy(m -> m, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
		int totalSum = marks.values().stream().mapToInt(Integer::intValue).sum();

		double averageIndex = totalSum / (double) marks.size();

		res = marks.entrySet().stream().min((e1, e2) -> {
			double diff1 = Math.abs(e1.getValue() - averageIndex);
			double diff2 = Math.abs(e2.getValue() - averageIndex);
			return Double.compare(diff1, diff2);
		}).map(Map.Entry::getKey).orElse(null);

		return res;
	}
	// Relationships ----------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Project	project;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Auditor	auditor;

}
