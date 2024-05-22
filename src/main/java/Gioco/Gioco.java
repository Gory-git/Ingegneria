package Gioco;

import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public enum Gioco implements Serializable
{
    INSTANCE;
    private static final File FILE = new File("Save.dat");
    private LinkedList<Soluzione> soluzioni = new LinkedList<>();

    public void avvia(int soluzioni, int dimensione) throws CloneNotSupportedException, IOException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");

        if (!FILE.exists())
            FILE.createNewFile();

        this.soluzioni.add(new SoluzioneMatrix(dimensione));

        for (int i = 0; i < soluzioni; i++)
            this.soluzioni.add(this.soluzioni.getFirst().clone());
    }

    public void salva()
    {
        try
        {
            if (FILE.exists())
            {
                FILE.delete();
                FILE.createNewFile();
            }
            FileOutputStream fileOut = new FileOutputStream(FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(this);

            //fileOut.close();
            out.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void carica()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream(FILE);
            ObjectInputStream in= new ObjectInputStream(fileIn);

            Object o = in.readObject();
            if(!(o instanceof Gioco))
                throw new RuntimeException("Non riesco a caricare tutte le soluzioni salvate");

            for (Soluzione soluzione : ((Gioco) o).soluzioni)
                this.soluzioni.add(soluzione);


            //fileIn.close();
            in.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean inserisciValore(int riga, int colonna, int valore)
    {
        soluzioni.getFirst().posiziona(riga, colonna, valore);
        return soluzioni.getFirst().verifica();
    }

    public void rimuoviValore(int riga, int colonna)
    {
        soluzioni.getFirst().rimuovi(riga, colonna);
    }

    public List<Soluzione> getSoluzioni()
    {
        return soluzioni;
    }

    private class Memento
    {

    }
}
