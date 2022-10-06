package com.example.semestralni_prace_a;


import AbstrDoubleList.AbstrDoubleList;
import Proces.Proces;
import Proces.ProcesManualni;
import Proces.ProcesRoboticky;

import java.util.Iterator;

public class Generator {
    static int id = 1;
    static int MIN_RG = 0;
    static int MAX_RG = 100;

    static String M = "M";
    static String R = "R";


    public Proces generateManualProces(int pocetOsob, int cas) {
        return new ProcesManualni(M + id++, cas, pocetOsob);
    }

    public Proces generateRobotickyProces(int cas) {
        return new ProcesRoboticky(R + id++, cas);
    }

    public Proces generateRandomRobotickyProces() {

        int cas = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        return new ProcesRoboticky(R + id++, cas);
    }

    public Proces generateRandomManualProces() {
        int cas = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        int pocetOsosb = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        return new ProcesManualni(M + id++, cas, pocetOsosb);
    }

    public Proces generateRandomProcess() {
        Proces proces;

        int chance = (int) ((Math.random() * (10 - 0)) + 0);
        if (chance < 5) proces = generateRandomManualProces();
        else proces = generateRandomRobotickyProces();
        return proces;

    }
}
