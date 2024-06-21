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

        for (Cella cella : this)
            cella.setValore(0);
    }

    public SoluzioneMatrix(Soluzione soluzione)
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
    public Blocco blocco(int dimensione)
    {
        return new BloccoList(dimensione);
    }

    @Override
    public SoluzioneMatrix clone()
    {
        SoluzioneMatrix clone = new SoluzioneMatrix(this);

        for (Cella cella : clone)
            cella.setValore(0);

        clone.risolvi(true);
        return clone;
    }

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
        Soluzione soluzioneMatrix = new SoluzioneMatrix(mementoSoluzione.soluzione);
        for (int i = 0; i < dimensione(); i++)
            for (int j = 0; j < dimensione(); j++)
                celle[i][j] = soluzioneMatrix.cella(i, j);
    }

    private class MementoSoluzione implements Memento, Serializable
    {
        private final Soluzione soluzione;

        private MementoSoluzione()
        {
            soluzione = new SoluzioneMatrix(SoluzioneMatrix.this);
        }

        private SoluzioneMatrix originator()
        {
            return SoluzioneMatrix.this;
        }
    }
}
