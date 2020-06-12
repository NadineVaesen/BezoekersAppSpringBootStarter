package be.pxl.ja2.examen.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "bezoekers")
public class BezoekersRest {

	private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");
	private static final Logger LOGGER = LogManager.getLogger(BezoekersRest.class);

	// TODO Add Rest endpoints

}
