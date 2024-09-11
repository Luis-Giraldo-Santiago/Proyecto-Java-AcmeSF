
package acme.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.helpers.RandomHelper;
import acme.entities.group.Banner;

@Service
public class RandomBannerService {

	@Autowired
	RandomBannerRepository repository;


	public Banner findRandomBanner() {
		final Date actualDate = MomentHelper.getCurrentMoment();
		Banner result;
		final Collection<Banner> banners = this.repository.findBannersWithActivePeriod(actualDate);
		final List<Banner> bannersList = new ArrayList<Banner>();
		bannersList.addAll(banners);
		if (banners.isEmpty())
			result = null;
		else {
			final int index = RandomHelper.nextInt(bannersList.size());
			result = bannersList.get(index);
		}
		return result;
	}

}
