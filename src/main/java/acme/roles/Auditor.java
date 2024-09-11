
package acme.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "user_account_id"),
})
public class Auditor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(max = 75)
	private String				firm;

	@NotBlank
	@Length(max = 25)
	@Column(unique = true)
	private String				professionalID;

	@NotBlank
	@Length(max = 100)
	private String				certifications;

	@URL
	@Length(max = 255)
	private String				link;
}
