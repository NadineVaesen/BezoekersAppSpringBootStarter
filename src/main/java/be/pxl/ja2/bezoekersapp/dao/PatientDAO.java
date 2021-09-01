package be.pxl.ja2.bezoekersapp.dao;

import be.pxl.ja2.bezoekersapp.model.Patient;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

public interface PatientDAO {
    Optional<Patient> findById(String patientCode);

    List<Patient> getAllPatients();
}
