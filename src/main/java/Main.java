import Gioco.Gioco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import Gioco.operatore.Operatore;

import java.util.LinkedList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        BloccoList prova = new BloccoList(Operatore.SOTTRAZIONE, 2, 3);
        Cella c1 = new Cella(7, prova);
        Cella c2 = new Cella(3, prova);
        Cella c3 = new Cella(1, prova);
        List<Cella> celle = new LinkedList<>();
        celle.add(c1);
        celle.add(c2);
        celle.add(c3);
        prova.setCelle(celle);

        System.out.println("--" + prova.soddisfatto() + "--");
    }
}
