package VyrobniProces;

import AbstrDoubleList.AbstrDoubleList;
import AbstrDoubleList.IAbstrDoubleList;
import AbstrLifo.AbstrLifo;
import AbstrLifo.IAbstrLifo;
import Enum.enumPozice;
import Enum.enumReorg;
import Proces.Proces;
import Proces.ProcesManualni;
import Proces.ProcesRoboticky;

import java.io.*;
import java.util.Iterator;

public class VyrobniProces implements IVyrobniProces {
    IAbstrDoubleList<Proces> procesy = new AbstrDoubleList<Proces>();


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
            fr.close();
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
        procesy.zrus();
        procesy = null;
    }

    @Override
    public void reorganizace(enumReorg reorgan, AbstrLifo zasobnik) {
        switch (reorgan) {
            case DEKOMPOZICE -> {
                Dekompozice.reorganizace(zasobnik);
            }
            case AGREGACE -> {
                Agregace.reorganizace(zasobnik);
            }
        }

        while (!zasobnik.jePrazdny()) procesy.vlozPosledni((Proces) zasobnik.odeber());
    }

    int getId(Proces proces) {
        StringBuffer id = new StringBuffer(proces.getId());
        id.deleteCharAt(0);
        int idNum = Integer.parseInt(String.valueOf(id.toString()));
        return idNum;
    }

    @Override
    public IAbstrLifo vytipujKandidatiReorg(int cas, enumReorg reorgan) {
        switch (reorgan) {
            case DEKOMPOZICE -> {
                return Dekompozice.vytipujKandidatiReorg(cas, procesy);
            }

            case AGREGACE -> {
                return Agregace.vytipujKandidatiReorg(cas, procesy);
            }
        }
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
