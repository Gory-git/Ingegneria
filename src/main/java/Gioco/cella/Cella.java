package Gioco.cella;

import Gioco.blocco.AbstractBlocco;

public class Cella
{
    private int valore;
    private AbstractBlocco blocco;

    public Cella(int valore)
    {
        this.valore = valore;
    }

    public Cella(int valore, AbstractBlocco blocco)
    {
        this.valore = valore;
        this.blocco = blocco;
    }
}
