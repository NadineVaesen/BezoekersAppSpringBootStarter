package be.pxl.ja2.bezoekersapp.service;

import be.pxl.ja2.bezoekersapp.dao.BezoekerDAO;
import be.pxl.ja2.bezoekersapp.dao.PatientDAO;
import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.model.Patient;
import be.pxl.ja2.bezoekersapp.rest.dto.BezoekerDTO;
import be.pxl.ja2.bezoekersapp.rest.dto.PatientDTO;
import be.pxl.ja2.bezoekersapp.rest.resources.RegistreerBezoekerResource;
import be.pxl.ja2.bezoekersapp.util.BezoekerstijdstipUtil;
import be.pxl.ja2.bezoekersapp.util.exception.BezoekersAppException;
import be.pxl.ja2.bezoekersapp.util.exception.OngeldigTijdstipException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BezoekersService {
    private static final Logger LOGGER = LogManager.getLogger(BezoekersService.class);
    public static final int BEZOEKERS_PER_TIJDSTIP_PER_AFDELING = 2;


    final
    BezoekerDAO bezoekerDAO;
    final
    PatientDAO patientDAO;

    public BezoekersService(BezoekerDAO bezoekerDAO, PatientDAO patientDAO) {
        this.bezoekerDAO = bezoekerDAO;
        this.patientDAO = patientDAO;
    }

    public Long registreerBezoeker(RegistreerBezoekerResource registreerBezoekerResource) throws BezoekersAppException, OngeldigTijdstipException {
        //ontbreekt er info?
        controleerAlleVeldenBezoekersResource(registreerBezoekerResource);

        System.out.println("resource: " + registreerBezoekerResource.getNaam());

        //er mogen maar 2 bezoekers zijn op 1 afdeling
        ////vindt de afdeling => eerst patient zoeken
        Optional<Patient> patientDTO = patientDAO.findById(registreerBezoekerResource.getPatientCode());
        List<Bezoeker> alleBezoekersVanAfdeling;

        if (patientDTO.isPresent()) {
            alleBezoekersVanAfdeling = getBezoekersVoorAfdeling(patientDTO.get().getAfdeling().getCode());
        } else {
            throw new BezoekersAppException("De patientcode is niet juist.");
        }
        for (var item :
                alleBezoekersVanAfdeling) {
            System.out.println("bezoekers van afdeling " + patientDTO.get().getAfdeling().getCode() + ": " + item.getNaam() + item.getTijdstip());
        }
        ////als de lijst niet leeg is, moet het tijdstip vergeleken worden. Als die niet overeenkomt is er geen probleem
        ////is het hetzelfde tijdstip mag er niet meer dan 1 zijn.
        List<Bezoeker> alleBezoekersMetZelfdeTijdstip = new ArrayList<>();


        if (!alleBezoekersVanAfdeling.isEmpty()) {
            for (var bezoeker :
                    alleBezoekersVanAfdeling) {
                if (bezoeker.getTijdstip().equals(registreerBezoekerResource.getTijdstip())) {
                    alleBezoekersMetZelfdeTijdstip.add(bezoeker);
                }
            }
        }

        for (var item :
                alleBezoekersMetZelfdeTijdstip) {
            System.out.println("bezoekers met zelfde tijdstip: " + item.getTijdstip());
        }

        if(!alleBezoekersMetZelfdeTijdstip.isEmpty()) {
            if (alleBezoekersMetZelfdeTijdstip.size() > 1) {
                throw new BezoekersAppException("Er zijn al 2 bezoekers geregistreerd. Kies een ander tijdstip");
            }
        }

        var alleBezoekers = getAlleBezoekers();

        //kijk of er reeds een bezoeker is met dezelfde patient
        for (var bezoeker : alleBezoekers
        ) {
            if (bezoeker == mapToBezoekerDTOFromResource(registreerBezoekerResource)) {
                throw new BezoekersAppException("Er is reeds een bezoeker geregistreerd voor patient " + registreerBezoekerResource.getPatientCode());
            }
        }

        //controlleer tijdstip
        BezoekerstijdstipUtil.controleerBezoekerstijdstip(registreerBezoekerResource.getTijdstip());


        //registreer bezoeker
        Bezoeker bezoeker = mapToBezoeker(registreerBezoekerResource);
        Long result;
        try {
            result = bezoekerDAO.registreerBezoeker(bezoeker);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private BezoekerDTO mapToBezoekerDTOFromResource(RegistreerBezoekerResource registreerBezoekerResource) {
        BezoekerDTO bezoekerDTO = new BezoekerDTO();
        bezoekerDTO.setVoornaam(registreerBezoekerResource.getVoornaam());
        bezoekerDTO.setNaam(registreerBezoekerResource.getNaam());
        return bezoekerDTO;
    }

    private boolean isPatientCodeNietGeldig(String patientCode) {
        return patientDAO.findById(patientCode).isPresent();
    }


    private void controleerAlleVeldenBezoekersResource(RegistreerBezoekerResource registreerBezoekerResource) throws BezoekersAppException, OngeldigTijdstipException {
        if (StringUtils.isBlank(registreerBezoekerResource.getNaam())) {
            throw new BezoekersAppException("Vul uw naam in.");
        }
        if (StringUtils.isBlank(registreerBezoekerResource.getVoornaam())) {
            throw new BezoekersAppException("Vul uw voornaam in.");
        }
        if (StringUtils.isBlank(registreerBezoekerResource.getTelefoonnummer())) {
            throw new BezoekersAppException("Vul uw telefoonnummer in.");
        }
        if (StringUtils.isBlank(registreerBezoekerResource.getPatientCode())) {
            throw new BezoekersAppException("Vul de patiëntencode in.");
        }
        BezoekerstijdstipUtil.controleerBezoekerstijdstip(registreerBezoekerResource.getTijdstip());
    }

    private Bezoeker mapToBezoeker(RegistreerBezoekerResource registreerBezoekerResource) throws BezoekersAppException {
        Bezoeker bezoeker = new Bezoeker();
        bezoeker.setNaam(registreerBezoekerResource.getNaam());
        bezoeker.setVoornaam(registreerBezoekerResource.getVoornaam());
        Optional<Patient> patient = patientDAO.findById(registreerBezoekerResource.getPatientCode());
        if (patient.isEmpty()) {
            throw new BezoekersAppException("No Patient found");
        }
        bezoeker.setPatient(patient.get());
        bezoeker.setTelefoonnummer(registreerBezoekerResource.getTelefoonnummer());
        try {
            bezoeker.setAanmelding(LocalDateTime.of(LocalDate.now(), registreerBezoekerResource.getTijdstip()));
        } catch (Exception e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        bezoeker.setTijdstip(registreerBezoekerResource.getTijdstip());
        return bezoeker;
    }

    public void controleerBezoek(Long bezoekerId, LocalDateTime aanmelding) throws Exception {
        bezoekerDAO.controleerBezoek(bezoekerId, aanmelding);
    }

    public List<Bezoeker> getBezoekersVoorAfdeling(String afdelingCode) {
        return bezoekerDAO.getBezoekersVoorAfdeling(afdelingCode);
    }

    public List<BezoekerDTO> getAlleBezoekers() {
        return bezoekerDAO.getAlleBezoekers().stream().map(this::mapToBezoekerDTO).collect(Collectors.toList());
    }

    private BezoekerDTO mapToBezoekerDTO(Bezoeker bezoeker) {
        BezoekerDTO bezoekerDTO = new BezoekerDTO();
        bezoekerDTO.setNaam(bezoeker.getNaam());
        bezoekerDTO.setVoornaam(bezoeker.getVoornaam());
        return bezoekerDTO;
    }
}
