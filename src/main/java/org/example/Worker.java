package org.example;

import java.util.ArrayList;
import java.util.List;

// Worker klasė, įgyvendinanti Runnable interfeisą
class Worker implements Runnable {
    private final List<Integer> skaiciai;
    private final List<Integer> rezultatai;
    private final int workerId; // Kiekvienam Worker priskiriame unikalų ID
    private final List<List<Integer>> workerResults; // Kiekvienos gijos rezultatai

    public Worker(List<Integer> skaiciai, List<Integer> rezultatai, int workerId, List<List<Integer>> workerResults) {
        this.skaiciai = skaiciai;
        this.rezultatai = rezultatai;
        this.workerId = workerId;
        this.workerResults = workerResults;
    }

    @Override
    public void run() {
        List<Integer> vietiniaiRezultatai = new ArrayList<>();
        for (int skaicius : skaiciai) {
            // Kiekvieną skaičių keliame kvadratu
            vietiniaiRezultatai.add(skaicius * skaicius);
        }
        synchronized (rezultatai) {
            // Sinchronizuojame, kad išvengtume konfliktų su bendrais duomenimis
            rezultatai.addAll(vietiniaiRezultatai);
        }

        // Pridedame Worker rezultatus į workerResults sąrašą
        synchronized (workerResults) {
            workerResults.add(vietiniaiRezultatai);
        }
    }
}
