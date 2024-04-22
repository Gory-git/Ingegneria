package Gioco.blocco;

import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

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

    public BloccoList(Operatore operatore, int valore, int dimensione, List celle)
    {
        this(operatore, valore, dimensione);
        if ( celle.size() != dimensione)
            throw new RuntimeException("Non sono state passate il numero corretto di celle");
        else
            this.celle = new LinkedList<Cella>(celle);
        if ( Blocco.verifica(operatore, valore, dimensione, celle) );
    }

    public BloccoList(Blocco blocco)
    {
        this(blocco.operatore, blocco.valore, blocco.dimensione, blocco.celle);
    }

    public boolean soddisfatto()
    {
        return Blocco.verifica(operatore, valore, dimensione, celle);
    }




    public void setCelle(List<Cella> celle)
    {
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
