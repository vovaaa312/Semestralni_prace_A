package Enum;

public enum enumPozice {
    PRVNI("prvni"),
    POSLEDNI("posledni"),
    NASLEDUJICI("nasledujici"),
    PREDCHOZI("predchozi"),
    AKTUALNI("aktualni");
    private String nazev;

    enumPozice(String nazev) {
        this.nazev = nazev;
    }
}
