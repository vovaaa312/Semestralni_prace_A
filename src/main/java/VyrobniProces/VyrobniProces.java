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
        ProcesManualni procesManualni;
        ProcesManualni procesFronty;
        Proces pom;
        boolean bool = false;

        switch (reorgan) {
            case DEKOMPOZICE -> {
                while (!zasobnik.jePrazdny()) {
                    procesFronty = (ProcesManualni) zasobnik.odeber();
                    for (Iterator iter = procesy.iterator(); iter.hasNext(); ) {
                        pom = (Proces) iter.next();
                        if (pom instanceof ProcesManualni) procesManualni = (ProcesManualni) pom;
                        else {
                            procesy.zpristupniNaslednika();
                            continue;
                        }
                        if (!bool) {
                            procesy.zpristupniPrvni();
                            bool = true;
                        } else procesy.zpristupniNaslednika();

                        if (procesManualni == procesFronty) {
                            ProcesManualni novyProces1 = new ProcesManualni(
                                    procesFronty.getId(),
                                    procesFronty.getCas() / 2,
                                    procesFronty.getPocetOsob() / 2);
                            ProcesManualni novyProces2 = new ProcesManualni
                                    (("M" + (getId(procesFronty) + 10)),
                                            procesFronty.getCas() / 2,
                                            procesFronty.getPocetOsob() / 2
                                    );
                            if (iter.hasNext()) {
                                procesy.zpristupniPrvni();
                                procesy.odeberNaslednika();
                                procesy.vlozPrvni(novyProces2);
                                procesy.vlozNaslednika(novyProces1);
                            } else {
                                procesy.zpristupniPrvni();
                                procesy.odeberNaslednika();
                                procesy.vlozNaslednika(novyProces2);
                                procesy.vlozNaslednika(novyProces1);
                            }

                        }

                    }
                    bool = false;
                }

            }
            case AGREGACE -> {
                System.out.println("Not implemented yet");
            }
        }
    }

    int getId(Proces proces) {
        StringBuffer id = new StringBuffer(proces.getId());
        id.deleteCharAt(id.length() - 1);
        int idNum = Integer.parseInt(String.valueOf(id.toString()));
        return idNum;
    }

    @Override
    public IAbstLifo vytipujKandidatiReorg(int cas, enumReorg reorgan) {
        kandidati.zrus();
        switch (reorgan) {
            case DEKOMPOZICE -> {
                for (Iterator iter = procesy.iterator(); iter.hasNext(); ) {
                    Proces prvniProces = (Proces) iter.next();
                    if (prvniProces instanceof ProcesManualni && prvniProces.getCas() >= cas) {
                        kandidati.vloz(prvniProces);
                    }
                }
                break;
            }
            case AGREGACE -> {
                System.out.println("Not implemented yet");
            }
        }
        return kandidati;
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
