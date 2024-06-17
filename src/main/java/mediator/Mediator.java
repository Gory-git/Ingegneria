package mediator;

import Gioco.soluzione.Soluzione;
import observer.ManagerSubscriber;

import javax.xml.crypto.dsig.Manifest;
import java.util.List;

public interface Mediator extends ManagerSubscriber
{
    void avvia(int numeroSoluzioni, int dimensioniGriglia, int numeroBlocchi);
    void salva();
    void carica();
    void inserisciValore(int riga, int colonna, int valore);
    int valore(int riga, int colonna);
    int valore(int riga, int colonna, int indiceSoluzione);
    int numeroSoluzioni();
    boolean controlla(int riga, int colonna, int valore);
    int dimensione();
}
