package Gioco;

public class Cella
{
    private int valore;
    private Blocco blocco;

    public Cella(int valore)
    {
        this.valore = valore;
    }

    public Cella(int valore, Blocco blocco)
    {
        this.valore = valore;
        this.blocco = blocco;
    }
}
