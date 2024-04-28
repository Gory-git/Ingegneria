package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.util.*;

public class BloccoList extends AbstractBlocco
{
    private List<Cella> celle;

    public BloccoList(Operatore operatore, int valore, int dimensione)
    {
        this(dimensione);
        this.operatore = operatore;
        this.valore = valore;
    }

    public BloccoList(Operatore operatore, int valore, int dimensione, List<Cella> celle)
    {
        // this(operatore, valore, dimensione); CONTROLLO ECCEZIONI
        if ( celle == null)
            throw new IllegalArgumentException("Celle dev'essere non null");
        else if ( celle.size() != dimensione)
            throw new IllegalArgumentException("Non sono state passate il numero corretto di celle");
        else
        {
            this.celle = new LinkedList<>(celle);
            this.operatore = operatore;
            this.valore = valore;
            this.dimensione = dimensione;
        }
        //if ( Blocco.verifica(operatore, valore, dimensione, celle) );
    }

    public BloccoList(int dimensione)
    {
        this.dimensione = dimensione;
        this.celle = new LinkedList<>();
    }

    @Override
    public boolean pieno()
    {
        return celle.size() == dimensione;
    }

    @Override
    public void inizializza()
    {
        if (dimensione == 1)
        {
            operatore = Operatore.NONE;
            valore = celle.get(0).getValore();
        }
        else
        {
            Collections.sort(celle);
            boolean soddisfatto = false;
            while (!soddisfatto)
            {
                operatore =  Operatore.DIVISIONE.getRandom();
                switch (operatore)
                {
                    case SOMMA -> valore = somma(celle);
                    case SOTTRAZIONE -> valore = sottrazione(celle);
                    case MOLTIPLICAZIONE -> valore = moltiplicazione(celle);
                    case DIVISIONE -> valore = divisione(celle);
                }
                soddisfatto = soddisfatto();
            }

        }
    }

    private static int somma(List<Cella> celle)
    {
        int ret = 0;
        for (Cella cella : celle)
            ret += cella.getValore();
        return ret;
    }

    private static int sottrazione(List<Cella> celle)
    {
        int ret = celle.get(0).getValore() * 2;
        for (Cella cella : celle)
            ret -= cella.getValore();
        return ret;
    }

    private static int moltiplicazione(List<Cella> celle)
    {
        int ret = 1;
        for (Cella cella : celle)
            ret *= cella.getValore();
        return ret;
    }

    private static int divisione(List<Cella> celle)
    {
        int ret = celle.get(0).getValore() * celle.get(0).getValore();
        for (Cella cella : celle)
            ret += cella.getValore();
        return ret;
    }

    @Override
    public void aggiungiCella(Cella cella)
    {
        if (celle.size() < dimensione)
            celle.add(cella);
        if (pieno())
            inizializza();
    }

    public boolean soddisfatto()
    {
        return Blocco.verifica(operatore, valore, dimensione, celle);
    }

    @Override
    public Blocco duplica()
    {
        return new BloccoList(this.operatore, this.valore, this.dimensione);
    }

    @Override
    public Iterator<Cella> iterator()
    {
        return celle.iterator();
    }

    public void setCelle(List<Cella> celle)
    {
        if (celle == null)
            throw new IllegalArgumentException("Celle dev'essere non null");
        if (celle.size() != dimensione)
            throw new IllegalArgumentException("Celle di dimensione errata");
        this.celle = new LinkedList<>(celle);
    }

    public List<Cella> getCelle()
    {
        return new LinkedList<>(celle);
    }

    public Operatore getOperatore()
    {
        return operatore;
    }

    public int getValore()
    {
        return valore;
    }

    public int getDimensione()
    {
        return dimensione;
    }
}
