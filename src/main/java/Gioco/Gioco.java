package Gioco;

import memento.Memento;
import memento.Originator;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import observer.Manager;
import observer.Subscriber;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public enum Gioco implements Originator, Manager
{
    INSTANCE;
    private LinkedList<Soluzione> soluzioni;
    private LinkedList<Subscriber> subscribers = new LinkedList<>();

    public void avvia(int soluzioni, int dimensione, int numeroBlocchi) throws CloneNotSupportedException, IOException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");

        this.soluzioni = new LinkedList<>();
        this.soluzioni.add(new SoluzioneMatrix(dimensione, numeroBlocchi));

        for (int i = 0; i < soluzioni; i++)
        {
            Soluzione clone;
            boolean uguale = false;
            do
            {
                clone = this.soluzioni.getFirst().clone();
                for (Soluzione soluzione : this.soluzioni)
                    if (soluzione.equals(clone))
                        uguale = true;
            }while (uguale);
            this.soluzioni.add(clone);

        }
    }

    public void inserisciValore(int riga, int colonna, int valore)
    {
        soluzioni.getFirst().posiziona(riga, colonna, valore);
        if (soluzioni.getFirst().risolta())
            sendNotification();
    }

    public int valore(int riga, int colonna)
    {
        return soluzioni.get(0).cella(riga, colonna).getValore();
    }

    public int valore(int riga, int colonna, int indiceSoluzione)
    {
        if (indiceSoluzione < 0 || indiceSoluzione > soluzioni.size() - 1)
            throw new IllegalArgumentException("Inserire un indice di soluzione valido");
        return soluzioni.get(indiceSoluzione + 1).cella(riga, colonna).getValore();
    }

    public int numeroSoluzioni()
    {
        return soluzioni.size() - 1;
    }

    public boolean controlla(int riga, int colonna, int valore)
    {
        return soluzioni.get(0).controlla(riga, colonna, valore);
    }

    public int dimensione()
    {
        return soluzioni.get(0).dimensione();
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

    @Override
    public void addSubscriber(Subscriber subscriber)
    {
        if (subscribers.contains(subscriber))
            throw new IllegalArgumentException("Subscriber presente!");
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber)
    {
        if (!subscribers.contains(subscriber))
            throw new IllegalArgumentException("Subscriber non presente!");
        subscribers.remove(subscriber);
    }

    @Override
    public void sendNotification()
    {
        for (Subscriber subscriber : subscribers)
            subscriber.update();
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
