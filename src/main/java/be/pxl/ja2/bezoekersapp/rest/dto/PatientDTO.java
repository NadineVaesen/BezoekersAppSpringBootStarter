package be.pxl.ja2.bezoekersapp.rest.dto;

import be.pxl.ja2.bezoekersapp.model.Afdeling;

import java.time.LocalDateTime;

public class PatientDTO {

    private String code;
    private LocalDateTime opname;
    private Afdeling afdeling;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getOpname() {
        return opname;
    }

    public void setOpname(LocalDateTime opname) {
        this.opname = opname;
    }

    public Afdeling getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(Afdeling afdeling) {
        this.afdeling = afdeling;
    }
}
