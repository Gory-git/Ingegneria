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

    default void risolvi(boolean controllaBlocchi)
    {
        risolviBT(0,0,controllaBlocchi);
    }
    /**
     * il metodo implementa la parte di backtracking di risolvi
     */
    private boolean risolviBT(int riga, int colonna, boolean controllaBlocchi)      // FIXME VERSIONE CON TRUE, molto lenta e non funziona correttamente
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
                    Blocco blocco = cella(riga, colonna).getBlocco();
                    boolean pienoOSoddisfatto = !blocco.pieno() || blocco.soddisfatto();
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi) && pienoOSoddisfatto)
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
        // caso di ingresso OR caso in cui il blocco precedente è stato posizionato correttamente
        if (blocco == null)
        {
            int dimensione = new Random().nextInt(1, (dimensioneMassima / 2) + 1);
            for (int i = dimensione; i > 0; i--)
            {
                blocco = blocco(dimensione);
                cella.setBlocco(blocco);
                blocco.aggiungiCella(cella);
                if (popolaBT(dimensioneMassima - dimensione, cella))
                    return true;
                if (cella.getBlocco() != null)
                {
                    blocco.rimuoviCella(cella);
                    cella.setBlocco(null);
                }
            }
            return false;
        }
        // caso in cui il blocco non è stato riempito.
        if (!blocco.pieno())
        {
            Cella ultimoVicino = null;
            for (Cella vicino : vicini)
                if (vicino.getBlocco() == null)
                {
                    vicino.setBlocco(blocco);
                    blocco.aggiungiCella(vicino);
                    ultimoVicino = vicino;
                    if (blocco.pieno())
                        break;
                }
            if (ultimoVicino != null)
                return popolaBT(dimensioneMassima, ultimoVicino);
            else
            {
                blocco.rimuoviCella(cella);
                cella.setBlocco(null);
                for (Cella vicino : vicini)
                    if (vicino.getBlocco() == blocco)
                    {
                        vicino.setBlocco(null);
                        blocco.rimuoviCella(vicino);
                    }
                return false;
            }
        }
        // caso in cui il blocco è pieno
        if (blocco.pieno())
        {
            for (Cella vicino : vicini)
                if (vicino.getBlocco() == null)
                    popolaBT(dimensioneMassima, vicino);
            for (Cella next : this)
                if (next.getBlocco() == null)
                    popolaBT(dimensioneMassima, next);
        }
        return true;
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
