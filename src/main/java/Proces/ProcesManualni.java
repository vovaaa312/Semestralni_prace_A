package Proces;

public class ProcesManualni extends Proces {
    private int pocetOsob;

    public ProcesManualni(String id, int cas, int pocetOsob) {
        super(id, cas);
        this.pocetOsob = pocetOsob;
    }

    @Override
    public String toString() {
        return "MANU" + super.toString() + " pocet osob: " + pocetOsob;
    }
}
