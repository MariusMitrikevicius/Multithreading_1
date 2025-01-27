package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Sukuriame pradinę skaičių aibę su 20 skaičių
        List<Integer> skaiciai = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            skaiciai.add(i);
        }

        // Bendras rezultato sąrašas (naudojame sinchronizuotą prieigą su synchronized bloku)
        List<Integer> rezultatai = new ArrayList<>();
        // Sąrašas, kuriame laikysime kiekvienos gijos rezultatus
        List<List<Integer>> workerResults = new ArrayList<>();

        // Nustatome gijų skaičių
        int gijuSkaicius = 4;
        int daliesDydis = skaiciai.size() / gijuSkaicius;  // 20 / 4 = 5

        // Sukuriame ir paleidžiame gijas
        List<Thread> gijos = new ArrayList<>();
        for (int i = 0; i < gijuSkaicius; i++) {
            int pradzia = i * daliesDydis;
            int pabaiga = (i == gijuSkaicius - 1) ? skaiciai.size() : pradzia + daliesDydis;

            // Daliname skaičių aibę į dalis kiekvienai gijai
            List<Integer> dalis = skaiciai.subList(pradzia, pabaiga);
            Worker darbininkas = new Worker(dalis, rezultatai, i + 1, workerResults); // Worker ID prasideda nuo 1
            Thread gija = new Thread(darbininkas);
            gijos.add(gija);
            gija.start();
        }

        // Laukiame, kol visos gijos baigs darbą
        for (Thread gija : gijos) {
            try {
                gija.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Atspausdiname bendrą rezultatą (vienoje eilutėje)
        System.out.println("Bendras rezultatas: " + rezultatai);

        // Atspausdiname tuščią eilutę, kad būtų tarpas
        System.out.println();

        // Atspausdiname kiekvieno Worker rezultatus
        for (int i = 0; i < workerResults.size(); i++) {
            System.out.println("Worker " + (i + 1) + " rezultatai: " + workerResults.get(i));
        }
    }
}
