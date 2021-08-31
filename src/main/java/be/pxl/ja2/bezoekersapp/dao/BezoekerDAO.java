package be.pxl.ja2.bezoekersapp.dao;

import be.pxl.ja2.bezoekersapp.model.Bezoeker;
import be.pxl.ja2.bezoekersapp.rest.dto.BezoekerDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BezoekerDAO {
    Long registreerBezoeker(Bezoeker mapToBezoeker);

    void controleerBezoek(Long bezoekerId, LocalDateTime aanmelding) throws Exception;

    List<Bezoeker> getBezoekersVoorAfdeling(String afdelingCode);

    List<Bezoeker> getAlleBezoekers();
}
