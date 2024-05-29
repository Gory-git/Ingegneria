import Gioco.Gioco;
import Gioco.blocco.Blocco;
import Gioco.blocco.BloccoList;
import Gioco.cella.Cella;
import Gioco.mediator.ConcreteMediator;
import Gioco.mediator.Mediator;
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
/*
        //Gioco.INSTANCE.avvia(2, 7);

        Mediator mediator = new ConcreteMediator();
        mediator.carica();
        System.out.println(Gioco.INSTANCE.getSoluzioni().get(0));

        System.out.println(Gioco.INSTANCE.getSoluzioni().get(1));

        System.out.println(Gioco.INSTANCE.getSoluzioni().get(2));
        //mediator.salva();
*/

        Finestra finestra = new Finestra();
    }
}
