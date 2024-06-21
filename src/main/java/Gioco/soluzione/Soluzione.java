package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import memento.Originator;
import observer.Manager;

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

        for (int i = 0; i < dimensione; i++)
        {
            inseribili[i] = new ArrayList[dimensione];
            for (int j = 0; j < dimensione; j++)
            {
                inseribili[i][j] = new ArrayList();
                riempi(i, j, inseribili);
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
                posizionaERimuovi(i, j, k, inseribili);
            }
        }
        risolviBT(0, 0, controllaBlocchi, inseribili);
    }

    /**
     * il metodo implementa la parte di risolvi. Utilizza il backtracking
     */
    private boolean risolviBT(int riga, int colonna, boolean controllaBlocchi, ArrayList<Integer>[][] inseribili)
    {
        if (riga == dimensione())
            return true;

        Cella cellaCorrente = cella(riga, colonna);

        int prossimaColonna = (colonna + 1) % dimensione();
        int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;

        if (cellaCorrente.getValore() != 0)
            return risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi, inseribili);

        while (!inseribili[riga][colonna].isEmpty())
        {
            //System.out.println("R: " + riga + " ;C: " + colonna + " not empty");
            //System.out.println(inseribili[riga][colonna]);
            int valore = inseribili[riga][colonna].remove(0);
            if (controlla(riga, colonna, valore))
            {
                //System.out.println("R: " + riga + " ;C: " + colonna + " --> valore assegnabile: " + valore);
                posizionaERimuovi(riga, colonna, valore, inseribili);
                if (controllaBlocchi) // FIXME ogni tanto si rincoglionisce pd
                {
                    if (risolta())
                        return true;
                    Blocco blocco = cellaCorrente.getBlocco();
                    boolean pieno = true;
                    for (Cella c : blocco)
                        if (c.getValore() == 0)
                        {
                            pieno = false;
                            break;
                        }
                    if (!pieno && risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi,inseribili))
                        return true;
                    for (Cella c : blocco)
                        if (c.getValore() == 0)
                        {
                            pieno = false;
                            break;
                        }
                    if (pieno && blocco.soddisfatto())
                        if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi,inseribili))
                            return true;
                } else
                {
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi, inseribili))
                        return true;
                }
                rimuoviEReinserisci(riga, colonna, valore, inseribili);
            }
        }
        riempi(riga, colonna, inseribili);
        return false;
    }
    private void riempi(int riga, int colonna, ArrayList<Integer>[][] inseribili)
    {
        for (int k = 1; k <= dimensione(); k++)
            if (controlla(riga, colonna, k))
                inseribili[riga][colonna].add(k);
        Collections.shuffle(inseribili[riga][colonna]);
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
     * restituisce true se la soluzione è riempita correttamente e i blocchi sono soddisfatti
     */
    default boolean risolta()
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
    default void popola(int numeroBlocchi)   // FIXME aggiungere controllo celle di un blocco tutte adiacenti.
    {
        int dimensioneMassima = dimensione() * dimensione();
        LinkedList<Blocco> blocchi = new LinkedList<>();
        if (numeroBlocchi > 1)
            for (int i = 0; i < numeroBlocchi - 1; i++)
            {
                int dimensione = new Random().nextInt(1, Math.max(Math.floorDiv(dimensioneMassima, numeroBlocchi - i) + 1, 1/*Math.floorDiv(dimensione() * dimensione(), numeroBlocchi) + 1*/));
                dimensioneMassima -= dimensione;
                blocchi.add(blocco(dimensione));
            }

        blocchi.add(blocco(dimensioneMassima));
        Collections.shuffle(blocchi);

        for (Blocco blocco : blocchi)
        {
            for (Cella cella : this)
                if (cella.getBlocco() == null && popolaBT(cella,blocco))
                    break;
        }
    }
    /**
     * il metodo implementa la parte backtracking di popola
     * @param cella
     * @param blocco
     * @return
     */
    private boolean popolaBT(Cella cella, Blocco blocco) // TODO creare nuova implementazione. SIAMO ALLA 5 %$*%#@
    {
        if (blocco.pieno())
            return true;
        if (cella.getBlocco() == null)
        {
            cella.setBlocco(blocco);
            blocco.aggiungiCella(cella);
        }

        List<Cella> vicini = vicini(cella.getPosizione()[0], cella.getPosizione()[1]);
        Cella ultimoVicino = null;
        for (Cella vicino : vicini)
            if (vicino.getBlocco() == null && !blocco.pieno())
            {
                vicino.setBlocco(blocco);
                blocco.aggiungiCella(vicino);
                ultimoVicino = vicino;
                if (blocco.pieno())
                    return true;
            }
        if (ultimoVicino != null)
            return popolaBT(ultimoVicino, blocco);
        return false;
    }/**/

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
