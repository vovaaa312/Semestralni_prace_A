package VyrobniProces;

import AbstrDoubleList.IAbstrDoubleList;
import AbstrLifo.AbstrLifo;
import AbstrLifo.IAbstrLifo;
import Proces.Proces;
import Proces.ProcesManualni;

import java.util.Iterator;

public class Dekompozice {

    static void odeber(Proces proces, IAbstrDoubleList list) {
        Iterator iter = list.iterator();
        list.zpristupniPrvni();
        if (proces.equals(list.zpristupniPrvni())) list.odeberPrvni();
        else {
            while (iter.hasNext()) {
                Proces vyhodit = (Proces) list.zpristupniNaslednika();
                if (vyhodit.equals(proces)) {
                    list.odeberAktualni();
                }
            }
        }

    }

    public static IAbstrLifo vytipujKandidatiReorg(double cas, IAbstrDoubleList<Proces> procesy) {
        IAbstrLifo<Proces> kandidati = new AbstrLifo<Proces>();
        Iterator iterator = procesy.iterator();
        while (iterator.hasNext()) {
            Proces procesManualni = (Proces) iterator.next();
            if (procesManualni instanceof ProcesManualni && procesManualni.getCas() <= cas) {
                kandidati.vloz(procesManualni);
                odeber(procesManualni, procesy);
            }


        }


        return (IAbstrLifo) kandidati;
    }

    public static void dekompozice(IAbstrLifo<Proces> zasobnik) {
        IAbstrLifo<Proces> lifo = new AbstrLifo<Proces>();
        while (!zasobnik.jePrazdny()) {
            ProcesManualni proces = (ProcesManualni) zasobnik.odeber();
            Proces prvni = new ProcesManualni(proces.getId(), proces.getCas() / 2, (int) Math.ceil(proces.getPocetOsob() / 2));
            Proces druhy = new ProcesManualni(proces.getId() + "_D", proces.getCas() / 2, (int) Math.ceil(proces.getPocetOsob() / 2));
            lifo.vloz(prvni);
            lifo.vloz(druhy);
        }

        while (!lifo.jePrazdny()) zasobnik.vloz(lifo.odeber());
    }
}
