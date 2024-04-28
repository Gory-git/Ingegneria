package Gioco.soluzione;

import Gioco.cella.Cella;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface Soluzione extends Serializable, Iterable<Cella>
{
    /*
     * il metodo risolve la griglia simil sudoku mediante backtracking
     */
    void risolvi();
    /*
     * il metodo posiziona il valore nella posizione designata
     */
    void posiziona(int riga, int colonna, int valore);
    /*
     * il metodo rimuove il contenuto della posizione designata
     */
    default void rimuovi (int riga, int colonna)
    {
        posiziona(riga, colonna, 0);
    }
    /*
     * il metodo controlla se il valore nella posizione designata è ammissibile
     */
    boolean controlla (int riga, int colonna, int valore);
    /*
     * il metodo controlla se la soluzione è valida
     */
    boolean verifica ();
    /*
     * il metodo popola i blocchi sopra la griglia
     */
    void popola(int dimensioneMassima);
    /*
     * il metodo restituisce una lista contenente gli adiacenti di una cella
     */
    List<Cella> vicini(int riga, int colonna);
    /*
     * il metodo genera soluzioni diverse dalla stessa griglia
     */
    Soluzione creaVariante();
}
