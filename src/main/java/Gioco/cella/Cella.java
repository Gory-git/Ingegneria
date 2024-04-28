package Gioco.cella;

import Gioco.blocco.AbstractBlocco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;

import java.util.Iterator;


public class Cella implements Comparable<Cella>
{
    private int valore;
    private Blocco blocco;

    public Cella(int valore)
    {
        this.valore = valore;
        this.blocco = null;
    }

    public Cella(int valore, Blocco blocco)
    {
        this(valore);
        if ( blocco == null)
            throw new IllegalArgumentException("Blocco dev'essere non null");
        this.blocco = blocco;
    }

    public Cella(Blocco blocco)
    {
        this(0);
        if ( blocco == null)
            throw new IllegalArgumentException("Blocco dev'essere non null");
        this.blocco = blocco;
    }

    public void setValore(int valore)
    {
        this.valore = valore;
    }

    public int getValore()
    {
        return valore;
    }

    public void setBlocco(Blocco blocco)
    {
        if ( blocco == null)
            throw new IllegalArgumentException("Blocco dev'essere non null");
        this.blocco = blocco;
    }

    public Blocco getBlocco()
    {
        return blocco;
    }

    @Override
    public int compareTo(Cella c)
    {
        if(c == null)
            throw new IllegalArgumentException();
        if(this.valore > c.valore)
            return 1;
        if(this.valore < c.valore)
            return -1;
        return 0;
    }
}
