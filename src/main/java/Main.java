import Gioco.Gioco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import Gioco.operatore.Operatore;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Soluzione soluzione = new SoluzioneMatrix(4);
        Iterator<Cella> it = soluzione.iterator();
        soluzione.risolvi(false);
        while (it.hasNext())
            System.out.println(it.next().getValore());
        //System.out.println(soluzione);
    }
}
