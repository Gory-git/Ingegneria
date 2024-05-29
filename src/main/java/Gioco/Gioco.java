package Gioco;

import Gioco.mediator.ConcreteMediator;
import Gioco.mediator.Mediator;
import Gioco.memento.Memento;
import Gioco.memento.Originator;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public enum Gioco implements Originator
{
    INSTANCE;
    private LinkedList<Soluzione> soluzioni = new LinkedList<>();
    public void avvia(int soluzioni, int dimensione) throws CloneNotSupportedException, IOException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");


        this.soluzioni.add(new SoluzioneMatrix(dimensione));

        for (int i = 0; i < soluzioni; i++)
            this.soluzioni.add(this.soluzioni.getFirst().clone());
    }


    public boolean inserisciValore(int riga, int colonna, int valore)
    {
        soluzioni.getFirst().posiziona(riga, colonna, valore);
        return soluzioni.getFirst().verifica();
    }

    public void rimuoviValore(int riga, int colonna)
    {
        soluzioni.getFirst().rimuovi(riga, colonna);
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

        MementoGioco mementoSoluzione = (MementoGioco) memento;
        //if (this != mementoSoluzione.originator())  // TODO valutare se utile. In questo caso credo non lo sia
        //    throw new IllegalArgumentException("Memento non corretto");
        for (Soluzione soluzione : mementoSoluzione.soluzioni)
        {
            try
            {
                this.soluzioni.add(new SoluzioneMatrix(soluzione));
            } catch (CloneNotSupportedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private class MementoGioco implements Memento, Serializable
    {
        LinkedList<Soluzione> soluzioni = new LinkedList<>();

        private MementoGioco()
        {
            for (Soluzione soluzione : Gioco.this.soluzioni)
            {
                try
                {
                    this.soluzioni.add(new SoluzioneMatrix(soluzione));
                } catch (CloneNotSupportedException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
