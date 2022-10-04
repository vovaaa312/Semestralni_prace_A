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

        ProcesManualni procesZFronty;
        ProcesManualni procesABSTR = null;
        Proces pom;
        boolean pruchod = false;
        switch (reorgan) {
            case AGREGACE:
                while (!zasobnik.jePrazdny()) {
                    procesZFronty = (ProcesManualni) zasobnik.odeber();

                    for (Iterator it = procesy.iterator(); it.hasNext(); ) {
                        pom = (Proces) it.next();
                        if (pom instanceof ProcesManualni) {
                            procesABSTR = (ProcesManualni) pom;

                        } else {
                            procesy.zpristupniNaslednika();
                            continue;
                        }
                        if (!pruchod) {
                            procesy.zpristupniPrvni();
                            pruchod = true;
                        } else {
                            procesy.zpristupniNaslednika();
                        }
                        if (procesZFronty == procesABSTR) {
                            procesy.zpristupniNaslednika();
                            procesZFronty = (ProcesManualni) it.next();
                            int pocetLidi = procesZFronty.getPocetOsob() + procesABSTR.getPocetOsob();
                            int cas = procesZFronty.getCas() + procesABSTR.getCas();
                            ProcesManualni buffer = new ProcesManualni(procesABSTR.getId(), pocetLidi, cas);

                            if (it.hasNext()) {
                                procesy.odeberNaslednika();
                                procesy.odeberNaslednika();
                                procesy.vlozNaslednika(buffer);
                            } else {
                                procesy.odeberPosledni();
                                procesy.odeberPredchudce();
                                procesy.vlozNaslednika(buffer);
                            }

                        }

                    }
                    pruchod = false;
                }

                break;

            case DEKOMPOZICE:

                while (!zasobnik.jePrazdny()) {
                    procesZFronty = (ProcesManualni) zasobnik.odeber();
                    for (Iterator it = procesy.iterator(); it.hasNext(); ) {
                        pom = (Proces) it.next();
                        if (pom instanceof ProcesManualni) {
                            procesABSTR = (ProcesManualni) pom;
                        } else {
                            procesy.zpristupniNaslednika();
                            continue;
                        }
                        if (!pruchod) {
                            procesy.zpristupniPrvni();
                            pruchod = true;
                        } else {
                            procesy.zpristupniNaslednika();
                        }
                        if (procesZFronty == procesABSTR) {
                            ProcesManualni novyproces_1;
                            ProcesManualni novyproces_2;
                            if (procesZFronty.getCas() % 2 == 0) {
                                novyproces_1 = new ProcesManualni(procesZFronty.getId(), procesZFronty.getCas() / 2, procesZFronty.getPocetOsob() / 2
                                );
                                novyproces_2 = new ProcesManualni(procesZFronty.getId(), procesZFronty.getCas() / 2, procesZFronty.getPocetOsob() / 2);
                            } else {
                                novyproces_1 = new ProcesManualni(procesZFronty.getId(), procesZFronty.getCas() / 2, procesZFronty.getPocetOsob() / 2);
                                novyproces_2 = new ProcesManualni(procesZFronty.getId(), 1 + procesZFronty.getCas() / 2, procesZFronty.getPocetOsob() / 2);
                            }

                            if (!it.hasNext()) {
                                procesy.zpristupniPosledni();
                                procesy.odeberPredchudce();
                                procesy.vlozPosledni(novyproces_1);
                                procesy.vlozNaslednika(novyproces_2);
                            } else {
                                procesy.zpristupniPosledni();
                                procesy.odeberPredchudce();
                                procesy.vlozPredchudce(novyproces_1);
                                procesy.vlozPredchudce(novyproces_2);
                            }


                        }

                    }
                    pruchod = false;
                }
                break;
        }

    }

    @Override
    public IAbstLifo vytipujKandidatiReorg(int cas, enumReorg reorgan) {
        // Smazeme predchozi seznam kandidatu
        kandidati.zrus();

        // Zjistime, jestli pozadujeme dekompozici nebo agregaci
        switch (reorgan) {
            case AGREGACE:
                // Projdeme seznam
                for (Iterator it = procesy.iterator(); it.hasNext(); ) {
                    Proces proces_prvni = (Proces) it.next();
                    // Pokud je proces manualni tak pokracujeme
                    if (proces_prvni instanceof ProcesManualni) {
                        // Pokud ma 1. proces dobu trvani mensi nebo rovnu vybranemu kriteriu
                        if (proces_prvni.getCas() <= cas) {
                            if (it.hasNext()) {
                                Proces proces_druhy = (Proces) it.next();
                                // Pokud i druhy proces je manualni
                                if (proces_druhy instanceof ProcesManualni) {
                                    // Pokud i bezprostredne dalsi prvek vyhovuje kriteriu tak oba zaradime do seznamu
                                    if (proces_druhy.getCas() <= cas) {
                                        kandidati.vloz(proces_prvni);
                                        System.out.println(proces_prvni);
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    } else {
                        continue;
                    }
                }
                break;

            case DEKOMPOZICE:
                // Projdeme seznam
                for (Iterator it = procesy.iterator(); it.hasNext(); ) {
                    Proces proces_prvni = (Proces) it.next();
                    // Pokud je proces manualni tak pokracujeme
                    if (proces_prvni instanceof ProcesManualni) {
                        // Pokud ma proces dobu trvani vetsi nebo rovnu vybranemu kriteriu
                        if (proces_prvni.getCas() >= cas) {
                            kandidati.vloz(proces_prvni);
                        }
                    } else {
                        continue;
                    }
                }
                break;
        }
        //Navratime seznam
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
