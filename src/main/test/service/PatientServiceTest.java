package service;

import be.pxl.ja2.bezoekersapp.dao.PatientDAO;
import be.pxl.ja2.bezoekersapp.model.Afdeling;
import be.pxl.ja2.bezoekersapp.model.Patient;
import be.pxl.ja2.bezoekersapp.rest.dto.PatientDTO;
import be.pxl.ja2.bezoekersapp.service.PatientService;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.ListAssert;
import org.hibernate.id.GUIDGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {


    @Mock
    private PatientDAO patientDAO;

    @InjectMocks
    private PatientService patientService;

    private List<Patient> patientList;

    @BeforeEach
    public void init(){
        patientList = new ArrayList<>();
        patientList.add(createNewPatient());
        patientList.add(createNewPatient());
        patientList.add(createNewPatient());
    }

    @Test
    public void getAllPatientsShouldReturnAllPatients(){

        when(patientDAO.getAllPatients()).thenReturn(patientList);

        List<Patient> patientListFromPatientService = patientService.getAllPatients().stream().map(this::mapToPatient).collect(Collectors.toList());

        Assertions.assertEquals(patientList.size(), patientListFromPatientService.size());
        for (var patient :
                patientList) {
            org.assertj.core.api.Assertions.assertThat(patientListFromPatientService.contains(patient));

        }

        Assertions.assertEquals(patientListFromPatientService.get(0).getCode(), patientList.get(0).getCode());
    }

    private Patient  mapToPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setOpname(patientDTO.getOpname());
        patient.setAfdeling(patientDTO.getAfdeling());
        patient.setCode(patientDTO.getCode());
        return patient;
    }

    private Patient createNewPatient() {
        Afdeling afdeling = new Afdeling();
        afdeling.setCode("KRA");
        afdeling.setNaam("Koriko");
        Patient patient = new Patient();
        patient.setCode(RandomStringUtils.random(10, true, false));
        patient.setAfdeling(afdeling);
        patient.setOpname(LocalDateTime.of(LocalDate.of(2021,8,10), LocalTime.of(10,0)));
        return patient;
    }


}
