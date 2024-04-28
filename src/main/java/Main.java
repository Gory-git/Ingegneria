import Gioco.Gioco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import Gioco.operatore.Operatore;
import Gioco.soluzione.Soluzione;
import Gioco.soluzione.SoluzioneMatrix;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        Soluzione soluzione = new SoluzioneMatrix(4);
        soluzione.risolvi(false);
        System.out.println(soluzione.toString());
    }
}
