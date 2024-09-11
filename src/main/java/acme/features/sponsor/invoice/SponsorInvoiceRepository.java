
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student4.Invoice;
import acme.entities.student4.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("SELECT i FROM Invoice i")
	Collection<Invoice> findAllInvoices();

	@Query("SELECT i FROM Invoice i WHERE i.id = :invoiceId")
	Invoice findOneInvoiceById(int invoiceId);

	@Query("SELECT sp from Sponsor sp where sp.id = :sponsorId")
	Sponsor findSponsorById(int sponsorId);

	@Query("SELECT i from Invoice i where i.code = :code")
	Invoice findOneInvoiceByCode(String code);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :sponsorshipId")
	Sponsorship findOneSponsorshipById(int sponsorshipId);

	@Query("select i.sponsorship from Invoice i where i.id = :id")
	Sponsorship findOneSponsorshipByInvoiceId(int id);
}
