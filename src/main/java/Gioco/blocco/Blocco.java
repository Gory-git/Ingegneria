package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface Blocco extends Serializable, Iterable<Cella>
{
    boolean soddisfatto();

    static boolean verifica(Operatore operatore, int valore, int dimensione, List<Cella> celle)
    {
        if ( celle == null)
            throw new IllegalArgumentException("Celle dev'essere non null");
        else if (celle.size() == dimensione)
        {
            if (dimensione == 1 && operatore == Operatore.NONE)
            {   //CASO BLOCCO DI DIMENSIONE UNITARIA, SEMPRE SODDISFATTO SE LA CELLA CONTIENE IL VALORE CORRETTO
                return celle.get(0).getValore() == valore;
            } else if (operatore == Operatore.SOMMA)
            {
                int s = 0;
                Iterator<Cella> iterator = celle.iterator();
                while (iterator.hasNext())
                    s += iterator.next().getValore();
                return s == valore;
            } else if (operatore == Operatore.SOTTRAZIONE)
            {
                int s = 0;
                Iterator<Cella> iterator = celle.iterator();
                while (iterator.hasNext())
                    s = Math.abs(s - iterator.next().getValore());
                return s == valore;
            } else if (operatore == Operatore.MOLTIPLICAZIONE)
            {
                int m = 1;
                Iterator<Cella> iterator = celle.iterator();
                while (iterator.hasNext())
                    m *= iterator.next().getValore();
                return m == valore;
            }else if(operatore == Operatore.DIVISIONE)
            {
                Collections.sort(celle);
                Collections.reverse(celle);
                int d = 0;
                Iterator<Cella> iterator = celle.iterator();
                if (iterator.hasNext())
                    d = iterator.next().getValore();
                while (iterator.hasNext())
                    d /= iterator.next().getValore();
                return d == valore;
            }
        }
        return false;
    }
}
