package Gioco.mediator;

import Gioco.Gioco;
import Gioco.memento.Memento;
import Gioco.soluzione.Soluzione;
import Interfaccia.Finestra;
import Permanenza.PermanenzaFile;

import java.io.IOException;
import java.util.List;

public class ConcreteMediator implements Mediator // TODO
{
    private Gioco istanza = Gioco.INSTANCE;

    @Override
    public void avvia(int numeroSoluzioni, int dimensioniGriglia)
    {
        try
        {
            istanza.avvia(numeroSoluzioni, dimensioniGriglia);
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
        Memento memento = Gioco.INSTANCE.salva(); // FIXME Gioco deve implementare Originator.
        PermanenzaFile.salva(memento);
    }

    @Override
    public void carica()
    {
        Memento memento = PermanenzaFile.carica();
        istanza.ripristina(memento);
    }

    @Override
    public List<Soluzione> soluzioni()
    {
        return istanza.getSoluzioni();
    }

    @Override
    public void inserisciValore(int riga, int colonna, int valore)
    {
        istanza.inserisciValore(riga, colonna, valore);
    }
}
