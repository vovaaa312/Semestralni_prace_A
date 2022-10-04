package AbstrDoubleList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private int pocetPvrku;

    private Prvek<T> prvni;
    private Prvek<T> posledni;
    private Prvek<T> aktualni;

    private static class Prvek<T> {
        public T data;
        public Prvek<T> predchozi;
        public Prvek<T> naslednik;

        public Prvek(T data, Prvek<T> predchozi, Prvek<T> naslednik) {
            this.data = data;
            this.predchozi = predchozi;
            this.naslednik = naslednik;
        }
    }

    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocetPvrku = 0;
    }

    @Override
    public boolean jePrazdny() {
        return pocetPvrku == 0;
    }

    @Override
    public void vlozPrvni(T data) {
        if (data == null) throw new NullPointerException();

        Prvek<T> first = prvni;
        Prvek<T> novy = new Prvek<T>(data, null, first);
        prvni = novy;
        if (first == null) posledni = novy;
        else first.predchozi = novy;
        aktualni = prvni;
        pocetPvrku++;
    }

    @Override
    public void vlozPosledni(T data) {
        if (data == null) throw new NullPointerException();

        Prvek<T> last = posledni;
        Prvek<T> novy = new Prvek<T>(data, last, null);
        posledni = novy;
        if (last == null) prvni = novy;
        else last.naslednik = novy;
        aktualni = posledni;
        pocetPvrku++;
    }

    @Override
    public void vlozNaslednika(T data) {
        if (data == null) throw new NullPointerException();
        if (jePrazdny() || pocetPvrku == 1 || aktualni == posledni) vlozPosledni(data);
        else {
            Prvek<T> novy = new Prvek<T>(data, aktualni, aktualni.naslednik);
            aktualni.naslednik.predchozi = novy;
            aktualni.naslednik = novy;
            pocetPvrku++;
        }
    }

    @Override
    public void vlozPredchudce(T data) {
        if (data == null) throw new NullPointerException();

        if (pocetPvrku == 1 || jePrazdny()) vlozPosledni(data);
        else if (aktualni == prvni) vlozPrvni(data);
        else {
            Prvek<T> novy = new Prvek<T>(data, aktualni.predchozi, aktualni);
            aktualni.predchozi.naslednik = novy;
            aktualni.predchozi = novy;
            pocetPvrku++;
        }
    }

    @Override
    public T zpristupniAktualni() {
        if (jePrazdny() || aktualni == null) throw new NullPointerException();
        return aktualni.data;
    }

    @Override
    public T zpristupniPrvni() {
        if (jePrazdny() || prvni == null) throw new NullPointerException();
        return prvni.data;
    }

    @Override
    public T zpristupniPosledni() {
        if (jePrazdny() || posledni == null) throw new NullPointerException();
        return posledni.data;
    }

    @Override
    public T zpristupniNaslednika() {
        if (jePrazdny() || aktualni.naslednik == null) throw new NullPointerException();
        return aktualni.naslednik.data;
    }

    @Override
    public T zpristupniPredchudce() {
        if (jePrazdny() || aktualni.predchozi == null) throw new NullPointerException();
        return aktualni.predchozi.data;
    }

    @Override
    public T odeberAktualni() {
        if (jePrazdny() || aktualni == null) throw new NullPointerException();
        if (aktualni == prvni) return odeberAktualni();
        else if (aktualni == posledni) return odeberPosledni();
        else {
            Prvek<T> pom = prvni;
            Prvek<T> akt = aktualni;
            while (pom.naslednik != aktualni) pom = pom.naslednik;
            pom.naslednik = aktualni.naslednik;
            aktualni = null;
            pocetPvrku--;
            return akt.data;
        }

    }

    @Override
    public T odeberPrvni() {
        if (jePrazdny() || prvni == null) throw new NullPointerException();
        if (aktualni == prvni) aktualni = null;
        Prvek<T> prv = prvni;
        prvni = prvni.naslednik;
        pocetPvrku--;
        return prv.data;
    }

    @Override
    public T odeberPosledni() {
        if (jePrazdny() || posledni == null) throw new NullPointerException();

        Prvek<T> last = posledni;
        if (pocetPvrku == 1) zrus();
        else {
            Prvek<T> pom = prvni;
            if (aktualni == posledni) aktualni = posledni.predchozi;
            for (int i = 0; i < pocetPvrku; i++) {
                if (pom.naslednik == posledni) {
                    posledni = pom;
                    posledni.naslednik = null;
                    break;
                }
                pom = pom.naslednik;
            }
            pocetPvrku--;
        }
        return last.data;
    }

    @Override
    public T odeberNaslednika() {
        if (jePrazdny() || aktualni.naslednik == null) throw new NullPointerException();
        if (aktualni == posledni) throw new NoSuchElementException("Chuba: aktualni je posledni");

        Prvek<T> nas = aktualni.naslednik;
        aktualni.naslednik = nas.naslednik;
        nas.predchozi = aktualni;
        pocetPvrku--;
        return nas.data;
    }

    @Override
    public T odeberPredchudce() {
        if (jePrazdny() || aktualni.predchozi == null) throw new NullPointerException();
        if (aktualni == prvni) throw new NoSuchElementException("Chuba: aktualni je prvni");

        Prvek<T> pre = aktualni.predchozi;
        aktualni.predchozi = pre.predchozi;
        pocetPvrku--;
        return pre.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Prvek<T> pr = prvni;

            @Override
            public boolean hasNext() {
                return pr != null;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    T data = pr.data;
                    pr = pr.naslednik;
                    return data;
                } else throw new NullPointerException();


            }
        };
        //bruh
    }
}
