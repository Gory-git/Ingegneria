package Gioco.soluzione;

import Gioco.cella.Cella;

import javax.swing.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    public String toString() // FIXME fa cacare, stampa a cazzo di cane
    {
        Iterator<Cella> iterator = this.iterator();
        LinkedList<Cella> celleL = new LinkedList<>();
        while (iterator.hasNext())
            celleL.add(iterator.next());
        int len = celleL.size();
        int dim = (int)Math.sqrt(len);

        List<Cella>[] celle = new LinkedList[dim];

        int k = 0;
        for (int i = 0; i < dim; i++)
        {
            celle[i] = new LinkedList<>();
            for (int j = 0; j < dim; j++)
            {
                celle[i].add(celleL.get(k));
                k++;
            }
            if (i % 2 == 0)
                Collections.reverse(celle[i]);
        }

        String ret = "";

        for (int i = 0; i < dim; i++)
        {
            ret += "|\t";
            for (Cella cella : celle[i])
                ret += cella.toString() + ";\t";
            ret += "|\n";
        }
        return ret;
    }
}
