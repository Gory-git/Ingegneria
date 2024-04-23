package Gioco.soluzione;

public interface Soluzione
{
    /*
     * il metodo risolve i blocchi sopra la griglia
     */
    boolean risolvi();
    /*
     * il metodo posiziona il valore nella posizione designata
     */
    boolean posiziona(int riga, int colonna, int valore);
    /*
     * il metodo rimuove il contenuto della posizione designata
     */
    boolean rimuovi (int riga, int colonna);
    /*
     * il metodo controlla se il valore nella posizione designata è ammissibile
     */
    boolean controlla (int riga, int colonna, int valore);
    /*
     * il metodo controlla se la soluzione è valida
     */
    boolean verifica ();
    /*
     * il metodo popola la griglia simil sudoku
     */
    void popola();
    /*
     * il metodo genera soluzioni diverse dalla stessa griglia
     */
    Soluzione creaVariante();

}
