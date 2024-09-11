
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.group.Claim;

@Repository
public interface AnyClaimRepository extends AbstractRepository {

	@Query("SELECT c FROM Claim c")
	Collection<Claim> findAllClaims();

	@Query("SELECT c FROM Claim c WHERE c.id = :claimId")
	Claim findOneClaimById(int claimId);

	@Query("SELECT c FROM Claim c WHERE c.published = :published")
	Collection<Claim> findClaimsByPublished(boolean published);

	@Query("select c from Claim c where c.code = :code")
	Claim findOneClaimByCode(String code);

}
