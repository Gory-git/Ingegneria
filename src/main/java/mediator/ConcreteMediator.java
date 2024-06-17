package mediator;

import Command.Command;
import Command.CommandHanler;
import Gioco.Gioco;
import memento.Memento;
import Gioco.soluzione.Soluzione;
import observer.Subscriber;
import permanenza.PermanenzaFile;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ConcreteMediator implements Mediator
{
    private Gioco istanza = Gioco.INSTANCE;
    private LinkedList<Subscriber> subscribers = new LinkedList<>();

    @Override
    public void avvia(int numeroSoluzioni, int dimensioniGriglia, int numeroBlocchi)
    {
        try
        {
            istanza.avvia(numeroSoluzioni, dimensioniGriglia, numeroBlocchi);
        } catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void salva()
    {
        Memento memento = istanza.salva();
        PermanenzaFile.salva(memento);
    }

    @Override
    public void carica()
    {
        Memento memento = PermanenzaFile.carica();
        istanza.ripristina(memento);
    }

    @Override
    public void inserisciValore(int riga, int colonna, int valore)
    {
        istanza.inserisciValore(riga, colonna, valore);
    }

    @Override
    public int valore(int riga, int colonna)
    {
        return istanza.valore(riga, colonna);
    }

    @Override
    public int valore(int riga, int colonna, int indiceSoluzione)
    {
        return valore(riga, colonna, indiceSoluzione);
    }

    @Override
    public int numeroSoluzioni()
    {
        return istanza.numeroSoluzioni();
    }

    @Override
    public boolean controlla(int riga, int colonna, int valore)
    {
        return istanza.controlla(riga, colonna, valore);
    }

    @Override
    public int dimensione()
    {
        return istanza.dimensione();
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
