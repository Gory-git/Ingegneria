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
    private final LinkedList<Soluzione> soluzioni = new LinkedList<>();

    public void avvia(int soluzioni, int dimensione) throws CloneNotSupportedException
    {
        if (soluzioni < 0)
            throw new IllegalArgumentException("Numero di soluzioni non valido");

        this.soluzioni.add(new SoluzioneMatrix(dimensione));

        for (int i = 0; i < soluzioni; i++)
            this.soluzioni.add(this.soluzioni.getLast().clone());
    }

    public void salva()
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (Soluzione soluzione : soluzioni)
                out.writeObject(soluzione);
            fileOut.close();
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
            while (in.available() > 0)
            {
                Object o = in.readObject();
                if(!(o instanceof Soluzione))
                    throw new RuntimeException("Non riesco a caricare tutte le soluzioni salvate");
                soluzioni.add((Soluzione)o);
            }
            fileIn.close();
            in.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void inserisciValore(int riga, int colonna, int valore)
    {
        soluzioni.getFirst().posiziona(riga, colonna, valore);
    }

    public void rimuoviValore(int riga, int colonna)
    {
        soluzioni.getFirst().rimuovi(riga, colonna);
    }

    public List<Soluzione> getSoluzioni()
    {
        return soluzioni;
    }
}
