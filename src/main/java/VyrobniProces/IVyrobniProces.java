package VyrobniProces;

import AbstrLifo.IAbstLifo;
import Proces.Proces;
import Enum.enumPozice;
import Enum.enumReorg;

import java.util.Iterator;

public interface IVyrobniProces<T> {
    int importDat(String soubor);

    void vlozProces(Proces proces, enumPozice pozice);

    Proces zpristupniProces(enumPozice pozice);

    Proces odeberProces(enumPozice pozice);

    Iterator<T> iterator();

    IAbstLifo<T> vytipujKandidatiReorg(int cas, enumReorg reorgan);

    void reorganizace(enumReorg reorgan, IAbstLifo zasobnik);

    void zrus();
}