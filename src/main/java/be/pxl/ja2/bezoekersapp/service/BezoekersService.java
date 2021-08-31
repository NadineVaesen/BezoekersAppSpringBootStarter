package be.pxl.ja2.bezoekersapp.service;

import be.pxl.ja2.bezoekersapp.dao.BezoekerDAO;
import be.pxl.ja2.bezoekersapp.dao.PatientDAO;
import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.model.Patient;
import be.pxl.ja2.bezoekersapp.rest.dto.BezoekerDTO;
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

        //is de patiëntcode gekend?
        if (isPatientCodeNietGeldig(registreerBezoekerResource.getPatientCode())) {
            throw new BezoekersAppException("Patiëntcode niet correct!");
        }





        //is er al een bezoeker geregistreerd voor de patiënt?
        ////vind de patiënt om de afdeling te vinden (hierboven werd al gecontroleerd of de patiënt bestaat)
        Patient patient = null;
        if (patientDAO.findById(registreerBezoekerResource.getPatientCode()).isPresent()) {
            patient = patientDAO.findById(registreerBezoekerResource.getPatientCode()).get();
        }


        ////kijk onder alle bezoekers of er al iemand is met dezelfde patiëntcode
        ////lijst met alle bezoekers van dezelfde afdeling
        assert patient != null;
        var bezoekersVoorAfdeling = getBezoekersVoorAfdeling(patient.getAfdeling().getCode());

        ////lijst met alle bezoekers voor dezelfde patient
        Patient finalPatient = patient;
        var bezoekersVoorPatient = bezoekersVoorAfdeling.stream()
                .filter(y -> y.getPatient().equals(finalPatient))
                .collect(Collectors.toList());

        int aantalBezoekersMetZelfdeTijdstip = 0;

        if (!bezoekersVoorPatient.isEmpty()) {
            for (var bezoek :
                    bezoekersVoorPatient) {
                if (bezoek.getTijdstip() == registreerBezoekerResource.getTijdstip()) {
                    aantalBezoekersMetZelfdeTijdstip += 1;
                }
            }
        }
        if (aantalBezoekersMetZelfdeTijdstip > BEZOEKERS_PER_TIJDSTIP_PER_AFDELING) {
            throw new BezoekersAppException("Er zijn al " + BEZOEKERS_PER_TIJDSTIP_PER_AFDELING + " bezoekers met hetzelfde tijdstip. \nKies een nieuw tijdstip.");
        }

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
