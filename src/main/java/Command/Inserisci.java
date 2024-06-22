package Command;

import mediator.Mediator;

public class Inserisci implements Command
{
    private int valorePrecedente, riga, colonna, nuovoValore, x, y;
    private final Mediator mediator = Mediator.ISTANZA;
    public Inserisci(int riga, int colonna, int nuovoValore, int x, int y)
    {
        this.riga = riga;
        this.colonna = colonna;
        this.nuovoValore = nuovoValore;
        this.x = x;
        this.y = y;
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
        valorePrecedente = mediator.valore(riga, colonna);
    }

    public int getNuovoValore()
    {
        return nuovoValore;
    }

    public int getValorePrecedente()
    {
        return valorePrecedente;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
