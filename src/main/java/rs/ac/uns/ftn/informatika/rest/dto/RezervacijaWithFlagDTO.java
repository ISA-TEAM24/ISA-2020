package rs.ac.uns.ftn.informatika.rest.dto;

import rs.ac.uns.ftn.informatika.rest.model.Rezervacija;

public class RezervacijaWithFlagDTO {

    private Rezervacija rezervacija;
    private boolean canCancel;

    public RezervacijaWithFlagDTO() {
    }

    public RezervacijaWithFlagDTO(Rezervacija rezervacija, boolean canCancel) {
        this.rezervacija = rezervacija;
        this.canCancel = canCancel;
    }

    public Rezervacija getRezervacija() {
        return rezervacija;
    }

    public void setRezervacija(Rezervacija rezervacija) {
        this.rezervacija = rezervacija;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }
}
