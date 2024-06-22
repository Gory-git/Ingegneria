package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.cella.Cella;
import memento.Originator;

import java.io.Serializable;
import java.util.*;

public interface Soluzione extends Serializable, Cloneable, Iterable<Cella>, Originator
{
    /**
     * il metodo posiziona il valore nella posizione designata
     * @param riga riga della cella
     * @param colonna colonna della cella
     * @param valore valore che si vuole posizionare
     */
    default void posiziona(int riga, int colonna, int valore)
    {
        if (riga < 0 || riga >= dimensione())
            throw new IllegalArgumentException("Valore di riga non ammissibile");
        if (colonna < 0 || colonna >= dimensione())
            throw new IllegalArgumentException("Valore di colonna non ammissibile");
        if (valore < 0)
            throw new IllegalArgumentException("Impossibile inserire un valore negativo");
        cella(riga, colonna).setValore(valore);
    }

    /**
     *  il metodo rimuove il contenuto della posizione designata
     * @param riga riga della cella
     * @param colonna colonna della cella
     */
    default void rimuovi(int riga, int colonna)
    {
        posiziona(riga, colonna, 0);
    }

    /**
     * il metodo controlla se il valore nella posizione designata è ammissibile
     * @param riga riga della cella presa in analisi
     * @param colonna colonna della cella presa in analisi
     * @param valore valore che si vuole controllare
     * @return true se il valore, una volta inserito nella cella presa in analisi, rispetta i vincoli
     */
    default boolean controlla(int riga, int colonna, int valore)
    {
        for (int i = 0; i < dimensione(); i++)
            if (i != riga && cella(i,colonna).getValore() == valore || i != colonna && cella(riga,i).getValore() == valore)
                return false;
        return true;
    }

    /**
     * @return restitusisce true se la soluzione è riempita correttamente e i blocchi sono soddisfatti, false altrimenti
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
     * il metodo calcola e restituisce una lista contenente i vicini di una cella
     * @param riga riga presa in analisi
     * @param colonna colonna presa in analisi
     * @return lista di celle contenente i vicini della cella presa in analisi
     */
    private List<Cella> vicini(int riga, int colonna)
    {
        List<Cella> vicini = new LinkedList<>();

        if (riga > 0)
            vicini.add(cella(riga - 1,colonna));
        if (colonna > 0)
            vicini.add(cella(riga,colonna - 1));
        if (colonna < dimensione() - 1)
            vicini.add(cella(riga,colonna + 1));
        if (riga < dimensione() - 1)
            vicini.add(cella(riga + 1,colonna));

        return vicini;
    }

    /**
     * il metodo restituisce la dimensione della griglia
     * @return dimensione della griglia
     */
    int dimensione();

    /**
     * Il metodo restituisce la cella di posizione riga, colonna
     * @param riga riga della cella che si vuole ottenere
     * @param colonna colonna della cella che si vuole ottenere
     * @return Cella richiesta
     */
    Cella cella(int riga, int colonna);

    /**
     * Factory di blocchi
     * @param dimensione dimensione del blocco che si vuole creare
     * @return nuovo blocco con dimensione specificata
     */
    Blocco blocco(int dimensione);

    /**
     * il metodo risolve la griglia invocando l'opportuno metodo,
     * @param controllaBlocchi true se devo controllare il vincolo dei blocchi, false altrimenti
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
                inseribili[i][j] = new ArrayList<>();
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
     * il metodo risllve la griglia utilizzando il backtracking
     * @param riga riga attuale
     * @param colonna colonna attuale
     * @param controllaBlocchi true se devo controllare il vincolo dei blocchi, false altrimenti
     * @param inseribili valori inseribili nella cella corrente rispettanti i vincoli
     * @return true se la soluzione risulta corretta, false se non posso proseguire nella risoluzione
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
            int valore = inseribili[riga][colonna].remove(0);
            if (controlla(riga, colonna, valore))
            {
                posizionaERimuovi(riga, colonna, valore, inseribili);
                if (controllaBlocchi)
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
     * crea i blocchi sopra la griglia inizializzata
     * @param numeroBlocchi numero dei blocchi da posizionare sulla griglia
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

        for (Blocco blocco : blocchi) // FIXME devo migliorarlo
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
    }

    /**
     * Prototype
     * @return un clone di this
     * @throws CloneNotSupportedException siccome potrebbe dover richiamare super.clone()
     */
    Soluzione clone() throws CloneNotSupportedException;

}
