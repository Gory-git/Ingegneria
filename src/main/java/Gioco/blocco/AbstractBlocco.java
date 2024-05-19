package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

public abstract class AbstractBlocco implements Blocco
{
    protected Operatore operatore;
    protected int valore, dimensione;
    @Override
    public int dimensione()
    {
        return dimensione;
    }
    @Override
    public int hashCode()
    {
        int M = 83;
        int H = 1;

        H += M * operatore.hashCode();
        H += M * valore;
        H += M * dimensione();
        for (Cella cella : celle())
            H += M * cella.hashCode();

        return H;
    }
    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof AbstractBlocco))
            return false;
        AbstractBlocco blocco = (AbstractBlocco) obj;
        return this.operatore.equals(blocco.operatore)
                && this.valore == blocco.valore
                && this.dimensione() == blocco.dimensione();
    }
    @Override
    public String toString()
    {
        return "( " + operatore.toString() + " " + valore + " #" + dimensione() + " )";
    }
    public AbstractBlocco clone() throws CloneNotSupportedException
    {
        return (AbstractBlocco) super.clone();
    }

}
