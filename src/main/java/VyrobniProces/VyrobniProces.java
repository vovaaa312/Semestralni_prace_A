package VyrobniProces;

import AbstrDoubleList.AbstrDoubleList;
import AbstrDoubleList.IAbstrDoubleList;
import AbstrLifo.AbstrLifo;
import AbstrLifo.IAbstLifo;
import Enum.enumPozice;
import Enum.enumReorg;
import Proces.Proces;
import Proces.ProcesManualni;
import Proces.ProcesRoboticky;

import java.io.*;
import java.util.Iterator;

public class VyrobniProces implements IVyrobniProces {
    IAbstrDoubleList<Proces> procesy = new AbstrDoubleList<Proces>();
    IAbstLifo<Proces> kandidati = new AbstrLifo<Proces>();

    @Override
    public int importDat(String soubor) {
        int pocet = 0;
        procesy.zrus();
        try {

            FileReader fr = new FileReader(soubor);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] nactenyProces;
            Proces proces;
            br.readLine();
            while ((line = br.readLine()) != null) {
                nactenyProces = line.split(";");
                if (Integer.parseInt(nactenyProces[1]) == 0) proces = new ProcesRoboticky(nactenyProces[0], 14);
                else
                    proces = new ProcesManualni(nactenyProces[0], Integer.parseInt(nactenyProces[1]), Integer.parseInt(nactenyProces[2]));
                procesy.vlozPosledni(proces);
            }
            br.close();


        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            ;
        }

        return pocet;
    }

    @Override
    public Iterator iterator() {
        return procesy.iterator();
    }

    @Override
    public void zrus() {
        procesy = null;
        kandidati = null;
    }

    @Override
    public void reorganizace(enumReorg reorgan, AbstrLifo zasobnik) {

    }

    @Override
    public IAbstLifo vytipujKandidatiReorg(int cas, enumReorg reorgan) {
        return null;
    }

    @Override
    public Proces odeberProces(enumPozice pozice) {
        return switch (pozice) {
            case PRVNI -> procesy.odeberPrvni();
            case POSLEDNI -> procesy.odeberPosledni();
            case AKTUALNI -> procesy.odeberAktualni();
            case PREDCHOZI -> procesy.odeberPredchudce();
            case NASLEDUJICI -> procesy.odeberNaslednika();
        };
    }

    @Override
    public Proces zpristupniProces(enumPozice pozice) {
        return switch (pozice) {
            case PRVNI -> procesy.zpristupniPrvni();
            case POSLEDNI -> procesy.zpristupniPosledni();
            case AKTUALNI -> procesy.zpristupniAktualni();
            case PREDCHOZI -> procesy.zpristupniPredchudce();
            case NASLEDUJICI -> procesy.zpristupniNaslednika();
        };
    }

    @Override
    public void vlozProces(Proces proces, enumPozice pozice) {
        if (pozice == null) throw new NullPointerException();
        switch (pozice) {
            case PRVNI -> procesy.vlozPrvni(proces);
            case POSLEDNI -> procesy.vlozPosledni(proces);
            case PREDCHOZI -> procesy.vlozPredchudce(proces);
            case NASLEDUJICI -> procesy.vlozNaslednika(proces);
        }
    }


}
