package be.pxl.ja2.bezoekersapp.service;

import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.rest.resources.RegistreerBezoekerResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BezoekersService {
	private static final Logger LOGGER = LogManager.getLogger(BezoekersService.class);
	public static final int BEZOEKERS_PER_TIJDSTIP_PER_AFDELING = 2;


	public Long registreerBezoeker(RegistreerBezoekerResource registreerBezoekerResource) {
		// TODO implement this method
		return null;
	}

	public void controleerBezoek(Long bezoekerId, LocalDateTime aanmelding) {
		// TODO implement this method
	}

	public List<Bezoeker> getBezoekersVoorAfdeling(String afdelingCode) {
		// TODO implement this method
		return null;
	}
}
