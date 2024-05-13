package Gioco.soluzione;

import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;

import java.io.Serializable;
import java.util.*;

public interface Soluzione extends Serializable, Iterable<Cella>
{
    /*
     * il metodo risolve la griglia simil sudoku
     */
    void risolvi(boolean controllaBlocchi);
    /*
     * il metodo implemente la parte di backtracking di risolvi
     */
    default boolean risolviBT(int riga, int colonna, boolean controllaBlocchi)
    {
        if ( riga == dimensione() || colonna  == dimensione())
            return true;
        List<Integer> scartati = new LinkedList<>();
        while (scartati.size() < dimensione())
        {
            int i = new Random().nextInt(1, dimensione() + 1);
            while (scartati.contains(i))
                i = new Random().nextInt(1, dimensione() + 1);
            if (controlla(riga, colonna, i))
            {
                posiziona(riga, colonna, i);
                int prossimaColonna = (colonna + 1) % dimensione();
                int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;
                if (controllaBlocchi)
                {
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi) && cella(riga, colonna).getBlocco().soddisfatto())
                        return true;
                } else
                {
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi))
                        return true;
                }
            }
            rimuovi(riga, colonna);
            scartati.add(i);
        }
        return false;
    }
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
    default boolean verifica()
    {
        for (int i = 0; i < dimensione(); i++)
            for (int j = 0; j < dimensione(); j++)
                if (!controlla(i, j, cella(i,j).getValore()) || !cella(i, j).getBlocco().soddisfatto())
                    return false;
        return true;
    }
    /*
     * il metodo popola i blocchi sopra la griglia
     */
    void popola(int dimensioneMassima);
    /*
     * il metodo implementa la parte backtracking di popola
     */
    default boolean popolaBT(int dimensioneMassima, Cella precedente)
    {
        LinkedList<Cella> vicini = new LinkedList<>(vicini(precedente.getPosizione()[0], precedente.getPosizione()[1]));
        for (Cella vicino : vicini)
        {
            if (vicino.getBlocco() == null)
            {
                if (precedente.getBlocco().pieno())

            }
        }
    }

    /*
     * il metodo restituisce una lista contenente i vicini di una cella
     */
    List<Cella> vicini(int riga, int colonna);
    /*
     * il metodo restituisce la dimensione della griglia
     */
    int dimensione();
    /*
     * Il metodo restituisce la cella di posizione riga, colonna
    */
    Cella cella(int riga, int colonna);
    /*
     * il metodo genera soluzioni diverse dalla stessa griglia
     */
    Soluzione creaVariante();
}
