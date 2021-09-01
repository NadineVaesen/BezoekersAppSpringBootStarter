package be.pxl.ja2.bezoekersapp.rest;

import be.pxl.ja2.bezoekersapp.rest.dto.PatientDTO;
import be.pxl.ja2.bezoekersapp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "patienten")
public class PatientRest {

    final
    PatientService patientService;

    public PatientRest(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDTO> getAllPatients(){
        return patientService.getAllPatients();
    }
}
