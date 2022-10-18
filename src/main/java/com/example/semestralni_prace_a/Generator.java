package com.example.semestralni_prace_a;


import Proces.Proces;
import Proces.ProcesManualni;
import Proces.ProcesRoboticky;

public class Generator {

    //tato trida je vytvorena vetsinou pro vygenerovani procesu s nahodnymi hodnotami casu a poctu osob
    static int id = 0;       //promenna pro nastaveni id kazdemu prvku. kazdy novy prvek se inkrementuje
    static int MIN_RG = 0;   //dolni hranice rozsahu cisel pro genrovani dat (cas a pocet osob)
    static int MAX_RG = 100; //horni hranice rozsahu cisel pro genrovani dat (cas a pocet osob)

    static String M = "M";
    static String R = "R";


    public Proces generateManualProces(int pocetOsob, int cas) {return new ProcesManualni(M + id++, cas, pocetOsob);
    }

    public Proces generateRobotickyProces(int cas) {
        return new ProcesRoboticky(R + id++, cas);
    }

    //generuje roboticky proces s nahodne hodnoty
    public Proces generateRandomRobotickyProces() {

        int cas = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        return new ProcesRoboticky(R + id++, cas);
    }

    //generuje manualni proces s nahodne hodnoty
    public Proces generateRandomManualProces() {
        int cas = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        int pocetOsosb = (int) ((Math.random() * (MAX_RG - MIN_RG)) + MIN_RG);
        return new ProcesManualni(M + id++, cas, pocetOsosb);
    }

    //generuje nahodny proces (roboticky nebo manualni)
    public Proces generateRandomProcess() {
        Proces proces;

        int chance = (int) ((Math.random() * (10 - 0)) + 0);
        if (chance < 5) proces = generateRandomManualProces();
        else proces = generateRandomRobotickyProces();
        return proces;

    }
    public static void reset(){
        id = 0;
    }
    public static int getId() {
        return id;
    }
}
