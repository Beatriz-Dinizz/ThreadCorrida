package controller;

import java.util.concurrent.Semaphore;

public class Carro extends Thread {
    private String piloto;
    private String escuderia;
    private Semaphore semaforoPista;
    private Semaphore semaforoEquipe;
    private double melhorTempo = Double.MAX_VALUE;

    public Carro(String piloto, String escuderia, Semaphore semaforoPista, Semaphore semaforoEquipe) {
        this.piloto = piloto;
        this.escuderia = escuderia;
        this.semaforoPista = semaforoPista;
        this.semaforoEquipe = semaforoEquipe;
    }

    @Override
    public void run() {
        try {
            semaforoPista.acquire();
            
            semaforoEquipe.acquire();
            
            System.out.println(piloto + " da escuderia " + escuderia + " está na pista.");
            
            for (int i = 1; i <= 3; i++) {
                double tempoVolta = 60 + (Math.random() * 30);
                System.out.printf("Piloto %s (Equipe %s), Volta %d: %.2f segundos%n", piloto, escuderia, i, tempoVolta);
                
                if (tempoVolta < melhorTempo) {
                    melhorTempo = tempoVolta;
                }
                
                Thread.sleep(2000);
            }
            
            System.out.printf("Piloto %s (Equipe %s) terminou o treino. Melhor tempo: %.2f segundos%n", piloto, escuderia, melhorTempo);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforoEquipe.release(); 
            semaforoPista.release();  
        }
    }

    public double getMelhorTempo() {
        return melhorTempo;
    }

    public String getPiloto() {
        return piloto;
    }

    public String getEscuderia() {
        return escuderia;
    }
}
