
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Integer						totalInvoicesTaxLessEqual21;
	Integer						totalSponsorshipsWithLink;
	Double						averageSponsorships;
	Double						deviationSponsorships;
	Double						minimumSponsorships;
	Double						maximunSponsorships;
	Double						averageInvoices;
	Double						deviationInvoices;
	Double						minimumInvoices;
	Double						maximunInvoices;
}
