package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;

import java.io.Serializable;
import java.util.*;

public interface Soluzione extends Serializable, Cloneable, Iterable<Cella>
{
    /**
     * il metodo risolve la griglia simil sudoku
     */
    void risolvi(boolean controllaBlocchi);

    /**
     * il metodo implementa la parte di backtracking di risolvi
     */
    default boolean risolviBT(int riga, int colonna, boolean controllaBlocchi)
    {
        if (riga == dimensione() || colonna == dimensione())
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

    /**
     * il metodo posiziona il valore nella posizione designata
     */
    void posiziona(int riga, int colonna, int valore);

    /**
     * il metodo rimuove il contenuto della posizione designata
     */
    default void rimuovi(int riga, int colonna)
    {
        posiziona(riga, colonna, 0);
    }

    /**
     * il metodo controlla se il valore nella posizione designata è ammissibile
     */
    boolean controlla(int riga, int colonna, int valore);

    /**
     * il metodo controlla se la soluzione è valida
     */
    default boolean verifica()
    {
        for (int i = 0; i < dimensione(); i++)
            for (int j = 0; j < dimensione(); j++)
                if (!controlla(i, j, cella(i, j).getValore()) || !cella(i, j).getBlocco().soddisfatto())
                    return false;
        return true;
    }

    /**
     * il metodo popola i blocchi sopra la griglia
     */
    void popola(int dimensioneMassima);

    /**
     * il metodo implementa la parte backtracking di popola
     */
    default boolean popolaBT(int dimensioneMassima, Cella cella)
    {
        // caso di uscita
        if (dimensioneMassima <= 1)
            return true;
        List<Cella> vicini = vicini(cella.getPosizione()[0], cella.getPosizione()[1]);
        Blocco blocco = cella.getBlocco();
        int dimensioneRec = dimensioneMassima;
        // caso di ingresso OR caso in cui il blocco precedente è stato posizionato correttamente
        if (blocco == null)
        {
            int dimensione = new Random().nextInt(1, dimensioneMassima);
            dimensioneRec -= dimensione;
            blocco = blocco(dimensione);
            cella.setBlocco(blocco);
            blocco.aggiungiCella(cella);
        }
        // caso in cui il blocco non è stato riempito.
        if (!blocco.pieno())
        {
            boolean ret = true;
            for (Cella vicino : vicini)
                if (vicino.getBlocco() == null && !blocco.pieno())
                {
                    vicino.setBlocco(blocco);
                    blocco.aggiungiCella(vicino);
                    ret = ret && popolaBT(dimensioneRec, vicino);
                    if (!ret)
                    {
                        vicino.setBlocco(null);
                        blocco.rimuoviCella(vicino);
                        return false;
                    }
                }
        }
        // caso in cui il blocco è pieno
        if (blocco.pieno())
        {
            boolean ret = true;
            for (Cella vicino : vicini)
            {
                if (vicino.getBlocco() == null)
                    ret = ret && popolaBT(dimensioneRec, vicino);
                if (!ret)
                {
                    vicino.setBlocco(null);
                    blocco.rimuoviCella(vicino);
                    return false;
                }
            }
            return ret;
        }
        else
            return false;
    }

    /**
     * il metodo restituisce una lista contenente i vicini di una cella
     */
    List<Cella> vicini(int riga, int colonna);

    /**
     * il metodo restituisce la dimensione della griglia
     */
    int dimensione();

    /**
     * Il metodo restituisce la cella di posizione riga, colonna
     */
    Cella cella(int riga, int colonna);

    /**
     * Factory di blocchi;
     */
    Blocco blocco(int dimensione);
    /**
     * Prototype
     */
    Soluzione clone() throws CloneNotSupportedException;
}
