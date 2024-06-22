package Gioco.cella;

import Gioco.blocco.Blocco;
import java.io.Serializable;
import java.util.Arrays;


public final class Cella implements Serializable, Cloneable, Comparable<Cella>
{
    private final int[] posizione;
    private int valore;
    private Blocco blocco;

    public Cella(int[] posizione)
    {
        if (posizione == null)
            throw new IllegalArgumentException("Posizione nulla");
        if(posizione.length != 2 || posizione[0] < 0 || posizione[1] < 0)
            throw new IllegalArgumentException("Posizione non valida");
        this.posizione = new int[]{posizione[0], posizione[1]};
    }
    public Cella(int valore, int[] posizione)
    {
        this(posizione);
        this.valore = valore;
        this.blocco = null;
    }

    public Cella(int[] posizione, int valore, Blocco blocco)
    {
        this(valore, posizione);
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
        this.blocco = blocco;
    }

    public Blocco getBlocco()
    {
        return blocco;
    }

    public int[] getPosizione()
    {
        return posizione;
    }

    @Override
    public int compareTo(Cella c)
    {
        if(c == null)
            throw new IllegalArgumentException();
        return Integer.compare(this.valore, c.valore);
    }

    @Override
    public int hashCode()
    {
        int M = 83;
        int H = 1;

        //H += M * blocco.hashCode();
        H += M * valore;
        H += M * Arrays.hashCode(posizione);

        return H;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof Cella cella))
            return false;

        return this.valore == cella.valore
                && Arrays.equals(this.posizione, cella.posizione)
                && this.blocco.equals(cella.blocco);
    }

    @Override
    public String toString()
    {
        return  posizione[0] + ", " +
                posizione[1] + ": " +
                valore + "->" + blocco;
    }

    @Override
    public Cella clone()
    {
        return new Cella(this.valore, this.posizione);
    }
}
