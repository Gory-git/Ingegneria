package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.util.*;

public class BloccoList extends AbstractBlocco
{
    private LinkedList<Cella> celle;

    public BloccoList(Operatore operatore, int valore, int dimensione, List<Cella> celle)
    {
        // CONTROLLO ECCEZIONI
        if ( celle == null)
            throw new IllegalArgumentException("Celle dev'essere non null");
        if ( celle.size() != dimensione)
            throw new IllegalArgumentException("Non sono state passate il numero corretto di celle");

        this.celle = new LinkedList<>();
        for (Cella cella : celle)
            this.celle.add(new Cella(cella.getPosizione(), cella.getValore(), this));
        this.operatore = operatore;
        this.valore = valore;
        this.dimensione = dimensione;
    }

    public BloccoList(int dimensione)
    {
        this.dimensione = dimensione;
        this.celle = new LinkedList<>();
        this.operatore = Operatore.NONE;
    }

    @Override
    public boolean pieno()
    {
        return celle.size() == dimensione();
    }

    @Override
    public void inizializza()
    {
        if (dimensione() != celle().size())
            throw new IllegalStateException("Celle non completamente riempita");
        if (dimensione() == 1)
        {
            operatore = Operatore.NONE;
            valore = celle.get(0).getValore();
        }
        else
        {
            Collections.sort(celle);
            Collections.reverse(celle);
            boolean soddisfatto = false;
            while (!soddisfatto)
            {
                operatore =  Operatore.getRandom();
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
        if (celle.size() > 10)
            return 0;
        int ret = 1;
        for (Cella cella : celle)
            ret *= cella.getValore();
        return ret;
    }

    private static int divisione(List<Cella> celle)
    {
        int ret = celle.get(0).getValore() * celle.get(0).getValore();
        for (Cella cella : celle)
        {
            if (ret == 0 || ret % cella.getValore() != 0)
                return 0;
            ret /= cella.getValore();
        }
        return ret;
    }

    @Override
    public void aggiungiCella(Cella cella)
    {
        if (pieno())
            throw new IllegalStateException("Blocco pieno!");
        if (!pieno())
            celle.add(cella);
        if (pieno())
            inizializza();
    }

    @Override
    public void rimuoviCella(Cella cella)
    {
        if (celle.contains(cella))
            celle.remove(cella);
        else
            throw new IllegalArgumentException("La cella non è in questo bloccco");
    }

    public boolean soddisfatto()
    {
        return Blocco.verifica(operatore, valore, dimensione(), celle);
    }

    @Override
    public int valore()
    {
        return valore;
    }

    @Override
    public List<Cella> celle()
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

    @Override
    public BloccoList clone() throws CloneNotSupportedException
    {
        BloccoList blocco = (BloccoList) super.clone();
        return new BloccoList(blocco.operatore, blocco.valore, blocco.dimensione(), blocco.celle);
    }

    @Override
    public Iterator<Cella> iterator()
    {
        return celle.iterator();
    }
}
