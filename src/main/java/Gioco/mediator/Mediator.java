package Gioco.mediator;

import Gioco.soluzione.Soluzione;

import java.util.List;

public interface Mediator // TODO
{
    void avvia(int numeroSoluzioni, int dimensioniGriglia);
    void salva();
    void carica();
    List<Soluzione> soluzioni();
    void inserisciValore(int riga, int colonna, int valore);

}
