package permanenza;

import memento.Memento;

import java.io.*;

public class PermanenzaFile
{
    private static final File FILE;

    static
    {
        FILE = new File("Save.dat");
        try
        {
            FILE.createNewFile();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void salva(Memento memento)
    {
        try
        {
            FileOutputStream fileOut = new FileOutputStream(FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(memento);
            //fileOut.close();
            out.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Memento carica()
    {
        Memento ret;
        try
        {
            FileInputStream fileIn = new FileInputStream(FILE);
            ObjectInputStream in= new ObjectInputStream(fileIn);

            Object o = in.readObject();
            if(!(o instanceof Memento))
                throw new RuntimeException("Salvataggio corrotto");

            //fileIn.close();
            in.close();
            ret = (Memento) o;
        } catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        return ret;
    }

}
