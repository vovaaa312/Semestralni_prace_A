package Proces;

public abstract class Proces {
    private String id;
    private double cas; // v sekundach

    public Proces(String id, double cas) {
        this.id = id;
        this.cas = cas;
    }

    public String getId() {
        return id;
    }

    public double getCas() {
        return cas;
    }

    @Override
    public String toString() {
        return "ID: " + id + " cas: " + cas;
    }


}
