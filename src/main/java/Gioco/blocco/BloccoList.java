package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BloccoList extends AbstractBlocco
{
    private Operatore operatore;
    private int valore, dimensione;
    private List<Cella> celle;

    public BloccoList(Operatore operatore, int valore, int dimensione)
    {
        this.operatore = operatore;
        this.valore = valore;
        this.dimensione = dimensione;
        this.celle = new LinkedList<>();
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

    public BloccoList(Blocco blocco)
    {
        // TODO basarsi su BIGINTLL
        // ha senso di esistere?
    }

    public boolean soddisfatto()
    {
        return Blocco.verifica(operatore, valore, dimensione, celle);
    }

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
