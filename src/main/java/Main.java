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
    public static void main(String[] args) throws IOException, CloneNotSupportedException
    {
        int x = 4;
        Soluzione soluzione = new SoluzioneMatrix(x);
        //Iterator<Cella> it = soluzione.iterator();
        //Blocco[] blocco = new Blocco[]{new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), new BloccoList(x), };
        //int i = 0;
        //soluzione.risolvi(false);
        //System.out.println(soluzione);
        /*while (it.hasNext())
        {
            Cella cella = it.next();
            cella.setBlocco(blocco[i]);
            blocco[i].aggiungiCella(cella);
            i = (i + 1) % x;
        }*/
        //System.out.println(blocco.soddisfatto());
        System.out.println("risolvo la soluzione originale");
        soluzione.risolvi(true);
        System.out.println("soluzione originale risolta");
        System.out.println(soluzione);
        System.out.println("clono la soluzione originale");
        Soluzione clone = soluzione.clone();
        System.out.println("clone");
        System.out.println(clone);

        /*
        Gioco.INSTANCE.avvia(0, 3);
        Gioco.INSTANCE.salva();
        Gioco.INSTANCE.carica();
        System.out.println(Gioco.INSTANCE.getSoluzioni().get(0).dimensione());
         */

        /*while (!soluzione.risolta())
        {
            var in = System.in();

        }*/
    }
}
