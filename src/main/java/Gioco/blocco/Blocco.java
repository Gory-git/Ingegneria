package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface Blocco extends Serializable, Iterable<Cella>, Cloneable
{
    boolean pieno();
    void inizializza();
    void aggiungiCella(Cella cella);
    void rimuoviCella(Cella cella);
    boolean soddisfatto();
    List<Cella> celle();

    static boolean verifica(Operatore operatore, int valore, int dimensione, List<Cella> celle)     // TODO CONTROLLO FUNZIONAMENTO
    {
        if ( celle == null)
            throw new IllegalArgumentException("Celle dev'essere non null");
        else if (celle.size() == dimensione)
        {
            for (Cella cella : celle) // caso in cui le celle non sono inizializzate
                if (cella.getValore() == 0)
                    return false;
            if (dimensione == 1 && operatore == Operatore.NONE)
            {   //CASO BLOCCO DI DIMENSIONE UNITARIA, SEMPRE SODDISFATTO SE LA CELLA CONTIENE IL VALORE CORRETTO
                return celle.get(0).getValore() == valore;
            }else if (valore == 0)
            {
                return false;
            } else if (operatore == Operatore.SOMMA)
            {
                int s = 0;
                for (Cella cella : celle)
                    s += cella.getValore();
                return s == valore;
            } else if (operatore == Operatore.SOTTRAZIONE)
            {
                int s = 0;
                for (Cella cella : celle)
                    s = Math.abs(s - cella.getValore());
                return s == valore;
            } else if (operatore == Operatore.MOLTIPLICAZIONE)
            {
                int m = 1;
                for (Cella cella : celle)
                    m *= cella.getValore();
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
    int dimensione();
    public Blocco clone() throws CloneNotSupportedException;
}
