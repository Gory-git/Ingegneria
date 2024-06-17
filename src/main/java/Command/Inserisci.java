package Command;

import mediator.ConcreteMediator;
import mediator.Mediator;

public class Inserisci implements Command
{
    private int valorePrecedente, riga, colonna;
    private final Mediator mediator = new ConcreteMediator();
    @Override
    public void execute(int riga, int colonna, int valore)
    {
        backup(riga, colonna);
        this.riga = riga;
        this.colonna = colonna;
        mediator.inserisciValore(riga, colonna, valore);
    }

    @Override
    public void deExecute()
    {
        mediator.inserisciValore(riga, colonna, valorePrecedente);
    }

    @Override
    public void backup(int riga, int colonna)
    {
        mediator.valore(riga, colonna);
    }
}
