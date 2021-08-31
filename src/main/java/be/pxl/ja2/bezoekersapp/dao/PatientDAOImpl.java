package be.pxl.ja2.bezoekersapp.dao;

import be.pxl.ja2.bezoekersapp.model.Patient;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class PatientDAOImpl implements PatientDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Patient> findById(String patientCode) {
        return Optional.ofNullable(entityManager.find(Patient.class, patientCode));


    }
}
