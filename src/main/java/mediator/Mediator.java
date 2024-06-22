package mediator;

import Gioco.Gioco;
import memento.Memento;
import observer.ManagerSubscriber;
import observer.Subscriber;
import permanenza.PermanenzaFile;

import java.io.IOException;
import java.util.LinkedList;

public enum Mediator implements ManagerSubscriber
{
    ISTANZA;
    private final Gioco istanza = Gioco.INSTANCE;
    private final LinkedList<Subscriber> subscribers = new LinkedList<>();

    public void avvia(int numeroSoluzioni, int dimensioniGriglia, int numeroBlocchi)
    {
        try
        {
            istanza.addSubscriber(this);
            istanza.avvia(numeroSoluzioni, dimensioniGriglia, numeroBlocchi);
        } catch (CloneNotSupportedException | IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void salva()
    {
        Memento memento = istanza.salva();
        PermanenzaFile.salva(memento);
    }

    public void carica()
    {
        Memento memento = PermanenzaFile.carica();
        istanza.ripristina(memento);
    }

    public void inserisciValore(int riga, int colonna, int valore)
    {
        istanza.inserisciValore(riga, colonna, valore);
    }

    public int valore(int riga, int colonna)
    {
        return istanza.valore(riga, colonna);
    }

    public int valore(int riga, int colonna, int indiceSoluzione)
    {
        return istanza.valore(riga, colonna, indiceSoluzione);
    }

    public int numeroSoluzioni()
    {
        return istanza.numeroSoluzioni();
    }

    public boolean controlla(int riga, int colonna, int valore)
    {
        return istanza.controlla(riga, colonna, valore);
    }

    public int dimensione()
    {
        return istanza.dimensione();
    }


    public String[] blocco(int riga, int colonna)
    {
        return istanza.blocco(riga, colonna);
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

    @Override
    public void update()
    {
        sendNotification();
    }
}
