package VyrobniProces;

import AbstrDoubleList.IAbstrDoubleList;
import AbstrLifo.AbstrLifo;
import AbstrLifo.IAbstrLifo;
import Proces.Proces;
import Proces.ProcesManualni;

import java.util.Iterator;

public class Agregace {

    //tato trida vytvorena pro provedeni agregace procesu ve seznamu
    static void odeber(Proces proces, IAbstrDoubleList list) {
        Iterator iter = list.iterator();

        //projdeme seznam a vyhodime ty prvky seznamu, ktere pak agregujeme
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
        //projdeme seznam
        while (iterator.hasNext()) {
            Proces procesManualni = (Proces) iterator.next();
            if (procesManualni instanceof ProcesManualni && procesManualni.getCas() <= cas) {
                //pokud prvek vyhovuje pozadavkum, vlozime ho do seznamu zasobniku
                kandidati.vloz(procesManualni);
                //vybereme kandidata z seznamu
                odeber(procesManualni, procesy);
            }
        }
        return (IAbstrLifo) kandidati;
    }

    public static void reorganizace(IAbstrLifo<Proces> zasobnik) {
        IAbstrLifo<Proces> lifo = new AbstrLifo<Proces>();
        ProcesManualni pom = null;
        //projdeme seznam
        while (!zasobnik.jePrazdny()) {
            if(pom == null){
                //do pomocne promene vlozime prvek ktery gregujeme
                pom = (ProcesManualni)zasobnik.odeber();
                if(zasobnik.jePrazdny()){
                    ProcesManualni proces = (ProcesManualni)lifo.odeber();
                     proces = new ProcesManualni(pom.getId()+"_S",pom.getCas()+ proces.getCas(), pom.getPocetOsob() + proces.getPocetOsob());
                     lifo.vloz(proces);
                }
            }else{
                ProcesManualni procesFronty = (ProcesManualni)zasobnik.odeber();
                Proces proces = new ProcesManualni(pom.getId(), pom.getCas() + procesFronty.getCas(), pom.getPocetOsob() + procesFronty.getPocetOsob());
                lifo.vloz(proces);
                pom = null;
            }

        }

        while (!lifo.jePrazdny()) zasobnik.vloz(lifo.odeber());
    }
}
