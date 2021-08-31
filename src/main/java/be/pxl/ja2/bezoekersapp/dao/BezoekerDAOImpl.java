package be.pxl.ja2.bezoekersapp.dao;

import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.rest.dto.BezoekerDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BezoekerDAOImpl implements BezoekerDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public Long registreerBezoeker(Bezoeker bezoeker) {
        entityManager.persist(bezoeker);
        return bezoeker.getId();
    }

    @Override
    public void controleerBezoek(Long bezoekerId, LocalDateTime aanmelding) throws Exception {
        if (entityManager.find(Bezoeker.class, bezoekerId) != null) {
            throw new Exception("Bezoeker is reeds aangemeld");
        }
    }

    @Override
    public List<Bezoeker> getBezoekersVoorAfdeling(String afdelingCode) {
        var bezoekersQuery = entityManager.createNamedQuery("findAllBezoekers", Bezoeker.class);
        bezoekersQuery.setParameter("codeAfdeling", afdelingCode);
        return bezoekersQuery.getResultList();
    }

    @Override
    public List<Bezoeker> getAlleBezoekers() {
        var resultList = entityManager.createQuery("SELECT b.voornaam  FROM Bezoeker b").getResultList();
        List<Bezoeker> bezoekers = new ArrayList<>();

        return bezoekers;
    }

}
