package Gioco.soluzione;

import Gioco.cella.Cella;

import java.util.*;

public class SoluzioneMatrix extends AbstractSoluzione
{
    private Cella[][] celle;

    public SoluzioneMatrix(int dimensione)
    {
        celle = new Cella[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++)
        {
            for (int j = 0; j < dimensione; j++)
            {
                celle[i][j] = new Cella(0);
            }
            risolvi();
            popola(new Random().nextInt(2, dimensione * dimensione));

        }
    }
    @Override
    public void risolvi()
    {
        // TODO col backtracking
    }

    @Override
    public void posiziona(int riga, int colonna, int valore)
    {
        if (controlla(riga, colonna, valore) || valore == 0)
            celle[riga][colonna].setValore(valore);
    }

    @Override
    public void rimuovi(int riga, int colonna)
    {
        celle[riga][colonna].setValore(0);
    }

    @Override
    public boolean controlla(int riga, int colonna, int valore)
    {
        for (int i = 0; i < celle.length; i++)
        {
            if(i != riga && celle[i][colonna].getValore() == valore || i != colonna && celle[riga][i].getValore() == valore)
                return false;
        }
        return celle[riga][colonna].getBlocco().soddisfatto();
    }

    @Override
    public boolean verifica()
    {
        for (int i = 0; i < celle.length; i++)
            for (int j = 0; j < celle.length; j++)
                if (!controlla(i, j, celle[i][j].getValore()) || !celle[i][j].getBlocco().soddisfatto())
                    return false;
        return true;
    }

    @Override
    public void popola(int dimensioneMassima)
    {
        // TODO col backtracking
    }
    @Override
    public List<Cella> vicini(int riga, int colonna)
    {
        List<Cella> vicini = new LinkedList<>();
        int rP = Math.max(riga - 1, 0);
        int cP = Math.max(colonna - 1, 0);
        int rA = Math.min(riga + 1, celle.length - 1);
        int cA = Math.min(colonna + 1, celle.length - 1);

        for (int i = rP; i <= rA; i++)
            for (int j = cP; j <= cA; j++)
                if(i != riga && j != colonna)
                    vicini.add(celle[i][j]);
        return vicini;
    }

    @Override
    public Soluzione creaVariante()
    {
        return null;
    }

    @Override
    public Iterator<Cella> iterator()
    {
        return new Iteratore();
    }

    private class Iteratore implements Iterator<Cella>
    {
        private int[] corrente = {-1, -1};
        private boolean rimuovibile = false;
        int val = -1;

        @Override
        public boolean hasNext()
        {
            return corrente[0] + corrente[1] < (celle.length - 1) * 2;
        }

        @Override
        public Cella next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            corrente[1]+= val;
            if(corrente[1] == celle.length || corrente[0] < 0)
            {
                corrente[0]++;
                val *= -1;
            }
            rimuovibile = true;
            return celle[corrente[0]][corrente[1]];
        }

        @Override
        public void remove()
        {
            if(!rimuovibile)
                throw new IllegalStateException();
            rimuovi(corrente[0], corrente[1]);
        }
    }
}
