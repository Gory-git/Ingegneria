package mediator;

import Gioco.Gioco;
import grafica.FinestraFinale;
import grafica.FinestraGioco;
import grafica.FinestraIniziale;
import grafica.FinestraNuovoGioco;
import memento.Memento;
import observer.Subscriber;
import permanenza.PermanenzaFile;

import java.io.IOException;
import java.util.LinkedList;

public class MediatorConcreto implements Mediator
{
    private final Gioco istanza = Gioco.INSTANCE;
    private final LinkedList<Subscriber> subscribers = new LinkedList<>();
    private FinestraIniziale finestraIniziale;
    private FinestraNuovoGioco finestraNuovoGioco;
    private FinestraGioco finestraGioco;
    private FinestraFinale finestraFinale;

    @Override
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
        return istanza.valore(riga, colonna, indiceSoluzione);
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
    public String[] blocco(int riga, int colonna)
    {
        return istanza.blocco(riga, colonna);
    }

    @Override
    public void crea(String nome)
    {
        switch (nome.toLowerCase())
        {
            case "i" :
            {
                finestraIniziale = new FinestraIniziale();
                finestraIniziale.setMediator(this);
                break;
            }
            case "ng" :
            {
                finestraNuovoGioco = new FinestraNuovoGioco();
                finestraNuovoGioco.addSubscriber(finestraIniziale);
                finestraNuovoGioco.setMediator(this);
                break;
            }
            case "gi":
            {
                finestraGioco = new FinestraGioco(this);
                finestraGioco.addSubscriber(finestraIniziale);
                break;
            }
            case "gng":
            {
                finestraGioco = new FinestraGioco(this);
                finestraGioco.addSubscriber(finestraNuovoGioco);
                break;
            }
            case "f":
            {
                finestraFinale = new FinestraFinale(this);
                break;
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

    @Override
    public void update()
    {
        sendNotification();
    }

    public static void main(String[] args)
    {
        new MediatorConcreto().crea("i");
    }
}
