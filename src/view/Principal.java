package view;

import controller.Carro;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

public class Principal {
    public static void main(String[] args) {
        Semaphore semaforoPista = new Semaphore(5); 
        Semaphore[] semaforosEquipe = new Semaphore[7]; 

        for (int i = 0; i < 7; i++) {
            semaforosEquipe[i] = new Semaphore(1);
        }

        String[][] equipes = {
            {"Equipe 1 - Carro 1", "Equipe 1 - Carro 2"},
            {"Equipe 2 - Carro 1", "Equipe 2 - Carro 2"},
            {"Equipe 3 - Carro 1", "Equipe 3 - Carro 2"},
            {"Equipe 4 - Carro 1", "Equipe 4 - Carro 2"},
            {"Equipe 5 - Carro 1", "Equipe 5 - Carro 2"},
            {"Equipe 6 - Carro 1", "Equipe 6 - Carro 2"},
            {"Equipe 7 - Carro 1", "Equipe 7 - Carro 2"}
        };

        Carro[] carros = new Carro[14];
        int index = 0;

        for (int i = 0; i < 7; i++) {
            carros[index++] = new Carro(equipes[i][0], "Equipe " + (i + 1), semaforoPista, semaforosEquipe[i]);
            carros[index++] = new Carro(equipes[i][1], "Equipe " + (i + 1), semaforoPista, semaforosEquipe[i]);
        }

        for (Carro carro : carros) {
            carro.start();
        }

        for (Carro carro : carros) {
            try {
                carro.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Arrays.sort(carros, Comparator.comparingDouble(Carro::getMelhorTempo));

        System.out.println("\n--- GRID DE LARGADA ---");
        for (int i = 0; i < carros.length; i++) {
            System.out.printf("%d. %s (Equipe %s) - Melhor tempo: %.2f segundos%n", i + 1, carros[i].getPiloto(), carros[i].getEscuderia(), carros[i].getMelhorTempo());
        }
    }
}
