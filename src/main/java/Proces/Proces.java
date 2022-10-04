package Proces;

public abstract class Proces {
    private String id;
    private int cas; // v sekundach

    public Proces(String id, int cas) {
        this.id = id;
        this.cas = cas;
    }

    public String getId() {
        return id;
    }

    public int getCas() {
        return cas;
    }

    @Override
    public String toString() {
        return "ID: " + id + " cas: " + cas;
    }
}
