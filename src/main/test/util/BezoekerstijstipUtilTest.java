package util;

import be.pxl.ja2.bezoekersapp.util.BezoekerstijdstipUtil;
import be.pxl.ja2.bezoekersapp.util.exception.OngeldigTijdstipException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class BezoekerstijstipUtilTest {

    @Test
    public void controleerBezoekerstijdstipOngeldigGooitExceptionTest() {
        assertThrows(OngeldigTijdstipException.class, () -> BezoekerstijdstipUtil.controleerBezoekerstijdstip(LocalTime.of(16, 8)));
    }

    @Test
    public void controleerAanmeldingstijdstipongeldigGooitExceptionTest() {
        LocalDateTime aanmeldingstijdstip = LocalDateTime.of(LocalDate.of(2021, 9, 1), LocalTime.of(16, 30));
        LocalTime localTime = LocalTime.of(16, 00);
        assertThrows(OngeldigTijdstipException.class,
                () -> BezoekerstijdstipUtil.controleerAanmeldingstijdstip(aanmeldingstijdstip, localTime));
    }
}
