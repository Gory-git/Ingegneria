package Command;

import mediator.ConcreteMediator;
import mediator.Mediator;

public class Inserisci implements Command
{
    private int valorePrecedente, riga, colonna, nuovoValore;
    private final Mediator mediator = new ConcreteMediator();
    public Inserisci(int riga, int colonna, int nuovoValore)
    {
        this.riga = riga;
        this.colonna = colonna;
        this.nuovoValore = nuovoValore;
    }

    @Override
    public void execute()
    {
        backup();
        mediator.inserisciValore(riga, colonna, nuovoValore);
    }

    @Override
    public void deExecute()
    {
        mediator.inserisciValore(riga, colonna, valorePrecedente);
    }

    @Override
    public void backup()
    {
        mediator.valore(riga, colonna);
    }
}
