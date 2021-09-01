package be.pxl.ja2.bezoekersapp.service;

import be.pxl.ja2.bezoekersapp.dao.PatientDAO;
import be.pxl.ja2.bezoekersapp.model.Patient;
import be.pxl.ja2.bezoekersapp.rest.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    final
    PatientDAO patientDAO;

    public PatientService(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    public List<PatientDTO> getAllPatients() {
        return patientDAO.getAllPatients().stream().map(this::mapToPatientDTO).collect(Collectors.toList());
    }

    public PatientDTO mapToPatientDTO(Patient patient) {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setCode(patient.getCode());
        patientDTO.setOpname(patient.getOpname());
        patientDTO.setAfdeling(patient.getAfdeling());
        return patientDTO;
    }
}
