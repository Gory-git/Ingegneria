package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public interface Blocco extends Serializable, Iterable<Cella>, Cloneable
{
    boolean pieno();
    void inizializza();
    void aggiungiCella(Cella cella);
    void rimuoviCella(Cella cella);
    boolean soddisfatto();
    int dimensione();
    int valore();
    List<Cella> celle();
    Operatore operatore();
    default boolean assegnabile(int valore)
    {
        switch (operatore())
        {
            case MOLTIPLICAZIONE ->
            {
                int mul = valore;
                for (Cella cella :this)
                    if (cella.getValore() != 0)
                        mul *= cella.getValore();
                return valore() >= mul;
            }
            case SOMMA ->
            {
                int sum = valore;
                for (Cella cella :this)
                    if (cella.getValore() != 0)
                        sum += cella.getValore();
                return valore() >= sum;
            }
            case SOTTRAZIONE ->
            {

                LinkedList<Cella> celle = new LinkedList<>();
                celle.add(new Cella(valore, new int[]{0,0}));
                for (Cella cella : this)
                    if (cella.getValore() != 0)
                        celle.add(cella);

                Collections.sort(celle);
                Collections.reverse(celle);

                int sub = celle.get(0).getValore() * 2;
                for (Cella cella : celle)
                    sub -= cella.getValore();

                return valore() <= sub;
            }
            case DIVISIONE ->
            {
                int div = valore;
                for (Cella cella :this)
                {
                    if (div == 0 || cella.getValore() == 0 || div % cella.getValore() != 0)
                        return false;
                    div /= cella.getValore();
                }
                return valore() <= div;
            }
        }
        return false;
    }

    static boolean verifica(Operatore operatore, int valore, int dimensione, List<Cella> celle)
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
    public Blocco clone() throws CloneNotSupportedException;
}
