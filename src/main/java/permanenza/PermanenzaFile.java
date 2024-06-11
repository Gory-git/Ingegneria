package permanenza;

import memento.Memento;

import java.io.*;

public class PermanenzaFile // TODO
{
    private static final File FILE = new File("Save.dat");
    // private static Mediator mediator = new ConcreteMediator(); FIXME capire se utile
    public PermanenzaFile()
    {

    }

    public static void salva(Memento memento)
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

            out.writeObject(memento);
            //fileOut.close();
            out.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Memento carica()
    {
        Memento ret = null;
        try
        {
            if (!FILE.exists())
            {
                FILE.createNewFile();
            }
            FileInputStream fileIn = new FileInputStream(FILE);
            ObjectInputStream in= new ObjectInputStream(fileIn);

            Object o = in.readObject();
            if(!(o instanceof Memento))
                throw new RuntimeException("Salvataggio corrotto");

            //fileIn.close();
            in.close();
            ret = (Memento) o;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

}
