package Proces;

public class ProcesManualni extends Proces {
    private int pocetOsob;

    public ProcesManualni(String id, double cas, int pocetOsob) {
        super(id, cas);

        this.pocetOsob = pocetOsob;
    }

    public int getPocetOsob() {
        return pocetOsob;
    }

    @Override
    public String toString() {
        return "MANU " + super.toString() + " pocet osob: " + pocetOsob;
    }


}
