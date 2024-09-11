
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						totalNumberOfTrainingModulesWithAnUpdatedMoment;
	Integer						totalNumberOfTrainingSessionsWithALink;
	Double						averageTimeOfTrainingModules;
	Double						devidationTimeOfTrainingModules;
	Double						minimumTimeOfTheTrainingModules;
	Double						maximumTimeOfTheTrainingModules;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
