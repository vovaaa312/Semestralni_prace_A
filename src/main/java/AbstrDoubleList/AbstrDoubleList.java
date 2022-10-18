package AbstrDoubleList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {

    private int pocetPvrku;

    //odkazy pro pohyb v seznamu
    private Prvek<T> prvni;
    private Prvek<T> posledni;
    private Prvek<T> aktualni;

    //pomocna trida
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

    //zruseni celeho seznamu
    @Override
    public void zrus() {
        prvni = null;
        posledni = null;
        aktualni = null;
        pocetPvrku = 0;
    }

    //kontrola zda je seznam prazndy
    @Override
    public boolean jePrazdny() {
        return pocetPvrku == 0;
    }

    //vlozeni prvku na prvni pozice
    @Override
    public void vlozPrvni(T data) {
        if (data == null) throw new NullPointerException();

        Prvek<T> first = prvni;
        Prvek<T> novy = new Prvek<T>(data, null, first); //  prvni prvek musi mit odkaz na prvni prvek seznam
        prvni = novy;
        if (first == null || jePrazdny())
            posledni = novy;        //  pokud seznam je prazdny, tak prvek bude i prvnim i poslednim
        else first.predchozi = novy;
        aktualni = prvni;
        pocetPvrku++;
    }

    //vlozeni prvku na posledni pozice
    @Override
    public void vlozPosledni(T data) {
        if (data == null) throw new NullPointerException();

        Prvek<T> last = posledni;
        Prvek<T> novy = new Prvek<T>(data, last, null);     //posledni prvek ma za odkaz na
        posledni = novy;                                            //predchudce odkaz na posledni prvek seznamu
        if (last == null) prvni = novy;
        else last.naslednik = novy;
        aktualni = posledni;
        pocetPvrku++;
    }

    //vlozeni prvku na pozice naslednika
    @Override
    public void vlozNaslednika(T data) {
        if (data == null) throw new NullPointerException();
        if (jePrazdny() || pocetPvrku == 1 || aktualni == posledni)
            vlozPosledni(data);  //pokud jePrazdny tak vlozime jako posledni
        else {
            Prvek<T> novy = new Prvek<T>(data, aktualni, aktualni.naslednik); //predchudcem noveho prvku je aktualni a
            aktualni.naslednik.predchozi = novy;                              //naslednik je naslednik aktualniho
            aktualni.naslednik = novy;
            aktualni = novy;
            pocetPvrku++;
        }
    }

    //vlozeni prvku na pozice predchudce
    @Override
    public void vlozPredchudce(T data) {
        if (data == null) throw new NullPointerException();

        if (pocetPvrku == 1 || jePrazdny()) vlozPosledni(data);     //pokud jePrazdny tak vlozime jako posledni
        else if (aktualni == prvni) vlozPrvni(data);
        else {
            Prvek<T> novy = new Prvek<T>(data, aktualni.predchozi, aktualni);  //tedy naopak jako u vlozNaslednika
            aktualni.predchozi.naslednik = novy;                               //predchudcem je predchudce aktualniho prvku
            aktualni.predchozi = novy;                                         //naslednik je aktualni prvek
            aktualni = novy;
            pocetPvrku++;
        }
    }

    //zpristupneni aktualniho prvku
    @Override
    public T zpristupniAktualni() {
        if (jePrazdny() || aktualni == null) throw new NullPointerException();
        return aktualni.data;
    }

    //zpristupneni prvniho prvku
    @Override
    public T zpristupniPrvni() {
        if (jePrazdny() || prvni == null) throw new NullPointerException();
        aktualni = prvni;
        return prvni.data;
    }

    //zpristupneni posldeniho prvku
    @Override
    public T zpristupniPosledni() {
        if (jePrazdny() || posledni == null) throw new NullPointerException();
        aktualni = posledni;
        return posledni.data;
    }

    //zpristupneni naslednika
    @Override
    public T zpristupniNaslednika() {
        if (jePrazdny() || aktualni.naslednik == null) throw new NullPointerException();
        aktualni = aktualni.naslednik;
        return aktualni.data;
    }

    //zpristupneni predchudce
    @Override
    public T zpristupniPredchudce() {
        if (jePrazdny() || aktualni.predchozi == null) throw new NullPointerException();
        aktualni = aktualni.predchozi;
        return aktualni.data;
    }

    //odebirani aktualniho prvku
    @Override
    public T odeberAktualni() {
        if (jePrazdny() || aktualni == null) throw new NullPointerException();
        if (aktualni == prvni) return odeberPrvni();
        else if (aktualni == posledni) return odeberPosledni();
        else {
            Prvek<T> pom = prvni;
            //aktualni prvek chranime do promeny aby potom vratit
            Prvek<T> akt = aktualni;
            //hledame aktualni prvek v seznamu
            while (pom.naslednik != aktualni) pom = pom.naslednik;
            //menime odkaz na nasledujici prvek od aktualniho
            pom.naslednik = aktualni.naslednik;
            //pokud existuji predchudce nebo naslednik, nastavime ho jako aktualni
            if (aktualni.naslednik != null) aktualni = aktualni.naslednik;
            else if (aktualni.predchozi != null) aktualni = aktualni.predchozi;
                //pokud ne, jako null
            else aktualni = null;
            pocetPvrku--;
            return akt.data;
        }

    }

    //odebirani prvniho prvku
    //Zmenime odkaz prvniho prvku na jeho naslednika a vracime vyhodeny prvek
    //Nastavime prvni prvek jako aktualni
    @Override
    public T odeberPrvni() {
        if (jePrazdny() || prvni == null) throw new NullPointerException();
        //prvni prvek chranime do promeny aby potom vratit
        Prvek<T> prv = prvni;
        //menime odkaz z prvniho na naslednika prvniho
        prvni = prvni.naslednik;
        //nastavime prvniho jako aktualni
        aktualni = prvni;
        pocetPvrku--;
        return prv.data;
    }

    //odebirani posledniho prvku
    //Zmenime odkaz posledniho prvku na jeho predchudce a vracime vyhodeny prvek
    //Nastavime posledni prvek jako aktualni
    @Override
    public T odeberPosledni() {
        if (jePrazdny() || posledni == null) throw new NullPointerException();
        //posledni prvek chranime do promeny aby potom vratit
        Prvek<T> last = posledni;
        if (pocetPvrku == 1) zrus();
        else {
            //pomocny prek z odkazem na prvni
            Prvek<T> pom = prvni;
            // pokud aktualni je roven poslednimu, menime odkaz na aktualni na jeho predchudce

            //projdeme seznam
            for (int i = 0; i < pocetPvrku; i++) {
                //kazdou iteraci menime pom na jeho naslednika
                //v moment kdyz jeho naslednik je roven poslednimu tak menime posledni na pom
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

    //odebirani naslednika
    @Override
    public T odeberNaslednika() {
        if (jePrazdny() || aktualni.naslednik == null) throw new NullPointerException();
        if (aktualni == posledni) throw new NoSuchElementException("Chuba: aktualni je posledni");
        //pomocny prek z odkazem na naslednika
        Prvek<T> nas = aktualni.naslednik;
        //vymenime odkaz aktualniho naslednika na jeste dalsiho naslednika
        //a nastavime ho jako aktualni
        aktualni.naslednik = nas.naslednik;
        aktualni = aktualni.naslednik;
        pocetPvrku--;
        return nas.data;
    }

    //odebirani predchudce
    @Override
    public T odeberPredchudce() {
        if (jePrazdny() || aktualni.predchozi == null) throw new NullPointerException();
        if (aktualni == prvni) throw new NoSuchElementException("Chyba: aktualni je prvni");
        //pomocny prek z odkazem na predchudce
        Prvek<T> pre = aktualni.predchozi;
        //vracime se ve seznamu na dva prvku zpatky, nastavime ho jako aktualniho
        //a oeberemo ho naslednika(zmenime odkaz na jeho naslednika na aktualni)
        aktualni = pre.predchozi;
        aktualni.naslednik = pre.naslednik;

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
