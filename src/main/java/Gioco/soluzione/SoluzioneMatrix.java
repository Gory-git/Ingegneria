package Gioco.soluzione;

import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;

import java.util.*;

public class SoluzioneMatrix extends AbstractSoluzione
{
    private Cella[][] celle;

    public SoluzioneMatrix(int dimensione)
    {
        if (dimensione < 1)
            throw new IllegalArgumentException("Dimensione non valida");
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
        popola(dimensione * dimensione);

        System.out.println(this);

        for (Cella cella : this)
            cella.setValore(0);
    }

    private SoluzioneMatrix(Soluzione soluzione) throws CloneNotSupportedException
    {
        if (soluzione == null)
            throw new IllegalArgumentException("Soluzione non valida");
        
        LinkedList<Blocco> blocchi = new LinkedList<>();
        for (Cella cella : soluzione)
            if (!blocchi.contains(cella.getBlocco()))
                blocchi.add(cella.getBlocco().clone());
        
        int dimensione = soluzione.dimensione();
        celle = new Cella[dimensione][dimensione];

        for (Blocco blocco : blocchi)
            for (Cella cella : blocco.celle())
                celle[cella.getPosizione()[0]][cella.getPosizione()[1]] = cella;

        risolvi(true);
    }

    @Override
    public void risolvi(boolean controllaBlocchi)
    {
        risolviBT(0,0,controllaBlocchi);
    }

    @Override
    public void posiziona(int riga, int colonna, int valore)
    {
        if (controlla(riga, colonna, valore) || valore == 0)
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
    public void popola(int dimensioneMassima)
    {
        if (!popolaBT(dimensioneMassima, celle[0][0]))
            throw new RuntimeException("Impossibile risolvere");
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
    public int dimensione()                                         // TODO forse private
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
        SoluzioneMatrix soluzione = (SoluzioneMatrix) super.clone();
        return new SoluzioneMatrix(soluzione);
    }
}
