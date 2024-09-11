
package acme.features.sponsor.dashboard;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface SponsorDashboardRepository extends AbstractRepository {

	// Obtener el número total de facturas con un impuesto igual o menor al 21%
	@Query("SELECT COUNT (i) FROM Invoice i WHERE i.tax<=21.0")
	Integer totalInvoicesTaxLessEqual21();

	//Obtener el número total de patrocinios con link
	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.link IS NOT NULL")
	Integer totalSponsorshipsWithLink();

	//Obtener el promedio de la cantidad de los patrocinios
	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s")
	Double averageSponsorships();

	//Obtener la desviación de la cantidad de los patrocinios
	@Query("SELECT STDDEV(s.amount.amount) FROM Sponsorship s")
	Double deviationSponsorships();

	//Obtener el patrocionio con la cantidad mínima
	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s")
	Double minimumSponsorships();

	//Obtener el patrocinio con la cantidad máxima
	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s")
	Double maximunSponsorships();

	//Obtener el promedio de la cantidad de las facturas
	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i")
	Double averageInvoices();

	//Obtener la desviación de la cantidad de facturas
	@Query("SELECT STDDEV(i.quantity.amount) FROM Invoice i")
	Double deviationInvoices();

	//Obtener la mínima cantidad de facturas
	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i")
	Double minimumInvoices();

	//Obtener la máxima cantidad de facturas
	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i")
	Double maximunInvoices();
}
