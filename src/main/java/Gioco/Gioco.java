package Gioco;

import memento.Memento;
import memento.Originator;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public enum Gioco implements Originator
{
    INSTANCE;
    private LinkedList<Soluzione> soluzioni;

    public void avvia(int soluzioni, int dimensione) throws CloneNotSupportedException, IOException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");

        this.soluzioni = new LinkedList<>();
        this.soluzioni.add(new SoluzioneMatrix(dimensione));

        for (int i = 0; i < soluzioni; i++)
            this.soluzioni.add(this.soluzioni.getFirst().clone());
    }


    public void inserisciValore(int riga, int colonna, int valore)
    {
        soluzioni.getFirst().posiziona(riga, colonna, valore);
    }

    public List<Soluzione> getSoluzioni()
    {
        return soluzioni;
    }

    @Override
    public Memento salva()
    {
        return new MementoGioco();
    }

    @Override
    public void ripristina(Memento memento)
    {
        if (!(memento instanceof MementoGioco))
            throw new IllegalArgumentException("Memento non corretto");

        MementoGioco mementoGioco = (MementoGioco) memento;
        //if (this != mementoSoluzione.originator())  // TODO valutare se utile. In questo caso credo non lo sia
        //    throw new IllegalArgumentException("Memento non corretto");
        for (Memento mementoSoluzione : mementoGioco.soluzioni)
        {
            try
            {
                this.soluzioni = new LinkedList<>();
                this.soluzioni.add(new SoluzioneMatrix((Soluzione) mementoSoluzione));
            } catch (CloneNotSupportedException e)
            {
                throw new IllegalArgumentException("Memento non corretto: non è una soluzione");
            }
        }
    }

    private class MementoGioco implements Memento, Serializable
    {
        LinkedList<Memento> soluzioni = new LinkedList<>();

        private MementoGioco()
        {
            for (Soluzione soluzione : Gioco.this.soluzioni)
            {
                this.soluzioni.add(soluzione.salva());
            }
        }
    }
}
