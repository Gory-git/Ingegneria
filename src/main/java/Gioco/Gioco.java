package Gioco;

import Gioco.blocco.Blocco;
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
    private final LinkedList<Subscriber> subscribers = new LinkedList<>();
    private int dimensioneSoluzioni, numeroBlocchiSoluzioni;

    public void avvia(int soluzioni, int dimensione, int numeroBlocchi) throws CloneNotSupportedException, IOException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");

        dimensioneSoluzioni = dimensione;
        numeroBlocchiSoluzioni = numeroBlocchi;

        this.soluzioni = new LinkedList<>();
        this.soluzioni.add(new SoluzioneMatrix(dimensione, numeroBlocchi));

        for (int i = 0; i < soluzioni; i++)
        {
            Soluzione clone = this.soluzioni.getFirst().clone();
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
        return soluzioni.get(indiceSoluzione).cella(riga, colonna).getValore();
    }

    public int numeroSoluzioni()
    {
        return soluzioni.size();
    }

    public boolean controlla(int riga, int colonna, int valore)
    {
        return soluzioni.get(0).controlla(riga, colonna, valore);
    }

    public int dimensione()
    {
        return soluzioni.get(0).dimensione();
    }

    public String[] blocco(int riga, int colonna)
    {
        Blocco blocco = soluzioni.getFirst().cella(riga, colonna).getBlocco();
        return new String[]
                {
                        blocco.hashCode() + "",
                        blocco.toString()
                };
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
            throw new IllegalArgumentException("Memento non corretto: non è una soluzione");

        MementoGioco mementoGioco = (MementoGioco) memento;

        for (Memento mementoSoluzione : mementoGioco.soluzioni)
        {
            int dimensione = mementoGioco.dimensioneSoluzioni;
            int numeroBlocchi = mementoGioco.numeroBlocchiSoluzioni;
            if (this.soluzioni == null)
                this.soluzioni = new LinkedList<>();
            Soluzione soluzioneMemento = new SoluzioneMatrix(dimensione, numeroBlocchi);
            soluzioneMemento.ripristina(mementoSoluzione);
            this.soluzioni.add(soluzioneMemento);
        }
    }

    @Override
    public void addSubscriber(Subscriber subscriber)
    {
        if (!subscribers.contains(subscriber))
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
        private final LinkedList<Memento> soluzioni = new LinkedList<>();
        private int dimensioneSoluzioni, numeroBlocchiSoluzioni;

        private MementoGioco()
        {
            for (Soluzione soluzione : Gioco.this.soluzioni)
            {
                this.soluzioni.add(soluzione.salva());
            }
            dimensioneSoluzioni = Gioco.this.dimensioneSoluzioni;
            numeroBlocchiSoluzioni = Gioco.this.numeroBlocchiSoluzioni;
        }
    }
}
