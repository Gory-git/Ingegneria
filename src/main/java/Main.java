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
        int x = 3;
        Soluzione soluzione = new SoluzioneMatrix(x);
        Iterator<Cella> it = soluzione.iterator();
        Blocco[] blocco = new Blocco[]{new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), };
        int i = 0;
        //soluzione.risolvi(false);
        while (it.hasNext())
        {
            Cella cella = it.next();
            cella.setBlocco(blocco[i]);
            blocco[i].aggiungiCella(cella);
            i = (i + 1) % x;
        }
        //System.out.println(blocco.soddisfatto());
        System.out.println(soluzione);
    }
}
