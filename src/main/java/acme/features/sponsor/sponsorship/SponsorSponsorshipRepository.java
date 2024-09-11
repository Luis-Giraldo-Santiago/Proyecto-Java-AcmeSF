
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.student1.Project;
import acme.entities.student4.Invoice;
import acme.entities.student4.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s")
	Collection<Sponsorship> findAllSponsosrships();

	@Query("SELECT i FROM Invoice i")
	Collection<Invoice> findAllInvoices();

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :sponsorshipId")
	Sponsorship findOneSponsorshipById(int sponsorshipId);

	@Query("SELECT sp from Sponsor sp where sp.id = :sponsorId")
	Sponsor findSponsorById(int sponsorId);

	@Query("SELECT s from Sponsorship s where s.code = :code")
	Sponsorship findOneSponsorshipByCode(String code);

	@Query("select p from Project p where p.id = :projectId")
	Project findOneProjectById(int projectId);

	@Query("select s from Sponsorship s")
	Collection<Sponsorship> findAllSponsorship();

	@Query("select s.project from Sponsorship s where s.sponsor.id = :sponsorId")
	Collection<Project> findManyProjectsBySponsorId(int sponsorId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findManyInvoiceBySponsorshipId(int sponsorshipId);

	@Query("select p from Project p where p.published = true")
	Collection<Project> findAllProjectsPublished();
}
