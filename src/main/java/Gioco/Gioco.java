package Gioco;

import Gioco.cella.Cella;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Gioco implements Serializable
{
    private static final File FILE = new File("Save.dat");
    private final ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE));
    private final ObjectInputStream in= new ObjectInputStream(new FileInputStream(FILE));
    private LinkedList<Soluzione> soluzioni;

    public Gioco() throws IOException
    {

        soluzioni = new LinkedList<>();
    }

    public Gioco( int numeroSoluzioni, int dimensione) throws IOException
    {
        this();
        soluzioni.add(new SoluzioneMatrix(dimensione));
        for (int i = 1 ; i < numeroSoluzioni; i++)
        {
            soluzioni.add(soluzioni.getFirst().creaVariante());
        }
        Iterator<Cella> iterator = soluzioni.getFirst().iterator();
        while (iterator.hasNext())
        {
            iterator.next();
            iterator.remove();
        }
    }

    public void avvia()
    {

    }

    public void salva()
    {
        try
        {
            for (Soluzione soluzione : soluzioni)
                out.writeObject(soluzione);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void carica()
    {
        try
        {
            while (in.available() > 0)
            {
                Object o = in.readObject();
                if(!(o instanceof Soluzione))
                    throw new RuntimeException("Non riesco a caricare tutte le soluzioni salvate");
                soluzioni.add((Soluzione)o);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Soluzione> getSoluzioni()
    {
        return soluzioni;
    }
}
