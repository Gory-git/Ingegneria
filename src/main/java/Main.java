import Gioco.Gioco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import Gioco.operatore.Operatore;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;
import Interfaccia.Finestra;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException, CloneNotSupportedException
    {
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
/*
        int x = 9;
        Soluzione soluzione = new SoluzioneMatrix(x);
        System.out.println("risolvo la soluzione originale");
        soluzione.risolvi(true);
        System.out.println("soluzione originale risolta");
        System.out.println(soluzione);
        System.out.println("clono la soluzione originale");
        Soluzione clone = soluzione.clone();
        System.out.println("clone");
        System.out.println(clone);
*/

        Gioco.INSTANCE.avvia(1, 9);
        //Gioco.INSTANCE.salva();
        //Gioco.INSTANCE.carica();
        System.out.println(Gioco.INSTANCE.getSoluzioni().get(1));


        Finestra finestra = new Finestra();

        /*while (!soluzione.risolta())
        {
            var in = System.in();

        }*/
    }
}
