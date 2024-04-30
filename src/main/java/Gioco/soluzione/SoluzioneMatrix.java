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
        popola(new Random().nextInt(2, dimensione * dimensione));

        for (int i = 0; i < dimensione; i++)
        {
            for (int j = 0; j < dimensione; j++)
            {
                System.out.print((celle[i][j].getBlocco() == null) + ", ");
            }
            System.out.println();
        }

        //for (Cella cella : this)
        //    cella.setValore(0);
    }

    public SoluzioneMatrix(Soluzione soluzione, int dimensione)
    {
        celle = new Cella[dimensione][dimensione];
        for (int i = 0; i < dimensione; i++)
        {
            for (int j = 0; j < dimensione; j++)
            {
                int[] posizione = {i, j};
                celle[i][j] = new Cella(0, posizione);
            }
        }

        Iterator<Cella> iterator = soluzione.iterator();

        LinkedList<Blocco> blocchi = new LinkedList<>();
        while (iterator.hasNext())
        {
            Cella next = iterator.next();
            if (!blocchi.contains(next.getBlocco()))
                blocchi.add(next.getBlocco());
        }
        for (Blocco blocco : blocchi)
        {
            Blocco duplicato = blocco.duplica();
            Iterator<Cella> iteratorBlocco = blocco.iterator();
            while (iteratorBlocco.hasNext())
            {
                Cella next = iteratorBlocco.next();
                int[] posizione = next.getPosizione();
                duplicato.aggiungiCella(celle[posizione[0]][posizione[1]]);
                celle[posizione[0]][posizione[1]].setBlocco(duplicato);
            }
        }
        risolvi(true);
    }

    @Override
    public boolean risolviBT(int riga, int colonna, boolean controllaBlocchi) // FIXME speculare
    {
        if ( riga == celle.length || colonna  == celle.length)
            return true;
        List<Integer> scartati = new LinkedList<>();
        while (scartati.size() < celle.length)
        {
            int i = new Random().nextInt(1, celle.length + 1);
            while (scartati.contains(i))
                i = new Random().nextInt(1, celle.length + 1);
            if (controlla(riga, colonna, i))
            {
                posiziona(riga, colonna, i);
                int prossimaColonna = (colonna + 1) % celle.length;
                int prossimaRiga = prossimaColonna == 0 ? riga + 1 : riga;
                if (controllaBlocchi)
                {
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi) && celle[riga][colonna].getBlocco().soddisfatto())
                        return true;
                } else
                {
                    if (risolviBT(prossimaRiga, prossimaColonna, controllaBlocchi))
                        return true;
                }
                rimuovi(riga, colonna);
                scartati.add(i);
            }
            scartati.add(i);
        }
        return false;
    }

    @Override
    public void posiziona(int riga, int colonna, int valore)
    {
        if (controlla(riga, colonna, valore) || valore == 0)
            celle[riga][colonna].setValore(valore);
    }

    @Override
    public boolean controlla(int riga, int colonna, int valore)
    {
        for (int i = 0; i < celle.length; i++)
        {
            if (i != riga && celle[i][colonna].getValore() == valore || i != colonna && celle[riga][i].getValore() == valore)
                return false;
        }
        return true;
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
        popolaBT(dimensioneMassima, celle[0][0]);
    }

    @Override
    public List<Cella> vicini(int riga, int colonna)
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
    public Soluzione creaVariante()
    {
        return new SoluzioneMatrix(this, this.celle.length);
    }

    @Override
    public Iterator<Cella> iterator()
    {
        return new Iteratore();
    }

    private class Iteratore implements Iterator<Cella> // FIXME non funziona, scorre soltanto la prima riga
    {
        private int[] corrente = {-1, -1};
        private boolean rimuovibile = false;

        @Override
        public boolean hasNext()
        {
            return corrente[0] + corrente[1] < (celle.length - 1) * 2;
        }

        @Override
        public Cella next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            corrente[1] ++;
            if (corrente[1] == celle.length || corrente[0] < 0)
            {
                corrente[1] = 0;
                corrente[0] ++;
            }
            return celle[corrente[0]][corrente[1]];
        }

        @Override
        public void remove()
        {
            if (!rimuovibile)
                throw new IllegalStateException();
            rimuovi(corrente[0], corrente[1]);
        }
    }
}
