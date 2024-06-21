package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import memento.Memento;
import observer.Subscriber;

import java.io.Serializable;
import java.util.*;

public final class SoluzioneMatrix extends AbstractSoluzione
{
    private final Cella[][] celle;

    public SoluzioneMatrix(int dimensione, int numeroBlocchi)
    {
        if (dimensione < 1)
            throw new IllegalArgumentException("Dimensione non valida");
        if (numeroBlocchi > dimensione * dimensione)
            throw new IllegalArgumentException("Non possono essere creati tutti questi blocchi");
        celle = new Cella[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++)
        {
            for (int j = 0; j < dimensione; j++)
            {
                int[] posizione = {i, j};
                celle[i][j] = new Cella(0, posizione);
            }
        }

        risolvi(false);
        popola(numeroBlocchi);

        // System.out.println(this);

        for (Cella cella : this)
            cella.setValore(0);
    }

    public SoluzioneMatrix(Soluzione soluzione) throws CloneNotSupportedException
    {
        if (soluzione == null)
            throw new IllegalArgumentException("Soluzione non valida");

        Set<Blocco> blocchi = new HashSet<>();
        for (Cella cella : soluzione)
            blocchi.add(cella.getBlocco().clone());

        int dimensione = soluzione.dimensione();
        celle = new Cella[dimensione][dimensione];

        for (Blocco blocco : blocchi)
            for (Cella cella : blocco.celle())
                celle[cella.getPosizione()[0]][cella.getPosizione()[1]] = cella;
    }

    @Override
    public void posiziona(int riga, int colonna, int valore)
    {
        // if (controlla(riga, colonna, valore) || valore == 0)
            celle[riga][colonna].setValore(valore);
    }

    @Override
    public boolean controlla(int riga, int colonna, int valore)     // TODO forse private
    {
        for (int i = 0; i < celle.length; i++)
            if (i != riga && celle[i][colonna].getValore() == valore || i != colonna && celle[riga][i].getValore() == valore)
                return false;
        return true;
    }

    @Override
    public List<Cella> vicini(int riga, int colonna)                // TODO forse private
    {
        List<Cella> vicini = new LinkedList<>();

        if (riga > 0)
            vicini.add(celle[riga - 1][colonna]);
        if (colonna > 0)
            vicini.add(celle[riga][colonna - 1]);
        if (colonna < celle.length - 1)
            vicini.add(celle[riga][colonna + 1]);
        if (riga < celle.length - 1)
            vicini.add(celle[riga + 1][colonna]);

        return vicini;
    }

    @Override
    public int dimensione()
    {
        return celle.length;
    }

    @Override
    public Cella cella(int riga, int colonna)
    {
        return celle[riga][colonna];
    }

    @Override
    public Blocco blocco(int dimensione)                            // TODO forse private
    {
        return new BloccoList(dimensione);
    }

    @Override
    public SoluzioneMatrix clone() throws CloneNotSupportedException
    {
        SoluzioneMatrix clone = new SoluzioneMatrix(this);

        for (Cella cella : clone)
            cella.setValore(0);

        clone.risolvi(true);
        return clone;
    }
/*
    @Override
    public boolean risolta()
    {
        for (Cella cella : this)
            if (!controlla(cella.getPosizione()[0], cella.getPosizione()[1], cella.getValore()) || !cella.getBlocco().soddisfatto())
                return false;
        return true;
    }
/**/

    @Override
    public Memento salva()
    {
        return new MementoSoluzione();
    }

    @Override
    public void ripristina(Memento memento)
    {
        if (!(memento instanceof MementoSoluzione mementoSoluzione))
            throw new IllegalArgumentException("Memento non corretto");
        try
        {
            Soluzione soluzioneMatrix = new SoluzioneMatrix(mementoSoluzione.soluzione);
            for (int i = 0; i < dimensione(); i++)
                for (int j = 0; j < dimensione(); j++)
                    celle[i][j] = soluzioneMatrix.cella(i, j);
        } catch (CloneNotSupportedException e)
        {
            throw new RuntimeException(e);
        }
    }

    private class MementoSoluzione implements Memento, Serializable
    {
        private final Soluzione soluzione;

        private MementoSoluzione()
        {
            try
            {
                soluzione = new SoluzioneMatrix(SoluzioneMatrix.this);
            } catch (CloneNotSupportedException e)
            {
                throw new RuntimeException(e);
            }
        }

        SoluzioneMatrix originator()
        {
            return SoluzioneMatrix.this;
        }
    }
}
