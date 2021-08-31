package be.pxl.ja2.bezoekersapp.rest;

import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.rest.dto.BezoekerDTO;
import be.pxl.ja2.bezoekersapp.rest.resources.RegistreerBezoekerResource;
import be.pxl.ja2.bezoekersapp.service.BezoekersService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(path = "bezoekers")
public class BezoekersRest {

	private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");
	private static final Logger LOGGER = LogManager.getLogger(BezoekersRest.class);

	final
	BezoekersService bezoekersService;

	public BezoekersRest(BezoekersService bezoekersService) {
		this.bezoekersService = bezoekersService;
	}


	// Hint: gebruik ResponseEntity<Long> als return-type voor het rest endpoint om een bezoeker te registreren

	@GetMapping
	public List<BezoekerDTO> showAllVisitors(){
		return bezoekersService.getAlleBezoekers();
	}

	@PostMapping
	public ResponseEntity<Long> registerVisitor(@RequestBody RegistreerBezoekerResource registreerBezoekerResource) throws Exception {
		System.out.println("test");
		Long result = bezoekersService.registreerBezoeker(registreerBezoekerResource);
		if (result != null) {
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

	}

}
