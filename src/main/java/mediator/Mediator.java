package mediator;

import observer.ManagerSubscriber;

public interface Mediator extends ManagerSubscriber
{
    /**
     * avvia una nuova partita
     * @param numeroSoluzioni numero di soluzioni che desidero visualizzare a fine gioco
     * @param dimensioniGriglia dimensioni del puzzle
     * @param numeroBlocchi numero minimo di blocchi che desidero risolvere
     */
    void avvia(int numeroSoluzioni, int dimensioniGriglia, int numeroBlocchi);

    /**
     * metodo che salva la soluzione attuale su file
     */
    void salva();

    /**
     * metodo che carica la soluzione attuale da file
     */
    void carica();

    /**
     * metodo che mi consente di inserire un valore nel puzzle
     * @param riga riga dove voglio effettuare l'inserimento
     * @param colonna colonna dove voglio effettuare l'inserimento
     * @param valore valore che voglio inserire
     */
    void inserisciValore(int riga, int colonna, int valore);

    /**
     * il metodo mi restituisce il valore della cella avente la posizione indicata
     * @param riga riga della cella della quale voglio sapere il valore
     * @param colonna colonna della cella della quale voglio sapere il valore
     * @return restituisce il valore della cella se esistente
     */
    int valore(int riga, int colonna);

    /**
     * il metodo mi restituisce il valore della cella avente la posizione indicata
     * @param riga riga della cella della quale voglio sapere il valore
     * @param colonna colonna della cella della quale voglio sapere il valore
     * @param indiceSoluzione indice di soluzione in analisi
     * @return restituisce il valore della cella se esistente
     */
    int valore(int riga, int colonna, int indiceSoluzione);

    /**
     * restituisce il numero di soluzioni esistenti
     * @return numero di soluzioni esistenti
     */
    int numeroSoluzioni();

    /**
     * controlla se risulta possibile inserire il valore passato nella cella avente le coordinate passate
     * @param riga riga della cella sulla quale si vuole effettuare il controllo
     * @param colonna colonna della cella sulla quale si vuole effettuare il controllo
     * @param valore valore che si vuole controllare
     * @return true se il valore risulta inseribile, false altrimenti
     */
    boolean controlla(int riga, int colonna, int valore);

    /**
     * restituisce la dimensione delle soluzioni
     * @return dimensione delle soluzioni
     */
    int dimensione();

    /**
     * restituisce le informazioni relative al blocco della cella avente le coordinate passate
     * @param riga riga della cella della quale si vuole conoscere il blocco
     * @param colonna colonna della cella della quale si vuole conoscere il blocco
     * @return restituisce una lista contenente le informazioni del blocco
     */
    String[] blocco(int riga, int colonna);

    /**
     * crea il component designato dal parametro passato
     * @param nome nome del component che si vuole creare
     */
    void crea(String nome);
}
