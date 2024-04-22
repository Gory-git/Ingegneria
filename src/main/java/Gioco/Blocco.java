package Gioco;

import java.lang.invoke.StringConcatFactory;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Blocco
{
    private Operatore operatore;
    private int valore, dimensione;
    private List<Cella> celle;

    public Blocco(Operatore operatore, int valore, int dimensione)
    {
        this.operatore = operatore;
        this.valore = valore;
        this.dimensione = dimensione;
        this.celle = new LinkedList<>();
    }

    public Blocco(Operatore operatore, int valore, int dimensione, List celle)
    {
        this(operatore, valore, dimensione);
        if ( celle.size() != dimensione)
            throw new RuntimeException("Non sono state passate il numero corretto di celle");
        else
            this.celle = new LinkedList<Cella>(celle);
        if verifica(operatore, valore, celle);
    }

    public Blocco (Blocco blocco)
    {
        this(blocco.operatore, blocco.valore, blocco.dimensione, blocco.celle);
    }

    public boolean soddisfatto()
    {

    }

    private static boolean verifica (Operatore operatore, int valore, List celle)
    {
        if ( operatore == Operatore.SOMMA )
        {
            int s = 0;
            Iterator iterator = celle.iterator();
            while ( iterator.hasNext() )
                s += (Integer)iterator.next();
            return s == valore;
        } else if ( operatore == Operatore.SOTTRAZIONE )
        {
            int s = 0;
            Iterator iterator = celle.iterator();
            while ( iterator.hasNext())
                s += (Integer)iterator.next();
            return s == valore;
        } else if ( operatore == Operatore.MOLTIPLICAZIONE )
        {
            int m = 1;
            Iterator iterator = celle.iterator();
            while ( iterator.hasNext() )
                m *= (Integer)iterator.next();
            return m == valore;
        }else
        {

        }
    }
}
