
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int					staticCodeAudits;

	int					dynamicCodeAudits;

	Double				averageNumAuditRecords;

	Double				deviationNumAuditRecords;

	Integer				minNumAuditRecords;

	Integer				maxNumAuditRecords;

	Double				averageTimeAuditRecords;

	Double				deviationTimeAuditRecords;

	Integer				minTimeAuditRecords;

	Integer				maxTimeAuditRecords;

}
