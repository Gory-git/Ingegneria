package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import Gioco.memento.Memento;
import Gioco.memento.Originator;

import java.io.Serializable;
import java.util.*;

public interface Soluzione extends Serializable, Cloneable, Iterable<Cella>, Originator
{
    /**
     * il metodo risolve la griglia simil sudoku
     */

    default void risolvi(boolean controllaBlocchi)
    {
        ArrayList<Integer>[][] inseribili = new ArrayList[dimensione()][dimensione()];

        for (int i = 0; i < dimensione(); i++)
        {
            inseribili[i] = new ArrayList[dimensione()];
            for (int j = 0; j < dimensione(); j++)
            {
                inseribili[i][j] = new ArrayList();
                for (int k = 1; k < dimensione() + 1; k++)
                {
                    inseribili[i][j].add(k);
                }
                Collections.shuffle(inseribili[i][j]);
            }
        }

        if (!controllaBlocchi)
            risolviBT(0,0, inseribili, false);
        else
        {
            for (Cella cella : this)
            {
                int riga = cella.getPosizione()[0];
                int colonna = cella.getPosizione()[1];
                if (controllaBlocchi && cella(riga, colonna).getBlocco() != null && cella(riga, colonna).getBlocco().dimensione() == 1)
                {
                    int i = cella(riga, colonna).getBlocco().valore();
                    inseribili[riga][colonna].clear();
                    inseribili[riga][colonna].add(i);
                }
            }
            risolviBT(0,0, inseribili, controllaBlocchi);
        }


    }
    /**
     * il metodo implementa la parte di risolvi senza il vincolo dei blocchi. Utilizza il backtracking
     */
    private boolean risolviBT(int riga, int colonna, ArrayList<Integer>[][] inseribili, boolean controllaBlocchi)
    {
        if (riga == dimensione() || colonna == dimensione())
            return true;

        int prossimaColonna = (colonna + 1) % dimensione();
        int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;

        while (!inseribili[riga][colonna].isEmpty())
        {
            int i = inseribili[riga][colonna].remove(0);
            if (controllaBlocchi)
            {
                if (controlla(riga, colonna, i))
                {
                    Blocco blocco = cella(riga, colonna).getBlocco();

                    boolean bloccoPieno = true;
                    for (Cella cella : blocco.celle())
                        if (cella.getValore() == 0)
                        {
                            bloccoPieno = false;
                            break;
                        }

                    posizionaERimuovi(riga, colonna, i, inseribili);
                    if (risolviBT(prossimaRiga, prossimaColonna, inseribili, controllaBlocchi) && (!bloccoPieno || blocco.soddisfatto()))
                        return true;
                }
            }else
                if (controlla(riga, colonna, i))
                {
                    posizionaERimuovi(riga, colonna, i, inseribili);
                    if (risolviBT(prossimaRiga, prossimaColonna, inseribili, controllaBlocchi))
                        return true;
                }
            rimuoviEReinserisci(riga, colonna, i, inseribili);
        }
        return false;
    }
    private void posizionaERimuovi(int riga, int colonna, int valore, ArrayList<Integer>[][] inseribili)
    {
        posiziona(riga, colonna, valore);
        for (int j = 0; j < dimensione(); j++)
        {
            for (int k = 0; k < inseribili[riga][j].size(); k++)
                if (colonna < j && inseribili[riga][j].get(k) == valore)
                    inseribili[riga][j].remove(k);
            for (int k = 0; k < inseribili[j][colonna].size(); k++)
                if (riga < j && inseribili[j][colonna].get(k) == valore)
                    inseribili[j][colonna].remove(k);
        }
    }
    private void rimuoviEReinserisci(int riga, int colonna, int valore, ArrayList<Integer>[][] inseribili)
    {
        for (int j = 0; j < dimensione(); j++)
        {
            if (colonna < j && !inseribili[riga][j].contains(valore))
                inseribili[riga][j].add(valore);
            if (riga < j && !inseribili[j][colonna].contains(valore))
                inseribili[j][colonna].add(valore);
        }
        rimuovi(riga, colonna);
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
    default void popola(int dimensioneMassima) // FIXME ogni tanto lancia qualche stronzata
    {
        popolaBT(dimensioneMassima, cella(0,0));
        for (Cella next : this)
            if (next.getBlocco() == null)
            {
                Blocco blocco = blocco(1);
                blocco.aggiungiCella(next);
                next.setBlocco(blocco);
            }
    }
    /**
     * ++-`
     * il metodo implementa la parte backtracking di popola
     */
    private boolean popolaBT(int dimensioneMassima, Cella cella)
    {
        // caso di uscita
        if (dimensioneMassima <= 1)
            return true;
        List<Cella> vicini = vicini(cella.getPosizione()[0], cella.getPosizione()[1]);
        Blocco blocco = cella.getBlocco();
        // caso di ingresso OR caso in cui il blocco precedente
        // è stato posizionato correttamente e quindi è tutto pieno
        if (blocco == null)
        {
            int dimensione = new Random().nextInt(1, Math.min(dimensioneMassima, dimensione()));
            for (int i = dimensione; i > 0; i--)
            {
                blocco = blocco(dimensione);
                cella.setBlocco(blocco);
                blocco.aggiungiCella(cella);
                if (popolaBT(dimensioneMassima - dimensione, cella) && blocco.pieno())
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
                if (vicino.getBlocco() == null || !vicino.getBlocco().pieno())
                    popolaBT(dimensioneMax(), vicino);
            for (Cella next : this)
                if (next.getBlocco() == null || !next.getBlocco().pieno())
                    popolaBT(dimensioneMax(), next);
        }
        return true;
    }

    private int dimensioneMax()
    {
        int dimensioneMax = 0;
        for (Cella next : this)
            if (next.getBlocco() == null)
                dimensioneMax++;

        return dimensioneMax;
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
    /**
     * restituisce true se la soluzione è riempita correttamente e i blocchi sono soddisfatti
     */
    boolean risolta();

}
