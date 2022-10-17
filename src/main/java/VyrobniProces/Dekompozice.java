package VyrobniProces;

import AbstrDoubleList.IAbstrDoubleList;
import AbstrLifo.AbstrLifo;
import AbstrLifo.IAbstrLifo;
import Proces.Proces;
import Proces.ProcesManualni;

import java.util.Iterator;

public class Dekompozice {

    public static IAbstrLifo vytipujKandidatiReorg(double cas, IAbstrDoubleList<Proces> procesy) {
        IAbstrLifo<Proces> kandidati = new AbstrLifo<Proces>();
        Iterator iterator = procesy.iterator();
        while (iterator.hasNext()){
            Proces procesManualni =(Proces) iterator.next();
            if (procesManualni instanceof ProcesManualni && procesManualni.getCas()<=cas)
                kandidati.vloz((Proces) iterator.next());
        }

        return (IAbstrLifo) kandidati;
    }

    public static void dekompozice(IAbstrLifo<Proces> zasobnik) {
        IAbstrLifo<Proces> lifo = new AbstrLifo<Proces>();
        Iterator iterator = lifo.iterator();
        while (iterator.hasNext()) {
            ProcesManualni proces = (ProcesManualni) zasobnik.odeber();
            Proces prvni = new ProcesManualni(proces.getId(), proces.getCas() / 2, (int) Math.ceil(proces.getPocetOsob() / 2));
            Proces druhy = new ProcesManualni(proces.getId()+"_D", proces.getCas() / 2, (int) Math.ceil(proces.getPocetOsob() / 2));
            lifo.vloz(prvni);
            lifo.vloz(druhy);
        }

        while (iterator.hasNext()) zasobnik.vloz(lifo.odeber());
    }
}
