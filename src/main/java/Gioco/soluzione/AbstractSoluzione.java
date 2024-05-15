package Gioco.soluzione;

import Gioco.cella.Cella;

import javax.swing.*;
import java.util.*;

public abstract class AbstractSoluzione implements Soluzione
{
    @Override
    public int hashCode()
    {
        int M = 83;
        int H = 1;

        Iterator<Cella> iterator = this.iterator();
        while (iterator.hasNext())
            H += M * iterator.next().hashCode();

        return H;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == this)
            return true;
        if(!(obj instanceof AbstractSoluzione))
            return false;
        AbstractSoluzione soluzione = (AbstractSoluzione) obj;

        Iterator<Cella> iterator1 = this.iterator();
        Iterator<Cella> iterator2 = soluzione.iterator();

        while (iterator1.hasNext() && iterator2.hasNext())
            if (!iterator1.next().equals(iterator2.next()))
                return false;
        return iterator1.hasNext() || iterator2.hasNext();
    }

    @Override
    public String toString()
    {
        int dim = dimensione();

        String ret = "";

        int i = 1;
        for (Cella cella : this)
        {
            if (i == 1)
            {
                ret += "|\t";
            }

            ret += cella.toString() + ";\t";

            if ( i == dim )
            {
                ret += "|\n";
                i = 0;
            }
            i++;
        }
        return ret;
    }

    @Override
    public AbstractSoluzione clone() throws CloneNotSupportedException
    {
        return (AbstractSoluzione) super.clone();
    }

    @Override
    public Iterator<Cella> iterator()
    {
        return new Iteratore();
    }

    private class Iteratore implements Iterator<Cella>
    {
        private int[] corrente = {-1, -1};
        private boolean rimuovibile = false;

        @Override
        public boolean hasNext()
        {
            return corrente[0] + corrente[1] < (dimensione() * 2) - 2;
        }

        @Override
        public Cella next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            corrente[1] ++;
            if (corrente[1] == dimensione() || corrente[0] < 0)
            {
                corrente[1] = 0;
                corrente[0] ++;
            }
            rimuovibile = true;
            return cella(corrente[0],corrente[1]);
        }

        @Override
        public void remove()
        {
            if (!rimuovibile)
                throw new IllegalStateException();
            rimuovi(corrente[0], corrente[1]);
        }
    }
}
