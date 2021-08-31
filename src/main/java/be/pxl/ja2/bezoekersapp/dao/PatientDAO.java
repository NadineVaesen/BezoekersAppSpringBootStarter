package be.pxl.ja2.bezoekersapp.dao;

import be.pxl.ja2.bezoekersapp.model.Patient;

import java.util.Optional;

public interface PatientDAO {
    Optional<Patient> findById(String patientCode);
}
