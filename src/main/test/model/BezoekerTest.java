package model;


import be.pxl.ja2.bezoekersapp.model.Afdeling;
import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.model.Patient;
import org.hibernate.id.GUIDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.cert.TrustAnchor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;


public class BezoekerTest {

    private Bezoeker bezoeker;
    private Patient patient;
    private Afdeling afdeling;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHH:mm");

    @BeforeEach
    public void init(){
        afdeling = new Afdeling();
        afdeling.setCode(GUIDGenerator.GENERATOR_NAME);
        afdeling.setNaam(GUIDGenerator.GENERATOR_NAME);

        patient = new Patient();
        patient.setCode(GUIDGenerator.GENERATOR_NAME);
        patient.setAfdeling(afdeling);


        bezoeker = new Bezoeker();
        bezoeker.setId(1L);
        bezoeker.setNaam("Mockers");
        bezoeker.setVoornaam("Tommy");
        bezoeker.setPatient(patient);
        bezoeker.setTelefoonnummer("0471002301");
        bezoeker.setAanmelding(LocalDateTime.of(LocalDate.of(2021, 9, 1), LocalTime.of(16, 30)));
        bezoeker.setTijdstip(LocalTime.of(16, 30));

    }

    @Test
    public void isReedsAangemeldIsTrueTest(){

        assertTrue(bezoeker.isReedsAangemeld(LocalDate.of(2021,9,1)));
    }

    @Test void isReedsAangemeldIsFalseTest(){
        bezoeker.setAanmelding(null);
        assertFalse(bezoeker.isReedsAangemeld(LocalDate.of(2021,9,1)));
    }
}
