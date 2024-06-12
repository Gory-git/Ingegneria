package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import memento.Originator;

import java.io.Serializable;
import java.util.*;

public interface Soluzione extends Serializable, Cloneable, Iterable<Cella>, Originator
{
    /**
     * il metodo risolve la griglia simil sudoku
     */
    default void risolvi(boolean controllaBlocchi)
    {
        int dimensione = dimensione();
        ArrayList<Integer>[][] inseribili = new ArrayList[dimensione][dimensione];
        ArrayList<Integer>[][] nonInseribili = new ArrayList[dimensione][dimensione];

        for (int i = 0; i < dimensione; i++)
        {
            inseribili[i] = new ArrayList[dimensione];
            nonInseribili[i] = new ArrayList[dimensione];
            for (int j = 0; j < dimensione; j++)
            {
                inseribili[i][j] = new ArrayList();
                nonInseribili[i][j] = new ArrayList();
                for (int k = 1; k < dimensione + 1; k++)
                    inseribili[i][j].add(k);
                Collections.shuffle(inseribili[i][j]);
            }
        }

        for (Cella cella : this)
        {
            int i = cella.getPosizione()[0];
            int j = cella.getPosizione()[1];
            if (cella(i, j).getBlocco() != null && cella(i, j).getBlocco().dimensione() == 1)
            {
                int k = cella(i, j).getBlocco().valore();
                inseribili[i][j].clear();
                posizionaERimuovi(i, j, k, inseribili, nonInseribili);
            }
        }

        risolviBT(0, 0, controllaBlocchi, inseribili, nonInseribili);
    }

    /**
     * il metodo implementa la parte di risolvi. Utilizza il backtracking
     */
    private boolean risolviBT(int riga, int colonna, boolean controllaBlocchi, ArrayList<Integer>[][] inseribili, ArrayList<Integer>[][] nonInseribili)
    {
        if (riga == dimensione())
            return true;

        Cella cellaCorrente = cella(riga, colonna);

        int prossimaColonna = (colonna + 1) % dimensione();
        int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;

        if (cellaCorrente.getValore() != 0)
            return risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi,inseribili, nonInseribili);

        if (inseribili[riga][colonna].isEmpty())
        {
            for (int i = 1; i <= dimensione(); i++)
                inseribili[riga][colonna].add(i);
            Collections.shuffle(inseribili[riga][colonna]);
        }

        while (!inseribili[riga][colonna].isEmpty())
        {
            int valore = inseribili[riga][colonna].remove(0);
            if (controlla(riga, colonna, valore))
            {
                posizionaERimuovi(riga, colonna, valore, inseribili, nonInseribili);
                System.out.println(this);
                if (controllaBlocchi) // FIXME non risolve
                {
                    Blocco blocco = cellaCorrente.getBlocco();

                    boolean pieno = true;
                    for (Cella c : blocco)
                        if (c.getValore() == 0)
                        {
                            pieno = false;
                            break;
                        }
                    if ((!pieno && risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi,inseribili, nonInseribili)) || blocco.soddisfatto())
                        return true;
                } else
                if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi,inseribili, nonInseribili))
                    return true;
                rimuoviEReinserisci(riga, colonna, valore, inseribili, nonInseribili);
            }
        }
        return false;
    }
    private void posizionaERimuovi(int riga, int colonna, int valore, ArrayList<Integer>[][] inseribili, ArrayList<Integer>[][] nonInseribili)
    {
        posiziona(riga, colonna, valore);
        nonInseribili[riga][colonna].add(valore);
        for (int j = 0; j < dimensione(); j++)
        {
            for (int k = 0; k < inseribili[riga][j].size(); k++)
                if (colonna != j && inseribili[riga][j].get(k) == valore)
                    inseribili[riga][j].remove(k);
            for (int k = 0; k < inseribili[j][colonna].size(); k++)
                if (riga != j && inseribili[j][colonna].get(k) == valore)
                    inseribili[j][colonna].remove(k);
        }
    }
    private void rimuoviEReinserisci(int riga, int colonna, int valore, ArrayList<Integer>[][] inseribili, ArrayList<Integer>[][] nonInseribili)
    {
        for (int j = 0; j < dimensione(); j++)
        {
            if (colonna != j && !inseribili[riga][j].contains(valore) && !nonInseribili[riga][j].contains(valore))
                inseribili[riga][j].add(valore);
            if (riga != j && !inseribili[j][colonna].contains(valore) && !nonInseribili[riga][j].contains(valore))
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
    default void popola(int dimensioneMassima)
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
