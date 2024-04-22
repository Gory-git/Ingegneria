package Gioco.blocco;

import Gioco.operatore.Operatore;

import java.util.Iterator;
import java.util.List;

public interface Blocco
{
    boolean soddisfatto();

    static boolean verifica(Operatore operatore, int valore, int dimensione, List celle)
    {
        if (celle.size() == dimensione)
        {
            if (dimensione == 1)
            {   //CASO BLOCCO DI DIMENSIONE UNITARIA, SEMPRE SODDISFATTO SE HA UNA CELLA PIENA
                return celle.contains(valore);
            } else if (operatore == Operatore.SOMMA)
            {
                int s = 0;
                Iterator iterator = celle.iterator();
                while (iterator.hasNext())
                    s += (Integer) iterator.next();
                return s == valore;
            } else if (operatore == Operatore.SOTTRAZIONE)
            {
                int s = 0;
                Iterator iterator = celle.iterator();
                while (iterator.hasNext())
                    s = Math.abs(s - (Integer) iterator.next());
                return s == valore;
            } else if (operatore == Operatore.MOLTIPLICAZIONE)
            {
                int m = 1;
                Iterator iterator = celle.iterator();
                while (iterator.hasNext())
                    m *= (Integer) iterator.next();
                return m == valore;
            }else
            {
                celle.sort(null);
                int d = 0;
                Iterator iterator = celle.iterator();
                if (iterator.hasNext())
                    d = (Integer) iterator.next();
                while (iterator.hasNext())
                    d /= (Integer) iterator.next();
                return d == valore;
            }
        }
        return false;
    }
}
