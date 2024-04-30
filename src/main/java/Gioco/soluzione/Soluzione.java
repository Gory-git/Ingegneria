package Gioco.soluzione;

import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public interface Soluzione extends Serializable, Iterable<Cella>
{
    /*
     * il metodo risolve la griglia simil sudoku
     */
    default void risolvi(boolean controllaBlocchi)
    {
        risolviBT(0,0, controllaBlocchi);
    }
    /*
     * il metodo implemente la parte di backtracking di risolvi
     */
    boolean risolviBT(int riga, int colonna, boolean controllaBlocchi);
    /*
     * il metodo posiziona il valore nella posizione designata
     */
    void posiziona(int riga, int colonna, int valore); // TODO da rifare qua dentro
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
     * il metodo implementa la parte backtracking di popola
     */
    default boolean popolaBT(int dimensioneMassima, Cella precedente) // TODO riscrivere per rispettare il nuovo funzionamento dell'iteratore
    {
        return false; // TODO mi sono rotto il cazzo, lo riprendo quando sono capace.
    }

    /*
     * il metodo restituisce una lista contenente i vicini di una cella
     */
    List<Cella> vicini(int riga, int colonna);
    /*
     * il metodo genera soluzioni diverse dalla stessa griglia
     */
    Soluzione creaVariante();
}
